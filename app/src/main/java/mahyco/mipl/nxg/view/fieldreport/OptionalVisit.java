package mahyco.mipl.nxg.view.fieldreport;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mahyco.mipl.nxg.BuildConfig;
import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.FieldMonitoringModels;
import mahyco.mipl.nxg.model.FieldPlantLaneModels;
import mahyco.mipl.nxg.model.FieldVisitFruitsCobModel;
import mahyco.mipl.nxg.model.FieldVisitLocationModel;
import mahyco.mipl.nxg.model.FieldVisitModel;
import mahyco.mipl.nxg.model.FieldVisitRoguedPlantModel;
import mahyco.mipl.nxg.spinner.CCFSerachSpinner;
import mahyco.mipl.nxg.util.BaseActivity;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.util.SqlightDatabase;

public class OptionalVisit extends BaseActivity {
    EditText grower_name_textview,
            issued_seed_area_textview,
            existing_area_ha_textview,
            production_code_textview,
            village_textview,
            reason_for_optional_visit,
            grower_mobile_no_edittext,
            recommendations_observations_edittext,
            date_of_field_visit_textview,
            geotag_location_textview, staff_name_textview;

    CCFSerachSpinner
            crop_stage_spinner;

    FieldVisitModel fieldVisitModel;
    FieldMonitoringModels fieldMonitoringModels;
    Button save_login;
    double longi = 0.0, lati = 0.0;
    String front_path = "";
    private FusedLocationProviderClient fusedLocationClient;
    int cropcode = 0, cropcategory = 0;
    String croptype = "";
    SqlightDatabase database;
    Context context;
    int mYear, mMonth, mDay;
    int userid;
    int countryId = 0;
    int countryCode;
    double prevExistingArea = 0.0;
    AppCompatTextView optional_field_visit_no_textview;

    String staffcode = "";
    String str_grower_name_textview,
            str_issued_seed_area_textview,
            str_existing_area_ha_textview,
            str_production_code_textview,
            str_village_textview,
            str_reason_for_optional_visit,
            str_grower_mobile_no_edittext,
            str_recommendations_observations_edittext,
            str_date_of_field_visit_textview,
            str_geotag_location_textview,
            str_staff_name_textview,
            str_crop_stage_spinner;

    @Override
    protected int getLayout() {
        return R.layout.optional_field_visit;
    }

    @Override
    protected void init() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Optional Visit");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        context = OptionalVisit.this;
        database = new SqlightDatabase(context);

        optional_field_visit_no_textview = findViewById(R.id.optional_field_visit_no_textview);
        grower_name_textview = findViewById(R.id.grower_name_textview);
        issued_seed_area_textview = findViewById(R.id.issued_seed_area_textview);
        existing_area_ha_textview = findViewById(R.id.existing_area_ha_textview);
        production_code_textview = findViewById(R.id.production_code_textview);
        village_textview = findViewById(R.id.village_textview);
        reason_for_optional_visit = findViewById(R.id.reason_for_optional_visit);
        grower_mobile_no_edittext = findViewById(R.id.grower_mobile_no_edittext);
        recommendations_observations_edittext = findViewById(R.id.recommendations_observations_edittext);
        date_of_field_visit_textview = findViewById(R.id.date_of_field_visit_textview);
        geotag_location_textview = findViewById(R.id.geotag_location_textview);
        staff_name_textview = findViewById(R.id.staff_name_textview);
        crop_stage_spinner = findViewById(R.id.crop_stage_spinner);
        save_login = findViewById(R.id.save_login);


