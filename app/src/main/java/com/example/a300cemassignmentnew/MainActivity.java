package com.example.a300cemassignmentnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.util.Log;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

//    Button btn_loca;
//    TextView textView_loca;
//    FusedLocationProviderClient  fusedLocationProviderClient;
    int ACCESS_LOCATION_REQUEST_CODE = 10001;
    private Geocoder geocoder;


    GoogleMap mapAPI;
    SupportMapFragment mapFragment;

    FusedLocationProviderClient fusedLocationProviderClient;

    ArrayList<LatLng>arrayList = new ArrayList<LatLng>();
    LatLng ChungkingMansions = new LatLng(22.29665719164455, 114.1727660342179);
    LatLng CulturalCentre = new LatLng(22.293898112216983, 114.17032357996645);
    LatLng ClockTower = new LatLng(22.29364994057736, 114.16957524368671);
    LatLng MuseumOfArt = new LatLng(22.293664830894336, 114.17212870661996);

    ArrayList<String> title = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAPI);

        mapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        arrayList.add(ChungkingMansions);
        arrayList.add(CulturalCentre);
        arrayList.add(ClockTower);
        arrayList.add(MuseumOfArt);

        title.add("Chung King Mansions");
        title.add("Cultural Centre");
        title.add("Clock Tower");
        title.add("Museum Of Art");

    }

    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        } else {
            askLocationPermission();
        }
    }

    private void getLastLocation() {
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

        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();

        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.d(null, "onSucess" + location.toString());
                    Log.d("null", "onSucess" + location.getLatitude());
                    Log.d("null", "onSucess" + location.getLongitude());
                } else {
                    Log.d("lcation", "null");
                }
            }
        });


        locationTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", "onFailure: " + e.getLocalizedMessage());
            }
        });

    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d("test", "askLocationPermission:");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_LOCATION_REQUEST_CODE);
            }
        }
    }

    public void onMapReady(GoogleMap googleMap) {

        mapAPI = googleMap;
        mapAPI.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        UiSettings uiSettings = mapAPI.getUiSettings();
//        uiSettings.setCompassEnabled(true);
//        mapAPI.setBuildingsEnabled(false);
//        mapAPI.setOnMapClickListener(this);

//        LatLng Maharashra = new LatLng(22.424880146850004, 114.23180782327015);
//        mapAPI.addMarker(new MarkerOptions().position(Maharashra).title("test"));
//        float zoomLevel = 17.0f;
//        mapAPI.moveCamera(CameraUpdateFactory.newLatLngZoom(Maharashra, zoomLevel));

//        LatLng melbourneLatLng = new LatLng(22.29665719164455, 114.1727660342179);
//        Marker melbourne = googleMap.addMarker(
//                new MarkerOptions()
//                        .position(melbourneLatLng)
//                        .title("Chungking Mansions")
//                        .snippet("Chungking Mansions is a building located at 36–44 Nathan Road in Tsim Sha Tsui, Kowloon, Hong Kong. Though the building was supposed to be residential, it is made up of many independent low-budget hotels, shops and other services. As well as selling to the public, the stalls in the building cater to wholesalers shipping goods to Africa and South Asia.[1] The unusual atmosphere of the building is sometimes compared to that of the former Kowloon Walled City"));
//        melbourne.showInfoWindow();

        //add marker on map
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < title.size(); j++) {
                googleMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(i))));
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableUserLocation();
            zoomToUserLocation();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_LOCATION_REQUEST_CODE);
            }
        }

        try {
            List<Address> addresses = geocoder.getFromLocationName("HongKong", 1);
            if (addresses.size() > 0) ;
            {
                Address address = addresses.get(0);
                LatLng HongKong = new LatLng(address.getLatitude(), address.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(HongKong).title(address.getLocality());
                mapAPI.addMarker(markerOptions);
                mapAPI.moveCamera(CameraUpdateFactory.newLatLngZoom(HongKong, 16));

                Log.d(null, "onMapReady" + address.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//       mapAPI.moveCamera(CameraUpdateFactory.newLatLng(Maharashra));

        mapAPI.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String markertitle =  marker.getTitle();

                Intent i = new Intent(MainActivity.this,DetailsActivity.class);
                i.putExtra("title", markertitle);
                startActivity(i);
                return false;
            }
        });
    }


    private void enableUserLocation() {
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
        mapAPI.setMyLocationEnabled(true);
    }

    private void zoomToUserLocation() {
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
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                mapAPI.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

            }
        });
    }


    public void onRequestPermisssResult(int requestCode,@NonNull String[] permission,@NonNull int[] grantResults){
        if(requestCode == ACCESS_LOCATION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                enableUserLocation();
                zoomToUserLocation();
                getLastLocation();
            }else{

            }
        }
    }



}