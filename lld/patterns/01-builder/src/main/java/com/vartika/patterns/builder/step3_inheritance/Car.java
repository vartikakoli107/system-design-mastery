package com.vartika.patterns.builder.step3_inheritance;

// CHILD class. A Car IS A Vehicle + its own fields.
public class Car extends Vehicle {

	private final String brand;
	private final String engine;
	private final String model;
	private final int noOfWheels;
	private final double mileage;
	private final String colour;

	// private -> the only way to make a Car is Builder.build()
	private Car(Builder builder) {
		super(builder.registrationNumber, builder.price);  // 1. build the Vehicle part FIRST
		this.brand = builder.brand;                        // 2. then the Car part
		this.engine = builder.engine;
		this.model = builder.model;
		this.noOfWheels = builder.noOfWheels;
		this.mileage = builder.mileage;
		this.colour = builder.colour;
	}

	// OLD constructor kept for old callers. One line: forwards to the Builder.
	@Deprecated
	public Car(String registrationNumber, String brand, String engine, String model) {
		this(new Builder(registrationNumber, brand, engine, model));
	}

	@Override
	public String describe() {
		return super.describe() + " -> " + brand + " " + model;  // parent's text + car's text
	}

	@Override
	public String toString() {
		return "Car{reg=" + registrationNumber + ", price=" + getPrice()
				+ ", brand=" + brand + ", engine=" + engine + ", model=" + model
				+ ", wheels=" + noOfWheels + ", mileage=" + mileage + ", colour=" + colour + "}";
	}

	public static class Builder {
		// required (same order everywhere: reg, brand, engine, model)
		private final String registrationNumber;
		private final String brand;
		private final String engine;
		private final String model;

		// optional, with defaults
		private long price = 0;
		private int noOfWheels = 4;
		private double mileage = 0;
		private String colour = "white";

		public Builder(String registrationNumber, String brand, String engine, String model) {
			this.registrationNumber = registrationNumber;
			this.brand = brand;
			this.engine = engine;
			this.model = model;
		}

		public Builder price(long price) {
			this.price = price;
			return this;
		}

		public Builder noOfWheels(int noOfWheels) {
			this.noOfWheels = noOfWheels;
			return this;
		}

		public Builder mileage(double mileage) {
			this.mileage = mileage;
			return this;
		}

		public Builder colour(String colour) {
			this.colour = colour;
			return this;
		}

		public Car build() {
			if (brand == null || brand.isBlank()) {
				throw new IllegalStateException("brand is required");
			}
			if (mileage < 0) {
				throw new IllegalStateException("mileage cannot be negative");
			}
			return new Car(this);
		}
	}
}
