package com.rojo.travel.sqlite;

public class temp_akun {


    private int id;
        private String akun_gelar;
        private String akun_nama_depan;
        private String akun_nama_belakang;
        private String akun_username;
        private String akun_email;
        private String akun_telepon;

        public temp_akun(){}

        /*public temp_akun(String akun_gelar, String akun_nama_depan, String akun_nama_belakang, String akun_username,String akun_email, String akun_telepon){
            this.akun_gelar=akun_gelar;
            this.akun_nama_depan=akun_nama_depan;
            this.akun_nama_belakang=akun_nama_belakang;
            this.akun_username=akun_username;
            this.akun_email=akun_email;
            this.akun_telepon=akun_telepon;
        }*/

        public temp_akun(int id, String akun_gelar, String akun_nama_depan, String akun_nama_belakang, String akun_username,String akun_email, String akun_telepon){
            this.id=id;
            this.akun_gelar=akun_gelar;
            this.akun_nama_depan=akun_nama_depan;
            this.akun_nama_belakang=akun_nama_belakang;
            this.akun_username=akun_username;
            this.akun_email=akun_email;
            this.akun_telepon=akun_telepon;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAkun_gelar() {
        return akun_gelar;
    }

    public void setAkun_gelar(String akun_gelar) {
        this.akun_gelar = akun_gelar;
    }

    public String getAkun_nama_depan() {
        return akun_nama_depan;
    }

    public void setAkun_nama_depan(String akun_nama_depan) {
        this.akun_nama_depan = akun_nama_depan;
    }

    public String getAkun_nama_belakang() {
        return akun_nama_belakang;
    }

    public void setAkun_nama_belakang(String akun_nama_belakang) {
        this.akun_nama_belakang = akun_nama_belakang;
    }

    public String getAkun_username() {
        return akun_username;
    }

    public void setAkun_username(String akun_username) {
        this.akun_username = akun_username;
    }

    public String getAkun_email() {
        return akun_email;
    }

    public void setAkun_email(String akun_email) {
        this.akun_email = akun_email;
    }

    public String getAkun_telepon() {
        return akun_telepon;
    }

    public void setAkun_telepon(String akun_telepon) {
        this.akun_telepon = akun_telepon;
    }

}
