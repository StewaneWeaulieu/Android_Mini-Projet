package ca.ulaval.ima.mp.ui.list;


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

import ca.ulaval.ima.mp.KungryLocation;
import ca.ulaval.ima.mp.R;
import ca.ulaval.ima.mp.data.LocationData;
import ca.ulaval.ima.mp.data.RestaurantLightData;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<RestaurantLightData> restaurants;
    private OnItemListener onItemListener;

    public ListAdapter(List<RestaurantLightData> pRestaurants, OnItemListener pOnItemListener) {
        restaurants = pRestaurants;
        onItemListener = pOnItemListener;
    }

    public void add(RestaurantLightData restaurant) {
        restaurants.add(restaurant);
        notifyItemInserted(restaurants.size() - 1);
    }

    public void addAll(List<RestaurantLightData> restaurantsList) {
        for (RestaurantLightData restaurant : restaurantsList) {
            add(restaurant);
        }
    }

    public RestaurantLightData get(int position) {
        return restaurants.get(position);
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image_view;
        public TextView name_text_view;
        public TextView type_text_view;
        public RatingBar review_average_rating_bar;
        public TextView review_count_text_view;
        public TextView distance_text_view;

        OnItemListener onItemListener;

        public ViewHolder(View itemView, OnItemListener pOnItemListener) {
            super(itemView);
            image_view = itemView.findViewById(R.id.list_restaurant_image_view);
            name_text_view = itemView.findViewById(R.id.list_restaurant_name_text_view);
            type_text_view = itemView.findViewById(R.id.list_restaurant_type_text_view);
            review_average_rating_bar = itemView.findViewById(R.id.list_restaurant_review_average_rating_bar);
            review_count_text_view = itemView.findViewById(R.id.list_restaurant_review_count_text_view);
            distance_text_view = itemView.findViewById(R.id.list_restaurant_distance_text_view);

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
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View restaurantView = inflater.inflate(R.layout.item_restaurant, parent, false);

        ViewHolder viewHolder = new ViewHolder(restaurantView,onItemListener);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder viewHolder, int position) {
        RestaurantLightData restaurantLight = restaurants.get(position);


        ImageView image_view = viewHolder.image_view;
        TextView name_text_view = viewHolder.name_text_view;
        TextView type_text_view = viewHolder.type_text_view;
        RatingBar review_average_rating_bar = viewHolder.review_average_rating_bar;
        TextView review_count_text_view = viewHolder.review_count_text_view;
        TextView distance_text_view = viewHolder.distance_text_view;


        Picasso.get().load(restaurantLight.getImage()).into(image_view);
        name_text_view.setText(restaurantLight.getName());
        type_text_view.setText(restaurantLight.getType());
        review_average_rating_bar.setRating(restaurantLight.getReviewAverage());
        review_count_text_view.setText(restaurantLight.getReviewCount());
        distance_text_view.setText(restaurantLight.getDistance());


    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }





}