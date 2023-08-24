package mahyco.mipl.nxg.view.prod_seed_create;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.ScanMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mahyco.mipl.nxg.BuildConfig;
import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.adapter.SeedDistrPlantingYearAdapter;
import mahyco.mipl.nxg.adapter.SeedProductionGrowerAdapter;
import mahyco.mipl.nxg.adapter.VisitGrowerAdapter;
import mahyco.mipl.nxg.model.CropModel;
import mahyco.mipl.nxg.model.DownloadGrowerModel;
import mahyco.mipl.nxg.model.GetAllSeedDistributionModel;
import mahyco.mipl.nxg.model.OldGrowerSeedDistributionModel;
import mahyco.mipl.nxg.model.ProductionClusterModel;
import mahyco.mipl.nxg.model.SeasonModel;
import mahyco.mipl.nxg.model.SeedBatchNoModel;
import mahyco.mipl.nxg.model.SeedReceiptModel;
import mahyco.mipl.nxg.model.SeedRegistraionLocalModel;
import mahyco.mipl.nxg.model.StoreAreaModel;
import mahyco.mipl.nxg.model.VillageModel;
import mahyco.mipl.nxg.spinner.CCFSerachSpinner;
import mahyco.mipl.nxg.util.BaseActivity;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.util.SqlightDatabase;

public class ProductionCreateGrowerListActivity extends BaseActivity implements View.OnClickListener, SeedProductionGrowerAdapter.EventListener {
    private Context mContext;

    private AppCompatSpinner mPlantingYearSpinner;
    private CCFSerachSpinner mSeasonSpinner;
    private CCFSerachSpinner mCropSpinner;
    private CCFSerachSpinner mProductionCodeSpinner;
    private CCFSerachSpinner mClusterSpinner;
    private CCFSerachSpinner mMaleBatchNoSpinner;
    private CCFSerachSpinner mFemaleBatchNoSpinner;
    private CCFSerachSpinner mOrganizerNameSpinner;
    private CCFSerachSpinner mSearchByIdNameSpinner;
    // private SearchableSpinner mCropTypeSpinner;
    private ArrayList<VillageModel> mSpinnerFocussVillageList;
    private ArrayList<CropModel> mCropList = new ArrayList<>();
    private ArrayList<DownloadGrowerModel> mGrowerList = new ArrayList<>();
    private ArrayList<DownloadGrowerModel> mOrganizerNameList = new ArrayList<>();
    private ArrayList<SeasonModel> mSeasonList = new ArrayList<>();
    private ArrayList<ProductionClusterModel> mProdClusterList = new ArrayList<>();
    // private ArrayList<ProductCodeModel> mProdCodeList = new ArrayList<>();
    private ArrayList<SeedBatchNoModel> mMaleBatchNoList = new ArrayList<>();
    private ArrayList<SeedBatchNoModel> mFemaleBatchNoList = new ArrayList<>();
    //private ArrayList<CropTypeModel> mCropTypeList = new ArrayList<>();
    private ArrayList<SeedReceiptModel> mSeedProductionCodeList = new ArrayList<>();

    private AppCompatEditText mAreaEditText;
    private AppCompatEditText grower_mobile_no_edittext;

    private AppCompatTextView mGrowerName;
    private AppCompatTextView mUniqueCode;
    private AppCompatTextView mAddressTextView;
    private AppCompatTextView mParentSeedIssueDate;
    private AppCompatTextView mStaffTextView;
    private AppCompatTextView mOrganizerNameTextView;
    private AppCompatButton grower_searchlbl;

    private CodeScanner mCodeScanner;
    private CodeScannerView mCodeScannerView;
    private ScrollView mScrollView;

    private Button mSubmitButton;
    private Button mScanQRCodeButton;
    private String mCountryId = "0";
    private String mCountryName = "";
    //private RadioGroup mRadioGroup;
    private OldGrowerSeedDistributionModel mOldGrowerSeedDistributionModel = new OldGrowerSeedDistributionModel();

    private boolean mFirstTimeSelectedCrop = false;
    private boolean mProductFirstTimeSelected = false;
    private boolean mProductSpinnerFirstTimeSelected = false;
    private boolean mClusterSpinnerFirstTimeCalled = false;
    private boolean mOrganizerSpinnerFirstTimeSelected = false;
    private boolean mGrowerRadioBtnSelected = true;
    private AppCompatTextView mMaleBatchNoTextView;
    CCFSerachSpinner sp_focusvillage;

    private androidx.appcompat.widget.Toolbar toolbar;
    int selectedGrowerId = 0;
    TextView txt_growersearch;

