/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class Karyawan {
    private final int idKaryawan;
    private String namaKaryawan;
    private String jabatan;
    public Karyawan(int idKaryawan, String namaKaryawan, String jabatan) {
        this.idKaryawan = idKaryawan;
        setNamaKaryawan(namaKaryawan);
        setJabatan(jabatan);
    }
    
    //getter
    public final int getIdKaryawan() { return idKaryawan; }
    public String getNamaKaryawan() { return namaKaryawan; }
    public String getJabatan() { return jabatan; }
    
    //setter
    public void setNamaKaryawan(String namaKaryawan) {
        if (Daging.isTeksValid(namaKaryawan)) {
            this.namaKaryawan = namaKaryawan;
        } else {
            System.out.println("[Peringatan] Nama karyawan tidak boleh kosong, nilai tidak diubah");
        }
    }
    
    public void setJabatan(String jabatan) {
        if (Daging.isTeksValid(jabatan)) {
            this.jabatan = jabatan;
        } else {
            System.out.println("[Peringatan] Jabatan tidak boleh kosong, nilai tidak diubah");
        }
    }
    
    public void tampilkanDetail() {
        System.out.println(idKaryawan + " | " + namaKaryawan + " | " + jabatan);
    }
}
