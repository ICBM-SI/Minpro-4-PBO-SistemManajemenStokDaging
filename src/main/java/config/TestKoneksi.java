/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author LENOVO
 */
public class TestKoneksi {
    public static void main(String[] args) {
        try {
            Connection koneksi = Koneksi.getConnection();
            System.out.println("Koneksi berhasil!");
            koneksi.close();
        } catch (SQLException kesalahanKoneksi) {
            System.out.println("Koneksi gagal: " + kesalahanKoneksi.getMessage());
        }
    }
}
