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
    private static final String URL = "jdbc:mysql://localhost:3306/db_manajemenstokdaging";
    private static final String USER = "root";
    private static final String PASS = "";

    private static Connection conn;
    public static Connection getConnection() {
        try {
            if (conn == null) {
                // Jika belum ada, membuat koneksi baru
                conn = DriverManager.getConnection(URL, USER, PASS);
            }
        } catch (SQLException e) {
            System.out.println("Koneksi GAGAL: " + e.getMessage());
        }
        return conn;
    }
    public static void laporkanKesalahan(String aksi, SQLException kesalahan) {
        System.out.println("[Database] Gagal " + aksi + ": " + kesalahan.getMessage());
    }
}
