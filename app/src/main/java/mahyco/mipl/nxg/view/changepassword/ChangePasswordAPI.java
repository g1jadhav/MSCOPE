package mahyco.mipl.nxg.view.changepassword;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

import mahyco.mipl.nxg.model.CategoryChildModel;
import mahyco.mipl.nxg.model.CategoryModel;
import mahyco.mipl.nxg.util.RetrofitClient;
import mahyco.mipl.nxg.view.login.LoginAPIListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordAPI {


    Context context;
    String result = "";
    ProgressDialog progressDialog;
    ChangePasswordAPIListener resultOutput;

    public ChangePasswordAPI(Context context, ChangePasswordAPIListener resultOutput) {
        this.context = context;
        this.resultOutput = resultOutput;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
    }

    public void validateLogin(JsonObject jsonObject) {
        try {

            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<String> call = RetrofitClient.getInstance().getMyApi().changePassword(jsonObject);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        String result = response.body();
                        try {
                            resultOutput.onResult(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

}
