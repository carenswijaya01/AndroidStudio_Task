package com.example.apiservices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIList {
    @GET("mahasiswa")
    Call<ArrayList<Mahasiswa>> getAllMahasiswa();

    @GET("pegawai")
    Call<ArrayList<Pegawai>> getAllPegawai();
}
