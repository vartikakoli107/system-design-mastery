package com.vartika.patterns.builder.step3_inheritance;

public class Main {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// 1. Builder: Vehicle part (reg, price) is built by super(...) inside Car
		Car car = new Car.Builder("MH12AB1234", "Toyota", "Diesel", "Fortuner")
				.price(3500000)
				.mileage(12)
				.build();
		System.out.println(car);

		// 2. Old constructor and Builder give the same car
		Car oldWay = new Car("MH12AB1234", "Toyota", "Diesel", "Fortuner");
		Car newWay = new Car.Builder("MH12AB1234", "Toyota", "Diesel", "Fortuner").build();
		System.out.println("same car? " + oldWay.toString().equals(newWay.toString()));

		// 3. A Car can be used as a Vehicle; the Car's describe() still runs
		Vehicle v = car;
		System.out.println(v.describe());

		// 4. Vehicle's rule (via super) rejects a missing registration number
		try {
			new Car.Builder(null, "Toyota", "Diesel", "Fortuner").build();
		} catch (IllegalStateException e) {
			System.out.println("rejected: " + e.getMessage());
		}
	}
}
