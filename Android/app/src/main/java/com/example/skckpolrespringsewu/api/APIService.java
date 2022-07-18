package com.example.skckpolrespringsewu.api;

import android.content.Context;
import com.example.skckpolrespringsewu.config.Constants;
import com.example.skckpolrespringsewu.model.SkckModel;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {

    @FormUrlEncoded
    @POST("api/skck")
    Call<SkckModel> tambahData(
            @Field("nik") String nik,
            @Field("nama") String nama,
            @Field("kecamatan") String kecamatan,
            @Field("status") String status,
            @Field("jk") String jk);

    class Factory{
        public static APIService create(Context mContext){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            builder.addInterceptor(new NetworkConnectionInterceptor(mContext));
            OkHttpClient client = builder.addInterceptor(interceptor).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(APIService.class);
        }
    }
}
