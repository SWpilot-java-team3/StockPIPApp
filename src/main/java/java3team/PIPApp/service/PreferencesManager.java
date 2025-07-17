package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject; // JsonObject 임포트 추가
import com.google.gson.JsonParser; // JsonParser 임포트 추가
import com.google.gson.JsonSyntaxException; // JsonSyntaxException 임포트 추가

import config.AppConstants; // AppConstants 임포트

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/**
 * 애플리케이션의 사용자 설정(API 키, 테마 등)을 관리하는 클래스입니다.
 * 설정을 파일에 저장하고 로드하는 기능을 포함합니다.
 * JSON 파일을 사용하여 설정을 저장하는 것을 가정합니다.
 */
public class PreferencesManager {

    private static final String SETTINGS_FILE_NAME = "settings.json";
    private final Gson gson;

    public PreferencesManager() {
        gson = new GsonBuilder().setPrettyPrinting().create(); // 가독성 좋은 JSON 출력을 위한 설정
    }

    /**
     * 애플리케이션 설정을 JSON 파일에 저장합니다.
     * @param settings 저장할 설정 객체
     */
    public void saveSettings() {
        JsonObject settingsJson = new JsonObject();
        settingsJson.addProperty("name", AppConstants.name);
        settingsJson.addProperty("targetPrice", AppConstants.targetPrice);
        settingsJson.addProperty("stopPrice", AppConstants.stopPrice);
        settingsJson.addProperty("refreshMinute", AppConstants.refreshMinute);
        settingsJson.addProperty("refreshSecond", AppConstants.refreshSecond);

        try (FileWriter writer = new FileWriter(SETTINGS_FILE_NAME)) {
            gson.toJson(settingsJson, writer);
            System.out.println("주식 종목 설정이 성공적으로 저장되었습니다: " + SETTINGS_FILE_NAME);
        } catch (IOException e) {
            System.err.println("주식 종목 설정 저장 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * JSON 파일에서 애플리케이션 설정을 로드합니다.
     * AppConstants에 적용합니다.
     */
    public void loadSettings() {
        File settingsFile = new File(SETTINGS_FILE_NAME);
        if (!settingsFile.exists()) {
            System.out.println("설정 파일이 존재하지 않습니다. AppConstants의 주식 종목 기본값이 유지됩니다.");
            // 파일이 없으면 AppConstants의 기본값이 유지됩니다.
            return;
        }

        try (FileReader reader = new FileReader(SETTINGS_FILE_NAME)) {
            // JSON 파일을 JsonObject로 파싱
            JsonObject loadedSettingsJson = JsonParser.parseReader(reader).getAsJsonObject();

            // JsonObject에서 값들을 추출하여 AppConstants에 적용
            if (loadedSettingsJson.has("name")) {
                AppConstants.name = loadedSettingsJson.get("name").getAsString();
            }
            if (loadedSettingsJson.has("targetPrice")) {
                AppConstants.targetPrice = loadedSettingsJson.get("targetPrice").getAsDouble();
            }
            if (loadedSettingsJson.has("stopPrice")) {
                AppConstants.stopPrice = loadedSettingsJson.get("stopPrice").getAsDouble();
            }
            if (loadedSettingsJson.has("refreshMinute")) {
                AppConstants.refreshMinute = loadedSettingsJson.get("refreshMinute").getAsInt();
            }
            if (loadedSettingsJson.has("refreshSecond")) {
                AppConstants.refreshSecond = loadedSettingsJson.get("refreshSecond").getAsInt();
            }


            System.out.println("주식 종목 설정이 성공적으로 로드되어 AppConstants에 적용되었습니다: " + SETTINGS_FILE_NAME);
            System.out.println("로드된 주식 종목 설정: " +
                    "Name=" + AppConstants.name +
                    ", TargetPrice=" + AppConstants.targetPrice +
                    ", StopPrice=" + AppConstants.stopPrice +
                    ", RefreshMinute=" + AppConstants.refreshMinute +
                    ", RefreshSecond=" + AppConstants.refreshSecond);

        } catch (IOException e) {
            System.err.println("설정 로드 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            // 오류 발생 시 AppConstants의 기본값이 유지됩니다.
        } catch (JsonSyntaxException e) {
            System.err.println("설정 파일의 JSON 형식이 올바르지 않습니다: " + e.getMessage());
            e.printStackTrace();
            // JSON 파싱 오류 발생 시 AppConstants의 기본값이 유지됩니다.
        }
    }
}