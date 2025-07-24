package api.model;

import com.google.gson.annotations.SerializedName;

// 개별 주식의 시세 정보를 담는 DTO 클래스
public class StockQuote {

    // 현재가
    @SerializedName("c")
    private double currentPrice;

    // 고가 (당일)
    @SerializedName("h")
    private double highPrice;

    // 저가 (당일)
    @SerializedName("l")
    private double lowPrice;

    // 시가 (당일)
    @SerializedName("o")
    private double openPrice;

    // 전일 종가
    @SerializedName("pc")
    private double previousClosePrice;

    // 기본 생성자 (Gson 파싱용)
    public StockQuote() {
        // TODO: 파라미터 없는 생성자 (Gson 자동 주입용)
    }

    // 모든 필드를 초기화하는 생성자
    public StockQuote(double currentPrice, double highPrice, double lowPrice, double openPrice, double previousClosePrice) {
        // TODO: 각 필드를 초기화
    }

    // Getter & Setter
    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }

    public double getHighPrice() { return highPrice; }
    public void setHighPrice(double highPrice) { this.highPrice = highPrice; }

    public double getLowPrice() { return lowPrice; }
    public void setLowPrice(double lowPrice) { this.lowPrice = lowPrice; }

    public double getOpenPrice() { return openPrice; }
    public void setOpenPrice(double openPrice) { this.openPrice = openPrice; }

    public double getPreviousClosePrice() { return previousClosePrice; }
    public void setPreviousClosePrice(double previousClosePrice) { this.previousClosePrice = previousClosePrice; }

    // 객체 정보를 출력하기 위한 문자열 포맷 정의
    @Override
    public String toString() {
        // TODO: 각 시세 항목을 보기 좋게 출력
        return "";
    }
}
