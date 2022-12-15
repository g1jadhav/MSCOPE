package mahyco.mipl.nxg.view.downloadcategories;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import mahyco.mipl.nxg.model.CategoryChildModel;
import mahyco.mipl.nxg.model.CategoryModel;
import mahyco.mipl.nxg.model.CropModel;
import mahyco.mipl.nxg.model.CropTypeModel;
import mahyco.mipl.nxg.model.DownloadGrowerModel;
import mahyco.mipl.nxg.model.GetAllSeedDistributionModel;
import mahyco.mipl.nxg.model.ProductCodeModel;
import mahyco.mipl.nxg.model.ProductionClusterModel;
import mahyco.mipl.nxg.model.SeasonModel;
import mahyco.mipl.nxg.model.SeedBatchNoModel;
import mahyco.mipl.nxg.model.SeedReceiptModel;
import mahyco.mipl.nxg.util.Preferences;
import mahyco.mipl.nxg.util.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadCategoryApi {

    Context context;
    ProgressDialog progressDialog;
    DownloadCategoryListListener resultOutput;

    public DownloadCategoryApi(Context context, DownloadCategoryListListener resultOutput) {
        this.context = context;
        this.resultOutput = resultOutput;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait..");
    }

    public void getCategory(JsonObject jsonObject) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<CategoryModel>> call = null;
            call = RetrofitClient.getInstance().getMyApi().getCategory(jsonObject);
            call.enqueue(new Callback<List<CategoryModel>>() {
                @Override
                public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<CategoryModel> result = response.body();
                        try {
                            resultOutput.onListCategoryMasterResponse(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    public void getLocation(JsonObject jsonObject) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<CategoryChildModel>> call = null;
            call = RetrofitClient.getInstance().getMyApi().getLocation(jsonObject);
            call.enqueue(new Callback<List<CategoryChildModel>>() {
                @Override
                public void onResponse(Call<List<CategoryChildModel>> call, Response<List<CategoryChildModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //  Toast.makeText(CourseList.this, "Calling..", Toast.LENGTH_SHORT).show();

                    if (response.body() != null) {
                        List<CategoryChildModel> result = response.body();
                        try {
                            resultOutput.onListLocationResponse(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<CategoryChildModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {
        }
    }

    public void getGrower(JsonObject jsonObject) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<DownloadGrowerModel>> call = null;
            call = RetrofitClient.getInstance().getMyApi().getGrower(jsonObject);
            call.enqueue(new Callback<List<DownloadGrowerModel>>() {
                @Override
                public void onResponse(Call<List<DownloadGrowerModel>> call, Response<List<DownloadGrowerModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<DownloadGrowerModel> result = response.body();
                        try {
                            resultOutput.onListGrowerResponse(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Grower master list is empty " , Toast.LENGTH_LONG).show();
                        Preferences.save(context, Preferences.CURRENT_DATE_FOR_GROWER_DOWNLOAD, getCurrentDate());
                        Preferences.save(context, Preferences.GROWER_DOWNLOAD, "emptyList");
                    }
                }

                @Override
                public void onFailure(Call<List<DownloadGrowerModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

   private String getCurrentDate() {
        Date date = Calendar.getInstance().getTime();
        String myFormat = "dd-MM-yyyy";

        SimpleDateFormat df = new SimpleDateFormat(myFormat, Locale.getDefault());
        return df.format(date);
    }

    public void getSeason(JsonObject jsonObject) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<SeasonModel>> call = null;
            call = RetrofitClient.getInstance().getMyApi().getSeason(jsonObject);
            call.enqueue(new Callback<List<SeasonModel>>() {
                @Override
                public void onResponse(Call<List<SeasonModel>> call, Response<List<SeasonModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<SeasonModel> result = response.body();
                        try {
                            resultOutput.onListSeasonMasterResponse(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Season master list is empty " , Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<SeasonModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    public void getCrop(JsonObject jsonObject) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<CropModel>> call = null;
            call = RetrofitClient.getInstance().getMyApi().getCrop(jsonObject);
            call.enqueue(new Callback<List<CropModel>>() {
                @Override
                public void onResponse(Call<List<CropModel>> call, Response<List<CropModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<CropModel> result = response.body();
                        try {
                            resultOutput.onListCropResponse(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Crop master list is empty " , Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<CropModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    public void getProductionCluster(JsonObject jsonObject) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<ProductionClusterModel>> call = null;
            call = RetrofitClient.getInstance().getMyApi().getProductionCluster(jsonObject);
            call.enqueue(new Callback<List<ProductionClusterModel>>() {
                @Override
                public void onResponse(Call<List<ProductionClusterModel>> call, Response<List<ProductionClusterModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<ProductionClusterModel> result = response.body();
                        try {
                            resultOutput.onListProductionClusterResponse(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Production cluster master list is empty " , Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ProductionClusterModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    public void getProductCode(JsonObject jsonObject) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<ProductCodeModel>> call = null;
            call = RetrofitClient.getInstance().getMyApi().getProductCode(jsonObject);
            call.enqueue(new Callback<List<ProductCodeModel>>() {
                @Override
                public void onResponse(Call<List<ProductCodeModel>> call, Response<List<ProductCodeModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<ProductCodeModel> result = response.body();
                        try {
                            resultOutput.onListProductCodeResponse(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Parent code master list is empty " , Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<ProductCodeModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    public void getSeedReceiptNo(JsonObject jsonObject) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<SeedReceiptModel>> call = null;
            call = RetrofitClient.getInstance().getMyApi().getSeedReceiptNo(jsonObject);
            call.enqueue(new Callback<List<SeedReceiptModel>>() {
                @Override
                public void onResponse(Call<List<SeedReceiptModel>> call, Response<List<SeedReceiptModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<SeedReceiptModel> result = response.body();
                        try {
                            resultOutput.onListSeedReceiptNoResponse(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Parent seed receipt master list is empty " , Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<SeedReceiptModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    public void getSeedBatchNo(JsonObject jsonObject) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<SeedBatchNoModel>> call = null;
            call = RetrofitClient.getInstance().getMyApi().getSeedBatchNo(jsonObject);
            call.enqueue(new Callback<List<SeedBatchNoModel>>() {
                @Override
                public void onResponse(Call<List<SeedBatchNoModel>> call, Response<List<SeedBatchNoModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<SeedBatchNoModel> result = response.body();
                        try {
                            resultOutput.onListSeedBatchNoResponse(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Parent seed batch master list is empty " , Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<SeedBatchNoModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    public void getCropType(JsonObject jsonObject) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<CropTypeModel>> call = null;
            call = RetrofitClient.getInstance().getMyApi().getCropType(jsonObject);
            call.enqueue(new Callback<List<CropTypeModel>>() {
                @Override
                public void onResponse(Call<List<CropTypeModel>> call, Response<List<CropTypeModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<CropTypeModel> result = response.body();
                        try {
                            resultOutput.onListCropTypeResponse(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "Crop type master list is empty " , Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<CropTypeModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    public void getAllSeedDistributionList(JsonObject jsonObject) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            Call<List<GetAllSeedDistributionModel>> call = null;
            call = RetrofitClient.getInstance().getMyApi().getSeedDistributionList(jsonObject);
            call.enqueue(new Callback<List<GetAllSeedDistributionModel>>() {
                @Override
                public void onResponse(Call<List<GetAllSeedDistributionModel>> call, Response<List<GetAllSeedDistributionModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    if (response.body() != null) {
                        List<GetAllSeedDistributionModel> result = response.body();
                        try {
                            resultOutput.onListAllSeedDistributionResponse(result);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Preferences.save(context,Preferences.DISTRIBUTION_LIST_DOWNLOAD,"emptyList");
                        Toast.makeText(context, "Parent Seed distribution list is empty", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<GetAllSeedDistributionModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }
}
