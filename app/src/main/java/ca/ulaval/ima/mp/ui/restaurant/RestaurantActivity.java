package ca.ulaval.ima.mp.ui.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ca.ulaval.ima.mp.KungryRequest;
import ca.ulaval.ima.mp.R;
import ca.ulaval.ima.mp.data.OpeningHoursData;
import ca.ulaval.ima.mp.data.RestaurantData;
import ca.ulaval.ima.mp.ui.account.AccountFragment;
import ca.ulaval.ima.mp.ui.account.InfoFragment;
import ca.ulaval.ima.mp.ui.review.ImageActivity;
import ca.ulaval.ima.mp.ui.review.ReviewActivity;
import ca.ulaval.ima.mp.ui.review.ReviewAdapter;
import ca.ulaval.ima.mp.ui.review.ReviewCreateActivity;


public class RestaurantActivity extends AppCompatActivity implements ReviewAdapter.OnItemListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        this.getSupportActionBar().hide();

        ImageButton restaurant_back_image_button = findViewById(R.id.restaurant_back_image_button);
        restaurant_back_image_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button restaurant_review_authenticated_button = findViewById(R.id.restaurant_review_authenticated_button);
        Button restaurant_review_not_authenticated_button = findViewById(R.id.restaurant_review_not_authenticated_button);

        SharedPreferences prefs = getSharedPreferences("ca.ulaval.ima.mp", getBaseContext().MODE_PRIVATE);
        String access_token = prefs.getString("ca.ulaval.ima.mp.access_token", "");

        if(!access_token.equals("")){
            restaurant_review_authenticated_button.setVisibility(View.VISIBLE);
            restaurant_review_not_authenticated_button.setVisibility(View.GONE);
        }

        final String restaurant_id = getIntent().getStringExtra("restaurantID");

        final Activity activity = this;
        restaurant_review_authenticated_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReviewCreateActivity.class);
                intent.putExtra("restaurantID", restaurant_id);
                startActivity(intent);
            }
        });

        restaurant_review_not_authenticated_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra("ok", "ok");
                setResult(Activity.RESULT_OK,resultIntent);
                finish();
            }
        });






        KungryRequest.restaurant(this,restaurant_id);



    }

    public void updateUi(final RestaurantData restaurant){
        ImageView restaurant_image_View = findViewById(R.id.restaurant_image_View);
        TextView restaurant_name_text_view = findViewById(R.id.restaurant_name_text_view);
        TextView restaurant_type_text_view = findViewById(R.id.restaurant_type_text_view);
        TextView restaurant_distance_text_view = findViewById(R.id.restaurant_distance_text_view);
        TextView restaurant_review_count_text_view = findViewById(R.id.restaurant_review_count_text_view);
        RatingBar restaurant_review_average_rating_bar = findViewById(R.id.restaurant_review_average_rating_bar);
        TextView restaurant_phone_number_text_view = findViewById(R.id.restaurant_phone_number_text_view);
        TextView restaurant_website_text_view = findViewById(R.id.restaurant_website_text_view);
        TextView restaurant_opening_hours_MON_value_text_view = findViewById(R.id.restaurant_opening_hours_MON_value_text_view);
        TextView restaurant_opening_hours_TUE_value_text_view = findViewById(R.id.restaurant_opening_hours_TUE_value_text_view);
        TextView restaurant_opening_hours_WED_value_text_view = findViewById(R.id.restaurant_opening_hours_WED_value_text_view);
        TextView restaurant_opening_hours_THU_value_text_view = findViewById(R.id.restaurant_opening_hours_THU_value_text_view);
        TextView restaurant_opening_hours_FRI_value_text_view = findViewById(R.id.restaurant_opening_hours_FRI_value_text_view);
        TextView restaurant_opening_hours_SAT_value_text_view = findViewById(R.id.restaurant_opening_hours_SAT_value_text_view);
        TextView restaurant_opening_hours__SUN_value_text_view = findViewById(R.id.restaurant_opening_hours__SUN_value_text_view);

        TextView restaurant_review_count_2_text_view = findViewById(R.id.restaurant_review_count_2_text_view);

        TextView restaurant_review_more_text_view = findViewById(R.id.restaurant_review_more_text_view);



        Picasso.get().load(restaurant.getImage()).into(restaurant_image_View);
        restaurant_name_text_view.setText(restaurant.getName());
        restaurant_type_text_view.setText(restaurant.getType());
        restaurant_distance_text_view.setText(restaurant.getDistance());
        restaurant_review_average_rating_bar.setRating(restaurant.getReviewAverage());
        restaurant_review_count_text_view.setText(restaurant.getReviewCount());

        restaurant_phone_number_text_view.setText(restaurant.getPhoneNumber());
        restaurant_phone_number_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(restaurant.getPhoneNumberForIntent()));
                startActivity(intent);
            }
        });

        restaurant_website_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurant.getWebsite()));
                startActivity(intent);
            }
        });


        OpeningHoursData openingHours = restaurant.getOpeningHours();
        if(openingHours.isClose()){
            restaurant_opening_hours_MON_value_text_view.setText(R.string.close);
            restaurant_opening_hours_TUE_value_text_view.setText(R.string.close);
            restaurant_opening_hours_WED_value_text_view.setText(R.string.close);
            restaurant_opening_hours_THU_value_text_view.setText(R.string.close);
            restaurant_opening_hours_FRI_value_text_view.setText(R.string.close);
            restaurant_opening_hours_SAT_value_text_view.setText(R.string.close);
            restaurant_opening_hours__SUN_value_text_view.setText(R.string.close);
        }
        else{
            restaurant_opening_hours_MON_value_text_view.setText(openingHours.getMonday());
            restaurant_opening_hours_TUE_value_text_view.setText(openingHours.getTuesday());
            restaurant_opening_hours_WED_value_text_view.setText(openingHours.getWednesday());
            restaurant_opening_hours_THU_value_text_view.setText(openingHours.getThursday());
            restaurant_opening_hours_FRI_value_text_view.setText(openingHours.getFriday());
            restaurant_opening_hours_SAT_value_text_view.setText(openingHours.getSaturday());
            restaurant_opening_hours__SUN_value_text_view.setText(openingHours.getSunday());
        }

        restaurant_review_count_2_text_view.setText(restaurant.getReviewCount());


        RecyclerView restaurant_review_recycle_view = findViewById(R.id.restaurant_review_recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        restaurant_review_recycle_view.setLayoutManager(linearLayoutManager);
        ReviewAdapter adapter = new ReviewAdapter(restaurant.getReviews(),this);
        restaurant_review_recycle_view.setAdapter(adapter);



        final Activity activity = this;
        restaurant_review_more_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReviewActivity.class);
                intent.putExtra("restaurantID", restaurant.getId());
                activity.startActivityForResult(intent,2);
            }
        });


    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (2) : {


                String ok;
                if(data != null){
                    ok = data.getStringExtra("ok");
                }
                else{
                    ok = "";
                }

                if(ok.equals("ok")) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("ok", "ok");
                    setResult(Activity.RESULT_OK,resultIntent);
                    finish();
                }
            }
        }
    }



}
