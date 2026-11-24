package com.vartika.patterns.builder.step1_telescoping;

public class Main {

	public static void main(String[] args) {
		// Works, but what do the values mean? You must open Car.java to know.
		Car car = new Car("Toyota", "DieselEngine", "AWXS");
		System.out.println("basic       : " + car);

		// TRAP 1: meant 4 wheels and 15 km/l, swapped them. Compiles fine -> a 15-wheel car.
		Car swapped = new Car("Toyota", "DieselEngine", "AWXS", 15, 4);
		System.out.println("swapped     : " + swapped);

		// TRAP 2: to set only the colour, you are FORCED to pass wheels and mileage too.
		Car redOnly = new Car("Toyota", "DieselEngine", "AWXS", 4, 0, "red");
		System.out.println("red only    : " + redOnly);

		// TRAP 3: no validation. No brand, negative mileage, zero wheels — all accepted.
		Car broken = new Car(null, "DieselEngine", "AWXS", 0, -10, "red");
		System.out.println("broken      : " + broken);
	}
}
