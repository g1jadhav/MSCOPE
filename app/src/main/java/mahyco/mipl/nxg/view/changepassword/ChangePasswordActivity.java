package mahyco.mipl.nxg.view.changepassword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import mahyco.mipl.nxg.MainActivity;
import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.view.login.Login;
import mahyco.mipl.nxg.view.login.LoginAPI;

public class ChangePasswordActivity extends AppCompatActivity implements  ChangePasswordAPIListener{
String str_usercode="",str_country="",str_password="";
    EditText et_usercode, et_password;
    Button btn_login;
    Context context;
  ChangePasswordAPI changePasswordAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        context=ChangePasswordActivity.this;
        init();
        Bundle bundle=getIntent().getExtras();
        try{

            str_usercode=bundle.getString("usercode").toString().trim();
            str_country=bundle.getString("countryid").toString().trim();
            et_usercode.setText(str_usercode.trim());
            Log.i("Test Data",str_country+" "+str_country);
        }catch (Exception e)
        {

        }

    }

    public void init() {

        et_password = findViewById(R.id.et_password);
        et_usercode = findViewById(R.id.et_usercode);
        btn_login = findViewById(R.id.btn_login);
changePasswordAPI=new ChangePasswordAPI(context,this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_usercode = et_usercode.getText().toString().trim();
                str_password = et_password.getText().toString().trim();
                if(str_password.trim().equals("Mahyco@23#"))
                {
                    Toast.makeText(context, "Please enter different password.", Toast.LENGTH_SHORT).show();
                }else
                {
                    if(validateUI()) {
                         changePassword(str_usercode, str_password);
                    }
                }
            }
        });

    }

     void changePassword(String str_usercode, String str_password) {
        try{
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("UserCode", str_usercode);
            jsonObject.addProperty("Password", str_password);
            jsonObject.addProperty("CountryId", str_country);

            changePasswordAPI.validateLogin(jsonObject);
        }catch(Exception e)
        {

        }
    }

    private boolean validateUI() {
        try {
            int cnt = 0;

            if (str_usercode.isEmpty()) {
                et_usercode.setError("Please Enter User Id");
                cnt++;
            }
            if (str_password.isEmpty()) {
                et_password.setError("Please enter Password");
                cnt++;
            }

            if (cnt == 0)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public void onResult(String result) {
         try{

             JSONObject jsonObject=new JSONObject(result.trim());
             //Toast.makeText(context, , Toast.LENGTH_SHORT).show();

             AlertDialog.Builder builder = new AlertDialog.Builder(context);
             builder.setMessage("Username : "+jsonObject.getString("UserName")+" ,\nPassword changed successfully. Please login again.");
             builder.setTitle("MScope");
             builder.setCancelable(false);
             builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
                 finish();
             });
             AlertDialog alertDialog = builder.create();
             alertDialog.show();

         }catch(Exception e)
         {
             AlertDialog.Builder builder = new AlertDialog.Builder(context);
             builder.setMessage("Something went wrong.");
             builder.setTitle("MScope");
             builder.setCancelable(false);
             builder.setPositiveButton("Ok", (DialogInterface.OnClickListener) (dialog, which) -> {
                 dialog.dismiss();
             });
             AlertDialog alertDialog = builder.create();
             alertDialog.show();
         }
    }
}