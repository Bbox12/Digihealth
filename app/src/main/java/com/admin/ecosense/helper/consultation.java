package com.admin.ecosense.helper;

public class consultation {

    private int ID,PaymentMode,Age;
    private String TimeSlot,Question,RiskFactor,BookDate,OtherRiskFactor;

    public int getAge(int position) {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getID(int position) {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPaymentMode(int position) {
        return PaymentMode;
    }

    public void setPaymentMode(int paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getTimeSlot(int position) {
        return TimeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        TimeSlot = timeSlot;
    }

    public String getQuestion(int position) {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getRiskFactor(int position) {
        return RiskFactor;
    }

    public void setRiskFactor(String riskFactor) {
        RiskFactor = riskFactor;
    }

    public String getBookDate(int position) {
        return BookDate;
    }

    public void setBookDate(String bookDate) {
        BookDate = bookDate;
    }

    public String getOtherRiskFactor(int position) {
        return OtherRiskFactor;
    }

    public void setOtherRiskFactor(String otherRiskFactor) {
        OtherRiskFactor = otherRiskFactor;
    }
}
