package com.vartika.patterns.builder.step1_telescoping;

/**
 * STEP 1 — Telescoping constructors (the PROBLEM).
 *
 * Required: brand, engine, model.  Optional: noOfWheels (4), mileage (0), colour ("white").
 * Each constructor adds ONE parameter and chains with this(...) to the next one.
 * The last one is the DESIGNATED constructor: the only place that sets fields.
 */
public class Car {
	private final int noOfWheels;
	private final double mileage;
	private final String colour;
	private final String engine;
	private final String brand;
	private final String model;

	public Car(String brand, String engine, String model) {
		this(brand, engine, model, 4);                            // default wheels
	}

	public Car(String brand, String engine, String model, int noOfWheels) {
		this(brand, engine, model, noOfWheels, 0);                // default mileage
	}

	public Car(String brand, String engine, String model, int noOfWheels, double mileage) {
		this(brand, engine, model, noOfWheels, mileage, "white"); // default colour
	}

	// ⭐ DESIGNATED constructor: every other constructor ends here.
	public Car(String brand, String engine, String model, int noOfWheels, double mileage, String colour) {
		this.brand = brand;
		this.engine = engine;
		this.model = model;
		this.noOfWheels = noOfWheels;
		this.mileage = mileage;
		this.colour = colour;
	}

	@Override
	public String toString() {
		return "Car{brand=" + brand + ", model=" + model + ", engine=" + engine
				+ ", wheels=" + noOfWheels + ", mileage=" + mileage + ", colour=" + colour + "}";
	}
}
