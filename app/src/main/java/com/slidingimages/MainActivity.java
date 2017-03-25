package com.slidingimages;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 24-Mar-17.
 */

public class MainActivity extends AppCompatActivity {
    MyRecyclerViewAdapter mAdapter;
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    Global globalVariable = Global.getObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        MultiDex.install(this);
        new getData().execute("");
    }

    private class getData extends AsyncTask<String,Void,List<MovieModel>> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected List<MovieModel> doInBackground(String... params) {
            try {
                JSONObject obj = new NetworkCallHandler().retriveMovie("https://api.themoviedb.org/3/movie/upcoming?api_key=f8d89eb956371dbecc5c56fe23ff25f4");
                Log.e("obj : ", "" + obj);
                JSONArray array = obj.getJSONArray("results");
                List<MovieModel> list = new ArrayList<>();
                MovieModel model;
                for (int i = 0; i < array.length(); i++){
                    model = new MovieModel();
                    model.setAdult(array.getJSONObject(i).getString("adult"));
                    model.setBackdrop_path(array.getJSONObject(i).getString("backdrop_path"));
                    model.setPoster_path(array.getJSONObject(i).getString("poster_path"));
//                    model.setGenre_ids(array.getJSONObject(i).getString(""));
                    model.setId(array.getJSONObject(i).getString("id"));
                    model.setOriginal_language(array.getJSONObject(i).getString("original_language"));
                    model.setOriginal_title(array.getJSONObject(i).getString("original_title"));
                    model.setTitle(array.getJSONObject(i).getString("title"));
                    model.setRelease_date(array.getJSONObject(i).getString("release_date"));
                    model.setOverview(array.getJSONObject(i).getString("overview"));
                    model.setVote_average(array.getJSONObject(i).getString("vote_average"));
                    model.setPopularity(array.getJSONObject(i).getString("popularity"));
                    list.add(model);
                }
                return list;
            }catch (Exception e){e.printStackTrace();}
            return null;
        }

        @Override
        protected void onPostExecute(List<MovieModel> movieModels) {
            super.onPostExecute(movieModels);
            if (dialog.isShowing())
                dialog.cancel();
            mAdapter = new MyRecyclerViewAdapter(movieModels,MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(MovieModel item) {
//                    Log.e("on item click", "" + item.getIncident_id());
//                    Log.e("on item click", "" + item.getAddress_of_incident());
//                    Log.e("on item click", "" + item.getDate_time());

                    Intent intent = new Intent(MainActivity.this, Page2.class);
                    globalVariable.selectedIncidentModel = item;
                    startActivity(intent);
                }
            });
            mAdapter.notifyDataSetChanged();
        }
    }
}
