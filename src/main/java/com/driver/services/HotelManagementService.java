package com.driver.services;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import com.driver.repositories.HotelManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelManagementService {
//    @Autowired
    HotelManagementRepository hotelManagementRepository = new HotelManagementRepository();

    public String addHotel(Hotel hotel){
        return hotelManagementRepository.addHotel(hotel);
    }

    public Integer addUser(User user){
        return hotelManagementRepository.addUser(user);
    }

    public String getHotelWithMostFacilities(){
        return hotelManagementRepository.getHotelWithMostFacilities();
    }

    public int bookARoom(Booking booking){
        return hotelManagementRepository.bookARoom(booking);
    }

    public int getBookings(Integer adharCard){
        return hotelManagementRepository.getBookings(adharCard);
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName){
        return hotelManagementRepository.updateFacilities(newFacilities, hotelName);
    }
}
