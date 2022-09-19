package police.bharti.katta.view.pdfviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import police.bharti.katta.R;
import police.bharti.katta.util.Constants;

public class PDFFileViewer extends AppCompatActivity {
    PDFView pdfView;
    RetrivePDFfromUrl UrlRender;

    // url of our PDF file.
    String pdfurl = "";
    ProgressDialog progressDialog;
    String filename = "";
    Spinner spinner;
    ArrayAdapter adapter;
    String[] strPageCounts;
    Button txt_goto;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*    getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);*/
        setContentView(R.layout.activity_p_d_f_file_viewer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pdfView = findViewById(R.id.idPDFView);
       // pdfView.setSwipeVertical(false);

        spinner = findViewById(R.id.spinner);
        txt_goto = findViewById(R.id.txt_goto);
       // seekBar=findViewById(R.id.seekBar);
        progressDialog = new ProgressDialog(PDFFileViewer.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Please wait...");
        progressDialog.setMessage("प्रतिक्षा करा..");
        setTitle("माहिती बघा");
        filename = getIntent().getExtras().getString("fname").trim();

     //   pdfView.setScrollBarSize(30);

        if (filename != null) {
            if (filename.trim().equals("")) {
                Toast.makeText(this, "File Missing..", Toast.LENGTH_SHORT).show();
            } else {
                pdfurl = Constants.BASE_URL + filename;
                Log.i("File URl is ", pdfurl);

                UrlRender = new RetrivePDFfromUrl();
                UrlRender.execute(pdfurl);
            }
        } else {
            Toast.makeText(this, "File is Missing..", Toast.LENGTH_SHORT).show();
        }

       /* seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pdfView.jumpTo(progress + 1);
                Toast.makeText(PDFFileViewer.this, "Page No. "+progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/


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

    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();
            //   pdfView.computeScroll();
            //  pdfView.setScrollBarSize(200);
            //  pdfView.jumpTo(2);

            //  Toast.makeText(PDFFileViewer.this, ""+pdfView.getPageCount(), Toast.LENGTH_SHORT).show();
             setSpinnerAdapter(pdfView.getPageCount());

            progressDialog.dismiss();
        }
    }

    void setSpinnerAdapter(int pageCount) {
        try {
          //  seekBar.setMax(pageCount);
            pdfView.setScrollBarSize(200);
            strPageCounts = new String[pageCount];
            for (int i = 0; i < pageCount; i++) {
                strPageCounts[i] = "" + (i + 1);
            }
            adapter = new ArrayAdapter(PDFFileViewer.this, android.R.layout.simple_list_item_1, strPageCounts);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                   // if (position + 1 < pdfView.getPageCount())
                        pdfView.jumpTo(position + 1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(PDFFileViewer.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            UrlRender.cancel(true);
        } catch (Exception e) {

        }
    }

    public void go(View v) {
        try {
            txt_goto.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);
           // pdfView.setPageFling(true);
            setSpinnerAdapter(pdfView.getPageCount());
            pdfView.setScrollBarSize(200);
        } catch (Exception e) {

        }
    }
}