package mahyco.mipl.nxg.view.fieldreport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mahyco.mipl.nxg.R;

public class FiledReportDashboard extends AppCompatActivity implements View.OnClickListener {
    Button btnStage1, btnStage2, btnStage3, btnStage4, btnOptional;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filed_report_dashboard);
        context = FiledReportDashboard.this;
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

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stage1:
                showStage(1);
                break;

            case R.id.btn_stage2:
                showStage(2);
                break;

            case R.id.btn_stage3:
                showStage(3);
                break;

            case R.id.btn_stage4:
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
                    intent = new Intent(context, FieldVisitFirst.class);
                    break;

                case 2:
                    intent = new Intent(context, FieldVisitSecond.class);
                    break;

                case 3:
                    intent = new Intent(context, FieldVisitThird.class);
                    break;

                case 4:
                    intent = new Intent(context, FieldVisitFourth.class);
                    break;

                case 5:
                    intent = new Intent(context, OptionalVisit.class);
                    break;
            }
            context.startActivity(intent);
        } catch (Exception e) {

        }
    }
}