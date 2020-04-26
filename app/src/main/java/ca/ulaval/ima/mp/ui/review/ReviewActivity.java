package ca.ulaval.ima.mp.ui.review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ulaval.ima.mp.KungryLocation;
import ca.ulaval.ima.mp.KungryRequest;
import ca.ulaval.ima.mp.PaginationScrollListener;
import ca.ulaval.ima.mp.R;
import ca.ulaval.ima.mp.data.RestaurantLightData;
import ca.ulaval.ima.mp.data.ReviewsData;
import ca.ulaval.ima.mp.ui.list.ListAdapter;
import ca.ulaval.ima.mp.ui.list.ListFragment;
import ca.ulaval.ima.mp.ui.restaurant.RestaurantActivity;


public class ReviewActivity extends AppCompatActivity implements ReviewAdapter.OnItemListener  {

    private RecyclerView recyclerView;

    int totalPage = 0;
    int currentPage;
    boolean isLoading;

    ReviewAdapter adapter;
    ReviewActivity reviewActivity = this;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar_with_back);

        ImageButton toolBarBack = findViewById(R.id.back_image_button);
        toolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        recyclerView = findViewById(R.id.reviews_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        final String restaurant_id = getIntent().getStringExtra("restaurantID");
        currentPage = 1;
        KungryRequest.reviews(this,currentPage,restaurant_id);


        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;

                //Increment page index to load the next one
                currentPage += 1;
                Log.d("Patate", "loadMoreItems: " + currentPage);
                KungryRequest.reviews(reviewActivity,currentPage, restaurant_id);
            }

            @Override
            public int getTotalPageCount() {
                Log.d("Patate", "getTotalPageCount: " + totalPage);
                return totalPage;
            }

            @Override
            public boolean isLastPage() {
                Log.d("Patate", "isLastPage: ");

                if(currentPage == totalPage){
                    return true;
                }
                else{
                    return false;
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });



        Button review_authenticated_button = findViewById(R.id.review_authenticated_button);
        Button review_not_authenticated_button = findViewById(R.id.review_not_authenticated_button);

        SharedPreferences prefs = getSharedPreferences("ca.ulaval.ima.mp", getBaseContext().MODE_PRIVATE);
        String access_token = prefs.getString("ca.ulaval.ima.mp.access_token", "");

        if(!access_token.equals("")){
            review_authenticated_button.setVisibility(View.VISIBLE);
            review_not_authenticated_button.setVisibility(View.GONE);
        }

        final Activity activity = this;
        review_authenticated_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReviewCreateActivity.class);
                intent.putExtra("restaurantID", restaurant_id);
                startActivity(intent);
            }
        });

        review_not_authenticated_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra("ok", "ok");
                setResult(Activity.RESULT_OK,resultIntent);
                finish();
            }
        });






    }


    public void updateUi(ArrayList< ReviewsData > reviews, int currentPage, int pTotalPage, String count){
        if(currentPage == 1){
            totalPage = pTotalPage;

            TextView reviews_count_text_view = findViewById(R.id.reviews_count_text_view);
            reviews_count_text_view.setText("(" + count + ")");

            adapter = new ReviewAdapter(reviews,this);
            recyclerView.setAdapter(adapter);
        }
        else{
            adapter.addAll(reviews);
        }

        isLoading = false;
    }



    @Override
    public void onItemClick(int position) {

        ReviewsData review = adapter.get(position);
        String image =review.getImage();

        if (!image.equals("null")){
            Intent intent = new Intent(reviewActivity, ImageActivity.class);
            intent.putExtra("image", image);
            startActivity(intent);
        }




    }


}
