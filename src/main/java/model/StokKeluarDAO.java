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
public class StokKeluarDAO {
 
    private final Connection koneksi;
 
    public StokKeluarDAO(Connection koneksi) {
        this.koneksi = koneksi;
    }
 
    public void tambah(StokKeluar stokKeluarBaru) throws SQLException {
        String sql = "INSERT INTO stok_keluar (id_daging, id_karyawan, tanggal_keluar, jumlah, "
                + "alasan, keterangan) VALUES (?, ?, STR_TO_DATE(?, '%d-%m-%Y'), ?, ?, ?)";
        PreparedStatement perintah = koneksi.prepareStatement(sql);
        perintah.setInt(1, stokKeluarBaru.getIdDaging());
        perintah.setInt(2, stokKeluarBaru.getIdKaryawan());
        perintah.setString(3, stokKeluarBaru.getTanggalTransaksi());
        perintah.setInt(4, stokKeluarBaru.getJumlah());
        perintah.setString(5, stokKeluarBaru.getAlasan());
        perintah.setString(6, stokKeluarBaru.getKeterangan());
        perintah.executeUpdate();
        perintah.close();
    }
 
    public ArrayList<StokKeluar> ambilSemua() throws SQLException {
        ArrayList<StokKeluar> daftarStokKeluar = new ArrayList<>();
        String sql = "SELECT id_stok_keluar, id_daging, id_karyawan, "
                + "DATE_FORMAT(tanggal_keluar, '%d-%m-%Y') AS tanggal_keluar, jumlah, alasan, keterangan "
                + "FROM stok_keluar ORDER BY id_stok_keluar";
        PreparedStatement perintah = koneksi.prepareStatement(sql);
        ResultSet hasil = perintah.executeQuery();
        while (hasil.next()) {
            daftarStokKeluar.add(new StokKeluar(
                    hasil.getInt("id_stok_keluar"),
                    hasil.getInt("id_daging"),
                    hasil.getInt("id_karyawan"),
                    hasil.getString("tanggal_keluar"),
                    hasil.getInt("jumlah"),
                    hasil.getString("alasan"),
                    hasil.getString("keterangan")));
        }
        hasil.close();
        perintah.close();
        return daftarStokKeluar;
    }
}
