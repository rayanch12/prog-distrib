package com.cherifi.Rentacar;

import java.time.LocalDate;

public class Car {
    private String brand;
    private String plateNumber;
    private int year;
    private boolean available;
    private LocalDate rentalStartDate;  
    private LocalDate rentalEndDate; 

    // Constructeur
    public Car(String brand, String plateNumber, int year, boolean available) {
        this.brand = brand;
        this.plateNumber = plateNumber;
        this.year = year;
        this.available = available;
    }

    // Getters et Setters pour les nouveaux champs
    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDate getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(LocalDate rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    // Getters et Setters existants
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // toString
    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", year=" + year +
                ", available=" + available +
                ", rentalStartDate=" + rentalStartDate +
                ", rentalEndDate=" + rentalEndDate +
                '}';
    }
}
