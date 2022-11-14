package com.example.inksapp.network;

import com.example.inksapp.model.Coop;
import com.example.inksapp.model.TempDataModel;
import com.example.inksapp.model.User;
import com.example.inksapp.model.WaterDataModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIinterface {
 @GET("temp.php")
 Call<TempDataModel> getTempData();

 @FormUrlEncoded
 @POST("registerCoop.php")
 Call<Coop> registerCoop(

         @Field("firstname") String firstname,
         @Field("lastname") String lastname,
         @Field("email") String email,
         @Field("phone") int phone,
         @Field("password") String password,
         @Field("gender") String gender,
         @Field("province") String province,
         @Field("district") String district,
         @Field("street_number") int street_number,
         @Field("poultry_size") int poultry_size,
         @Field("poultry_name") String poultry_name,
         @Field("cdate") String cdate

 );


 @GET("water.php")
 Call<WaterDataModel> getWaterData();

 @FormUrlEncoded
 @POST("login.php")
 Call<User> userLogin(

         @Field("email") String email,
         @Field("password") String password


         );



}




