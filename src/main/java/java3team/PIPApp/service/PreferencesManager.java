package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import config.*; // Stocks 클래스를 포함
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // 날짜/시간 포맷터를 위한 임포트
import java.util.ArrayList;
import java.util.List;

/**
 * 애플리케이션의 사용자 설정(API 키, 테마 등)을 관리하는 클래스입니다.
 * 설정을 파일에 저장하고 로드하는 기능을 포함합니다.
 * JSON 파일을 사용하여 설정을 저장하는 것을 가정합니다.
 */
public class PreferencesManager {

    private static final String SETTINGS_FILE_NAME = "settings.json";
    private final Gson gson;

    public PreferencesManager() {
        // GsonBuilder를 사용하여 ExclusionStrategy 및 TypeAdapter를 등록합니다.
        GsonBuilder gsonBuilder = new GsonBuilder();

        // LocalDate 어댑터 등록 (iso 8601 형식)
        gsonBuilder.registerTypeAdapter(LocalDate.class, new com.google.gson.JsonSerializer<LocalDate>() {
            private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            @Override
            public com.google.gson.JsonElement serialize(LocalDate src, Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
                return new com.google.gson.JsonPrimitive(src.format(formatter));
            }
        });
        gsonBuilder.registerTypeAdapter(LocalDate.class, new com.google.gson.JsonDeserializer<LocalDate>() {
            private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            @Override
            public LocalDate deserialize(com.google.gson.JsonElement json, Type typeOfT, com.google.gson.JsonDeserializationContext context) throws com.google.gson.JsonParseException {
                return LocalDate.parse(json.getAsString(), formatter);
            }
        });

        // LocalDateTime 어댑터 등록 (iso 8601 형식)
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new com.google.gson.JsonSerializer<LocalDateTime>() {
            private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            @Override
            public com.google.gson.JsonElement serialize(LocalDateTime src, Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
                return new com.google.gson.JsonPrimitive(src.format(formatter));
            }
        });
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new com.google.gson.JsonDeserializer<LocalDateTime>() {
            private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            @Override
            public LocalDateTime deserialize(com.google.gson.JsonElement json, Type typeOfT, com.google.gson.JsonDeserializationContext context) throws com.google.gson.JsonParseException {
                return LocalDateTime.parse(json.getAsString(), formatter);
            }
        });

        // ExclusionStrategy는 시세 정보와 ImageView만 제외하도록 설정
        // 이 필드들은 프로그램을 시작할 때 API를 통해 새로 가져오거나, 다른 방식으로 처리해야 합니다.
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                // Stocks 클래스의 다음 필드들은 저장하지 않음 (API에서 새로 가져올 정보 또는 직렬화 불가능한 타입)
                return f.getDeclaringClass() == Stocks.class &&
                        (f.getName().equals("currentPrice") ||
                                f.getName().equals("openPrice") ||
                                f.getName().equals("highPrice") ||
                                f.getName().equals("lowPrice") ||
                                f.getName().equals("previousClosePrice") ||
                                f.getName().equals("api_refreshTime") ||
                                f.getName().equals("logoUrl") // ImageView는 직접 직렬화/역직렬화하기 어려우므로 제외
                        );
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });

        this.gson = gsonBuilder.setPrettyPrinting().create();
    }

    /**
     * 현재 StockList에 저장된 주식 종목 설정을 JSON 파일로 저장합니다.
     */
    public void saveSettings() {
        try (FileWriter writer = new FileWriter(SETTINGS_FILE_NAME)) {
            gson.toJson(StockList.stockArray, writer);
            System.out.println("주식 종목 설정이 성공적으로 저장되었습니다: " + SETTINGS_FILE_NAME);
        } catch (IOException e) {
            System.err.println("설정 저장 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * JSON 파일에서 주식 종목 설정을 로드하여 StockList에 적용합니다.
     * 파일이 없거나 파싱 오류가 발생하면 StockList는 초기화됩니다.
     */
    public void loadSettings() {
        File settingsFile = new File(SETTINGS_FILE_NAME);
        if (!settingsFile.exists()) {
            System.out.println("설정 파일이 존재하지 않습니다. StockList는 빈 상태로 시작합니다.");
            StockList.stockArray.clear(); // 기존에 추가된 것이 있을 수도 있으니 비우기.
            return;
        }

        try (FileReader reader = new FileReader(SETTINGS_FILE_NAME)) {
            // List<Stocks> 타입을 Gson이 파싱할 수 있도록 TypeToken을 사용.
            Type stockListType = new TypeToken<ArrayList<Stocks>>() {}.getType();
            List<Stocks> loadedStocks = gson.fromJson(reader, stockListType);

            // 기존 StockList의 내용을 로드된 내용으로 덮어쓰기.
            StockList.stockArray.clear(); // 기존 목록 비우기
            if (loadedStocks != null) {
                StockList.stockArray.addAll(loadedStocks); // 로드된 목록 추가
            }

            System.out.println("주식 종목 설정이 성공적으로 로드되어 StockList에 적용되었습니다: " + SETTINGS_FILE_NAME);
            System.out.println("로드된 주식 종목 수: " + StockList.stockArray.size());

        } catch (IOException e) {
            System.err.println("설정 로드 중 오류 발생: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            System.err.println("설정 파일 파싱 중 오류 발생 (JSON 형식이 올바르지 않음): " + e.getMessage());
            // 잘못된 JSON 파일일 경우 기존 파일 삭제 또는 백업 후 재시작 고려
            StockList.stockArray.clear(); // 오류 발생 시 목록 비우기
        }
    }
}