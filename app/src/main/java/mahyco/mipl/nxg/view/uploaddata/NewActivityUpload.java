package mahyco.mipl.nxg.view.uploaddata;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import mahyco.mipl.nxg.BuildConfig;
import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.CategoryChildModel;
import mahyco.mipl.nxg.model.CategoryModel;
import mahyco.mipl.nxg.model.CropModel;
import mahyco.mipl.nxg.model.CropTypeModel;
import mahyco.mipl.nxg.model.DownloadGrowerModel;
import mahyco.mipl.nxg.model.FieldMonitoringModels;
import mahyco.mipl.nxg.model.FieldVisitModel;
import mahyco.mipl.nxg.model.FieldVisitServerModel;
import mahyco.mipl.nxg.model.FirstVisitLocalModel;
import mahyco.mipl.nxg.model.GetAllSeedDistributionModel;
import mahyco.mipl.nxg.model.GrowerModel;
import mahyco.mipl.nxg.model.OldGrowerSeedDistributionModel;
import mahyco.mipl.nxg.model.ProductCodeModel;
import mahyco.mipl.nxg.model.ProductionClusterModel;
import mahyco.mipl.nxg.model.SeasonModel;
import mahyco.mipl.nxg.model.SeedBatchNoModel;
import mahyco.mipl.nxg.model.SeedReceiptModel;
import mahyco.mipl.nxg.model.StoreAreaModel;
import mahyco.mipl.nxg.model.SuccessModel;
import mahyco.mipl.nxg.model.VillageModel;
import mahyco.mipl.nxg.util.BaseActivity;
import mahyco.mipl.nxg.util.Constants;
import mahyco.mipl.nxg.util.MultipartUtility;
import mahyco.mipl.nxg.util.MyApplicationUtil;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.util.SqlightDatabase;
import mahyco.mipl.nxg.view.downloadcategories.DownloadCategoryApi;
import mahyco.mipl.nxg.view.downloadcategories.DownloadCategoryListListener;
import mahyco.mipl.nxg.view.growerregistration.GrowerRegistrationAPI;
import mahyco.mipl.nxg.view.growerregistration.Listener;
import mahyco.mipl.nxg.view.seeddistribution.DistributionListener;
import mahyco.mipl.nxg.view.seeddistribution.OldGrowerSeedDistrAPI;
import mahyco.mipl.nxg.view.seeddistribution.ParentSeedDistributionParameter;

