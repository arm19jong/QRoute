package com.teamsmokeweed.qroute;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.model.PolylineOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jongzazaal on 15/10/2559.
 */

public class RouteMap extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleApiClient googleApiClient;
    double lat, lng;
    Marker mMarker;
    GoogleMap mMap;
    GoogleMap googleMap;
    int check_navi;
    LatLng latLng;

    TextView ddd, ggg;
    //FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_map);

        Intent i = getIntent();
        //Float a = i.getFloatExtra("lat", 2);
        latLng = new LatLng(i.getFloatExtra("lat", 1), i.getFloatExtra("lng", 1));
        check_navi = 0;

        MapFragment mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_map_container, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(this);

        ddd=(TextView) findViewById(R.id.ddd);
        ggg=(TextView) findViewById(R.id.ggg);

//        mMap = ((SupportMapFragment)getSupportFragmentManager()
//                .findFragmentById(R.id.fragment_map_container)).getMap();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        // Do something with Google Map
        this.googleMap = googleMap;

        //this.googleMap.getUiSettings().setMapToolbarEnabled(true);
        //this.googleMap.getUiSettings().setCompassEnabled(true);
        //this.googleMap.setTrafficEnabled(true);
        //this.googleMap.set
        this.googleMap.setBuildingsEnabled(true);
        this.googleMap.getProjection();


    }

    Toast m_currentToast;

    void showToast(String text) {
        if (m_currentToast == null) {
            m_currentToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        }

        m_currentToast.setText(text);
        m_currentToast.setDuration(Toast.LENGTH_LONG);
        m_currentToast.show();
    }

    public void navigator(LatLng ori, int n) {


        if (check_navi > 0) {
            return;
        }
        check_navi += n;
        String serverKey = "AIzaSyB-bqxEbBrz8S_l9V3i57Xlu8WmHgK5umE";
        final LatLng origin = ori;
        //final LatLng destination = new LatLng(13.728069, 100.774635);
        final LatLng destination = latLng;
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .language(Language.THAI)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        // Do something here
                        //showToast("Success");

                        //googleMap.make(btnRequestDirection, "Success with status : " + direction.getStatus(), Snackbar.LENGTH_SHORT).show();
//                        if (direction.isOK()) {
//                            googleMap.addMarker(new MarkerOptions().position(origin));
//                            googleMap.addMarker(new MarkerOptions().position(destination));
//
//                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
//                            googleMap.addPolyline(DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.RED));
//
//                            showToast(rawBody);
//                        }
                        if (direction.isOK()) {

                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            List<Step> step = leg.getStepList();

                            Info distanceInfo = leg.getDistance();
                            Info durationInfo = leg.getDuration();
                            String distance = distanceInfo.getText();
                            String duration = durationInfo.getText();

                            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(RouteMap.this, directionPositionList, 5, Color.RED);
                            googleMap.addPolyline(polylineOptions);
                            //showToast(distance+"//"+duration);
                            ddd.setText("ถึงจุดหมายในอีก "+distance+"\n"+"ภายในเวลา "+duration);

//                            Info distanceInfo = step.getDistance();
//                            Info durationInfo = step.getDuration();
//                            String distance = distanceInfo.getText();
//                            String duration = durationInfo.getText();

//                            String maneuver = step.getManeuver();
//                            String instruction = step.getHtmlInstruction();

                            String s = "";
                            String s2 = "";
                            for (Step step1: step){
                                s += step1.getDistance().getText()+"//"+step1.getDuration().getText()+
                                        step1.getManeuver()+"ppppp"+step1.getHtmlInstruction()+
                                        "\n";
                                s2+=step1.getHtmlInstruction();
                            }
                            //showToast(s);
                            ggg.setText(Html.fromHtml(s2));





                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something here
                        showToast("Unnnnnnnnnnn");
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // Do something when connected with Google API Client
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient);
        if (locationAvailability.isLocationAvailable()) {
            LocationRequest locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            // Do something when location provider not available
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Do something when Google API Client connection was suspended
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Do something when Google API Client connection failed
    }


    @Override
    public void onLocationChanged(Location location) {
        // Do something when got new current location

        LatLng coordinate = new LatLng(location.getLatitude()
                , location.getLongitude());
        lat = location.getLatitude();
        lng = location.getLongitude();


        //showToast("lat: "+lat+" Lng: "+lng);
//
        if (mMarker != null)
            mMarker.remove();


//        mMarker = googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(lat, lng))
//                .title("Current Position")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
//                );

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                coordinate, 16));

        navigator(coordinate, 0);


    }
}
