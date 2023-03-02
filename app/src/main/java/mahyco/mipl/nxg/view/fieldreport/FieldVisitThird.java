package mahyco.mipl.nxg.view.fieldreport;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

public class FieldVisitThird extends BaseActivity {
    EditText
            grower_name_textview,
            issued_seed_area_textview,
            production_code_textview,
            village_textview,
            existing_area_ha_edittext,
            area_loss_ha_textview,
            reason_for_area_loss,
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
            pollination_start_date,
            first_editetext_female_per_line,
            second_editetext_female_per_line,
            third_editetext_female_per_line,
            fourth_editetext_female_per_line,
            fifth_editetext_female_per_line,
            six_editetext_female_per_line,
            seven_editetext_female_per_line,
            eight_editetext_female_per_line,
            nine_editetext_female_per_line,
            ten_editetext_female_per_line,
            number_of_expected_edittextview,
            average_weight_seed_edittextview,
            yield_estimate_kg_edittext,
            grower_mobile_no_edittext,
            recommendations_observations_edittext,
            date_of_field_visit_textview,
            staff_name_textview,
            geotag_location_textview, total_nos_female_lines,
            female_spacing_rr,
            female_spacing_pp,
            male_spacing_rr,
            male_spacing_pp,
            female_planting_ratio,
            male_planting_ratio;

    CCFSerachSpinner
            seed_production_method_spinner,
            roguing_completed_and_validated_spinner,
            crop_stage_spinner,area_lost_spinner;

    Button save_login, buttonfemalelines;
    ImageView
            capture_photo_image_view;
    LinearLayout female_no_lines_layout, female_spacinglayout,image_layout;
    String str_grower_name_textview = "",
            str_issued_seed_area_textview = "",
            str_production_code_textview = "",
            str_village_textview = "",
            str_existing_area_ha_edittext = "",
            str_area_loss_ha_textview = "",
            str_reason_for_area_loss = "",
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
            str_pollination_start_date = "",
            str_first_editetext_female_per_line = "",
            str_second_editetext_female_per_line = "",
            str_third_editetext_female_per_line = "",
            str_fourth_editetext_female_per_line = "",
            str_fifth_editetext_female_per_line = "",
            str_six_editetext_female_per_line = "",
            str_seven_editetext_female_per_line = "",
            str_eight_editetext_female_per_line = "",
            str_nine_editetext_female_per_line = "",
            str_ten_editetext_female_per_line = "",
            str_number_of_expected_edittextview = "",
            str_average_weight_seed_edittextview = "",
            str_yield_estimate_kg_edittext = "",
            str_grower_mobile_no_edittext = "",
            str_recommendations_observations_edittext = "",
            str_date_of_field_visit_textview = "",
            str_staff_name_textview = "",
            str_geotag_location_textview = "",
            str_seed_production_method_spinner = "",
            str_roguing_completed_and_validated_spinner = "",
            str_crop_stage_spinner = "";
    private Dialog lineDialog;
    List<EditText> allEds; // Female
    List<EditText> allEds_male; //male
    List<EditText> allEds_female; //male
    int sum = 0;
    double longi = 0.0, lati = 0.0;
    String front_path = "";
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
    double prevExistingArea = 0.0;

    String staffcode = "";
    SqlightDatabase database;
    AppCompatButton national_id_photo_front_side_btn;
    private File mDocFrontPhotoFile = null;


    int currentStage = 0;
    int totalFemalePlants = 0;
    int totalMalePlants = 0;
    LinearLayout layout_losslayout, layout_existarea;
    int lossStatus = 0;

    @Override
    protected int getLayout() {
        return R.layout.field_visit_third;
    }

    @Override
    protected void init() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Field Visit Third");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        context = FieldVisitThird.this;
        database = new SqlightDatabase(context);
        image_layout = findViewById(R.id.image_layout);
        grower_name_textview = findViewById(R.id.grower_name_textview);
        issued_seed_area_textview = findViewById(R.id.issued_seed_area_textview);
        production_code_textview = findViewById(R.id.production_code_textview);
        village_textview = findViewById(R.id.village_textview);
        existing_area_ha_edittext = findViewById(R.id.existing_area_ha_edittext);
        area_loss_ha_textview = findViewById(R.id.area_loss_ha_textview);
        reason_for_area_loss = findViewById(R.id.reason_for_area_loss);
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
        pollination_start_date = findViewById(R.id.pollination_start_date);
        first_editetext_female_per_line = findViewById(R.id.first_editetext_female_per_line);
        second_editetext_female_per_line = findViewById(R.id.second_editetext_female_per_line);
        third_editetext_female_per_line = findViewById(R.id.third_editetext_female_per_line);
        fourth_editetext_female_per_line = findViewById(R.id.fourth_editetext_female_per_line);
        fifth_editetext_female_per_line = findViewById(R.id.fifth_editetext_female_per_line);
        six_editetext_female_per_line = findViewById(R.id.six_editetext_female_per_line);
        seven_editetext_female_per_line = findViewById(R.id.seven_editetext_female_per_line);
        eight_editetext_female_per_line = findViewById(R.id.eight_editetext_female_per_line);
        nine_editetext_female_per_line = findViewById(R.id.nine_editetext_female_per_line);
        ten_editetext_female_per_line = findViewById(R.id.ten_editetext_female_per_line);
        number_of_expected_edittextview = findViewById(R.id.number_of_expected_edittextview);
        average_weight_seed_edittextview = findViewById(R.id.average_weight_seed_edittextview);
        yield_estimate_kg_edittext = findViewById(R.id.yield_estimate_kg_edittext);
        grower_mobile_no_edittext = findViewById(R.id.grower_mobile_no_edittext);
        recommendations_observations_edittext = findViewById(R.id.recommendations_observations_edittext);
        date_of_field_visit_textview = findViewById(R.id.date_of_field_visit_textview);
        staff_name_textview = findViewById(R.id.staff_name_textview);
        geotag_location_textview = findViewById(R.id.geotag_location_textview);
        national_id_photo_front_side_btn = findViewById(R.id.national_id_photo_front_side_btn);
        capture_photo_image_view = findViewById(R.id.capture_photo_image_view);
        seed_production_method_spinner = findViewById(R.id.seed_production_method_spinner);
        roguing_completed_and_validated_spinner = findViewById(R.id.roguing_completed_and_validated_spinner);
        crop_stage_spinner = findViewById(R.id.crop_stage_spinner);
        female_no_lines_layout = findViewById(R.id.female_no_lines_layout);
        total_nos_female_lines = findViewById(R.id.total_nos_female_lines);
        buttonfemalelines = findViewById(R.id.buttonfemalelines);
        save_login = findViewById(R.id.save_login);
        female_spacing_rr = findViewById(R.id.female_spacing_rr);
        female_spacing_pp = findViewById(R.id.female_spacing_pp);
        male_spacing_rr = findViewById(R.id.male_spacing_rr);
        male_spacing_pp = findViewById(R.id.male_spacing_pp);
        female_planting_ratio = findViewById(R.id.female_planting_ratio);
        male_planting_ratio = findViewById(R.id.male_planting_ratio);
        female_spacinglayout = findViewById(R.id.female_spacinglayout);

