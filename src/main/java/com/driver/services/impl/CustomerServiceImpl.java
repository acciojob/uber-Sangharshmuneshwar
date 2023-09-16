package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
        customerRepository2.deleteById(customerId);

	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query

		List<Driver> drivers = new ArrayList<>();
		drivers = driverRepository2.findAll();

		Driver currDriver = null;
		for(Driver driver : drivers){
			if(driver.getCab().getAvailable()){
				currDriver = driver;
				break;
			}
		}

		if(currDriver == null){
			throw new Exception("No cab available!");
		}

		Optional<Customer> customerOptional = customerRepository2.findById(customerId);

		if(!customerOptional.isPresent()){
			throw new Exception("Customer is not present!");
		}

		currDriver.getCab().setAvailable(false);
		Customer currCustomer = customerOptional.get();



		TripBooking tripBooking = new TripBooking();
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setBill(0);
		tripBooking.setStatus(TripStatus.CONFIRMED);
		tripBooking.setDriver(currDriver);
		tripBooking.setCustomer(customerOptional.get());

		List<TripBooking> tripBookings = currDriver.getTripBookingList2();
		tripBookings.add(tripBooking);

		List<TripBooking> tripBookings2 = currCustomer.getTripBookingList();
		tripBookings2.add(tripBooking);

		customerRepository2.save(currCustomer);
		driverRepository2.save(currDriver);
		tripBookingRepository2.save(tripBooking);
		return tripBooking;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> tripBooking = tripBookingRepository2.findById(tripId);
		if (!tripBooking.isPresent())
			return;

		TripBooking currBooking = tripBooking.get();
		Driver driver = currBooking.getDriver();
		driver.getCab().setAvailable(true);
		currBooking.setBill(0);
		currBooking.setStatus(TripStatus.CANCELED);
		tripBookingRepository2.save(currBooking);

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> currTrip = tripBookingRepository2.findById(tripId);
		if (!currTrip.isPresent())
			return;

		TripBooking tripBooking = currTrip.get();
		tripBooking.setStatus(TripStatus.COMPLETED);
		Driver driver = tripBooking.getDriver();
		driver.getCab().setAvailable(true);
		tripBookingRepository2.save(tripBooking);

	}
}
