/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class StokKeluar extends TransaksiStok{
    private String alasan;
    public StokKeluar(int idStokKeluar, int idDaging, int idKaryawan, String tanggalKeluar,
        int jumlah, String alasan, String keterangan){
        super(idStokKeluar, idDaging, idKaryawan, tanggalKeluar, jumlah, keterangan);
        setAlasan(alasan);
    }
    
    public String getAlasan() { return alasan; }
    public void setAlasan(String alasan) {
        if (Daging.isTeksValid(alasan)) {
            this.alasan = alasan;
        } else {
            System.out.println("[Peringatan] Alasan tidak boleh kosong, nilai tidak diubah");
        }
    }

    @Override
    public void tampilkanDetail() {
        System.out.println(getIdTransaksi() + " | Daging:" + getIdDaging() + " | Karyawan:" + getIdKaryawan()
        + " | " + getTanggalTransaksi() + " | " + getJumlah() + " | " + alasan + " | " + getKeterangan());
    }
}
