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
import android.widget.Spinner;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import police.bharti.katta.R;
import police.bharti.katta.util.Constants;

public class PDFViewForEBook extends AppCompatActivity {
    PDFView pdfView;
    RetrivePDFfromUrl UrlRender;

    // url of our PDF file.
    String pdfurl = "";
    ProgressDialog progressDialog;
    String filename="";

    Spinner spinner;
    ArrayAdapter adapter;
    String[] strPageCounts;
    Button txt_goto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view_for_e_book);

   /*     getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pdfView = findViewById(R.id.idPDFView);
        spinner = findViewById(R.id.spinner);
        txt_goto = findViewById(R.id.txt_goto);

        pdfView.computeScroll();
//        ScrollBar scrollBar = (ScrollBar) findViewById(R.id.scrollBar);
//        pdfView.setScrollBar(scrollBar);
       // pdfView.setScrollBarSize(100);
        progressDialog=new ProgressDialog(PDFViewForEBook.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("प्रतिक्षा करा..");

        filename=getIntent().getExtras().getString("fname").trim();
        pdfurl= Constants.BASE_URL+"ebooks/"+filename;
        Log.i("File URl is ",pdfurl);
        setTitle("माहिती बघा");
        UrlRender=new RetrivePDFfromUrl();
        UrlRender.execute(pdfurl);
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
          //  pdfView.setScrollBarSize(100);
            setSpinnerAdapter(pdfView.getPageCount());
            progressDialog.dismiss();
        }
    }
    void setSpinnerAdapter(int pageCount) {
        try {
            strPageCounts = new String[pageCount];
            for (int i = 0; i < pageCount; i++) {
                strPageCounts[i] = "" + (i + 1);
            }
            adapter = new ArrayAdapter(PDFViewForEBook.this, android.R.layout.simple_list_item_1, strPageCounts);
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
            Toast.makeText(PDFViewForEBook.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            UrlRender.cancel(true);
        }catch (Exception e)
        {

        }
    }
    public void go(View v) {
        try {
            txt_goto.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);
         //   pdfView.setPageFling(true);
            setSpinnerAdapter(pdfView.getPageCount());
          //  pdfView.setScrollBarSize(200);
        } catch (Exception e) {

        }
    }
}