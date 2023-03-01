package mahyco.mipl.nxg.view.fieldreport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.FieldVisitModel_Server;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.util.SqlightDatabase;

public class FiledReportDashboard extends AppCompatActivity implements View.OnClickListener {
    Button btnStage1, btnStage2, btnStage3, btnStage4, btnOptional;
    Context mContext;
    SqlightDatabase database;
    FieldVisitModel_Server serverModel;
    int userid = 0;
    int currentStage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filed_report_dashboard);
        mContext = FiledReportDashboard.this;
        database = new SqlightDatabase(mContext);
        userid = Integer.parseInt(Preferences.get(mContext, Preferences.SELECTED_GROWERID).toString().trim());

        btnStage1 = findViewById(R.id.btn_stage1);
        btnStage2 = findViewById(R.id.btn_stage2);
        btnStage3 = findViewById(R.id.btn_stage3);
        btnStage4 = findViewById(R.id.btn_stage4);
        btnOptional = findViewById(R.id.btn_optional);

        btnStage1.setOnClickListener(this);
        btnStage2.setOnClickListener(this);
        btnStage3.setOnClickListener(this);
        btnStage4.setOnClickListener(this);
        btnOptional.setOnClickListener(this);
        serverModel = new FieldVisitModel_Server();
        try {
            serverModel = database.getVisitDetailsById(userid);
           // Preferences.save(mContext, Preferences.SELECTED_GROWERNAME, m.getGrowerFullName());
          //  Preferences.save(mContext, Preferences.SELECTED_GROWERMOBILE, m.getGrowerMobileNo());
          //  Preferences.save(mContext, Preferences.SELECTED_GROWERID, "" + m.getGrowerId());
          //  Preferences.save(mContext, Preferences.SELECTED_GROWERAREA, "" + m.getSeedProductionArea());
          //  Preferences.save(mContext, Preferences.SELECTED_GROWERPRODUCTIONCODE, "" + m.getProductionCode());
          //  Preferences.save(mContext, Preferences.SELECTED_GROWERUNIQUECODE, "" + m.getGrowerUniqueCode());
           // Preferences.save(mContext, Preferences.SELECTEDCROPECODE, "" + m.getCropCode());
            Preferences.save(mContext, Preferences.SELECTEVISITEXISITINGAREA, "" + serverModel.getExistingAreaInHA());
            Preferences.save(mContext, Preferences.SELECTEDVISITID, "" + serverModel.getMandatoryFieldVisitId());
            Preferences.save(mContext, Preferences.PREVTOTAL_FEMALE_PLANTS, "" + serverModel.getTotalFemalePlants());
            Preferences.save(mContext, Preferences.PREVTOTAL_MALE_PLANTS, "" + serverModel.getTotalMalePlants());
            currentStage = serverModel.getMandatoryFieldVisitId();
            Toast.makeText(mContext, "Selected Visit "+currentStage, Toast.LENGTH_SHORT).show();
            Log.i("VisitID", "" + serverModel.getMandatoryFieldVisitId());
            Log.i("ExisitingArea", "" + serverModel.getExistingAreaInHA());
        } catch (Exception e) {
            Log.i("Error is ", "" + e.getMessage());

        }

        switch(currentStage+1)
        {
            case 1:
                btnStage1.setVisibility(View.VISIBLE);
                btnStage2.setVisibility(View.GONE);
                btnStage3.setVisibility(View.GONE);
                btnStage4.setVisibility(View.GONE);
                btnOptional.setVisibility(View.GONE);
                break;
            case 2:
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setTitle("Skip Second Mandatory Visit")
                        .setMessage("Do you want to skip?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                btnStage1.setVisibility(View.GONE);
                                btnStage2.setVisibility(View.GONE);
                                btnStage3.setVisibility(View.VISIBLE);
                                btnStage4.setVisibility(View.GONE);
                                btnOptional.setVisibility(View.VISIBLE);
                            }})
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                btnStage1.setVisibility(View.GONE);
                btnStage2.setVisibility(View.VISIBLE);
                btnStage3.setVisibility(View.GONE);
                btnStage4.setVisibility(View.GONE);
                btnOptional.setVisibility(View.VISIBLE);
                break;
            case 3:
                btnStage1.setVisibility(View.GONE);
                btnStage2.setVisibility(View.GONE);
                btnStage3.setVisibility(View.VISIBLE);
                btnStage4.setVisibility(View.GONE);
                btnOptional.setVisibility(View.VISIBLE);
                break;
            case 4:
                btnStage1.setVisibility(View.GONE);
                btnStage2.setVisibility(View.GONE);
                btnStage3.setVisibility(View.GONE);
                btnStage4.setVisibility(View.VISIBLE);
                btnOptional.setVisibility(View.VISIBLE);
                break;
            case 5:
                btnStage1.setVisibility(View.GONE);
                btnStage2.setVisibility(View.GONE);
                btnStage3.setVisibility(View.GONE);
                btnStage4.setVisibility(View.GONE);
                btnOptional.setVisibility(View.VISIBLE);
                break;
            default:
                btnStage1.setVisibility(View.GONE);
                btnStage2.setVisibility(View.GONE);
                btnStage3.setVisibility(View.GONE);
                btnStage4.setVisibility(View.GONE);
                btnOptional.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stage1:
                if(database.isMandetoryVisitDone(userid,1))
                {
                    Toast.makeText(mContext, "Field Visit is Done.", Toast.LENGTH_SHORT).show();
                }else
                    showStage(1);

                break;

            case R.id.btn_stage2:
                if(database.isMandetoryVisitDone(userid,2))
                {
                    Toast.makeText(mContext, "Field Visit is Done.", Toast.LENGTH_SHORT).show();
                }else
                    showStage(2);
                break;

            case R.id.btn_stage3:
                if(database.isMandetoryVisitDone(userid,3))
                {
                    Toast.makeText(mContext, "Field Visit is Done.", Toast.LENGTH_SHORT).show();
                }else
                    showStage(3);
                break;

            case R.id.btn_stage4:
                if(database.isMandetoryVisitDone(userid,4))
                {
                    Toast.makeText(mContext, "Field Visit is Done.", Toast.LENGTH_SHORT).show();
                }else
                    showStage(4);
                break;

            case R.id.btn_optional:
                showStage(5);
                break;
        }
    }

    public void showStage(int stageId) {
        Intent intent = null;
        try {
            switch (stageId) {
                case 1:
                    intent = new Intent(mContext, FieldVisitFirst.class);
                    break;

                case 2:
                    intent = new Intent(mContext, FieldVisitSecond.class);
                    break;

                case 3:
                    intent = new Intent(mContext, FieldVisitThird.class);
                    break;

                case 4:
                    intent = new Intent(mContext, FieldVisitFourth.class);
                    break;

                case 5:
                    intent = new Intent(mContext, OptionalVisit.class);
                    break;
            }
            mContext.startActivity(intent);
        } catch (Exception e) {

        }
    }
}