    @Override
    protected int getLayout() {
        return R.layout.activity_production_create_grower_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_production_create_grower_list);
        ArrayList<String> mYearList = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Seed Production Registration");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCodeScannerView != null && mCodeScannerView.getVisibility() == View.VISIBLE) {
                    mCodeScanner.releaseResources();
                    hideScannerLayout();
                } else {
                    finish();
                }
            }
        });

        mContext = this;
        AppCompatTextView mVersionTextView = findViewById(R.id.registration_version_code);
        mVersionTextView.setText(getString(R.string.version_code, ""+BuildConfig.VERSION_CODE));
        // Log.e("temporary", "is check called grower");

        sp_focusvillage = findViewById(R.id.sp_focusvillage);
        grower_searchlbl = findViewById(R.id.grower_searchlbl);
        mMaleBatchNoTextView = findViewById(R.id.male_batch_no_textview);
        mPlantingYearSpinner = findViewById(R.id.planting_year_drop_down);
        mOrganizerNameSpinner = findViewById(R.id.organizer_name_drop_down);
        mSeasonSpinner = findViewById(R.id.season_drop_down);
        mCropSpinner = findViewById(R.id.crop_drop_down);
        mProductionCodeSpinner = findViewById(R.id.production_code_drop_down);
        mClusterSpinner = findViewById(R.id.parent_seed_issue_location_drop_down);
        mMaleBatchNoSpinner = findViewById(R.id.parent_seed_batch_no_male_drop_down);
        mFemaleBatchNoSpinner = findViewById(R.id.parent_seed_batch_no_female_drop_down);
        mGrowerName = findViewById(R.id.grower_name_textview);
        mUniqueCode = findViewById(R.id.unique_id_textview);
        mAddressTextView = findViewById(R.id.address_textview);
        mOrganizerNameTextView = findViewById(R.id.organizer_name_textview);
        mSearchByIdNameSpinner = findViewById(R.id.search_grower_by_id_name);
        txt_growersearch = findViewById(R.id.txt_growersearch);
        // mCropTypeSpinner = findViewById(R.id.crop_type_down);


        mSearchByIdNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Log.e("temporary", " mSearchByIdNameSpinner.setOnItemSelectedListener");
                if (l != 0) {
                    mGrowerName.setText(mGrowerList.get(i).getFullName());
                    mUniqueCode.setText(mGrowerList.get(i).getUniqueCode());
                    mAddressTextView.setText(mGrowerList.get(i).getLandMark() + ", " +
                            mGrowerList.get(i).getCountryName());
                    grower_mobile_no_edittext.setText(mGrowerList.get(i).getMobileNo());
                } else {
                    mGrowerName.setText("");
                    mUniqueCode.setText("");
                    mAddressTextView.setText("");
                    grower_mobile_no_edittext.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mCropSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Log.e("temporary", "selected item called mFirstTimeSelectedCrop " + mFirstTimeSelectedCrop);
                mProductSpinnerFirstTimeSelected = false;
                mClusterSpinnerFirstTimeCalled = false;
                if (mFirstTimeSelectedCrop) {
                    mProductionCodeSpinner.setAdapter(null);
                    new GetClusterMasterAsyncTask().execute();
                } else {
                    mFirstTimeSelectedCrop = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        mClusterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Log.e("temporary", "mClusterSpinner selected item called mClusterSpinnerFirstTimeCalled " + mClusterSpinnerFirstTimeCalled);
//                Log.e("temporary", "mSelectedProductionSpinner " + mSelectedProductionSpinner);
               /* if (!mGrowerRadioBtnSelected) {
                    return;
                }*/
                mProductSpinnerFirstTimeSelected = false;
                if (mClusterSpinnerFirstTimeCalled) {
                    new GetProductionCodeMasterAsyncTask().execute();
                } else {
                    mClusterSpinnerFirstTimeCalled = true;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        mOrganizerNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Log.e("temporary", "mOrganizerNameSpinner selected item called mClusterSpinnerFirstTimeCalled " + mOrganizerSpinnerFirstTimeSelected
                //       + " i " + i);
//                Log.e("temporary", "mSelectedProductionSpinner " + mSelectedProductionSpinner);
                if (mOrganizerSpinnerFirstTimeSelected) {
                    new GetCropMasterAsyncTask().execute();
                } else {
                    mOrganizerSpinnerFirstTimeSelected = true;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        mPlantingYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //  Log.e("temporary", "selected item called mPlantingYearFirstTimeSelected " + mProductFirstTimeSelected);
                mProductSpinnerFirstTimeSelected = false;
                mClusterSpinnerFirstTimeCalled = false;
                if (mProductFirstTimeSelected) {
                    //  new GetProductionCodeMasterAsyncTask().execute();
                    new GetCropMasterAsyncTask().execute();
                } else {
                    mProductFirstTimeSelected = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mScanQRCodeButton = findViewById(R.id.seed_scan_qr_code);
        mCodeScannerView = findViewById(R.id.seed_distribution_scanner_view);
        mCodeScanner = new CodeScanner(this, mCodeScannerView);
        mScrollView = findViewById(R.id.seed_distribution_main_scrollview);

        mSubmitButton = findViewById(R.id.seed_distribution_submit_btn);
        mSubmitButton.setOnClickListener(this);

        mParentSeedIssueDate = findViewById(R.id.parent_seed_issue_date_textview);
        mParentSeedIssueDate.setText(getCurrentDate());
        try {
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = df.format(c);
            mParentSeedIssueDate.setText(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mStaffTextView = findViewById(R.id.parent_seed_distribution_by_staff_textview);
        mStaffTextView.setText(Preferences.get(mContext, Preferences.USER_NAME));

        grower_mobile_no_edittext = findViewById(R.id.grower_mobile_no_edittext);
        mAreaEditText = findViewById(R.id.seed_production_area_edittext);
        mAreaEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilters(100, 2)});

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.YEAR, +1);
        mYearList.add(String.valueOf(calendar.get(Calendar.YEAR)));

        calendar.add(Calendar.YEAR, -1);
        mYearList.add(String.valueOf(calendar.get(Calendar.YEAR)));

        calendar.add(Calendar.YEAR, -1);
        mYearList.add(String.valueOf(calendar.get(Calendar.YEAR)));

        SeedDistrPlantingYearAdapter adapter = new SeedDistrPlantingYearAdapter(mContext, R.layout.planting_year_rows, mYearList);
        mPlantingYearSpinner.setAdapter(adapter);
        mPlantingYearSpinner.setSelection(1);

        mCountryName = Preferences.get(mContext, Preferences.COUNTRYNAME);

        if (mCountryName.equalsIgnoreCase("Malawi")) {
            mScanQRCodeButton.setVisibility(View.VISIBLE);
            mScanQRCodeButton.setOnClickListener(this);
        }
        mGrowerRadioBtnSelected = true;
        mOrganizerNameSpinner.setVisibility(View.GONE);
        mOrganizerNameTextView.setVisibility(View.GONE);
        mMaleBatchNoSpinner.setAdapter(null);
        mFemaleBatchNoSpinner.setAdapter(null);
        mProductionCodeSpinner.setAdapter(null);
        new GetSeasonMasterAsyncTask().execute();
        new GetGrowerMasterAsyncTask().execute();
//        new GetClusterMasterAsyncTask().execute();
        // new GetCropTypeMasterAsyncTask().execute();

        txt_growersearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mContext, "Hiiii ", Toast.LENGTH_SHORT).show();
                show_grower_list();
            }
        });
    }


    @Override
    protected void init() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.seed_scan_qr_code: {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {
                    showToast("Camera Permission is Required.");
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    return;
                }
                try {
                    // open scanner
                    hideKeyboard(mContext);
                    mCodeScanner.startPreview();
                    visibleScannerLayout();

                    mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);
                    mCodeScanner.setFormats(CodeScanner.ALL_FORMATS); // list of type BarcodeFormat,
                    mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE); // or CONTINUOUS
                    mCodeScanner.setScanMode(ScanMode.SINGLE);// or CONTINUOUS or PREVIEW
                    mCodeScanner.setAutoFocusEnabled(true);// Whether to enable auto focus or not
                    mCodeScanner.setFlashEnabled(false);// Whether to enable flash or not
                    mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
                        if (!result.getText().isEmpty()) {
                            hideScannerLayout();
                            // Log.e("temporary", " result " + result + " length " + result.getText().length());
                            String[] results = result.getText().split("~");

                            //  Log.e("temporary", " results " + results.length + " results " + results/*+" 1 " + results[1]
                            // +" 5 " + results[5]*/);
                            if (results.length > 10 && results[1].contains("MWI")
                            ) {
                                // showToast("Scanner successfully !!");
                            /*et_dob.setText(results[9]);
                            et_fullname.setText(results[4] + " " + results[6]);
                            et_uniqcode.setText(  results[5]  );*/
                                boolean resultFound = false;
                                for (int i = 0; i < mGrowerList.size(); i++) {
                                    if (mGrowerList.get(i).getUniqueCode().equals(results[5])) {
                                        mSearchByIdNameSpinner.setSelection(i);
                                        mGrowerName.setText(mGrowerList.get(i).getFullName());
                                        mUniqueCode.setText(mGrowerList.get(i).getUniqueCode());
                                        mAddressTextView.setText(mGrowerList.get(i).getLandMark() + ", " +
                                                mGrowerList.get(i).getCountryName());
                                        /*Added by jeevan 9-12-2022*/
                                        new CheckWithLocalDatabaseAsyncTask().execute();
                                        /*Added by jeevan 9-12-2022 ended here*/
                                        resultFound = true;
                                        break;
                                    }
                                }
                                if (!resultFound) {
                                    showToast("Grower not found");
                                }
                            } else {
                                showToast("SCANNER ERROR !! INVALID DATA");
                            }
                        } else {
                            showToast("SCANNER ERROR !! INVALID DATA");
                        }
                    }));
                    mCodeScanner.setErrorCallback(thrown -> runOnUiThread(() -> {
                        hideScannerLayout();
                        showToast("Camera initialization error: ${it.message}");
                    }));
                } catch (Exception e) {
                    //   Log.e("temporary", " e " + e.getCause());
                }
            }
            break;

            case R.id.seed_distribution_submit_btn:

               // Toast.makeText(mContext, "Clicked ", Toast.LENGTH_SHORT).show();
                 validation();

                //    hideKeyboard(mContext);
                //       validation();