        capture_photo_image_view = findViewById(R.id.capture_photo_image_view);
        layout_losslayout = findViewById(R.id.losslayout);
        layout_existarea = findViewById(R.id.existlayout);
        layout_losslayout.setVisibility(View.GONE);

        save_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        buttonfemalelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Female " + total_nos_female_lines.getText(), Toast.LENGTH_SHORT).show();
                String str = total_nos_female_lines.getText().toString().trim();
                if (str.equals("")) {
                    total_nos_female_lines.setError("Please Enter no of female lines.");
                } else {
                    int total = Integer.parseInt(total_nos_female_lines.getText().toString().trim());
                    showLineDialog("Enter No of plants per female line", total, 1);
                }
            }
        });
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
        totalFemalePlants = Integer.parseInt(Preferences.get(context, Preferences.PREVTOTAL_FEMALE_PLANTS));
        totalMalePlants = Integer.parseInt(Preferences.get(context, Preferences.PREVTOTAL_MALE_PLANTS));
        total_female_plants_textview.setText("" + totalFemalePlants);
        total_male_plants_textview.setText("" + totalMalePlants);

        prevExistingArea = Double.parseDouble(Preferences.get(context, Preferences.SELECTEVISITEXISITINGAREA));

        if(prevExistingArea<=0) {
            lossStatus = 1;
            showOther();
        }else
        {
            lossStatus=2;
        }
        existing_area_ha_edittext.setText("" + prevExistingArea);
        try {
            double loss = prevExistingArea - Double.parseDouble(existing_area_ha_edittext.getText().toString().trim());
            area_loss_ha_textview.setText("" + loss);
        } catch (Exception e) {

        }
        currentStage = Integer.parseInt(Preferences.get(context, Preferences.SELECTEDVISITID));
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


        seed_production_method_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (seed_production_method_spinner.getSelectedItem().toString().trim().contains("GMS")) {
                    if (cropcategory == 1) {
                        female_no_lines_layout.setVisibility(View.VISIBLE);
                        female_spacinglayout.setVisibility(View.GONE);
                    } else {
                        female_no_lines_layout.setVisibility(View.GONE);
                        female_spacinglayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    female_no_lines_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
     /*   existing_area_ha_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    hideOther();
                }else
                {
                    showOther();
                }
            }
        });*/
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

        female_off_type_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateFemaleRogudedPlants();
            }
        });
        female_b_type_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateFemaleRogudedPlants();
            }
        });
        female_volunteer_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateFemaleRogudedPlants();
            }
        });

        male_off_type_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateMaleRogudedPlants();
            }
        });

        male_b_type_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateMaleRogudedPlants();
            }
        });
        male_volunteer_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateMaleRogudedPlants();
            }
        });

        existing_area_ha_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (existing_area_ha_edittext.getText().toString().trim().equals("") || existing_area_ha_edittext.getText().toString().trim().equals(".")) {

                } else {
                    double loss = prevExistingArea - Double.parseDouble(existing_area_ha_edittext.getText().toString().trim());
                    area_loss_ha_textview.setText("" + loss);
                    double exa = Double.parseDouble(existing_area_ha_edittext.getText().toString().trim());
                    if(exa<=0)
                    {
                        lossStatus=1;
                        showOther();
                    }else
                    {
                        lossStatus=2;
                        hideOther();
                    }
                }
                if(lossStatus==1)
                {
                    reason_for_area_loss.setText("");
                    str_reason_for_area_loss="";
                }else if(lossStatus == 2)
                {
                    reason_for_area_loss.setText("-");
                    str_reason_for_area_loss="-";
                }
            }
        });
        pollination_start_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */

                        String ssm = "", ssd = "";
                        if ((selectedmonth + 1) < 10)
                            ssm = "0" + (selectedmonth + 1);
                        else
                            ssm = "" + (selectedmonth + 1);
                        if ((selectedday) < 10)
                            ssd = "0" + selectedday;
                        else
                            ssd = "" + selectedday;

                        String dd = selectedyear + "-" + (ssm) + "-" + ssd;
                        pollination_start_date.setText(dd);

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.setTitle("Pollination start date");
                mDatePicker.show();

            }
        });
        onTextChangeEvent();
        area_lost_spinner = findViewById(R.id.area_lost_spinner);
        area_lost_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {


                    lossStatus=1;
                    showOther();
                } else {
                    lossStatus=2;
                    hideOther();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void hideOther() {
        try{
            layout_losslayout.setVisibility(View.GONE);
            layout_existarea.setVisibility(View.VISIBLE);
          //  recommendations_observations_edittext.setVisibility(View.VISIBLE);
            image_layout.setVisibility(View.VISIBLE);
        }catch (Exception e)
        {

        }
    }

    public void showOther() {
        try{
            layout_losslayout.setVisibility(View.VISIBLE);
            layout_existarea.setVisibility(View.GONE);
           // recommendations_observations_edittext.setVisibility(View.GONE);
            image_layout.setVisibility(View.GONE);
        }catch (Exception e)
        {

        }
    }

    private void calculateFemaleRogudedPlants() {
        try {
            if (female_b_type_edittext.getText().toString().trim().equals("")) {

            } else if (female_off_type_edittext.getText().toString().trim().equals("")) {

            } else if (female_volunteer_edittext.getText().toString().trim().equals("")) {

            } else {
                int btype = Integer.parseInt(female_b_type_edittext.getText().toString().trim());
                int offtype = Integer.parseInt(female_off_type_edittext.getText().toString().trim());
                int volunteer = Integer.parseInt(female_volunteer_edittext.getText().toString().trim());
                int total = btype + offtype + volunteer;
                no_of_rogued_plants_female_edittext.setText("" + total);
                //  total_female_plants_textview.setText(""+(totalplants-total));
            }
        } catch (Exception e) {

        }
    }

    private void calculateMaleRogudedPlants() {
        try {

            if (male_b_type_edittext.getText().toString().trim().equals("")) {

            } else if (male_off_type_edittext.getText().toString().trim().equals("")) {

            } else if (male_volunteer_edittext.getText().toString().trim().equals("")) {

            } else {
                int btype = Integer.parseInt(male_b_type_edittext.getText().toString().trim());
                int offtype = Integer.parseInt(male_off_type_edittext.getText().toString().trim());
                int volunteer = Integer.parseInt(male_volunteer_edittext.getText().toString().trim());
                int total = btype + offtype + volunteer;
                no_of_rogued_plants_male_edittext.setText("" + total);
                //  total_female_plants_textview.setText(""+(totalplants-total));
            }
        } catch (Exception e) {

        }
    }


    public void submit() {
        try {

            str_grower_name_textview = grower_name_textview.getText().toString().trim();
            str_issued_seed_area_textview = issued_seed_area_textview.getText().toString().trim();
            str_production_code_textview = production_code_textview.getText().toString().trim();
            str_village_textview = village_textview.getText().toString().trim();
            str_existing_area_ha_edittext = existing_area_ha_edittext.getText().toString().trim();
            str_area_loss_ha_textview = area_loss_ha_textview.getText().toString().trim();
            str_reason_for_area_loss = reason_for_area_loss.getText().toString().trim();
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
            str_pollination_start_date = pollination_start_date.getText().toString().trim();
            str_first_editetext_female_per_line = first_editetext_female_per_line.getText().toString().trim();
            str_second_editetext_female_per_line = second_editetext_female_per_line.getText().toString().trim();
            str_third_editetext_female_per_line = third_editetext_female_per_line.getText().toString().trim();
            str_fourth_editetext_female_per_line = fourth_editetext_female_per_line.getText().toString().trim();
            str_fifth_editetext_female_per_line = fifth_editetext_female_per_line.getText().toString().trim();
            str_six_editetext_female_per_line = six_editetext_female_per_line.getText().toString().trim();
            str_seven_editetext_female_per_line = seven_editetext_female_per_line.getText().toString().trim();
            str_eight_editetext_female_per_line = eight_editetext_female_per_line.getText().toString().trim();
            str_nine_editetext_female_per_line = nine_editetext_female_per_line.getText().toString().trim();
            str_ten_editetext_female_per_line = ten_editetext_female_per_line.getText().toString().trim();
            str_number_of_expected_edittextview = number_of_expected_edittextview.getText().toString().trim();
            str_average_weight_seed_edittextview = average_weight_seed_edittextview.getText().toString().trim();
            str_yield_estimate_kg_edittext = yield_estimate_kg_edittext.getText().toString().trim();
            str_grower_mobile_no_edittext = grower_mobile_no_edittext.getText().toString().trim();
            str_recommendations_observations_edittext = recommendations_observations_edittext.getText().toString().trim();
            str_date_of_field_visit_textview = date_of_field_visit_textview.getText().toString().trim();
            str_staff_name_textview = staff_name_textview.getText().toString().trim();
            str_geotag_location_textview = geotag_location_textview.getText().toString().trim();
            str_seed_production_method_spinner = seed_production_method_spinner.getSelectedItem().toString().trim();
            str_roguing_completed_and_validated_spinner = roguing_completed_and_validated_spinner.getSelectedItem().toString().trim();
            str_crop_stage_spinner = crop_stage_spinner.getSelectedItem().toString().trim();

            String data = str_grower_name_textview + " " +
                    str_issued_seed_area_textview + " " +
                    str_production_code_textview + " " +
                    str_village_textview + " " +
                    str_existing_area_ha_edittext + " " +
                    str_area_loss_ha_textview + " " +
                    str_reason_for_area_loss + " " +
                    str_no_of_rogued_plants_female_edittext + " " +
                    str_female_off_type_edittext + " " +
                    str_female_volunteer_edittext + " " +
                    str_female_b_type_edittext + " " +
                    str_total_female_plants_textview + " " +
                    str_no_of_rogued_plants_male_edittext + " " +
                    str_male_off_type_edittext + " " +
                    str_male_volunteer_edittext + " " +
                    str_male_b_type_edittext + " " +
                    str_total_male_plants_textview + " " +
                    str_pollination_start_date + " " +
                    str_first_editetext_female_per_line + " " +
                    str_second_editetext_female_per_line + " " +
                    str_third_editetext_female_per_line + " " +
                    str_fourth_editetext_female_per_line + " " +
                    str_fifth_editetext_female_per_line + " " +
                    str_six_editetext_female_per_line + " " +
                    str_seven_editetext_female_per_line + " " +
                    str_eight_editetext_female_per_line + " " +
                    str_nine_editetext_female_per_line + " " +
                    str_ten_editetext_female_per_line + " " +
                    str_number_of_expected_edittextview + " " +
                    str_average_weight_seed_edittextview + " " +
                    str_yield_estimate_kg_edittext + " " +
                    str_grower_mobile_no_edittext + " " +
                    str_recommendations_observations_edittext + " " +
                    str_date_of_field_visit_textview + " " +
                    str_staff_name_textview + " " +
                    str_geotag_location_textview + " " +
                    str_seed_production_method_spinner + " " +
                    str_roguing_completed_and_validated_spinner + " " +
                    str_crop_stage_spinner + " ";
            Log.i("Data :", data);

        } catch (Exception e) {
            Log.i("Error is  :", e.getMessage());
        }
    }

    public void submit1() {

        try {

            str_grower_name_textview = grower_name_textview.getText().toString().trim();
            str_issued_seed_area_textview = issued_seed_area_textview.getText().toString().trim();
            str_production_code_textview = production_code_textview.getText().toString().trim();
            str_village_textview = village_textview.getText().toString().trim();
            str_existing_area_ha_edittext = existing_area_ha_edittext.getText().toString().trim();
            str_area_loss_ha_textview = area_loss_ha_textview.getText().toString().trim();
            str_reason_for_area_loss = reason_for_area_loss.getText().toString().trim();
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
            str_crop_stage_spinner = crop_stage_spinner.getSelectedItem().toString();

            str_pollination_start_date = pollination_start_date.getText().toString().trim();
            str_first_editetext_female_per_line = first_editetext_female_per_line.getText().toString().trim();
            str_second_editetext_female_per_line = second_editetext_female_per_line.getText().toString().trim();
            str_third_editetext_female_per_line = third_editetext_female_per_line.getText().toString().trim();
            str_fourth_editetext_female_per_line = fourth_editetext_female_per_line.getText().toString().trim();
            str_fifth_editetext_female_per_line = fifth_editetext_female_per_line.getText().toString().trim();
            str_six_editetext_female_per_line = six_editetext_female_per_line.getText().toString().trim();
            str_seven_editetext_female_per_line = seven_editetext_female_per_line.getText().toString().trim();
            str_eight_editetext_female_per_line = eight_editetext_female_per_line.getText().toString().trim();
            str_nine_editetext_female_per_line = nine_editetext_female_per_line.getText().toString().trim();
            str_ten_editetext_female_per_line = ten_editetext_female_per_line.getText().toString().trim();
            str_number_of_expected_edittextview = number_of_expected_edittextview.getText().toString().trim();
            str_average_weight_seed_edittextview = average_weight_seed_edittextview.getText().toString().trim();
            str_yield_estimate_kg_edittext = yield_estimate_kg_edittext.getText().toString().trim();
            str_grower_mobile_no_edittext = grower_mobile_no_edittext.getText().toString().trim();
            str_seed_production_method_spinner = seed_production_method_spinner.getSelectedItem().toString().trim();
            str_roguing_completed_and_validated_spinner = roguing_completed_and_validated_spinner.getSelectedItem().toString().trim();
            str_crop_stage_spinner = crop_stage_spinner.getSelectedItem().toString().trim();


            String data = str_grower_name_textview + " " + str_issued_seed_area_textview + " " + str_production_code_textview + " " + str_village_textview + " " + str_existing_area_ha_edittext + " " + str_area_loss_ha_textview + " " + str_reason_for_area_loss + " " + str_no_of_rogued_plants_female_edittext + " " + str_female_off_type_edittext + " " + str_female_volunteer_edittext + " " + str_female_b_type_edittext + " " + str_total_female_plants_textview + " " + str_no_of_rogued_plants_male_edittext + " " + str_male_off_type_edittext + " " + str_male_volunteer_edittext + " " + str_male_b_type_edittext + " " + str_total_male_plants_textview + " " + str_yield_estimate_kg_edittext + " " + str_grower_mobile_no_edittext + " " + str_recommendations_observations_edittext + " " + str_date_of_field_visit_textview + " " + str_staff_name_textview + " " + str_geotag_location_textview;
            Log.i("Entered Data ", data);

            if (lossStatus == 1) {
                str_area_loss_ha_textview = "0";
              //  str_reason_for_area_loss = "0";
                str_no_of_rogued_plants_female_edittext = "0";
                str_female_off_type_edittext = "0";
                str_female_volunteer_edittext = "0";
                str_female_b_type_edittext = "0";
                str_total_female_plants_textview = "0";
                str_no_of_rogued_plants_male_edittext = "0";
                str_male_off_type_edittext = "0";
                str_male_volunteer_edittext = "0";
                str_male_b_type_edittext = "0";
                str_total_male_plants_textview = "0";
                str_yield_estimate_kg_edittext = "0";
                // str_grower_mobile_no_edittext = grower_mobile_no_edittext.getText().toString().trim();
                str_recommendations_observations_edittext = "Area Loss";


                str_no_of_rogued_plants_female_edittext = "0";
                str_female_off_type_edittext = "0";
                str_female_volunteer_edittext = "0";
                str_female_b_type_edittext = "0";
                str_total_female_plants_textview = "0";
                str_no_of_rogued_plants_male_edittext = "0";
                str_male_off_type_edittext= "0";
                str_male_volunteer_edittext = "0";
                str_male_b_type_edittext = "0";
                str_total_male_plants_textview = "0";
                str_yield_estimate_kg_edittext= "0";
                str_recommendations_observations_edittext = "0";
                str_crop_stage_spinner = "0";
                str_pollination_start_date = "0";
                str_first_editetext_female_per_line = "0";
                str_second_editetext_female_per_line = "0";
                str_third_editetext_female_per_line ="0";
                str_fourth_editetext_female_per_line = "0";
                str_fifth_editetext_female_per_line ="0";
                str_six_editetext_female_per_line ="0";
                str_seven_editetext_female_per_line ="0";
                str_eight_editetext_female_per_line = "0";
                str_nine_editetext_female_per_line = "0";
                str_ten_editetext_female_per_line ="0";
                str_number_of_expected_edittextview ="0";
                str_average_weight_seed_edittextview ="0";
                str_yield_estimate_kg_edittext ="0";
                str_seed_production_method_spinner = "0";
                str_roguing_completed_and_validated_spinner ="0";
                front_path="NA";
                str_existing_area_ha_edittext="0";


            }
            if (lossStatus == 2)
            {
                str_reason_for_area_loss="NA";
            }

            if (validation()) {
                fieldVisitModel.setUserId(userid);// 1,
                fieldVisitModel.setCountryId(countryId);// 1,
                fieldVisitModel.setCountryMasterId(countryCode);// 90,
                fieldVisitModel.setMandatoryFieldVisitId(3);// 1,
                fieldVisitModel.setFieldVisitType("Mandatory Field Visit");// Mandatory Field Visit,
                fieldVisitModel.setTotalSeedAreaLost(Double.parseDouble(str_area_loss_ha_textview));// 0.02,
                fieldVisitModel.setTaggedAreaInHA(Double.parseDouble("0.0"));// 0.1,
                fieldVisitModel.setExistingAreaInHA(Double.parseDouble(str_existing_area_ha_edittext));// 0.1,
                fieldVisitModel.setReasonForTotalLossed(str_reason_for_area_loss);// Reason For Total Lossed,
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
                fieldVisitModel.setSeedProductionMethod(str_seed_production_method_spinner);
                fieldVisitModel.setRoguingCompletedValidated(str_roguing_completed_and_validated_spinner);
                fieldVisitModel.setSingleCobsPerPlant(str_number_of_expected_edittextview);
                fieldVisitModel.setSingleCobsPerPlantInGm(str_average_weight_seed_edittextview);
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
                FieldVisitRoguedPlantModel r_male_offtype = new FieldVisitRoguedPlantModel();
                FieldVisitRoguedPlantModel r_male_volunteer = new FieldVisitRoguedPlantModel();
                FieldVisitRoguedPlantModel r_male_btype = new FieldVisitRoguedPlantModel();
                r_male_offtype.setCategoryType("Male");
                r_male_offtype.setPlantType("OffType");
                r_male_offtype.setNoOfCount(Integer.parseInt(str_male_off_type_edittext.trim()));

                r_male_volunteer.setCategoryType("Male");
                r_male_volunteer.setPlantType("Volunteer");
                r_male_volunteer.setNoOfCount(Integer.parseInt(str_male_volunteer_edittext.trim()));

                r_male_btype.setCategoryType("Male");
                r_male_btype.setPlantType("B-Type");
                r_male_btype.setNoOfCount(Integer.parseInt(str_male_b_type_edittext.trim()));

                FieldVisitRoguedPlantModel r_female_offtype = new FieldVisitRoguedPlantModel();
                FieldVisitRoguedPlantModel r_female_volunteer = new FieldVisitRoguedPlantModel();
                FieldVisitRoguedPlantModel r_female_btype = new FieldVisitRoguedPlantModel();
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
                   showDialogwithFinish(context,"Data Saved Successfully.");

                } else {
                    Toast.makeText(context, "Local Data Not Saved.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Please check all fields are fill properly.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Error in submit" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
            if (str_existing_area_ha_edittext.trim().equals("")) {
                existing_area_ha_edittext.setError("Required");
                cnt++;
            }
            if (str_area_loss_ha_textview.trim().equals("")) {
                area_loss_ha_textview.setError("Required");
                cnt++;
            }
            if (str_reason_for_area_loss.trim().equals("")) {
                reason_for_area_loss.setError("Required");
                cnt++;
            }
            if (str_no_of_rogued_plants_female_edittext.trim().equals("")) {
                no_of_rogued_plants_female_edittext.setError("Required");
                cnt++;
            }
            if (str_female_off_type_edittext.trim().equals("")) {
                female_off_type_edittext.setError("Required");
                cnt++;
            }
            if (str_female_volunteer_edittext.trim().equals("")) {
                female_volunteer_edittext.setError("Required");
                cnt++;
            }
            if (str_female_b_type_edittext.trim().equals("")) {
                female_b_type_edittext.setError("Required");
                cnt++;
            }
            if (str_total_female_plants_textview.trim().equals("")) {
                total_female_plants_textview.setError("Required");
                cnt++;
            }
            if (str_no_of_rogued_plants_male_edittext.trim().equals("")) {
                no_of_rogued_plants_male_edittext.setError("Required");
                cnt++;
            }
            if (str_male_off_type_edittext.trim().equals("")) {
                male_off_type_edittext.setError("Required");
                cnt++;
            }
            if (str_male_volunteer_edittext.trim().equals("")) {
                male_volunteer_edittext.setError("Required");
                cnt++;
            }
            if (str_male_b_type_edittext.trim().equals("")) {
                male_b_type_edittext.setError("Required");
                cnt++;
            }
            if (str_total_male_plants_textview.trim().equals("")) {
                total_male_plants_textview.setError("Required");
                cnt++;
            }
            if (str_yield_estimate_kg_edittext.trim().equals("")) {
                yield_estimate_kg_edittext.setError("Required");
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
            if (str_geotag_location_textview.trim().equals("")) {
                geotag_location_textview.setError("Required");
                cnt++;
            }

            if (str_pollination_start_date.trim().equals("")) {
                pollination_start_date.setError("Required");
                cnt++;
            }
            if (str_number_of_expected_edittextview.trim().equals("")) {
                number_of_expected_edittextview.setError("Required");
                cnt++;
            }
            if (str_seed_production_method_spinner.trim().equals("") || str_seed_production_method_spinner.trim().contains("Select")) {
                Toast.makeText(context, "Select Seed production method.", Toast.LENGTH_SHORT).show();
                cnt++;
            }
            if (str_roguing_completed_and_validated_spinner.trim().equals("") || str_roguing_completed_and_validated_spinner.trim().contains("Select")) {
                Toast.makeText(context, "Select roguing completed and validated.", Toast.LENGTH_SHORT).show();
                cnt++;
            }

            if (str_crop_stage_spinner.trim().equals("") || str_crop_stage_spinner.trim().contains("Select")) {
                Toast.makeText(context, "Select crop stage.", Toast.LENGTH_SHORT).show();
                cnt++;
            }
            if(lossStatus==2) {
                if (front_path.trim().equals("")) {
                    Toast.makeText(context, "Please capture photo.", Toast.LENGTH_SHORT).show();
                    cnt++;
                }
                if (first_editetext_female_per_line.getText().toString().trim().equals("")) {
                    first_editetext_female_per_line.setError("Required");
                    cnt++;
                }
                if (second_editetext_female_per_line.getText().toString().trim().equals("")) {
                    second_editetext_female_per_line.setError("Required");
                    cnt++;
                }
                if (third_editetext_female_per_line.getText().toString().trim().equals("")) {
                    third_editetext_female_per_line.setError("Required");
                    cnt++;
                }
                if (fourth_editetext_female_per_line.getText().toString().trim().equals("")) {
                    fourth_editetext_female_per_line.setError("Required");
                    cnt++;
                }
                if (fifth_editetext_female_per_line.getText().toString().trim().equals("")) {
                    fifth_editetext_female_per_line.setError("Required");
                    cnt++;
                }
                if (six_editetext_female_per_line.getText().toString().trim().equals("")) {
                    six_editetext_female_per_line.setError("Required");
                    cnt++;
                }
                if (seven_editetext_female_per_line.getText().toString().trim().equals("")) {
                    seven_editetext_female_per_line.setError("Required");
                    cnt++;
                }
                if (eight_editetext_female_per_line.getText().toString().trim().equals("")) {
                    eight_editetext_female_per_line.setError("Required");
                    cnt++;
                }
                if (nine_editetext_female_per_line.getText().toString().trim().equals("")) {
                    nine_editetext_female_per_line.setError("Required");
                    cnt++;
                }
                if (ten_editetext_female_per_line.getText().toString().trim().equals("")) {
                    ten_editetext_female_per_line.setError("Required");
                    cnt++;
                }
                if (average_weight_seed_edittextview.getText().toString().trim().equals("")) {
                    average_weight_seed_edittextview.setError("Required");
                    cnt++;
                }
            }

            if (cnt == 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    private void onTextChangeEvent() {

        first_editetext_female_per_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                performSum();
            }
        });
        second_editetext_female_per_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                performSum();
            }
        });
        third_editetext_female_per_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                performSum();
            }
        });
        fourth_editetext_female_per_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                performSum();
            }
        });
        fifth_editetext_female_per_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                performSum();
            }
        });
        six_editetext_female_per_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                performSum();
            }
        });
        seven_editetext_female_per_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                performSum();
            }
        });
        eight_editetext_female_per_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                performSum();
            }
        });
        nine_editetext_female_per_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                performSum();
            }
        });
        ten_editetext_female_per_line.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                performSum();

            }
        });
        average_weight_seed_edittextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                sum = 0;
                if (first_editetext_female_per_line.getText().toString().trim().equals("")) {
                    first_editetext_female_per_line.setText("0");
                } else {
                    sum += Integer.parseInt(first_editetext_female_per_line.getText().toString().trim());
                }
                if (second_editetext_female_per_line.getText().toString().trim().equals("")) {
                    second_editetext_female_per_line.setText("0");
                } else {
                    sum += Integer.parseInt(second_editetext_female_per_line.getText().toString().trim());
                }
                if (third_editetext_female_per_line.getText().toString().trim().equals("")) {
                    third_editetext_female_per_line.setText("0");
                } else {
                    sum += Integer.parseInt(third_editetext_female_per_line.getText().toString().trim());
                }
                if (fourth_editetext_female_per_line.getText().toString().trim().equals("")) {
                    fourth_editetext_female_per_line.setText("0");
                } else {
                    sum += Integer.parseInt(fourth_editetext_female_per_line.getText().toString().trim());
                }
                if (fifth_editetext_female_per_line.getText().toString().trim().equals("")) {
                    fifth_editetext_female_per_line.setText("0");
                } else {
                    sum += Integer.parseInt(fifth_editetext_female_per_line.getText().toString().trim());
                }
                if (six_editetext_female_per_line.getText().toString().trim().equals("")) {
                    six_editetext_female_per_line.setText("0");
                } else {
                    sum += Integer.parseInt(six_editetext_female_per_line.getText().toString().trim());
                }
                if (seven_editetext_female_per_line.getText().toString().trim().equals("")) {
                    seven_editetext_female_per_line.setText("0");
                } else {
                    sum += Integer.parseInt(seven_editetext_female_per_line.getText().toString().trim());
                }
                if (eight_editetext_female_per_line.getText().toString().trim().equals("")) {
                    eight_editetext_female_per_line.setText("0");
                } else {
                    sum += Integer.parseInt(eight_editetext_female_per_line.getText().toString().trim());
                }
                if (nine_editetext_female_per_line.getText().toString().trim().equals("")) {
                    nine_editetext_female_per_line.setText("0");
                } else {
                    sum += Integer.parseInt(nine_editetext_female_per_line.getText().toString().trim());
                }
                if (ten_editetext_female_per_line.getText().toString().trim().equals("")) {
                    ten_editetext_female_per_line.setText("0");
                } else {
                    sum += Integer.parseInt(ten_editetext_female_per_line.getText().toString().trim());
                }
                if (average_weight_seed_edittextview.getText().toString().trim().equals("")) {

                } else {
                    double ssum = sum;
                    Log.i("sum", "" + sum);
                    double dd = ssum / 10.0;
                    Log.i("sum1", "" + dd);
                    dd = dd + Integer.parseInt(number_of_expected_edittextview.getText().toString().trim());
                    Log.i("sum2", "" + dd);
                    dd = dd * Integer.parseInt(total_female_plants_textview.getText().toString().trim());
                    Log.i("sum3", "" + dd);
                    dd = dd * Double.parseDouble(average_weight_seed_edittextview.getText().toString().trim());
                    Log.i("sum4", "" + dd);
                    dd = dd / 1000;
                    Log.i("sum5", "" + dd);
                    double d = dd;

                    yield_estimate_kg_edittext.setText("" + (int) Math.round(d));
                }
            }
        });

        male_planting_ratio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    setFieldCropArea();


                }
            }
        });
    }
    public void performSum() {
        try {
            int cnt = 0;
            sum = 0;
            if (first_editetext_female_per_line.getText().toString().trim().equals("")) {
                //    first_editetext_female_per_line.setText("0");
                cnt++;
            } else {
                sum += Integer.parseInt(first_editetext_female_per_line.getText().toString().trim());
            }
            if (second_editetext_female_per_line.getText().toString().trim().equals("")) {
                //   second_editetext_female_per_line.setText("0");
                cnt++;
            } else {
                sum += Integer.parseInt(second_editetext_female_per_line.getText().toString().trim());
            }
            if (third_editetext_female_per_line.getText().toString().trim().equals("")) {
                //    third_editetext_female_per_line.setText("0");
                cnt++;
            } else {
                sum += Integer.parseInt(third_editetext_female_per_line.getText().toString().trim());
            }
            if (fourth_editetext_female_per_line.getText().toString().trim().equals("")) {
                //  fourth_editetext_female_per_line.setText("0");
                cnt++;
            } else {
                sum += Integer.parseInt(fourth_editetext_female_per_line.getText().toString().trim());
            }
            if (fifth_editetext_female_per_line.getText().toString().trim().equals("")) {
                //  fifth_editetext_female_per_line.setText("0");
                cnt++;
            } else {
                sum += Integer.parseInt(fifth_editetext_female_per_line.getText().toString().trim());
            }
            if (six_editetext_female_per_line.getText().toString().trim().equals("")) {
                //  six_editetext_female_per_line.setText("0");
                cnt++;
            } else {
                sum += Integer.parseInt(six_editetext_female_per_line.getText().toString().trim());
            }
            if (seven_editetext_female_per_line.getText().toString().trim().equals("")) {
                //   seven_editetext_female_per_line.setText("0");
                cnt++;
            } else {
                sum += Integer.parseInt(seven_editetext_female_per_line.getText().toString().trim());
            }
            if (eight_editetext_female_per_line.getText().toString().trim().equals("")) {
                //  eight_editetext_female_per_line.setText("0");
                cnt++;
            } else {
                sum += Integer.parseInt(eight_editetext_female_per_line.getText().toString().trim());
            }
            if (nine_editetext_female_per_line.getText().toString().trim().equals("")) {
                // nine_editetext_female_per_line.setText("0");
                cnt++;
            } else {
                sum += Integer.parseInt(nine_editetext_female_per_line.getText().toString().trim());
            }
            if (ten_editetext_female_per_line.getText().toString().trim().equals("")) {
                //    ten_editetext_female_per_line.setText("0");
                cnt++;
            } else {
                sum += Integer.parseInt(ten_editetext_female_per_line.getText().toString().trim());
            }
            if (average_weight_seed_edittextview.getText().toString().trim().equals("")) {
                cnt++;
            }


            if (cnt == 0) {

                double ssum = sum;
                Log.i("sum", "" + sum);
                double dd = ssum / 10.0;
                Log.i("sum1", "" + dd);
                dd = dd + Integer.parseInt(number_of_expected_edittextview.getText().toString().trim());
                Log.i("sum2", "" + dd);
                dd = dd * Integer.parseInt(total_female_plants_textview.getText().toString().trim());
                Log.i("sum3", "" + dd);
                dd = dd * Double.parseDouble(average_weight_seed_edittextview.getText().toString().trim());
                Log.i("sum4", "" + dd);
                dd = dd / 1000;
                Log.i("sum5", "" + dd);
                double d = dd;

                yield_estimate_kg_edittext.setText("" + (int) Math.round(d));
            } else {
                //   Toast.makeText(context, "Some Fields are Empty. ", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "check proper fields are entered or not.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setFieldCropArea() {
        try {
            //  setArea();
            //  totalTaggedArea = 0.0;
            double issuedArea = Double.parseDouble(issued_seed_area_textview.getText().toString());
            double existingArea = Double.parseDouble(existing_area_ha_edittext.getText().toString());
            double femaleRRSpacingArea = Double.parseDouble(female_spacing_rr.getText().toString());
            double femalePPSpacingArea = Double.parseDouble(female_spacing_pp.getText().toString());
            double maleRRSpacingArea = Double.parseDouble(male_spacing_rr.getText().toString());
            double malePPSpacingArea = Double.parseDouble(male_spacing_pp.getText().toString());
            double femalePlantingRatioArea = Double.parseDouble(female_planting_ratio.getText().toString());
            double malePlantingRatioArea = Double.parseDouble(male_planting_ratio.getText().toString());
            double totalFemale = (((existingArea * 100000000) / (femaleRRSpacingArea * femalePPSpacingArea)) * ((femalePlantingRatioArea / (femalePlantingRatioArea + malePlantingRatioArea))));
            double totalMale = (((existingArea * 100000000) / (maleRRSpacingArea * malePPSpacingArea)) * ((malePlantingRatioArea / (femalePlantingRatioArea + malePlantingRatioArea))));
            total_female_plants_textview.setText("" + new Double(totalFemale).intValue());
            total_male_plants_textview.setText("" + new Double(totalMale).intValue());
        } catch (Exception e) {
            Log.i("Error is FCA", e.getMessage());
        }
    }

    void showLineDialog(String s, int total, int type) {
        try {
            lineDialog = new Dialog(context);
            lineDialog.setContentView(R.layout.line_dialog_entry);

            TextView txt_title, btnclose;
            Button btn_save;
            TableLayout grid;
            allEds = new ArrayList<>();
            txt_title = lineDialog.findViewById(R.id.txt_title);
            btnclose = lineDialog.findViewById(R.id.btnclose);
            btn_save = lineDialog.findViewById(R.id.btn_save);
            grid = lineDialog.findViewById(R.id.grid);
            txt_title.setText("" + s);
            btnclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lineDialog.dismiss();
                }
            });
            TableRow row = new TableRow(context);
            for (int i = 0; i < total; i++) {
                if (i == 0)
                    row = new TableRow(context);
                if (i % 3 == 0)
                    row = new TableRow(context);

                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(120, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f); // Width , height
                EditText editText = new EditText(this);
                editText.setBackgroundResource(R.drawable.line_edittext);
                editText.setPadding(7, 7, 7, 7);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                // editText.setLayoutParams(lparams);
                //editText.setTextSize(10);
                editText.setTextColor(Color.BLACK);
                editText.setGravity(Gravity.CENTER);
                editText.setHint("Line " + (i + 1));
                // editText.setNextFocusForwardId(i+2);
                editText.setId(i + 1);
                allEds.add(editText);
                TextView txt = new TextView(context);
                txt.setText(" ");


                row.addView(editText);
//                row.addView(txt);
                Log.i("Added", "pass" + i);
                if (i % 3 == 0)
                    grid.addView(row);
            }

            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int cnt = 0;
                    int total = 0;
                    for (EditText et : allEds) {
                        Log.i("Test : ", et.getText().toString());
                        if (et.getText().toString().trim().equals("")) {
                            et.setError("Missing");
                            cnt++;
                        } else {
                            try {
                                total += Integer.parseInt(et.getText().toString().trim());

                            } catch (Exception e) {
                                cnt++;
                                et.setError("Invalid");
                            }

                        }

                    }
                    if (cnt == 0) {
                        if (type == 1) {
                            allEds_female = new ArrayList<>(allEds);
                            total_female_plants_textview.setText("" + total);

                        } else {
                            allEds_male = new ArrayList<>(allEds);
                            total_male_plants_textview.setText("" + total);

                        }
                        lineDialog.dismiss();
                    }


                }
            });


            lineDialog.show();

        } catch (Exception e) {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
