package mahyco.mipl.nxg.util;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import mahyco.mipl.nxg.R;

public class GridViewAdapter1 extends RecyclerView.Adapter<GridViewAdapter1.ViewHolder> {

    String[] result;
    String[] imageId;
    private Context context;
    private GetClickedPosition getClickedPosition;

    public GridViewAdapter1(Context context, String[] prgmNameList, String[] ImageList, GetClickedPosition getClickedPosition) {
        this.result = prgmNameList;
        this.context = context;
        this.imageId = ImageList;
        this.getClickedPosition = getClickedPosition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mscope_home_programlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        View rowView;
        try {

            holder.tv.setText(result[position]);
            holder.tv.setTextColor(Color.parseColor("#000000"));
            holder.tv.setBackgroundResource(android.R.color.transparent);
            Picasso.with(context).load("file:///android_asset/DashboardImages/" + imageId[position])
                    .into(holder.img);

            holder.card.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                       getClickedPosition.getPosition(position);
                    } catch (Exception ex) {
                        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return result.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView img;
        CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView1);
            img = (ImageView) itemView.findViewById(R.id.imageView1);
            card = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
