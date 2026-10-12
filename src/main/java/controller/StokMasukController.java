/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.DagingDAO;
import model.KaryawanDAO;
import model.StokMasukDAO;
import model.Daging;
import model.Karyawan;
import model.StokMasuk;
import view.DagingView;
import java.sql.SQLException;
/**
 *
 * @author LENOVO
 */
public class StokMasukController {
 
    private final StokMasukDAO stokMasukDAO;
    private final DagingDAO dagingDAO;
    private final KaryawanDAO karyawanDAO;
    private final DagingView view;
 
    public StokMasukController(StokMasukDAO stokMasukDAO, DagingDAO dagingDAO,
                                KaryawanDAO karyawanDAO, DagingView view) {
        this.stokMasukDAO = stokMasukDAO;
        this.dagingDAO = dagingDAO;
        this.karyawanDAO = karyawanDAO;
        this.view = view;
    }
 
    public void jalankanMenu() {
        int pilihanMenu;
        do {
            view.tampilkanMenu("Menu Stok Masuk", new String[] {
                    "1. Catat Stok Masuk",
                    "2. Lihat Data Stok Masuk",
                    "0. Kembali ke Menu Utama"});
            pilihanMenu = view.bacaAngka("Pilih menu: ", 0, 2);
 
            try {
                if (pilihanMenu == 1) {
                    catatStokMasuk();
                } else if (pilihanMenu == 2) {
                    tampilkanDaftarStokMasuk();
                }
            } catch (SQLException kesalahanDatabase) {
                view.tampilkanPesan("Terjadi kesalahan database: " + kesalahanDatabase.getMessage());
            }
        } while (pilihanMenu != 0);
    }
 
    private void catatStokMasuk() throws SQLException {
        view.tampilkanTabelDaging(dagingDAO.ambilSemua());
        int idDagingDipilih = view.bacaAngkaPositif("ID daging yang menerima stok (lihat daftar di atas): ");
        Daging dagingTerpilih = dagingDAO.cariById(idDagingDipilih);
        if (dagingTerpilih == null) {
            view.tampilkanPesan("ID daging tidak ditemukan! Tambahkan data daging terlebih dahulu.");
            return;
        }
 
        view.tampilkanTabelKaryawan(karyawanDAO.ambilSemua());
        int idKaryawanDipilih = view.bacaAngkaPositif("ID karyawan yang mencatat (lihat daftar di atas): ");
        Karyawan karyawanTerpilih = karyawanDAO.cariById(idKaryawanDipilih);
        if (karyawanTerpilih == null) {
            view.tampilkanPesan("ID karyawan tidak ditemukan! Tambahkan data karyawan terlebih dahulu.");
            return;
        }
 
        String tanggalMasuk = view.bacaTanggal("Tanggal masuk");
        int jumlah = view.bacaAngkaPositif("Jumlah daging masuk (contoh: 20): ");
        String keterangan = view.bacaTeks("Keterangan", "Pembelian rutin mingguan");
 
        // ID stok masuk diisi 0 (dibuat otomatis oleh database), ID supplier dibuat otomatis oleh DAO
        int idSupplier = stokMasukDAO.ambilIdSupplierBerikutnya();
        StokMasuk stokMasukBaru = new StokMasuk(0, idDagingDipilih, idSupplier, idKaryawanDipilih,
                tanggalMasuk, jumlah, keterangan);
        stokMasukDAO.tambah(stokMasukBaru);
        dagingDAO.ubahStok(idDagingDipilih, jumlah);
 
        Daging dagingTerbaru = dagingDAO.cariById(idDagingDipilih);
        view.tampilkanPesan("Stok masuk berhasil dicatat, stok daging bertambah menjadi "
                + dagingTerbaru.getStok() + "!");
    }
 
    public void tampilkanDaftarStokMasuk() throws SQLException {
        view.tampilkanTabelStokMasuk(stokMasukDAO.ambilSemua());
    }
}
