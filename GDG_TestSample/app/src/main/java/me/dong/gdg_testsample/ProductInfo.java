package me.dong.gdg_testsample;

/**
 * Created by Dong on 2016-01-15.
 */
public class ProductInfo {

    private String name;
    private String imageUrl;
    private String detailPageUrl;
    private String productCode;

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

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public ProductInfo(String name, String imageUrl, String detailPageUrl, String productCode) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.detailPageUrl = detailPageUrl;
        this.productCode = productCode;
    }

    public ProductInfo() {
        this.name = "";
        this.imageUrl = "";
        this.detailPageUrl = "";
        this.productCode = "";
    }
}
