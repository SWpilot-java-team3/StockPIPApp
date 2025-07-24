package service;

import api.model.FinnhubApiClient;
import api.model.CompanyProfile;

import java.util.Optional;

// 회사 정보를 가져오는 서비스 클래스
public class CompanyService {

    // Finnhub API 클라이언트 인스턴스
    private final FinnhubApiClient apiClient;

    // 생성자 - API 클라이언트 초기화
    public CompanyService() {
        // TODO: FinnhubApiClient 객체 생성
        this.apiClient = new FinnhubApiClient();
    }

    // 종목 코드(symbol)를 입력받아 회사 프로필 정보를 반환
    public Optional<CompanyProfile> getCompanyInfo(String symbol) {
        // TODO: 빈 문자열 방지 및 대문자 정규화
        // TODO: API 클라이언트로부터 CompanyProfile 받아오기
        return Optional.empty();
    }
}
