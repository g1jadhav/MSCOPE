package mahyco.mipl.nxg.view.fieldreport;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
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
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.gridlayout.widget.GridLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mahyco.mipl.nxg.BuildConfig;
import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.FieldLocation;
import mahyco.mipl.nxg.model.FieldMaster;
import mahyco.mipl.nxg.model.FieldMonitoringModels;
import mahyco.mipl.nxg.model.FieldPlantLaneModels;
import mahyco.mipl.nxg.model.FieldVisitFruitsCobModel;
import mahyco.mipl.nxg.model.FieldVisitLocationModel;
import mahyco.mipl.nxg.model.FieldVisitModel;
import mahyco.mipl.nxg.model.FieldVisitRoguedPlantModel;
import mahyco.mipl.nxg.model.FirstVisitLocalModel;
import mahyco.mipl.nxg.spinner.CCFSerachSpinner;
import mahyco.mipl.nxg.util.BaseActivity;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.util.SqlightDatabase;
import mahyco.mipl.nxg.view.AreaTagingActivity;

public class FieldVisitFirst extends BaseActivity {
    //  AppCompatEditText female_date_sowing, male_date_sowing, staff_name_textview, date_of_field_visit_textview;
    int mYear, mMonth, mDay;
    Context context;
    AppCompatButton national_id_photo_front_side_btn;
    private File mDocFrontPhotoFile = null;
    String front_path;
    ImageView capture_photo_image_view;
    SqlightDatabase database;
    //   EditText total_nos_female_lines, total_nos_male_lines, total_female_plants_textview, total_male_plants_textview;
    private Dialog lineDialog;
    List<EditText> allEds; // Female
    List<EditText> allEds_male; //male
    List<EditText> allEds_female; //male
    int countryId = 0;
    Double lati = 0.0, longi = 0.0;
    Double totalTaggedArea = 0.0;
    LinearLayout ll_yes, ll_no, ll_femalelinelayout, ll_malelinelayout, female_spacinglayout,image_layout;
     int buttonclick=0;

    EditText
            grower_name_textview,
            issued_seed_area_textview,
            production_code_textview,
            village_textview,
            female_date_sowing,
            male_date_sowing,
            reason_for_total_area_loss,
            total_tagged_area_below_location_textview,
            area_loss_or_gain_textview,
            existing_area_ha_textview,
            total_nos_female_lines,
            total_nos_male_lines,
            female_spacing_rr,
            female_spacing_pp,
            male_spacing_rr,
            male_spacing_pp,
            female_planting_ratio,
            male_planting_ratio,
            total_female_plants_textview,
            total_male_plants_textview,
            yield_estimate_kg_edittext,
            grower_mobile_no_edittext,
            recommendations_observations_edittext,
            date_of_field_visit_textview,
            staff_name_textview,
            geotag_location_textview,
            isolation_meter_textview;

    CCFSerachSpinner area_lost_spinner,
            isolation_spinner,
            crop_stage_spinner;

    Button
            tag_area_location,
            buttonfemalelines,
            buttonmalelines,
            save_login;
    TextView lblmeters;

    String

            str_grower_name_textview = "",
            str_issued_seed_area_textview,
            str_production_code_textview,
            str_village_textview,
            str_female_date_sowing,
            str_male_date_sowing,
            str_reason_for_total_area_loss,
            str_total_tagged_area_below_location_textview,
            str_area_loss_or_gain_textview,
            str_existing_area_ha_textview,
            str_total_nos_female_lines,
            str_total_nos_male_lines,
            str_female_spacing_rr,
            str_female_spacing_pp,
            str_male_spacing_rr,
            str_male_spacing_pp,
            str_female_planting_ratio,
            str_male_planting_ratio,
            str_total_female_plants_textview,
            str_total_male_plants_textview,
            str_yield_estimate_kg_edittext,
            str_grower_mobile_no_edittext,
            str_recommendations_observations_edittext,
            str_date_of_field_visit_textview,
            str_staff_name_textview,
            str_geotag_location_textview,
            str_area_lost_spinner,
            str_isolation_spinner,
            str_crop_stage_spinner, str_isolation_meter_textview;


    FieldVisitModel fieldVisitModel;
    FieldMonitoringModels fieldMonitoringModels;

    JsonArray jsonArrayFieldVisitModel;
    JsonArray jsonArray_location;
    JsonArray jsonArray_linecount;
    int userid;
    int countryCode;
    String staffcode="";
    private FusedLocationProviderClient fusedLocationClient;
    int cropcode = 0, cropcategory = 0;
    String croptype = "";

    @Override
    protected int getLayout() {
        return R.layout.field_visit_first;
    }

    @Override
    protected void init() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Field Visit First ");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = FieldVisitFirst.this;
        database = new SqlightDatabase(context);
        database.trucateTable("tbl_field_location");
        database.trucateTable("tbl_field_master");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        countryId = Integer.parseInt(Preferences.get(context, Preferences.COUNTRYCODE));

