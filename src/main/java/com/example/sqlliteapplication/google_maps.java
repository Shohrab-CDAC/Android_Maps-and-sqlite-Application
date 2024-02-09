package com.example.sqlliteapplication;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class google_maps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap myMap;
    private LatLng origin;
    private LatLng destination;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Request location permissions if not granted
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            // Request location updates
            requestLocationUpdates();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;

        // Set a click listener for the map
        myMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Check if origin is set, if not set origin, else set destination
                if (origin == null) {
                    origin = latLng;
                    myMap.addMarker(new MarkerOptions().position(origin).title("Origin"));
                } else {
                    destination = latLng;
                    myMap.addMarker(new MarkerOptions().position(destination).title("Destination"));
                    // Draw route when both origin and destination are set
                    drawRoute();
                }
            }
        });

        // Set the initial camera position to the user's current location
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            myMap.setMyLocationEnabled(true);
            getDeviceLocation();
        }
    }

    // Request location updates
    private void requestLocationUpdates() {
        // Implement your code to request location updates here
    }

    // Get the device's current location
    private void getDeviceLocation() {
        try {
            fusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            Location lastKnownLocation = task.getResult();
                            LatLng currentLocation = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                            myMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                            myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f));
                        }
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    // Method to draw a route between origin and destination
    private void drawRoute() {
        // Check if both origin and destination are set
        if (origin != null && destination != null) {
            // Implement your logic to draw the route here
            // You can use a library like Google Directions API or draw a polyline between origin and destination
            // For simplicity, I'll just draw a straight line (polyline) between origin and destination
            myMap.addPolyline(new PolylineOptions()
                    .add(origin, destination)
                    .width(5)
                    .color(Color.RED));
        }
    }
}
