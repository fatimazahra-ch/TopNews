package com.fatimazahra.topnews.model;

public class Person {

    //Les attributs
    private long id_Person;
    private String login;
    private String email_Person;
    private String password_Person;
    private String num_Serie;

    //Getter qnd Setter
        public long getId_Person() { return id_Person; }

        public void setId_Person(long id_Person) {
            this.id_Person = id_Person;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword_Person() {
            return password_Person;
        }

        public void setPassword_Person(String password_Person) { this.password_Person = password_Person; }

        public String getNum_Serie() {
            return num_Serie;
        }

        public void setNum_Serie(String num_Serie) {
            this.num_Serie = num_Serie;
        }

        public String getEmail_Person() {
            return email_Person;
        }

        public void setEmail_Person(String email_Person) {
            this.email_Person = email_Person;
        }
}
