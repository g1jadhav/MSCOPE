package mahyco.mipl.nxg.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.SphericalUtil;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Locale;

import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.FieldLocation;
import mahyco.mipl.nxg.model.FieldMaster;
import mahyco.mipl.nxg.util.SqlightDatabase;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class AreaTagingActivity extends AppCompatActivity {
    ArrayList<LatLng> latLngArrayList;
    Context context;
    String TAG = "FusedLocationProviderClient";
    TextView txt_location;
    TextView txtLocationDetails;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    LatLng tempLatLong;
    SqlightDatabase database;
    int fieldId;
    TableLayout tbl_locdetails;
    SearchableSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_taging);
        context = AreaTagingActivity.this;
        database = new SqlightDatabase(context);
        fieldId = 0;


        latLngArrayList = new ArrayList<>();
        txt_location = findViewById(R.id.txtLocation);
        txtLocationDetails = findViewById(R.id.txtLocationDetails);
        tbl_locdetails = findViewById(R.id.tbl_locdetails);
        spinner = findViewById(R.id.sp_field);
        startLocationUpdates();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "" + position, Toast.LENGTH_SHORT).show();
                if (position > 0) {
                    fieldId = Integer.parseInt(spinner.getSelectedItem().toString().trim());
                    database.trucateTable("tbl_field_master where FieldId=" + fieldId);
                    FieldMaster fieldMaster = new FieldMaster();
                    fieldMaster.setTotalArea("0");
                    fieldMaster.setFieldId(fieldId);
                    if (database.addFieldMaster(fieldMaster))
                        Toast.makeText(context, "Master Added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Please select the field number.", Toast.LENGTH_SHORT).show();
                    fieldId = 0;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
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
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
        // New location has now been determined
        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        //  Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        txt_location.setText(msg);
        tempLatLong = new LatLng(location.getLatitude(), location.getLongitude());
    }

    public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

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
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    public void addlocation(View view) {
        try {
            if (fieldId > 0) {
                if (tempLatLong != null) {
                    LatLng latLng = tempLatLong;
                    latLngArrayList.add(latLng);
                    Toast.makeText(context, "Location Latitude : " + latLng.latitude + " Longitude :" + latLng.longitude, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Please select the field number.", Toast.LENGTH_SHORT).show();
            }
            showData();

        } catch (Exception e) {
            Log.i("Error is ", e.getMessage());
        }
    }

    private void showData() {
        try {
            //    latLngArrayList.add(latLngArrayList.get(0));
            double area = SphericalUtil.computeArea(latLngArrayList);
            String data = "";
            int i = 1;
            for (LatLng latLng : latLngArrayList) {
                data += "['Point " + i + " ', " + latLng.latitude + ", " + latLng.longitude + ", " + i + "],";
                //  data += i + "- " + latLng.latitude + "," + latLng.longitude + "\n";
                i++;
            }
            Double ar = Math.round((area * 0.00024711) * 100.0) / 100.0;//area(arrayPoints);
            txtLocationDetails.setText(data + " \n Total : " + area + "\n\n Area is(in Acre) : " + ar);
            if (database.updateFieldMaster(fieldId, "" + ar))
                Toast.makeText(context, "Master Updated.", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "addLocation: " + data);
            showLocationData();
        } catch (Exception e) {

        }
    }

    public void showrecords(View view) {
        try {
            latLngArrayList.add(latLngArrayList.get(0));
            double area = SphericalUtil.computeArea(latLngArrayList);
            String data = "";
            int i = 1;
            for (LatLng latLng : latLngArrayList) {

                FieldLocation fieldLocation = new FieldLocation();
                fieldLocation.setFieldId(fieldId);
                fieldLocation.setLatitude("" + latLng.latitude);
                fieldLocation.setLongitude("" + latLng.longitude);
                if (database.addFieldLocation(fieldLocation))
                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "Not Added ", Toast.LENGTH_SHORT).show();
                i++;
            }
            showData();
            latLngArrayList.clear();
        } catch (Exception e) {

        }
    }

    public void clearall(View v) {
        try {

            latLngArrayList.clear();
            database.trucateTable("tbl_field_location where FieldId=" + fieldId);
            showData();
        } catch (Exception e) {

        }
    }

    public void showLocationData() {
        try {

            tbl_locdetails.removeAllViews();
            Log.i("Master", database.getAllFieldMaster().size() + "");
            //    Toast.makeText(context, "" + database.getAllFieldMaster().size(), Toast.LENGTH_SHORT).show();
            for (FieldMaster f : database.getAllFieldMaster()) {

                TableRow row_title = new TableRow(context);
                row_title.setPadding(5, 5, 5, 5);
                row_title.setBackgroundColor(Color.GREEN);

//                TableRow.LayoutParams params = (TableRow.LayoutParams) row_title.getLayoutParams();
//                params.span = 3; //amount of columns you will span
//                row_title.setLayoutParams(params);
                TextView txt_title = new TextView(context);
                TextView txt_tot = new TextView(context);
                TextView txt_area = new TextView(context);
                txt_title.setText("Field - " + f.getFieldId());
                txt_tot.setText(" Tot.Area:");
                txt_area.setText(f.getTotalArea() + " Hec.");


                txt_title.setTypeface(txt_location.getTypeface(), Typeface.BOLD);
                txt_tot.setTypeface(txt_location.getTypeface(), Typeface.BOLD);
                txt_area.setTypeface(txt_location.getTypeface(), Typeface.BOLD);

                row_title.addView(txt_title);
                row_title.addView(txt_tot);
                row_title.addView(txt_area);
                tbl_locdetails.addView(row_title);
                int i = 1;
                Log.i("details", database.getAllFieldDetails(f.getFieldId()).size() + "");
                //  Toast.makeText(context, ""+database.getAllFieldDetails(), Toast.LENGTH_SHORT).show();
                for (FieldLocation fieldLocation : database.getAllFieldDetails(f.getFieldId())) {
                    TableRow row_data = new TableRow(context);
                    TextView txt_srno = new TextView(context);
                    TextView txt_latitude = new TextView(context);
                    TextView txt_longitude = new TextView(context);

                    txt_srno.setText("Point " + i);
                    txt_latitude.setText(" " + fieldLocation.getLatitude());
                    txt_longitude.setText(" " + fieldLocation.getLongitude());

                    txt_srno.setTypeface(txt_location.getTypeface(), Typeface.BOLD);
                    txt_latitude.setTypeface(txt_location.getTypeface(), Typeface.BOLD);
                    txt_longitude.setTypeface(txt_location.getTypeface(), Typeface.BOLD);

                    row_data.addView(txt_srno);
                    row_data.addView(txt_latitude);
                    row_data.addView(txt_longitude);
                    tbl_locdetails.addView(row_data);
                    i++;
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Error" + e.getMessage());
        }
    }

    public void done(View view) {
        try{
            finish();
        }catch (Exception e)
        {

        }
    }
}