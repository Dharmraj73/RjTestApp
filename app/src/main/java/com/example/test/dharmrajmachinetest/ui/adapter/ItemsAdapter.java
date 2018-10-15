package com.example.test.dharmrajmachinetest.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.dharmrajmachinetest.R;
import com.example.test.dharmrajmachinetest.data.roomdb.model.Product;

import java.util.List;

/**
 * Created by Test on 6/1/2018.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>  {

    private Context mContext;
    private List<Product> items;
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener{
        void onItemClick(Product product, int position);
    }

    public ItemsAdapter(Context mContext, List<Product> items){
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_items, parent, false);
        ViewHolder holder = new ViewHolder(itemView);
        holder.setClick(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        Product product = items.get(position);

        h.tvItemName.setText(product.name);
        h.tvPrice.setText(String.valueOf(product.price));
        h.tvQuantity.setText(String.valueOf(product.quantity));
        h.tvTotal.setText(String.valueOf(product.getTotal()));

        if(product.checkExpiery()){
            h.ll_parentView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            h.tvItemName.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            h.tvPrice.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            h.tvQuantity.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            h.tvTotal.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }else {

            h.ll_parentView.setBackgroundResource(0);
            h.tvItemName.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            h.tvPrice.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            h.tvQuantity.setTextColor(ContextCompat.getColor(mContext, R.color.black));
            h.tvTotal.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvItemName, tvPrice, tvQuantity, tvTotal;
        private LinearLayout ll_parentView;

        ViewHolder(View itemView) {
            super(itemView);

            tvItemName =  itemView.findViewById(R.id.tvItemName);
            tvPrice =     itemView.findViewById(R.id.tvPrice);
            tvQuantity =  itemView.findViewById(R.id.tvQuantity);
            tvTotal =     itemView.findViewById(R.id.tvTotal);
            ll_parentView =     itemView.findViewById(R.id.ll_parentView);
        }

        private void setClick(View itemView){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int pos = getAdapterPosition();
                        listener.onItemClick(items.get(pos), pos);
                    }

                }
            });
        }
    }
}
