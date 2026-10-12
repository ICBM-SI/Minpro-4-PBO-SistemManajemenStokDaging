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
import config.Koneksi;
/**
 *
 * @author LENOVO
 */
public class KaryawanDAO {
    private static final String SQL_PILIH_KARYAWAN = "SELECT id_karyawan, nama_karyawan, jabatan FROM karyawan";
    private final Connection koneksi;
    public KaryawanDAO(Connection koneksi){
        this.koneksi = koneksi;
    }
    
    public boolean tambah(Karyawan karyawanBaru){
        try {
            PreparedStatement perintah = koneksi.prepareStatement(
                    "INSERT INTO karyawan (nama_karyawan, jabatan) VALUES (?, ?)");
            perintah.setString(1, karyawanBaru.getNamaKaryawan());
            perintah.setString(2, karyawanBaru.getJabatan());
            perintah.executeUpdate();
            perintah.close();
            return true;
        } catch (SQLException kesalahan){
            Koneksi.laporkanKesalahan("menambah data karyawan", kesalahan);
            return false;
        }
    }
    
    public ArrayList<Karyawan> ambilSemua(){
        ArrayList<Karyawan> daftarKaryawan = new ArrayList<>();
        try {
            PreparedStatement perintah = koneksi.prepareStatement(SQL_PILIH_KARYAWAN + " ORDER BY id_karyawan");
            ResultSet hasil = perintah.executeQuery();
            while (hasil.next()) {
                daftarKaryawan.add(new Karyawan(
                        hasil.getInt("id_karyawan"),
                        hasil.getString("nama_karyawan"),
                        hasil.getString("jabatan")));
            }
            hasil.close();
            perintah.close();
        } catch (SQLException kesalahan){
            Koneksi.laporkanKesalahan("mengambil data karyawan", kesalahan);
        }
        return daftarKaryawan;
    }
    
    public Karyawan cariById(int idKaryawan){
        Karyawan karyawanDitemukan = null;
        try {
            PreparedStatement perintah = koneksi.prepareStatement(SQL_PILIH_KARYAWAN + " WHERE id_karyawan = ?");
            perintah.setInt(1, idKaryawan);
            ResultSet hasil = perintah.executeQuery();
            if (hasil.next()) {
                karyawanDitemukan = new Karyawan(
                        hasil.getInt("id_karyawan"),
                        hasil.getString("nama_karyawan"),
                        hasil.getString("jabatan"));
            }
            hasil.close();
            perintah.close();
        } catch (SQLException kesalahan){
            Koneksi.laporkanKesalahan("mencari data karyawan", kesalahan);
        }
        return karyawanDitemukan;
    }
}