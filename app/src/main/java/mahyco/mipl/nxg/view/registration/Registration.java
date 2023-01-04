package mahyco.mipl.nxg.view.registration;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.CategoryChildModel;
import mahyco.mipl.nxg.model.CategoryModel;
import mahyco.mipl.nxg.model.UserTypeModel;
import mahyco.mipl.nxg.util.MyApplicationUtil;

public class Registration extends AppCompatActivity implements RegistrationListener {

    EditText et_staffname, et_staffid, et_mobileno, et_password, et_confirmpassword, et_email;
    SearchableSpinner sp_country;
    SearchableSpinner sp_usertype;
    Context context;
    String str_staffname, str_staffid, str_mobile, str_password, str_confirmpassword, str_countrycode;
    JsonObject jsonObject;
    RegisrationAPI regisrationAPI;
    ArrayAdapter adapter;
    ArrayAdapter adapter_usertype;
    JsonObject categoryJson;
    JsonObject userTypeJson;
    int ccode = 0;
    CircleImageView iv_dp;
    ImageView imageView_front, imageView_back;
    String dp_path, front_path, back_path;
    String base64_image_dp = "", base64_image_back = "", base64_image_front = "";
    private File mDocFrontPhotoFile = null;
    private File mGrowerPhotoFile = null;
    private File mDocBackPhotoFile = null;
    int selectedRoleId = 0;
    String selectedRoleName = "";
    EditText et_userUniqueid, et_address;
    String str_UniqueId = "", str_address = "";
    /*Added by Jeevan 28-11-2022*/
    TextInputLayout staffIdTextInputLayout;
    /*Added by Jeevan ended here 28-11-2022*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = Registration.this;
        init();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void init() {
        regisrationAPI = new RegisrationAPI(context, this);
        et_staffname = findViewById(R.id.staff_name);
        et_staffid = findViewById(R.id.staff_id);
        et_mobileno = findViewById(R.id.mobile_no);
        et_password = findViewById(R.id.registration_password);
        et_confirmpassword = findViewById(R.id.registration_confirm_password);
        sp_country = findViewById(R.id.registration_country_drop_down);
        sp_usertype = findViewById(R.id.usertype);
        et_email = findViewById(R.id.emailid);
        et_userUniqueid = findViewById(R.id.unique_code_edittext);
        et_address = findViewById(R.id.et_address);
        iv_dp = findViewById(R.id.farmer_photo);
        imageView_front = findViewById(R.id.national_id_photo_front_side_image_view);
        imageView_back = findViewById(R.id.national_id_photo_back_side_image_view);

        categoryJson = new JsonObject();
        categoryJson.addProperty("filterValue", "0");
        categoryJson.addProperty("FilterOption", "GetCountryList");
//        regisrationAPI.getCategory(categoryJson);
        regisrationAPI.getCountry(categoryJson);

        userTypeJson = new JsonObject();
        userTypeJson.addProperty("filterValue", "");
        userTypeJson.addProperty("FilterOption", "GetUserRole");
        regisrationAPI.getUserType(userTypeJson);

        /*Added by Jeevan 28-11-2022*/
        staffIdTextInputLayout = findViewById(R.id.staff_id_layout);
        /*Added by Jeevan ended here 28-11-2022*/
    }

    public void submit(View view) {
        try {
            register();
        } catch (Exception e) {
        }
    }

    public void register() {
        if (sp_country.getSelectedItemPosition() == -1) {
            Toast.makeText(this, "Please select country", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (ccode == 0) {
            Toast.makeText(this, "Please select country", Toast.LENGTH_SHORT).show();
           return;
        }
        else if (selectedRoleId == 0) {
            Toast.makeText(this, "Please select user role", Toast.LENGTH_SHORT).show();
        return;
        }
        else if (TextUtils.isEmpty(et_staffname.getText().toString().trim())) {
            Toast.makeText(this, "Please enter staff name", Toast.LENGTH_SHORT).show();
            return;
        } else if (/*Added by Jeevan 28-11-2022*/staffIdTextInputLayout.getVisibility() == View.VISIBLE
        && /*Added by Jeevan ended here 28-11-2022*/ TextUtils.isEmpty(et_staffid.getText().toString().trim())) {
            Toast.makeText(this, "Please enter staff id", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(et_mobileno.getText().toString().trim())) {
            Toast.makeText(this, "Please enter mobile no", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(et_email.getText().toString().trim())) {
            Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString().trim()).matches()) {
            Toast.makeText(this, "Please enter valid email id", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(et_confirmpassword.getText().toString().trim())) {
            Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(et_userUniqueid.getText().toString().trim())) {
            Toast.makeText(this, "Please enter national id number", Toast.LENGTH_SHORT).show();
            return;
        } else if (ccode == 3 && (et_userUniqueid.getText().toString().trim().length() < 16
                || et_userUniqueid.getText().toString().trim().length() > 16)) {
            showToast(getString(R.string.Please_enter_valid_national_id_number));
            return;
        } else if (TextUtils.isEmpty(et_address.getText().toString().trim())) {
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show();
            return;
        } else if (!et_password.getText().toString().trim().equalsIgnoreCase(et_confirmpassword.getText().toString().trim())) {
            Toast.makeText(this, "Password and confirm password not match", Toast.LENGTH_SHORT).show();
            return;
        } else if (base64_image_dp.trim().equals("")) {
            Toast.makeText(this, "Missing user photo", Toast.LENGTH_SHORT).show();
            return;
        } else if (base64_image_front.trim().equals("")) {
            Toast.makeText(this, "Missing user front id proof photo", Toast.LENGTH_SHORT).show();
            return;
        } else if (base64_image_back.trim().equals("")) {
            Toast.makeText(this, "Missing user back id proof photo", Toast.LENGTH_SHORT).show();
            return;
        }
        str_staffname = et_staffname.getText().toString().trim();
        str_staffid = et_staffid.getText().toString().trim();
        str_mobile = et_mobileno.getText().toString().trim();
        str_password = et_password.getText().toString().trim();
        str_confirmpassword = et_confirmpassword.getText().toString().trim();
        str_countrycode = sp_country.getSelectedItem().toString();
        str_UniqueId=et_userUniqueid.getText().toString().trim();
        str_address=et_address.getText().toString().trim();

        Toast.makeText(context, "" + str_countrycode, Toast.LENGTH_SHORT).show();
        jsonObject = new JsonObject();
        jsonObject.addProperty("CountryId", ccode);//: 0,
        jsonObject.addProperty("RoleId", selectedRoleId);//: 0,
        jsonObject.addProperty("UserType", selectedRoleName);//: "string",
        jsonObject.addProperty("UserName", str_staffname);//: "string",
        jsonObject.addProperty("UserCode", str_staffid);//: "string",
        jsonObject.addProperty("EmailId", et_email.getText().toString().trim());//: "string",
        jsonObject.addProperty("MobileNo", str_mobile);//: "string",
        jsonObject.addProperty("Password", str_password);//: "string",
        jsonObject.addProperty("ConfirmPassword", str_confirmpassword);//: "string",
        jsonObject.addProperty("ParentId", 0);//: 0,
        jsonObject.addProperty("IsActive", false);//: true,
        jsonObject.addProperty("CreatedBy", str_staffid);//: "string",
        jsonObject.addProperty("UniqueCode", str_UniqueId);//: "string",
        jsonObject.addProperty("IdProofFrontCopy", base64_image_front);//: "string",
        jsonObject.addProperty("IdProofBackCopy", base64_image_back);//: "string",
        jsonObject.addProperty("UploadPhoto", base64_image_dp);//: "string"
        jsonObject.addProperty("Addr", str_address);//: "string"

        regisrationAPI.createObservation(jsonObject);

    }

    private String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        String myFormat = "yyyy-MM-dd";

        SimpleDateFormat df = new SimpleDateFormat(myFormat, Locale.getDefault());
        return df.format(date);
    }

    @Override
    public void onResult(String result) {
        Toast.makeText(context, "" + result, Toast.LENGTH_SHORT).show();

        finish();

    }


    @Override
    public void onListResponce(List<CategoryModel> result) {
        try {

            adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, result);
            sp_country.setAdapter(adapter);
            sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    CategoryModel categoryModel = (CategoryModel) parent.getItemAtPosition(position);
                    Toast.makeText(context, "" + categoryModel.getCategoryName(), Toast.LENGTH_SHORT).show();
                    ccode = categoryModel.getCategoryId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } catch (Exception e) {

        }
    }

    @Override
    public void onCountryListResponce(List<CategoryChildModel> result) {
        try {
            CategoryChildModel categoryChildModel = new CategoryChildModel();
            categoryChildModel.setCountryMasterId(0);
            categoryChildModel.setCountryName("Select Country");
            categoryChildModel.setKeyValue("Select Country");
            result.add(0, categoryChildModel);
            adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, result);
            sp_country.setAdapter(adapter);
            sp_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CategoryChildModel categoryModel = (CategoryChildModel) parent.getItemAtPosition(position);
                    //   Toast.makeText(context, "" + categoryModel.getCategoryName(), Toast.LENGTH_SHORT).show();
                    ccode = categoryModel.getCountryMasterId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    public void onUserTypeListResponce(List<UserTypeModel> result) {
        try {
            UserTypeModel userTypeModel = new UserTypeModel();
            userTypeModel.setRole("Select Role");
            userTypeModel.setRoleId(0);
            userTypeModel.setActive(false);
            result.add(0, userTypeModel);
            adapter_usertype = new ArrayAdapter(context, android.R.layout.simple_list_item_1, result);
            sp_usertype.setAdapter(adapter_usertype);
            sp_usertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    UserTypeModel categoryModel = (UserTypeModel) parent.getItemAtPosition(position);
                    //   Toast.makeText(context, "" + categoryModel.getCategoryName(), Toast.LENGTH_SHORT).show();
                    selectedRoleId = categoryModel.getRoleId();
                    selectedRoleName = categoryModel.getRole();
                    /*Added by Jeevan 28-11-2022*/
                    if(selectedRoleId == 5){
                        et_staffid.setText("");
                        staffIdTextInputLayout.setVisibility(View.GONE);
                    } else {
                        staffIdTextInputLayout.setVisibility(View.VISIBLE);
                    }
                    /*Added by Jeevan ended here 28-11-2022*/
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
        }
    }

    public void front(View v) {
        try {
            PickImageDialog.build(new PickSetup())
                    .setOnPickResult(new IPickResult() {
                        @Override
                        public void onPickResult(PickResult r) {
                            //TODO: do what you have to...
                            imageView_front.setVisibility(View.VISIBLE);
                            imageView_front.setImageBitmap(r.getBitmap());
                            // front_path = r.getPath();
                            try {
                                mDocFrontPhotoFile = createImageFile("front_photo");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (mDocFrontPhotoFile != null && r.getBitmap() != null) {
                                try {
                                    front_path = mDocFrontPhotoFile.getAbsolutePath();
                                    //Z Log.e("temporary", " front_path " + front_path);
                                    FileOutputStream out = new FileOutputStream(front_path);
                                    r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);
                                    out.flush();
                                    out.close();
                                    base64_image_front = MyApplicationUtil.getImageDatadetail(front_path);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                showToast(getString(R.string.went_wrong));
                            }
                        }
                    })
                    .setOnPickCancel(new IPickCancel() {
                        @Override
                        public void onCancelClick() {
                            //TODO: do what you have to if user clicked cancel
                        }
                    }).show(getSupportFragmentManager());
        } catch (Exception e) {

        }
    }

    public void back(View v) {
        try {
            PickImageDialog.build(new PickSetup())
                    .setOnPickResult(new IPickResult() {
                        @Override
                        public void onPickResult(PickResult r) {
                            //TODO: do what you have to...
                            imageView_back.setVisibility(View.VISIBLE);
                            imageView_back.setImageBitmap(r.getBitmap());
                            // back_path = r.getPath();
                           /* str_chk_img1 = r.getPath();
                            uploadstatus = 11;*/
                            try {
                                mDocBackPhotoFile = createImageFile("back_photo");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (mDocBackPhotoFile != null && r.getBitmap() != null) {
                                try {
                                    back_path = mDocBackPhotoFile.getAbsolutePath();
                                    //   Log.e("temporary", " back_path " + back_path);
                                    FileOutputStream out = new FileOutputStream(back_path);
                                    r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);
                                    out.flush();
                                    out.close();
                                    base64_image_back = MyApplicationUtil.getImageDatadetail(back_path);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                showToast(getString(R.string.went_wrong));
                            }
                        }
                    })
                    .setOnPickCancel(new IPickCancel() {
                        @Override
                        public void onCancelClick() {
                            //TODO: do what you have to if user clicked cancel
                        }
                    }).show(getSupportFragmentManager());
        } catch (Exception e) {

        }
    }

    public void dp(View v) {
        try {
            PickImageDialog.build(new PickSetup())
                    .setOnPickResult(new IPickResult() {
                        @Override
                        public void onPickResult(PickResult r) {
                            //TODO: do what you have to...
                            iv_dp.setImageBitmap(r.getBitmap());
                            //  dp_path = r.getPath();
                          /*  str_chk_img1 = r.getPath();
                            uploadstatus = 11;*/
                            try {
                                mGrowerPhotoFile = createImageFile("profile_photo");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (mGrowerPhotoFile != null && r.getBitmap() != null) {
                                try {
                                    dp_path = mGrowerPhotoFile.getAbsolutePath();
                                    //  Log.e("temporary", " dp " + dp_path);
                                    FileOutputStream out = new FileOutputStream(dp_path);
                                    r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);
                                    out.flush();
                                    out.close();
                                    base64_image_dp = MyApplicationUtil.getImageDatadetail(dp_path);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                showToast(getString(R.string.went_wrong));
                            }
                        }
                    })
                    .setOnPickCancel(new IPickCancel() {
                        @Override
                        public void onCancelClick() {
                            //TODO: do what you have to if user clicked cancel
                        }
                    }).show(getSupportFragmentManager());
        } catch (Exception e) {

        }
    }

    private void showToast(String string) {
        Toast.makeText(context, "" + string, Toast.LENGTH_SHORT).show();
    }


    protected File createImageFile(String type) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = /*Preferences.get(this,Preferences.USER_ID) + "_" +*/ type + "_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        );
    }
}