package com.vartika;

import com.vartika.Booking.Seat;
import com.vartika.exception.SeatUnavailableException;
import com.vartika.repositories.BookingRepository;
import com.vartika.service.BookingService;
import com.vartika.service.SeatService;

public class RaceLab {
	public static void main(String[] args) throws Exception {
		// set up the system
		SeatService seatService = new SeatService();
		seatService.addSeat(new Seat("abc"));
		BookingService bookingService = new BookingService(seatService, new BookingRepository());
		
		// what request does it that it asks for seat A with idkey 123
		
		Runnable request = () -> {
				try {
					bookingService.createBooking("A", "abc", "123");
					System.out.println("Current thread name is :" + Thread.currentThread().getName());
				} catch (SeatUnavailableException e) {
					System.out.println("Exception occurred with thread name:"+ Thread.currentThread().getName() + "for user: A. ERROR" + e.getMessage());
				}
		};
		
		Thread t1 = new Thread(request);
		Thread t2 = new Thread(request);
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
	}
}
