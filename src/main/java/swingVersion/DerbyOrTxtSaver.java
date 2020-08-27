package swingVersion;

import consoleVersion.FuelCost;
import consoleVersion.Trip;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

class DerbyOrTxtSaver implements IO_operations {

    @Override
    public String saveData(Trip trip) {// zapis do bazy danych

        try (
                Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/D:/Databases/Baza Adama",
                        "Adam", "1234")) {
            Statement mkTable = conn.createStatement();
            mkTable.executeUpdate(
                    "CREATE TABLE cardata(lpgOn100km double, lpgPrice double, kmOnLPG double, pb95On100km double, pb95Price double, kmOnPB95 double)");
        } catch (SQLException ole) {
            System.out.println(ole.getMessage());
        }
        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/D:/Databases/Baza Adama",
                "Adam", "1234");
             PreparedStatement upTable = conn.prepareStatement("INSERT INTO "
                     + "ADAM.CARDATA(lpgOn100km, lpgPrice, kmOnLPG, pb95On100km, pb95Price, kmOnPB95)\r\n"
                     + "VALUES(?,?,?,?,?,?)"))
        // "UPDATE ADAM.CARDATA SET DLPGON100KM=?" gdy chcemy nadpisac wszystkie wiersze
        // lub wybieramy wg klucza z WHERE. Nie dziala, gdy brak wierszy
        {
            upTable.setDouble(1, trip.getLpgOn100Km());
            upTable.setDouble(2, trip.getLpgPrice());
            upTable.setDouble(3, trip.getLpgOn100Km());
            upTable.setDouble(4, trip.getPbOn100Km());
            upTable.setDouble(5, trip.getPbPrice());
            upTable.setDouble(6, trip.getKmOnPb());
            upTable.executeUpdate();
            return "Wartości zostały zapisane w bazie danych";
        } catch (SQLException ole) {
            String message = "Błąd zapisu do bazy danych";
            JOptionPane.showMessageDialog(null, message);
            System.out.println(ole.getMessage());
            return message;
        }

    }

    @Override
     public String readData(Trip trip) {

        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/D:/Databases/Baza Adama",
                "Adam", "1234"); PreparedStatement upTable = conn.prepareStatement("SELECT*FROM ADAM.CARDATA")) {
            ResultSet result = upTable.executeQuery();

            while (result.next()) {
                trip.setLpgOn100Km(result.getDouble("LPGON100KM"));
                trip.setLpgPrice(result.getDouble("LPGPRICE"));
                trip.setKmOnLpg(result.getDouble("KMONLPG"));
                trip.setPbOn100Km(result.getDouble("PB95ON100KM"));
                trip.setPbPrice(result.getDouble("PB95PRICE"));
                trip.setKmOnPb(result.getDouble("KMONPB95"));

            }
            return "Wczytano ostatnio zapisane dane";
        } catch (SQLException ole) {
            System.out.println(ole.getMessage());
            return "Brak rekordów, najpierw wykonaj zapis danych!";
        }

    }
    @Override
    public String saveData(Trip trip, FuelCost fuelCost) {// zapis do pliku txt
        Path plik = Paths.get("Dane.txt");
        try (BufferedWriter pw1 = Files.newBufferedWriter(plik, StandardCharsets.UTF_16);
             // PrintWriter pw = new PrintWriter(new BufferedWriter(new
             // FileWriter("Dane.txt")));
        ) {
            pw1.write("Spalanie LPG na 100km: " + trip.getLpgOn100Km() + "\n" + "Cena LPG:"
                    + trip.getLpgPrice() + "\n" + "Ilość kilometrów na LPG: " + trip.getKmOnLpg() + "\n"
                    + "Spalanie pb95 na 100km: " + trip.getPbOn100Km() + "\n" + "Cena PB95: "
                    + trip.getPbPrice() + "\n" + "Ilość kilometrów na pb95: " + trip.getKmOnPb() + "\n"
                    + "Koszt trasy wyniesie: " + fuelCost.getCost());
            return "OK";

        } catch (IOException e) {
            String err = "Błąd we/wy";
            System.err.println(err);
            e.printStackTrace();
            return err;
        }

    }

}

