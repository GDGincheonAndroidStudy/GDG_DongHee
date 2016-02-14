package me.dong.gdg_testsample.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Dong on 2016-01-15.
 */
//model: 비즈니스 로직과 데이터
public class Product extends RealmObject {

    //기본키(암묵적으로 @Index 지원)
    @PrimaryKey
    @SerializedName("ProductName")
    private String name;

    @SerializedName("ProductImage")
    private String imageUrl;

    @SerializedName("DetailPageUrl")
    private String detailPageUrl;

    //null값을 허용하지 않는다.
    @Required
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
