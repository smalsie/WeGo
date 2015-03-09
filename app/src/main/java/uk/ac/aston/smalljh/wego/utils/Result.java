package uk.ac.aston.smalljh.wego.utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by joshuahugh on 09/03/15.
 */
public class Result {
    @SerializedName("geometry")
    private Geometry geometry;

    @SerializedName("icon")
    private String icon;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("photos")
    private Photos photos;

    @SerializedName("rating")
    private Double rating;

    @SerializedName("reference")
    private String reference;

    @SerializedName("types")
    private List<String> types;

    @SerializedName("vicinity")
    private String vicinity;

}
