package com.admin.ecosense.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by parag on 12/01/17.
 */
public class PrefManager {
    public static final String KEY_MOBILE = "mobileno";
    private static final String NEW_VERSION = "Newversion";
    private static final String PREF_NAME = "Contri";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String NEW_UNIQUE_RIDE = "Ride";
    private static final String NEW_PROFILE = "profile";
    private static final String NEW_REVIEW = "review";
    private static final String NEW_NAME = "name";
    private static final String KEY_DROP_AT = "DROP";
    private static final String KEY_PICK_UP = "PICK";
    private static final String KEY_DROP_LAT = "dlat";
    private static final String KEY_DROP_LONG = "dlong";
    private static final String KEY_PICK_LAT = "plat";
    private static final String KEY_PICK_LONG = "plong";
    private static final String DPHONE = "dphn";
    private static final String NEW_IMP = "imp";
    private static final String TRAVEL = "travel";
    private static final String CONTACT = "contact";
    private static final String FEVER = "fever";
    private static final String NEW_UNIQUE_REFERAL_CODE = "refer";
    private static final String User_refernce_code_coupon_Amt = "User_refernce_code_coupon_Amt";


    private static final String KEY_DATE = "date";
    private static final String KEY_CLOSE1 = "close1";
    private static final String KEY_OPEN1 = "open1";

    private static final String KEY_RESPONSIBILITY = "RESPONSIBILITY";
    private static final String KET_DATE = "date";
    public static final String KEY_NAME = "Namme";
    private static final String KEY_ID = "ID";
    private static final String KEY_TIMER = "Timer";
    private static final String KEY_STARTTIME = "StartTime";
    private static final String KEY_PAUSED = "PAUSED";
    private static final String KEY_PAUSETIME = "PauseTime";
    private static final String KEY_TIMERRUNNING = "running";
    private static final String KEY_WID = "WID";
    private static final String KEYNOOFSITES = "nosites";
    private static final String ACCEPTED = "accepted";
    private static final String ACCEPTEDSITE = "asite";
    private static final String KEY_ENDTIME = "ETIME";
    private static final String COUNT = "count";
    private static final String LAUNCH = "lU";
    private static final String WORKID = "WORKID";
    private static final String KEY_PUSHMESSAGE = "pushmessage";
    private static final String MASK = "mask";
    private static final String WASHHANDS = "WASHHANDS";
    private static final String LINKS1 = "links";
    private static final String LINKS2 = "links2";
    private static final String LINKS3 = "links3";
    private static final String LINKS4 = "links4";
    private static final String LINKS5 = "links5";
    private static final String LINKS6 = "links6";
    private static final String TEST = "test";
    private static final String QURAN = "QURAN";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_FIRST_TIME_Q = "IsFirstQuestions";
    private static final String TIMER = "timer";
    private static final String FIRSTMAP = "firstmap";
    private static final String COUNTRYNAME = "COUNTRYNAME";
    private static final String DISTANCE = "distancemin";
    private static final String RTIMER = "RTIMER";
    private static final String FAMILYPHONE = "FAMILYPHONE";
    private static final String LIVELOCATION = "LIVELOCATION";
    private static final String DOB = "DOB";
    private static final String PAID = "paid";
    private static final String COVID = "covid";
    private static final String CONSULTATION = "consultation";
    private static final String WAQF = "waqf";
    private static final String RESPONSIBILITY = "role";
    private static final String AGED = "ages";
    private static final String ALARM = "alarms";
    private static final String MEDICAL = "medical";
    private static final String POD = "pod";
    private static final String PODMOBILE = "PODMOBILE";
    private static final String OXYGEN = "oxygen";
    private static final String SYMPTOMS = "SYMPTOMS";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    private ArrayList<String> arrPackage = new ArrayList<>();
    private static final  String isDeleted="isDeleted";
    private static final String Entered="enter";
    private String DigiCategory="DigiCategory";


    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstQuestion(int isFirstTime) {
        editor.putInt(IS_FIRST_TIME_Q, isFirstTime);
        editor.commit();
    }

