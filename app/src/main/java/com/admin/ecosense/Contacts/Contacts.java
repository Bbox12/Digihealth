package com.admin.ecosense.Contacts;

/**
 * Created by parag on 28/02/18.
 */

public class Contacts {

    public String Name;
    public String Contacts;
    public int ID;
    public int isRequested,isAccpted;


    public int getIsRequested(int position) {
        return isRequested;
    }

    public void setIsRequested(int isRequested) {
        this.isRequested = isRequested;
    }

    public int getIsAccpted(int position) {
        return isAccpted;
    }

    public void setIsAccpted(int isAccpted) {
        this.isAccpted = isAccpted;
    }

    public String getName(int position) {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContacts(int position) {
        return Contacts;
    }

    public void setContacts(String contacts) {
        Contacts = contacts;
    }

    public int getID(int position) {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
