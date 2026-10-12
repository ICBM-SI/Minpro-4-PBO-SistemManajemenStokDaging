/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.KaryawanDAO;
import model.Karyawan;
import view.DagingView;
import java.sql.SQLException;
/**
 *
 * @author LENOVO
 */
public class Karyawancontroller {
    private final KaryawanDAO karyawanDAO;
    private final DagingView view;
    public Karyawancontroller(KaryawanDAO karyawanDAO, DagingView view) {
        this.karyawanDAO = karyawanDAO;
        this.view = view;
    }
    public void jalankanMenu() {
        int pilihanMenu;
        do {
            view.tampilkanMenu("Menu Karyawan", new String[] {
                    "1. Tambah Data Karyawan",
                    "2. Lihat Data Karyawan",
                    "0. Kembali ke Menu Utama"});
            pilihanMenu = view.bacaAngka("Pilih menu: ", 0, 2);
            try {
                if (pilihanMenu == 1) {
                    tambahKaryawan();
                } else if (pilihanMenu == 2) {
                    tampilkanDaftarKaryawan();
                }
            } catch (SQLException kesalahanDatabase) {
                view.tampilkanPesan("Terjadi kesalahan database: " + kesalahanDatabase.getMessage());
            }
        } while (pilihanMenu != 0);
    }
    
    private void tambahKaryawan() throws SQLException {
        String namaKaryawan = view.bacaTeks("Nama karyawan", "Budi Santoso");
        String jabatan = view.bacaTeks("Jabatan", "Admin Gudang");
        karyawanDAO.tambah(new Karyawan(0, namaKaryawan, jabatan));
        view.tampilkanPesan("Data karyawan berhasil ditambahkan!");
    }
    
    public void tampilkanDaftarKaryawan() throws SQLException {
        view.tampilkanTabelKaryawan(karyawanDAO.ambilSemua());
    }
}
