package com.teamsmokeweed.qroute.genqr;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.squareup.otto.Subscribe;
import com.teamsmokeweed.qroute.R;
import com.teamsmokeweed.qroute.bar.App;
import com.teamsmokeweed.qroute.bar.BlackButtonClicked;
import com.teamsmokeweed.qroute.bar.DoneButtonClicked;
import com.teamsmokeweed.qroute.database.AddDatabaseQr;

import java.io.ByteArrayOutputStream;

/**
 * Created by jongzazaal on 14/10/2559.
 */

public class GenQrActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private Button buttonGen;
            ImageButton currentLocationButton;
    private EditText titles, placeName, placeType, des, latLng, webPage;
    private ImageView imgQrGen;
    private String s;

    private GoogleApiClient googleApiClient;
    double lat, lng;
    Marker mMarker;
    //GoogleMap mMap;
    GoogleMap googleMap;
    //int check_navi;
    //LatLng latLngs;
    boolean currentLocationclick;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gen_qr);


        buttonGen = (Button) findViewById(R.id.buttonGen);
        titles = (EditText) findViewById(R.id.titles);
        placeName = (EditText) findViewById(R.id.placeName);
        placeType = (EditText) findViewById(R.id.placeType);
        des = (EditText) findViewById(R.id.des);
        latLng = (EditText) findViewById(R.id.latLng);
        webPage = (EditText) findViewById(R.id.webPage);
        imgQrGen = (ImageView) findViewById(R.id.imgQrGen);
        currentLocationButton = (ImageButton) findViewById(R.id.currentLocation);
        currentLocationclick = false;


        final DateQr dateQr = new DateQr();
        buttonGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Find screen size
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;

                dateQr.setTitles(titles.getText().toString());
                dateQr.setPlaceName(placeName.getText().toString());
                dateQr.setPlaceType(placeType.getText().toString());
                dateQr.setDes(des.getText().toString());
                if (latLng.getText().toString().equals("")){
                    latLng.setText("11, 12");
                }
                String[] sLatLng = latLng.getText().toString().split(",");
                dateQr.setLat(Float.valueOf(sLatLng[0]));
                dateQr.setLng(Float.valueOf(sLatLng[1]));
                dateQr.setWebPage(webPage.getText().toString());

                String sQr = dateQr.getsQr();
                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(sQr,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smallerDimension);

                Bitmap bitmap = null;
                try {
                    bitmap = qrCodeEncoder.encodeAsBitmap();
                    //imgQrGen.setImageBitmap(bitmap);
                    AddDatabaseQr addDatabaseQr = new AddDatabaseQr(dateQr, getApplicationContext());
                    addDatabaseQr.addDb();
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Intent i = new Intent(getApplicationContext(), GenQr2Activity.class);
                i.putExtra("img",byteArray);
                startActivity(i);


            }
        });

        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLocationclick = !currentLocationclick;
                if (currentLocationclick == true){
                    //latLng.setVisibility(View.INVISIBLE);
                    //latLng.
                    latLng.setText(lat+", "+lng);
                   // currentLocationButton.setColorFilter(getResources().getColor(R.color.colorPrimaryDark));
                    //currentLocationButton.setColorFilter(Color.rgb(0,0,50));
                    //currentLocationButton.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
                    //currentLocationButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
                    currentLocationButton.setBackgroundResource(R.drawable.ic_gps_blue);
                }
                else{
                    //latLng.setVisibility(View.VISIBLE);
                    latLng.setText("");
                    currentLocationButton.setBackgroundResource(R.drawable.ic_gps);
                    //currentLocationButton.setColorFilter(getResources().getColor(R.color.Black));
                }
            }
        });

        //Mapppp
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
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
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
    }

    @Override
    protected void onPause(){
        super.onPause();
        App.getBus().unregister(this); // Here we unregister this acitivity from the bus.
    }

    @Subscribe
    public void OnBackButtonClicked(BlackButtonClicked blackButtonClicked)
    {
        finish();
    }
    @Subscribe
    public void OnDoneButtonClicked(DoneButtonClicked doneButtonClicked)
    {
        final DateQr dateQr = new DateQr();
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3 / 4;

        dateQr.setTitles(titles.getText().toString());
        dateQr.setPlaceName(placeName.getText().toString());
        dateQr.setPlaceType(placeType.getText().toString());
        dateQr.setDes(des.getText().toString());
        if (latLng.getText().toString().equals("")){
            latLng.setText("11, 12");
        }
        String[] sLatLng = latLng.getText().toString().split(",");
        dateQr.setLat(Float.valueOf(sLatLng[0]));
        dateQr.setLng(Float.valueOf(sLatLng[1]));
        dateQr.setWebPage(webPage.getText().toString());

        String sQr = dateQr.getsQr();
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(sQr,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);

        Bitmap bitmap = null;
        try {
            bitmap = qrCodeEncoder.encodeAsBitmap();
            //imgQrGen.setImageBitmap(bitmap);
            AddDatabaseQr addDatabaseQr = new AddDatabaseQr(dateQr, getApplicationContext());
            addDatabaseQr.addDb();
        } catch (WriterException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        Intent i = new Intent(getApplicationContext(), GenQr2Activity.class);
        i.putExtra("img",byteArray);
        startActivity(i);
    }

}
