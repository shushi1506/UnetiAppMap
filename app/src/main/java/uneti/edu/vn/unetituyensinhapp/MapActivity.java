package uneti.edu.vn.unetituyensinhapp;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Route;

public class MapActivity extends SampleActivityBase implements OnMapReadyCallback , DirectionFinderListener{

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private GoogleMap mMap;
    private String placePickName;

    private String destination="";
    private Spinner diaChiLienHe;

    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        Button btnFindPath = (Button) findViewById(R.id.btnFindPath);
        diaChiLienHe= (Spinner) findViewById(R.id.spinner_dia_chi_lien_he);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
//                Log.i("abc", "Place: " + place.getName());

//                String placeDetailsStr = place.getName() + "\n"
//                        + place.getId() + "\n"
//                        + place.getLatLng().toString() + "\n"
//                        + place.getAddress() + "\n"
//                        + place.getAttributions();
//                etOrigin.setText(place.getName());
                placePickName= (String) place.getName();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });
        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(diaChiLienHe.getSelectedItem().equals(getResources().getString(R.string.minhKhai))){
                    destination="20.995694, 105.865522";
                }else {
                    destination="20.980092, 105.876253";
                }
                sendRequest();
            }
        });
//        sendRequest();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        LatLng myschool = new LatLng(20.995694, 105.865522);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //   ActivityCompat.requestPermissions(newfeed.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Toast.makeText(getApplicationContext(),"Cấp quyền vị trị cho ứng dụng",Toast.LENGTH_SHORT).show();
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myschool,15));
        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(14.0f);
        mMap.addMarker(new MarkerOptions()
                .title("Đại học kinh tế kỹ thuật công nghiệp Hà Nội")
                .snippet("My school.")
                .position(myschool));
        Location location = mMap.getMyLocation();
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    public String getDestination() {
        return destination;
    }

    private void sendRequest() {
        String origin =placePickName;
        String destination = getDestination();

        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
//********************
//public class MapActivity extends SampleActivityBase implements PlaceSelectionListener {
//
//    private TextView mPlaceDetailsText;
//
//    private TextView mPlaceAttribution;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_map);
//
//        // Retrieve the PlaceAutocompleteFragment.
//        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);
//
//        // Register a listener to receive callbacks when a place has been selected or an error has
//        // occurred.
//        autocompleteFragment.setOnPlaceSelectedListener(this);
//
//        // Retrieve the TextViews that will display details about the selected place.
//        mPlaceDetailsText = (TextView) findViewById(R.id.place_details);
//        mPlaceAttribution = (TextView) findViewById(R.id.place_attribution);
//    }
//
//    /**
//     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
//     */
//    @Override
//    public void onPlaceSelected(Place place) {
//        Log.i(TAG, "Place Selected: " + place.getName());
//
//        // Format the returned place's details and display them in the TextView.
//        mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(), place.getId(),
//                place.getAddress(), place.getPhoneNumber(), place.getWebsiteUri()));
//
//        CharSequence attributions = place.getAttributions();
//        if (!TextUtils.isEmpty(attributions)) {
//            mPlaceAttribution.setText(Html.fromHtml(attributions.toString()));
//        } else {
//            mPlaceAttribution.setText("");
//        }
//    }
//
//    /**
//     * Callback invoked when PlaceAutocompleteFragment encounters an error.
//     */
//    @Override
//    public void onError(Status status) {
//        Log.e(TAG, "onError: Status = " + status.toString());
//
//        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
//                Toast.LENGTH_SHORT).show();
//    }
//
//    /**
//     * Helper method to format information about a place nicely.
//     */
//    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
//                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
//        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
//                websiteUri));
//        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
//                websiteUri));
//
//    }
//}
