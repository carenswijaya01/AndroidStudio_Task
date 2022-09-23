package com.example.apiservices;

import com.google.gson.annotations.SerializedName;

public class Mahasiswa {
    @SerializedName("name")
    private String name;

    public Mahasiswa(String name, int nim, int age, String address){
        this.name = name;
        this.nim = nim;
        this.age = age;
        this.address = address;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    @SerializedName("nim")
    private int nim;

    public int getNim() {
        return nim;
    }

    public void setNim(int nim) {
        this.nim = nim;
    }

    @SerializedName("age")
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @SerializedName("address")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
