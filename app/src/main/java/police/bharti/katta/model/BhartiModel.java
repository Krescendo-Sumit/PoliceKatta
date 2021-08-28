package police.bharti.katta.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BhartiModel  implements Serializable {
    String id;
    String title;
    String details;
    String qualification;
    String writtentext;
    String ground;
    String readingbooks;
    String classneed;
    String writtenskill;
    String groundtesttraining;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getWrittentext() {
        return writtentext;
    }

    public void setWrittentext(String writtentext) {
        this.writtentext = writtentext;
    }

    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public String getReadingbooks() {
        return readingbooks;
    }

    public void setReadingbooks(String readingbooks) {
        this.readingbooks = readingbooks;
    }

    public String getClassneed() {
        return classneed;
    }

    public void setClassneed(String classneed) {
        this.classneed = classneed;
    }

    public String getWrittenskill() {
        return writtenskill;
    }

    public void setWrittenskill(String writtenskill) {
        this.writtenskill = writtenskill;
    }

    public String getGroundtesttraining() {
        return groundtesttraining;
    }

    public void setGroundtesttraining(String groundtesttraining) {
        this.groundtesttraining = groundtesttraining;
    }

    public String getDailyschedule() {
        return dailyschedule;
    }

    public void setDailyschedule(String dailyschedule) {
        this.dailyschedule = dailyschedule;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public String getApplylink() {
        return applylink;
    }

    public void setApplylink(String applylink) {
        this.applylink = applylink;
    }

    public String getNotificationdocument() {
        return notificationdocument;
    }

    public void setNotificationdocument(String notificationdocument) {
        this.notificationdocument = notificationdocument;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getMaster_accountid() {
        return master_accountid;
    }

    public void setMaster_accountid(String master_accountid) {
        this.master_accountid = master_accountid;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    String dailyschedule;
    String videolink;
    String applylink;
    String notificationdocument;
    String createddate;
    String master_accountid;
    String accountid;

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    String imagepath;
    String filepath;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    String data;
}
