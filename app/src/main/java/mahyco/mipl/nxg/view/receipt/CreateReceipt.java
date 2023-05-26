package mahyco.mipl.nxg.view.receipt;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import mahyco.mipl.nxg.model.ReceiptModel;
import mahyco.mipl.nxg.spinner.CCFSerachSpinner;
import mahyco.mipl.nxg.util.BaseActivity;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.util.SqlightDatabase;

public class CreateReceipt extends BaseActivity {
    EditText
            grower_name_textview,
            issued_seed_area_textview,
            production_code_textview,
            village_textview,
            existing_area_ha_edittext,
            reason_for_area_loss_spinner,
            et_yeildinkg,
            et_batchno,
            et_batchnoreceipt,

    et_noofbags,
            et_weightinkg,

    grower_mobile_no_edittext,

    date_of_field_visit_textview,
            staff_name_textview;

    CCFSerachSpinner
            field_ratings_for_spinner, area_lost_spinner, sp_serviceprovider;

    Button btn_save;

    String str_grower_name_textview,
            str_issued_seed_area_textview,
            str_production_code_textview,
            str_village_textview,
            str_existing_area_ha_edittext,
            str_area_loss_ha_textview,
            str_reason_for_area_loss_spinner,
            str_et_yeildinkg,
            str_et_batchno,
            str_et_batchnoreceipt,
            str_area_lost_spinner,

    str_et_noofbags,
            str_et_weightinkg,
            str_sp_serviceprovider,

    str_grower_mobile_no_edittext,
            str_date_of_field_visit_textview,
            str_staff_name_textview;

    Context context;

    int cropcode = 0, cropcategory = 0;
    String croptype = "";
    ReceiptModel receiptModel;

    FieldVisitModel fieldVisitModel;
    FieldMonitoringModels fieldMonitoringModels;
    int mYear, mMonth, mDay;
    int userid;
    int countryId = 0;
    int countryCode;
    double prevExistingArea = 0.0;

    String staffcode = "";
    SqlightDatabase database;

    int currentStage = 0;
    int totalFemalePlants = 0;
    int totalMalePlants = 0;
    LinearLayout layout_losslayout, layout_existarea;
    int lossStatus = 0;
    String yeidinkg, BatchNo;
    int receiptcount = 0;

    @Override
    protected int getLayout() {
        return R.layout.field_receipt_create;
    }

    @Override
    protected void init() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Create Seed Receipt");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = CreateReceipt.this;
        database = new SqlightDatabase(context);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        grower_name_textview = findViewById(R.id.grower_name_textview);
        issued_seed_area_textview = findViewById(R.id.issued_seed_area_textview);
        production_code_textview = findViewById(R.id.production_code_textview);
        village_textview = findViewById(R.id.village_textview);
        existing_area_ha_edittext = findViewById(R.id.existing_area_ha_edittext);
        reason_for_area_loss_spinner = findViewById(R.id.reason_for_area_loss_spinner);
        grower_mobile_no_edittext = findViewById(R.id.grower_mobile_no_edittext);
        date_of_field_visit_textview = findViewById(R.id.date_of_field_visit_textview);
        staff_name_textview = findViewById(R.id.staff_name_textview);
        field_ratings_for_spinner = findViewById(R.id.field_ratings_for_spinner);
        et_yeildinkg = findViewById(R.id.et_yeildinkg);
        et_batchno = findViewById(R.id.et_batchno);
        et_batchnoreceipt = findViewById(R.id.et_batchnoreceipt);

        et_noofbags = findViewById(R.id.et_noofbags);
        et_weightinkg = findViewById(R.id.et_weightinkg);

        field_ratings_for_spinner = findViewById(R.id.field_ratings_for_spinner);
        area_lost_spinner = findViewById(R.id.area_lost_spinner);
        sp_serviceprovider = findViewById(R.id.sp_serviceprovider);


        btn_save = findViewById(R.id.save_login);
        layout_losslayout = findViewById(R.id.losslayout);
        layout_existarea = findViewById(R.id.existlayout);
        layout_losslayout.setVisibility(View.GONE);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit1();
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

        prevExistingArea = Double.parseDouble(Preferences.get(context, Preferences.SELECTEVISITEXISITINGAREA));


