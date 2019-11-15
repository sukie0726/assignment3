package com.example.mycatapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.VH> {

    private Context context;
    private List<Cat> data;
    public HomeAdapter(Context context, List<Cat> data) {
        this.context = context;
        this.data = data;
    }



    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view =  LayoutInflater.from(context).inflate(R.layout.item_cat,viewGroup,false);
         VH vh = new VH(view) ;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        final Cat cat = data.get(i);
        vh.name.setText(cat.getName());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CatDetailActivity.class);
                intent.putExtra("id",cat.getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
    class VH extends RecyclerView.ViewHolder{
        TextView name;
        public VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}





