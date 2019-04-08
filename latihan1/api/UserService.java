package com.ukk.latihan1.api;

import com.ukk.latihan1.modul.response.ResponseInventaris;
import com.ukk.latihan1.modul.response.ResponseJenis;
import com.ukk.latihan1.modul.response.ResponseLogin;
import com.ukk.latihan1.modul.response.ResponsePeminjam;
import com.ukk.latihan1.modul.response.ResponsePeminjaman;
import com.ukk.latihan1.modul.response.ResponsePetugas;
import com.ukk.latihan1.modul.response.ResponseReport;
import com.ukk.latihan1.modul.response.ResponseRuang;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("inventaris.php")
    Call<ResponseInventaris> getInventaris();

    @FormUrlEncoded
    @POST("inventaris.php")
    Call<ResponseInventaris> getInventaris(
            @Field("keyword") String keyword
    );

    @FormUrlEncoded
    @POST("inventaris.php")
    Call<ResponseInventaris> getInventarisId(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("setInventaris.php")
    Call<String>setInventaris(
            @Field("nama") String nama,
            @Field("kondisi") String kondisi,
            @Field("keterangan") String keterangan,
            @Field("jumlah") String jumlah,
            @Field("idJenis") String idJenis,
            @Field("idRuang") String idRuang,
            @Field("idPetugas") String idPetugas
    );

    @FormUrlEncoded
    @POST("delInvent.php")
    Call<String> delnventaris(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("putInvent.php")
    Call<String>putInventaris(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("kondisi") String kondisi,
            @Field("keterangan") String keterangan,
            @Field("jumlah") String jumlah,
            @Field("idJenis") String idJenis,
            @Field("idRuang") String idRuang
    );

    //jenis
    @FormUrlEncoded
    @POST("jenis.php")
    Call<String> setJenis(
            @Field("nama") String nama,
            @Field("kode") String kode,
            @Field("keterangan") String keterangan
    );

    @GET("jenis.php")
    Call<ResponseJenis>getJenis();

    @FormUrlEncoded
    @POST("jenis.php")
    Call<String> putJenis(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("kode") String kode,
            @Field("keterangan") String keterangan
    );

    @FormUrlEncoded
    @POST("jenis.php")
    Call<String> delJenis(
            @Field("id") String id
    );

    //ruang
    @FormUrlEncoded
    @POST("ruang.php")
    Call<String> setRuang(
            @Field("nama") String nama,
            @Field("kode") String kode,
            @Field("keterangan") String keterangan
    );

    @GET("ruang.php")
    Call<ResponseRuang>getRuang();

    @FormUrlEncoded
    @POST("ruang.php")
    Call<String> putRuang(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("kode") String kode,
            @Field("keterangan") String keterangan
    );

    @FormUrlEncoded
    @POST("ruang.php")
    Call<String> delRuang(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("setPinjam.php")
    Call<String>pinjam(
            @Field("id_invent") String id_invent,
            @Field("jumlah") String jumlah,
            @Field("id_peminjam") String id_peminjam
    );

    @FormUrlEncoded
    @POST("putKembali.php")
    Call<String>kembali(
            @Field("kode") String kode
    );

    @GET("peminjaman.php")
    Call<ResponsePeminjaman> getPeminjaman();

    @FormUrlEncoded
    @POST("peminjaman.php")
    Call<ResponsePeminjaman> getPeminjaman(
            @Field("id") String id
    );

    @GET("peminjam.php")
    Call<ResponsePeminjam> getPeminjam();

    @FormUrlEncoded
    @POST("peminjam.php")
    Call<String>setPeminjam(
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("peminjam.php")
    Call<String>putPeminjam(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("delPeminjam.php")
    Call<String> delPeminjam(
            @Field("id") String id
    );

    @FormUrlEncoded
    @POST("report.php")
    Call<ResponseReport>getReport(
            @Field("dFrom") String dFrom,
            @Field("dTo") String dTo
    );

    @GET("petugas.php")
    Call<ResponsePetugas>getPetugas();

    @FormUrlEncoded
    @POST("petugas.php")
    Call<String>setPetugas(
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("petugas.php")
    Call<String>putPetugas(
            @Field("id") String id,
            @Field("nama") String nama,
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("delPetugas.php")
    Call<String> delPetugas(
            @Field("id") String id
    );

}
