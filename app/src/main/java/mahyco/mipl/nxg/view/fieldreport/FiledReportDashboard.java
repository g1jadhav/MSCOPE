package mahyco.mipl.nxg.view.fieldreport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.SphericalUtil;


import java.util.List;

import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.FieldVisitModel_Server;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.util.SqlightDatabase;

public class FiledReportDashboard extends AppCompatActivity implements View.OnClickListener {
    Button btnStage1, btnStage2, btnStage3, btnStage4, btnOptional;
    Context mContext;
    SqlightDatabase database;
    FieldVisitModel_Server serverModel;
    int userid = 0;
    int currentStage = 0;
    double longi = 0.0, lati = 0.0;
    LatLng toLatLng;
    double distanceFromCurrentLoc = 0.0;
    String appName="";

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filed_report_dashboard);
        mContext = FiledReportDashboard.this;
        database = new SqlightDatabase(mContext);
        userid = Integer.parseInt(Preferences.get(mContext, Preferences.SELECTED_GROWERID).toString().trim());

        btnStage1 = findViewById(R.id.btn_stage1);
        btnStage2 = findViewById(R.id.btn_stage2);
        btnStage3 = findViewById(R.id.btn_stage3);
        btnStage4 = findViewById(R.id.btn_stage4);
        btnOptional = findViewById(R.id.btn_optional);

        btnStage1.setOnClickListener(this);
        btnStage2.setOnClickListener(this);
        btnStage3.setOnClickListener(this);
        btnStage4.setOnClickListener(this);
        btnOptional.setOnClickListener(this);
        serverModel = new FieldVisitModel_Server();
        try {
            serverModel = database.getVisitDetailsById(userid);
            // Preferences.save(mContext, Preferences.SELECTED_GROWERNAME, m.getGrowerFullName());
            //  Preferences.save(mContext, Preferences.SELECTED_GROWERMOBILE, m.getGrowerMobileNo());
            //  Preferences.save(mContext, Preferences.SELECTED_GROWERID, "" + m.getGrowerId());
            //  Preferences.save(mContext, Preferences.SELECTED_GROWERAREA, "" + m.getSeedProductionArea());
            //  Preferences.save(mContext, Preferences.SELECTED_GROWERPRODUCTIONCODE, "" + m.getProductionCode());
            //  Preferences.save(mContext, Preferences.SELECTED_GROWERUNIQUECODE, "" + m.getGrowerUniqueCode());
            // Preferences.save(mContext, Preferences.SELECTEDCROPECODE, "" + m.getCropCode());
            Preferences.save(mContext, Preferences.SELECTEVISITEXISITINGAREA, "" + serverModel.getExistingAreaInHA());
            Preferences.save(mContext, Preferences.SELECTEDVISITID, "" + serverModel.getMandatoryFieldVisitId());
            Preferences.save(mContext, Preferences.PREVTOTAL_FEMALE_PLANTS, "" + serverModel.getTotalFemalePlants());
            Preferences.save(mContext, Preferences.PREVTOTAL_MALE_PLANTS, "" + serverModel.getTotalMalePlants());
            currentStage = serverModel.getMandatoryFieldVisitId();
            Toast.makeText(mContext, "Selected Visit " + currentStage, Toast.LENGTH_SHORT).show();

            toLatLng = new LatLng(Double.parseDouble(serverModel.getLatitude().trim()), Double.parseDouble(serverModel.getLongitude()));
            //   toLatLng=new LatLng(19.9541698,75.3537161);
            Toast.makeText(mContext, "Lat :" + serverModel.getLatitude() + " Longi: " + serverModel.getLongitude(), Toast.LENGTH_SHORT).show();
            Log.i("VisitID", "" + serverModel.getMandatoryFieldVisitId());
            Log.i("ExisitingArea", "" + serverModel.getExistingAreaInHA());
        } catch (Exception e) {
            Log.i("Error is ", "" + e.getMessage());

        }

        switch (currentStage + 1) {
            case 1:
                btnStage1.setVisibility(View.VISIBLE);
                btnStage2.setVisibility(View.GONE);
                btnStage3.setVisibility(View.GONE);
                btnStage4.setVisibility(View.GONE);
                btnOptional.setVisibility(View.GONE);
                break;
            case 2:
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setTitle("Skip Second Mandatory Visit")
                        .setMessage("Do you want to skip?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                btnStage1.setVisibility(View.GONE);
                                btnStage2.setVisibility(View.GONE);
                                btnStage3.setVisibility(View.VISIBLE);
                                btnStage4.setVisibility(View.GONE);
                                btnOptional.setVisibility(View.VISIBLE);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                btnStage1.setVisibility(View.GONE);
                btnStage2.setVisibility(View.VISIBLE);
                btnStage3.setVisibility(View.GONE);
                btnStage4.setVisibility(View.GONE);
                btnOptional.setVisibility(View.VISIBLE);
                break;
            case 3:
                btnStage1.setVisibility(View.GONE);
                btnStage2.setVisibility(View.GONE);
                btnStage3.setVisibility(View.VISIBLE);
                btnStage4.setVisibility(View.GONE);
                btnOptional.setVisibility(View.VISIBLE);
                break;
            case 4:
                btnStage1.setVisibility(View.GONE);
                btnStage2.setVisibility(View.GONE);
                btnStage3.setVisibility(View.GONE);
                btnStage4.setVisibility(View.VISIBLE);
                btnOptional.setVisibility(View.VISIBLE);
                break;
            case 5:
                btnStage1.setVisibility(View.GONE);
                btnStage2.setVisibility(View.GONE);
                btnStage3.setVisibility(View.GONE);
                btnStage4.setVisibility(View.GONE);
                btnOptional.setVisibility(View.VISIBLE);
                break;
            default:
                btnStage1.setVisibility(View.GONE);
                btnStage2.setVisibility(View.GONE);
                btnStage3.setVisibility(View.GONE);
                btnStage4.setVisibility(View.GONE);
                btnOptional.setVisibility(View.VISIBLE);
        }


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    // Logic to handle location object

                    lati = location.getLatitude();
                    longi = location.getLongitude();
                    String cordinates = String.valueOf(lati) + "," + String.valueOf(longi);
                    Log.i("Coordinates", cordinates);
                    //  geotag_location_textview.setText(cordinates);
                 //   Toast.makeText(mContext, "Location Latitude : " + location.getLatitude() + " Longitude :" + location.getLongitude(), Toast.LENGTH_SHORT).show();
                    //  edGeoTagging.setText(location.getLatitude() + "," + location.getLongitude());

                    LatLng fromLatLng = new LatLng(lati, longi);
                    if(toLatLng!=null)
                    distanceFromCurrentLoc = SphericalUtil.computeDistanceBetween(fromLatLng, toLatLng);
                   // Toast.makeText(mContext, "Distance From Location :" + distanceFromCurrentLoc, Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


    @Override
    public void onClick(View v) {
        if(longi!=0 && lati!=0) {
            if (isMockSettingsON(mContext)) {
                showMeesageDiaog("These application need to uninstall.\n" + appName);
            } else {
                if (distanceFromCurrentLoc > 500) {
                    showMeesageDiaog("You Are too far from location.Please go to location and then fill the visit details.\nYour distance from field :\nIn Meter :" + String.format("%.2f", distanceFromCurrentLoc) + "meter.\nIn KM :" + String.format("%.2f", (distanceFromCurrentLoc / 1000)) + "km.");
                } else {

                    switch (v.getId()) {
                        case R.id.btn_stage1:
                            if (database.isMandetoryVisitDone(userid, 1)) {
                                // Toast.makeText(mContext, "Field Visit is Done.", Toast.LENGTH_SHORT).show();
                                showMeesageDiaog("Field Visit is Done.");
                            } else
                                showStage(1);

                            break;

                        case R.id.btn_stage2:
                            if (database.isMandetoryVisitDone(userid, 2)) {
                                showMeesageDiaog("Field Visit is Done.");
                            } else
                                showStage(2);
                            break;

                        case R.id.btn_stage3:
                            if (database.isMandetoryVisitDone(userid, 3)) {
                                showMeesageDiaog("Field Visit is Done.");
                            } else
                                showStage(3);
                            break;

                        case R.id.btn_stage4:
                            if (database.isMandetoryVisitDone(userid, 4)) {
                                showMeesageDiaog("Field Visit is Done.");
                            } else
                                showStage(4);
                            break;

                        case R.id.btn_optional:
                            showStage(5);
                            break;
                    }
                }
            }
        }else
        {
            showMeesageDiaog("Location details not found.Please retry.");
        }
    }

    private void showMeesageDiaog(String message) {
        try {
            Dialog mDialog = null;
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("MSCOPE");
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss());
            mDialog = alertDialog.create();
            mDialog.show();
        } catch (Exception e) {

        }
    }

    public void showStage(int stageId) {
        Intent intent = null;
        try {
            switch (stageId) {
                case 1:
                    intent = new Intent(mContext, FieldVisitFirst.class);
                    break;

                case 2:
                    intent = new Intent(mContext, FieldVisitSecond.class);
                    break;

                case 3:
                    intent = new Intent(mContext, FieldVisitThird.class);
                    break;

                case 4:
                    intent = new Intent(mContext, FieldVisitFourth.class);
                    break;

                case 5:
                    intent = new Intent(mContext, OptionalVisit.class);
                    break;
            }
            mContext.startActivity(intent);
        } catch (Exception e) {

        }
    }

    public  boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;

    }
}