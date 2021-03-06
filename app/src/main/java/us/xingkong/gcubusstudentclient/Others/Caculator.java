package us.xingkong.gcubusstudentclient.Others;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SeaLynn0 on 2017/5/19.
 */

public class Caculator {

    private final int maxpoint = 100;

    private ArrayList<Point> plist;
    private LatLng userLatLng;

    public Caculator(LatLng latlng) {
        plist = new ArrayList<>();
        userLatLng = latlng;
    }

    public void addPoint(double longitude, double latitude) {

        plist.add(new Point(longitude, latitude));
        if (plist.size() > maxpoint) {
            plist.remove(0);
        }
    }

    public double getDistance() {
        if (plist.size() <= 0)
            return 0;
        System.out.println("+++++" + userLatLng + "+++++++++++ " + plist.get(plist.size() - 1).getLatLng());
        return AMapUtils.calculateLineDistance(userLatLng, plist.get(plist.size() - 1).getLatLng());
    }

    public double getSpeed() {
        if (plist.size() <= 1)
            return 0;
        Point p1 = plist.get(plist.size() - 2), p2 = plist.get(plist.size() - 1);

        double distance = AMapUtils.calculateLineDistance(p1.getLatLng(), p2.getLatLng());
        double dt = -(p1.getDate().getTime() - p2.getDate().getTime()) / 1000;

        return distance / dt;
    }

    public double getTime() {
        double distance = getDistance();
        double speed = getSpeed();
        if (distance == 0 || speed == 0) {
            return 0;
        }
        return distance / speed;
    }

    private class Point {
        private double longitude;
        private double latitude;
        private Date date;

        Point(double longitude, double latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
            date = new Date();
        }

        LatLng getLatLng() {
            return new LatLng(longitude, latitude);
        }

        Date getDate() {
            return date;
        }
    }
}