public class NewActivityUpload extends BaseActivity implements View.OnClickListener, Listener, DistributionListener,
        DownloadCategoryListListener {

    private AppCompatButton mGrowerRegistrationBtn;
    private AppCompatButton mOrganizerRegistrationBtn;
    private AppCompatButton mDistributionUploadBtn;
    private AppCompatButton field_visit_1st_upload;

    private Context mContext;
    private List<GrowerModel> mGrowerList;
    private List<GrowerModel> mOrganizerList;
    private List<OldGrowerSeedDistributionModel> mSeedDistributionList;
//    private int mTempIdForSeedDitri;

    private TextView mGrowerRecords;
    private TextView mOrganizerRecords;
    private TextView mSeedDistributionRecords;
    private TextView field_visit_1st_no_of_records;

    private int stid = 0;
    private GrowerRegistrationAPI registrationAPI;
    private OldGrowerSeedDistrAPI mOldGrowerSeedDistrAPI;
    private boolean mGrowerClicked;
    private String mResponseString = "";

    private int mGrowerListSize;
    private int mOrganizerSize;
    int userType = 0;
    private androidx.appcompat.widget.Toolbar toolbar;
    SqlightDatabase database;
    int totalFirstVisit = 0;
    // TextView txt_visit_count;
    TextView txt1, txt2, txt3, txt4, txt5;

    @Override
    protected int getLayout() {
        return R.layout.activity_upload_layout;
    }

    @Override
    protected void init() {

        // setTitle("Data Upload");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Data Upload");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mContext = this;
        database = new SqlightDatabase(mContext);
        // Toast.makeText(mContext, "HI1", Toast.LENGTH_SHORT).show();
        registrationAPI = new GrowerRegistrationAPI(mContext, this);
        mGrowerRegistrationBtn = findViewById(R.id.grower_registration_upload);
        mGrowerRegistrationBtn.setOnClickListener(this);

        field_visit_1st_upload = findViewById(R.id.field_visit_1st_upload);
        //  txt_visit_count = findViewById(R.id.txt_visit_count);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);


        field_visit_1st_upload.setOnClickListener(this);

        mOrganizerRegistrationBtn = findViewById(R.id.organizer_registration_upload);
        mOrganizerRegistrationBtn.setOnClickListener(this);

        mOldGrowerSeedDistrAPI = new OldGrowerSeedDistrAPI(mContext, this);
        mDistributionUploadBtn = findViewById(R.id.seed_distribution_upload);
        mDistributionUploadBtn.setOnClickListener(this);

        mGrowerRecords = findViewById(R.id.grower_registration_no_of_records);
        mOrganizerRecords = findViewById(R.id.organizer_registration_no_of_records);
        mSeedDistributionRecords = findViewById(R.id.seed_distribution_no_of_records);
        field_visit_1st_no_of_records = findViewById(R.id.field_visit_1st_no_of_records);


        mGrowerRecords.setText(getString(R.string.no_of_records_for_upload, 0));
        mOrganizerRecords.setText(getString(R.string.no_of_records_for_upload, 0));
        mSeedDistributionRecords.setText(getString(R.string.no_of_records_for_upload, 0));
        field_visit_1st_no_of_records.setText(getString(R.string.no_of_records_for_upload, database.getAllFirstFieldVisit1().size()));
        //  txt_visit_count.setText(database.getCount());

        String data = database.getCount();

        if (data.contains("~")) {
            try {
                String k[] = data.trim().split("~");
                txt1.setText(k[0]);
                txt2.setText(k[1]);
                txt3.setText(k[2]);
                txt4.setText(k[3]);
                txt5.setText(k[4]);

            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }


        AppCompatTextView mVersionTextView = findViewById(R.id.upload_data_version_code);
        mVersionTextView.setText(getString(R.string.version_code, BuildConfig.VERSION_NAME));

        mGrowerList = new ArrayList<>();
        mOrganizerList = new ArrayList<>();
        mSeedDistributionList = new ArrayList<>();

        mDownloadCategoryApi = new DownloadCategoryApi(mContext, this);

        new GetRegistrationAsyncTaskList().execute();
        new GetParentSeedDistrAsyncTaskList().execute();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.grower_registration_upload: {
                if (checkInternetConnection(mContext)) {
                    if (mGrowerList.size() > 0) {
                        //      14-12-2022 Added by Jeevan
                        Log.e("temporary", " before SystemClock.elapsedRealtime() " + SystemClock.elapsedRealtime() +
                                " lastClickTimeOrganizer " + lastClickTimeGrower + " = " +
                                (SystemClock.elapsedRealtime() - lastClickTimeGrower));
                        if (SystemClock.elapsedRealtime() - lastClickTimeGrower < 3500) {
                            return;
                        }
                        lastClickTimeGrower = SystemClock.elapsedRealtime();
                        //Log.e("temporary"," after "+ lastClickTimeGrower);
                        //           14-12-2022 Added by Jeevan ended here
                        mGrowerClicked = true;
//                        Log.e("temporary", "on cllick  mGrowerList.get(0).getGrowerImageUpload() " +  mGrowerList.get(0).getGrowerImageUpload() +
//                                " mGrowerList.get(0).FrontImageUpload() " + mGrowerList.get(0).getFrontImageUpload() +
//                                " mGrowerList.get(0).BackImageUpload() " + mGrowerList.get(0).getBackImageUpload() +
//                                " mGrowerList.get(0).getUploadPhoto() " + mGrowerList.get(0).getUploadPhoto() +
//                                " mGrowerList.get(0).getIdProofFrontCopy() " + mGrowerList.get(0).getIdProofFrontCopy()+
//                                " mGrowerList.get(0).getIdProofBackCopy() " + mGrowerList.get(0).getIdProofBackCopy());
                        userType = 1;
                        uploadData();
                    } else {
                        showNoInternetDialog(mContext, "No data available to upload");
                    }
                } else {
                    showNoInternetDialog(mContext, "Please check your internet connection");
                }
            }
            break;
            case R.id.organizer_registration_upload: {
                if (checkInternetConnection(mContext)) {
                    if (mOrganizerList.size() > 0) {
                        /*14-12-2022 Added by Jeevan*/
                       /* Log.e("temporary"," before SystemClock.elapsedRealtime() "+ SystemClock.elapsedRealtime() +
                                " lastClickTimeOrganizer " + lastClickTimeOrganizer +" = " +
                                (SystemClock.elapsedRealtime() - lastClickTimeOrganizer));*/
                        if (SystemClock.elapsedRealtime() - lastClickTimeOrganizer < 3500) {
                            return;
                        }
                        lastClickTimeOrganizer = SystemClock.elapsedRealtime();
                        //Log.e("temporary"," after "+ lastClickTimeOrganizer);
                        /*14-12-2022 Added by Jeevan ended here*/
                        mGrowerClicked = false;
//                        Log.e("temporary", "on cllick  mOrganizerList.get(0).getGrowerImageUpload() " +  mOrganizerList.get(0).getGrowerImageUpload() +
//                                " mOrganizerList.get(0).getUploadPhoto() " + mOrganizerList.get(0).getUploadPhoto() +
//                                " mOrganizerList.get(0).getIdProofFrontCopy() " + mOrganizerList.get(0).getIdProofFrontCopy()+
//                                " mOrganizerList.get(0).getIdProofBackCopy() " + mOrganizerList.get(0).getIdProofBackCopy());
                       /* stid = 1;
                        new UploadFile().execute(mOrganizerList.get(0).getUploadPhoto());*/
                        userType = 2;
                        uploadData();

                    } else {
                        showNoInternetDialog(mContext, "No data available to upload");
                    }
                } else {
                    showNoInternetDialog(mContext, "Please check your internet connection");
                }
            }
            break;
            case R.id.seed_distribution_upload: {
                if (checkInternetConnection(mContext)) {
                    if (mSeedDistributionList.size() > 0) {
                        /*14-12-2022 Added by Jeevan*/
                      /*  Log.e("temporary"," before SystemClock.elapsedRealtime() "+ SystemClock.elapsedRealtime() +
                                " lastClickTime " + lastClickTime +" = " +
                                (SystemClock.elapsedRealtime() - lastClickTime));*/
                        if (SystemClock.elapsedRealtime() - lastClickTime < 3500) {
                            return;
                        }
                        lastClickTime = SystemClock.elapsedRealtime();
                        //Log.e("temporary"," after "+ lastClickTime);
                        /*14-12-2022 Added by Jeevan ended here*/
                        callSeedDistributionApi();
                    } else {
                        showNoInternetDialog(mContext, "No data available to upload");
                    }
                } else {
                    showNoInternetDialog(mContext, "Please check your internet connection");
                }
            }
            break;

            case R.id.field_visit_1st_upload:
                totalFirstVisit = database.getAllFirstFieldVisit1().size();
                uploadFirstVisit();
                break;
        }
    }

    private void uploadFirstVisit() {
        if (totalFirstVisit > 0) {
            try {

                ArrayList<FieldVisitModel> f = database.getAllFirstFieldVisit1();
     /*       JsonObject jsonObject=new JsonParser().parse(f.get(0).getData()).getAsJsonObject();
            Gson gson = new Gson();
            FieldMonitoringModels monitoringModels = gson.fromJson(jsonObject, FieldMonitoringModels.class);
            monitoringModels.getFieldVisitModel().setCapturePhoto("Tests Changes");
            JsonObject jsonObjectFinal=new JsonParser().parse(new Gson().toJson(monitoringModels)).getAsJsonObject();

            Log.i("JsonData",jsonObjectFinal.toString());*/
                JsonObject jsonObject_Visit = new JsonObject();
                JsonArray jsonArray = new JsonArray();
                for (FieldVisitModel fieldVisitModel : f) {
          /*          if (fieldVisitModel.getMandatoryFieldVisitId() == 1) {

                        if (fieldVisitModel.getFieldVisitFruitsCobModelsText() == null || fieldVisitModel.getFieldVisitFruitsCobModelsText().trim().equals("")) {

                        } else
                            fieldVisitModel.setFieldVisitFruitsCobModelsText("[]");

                        if (fieldVisitModel.getFieldVisitRoguedPlantModels() == null || fieldVisitModel.getFieldVisitRoguedPlantModels().trim().equals("")) {
                        } else
                            fieldVisitModel.setFieldVisitRoguedPlantModels("[]");
                    }*/

                    try {
                        JsonObject jsonObjectFinale = new JsonObject();
                        String base64 = "";
                        Log.i("Final base64 R :", fieldVisitModel.getCapturePhoto());

                        if (fieldVisitModel.getCapturePhoto().equals("") || fieldVisitModel.getCapturePhoto().trim().equals("NA")) {
                            base64 = "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";
                        } else {
                            if (new File(fieldVisitModel.getCapturePhoto().toString().trim()).exists())
                                Log.i("Present Status :", ">>>>>>>>>>>Yes");
                            base64 = MyApplicationUtil.getImageDatadetail(fieldVisitModel.getCapturePhoto());

                        }

                        JsonArray jsonObject_location = new JsonParser().parse(f.get(0).getLocationData()).getAsJsonArray();
                        JsonArray jsonObject_line = new JsonParser().parse(f.get(0).getLineData()).getAsJsonArray();
                        Log.i("Pass ", "1" + f.get(0).getFieldVisitFruitsCobModelsText());
                        JsonArray jsonObject_roguedplant = new JsonParser().parse(f.get(0).getFieldVisitRoguedPlantModels()).getAsJsonArray();
                        Log.i("Pass ", "2" + f.get(0).getFieldVisitRoguedPlantModels());
                        JsonArray jsonObject_fruitscobs = new JsonParser().parse(f.get(0).getFieldVisitFruitsCobModelsText()).getAsJsonArray();
                        Log.i("Pass ", "3" + f.get(0).getFieldVisitFruitsCobModelsText());
                        JsonObject json_visitModel = new JsonObject();
                        json_visitModel.addProperty("UserId", fieldVisitModel.getUserId());
                        json_visitModel.addProperty("CountryId", fieldVisitModel.getCountryId());
                        json_visitModel.addProperty("CountryMasterId", fieldVisitModel.getCountryMasterId());
                        json_visitModel.addProperty("MandatoryFieldVisitId", fieldVisitModel.getMandatoryFieldVisitId());
                        json_visitModel.addProperty("FieldVisitType", fieldVisitModel.getFieldVisitType());
                        json_visitModel.addProperty("TotalSeedAreaLost", fieldVisitModel.getTotalSeedAreaLost());
                        json_visitModel.addProperty("TaggedAreaInHA", fieldVisitModel.getTaggedAreaInHA());
                        json_visitModel.addProperty("ExistingAreaInHA", fieldVisitModel.getExistingAreaInHA());
                        json_visitModel.addProperty("ReasonForTotalLossed", fieldVisitModel.getReasonForTotalLossed());
                        json_visitModel.addProperty("FemaleSowingDt", fieldVisitModel.getFemaleSowingDt());
                        json_visitModel.addProperty("MaleSowingDt", fieldVisitModel.getMaleSowingDt());
                        json_visitModel.addProperty("IsolationM", fieldVisitModel.getIsolationM());
                        json_visitModel.addProperty("IsolationMeter", fieldVisitModel.getIsolationMeter());
                        json_visitModel.addProperty("CropStage", fieldVisitModel.getCropStage());
                        json_visitModel.addProperty("TotalNoOfFemaleLines", fieldVisitModel.getTotalNoOfFemaleLines());
                        json_visitModel.addProperty("TotalNoOfMaleLines", fieldVisitModel.getTotalNoOfMaleLines());
                        json_visitModel.addProperty("FemaleSpacingRRinCM", fieldVisitModel.getFemaleSpacingRRinCM());
                        json_visitModel.addProperty("FemaleSpacingPPinCM", fieldVisitModel.getFemaleSpacingPPinCM());
                        json_visitModel.addProperty("MaleSpacingRRinCM", fieldVisitModel.getMaleSpacingRRinCM());
                        json_visitModel.addProperty("MaleSpacingPPinCM", fieldVisitModel.getMaleSpacingPPinCM());
                        json_visitModel.addProperty("PlantingRatioFemale", fieldVisitModel.getPlantingRatioFemale());
                        json_visitModel.addProperty("PlantingRatioMale", fieldVisitModel.getPlantingRatioMale());
                        json_visitModel.addProperty("CropCategoryType", fieldVisitModel.getCropCategoryType());
                        json_visitModel.addProperty("TotalFemalePlants", fieldVisitModel.getTotalFemalePlants());
                        json_visitModel.addProperty("TotalMalePlants", fieldVisitModel.getTotalMalePlants());
                        json_visitModel.addProperty("YieldEstimateInKg", fieldVisitModel.getYieldEstimateInKg());
                        json_visitModel.addProperty("Observations", fieldVisitModel.getObservations());
                        json_visitModel.addProperty("FieldVisitDt", fieldVisitModel.getFieldVisitDt());
                        json_visitModel.addProperty("Latitude", fieldVisitModel.getLatitude());
                        json_visitModel.addProperty("Longitude", fieldVisitModel.getLongitude());
                        json_visitModel.addProperty("CapturePhoto", base64);
                        json_visitModel.addProperty("CreatedBy", fieldVisitModel.getCreatedBy());
                        json_visitModel.addProperty("AreaLossInHa", fieldVisitModel.getAreaLossInHa());
                        json_visitModel.addProperty("NoOfRoguedFemalePlants", fieldVisitModel.getNoOfRoguedFemalePlants());
                        json_visitModel.addProperty("NoOfRoguedMalePlants", fieldVisitModel.getNoOfRoguedMalePlants());
                        json_visitModel.addProperty("SeedProductionMethod", fieldVisitModel.getSeedProductionMethod());
                        json_visitModel.addProperty("RoguingCompletedValidated", fieldVisitModel.getRoguingCompletedValidated());
                        json_visitModel.addProperty("SingleCobsPerPlant", fieldVisitModel.getSingleCobsPerPlant());
                        json_visitModel.addProperty("SingleCobsPerPlantInGm", fieldVisitModel.getSingleCobsPerPlantInGm());
                        json_visitModel.addProperty("UnprocessedSeedReadyInKg", fieldVisitModel.getUnprocessedSeedReadyInKg());
                        json_visitModel.addProperty("PollinationStartDt", fieldVisitModel.getPollinationStartDt());
                        json_visitModel.addProperty("PollinationEndDt", fieldVisitModel.getPollinationEndDt());
                        json_visitModel.addProperty("ExpectedDtOfHarvesting", fieldVisitModel.getExpectedDtOfHarvesting());
                        json_visitModel.addProperty("ExpectedDtOfDespatching", fieldVisitModel.getExpectedDtOfDespatching());
                        json_visitModel.addProperty("MaleParentUprooted", fieldVisitModel.getMaleParentUprooted());
                        json_visitModel.addProperty("AreaLossStatus", fieldVisitModel.getAreaLossStatus());
                        if (fieldVisitModel.getAverageNoofExistingbolls() != null && !(fieldVisitModel.getAverageNoofExistingbolls().trim().equals("")))
                            json_visitModel.addProperty("AverageNoofExistingbolls", Double.parseDouble(fieldVisitModel.getAverageNoofExistingbolls().trim()));
                        else
                            json_visitModel.addProperty("AverageNoofExistingbolls", 0);

                        if (fieldVisitModel.getDistanceFromField() != null && !(fieldVisitModel.getDistanceFromField().trim().equals("")))
                            json_visitModel.addProperty("DistanceFromField", fieldVisitModel.getDistanceFromField().trim());
                        else
                            json_visitModel.addProperty("DistanceFromField", "0");

                        jsonObjectFinale.add("fieldVisitModel", json_visitModel);
                        jsonObjectFinale.add("fieldVisitLocationModels", jsonObject_location);
                        jsonObjectFinale.add("fieldPlantLaneModels", jsonObject_line);
                        jsonObjectFinale.add("fieldVisitRoguedPlantModels", jsonObject_roguedplant);
                        jsonObjectFinale.add("fieldVisitFruitsCobModels", jsonObject_fruitscobs);

                        jsonArray.add(jsonObjectFinale);
                    } catch (Exception e) {
                       Log.i("Getting Error ",e.getMessage());
                    }
                }
                jsonObject_Visit.add("fieldMonitoringModels", jsonArray);

                registrationAPI.createFirstVisit(jsonObject_Visit);
                Log.i("Final Json R :", jsonObject_Visit.toString());


            } catch (Exception e) {
                Log.i("JsonDataError : ", e.getMessage());
            }
        } else {
            showNoInternetDialog(mContext, "No Records Found.");

        }
    }

    private long lastClickTime = 0;
    private long lastClickTimeGrower = 0;
    private long lastClickTimeOrganizer = 0;

    @Override
    public void onResult(String result) {

    }

    @Override
    public void onSeedDistributionResult(SuccessModel result) {
        if (result.getStatus().equalsIgnoreCase("Success")) {
            mResponseString = result.getComment();
            showNoInternetDialog(mContext, "New Parent Seed Distribution Record/s Uploaded Successfully");
            new DeleteParentSeedDistributiSyncSuccessfully().execute();
        } else {
            showNoInternetDialog(mContext, result.getComment());
        }
    }

    @Override
    public void onListResponce(List result) {

    }

    @Override
    public void onSpinnerClick(int id, String categoryId) {

    }

    @Override
    public void loadChildSpinner(List<CategoryChildModel> result, SearchableSpinner spinner) {

    }

    @Override
    public void onGrowerRegister(SuccessModel result) {
        if (result.getStatus().equalsIgnoreCase("Success")) {
            /*if (mGrowerClicked) {*/
//            Log.e("temporary", "result.getStatus().equalsIgnoreCase(\"Success\")");
            mResponseString = result.getComment();
            //  Log.e("temporary","onGrowerRegister mResponseString " + mResponseString);
            //  new DeleteIfSyncSuccessfully().execute();

            if (result.isResultFlag()) {
                SqlightDatabase database = null;
                boolean b;

                try {
                    int cnt = 0;
                    database = new SqlightDatabase(mContext);
                    if (userType == 1) {
                        cnt = mGrowerList.size();
                        mGrowerList.clear();
                    } else if (userType == 2) {
                        cnt = mOrganizerList.size();
                        mOrganizerList.clear();
                    }
                    b = database.updateAllUserType(userType);
                    if (b) {
                        showNoInternetDialog(mContext, cnt + " Records Uploaded Successfully.");
                    }
                    new GetRegistrationAsyncTaskList().execute();

                } catch (Exception e) {

                }
            } else {
                showNoInternetDialog(mContext, result.getComment());
            }
        } else {
            showNoInternetDialog(mContext, result.getComment());
        }
    }

    @Override
    public void onFirstVisitRegister(SuccessModel result) {
        try {
            if (result.isResultFlag()) {
                database.trucateTable("tbl_firstVisit");

                showNoInternetDialog(mContext, totalFirstVisit + " Records Uploaded Successfully.");
                totalFirstVisit = database.getAllFirstFieldVisit1().size();
                field_visit_1st_no_of_records.setText(getString(R.string.no_of_records_for_upload, totalFirstVisit));
                String data = database.getCount();

                if (data.contains("~")) {
                    try {
                        String k[] = data.trim().split("~");
                        txt1.setText(k[0]);
                        txt2.setText(k[1]);
                        txt3.setText(k[2]);
                        txt4.setText(k[3]);
                        txt5.setText(k[4]);

                    } catch (ArrayIndexOutOfBoundsException e) {

                    }
                }

            } else {
                showNoInternetDialog(mContext, "Result : " + result.getStatus() + "\nDetails :" + result.getComment());
            }
            Log.i("Result", result.getStatus() + "" + result.toString());
        } catch (Exception e) {
            Toast.makeText(mContext, "error" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class GetRegistrationAsyncTaskList extends AsyncTask<Void, Void, Void> {
        @Override
        protected final Void doInBackground(Void... voids) {
            SqlightDatabase database = null;
            try {
                database = new SqlightDatabase(mContext);
                List<GrowerModel> list = database.getAllRegistration();
                mGrowerList = new ArrayList<>();
                mOrganizerList = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getUserType().equalsIgnoreCase("Grower")) {
                        mGrowerList.add(list.get(i));
                    } else {
                        mOrganizerList.add(list.get(i));
                    }
                }
                mOrganizerSize = mOrganizerList.size();
                mGrowerListSize = mGrowerList.size();
            } catch (Exception e) {
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            try {
                mGrowerRecords.setText(getString(R.string.no_of_records_for_upload, mGrowerList.size()));
                mOrganizerRecords.setText(getString(R.string.no_of_records_for_upload, mOrganizerList.size()));
                super.onPostExecute(unused);
            } catch (Exception e) {
            }
        }
    }

    public class UploadFile extends AsyncTask {
        ProgressDialog progressDialog;

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            progressDialog.dismiss();
            try {
                List<String> response = (List) o;
//                Log.e("temporary", "SERVER REPLIED: " + response.size());
                for (String line : response) {
//                    Log.e("temporary", "Line : " + line);
                    try {
                        JSONObject job = new JSONObject(line.toString());
                        new UpdateAsyncTaskList().execute(job.getString("FileName"));
                    } catch (Exception e) {

                    }
                }
            } catch (Exception e) {
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("File Uploading ....");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected List<String> doInBackground(Object[] objects) {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String charset = "UTF-8";
                File uploadFile1 = new File(objects[0].toString().trim());
                String requestURL = Constants.BASE_URL + "users/uploadFile";
                MultipartUtility multipart = new MultipartUtility(requestURL, charset);

                if (mGrowerClicked) {
                    multipart.addFormField("UniqueCode", mGrowerList.get(0).getUniqueCode());
                    multipart.addFormField("FarmerName", mGrowerList.get(0).getFullName());
                } else {
                    multipart.addFormField("UniqueCode", mOrganizerList.get(0).getUniqueCode());
                    multipart.addFormField("FarmerName", mOrganizerList.get(0).getFullName());
                }
                multipart.addFilePart("UploadFile", uploadFile1);

                List<String> response = multipart.finish();
                return response;
            } catch (Exception e) {

            }
            return null;
        }
    }

//    private String mGrowerPath;
//    private String mDocBackPath;
//    private String mDocFrontPath;

    private class UpdateAsyncTaskList extends AsyncTask<String, String, Void> {

        private String paths[];

        @Override
        protected final Void doInBackground(String... path) {
            SqlightDatabase database = null;
            try {
                paths = path;
                database = new SqlightDatabase(mContext);
//                Log.e("temporary", "UpdateAsyncTaskList stid " + stid + " paths " + paths[0]);
                if (stid == 1) {
                    // mGrowerPath = paths[0];
                    if (mGrowerClicked) {
                        database.updateRegistrationImagePath(mGrowerList.get(0).getUniqueCode(), "growerPhoto", path[0], 1);
                    } else {
                        database.updateRegistrationImagePath(mOrganizerList.get(0).getUniqueCode(), "growerPhoto", path[0], 1);
                    }
                    runOnUiThread(() -> Toast.makeText(mContext, "Dp Uploaded", Toast.LENGTH_SHORT).show());
                    stid = 2;
                } else if (stid == 2) {
                    stid = 3;
                    //  mDocFrontPath = paths[0];
                    if (mGrowerClicked) {
                        database.updateRegistrationImagePath(mGrowerList.get(0).getUniqueCode(), "frontPhoto", path[0], 1);
                    } else {
                        database.updateRegistrationImagePath(mOrganizerList.get(0).getUniqueCode(), "frontPhoto", path[0], 1);
                    }
                    runOnUiThread(() -> Toast.makeText(mContext, "front Uploaded", Toast.LENGTH_SHORT).show());
                } else if (stid == 3) {
                    //  mDocBackPath = paths[0];
                    stid = 4;
                    if (mGrowerClicked) {
                        database.updateRegistrationImagePath(mGrowerList.get(0).getUniqueCode(), "docBackPhoto", path[0], 1);
                    } else {
                        database.updateRegistrationImagePath(mOrganizerList.get(0).getUniqueCode(), "docBackPhoto", path[0], 1);
                    }
                    runOnUiThread(() -> Toast.makeText(mContext, "back Uploaded", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            /*Log.e("temporary", "onPostExecute mGrowerClicked " + mGrowerClicked + " mGrowerList " + mGrowerList + " mGrowerList size " + mGrowerList.size()
                    + " mGrowerPath " + mGrowerPath + " mDocBack " + mDocBackPath + " mDocFrontPath " + mDocFrontPath);
            if (stid < 4) {
                if (mGrowerClicked && mGrowerList != null && mGrowerList.size() > 0) {
                    new UploadFile().execute(mGrowerList.get(0).getIdProofFrontCopy());
                } else if (mOrganizerList != null && mOrganizerList.size() > 0) {
                    new UploadFile().execute(mOrganizerList.get(0).getIdProofFrontCopy());
                }
            } else {
                JsonObject jsonObject = new JsonObject();
                if (mGrowerClicked) {
                    jsonObject.addProperty("CountryId", mGrowerList.get(0).getCountryId());
                    jsonObject.addProperty("CountryMasterId", mGrowerList.get(0).getCountryMasterId());
                    jsonObject.addProperty("CreatedBy", mGrowerList.get(0).getCreatedBy());
                    jsonObject.addProperty("DOB", mGrowerList.get(0).getDOB());
                    jsonObject.addProperty("FullName", mGrowerList.get(0).getFullName());
                    jsonObject.addProperty("Gender", mGrowerList.get(0).getGender());
                    jsonObject.addProperty("IdProofBackCopy", *//*mGrowerList.get(mIndex).getIdProofBackCopy()*//*mDocBackPath);
                    jsonObject.addProperty("IdProofFrontCopy", *//*mGrowerList.get(mIndex).getIdProofFrontCopy()*//*mDocFrontPath);
                    jsonObject.addProperty("LandMark", mGrowerList.get(0).getLandMark());
                    jsonObject.addProperty("LoginId", mGrowerList.get(0).getLoginId());
                    jsonObject.addProperty("MobileNo", mGrowerList.get(0).getMobileNo());
                    jsonObject.addProperty("RegDt", mGrowerList.get(0).getRegDt());
                    jsonObject.addProperty("StaffNameAndId", mGrowerList.get(0).getStaffNameAndId());
                    jsonObject.addProperty("UniqueCode", mGrowerList.get(0).getUniqueCode());
                    jsonObject.addProperty("UploadPhoto", *//*mGrowerList.get(mIndex).getUploadPhoto()*//*mGrowerPath);
                    jsonObject.addProperty("UserType", mGrowerList.get(0).getUserType());
                    jsonObject.addProperty("UniqueId", mGrowerList.get(0).getUniqueId());
                } else {
                    jsonObject.addProperty("CountryId", mOrganizerList.get(0).getCountryId());
                    jsonObject.addProperty("CountryMasterId", mOrganizerList.get(0).getCountryMasterId());
                    jsonObject.addProperty("CreatedBy", mOrganizerList.get(0).getCreatedBy());
                    jsonObject.addProperty("DOB", mOrganizerList.get(0).getDOB());
                    jsonObject.addProperty("FullName", mOrganizerList.get(0).getFullName());
                    jsonObject.addProperty("Gender", mOrganizerList.get(0).getGender());
                    jsonObject.addProperty("IdProofBackCopy",*//* mOrganizerList.get(mIndex).getIdProofBackCopy()*//*mDocBackPath);
                    jsonObject.addProperty("IdProofFrontCopy", *//*mOrganizerList.get(mIndex).getIdProofFrontCopy()*//*mDocFrontPath);
                    jsonObject.addProperty("LandMark", mOrganizerList.get(0).getLandMark());
                    jsonObject.addProperty("LoginId", mOrganizerList.get(0).getLoginId());
                    jsonObject.addProperty("MobileNo", mOrganizerList.get(0).getMobileNo());
                    jsonObject.addProperty("RegDt", mOrganizerList.get(0).getRegDt());
                    jsonObject.addProperty("StaffNameAndId", mOrganizerList.get(0).getStaffNameAndId());
                    jsonObject.addProperty("UniqueCode", mOrganizerList.get(0).getUniqueCode());
                    jsonObject.addProperty("UploadPhoto", *//*mOrganizerList.get(mIndex).getUploadPhoto()*//*mGrowerPath);
                    jsonObject.addProperty("UserType", mOrganizerList.get(0).getUserType());
                    jsonObject.addProperty("UniqueId", mOrganizerList.get(0).getUniqueId());
                }
                registrationAPI.createGrower(jsonObject);
            }*/
            new GetUpdatedListAfterUpdateAsyncTaskList().execute();
            super.onPostExecute(unused);
        }
    }

    private class DeleteIfSyncSuccessfully extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected final Boolean doInBackground(Void... voids) {
            SqlightDatabase database = null;
            boolean b;
            try {
                database = new SqlightDatabase(mContext);

                b = database.updateAllUserType(userType);

               /* if (mGrowerClicked) {
                    b = database.deleteRegistration(mGrowerList.get(0).getUniqueCode());
                } else {
                    b = database.deleteRegistration(mOrganizerList.get(0).getUniqueCode());
                }*/
            } catch (Exception e) {
                return false;
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return b;
        }

        @Override
        protected void onPostExecute(Boolean deleted) {
//            Log.e("temporary", " onPostExecute deleted " + deleted);
            new GetUpdatedListAfterDeleteAsyncTaskList().execute();
            super.onPostExecute(deleted);
        }
    }

    private class GetUpdatedListAfterDeleteAsyncTaskList extends AsyncTask<Void, Void, Void> {
        @Override
        protected final Void doInBackground(Void... voids) {
            SqlightDatabase database = null;
            try {
                database = new SqlightDatabase(mContext);
                List<GrowerModel> list = database.getAllRegistration();
                if (mGrowerList != null && mGrowerList.size() > 0) {
                    mGrowerList.clear();
                }
                if (mOrganizerList != null && mOrganizerList.size() > 0) {
                    mOrganizerList.clear();
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getUserType().equalsIgnoreCase("Grower")) {
                        mGrowerList.add(list.get(i));
                    } else {
                        mOrganizerList.add(list.get(i));
                    }
//                    Log.e("temporary", "\nGetUpdatedListAfterDeleteAsyncTaskList" +
//                            "\nnew refresh list\n i "+ i +"\nfarmer photo " + list.get(i).getUploadPhoto() + "\n country id " + list.get(i).getCountryId() +
//                            "\n i "+ i+"\n CountryMasterId() " + list.get(i).getCountryMasterId() +
//                            "\ni "+ i+"\nLandMark()" + list.get(i).getLandMark() +
//                            "\ni "+ i+"\nLandFullName()" + list.get(i).getFullName() +
//                            "\ni "+ i+"\nLandGender()" + list.get(i).getGender() +
//                            "\ni "+ i+"\nLandDOB()()" + list.get(i).getDOB() +
//                            "\ni "+ i+"\nLandMobileNo()" + list.get(i).getMobileNo() +
//                            "\ni "+ i+"\nLandUniqueCode()" + list.get(i).getUniqueCode() +
//                            "\ni "+ i+"\nLandRegDt()" + list.get(i).getRegDt() +
//                            "\ni "+ i+"\nLandStaffNameAndI()" + list.get(i).getStaffNameAndId() +
//                            "\ni "+ i+"\nLandFrontCopy()" + list.get(i).getIdProofFrontCopy() +
//                            "\ni "+ i+"\nIsSync()" + list.get(i).getIsSync() +
//                            "\ni "+ i+"\nreatedBy()" + list.get(i).getCreatedBy() +
//                            "\ni "+ i+"\nUserType()" + list.get(i).getUserType() +
//                            "\ni "+ i+"\nBackCopy()" + list.get(i).getIdProofBackCopy() +
//                            "\ni "+ i+"\ntempid ()" + list.get(i).getTempId() +
//                            "\ni "+ i+"\nloginId ()" + list.get(i).getLoginId() +
//                            "\ni "+ i+"\ngetGrowerImageUpload ()" + list.get(i).getGrowerImageUpload() +
//                            "\ni "+ i+"\ngetFrontImageUpload ()" + list.get(i).getFrontImageUpload() +
//                            "\ni "+ i+"\ngetBackImageUpload ()" + list.get(i).getBackImageUpload());
                }
            } catch (Exception e) {
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            try {
                if (mGrowerClicked) {
//                Log.e("temporary", "onGrowerRegister mGrowerUpload after growerlist size " +
//                        mGrowerList.size());
                    if (mGrowerList.size() > 0) {
//                    Log.e("temporary", "UploadFile() called");
                        stid = 1;
                        new UploadFile().execute(mGrowerList.get(0).getUploadPhoto());
                    } else {
                        //  Log.e("temporary","mResponseString " + mResponseString);
                        if (mResponseString.contains("Error")) {
                            showNoInternetDialog(mContext, mResponseString);
                        } else {
                            showNoInternetDialog(mContext, "New Grower Registration " + mGrowerListSize + " Record/s Uploaded Successfully");
                        }
//                    Log.e("temporary", "onGrowerRegister mGrowerUpload all data upload");
                    }
                    mGrowerRecords.setText(getString(R.string.no_of_records_for_upload, mGrowerList.size()));
                } else {
//                Log.e("temporary", "onOrganizerRegister mOrganizerUpload after " + 0 + " mOrganizerList size " +
//                        mOrganizerList.size());
                    if (mOrganizerList.size() > 0) {
//                    Log.e("temporary", "UploadFile() called");
                        stid = 1;
                        new UploadFile().execute(mOrganizerList.get(0).getUploadPhoto());
                    } else {
                        if (mResponseString.contains("Error")) {
                            showNoInternetDialog(mContext, mResponseString);
                        } else {
                            showNoInternetDialog(mContext, "New Coordinator Registration " + mOrganizerSize + "  Record/s Uploaded Successfully");
                        }//                    Log.e("temporary", "onGrowerRegister mGrowerUpload all data upload");
                    }
                    mOrganizerRecords.setText(getString(R.string.no_of_records_for_upload, mOrganizerList.size()));
                }
                super.onPostExecute(unused);
            } catch (Exception e) {
            }
        }
    }

    private class GetUpdatedListAfterUpdateAsyncTaskList extends AsyncTask<Void, Void, Void> {
        @Override
        protected final Void doInBackground(Void... voids) {
            SqlightDatabase database = null;
            try {
                database = new SqlightDatabase(mContext);
                List<GrowerModel> list = database.getAllRegistration();
                if (mGrowerList != null && mGrowerList.size() > 0) {
                    mGrowerList.clear();
                }
                if (mOrganizerList != null && mOrganizerList.size() > 0) {
                    mOrganizerList.clear();
                }
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getUserType().equalsIgnoreCase("Grower")) {
                        mGrowerList.add(list.get(i));
                    } else {
                        mOrganizerList.add(list.get(i));
                    }
                    /*Log.e("temporary", "\nGetUpdatedListAfterUpdateAsyncTaskList new refresh list" +
                            " \n i "+ i +"\nfarmer photo " + list.get(i).getUploadPhoto() + "\n country id " + list.get(i).getCountryId() +
                            "\n i "+ i+"\n CountryMasterId() " + list.get(i).getCountryMasterId() +
                            "\ni "+ i+"\nLandMark()" + list.get(i).getLandMark() +
                            "\ni "+ i+"\nLandFullName()" + list.get(i).getFullName() +
                            "\ni "+ i+"\nLandGender()" + list.get(i).getGender() +
                            "\ni "+ i+"\nLandDOB()()" + list.get(i).getDOB() +
                            "\ni "+ i+"\nLandMobileNo()" + list.get(i).getMobileNo() +
                            "\ni "+ i+"\nLandUniqueCode()" + list.get(i).getUniqueCode() +
                            "\ni "+ i+"\nLandRegDt()" + list.get(i).getRegDt() +
                            "\ni "+ i+"\nLandStaffNameAndI()" + list.get(i).getStaffNameAndId() +
                            "\ni "+ i+"\nLandFrontCopy()" + list.get(i).getIdProofFrontCopy() +
                            "\ni "+ i+"\nIsSync()" + list.get(i).getIsSync() +
                            "\ni "+ i+"\nreatedBy()" + list.get(i).getCreatedBy() +
                            "\ni "+ i+"\nUserType()" + list.get(i).getUserType() +
                            "\ni "+ i+"\nBackCopy()" + list.get(i).getIdProofBackCopy() +
                            "\ni "+ i+"\ntempid ()" + list.get(i).getTempId() +
                            "\ni "+ i+"\nloginId ()" + list.get(i).getLoginId() +
                            "\ni "+ i+"\ngetGrowerImageUpload ()" + list.get(i).getGrowerImageUpload() +
                            "\ni "+ i+"\ngetFrontImageUpload ()" + list.get(i).getFrontImageUpload() +
                            "\ni "+ i+"\ngetBackImageUpload ()" + list.get(i).getBackImageUpload());*/
                }
            } catch (Exception e) {
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            try {
                if (stid < 4) {
                    if (mGrowerClicked && mGrowerList != null && mGrowerList.size() > 0) {
                 /*   Log.e("temporary", "GetUpdatedListAfterUpdateAsyncTaskList onPostExecute mGrowerClicked " + mGrowerClicked + " mGrowerList " + mGrowerList + " mGrowerList size " + mGrowerList.size()
                            + "stid " + stid+" mGrowerPath " + mGrowerList.get(0).getUploadPhoto() + " mDocBack " + mGrowerList.get(0).getIdProofBackCopy() + " mDocFrontPath " + mGrowerList.get(0).getIdProofFrontCopy());*/

                        if (stid == 2) {
                            new UploadFile().execute(mGrowerList.get(0).getIdProofFrontCopy());
                        } else {
                            new UploadFile().execute(mGrowerList.get(0).getIdProofBackCopy());
                        }
                    } else if (mOrganizerList != null && mOrganizerList.size() > 0) {
//                    Log.e("temporary", "GetUpdatedListAfterUpdateAsyncTaskList onPostExecute mGrowerClicked " + mGrowerClicked + " mGrowerList " + mOrganizerList + " mOrganizerList size " + mOrganizerList.size()
//                            + "stid " + stid+" mGrowerPath " + mOrganizerList.get(0).getUploadPhoto() + " mDocBack " + mOrganizerList.get(0).getIdProofBackCopy() + " mDocFrontPath " + mOrganizerList.get(0).getIdProofFrontCopy());

                        if (stid == 2) {
                            new UploadFile().execute(mOrganizerList.get(0).getIdProofFrontCopy());
                        } else {
                            new UploadFile().execute(mOrganizerList.get(0).getIdProofBackCopy());
                        }
                    }
                } else {
                /*JsonObject jsonObject = new JsonObject();
                if (mGrowerClicked) {
                    jsonObject.addProperty("CountryId", mGrowerList.get(0).getCountryId());
                    jsonObject.addProperty("CountryMasterId", mGrowerList.get(0).getCountryMasterId());
                    jsonObject.addProperty("CreatedBy", mGrowerList.get(0).getCreatedBy());
                    jsonObject.addProperty("DOB", mGrowerList.get(0).getDOB());
                    jsonObject.addProperty("FullName", mGrowerList.get(0).getFullName());
                    jsonObject.addProperty("Gender", mGrowerList.get(0).getGender());
                    jsonObject.addProperty("IdProofBackCopy", *//*mGrowerList.get(mIndex).getIdProofBackCopy()*//*mDocBackPath);
                    jsonObject.addProperty("IdProofFrontCopy", *//*mGrowerList.get(mIndex).getIdProofFrontCopy()*//*mDocFrontPath);
                    jsonObject.addProperty("LandMark", mGrowerList.get(0).getLandMark());
                    jsonObject.addProperty("LoginId", mGrowerList.get(0).getLoginId());
                    jsonObject.addProperty("MobileNo", mGrowerList.get(0).getMobileNo());
                    jsonObject.addProperty("RegDt", mGrowerList.get(0).getRegDt());
                    jsonObject.addProperty("StaffNameAndId", mGrowerList.get(0).getStaffNameAndId());
                    jsonObject.addProperty("UniqueCode", mGrowerList.get(0).getUniqueCode());
                    jsonObject.addProperty("UploadPhoto", *//*mGrowerList.get(mIndex).getUploadPhoto()*//*mGrowerPath);
                    jsonObject.addProperty("UserType", mGrowerList.get(0).getUserType());
                    jsonObject.addProperty("UniqueId", mGrowerList.get(0).getUniqueId());
                } else {
                    jsonObject.addProperty("CountryId", mOrganizerList.get(0).getCountryId());
                    jsonObject.addProperty("CountryMasterId", mOrganizerList.get(0).getCountryMasterId());
                    jsonObject.addProperty("CreatedBy", mOrganizerList.get(0).getCreatedBy());
                    jsonObject.addProperty("DOB", mOrganizerList.get(0).getDOB());
                    jsonObject.addProperty("FullName", mOrganizerList.get(0).getFullName());
                    jsonObject.addProperty("Gender", mOrganizerList.get(0).getGender());
                    jsonObject.addProperty("IdProofBackCopy",*//* mOrganizerList.get(mIndex).getIdProofBackCopy()*//*mDocBackPath);
                    jsonObject.addProperty("IdProofFrontCopy", *//*mOrganizerList.get(mIndex).getIdProofFrontCopy()*//*mDocFrontPath);
                    jsonObject.addProperty("LandMark", mOrganizerList.get(0).getLandMark());
                    jsonObject.addProperty("LoginId", mOrganizerList.get(0).getLoginId());
                    jsonObject.addProperty("MobileNo", mOrganizerList.get(0).getMobileNo());
                    jsonObject.addProperty("RegDt", mOrganizerList.get(0).getRegDt());
                    jsonObject.addProperty("StaffNameAndId", mOrganizerList.get(0).getStaffNameAndId());
                    jsonObject.addProperty("UniqueCode", mOrganizerList.get(0).getUniqueCode());
                    jsonObject.addProperty("UploadPhoto", *//*mOrganizerList.get(mIndex).getUploadPhoto()*//*mGrowerPath);
                    jsonObject.addProperty("UserType", mOrganizerList.get(0).getUserType());
                    jsonObject.addProperty("UniqueId", mOrganizerList.get(0).getUniqueId());
                }
                registrationAPI.createGrower(jsonObject);*/
                    uploadDataAfterThreeImagesUpload(/*mGrowerPath, mDocBackPath, mDocFrontPath*/);
                }
                super.onPostExecute(unused);
            } catch (Exception e) {
            }
        }
    }

    private void uploadDataAfterThreeImagesUpload(/*String dpPhotoPath, String docBackPath, String docFrontPath*/) {
        try {
            JsonObject jsonObject = new JsonObject();
            if (mGrowerClicked) {
                jsonObject.addProperty("CountryId", mGrowerList.get(0).getCountryId());
                jsonObject.addProperty("CountryMasterId", mGrowerList.get(0).getCountryMasterId());
                jsonObject.addProperty("CreatedBy",/* mGrowerList.get(0).getCreatedBy()*/Preferences.get(mContext, Preferences.USER_ID));
                jsonObject.addProperty("DOB", mGrowerList.get(0).getDOB());
                jsonObject.addProperty("FullName", mGrowerList.get(0).getFullName());
                jsonObject.addProperty("Gender", mGrowerList.get(0).getGender());
                jsonObject.addProperty("IdProofBackCopy", mGrowerList.get(0).getIdProofBackCopy());
                jsonObject.addProperty("IdProofFrontCopy", mGrowerList.get(0).getIdProofFrontCopy());
                jsonObject.addProperty("LandMark", mGrowerList.get(0).getLandMark());
                jsonObject.addProperty("LoginId", mGrowerList.get(0).getLoginId());
                jsonObject.addProperty("MobileNo", mGrowerList.get(0).getMobileNo());
                jsonObject.addProperty("RegDt", mGrowerList.get(0).getRegDt());
                jsonObject.addProperty("StaffNameAndId", mGrowerList.get(0).getStaffNameAndId());
                jsonObject.addProperty("UniqueCode", mGrowerList.get(0).getUniqueCode());
                jsonObject.addProperty("UploadPhoto", mGrowerList.get(0).getUploadPhoto());
                jsonObject.addProperty("UserType", mGrowerList.get(0).getUserType());
                jsonObject.addProperty("UniqueId", mGrowerList.get(0).getUniqueId());
                jsonObject.addProperty("Addr", mGrowerList.get(0).getAddr());

            } else {
                jsonObject.addProperty("CountryId", mOrganizerList.get(0).getCountryId());
                jsonObject.addProperty("CountryMasterId", mOrganizerList.get(0).getCountryMasterId());
                jsonObject.addProperty("CreatedBy", /*mOrganizerList.get(0).getCreatedBy()*/Preferences.get(mContext, Preferences.USER_ID));
                jsonObject.addProperty("DOB", mOrganizerList.get(0).getDOB());
                jsonObject.addProperty("FullName", mOrganizerList.get(0).getFullName());
                jsonObject.addProperty("Gender", mOrganizerList.get(0).getGender());
                jsonObject.addProperty("IdProofBackCopy", mOrganizerList.get(0).getIdProofBackCopy());
                jsonObject.addProperty("IdProofFrontCopy", mOrganizerList.get(0).getIdProofFrontCopy());
                jsonObject.addProperty("LandMark", mOrganizerList.get(0).getLandMark());
                jsonObject.addProperty("LoginId", mOrganizerList.get(0).getLoginId());
                jsonObject.addProperty("MobileNo", mOrganizerList.get(0).getMobileNo());
                jsonObject.addProperty("RegDt", mOrganizerList.get(0).getRegDt());
                jsonObject.addProperty("StaffNameAndId", mOrganizerList.get(0).getStaffNameAndId());
                jsonObject.addProperty("UniqueCode", mOrganizerList.get(0).getUniqueCode());
                jsonObject.addProperty("UploadPhoto", mOrganizerList.get(0).getUploadPhoto());
                jsonObject.addProperty("UserType", mOrganizerList.get(0).getUserType());
                jsonObject.addProperty("UniqueId", mOrganizerList.get(0).getUniqueId());
                jsonObject.addProperty("Addr", mOrganizerList.get(0).getAddr());
            }
            // Log.e("temporary"," jsonobject "+jsonObject);
            registrationAPI.createGrower(jsonObject);
        } catch (Exception e) {
        }
    }

    private void callSeedDistributionApi() {
        try {
            ArrayList<OldGrowerSeedDistributionModel> list = new ArrayList<>();

            for (int i = 0; i < mSeedDistributionList.size(); i++) {
                OldGrowerSeedDistributionModel old = new OldGrowerSeedDistributionModel();
                old.setCountryId(mSeedDistributionList.get(i).getCountryId());
                old.setPlantingYear(mSeedDistributionList.get(i).getPlantingYear());
                old.setSeasonId(mSeedDistributionList.get(i).getSeasonId());
                old.setCropId(mSeedDistributionList.get(i).getCropId());
                old.setProductionClusterId(mSeedDistributionList.get(i).getProductionClusterId());
                old.setOrganizerId(mSeedDistributionList.get(i).getOrganizerId());
                old.setGrowerId(mSeedDistributionList.get(i).getGrowerId());
                old.setParentSeedReceiptId(mSeedDistributionList.get(i).getParentSeedReceiptId());
                old.setFemaleParentSeedBatchId(mSeedDistributionList.get(i).getFemaleParentSeedBatchId());
                old.setMaleParentSeedBatchId(mSeedDistributionList.get(i).getMaleParentSeedBatchId());
                old.setIssueDt(mSeedDistributionList.get(i).getIssueDt());
                old.setSeedProductionArea(mSeedDistributionList.get(i).getSeedProductionArea());
                old.setCreatedBy(mSeedDistributionList.get(i).getCreatedBy());
                list.add(old);
            }
            ParentSeedDistributionParameter parentSeedDistributionParameter
                    = new ParentSeedDistributionParameter(list);

            // Log.e("temporary"," list "+parentSeedDistributionParameter.list);

        /*JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("CountryId", );
        jsonObject.addProperty("PlantingYear", mSeedDistributionList.get(0).getPlantingYear());
        jsonObject.addProperty("SeasonId", mSeedDistributionList.get(0).getSeasonId());
        jsonObject.addProperty("CropId", mSeedDistributionList.get(0).getCropId());
        jsonObject.addProperty("CropTypeId", mSeedDistributionList.get(0).getCropTypeId());
        jsonObject.addProperty("ProductionClusterId", mSeedDistributionList.get(0).getProductionClusterId());
        jsonObject.addProperty("OrganizerId", 0);
        jsonObject.addProperty("ProductionCode", mSeedDistributionList.get(0).getProductionCode());
        jsonObject.addProperty("ProductionStaff", Preferences.get(mContext, Preferences.LOGINID));
        jsonObject.addProperty("IsDelete", false);
        jsonObject.addProperty("CreatedBy", "");*/

            mOldGrowerSeedDistrAPI.createDistribution(parentSeedDistributionParameter);
        } catch (Exception e) {
        }
    }

    private class GetParentSeedDistrAsyncTaskList extends AsyncTask<Void, Void, Void> {
        @Override
        protected final Void doInBackground(Void... voids) {
            SqlightDatabase database = null;
            try {
                if (mSeedDistributionList != null && mSeedDistributionList.size() > 0) {
                    mSeedDistributionList.clear();
                }
                database = new SqlightDatabase(mContext);
                mSeedDistributionList = database.getParentSeedDistributionList();
               /* if (mSeedDistributionList != null && mSeedDistributionList.size() > 0) {
                    mTempIdForSeedDitri = mSeedDistributionList.get(0).getTempId();
                }*/
            } catch (Exception e) {
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            mSeedDistributionRecords.setText(getString(R.string.no_of_records_for_upload, mSeedDistributionList.size()));
            super.onPostExecute(unused);
        }
    }


    private class DeleteParentSeedDistributiSyncSuccessfully extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected final Boolean doInBackground(Void... voids) {
            SqlightDatabase database = null;
            boolean b;
            try {
                database = new SqlightDatabase(mContext);
                b = database.deleteSeedDistribution(/*mTempIdForSeedDitri*/);
            } catch (Exception e) {
                return false;
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return b;
        }

        @Override
        protected void onPostExecute(Boolean deleted) {
            try {
                if (deleted) {
                    Preferences.saveBool(mContext, Preferences.UPLOAD_DISTRIBUTION_DATA_AVAILABLE, false);
                    if (mSeedDistributionList != null && mSeedDistributionList.size() > 0) {
                        mSeedDistributionList.clear();
                    }
                    mSeedDistributionRecords.setText(getString(R.string.no_of_records_for_upload, 0));
                }
            } catch (Exception e) {
            }
            new DeleteAreaDataAsyncTaskList().execute();
            super.onPostExecute(deleted);
        }
    }

   /* private class GetUpadteSeedDistrAfterDeleteAsyncTaskList extends AsyncTask<Void, Void, Void> {
        @Override
        protected final Void doInBackground(Void... voids) {
            SqlightDatabase database = null;
            try {
                database = new SqlightDatabase(mContext);
                mSeedDistributionList = database.getParentSeedDistributionList();
                mTempIdForSeedDitri = mSeedDistributionList.get(0).getTempId();
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            mSeedDistributionRecords.setText(getString(R.string.no_of_records_for_upload, mSeedDistributionList.size()));
            if (mSeedDistributionList != null && mSeedDistributionList.size() > 0) {
                callSeedDistributionApi();
            }
            super.onPostExecute(unused);
        }
    }*/

    private class DeleteAreaDataAsyncTaskList extends AsyncTask<Void, Void, Void> {
        @Override
        protected final Void doInBackground(Void... voids) {
            SqlightDatabase database = null;
            try {
                database = new SqlightDatabase(mContext);
                boolean b = database.deleteAreaData();
                //  Log.e("temporary","area database deleted " + b);
            } catch (Exception e) {
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            downloadAllSeedDistributionMasterData();
            super.onPostExecute(unused);
        }
    }

    private JsonObject mJsonObjectCategory;
    private DownloadCategoryApi mDownloadCategoryApi;
    private List<GetAllSeedDistributionModel> mGetAllSeedDistributionList;
    private List<SeedReceiptModel> mParentSeedReceiptList;
    private List<SeedBatchNoModel> mSeedBatchNoList;
    private String mDatabaseName = "";

    final String SEED_BATCH_NO_MASTER_DATABASE = "SeedBatchNoMaster";
    final String PARENT_SEED_RECEIPT_MASTER_DATABASE = "ParentSeedReceiptMaster";
    final String GET_ALL_SEED_DISTRIBUTION_MASTER_DATABASE = "GetAllSeedDistributionMaster";

    @Override
    public void onListCategoryMasterResponse(List<CategoryModel> result) {
    }

    @Override
    public void onListLocationResponse(List<CategoryChildModel> result) {
    }

    @Override
    public void onListSeasonMasterResponse(List<SeasonModel> result) {
    }

    @Override
    public void onListGrowerResponse(List<DownloadGrowerModel> result) {
    }

    @Override
    public void onListCropResponse(List<CropModel> result) {
    }

    @Override
    public void onListProductionClusterResponse(List<ProductionClusterModel> result) {
    }

    @Override
    public void onListProductCodeResponse(List<ProductCodeModel> result) {
    }

    @Override
    public void onListCropTypeResponse(List<CropTypeModel> result) {
    }

    @Override
    public void onListAllSeedDistributionResponse(List<GetAllSeedDistributionModel> result) {
        if (mGetAllSeedDistributionList != null) {
            mGetAllSeedDistributionList.clear();
        }
        // Log.e("temporary","All Seed Distribution List result " + result);
        mDatabaseName = "GetAllSeedDistributionMaster";
        mGetAllSeedDistributionList = result;
        new MasterAsyncTask().execute();
    }

    @Override
    public void onListAllVisitData(FieldVisitServerModel result) {

    }

    @Override
    public void onListAllVillageData(List<VillageModel> result) {

    }

    @Override
    public void onListSeedReceiptNoResponse(List<SeedReceiptModel> lst) {
        if (mParentSeedReceiptList != null) {
            mParentSeedReceiptList.clear();
        }
        //  Log.e("temporary","Seed Receipt List Response lst " + lst);
        mParentSeedReceiptList = lst;
        mDatabaseName = "ParentSeedReceiptMaster";
        new MasterAsyncTask().execute();
    }

    @Override
    public void onListSeedBatchNoResponse(List<SeedBatchNoModel> lst) {
        if (mSeedBatchNoList != null) {
            mSeedBatchNoList.clear();
        }
        //  Log.e("temporary","Seed Batch  List Response lst " + lst);
        mSeedBatchNoList = lst;
        mDatabaseName = "SeedBatchNoMaster";
        new MasterAsyncTask().execute();
    }

    private void downloadParentSeedReceiptMasterData() {
        try {
            mJsonObjectCategory = null;
            mJsonObjectCategory = new JsonObject();
            mJsonObjectCategory.addProperty("filterValue", Preferences.get(mContext, Preferences.COUNTRYCODE));
            mJsonObjectCategory.addProperty("FilterOption", "CountryId");
            mDownloadCategoryApi.getSeedReceiptNo(mJsonObjectCategory);
        } catch (Exception e) {
        }
    }

    private void downloadSeedBatchNoMasterData() {
        try {
            mJsonObjectCategory = null;
            mJsonObjectCategory = new JsonObject();
            mJsonObjectCategory.addProperty("filterValue", Preferences.get(mContext, Preferences.COUNTRYCODE));
            mJsonObjectCategory.addProperty("FilterOption", "CountryId");
            mDownloadCategoryApi.getSeedBatchNo(mJsonObjectCategory);
        } catch (Exception e) {
        }
    }

    private void downloadAllSeedDistributionMasterData() {
        try {
            mJsonObjectCategory = null;
            mJsonObjectCategory = new JsonObject();
            mJsonObjectCategory.addProperty("filterValue", Preferences.get(mContext, Preferences.COUNTRYCODE));
            mJsonObjectCategory.addProperty("FilterOption", "CountryId");
            mDownloadCategoryApi.getAllSeedDistributionList(mJsonObjectCategory);
        } catch (Exception e) {
        }
    }

    private class MasterAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected final Void doInBackground(Void... voids) {
            SqlightDatabase database = null;
            try {
                database = new SqlightDatabase(mContext);
                switch (mDatabaseName) {
                    case SEED_BATCH_NO_MASTER_DATABASE:
                        database = new SqlightDatabase(mContext);
                        database.trucateTable("tbl_seedbatchnomaster");
                        for (SeedBatchNoModel param : mSeedBatchNoList) {
                            database.addSeedBatchNo(param);
                        }
                        break;
                    case PARENT_SEED_RECEIPT_MASTER_DATABASE:
                        database = new SqlightDatabase(mContext);
                        database.trucateTable("tbl_seedreciptmaster");
                        for (SeedReceiptModel param : mParentSeedReceiptList) {
                            database.addSeedReceipt(param);
                        }
                        break;
                    case GET_ALL_SEED_DISTRIBUTION_MASTER_DATABASE:
                        database = new SqlightDatabase(mContext);
                        database.trucateTable("tbl_allseeddistributionmaster");
                        Preferences.save(mContext, Preferences.DISTRIBUTION_LIST_DOWNLOAD, "");
                        // Log.e("temporary","before size " + mGetAllSeedDistributionList.size());

                        for (GetAllSeedDistributionModel param : mGetAllSeedDistributionList) {
                            database.addAllSeedDistributionList(param);
                        }

                        if (mGetAllSeedDistributionList.size() > 0) {
                            Preferences.save(mContext, Preferences.DISTRIBUTION_LIST_DOWNLOAD, "Yes");
                        }

//                        Log.e("temporary","after is download " +
//                                Preferences.get(mContext, Preferences.DISTRIBUTION_LIST_DOWNLOAD)+
//                                " mGetAllSeedDistributionList.size() "+ mGetAllSeedDistributionList.size());
                        break;
                }
            } catch (Exception e) {
            } finally {
                switch (mDatabaseName) {
                    case SEED_BATCH_NO_MASTER_DATABASE:
                        mSeedBatchNoList.clear();
                        break;
                    case PARENT_SEED_RECEIPT_MASTER_DATABASE:
                        mParentSeedReceiptList.clear();
                        break;
                   /* case GET_ALL_SEED_DISTRIBUTION_MASTER_DATABASE:
                        mGetAllSeedDistributionList.clear();
                        break;*/
                }
                if (database != null) {
                    database.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            switch (mDatabaseName) {
                case SEED_BATCH_NO_MASTER_DATABASE:
                    downloadParentSeedReceiptMasterData();
                    break;
                case GET_ALL_SEED_DISTRIBUTION_MASTER_DATABASE:
                    // downloadSeedBatchNoMasterData();
                    //  Log.e("temporary","");
                    new StoreDataAsyncTask().execute();
                    break;
            }
            super.onPostExecute(voids);
        }
    }

    private class StoreDataAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected final Void doInBackground(Void... voids) {
            try {
                SqlightDatabase database = new SqlightDatabase(mContext);
                database.trucateTable("tbl_storestributiondata");
                //  Log.e("temporary","doInBackground mGetAllSeedDistributionList "+ mGetAllSeedDistributionList.size());
                for (int i = 0; i < mGetAllSeedDistributionList.size(); i++) {
                    StoreAreaModel storeAreaModel = new StoreAreaModel(mGetAllSeedDistributionList.get(i).getPlantingYear(), mGetAllSeedDistributionList.get(i).getProductionCode(),
                            mGetAllSeedDistributionList.get(i).getFemaleBatchNo(), mGetAllSeedDistributionList.get(i).getMaleBatchNo(),
                            mGetAllSeedDistributionList.get(i).getSeedProductionArea(),
                            mGetAllSeedDistributionList.get(i).getFemaleParentSeedBatchId(),
                            mGetAllSeedDistributionList.get(i).getMaleParentSeedBatchId(),
                            mGetAllSeedDistributionList.get(i).getParentSeedReceiptId(),
                            mGetAllSeedDistributionList.get(i).getParentSeedReceiptType(),
                            mGetAllSeedDistributionList.get(i).getProductionClusterId(),
                            /*Added by Jeevan 9-12-2022*/
                            mGetAllSeedDistributionList.get(i).getGrowerId()
                            /*Added by Jeevan 9-12-2022 ended here*/);
                    database.addAreaData(storeAreaModel);
                }
            } catch (Exception e) {
            } finally {
                //  Log.e("temporary","finally mGetAllSeedDistributionList "+ mGetAllSeedDistributionList.size());
                mGetAllSeedDistributionList.clear();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //   Log.e("temporary","StoreAreaAsyncTask result "+result);
            downloadSeedBatchNoMasterData();
            super.onPostExecute(result);
        }
    }

    // type     1=grower  2=organizer
    private void uploadData() {
        try {

            List<GrowerModel> lst_temp = new ArrayList<>();

            if (userType == 1) {
                // lst_temp = mGrowerList;
                lst_temp = new ArrayList<>(mGrowerList);
            } else if (userType == 2) {
                lst_temp = new ArrayList<>(mOrganizerList);
                //   lst_temp = mOrganizerList;
            }


            JsonObject json_UploadGrower = new JsonObject();
            JsonArray jj = new JsonArray();
            String base64_dp = "";//
            String base64_front = "";
            String base64_back = "";
            if (lst_temp.size() > 0) {
                Log.i("Tag:", lst_temp.size() + " Passin " + userType);
                for (int i = 0; i < lst_temp.size(); i++) {

                    Log.i("Tag:", "pass " + i);
                    base64_dp = MyApplicationUtil.getImageDatadetail(lst_temp.get(i).getUploadPhoto());
                    base64_back = MyApplicationUtil.getImageDatadetail(lst_temp.get(i).getIdProofFrontCopy());
                    base64_front = MyApplicationUtil.getImageDatadetail(lst_temp.get(i).getIdProofBackCopy());


                    // Log.i("Base",base64_dp);


                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("CountryId", lst_temp.get(i).getCountryId());
                    jsonObject.addProperty("CountryMasterId", lst_temp.get(i).getCountryMasterId());
                    jsonObject.addProperty("CreatedBy", lst_temp.get(i).getCreatedBy());
                    jsonObject.addProperty("DOB", lst_temp.get(i).getDOB());
                    jsonObject.addProperty("FullName", lst_temp.get(i).getFullName());
                    jsonObject.addProperty("Gender", lst_temp.get(i).getGender());
                    jsonObject.addProperty("IdProofBackCopy", base64_back);
                    jsonObject.addProperty("IdProofFrontCopy", base64_front);
                    jsonObject.addProperty("LandMark", lst_temp.get(i).getLandMark());
                    jsonObject.addProperty("LoginId", lst_temp.get(i).getLoginId());
                    jsonObject.addProperty("MobileNo", lst_temp.get(i).getMobileNo());
                    jsonObject.addProperty("RegDt", lst_temp.get(i).getRegDt());
                    jsonObject.addProperty("StaffNameAndId", lst_temp.get(i).getStaffNameAndId());
                    jsonObject.addProperty("UniqueCode", lst_temp.get(i).getUniqueCode());
                    jsonObject.addProperty("UploadPhoto", base64_dp);
                    jsonObject.addProperty("UserType", lst_temp.get(i).getUserType());
                    jsonObject.addProperty("UniqueId", lst_temp.get(i).getUniqueId());
                    jsonObject.addProperty("Addr", lst_temp.get(i).getAddr());
                    Log.i("Tag:", "pass out" + jsonObject);
                    jj.add(jsonObject);

                }
                Log.i("Tag:", jj.toString());
                //  registrationAPI.createGrower(jsonObject);
                json_UploadGrower.add("createUsersModel", jj);
                registrationAPI.createGrower(json_UploadGrower);
            }
            Log.i("Final Json", "" + json_UploadGrower.toString());
        } catch (Exception e) {
            Log.i("Tag:Error: ", e.getMessage() + mGrowerList.size());
        }
    }


}