        tag_area_location = findViewById(R.id.tag_area_location);
        tag_area_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonclick=1;
                Intent intent = new Intent(FieldVisitFirst.this, AreaTagingActivity.class);
                startActivity(intent);
            }
        });


        date_of_field_visit_textview = findViewById(R.id.date_of_field_visit_textview);
        national_id_photo_front_side_btn = findViewById(R.id.national_id_photo_front_side_btn);
        capture_photo_image_view = findViewById(R.id.capture_photo_image_view);
        total_nos_female_lines = findViewById(R.id.total_nos_female_lines);
        total_nos_male_lines = findViewById(R.id.total_nos_male_lines);
        buttonfemalelines = findViewById(R.id.buttonfemalelines);
        buttonmalelines = findViewById(R.id.buttonmalelines);
        total_female_plants_textview = findViewById(R.id.total_female_plants_textview);
        total_male_plants_textview = findViewById(R.id.total_male_plants_textview);


        grower_name_textview = findViewById(R.id.grower_name_textview);
        issued_seed_area_textview = findViewById(R.id.issued_seed_area_textview);
        production_code_textview = findViewById(R.id.production_code_textview);
        village_textview = findViewById(R.id.village_textview);
        female_date_sowing = findViewById(R.id.female_date_sowing);
        male_date_sowing = findViewById(R.id.male_date_sowing);
        reason_for_total_area_loss = findViewById(R.id.reason_for_total_area_loss);
        total_tagged_area_below_location_textview = findViewById(R.id.total_tagged_area_below_location_textview);
        area_loss_or_gain_textview = findViewById(R.id.area_loss_or_gain_textview);
        existing_area_ha_textview = findViewById(R.id.existing_area_ha_textview);
        total_nos_female_lines = findViewById(R.id.total_nos_female_lines);
        total_nos_male_lines = findViewById(R.id.total_nos_male_lines);
        female_spacing_rr = findViewById(R.id.female_spacing_rr);
        female_spacing_pp = findViewById(R.id.female_spacing_pp);
        male_spacing_rr = findViewById(R.id.male_spacing_rr);
        male_spacing_pp = findViewById(R.id.male_spacing_pp);
        female_planting_ratio = findViewById(R.id.female_planting_ratio);
        male_planting_ratio = findViewById(R.id.male_planting_ratio);
        total_female_plants_textview = findViewById(R.id.total_female_plants_textview);
        total_male_plants_textview = findViewById(R.id.total_male_plants_textview);
        yield_estimate_kg_edittext = findViewById(R.id.yield_estimate_kg_edittext);
        grower_mobile_no_edittext = findViewById(R.id.grower_mobile_no_edittext);
        recommendations_observations_edittext = findViewById(R.id.recommendations_observations_edittext);
        date_of_field_visit_textview = findViewById(R.id.date_of_field_visit_textview);
        staff_name_textview = findViewById(R.id.staff_name_textview);
        geotag_location_textview = findViewById(R.id.geotag_location_textview);
        isolation_meter_textview = findViewById(R.id.isolation_meter_textview);

        //Spinners
        area_lost_spinner = findViewById(R.id.area_lost_spinner);
        isolation_spinner = findViewById(R.id.isolation_spinner);
        crop_stage_spinner = findViewById(R.id.crop_stage_spinner);

        //Buttons
        tag_area_location = findViewById(R.id.tag_area_location);
        buttonfemalelines = findViewById(R.id.buttonfemalelines);
        buttonmalelines = findViewById(R.id.buttonmalelines);
        save_login = findViewById(R.id.save_login);

        lblmeters = findViewById(R.id.lbl_meters);

        //LinearLayout
        ll_yes = findViewById(R.id.ll_yes);
        ll_no = findViewById(R.id.ll_no);
        image_layout = findViewById(R.id.image_layout);

        ll_femalelinelayout = findViewById(R.id.female_no_lines_layout);
        ll_malelinelayout = findViewById(R.id.male_no_lines_layout);
        female_spacinglayout = findViewById(R.id.female_spacinglayout);

        ll_yes.setVisibility(View.GONE);
        staff_name_textview.setText("" + Preferences.get(context, Preferences.USER_NAME));
        staffcode= Preferences.get(context, Preferences.USER_ID);
        grower_name_textview.setText("" + Preferences.get(context, Preferences.SELECTED_GROWERNAME));
        grower_mobile_no_edittext.setText("" + Preferences.get(context, Preferences.SELECTED_GROWERMOBILE));
        userid = Integer.parseInt(Preferences.get(context, Preferences.SELECTED_GROWERID).toString().trim());
        cropcode = Integer.parseInt(Preferences.get(context, Preferences.SELECTEDCROPECODE).toString().trim());
        issued_seed_area_textview.setText("" + String.format("%.2f",Double.parseDouble(Preferences.get(context, Preferences.SELECTED_GROWERAREA).trim())));
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


        fieldVisitModel = new FieldVisitModel();
        fieldMonitoringModels = new FieldMonitoringModels();

        female_date_sowing.setOnClickListener(new View.OnClickListener() {

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
                        female_date_sowing.setText(dd);

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.setTitle("Female sowing date");
                mDatePicker.show();

            }
        });

        male_date_sowing.setOnClickListener(new View.OnClickListener() {

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
                        male_date_sowing.setText(dd);

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.setTitle("Male sowing date");
                mDatePicker.show();

            }
        });


        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = df.format(c);
            date_of_field_visit_textview.setText(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        national_id_photo_front_side_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    buttonclick=2;
                    PickImageDialog.build(new PickSetup().setPickTypes(EPickType.CAMERA))
                            .setOnPickResult(new IPickResult() {
                                @Override
                                public void onPickResult(PickResult r) {
                                    //TODO: do what you have to...
                                    capture_photo_image_view.setVisibility(View.VISIBLE);
                                    capture_photo_image_view.setImageBitmap(r.getBitmap());
                                    // front_path = r.getPath();
                                    try {
                                        mDocFrontPhotoFile = createImageFile("FirstFieldVisit");
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

        buttonfemalelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "Female " + total_nos_female_lines.getText(), Toast.LENGTH_SHORT).show();
                String str=total_nos_female_lines.getText().toString().trim();
                if(str.equals(""))
                {
                    total_nos_female_lines.setError("Please Enter no of female lines.");
                }else {
                    int total = Integer.parseInt(total_nos_female_lines.getText().toString().trim());
                    showLineDialog("Enter No of plants per female line", total, 1);
                }
            }
        });
        buttonmalelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(context, " Male  " + total_nos_male_lines.getText(), Toast.LENGTH_SHORT).show();
                String str=total_nos_male_lines.getText().toString().trim();
                if(str.equals(""))
                {
                    total_nos_male_lines.setError("Please Enter no of male lines.");
                }else {
                    int total = Integer.parseInt(total_nos_male_lines.getText().toString().trim());
                    showLineDialog("Enter No of plants per male line", total, 2);
                }
            }
        });
        save_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "Save Records", Toast.LENGTH_SHORT).show();
                submitRecord();
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

        total_tagged_area_below_location_textview.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (total_tagged_area_below_location_textview.getText().toString().trim().equals("")) {

                        setAreaAfterOnChange(0.0);

                    } else {
                        double d = Double.parseDouble(total_tagged_area_below_location_textview.getText().toString().trim());

                        setAreaAfterOnChange(d);

                    }
                }
            }
        });
        male_planting_ratio.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    if (total_tagged_area_below_location_textview.getText().toString().trim().equals("")) {

                        setAreaAfterOnChange(0.0);
                        setFieldCropArea();

                    } else {

                        double d = Double.parseDouble(total_tagged_area_below_location_textview.getText().toString().trim());
                        setAreaAfterOnChange(d);
                        setFieldCropArea();

                    }
                }
            }
        });

        area_lost_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    ll_no.setVisibility(View.GONE);
                    ll_yes.setVisibility(View.VISIBLE);
                    image_layout.setVisibility(View.GONE);
                } else {
                    ll_no.setVisibility(View.VISIBLE);
                    ll_yes.setVisibility(View.GONE);
                    image_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        isolation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    isolation_meter_textview.setVisibility(View.GONE);
                    lblmeters.setVisibility(View.GONE);
                    str_isolation_meter_textview = "0";
                    isolation_meter_textview.setText("0");
                } else if (position == 1) {
                    isolation_meter_textview.setVisibility(View.VISIBLE);
                    lblmeters.setVisibility(View.VISIBLE);
                    str_isolation_meter_textview = "0";
                    isolation_meter_textview.setText("");
                }else{
                    isolation_meter_textview.setVisibility(View.GONE);
                    lblmeters.setVisibility(View.GONE);
                    str_isolation_meter_textview = "0";
                    isolation_meter_textview.setText("0");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // cropcode=111;
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

            female_spacing_rr.setVisibility(View.GONE);
            female_spacing_pp.setVisibility(View.GONE);
            male_spacing_rr.setVisibility(View.GONE);
            male_spacing_pp.setVisibility(View.GONE);
            female_planting_ratio.setVisibility(View.GONE);
            male_planting_ratio.setVisibility(View.GONE);

            female_spacing_rr.setText("0");
            female_spacing_pp.setText("0");
            male_spacing_rr.setText("0");
            male_spacing_pp.setText("0");
            female_planting_ratio.setText("0");
            male_planting_ratio.setText("0");


            female_spacinglayout.setVisibility(View.GONE);

        } else {
            croptype = "For Field Crop";
            cropcategory = 2; // 2 for Field Crop

            ll_femalelinelayout.setVisibility(View.GONE);
            ll_malelinelayout.setVisibility(View.GONE);
            total_nos_female_lines.setVisibility(View.GONE);
            total_nos_male_lines.setVisibility(View.GONE);
            total_nos_female_lines.setText("0");
            total_nos_male_lines.setText("0");
            buttonfemalelines.setVisibility(View.GONE);
            buttonmalelines.setVisibility(View.GONE);

        }
        Toast.makeText(context, "Selected Crop Type" + croptype, Toast.LENGTH_SHORT).show();
    }


    private void submitRecord() {

        try {
            str_grower_name_textview = grower_name_textview.getText().toString().trim();
            str_issued_seed_area_textview = issued_seed_area_textview.getText().toString().trim();
            str_production_code_textview = production_code_textview.getText().toString().trim();
            str_village_textview = village_textview.getText().toString().trim();
            str_female_date_sowing = female_date_sowing.getText().toString().trim();
            str_male_date_sowing = male_date_sowing.getText().toString().trim();
            str_reason_for_total_area_loss = reason_for_total_area_loss.getText().toString().trim();
            str_total_tagged_area_below_location_textview = total_tagged_area_below_location_textview.getText().toString().trim();
            str_area_loss_or_gain_textview = area_loss_or_gain_textview.getText().toString().trim();
            str_existing_area_ha_textview = existing_area_ha_textview.getText().toString().trim();
            str_total_nos_female_lines = total_nos_female_lines.getText().toString().trim();
            str_total_nos_male_lines = total_nos_male_lines.getText().toString().trim();
            str_female_spacing_rr = female_spacing_rr.getText().toString().trim();
            str_female_spacing_pp = female_spacing_pp.getText().toString().trim();
            str_male_spacing_rr = male_spacing_rr.getText().toString().trim();
            str_male_spacing_pp = male_spacing_pp.getText().toString().trim();
            str_female_planting_ratio = female_planting_ratio.getText().toString().trim();
            str_male_planting_ratio = male_planting_ratio.getText().toString().trim();
            str_total_female_plants_textview = total_female_plants_textview.getText().toString().trim();
            str_total_male_plants_textview = total_male_plants_textview.getText().toString().trim();
            str_yield_estimate_kg_edittext = yield_estimate_kg_edittext.getText().toString().trim();
            str_grower_mobile_no_edittext = grower_mobile_no_edittext.getText().toString().trim();
            str_recommendations_observations_edittext = recommendations_observations_edittext.getText().toString().trim();
            str_date_of_field_visit_textview = date_of_field_visit_textview.getText().toString().trim();
            str_staff_name_textview = staffcode;
            //  str_staff_name_textview = staff_name_textview.getText().toString().trim();
            str_geotag_location_textview = geotag_location_textview.getText().toString().trim();
            str_isolation_meter_textview = isolation_meter_textview.getText().toString().trim();
            str_area_lost_spinner = area_lost_spinner.getSelectedItem().toString().trim();
            str_isolation_spinner = isolation_spinner.getSelectedItem().toString().trim();
            str_crop_stage_spinner = crop_stage_spinner.getSelectedItem().toString().trim();
            boolean b = false;
            if (str_area_lost_spinner.equals("No")) {
                b = validation();
            } else {
                str_reason_for_total_area_loss = reason_for_total_area_loss.getText().toString().trim();
                str_total_tagged_area_below_location_textview = "0";
                str_area_loss_or_gain_textview ="0";
                str_existing_area_ha_textview = "0";
                str_total_nos_female_lines = "0";
                str_total_nos_male_lines ="0";
                str_female_spacing_rr = "0";
                str_female_spacing_pp = "0";
                str_male_spacing_rr = "0";
                str_male_spacing_pp ="0";
                str_female_planting_ratio ="0";
                str_male_planting_ratio = "0";
                str_total_female_plants_textview = "0";
                str_total_male_plants_textview = "0";
                str_yield_estimate_kg_edittext ="0";
                //  str_staff_name_textview = staff_name_textview.getText().toString().trim();
                str_geotag_location_textview = geotag_location_textview.getText().toString().trim();
                str_isolation_meter_textview = "0";
                str_area_lost_spinner = area_lost_spinner.getSelectedItem().toString().trim();
                str_isolation_spinner  ="0";
                str_crop_stage_spinner ="0";
                front_path="NA";

                if(str_reason_for_total_area_loss.trim().equals(""))
                {
                    reason_for_total_area_loss.setError("REQUIRED");
                    b=true;
                }else if(str_geotag_location_textview.trim().equals(""))
                {geotag_location_textview.setError("REQUIRED");
                    b=true;
                }else if(str_grower_name_textview.trim().equals(""))
                {  b=true;
                    grower_name_textview.setError("REQUIRED");
                }else if(str_issued_seed_area_textview.trim().equals(""))
                {issued_seed_area_textview.setError("REQUIRED");
                    b=true;
                }else if(str_production_code_textview.trim().equals(""))
                {production_code_textview.setError("REQUIRED");
                    b=true;
                }else if(str_village_textview.trim().equals(""))
                {  b=true;
                    village_textview.setError("REQUIRED");
                }else if(str_female_date_sowing.trim().equals(""))
                {  b=true;
                    female_date_sowing.setError("REQUIRED");
                }else if(str_male_date_sowing.trim().equals(""))
                {male_date_sowing.setError("REQUIRED");
                    b=true;
                }else if(str_staff_name_textview.trim().equals(""))
                {  b=true;
                    staff_name_textview.setError("REQUIRED");
                }else
                {
                    b=false;
                }

            }

            if (b) {
                Toast.makeText(context, "Validation Error", Toast.LENGTH_SHORT).show();
                showNoInternetDialog(context, "Some fields are empty.Please check. ");
            } else {

                fieldVisitModel.setUserId(userid);// 1,
                fieldVisitModel.setCountryId(countryId);// 1,
                fieldVisitModel.setCountryMasterId(countryCode);// 90,
                fieldVisitModel.setMandatoryFieldVisitId(1);// 1,
                fieldVisitModel.setFieldVisitType("Mandatory Field Visit");// Mandatory Field Visit,
                fieldVisitModel.setTotalSeedAreaLost(Double.parseDouble(str_area_loss_or_gain_textview));// 0.02,
                fieldVisitModel.setTaggedAreaInHA(Double.parseDouble(str_total_tagged_area_below_location_textview));// 0.1,
                fieldVisitModel.setExistingAreaInHA(Double.parseDouble(str_existing_area_ha_textview));// 0.1,
                fieldVisitModel.setReasonForTotalLossed(str_reason_for_total_area_loss);// Reason For Total Lossed,
                fieldVisitModel.setFemaleSowingDt(str_female_date_sowing);// 2023-01-15T05;//35;//13.528Z,
                fieldVisitModel.setMaleSowingDt(str_male_date_sowing);// 2023-01-15T05;//35;//13.528Z,
                fieldVisitModel.setIsolationM(str_isolation_spinner);// Yes,
                fieldVisitModel.setIsolationMeter(Integer.parseInt(str_isolation_meter_textview));// 2,
                fieldVisitModel.setCropStage(str_crop_stage_spinner);// For Field Crop,
                fieldVisitModel.setTotalNoOfFemaleLines(Integer.parseInt(str_total_nos_female_lines));// 10,
                fieldVisitModel.setTotalNoOfMaleLines(Integer.parseInt(str_total_nos_male_lines));// 10,
                fieldVisitModel.setFemaleSpacingRRinCM(Integer.parseInt(str_female_spacing_rr));// 2,
                fieldVisitModel.setFemaleSpacingPPinCM(Integer.parseInt(str_female_spacing_pp));// 3,
                fieldVisitModel.setMaleSpacingRRinCM(Integer.parseInt(str_male_spacing_rr));// 2,
                fieldVisitModel.setMaleSpacingPPinCM(Integer.parseInt(str_male_spacing_pp));// 3,
                fieldVisitModel.setPlantingRatioFemale(Integer.parseInt(str_female_planting_ratio));// 5,
                fieldVisitModel.setPlantingRatioMale(Integer.parseInt(str_male_planting_ratio));// 4,
                fieldVisitModel.setCropCategoryType("" + croptype);// For Field Crop,
                fieldVisitModel.setTotalFemalePlants(Integer.parseInt(str_total_female_plants_textview));// 20,
                fieldVisitModel.setTotalMalePlants(Integer.parseInt(str_total_male_plants_textview));// 20,
                fieldVisitModel.setYieldEstimateInKg(Integer.parseInt(str_yield_estimate_kg_edittext));// 50,
                fieldVisitModel.setObservations(str_recommendations_observations_edittext+" v-"+ BuildConfig.VERSION_NAME);// Observations Here,
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

                fieldMonitoringModels.setFieldVisitModel(fieldVisitModel);
                ArrayList<FieldVisitLocationModel> lst_location = new ArrayList<>();
                try {
                    for (FieldLocation f : database.getAllFieldDetails(-1)) {
                        FieldVisitLocationModel fieldVisitLocationModel = new FieldVisitLocationModel();
                        fieldVisitLocationModel.setFieldNo(f.getFieldId());
                        fieldVisitLocationModel.setLatitude(f.getLatitude());
                        fieldVisitLocationModel.setLongitude(f.getLongitude());
                        lst_location.add(fieldVisitLocationModel);
                    }
                } catch (Exception e) {
                    Log.i("Error", "Error" + e.getMessage());
                }

                fieldMonitoringModels.setFieldVisitLocationModels(lst_location);

                ArrayList<FieldPlantLaneModels> lst_FieldPlantLaneModels = new ArrayList<>();

                int cnt = 0;
                int total = 0;
                if(allEds_female!=null)
                    for (EditText et : allEds_female) {
                        FieldPlantLaneModels fieldPlantLaneModels = new FieldPlantLaneModels();
                        try {
                            fieldPlantLaneModels.setLaneNo(cnt + 1);
                            fieldPlantLaneModels.setNoOfPlants(Integer.parseInt(et.getText().toString().trim()));
                            fieldPlantLaneModels.setPlantType("Female");
                            cnt++;
                            lst_FieldPlantLaneModels.add(fieldPlantLaneModels);
                        } catch (Exception e) {

                        }

                    }
                cnt = 0;
                if(allEds_male!=null)
                    for (EditText et : allEds_male) {
                        FieldPlantLaneModels fieldPlantLaneModels = new FieldPlantLaneModels();
                        try {
                            fieldPlantLaneModels.setLaneNo(cnt + 1);
                            fieldPlantLaneModels.setNoOfPlants(Integer.parseInt(et.getText().toString().trim()));
                            fieldPlantLaneModels.setPlantType("Male");
                            cnt++;
                            lst_FieldPlantLaneModels.add(fieldPlantLaneModels);
                        } catch (Exception e) {

                        }

                    }
                fieldMonitoringModels.setFieldPlantLaneModels(lst_FieldPlantLaneModels);

                JsonArray jsonObjectLocation = new JsonParser().parse(new Gson().toJson(lst_location)).getAsJsonArray();
                JsonArray jsonObjectLine = new JsonParser().parse(new Gson().toJson(lst_FieldPlantLaneModels)).getAsJsonArray();

                Log.i("Location JSON", "" + jsonObjectLocation.toString());
                Log.i("Line JSON", "" + jsonObjectLine.toString());
                fieldVisitModel.setLineData(jsonObjectLine.toString());
                fieldVisitModel.setLocationData(jsonObjectLocation.toString());


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

                JsonArray jsonObjectroguredPlants = new JsonParser().parse(new Gson().toJson(lst_roguredPlants)).getAsJsonArray();
                JsonArray jsonFruitCobds = new JsonParser().parse(new Gson().toJson(lst_fruitCob)).getAsJsonArray();

                fieldVisitModel.setFieldVisitRoguedPlantModels(jsonObjectroguredPlants.toString().trim());
                fieldVisitModel.setFieldVisitFruitsCobModelsText(jsonFruitCobds.toString().trim());




                JsonObject jsonObject = new JsonParser().parse(new Gson().toJson(fieldMonitoringModels)).getAsJsonObject();
                JsonArray jsonArray = new JsonArray();
                jsonArray.add(jsonObject);
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.add("fieldMonitoringModels", jsonArray);
                new Gson().toJson(jsonObject1);
                Log.i("Data : ", jsonObject1.toString());


                FirstVisitLocalModel firstVisitLocalModel = new FirstVisitLocalModel();
                firstVisitLocalModel.setUserid(userid);
                firstVisitLocalModel.setPath(front_path);
                firstVisitLocalModel.setData(jsonObject.toString());
                if (database.addFirstVisit1(fieldVisitModel)) {
                    Toast.makeText(context, "Local Data Saved.", Toast.LENGTH_SHORT).show();
                    showDialogwithFinish(context,"Data Saved Successfully.");
                    // finish();
               /*     Intent i = new Intent(context, FiledMonitoringReportEntry.class);
// set the new task and clear flags
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);*/
                } else {
                    Toast.makeText(context, "Local Data Not Saved.", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception e) {
            Log.i("Error Save Data : ", e.getMessage());
        }

    }

    boolean validation() {
        int cnt = 0;
        try {

            if (str_grower_name_textview == null || str_grower_name_textview.trim().equals("")) {
                cnt++;
                grower_name_textview.setError("Required");
                Log.i("pass", "1");
            }
            if (str_issued_seed_area_textview == null ||
                    str_issued_seed_area_textview.trim().equals("")) {
                issued_seed_area_textview.setError("Required");
                cnt++;
                Log.i("pass", "2");
            }
            if (str_production_code_textview == null ||
                    str_production_code_textview.trim().equals("")) {
                cnt++;
                production_code_textview.setError("Required");
                Log.i("pass", "3");
            }
            if (str_village_textview == null ||
                    str_village_textview.trim().equals("")) {
                village_textview.setError("Required");
                cnt++;
                Log.i("pass", "4");
            }

            if (str_female_date_sowing == null ||
                    str_female_date_sowing.trim().equals("")) {

                female_date_sowing.setError("Required");
                cnt++;
                Log.i("pass", "5");
            }

            if (str_male_date_sowing == null ||
                    str_male_date_sowing.trim().equals("")) {

                Log.i("pass", "6");
                male_date_sowing.setError("Required");
                cnt++;
            }
   /*         if (str_reason_for_total_area_loss==null ||
                    str_reason_for_total_area_loss.trim().equals("")) {
                cnt++;
                reason_for_total_area_loss.setError("Required");
                Log.i("pass","7");
            }*/
            if (str_total_tagged_area_below_location_textview == null || str_total_tagged_area_below_location_textview.trim().equals("")) {
                cnt++;
                Log.i("pass", "8");
                total_tagged_area_below_location_textview.setError("Required");
            }
            if (str_area_loss_or_gain_textview == null || str_area_loss_or_gain_textview.trim().equals("")) {
                cnt++;
                Log.i("pass", "9");
                area_loss_or_gain_textview.setError("Required");
            }
            if (str_existing_area_ha_textview == null ||
                    str_existing_area_ha_textview.trim().equals("")) {
                cnt++;
                Log.i("pass", "10");
                existing_area_ha_textview.setError("Required");
            }
            if (str_total_nos_female_lines == null ||
                    str_total_nos_female_lines.trim().equals("")) {
                cnt++;
                Log.i("pass", "11");
                total_nos_female_lines.setError("Required");
            }
            if (str_total_nos_male_lines == null ||
                    str_total_nos_male_lines.trim().equals("")) {
                cnt++;
                Log.i("pass", "12");
                total_nos_male_lines.setError("Required");
            }
            if (str_female_spacing_rr == null ||
                    str_female_spacing_rr.trim().equals("")) {
                cnt++;
                Log.i("pass", "13");
                female_spacing_rr.setError("Required");
            }
            if (str_female_spacing_pp == null ||
                    str_female_spacing_pp.trim().equals("")) {
                cnt++;
                Log.i("pass", "14");
                female_spacing_pp.setError("Required");
            }
            if (str_male_spacing_rr == null ||
                    str_male_spacing_rr.trim().equals("")) {
                cnt++;
                male_spacing_rr.setError("Required");
            }
            if (str_male_spacing_pp == null ||
                    str_male_spacing_pp.trim().equals("")) {
                cnt++;
                Log.i("pass", "15");
                male_spacing_pp.setError("Required");
            }
            if (str_female_planting_ratio == null ||
                    str_female_planting_ratio.trim().equals("")) {
                cnt++;
                Log.i("pass", "16");
                female_planting_ratio.setError("Required");
            }
            if (str_male_planting_ratio == null ||
                    str_male_planting_ratio.trim().equals("")) {
                cnt++;
                Log.i("pass", "17");
                male_planting_ratio.setError("Required");
            }
            if (str_total_female_plants_textview == null ||
                    str_total_female_plants_textview.trim().equals("")) {
                cnt++;
                Log.i("pass", "18");
                total_female_plants_textview.setError("Required");
            }
            if (str_total_male_plants_textview == null ||
                    str_total_male_plants_textview.trim().equals("")) {
                cnt++;
                Log.i("pass", "19");
                total_male_plants_textview.setError("Required");
            }
            if (str_yield_estimate_kg_edittext == null &&
                    str_yield_estimate_kg_edittext.trim().equals("")) {
                cnt++;
                Log.i("pass", "20");
                yield_estimate_kg_edittext.setError("Required");
            }
            if (str_grower_mobile_no_edittext == null &&
                    str_grower_mobile_no_edittext.trim().equals("")) {
                cnt++;
                Log.i("pass", "21");
                grower_mobile_no_edittext.setError("Required");
            }
            if (str_recommendations_observations_edittext == null &&
                    str_recommendations_observations_edittext.trim().equals("")) {
                cnt++;
                Log.i("pass", "22");
                recommendations_observations_edittext.setError("Required");
            }
            if (str_date_of_field_visit_textview == null &&
                    str_date_of_field_visit_textview.trim().equals("")) {
                cnt++;
                date_of_field_visit_textview.setError("Required");
            }
            if (str_staff_name_textview == null &&
                    str_staff_name_textview.trim().equals("")) {
                cnt++;
                staff_name_textview.setError("Required");
            }
            if (str_geotag_location_textview == null &&
                    str_geotag_location_textview.trim().equals("")) {
                cnt++;
                geotag_location_textview.setError("Required");
            }
            if (str_area_lost_spinner == null &&
                    str_area_lost_spinner.trim().equals("")) {
                cnt++; //area_lost_spinner.setError("Required");
                Toast.makeText(context, "Choose Area Lost Dropdown.", Toast.LENGTH_SHORT).show();
            }
            if (str_isolation_spinner == null &&
                    str_isolation_spinner.trim().equals("")) {
                cnt++;// isolation_spinner.setError("Required");
                Toast.makeText(context, "Choose Isolation Dropdown.", Toast.LENGTH_SHORT).show();
            }
            if (str_crop_stage_spinner == null &&
                    str_crop_stage_spinner.trim().equals("")) {
                cnt++;
                //crop_stage_spinner.setError("Required");
                Toast.makeText(context, "Choose Crop Stage Dropdown.", Toast.LENGTH_SHORT).show();

            }
            if (str_isolation_meter_textview == null && str_isolation_meter_textview.trim().equals("")) {
                cnt++;
                isolation_meter_textview.setError("Required");
            }
            if (str_geotag_location_textview == null || str_geotag_location_textview.trim().equals("")) {
                cnt++;
                geotag_location_textview.setError("Required");
            }
            if (front_path == null || front_path.trim().equals("")) {
                cnt++;
                Toast.makeText(context, "Photo Missing.Please Take Photo.", Toast.LENGTH_SHORT).show();

            }
            if (str_crop_stage_spinner.contains("Select")) {
                cnt++;
                Toast.makeText(context, "Please Select Crop Stage.", Toast.LENGTH_SHORT).show();

            }

            if(database.getAllFieldDetails(-1).size()==0)
            {
                cnt++;
                Toast.makeText(context, "Please tagged the location.", Toast.LENGTH_SHORT).show();

            }
            if(cropcategory==1)
            {
                if(allEds_male==null||allEds_male.size()==0)
                {
                    cnt++;
                    Toast.makeText(context, "Missing male line entry", Toast.LENGTH_SHORT).show();

                }
                if(allEds_female==null||allEds_female.size()==0)
                {
                    cnt++;
                    Toast.makeText(context, "Missing female line entry", Toast.LENGTH_SHORT).show();

                }else
                {

                }

            }


            //   Toast.makeText(this, "Count :"+cnt, Toast.LENGTH_SHORT).show();
            if (cnt == 0)
                return false;
            else
                return true;
        } catch (Exception e) {
            Toast.makeText(context, "Error is " + e.getMessage() + " " + cnt, Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        // if (cropcategory == 1)
        if(buttonclick==1)
        setArea();
//        else if (cropcategory == 2)
//            setFieldCropArea();


    }

    private void setArea() {
        try {
            totalTaggedArea = 0.0;
            for (FieldMaster f : database.getAllFieldMaster()) {
                totalTaggedArea += Double.parseDouble(f.getTotalArea());
            }

            total_tagged_area_below_location_textview.setText("" +String.format("%.2f",totalTaggedArea));
            double totalArea = Double.parseDouble(issued_seed_area_textview.getText().toString());
            double loss = totalTaggedArea - totalArea;
            area_loss_or_gain_textview.setText("" +String.format("%.2f",loss) );
            double existingarea = totalArea + loss;
            existing_area_ha_textview.setText("" + String.format("%.2f",existingarea));
            Toast.makeText(context, "Existing Area is "+existingarea, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Error to calculate Tagged Area : "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setFieldCropArea() {
        try {
            //  setArea();
            totalTaggedArea = 0.0;
            double issuedArea = Double.parseDouble(issued_seed_area_textview.getText().toString());
            double existingArea = Double.parseDouble(existing_area_ha_textview.getText().toString());
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


    private void setAreaAfterOnChange(Double s) {
        try {
            totalTaggedArea = s;
            total_tagged_area_below_location_textview.setText("" + totalTaggedArea);
            double totalArea = Double.parseDouble(issued_seed_area_textview.getText().toString());
            double loss = totalTaggedArea - totalArea;
            area_loss_or_gain_textview.setText("" + loss);
            double existingarea = totalArea + loss;
            existing_area_ha_textview.setText("" + existingarea);
        } catch (Exception e) {

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
            TableRow row=new TableRow(context);
            for (int i = 0; i < total; i++) {
                if(i==0)
                    row=new TableRow(context);
                if(i%3==0)
                    row=new TableRow(context);

                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(120, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f); // Width , height
                EditText editText = new EditText(this);
                editText.setBackgroundResource(R.drawable.line_edittext);
                editText.setPadding(7,7,7,7);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                // editText.setLayoutParams(lparams);
                //editText.setTextSize(10);
                editText.setTextColor(Color.BLACK);
                editText.setGravity(Gravity.CENTER);
                editText.setHint("Line " + (i + 1));
                // editText.setNextFocusForwardId(i+2);
                editText.setId(i + 1);
                allEds.add(editText);
                TextView txt=new TextView(context);
                txt.setText(" ");


                row.addView(editText);
//                row.addView(txt);
                Log.i("Added","pass"+i);
                if(i%3==0)
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
