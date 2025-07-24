package api.model;

import com.google.gson.annotations.SerializedName;

// 회사 프로필 정보를 담는 모델 클래스
public class CompanyProfile {

    // 회사명
    @SerializedName("name")
    private String name;

    // 종목 코드 (티커)
    @SerializedName("ticker")
    private String ticker;

    // 국가
    @SerializedName("country")
    private String country;

    // 상장일
    @SerializedName("ipo")
    private String ipoDate;

    // 공식 웹사이트 주소
    @SerializedName("weburl")
    private String website;

    // 로고 이미지 URL
    @SerializedName("logo")
    private String logoUrl;

    // 산업군
    @SerializedName("finnhubIndustry")
    private String industry;

    // 통화
    @SerializedName("currency")
    private String currency;

    // 거래소
    @SerializedName("exchange")
    private String exchange;

    // 시가총액
    @SerializedName("marketCapitalization")
    private double marketCapitalization;

    // Getter 메서드
    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCountry() {
        return country;
    }

    public String getIpoDate() {
        return ipoDate;
    }

    public String getWebsite() {
        return website;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getIndustry() {
        return industry;
    }

    public String getCurrency() {
        return currency;
    }

    public String getExchange() {
        return exchange;
    }

    public double getMarketCapitalization() {
        return marketCapitalization;
    }

    // 회사 정보 출력용 toString 오버라이드
    @Override
    public String toString() {
        // TODO: 객체 정보를 보기 좋게 출력하기 위한 문자열 구성

        return "";
    }
}
