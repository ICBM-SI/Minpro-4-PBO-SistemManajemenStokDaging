/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class Daging {
    private final int idDaging;
    private String namaDaging;
    private String bagianDaging;
    private double berat;
    private String tanggalMasuk;
    private String tanggalExpired;
    private int stok;
    private String status;
    
    public Daging(int idDaging, String namaDaging, String bagianDaging, double berat,
                  String tanggalMasuk, String tanggalExpired, int stok, String status) {
        this.idDaging = idDaging;
        setNamaDaging(namaDaging);
        setBagianDaging(bagianDaging);
        setBerat(berat);
        setTanggalMasuk(tanggalMasuk);
        setTanggalExpired(tanggalExpired);
        setStok(stok);
        setStatus(status);
    }

    public final int getIdDaging() { return idDaging; }
    public String getNamaDaging() { return namaDaging; }
    public String getBagianDaging() { return bagianDaging; }
    public double getBerat() { return berat; }
    public String getTanggalMasuk() { return tanggalMasuk; }
    public String getTanggalExpired() { return tanggalExpired; }
    public int getStok() { return stok; }
    public String getStatus() { return status; }

    public void setNamaDaging(String namaDaging) {
        if (isTeksValid(namaDaging)) {
            this.namaDaging = namaDaging;
        } else {
            System.out.println("[Peringatan] Nama daging tidak boleh kosong, nilai tidak diubah");
        }
    }
    
    public void setBagianDaging(String bagianDaging) {
        if (isTeksValid(bagianDaging)) {
            this.bagianDaging = bagianDaging;
        } else {
            System.out.println("[Peringatan] Bagian daging tidak boleh kosong, nilai tidak diubah");
        }
    }
    
    public void setBerat(double berat) {
        if (isAngkaPositif(berat)) {
            this.berat = berat;
        } else {
            System.out.println("[Peringatan] Berat harus lebih dari 0, nilai tidak diubah");
        }
    }
    
    public void setTanggalMasuk(String tanggalMasuk) {
        if (isTanggalValid(tanggalMasuk)) {
            this.tanggalMasuk = tanggalMasuk;
        } else {
            System.out.println("[Peringatan] Format tanggal masuk harus " + FORMAT_TANGGAL
                    + ", nilai tidak diubah");
        }
    }
    
    public void setTanggalExpired(String tanggalExpired) {
        if (isTanggalValid(tanggalExpired)) {
            this.tanggalExpired = tanggalExpired;
        } else {
            System.out.println("[Peringatan] Format tanggal expired harus " + FORMAT_TANGGAL
                    + ", nilai tidak diubah");
        }
    }
    
    public void setStok(int stok) {
        if (stok >= 0) {
            this.stok = stok;
        } else {
            System.out.println("[Peringatan] Stok tidak boleh negatif, nilai tidak diubah");
        }
    }
    
    public void setStatus(String status) {
        if (isTeksValid(status)) {
            this.status = status;
        } else {
            System.out.println("[Peringatan] Status tidak boleh kosong, nilai tidak diubah");
        }
    }
    
    public void tampilkanDetail() {
        System.out.println(idDaging + " | " + namaDaging + " | " + bagianDaging + " | "
                + berat + " kg | " + tanggalMasuk + " | " + tanggalExpired + " | "
                + stok + " | " + status);
    }

    public static final String FORMAT_TANGGAL = "dd-MM-yyyy";
    public static boolean isTeksValid(String teks) {
        return teks != null && !teks.trim().isEmpty();
    }
    public static boolean isAngkaPositif(int angka) {
        return angka > 0;
    }
    public static boolean isAngkaPositif(double angka) {
        return angka > 0;
    }
    public static boolean isTanggalValid(String teksTanggal) {
        if (!isTeksValid(teksTanggal)) {
            return false;
        }        
        String[] bagianTanggal = teksTanggal.split("-");
        if (bagianTanggal.length != 3) {
            return false;
        }        
        String teksTgl = bagianTanggal[0];
        String teksBulan = bagianTanggal[1];
        String teksTahun = bagianTanggal[2];
        if (teksTgl.length() != 2 || teksBulan.length() != 2 || teksTahun.length() != 4) {
            return false;
        }        
        if (!isHanyaAngka(teksTgl) || !isHanyaAngka(teksBulan) || !isHanyaAngka(teksTahun)) {
            return false;
        }
        int tanggal = Integer.parseInt(teksTgl);
        int bulan = Integer.parseInt(teksBulan);
        int tahun = Integer.parseInt(teksTahun);
        if (bulan < 1 || bulan > 12) {
            return false;
        }
        if (tanggal < 1 || tanggal > jumlahHariDalamBulan(bulan, tahun)) {
            return false;
        }
        return tahun >= 1900 && tahun <= 2100;
    }

    public static boolean isAngkaBulat(String teks) {
        if (!isTeksValid(teks)) {
            return false;
        }
        String digit = teks;
        if (teks.startsWith("-")) {
            digit = teks.substring(1);
        }
        return digit.length() >= 1 && digit.length() <= 9 && isHanyaAngka(digit);
    }

    public static boolean isAngkaDesimal(String teks) {
        if (!isTeksValid(teks)) {
            return false;
        }
        String digit = teks;
        if (teks.startsWith("-")) {
            digit = teks.substring(1);
        }
        int posisiTitik = digit.indexOf('.');
        if (posisiTitik == -1) {
            return digit.length() >= 1 && digit.length() <= 9 && isHanyaAngka(digit);
        }
        String bagianDepan = digit.substring(0, posisiTitik);
        String bagianBelakang = digit.substring(posisiTitik + 1);
        return bagianDepan.length() >= 1 && bagianDepan.length() <= 9 && isHanyaAngka(bagianDepan)
                && bagianBelakang.length() >= 1 && bagianBelakang.length() <= 9 && isHanyaAngka(bagianBelakang);
    }

    private static boolean isHanyaAngka(String teks) {
        for (int i = 0; i < teks.length(); i++) {
            if (!Character.isDigit(teks.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static int jumlahHariDalamBulan(int bulan, int tahun) {
        if (bulan == 1 || bulan == 3 || bulan == 5 || bulan == 7 || bulan == 8 || bulan == 10 || bulan == 12) {
            return 31;
        } else if (bulan == 4 || bulan == 6 || bulan == 9 || bulan == 11) {
            return 30;
        } else if (bulan == 2) {
            if (isTahunKabisat(tahun)) {
                return 29;
            }
            return 28;
        }
        return 0;
    }
    
    private static boolean isTahunKabisat(int tahun) {
        return (tahun % 4 == 0 && tahun % 100 != 0) || (tahun % 400 == 0);
    }
}
