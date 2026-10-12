/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public abstract class TransaksiStok {
    private final int idTransaksi;
    private final int idDaging;
    private int idKaryawan;
    private String tanggalTransaksi;
    private int jumlah;
    private String keterangan;
    
    protected TransaksiStok(int idTransaksi, int idDaging, int idKaryawan, String tanggalTransaksi, int jumlah, String keterangan){
        this.idTransaksi = idTransaksi;
        this.idDaging = idDaging;
        setIdKaryawan(idKaryawan);
        setTanggalTransaksi(tanggalTransaksi);
        setJumlah(jumlah);
        setKeterangan(keterangan);
    }
    
    //getter
    public final int getIdTransaksi() { return idTransaksi;}
    public final int getIdDaging() { return idDaging;}
    public int getIdKaryawan() { return idKaryawan;}
    public String getTanggalTransaksi() { return tanggalTransaksi;}
    public int getJumlah() { return jumlah;}
    public String getKeterangan() { return keterangan;}
    
    //setter
    public void setIdKaryawan(int idKaryawan) {
        if (Daging.isAngkaPositif(idKaryawan)) {
            this.idKaryawan = idKaryawan;
        } else {
            System.out.println("[Peringatan] ID karyawan harus lebih dari 0, nilai tidak diubah");
        }
    }
    
    public void setTanggalTransaksi(String tanggalTransaksi) {
        if (Daging.isTanggalValid(tanggalTransaksi)) {
            this.tanggalTransaksi = tanggalTransaksi;
        } else {
            System.out.println("[Peringatan] Format tanggal harus " + Daging.FORMAT_TANGGAL
                    + ", nilai tidak diubah");
        }
    }
    
    public void setJumlah(int jumlah) {
        if (Daging.isAngkaPositif(jumlah)) {
            this.jumlah = jumlah;
        } else {
            System.out.println("[Peringatan] Jumlah harus lebih dari 0, nilai tidak diubah");
        }
    }
    
    public void setKeterangan(String keterangan) {
        if (Daging.isTeksValid(keterangan)) {
            this.keterangan = keterangan;
        } else {
            System.out.println("[Peringatan] Keterangan tidak boleh kosong, nilai tidak diubah");
        }
    }

    public abstract void tampilkanDetail();
}
