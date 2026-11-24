package com.vartika.patterns.builder.step2_builder;

/**
 * STEP 2 — Builder (the FIX).
 *
 * - Car is immutable: final fields, no setters.
 * - The constructor is private: the ONLY way to get a Car is Builder.build().
 * - Required values go in the Builder's constructor; optional values are methods with defaults.
 * - build() checks the rules before creating the Car.
 */
public class Car {

	private final int noOfWheels;
	private final double mileage;
	private final String colour;
	private final String engine;
	private final String brand;
	private final String model;

	// private: callers cannot do "new Car(...)", they must use the Builder.
	private Car(Builder builder) {
		this.brand = builder.brand;
		this.engine = builder.engine;
		this.model = builder.model;
		this.noOfWheels = builder.noOfWheels;
		this.mileage = builder.mileage;
		this.colour = builder.colour;
	}

	// Static factory method: Car.builder(..) reads nicer than new Car.Builder(..). Both work.
	public static Builder builder(String brand, String engine, String model) {
		return new Builder(brand, engine, model);
	}

	public static class Builder {

		// required -> final, no default
		private final String brand;
		private final String engine;
		private final String model;

		// optional -> not final, default lives HERE (one place)
		private int noOfWheels = 4;
		private double mileage = 0;
		private String colour = "white";

		public Builder(String brand, String engine, String model) {
			this.brand = brand;
			this.engine = engine;
			this.model = model;
		}

		public Builder noOfWheels(int noOfWheels) {
			this.noOfWheels = noOfWheels;
			return this;                       // same builder back -> allows .a().b().c()
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
			// Rules are checked ONCE, right before the Car exists.
			requireText(brand, "brand");
			requireText(engine, "engine");
			requireText(model, "model");
			requireText(colour, "colour");
			if (noOfWheels <= 0) {
				throw new IllegalStateException("noOfWheels must be > 0, got " + noOfWheels);
			}
			if (mileage < 0) {
				throw new IllegalStateException("mileage cannot be negative, got " + mileage);
			}
			return new Car(this);
		}

		private static void requireText(String value, String name) {
			if (value == null || value.isBlank()) {
				throw new IllegalStateException(name + " is required");
			}
		}
	}

	@Override
	public String toString() {
		return "Car{brand=" + brand + ", model=" + model + ", engine=" + engine
				+ ", wheels=" + noOfWheels + ", mileage=" + mileage + ", colour=" + colour + "}";
	}
}
