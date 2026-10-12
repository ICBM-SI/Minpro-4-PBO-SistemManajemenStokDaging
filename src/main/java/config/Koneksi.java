/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author LENOVO
 */
public class Koneksi {
    private static final String URL_DATABASE = "jdbc:mysql://localhost:3306/db_stok_daging";
    private static final String USER_DATABASE = "root";
    private static final String PASSWORD_DATABASE = "";

    public static Connection getKoneksi() throws SQLException {
        return DriverManager.getConnection(URL_DATABASE, USER_DATABASE, PASSWORD_DATABASE);
    }
}
