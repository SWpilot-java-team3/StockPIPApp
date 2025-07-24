package service;

import api.model.FinnhubApiClient;

// Finnhub API를 활용한 주가 정보 서비스 클래스
public class FinnhubService {

    // Finnhub API 클라이언트
    private final FinnhubApiClient client = new FinnhubApiClient();

    // 종목 코드를 입력받아 시세 정보를 JSON 문자열로 반환
    public String getStockQuote(String symbol) {
        // TODO: API 클라이언트를 통해 시세 JSON 받아오기
        // TODO: 실패 시 "오류 발생" 문자열 반환
        return "오류 발생";
    }
}
