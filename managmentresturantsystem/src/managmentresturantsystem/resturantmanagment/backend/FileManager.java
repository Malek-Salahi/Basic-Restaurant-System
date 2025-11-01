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
import java.io.FileWriter;
import java.io.IOException;

class FileManager {

    // saving order to file
    public static void saveOrderToFile(Order order) {
        try {
            FileWriter writer = new FileWriter("D:\\managmentresturantsystem\\src\\managmentresturantsystem\\resturantmanagment\\backend\\saved_orders.txt", true);
            writer.write(order.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("error while writing to file: " + e.getMessage());
        } finally {
            System.out.println("done writing to file successfully");
        }
    }
}