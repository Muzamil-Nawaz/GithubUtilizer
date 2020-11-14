package com.example.githubutilizer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements Filterable {
    LayoutInflater layoutInflater;
    private ArrayList<RepoDataModel> dataSet;
    ArrayList<RepoDataModel> dataSetCopy;

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RepoDataModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(dataSetCopy);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (RepoDataModel item : dataSetCopy) {
                    if (item.getUsername().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataSet.clear();

            dataSet.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user_name;
        TextView repo_desc;
        ImageView profile_pic;
        TextView repo_link;

        public MyViewHolder(View itemView) {
            super(itemView);
//            Toast.makeText(itemView.getContext(),"In the Custom Adapter",Toast.LENGTH_SHORT).show();
            this.user_name = (TextView) itemView.findViewById(R.id.user_name);
            this.repo_desc = (TextView) itemView.findViewById(R.id.repo_desc);
            this.profile_pic = (ImageView) itemView.findViewById(R.id.profile_pic);
            this.repo_link = (TextView) itemView.findViewById(R.id.repo_link);
        }
    }

    public CustomAdapter(Context context, ArrayList<RepoDataModel> data) {
        System.out.println("Constructor is called:");
        layoutInflater = LayoutInflater.from(context);
        this.dataSet = data;
        dataSetCopy = new ArrayList<>(data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = layoutInflater.inflate(R.layout.users_layout, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView repoLink = (TextView) view.findViewById(R.id.repo_link);
                String githubProfileUrl = repoLink.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubProfileUrl));
                parent.getContext().startActivity(intent);
            }
        });
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomAdapter.MyViewHolder holder, final int listPosition) {

        TextView username = holder.user_name;
        TextView repoDesc = holder.repo_desc;
        ImageView imageView = holder.profile_pic;
        TextView repoLink = holder.repo_link;

        username.setTextAppearance(holder.itemView.getContext(),R.style.fontForNotificationLandingPage);
        username.setText("Username: "+dataSet.get(listPosition).getUsername());
        repoDesc.setText("Repo Descripton:"+dataSet.get(listPosition).getRepoDesc());
        repoLink.setText(dataSet.get(listPosition).getRepoLink());
        Glide.with(holder.itemView).load(dataSet.get(listPosition).getProfileUrl()).into(imageView);
    }

    @Override
    public int getItemCount() {
        System.out.println("Data set size:"+this.dataSet.size());
        return this.dataSet.size();
    }
    public void filter(String text) {
        System.out.println(" in filter :"+dataSetCopy.size());
        dataSetCopy.clear();
        if(text.isEmpty()){
            System.out.println("text:"+text);
            System.out.println("DataSet Copy size:"+dataSetCopy.size());
            dataSetCopy.addAll(dataSet);
        }
        else{
            text = text.toLowerCase();
            System.out.println("Inside else:"+text);
            for(RepoDataModel item: dataSet){
                System.out.println("text:"+text);
                if(item.getUsername().toLowerCase().contains(text) ){
                    System.out.println(" Item Matched:");
                    dataSetCopy.add(item);
                }
            }
            dataSet = dataSetCopy;
        }
        notifyDataSetChanged();
    }
}