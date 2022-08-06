package police.bharti.katta.adapter;

import android.app.Dialog;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import police.bharti.katta.R;
import police.bharti.katta.model.YashoGathaModel;
import police.bharti.katta.model.YashoGathaModel;
import police.bharti.katta.util.Constants;
import police.bharti.katta.view.pdfviewer.PDFFileViewer;

public class YashogathaAdapter extends  RecyclerView.Adapter<YashogathaAdapter.DataObjectHolder> {
    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<YashoGathaModel> bhartiModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public YashogathaAdapter(ArrayList<YashoGathaModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_yashogatha, parent, false);

        return new DataObjectHolder(view);
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
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            YashoGathaModel YashoGathaModel=bhartiModelArrayList.get(position);
            holder.price.setText(YashoGathaModel.getTitle());
            holder.txt_writer.setText(Html.fromHtml("<b>पत्ता :</b>"+YashoGathaModel.getLocation()));
            holder.txt_pages.setText(Html.fromHtml("<b>मोबाईल :</b>"+YashoGathaModel.getMobile()));
            holder.txt_price.setText(Html.fromHtml("<b>निवड :</b>"+YashoGathaModel.getPost()));
            holder.txt_details.setText(Html.fromHtml("<b>वर्ष :</b>"+YashoGathaModel.getFeedback()));

            String url= Constants.BASE_URL+YashoGathaModel.getImagepath();
            String serverpath = Constants.BASE_URL+"no-image.png";
            Log.i("DefaultPath",url);
            String data = "<html><head><meta name=\"viewport\"\"content=\"width='100%', initial-scale=0.65 \" />" +
                    "" +
                    "<style>img {\n" +
                    "  display: block;\n" +
                    "  max-width:90px;\n" +
                    "  max-height:95px;\n" +
                    "  width: auto;\n" +
                    "  height: auto;\n" +
                    "}</style>" +
                    "</head>"; data = data + "<body><center>" +
                    "<img width=\"100%\" height='auto' src=\"" + url + "\" " +
                    "onerror=\"this.onerror=null; this.src='" + serverpath + "'\"/>" +
                    "" +
                    "</center></body></html>";
            holder.img_web.getSettings().setJavaScriptEnabled(true);
            holder.img_web.loadData(data, "text/html", "UTF-8");

            holder.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(context, PDFFileViewer.class);
                    intent.putExtra("fname",YashoGathaModel.getFilepath());
                    context.startActivity(intent);
              /*      Dialog dialog=new Dialog(context);
                    dialog.setContentView(R.layout.popup_showbookdetails);
                    TextView textView=dialog.findViewById(R.id.txt_details);
                    textView.setText(Html.fromHtml("<pre>"+YashoGathaModel.getOthers()+"</pre>"));
                    dialog.show();*/
                }
            });

        }catch(Exception e)
        {
            Log.i("Error ",e.getMessage());
        }
    }





    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView totalQuantity, price, txtQtyUnit;
        ImageView imageView;
        LinearLayout ll;
        WebView img_web;
        TextView txt_price,txt_writer,txt_discountprice,txt_pages,txt_details;
        public DataObjectHolder(View itemView) {
            super(itemView);
            price = (TextView) itemView.findViewById(R.id.txt_title);
            imageView=(ImageView)itemView.findViewById(R.id.img_bhartilogo);
            ll=(LinearLayout)itemView.findViewById(R.id.ll);
            img_web=(WebView) itemView.findViewById(R.id.img_web);
            txt_price=itemView.findViewById(R.id.txt_price);
            txt_writer=itemView.findViewById(R.id.txt_writer);
            txt_discountprice=itemView.findViewById(R.id.txt_discountprize);
            txt_pages=itemView.findViewById(R.id.txt_pages);
            txt_details=itemView.findViewById(R.id.txt_details);
        }
    }

}
