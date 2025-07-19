package api.model;

import com.google.gson.annotations.SerializedName;

public class CompanyProfile {

    @SerializedName("name")
    private String name;

    @SerializedName("ticker")
    private String ticker;

    @SerializedName("country")
    private String country;

    @SerializedName("ipo")
    private String ipoDate;

    @SerializedName("weburl")
    private String website;

    @SerializedName("logo")
    private String logoUrl;

    // Getters
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

    @Override
    public String toString() {
        return "[회사 정보] " + name + " (" + ticker + ")"
                + "\n국가: " + country
                + "\n상장일: " + ipoDate
                + "\n웹사이트: " + website
                + "\n로고: " + logoUrl;
    }
}
