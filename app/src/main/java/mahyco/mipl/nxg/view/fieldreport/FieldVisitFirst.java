package mahyco.mipl.nxg.view.fieldreport;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.gridlayout.widget.GridLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
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

import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.FieldLocation;
import mahyco.mipl.nxg.model.FieldMaster;
import mahyco.mipl.nxg.model.FieldMonitoringModels;
import mahyco.mipl.nxg.model.FieldPlantLaneModels;
import mahyco.mipl.nxg.model.FieldVisitLocationModel;
import mahyco.mipl.nxg.model.FieldVisitModel;
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
    List<EditText> allEds;
    int countryId=0;


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
            geotag_location_textview;

    CCFSerachSpinner area_lost_spinner,
            isolation_spinner,
            crop_stage_spinner;

    Button
            tag_area_location,
            buttonfemalelines,
            buttonmalelines,
            save_login;

    String

            str_grower_name_textview,
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
            str_crop_stage_spinner;


    FieldVisitModel fieldVisitModel;
    FieldMonitoringModels fieldMonitoringModels;

    JsonArray jsonArrayFieldVisitModel;
    JsonArray jsonArray_location;
    JsonArray jsonArray_linecount;


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
       countryId=Integer.parseInt(Preferences.get(context, Preferences.COUNTRYCODE));

        tag_area_location = findViewById(R.id.tag_area_location);
        tag_area_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FieldVisitFirst.this, AreaTagingActivity.class);
                startActivity(intent);
            }
        });

        female_date_sowing = findViewById(R.id.female_date_sowing);
        male_date_sowing = findViewById(R.id.male_date_sowing);
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

        //Spinners
        area_lost_spinner = findViewById(R.id.area_lost_spinner);
        isolation_spinner = findViewById(R.id.isolation_spinner);
        crop_stage_spinner = findViewById(R.id.crop_stage_spinner);

        //Buttons
        tag_area_location = findViewById(R.id.tag_area_location);
        buttonfemalelines = findViewById(R.id.buttonfemalelines);
        buttonmalelines = findViewById(R.id.buttonmalelines);
        save_login = findViewById(R.id.save_login);

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
                //mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
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
                //mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                mDatePicker.setTitle("Male sowing date");
                mDatePicker.show();

            }
        });


        try {
            date_of_field_visit_textview.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date().getDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        national_id_photo_front_side_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PickImageDialog.build(new PickSetup())
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
                Toast.makeText(context, "Female " + total_nos_female_lines.getText(), Toast.LENGTH_SHORT).show();

                int total = Integer.parseInt(total_nos_female_lines.getText().toString().trim());
                showLineDialog("Enter No of plants per female line", total, 1);
            }
        });
        buttonmalelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, " Male  " + total_nos_male_lines.getText(), Toast.LENGTH_SHORT).show();
                int total = Integer.parseInt(total_nos_male_lines.getText().toString().trim());
                showLineDialog("Enter No of plants per male line", total, 2);
            }
        });
        save_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Save Records", Toast.LENGTH_SHORT).show();
                submitRecord();
            }
        });

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
            str_staff_name_textview = staff_name_textview.getText().toString().trim();
            str_geotag_location_textview = geotag_location_textview.getText().toString().trim();
            str_area_lost_spinner = area_lost_spinner.getSelectedItem().toString().trim();
            str_isolation_spinner = isolation_spinner.getSelectedItem().toString().trim();
            str_crop_stage_spinner = crop_stage_spinner.getSelectedItem().toString().trim();


            fieldVisitModel.setUserId(1);// 1,
            fieldVisitModel.setCountryId(countryId);// 1,
            fieldVisitModel.setCountryMasterId(90);// 90,
            fieldVisitModel.setMandatoryFieldVisitId(1);// 1,
            fieldVisitModel.setFieldVisitType("Mandatory Field Visit");// Mandatory Field Visit,
            fieldVisitModel.setTotalSeedAreaLost(Double.parseDouble(str_area_loss_or_gain_textview));// 0.02,
            fieldVisitModel.setTaggedAreaInHA(Double.parseDouble(str_total_tagged_area_below_location_textview));// 0.1,
            fieldVisitModel.setExistingAreaInHA(Double.parseDouble(str_existing_area_ha_textview));// 0.1,
            fieldVisitModel.setReasonForTotalLossed(str_reason_for_total_area_loss);// Reason For Total Lossed,
            fieldVisitModel.setFemaleSowingDt(str_female_date_sowing);// 2023-01-15T05;//35;//13.528Z,
            fieldVisitModel.setMaleSowingDt(str_male_date_sowing);// 2023-01-15T05;//35;//13.528Z,
            fieldVisitModel.setIsolationM(str_isolation_spinner);// Yes,
            fieldVisitModel.setIsolationMeter(Integer.parseInt("2"));// 2,
            fieldVisitModel.setCropStage(str_crop_stage_spinner);// For Field Crop,
            fieldVisitModel.setTotalNoOfFemaleLines(Integer.parseInt(str_total_nos_female_lines));// 10,
            fieldVisitModel.setTotalNoOfMaleLines(Integer.parseInt(str_total_nos_male_lines));// 10,
            fieldVisitModel.setFemaleSpacingRRinCM(Integer.parseInt(str_female_spacing_rr));// 2,
            fieldVisitModel.setFemaleSpacingPPinCM(Integer.parseInt(str_female_spacing_pp));// 3,
            fieldVisitModel.setMaleSpacingRRinCM(Integer.parseInt(str_male_spacing_rr));// 2,
            fieldVisitModel.setMaleSpacingPPinCM(Integer.parseInt(str_male_spacing_pp));// 3,
            fieldVisitModel.setPlantingRatioFemale(Integer.parseInt(str_female_planting_ratio));// 5,
            fieldVisitModel.setPlantingRatioMale(Integer.parseInt(str_male_planting_ratio));// 4,
            fieldVisitModel.setCropCategoryType("For Field Crop");// For Field Crop,
            fieldVisitModel.setTotalFemalePlants(Integer.parseInt(str_total_female_plants_textview));// 20,
            fieldVisitModel.setTotalMalePlants(Integer.parseInt(str_total_male_plants_textview));// 20,
            fieldVisitModel.setYieldEstimateInKg(Integer.parseInt(str_yield_estimate_kg_edittext));// 50,
            fieldVisitModel.setObservations(str_recommendations_observations_edittext);// Observations Here,
            fieldVisitModel.setFieldVisitDt(str_date_of_field_visit_textview);// 2023-01-15T05;//35;//13.529Z,
            fieldVisitModel.setLatitude("19.886857");// 19.886857,
            fieldVisitModel.setLongitude("75.3514908");// 75.3514908,
            fieldVisitModel.setCapturePhoto("");// ,
            fieldVisitModel.setCreatedBy("55000066");
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
            for (EditText et : allEds) {
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
            fieldMonitoringModels.setFieldPlantLaneModels(lst_FieldPlantLaneModels);

            JsonObject jsonObject=new JsonParser().parse(new Gson().toJson(fieldMonitoringModels)).getAsJsonObject();
            JsonArray jsonArray=new JsonArray();
            jsonArray.add(jsonObject);
            JsonObject jsonObject1=new JsonObject();
            jsonObject1.add("fieldMonitoringModels",jsonArray);
            new Gson().toJson(jsonObject1);
            Log.i("Data : ",jsonObject1.toString());


        } catch (Exception e) {
            Log.i("Error Save Data : ",e.getMessage());
        }

    }

    void showLineDialog(String s, int total, int type) {
        try {
            lineDialog = new Dialog(context);
            lineDialog.setContentView(R.layout.line_dialog_entry);

            TextView txt_title, btnclose;
            Button btn_save;
            GridLayout grid;
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
            for (int i = 0; i < total; i++) {
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f); // Width , height
                EditText editText = new EditText(this);
                editText.setBackgroundResource(R.drawable.login_screen_edittext);
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
                editText.setLayoutParams(lparams);
                editText.setGravity(Gravity.CENTER);
                editText.setHint("Line " + (i + 1));
                editText.setId(i + 1);
                allEds.add(editText);
                grid.addView(editText);

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
                            total_female_plants_textview.setText("" + total);

                        } else {
                            total_male_plants_textview.setText("" + total);

                        }
                    }
                }
            });


            lineDialog.show();

        } catch (Exception e) {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
