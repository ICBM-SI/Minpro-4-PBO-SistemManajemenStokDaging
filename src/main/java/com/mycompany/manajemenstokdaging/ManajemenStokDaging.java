/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.manajemenstokdaging;
import config.Koneksi;
import controller.Dagingcontroller;
import controller.Karyawancontroller;
import controller.StokKeluarController;
import controller.StokMasukController;
import model.DagingDAO;
import model.KaryawanDAO;
import model.StokKeluarDAO;
import model.StokMasukDAO;
import view.DagingView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
/**
 *
 * @author LENOVO
 */
public class ManajemenStokDaging {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DagingView view = new DagingView(scanner);
        Connection koneksi = null;
        try {
            koneksi = Koneksi.getKoneksi();
        } catch (SQLException kesalahanKoneksi) {
            view.tampilkanPesan("Gagal terhubung ke database: " + kesalahanKoneksi.getMessage());
            view.tampilkanPesan("Periksa: (1) MySQL sudah menyala, (2) file db_stok_daging.sql sudah di-import,");
            view.tampilkanPesan("(3) URL/user/password di config/Koneksi.java sudah benar, (4) driver MySQL JDBC sudah ditambahkan.");
            scanner.close();
            return;
        }
        
        DagingDAO dagingDAO = new DagingDAO(koneksi);
        KaryawanDAO karyawanDAO = new KaryawanDAO(koneksi);
        StokMasukDAO stokMasukDAO = new StokMasukDAO(koneksi);
        StokKeluarDAO stokKeluarDAO = new StokKeluarDAO(koneksi);
        Dagingcontroller dagingController = new Dagingcontroller(
                dagingDAO, view);
        Karyawancontroller karyawanController = new Karyawancontroller(
                karyawanDAO, view);
        StokMasukController stokMasukController = new StokMasukController(
                stokMasukDAO, dagingDAO, karyawanDAO, view);
        StokKeluarController stokKeluarController = new StokKeluarController(
                stokKeluarDAO, dagingDAO, karyawanDAO, view);

        int pilihanMenuUtama;
        do {
            view.tampilkanMenu("Sistem Manajemen Stok Daging", new String[] {
                    "1. Data Daging",
                    "2. Data Karyawan",
                    "3. Stok Masuk",
                    "4. Stok Keluar",
                    "0. Keluar"});
            pilihanMenuUtama = view.bacaAngka("Pilih menu: ", 0, 4);
            if (pilihanMenuUtama == 1) {
                dagingController.jalankanMenu();
            } else if (pilihanMenuUtama == 2) {
                karyawanController.jalankanMenu();
            } else if (pilihanMenuUtama == 3) {
                stokMasukController.jalankanMenu();
            } else if (pilihanMenuUtama == 4) {
                stokKeluarController.jalankanMenu();
            } else if (pilihanMenuUtama == 0) {
                view.tampilkanPesan("Program selesai.");
            }
        } while (pilihanMenuUtama != 0);
        
        try {
            koneksi.close();
        } catch (SQLException kesalahanTutup) {
            view.tampilkanPesan("Gagal menutup koneksi database: " + kesalahanTutup.getMessage());
        }
        scanner.close();
    }
}
