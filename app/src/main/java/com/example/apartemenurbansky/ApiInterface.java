package com.example.apartemenurbansky;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("get_pets.php")
    Call<List<Pets>> getPets(
            @Field("key") String key,
            @Field("name") String name);


    @FormUrlEncoded
    @POST("add_pet.php")
    Call<Pets> insertPet(
            @Field("key") String key,
            @Field("name") String name,
            @Field("species") String species,
            @Field("breed") String breed,
            @Field("gender") String gender,
            @Field("birth") String birth,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("update_pet.php")
    Call<Pets> updatePet(
            @Field("key") String key,
            @Field("id") int id,
            @Field("name") String name,
            @Field("species") String species,
            @Field("breed") String breed,
            @Field("gender") String gender,
            @Field("birth") String birth,
            @Field("picture") String picture);

    @FormUrlEncoded
    @POST("delete_pet.php")
    Call<Pets> deletePet(
            @Field("key") String key,
            @Field("id") int id);

    @FormUrlEncoded
    @POST("update_love.php")
    Call<Pets> updateLove(
            @Field("key") String key,
            @Field("id") int id,
            @Field("love") boolean love);

    @FormUrlEncoded
    @POST("add_pameran.php")
    Call<ResponseData> addPameran(
            @Field("key") String key,
            @Field("nama_sales") String nama_sales,
            @Field("jenis_event") String jenis_event,
            @Field("name") String name,
            @Field("no_tlp") String no_tlp,
            @Field("alamat") String alamat,
            @Field("tanggal") String tanggal,
            @Field("keterangan") String keterangan,
            @Field("status") String status);

    @FormUrlEncoded
    @POST("get_pameran.php")
    Call<ResponseData> getPameran(
            @Field("key") String key,
            @Field("nama_sales") String nama_sales);


    @FormUrlEncoded
    @POST("update_pameran.php")
    Call<ResponseData> updatePameran(
            @Field("key") String key,
            @Field("id") int id,
            @Field("jenis_event") String jenis_event,
            @Field("name") String name,
            @Field("no_tlp") String no_tlp,
            @Field("alamat") String alamat,
            @Field("tanggal") String tanggal,
            @Field("keterangan") String keterangan,
            @Field("status") String status);

    @FormUrlEncoded
    @POST("delete_pameran.php")
    Call<ResponseData> deletePameran(
            @Field("key") String key,
            @Field("id") int id);

    @FormUrlEncoded
    @POST("add_sales.php")
    Call<DataSales> addSales(
            @Field("key") String key,
            @Field("nama_sales") String jenis_event,
            @Field("jabatan") String name,
            @Field("jenis_kel") String no_tlp,
            @Field("jenis_kegiatan") String alamat,
            @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("add_absen_datang.php")
    Call<DataAbsenDatang> addAbsenDatang(
                    @Field("key") String key,
                    @Field("nama") String nama_absen_datang,
                    @Field("lokasi") String lokasi_absen_datang,
                    @Field("tanggal") String waktu,
                    @Field("foto") String foto);

    @FormUrlEncoded
    @POST("add_absen_pulang.php")
    Call<DataAbsenPulang> addAbsenPulang(
            @Field("key") String key,
            @Field("nama") String nama_absen_pulang,
            @Field("lokasi") String lokasi_absen_pulang,
            @Field("tanggal") String waktu,
            @Field("foto") String foto);


}