        prevExistingArea = 0.00;
        fieldVisitModel = new FieldVisitModel();
        fieldMonitoringModels = new FieldMonitoringModels();
        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = df.format(c);
            date_of_field_visit_textview.setText(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        countryId = Integer.parseInt(Preferences.get(context, Preferences.COUNTRYCODE));

        prevExistingArea = Double.parseDouble(Preferences.get(context, Preferences.SELECTEVISITEXISITINGAREA));
        existing_area_ha_textview.setText("" + prevExistingArea);

        //   currentStage = Integer.parseInt(Preferences.get(context, Preferences.SELECTEDVISITID));
        staff_name_textview.setText("" + Preferences.get(context, Preferences.USER_NAME));
        staffcode = Preferences.get(context, Preferences.USER_ID);
        grower_name_textview.setText("" + Preferences.get(context, Preferences.SELECTED_GROWERNAME));
        grower_mobile_no_edittext.setText("" + Preferences.get(context, Preferences.SELECTED_GROWERMOBILE));
        userid = Integer.parseInt(Preferences.get(context, Preferences.SELECTED_GROWERID).toString().trim());
        optional_field_visit_no_textview.setText(""+(database.getCount(0,userid)+database.getCountServer(0,userid)+1));
        cropcode = Integer.parseInt(Preferences.get(context, Preferences.SELECTEDCROPECODE).toString().trim());
        issued_seed_area_textview.setText("" + String.format("%.2f", Double.parseDouble(Preferences.get(context, Preferences.SELECTED_GROWERAREA).trim())));
        production_code_textview.setText("" + Preferences.get(context, Preferences.SELECTED_GROWERPRODUCTIONCODE));
        try {
            String str[] = database.getGetGrowerCountryMasterId(Preferences.get(context, Preferences.SELECTED_GROWERUNIQUECODE)).split("~");
            if (str.length > 1) {
                countryCode = Integer.parseInt(str[0].trim());
                village_textview.setText(str[1].trim());

                Toast.makeText(context, "Selected ." + countryCode, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Missing Country Master Id.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Missing Country Master Id.", Toast.LENGTH_SHORT).show();
            countryCode = 0;
        }


        if (cropcode == 113 || cropcode == 115 ||
                cropcode == 201 || cropcode == 202 ||
                cropcode == 203 || cropcode == 204 ||
                cropcode == 205 || cropcode == 206 ||
                cropcode == 207 || cropcode == 208 ||
                cropcode == 209 || cropcode == 210 ||
                cropcode == 211 || cropcode == 212 ||
                cropcode == 213 || cropcode == 214 ||
                cropcode == 215 || cropcode == 216 ||
                cropcode == 217 || cropcode == 218 ||
                cropcode == 219 || cropcode == 220 ||
                cropcode == 221 || cropcode == 222 ||
                cropcode == 223 || cropcode == 224 ||
                cropcode == 225 || cropcode == 226 ||
                cropcode == 227 || cropcode == 228 ||
                cropcode == 229 || cropcode == 230 ||
                cropcode == 231 || cropcode == 232 ||
                cropcode == 233 || cropcode == 234 ||
                cropcode == 235 || cropcode == 236 ||
                cropcode == 237 || cropcode == 238 ||
                cropcode == 240 || cropcode == 241) {
            croptype = "For Cotton Crop";
            cropcategory = 1; // 1 for Cotton / Vegitable Crop


        } else {
            croptype = "For Field Crop";
            cropcategory = 2; // 2 for Field Crop


        }


        save_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit1();
            }
        });

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
                    geotag_location_textview.setText(cordinates);
                    //      Toast.makeText(context, "Location Latitude : " + location.getLatitude() + " Longitude :" + location.getLongitude()+" Hello :" +address, Toast.LENGTH_SHORT).show();
                    //  edGeoTagging.setText(location.getLatitude() + "," + location.getLongitude());
                }
            }
        });


    }


    public void submit1() {
        try {

            str_grower_name_textview = grower_name_textview.getText().toString().trim();
            str_issued_seed_area_textview = issued_seed_area_textview.getText().toString().trim();
            str_existing_area_ha_textview = existing_area_ha_textview.getText().toString().trim();
            str_production_code_textview = production_code_textview.getText().toString().trim();
            str_village_textview = village_textview.getText().toString().trim();
            str_reason_for_optional_visit = reason_for_optional_visit.getText().toString().trim();
            str_grower_mobile_no_edittext = grower_mobile_no_edittext.getText().toString().trim();
            str_recommendations_observations_edittext = recommendations_observations_edittext.getText().toString().trim();
            str_date_of_field_visit_textview = date_of_field_visit_textview.getText().toString().trim();
            str_geotag_location_textview = geotag_location_textview.getText().toString().trim();
            str_staff_name_textview = staff_name_textview.getText().toString().trim();
            str_crop_stage_spinner = crop_stage_spinner.getSelectedItem().toString().trim();

            if (validation()) {
                fieldVisitModel.setUserId(userid);// 1,
                fieldVisitModel.setCountryId(countryId);// 1,
                fieldVisitModel.setCountryMasterId(countryCode);// 90,
                fieldVisitModel.setMandatoryFieldVisitId(0);// 1,
                fieldVisitModel.setFieldVisitType("Optional Field Visit");// Mandatory Field Visit,
                fieldVisitModel.setTotalSeedAreaLost(Double.parseDouble("0.0"));// 0.02,
                fieldVisitModel.setTaggedAreaInHA(Double.parseDouble("0.0"));// 0.1,
                fieldVisitModel.setExistingAreaInHA(Double.parseDouble(str_existing_area_ha_textview));// 0.1,
                fieldVisitModel.setReasonForTotalLossed(str_reason_for_optional_visit);// Reason For Total Lossed,
                fieldVisitModel.setFemaleSowingDt(str_date_of_field_visit_textview);// 2023-01-15T05;//35;//13.528Z,
                fieldVisitModel.setMaleSowingDt(str_date_of_field_visit_textview);// 2023-01-15T05;//35;//13.528Z,
                fieldVisitModel.setIsolationM(str_date_of_field_visit_textview);// Yes,
                fieldVisitModel.setIsolationMeter(0);// 2,
                fieldVisitModel.setCropStage(str_crop_stage_spinner);// For Field Crop,
                fieldVisitModel.setTotalNoOfFemaleLines(Integer.parseInt("0"));// 10,
                fieldVisitModel.setTotalNoOfMaleLines(Integer.parseInt("0"));// 10,
                fieldVisitModel.setFemaleSpacingRRinCM(Integer.parseInt("0"));// 2,
                fieldVisitModel.setFemaleSpacingPPinCM(Integer.parseInt("0"));// 3,
                fieldVisitModel.setMaleSpacingRRinCM(Integer.parseInt("0"));// 2,
                fieldVisitModel.setMaleSpacingPPinCM(Integer.parseInt("0"));// 3,
                fieldVisitModel.setPlantingRatioFemale(Integer.parseInt("0"));// 5,
                fieldVisitModel.setPlantingRatioMale(Integer.parseInt("0"));// 4,
                fieldVisitModel.setCropCategoryType("" + croptype);// For Field Crop,
                fieldVisitModel.setTotalFemalePlants(Integer.parseInt("0"));// 20,
                fieldVisitModel.setTotalMalePlants(Integer.parseInt("0"));// 20,
                fieldVisitModel.setYieldEstimateInKg(Integer.parseInt("0"));// 50,
                fieldVisitModel.setObservations(str_recommendations_observations_edittext + " v-" + BuildConfig.VERSION_NAME);// Observations Here,
                fieldVisitModel.setFieldVisitDt(str_date_of_field_visit_textview);// 2023-01-15T05;//35;//13.529Z,
                fieldVisitModel.setLatitude("" + lati);// 19.886857,
                fieldVisitModel.setLongitude("" + longi);// 75.3514908,
                fieldVisitModel.setCapturePhoto(front_path);// ,
                fieldVisitModel.setCreatedBy(str_staff_name_textview);

                fieldVisitModel.setAreaLossInHa("0");
                fieldVisitModel.setNoOfRoguedFemalePlants("0");
                fieldVisitModel.setNoOfRoguedMalePlants("0");
                fieldVisitModel.setSeedProductionMethod("0");
                fieldVisitModel.setRoguingCompletedValidated("0");
                fieldVisitModel.setSingleCobsPerPlant("0");
                fieldVisitModel.setSingleCobsPerPlantInGm("0");
                fieldVisitModel.setUnprocessedSeedReadyInKg("0");
                fieldVisitModel.setPollinationStartDt(str_date_of_field_visit_textview);
                fieldVisitModel.setPollinationEndDt(str_date_of_field_visit_textview);
                fieldVisitModel.setExpectedDtOfHarvesting(str_date_of_field_visit_textview);
                fieldVisitModel.setExpectedDtOfDespatching(str_date_of_field_visit_textview);
                fieldVisitModel.setMaleParentUprooted("0");

                ArrayList<FieldPlantLaneModels> lst_FieldPlantLaneModels = new ArrayList<>();
                ArrayList<FieldVisitLocationModel> lst_location = new ArrayList<>();

                ArrayList<FieldVisitRoguedPlantModel> lst_roguredPlants = new ArrayList<>();
                ArrayList<FieldVisitFruitsCobModel> lst_fruitCob = new ArrayList<>();

                FieldVisitRoguedPlantModel r_male_btype = new FieldVisitRoguedPlantModel();
                r_male_btype.setCategoryType("Male");
                r_male_btype.setPlantType("OffType");
                r_male_btype.setNoOfCount(0);
                lst_roguredPlants.add(r_male_btype);


                fieldMonitoringModels.setFieldVisitModel(fieldVisitModel);
                fieldMonitoringModels.setFieldVisitLocationModels(lst_location);
                fieldMonitoringModels.setFieldPlantLaneModels(lst_FieldPlantLaneModels);
                fieldMonitoringModels.setFieldVisitFruitsCobModels(lst_fruitCob);
                fieldMonitoringModels.setFieldVisitRoguedPlantModels(lst_roguredPlants);

                JsonArray jsonObjectLocation = new JsonParser().parse(new Gson().toJson(lst_location)).getAsJsonArray();
                JsonArray jsonObjectLine = new JsonParser().parse(new Gson().toJson(lst_FieldPlantLaneModels)).getAsJsonArray();
                JsonArray jsonObjectroguredPlants = new JsonParser().parse(new Gson().toJson(lst_roguredPlants)).getAsJsonArray();
                JsonArray jsonFruitCobds = new JsonParser().parse(new Gson().toJson(lst_fruitCob)).getAsJsonArray();
                fieldVisitModel.setLineData(jsonObjectLine.toString());
                fieldVisitModel.setLocationData(jsonObjectLocation.toString());
                fieldVisitModel.setFieldVisitRoguedPlantModels(jsonObjectroguredPlants.toString().trim());
                fieldVisitModel.setFieldVisitFruitsCobModelsText(jsonFruitCobds.toString().trim());
                JsonObject jsonObject = new JsonParser().parse(new Gson().toJson(fieldMonitoringModels)).getAsJsonObject();
                Log.i("JsonData", jsonObject.toString().trim());
                if (database.addFirstVisit1(fieldVisitModel)) {
                    showDialogwithFinish(context, "Data Saved Successfully.");
                } else {
                    Toast.makeText(context, "Local Data Not Saved.", Toast.LENGTH_SHORT).show();
                }
            } else {
                showNoInternetDialog(context, "Some fields are empty.");
            }
        } catch (Exception e) {

        }
    }

    private boolean validation() {
        try {
            int cnt = 0;
            if (str_grower_name_textview.trim().equals("")) {
                grower_name_textview.setError("Required");
                cnt++;
            }
            if (str_issued_seed_area_textview.trim().equals("")) {
                issued_seed_area_textview.setError("Required");
                cnt++;
            }
            if (str_production_code_textview.trim().equals("")) {
                production_code_textview.setError("Required");
                cnt++;
            }
            if (str_village_textview.trim().equals("")) {
                village_textview.setError("Required");
                cnt++;
            }
            if (str_existing_area_ha_textview.trim().equals("")) {
                existing_area_ha_textview.setError("Required");
                cnt++;
            }

            if (str_grower_mobile_no_edittext.trim().equals("")) {
                grower_mobile_no_edittext.setError("Required");
                cnt++;
            }
            if (str_recommendations_observations_edittext.trim().equals("")) {
                recommendations_observations_edittext.setError("Required");
                cnt++;
            }
            if (str_date_of_field_visit_textview.trim().equals("")) {
                date_of_field_visit_textview.setError("Required");
                cnt++;
            }
            if (str_staff_name_textview.trim().equals("")) {
                staff_name_textview.setError("Required");
                cnt++;
            }
          /*  if (str_geotag_location_textview.trim().equals("")) {
                geotag_location_textview.setError("Required");
                cnt++;
            }*/
            if (str_reason_for_optional_visit.trim().equals("")) {
                reason_for_optional_visit.setError("Required");
                cnt++;
            }
            if (str_crop_stage_spinner.trim().equals("") || str_crop_stage_spinner.trim().contains("Select")) {
                Toast.makeText(context, "Select crop stage.", Toast.LENGTH_SHORT).show();
                cnt++;
            }
            if (cnt == 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

}
