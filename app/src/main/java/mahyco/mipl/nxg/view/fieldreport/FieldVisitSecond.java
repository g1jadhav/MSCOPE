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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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

public class FieldVisitSecond extends BaseActivity {

    EditText grower_name_textview,
            issued_seed_area_textview,
            production_code_textview,
            village_textview,
            existing_area_ha_edittext,
            area_loss_ha_textview,
            reason_area_loss,
            no_of_rogued_plants_female_edittext,
            female_off_type_edittext,
            female_volunteer_edittext,
            female_b_type_edittext,
            total_female_plants_textview,
            no_of_rogued_plants_male_edittext,
            male_off_type_edittext,
            male_volunteer_edittext,
            male_b_type_edittext,
            total_male_plants_textview,
            yield_estimate_kg_edittext,
            grower_mobile_no_edittext,
            recommendations_observations_edittext,
            date_of_field_visit_textview,
            staff_name_textview,
            geotag_location_textview;

    String str_grower_name_textview = "",
            str_issued_seed_area_textview = "",
            str_production_code_textview = "",
            str_village_textview = "",
            str_existing_area_ha_edittext = "",
            str_area_loss_ha_textview = "",
            str_reason_area_loss = "",
            str_no_of_rogued_plants_female_edittext = "",
            str_female_off_type_edittext = "",
            str_female_volunteer_edittext = "",
            str_female_b_type_edittext = "",
            str_total_female_plants_textview = "",
            str_no_of_rogued_plants_male_edittext = "",
            str_male_off_type_edittext = "",
            str_male_volunteer_edittext = "",
            str_male_b_type_edittext = "",
            str_total_male_plants_textview = "",
            str_yield_estimate_kg_edittext = "",
            str_grower_mobile_no_edittext = "",
            str_recommendations_observations_edittext = "",
            str_date_of_field_visit_textview = "",
            str_staff_name_textview = "",
            str_crop_stage_spinner = "",
            str_geotag_location_textview = "";
    double longi = 0.0, lati = 0.0;
    String front_path = "";
    Button save_login;
    CCFSerachSpinner crop_stage_spinner;
    private FusedLocationProviderClient fusedLocationClient;
    int cropcode = 0, cropcategory = 0;
    String croptype = "";
    Context context;
    FieldVisitModel fieldVisitModel;
    FieldMonitoringModels fieldMonitoringModels;
    int mYear, mMonth, mDay;
    int userid;
    int countryId = 0;
    int countryCode;
    String staffcode = "";
    SqlightDatabase database;
    AppCompatButton national_id_photo_front_side_btn;
    private File mDocFrontPhotoFile = null;

    ImageView capture_photo_image_view;

    @Override
    protected int getLayout() {
        return R.layout.field_visit_second;
    }

    @Override
    protected void init() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Field Visit Second");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        context = FieldVisitSecond.this;
        database = new SqlightDatabase(context);
        national_id_photo_front_side_btn = findViewById(R.id.national_id_photo_front_side_btn);
        capture_photo_image_view = findViewById(R.id.capture_photo_image_view);
        grower_name_textview = findViewById(R.id.grower_name_textview);
        issued_seed_area_textview = findViewById(R.id.issued_seed_area_textview);
        production_code_textview = findViewById(R.id.production_code_textview);
        village_textview = findViewById(R.id.village_textview);
        existing_area_ha_edittext = findViewById(R.id.existing_area_ha_edittext);
        area_loss_ha_textview = findViewById(R.id.area_loss_ha_textview);
        reason_area_loss = findViewById(R.id.reason_area_loss);
        no_of_rogued_plants_female_edittext = findViewById(R.id.no_of_rogued_plants_female_edittext);
        female_off_type_edittext = findViewById(R.id.female_off_type_edittext);
        female_volunteer_edittext = findViewById(R.id.female_volunteer_edittext);
        female_b_type_edittext = findViewById(R.id.female_b_type_edittext);
        total_female_plants_textview = findViewById(R.id.total_female_plants_textview);
        no_of_rogued_plants_male_edittext = findViewById(R.id.no_of_rogued_plants_male_edittext);
        male_off_type_edittext = findViewById(R.id.male_off_type_edittext);
        male_volunteer_edittext = findViewById(R.id.male_volunteer_edittext);
        male_b_type_edittext = findViewById(R.id.male_b_type_edittext);
        total_male_plants_textview = findViewById(R.id.total_male_plants_textview);
        yield_estimate_kg_edittext = findViewById(R.id.yield_estimate_kg_edittext);
        grower_mobile_no_edittext = findViewById(R.id.grower_mobile_no_edittext);
        recommendations_observations_edittext = findViewById(R.id.recommendations_observations_edittext);
        date_of_field_visit_textview = findViewById(R.id.date_of_field_visit_textview);
        staff_name_textview = findViewById(R.id.staff_name_textview);
        geotag_location_textview = findViewById(R.id.geotag_location_textview);
        crop_stage_spinner = findViewById(R.id.crop_stage_spinner);
        save_login = findViewById(R.id.save_login);
        fieldVisitModel = new FieldVisitModel();
        fieldMonitoringModels = new FieldMonitoringModels();

