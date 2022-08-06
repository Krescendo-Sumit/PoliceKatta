package police.bharti.katta.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import police.bharti.katta.model.SaravMenuModel;
import police.bharti.katta.model.SaravQuestionModel;


public class MyDb extends SQLiteOpenHelper {

    final static String DBName = "weekree";
    final static int version = 1;

    public MyDb(Context context) {
        super(context, DBName, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
//[{"UserId":1,"PersonName":"VAN-MH-20-A 2245","UserName":"pratik","Password":"12345","UserTypeId":1}]
        try {

            String tbl_user = "CREATE TABLE tbl_sarav(id INTEGER PRIMARY KEY,saravid text,cnt text)";
            db.execSQL(tbl_user);
            String tbl_saravmenu = "CREATE TABLE `tbl_saravprashn_master` (\n" +
                    "\t`id` INT(11) NOT NULL ,\n" +
                    "\t`title` TEXT ,\n" +
                    "\t`details` TEXT ,\n" +
                    "\t`status` INT(11) ,\n" +
                    "\t`imagepath` TEXT ,\n" +
                    "\t`accountid` INT(11)  ,\n" +
                    "\t`master_accountid` INT(11)  ,\n" +
                    "\t`noofcnt` INT(11)  ,\n" +
                    "\tPRIMARY KEY (`id`)\n" +
                    ")";
            db.execSQL(tbl_saravmenu);
            String tbl_saravquestion = "CREATE TABLE `tbl_sarav_question3` (\n" +
                    "\t`id` INT(11) NOT NULL ,\n" +
                    "\t`question` TEXT ,\n" +
                    "\t`opt1` TEXT ,\n" +
                    "\t`opt2` TEXT ,\n" +
                    "\t`opt3` TEXT ,\n" +
                    "\t`opt4` TEXT ,\n" +
                    "\t`correct` INT(11) ,\n" +
                    "\t`hint` TEXT ,\n" +
                    "\t`status` TEXT ,\n" +
                    "\t`cdate` TEXT ,\n" +
                    "\t`saravid` INT(11) NOT NULL,\n" +
                    "\tPRIMARY KEY (`id`)\n" +
                    ")";
            db.execSQL(tbl_saravquestion);

        } catch (Exception e) {

        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    //user(id INTEGER PRIMARY KEY,uid text,pname text,uname text,usertype text,pass text

    public boolean insertSarav(String sid, String cnt) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "insert into tbl_sarav(id,saravid,cnt) values(null,'" + sid + "','" + cnt + "'); ";
            Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            Log.i("Query is -------> Done ", "" + q);
            return true;
        } catch (Exception e) {
            Log.i("Error is Add User", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }


    public boolean updatSarav(String sid, String cnt) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = "update tbl_sarav set cnt='" + cnt + "' where saravid=" + sid;
            Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            Log.i("Query is -------> Done ", "" + q);
            return true;
        } catch (Exception e) {
            Log.i("Error is Add User", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }

    public boolean insertQuestion(String query) {

        SQLiteDatabase mydb = null;
        try {
            removeMaster("Delete from tbl_sarav_question3");
            mydb = this.getReadableDatabase();
            String q = query;
            Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            Log.i("Query is -------> Done ", "" + q);
            return true;
        } catch (Exception e) {
            Log.i("Error is Add User", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }


    public boolean insertMaster(String query) {

        SQLiteDatabase mydb = null;
        try {
            removeMaster("Delete from tbl_saravprashn_master");
            mydb = this.getReadableDatabase();
            String q = query;
            Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            Log.i("Query is -------> Done ", "" + q);
            return true;
        } catch (Exception e) {
            Log.i("Error is Add User", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }

    public boolean removeMaster(String query) {

        SQLiteDatabase mydb = null;
        try {
            mydb = this.getReadableDatabase();
            String q = query;
            Log.i("Query is -------> ", "" + q);
            mydb.execSQL(q);
            Log.i("Query is -------> Done ", "" + q);
            return true;
        } catch (Exception e) {
            Log.i("Error is Add User", "" + e.getMessage());
            return false;
        } finally {
            mydb.close();
        }

    }

    public Vector getSarav() {
        SQLiteDatabase mydb = null;
        String k = "";
        Vector v = new Vector();
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_sarav";

            Cursor c = mydb.rawQuery(q, null);

            if (c.moveToNext()) {
                v.addElement(c.getString(0).trim());
                v.addElement(c.getString(1).trim());
                v.addElement(c.getString(2).trim());
            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }

    public ArrayList getSaravMaster() {
        SQLiteDatabase mydb = null;
        String k = "";
        Vector v = new Vector();
        ArrayList<SaravMenuModel> list = new ArrayList<SaravMenuModel>();
        int i = 0;
        try {

            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_saravprashn_master";
            Cursor c = mydb.rawQuery(q, null);
            while (c.moveToNext()) {
                ++i;
            }
            SaravMenuModel s = null;
            c= mydb.rawQuery(q, null);
            while (c.moveToNext()) {
                s = new SaravMenuModel();
                s.setId(c.getString(0).trim());
                s.setTitle(c.getString(1).trim());
                s.setDetails(c.getString(2).trim());
                s.setStatus(c.getString(3).trim());
                s.setImagepath(c.getString(4).trim());
                s.setAccountid(c.getString(5).trim());
                s.setMaster_accountid(c.getString(6).trim());
                s.setNoofcnt(c.getString(7).trim());
                list.add(s);


/*
                v.addElement(c.getString(0).trim());// id
                v.addElement(c.getString(1).trim());//title
                v.addElement(c.getString(2).trim());//detail
                v.addElement(c.getString(3).trim());//status
                v.addElement(c.getString(4).trim());//imgpath
                v.addElement(c.getString(5).trim());//accountid
                v.addElement(c.getString(6).trim());//masterid*/

            }
            return list;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }
    public ArrayList getSaravQuestions(String id) {
        SQLiteDatabase mydb = null;
        String k = "";
        Vector v = new Vector();
        ArrayList<SaravQuestionModel> list = new ArrayList<SaravQuestionModel>();
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_sarav_question3 where saravid="+id+" order by id desc";
            Cursor c = mydb.rawQuery(q, null);
            while (c.moveToNext()) {
                i++;
            }
            SaravQuestionModel s = null;
            c= mydb.rawQuery(q, null);
            while (c.moveToNext()) {
                s = new SaravQuestionModel();
                s.setId(c.getString(0).trim());
                s.setQuestion(c.getString(1).trim());
                s.setOpt1(c.getString(2).trim());
                s.setOpt2(c.getString(3).trim());
                s.setOpt3(c.getString(4).trim());
                s.setOpt4(c.getString(5).trim());
                s.setCorrect(c.getString(6).trim());
                s.setHint(c.getString(7).trim());
                s.setStatus(c.getString(8).trim());
                s.setCdate(c.getString(9).trim());
                s.setSaravid(c.getString(10).trim());
                list.add(s);
                i++;

/*
                v.addElement(c.getString(0).trim());// id
                v.addElement(c.getString(1).trim());//title
                v.addElement(c.getString(2).trim());//detail
                v.addElement(c.getString(3).trim());//status
                v.addElement(c.getString(4).trim());//imgpath
                v.addElement(c.getString(5).trim());//accountid
                v.addElement(c.getString(6).trim());//masterid*/

            }
            return list;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }


    public String getSaravCnt(String saravid) {
        SQLiteDatabase mydb = null;
        String k = "";
        String v = null;
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM tbl_sarav where saravid=" + saravid;

            Cursor c = mydb.rawQuery(q, null);

            if (c.moveToNext()) {
                v = c.getString(2).trim();
            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }

    public String getSaravExist(String saravid) {
        SQLiteDatabase mydb = null;
        String k = "";
        String v = null;
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  count(*)as cnt FROM tbl_sarav where saravid=" + saravid;

            Cursor c = mydb.rawQuery(q, null);

            if (c.moveToNext()) {
                v = c.getString(0).trim();
            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }


    public String getUserName() {
        SQLiteDatabase mydb = null;
        String k = "";
        String v = null;
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM user";

            Cursor c = mydb.rawQuery(q, null);

            if (c.moveToNext()) {

                v = "" + c.getString(2).trim();

            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }

    public String getUserMobile() {
        SQLiteDatabase mydb = null;
        String k = "";
        String v = null;
        int i = 0;
        try {
            mydb = this.getReadableDatabase();
            String q = "SELECT  * FROM user";

            Cursor c = mydb.rawQuery(q, null);

            if (c.moveToNext()) {

                v = "" + c.getString(3).trim();

            }

            return v;
        } catch (Exception e) {

            return null;
        } finally {
            mydb.close();
        }
    }


}
