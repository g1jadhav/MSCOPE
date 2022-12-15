package mahyco.mipl.nxg.util;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import mahyco.mipl.nxg.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitClient {
    private static RetrofitClient instance = null;
    private Api myApi;
    private static Retrofit retrofit = null;
    static Context context;
    private RetrofitClient(Context context) {
      /*  Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)

                .addConverterFactory(GsonConverterFactory.create())

                .build();*/
        this.context=context;
          Retrofit retrofit = getRetrofitInstance(context);
        myApi = retrofit.create(Api.class);
    }

    public static Retrofit getRetrofitInstance(Context mContext) {


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(45, TimeUnit.SECONDS);
        httpClient.readTimeout(45, TimeUnit.SECONDS);
        httpClient.writeTimeout(45, TimeUnit.SECONDS);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);
        httpClient.addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request = originalRequest.newBuilder()
                        //.header("Authorization", "Bearer " + Preferences.get(mContext, Preferences.KEY_ACCESS_TOKEN))
                        // .header("Authorization", "Bearer " + "3n4F2eiPjozgbOruMyF2VGQL8Gntp1GYZvEeNy1zMsSWd6fpKMEHD3x-F9C1vckRrtsnVbY5TVD-zQFSH2u92o_VCsoeYjCMsrYg6iJST6W4q3mmNEi5jzOOl1UJYDlU6wxHvdOKTPSQtD_Zc0bhFF--VmchN_BchU5bm9qY1zcSxwWPUdETAagrivmhxx1fZ2FVS_rg5KzDyD1qYdWGuw")
                        .header("Content-Type", "application/json")
                        .header("AppVersion", BuildConfig.VERSION_NAME)
                        //.header("bearer", Preferences.get(mContext, Preferences.TOKEN))
                        .method(originalRequest.method(), originalRequest.body())
                        .build();

                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;


    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }

    public Api getMyApi() {
        return myApi;
    }
}
