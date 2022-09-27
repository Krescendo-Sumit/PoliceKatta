package police.bharti.katta.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import police.bharti.katta.R;
import police.bharti.katta.model.TestSeriesResultModel;
import police.bharti.katta.util.Constants;
import police.bharti.katta.view.pdfviewer.PDFFileViewer;

public class TestSeriesResultAdapter extends  RecyclerView.Adapter<TestSeriesResultAdapter.DataObjectHolder> {
    Context context;

    private static final int UNSELECTED = -1;

    ArrayList<TestSeriesResultModel> bhartiModelArrayList=null;

    public interface EventListener {
        void onDelete(int trid, int position);
    }
    public TestSeriesResultAdapter(ArrayList<TestSeriesResultModel> productModels, Context context) {

        this.bhartiModelArrayList = productModels;
        Log.i("Seller produ:",">>"+productModels.size());
        this.context = context;

    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);

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
             return bhartiModelArrayList.size();
    }
    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        try {
            TestSeriesResultModel TestSeriesResultModel=bhartiModelArrayList.get(position);
            holder.txt_name.setText(TestSeriesResultModel.getCdate());
            holder.txt_marks.setText(TestSeriesResultModel.getCorrect());
            holder.txt_rank.setText(""+(position+1));


        }catch(Exception e)
        {
            Log.i("Error ",e.getMessage());
        }
    }





    public class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_rank, txt_marks;

        public DataObjectHolder(View itemView) {
            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            txt_rank = (TextView) itemView.findViewById(R.id.txt_rank);
            txt_marks=(TextView)itemView.findViewById(R.id.txt_marks);


        }
    }

}
