package com.rojo.travel.akun;

/**
 * Created by kangyasin on 04/02/2018.
 */

public class NeagaraDataSet {
    String negara_id, negara_nama_1, negara_nama_2, negara_kode_numerik, negara_kode_alpha_3, negara_kode_alpha_2;

    public NeagaraDataSet() {
    }

    public NeagaraDataSet(String negara_id, String negara_nama_1, String negara_nama_2, String negara_kode_numerik, String negara_kode_alpha_3, String negara_kode_alpha_2) {
        this.negara_id = negara_id;
        this.negara_nama_1 = negara_nama_1;
        this.negara_nama_2 = negara_nama_2;
        this.negara_kode_numerik = negara_kode_numerik;
        this.negara_kode_alpha_3 = negara_kode_alpha_3;
        this.negara_kode_alpha_2 = negara_kode_alpha_2;
    }

    public String getNegara_id() {
        return negara_id;
    }

    public void setNegara_id(String negara_id) {
        this.negara_id = negara_id;
    }

    public String getNegara_nama_1() {
        return negara_nama_1;
    }

    public void setNegara_nama_1(String negara_nama_1) {
        this.negara_nama_1 = negara_nama_1;
    }

    public String getNegara_nama_2() {
        return negara_nama_2;
    }

    public void setNegara_nama_2(String negara_nama_2) {
        this.negara_nama_2 = negara_nama_2;
    }

    public String getNegara_kode_numerik() {
        return negara_kode_numerik;
    }

    public void setNegara_kode_numerik(String negara_kode_numerik) {
        this.negara_kode_numerik = negara_kode_numerik;
    }

    public String getNegara_kode_alpha_3() {
        return negara_kode_alpha_3;
    }

    public void setNegara_kode_alpha_3(String negara_kode_alpha_3) {
        this.negara_kode_alpha_3 = negara_kode_alpha_3;
    }

    public String getNegara_kode_alpha_2() {
        return negara_kode_alpha_2;
    }

    public void setNegara_kode_alpha_2(String negara_kode_alpha_2) {
        this.negara_kode_alpha_2 = negara_kode_alpha_2;
    }
}