        if (Preferences.get(context, Preferences.SELECTEDBATCHID) != null) {
            BatchNo = Preferences.get(context, Preferences.SELECTEDBATCHID);

            et_batchno.setText(BatchNo);
        }
        receiptcount = 0;
        if (Preferences.get(context, Preferences.TOTALRECEIPTCOUNT) != null) {
            receiptcount = Integer.parseInt(Preferences.get(context, Preferences.TOTALRECEIPTCOUNT).toString().trim());


        }
        switch (receiptcount + 1) {
            case 1:
                et_batchnoreceipt.setText(BatchNo + "A");
                break;

            case 2:
                et_batchnoreceipt.setText(BatchNo + "B");
                break;

            case 3:
                et_batchnoreceipt.setText(BatchNo + "C");
                break;

            case 4:
                et_batchnoreceipt.setText(BatchNo + "D");
                break;

            case 5:
                et_batchnoreceipt.setText(BatchNo + "E");
                break;

            case 6:
                et_batchnoreceipt.setText(BatchNo + "F");
                break;

            case 7:
                et_batchnoreceipt.setText(BatchNo + "G");
                break;

            case 8:
                et_batchnoreceipt.setText(BatchNo + "H");
                break;

            case 9:
                et_batchnoreceipt.setText(BatchNo + "I");
                break;

            case 10:
                et_batchnoreceipt.setText(BatchNo + "J");
                break;

            case 11:
                et_batchnoreceipt.setText(BatchNo + "K");
                break;

            case 12:
                et_batchnoreceipt.setText(BatchNo + "L");
                break;

            case 13:
                et_batchnoreceipt.setText(BatchNo + "M");
                break;

            case 14:
                et_batchnoreceipt.setText(BatchNo + "N");
                break;

            case 15:
                et_batchnoreceipt.setText(BatchNo + "O");
                break;

            case 16:
                et_batchnoreceipt.setText(BatchNo + "P");
                break;

            case 17:
                et_batchnoreceipt.setText(BatchNo + "Q");
                break;

            case 18:
                et_batchnoreceipt.setText(BatchNo + "R");
                break;

            case 19:
                et_batchnoreceipt.setText(BatchNo + "S");
                break;

            case 20:
                et_batchnoreceipt.setText(BatchNo + "T");
                break;

            case 21:
                et_batchnoreceipt.setText(BatchNo + "U");
                break;

            case 22:
                et_batchnoreceipt.setText(BatchNo + "V");
                break;
            case 23:
                et_batchnoreceipt.setText(BatchNo + "W");
                break;
            case 24:
                et_batchnoreceipt.setText(BatchNo + "X");
                break;
            case 25:
                et_batchnoreceipt.setText(BatchNo + "Y");
                break;

            case 26:
                et_batchnoreceipt.setText(BatchNo + "Z");
                break;

        }


        if (Preferences.get(context, Preferences.YEILDKG) != null) {
            yeidinkg = Preferences.get(context, Preferences.YEILDKG);
            et_yeildinkg.setText(yeidinkg);
        }


