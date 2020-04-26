package ca.ulaval.ima.mp.ui.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ca.ulaval.ima.mp.R;
import ca.ulaval.ima.mp.data.RestaurantData;
import ca.ulaval.ima.mp.data.RestaurantLightData;
import ca.ulaval.ima.mp.data.ReviewsData;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewsData> reviews;
    private OnItemListener onItemListener;

    public ReviewAdapter(List<ReviewsData> pReviews, OnItemListener pOnItemListener) {
        reviews = pReviews;
        onItemListener = pOnItemListener;
    }

    public void add(ReviewsData review) {
        reviews.add(review);
        notifyItemInserted(reviews.size() - 1);
    }

    public void addAll(List<ReviewsData> reviews) {
        for (ReviewsData review : reviews) {
            add(review);
        }
    }

    public ReviewsData get(int position) {
        return reviews.get(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView date_text_view;
        public RatingBar stars_rating_bar;
        public TextView creator_text_view;
        public TextView comment_text_view;
        public ImageView image_view;

        OnItemListener onItemListener;

        public ViewHolder(View itemView, OnItemListener pOnItemListener) {
            super(itemView);
            date_text_view = itemView.findViewById(R.id.review_date_text_view);
            stars_rating_bar = itemView.findViewById(R.id.review_stars_rating_bar);
            creator_text_view = itemView.findViewById(R.id.review_creator_text_view);
            comment_text_view = itemView.findViewById(R.id.review_comment_text_view);
            image_view = itemView.findViewById(R.id.review_image_view);

            onItemListener = pOnItemListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }



    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View restaurantView = inflater.inflate(R.layout.item_review, parent, false);

        ViewHolder viewHolder = new ViewHolder(restaurantView,onItemListener);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder viewHolder, int position) {
        ReviewsData review = reviews.get(position);

        TextView date_text_view = viewHolder.date_text_view;
        RatingBar stars_rating_bar = viewHolder.stars_rating_bar;
        TextView creator_text_view = viewHolder.creator_text_view;
        TextView comment_text_view = viewHolder.comment_text_view;
        ImageView image_view = viewHolder.image_view;

        date_text_view.setText(review.getDate());
        stars_rating_bar.setRating(review.getStars());
        creator_text_view.setText(review.getCreator());
        comment_text_view.setText(review.getComment());

        String image = review.getImage();
        if (image.equals("null")) {
            image_view.setVisibility(View.GONE);
        }

        else{
            Picasso.get().load(review.getImage()).into(image_view);
        }





    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}



