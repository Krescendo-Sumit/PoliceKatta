package police.bharti.katta.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import police.bharti.katta.R;
import police.bharti.katta.model.TestPaperModel;
import police.bharti.katta.model.TestSeriesModel;
import police.bharti.katta.util.Constants;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.view.saravmenu.SaravQuestions;
import police.bharti.katta.view.testserise.TestSeriesQuestions;
import police.bharti.katta.view.testserise.TestSeriesResult;

public class TestSeriesPaperAdapter extends  RecyclerView.Adapter<TestSeriesPaperAdapter.DataObjectHolder> {

    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<TestPaperModel> bhartiModelArrayList=null;
    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public TestSeriesPaperAdapter(ArrayList<TestPaperModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public TestSeriesPaperAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_listoftestpaper, parent, false);

        return new TestSeriesPaperAdapter.DataObjectHolder(view);
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        //  if (mSellerProductlist.size() > 0) {
        return bhartiModelArrayList.size();
        //} else {
        //  return 0;
        // }
    }




    @Override
    public void onBindViewHolder(final TestSeriesPaperAdapter.DataObjectHolder holder, final int position) {
        try {
            TestPaperModel saravMenuModel=bhartiModelArrayList.get(position);
            holder.price.setText(saravMenuModel.getTitle());
            holder.txt_resultcount.setText(Html.fromHtml("<b>"+saravMenuModel.getResultcnt()+"</b> attempt found."));
            //holder.txt_details.setText(saravMenuModel.get());

            String url= Constants.BASE_URL+saravMenuModel.getImagepath();
            String serverpath = Constants.BASE_URL+"no-image.png";
            // Log.i("DefaultPath",serverpath);
            String data = "<html><head><meta name=\"viewport\"\"content=\"width='100%', initial-scale=0.65 \" /></head>";
            data = data + "<body><center>" +
                    "<img width=\"100%\" height='150px' src=\"" + url + "\" " +
                    "onerror=\"this.onerror=null; this.src='" + serverpath + "'\"/>" +
                    "" +
                    "</center></body></html>";
            //  Log.i("WebViewData","====="+data);
            holder.img_web.getSettings().setJavaScriptEnabled(true);
            holder.img_web.loadData(data, "text/html", "UTF-8");

            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Preferences.save(context,Preferences.SELECTEDPAPERID,saravMenuModel.getId());
                    Preferences.save(context,Preferences.SELECTEDPAPERFILE,saravMenuModel.getFilepath());
                    Preferences.save(context,Preferences.LIVESELECTEDPAPERDURATION,saravMenuModel.getTotalduration());

                    Intent intent=new Intent(context, TestSeriesQuestions.class);
                    context.startActivity(intent);
                }
            });
            holder.txt_resultcount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Preferences.save(context,Preferences.SELECTEDPAPERID,saravMenuModel.getId());
                    Intent intent=new Intent(context, TestSeriesResult.class);
                    context.startActivity(intent);
                }
            });

        }catch(Exception e)
        {
            Log.i("Error ",e.getMessage());
        }
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView totalQuantity, price, txt_details,txt_resultcount;
        ImageView imageView;
        LinearLayout ll;
        WebView img_web;
        public DataObjectHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.txt_title);
            imageView=(ImageView)itemView.findViewById(R.id.img_bhartilogo);
            txt_details= (TextView) itemView.findViewById(R.id.txt_details);
            txt_resultcount= (TextView) itemView.findViewById(R.id.txt_resultcount);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);
            img_web=(WebView) itemView.findViewById(R.id.img_web);

        }
    }



}
