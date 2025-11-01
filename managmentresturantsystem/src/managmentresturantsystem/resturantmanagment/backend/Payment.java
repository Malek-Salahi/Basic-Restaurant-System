/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managmentresturantsystem.resturantmanagment.backend;

/**
 *
 * @author hp
 */

public class Payment {
    private double totalAmount;
    private double tip;

    // Constructor
    public Payment(double totalAmount, double tip) {
        this.totalAmount = totalAmount;
        this.tip = tip;
    }

    // calculating total amount with tips
    public double calculateTotalWithTip() {
        try {
            if (totalAmount == 0) {
                throw new ArithmeticException("total amount can't be zero !");
            }
            return totalAmount + tip;
        } catch (ArithmeticException e) {
            System.out.println("error in calculation: " + e.getMessage());
            return 0; // returning default value
        }
    }
}