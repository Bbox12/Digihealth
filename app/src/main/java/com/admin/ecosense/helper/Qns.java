package com.admin.ecosense.helper;

/**
 * Created by parag on 18/02/18.
 */

public class Qns {

    public String version,imp,Category,Question,Photo,Colors,Description,Files,NextDate,NextTime,Date,Time,Name,Relation,
    DigiCategory,Doctor,PhoneNo,riskFactors,OtherFactors,Symptoms;
    public int ID,Ans,isVideo,IDDoc,risk,symID,IDFirstLevel;
    private boolean isSelected;
    private double LDH,CRP,Ferritin,Lymphocytes,Count,DDimer,Interleukin,PCT;


    public String getDescription(int position) {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFiles(int position) {
        return Files;
    }

    public void setFiles(String files) {
        Files = files;
    }

    public String getNextDate(int position) {
        return NextDate;
    }

    public void setNextDate(String nextDate) {
        NextDate = nextDate;
    }

    public String getNextTime(int position) {
        return NextTime;
    }

    public void setNextTime(String nextTime) {
        NextTime = nextTime;
    }

    public String getDate(int position) {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime(int position) {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public boolean isSelected(int position) {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public String getVersion(int position) {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getImp(int position) {
        return imp;
    }

    public void setImp(String imp) {
        this.imp = imp;
    }

    public String getCategory(int position) {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getQuestion(int position) {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public int getID(int position) {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAns(int position) {
        return Ans;
    }

    public void setAns(int ans) {
        Ans = ans;
    }

    public String getPhoto(int position) {
        return Photo;
    }

    public void setPhoto(String ans) {
        Photo = ans;
    }

    public String getColors(int position) {
        return Colors;
    }

    public void setColors(String colors) {
        Colors = colors;
    }

    public int getIsVideo(int position) {
        return isVideo;
    }

    public void setIsVideo(int isVideo) {
        this.isVideo = isVideo;
    }

    public String getName(int position) {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRelation(int position) {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }

    public String getDigiCategory(int position) {
        return DigiCategory;
    }

    public void setDigiCategory(String digiCategory) {
        DigiCategory = digiCategory;
    }

    public String getDoctor(int position) {
        return Doctor;
    }

    public void setDoctor(String doctor) {
        Doctor = doctor;
    }

    public int getIDDoc(int position) {
        return IDDoc;
    }

    public void setIDDoc(int IDDoc) {
        this.IDDoc = IDDoc;
    }

    public String getPhoneNo(int position) {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getRiskFactors(int position) {
        return riskFactors;
    }

    public void setRiskFactors(String riskFactors) {
        this.riskFactors = riskFactors;
    }

    public int getRisk(int position) {
        return risk;
    }

    public void setRisk(int risk) {
        this.risk = risk;
    }

    public String getOtherFactors(int position) {
        return OtherFactors;
    }

    public void setOtherFactors(String otherFactors) {
        OtherFactors = otherFactors;
    }

    public String getSymptoms(int position) {
        return Symptoms;
    }

    public void setSymptoms(String symptoms) {
        Symptoms = symptoms;
    }

    public int getSymID(int position) {
        return symID;
    }

    public void setSymID(int symID) {
        this.symID = symID;
    }

    public double getLDH(int position) {
        return LDH;
    }

    public void setLDH(double LDH) {
        this.LDH = LDH;
    }

    public double getCRP(int position) {
        return CRP;
    }

    public void setCRP(double CRP) {
        this.CRP = CRP;
    }

    public double getFerritin(int position) {
        return Ferritin;
    }

    public void setFerritin(double ferritin) {
        Ferritin = ferritin;
    }

    public double getLymphocytes(int position) {
        return Lymphocytes;
    }

    public void setLymphocytes(double lymphocytes) {
        Lymphocytes = lymphocytes;
    }

    public double getCount(int position) {
        return Count;
    }

    public void setCount(double count) {
        Count = count;
    }

    public double getDDimer(int position) {
        return DDimer;
    }

    public void setDDimer(double DDimer) {
        this.DDimer = DDimer;
    }

    public double getInterleukin(int position) {
        return Interleukin;
    }

    public void setInterleukin(double interleukin) {
        Interleukin = interleukin;
    }

    public double getPCT(int position) {
        return PCT;
    }

    public void setPCT(double PCT) {
        this.PCT = PCT;
    }

    public int getIDFirstLevel(int position) {
        return IDFirstLevel;
    }

    public void setIDFirstLevel(int IDFirstLevel) {
        this.IDFirstLevel = IDFirstLevel;
    }
}
