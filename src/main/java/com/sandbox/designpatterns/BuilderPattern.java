package com.sandbox.designpatterns;

public class BuilderPattern {

    public static void main(String[] args) {
        Car car = Car.builder().withCompany("Ford").build();
        System.out.println(car.toString());
    }

    static class Car {
        private Car() {}
        String name;
        String company;
        int make;

        public static CarBuilder builder() {
            return new CarBuilder();
        }

        @Override
        public String toString() {
            return "Car{" +
                    "name='" + name + '\'' +
                    ", company='" + company + '\'' +
                    ", make=" + make +
                    '}';
        }
    }

    static class CarBuilder {
        private CarBuilder() {}
        private Car car = new Car();

        public CarBuilder withCompany(String company) {
            car.company = company;
            return this;
        }

        public Car build() {
            return car;
        }
    }

}
