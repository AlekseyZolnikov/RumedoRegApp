package ru.rumedo.rumedoregapp.pojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Region {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
}
