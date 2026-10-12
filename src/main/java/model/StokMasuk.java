/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author LENOVO
 */
public class StokMasuk extends TransaksiStok {
    private int idSupplier;
    public StokMasuk(int idStokMasuk, int idDaging, int idSupplier, int idKaryawan,
    String tanggalMasuk, int jumlah, String keterangan){
        super(idStokMasuk, idDaging, idKaryawan, tanggalMasuk, jumlah, keterangan);
        setIdSupplier(idSupplier);
    }
 
    public int getIdSupplier() { return idSupplier;}
 
    public void setIdSupplier(int idSupplier){
        if (Daging.isAngkaPositif(idSupplier)){
            this.idSupplier = idSupplier;
        } else {
            System.out.println("[Peringatan] ID supplier harus lebih dari 0, nilai tidak diubah");
        }
    }
    
    @Override
    public void tampilkanDetail() {
        System.out.println(getIdTransaksi() + " | Daging:" + getIdDaging() + " | Supplier:" + idSupplier
        + " | Karyawan:" + getIdKaryawan() + " | " + getTanggalTransaksi() + " | " + getJumlah() + " | " + getKeterangan());
    }
}
