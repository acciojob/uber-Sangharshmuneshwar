package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int adminId;
    private String username;
   private String password;

//   @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
//    List<Customer> customerList = new ArrayList<>();
//
//   @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
//   List<Driver> driverList = new ArrayList<>();

    public Admin() {
    }

    public Admin(int adminId, String username, String password) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}