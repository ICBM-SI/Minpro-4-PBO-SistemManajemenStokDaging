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
public class DagingDAO {
    private static final String SQL_PILIH_DAGING = "SELECT id_daging, nama_daging, bagian_daging, berat, "
            + "DATE_FORMAT(tanggal_masuk, '%d-%m-%Y') AS tanggal_masuk, "
            + "DATE_FORMAT(tanggal_expired, '%d-%m-%Y') AS tanggal_expired, "
            + "stok, status FROM daging";
    private final Connection koneksi;
 
    public DagingDAO(Connection koneksi) {
        this.koneksi = koneksi;
    }

    //create
    public boolean tambah(Daging dagingBaru) {
        String sql = "INSERT INTO daging (nama_daging, bagian_daging, berat, tanggal_masuk, "
                + "tanggal_expired, stok, status) "
                + "VALUES (?, ?, ?, STR_TO_DATE(?, '%d-%m-%Y'), STR_TO_DATE(?, '%d-%m-%Y'), ?, ?)";
        try {
            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, dagingBaru.getNamaDaging());
            perintah.setString(2, dagingBaru.getBagianDaging());
            perintah.setDouble(3, dagingBaru.getBerat());
            perintah.setString(4, dagingBaru.getTanggalMasuk());
            perintah.setString(5, dagingBaru.getTanggalExpired());
            perintah.setInt(6, dagingBaru.getStok());
            perintah.setString(7, dagingBaru.getStatus());
            perintah.executeUpdate();
            perintah.close();
            return true;
        } catch (SQLException kesalahan) {
            Koneksi.laporkanKesalahan("menambah data daging", kesalahan);
            return false;
        }
    }
    
    //read
    public ArrayList<Daging> ambilSemua() {
        ArrayList<Daging> daftarDaging = new ArrayList<>();
        try {
            PreparedStatement perintah = koneksi.prepareStatement(SQL_PILIH_DAGING + " ORDER BY id_daging");
            ResultSet hasil = perintah.executeQuery();
            while (hasil.next()) {
                daftarDaging.add(new Daging(
                        hasil.getInt("id_daging"),
                        hasil.getString("nama_daging"),
                        hasil.getString("bagian_daging"),
                        hasil.getDouble("berat"),
                        hasil.getString("tanggal_masuk"),
                        hasil.getString("tanggal_expired"),
                        hasil.getInt("stok"),
                        hasil.getString("status")));
            }
            hasil.close();
            perintah.close();
        } catch (SQLException kesalahan) {
            Koneksi.laporkanKesalahan("mengambil data daging", kesalahan);
        }
        return daftarDaging;
    }

    public Daging cariById(int idDaging) {
        Daging dagingDitemukan = null;
        try {
            PreparedStatement perintah = koneksi.prepareStatement(SQL_PILIH_DAGING + " WHERE id_daging = ?");
            perintah.setInt(1, idDaging);
            ResultSet hasil = perintah.executeQuery();
            if (hasil.next()) {
                dagingDitemukan = new Daging(
                        hasil.getInt("id_daging"),
                        hasil.getString("nama_daging"),
                        hasil.getString("bagian_daging"),
                        hasil.getDouble("berat"),
                        hasil.getString("tanggal_masuk"),
                        hasil.getString("tanggal_expired"),
                        hasil.getInt("stok"),
                        hasil.getString("status"));
            }
            hasil.close();
            perintah.close();
        } catch (SQLException kesalahan) {
            Koneksi.laporkanKesalahan("mencari data daging", kesalahan);
        }
        return dagingDitemukan;
    }
    
    //update
    public boolean ubah(Daging dagingDiubah) {
        String sql = "UPDATE daging SET nama_daging = ?, stok = ?, status = ? WHERE id_daging = ?";
        try {
            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setString(1, dagingDiubah.getNamaDaging());
            perintah.setInt(2, dagingDiubah.getStok());
            perintah.setString(3, dagingDiubah.getStatus());
            perintah.setInt(4, dagingDiubah.getIdDaging());
            perintah.executeUpdate();
            perintah.close();
            return true;
        } catch (SQLException kesalahan) {
            Koneksi.laporkanKesalahan("mengubah data daging", kesalahan);
            return false;
        }
    }
    
    //dilet
    public boolean hapus(int idDaging) {
        try {
            PreparedStatement perintah = koneksi.prepareStatement("DELETE FROM daging WHERE id_daging = ?");
            perintah.setInt(1, idDaging);
            perintah.executeUpdate();
            perintah.close();
            return true;
        } catch (SQLException kesalahan) {
            Koneksi.laporkanKesalahan("menghapus data daging", kesalahan);
            return false;
        }
    }
    
    public boolean ubahStok(int idDaging, int selisihStok) {
        try {
            PreparedStatement perintah = koneksi.prepareStatement(
                    "UPDATE daging SET stok = stok + ? WHERE id_daging = ?");
            perintah.setInt(1, selisihStok);
            perintah.setInt(2, idDaging);
            perintah.executeUpdate();
            perintah.close();
            return true;
        } catch (SQLException kesalahan) {
            Koneksi.laporkanKesalahan("memperbarui stok daging", kesalahan);
            return false;
        }
    }
    
    public boolean punyaRiwayatTransaksi(int idDaging) {
        String sql = "SELECT (SELECT COUNT(*) FROM stok_masuk WHERE id_daging = ?) "
                + "+ (SELECT COUNT(*) FROM stok_keluar WHERE id_daging = ?) AS total";
        try {
            PreparedStatement perintah = koneksi.prepareStatement(sql);
            perintah.setInt(1, idDaging);
            perintah.setInt(2, idDaging);
            ResultSet hasil = perintah.executeQuery();
            hasil.next();
            int totalTransaksi = hasil.getInt("total");
            hasil.close();
            perintah.close();
            return totalTransaksi > 0;
        } catch (SQLException kesalahan) {
            Koneksi.laporkanKesalahan("memeriksa riwayat transaksi daging", kesalahan);
            return true;
        }
    }
}
