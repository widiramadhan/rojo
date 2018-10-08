package com.rojo.travel.order;

/**
 * Created by limadawai on 15/04/18.
 */

public class ListWisataDataSet {

    String kode_invoice_pemesanan, paket_wisata_nama, jumlah_peserta, harga_paket, paket_wisata_tanggal_berangkat,
            total_harga_pembayaran_paket;

    public ListWisataDataSet(){}

    public ListWisataDataSet(String kode_invoice_pemesanan, String paket_wisata_nama, String jumlah_peserta, String harga_paket, String paket_wisata_tanggal_berangkat, String total_harga_pembayaran_paket) {
        this.kode_invoice_pemesanan = kode_invoice_pemesanan;
        this.paket_wisata_nama = paket_wisata_nama;
        this.jumlah_peserta = jumlah_peserta;
        this.harga_paket = harga_paket;
        this.paket_wisata_tanggal_berangkat = paket_wisata_tanggal_berangkat;
        this.total_harga_pembayaran_paket = total_harga_pembayaran_paket;
    }

    public String getKode_invoice_pemesanan() {
        return kode_invoice_pemesanan;
    }

    public void setKode_invoice_pemesanan(String kode_invoice_pemesanan) {
        this.kode_invoice_pemesanan = kode_invoice_pemesanan;
    }

    public String getPaket_wisata_nama() {
        return paket_wisata_nama;
    }

    public void setPaket_wisata_nama(String paket_wisata_nama) {
        this.paket_wisata_nama = paket_wisata_nama;
    }

    public String getJumlah_peserta() {
        return jumlah_peserta;
    }

    public void setJumlah_peserta(String jumlah_peserta) {
        this.jumlah_peserta = jumlah_peserta;
    }

    public String getHarga_paket() {
        return harga_paket;
    }

    public void setHarga_paket(String harga_paket) {
        this.harga_paket = harga_paket;
    }

    public String getPaket_wisata_tanggal_berangkat() {
        return paket_wisata_tanggal_berangkat;
    }

    public void setPaket_wisata_tanggal_berangkat(String paket_wisata_tanggal_berangkat) {
        this.paket_wisata_tanggal_berangkat = paket_wisata_tanggal_berangkat;
    }

    public String getTotal_harga_pembayaran_paket() {
        return total_harga_pembayaran_paket;
    }

    public void setTotal_harga_pembayaran_paket(String total_harga_pembayaran_paket) {
        this.total_harga_pembayaran_paket = total_harga_pembayaran_paket;
    }
}
