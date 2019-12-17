package com.example.administrator.ShouYe;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.R;

import java.io.InputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Map;

/**

 */
public class RecycleView_Adapter extends RecyclerView.Adapter<RecycleView_Adapter.ViewHolder> {

    Context context;
    List<Map<String, Object>> data;

    public RecycleView_Adapter(Context context, List<Map<String, Object>> data) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shouye_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        //                                                                             Blob im=(Blob)data.get(i).get("pic");

        Glide.with(context).load(data.get(i).get("pic")).into(holder.img);
        //Log.d("FUCK",(String)data.get(i).get("pic"));
        holder.name.setText(data.get(i).get("name").toString());
        holder.introduce.setText(data.get(i).get("introduce").toString());
        holder.price.setText(data.get(i).get("price").toString());
        final Intent intent = new Intent(context, ShangPin_Xiangqing.class);
//        intent.putExtra("pic", (Integer) data.get(i).get("pic"));
        intent.putExtra("pic",(byte[])data.get(i).get("pic"));
        intent.putExtra("name", (String) data.get(i).get("name"));
        intent.putExtra("introduce",(String)data.get(i).get("introduce"));
        intent.putExtra("price",(int)data.get(i).get("price"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView name;
        TextView introduce;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img_recy_item_1_pic);
            name = itemView.findViewById(R.id.tv_recy_item_1_name);
            introduce=itemView.findViewById(R.id.tv_recy_item_1_introduce);
            price=itemView.findViewById(R.id.tv_recy_item_1_price);

        }
    }
}
