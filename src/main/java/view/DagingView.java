/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import model.Daging;
import model.Karyawan;
import model.StokKeluar;
import model.StokMasuk;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author LENOVO
 */
public class DagingView {
    private final Scanner scanner;
    
    public DagingView(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public void tampilkanJudul(String judul) {
        System.out.println();
        System.out.println("=== " + judul + " ===");
    }
    
    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }
    public void tampilkanMenu(String judul, String[] daftarPilihan) {
        tampilkanJudul(judul);
        for (int i = 0; i < daftarPilihan.length; i++) {
            System.out.println(daftarPilihan[i]);
        }
    }

    public void tampilkanBarisData(Daging dagingItem) {
        dagingItem.tampilkanDetail();
    }
    public void tampilkanBarisData(Karyawan karyawanItem) {
        karyawanItem.tampilkanDetail();
    }
    public void tampilkanBarisData(StokMasuk stokMasukItem) {
        stokMasukItem.tampilkanDetail();
    }
    public void tampilkanBarisData(StokKeluar stokKeluarItem) {
        stokKeluarItem.tampilkanDetail();
    }

    public void tampilkanTabelDaging(ArrayList<Daging> daftarDaging) {
        System.out.println("ID | Nama | Bagian | Berat | Tgl Masuk | Tgl Expired | Stok | Status");
        if (daftarDaging.isEmpty()) {
            System.out.println("(Belum ada data)");
            return;
        }
        for (Daging dagingItem : daftarDaging) {
            tampilkanBarisData(dagingItem);
        }
    }
    
    public void tampilkanTabelKaryawan(ArrayList<Karyawan> daftarKaryawan) {
        System.out.println("ID | Nama | Jabatan");
        if (daftarKaryawan.isEmpty()) {
            System.out.println("(Belum ada data)");
            return;
        }
        for (Karyawan karyawanItem : daftarKaryawan) {
            tampilkanBarisData(karyawanItem);
        }
    }
    
    public void tampilkanTabelStokMasuk(ArrayList<StokMasuk> daftarStokMasuk) {
        System.out.println("ID | Daging | Supplier | Karyawan | Tanggal | Jumlah | Keterangan");
        if (daftarStokMasuk.isEmpty()) {
            System.out.println("(Belum ada data)");
            return;
        }
        for (StokMasuk stokMasukItem : daftarStokMasuk) {
            tampilkanBarisData(stokMasukItem);
        }
    }
    
    public void tampilkanTabelStokKeluar(ArrayList<StokKeluar> daftarStokKeluar) {
        System.out.println("ID | Daging | Karyawan | Tanggal | Jumlah | Alasan | Keterangan");
        if (daftarStokKeluar.isEmpty()) {
            System.out.println("(Belum ada data)");
            return;
        }
        for (StokKeluar stokKeluarItem : daftarStokKeluar) {
            tampilkanBarisData(stokKeluarItem);
        }
    }
    
    public int bacaAngka(String label) {
        while (true) {
            System.out.print(label);
            String teksInput = scanner.nextLine().trim();
            if (!Daging.isAngkaBulat(teksInput)) {
                System.out.println("Input harus berupa angka bulat, contoh: 1");
                continue;
            }
            return Integer.parseInt(teksInput);
        }
    }
    
    public int bacaAngka(String label, int batasMinimum, int batasMaksimum) {
        while (true) {
            int nilaiAngka = bacaAngka(label);
            if (nilaiAngka < batasMinimum || nilaiAngka > batasMaksimum) {
                System.out.println("Pilihan harus di antara " + batasMinimum + " sampai " + batasMaksimum + "!");
                continue;
            }
            return nilaiAngka;
        }
    }
    
    public int bacaAngkaPositif(String label) {
        while (true) {
            int nilaiAngka = bacaAngka(label);
            if (!Daging.isAngkaPositif(nilaiAngka)) {
                System.out.println("Nilai harus lebih besar dari 0!");
                continue;
            }
            return nilaiAngka;
        }
    }
    
    public int bacaAngkaNonNegatif(String label) {
        while (true) {
            int nilaiAngka = bacaAngka(label);
            if (nilaiAngka < 0) {
                System.out.println("Nilai tidak boleh negatif!");
                continue;
            }
            return nilaiAngka;
        }
    }
    
    public double bacaAngkaDesimalPositif(String label) {
        while (true) {
            System.out.print(label);
            String teksInput = scanner.nextLine().trim();
            if (!Daging.isAngkaDesimal(teksInput)) {
                System.out.println("Input harus berupa angka, contoh: 2.5");
                continue;
            }
            double nilaiDesimal = Double.parseDouble(teksInput);
            if (!Daging.isAngkaPositif(nilaiDesimal)) {
                System.out.println("Nilai harus lebih besar dari 0!");
                continue;
            }
            return nilaiDesimal;
        }
    }

    public String bacaTeks(String label) {
        while (true) {
            System.out.print(label);
            String teksInput = scanner.nextLine().trim();
            if (!Daging.isTeksValid(teksInput)) {
                System.out.println("Input tidak boleh kosong!");
                continue;
            }
            return teksInput;
        }
    }
    
    public String bacaTeks(String label, String contohIsian) {
        return bacaTeks(label + " (contoh: " + contohIsian + "): ");
    }

    public String bacaTanggal(String label) {
        while (true) {
            System.out.print(label + " (format " + Daging.FORMAT_TANGGAL + ", contoh: 05-10-2026): ");
            String teksTanggal = scanner.nextLine().trim();
            if (!Daging.isTanggalValid(teksTanggal)) {
                System.out.println("Tanggal tidak valid! Gunakan format " + Daging.FORMAT_TANGGAL
                        + ", contoh: 05-10-2026");
                continue;
            }
            return teksTanggal;
        }
    }
}
