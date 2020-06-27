package com.example.gameoflife.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.gameoflife.R;

import java.util.ArrayList;
import java.util.HashMap;

public class PatternAdapter extends RecyclerView.Adapter<PatternAdapter.PatternViewHolder> {

    private ArrayList<Pattern> patternArrayList;
    private View view;
    private HashMap<String,Integer> paths = new HashMap<>();
    public DbPatterns db;


    public PatternAdapter(ArrayList<Pattern> personArrayList, View view, Context context){
        this.patternArrayList = personArrayList;
        db = new DbPatterns(context);
        this.view = view;
        paths.put("glider", R.mipmap.ic_glider);
        paths.put("fight",R.mipmap.ic_fight);
        paths.put("wash",R.mipmap.ic_wash);
        paths.put("weekender",R.mipmap.ic_weekender);
        paths.put("me",R.drawable.ic_baseline_grid_on_24);

    }


    static class PatternViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView image;

        PatternViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_item_title);
            image = itemView.findViewById(R.id.iv_item_pattern);

        }
    }

    @NonNull
    @Override
    public PatternViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pattern, parent, false);
        return new PatternViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PatternViewHolder holder, final int position) {
        holder.title.setText(patternArrayList.get(position).getTitle());
        holder.image.setImageResource(paths.get(patternArrayList.get(position).getPath()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern pattern = patternArrayList.get(position);

                ImageView iv = view.findViewById(R.id.pattern_iv_curent);
                iv.setImageResource(paths.get(pattern.getPath()));
                TextView tv = view.findViewById(R.id.tv_current_title);
                tv.setText(pattern.getTitle());
                db.insqq(pattern.getTitle());


            }
        });

    }

    @Override
    public int getItemCount(){
        return patternArrayList.size();
    }

}