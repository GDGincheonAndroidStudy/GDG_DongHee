package me.dong.gdg_testsample;

/**
 * Created by Dong on 2016-01-15.
 */
public class ProductInfo {

    private String name;
    private String imageUrl;
    private String detailPageUrl;
    private String priductCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetailPageUrl() {
        return detailPageUrl;
    }

    public void setDetailPageUrl(String detailPageUrl) {
        this.detailPageUrl = detailPageUrl;
    }

    public String getPriductCode() {
        return priductCode;
    }

    public void setPriductCode(String priductCode) {
        this.priductCode = priductCode;
    }

    public ProductInfo(String name, String imageUrl, String detailPageUrl, String priductCode) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.detailPageUrl = detailPageUrl;
        this.priductCode = priductCode;
    }

    public ProductInfo() {
        this.name = "";
        this.imageUrl = "";
        this.detailPageUrl = "";
        this.priductCode = "";
    }
}
