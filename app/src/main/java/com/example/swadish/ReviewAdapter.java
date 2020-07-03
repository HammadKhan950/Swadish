package com.example.swadish;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private ArrayList<Review> reviews=new ArrayList<>();
    public ReviewAdapter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtreview.setText(reviews.get(position).getText());
        holder.txtDate.setText(reviews.get(position).getDate());
        holder.txtUserName.setText(reviews.get(position).getUserName());

    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }
    public void setReviews(ArrayList<Review> reviews){
        this.reviews=reviews;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtUserName,txtreview,txtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUserName=itemView.findViewById(R.id.txtUserName);
            txtreview=itemView.findViewById(R.id.txtReview);
            txtDate=itemView.findViewById(R.id.txtDate);
        }
    }
}
