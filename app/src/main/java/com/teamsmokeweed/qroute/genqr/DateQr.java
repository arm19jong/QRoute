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
        if (des.equals("")){
            des = "-";
        }
        this.des = des;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        if (lat.equals("")){
            lat = Float.valueOf(11);
        }
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {

        if (lng.equals("")){
            lng = Float.valueOf(12);
        }
        this.lng = lng;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        if (placeName.equals("")){
            placeName = "-";
        }
        this.placeName = placeName;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        if (placeType.equals("")){
            placeType = "-";
        }
        this.placeType = placeType;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {

        if (titles.equals("")){
            titles = "-";
        }
        this.titles = titles;
    }

    public String getWebPage() {
        return webPage;
    }

    public void setWebPage(String webPage) {
        if (webPage.equals("")){
            webPage = "-";
        }
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
