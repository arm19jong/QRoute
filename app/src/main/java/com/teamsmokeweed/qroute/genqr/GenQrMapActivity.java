package com.teamsmokeweed.qroute.genqr;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;
import com.teamsmokeweed.qroute.R;
import com.teamsmokeweed.qroute.bar.App;
import com.teamsmokeweed.qroute.bar.BlackButtonClicked;
import com.teamsmokeweed.qroute.bar.DoneButtonClicked;

/**
 * Created by jongzazaal on 26/10/2559.
 */

public class GenQrMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMapClickListener {

    double lat, lng;
    EditText latLng;
    Marker mMarker;
    boolean checkFirst;

    GoogleMap googleMap;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gen_qr_map);

        checkFirst=true;

        latLng = (EditText) findViewById(R.id.latLng);

        MapFragment mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_map_container, mapFragment);
        fragmentTransaction.commit();
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        latLng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!latLng.getText().toString().equals("")) {
                    latLng.setError(null);

                    try {
                        String[] sLatLng = latLng.getText().toString().split(",");

                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Float.valueOf(sLatLng[0]), Float.valueOf(sLatLng[1])))
                                .title("Select Location"));

                        LatLng coordinate = new LatLng(Float.valueOf(sLatLng[0])
                                , Float.valueOf(sLatLng[1]));

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                coordinate, 16));
                        latLng.setError(null);
                        //latLng.setEnabled(false);
                        //Toast.makeText(GenQrMapActivity.this, "111", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        latLng.setError("Latitude, Longitude");
                        return;
                    }
                }
                else{
                    latLng.setError("Latitude, Longitude");
                    return;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
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

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng coordinate = new LatLng(location.getLatitude()
                , location.getLongitude());
        lat = location.getLatitude();
        lng = location.getLongitude();

        //latLng.setText("");


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

        if (checkFirst==true) {
            latLng.setText(lat + ", " + lng);
            checkFirst=false;
        }

    }

    @Override
    public void onMapClick(LatLng latLng) {

        this.latLng.setText((float)latLng.latitude+", "+(float)latLng.longitude);
        this.googleMap.clear();
        this.googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("This Location")
        );

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMapClickListener(this);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        //this.googleMap.getUiSettings().my
        this.googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                LatLng coordinate = new LatLng(lat
                        , lng);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                coordinate, 16));


                latLng.setText(lat+", "+lng);
                return true;

            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();
        App.getBus().register(this); // Here we register this activity in bus.
    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
        App.getBus().unregister(this); // Here we unregister this acitivity from the bus.
    }
    @Subscribe
    public void OnBackButtonClicked(BlackButtonClicked blackButtonClicked)
    {
        Intent returnIntent = new Intent();
        //spinner.getId();
        //Log.d("ccccccccccccccccccccc", spinner.getSelectedItem().toString() + " --- "+spinner.getSelectedItemPosition());
        returnIntent.putExtra("latLng", "");
        setResult(RESULT_OK,returnIntent);
        finish();
    }
    @Subscribe
    public void OnDoneButtonClicked(DoneButtonClicked doneButtonClicked) {
        Intent returnIntent = new Intent();
        //spinner.getId();
        //Log.d("ccccccccccccccccccccc", spinner.getSelectedItem().toString() + " --- "+spinner.getSelectedItemPosition());
        returnIntent.putExtra("latLng", latLng.getText().toString());
        setResult(RESULT_OK,returnIntent);
        finish();

    }


}
