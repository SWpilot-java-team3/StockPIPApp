package config;

import java.net.http.HttpClient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson; // Gson 라이브러리 임포트
import com.google.gson.GsonBuilder; // GsonBuilder 임포트 (Gson 객체 설정을 위함)
import javafx.scene.image.ImageView;

public final class AppConstants {
    private AppConstants() {
        // 인스턴스화 방지
    }

    public static final String API_BASE_URL = "https://finnhub.io/api/v1";
    public static final String API_KEY = "d1m5qppr01qvvurkadhgd1m5qppr01qvvurkadi0";
    public static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();



    // 환경설정용 변수
    public static int AlertOption = 0;           // 알림 방식
    public static boolean pipOutlineOption = false; // PIP 테두리 고정
    public static double pipFontSize = 28.0;        // PIP 폰트 크기
    public static int UI_theme = 1;                 // 테마 설정
}