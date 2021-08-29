package police.bharti.katta.view.saravmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import police.bharti.katta.R;
import police.bharti.katta.adapter.SaravQuestionAdapter;
import police.bharti.katta.model.SaravQuestionModel;
import police.bharti.katta.model.TestSeriesQuestionModel;
import police.bharti.katta.util.MyDb;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.util.RetrofitClient;
import police.bharti.katta.view.testserise.TestSeriesQuestions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleSaravQuestionView extends AppCompatActivity implements View.OnClickListener {
    int cnt = 0;
    ArrayList list_question[];
    TextView btn_a, btn_b, btn_c, btn_d;
    TextView question_number;
    TextView txt_hint;
    String reportMessage = "";

    TextView txt_question;
    // TextView txt_timmer, txt_total, txt_answer, txt_unanser;
    //  Button btn_submit;
    int selected_item = 0;
    Context context;
    ProgressDialog progressDialog;
    int correct = 0;
    int unanswered = 0;
    int wrong = 0;
    int total = 0;
    Dialog dialog_sarav;
    List<SaravQuestionModel> saravMenuModels;
    ArrayList saa;
    int localStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_sarav_question_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      /*  getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);*/

        context = SingleSaravQuestionView.this;
        init();
    }

    private void init() {
        cnt = 0;
        btn_a = findViewById(R.id.btn_a);
        btn_b = findViewById(R.id.btn_b);
        btn_c = findViewById(R.id.btn_c);
        btn_d = findViewById(R.id.btn_d);
        //     btn_submit = findViewById(R.id.btn_submit);

        question_number = findViewById(R.id.txt_question_no);
        txt_question = findViewById(R.id.txt_question);
        btn_a.setOnClickListener(this);
        btn_b.setOnClickListener(this);
        btn_c.setOnClickListener(this);
        btn_d.setOnClickListener(this);
        //  btn_submit.setOnClickListener(this);
  /*      txt_timmer = (TextView) findViewById(R.id.txt_timmer);
        txt_total = (TextView) findViewById(R.id.txt_total);
        txt_answer = (TextView) findViewById(R.id.txt_answer);
        txt_unanser = (TextView) findViewById(R.id.txt_unanswers);
*/

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("प्रतिक्षा करा..");
        setTitle("सराव प्रश्न");
        //  Toast.makeText(context,""+new MyDb(context).getSaravExist(Preferences.get(context, Preferences.SELECTEDSARAVID)),Toast.LENGTH_LONG).show();
        if (Integer.parseInt(new MyDb(context).getSaravExist(Preferences.get(context, Preferences.SELECTEDSARAVID))) > 0) {

        } else {
            if (new MyDb(context).insertSarav(Preferences.get(context, Preferences.SELECTEDSARAVID), "0")) {
                Log.i("Record Created", "New Sarav Found");

            }
        }
        getMenuList();

        saa = new MyDb(context).getSaravQuestions(Preferences.get(context, Preferences.SELECTEDSARAVID));
        if (saa.size() == 0) {
            localStatus = 1;
        }
        Toast.makeText(context, "Local Status is "+localStatus, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            // app icon in action bar clicked; goto parent activity.
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMenuList() {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();

            String mobile = Preferences.get(context, Preferences.USER_MOBILE);
            String id = Preferences.get(context, Preferences.SELECTEDSARAVID);
            String type = "1";

            Call<List<SaravQuestionModel>> call = RetrofitClient.getInstance().getMyApi().getSaravQuestions(mobile, id, type);
            call.enqueue(new Callback<List<SaravQuestionModel>>() {
                @Override
                public void onResponse(Call<List<SaravQuestionModel>> call, Response<List<SaravQuestionModel>> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //   Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if (response.body() != null) {
                        saravMenuModels = response.body();
                        try {
                            //   Toast.makeText(TestSeriesQuestions.this, "Total Questions : "+saravMenuModels.size(), Toast.LENGTH_SHORT).show();
                            addQuestionInList(saravMenuModels);

                            Toast.makeText(context, "s =" + saa.size() + "  live " + saravMenuModels.size(), Toast.LENGTH_SHORT).show();
                            if (localStatus == 1) {
                                insertRecords();
                            } else if (saa.size()+1 < saravMenuModels.size()) {
                                insertRecords();
                            } else {
                                showRecords(saa);
                            }


                            // list_question=saravMenuModels;
                            //   showQuestion(cnt);
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<SaravQuestionModel>> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    private void showRecords(ArrayList s) {
        try {
       /*     ArrayList s=new MyDb(context).getSaravMaster();
            adapter = new SaravQuestionAdapter((ArrayList) s, context);
            rc_listofsaravmenu.setAdapter(adapter);*/
            //Toast.makeText(context, "Entered "+saravMenuModels, Toast.LENGTH_SHORT).show();

            ArrayList sa = s;
            if (sa != null) {
                Toast.makeText(context, "" + sa.size(), Toast.LENGTH_SHORT).show();
                addQuestionInList(sa);

            }

        } catch (Exception r) {
            Log.i("Error in Load", r.getMessage());

        }

    }

    private void addQuestionInList(List<SaravQuestionModel> saravMenuModels) {
        cnt = 0;
        list_question = new ArrayList[saravMenuModels.size()];
        for (int i = 0; i < saravMenuModels.size(); i++) {
            list_question[i] = new ArrayList();
        }
        for (int i = 0; i < saravMenuModels.size(); i++) {
            list_question[i].add(saravMenuModels.get(i).getQuestion());  // Question number
            list_question[i].add(saravMenuModels.get(i).getOpt1());  // Correct Option
            list_question[i].add(saravMenuModels.get(i).getOpt2());
            list_question[i].add(saravMenuModels.get(i).getOpt3());
            list_question[i].add(saravMenuModels.get(i).getOpt4());
            list_question[i].add(saravMenuModels.get(i).getCorrect());
            list_question[i].add(saravMenuModels.get(i).getHint());
            list_question[i].add(0);
            list_question[i].add(saravMenuModels.get(i).getId());
        }


        //  Toast.makeText(context, "After List : "+list_question.length, Toast.LENGTH_SHORT).show();

       /* String dura=Preferences.get(context, Preferences.LIVESELECTEDPAPERDURATION);
        new CountDownTimer(10000 * (Integer.parseInt(dura.trim())), 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                txt_timmer.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec));
            }

            // When the task is over it will print 00:00:00 there
            public void onFinish() {
                txt_timmer.setText("00:00:00");
                submittest();
            }
        }.start();*/

        //  Toast.makeText(context,"Cusrrent "+new MyDb(context).getSaravCnt(Preferences.get(context, Preferences.SELECTEDSARAVID)),Toast.LENGTH_LONG).show();

        if (new MyDb(context).getSaravCnt(Preferences.get(context, Preferences.SELECTEDSARAVID)) != null) {
            int ccnt = Integer.parseInt(new MyDb(context).getSaravCnt(Preferences.get(context, Preferences.SELECTEDSARAVID)));
            if (ccnt < list_question.length) {
                showQuestion(ccnt);
                cnt = ccnt;
            } else {
                showQuestion(cnt);
            }
        } else {
            showQuestion(cnt);
        }
    }

    public void next(View v) {
        try {

            if (cnt < list_question.length - 1) {
                cnt++;
                if (new MyDb(context).updatSarav(Preferences.get(context, Preferences.SELECTEDSARAVID), "" + cnt)) {
                    Log.i("Record ", "Updated");
                }
            } else {
                //        Toast.makeText(this, "No More Question", Toast.LENGTH_SHORT).show();
            }
            showQuestion(cnt);
            highlightButton(0);
        } catch (Exception e) {
            Toast.makeText(this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showQuestion(int cnt) {
        try {

            question_number.setText("" + (cnt + 1) + "/" + list_question.length);
            txt_question.setText("" + list_question[cnt].get(0).toString().trim());
            btn_a.setText("" + list_question[cnt].get(1).toString().trim());
            btn_b.setText("" + list_question[cnt].get(2).toString().trim());
            btn_c.setText("" + list_question[cnt].get(3).toString().trim());
            btn_d.setText("" + list_question[cnt].get(4).toString().trim());
            showListElements();
            //     Toast.makeText(context, ""+list_question[cnt].size(), Toast.LENGTH_SHORT).show();
            int temp = Integer.parseInt(list_question[cnt].get(7).toString().trim());
            if (temp != 0)
                highlightButton(temp);
            else
                highlightButton(0);

        } catch (Exception e) {
            Log.i("Error is 1234", e.getMessage());
        }
    }


    public void previous(View v) {
        try {

            if (cnt > 0) {
                cnt--;
                //    Toast.makeText(TestSeriesQuestions.this, "count " + cnt, Toast.LENGTH_LONG).show();

            } else {
                //         Toast.makeText(this, "Start Position", Toast.LENGTH_SHORT).show();
            }
            showQuestion(cnt);
            highlightButton(0);
        } catch (Exception e) {
            Toast.makeText(this, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View v) {
        selected_item = 0;
        switch (v.getId()) {
            case R.id.btn_a:
                selected_item = 1;
                highlightButton(1);
                break;

            case R.id.btn_b:
                selected_item = 2;
                highlightButton(2);
                break;

            case R.id.btn_c:
                selected_item = 3;
                highlightButton(3);
                break;

            case R.id.btn_d:
                selected_item = 4;
                highlightButton(4);
                break;
            case R.id.btn_submit:
                //   Toast.makeText(context, "Correct : "+correct+" \n Wrong :"+wrong, Toast.LENGTH_SHORT).show();
                //   Submit();

                break;

        }
        //  Toast.makeText(this, "Current Selection " + cnt, Toast.LENGTH_SHORT).show();

        list_question[cnt].set(7, selected_item);
        if (list_question[cnt].get(5).toString().trim().equals("1"))
            btn_a.setBackgroundResource(R.drawable.correctansbutton);
        else if (list_question[cnt].get(5).toString().trim().equals("2"))
            btn_b.setBackgroundResource(R.drawable.correctansbutton);
        else if (list_question[cnt].get(5).toString().trim().equals("3"))
            btn_c.setBackgroundResource(R.drawable.correctansbutton);
        else if (list_question[cnt].get(5).toString().trim().equals("4"))
            btn_d.setBackgroundResource(R.drawable.correctansbutton);

    }

    /*   private void Submit() {
           AlertDialog.Builder builder
                   = new AlertDialog
                   .Builder(context);

           // Set the message show for the Alert time
           builder.setMessage("Do you want submit test?");

           // Set Alert Title
           builder.setTitle("Alert !");

           // Set Cancelable false
           // for when the user clicks on the outside
           // the Dialog Box then it will remain show
           builder.setCancelable(false);

           // Set the positive button with yes name
           // OnClickListener method is use of
           // DialogInterface interface.

           builder
                   .setPositiveButton(
                           "Yes",
                           new DialogInterface
                                   .OnClickListener() {

                               @Override
                               public void onClick(DialogInterface dialog,
                                                   int which)
                               {
                                   submittest();

                               }
                           });

           // Set the Negative button with No name
           // OnClickListener method is use
           // of DialogInterface interface.
           builder
                   .setNegativeButton(
                           "No",
                           new DialogInterface
                                   .OnClickListener() {

                               @Override
                               public void onClick(DialogInterface dialog,
                                                   int which)
                               {

                                   // If user click no
                                   // then dialog box is canceled.
                                   dialog.cancel();
                               }
                           });

           // Create the Alert dialog
           AlertDialog alertDialog = builder.create();

           // Show the Alert Dialog box
           alertDialog.show();

       }


       private void submittest() {

           try {
               progressDialog.setCanceledOnTouchOutside(false);
               if (!progressDialog.isShowing())
                   progressDialog.show();

               Call<String> call = RetrofitClient.getInstance().getMyApi().submitTestSeriesResult(Preferences.get(context,Preferences.SELECTEDPAPERID).toString(),Preferences.get(context,Preferences.USER_MOBILE).toString(),""+correct,""+total,""+unanswered,""+wrong);
               call.enqueue(new Callback<String>() {
                   @Override
                   public void onResponse(Call<String> call, Response<String> response) {

                       if (progressDialog.isShowing())
                           progressDialog.dismiss();

                       if (response.body() != null) {

                           try {
                               String saravMenuModels = response.body();
                               Toast.makeText(context, ""+saravMenuModels, Toast.LENGTH_SHORT).show();
                               finish();
                           } catch (NullPointerException e) {
                               Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                           } catch (Exception e) {
                               Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                           }
                       } else {
                           Toast.makeText(context, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                       }
                   }

                   @Override
                   public void onFailure(Call<String> call, Throwable t) {
                       if (progressDialog.isShowing())
                           progressDialog.dismiss();

                       Log.e("Error is", t.getMessage());
                   }
               });
           } catch (Exception e) {

           }

       }
   */
    private void highlightButton(int i) {


        switch (i) {
            case 0:
                // Toast.makeText(context, ""+list_question[cnt].get(5), Toast.LENGTH_SHORT).show();
                btn_a.setBackgroundResource(R.drawable.defaultbutton);
                btn_b.setBackgroundResource(R.drawable.defaultbutton);
                btn_c.setBackgroundResource(R.drawable.defaultbutton);
                btn_d.setBackgroundResource(R.drawable.defaultbutton);
                break;
            case 1:
                btn_a.setBackgroundResource(R.drawable.selectedbutton);
                btn_b.setBackgroundResource(R.drawable.defaultbutton);
                btn_c.setBackgroundResource(R.drawable.defaultbutton);
                btn_d.setBackgroundResource(R.drawable.defaultbutton);
                break;
            case 2:
                btn_a.setBackgroundResource(R.drawable.defaultbutton);
                btn_b.setBackgroundResource(R.drawable.selectedbutton);
                btn_c.setBackgroundResource(R.drawable.defaultbutton);
                btn_d.setBackgroundResource(R.drawable.defaultbutton);
                break;
            case 3:
                btn_a.setBackgroundResource(R.drawable.defaultbutton);
                btn_b.setBackgroundResource(R.drawable.defaultbutton);
                btn_c.setBackgroundResource(R.drawable.selectedbutton);
                btn_d.setBackgroundResource(R.drawable.defaultbutton);
                break;
            case 4:
                btn_a.setBackgroundResource(R.drawable.defaultbutton);
                btn_b.setBackgroundResource(R.drawable.defaultbutton);
                btn_c.setBackgroundResource(R.drawable.defaultbutton);
                btn_d.setBackgroundResource(R.drawable.selectedbutton);
                break;

        }


    }

    public void showListElements() {
        try {
            total = wrong = unanswered = correct = 0;
            total = list_question.length;
            //Toast.makeText(this, "Length "+list_question.length, Toast.LENGTH_SHORT).show();
            for (ArrayList a : list_question) {
                Log.i("Elemements", a.toString() + "  " + a.get(0) + " = " + a.get(2));
                if (Integer.parseInt(a.get(2).toString().trim()) == 0) {
                    unanswered++;
                } else if (Integer.parseInt(a.get(1).toString().trim()) == Integer.parseInt(a.get(2).toString().trim())) {
                    correct++;
                } else {
                    wrong++;
                }
            }


          /*  txt_answer.setText("" + (total - unanswered));
            txt_total.setText("" + total);
            txt_unanser.setText("" + unanswered);*/

        } catch (Exception r) {

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            // UrlRender.cancel(true);
        } catch (Exception e) {

        }
    }

    public void hint(View v) {

        Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_showbookdetails);

        TextView textView = dialog.findViewById(R.id.txt_details);
        TextView txt_title = dialog.findViewById(R.id.txt_title);
        txt_title.setText("Hint");
        ImageView btn_close = dialog.findViewById(R.id.close);
        Button btn_purchase = dialog.findViewById(R.id.btn_purchase);
        btn_purchase.setText("Close");
        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        textView.setText(Html.fromHtml("<pre>" + list_question[cnt].get(6).toString().trim() + "</pre>"));
        dialog.show();

    }

    public void report(View view) {


        dialog_sarav = new Dialog(context);
        dialog_sarav.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_sarav.setContentView(R.layout.popup_report_sarav_question);

        EditText editText_message = dialog_sarav.findViewById(R.id.et_message);
        ImageView btn_close = dialog_sarav.findViewById(R.id.close);
        Button btn_purchase = dialog_sarav.findViewById(R.id.btn_purchase);
        //btn_purchase.setText("Close");
        btn_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = editText_message.getText().toString().trim();
                if (message.trim().equals("")) {
                    editText_message.setError("Please Enter Message.");

                } else {
                    reportQuestion(message);
                }


            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_sarav.dismiss();
            }
        });

        dialog_sarav.show();


    }


    private void reportQuestion(String msg) {
        try {
            if (!progressDialog.isShowing())
                progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            String mobile = Preferences.get(context, Preferences.USER_MOBILE);
            String id = list_question[cnt].get(8).toString();
            String Message = msg;

            Call<String> call = RetrofitClient.getInstance().getMyApi().submitSravaReport(id, mobile, Message);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    //   Toast.makeText(context, "Size is " + response.body().size(), Toast.LENGTH_SHORT).show();
                    if (response.body() != null) {
                        String saravMenuModels = response.body();
                        try {
                            Toast.makeText(context, "Total Questions : " + saravMenuModels, Toast.LENGTH_SHORT).show();
                            dialog_sarav.dismiss();
                        } catch (NullPointerException e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Error is " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, "माहिती उपलब्ध नाही.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Log.e("Error is", t.getMessage());
                }
            });
        } catch (Exception e) {

        }
    }


    public void showquery(View view) {
        insertRecords();
    }

    private void insertRecords() {
        try {
            if (saravMenuModels != null) {
                String s = "insert into tbl_sarav_question3 (id,question,opt1,opt2,opt3,opt4,correct,hint,status,cdate,saravid) values";
                String data = " ";
                for (SaravQuestionModel m : saravMenuModels) {
                    Log.i("Title ", m.getQuestion());
                    data += "(" + m.getId() + "," +
                            "'" + m.getQuestion() + "'," +
                            "'" + m.getOpt1() + "'," +
                            "'" + m.getOpt2() + "'," +
                            "'" + m.getOpt3() + "'," +
                            "'" + m.getOpt4() + "'," +
                            "'" + m.getCorrect() + "'," +
                            "'" + m.getHint() + "'," +
                            "'" + m.getStatus() + "'," +
                            "'" + m.getCdate() + "'," +
                            "'" + m.getSaravid() + "'" +
                            "),";
                }
                s = s + " " + data;
                s = s.trim();
                s = s.substring(0, s.length() - 1);
                Log.i("Data is ", data + "\n" + s);

                if (new MyDb(context).insertQuestion(s)) {
                    Toast.makeText(context, "Record Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Getting Error to Insert Bulk Records", Toast.LENGTH_LONG).show();
                }

                saa = new MyDb(context).getSaravQuestions(Preferences.get(context, Preferences.SELECTEDSARAVID));
                showRecords(saa);

            }
        } catch (Exception e) {

        }
    }

    public void showrecordinlist(View v) {
        try {
       /*     ArrayList s=new MyDb(context).getSaravMaster();
            adapter = new SaravQuestionAdapter((ArrayList) s, context);
            rc_listofsaravmenu.setAdapter(adapter);*/
            //Toast.makeText(context, "Entered "+saravMenuModels, Toast.LENGTH_SHORT).show();

            ArrayList sa = new MyDb(context).getSaravQuestions(Preferences.get(context, Preferences.SELECTEDSARAVID));
            if (saravMenuModels != null) {
                Toast.makeText(context, "" + sa.size(), Toast.LENGTH_SHORT).show();
                addQuestionInList(sa);
            }

        } catch (Exception r) {
            Log.i("Error in Load", r.getMessage());

        }

    }


}