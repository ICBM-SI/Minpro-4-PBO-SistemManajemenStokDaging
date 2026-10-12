/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Daging;
import model.DagingDAO;
import model.Karyawan;
import model.KaryawanDAO;
import model.StokMasuk;
import model.StokMasukDAO;
import view.DagingView;
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
    
    public void jalankanMenu(){
        int pilihanMenu;
        do {
            view.tampilkanMenu("Menu Stok Masuk", new String[]{
                    "1. Catat Stok Masuk",
                    "2. Lihat Data Stok Masuk",
                    "0. Kembali ke Menu Utama"});
            pilihanMenu = view.bacaAngka("Pilih menu: ", 0, 2);
            if (pilihanMenu == 1) {
                catatStokMasuk();
            } else if (pilihanMenu == 2) {
                tampilkanDaftarStokMasuk();
            }
        } while (pilihanMenu != 0);
    }
    
    private void catatStokMasuk(){
        view.tampilkanTabelDaging(dagingDAO.ambilSemua());
        int idDagingDipilih = view.bacaAngkaPositif("ID daging yang menerima stok (lihat daftar di atas): ");
        Daging dagingTerpilih = dagingDAO.cariById(idDagingDipilih);
        if (dagingTerpilih == null){
            view.tampilkanPesan("ID daging tidak ditemukan! Tambahkan data daging terlebih dahulu.");
            return;
        }
        
        view.tampilkanTabelKaryawan(karyawanDAO.ambilSemua());
        int idKaryawanDipilih = view.bacaAngkaPositif("ID karyawan yang mencatat (lihat daftar di atas): ");
        Karyawan karyawanTerpilih = karyawanDAO.cariById(idKaryawanDipilih);
        if (karyawanTerpilih == null){
            view.tampilkanPesan("ID karyawan tidak ditemukan! Tambahkan data karyawan terlebih dahulu.");
            return;
        }
        
        String tanggalMasuk = view.bacaTanggal("Tanggal masuk");
        int jumlah = view.bacaAngkaPositif("Jumlah daging masuk (contoh: 20): ");
        String keterangan = view.bacaTeks("Keterangan", "Pembelian rutin mingguan");
        
        int idSupplier = stokMasukDAO.ambilIdSupplierBerikutnya();
        StokMasuk stokMasukBaru = new StokMasuk(0, idDagingDipilih, idSupplier, idKaryawanDipilih,
                tanggalMasuk, jumlah, keterangan);
        
        if (!stokMasukDAO.tambah(stokMasukBaru)){
            return;
        }
        if (!dagingDAO.ubahStok(idDagingDipilih, jumlah)){
            view.tampilkanPesan("Peringatan: transaksi tersimpan, tetapi stok daging gagal diperbarui.");
            return;
        }
        
        Daging dagingTerbaru = dagingDAO.cariById(idDagingDipilih);
        view.tampilkanPesan("Stok masuk berhasil dicatat, stok daging bertambah menjadi: "
                + dagingTerbaru.getStok() + "!");
    }
    
    public void tampilkanDaftarStokMasuk(){
        view.tampilkanTabelStokMasuk(stokMasukDAO.ambilSemua());
    }
}