package police.bharti.katta.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import police.bharti.katta.R;
import police.bharti.katta.model.VideoItemModel;
import police.bharti.katta.model.VideoItemModel;
import police.bharti.katta.util.Constants;
import police.bharti.katta.util.Preferences;
import police.bharti.katta.view.onlineclass.ViewVideo;
import police.bharti.katta.view.testserise.TestSeriesPaper;

public class VideoItemAdapter extends  RecyclerView.Adapter<VideoItemAdapter.DataObjectHolder> {
    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<VideoItemModel> bhartiModelArrayList=null;
    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public VideoItemAdapter(ArrayList<VideoItemModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public VideoItemAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_listofexams, parent, false);

        return new VideoItemAdapter.DataObjectHolder(view);
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
    public void onBindViewHolder(final VideoItemAdapter.DataObjectHolder holder, final int position) {
        try {
            VideoItemModel saravMenuModel=bhartiModelArrayList.get(position);
            holder.price.setText(saravMenuModel.getTitle());
            //holder.txt_details.setText(saravMenuModel.get());
            String imgurl="https://img.youtube.com/vi/"+saravMenuModel.getUrl()+"/sddefault.jpg";
            String url= imgurl;
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

                 //    Toast.makeText(context, ""+saravMenuModel.getId(), Toast.LENGTH_SHORT).show();
                  //  Preferences.save(context,Preferences.SELECTEDTESTMENU,saravMenuModel.getId());
                    Intent intent=new Intent(context, ViewVideo.class);
                    intent.putExtra("url",saravMenuModel.getUrl().trim());

                    context.startActivity(intent);

                }
            });

        }catch(Exception e)
        {
            Log.i("Error ",e.getMessage());
        }
    }





    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView totalQuantity, price, txt_details;
        ImageView imageView;
        LinearLayout ll;


        WebView img_web;
        public DataObjectHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.txt_title);
            imageView=(ImageView)itemView.findViewById(R.id.img_bhartilogo);
            txt_details= (TextView) itemView.findViewById(R.id.txt_details);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);
            img_web=(WebView) itemView.findViewById(R.id.img_web);
        }
    }

}
