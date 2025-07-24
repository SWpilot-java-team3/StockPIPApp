package api.model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import config.AppConstants;

import java.net.URI;
import java.net.http.*;
import java.io.IOException;
import java.util.Optional;

// Finnhub API와 통신하여 주가/회사 정보를 받아오는 HTTP 클라이언트
public class FinnhubApiClient {

    // 기본 API URL
    private static final String BASE_URL = "https://finnhub.io/api/v1";

    // API 키
    private static final String API_KEY = "d1nhhu9r01qovv8jaik0d1nhhu9r01qovv8jaikg"; // TODO: 보안을 위해 환경변수 처리 가능

    // HTTP 클라이언트 및 JSON 파서
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    // 주가 정보를 JSON 문자열 그대로 받아오는 메서드
    public Optional<String> getQuote(String symbol) {
        // TODO: /quote API 호출 후 문자열 그대로 반환
        return Optional.empty();
    }

    // 주가 정보를 StockQuote 객체로 변환하여 반환하는 메서드
    public Optional<StockQuote> getStockQuote(String symbol) {
        // TODO: /quote API 호출 → JSON 파싱 → StockQuote 객체 반환
        return Optional.empty();
    }

    // 회사 기본 정보를 CompanyProfile 객체로 받아오는 메서드
    public Optional<CompanyProfile> fetchCompanyProfile(String symbol) {
        // TODO: /stock/profile2 API 호출 → JSON 파싱 → CompanyProfile 객체 반환
        return Optional.empty();
    }
}
