package mahyco.mipl.nxg.view.fieldreport;

import android.content.Intent;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.util.BaseActivity;
import mahyco.mipl.nxg.view.AreaTagingActivity;

public class FieldVisitFirst extends BaseActivity {
AppCompatButton tag_area_location;
    @Override
    protected int getLayout() {
        return R.layout.field_visit_first;
    }

    @Override
    protected void init() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Field Visit First");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tag_area_location=findViewById(R.id.tag_area_location);
        tag_area_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FieldVisitFirst.this, AreaTagingActivity.class);
                startActivity(intent);
            }
        });

    }
}