//                if (validation()) {
//                    try {
//                        //   Log.e("temporary", " selection value " + mGrowerList.get(mSearchByIdNameSpinner.getSelectedItemPosition()).getUniqueId());
//                        mOldGrowerSeedDistributionModel.setCountryId(Integer.parseInt(Preferences.get(mContext, Preferences.COUNTRYCODE)));
//                        mOldGrowerSeedDistributionModel.setPlantingYear(mPlantingYearSpinner.getSelectedItem().toString());
//                        mOldGrowerSeedDistributionModel.setSeasonId(mSeasonList.get(mSeasonSpinner.getSelectedItemPosition()).getSeasonId());
//                        mOldGrowerSeedDistributionModel.setCropId(mCropList.get(mCropSpinner.getSelectedItemPosition()).getCropId());
//                        mOldGrowerSeedDistributionModel.setProductionClusterId(mProdClusterList.get(mClusterSpinner.getSelectedItemPosition()).getProductionClusterId());
//
//                        if (!mGrowerRadioBtnSelected) {
//                            mOldGrowerSeedDistributionModel.setOrganizerId(mOrganizerNameList.get(mOrganizerNameSpinner.getSelectedItemPosition()).getUserId());
//                        } else {
//                            mOldGrowerSeedDistributionModel.setOrganizerId(0);
//                        }
//                        mOldGrowerSeedDistributionModel.setGrowerId(mGrowerList.get(mSearchByIdNameSpinner.getSelectedItemPosition()).getUserId());
//                        mOldGrowerSeedDistributionModel.setParentSeedReceiptId(mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId());
//                        mOldGrowerSeedDistributionModel.setCreatedBy(Preferences.get(mContext, Preferences.USER_ID));
//                        mOldGrowerSeedDistributionModel.setFemaleParentSeedBatchId(mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedBatchId());
//
//                        if (mMaleBatchNoSpinner.getVisibility() == View.VISIBLE) {
//
//                            Preferences.saveFloat(mContext, Preferences.MALE_PARENT_SEED_AREA +
//                                            mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()
//                                    , Preferences.getFloat(mContext, Preferences.MALE_PARENT_SEED_AREA +
//                                            mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()) - Float.parseFloat(mAreaEditText.getText().toString()));
//
//                            mOldGrowerSeedDistributionModel.setMaleParentSeedBatchId(mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedBatchId());
//                        } else {
//                            mOldGrowerSeedDistributionModel.setMaleParentSeedBatchId(0);
//
//                        }
//                        mOldGrowerSeedDistributionModel.setIssueDt(mParentSeedIssueDate.getText().toString());
//                        mOldGrowerSeedDistributionModel.setSeedProductionArea(Float.parseFloat(mAreaEditText.getText().toString()));
//
//                        Preferences.saveFloat(mContext, Preferences.FEMALE_PARENT_SEED_AREA +
//                                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()
//                                , Preferences.getFloat(mContext, Preferences.FEMALE_PARENT_SEED_AREA +
//                                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()) - Float.parseFloat(mAreaEditText.getText().toString()));
//                        new AddDataAsyncTask().execute();
//                    } catch (Exception e) {
//                        showToast("Data not found");
//                    }
//                }
                break;
        }
    }

    private long lastClickTime = 0;

    private /*boolean*/void validation() {
        if (selectedGrowerId == 0) {
            showNoInternetDialog(mContext, "Please search grower first by name or id");
            //return false;
        } else if (mPlantingYearSpinner.getSelectedItemPosition() == 0 ||
                mPlantingYearSpinner.getSelectedItemPosition() == -1) {
            showNoInternetDialog(mContext, "Please select planting year");
            // return false;
        }else if (mSeasonSpinner.getSelectedItemPosition() == 0 ||
                mSeasonSpinner.getSelectedItemPosition() == -1) {
            showNoInternetDialog(mContext, "Please select season");
            // return false;
        } else if (mCropSpinner.getSelectedItemPosition() == 0
                || (mCropSpinner.getSelectedItemPosition() == -1)) {
            showNoInternetDialog(mContext, "Please select crop");
            // return false;
        } else if (mClusterSpinner.getSelectedItemPosition() == 0 ||
                mClusterSpinner.getSelectedItemPosition() == -1) {
            showNoInternetDialog(mContext, "Please select production cluster");
            //  return false;
        } else if (TextUtils.isEmpty(mAreaEditText.getText().toString().trim()) ||
                mAreaEditText.getText().toString().trim().equalsIgnoreCase(".")
                /*|| mAreaEditText.getText().toString().trim().equalsIgnoreCase("0.")
                || mAreaEditText.getText().toString().trim().equalsIgnoreCase(".0")
                || mAreaEditText.getText().toString().trim().equalsIgnoreCase(".00")
                || mAreaEditText.getText().toString().trim().equalsIgnoreCase("0.0")*/) {
            showNoInternetDialog(mContext, "Please enter seed production area");
            // return false;
        } else if (Float.parseFloat(mAreaEditText.getText().toString().trim()) <= 0) {
            showNoInternetDialog(mContext, "Please enter seed production area");
        } else if (mAreaEditText.getText().toString().trim().contains(".")
                && (mAreaEditText.getText().toString().trim().indexOf(".") ==
                mAreaEditText.getText().toString().trim().length() - 3) &&
                mAreaEditText.getText().toString().trim().charAt(mAreaEditText.getText().toString().trim().length() - 1) != '0') {
            showNoInternetDialog(mContext, "Enter seed production area in the range of 0.10,0.20,0.30,0.40....1.00");
        } else if (mClusterSpinner.getSelectedItemPosition() == 0 ||
                mClusterSpinner.getSelectedItemPosition() == -1) {
            showNoInternetDialog(mContext, "Please select parent seed issue cluster");
            // return false;
        }
        else if (TextUtils.isEmpty(grower_mobile_no_edittext.getText().toString().trim()) ||
                grower_mobile_no_edittext.getText().toString().trim().length()<5) {
            showNoInternetDialog(mContext, "Please enter Grower Mobile Number");
            // return false;
        }
        else if (TextUtils.isEmpty(mGrowerName.getText().toString().trim()) ||
                mGrowerName.getText().toString().trim().length()<5) {
            showNoInternetDialog(mContext, "Please enter Grower Name");
            // return false;
        }
        else if (TextUtils.isEmpty(mAddressTextView.getText().toString().trim()) ||
                mAddressTextView.getText().toString().trim().length()<5) {
            showNoInternetDialog(mContext, "Please enter Grower Address ");
            // return false;
        }
        else if (TextUtils.isEmpty(mUniqueCode.getText().toString().trim()) ||
                mUniqueCode.getText().toString().trim().length()<5) {
            showNoInternetDialog(mContext, "Please enter Grower National ID");
            // return false;
        }
        else {
            if (SystemClock.elapsedRealtime() - lastClickTime < 5500) {
                return;
            }
            lastClickTime = SystemClock.elapsedRealtime();
            // Log.e("temporary"," after "+ lastClickTime);
            /*14-12-2022 Added by Jeevan ended here*/

                SeedRegistraionLocalModel registraionLocalModel = new SeedRegistraionLocalModel();

                registraionLocalModel.setTempid(selectedGrowerId);
                registraionLocalModel.setGrowerid("" + selectedGrowerId);
                registraionLocalModel.setGrowername(mGrowerName.getText().toString().trim());
                registraionLocalModel.setNationalis(mUniqueCode.getText().toString().trim());
                registraionLocalModel.setAddress(mAddressTextView.getText().toString().trim());
                registraionLocalModel.setPlantingyear(mPlantingYearSpinner.getSelectedItem().toString().trim());
                registraionLocalModel.setSeasonid(mSeasonList.get(mSeasonSpinner.getSelectedItemPosition()).getSeasonId());
                registraionLocalModel.setSeasonnmae(mSeasonList.get(mSeasonSpinner.getSelectedItemPosition()).getSeason());
                registraionLocalModel.setCropid(mCropList.get(mCropSpinner.getSelectedItemPosition()).getCropId());
                registraionLocalModel.setCropname(mCropList.get(mCropSpinner.getSelectedItemPosition()).getCropName());
                registraionLocalModel.setClusterid(mProdClusterList.get(mClusterSpinner.getSelectedItemPosition()).getProductionClusterId());
                registraionLocalModel.setClustername(mProdClusterList.get(mClusterSpinner.getSelectedItemPosition()).getProductionCluster());
                registraionLocalModel.setSeedarea(mAreaEditText.getText().toString().trim());
                registraionLocalModel.setMobile(grower_mobile_no_edittext.getText().toString().trim());
                registraionLocalModel.setIssuedate(mParentSeedIssueDate.getText().toString().trim());
                registraionLocalModel.setCreatedbyid(Preferences.get(mContext, Preferences.USER_ID));
                registraionLocalModel.setCreatedbyname(Preferences.get(mContext, Preferences.USER_NAME));


                createSeedRegistration(registraionLocalModel);

        }
        /*return true;*/
    }

    @Override
    public void setGrowerData(DownloadGrowerModel g) {
        Toast.makeText(mContext, "" + g.getFullName(), Toast.LENGTH_SHORT).show();
        dialog_growerlist.dismiss();
        txt_growersearch.setText("Choose Grower");
        // saasd
        if (g != null) {
            txt_growersearch.setText(g.getUserId()+"-"+g.getFullName());
            mGrowerName.setText(g.getFullName());
            mUniqueCode.setText(g.getUniqueCode());
            mAddressTextView.setText(g.getLandMark() + ", " + g.getCountryName());
            grower_mobile_no_edittext.setText(g.getMobileNo());
            selectedGrowerId = g.getUserId();
        }
    }

    @Override
    public void setNothingSelected() {
             txt_growersearch.setText("Choose Grower");

          //  txt_growersearch.setText("");
            mGrowerName.setText("");
            mUniqueCode.setText("");
            mAddressTextView.setText("");
            grower_mobile_no_edittext.setText("");
            selectedGrowerId = 0;

    }

    private class AddDistributionDataAsyncTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected final Boolean doInBackground(Void... voids) {
            SqlightDatabase database = null;
            boolean b = false;
            try {
                database = new SqlightDatabase(mContext);
                //   Log.e("temporary", "AddDistributionDataAsyncTask called "+
                //     mMaleBatchNoSpinner.getVisibility());
//                if (Preferences.get(mContext, Preferences.DISTRIBUTION_LIST_DOWNLOAD).equalsIgnoreCase("") ||
//                        Preferences.get(mContext, Preferences.DISTRIBUTION_LIST_DOWNLOAD).equalsIgnoreCase("emptyList")) {
                StoreAreaModel storeAreaModel;
                if (mMaleBatchNoSpinner.getVisibility() == View.VISIBLE) {
                    storeAreaModel = new StoreAreaModel(
                            mPlantingYearSpinner.getSelectedItem().toString(),
                            mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionCode(),
                            mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getBatchNo(),
                            mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getBatchNo(),
                            Float.parseFloat(mAreaEditText.getText().toString().trim()),
                            mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedBatchId(),
                            mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedBatchId(),
                            mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId(),
                            mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptType(),
                            mProdClusterList.get(mClusterSpinner.getSelectedItemPosition()).getProductionClusterId(),
                            /*Added by Jeevan 9-12-2022*/
                            mGrowerList.get(mSearchByIdNameSpinner.getSelectedItemPosition()).getUserId()
                            /*Added by Jeevan 9-12-2022 ended here*/);
                } else {
                    storeAreaModel = new StoreAreaModel(
                            mPlantingYearSpinner.getSelectedItem().toString(),
                            mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionCode(),
                            mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getBatchNo(),
                            "",
                            Float.parseFloat(mAreaEditText.getText().toString().trim()),
                            mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedBatchId(),
                            0,
                            mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId(),
                            mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptType(),
                            mProdClusterList.get(mClusterSpinner.getSelectedItemPosition()).getProductionClusterId(),
                            /*Added by Jeevan 9-12-2022*/
                            mGrowerList.get(mSearchByIdNameSpinner.getSelectedItemPosition()).getUserId()
                            /*Added by Jeevan 9-12-2022 ended here*/);
                }
                b = database.addAreaData(storeAreaModel);
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return b;
        }

        @Override
        protected void onPostExecute(Boolean isDataAdded) {
            //  Log.e("temporary", " isDataAdded " + isDataAdded);
            if (isDataAdded) {
                //   Log.e("temporary", "saveData called");
//                Preferences.save(mContext, Preferences.DISTRIBUTION_LIST_DOWNLOAD, "Yes");
                saveData();
            } /*else {
                new GetStoredDataCount().execute();
            }*/
            super.onPostExecute(isDataAdded);
        }
    }

    private class GetFemaleBatchStoredData extends AsyncTask<Void, Void, Float> {
        @Override
        protected final Float doInBackground(Void... voids) {
            SqlightDatabase database = null;
            float b = 0;
            try {
                database = new SqlightDatabase(mContext);
                b = database.getStoreFemaleBatchAreaArea(
                        mPlantingYearSpinner.getSelectedItem().toString(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionCode(),
                        mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getBatchNo(),
                        mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedBatchId()/*,
                        mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedReceiptId(),
                        mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedReceiptType(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionClusterId()*/);
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return b;
        }

        @Override
        protected void onPostExecute(Float sum) {
//            Log.e("temporary", "Female Local SeedArea sum " + sum +
//                    " area " + mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getSeedArea()+
//                    " entered area "+ Float.parseFloat(mAreaEditText.getText().toString().trim()));
            storedAra = sum + Float.parseFloat(mAreaEditText.getText().toString().trim());
            // Log.e("temporary", "Female Local SeedArea after sum " + storedAra);
            storedAra = Float.parseFloat(String.format("%.2f", storedAra));
            // Log.e("temporary", "Female after two digit SeedArea after sum " + storedAra);
            if (storedAra <= mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getSeedArea()) {
                if (mMaleBatchNoSpinner.getVisibility() == View.VISIBLE) {
                    storedAra = 0.0f;
                    new MaleBatchStoredData().execute();
                } else {
                    new AddDistributionDataAsyncTask().execute();
                }
            } else {
                showNoInternetDialog(mContext, "Distributed female batch no. " + mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getBatchNo() + " is " + sum + " Hac. " +
                        " Balance available for distribution is only " + (String.format("%.2f", (mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getSeedArea() - sum))) + " Hac.");
            }
            super.onPostExecute(storedAra);
        }
    }

    private class MaleBatchStoredData extends AsyncTask<Void, Void, Float> {
        @Override
        protected final Float doInBackground(Void... voids) {
            SqlightDatabase database = null;
            float b = 0;
            try {
                database = new SqlightDatabase(mContext);
                b = database.getStoreMaleBatchAreaArea(
                        mPlantingYearSpinner.getSelectedItem().toString(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionCode(),
                        mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getBatchNo(),
                        mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedBatchId()/*,
                        mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedReceiptId(),
                        mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedReceiptType(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionClusterId()*/);
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return b;
        }

        @Override
        protected void onPostExecute(Float sum) {
//            Log.e("temporary", "Male Local SeedArea sum " + sum +
//                    " area " + mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getSeedArea());
            storedAra = sum + Float.parseFloat(mAreaEditText.getText().toString().trim());
            //  Log.e("temporary", "Male Local SeedArea after sum " + storedAra);
            storedAra = Float.parseFloat(String.format("%.2f", storedAra));
            //  Log.e("temporary", "Male after two digit SeedArea after sum " + storedAra);
            if (storedAra <= mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getSeedArea()) {
                new AddDistributionDataAsyncTask().execute();
            } else {
                showNoInternetDialog(mContext, "Distributed male batch no. " + mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getBatchNo() + " is " + sum + " Hac. " +
                        " Balance available for distribution is only " + (String.format("%.2f", (mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getSeedArea() - sum))) + " Hac.");
            }
            super.onPostExecute(storedAra);
        }
    }

    private float storedAra = 0.0f;

    private class GetStoredDataCount extends AsyncTask<Void, Void, Integer> {
        @Override
        protected final Integer doInBackground(Void... voids) {
            SqlightDatabase database = null;
            int b;
            try {
                //   Log.e("temporary", "GetStoredDataCount called");
                database = new SqlightDatabase(mContext);
                b = database.gteStoredDataCount(
                        mPlantingYearSpinner.getSelectedItem().toString(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionCode(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptType(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionClusterId());
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return b;
        }

        @Override
        protected void onPostExecute(Integer unused) {
            // Log.e("temporary", "onPostExecute GetStoredDataCount " + unused);
            if (unused != 0) {
                //    Log.e("temporary", "onPostExecute GetStoredDataCount GetStoredData called");
                new GetFemaleBatchStoredData().execute();
            } else {
                Preferences.save(mContext, Preferences.DISTRIBUTION_LIST_DOWNLOAD, "");
                new AddDistributionDataAsyncTask().execute();
            }
            super.onPostExecute(unused);
        }
    }

    /*private class CheckTotalSeedAreaDatabaseAsyncTask extends AsyncTask<Void, Void, Float> {
        @Override
        protected final Float doInBackground(Void... voids) {
            SqlightDatabase database = null;
            float b;
            try {
                database = new SqlightDatabase(mContext);
                b = database.totalOfSeedDistribution(
                        mPlantingYearSpinner.getSelectedItem().toString(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionCode(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptType(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionClusterId());
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return b;
        }

        @Override
        protected void onPostExecute(Float sum) {
            Log.e("temporary", "server check sum " + sum +
                    " area " + mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getFemaleParentSeedsArea());
            float temp = sum;
            sum += Float.parseFloat(mAreaEditText.getText().toString().trim());
            Log.e("temporary", "server after sum " + sum);
            if (sum <= mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getFemaleParentSeedsArea()) {
                new updateArea().execute();
            } else {
                showNoInternetDialog(mContext, "server All ready distributed seed area is " + temp + " entered area " +
                        Float.parseFloat(mAreaEditText.getText().toString().trim()) + " = " + sum + " available seed area " + mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getFemaleParentSeedsArea());
            }
            super.onPostExecute(sum);
        }
    }*/


    private class updateArea extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected final Boolean doInBackground(Void... voids) {
            SqlightDatabase database = null;
            boolean b = false;
            try {
                //   Log.e("temporary", "updateArea area storedAra " + storedAra);
                database = new SqlightDatabase(mContext);
                b = database.updateAreaData(
                        mPlantingYearSpinner.getSelectedItem().toString(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionCode(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptType(),
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionClusterId(),
                        storedAra);
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return b;
        }

        @Override
        protected void onPostExecute(Boolean unused) {
            //  Log.e("temporary", "updateArea area unused " + unused);
            if (unused) {
                saveData();
            } else {
                showToast("Area update error");
            }
            super.onPostExecute(unused);
        }
    }

    private class CheckWithLocalDatabaseAsyncTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected final Boolean doInBackground(Void... voids) {
            SqlightDatabase database = null;
            boolean b = false;
            try {
                database = new SqlightDatabase(mContext);
                b = database.isSeedDistributionRegister(mGrowerList.get(mSearchByIdNameSpinner.getSelectedItemPosition()).getUserId(),
                        mPlantingYearSpinner.getSelectedItem().toString());
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return b;
        }

        @Override
        protected void onPostExecute(Boolean unused) {
            if (unused) {
                Dialog mDialog = null;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setCancelable(false);
                alertDialog.setTitle("MSCOPE");
                alertDialog.setMessage("All Ready Distributed To This Grower");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mDialog = alertDialog.create();
                mDialog.show();
            } else {
                new CheckWithServerDatabaseAsyncTask().execute();
            }
            super.onPostExecute(unused);
        }
    }

    private class CheckWithServerDatabaseAsyncTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected final Boolean doInBackground(Void... voids) {
            SqlightDatabase database = null;
            boolean b = false;
            try {
                database = new SqlightDatabase(mContext);
                b = database.isSeedDistributionListDownloaded(mGrowerList.get(mSearchByIdNameSpinner.getSelectedItemPosition()).getUserId(),
                        mPlantingYearSpinner.getSelectedItem().toString());
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return b;
        }

        @Override
        protected void onPostExecute(Boolean unused) {
            if (unused) {
                Dialog mDialog = null;
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setCancelable(false);
                alertDialog.setTitle("MSCOPE");
                alertDialog.setMessage("All Ready Distributed To This Grower");
                alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mDialog = alertDialog.create();
                mDialog.show();
            }/*Commented by jeevan 9-12-2022*/ /*else {
                saveData();
            }*//*Commented by jeevan 9-12-2022 ended here*/
            super.onPostExecute(unused);
        }
    }

    private void saveData() {
        try {
            //   Log.e("temporary", " selection value " + mGrowerList.get(mSearchByIdNameSpinner.getSelectedItemPosition()).getUniqueId());
            mOldGrowerSeedDistributionModel.setCountryId(Integer.parseInt(Preferences.get(mContext, Preferences.COUNTRYCODE)));
            mOldGrowerSeedDistributionModel.setPlantingYear(mPlantingYearSpinner.getSelectedItem().toString());
            mOldGrowerSeedDistributionModel.setSeasonId(mSeasonList.get(mSeasonSpinner.getSelectedItemPosition()).getSeasonId());
            mOldGrowerSeedDistributionModel.setCropId(mCropList.get(mCropSpinner.getSelectedItemPosition()).getCropId());
            mOldGrowerSeedDistributionModel.setProductionClusterId(mProdClusterList.get(mClusterSpinner.getSelectedItemPosition()).getProductionClusterId());

            if (!mGrowerRadioBtnSelected) {
                mOldGrowerSeedDistributionModel.setOrganizerId(mOrganizerNameList.get(mOrganizerNameSpinner.getSelectedItemPosition()).getUserId());
            } else {
                mOldGrowerSeedDistributionModel.setOrganizerId(0);
            }
            mOldGrowerSeedDistributionModel.setGrowerId(mGrowerList.get(mSearchByIdNameSpinner.getSelectedItemPosition()).getUserId());
            mOldGrowerSeedDistributionModel.setParentSeedReceiptId(mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId());
            mOldGrowerSeedDistributionModel.setCreatedBy(Preferences.get(mContext, Preferences.USER_ID));
            mOldGrowerSeedDistributionModel.setFemaleParentSeedBatchId(mFemaleBatchNoList.get(mFemaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedBatchId());

            if (mMaleBatchNoSpinner.getVisibility() == View.VISIBLE) {

//                Preferences.saveFloat(mContext, Preferences.MALE_PARENT_SEED_AREA +
//                                mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()
//                        , Preferences.getFloat(mContext, Preferences.MALE_PARENT_SEED_AREA +
//                                mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()) - Float.parseFloat(mAreaEditText.getText().toString()));

                mOldGrowerSeedDistributionModel.setMaleParentSeedBatchId(mMaleBatchNoList.get(mMaleBatchNoSpinner.getSelectedItemPosition()).getParentSeedBatchId());
            } else {
                mOldGrowerSeedDistributionModel.setMaleParentSeedBatchId(0);

            }

//            Log.e("temporary","curent date send to server "+
//                    getCurrentDateToStoreInDb());
            mOldGrowerSeedDistributionModel.setIssueDt(/*mParentSeedIssueDate.getText().toString()*/getCurrentDateToStoreInDb());
            mOldGrowerSeedDistributionModel.setSeedProductionArea(Float.parseFloat(mAreaEditText.getText().toString()));

//            Preferences.saveFloat(mContext, Preferences.FEMALE_PARENT_SEED_AREA +
//                            mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()
//                    , Preferences.getFloat(mContext, Preferences.FEMALE_PARENT_SEED_AREA +
//                            mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()) - Float.parseFloat(mAreaEditText.getText().toString()));

            new AddDataAsyncTask().execute();
        } catch (Exception e) {
            showToast("Data not found");
        }
    }

    private class AddDataAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected final Void doInBackground(Void... voids) {
            SqlightDatabase database = null;
            try {
                database = new SqlightDatabase(mContext);
                database.parentSeedDistribution(mOldGrowerSeedDistributionModel);
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            /*Dialog mDialog = null;
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("MIPL");
            alertDialog.setMessage("Seed distribution data stored successfully");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            mDialog = alertDialog.create();
            mDialog.show();*/
            Preferences.saveBool(mContext, Preferences.UPLOAD_DISTRIBUTION_DATA_AVAILABLE, true);
            Dialog mDialog = null;
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("MSCOPE");
            alertDialog.setMessage("Seed distribution data stored successfully");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            mDialog = alertDialog.create();
            mDialog.show();
            //  new UpdateParentSeedStockAsyncTask().execute();
            super.onPostExecute(unused);
        }
    }

    private class UpdateParentSeedStockAsyncTask extends AsyncTask<Void, Void, Boolean> {
        float maleParentSeedArea = 0;

        @Override
        protected final Boolean doInBackground(Void... voids) {
            SqlightDatabase database = null;
            boolean b = false;
            try {
               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {*/
//                        Log.e("temporary", " area " + mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getMaleParentSeedArea());
                if (mMaleBatchNoSpinner.getVisibility() == View.VISIBLE) {
                    maleParentSeedArea = mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getMaleParentSeedArea()
                            - Float.parseFloat(mAreaEditText.getText().toString().trim());

//                            Log.e("temporary", "if  maleParentSeedArea " + maleParentSeedArea
//                            +" Float.parseFloat(mAreaEditText.getText().toString().trim() " +Float.parseFloat(mAreaEditText.getText().toString().trim()));
                } else {
                    maleParentSeedArea = mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getMaleParentSeedArea();
//                            Log.e("temporary", "else  maleParentSeedArea " + maleParentSeedArea
//                                    +" mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getMaleParentSeedArea() "+ mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getMaleParentSeedArea());
                }
                  /*  }
                });*/
                database = new SqlightDatabase(mContext);
//                Log.e("temporary","after if else maleParentSeedArea "+ maleParentSeedArea);
                b = database.updateSeedParentArea(
                        mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId(),
                        (mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getFemaleParentSeedsArea()
                                - Float.parseFloat(mAreaEditText.getText().toString())), maleParentSeedArea);
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean unused) {
            Dialog mDialog = null;
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
            alertDialog.setCancelable(false);
            alertDialog.setTitle("MSCOPE");
            alertDialog.setMessage("Seed distribution data stored successfully");
            alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            mDialog = alertDialog.create();
            mDialog.show();
            super.onPostExecute(unused);
        }
    }

    private void visibleScannerLayout() {
        mCodeScannerView.setVisibility(View.VISIBLE);
        mSubmitButton.setVisibility(View.GONE);
        mScrollView.setVisibility(View.GONE);
    }

    private void hideScannerLayout() {
        mCodeScannerView.setVisibility(View.GONE);
        mSubmitButton.setVisibility(View.VISIBLE);
        mScrollView.setVisibility(View.VISIBLE);
    }

    class DecimalDigitsInputFilters implements InputFilter {

        private int mDigitsBeforeZero;
        private int mDigitsAfterZero;
        private Pattern mPattern;

        private static final int DIGITS_BEFORE_ZERO_DEFAULT = 100;
        private static final int DIGITS_AFTER_ZERO_DEFAULT = 100;

        public DecimalDigitsInputFilters(Integer digitsBeforeZero, Integer digitsAfterZero) {
            this.mDigitsBeforeZero = (digitsBeforeZero != null ? digitsBeforeZero : DIGITS_BEFORE_ZERO_DEFAULT);
            this.mDigitsAfterZero = (digitsAfterZero != null ? digitsAfterZero : DIGITS_AFTER_ZERO_DEFAULT);
            mPattern = Pattern.compile("-?[0-9]{0," + (mDigitsBeforeZero) + "}+((\\.[0-9]{0," + (mDigitsAfterZero)
                    + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String replacement = source.subSequence(start, end).toString();
            String newVal = dest.subSequence(0, dstart) + replacement
                    + dest.subSequence(dend, dest.length());

            Matcher matcher = mPattern.matcher(newVal);

            if (matcher.matches())
                return null;

            if (TextUtils.isEmpty(source))
                return dest.subSequence(dstart, dend);
            else
                return "";
        }
    }

    private class GetGrowerMasterAsyncTask extends AsyncTask<Void, Void, ArrayList<DownloadGrowerModel>> {

        @Override
        protected void onPreExecute() {
            showProgressDialog(mContext);
            super.onPreExecute();
        }

        @Override
        protected final ArrayList<DownloadGrowerModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<DownloadGrowerModel> actionModels;
            try {
                database = new SqlightDatabase(mContext);
                actionModels = database.getDownloadedGrowerMaster();
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return actionModels;
        }

        @Override
        protected void onPostExecute(ArrayList<DownloadGrowerModel> result) {
            hideProgressDialog();
            if (mGrowerList != null) {
                mGrowerList.clear();
            }
            if (mOrganizerNameList != null) {
                mOrganizerNameList.clear();
            }

            if (result != null && result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getUserType().equalsIgnoreCase("Organizer")) {
                        mOrganizerNameList.add(result.get(i));
                    } else {
                        mGrowerList.add(result.get(i));
                    }
                }
                if (mGrowerList.size() > 0) {
                    // GrowerAdapter adapter = new GrowerAdapter(mContext, R.layout.spinner_rows, mGrowerList);
                    ArrayAdapter<DownloadGrowerModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows, mGrowerList);
                    mSearchByIdNameSpinner.setAdapter(adapter);
                }

                DownloadGrowerModel downloadGrowerModel = new DownloadGrowerModel();
                downloadGrowerModel.setFullName("Select");
                downloadGrowerModel.setUniqueCode("");
                mOrganizerNameList.add(0, downloadGrowerModel);

                if (mOrganizerNameList.size() > 0) {
                    // OrganizerAdapter adapter1 = new OrganizerAdapter(mContext, R.layout.spinner_rows, mOrganizerNameList);
                    ArrayAdapter<DownloadGrowerModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows, mOrganizerNameList);
                    mOrganizerNameSpinner.setAdapter(adapter);
                }
                super.onPostExecute(result);
            }
            new GetSeasonMasterAsyncTask().execute();
        }
    }

    private class GetSeasonMasterAsyncTask extends AsyncTask<Void, Void, ArrayList<SeasonModel>> {

        @Override
        protected void onPreExecute() {
            showProgressDialog(mContext);
            super.onPreExecute();
        }

        @Override
        protected final ArrayList<SeasonModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<SeasonModel> actionModels;
            try {
                database = new SqlightDatabase(mContext);
                actionModels = database.getSeasonMaster();
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return actionModels;
        }

        @Override
        protected void onPostExecute(ArrayList<SeasonModel> result) {
            hideProgressDialog();
            if (mSeasonList != null) {
                mSeasonList.clear();
            }
            if (result != null && result.size() > 0) {
                mSeasonList = result;
                // SeasonAdapter adapter = new SeasonAdapter(mContext, R.layout.spinner_rows, mSeasonList);
                ArrayAdapter<SeasonModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows, mSeasonList);
                mSeasonSpinner.setAdapter(adapter);
                super.onPostExecute(result);
            }

            new GetCropMasterAsyncTask().execute();
        }
    }

    private class GetCropMasterAsyncTask extends AsyncTask<Void, Void, ArrayList<CropModel>> {

        @Override
        protected void onPreExecute() {
//            Log.e("temporary", "GetCropMasterAsyncTask onPreExecute called");
            showProgressDialog(mContext);
            super.onPreExecute();
        }

        @Override
        protected final ArrayList<CropModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<CropModel> actionModels;
            try {
                database = new SqlightDatabase(mContext);
                actionModels = database.getCropMaster();
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return actionModels;
        }

        @Override
        protected void onPostExecute(ArrayList<CropModel> result) {
            hideProgressDialog();
            if (mCropList != null) {
                mCropList.clear();
            }
            if (result != null && result.size() > 0) {
                mCropList = result;
                mFirstTimeSelectedCrop = false;
                // CropAdapter adapter = new CropAdapter(mContext, R.layout.spinner_rows, mCropList);
                ArrayAdapter<CropModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows, mCropList);
                mCropSpinner.setAdapter(adapter);
                new GetClusterMasterAsyncTask().execute();
//                new GetProductionCodeMasterAsyncTask().execute();
                super.onPostExecute(result);
                // new GetCodeMasterAsyncTask().execute();
            }
        }
    }

/*
    private class GetCodeMasterAsyncTask extends AsyncTask<Void, Void, ArrayList<ProductCodeModel>> {

        @Override
        protected void onPreExecute() {
           // Log.e("temporary", "GetCodeMasterAsyncTask onPreExecute called");
            showProgressDialog(mContext);
            super.onPreExecute();
        }

        @Override
        protected final ArrayList<ProductCodeModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<ProductCodeModel> actionModels;
            try {
                database = new SqlightDatabase(mContext);
             //   Log.e("temporary", "asynctask crop code " + mCropList.get(mCropSpinner.getSelectedItemPosition()).getCropCode());
                actionModels = database.getProdCodeMaster(mCropList.get(mCropSpinner.getSelectedItemPosition()).getCropCode());
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return actionModels;
        }

        @Override
        protected void onPostExecute(ArrayList<ProductCodeModel> result) {
            hideProgressDialog();
            if (mProdCodeList != null) {
                mProdCodeList.clear();
            }
            if (result != null && result.size() > 0) {
                //  Log.e("temporary", "result " + result.size());
                mProdCodeList = result;
                ProductCodeAdapter adapter = new ProductCodeAdapter(mContext, R.layout.spinner_rows, mProdCodeList);
                mProductionCodeSpinner.setAdapter(adapter);
                super.onPostExecute(result);

                // new GetBatchNoMasterAsyncTask().execute();
                new GetSeedReceiptMasterAsyncTask().execute();
            } else {
                mMaleBatchNoSpinner.setAdapter(null);
                mFemaleBatchNoSpinner.setAdapter(null);
            //    Log.e("temporary", "Product master onPostExecute " + result.size());
            }
        }
    }
*/


    private class GetClusterMasterAsyncTask extends AsyncTask<Void, Void, ArrayList<ProductionClusterModel>> {

        @Override
        protected void onPreExecute() {
            //  Log.e("temporary", "GetClusterMasterAsyncTask onPreExecute called");
            showProgressDialog(mContext);
            super.onPreExecute();
        }

        @Override
        protected final ArrayList<ProductionClusterModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<ProductionClusterModel> actionModels;
            try {
                database = new SqlightDatabase(mContext);
                actionModels = database.getProdClusterMaster();
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return actionModels;
        }

        @Override
        protected void onPostExecute(ArrayList<ProductionClusterModel> result) {
            hideProgressDialog();
            if (mProdClusterList != null) {
                mProdClusterList.clear();
            }
            if (result != null && result.size() > 0) {
                mProdClusterList = result;
                mClusterSpinnerFirstTimeCalled = false;
                // ProductionClusterAdapter adapter = new ProductionClusterAdapter(mContext, R.layout.spinner_rows, mProdClusterList);
                ArrayAdapter<ProductionClusterModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        mProdClusterList);
                new GetProductionCodeMasterAsyncTask().execute();
                mClusterSpinner.setAdapter(adapter);
                super.onPostExecute(result);
            }
        }
    }

    private class GetProductionCodeMasterAsyncTask extends AsyncTask<Void, Void, ArrayList<SeedReceiptModel>> {

        @Override
        protected void onPreExecute() {
            //  Log.e("temporary", "GetSeedReceiptMasterAsyncTask onPreExecute called");
            showProgressDialog(mContext);
            super.onPreExecute();
        }

        @Override
        protected final ArrayList<SeedReceiptModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<SeedReceiptModel> actionModels;
            try {
                database = new SqlightDatabase(mContext);
                //  Log.e("temporary", "mProdCodeList crop code " + mProdCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getCropCode());
                actionModels = database.getSeedReceiptMaster();
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return actionModels;
        }

        @Override
        protected void onPostExecute(ArrayList<SeedReceiptModel> result) {
            hideProgressDialog();
            if (mSeedProductionCodeList != null) {
                mSeedProductionCodeList.clear();
            }
//              Log.e("temporary", " onPostExecute seed result " + result);
            if (result != null && result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
//                    Log.e("temporary", "year " + result.get(i).getPlantingYear()
//                            + " year " + mPlantingYearSpinner.getSelectedItem().toString() +
//                            "\n CropName() " + result.get(i).getCropName() + " crop name " + mCropList.get(mCropSpinner.getSelectedItemPosition()).getCropName()
//                    +" mGrowerRadioBtnSelected " + mGrowerRadioBtnSelected);
                    if (result.get(i).getPlantingYear().equalsIgnoreCase(mPlantingYearSpinner.getSelectedItem().toString()) &&
                            result.get(i).getCropName().equalsIgnoreCase(mCropList.get(mCropSpinner.getSelectedItemPosition()).getCropName())) {
                        if (mGrowerRadioBtnSelected) {
//                             Log.e("temporary", "id " + result.get(i).getProductionClusterId()
//                             + " id "+ mProdClusterList.get(mClusterSpinner.getSelectedItemPosition()).getProductionClusterId());
                            if (result.get(i).getParentSeedReceiptType().equalsIgnoreCase("Production Cluster Level")
                                    && result.get(i).getProductionClusterId() == mProdClusterList.get(mClusterSpinner.getSelectedItemPosition()).getProductionClusterId()) {
                                //  Log.e("temporary", "ProductionCode Production Cluster level " + result.get(i).getParentSeedReceiptId());
                                mSeedProductionCodeList.add(result.get(i));
                            }
                        } else {
//                            Log.e("temporary"," mOrganizerNameSpinner.getSelectedItemPosition() "+
//                                   " mOrganizerNameSpinner.getSelectedItemPosition()");
                            if (mOrganizerNameSpinner.getSelectedItemPosition() != -1 ||
                                    mOrganizerNameSpinner.getSelectedItemPosition() != 0) {
//                                Log.e("temporary","result.get(i).getUserId() "+
//                                        result.get(i).getUserId() +" selected id "+
//                                        mOrganizerNameList.get(mOrganizerNameSpinner.getSelectedItemPosition()).getUserId()
//                                +" cluster id "+ result.get(i).getProductionClusterId()+" selected cluster id " +
//                                        mProdClusterList.get(mClusterSpinner.getSelectedItemPosition()).getProductionClusterId());
                                if (result.get(i).getParentSeedReceiptType().equalsIgnoreCase("Production Organizer Level")
                                        && result.get(i).getUserId() == mOrganizerNameList.get(mOrganizerNameSpinner.getSelectedItemPosition()).getUserId()
                                        && result.get(i).getProductionClusterId() == mProdClusterList.get(mClusterSpinner.getSelectedItemPosition()).getProductionClusterId()) {
                                    //  Log.e("temporary", "ProductionCode Production Organizer Level " + result.get(i).getParentSeedReceiptId());
                                    mSeedProductionCodeList.add(result.get(i));
                                }
                            }
                        }
                    }
                }
                SeedReceiptModel seedReceiptModel = new SeedReceiptModel();
                seedReceiptModel.setProductionCode("Select");
                seedReceiptModel.setParentSeedReceiptId(0);
                mSeedProductionCodeList.add(0, seedReceiptModel);
                mProductSpinnerFirstTimeSelected = false;
                // ProductCodeAdapter adapter = new ProductCodeAdapter(mContext, R.layout.spinner_rows, mSeedProductionCodeList);
                ArrayAdapter<SeedReceiptModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        mSeedProductionCodeList);
                mProductionCodeSpinner.setAdapter(adapter);

                if (mMaleBatchNoList != null) {
                    mMaleBatchNoList.clear();
                }
                if (mFemaleBatchNoList != null) {
                    mFemaleBatchNoList.clear();
                }

                SeedBatchNoModel seedBatchModel = new SeedBatchNoModel();
                seedBatchModel.setBatchNo("Select");
                seedBatchModel.setParentSeedBatchId(0);
                mMaleBatchNoList.add(0, seedBatchModel);
                mFemaleBatchNoList.add(0, seedBatchModel);

                ArrayAdapter<SeedBatchNoModel> adapter1 = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        mMaleBatchNoList);
                mMaleBatchNoSpinner.setAdapter(adapter1);

                ArrayAdapter<SeedBatchNoModel> adapter2 = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        mFemaleBatchNoList);
                mFemaleBatchNoSpinner.setAdapter(adapter2);

                /*  if (mSeedProductionCodeList.size() > 0) {*/
                  /*  if (mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getCropType().equalsIgnoreCase("Hybrid")) {
                        mMaleBatchNoTextView.setVisibility(View.VISIBLE);
                        mMaleBatchNoSpinner.setVisibility(View.VISIBLE);
                    } else {
                        mMaleBatchNoTextView.setVisibility(View.GONE);
                        mMaleBatchNoSpinner.setVisibility(View.GONE);
                    }
                    mFemaleBatchNoSpinner.setVisibility(View.VISIBLE);
                    new GetBatchNoMasterAsyncTask().execute();*/
                /*} else {
                    mMaleBatchNoSpinner.setAdapter(null);
                    mFemaleBatchNoSpinner.setAdapter(null);
                }*/
            }
        }
    }

    private class GetBatchNoMasterAsyncTask extends AsyncTask<Void, Void, ArrayList<SeedBatchNoModel>> {
        @Override
        protected void onPreExecute() {
//          Log.e("temporary", "GetBatchNoMasterAsyncTask onPreExecute called");
            showProgressDialog(mContext);
            super.onPreExecute();
        }

        @Override
        protected final ArrayList<SeedBatchNoModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<SeedBatchNoModel> actionModels;
            try {
                database = new SqlightDatabase(mContext);
                //   Log.e("temporary", "mProdCodeList crop code " + mSeedReceiptList.get(0).getProductionCode());
                //   Log.e("temporary", "mProductionCodeSpinner.getSelectedItemPosition() " + mProductionCodeSpinner.getSelectedItemPosition());
                actionModels = database.getSeedBatchNo(mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getProductionCode());
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return actionModels;
        }

        @Override
        protected void onPostExecute(ArrayList<SeedBatchNoModel> result) {
            hideProgressDialog();
            if (mMaleBatchNoList != null) {
                mMaleBatchNoList.clear();
            }
            if (mFemaleBatchNoList != null) {
                mFemaleBatchNoList.clear();
            }
            //  Log.e("temporary", "result " + result);
            if (result != null && result.size() > 0) {
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getParentType().equalsIgnoreCase("Female Batch No")) {
                        if (mGrowerRadioBtnSelected) {
                            if (result.get(i).getParentSeedReceiptType().equalsIgnoreCase("Production Cluster Level")
                                    && result.get(i).getParentSeedReceiptId() == mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()) {
                                mFemaleBatchNoList.add(result.get(i));
                                //   Log.e("temporary", "Production Cluster level Female Batch  " + result.get(i).getParentSeedBatchId());
                            }
                        } else {
                            if (result.get(i).getParentSeedReceiptType().equalsIgnoreCase("Production Organizer Level")
                                    && result.get(i).getParentSeedReceiptId() == mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()) {
                                mFemaleBatchNoList.add(result.get(i));
                                //  Log.e("temporary", "Production Organizer level Female Batch  " + result.get(i).getParentSeedBatchId());
                            }
                        }
                    } else {
                        if (mGrowerRadioBtnSelected) {
                            if (result.get(i).getParentSeedReceiptType().equalsIgnoreCase("Production Cluster Level")
                                    && result.get(i).getParentSeedReceiptId() == mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()) {
                                mMaleBatchNoList.add(result.get(i));
                                //   Log.e("temporary", "Production Cluster level Male Batch  " + result.get(i).getParentSeedBatchId());
                            }
                        } else {
                            if (result.get(i).getParentSeedReceiptType().equalsIgnoreCase("Production Organizer Level")
                                    && result.get(i).getParentSeedReceiptId() == mSeedProductionCodeList.get(mProductionCodeSpinner.getSelectedItemPosition()).getParentSeedReceiptId()) {
                                mMaleBatchNoList.add(result.get(i));
                                //   Log.e("temporary", "Production Organizer level  Male Batch  " + result.get(i).getParentSeedBatchId());
                            }
                        }
                    }
                }
                //SeedBatchNoMaleAdapter adapter = new SeedBatchNoMaleAdapter(mContext, R.layout.spinner_rows, mMaleBatchNoList);

                SeedBatchNoModel seedBatchModel = new SeedBatchNoModel();
                seedBatchModel.setBatchNo("Select");
                seedBatchModel.setParentSeedBatchId(0);
                mMaleBatchNoList.add(0, seedBatchModel);
                mFemaleBatchNoList.add(0, seedBatchModel);

                ArrayAdapter<SeedBatchNoModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        mMaleBatchNoList);
                mMaleBatchNoSpinner.setAdapter(adapter);

                //SeedBatchNoFemaleAdapter adapter1 = new SeedBatchNoFemaleAdapter(mContext, R.layout.spinner_rows, mFemaleBatchNoList);

                ArrayAdapter<SeedBatchNoModel> adapter1 = new ArrayAdapter<>(mContext, R.layout.spinner_rows,
                        mFemaleBatchNoList);
                mFemaleBatchNoSpinner.setAdapter(adapter1);
                super.onPostExecute(result);
            }
        }
    }

    /*private class GetCropTypeMasterAsyncTask extends AsyncTask<Void, Void, ArrayList<CropTypeModel>> {

        @Override
        protected void onPreExecute() {
          //  Log.e("temporary", "GetCropTypeMasterAsyncTask onPreExecute called");
            showProgressDialog(mContext);
            super.onPreExecute();
        }

        @Override
        protected final ArrayList<CropTypeModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<CropTypeModel> actionModels;
            try {
                database = new SqlightDatabase(mContext);
                actionModels = database.getCropType();
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return actionModels;
        }

        @Override
        protected void onPostExecute(ArrayList<CropTypeModel> result) {
            hideProgressDialog();
           // Log.e("temporary", " result " + result);
            if (mCropTypeList != null) {
                mCropTypeList.clear();
            }
            if (result != null && result.size() > 0) {
             //   Log.e("temporary", " result.size() " + result.size());
                mCropTypeList = result;
                CropTypeAdapter adapter = new CropTypeAdapter(mContext, R.layout.spinner_rows, mCropTypeList);
                mCropTypeSpinner.setAdapter(adapter);
                super.onPostExecute(result);
            }
        }
    }*/


    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (mCodeScannerView.getVisibility() == View.VISIBLE) {
            mCodeScanner.releaseResources();
            hideScannerLayout();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        hideProgressDialog();
        hideKeyboard(mContext);
        dismissNoInternetDialog();
        super.onDestroy();
    }


    public void createSeedRegistration(SeedRegistraionLocalModel registraionLocalModel) {
        String data = new SqlightDatabase(mContext).AddSeedRegistration(registraionLocalModel);
        if (data.equals("true")) {
            Toast.makeText(mContext, "Data Saved", Toast.LENGTH_SHORT).show();
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(mContext)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setMessage("Seed registration successful.")
                    .show();
        } else {
            // Toast.makeText(mContext, "Data not sa", Toast.LENGTH_SHORT).show();
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(mContext)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setTitle("Something went wrong")
                    .setMessage("Message:\n" + data)
                    .show();
        }
    }

    Dialog dialog_growerlist;
    LinearLayoutManager mManager;
    SeedProductionGrowerAdapter visitGrowerAdapter;

    public void show_grower_list() {
        try {
            mManager = new LinearLayoutManager(mContext);
            dialog_growerlist = new Dialog(mContext, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            dialog_growerlist.setContentView(R.layout.growerlist_dialog);
            RecyclerView rc_list = dialog_growerlist.findViewById(R.id.rc_list);
            EditText et_search = dialog_growerlist.findViewById(R.id.et_search);
            sp_focusvillage = dialog_growerlist.findViewById(R.id.sp_focusvillage);
            ImageView imageButton3 = dialog_growerlist.findViewById(R.id.imageButton3);
            imageButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog_growerlist.dismiss();
                }
            });

            try{
                mSpinnerFocussVillageList=new SqlightDatabase(mContext).getAllFocusVillage();
//                VillageModel vv=new VillageModel();
//                vv.setVillage("Select");

                VillageModel vv1=new VillageModel();
                vv1.setVillage("All Growers");
                vv1.setVillageCode("0");


                VillageModel vv2=new VillageModel();
                vv2.setVillage("All Focused Village Growers");
                vv2.setVillageCode("-1");

            //    mSpinnerFocussVillageList.add(0,vv);
                mSpinnerFocussVillageList.add(0,vv1);
                mSpinnerFocussVillageList.add(1,vv2);
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

                sp_focusvillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try{
                            VillageModel villageModel=(VillageModel) parent.getSelectedItem();
                            mGrowerList=new ArrayList<DownloadGrowerModel>();

                            if (villageModel.getVillage() != null &&  !villageModel.getVillage().contains("Select")) {
                                Log.i("Selected Type ",villageModel.getVillageCode());
                                if(villageModel.getVillageCode().trim().equals("0"))
                                {
                                    // this is for all
                                    mGrowerList=getGrowerData(0,"All");
                                    Toast.makeText(mContext, "All Growers ", Toast.LENGTH_SHORT).show();
                                }
                                else if(villageModel.getVillageCode().trim().equals("-1"))
                                {
                                    mGrowerList=getGrowerData(1,"allfocussed");
                                    // this is for all focussed villages
                                    Toast.makeText(mContext, "All Focused Growers ", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(mContext, "Specific Village Growers ", Toast.LENGTH_SHORT).show();
                                    mGrowerList=getGrowerData(2,villageModel.getVillage().trim());
                                    // this for selected focussed village
                                  //  Toast.makeText(mContext, ""+getGrowerData(2,villageModel.getVillage().trim()), Toast.LENGTH_SHORT).show();
                                }
                                
                                if(mGrowerList!=null) {
                                    Toast.makeText(mContext, "Total Growers" + mGrowerList.size(), Toast.LENGTH_SHORT).show();

                                    rc_list.setAdapter(null);
                                   // rc_list.notify();
                                    visitGrowerAdapter = new SeedProductionGrowerAdapter((ArrayList) mGrowerList, mContext, ProductionCreateGrowerListActivity.this);
                                    rc_list.setAdapter(visitGrowerAdapter);
                                    //Toast.makeText(mContext, "Selected Village is " + villageModel.getVillage(), Toast.LENGTH_SHORT).show();
                                }else
                                {
                                    Toast.makeText(mContext, "List is null", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }catch (Exception e)
                        {
                            Log.i("Error is # ",e.getMessage());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }catch (Exception e)
            {

            }
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
                if(mGrowerList.size()==0)
                {
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(mContext)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setTitle("Something went wrong")
                            .setMessage("")
                            .show();
                }
                Toast.makeText(mContext, "Total grower" + mGrowerList.size(), Toast.LENGTH_SHORT).show();
                visitGrowerAdapter = new SeedProductionGrowerAdapter((ArrayList) mGrowerList, mContext, this);
                rc_list.setAdapter(visitGrowerAdapter);
                dialog_growerlist.show();
            } catch (NullPointerException e) {
                Toast.makeText(mContext, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(mContext, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(mContext, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<DownloadGrowerModel> filteredlist = new ArrayList<DownloadGrowerModel>();

        // running a for loop to compare elements.
        for (DownloadGrowerModel item : mGrowerList) {
            // checking if the entered string matched with any item of our recycler view.
            String data=item.getFullName().toLowerCase()+" "+item.getUniqueCode().toLowerCase();
            if (data.contains(text.toLowerCase())) {
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
    ArrayList<DownloadGrowerModel> getGrowerData(int type,String value)
    {
        //type
        // 0 = all  1= all focussed village 2= selected focus village

        SqlightDatabase database = null;
        ArrayList<DownloadGrowerModel> actionModels;
        try {
            database = new SqlightDatabase(mContext);

            actionModels = database.getFocussedVillageGrower(type,value);
           // Toast.makeText(mContext, " Count From Data base "+actionModels.size(), Toast.LENGTH_SHORT).show();
            return actionModels;
        }catch (Exception e)
        {
            Log.i("Error is123 ",e.getMessage());
            return null;

        }
            finally {
            if (database != null) {
                database.close();
            }

        }
    }

}
