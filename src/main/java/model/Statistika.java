package model;

public class Statistika {

    private int activeReservations;
    private int activeRentals;
    private int availableEquipment;
    private int rentedEquipment;
    private double totalRevenue;

    public Statistika(int activeReservations,
                      int activeRentals,
                      int availableEquipment,
                      int rentedEquipment,
                      double totalRevenue) {
        this.activeReservations = activeReservations;
        this.activeRentals = activeRentals;
        this.availableEquipment = availableEquipment;
        this.rentedEquipment = rentedEquipment;
        this.totalRevenue = totalRevenue;
    }

    public int getActiveReservations() {
        return activeReservations;
    }

    public int getActiveRentals() {
        return activeRentals;
    }

    public int getAvailableEquipment() {
        return availableEquipment;
    }

    public int getRentedEquipment() {
        return rentedEquipment;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }
}