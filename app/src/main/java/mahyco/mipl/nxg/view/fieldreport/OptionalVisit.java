package mahyco.mipl.nxg.view.fieldreport;

import android.view.View;

import androidx.appcompat.widget.Toolbar;

import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.util.BaseActivity;

public class OptionalVisit extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.optional_field_visit;
    }

    @Override
    protected void init() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("Optional Visit");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
