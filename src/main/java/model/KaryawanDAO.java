/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author LENOVO
 */
public class KaryawanDAO {
    private final Connection koneksi;
    public KaryawanDAO(Connection koneksi) {
        this.koneksi = koneksi;
    }
    
    public void tambah(Karyawan karyawanBaru) throws SQLException {
        PreparedStatement perintah = koneksi.prepareStatement(
                "INSERT INTO karyawan (nama_karyawan, jabatan) VALUES (?, ?)");
        perintah.setString(1, karyawanBaru.getNamaKaryawan());
        perintah.setString(2, karyawanBaru.getJabatan());
        perintah.executeUpdate();
        perintah.close();
    }
    
    public ArrayList<Karyawan> ambilSemua() throws SQLException {
        ArrayList<Karyawan> daftarKaryawan = new ArrayList<>();
        PreparedStatement perintah = koneksi.prepareStatement(
                "SELECT id_karyawan, nama_karyawan, jabatan FROM karyawan ORDER BY id_karyawan");
        ResultSet hasil = perintah.executeQuery();
        while (hasil.next()) {
            daftarKaryawan.add(petakanBaris(hasil));
        }
        hasil.close();
        perintah.close();
        return daftarKaryawan;
    }
    
    public Karyawan cariById(int idKaryawan) throws SQLException {
        PreparedStatement perintah = koneksi.prepareStatement(
                "SELECT id_karyawan, nama_karyawan, jabatan FROM karyawan WHERE id_karyawan = ?");
        perintah.setInt(1, idKaryawan);
        ResultSet hasil = perintah.executeQuery();
        Karyawan karyawanDitemukan = null;
        if (hasil.next()) {
            karyawanDitemukan = petakanBaris(hasil);
        }
        hasil.close();
        perintah.close();
        return karyawanDitemukan;
    }
    
    private Karyawan petakanBaris(ResultSet hasil) throws SQLException {
        return new Karyawan(
                hasil.getInt("id_karyawan"),
                hasil.getString("nama_karyawan"),
                hasil.getString("jabatan"));
    }
}
