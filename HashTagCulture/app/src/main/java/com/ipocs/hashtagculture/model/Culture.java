package com.ipocs.hashtagculture.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dong on 2015-12-01.
 */
public class Culture implements Serializable {

    Integer id;
    String title;
    @SerializedName("mainposterurl")
    String mainPosterUrl;
    @SerializedName("startdate")
    Long startDate;
    @SerializedName("enddate")
    Long endDate;
    String category;
    String area;
    //ArrayList<String> hashTag;
    //boolean bookMark;

    public Culture() {
    }

    public Culture(Integer id, String title, String mainPosterUrl, Long startDate, Long endDate, String category, String area) {
        this.id = id;
        this.title = title;
        this.mainPosterUrl = mainPosterUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.area = area;
    }

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

    public String getMainPosterUrl() {
        return mainPosterUrl;
    }

    public void setMainPosterUrl(String mainPosterUrl) {
        this.mainPosterUrl = mainPosterUrl;
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
}
