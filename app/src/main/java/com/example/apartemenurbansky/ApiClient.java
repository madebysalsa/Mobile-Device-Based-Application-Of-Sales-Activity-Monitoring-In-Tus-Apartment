package com.example.apartemenurbansky;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
class ApiClient {
    private static final String BASE_URL = "https://apartemenusky.000webhostapp.com/urbansky/android_register_login/";
    private static Retrofit retrofit;

    static Retrofit getApiClient(){

        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
