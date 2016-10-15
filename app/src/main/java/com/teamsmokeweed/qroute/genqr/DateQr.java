package com.teamsmokeweed.qroute.genqr;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jongzazaal on 14/10/2559.
 */

public class DateQr {
    String titles, placeName, placeType, des, webPage, sQr;
    Float lat, lng;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        this.webPage = webPage;
    }

    public String getsQr(){
        //this.sQr=lat+"";
        List<String> messages = Arrays.asList( lat.toString(), lng.toString(), titles, placeName,
                placeType, des, webPage);

        this.sQr = TextUtils.join("#420#", messages);
        return this.sQr;
    }
}
