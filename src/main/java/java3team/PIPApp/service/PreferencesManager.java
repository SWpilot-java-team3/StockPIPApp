package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import config.*;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        // TODO: Gson 객체 초기화 (LocalDate, LocalDateTime 어댑터 및 ExclusionStrategy 포함)
        GsonBuilder gsonBuilder = new GsonBuilder();

        // TODO: LocalDate 어댑터 등록
        // TODO: LocalDateTime 어댑터 등록
        // TODO: ExclusionStrategy 등록 (Stocks 클래스의 특정 필드 제외)

        this.gson = gsonBuilder.setPrettyPrinting().create();
    }

    /**
     * 현재 StockList에 저장된 주식 종목 설정을 JSON 파일로 저장합니다.
     */
    public void saveSettings() {
        // TODO: 주식 종목 설정 저장 로직
    }

    /**
     * JSON 파일에서 주식 종목 설정을 로드하여 StockList에 적용합니다.
     * 파일이 없거나 파싱 오류가 발생하면 StockList는 초기화됩니다.
     */
    public void loadSettings() {
        // TODO: 주식 종목 설정 로드 로직
    }
}