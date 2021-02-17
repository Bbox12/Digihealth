package com.admin.ecosense.Login;

public class Medicalmodel {

    private String LBH,CRP,Ferritin,Lymphocytes,Count,DDimer,Interleukin,PCT,Photo;

    public String getLBH(int position) {
        return LBH;
    }

    public void setLBH(String LBH) {
        this.LBH = LBH;
    }

    public String getCRP(int position) {
        return CRP;
    }

    public void setCRP(String CRP) {
        this.CRP = CRP;
    }

    public String getFerritin(int position) {
        return Ferritin;
    }

    public void setFerritin(String ferritin) {
        Ferritin = ferritin;
    }

    public String getLymphocytes(int position) {
        return Lymphocytes;
    }

    public void setLymphocytes(String lymphocytes) {
        Lymphocytes = lymphocytes;
    }

    public String getCount(int position) {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getDDimer(int position) {
        return DDimer;
    }

    public void setDDimer(String DDimer) {
        this.DDimer = DDimer;
    }

    public String getInterleukin(int position) {
        return Interleukin;
    }

    public void setInterleukin(String interleukin) {
        Interleukin = interleukin;
    }

    public String getPCT(int position) {
        return PCT;
    }

    public void setPCT(String PCT) {
        this.PCT = PCT;
    }

    public String getPhoto(int position) {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
