package mahyco.mipl.nxg.view.fieldreport;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mahyco.mipl.nxg.BuildConfig;
import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.adapter.CategoryLoadingAdapter;
import mahyco.mipl.nxg.adapter.GrowerAdapter;
import mahyco.mipl.nxg.adapter.VisitGrowerAdapter;
import mahyco.mipl.nxg.model.CategoryChildModel;
import mahyco.mipl.nxg.model.CategoryModel;
import mahyco.mipl.nxg.model.DownloadGrowerModel;
import mahyco.mipl.nxg.model.GetAllSeedDistributionModel;
import mahyco.mipl.nxg.model.GrowerModel;
import mahyco.mipl.nxg.model.VillageModel;
import mahyco.mipl.nxg.spinner.CCFSerachSpinner;
import mahyco.mipl.nxg.spinner.SpinnerGrowerAdapter;
import mahyco.mipl.nxg.util.BaseActivity;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.util.SqlightDatabase;
import mahyco.mipl.nxg.view.growerregistration.GrowerRegistrationAPI;
import mahyco.mipl.nxg.view.growerregistration.NewGrowerRegistration;

public class FiledMonitoringReportEntry extends BaseActivity implements RecyclerViewClickListener,
        View.OnClickListener {

    private Context mContext;
    Dialog dialog_growerlist;
    private CCFSerachSpinner mSearchByIdNameSpinner;

    private ArrayList<GetAllSeedDistributionModel> mGrowerList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private FieldMonitoringReportAdapter mFieldMonitoringReportAdapter;

    private AppCompatButton mNextButton;
    private AppCompatButton mBackButton;

    private LinearLayout mGrowerSearchLayout;
    private LinearLayout mFilterSearchLayout;
    //  private LinearLayout mFieldVisitFirst;
    JsonObject categoryJson;

    LinearLayoutManager mManager;
    RecyclerView rc_list;
    CategoryLoadingAdapter adapter;
    VisitGrowerAdapter visitGrowerAdapter;
    EditText et_landmark, et_fullname, /*et_gender,*/ /*et_dob,*/
            et_mobile, et_uniqcode /*et_regdate,*/
            /*,et_satffname*/;
    EditText et_address_edittext;
    String str_et_landmark, str_et_fullname, str_et_gender, str_et_dob, str_et_mobile, str_et_uniqcode, str_et_regdate, str_et_satffname, str_et_address;
    Button grower_registration_submit_btn, scan_qr_code_btn;
    CircleImageView iv_dp;
    ImageView imageView_front, imageView_back;
    String str_Lable = "";
    TextView txt_name, et_dob, et_regdate, et_satffname;
    TextView txt_registration_country;
    String dp_path, front_path, back_path;
    static int stid = 0;
    GrowerModel growerModel = new GrowerModel();
    String counrtyId = "0", countryName = "";
    String strFilterName = "",strFilterValue="";
    String strFilterName_PC = "",strFilterValue_PC="";
    private CCFSerachSpinner mSpinnerArray[];
    private int[] mSpinnerHeadingTextView;

    private CCFSerachSpinner mSearchableSpinner1;
    private CCFSerachSpinner mSearchableSpinner2;
    private CCFSerachSpinner mSearchableSpinner3;
    private CCFSerachSpinner mSearchableSpinner4;
    private CCFSerachSpinner mSearchableSpinner5;
    private CCFSerachSpinner mSearchableSpinner6;
    private CCFSerachSpinner mSearchableSpinner7;
    private CCFSerachSpinner mSearchableSpinner8;
    private CCFSerachSpinner mSearchableSpinner9;
    private CCFSerachSpinner mSearchableSpinner10;
    private CCFSerachSpinner sp_focusvillage;
    private CCFSerachSpinner sp_productioncode;

    private DatePickerDialog mDatePickerDialog = null;

    private ArrayList<CategoryChildModel> mSpinner1List;
    private ArrayList<VillageModel> mSpinnerFocussVillageList;
    private ArrayList<String> mSpinnerProductionCodeList;
    private ArrayList<CategoryChildModel> mSpinner2List;
    private ArrayList<CategoryChildModel> mSpinner3List;
    private ArrayList<CategoryChildModel> mSpinner4List;
    private ArrayList<CategoryChildModel> mSpinner5List;
    private ArrayList<CategoryChildModel> mSpinner6List;
    private ArrayList<CategoryChildModel> mSpinner7List;
    private ArrayList<CategoryChildModel> mSpinner8List;
    private ArrayList<CategoryChildModel> mSpinner9List;
    private ArrayList<CategoryChildModel> mSpinner10List;
   AppCompatTextView lbl_search_filter_result;
    private CodeScanner mCodeScanner;
    private CodeScannerView mCodeScannerView;
    private ScrollView mScrollView;

    private int mCountryMasterIdAsPerSelection = 0;
    private int mSpinnerPosition = 1;

    private File mDocFrontPhotoFile = null;
    private File mGrowerPhotoFile = null;
    private File mDocBackPhotoFile = null;

    private RadioButton mMaleRadioButton;
    private RadioButton mFemaleRadioButton;

    private androidx.appcompat.widget.Toolbar toolbar;
    String str_address = "";
    int total_active_spinners = 0;
    SqlightDatabase database;
    AppCompatButton btn_search;
    int selectedGrowerId=0;
    LocationManager locationManager ;
    boolean GpsStatus ;
    ProgressDialog progressDialog;

    @Override
    protected int getLayout() {
        return R.layout.filed_monitoring_report_entry;
    }

    @Override
    protected void init() {
        try {
            AppCompatTextView mVersionTextView = findViewById(R.id.registration_version_code);
            mVersionTextView.setText(getString(R.string.version_code, BuildConfig.VERSION_CODE));

            mContext = this;
            database = new SqlightDatabase(mContext);

            Toolbar mToolbar = findViewById(R.id.toolbar);
            mToolbar.setTitle("Field Monitoring Report Entry");

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            lbl_search_filter_result=findViewById(R.id.lbl_search_filter_result);
            mManager = new LinearLayoutManager(mContext);
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("प्रतिक्षा करा..");
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            mSearchByIdNameSpinner = findViewById(R.id.search_grower_by_id_name);

            mSearchByIdNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //   Log.e("temporary", " mSearchByIdNameSpinner.setOnItemSelectedListener");

                    GetAllSeedDistributionModel m = (GetAllSeedDistributionModel) adapterView.getSelectedItem();
                    Log.i("GetAll", m.getGrowerId() + " " + m.getGrowerFullName());
                    if (i != 0) {
                        selectedGrowerId=m.getGrowerId();
                /*        if (database.isFirstFieldVisitDone(m.getGrowerId())) {
                            showNoInternetDialog(mContext, "First visit is done with this grower.");
                        }else if(database.isFirstFieldVisitDoneLocal(m.getGrowerId()))
                        {
                            showNoInternetDialog(mContext, "First visit is done with this grower.");

                        }
                            else {*/
                            Preferences.save(mContext, Preferences.SELECTED_GROWERNAME, m.getGrowerFullName());
                            Preferences.save(mContext, Preferences.SELECTED_GROWERMOBILE, m.getGrowerMobileNo());
                            Preferences.save(mContext, Preferences.SELECTED_GROWERID, "" + m.getGrowerId());
                            Preferences.save(mContext, Preferences.SELECTED_GROWERAREA, "" + m.getSeedProductionArea());
                            Preferences.save(mContext, Preferences.SELECTED_GROWERPRODUCTIONCODE, "" + m.getProductionCode());
                            Preferences.save(mContext, Preferences.SELECTED_GROWERUNIQUECODE, "" + m.getGrowerUniqueCode());
                            Preferences.save(mContext, Preferences.SELECTEDCROPECODE, "" + m.getCropCode());
                       // }
                    } else {
                        Toast.makeText(mContext, "Please choose grower.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            mRecyclerView = findViewById(R.id.grower_list_recyclerview);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
            mRecyclerView.setLayoutManager(linearLayoutManager1);

            mNextButton = findViewById(R.id.next_btn);
            mNextButton.setOnClickListener(this);

            mBackButton = findViewById(R.id.back_btn);
            mBackButton.setOnClickListener(this);

            btn_search = findViewById(R.id.btn_search);
            btn_search.setOnClickListener(this);

            // mFieldVisitFirst = findViewById(R.id.field_visit_second);

            mGrowerSearchLayout = findViewById(R.id.grower_search_layout);
            mFilterSearchLayout = findViewById(R.id.filter_search_layout);

         //   new GetGrowerMasterAsyncTask().execute();
            new GetCategoriesAsyncTask().execute();
            sp_focusvillage = findViewById(R.id.sp_focusvillage);
            sp_productioncode = findViewById(R.id.sp_productioncode);
            mSearchableSpinner1 = findViewById(R.id.sp1);
            mSearchableSpinner2 = findViewById(R.id.sp2);
            mSearchableSpinner3 = findViewById(R.id.sp3);
            mSearchableSpinner4 = findViewById(R.id.sp4);
            mSearchableSpinner5 = findViewById(R.id.sp5);
            mSearchableSpinner6 = findViewById(R.id.sp6);
            mSearchableSpinner7 = findViewById(R.id.sp7);
            mSearchableSpinner8 = findViewById(R.id.sp8);
            mSearchableSpinner9 = findViewById(R.id.sp9);
            mSearchableSpinner10 = findViewById(R.id.sp10);

            mSpinnerArray = new CCFSerachSpinner[]{mSearchableSpinner1, mSearchableSpinner2, mSearchableSpinner3, mSearchableSpinner4, mSearchableSpinner5, mSearchableSpinner6, mSearchableSpinner7, mSearchableSpinner8, mSearchableSpinner9, mSearchableSpinner10};
            mSpinnerHeadingTextView = new int[]{R.id.textview1, R.id.textview2, R.id.textview3, R.id.textview4, R.id.textview5, R.id.textview6, R.id.textview7, R.id.textview8, R.id.textview9, R.id.textview10};

            mSearchableSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    strFilterName="";
                    strFilterValue="";
                    if (mSpinner1List != null && mSpinner1List.size() > 0) {
                        mSpinnerPosition = 2;
                        mCountryMasterIdAsPerSelection = mSpinner1List.get(i).getCountryMasterId();
                        CategoryChildModel d = (CategoryChildModel) adapterView.getSelectedItem();
                        if (d.getCategoryName() != null &&  !d.getCategoryName().contains("Select")&& !d.getKeyValue().contains("Select")) {
                              strFilterName = "" + d.getCategoryName();
                            strFilterValue=""+d.getKeyValue();

                        }
                            new GetLocationMasterAsyncTask().execute();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            /*mGrowerName = findViewById(R.id.grower_name_textview);
            mIssuedSeedArea = findViewById(R.id.issued_seed_area_textview);
            mProductionCode = findViewById(R.id.production_code_textview);*/

//            new GetGrowerMasterAsyncTask().execute();
            try{
                mSpinnerProductionCodeList=database.getAllDistinctProductionCode();
                mSpinnerProductionCodeList.add(0,"Select");
                if(mSpinnerProductionCodeList!=null && mSpinnerProductionCodeList.size()>0)
                {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                            mSpinnerProductionCodeList);
                    sp_productioncode.setAdapter(adapter);
                }else {
                    showNoInternetDialog(
                            mContext,"Please download parent seed distribution data."
                    );
                }

            }catch (Exception e)
            {

            }


            sp_productioncode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        String str=parent.getSelectedItem().toString().trim();
                        Toast.makeText(mContext, ""+str, Toast.LENGTH_SHORT).show();
                        if (!str.contains("Select")) {
                            strFilterValue_PC = str;
                            strFilterName_PC = "Production code";
                        }else {
                            strFilterValue_PC = "";
                            strFilterName_PC = "";
                        }  }catch (Exception e)
                    {

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            try{
                mSpinnerFocussVillageList=database.getAllFocusVillage();
                VillageModel vv=new VillageModel();
                vv.setVillage("Select");
                mSpinnerFocussVillageList.add(0,vv);
                if(mSpinnerFocussVillageList!=null && mSpinnerFocussVillageList.size()>0)
                {
                    ArrayAdapter<VillageModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                            mSpinnerFocussVillageList);
                    sp_focusvillage.setAdapter(adapter);
                }else {
                    showNoInternetDialog(
                            mContext,"Please download focused village."
                    );
                }

            }catch (Exception e)
            {

            }
            sp_focusvillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        VillageModel villageModel=(VillageModel) parent.getSelectedItem();
                        if (villageModel.getVillage() != null &&  !villageModel.getVillage().contains("Select")) {

                            Toast.makeText(mContext, "Selected Village is " + villageModel.getVillage(), Toast.LENGTH_SHORT).show();
                            strFilterValue = villageModel.getVillage();
                            strFilterName = "Focus village ";
                        }else {
                            strFilterValue = "";
                            strFilterName = "";
                        }
                    }catch (Exception e)
                    {

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_btn:
                /*mGrowerSearchLayout.setVisibility(View.GONE);
                mFilterSearchLayout.setVisibility(View.GONE);
                mBackButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.GONE);
                if (mGrowerList.size() > 0) {
                    mFieldMonitoringReportAdapter = null;
                    mFieldMonitoringReportAdapter = new FieldMonitoringReportAdapter(mContext, FiledMonitoringReportEntry.this, mGrowerList);
                    mRecyclerView.setAdapter(mFieldMonitoringReportAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }*/
           /*     if(database.isFirstFieldVisitDone(selectedGrowerId))
                {
                 showNoInternetDialog(mContext,"First visit is done with this grower.");
                }
                else if(database.isFirstFieldVisitDoneLocal(selectedGrowerId))
                {
                    showNoInternetDialog(mContext, "First visit is done with this grower.");

                }
                else {*/
             //   Toast.makeText(mContext, ""+selectedGrowerId, Toast.LENGTH_SHORT).show();
                if (CheckGpsStatus()) {
                    if (selectedGrowerId == 0) {
                        Toast.makeText(mContext, "Please Select Grower.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(this, FiledReportDashboard.class);
                        startActivity(intent);
                    }
                }else {
                    Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent1);
                }
             //   }
                break;
            case R.id.back_btn:
                mGrowerSearchLayout.setVisibility(View.VISIBLE);
                mFilterSearchLayout.setVisibility(View.VISIBLE);
                mBackButton.setVisibility(View.GONE);
                mNextButton.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                break;
            case R.id.btn_search:
                searchGrower();
                break;

        }

    }

    private void searchGrower() {
        try{
           // Toast.makeText(mContext, "Grower Location "+strFilterName+" FilterName :"+strFilterValue, Toast.LENGTH_SHORT).show();



           new GetGrowerMasterAsyncTask().execute();
        }catch (Exception exception)
        {

        }
    }


    private class GetGrowerMasterAsyncTask extends AsyncTask<Void, Void, ArrayList<GetAllSeedDistributionModel>> {
ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog=new ProgressDialog(mContext);
            String lable="";
            if(strFilterValue.trim().equals(""))
            {
                lable+="Result for all grower.";
                progressDialog.setMessage("Searching all grower");
                lbl_search_filter_result.setText("Result for all grower.");
            }else {
                lable+="Result for all grower where " + strFilterName + " = " + strFilterValue;
                progressDialog.setMessage("Searching grower by " + strFilterName + " = " + strFilterValue);
                lbl_search_filter_result.setText("Result for all grower where " + strFilterName + " = " + strFilterValue);
            }

            if(strFilterValue_PC.trim().equals(""))
            {
                lable+="Result for all production code.";

                progressDialog.setMessage("Searching all grower");
                lbl_search_filter_result.setText("Result for all production code.");
            }else {
                lable+="Result for Grower where " + strFilterName_PC + " = " + strFilterValue_PC;

                progressDialog.setMessage("Searching grower by " + strFilterName_PC + " = " + strFilterValue_PC);
                lbl_search_filter_result.setText("Result for Grower where " + strFilterName_PC + " = " + strFilterValue_PC);
            }
            lbl_search_filter_result.setText(lable);
            progressDialog.show();
            }

        @Override
        protected final ArrayList<GetAllSeedDistributionModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<GetAllSeedDistributionModel> actionModels;
            try {
                database = new SqlightDatabase(mContext);
                actionModels = database.getAllSeedDistributionListNo(strFilterValue,strFilterValue_PC);
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return actionModels;
        }

        @Override
        protected void onPostExecute(ArrayList<GetAllSeedDistributionModel> result) {
         //   hideProgressDialog();
            if (mGrowerList != null) {
                mGrowerList.clear();
            }
            progressDialog.dismiss();
            GetAllSeedDistributionModel model = new GetAllSeedDistributionModel();
            model.setGrowerFullName("Select");
            model.setGrowerUniqueCode("");

            if (result != null && result.size() > 0) {
                result.add(0, model);
                mGrowerList = result;
               /* for (int i = 0; i < result.size(); i++) {
                    if (!result.get(i).getUserType().equalsIgnoreCase("Organizer")) {
                        mGrowerList.add(result.get(i));
                    }
                }

                if (mGrowerList.size() > 0) {*/
                SpinnerGrowerAdapter adapter = new SpinnerGrowerAdapter(mContext, mGrowerList);
                mSearchByIdNameSpinner.setAdapter(adapter);
                /* }*/
                super.onPostExecute(result);

                show_grower_list();
            }

            /*if (mGrowerList.size() > 0) {
                mFieldMonitoringReportAdapter = null;
                mFieldMonitoringReportAdapter = new FieldMonitoringReportAdapter(mContext, FiledMonitoringReportEntry.this, mGrowerList);
                mRecyclerView.setAdapter(mFieldMonitoringReportAdapter);
                mRecyclerView.setVisibility(View.VISIBLE);
            }*/
        }
    }

    @Override
    public void recyclerViewListClicked(View v, int position, String userId) {
        showFieldVisitFirstLayout(position);
    }

    private void showFieldVisitFirstLayout(int position) {
        /*mGrowerSearchLayout.setVisibility(View.GONE);
        mFilterSearchLayout.setVisibility(View.GONE);
        mBackButton.setVisibility(View.GONE);
        mNextButton.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);*/
        // mFieldVisitFirst.setVisibility(View.VISIBLE);

       /* mGrowerName.setText(mGrowerList.get(position).getGrowerFullName());
        mIssuedSeedArea.setText(""+mGrowerList.get(position).getSeedProductionArea());
        mProductionCode.setText(mGrowerList.get(position).getProductionCode());*/
    }


    private class GetCategoriesAsyncTask extends AsyncTask<Void, Void, ArrayList<CategoryModel>> {
        @Override
        protected final ArrayList<CategoryModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<CategoryModel> actionModels;
            try {
                database = new SqlightDatabase(mContext);
                actionModels = database.getAllCategories();
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return actionModels;
        }

        @Override
        protected void onPostExecute(ArrayList<CategoryModel> result) {

            if (result != null && result.size() > 0) {
                total_active_spinners = result.size();
                //  et_address_edittext.setText("" + total_active_spinners);
                for (int i = 0; i < result.size(); i++) {
//                    SearchableSpinner searchableSpinner = findViewById(mSpinnerArray[i]);
                    mSpinnerArray[i].setVisibility(View.VISIBLE);
                    mSpinnerArray[i].setTitle("Select " + result.get(i).getDisplayTitle());

                    TextView textView = findViewById(mSpinnerHeadingTextView[i]);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(Html.fromHtml(result.get(i).getDisplayTitle() + "<font color='#FF0000'>*</font>"));
                }
                mSpinnerPosition = 1;
                mCountryMasterIdAsPerSelection = Integer.parseInt(Preferences.get(mContext, Preferences.COUNTRY_MASTER_ID));
                new GetLocationMasterAsyncTask().execute();
            } /*else {
                Log.e("temporary", "onPostExecute result null ");
            }*/
            super.onPostExecute(result);
        }
    }

    private class GetLocationMasterAsyncTask extends AsyncTask<Void, Void, ArrayList<CategoryChildModel>> {
        @Override
        protected final ArrayList<CategoryChildModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<CategoryChildModel> mCategoryChildModelsList = null;
            try {
                database = new SqlightDatabase(mContext);
                mCategoryChildModelsList = database.getLocationCategories(/*Integer.parseInt(Preferences.get(mContext, Preferences.COUNTRY_MASTER_ID))*/mCountryMasterIdAsPerSelection);
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return mCategoryChildModelsList;
        }

        @Override
        protected void onPostExecute(ArrayList<CategoryChildModel> result) {
            if (result != null && result.size() > 0) {
                /*Added by jeevan 12-12-2022*/
                if (mCountryMasterIdAsPerSelection != 0) {
                    /*Added by jeevan 12-12-2022 ended here*/
                    callLocationAdapter(result);
                    /*Added by jeevan 12-12-2022*/
                } else {
                   /* CategoryChildModel categoryChildModel = new CategoryChildModel();
                    categoryChildModel.setKeyValue("");
                    categoryChildModel.setCountryMasterId(0);
                    categoryChildModel.setCategoryId(0);
                    ArrayList<CategoryChildModel> result1 = new ArrayList<>();
                    result1.add(categoryChildModel);*/
                    result.clear();
                    callLocationAdapter(result);
                }
                /*Added by jeevan 12-12-2022 ended here*/
            } else {
                clearSpinnerData(mSpinnerPosition);
            }
            super.onPostExecute(result);
        }
    }

    private void callLocationAdapter(ArrayList<CategoryChildModel> result) {
        CategoryChildModel categoryChildModel = new CategoryChildModel();
        categoryChildModel.setKeyValue("Select");
        categoryChildModel.setCountryMasterId(0);
        categoryChildModel.setCategoryId(0);
        result.add(0, categoryChildModel);
        switch (mSpinnerPosition) {
            case 1: {
                mSpinner1List = result;
//                Spinner1Adapter adapter = new Spinner1Adapter(mContext, R.layout.spinner_rows,
//                        result);
                ArrayAdapter<CategoryChildModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        result);
                mSearchableSpinner1.setAdapter(adapter);
            }
            break;
            case 2: {
                mSpinner2List = result;
                //Spinner2Adapter adapter = new Spinner2Adapter(mContext, R.layout.spinner_rows, result);
                ArrayAdapter<CategoryChildModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        result);
                mSearchableSpinner2.setAdapter(adapter);
                mSearchableSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (mSpinner2List != null && mSpinner2List.size() > 0) {
                            mSpinnerPosition = 3;
                            mCountryMasterIdAsPerSelection = mSpinner2List.get(i).getCountryMasterId();

                            CategoryChildModel d = (CategoryChildModel) adapterView.getSelectedItem();
                            if (d.getCategoryName() != null &&  !d.getCategoryName().contains("Select")&& !d.getKeyValue().contains("Select")){
                                  strFilterName = "" + d.getCategoryName();
                            strFilterValue=""+d.getKeyValue();}
                            new GetLocationMasterAsyncTask().execute();

                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            break;
            case 3: {
                mSpinner3List = result;
                // Spinner3Adapter adapter = new Spinner3Adapter(mContext, R.layout.spinner_rows, result);
                ArrayAdapter<CategoryChildModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        result);
                mSearchableSpinner3.setAdapter(adapter);

                mSearchableSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (mSpinner3List != null && mSpinner3List.size() > 0) {
                            mSpinnerPosition = 4;
                            mCountryMasterIdAsPerSelection = mSpinner3List.get(i).getCountryMasterId();

                            CategoryChildModel d = (CategoryChildModel) adapterView.getSelectedItem();
                            if (d.getCategoryName() != null &&  !d.getCategoryName().contains("Select")&& !d.getKeyValue().contains("Select")){
                                  strFilterName = "" + d.getCategoryName();
                            strFilterValue=""+d.getKeyValue();}
                            new GetLocationMasterAsyncTask().execute();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            break;
            case 4: {
                mSpinner4List = result;
                //Spinner4Adapter adapter = new Spinner4Adapter(mContext, R.layout.spinner_rows, result);
                ArrayAdapter<CategoryChildModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        result);
                mSearchableSpinner4.setAdapter(adapter);

                mSearchableSpinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (mSpinner4List != null && mSpinner4List.size() > 0) {
                            mSpinnerPosition = 5;
                            mCountryMasterIdAsPerSelection = mSpinner4List.get(i).getCountryMasterId();

                            CategoryChildModel d = (CategoryChildModel) adapterView.getSelectedItem();
                            if (d.getCategoryName() != null &&  !d.getCategoryName().contains("Select")&& !d.getKeyValue().contains("Select")){
                                  strFilterName = "" + d.getCategoryName();
                            strFilterValue=""+d.getKeyValue();}
                            new GetLocationMasterAsyncTask().execute();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            break;
            case 5: {
                mSpinner5List = result;
                //Spinner5Adapter adapter = new Spinner5Adapter(mContext, R.layout.spinner_rows, result);
                ArrayAdapter<CategoryChildModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        result);
                mSearchableSpinner5.setAdapter(adapter);

                mSearchableSpinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (mSpinner5List != null && mSpinner5List.size() > 0) {
                            mSpinnerPosition = 6;
                            mCountryMasterIdAsPerSelection = mSpinner5List.get(i).getCountryMasterId();

                            CategoryChildModel d = (CategoryChildModel) adapterView.getSelectedItem();
                            if (d.getCategoryName() != null &&  !d.getCategoryName().contains("Select")&& !d.getKeyValue().contains("Select")){
                                  strFilterName = "" + d.getCategoryName();
                            strFilterValue=""+d.getKeyValue();}
                            new GetLocationMasterAsyncTask().execute();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            break;
            case 6: {
                mSpinner6List = result;
                // Spinner6Adapter adapter = new Spinner6Adapter(mContext, R.layout.spinner_rows, result);
                ArrayAdapter<CategoryChildModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        result);
                mSearchableSpinner6.setAdapter(adapter);

                mSearchableSpinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (mSpinner6List != null && mSpinner6List.size() > 0) {
                            mSpinnerPosition = 7;
                            mCountryMasterIdAsPerSelection = mSpinner6List.get(i).getCountryMasterId();

                            CategoryChildModel d = (CategoryChildModel) adapterView.getSelectedItem();
                            if (d.getCategoryName() != null &&  !d.getCategoryName().contains("Select")&& !d.getKeyValue().contains("Select")){
                                  strFilterName = "" + d.getCategoryName();
                            strFilterValue=""+d.getKeyValue();}
                            new GetLocationMasterAsyncTask().execute();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            break;
            case 7: {
                mSpinner7List = result;
                //Spinner7Adapter adapter = new Spinner7Adapter(mContext, R.layout.spinner_rows, result);
                ArrayAdapter<CategoryChildModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        result);
                mSearchableSpinner7.setAdapter(adapter);

                mSearchableSpinner7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (mSpinner7List != null && mSpinner7List.size() > 0) {
                            mSpinnerPosition = 8;
                            mCountryMasterIdAsPerSelection = mSpinner7List.get(i).getCountryMasterId();
                            CategoryChildModel d = (CategoryChildModel) adapterView.getSelectedItem();
                            if (d.getCategoryName() != null &&  !d.getCategoryName().contains("Select")&& !d.getKeyValue().contains("Select")){
                                strFilterName = "" + d.getCategoryName();
                                strFilterValue=""+d.getKeyValue();}
                            new GetLocationMasterAsyncTask().execute();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            break;
            case 8: {
                mSpinner8List = result;
                // Spinner8Adapter adapter = new Spinner8Adapter(mContext, R.layout.spinner_rows, result);
                ArrayAdapter<CategoryChildModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        result);
                mSearchableSpinner8.setAdapter(adapter);

                mSearchableSpinner8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (mSpinner8List != null && mSpinner8List.size() > 0) {
                            mSpinnerPosition = 9;
                            mCountryMasterIdAsPerSelection = mSpinner8List.get(i).getCountryMasterId();
                            CategoryChildModel d = (CategoryChildModel) adapterView.getSelectedItem();
                            if (d.getCategoryName() != null &&  !d.getCategoryName().contains("Select")&& !d.getKeyValue().contains("Select")){
                                strFilterName = "" + d.getCategoryName();
                                strFilterValue=""+d.getKeyValue();}
                            new GetLocationMasterAsyncTask().execute();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            break;
            case 9: {
                mSpinner9List = result;
                // Spinner9Adapter adapter = new Spinner9Adapter(mContext, R.layout.spinner_rows, result);
                ArrayAdapter<CategoryChildModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        result);
                mSearchableSpinner9.setAdapter(adapter);

                mSearchableSpinner9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (mSpinner9List != null && mSpinner9List.size() > 0) {
                            mSpinnerPosition = 10;
                            mCountryMasterIdAsPerSelection = mSpinner9List.get(i).getCountryMasterId();
                            CategoryChildModel d = (CategoryChildModel) adapterView.getSelectedItem();
                            if (d.getCategoryName() != null &&  !d.getCategoryName().contains("Select")&& !d.getKeyValue().contains("Select")){
                                strFilterName = "" + d.getCategoryName();
                                strFilterValue=""+d.getKeyValue();}
                            new GetLocationMasterAsyncTask().execute();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            break;
            case 10: {
                mSpinner10List = result;
                // Spinner10Adapter adapter = new Spinner10Adapter(mContext, R.layout.spinner_rows, result);
                ArrayAdapter<CategoryChildModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        result);
                mSearchableSpinner10.setAdapter(adapter);
                mSearchableSpinner10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (mSpinner10List != null && mSpinner10List.size() > 0) {
                            mSpinnerPosition = 11;
                            mCountryMasterIdAsPerSelection = mSpinner10List.get(i).getCountryMasterId();
                            CategoryChildModel d = (CategoryChildModel) adapterView.getSelectedItem();
                            if (d.getCategoryName() != null &&  !d.getCategoryName().contains("Select")&& !d.getKeyValue().contains("Select")){
                                strFilterName = "" + d.getCategoryName();
                                strFilterValue=""+d.getKeyValue();}
                            new GetLocationMasterAsyncTask().execute();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
            break;
        }
    }

    private void clearSpinnerData(int mSpinnerPosition) {
        switch (mSpinnerPosition) {
            case 2: {

                if (mSpinner2List != null) {
                    mSpinner2List.clear();
                }
                if (mSpinner3List != null) {
                    mSpinner3List.clear();
                }
                if (mSpinner4List != null) {
                    mSpinner4List.clear();
                }
                if (mSpinner5List != null) {
                    mSpinner5List.clear();
                }
                if (mSpinner6List != null) {
                    mSpinner6List.clear();
                }
                if (mSpinner7List != null) {
                    mSpinner7List.clear();
                }
                if (mSpinner8List != null) {
                    mSpinner8List.clear();
                }
                if (mSpinner9List != null) {
                    mSpinner9List.clear();
                }
                if (mSpinner10List != null) {
                    mSpinner10List.clear();
                }

                mSearchableSpinner2.setAdapter(null);
                mSearchableSpinner3.setAdapter(null);
                mSearchableSpinner4.setAdapter(null);
                mSearchableSpinner5.setAdapter(null);
                mSearchableSpinner6.setAdapter(null);
                mSearchableSpinner7.setAdapter(null);
                mSearchableSpinner8.setAdapter(null);
                mSearchableSpinner9.setAdapter(null);
                mSearchableSpinner10.setAdapter(null);
            }
            break;
            case 3: {

                if (mSpinner3List != null) {
                    mSpinner3List.clear();
                }
                if (mSpinner4List != null) {
                    mSpinner4List.clear();
                }
                if (mSpinner5List != null) {
                    mSpinner5List.clear();
                }
                if (mSpinner6List != null) {
                    mSpinner6List.clear();
                }
                if (mSpinner7List != null) {
                    mSpinner7List.clear();
                }
                if (mSpinner8List != null) {
                    mSpinner8List.clear();
                }
                if (mSpinner9List != null) {
                    mSpinner9List.clear();
                }
                if (mSpinner10List != null) {
                    mSpinner10List.clear();
                }

                mSearchableSpinner3.setAdapter(null);
                mSearchableSpinner4.setAdapter(null);
                mSearchableSpinner5.setAdapter(null);
                mSearchableSpinner6.setAdapter(null);
                mSearchableSpinner7.setAdapter(null);
                mSearchableSpinner8.setAdapter(null);
                mSearchableSpinner9.setAdapter(null);
                mSearchableSpinner10.setAdapter(null);
            }
            break;
            case 4: {
                if (mSpinner4List != null) {
                    mSpinner4List.clear();
                }
                if (mSpinner5List != null) {
                    mSpinner5List.clear();
                }
                if (mSpinner6List != null) {
                    mSpinner6List.clear();
                }
                if (mSpinner7List != null) {
                    mSpinner7List.clear();
                }
                if (mSpinner8List != null) {
                    mSpinner8List.clear();
                }
                if (mSpinner9List != null) {
                    mSpinner9List.clear();
                }
                if (mSpinner10List != null) {
                    mSpinner10List.clear();
                }

                mSearchableSpinner4.setAdapter(null);
                mSearchableSpinner5.setAdapter(null);
                mSearchableSpinner6.setAdapter(null);
                mSearchableSpinner7.setAdapter(null);
                mSearchableSpinner8.setAdapter(null);
                mSearchableSpinner9.setAdapter(null);
                mSearchableSpinner10.setAdapter(null);
            }
            break;
            case 5: {
                if (mSpinner5List != null) {
                    mSpinner5List.clear();
                }
                if (mSpinner6List != null) {
                    mSpinner6List.clear();
                }
                if (mSpinner7List != null) {
                    mSpinner7List.clear();
                }
                if (mSpinner8List != null) {
                    mSpinner8List.clear();
                }
                if (mSpinner9List != null) {
                    mSpinner9List.clear();
                }
                if (mSpinner10List != null) {
                    mSpinner10List.clear();
                }

                mSearchableSpinner5.setAdapter(null);
                mSearchableSpinner6.setAdapter(null);
                mSearchableSpinner7.setAdapter(null);
                mSearchableSpinner8.setAdapter(null);
                mSearchableSpinner9.setAdapter(null);
                mSearchableSpinner10.setAdapter(null);
            }
            break;
            case 6: {
                if (mSpinner6List != null) {
                    mSpinner6List.clear();
                }
                if (mSpinner7List != null) {
                    mSpinner7List.clear();
                }
                if (mSpinner8List != null) {
                    mSpinner8List.clear();
                }
                if (mSpinner9List != null) {
                    mSpinner9List.clear();
                }
                if (mSpinner10List != null) {
                    mSpinner10List.clear();
                }

                mSearchableSpinner6.setAdapter(null);
                mSearchableSpinner7.setAdapter(null);
                mSearchableSpinner8.setAdapter(null);
                mSearchableSpinner9.setAdapter(null);
                mSearchableSpinner10.setAdapter(null);
            }
            break;
            case 7: {
                if (mSpinner7List != null) {
                    mSpinner7List.clear();
                }
                if (mSpinner8List != null) {
                    mSpinner8List.clear();
                }
                if (mSpinner9List != null) {
                    mSpinner9List.clear();
                }
                if (mSpinner10List != null) {
                    mSpinner10List.clear();
                }
                mSearchableSpinner7.setAdapter(null);
                mSearchableSpinner8.setAdapter(null);
                mSearchableSpinner9.setAdapter(null);
                mSearchableSpinner10.setAdapter(null);
            }
            break;
            case 8: {

                if (mSpinner8List != null) {
                    mSpinner8List.clear();
                }
                if (mSpinner9List != null) {
                    mSpinner9List.clear();
                }
                if (mSpinner10List != null) {
                    mSpinner10List.clear();
                }
                mSearchableSpinner8.setAdapter(null);
                mSearchableSpinner9.setAdapter(null);
                mSearchableSpinner10.setAdapter(null);
            }
            break;
            case 9: {
                if (mSpinner9List != null) {
                    mSpinner9List.clear();
                }
                if (mSpinner10List != null) {
                    mSpinner10List.clear();
                }
                mSearchableSpinner9.setAdapter(null);
                mSearchableSpinner10.setAdapter(null);
            }
            break;
            case 10: {
                if (mSpinner10List != null) {
                    mSpinner10List.clear();
                }
                mSearchableSpinner10.setAdapter(null);
            }
            break;
        }
    }

    /*private boolean validation() {
        if (mGrowerPhotoFile == null) {
            showToast(getString(R.string.please_grower_photo));
            return false;
        } else if (mSearchableSpinner5.getSelectedItemPosition() == -1) {
            showToast("Data not found");
            return false;
        } else if (TextUtils.isEmpty(et_landmark.getText().toString())) {
            showToast(getString(R.string.Please_enter_landmark));
            return false;
        } else if (TextUtils.isEmpty(et_fullname.getText().toString())) {
            if (str_Lable.equalsIgnoreCase("Grower")) {
                showToast(getString(R.string.Please_enter_farmer_name));
            } else {
                showToast(getString(R.string.Please_enter_organizer_name));
            }
            return false;
        } else if (*//*TextUtils.isEmpty(et_gender.getText().toString())*//*
                !mMaleRadioButton.isChecked() && !mFemaleRadioButton.isChecked()) {
            showToast(getString(R.string.Please_select_gender));
            return false;
        } else if (TextUtils.isEmpty(et_dob.getText().toString().trim())) {
            showToast(getString(R.string.Please_enter_date_of_birth));
            return false;
        } else if (TextUtils.isEmpty(et_mobile.getText().toString().trim())) {
            showToast(getString(R.string.Please_enter_mobile_no));
            return false;
        } else if (mDocFrontPhotoFile == null) {
            showToast(getString(R.string.please_capture_national_id_photo_front));
            return false;
        } else if (mDocBackPhotoFile == null) {
            showToast(getString(R.string.please_capture_national_id_photo_back));
            return false;
        } else if (TextUtils.isEmpty(et_uniqcode.getText().toString().trim())) {
            showToast(getString(R.string.Please_enter_unique_code));
            return false;
        } else {
            *//*return if (checkInternetConnection()) {*//*
            return true;
            *//*} else {
                setEnableOrDisable(true)
                showToast(getString(R.string.err_internet))
                false
            }*//*
        }
    }*/
    private /*boolean*/void validation() {

        /*Added by jeevan 28-11-2022*/
        if (mSearchableSpinner1.getVisibility() == View.VISIBLE &&
                (mSearchableSpinner1.getSelectedItemPosition() == 0 ||
                        mSearchableSpinner1.getSelectedItemPosition() == -1)) {
            TextView textView = findViewById(mSpinnerHeadingTextView[0]);
            showToast("Please select " + textView.getText().toString());
            // return false;
        } else if (mSearchableSpinner2.getVisibility() == View.VISIBLE &&
                (mSearchableSpinner2.getSelectedItemPosition() == 0 ||
                        mSearchableSpinner2.getSelectedItemPosition() == -1)) {
            TextView textView = findViewById(mSpinnerHeadingTextView[1]);
            showToast("Please select " + textView.getText().toString());
            // return false;
        } else if (mSearchableSpinner3.getVisibility() == View.VISIBLE &&
                (mSearchableSpinner3.getSelectedItemPosition() == 0 ||
                        mSearchableSpinner3.getSelectedItemPosition() == -1)) {
            TextView textView = findViewById(mSpinnerHeadingTextView[2]);
            showToast("Please select " + textView.getText().toString());
            // return false;
        } else if (mSearchableSpinner4.getVisibility() == View.VISIBLE &&
                (mSearchableSpinner4.getSelectedItemPosition() == 0 ||
                        mSearchableSpinner4.getSelectedItemPosition() == -1)) {
            TextView textView = findViewById(mSpinnerHeadingTextView[3]);
            showToast("Please select " + textView.getText().toString());
            // return false;
        } else if (mSearchableSpinner5.getVisibility() == View.VISIBLE &&
                (mSearchableSpinner5.getSelectedItemPosition() == 0 ||
                        mSearchableSpinner5.getSelectedItemPosition() == -1)) {
            TextView textView = findViewById(mSpinnerHeadingTextView[4]);
            showToast("Please select " + textView.getText().toString());
            // return false;
        } else if (mSearchableSpinner6.getVisibility() == View.VISIBLE &&
                (mSearchableSpinner6.getSelectedItemPosition() == 0 ||
                        mSearchableSpinner6.getSelectedItemPosition() == -1)) {
            TextView textView = findViewById(mSpinnerHeadingTextView[5]);
            showToast("Please select " + textView.getText().toString());
            // return false;
        } else if (mSearchableSpinner7.getVisibility() == View.VISIBLE &&
                (mSearchableSpinner7.getSelectedItemPosition() == 0 ||
                        mSearchableSpinner7.getSelectedItemPosition() == -1)) {
            TextView textView = findViewById(mSpinnerHeadingTextView[6]);
            showToast("Please select " + textView.getText().toString());
            // return false;
        } else if (mSearchableSpinner8.getVisibility() == View.VISIBLE &&
                (mSearchableSpinner8.getSelectedItemPosition() == 0 ||
                        mSearchableSpinner8.getSelectedItemPosition() == -1)) {
            TextView textView = findViewById(mSpinnerHeadingTextView[7]);
            showToast("Please select " + textView.getText().toString());
            // return false;
        } else if (mSearchableSpinner9.getVisibility() == View.VISIBLE &&
                (mSearchableSpinner9.getSelectedItemPosition() == 0 ||
                        mSearchableSpinner9.getSelectedItemPosition() == -1)) {
            TextView textView = findViewById(mSpinnerHeadingTextView[8]);
            showToast("Please select " + textView.getText().toString());
            // return false;
        } else if (mSearchableSpinner10.getVisibility() == View.VISIBLE &&
                (mSearchableSpinner10.getSelectedItemPosition() == 0 ||
                        mSearchableSpinner10.getSelectedItemPosition() == -1)) {
            TextView textView = findViewById(mSpinnerHeadingTextView[9]);
            showToast("Please select " + textView.getText().toString());
            // return false;
        }
        /*Added by jeevan ended here 28-11-2022*/


    }

    public boolean CheckGpsStatus(){
        locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return GpsStatus;
    }

    public void show_grower_list()
    {
        try{
            mManager=new LinearLayoutManager(mContext);
            dialog_growerlist=new Dialog(mContext,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog_growerlist.setContentView(R.layout.growerlist_dialog);
            RecyclerView rc_list=dialog_growerlist.findViewById(R.id.rc_list);
            EditText et_search=dialog_growerlist.findViewById(R.id.et_search);
            ImageView imageButton3=dialog_growerlist.findViewById(R.id.imageButton3);
            imageButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_growerlist.dismiss();
                }
            });
            et_search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                       filter(s.toString().trim());
                }
            });
            rc_list.setLayoutManager(mManager);


            try {
                visitGrowerAdapter = new VisitGrowerAdapter((ArrayList) mGrowerList, mContext);
                rc_list.setAdapter(visitGrowerAdapter);
                dialog_growerlist.show();
            } catch (NullPointerException e) {
                Toast.makeText(mContext, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(mContext, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(mContext, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<GetAllSeedDistributionModel> filteredlist = new ArrayList<GetAllSeedDistributionModel>();

        // running a for loop to compare elements.
        for (GetAllSeedDistributionModel item : mGrowerList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getGrowerFullName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            visitGrowerAdapter.filterList(filteredlist);
        }
    }
}
