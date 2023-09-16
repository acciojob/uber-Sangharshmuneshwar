package com.driver.model;

import com.driver.model.Cab;
import com.driver.model.TripBooking;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Driver{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int driverId;

    String mobile;
    String password;

    @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
    Cab cab;

    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    List<TripBooking> tripBookingList2 = new ArrayList<>();

    public Driver() {
    }

    public Driver(int driverId, String mobile, String password, Cab cab, List<TripBooking> tripBookingList2) {
        this.driverId = driverId;
        this.mobile = mobile;
        this.password = password;
        this.cab = cab;
        this.tripBookingList2 = tripBookingList2;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public List<TripBooking> getTripBookingList2() {
        return tripBookingList2;
    }

    public void setTripBookingList2(List<TripBooking> tripBookingList2) {
        this.tripBookingList2 = tripBookingList2;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}