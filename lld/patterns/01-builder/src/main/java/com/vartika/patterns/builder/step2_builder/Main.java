package com.vartika.patterns.builder.step2_builder;

public class Main {
	public static void main(String[] args) {
		// Only required values -> defaults for the rest.
		Car basic = new Car.Builder("Toyota", "Diesel", "ABC").build();
		System.out.println("basic       : " + basic);

		// Every value has a NAME -> you cannot swap wheels and mileage by accident.
		Car custom = new Car.Builder("Toyota", "Diesel", "ABC")
				.mileage(15)
				.noOfWheels(4)
				.build();
		System.out.println("custom      : " + custom);

		// Set ONLY the colour. No need to pass wheels or mileage. Order does not matter.
		Car redOnly = Car.builder("Toyota", "Diesel", "ABC")   // static factory method
				.colour("red")
				.build();
		System.out.println("red only    : " + redOnly);

		// Validation: bad values are rejected in build().
		try {
			Car.builder("Toyota", "Diesel", "ABC").mileage(-10).build();
		} catch (IllegalStateException e) {
			System.out.println("rejected    : " + e.getMessage());
		}
		try {
			Car.builder(null, "Diesel", "ABC").build();
		} catch (IllegalStateException e) {
			System.out.println("rejected    : " + e.getMessage());
		}

		// new Car(...)  -> does NOT compile here: the constructor is private.
	}
}
