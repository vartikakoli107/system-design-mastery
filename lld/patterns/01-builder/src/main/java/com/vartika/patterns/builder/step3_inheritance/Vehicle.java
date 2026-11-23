package com.vartika.patterns.builder.step3_inheritance;

// PARENT class. Every vehicle has a registration number and a price.
public class Vehicle {
	protected final String registrationNumber;  // protected -> child can read it directly
	private final long price;                    // private   -> child must use getPrice()

	// Only constructor of Vehicle. protected -> only child classes call it (via super).
	protected Vehicle(String registrationNumber, long price) {
		if (registrationNumber == null || registrationNumber.isBlank()) {
			throw new IllegalStateException("registrationNumber is required");
		}
		this.registrationNumber = registrationNumber;
		this.price = price;
	}

	public long getPrice() {
		return price;
	}

	public String describe() {
		return "Vehicle " + registrationNumber + ", price " + price;
	}
}
