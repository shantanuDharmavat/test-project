package com.slidingimages;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by admin on 24-Mar-17.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.IncidentModelHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private Context context;
    private List<MovieModel> mDataset;
    private OnItemClickListener onItemClickListener;
    String URL = "http://image.tmdb.org/t/p/w185/";

    public static class IncidentModelHolder extends RecyclerView.ViewHolder {
        TextView textViewMovieName;
        TextView textViewDateTime;
        TextView textViewRating;
        ImageView imageThumbNail;

        public IncidentModelHolder(View itemView) {
            super(itemView);
            textViewMovieName = (TextView) itemView.findViewById(R.id.TextViewMoviewName);
            textViewDateTime = (TextView) itemView.findViewById(R.id.TextViewReleaseDate);
            textViewRating = (TextView) itemView.findViewById(R.id.TextViewMovieRating);
            imageThumbNail = (ImageView) itemView.findViewById(R.id.imageViewThumbNail);
            Log.i(LOG_TAG, "Adding Listener");
        }
    }

    public MyRecyclerViewAdapter(List<MovieModel> myDataset, Context context) {
        this.context = context;
        mDataset = myDataset;
    }

    @Override
    public IncidentModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.incident_list_row, parent, false);

        IncidentModelHolder IncidentModelHolder = new IncidentModelHolder(view);
        return IncidentModelHolder;
    }

    @Override
    public void onBindViewHolder(IncidentModelHolder holder, int position) {
        holder.textViewMovieName.setText(mDataset.get(position).getTitle());
        holder.textViewDateTime.setText(mDataset.get(position).getRelease_date());
        if (mDataset.get(position).getAdult().equalsIgnoreCase("false"))
            holder.textViewRating.setText("(U/A)");
        else
            holder.textViewRating.setText("(A)");
        Picasso.with(context)
                .load(URL + mDataset.get(position).getBackdrop_path())
                .into(holder.imageThumbNail);

        final MovieModel model = mDataset.get(position);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(model);
            }
        };

        holder.textViewMovieName.setOnClickListener(listener);
        holder.textViewRating.setOnClickListener(listener);
        holder.textViewDateTime.setOnClickListener(listener);
    }

    public void addItem(MovieModel dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

