package com.rojo.travel.sqlite;

public class temp_akun {
        private int id;
        private String akun_username;
        private String akun_email;

        public temp_akun(){}

        public temp_akun(String akun_username,String akun_email){
            this.akun_username=akun_username;
            this.akun_email=akun_email;
        }

        public temp_akun(int id,String akun_username,String akun_email){
            this.id=id;
            this.akun_username=akun_username;
            this.akun_email=akun_email;
        }

        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
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
}
