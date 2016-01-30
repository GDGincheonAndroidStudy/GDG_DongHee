package me.dong.gdg_testsample.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by Dong on 2016-01-15.
 */
public class Product {

    @SerializedName("ProductName")
    private String name;

    @SerializedName("ProductImage")
    private String imageUrl;

    @SerializedName("DetailPageUrl")
    private String detailPageUrl;

    @SerializedName("ProductCode")
    private Integer productCode;

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

    public Integer getProductCode() {
        return productCode;
    }

    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    public Product(String name, String imageUrl, String detailPageUrl, Integer productCode) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.detailPageUrl = detailPageUrl;
        this.productCode = productCode;
    }

    public Product() {
        this.name = "";
        this.imageUrl = "";
        this.detailPageUrl = "";
        this.productCode = 0;
    }
}
