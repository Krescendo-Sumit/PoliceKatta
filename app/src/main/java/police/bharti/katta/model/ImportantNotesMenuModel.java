package police.bharti.katta.model;

public class ImportantNotesMenuModel {

    String id;
    String title;
    String details;
    String status;
    String imagepath;
    String accountid;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getMaster_accountid() {
        return master_accountid;
    }

    public void setMaster_accountid(String master_accountid) {
        this.master_accountid = master_accountid;
    }

    String master_accountid;
}
