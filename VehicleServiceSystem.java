package Sql_project;

import java.sql.*;
import java.util.Scanner;

public class VehicleServiceSystem {

    static final String url = "jdbc:mysql://localhost:3306/vehicle_service";
    static final String user = "root";
    static final String pass = "";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== VEHICLE SERVICE MANAGEMENT =====");
            System.out.println("1. Add Service Record");
            System.out.println("2. View All Records");
            System.out.println("3. Search by Vehicle Number");
            System.out.println("4. Update Service Record");
            System.out.println("5. Delete Service Record");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addRecord();
                    break;

                case 2:
                    viewAll();
                    break;

                case 3:
                    searchRecord();
                    break;

                case 4:
                    updateRecord();
                    break;

                case 5:
                    deleteRecord();
                    break;

                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice, try again!");
            }
        }
    }

    // ADD RECORD
    public static void addRecord() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Owner Name: ");
            String owner = sc.nextLine();

            System.out.print("Vehicle No: ");
            String vehicle = sc.nextLine();

            System.out.print("Service Type: ");
            String type = sc.nextLine();

            System.out.print("Service Date (YYYY-MM-DD): ");
            String date = sc.nextLine();

            System.out.print("Amount: ");
            double amount = sc.nextDouble();

            Connection con = DriverManager.getConnection(url, user, pass);
            String sql = "INSERT INTO service_records(owner_name, vehicle_no, service_type, service_date, amount) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, owner);
            stmt.setString(2, vehicle);
            stmt.setString(3, type);
            stmt.setString(4, date);
            stmt.setDouble(5, amount);

            stmt.executeUpdate();
            con.close();

            System.out.println("Record Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW ALL
    public static void viewAll() {
        try {
            Connection con = DriverManager.getConnection(url, user, pass);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM service_records");

            System.out.println("\n===== SERVICE RECORDS =====");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("service_id"));
                System.out.println("Owner: " + rs.getString("owner_name"));
                System.out.println("Vehicle No: " + rs.getString("vehicle_no"));
                System.out.println("Service Type: " + rs.getString("service_type"));
                System.out.println("Date: " + rs.getDate("service_date"));
                System.out.println("Amount: ₹" + rs.getDouble("amount"));
                System.out.println("-----------------------------------");
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SEARCH BY VEHICLE NUMBER
    public static void searchRecord() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Vehicle Number: ");
            String vehicle = sc.nextLine();

            Connection con = DriverManager.getConnection(url, user, pass);
            String sql = "SELECT * FROM service_records WHERE vehicle_no=?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, vehicle);
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No record found!");
            }

            while (rs.next()) {
                System.out.println("\n===== RECORD FOUND =====");
                System.out.println("ID: " + rs.getInt("service_id"));
                System.out.println("Owner: " + rs.getString("owner_name"));
                System.out.println("Vehicle No: " + rs.getString("vehicle_no"));
                System.out.println("Service Type: " + rs.getString("service_type"));
                System.out.println("Date: " + rs.getDate("service_date"));
                System.out.println("Amount: ₹" + rs.getDouble("amount"));
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // UPDATE RECORD
    public static void updateRecord() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Service ID to Update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("New Service Type: ");
            String type = sc.nextLine();

            System.out.print("New Amount: ");
            double amount = sc.nextDouble();

            Connection con = DriverManager.getConnection(url, user, pass);
            String sql = "UPDATE service_records SET service_type=?, amount=? WHERE service_id=?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, type);
            stmt.setDouble(2, amount);
            stmt.setInt(3, id);

            int rows = stmt.executeUpdate();
            con.close();

            if (rows > 0)
                System.out.println("Record Updated Successfully!");
            else
                System.out.println("Service ID Not Found!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // DELETE RECORD
    public static void deleteRecord() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Service ID to Delete: ");
            int id = sc.nextInt();

            Connection con = DriverManager.getConnection(url, user, pass);
            String sql = "DELETE FROM service_records WHERE service_id=?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            con.close();

            if (rows > 0)
                System.out.println("Record Deleted Successfully!");
            else
                System.out.println("No record found!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
