package mahyco.mipl.nxg.view.fieldreport;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mahyco.mipl.nxg.R;
import mahyco.mipl.nxg.model.DownloadGrowerModel;
import mahyco.mipl.nxg.model.GetAllSeedDistributionModel;


public class FieldMonitoringReportAdapter extends RecyclerView.Adapter<FieldMonitoringReportAdapter.MyViewHolder> {

    private ArrayList<GetAllSeedDistributionModel> mArrayList;
    private RecyclerViewClickListener itemListener;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView mGuestName;
        private AppCompatTextView mGrowerId;
        private AppCompatTextView mSeedArea;
        private CardView mRecentTrips;

        MyViewHolder(View v) {
            super(v);

            mGuestName = v.findViewById(R.id.grower_name);
            mGrowerId = v.findViewById(R.id.grower_id);
            mSeedArea = v.findViewById(R.id.seed_area);

            mRecentTrips = v.findViewById(R.id.completed_cardview);
            mRecentTrips.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, getLayoutPosition(), "");
        }
    }

    public FieldMonitoringReportAdapter(Context context, RecyclerViewClickListener itemListener, ArrayList<GetAllSeedDistributionModel> mArrayList) {
        this.itemListener = itemListener;
        this.context = context;
        this.mArrayList = mArrayList;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.field_report_recycleview_layout, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position == 0) {
            holder.mRecentTrips.setVisibility(View.GONE);
        } else {
            holder.mRecentTrips.setVisibility(View.VISIBLE);
            holder.mGuestName.setText(Html.fromHtml("Name: "+"<b>"+(mArrayList.get(position).getGrowerFullName() != null ? mArrayList.get(position).getGrowerFullName() : ""+"</b>")));
            holder.mGrowerId.setText(Html.fromHtml("Unicode: "+"<b>"+(mArrayList.get(position).getGrowerUniqueCode() != null ? mArrayList.get(position).getGrowerUniqueCode() : ""+"</b>")));
            holder.mSeedArea.setText(Html.fromHtml("Issued seed area: "+"<b>"+String.format("%.2f", mArrayList.get(position).getSeedProductionArea())+"</b>" +" Ha"));
        }
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }
}