        countryId = Integer.parseInt(Preferences.get(context, Preferences.COUNTRYCODE));
        staff_name_textview.setText("" + Preferences.get(context, Preferences.USER_NAME));
        staffcode = Preferences.get(context, Preferences.USER_ID);
        grower_name_textview.setText("" + Preferences.get(context, Preferences.SELECTED_GROWERNAME));
        grower_mobile_no_edittext.setText("" + Preferences.get(context, Preferences.SELECTED_GROWERMOBILE));
        userid = Integer.parseInt(Preferences.get(context, Preferences.SELECTED_GROWERID).toString().trim());
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
                submit();
            }
        });

        national_id_photo_front_side_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PickImageDialog.build(new PickSetup().setPickTypes(EPickType.CAMERA))
                            .setOnPickResult(new IPickResult() {
                                @Override
                                public void onPickResult(PickResult r) {
                                    //TODO: do what you have to...
                                    capture_photo_image_view.setVisibility(View.VISIBLE);
                                    capture_photo_image_view.setImageBitmap(r.getBitmap());
                                    // front_path = r.getPath();
                                    try {
                                        mDocFrontPhotoFile = createImageFile("SecondFieldVisit");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    if (mDocFrontPhotoFile != null && r.getBitmap() != null) {
                                        try {
                                            front_path = mDocFrontPhotoFile.getAbsolutePath();
                                            //Z Log.e("temporary", " front_path " + front_path);
                                            FileOutputStream out = new FileOutputStream(front_path);
                                            r.getBitmap().compress(Bitmap.CompressFormat.PNG, 60, out);
                                            out.flush();
                                            out.close();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        showToast(getString(R.string.went_wrong));
                                    }
                                }
                            })
                            .setOnPickCancel(new IPickCancel() {
                                @Override
                                public void onCancelClick() {
                                    //TODO: do what you have to if user clicked cancel
                                }
                            }).show(getSupportFragmentManager());
                } catch (Exception e) {

                }
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

    public void submit() {
        try {

            str_grower_name_textview = grower_name_textview.getText().toString().trim();
            str_issued_seed_area_textview = issued_seed_area_textview.getText().toString().trim();
            str_production_code_textview = production_code_textview.getText().toString().trim();
            str_village_textview = village_textview.getText().toString().trim();
            str_existing_area_ha_edittext = existing_area_ha_edittext.getText().toString().trim();
            str_area_loss_ha_textview = area_loss_ha_textview.getText().toString().trim();
            str_reason_area_loss = reason_area_loss.getText().toString().trim();
            str_no_of_rogued_plants_female_edittext = no_of_rogued_plants_female_edittext.getText().toString().trim();
            str_female_off_type_edittext = female_off_type_edittext.getText().toString().trim();
            str_female_volunteer_edittext = female_volunteer_edittext.getText().toString().trim();
            str_female_b_type_edittext = female_b_type_edittext.getText().toString().trim();
            str_total_female_plants_textview = total_female_plants_textview.getText().toString().trim();
            str_no_of_rogued_plants_male_edittext = no_of_rogued_plants_male_edittext.getText().toString().trim();
            str_male_off_type_edittext = male_off_type_edittext.getText().toString().trim();
            str_male_volunteer_edittext = male_volunteer_edittext.getText().toString().trim();
            str_male_b_type_edittext = male_b_type_edittext.getText().toString().trim();
            str_total_male_plants_textview = total_male_plants_textview.getText().toString().trim();
            str_yield_estimate_kg_edittext = yield_estimate_kg_edittext.getText().toString().trim();
            str_grower_mobile_no_edittext = grower_mobile_no_edittext.getText().toString().trim();
            str_recommendations_observations_edittext = recommendations_observations_edittext.getText().toString().trim();
            str_date_of_field_visit_textview = date_of_field_visit_textview.getText().toString().trim();
            str_staff_name_textview = staff_name_textview.getText().toString().trim();
            str_geotag_location_textview = geotag_location_textview.getText().toString().trim();
            String data = str_grower_name_textview + " " + str_issued_seed_area_textview + " " + str_production_code_textview + " " + str_village_textview + " " + str_existing_area_ha_edittext + " " + str_area_loss_ha_textview + " " + str_reason_area_loss + " " + str_no_of_rogued_plants_female_edittext + " " + str_female_off_type_edittext + " " + str_female_volunteer_edittext + " " + str_female_b_type_edittext + " " + str_total_female_plants_textview + " " + str_no_of_rogued_plants_male_edittext + " " + str_male_off_type_edittext + " " + str_male_volunteer_edittext + " " + str_male_b_type_edittext + " " + str_total_male_plants_textview + " " + str_yield_estimate_kg_edittext + " " + str_grower_mobile_no_edittext + " " + str_recommendations_observations_edittext + " " + str_date_of_field_visit_textview + " " + str_staff_name_textview + " " + str_geotag_location_textview;
            Log.i("Entered Data ", data);

            fieldVisitModel.setUserId(userid);// 1,
            fieldVisitModel.setCountryId(countryId);// 1,
            fieldVisitModel.setCountryMasterId(countryCode);// 90,
            fieldVisitModel.setMandatoryFieldVisitId(2);// 1,
            fieldVisitModel.setFieldVisitType("Mandatory Field Visit");// Mandatory Field Visit,
            fieldVisitModel.setTotalSeedAreaLost(Double.parseDouble(str_area_loss_ha_textview));// 0.02,
            fieldVisitModel.setTaggedAreaInHA(Double.parseDouble("0.0"));// 0.1,
            fieldVisitModel.setExistingAreaInHA(Double.parseDouble(str_existing_area_ha_edittext));// 0.1,
            fieldVisitModel.setReasonForTotalLossed(str_reason_area_loss);// Reason For Total Lossed,
            fieldVisitModel.setFemaleSowingDt("");// 2023-01-15T05;//35;//13.528Z,
            fieldVisitModel.setMaleSowingDt("");// 2023-01-15T05;//35;//13.528Z,
            fieldVisitModel.setIsolationM("");// Yes,
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
            fieldVisitModel.setTotalFemalePlants(Integer.parseInt(str_total_female_plants_textview));// 20,
            fieldVisitModel.setTotalMalePlants(Integer.parseInt(str_total_male_plants_textview));// 20,
            fieldVisitModel.setYieldEstimateInKg(Integer.parseInt(str_yield_estimate_kg_edittext));// 50,
            fieldVisitModel.setObservations(str_recommendations_observations_edittext + " v-" + BuildConfig.VERSION_NAME);// Observations Here,
            fieldVisitModel.setFieldVisitDt(str_date_of_field_visit_textview);// 2023-01-15T05;//35;//13.529Z,
            fieldVisitModel.setLatitude("" + lati);// 19.886857,
            fieldVisitModel.setLongitude("" + longi);// 75.3514908,
            fieldVisitModel.setCapturePhoto(front_path);// ,
            fieldVisitModel.setCreatedBy(str_staff_name_textview);

            fieldVisitModel.setAreaLossInHa(str_area_loss_ha_textview);
            fieldVisitModel.setNoOfRoguedFemalePlants(str_no_of_rogued_plants_female_edittext);
            fieldVisitModel.setNoOfRoguedMalePlants(str_no_of_rogued_plants_male_edittext);
            fieldVisitModel.setSeedProductionMethod("0");
            fieldVisitModel.setRoguingCompletedValidated("0");
            fieldVisitModel.setSingleCobsPerPlant("0");
            fieldVisitModel.setSingleCobsPerPlantInGm("0");
            fieldVisitModel.setUnprocessedSeedReadyInKg("0");
            fieldVisitModel.setPollinationStartDt("0");
            fieldVisitModel.setPollinationEndDt("0");
            fieldVisitModel.setExpectedDtOfHarvesting("0");
            fieldVisitModel.setExpectedDtOfDespatching("0");
            fieldVisitModel.setMaleParentUprooted("0");
            fieldVisitModel.setFieldVisitRoguedPlantModels("0");
            fieldVisitModel.setFieldVisitFruitsCobModelsText("0");

            ArrayList<FieldPlantLaneModels> lst_FieldPlantLaneModels = new ArrayList<>();
            ArrayList<FieldVisitLocationModel> lst_location = new ArrayList<>();

            ArrayList<FieldVisitRoguedPlantModel> lst_roguredPlants = new ArrayList<>();
            ArrayList<FieldVisitFruitsCobModel> lst_fruitCob = new ArrayList<>();
            FieldVisitRoguedPlantModel r_male_offtype=new FieldVisitRoguedPlantModel();
            FieldVisitRoguedPlantModel r_male_volunteer=new FieldVisitRoguedPlantModel();
            FieldVisitRoguedPlantModel r_male_btype=new FieldVisitRoguedPlantModel();
            r_male_offtype.setCategoryType("Male");
            r_male_offtype.setPlantType("OffType");
            r_male_offtype.setNoOfCount(Integer.parseInt(str_male_off_type_edittext.trim()));

            r_male_volunteer.setCategoryType("Male");
            r_male_volunteer.setPlantType("Volunteer");
            r_male_volunteer.setNoOfCount(Integer.parseInt(str_male_volunteer_edittext.trim()));

            r_male_btype.setCategoryType("Male");
            r_male_btype.setPlantType("B-Type");
            r_male_btype.setNoOfCount(Integer.parseInt(str_male_b_type_edittext.trim()));

            FieldVisitRoguedPlantModel r_female_offtype=new FieldVisitRoguedPlantModel();
            FieldVisitRoguedPlantModel r_female_volunteer=new FieldVisitRoguedPlantModel();
            FieldVisitRoguedPlantModel r_female_btype=new FieldVisitRoguedPlantModel();
            r_female_offtype.setCategoryType("Female");
            r_female_offtype.setPlantType("OffType");
            r_female_offtype.setNoOfCount(Integer.parseInt(str_female_off_type_edittext.trim()));

            r_female_volunteer.setCategoryType("Female");
            r_female_volunteer.setPlantType("Volunteer");
            r_female_volunteer.setNoOfCount(Integer.parseInt(str_female_volunteer_edittext.trim()));

            r_female_btype.setCategoryType("Female");
            r_female_btype.setPlantType("B-Type");
            r_female_btype.setNoOfCount(Integer.parseInt(str_female_b_type_edittext.trim()));

            lst_roguredPlants.add(r_male_btype);
            lst_roguredPlants.add(r_male_volunteer);
            lst_roguredPlants.add(r_male_offtype);
            lst_roguredPlants.add(r_female_btype);
            lst_roguredPlants.add(r_female_volunteer);
            lst_roguredPlants.add(r_female_offtype);


            fieldMonitoringModels.setFieldVisitModel(fieldVisitModel);
            fieldMonitoringModels.setFieldVisitLocationModels(lst_location);
            fieldMonitoringModels.setFieldPlantLaneModels(lst_FieldPlantLaneModels);
            fieldMonitoringModels.setFieldVisitFruitsCobModels(lst_fruitCob);
            fieldMonitoringModels.setFieldVisitRoguedPlantModels(lst_roguredPlants);

            JsonObject jsonObject = new JsonParser().parse(new Gson().toJson(fieldMonitoringModels)).getAsJsonObject();
            Log.i("JsonData", jsonObject.toString().trim());

        } catch (Exception e) {
            Toast.makeText(context, "Error in submit" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