    public int getFirstQuestion() {
        return pref.getInt(IS_FIRST_TIME_Q, 0);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public int getLaunch() {
        return pref.getInt(LAUNCH, 0);
    }

    public void setLaunch(int ride) {
        editor.putInt(LAUNCH, ride);
        editor.commit();
    }

    public String getSiteName() {
        return pref.getString(ACCEPTEDSITE,null);
    }

    public void setSiteName(String ride) {
        editor.putString(ACCEPTEDSITE, ride);
        editor.commit();
    }
    public int getMask() {
        return pref.getInt(MASK, 0);
    }

    public void setMask(int ride) {
        editor.putInt(MASK, ride);
        editor.commit();
    }
    public int getWashHands() {
        return pref.getInt(WASHHANDS, 0);
    }

    public void setWashHands(int ride) {
        editor.putInt(WASHHANDS, ride);
        editor.commit();
    }

    public int getWorkID() {
        return pref.getInt(WORKID, 0);
    }

    public void setWorkID(int ride) {
        editor.putInt(WORKID, ride);
        editor.commit();
    }

    public int getEntered() {
        return pref.getInt(Entered, 0);
    }

    public void setEntered(int ride) {
        editor.putInt(Entered, ride);
        editor.commit();
    }


    public int getRunning() {
        return pref.getInt(KEY_TIMERRUNNING, 0);
    }

    public void setRunning(int ride) {
        editor.putInt(KEY_TIMERRUNNING, ride);
        editor.commit();
    }


    public int getClockIn() {
        return pref.getInt(KEY_TIMER, 0);
    }

    public void setClockIn(int ride) {
        editor.putInt(KEY_TIMER, ride);
        editor.commit();
    }

    public int getPaused() {
        return pref.getInt(KEY_PAUSED, 0);
    }

    public void setPaused(int ride) {
        editor.putInt(KEY_PAUSED, ride);
        editor.commit();
    }


    public String getPushMessage() {
        return pref.getString(KEY_PUSHMESSAGE, null);
    }

    public void setPushMessage(String ride) {
        editor.putString(KEY_PUSHMESSAGE, ride);
        editor.commit();
    }



    public String getEndTime() {
        return pref.getString(KEY_ENDTIME, null);
    }

    public void setEndTime(String ride) {
        editor.putString(KEY_ENDTIME, ride);
        editor.commit();
    }

    public String getStartTime() {
        return pref.getString(KEY_STARTTIME, null);
    }

    public void setStartTime(String ride) {
        editor.putString(KEY_STARTTIME, ride);
        editor.commit();
    }
    public String getPausedTime() {
        return pref.getString(KEY_PAUSETIME, null);
    }

    public void setPausedTime(String ride) {
        editor.putString(KEY_PAUSETIME, ride);
        editor.commit();
    }
    public int getID() {
        return pref.getInt(KEY_ID, 0);
    }

    public void setID(int ride) {
        editor.putInt(KEY_ID, ride);
        editor.commit();
    }

    public int getKeyResponsibility() {
        return pref.getInt(KEY_RESPONSIBILITY, 0);
    }

    public void setKeyResponsibility(int ride) {
        editor.putInt(KEY_RESPONSIBILITY, ride);
        editor.commit();
    }

    public String getDate() {
        return pref.getString(KEY_DATE, null);
    }

    public void setDate(String date) {
        editor.putString(KEY_DATE, date);
        editor.commit();
    }

    public int getUser_refernce_code_coupon_Amt() {
        return pref.getInt(User_refernce_code_coupon_Amt, 0);
    }

    public void setUser_refernce_code_coupon_Amt(int ride) {
        editor.putInt(User_refernce_code_coupon_Amt, ride);
        editor.commit();
    }

    public String getReferalCode() {
        return pref.getString(NEW_UNIQUE_REFERAL_CODE, null);
    }

    public void setReferalCode(String ride) {
        editor.putString(NEW_UNIQUE_REFERAL_CODE, ride);
        editor.commit();
    }




    public String getClostingTime1() {
        return pref.getString(KEY_CLOSE1, null);
    }


    public void setClostingTime1(String cost) {
        editor.putString(KEY_CLOSE1, cost);
        editor.commit();
    }

    public String getOpeningTime1() {
        return pref.getString(KEY_OPEN1, null);
    }


    public void setOpeningTime1(String cost) {
        editor.putString(KEY_OPEN1, cost);
        editor.commit();
    }



    public String getPhone() {
        return pref.getString(DPHONE, null);
    }

    public void setPhone(String regId) {
        editor.putString(DPHONE, regId);
        editor.commit();
    }

    public String getName() {
        return pref.getString(NEW_NAME, null);
    }

    public void setName(String profile) {
        editor.putString(NEW_NAME, profile);
        editor.commit();
    }


    public String getProfile() {
        return pref.getString(NEW_PROFILE, null);
    }

    public void setProfile(String profile) {
        editor.putString(NEW_PROFILE, profile);
        editor.commit();
    }



    public void createLogin(String name,String mobileno) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_MOBILE, mobileno);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }


    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> profile = new HashMap<>();
        profile.put(KEY_NAME, pref.getString(KEY_NAME, null));
        profile.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));
        return profile;
    }

    public int getCount() {
        return pref.getInt(COUNT, 0);
    }

    public void setCount(int version) {
        editor.putInt(COUNT, version);
        editor.commit();
    }


    public int getNewVersion() {
        return pref.getInt(NEW_VERSION, 0);
    }

    public void setNewVersion(int version) {
        editor.putInt(NEW_VERSION, version);
        editor.commit();
    }

    public int getImp() {
        return pref.getInt(NEW_IMP, 0);
    }

    public void setImp(int imp) {
        editor.putInt(NEW_IMP, imp);
        editor.commit();
    }

    public int getTravel() {
        return pref.getInt(TRAVEL, 0);
    }

    public void setTravel(int imp) {
        editor.putInt(TRAVEL, imp);
        editor.commit();
    }
    public int getContact() {
        return pref.getInt(CONTACT, 0);
    }

    public void setContact(int imp) {
        editor.putInt(CONTACT, imp);
        editor.commit();
    }

    public int getFever() {
        return pref.getInt(FEVER, 0);
    }

    public void setFever(int imp) {
        editor.putInt(FEVER, imp);
        editor.commit();
    }

    public void deleteState() {
        editor.remove(KEY_MOBILE).commit();
        editor.clear().commit();

    }


    public int getisDeleted() {
        return pref.getInt(isDeleted, 0);
    }

    public void setisDeleted(int rate) {
        editor.putInt(isDeleted, rate);
        editor.commit();

    }




    public void setHomeLat(String rate) {
        editor.putString(KEY_PICK_LAT, rate);
        editor.commit();

    }

    public String getHomeLat() {
        return pref.getString(KEY_PICK_LAT, null);
    }

    public void setHomeLong(String rate) {
        editor.putString(KEY_PICK_LONG, rate);
        editor.commit();
    }

    public String getHomeLong() {
        return pref.getString(KEY_PICK_LONG, null);
    }


    public void setLinks1(String links) {
        editor.putString(LINKS1, links);
        editor.commit();
    }
    public String getLinks1() {
        return pref.getString(LINKS1, null);
    }

    public void setLinks2(String links) {
        editor.putString(LINKS2, links);
        editor.commit();
    }
    public String getLinks2() {
        return pref.getString(LINKS2, null);
    }

    public void setLinks3(String links) {
        editor.putString(LINKS3, links);
        editor.commit();
    }
    public String getLinks3() {
        return pref.getString(LINKS3, null);
    }

    public void setLinks4(String links) {
        editor.putString(LINKS4, links);
        editor.commit();
    }
    public String getLinks4() {
        return pref.getString(LINKS4, null);
    }

    public void setLinks5(String links) {
        editor.putString(LINKS5, links);
        editor.commit();
    }
    public String getLinks5() {
        return pref.getString(LINKS5, null);
    }

    public void setLinks6(String links) {
        editor.putString(LINKS6, links);
        editor.commit();
    }
    public String getLinks6() {
        return pref.getString(LINKS6, null);
    }
    public int getTest() {
        return pref.getInt(TEST, 0);
    }

    public void setTest(int ride) {
        editor.putInt(TEST, ride);
        editor.commit();
    }
    public int getQuran() {
        return pref.getInt(QURAN, 0);
    }

    public void setQuran(int ride) {
        editor.putInt(QURAN, ride);
        editor.commit();
    }

    public void setTimer(long i) {
        editor.putLong(TIMER, i);
        editor.commit();
    }
    public long getTimer() {
        return pref.getLong(TIMER, 0);
    }

    public void setRepeatTimer(long i) {
        editor.putLong(RTIMER, i);
        editor.commit();
    }
    public long getRepeatTimer() {
        return pref.getLong(RTIMER, 0);
    }

    public int getFirstMap() {
        return pref.getInt(FIRSTMAP, 0);
    }
    public void setFirstMap(int i) {
        editor.putInt(FIRSTMAP, i);
        editor.commit();
    }

    public void setCountry(String add) {
        editor.putString(COUNTRYNAME, add);
        editor.commit();
    }
    public String getCountry() {
        return pref.getString(COUNTRYNAME, null);
    }

    public Float getDistance() {
        return pref.getFloat(DISTANCE, 0);
    }
    public void setDistance(Float add) {
        editor.putFloat(DISTANCE, add);
        editor.commit();
    }

    public void setFamilyPhone(String familyPhone) {
        editor.putString(FAMILYPHONE, familyPhone);
        editor.commit();
    }
    public String getFamilyPhone() {
        return pref.getString(FAMILYPHONE, null);
    }

    public void setLiveLocation(int l) {
        editor.putInt(LIVELOCATION, l);
        editor.commit();
    }
    public int getLiveLocation() {
        return pref.getInt(LIVELOCATION, 0);
    }

    public void setDOB(String newBday) {
        editor.putString(DOB, newBday);
        editor.commit();
    }
    public String getDOB() {
        return pref.getString(DOB, null);
    }

    public void setPaid(int i) {
        editor.putInt(PAID, i);
        editor.commit();
    }
    public int getPaid() {
        return pref.getInt(PAID, 0);
    }

    public void setCovid(int second) {
        editor.putInt(COVID, second);
        editor.commit();
    }
    public int getCovid() {
        return pref.getInt(COVID, 0);
    }

    public void setConsultation(int i) {
        editor.putInt(CONSULTATION, i);
        editor.commit();
    }
    public int getConsultation() {
        return pref.getInt(CONSULTATION, 0);
    }

    public void setWAQF(String waqf) {
        editor.putString(WAQF, waqf);
        editor.commit();
    }

    public String getWAQF() {
        return pref.getString(WAQF, null);
    }

    public void setResponsibility(int role) {
        editor.putInt(RESPONSIBILITY, role);
        editor.commit();
    }
    public int getResponsibility() {
        return pref.getInt(RESPONSIBILITY, 0);
    }

    public void setAge(int parseInt) {
        editor.putInt(AGED, parseInt);
        editor.commit();
    }
    public int getAge() {
        return pref.getInt(AGED, 0);
    }


    public void setAlarm(int l) {
        editor.putInt(ALARM, l);
        editor.commit();
    }
    public int getAlarm() {
        return pref.getInt(ALARM, 0);
    }

    public void setDigiCategory(int amount) {
        editor.putInt(DigiCategory, amount);
        editor.commit();
    }

    public int getDigiCategory() {
        return pref.getInt(DigiCategory, 0);
    }

    public void setMedical(int medical) {
        editor.putInt(MEDICAL, medical);
        editor.commit();
    }
    public int getMedical() {
        return pref.getInt(MEDICAL, 0);
    }

    public void setPOD(int pod) {
        editor.putInt(POD, pod);
        editor.commit();
    }

    public int getPOD() {
        return pref.getInt(POD, 0);
    }

    public void setPODmobile(String phoneNo) {
        editor.putString(PODMOBILE, phoneNo);
        editor.commit();
    }
    public String getPODmobile() {
        return pref.getString(PODMOBILE, null);
    }

    public void setOxygen(int i) {
        editor.putInt(OXYGEN, i);
        editor.commit();
    }
    public int getOxygen() {
        return pref.getInt(OXYGEN, 0);
    }

    public void setSymptoms(int i) {
        editor.putInt(SYMPTOMS, i);
        editor.commit();
    }
    public int getSymptoms() {
        return pref.getInt(SYMPTOMS, 0);
    }
}