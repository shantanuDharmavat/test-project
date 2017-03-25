package com.slidingimages;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 24-Mar-17.
 */

public class Page2 extends AppCompatActivity {
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.five};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    TextView textViewTitle,textViewOverView,textViewRating;
    MovieModel model;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        init();
    }


    private void init() {
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewOverView = (TextView) findViewById(R.id.textViewOverView);
        textViewRating = (TextView) findViewById(R.id.textViewRatingStar);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        model = Global.getObject().selectedIncidentModel;

        textViewTitle.setText(model.getTitle());
        textViewOverView.setText(model.getOverview());
        textViewRating.setText(model.getVote_average());
        float calculatedValue = Float.valueOf(model.vote_average)/2;
        ratingBar.setRating(calculatedValue);
        ratingBar.setStepSize((float).5);

        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(Page2.this, ImagesArray));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);


        NUM_PAGES = IMAGES.length;


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }


}
