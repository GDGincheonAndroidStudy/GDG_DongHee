package com.ipocs.hashtagculture.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dong on 2015-12-14.
 */
public class DetailCulture implements Serializable {

    Integer id;
    String title;

    @SerializedName("startdate")
    Long startDate;
    @SerializedName("enddate")
    Long endDate;
    String place;
    String category;
    String area;
    String price;
    @SerializedName("homepage")
    String homePage;
    String phone;
    @SerializedName("mainposterurl")
    String mainPosterUrl;
    @SerializedName("detailposterurl")
    String detailPosterUrl;

    Double gpsX;
    Double gpsY;

    @SerializedName("placeurl")
    String placeUrl;

    @SerializedName("placeaddr")
    String placeAddr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMainPosterUrl() {
        return mainPosterUrl;
    }

    public void setMainPosterUrl(String mainPosterUrl) {
        this.mainPosterUrl = mainPosterUrl;
    }

    public String getDetailPosterUrl() {
        return detailPosterUrl;
    }

    public void setDetailPosterUrl(String detailPosterUrl) {
        this.detailPosterUrl = detailPosterUrl;
    }

    public Double getGpsX() {
        return gpsX;
    }

    public void setGpsX(Double gpsX) {
        this.gpsX = gpsX;
    }

    public Double getGpsY() {
        return gpsY;
    }

    public void setGpsY(Double gpsY) {
        this.gpsY = gpsY;
    }

    public String getPlaceUrl() {
        return placeUrl;
    }

    public void setPlaceUrl(String placeUrl) {
        this.placeUrl = placeUrl;
    }

    public String getPlaceAddr() {
        return placeAddr;
    }

    public void setPlaceAddr(String placeAddr) {
        this.placeAddr = placeAddr;
    }

    public DetailCulture(Integer id, String title, Long startDate, Long endDate, String place, String category, String area, String price, String homePage, String phone, String mainPosterUrl, String detailPosterUrl, Double gpsX, Double gpsY, String placeUrl, String placeAddr) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.place = place;
        this.category = category;
        this.area = area;
        this.price = price;
        this.homePage = homePage;
        this.phone = phone;
        this.mainPosterUrl = mainPosterUrl;
        this.detailPosterUrl = detailPosterUrl;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.placeUrl = placeUrl;
        this.placeAddr = placeAddr;
    }

    public DetailCulture() {
    }
}
