package com.example.apiservices;

import com.google.gson.annotations.SerializedName;

public class Pegawai {
    @SerializedName("salary")
    private String salary;

    public Pegawai(String salary) {
        this.salary = salary;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
