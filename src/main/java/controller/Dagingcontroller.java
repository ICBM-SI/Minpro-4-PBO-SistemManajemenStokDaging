/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.DagingDAO;
import model.Daging;
import view.DagingView;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author LENOVO
 */
public class Dagingcontroller {
    private final DagingDAO dagingDAO;
    private final DagingView view;
    public Dagingcontroller(DagingDAO dagingDAO, DagingView view) {
        this.dagingDAO = dagingDAO;
        this.view = view;
    }
    public void jalankanMenu() {
        int pilihanMenu;
        do {
            view.tampilkanMenu("Menu Daging", new String[] {
                    "1. Tambah Data Daging",
                    "2. Lihat Data Daging",
                    "3. Ubah Data Daging",
                    "4. Hapus Data Daging",
                    "0. Kembali ke Menu Utama"});
            pilihanMenu = view.bacaAngka("Pilih menu: ", 0, 4);
            try {
                if (pilihanMenu == 1) {
                    tambahDaging();
                } else if (pilihanMenu == 2) {
                    tampilkanDaftarDaging();
                } else if (pilihanMenu == 3) {
                    ubahDaging();
                } else if (pilihanMenu == 4) {
                    hapusDaging();
                }
            } catch (SQLException kesalahanDatabase) {
                view.tampilkanPesan("Terjadi kesalahan database: " + kesalahanDatabase.getMessage());
            }
        } while (pilihanMenu != 0);
    }
    
    private void tambahDaging() throws SQLException {
        String namaDaging = view.bacaTeks("Nama daging", "Daging Sapi");
        String bagianDaging = view.bacaTeks("Bagian daging", "Paha");
        double berat = view.bacaAngkaDesimalPositif("Berat dalam kg (contoh: 10.5): ");
        String tanggalMasuk = view.bacaTanggal("Tanggal masuk");
        String tanggalExpired = view.bacaTanggal("Tanggal expired");
        int stok = view.bacaAngkaNonNegatif("Jumlah stok awal (contoh: 20): ");
        String status = view.bacaTeks("Status", "Tersedia");

        Daging dagingBaru = new Daging(0, namaDaging, bagianDaging, berat,
                tanggalMasuk, tanggalExpired, stok, status);
        dagingDAO.tambah(dagingBaru);
        view.tampilkanPesan("Data daging berhasil ditambahkan!");
    }
    
    public void tampilkanDaftarDaging() throws SQLException {
        view.tampilkanTabelDaging(dagingDAO.ambilSemua());
    }
    
    private void ubahDaging() throws SQLException {
        ArrayList<Daging> daftarDaging = dagingDAO.ambilSemua();
        view.tampilkanTabelDaging(daftarDaging);
        if (daftarDaging.isEmpty()) {
            return;
        }
        int idDagingDipilih = view.bacaAngkaPositif("ID daging yang ingin diubah (contoh: 1): ");
        Daging dagingTerpilih = dagingDAO.cariById(idDagingDipilih);
        if (dagingTerpilih == null) {
            view.tampilkanPesan("ID daging tidak ditemukan!");
            return;
        }
        
        dagingTerpilih.setNamaDaging(view.bacaTeks("Nama daging baru", dagingTerpilih.getNamaDaging()));
        dagingTerpilih.setStok(view.bacaAngkaNonNegatif("Stok baru (contoh: 15): "));
        dagingTerpilih.setStatus(view.bacaTeks("Status baru", "Tersedia"));
        dagingDAO.ubah(dagingTerpilih);
        view.tampilkanPesan("Data daging berhasil diubah!");
    }
    
    private void hapusDaging() throws SQLException {
        ArrayList<Daging> daftarDaging = dagingDAO.ambilSemua();
        view.tampilkanTabelDaging(daftarDaging);
        if (daftarDaging.isEmpty()) {
            return;
        }
        int idDagingDipilih = view.bacaAngkaPositif("ID daging yang ingin dihapus (contoh: 1): ");
        Daging dagingTerpilih = dagingDAO.cariById(idDagingDipilih);
        if (dagingTerpilih == null) {
            view.tampilkanPesan("ID daging tidak ditemukan!");
            return;
        }
        if (dagingDAO.punyaRiwayatTransaksi(idDagingDipilih)) {
            view.tampilkanPesan("Daging tidak bisa dihapus karena sudah punya riwayat stok masuk/keluar.");
            return;
        }
        dagingDAO.hapus(idDagingDipilih);
        view.tampilkanPesan("Data daging berhasil dihapus!");
    }
}
