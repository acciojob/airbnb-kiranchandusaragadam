package com.driver.repositories;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HotelManagementRepository {
    private HashMap<String, Hotel> hotelsMap;
    private HashMap<Integer, User> usersMap;
    private HashMap<String, Booking> bookingsMap;
    private HashMap<Integer, List<String>> adharVsBookings;

    public HotelManagementRepository(){
        this.hotelsMap = new HashMap<>();
        this.usersMap = new HashMap<>();
        this.bookingsMap = new HashMap<>();
        this.adharVsBookings = new HashMap<>();
    }

    public String addHotel(Hotel hotel){
        String hName = hotel.getHotelName();

        if(hName.length() == 0 || hotel == null || hotelsMap.containsKey(hName)){
            return "FAILURE";
        }
        else{
            hotelsMap.put(hName, hotel);

            return "SUCCESS";
        }
    }

    public Integer addUser(User user){
        int adharNo = user.getaadharCardNo();

        if(!usersMap.containsKey(adharNo)){
            usersMap.put(adharNo, user);
        }

        return adharNo;
    }

    public String getHotelWithMostFacilities(){
        if(hotelsMap.size() == 0){
            return "";
        }

        String mostFacilitiesHotel = "";

        int maxFacilities = 0;

        for(String hn : hotelsMap.keySet()){
            int facilities = hotelsMap.get(hn).getFacilities().size();
            if(facilities > maxFacilities){
                mostFacilitiesHotel = hn;
                maxFacilities = facilities;
            }
            else if(maxFacilities > 0 && facilities == maxFacilities){
                if(mostFacilitiesHotel.compareTo(hn) > 0){
                    mostFacilitiesHotel = hn;
                }
            }
        }

        return mostFacilitiesHotel;
    }

    public int bookARoom(Booking booking){
        int roomsRequired = booking.getNoOfRooms();
        String hName = booking.getHotelName();

        if(hotelsMap.containsKey(hName) && roomsRequired <= hotelsMap.get(hName).getAvailableRooms()){
            Hotel hotel = hotelsMap.get(hName);
            // generate random UUID
            String bId = UUID.randomUUID().toString();
            int amountToBePaid = roomsRequired * hotel.getPricePerNight();

            booking.setBookingId(bId);
            booking.setAmountToBePaid(amountToBePaid);
            // add booking record
            bookingsMap.put(bId, booking);

            // deduct available rooms from that hotel
            hotel.setAvailableRooms(hotel.getAvailableRooms()-roomsRequired);

            // update bookings under person
            int adharNo = booking.getBookingAadharCard();
            if(!adharVsBookings.containsKey(adharNo)){
                adharVsBookings.put(adharNo, new ArrayList<>());
            }
            adharVsBookings.get(adharNo).add(bId);

            return amountToBePaid;
        }

        return -1;
    }

    public int getBookings(Integer adharcard){
        if(adharVsBookings.containsKey(adharcard)){
            return adharVsBookings.get(adharcard).size();
        }
        return 0;
    }

    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName){
        if(hotelsMap.containsKey(hotelName)){
            Hotel hotel = hotelsMap.get(hotelName);
            List<Facility> oldFacilities = hotel.getFacilities();

            for(Facility nf : newFacilities){
                if(!oldFacilities.contains(nf)){
                    oldFacilities.add(nf);
                }
            }

            hotel.setFacilities(oldFacilities);

            return hotel;
        }

        return null;
    }
}
