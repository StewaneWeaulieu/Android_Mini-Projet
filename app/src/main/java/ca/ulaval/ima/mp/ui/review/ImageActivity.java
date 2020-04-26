package ca.ulaval.ima.mp.ui.review;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ca.ulaval.ima.mp.R;


public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        ImageView full_screen_review_image_view = findViewById(R.id.full_screen_review_image_view);
        ImageButton review_image_button = findViewById(R.id.image_back_image_button);
        String image = getIntent().getStringExtra("image");


        Picasso.get().load(image).into(full_screen_review_image_view);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };

        full_screen_review_image_view.setOnClickListener(onClickListener);
        review_image_button.setOnClickListener(onClickListener);


    }
}
