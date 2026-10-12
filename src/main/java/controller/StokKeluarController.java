/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.DagingDAO;
import model.KaryawanDAO;
import model.StokKeluarDAO;
import model.Daging;
import model.Karyawan;
import model.StokKeluar;
import view.DagingView;
import java.sql.SQLException;
/**
 *
 * @author LENOVO
 */
public class StokKeluarController {
    private final StokKeluarDAO stokKeluarDAO;
    private final DagingDAO dagingDAO;
    private final KaryawanDAO karyawanDAO;
    private final DagingView view;
    
    public StokKeluarController(StokKeluarDAO stokKeluarDAO, DagingDAO dagingDAO,
        KaryawanDAO karyawanDAO, DagingView view){
        this.stokKeluarDAO = stokKeluarDAO;
        this.dagingDAO = dagingDAO;
        this.karyawanDAO = karyawanDAO;
        this.view = view;
    }
    
    public void jalankanMenu(){
        int pilihanMenu;
        do {
            view.tampilkanMenu("Menu Stok Keluar", new String[]{
                    "1. Catat Stok Keluar",
                    "2. Lihat Data Stok Keluar",
                    "0. Kembali ke Menu Utama"});
            pilihanMenu = view.bacaAngka("Pilih menu: ", 0, 2);
            try {
                if (pilihanMenu == 1) {
                    catatStokKeluar();
                } else if (pilihanMenu == 2) {
                    tampilkanDaftarStokKeluar();
                }
            } catch (SQLException kesalahanDatabase) {
                view.tampilkanPesan("Terjadi kesalahan database: " + kesalahanDatabase.getMessage());
            }
        } while (pilihanMenu != 0);
    }
    
    private void catatStokKeluar() throws SQLException {
        view.tampilkanTabelDaging(dagingDAO.ambilSemua());
        int idDagingDipilih = view.bacaAngkaPositif("ID daging yang keluar (lihat daftar di atas): ");
        Daging dagingTerpilih = dagingDAO.cariById(idDagingDipilih);
        if (dagingTerpilih == null) {
            view.tampilkanPesan("ID daging tidak ditemukan!");
            return;
        }
        
        view.tampilkanTabelKaryawan(karyawanDAO.ambilSemua());
        int idKaryawanDipilih = view.bacaAngkaPositif("ID karyawan yang mencatat (lihat daftar di atas): ");
        Karyawan karyawanTerpilih = karyawanDAO.cariById(idKaryawanDipilih);
        if (karyawanTerpilih == null) {
            view.tampilkanPesan("ID karyawan tidak ditemukan!");
            return;
        }
        
        String tanggalKeluar = view.bacaTanggal("Tanggal keluar");
        int jumlah = view.bacaAngkaPositif("Jumlah daging keluar (contoh: 3): ");
        
        if (jumlah > dagingTerpilih.getStok()) {
            view.tampilkanPesan("Stok tidak mencukupi! Stok tersedia saat ini: " + dagingTerpilih.getStok());
            return;
        }
        
        String alasan = view.bacaTeks("Alasan", "Penjualan ke pelanggan");
        String keterangan = view.bacaTeks("Keterangan", "Dijual ke pelanggan tetap");

        StokKeluar stokKeluarBaru = new StokKeluar(0, idDagingDipilih, idKaryawanDipilih,
                tanggalKeluar, jumlah, alasan, keterangan);
        stokKeluarDAO.tambah(stokKeluarBaru);
        dagingDAO.ubahStok(idDagingDipilih, -jumlah);
        
        Daging dagingTerbaru = dagingDAO.cariById(idDagingDipilih);
        view.tampilkanPesan("Stok keluar berhasil dicatat, sisa stok daging menjadi "
                + dagingTerbaru.getStok() + "!");
    }
    
    public void tampilkanDaftarStokKeluar() throws SQLException {
        view.tampilkanTabelStokKeluar(stokKeluarDAO.ambilSemua());
    }
}