        existing_area_ha_edittext.setText("" + prevExistingArea);

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


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit1();
            }
        });


        area_lost_spinner = findViewById(R.id.area_lost_spinner);
        area_lost_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {

                    showOther();
                    lossStatus = 1;

                } else {
                    lossStatus = 2;
                    hideOther();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void hideOther() {
        try {
            layout_losslayout.setVisibility(View.GONE);
            layout_existarea.setVisibility(View.VISIBLE);
            //  recommendations_observations_edittext.setVisibility(View.VISIBLE);
        } catch (Exception e) {

        }
    }

    public void showOther() {
        try {
            layout_losslayout.setVisibility(View.VISIBLE);
            layout_existarea.setVisibility(View.GONE);
            //   recommendations_observations_edittext.setVisibility(View.GONE);
        } catch (Exception e) {

        }
    }


    public void submit1() {

        try {


            str_grower_name_textview = grower_name_textview.getText().toString().trim();
            str_issued_seed_area_textview = issued_seed_area_textview.getText().toString().trim();
            str_production_code_textview = production_code_textview.getText().toString().trim();
            str_village_textview = village_textview.getText().toString().trim();
            str_existing_area_ha_edittext = existing_area_ha_edittext.getText().toString().trim();
            str_reason_for_area_loss_spinner = reason_for_area_loss_spinner.getText().toString().trim();
            str_et_yeildinkg = et_yeildinkg.getText().toString().trim();
            str_et_batchno = et_batchno.getText().toString().trim();
            str_et_batchnoreceipt = et_batchnoreceipt.getText().toString().trim();
            str_area_lost_spinner = area_lost_spinner.getSelectedItem().toString().trim();

            str_et_noofbags = et_noofbags.getText().toString().trim();
            str_et_weightinkg = et_weightinkg.getText().toString().trim();
            str_sp_serviceprovider = sp_serviceprovider.getSelectedItem().toString().trim();

            str_grower_mobile_no_edittext = grower_mobile_no_edittext.getText().toString().trim();
            str_date_of_field_visit_textview = date_of_field_visit_textview.getText().toString().trim();
            str_staff_name_textview = staff_name_textview.getText().toString().trim();

            if (validation()) {

                receiptModel = new ReceiptModel();

                receiptModel.setGrowerId("" + userid);
                receiptModel.setGrowerName(str_grower_name_textview);
                receiptModel.setIssued_seed_area(str_issued_seed_area_textview);
                receiptModel.setProduction_code(str_production_code_textview);
                receiptModel.setVillage(str_village_textview);
                receiptModel.setExisting_area(str_existing_area_ha_edittext);
                receiptModel.setArea_loss(str_area_lost_spinner);
                receiptModel.setReason_for_area_loss(str_reason_for_area_loss_spinner);
                receiptModel.setYeildinkg(str_et_yeildinkg);
                receiptModel.setBatchno(str_et_batchno);
                receiptModel.setNoofbags(str_et_noofbags);
                receiptModel.setWeightinkg(str_et_weightinkg);
                receiptModel.setServiceprovider(str_sp_serviceprovider);
                receiptModel.setGrower_mobile_no_edittext(str_grower_mobile_no_edittext);
                receiptModel.setDate_of_field_visit_textview(str_date_of_field_visit_textview);
                receiptModel.setStaff_name_textview(str_staff_name_textview);
                receiptModel.setStaffID(staffcode);
                receiptModel.setReceiptBatchno(str_et_batchnoreceipt);

                if (database.addReceiptDetails(receiptModel)) {
                    // Toast.makeText(context, "Data Saved Successfully..", Toast.LENGTH_SHORT).show();

                    new AlertDialog.Builder(context)
                            .setMessage("Data Saved Successfully")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();

                                }
                            })
                            .setTitle("Receipt details.")
                            .show();
                } else {
                    // Toast.makeText(context, "Data Not Saved", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(context)
                            .setMessage("Something went wrong.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    //finish();

                                }
                            })
                            .setTitle("Receipt details.")
                            .show();
                }

                Log.i("Values", str_grower_name_textview);
                Log.i("Values", str_issued_seed_area_textview);
                Log.i("Values", str_production_code_textview);
                Log.i("Values", str_village_textview);
                Log.i("Values", str_existing_area_ha_edittext);
                Log.i("Values", str_reason_for_area_loss_spinner);
                Log.i("Values", str_et_yeildinkg);
                Log.i("Values", str_et_batchno);
                Log.i("Values", str_area_lost_spinner);
                Log.i("Values", str_et_noofbags);
                Log.i("Values", str_et_weightinkg);
                Log.i("Values", str_sp_serviceprovider);
                Log.i("Values", str_grower_mobile_no_edittext);
                Log.i("Values", str_date_of_field_visit_textview);
                Log.i("Values", str_staff_name_textview);

            } else {
                Toast.makeText(context, "Please Check Validations. Some Fields are missing. Please check.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.i("Error is ", e.getMessage());
        }

    }

    private boolean validation() {
        try {


            int cnt = 0;

            if (str_area_lost_spinner.toLowerCase().contains("no")) {
                str_et_noofbags = "0";
                str_et_weightinkg = "0";
                str_sp_serviceprovider = "NA";
            }


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


            if (str_grower_mobile_no_edittext.trim().equals("")) {
                grower_mobile_no_edittext.setError("Required");
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

            if (str_area_lost_spinner.contains("No"))
                if (str_reason_for_area_loss_spinner.trim().equals("")) {
                    reason_for_area_loss_spinner.setError("Required");
                    cnt++;
                }

            if (str_et_yeildinkg.trim().equals("")) {
                et_yeildinkg.setError("Required");
                cnt++;
            }
            if (str_et_batchno.trim().equals("")) {
                et_batchno.setError("Required");
                cnt++;
            }
            if (str_et_batchnoreceipt.trim().equals("")) {
                et_batchnoreceipt.setError("Required");
                cnt++;
            }


            if (str_et_noofbags.trim().equals("")) {
                et_noofbags.setError("Required");
                cnt++;
            }
            if (str_et_weightinkg.trim().equals("")) {
                et_weightinkg.setError("Required");
                cnt++;
            }
            if (str_sp_serviceprovider.trim().equals("") || str_sp_serviceprovider.trim().toLowerCase().contains("select")) {
                Toast.makeText(context, "Please select service ptrovider.", Toast.LENGTH_SHORT).show();
                cnt++;
            }


            //Toast.makeText(context, "Total Validation " + cnt, Toast.LENGTH_SHORT).show();
            if (cnt == 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

}
