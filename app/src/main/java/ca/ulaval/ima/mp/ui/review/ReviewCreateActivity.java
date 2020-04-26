package ca.ulaval.ima.mp.ui.review;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import ca.ulaval.ima.mp.KungryRequest;
import ca.ulaval.ima.mp.R;
import ca.ulaval.ima.mp.data.ReviewCreateData;
import ca.ulaval.ima.mp.data.ReviewsData;

public class ReviewCreateActivity extends AppCompatActivity {


    Boolean picture_set = false;

    RatingBar review_create_rating_bar;
    EditText review_create_comment_edit_text;
    ImageButton review_create_add_picture_image_button;

    String file_path;

    ReviewCreateActivity reviewCreateActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_create);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        getSupportActionBar().show();


        review_create_rating_bar = findViewById(R.id.review_create_rating_bar);
        review_create_comment_edit_text = findViewById(R.id.review_create_comment_edit_text);

        review_create_add_picture_image_button = findViewById(R.id.review_create_add_picture_image_button);
        review_create_add_picture_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 3);
            }
        });

        Button review_create_submit_button = findViewById(R.id.review_create_submit_button);
        review_create_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String restaurant_id = getIntent().getStringExtra("restaurantID");
                int stars_int = (int) Math.round(review_create_rating_bar.getRating());
                String stars = Integer.toString(stars_int);
                String comment = review_create_comment_edit_text.getText().toString();

                ReviewCreateData reviewCreateData = new ReviewCreateData(restaurant_id,stars,comment);
                KungryRequest.review_create(reviewCreateActivity, reviewCreateData);
            }
        });








    }

    public void updateUi(String reviewID){
        if(picture_set){
            File file = new File(file_path);
            KungryRequest.set_review_picture(reviewCreateActivity, reviewID, file);
        }
        else{
            finish();
        }
    }

    public void closeUi(){
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            file_path = data.getData().getPath();
            Uri selectedImageURI = data.getData();
            Picasso.get().load(selectedImageURI).noPlaceholder().centerCrop().fit().into(review_create_add_picture_image_button);
            picture_set = true;
        }
    }



}
