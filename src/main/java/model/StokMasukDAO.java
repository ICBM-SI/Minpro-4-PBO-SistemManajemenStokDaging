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
public class StokMasukDAO {
 
    private final Connection koneksi;
 
    public StokMasukDAO(Connection koneksi) {
        this.koneksi = koneksi;
    }
 
    public void tambah(StokMasuk stokMasukBaru) throws SQLException {
        String sql = "INSERT INTO stok_masuk (id_daging, id_supplier, id_karyawan, tanggal_masuk, "
                + "jumlah, keterangan) VALUES (?, ?, ?, STR_TO_DATE(?, '%d-%m-%Y'), ?, ?)";
        PreparedStatement perintah = koneksi.prepareStatement(sql);
        perintah.setInt(1, stokMasukBaru.getIdDaging());
        perintah.setInt(2, stokMasukBaru.getIdSupplier());
        perintah.setInt(3, stokMasukBaru.getIdKaryawan());
        perintah.setString(4, stokMasukBaru.getTanggalTransaksi());
        perintah.setInt(5, stokMasukBaru.getJumlah());
        perintah.setString(6, stokMasukBaru.getKeterangan());
        perintah.executeUpdate();
        perintah.close();
    }
 
    public ArrayList<StokMasuk> ambilSemua() throws SQLException {
        ArrayList<StokMasuk> daftarStokMasuk = new ArrayList<>();
        String sql = "SELECT id_stok_masuk, id_daging, id_supplier, id_karyawan, "
                + "DATE_FORMAT(tanggal_masuk, '%d-%m-%Y') AS tanggal_masuk, jumlah, keterangan "
                + "FROM stok_masuk ORDER BY id_stok_masuk";
        PreparedStatement perintah = koneksi.prepareStatement(sql);
        ResultSet hasil = perintah.executeQuery();
        while (hasil.next()) {
            daftarStokMasuk.add(new StokMasuk(
                    hasil.getInt("id_stok_masuk"),
                    hasil.getInt("id_daging"),
                    hasil.getInt("id_supplier"),
                    hasil.getInt("id_karyawan"),
                    hasil.getString("tanggal_masuk"),
                    hasil.getInt("jumlah"),
                    hasil.getString("keterangan")));
        }
        hasil.close();
        perintah.close();
        return daftarStokMasuk;
    }
 
    // Tidak ada tabel master supplier, jadi ID supplier dibuat otomatis:
    // nomor terbesar yang sudah ada + 1 (user tidak perlu mengetiknya).
    public int ambilIdSupplierBerikutnya() throws SQLException {
        PreparedStatement perintah = koneksi.prepareStatement(
                "SELECT COALESCE(MAX(id_supplier), 0) + 1 AS id_berikutnya FROM stok_masuk");
        ResultSet hasil = perintah.executeQuery();
        hasil.next();
        int idSupplierBerikutnya = hasil.getInt("id_berikutnya");
        hasil.close();
        perintah.close();
        return idSupplierBerikutnya;
    }
}
