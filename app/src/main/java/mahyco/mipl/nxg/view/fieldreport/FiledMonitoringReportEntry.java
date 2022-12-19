package mahyco.mipl.nxg.view.fieldreport;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mahyco.mipl.nxg.BuildConfig;
import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.DownloadGrowerModel;
import mahyco.mipl.nxg.model.GetAllSeedDistributionModel;
import mahyco.mipl.nxg.spinner.CCFSerachSpinner;
import mahyco.mipl.nxg.util.BaseActivity;
import mahyco.mipl.nxg.util.SqlightDatabase;

public class FiledMonitoringReportEntry extends BaseActivity implements RecyclerViewClickListener,
        View.OnClickListener {

    private Context mContext;
String Test="";
    private CCFSerachSpinner mSearchByIdNameSpinner;

    private ArrayList<GetAllSeedDistributionModel> mGrowerList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private FieldMonitoringReportAdapter mFieldMonitoringReportAdapter;

    private AppCompatButton mNextButton;
    private AppCompatButton mBackButton;

    private LinearLayout mGrowerSearchLayout;
    private LinearLayout mFilterSearchLayout;
    private LinearLayout mFieldVisitFirst;

    private AppCompatTextView mGrowerName;
    private AppCompatTextView mIssuedSeedArea;
    private AppCompatTextView mProductionCode;

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

            Toolbar mToolbar = findViewById(R.id.toolbar);
            mToolbar.setTitle("Field Monitoring Report Entry");

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                    if (l != 0) {
                       showFieldVisitFirstLayout(i);
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

            mFieldVisitFirst = findViewById(R.id.field_visit_second);

            mGrowerSearchLayout = findViewById(R.id.grower_search_layout);
            mFilterSearchLayout = findViewById(R.id.filter_search_layout);

            new GetGrowerMasterAsyncTask().execute();

            /*mGrowerName = findViewById(R.id.grower_name_textview);
            mIssuedSeedArea = findViewById(R.id.issued_seed_area_textview);
            mProductionCode = findViewById(R.id.production_code_textview);*/

//            new GetGrowerMasterAsyncTask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_btn:
                mGrowerSearchLayout.setVisibility(View.GONE);
                mFilterSearchLayout.setVisibility(View.GONE);
                mBackButton.setVisibility(View.VISIBLE);
                mNextButton.setVisibility(View.GONE);
                if (mGrowerList.size() > 0) {
                    mFieldMonitoringReportAdapter = null;
                    mFieldMonitoringReportAdapter = new FieldMonitoringReportAdapter(mContext, FiledMonitoringReportEntry.this, mGrowerList);
                    mRecyclerView.setAdapter(mFieldMonitoringReportAdapter);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.back_btn:
                mGrowerSearchLayout.setVisibility(View.VISIBLE);
                mFilterSearchLayout.setVisibility(View.VISIBLE);
                mBackButton.setVisibility(View.GONE);
                mNextButton.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                break;
        }

    }


    private class GetGrowerMasterAsyncTask extends AsyncTask<Void, Void, ArrayList<GetAllSeedDistributionModel>> {

        @Override
        protected void onPreExecute() {
            showProgressDialog(mContext);
            super.onPreExecute();
        }

        @Override
        protected final ArrayList<GetAllSeedDistributionModel> doInBackground(Void... voids) {
            SqlightDatabase database = null;
            ArrayList<GetAllSeedDistributionModel> actionModels;
            try {
                database = new SqlightDatabase(mContext);
                actionModels = database.getAllSeedDistributionListNo();
            } finally {
                if (database != null) {
                    database.close();
                }
            }
            return actionModels;
        }

        @Override
        protected void onPostExecute(ArrayList<GetAllSeedDistributionModel> result) {
            hideProgressDialog();
            if (mGrowerList != null) {
                mGrowerList.clear();
            }

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
                    ArrayAdapter<GetAllSeedDistributionModel> adapter = new ArrayAdapter<>(mContext, R.layout.spinner_rows, mGrowerList);
                    mSearchByIdNameSpinner.setAdapter(adapter);
               /* }*/
                super.onPostExecute(result);
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

    private void showFieldVisitFirstLayout(int position){
        mGrowerSearchLayout.setVisibility(View.GONE);
        mFilterSearchLayout.setVisibility(View.GONE);
        mBackButton.setVisibility(View.GONE);
        mNextButton.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mFieldVisitFirst.setVisibility(View.VISIBLE);

       /* mGrowerName.setText(mGrowerList.get(position).getGrowerFullName());
        mIssuedSeedArea.setText(""+mGrowerList.get(position).getSeedProductionArea());
        mProductionCode.setText(mGrowerList.get(position).getProductionCode());*/
    }
}
