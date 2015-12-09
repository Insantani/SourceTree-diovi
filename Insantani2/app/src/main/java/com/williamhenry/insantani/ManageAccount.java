package com.williamhenry.insantani;



/**
 * Created by dioviazalia on 12/4/15.
 */
public class ManageAccount {
    String fullName;
    String enteremail;
    int enterphonenumber;
    String enteraddress;


    public ManageAccount(String fullName, String enteremail, int enterphonenumber, String enteraddress) {
        this.fullName = fullName;
        this.enteremail = enteremail;
        this.enterphonenumber = enterphonenumber;
        this.enteraddress = enteraddress;
    }

    public String getFullName() {

        return fullName;
    }

    public String getEnteremail() {
        return enteremail
    }

    public int getEnterphonenumber() {

        return enterphonenumber;
    }
    public String getEnteraddress(){
        return enteraddress
    }
