package ca.ulaval.ima.mp;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import ca.ulaval.ima.mp.data.AccountCreateData;
import ca.ulaval.ima.mp.data.AccountData;
import ca.ulaval.ima.mp.data.AccountLoginData;
import ca.ulaval.ima.mp.data.CreatorData;
import ca.ulaval.ima.mp.data.CuisineData;
import ca.ulaval.ima.mp.data.LocationData;
import ca.ulaval.ima.mp.data.OpeningHoursData;
import ca.ulaval.ima.mp.data.OpeningHoursDayData;
import ca.ulaval.ima.mp.data.RestaurantData;
import ca.ulaval.ima.mp.data.RestaurantLightData;
import ca.ulaval.ima.mp.data.ReviewCreateData;
import ca.ulaval.ima.mp.data.ReviewsData;
import ca.ulaval.ima.mp.ui.account.AccountFragment;
import ca.ulaval.ima.mp.ui.account.InfoFragment;
import ca.ulaval.ima.mp.ui.account.SubscribeFragment;
import ca.ulaval.ima.mp.ui.list.ListFragment;
import ca.ulaval.ima.mp.ui.restaurant.RestaurantActivity;
import ca.ulaval.ima.mp.ui.review.ReviewActivity;
import ca.ulaval.ima.mp.ui.review.ReviewCreateActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class KungryRequest {
    static private OkHttpClient client = new OkHttpClient();
    static private String url = "https://kungry.ca/api/v1/";


    static public void restaurant_list(final ListFragment listFragment, final int currentPage, LocationData locationData, int radius){


        final String endpoint = url +
                "restaurant/search/" +
                "?page=" + currentPage +
                "&latitude=" + locationData.getLatitude() +
                "&longitude=" + locationData.getLongitude() +
                "&radius=" + radius;


        Request request = new Request.Builder().url(endpoint).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.d("Mini-Projet", "Error : " + endpoint);
                }
                else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONObject json_content = jsonResponse.getJSONObject("content");

                        String json_count = json_content.optString("count");
                        JSONArray json_results = json_content.getJSONArray("results");


                        final int totalPage = (int) Math.ceil(Double.valueOf(json_count) / json_results.length());

                        final ArrayList<RestaurantLightData> restaurants = new ArrayList<RestaurantLightData>();

                        for (int i = 0; i < json_results.length(); i++) {
                            JSONObject value  = json_results.getJSONObject(i);
                            String id = value.optString("id");
                            String name = value.optString("name");

                            JSONArray json_cuisine_array  = value.getJSONArray("cuisine");
                            JSONObject json_cuisine  = json_cuisine_array.getJSONObject(0);
                            String cuisine_id = json_cuisine.optString("id");
                            String cuisine_name = json_cuisine.optString("name");
                            CuisineData cuisine = new CuisineData(cuisine_id,cuisine_name);

                            String type = value.optString("type");
                            String review_count = value.optString("review_count");
                            String review_average = value.optString("review_average");
                            String image = value.optString("image");
                            String distance = value.optString("distance");

                            JSONObject json_location  = value.getJSONObject("location");
                            String location_latitude = json_location.optString("latitude");
                            String location_longitude = json_location.optString("longitude");
                            LocationData location = new LocationData(Double.parseDouble(location_latitude),Double.parseDouble(location_longitude));

                            RestaurantLightData restaurant = new RestaurantLightData(
                                    id,
                                    name,
                                    cuisine,
                                    type,
                                    review_count,
                                    review_average,
                                    image,
                                    distance,
                                    location);
                            restaurants.add(restaurant);
                        }

                        listFragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listFragment.updateUi(restaurants,currentPage,totalPage);
                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    static public void restaurant(final RestaurantActivity restaurantActivity, String restaurantID){
        final String endpoint = url + "restaurant/" + restaurantID + "/";

        Request request = new Request.Builder().url(endpoint).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.d("Mini-Projet", "Error : " + endpoint);
                }
                else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONObject value = jsonResponse.getJSONObject("content");



                        String id = value.optString("id");
                        String name = value.optString("name");

                        JSONArray json_cuisine_array  = value.getJSONArray("cuisine");
                        JSONObject json_cuisine  = json_cuisine_array.getJSONObject(0);
                        String cuisine_id = json_cuisine.optString("id");
                        String cuisine_name = json_cuisine.optString("name");
                        CuisineData cuisine = new CuisineData(cuisine_id,cuisine_name);

                        String type = value.optString("type");
                        String review_count = value.optString("review_count");
                        String review_average = value.optString("review_average");
                        String image = value.optString("image");
                        String distance = value.optString("distance");

                        JSONObject json_location  = value.getJSONObject("location");
                        String location_latitude = json_location.optString("latitude");
                        String location_longitude = json_location.optString("longitude");
                        LocationData location = new LocationData(Double.parseDouble(location_latitude),Double.parseDouble(location_longitude));

                        JSONArray json_opening_hours = value.getJSONArray("opening_hours");
                        ArrayList<OpeningHoursDayData> days = new ArrayList<OpeningHoursDayData>();
                        for (int i = 0; i < json_opening_hours.length(); i++) {
                            JSONObject json_day  = json_opening_hours.getJSONObject(i);
                            String day_id = json_day.optString("id");
                            String day_opening_hour = json_day.optString("opening_hour");
                            String day_closing_hour= json_day.optString("closing_hour");
                            String day_day = json_day.optString("day");
                            OpeningHoursDayData day = new OpeningHoursDayData(
                                    day_id,
                                    day_opening_hour,
                                    day_closing_hour,
                                    day_day);
                            days.add(day);
                        }
                        OpeningHoursData openingHoursData = new OpeningHoursData(days);

                        JSONArray json_reviews = value.getJSONArray("reviews");
                        ArrayList<ReviewsData> reviews = new ArrayList<ReviewsData>();
                        for (int i = 0; i < json_reviews.length(); i++) {
                            JSONObject json_review  = json_reviews.getJSONObject(i);

                            String review_id = json_review.optString("id");

                            JSONObject json_creator = json_review.getJSONObject("creator");
                            String creator_first_name = json_creator.optString("first_name");
                            String creator_last_name = json_creator.optString("last_name");
                            CreatorData review_creator = new CreatorData(creator_first_name,creator_last_name);

                            String review_stars = json_review.optString("stars");
                            String review_image = json_review.optString("image");
                            String review_comment = json_review.optString("comment");
                            String review_date = json_review.optString("date");

                            ReviewsData review = new ReviewsData(
                                    review_id,
                                    review_creator,
                                    review_stars,
                                    review_image,
                                    review_comment,
                                    review_date);
                            reviews.add(review);
                        }

                        String website = value.optString("website");
                        String phoneNumber = value.optString("phone_number");

                        final RestaurantData restaurant = new RestaurantData(
                                id,
                                name,
                                cuisine,
                                type,
                                review_count,
                                review_average,
                                image,
                                distance,
                                location,
                                openingHoursData,
                                reviews,
                                website,
                                phoneNumber
                            );



                        restaurantActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                restaurantActivity.updateUi(restaurant);
                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


    static public void reviews(final ReviewActivity reviewActivity, final int currentPage, String restaurantID){
        final String endpoint = url + "restaurant/" + restaurantID +  "/reviews/" + "?page=" + currentPage;

        Request request = new Request.Builder().url(endpoint).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.d("Mini-Projet", "Error : " + endpoint);
                }
                else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONObject json_content = jsonResponse.getJSONObject("content");

                        final String json_count = json_content.optString("count");
                        JSONArray json_results = json_content.getJSONArray("results");

                        final int totalPage = (int) Math.ceil(Double.valueOf(json_count) / json_results.length());

                        final ArrayList<ReviewsData> reviews = new ArrayList<ReviewsData>();

                        for (int i = 0; i < json_results.length(); i++) {
                            JSONObject value  = json_results.getJSONObject(i);

                            String id = value.optString("id");

                            JSONObject json_creator = value.getJSONObject("creator");
                            String first_name = json_creator.optString("first_name");
                            String last_name = json_creator.optString("last_name");
                            CreatorData creator = new CreatorData(first_name,last_name);

                            String stars = value.optString("stars");
                            String image = value.optString("image");
                            String comment = value.optString("comment");
                            String date = value.optString("date");


                            ReviewsData review = new ReviewsData(
                                    id,
                                    creator,
                                    stars,
                                    image,
                                    comment,
                                    date);
                            reviews.add(review);
                        }

                        reviewActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                reviewActivity.updateUi(reviews,currentPage,totalPage,json_count);
                            }
                        });



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    static public void account_create(final SubscribeFragment subscribeFragment, AccountCreateData accountCreate){
        final String endpoint = url + "account/";

        RequestBody formBody = new FormBody.Builder()
                .add("client_id", accountCreate.getClient_id())
                .add("client_secret", accountCreate.getClient_secret())
                .add("first_name", accountCreate.getFirst_name())
                .add("last_name", accountCreate.getLast_name())
                .add("email", accountCreate.getEmail())
                .add("password", accountCreate.getPassword())
                .build();

        Request request = new Request.Builder()
                .url(endpoint)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {

                    if(response.code() == 404 || response.code() == 409){
                        try {
                            JSONObject jsonResponse = new JSONObject(response.body().string());
                            JSONObject json_error = jsonResponse.getJSONObject("error");
                            final String display = json_error.optString("display");


                            subscribeFragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                subscribeFragment.requestError(display,"EMAIL");
                            }
                        });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else if(response.code() == 400){
                        try {
                            JSONObject jsonResponse = new JSONObject(response.body().string());
                            JSONObject json_error = jsonResponse.getJSONObject("error");
                            final String display = json_error.optString("display");


                            subscribeFragment.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    subscribeFragment.requestError(display,"PASSWORD");
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    else{
                        Log.d("Mini-Projet", "Error : " + endpoint);
                        Log.d("Mini-Projet", "Error : " + response.code());
                        Log.d("Mini-Projet", "Error : " + response.body().string());
                    }

                }
                else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONObject json_content = jsonResponse.getJSONObject("content");
                        String access_token = json_content.optString("access_token");
                        String token_type = json_content.optString("token_type");
                        String refresh_token = json_content.optString("refresh_token");

                        SharedPreferences prefs = subscribeFragment.getActivity().getSharedPreferences("ca.ulaval.ima.mp", Context.MODE_PRIVATE);
                        prefs.edit().putString("ca.ulaval.ima.mp.access_token", access_token).apply();


                        subscribeFragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                subscribeFragment.updateUi();
                            }
                        });



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    static public void account_login(final AccountFragment accountFragment, AccountLoginData accountLogin){
        final String endpoint = url + "account/login/";

        RequestBody formBody = new FormBody.Builder()
                .add("client_id", accountLogin.getClient_id())
                .add("client_secret", accountLogin.getClient_secret())
                .add("email", accountLogin.getEmail())
                .add("password", accountLogin.getPassword())
                .build();

        Request request = new Request.Builder()
                .url(endpoint)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {

                    try {
                    JSONObject jsonResponse = new JSONObject(response.body().string());
                    JSONObject json_error = jsonResponse.getJSONObject("error");
                    final String display = json_error.optString("display");

                    accountFragment.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(response.code() == 404){
                                accountFragment.requestError(display,"EMAIL");
                            }
                            else if(response.code() == 401){
                                accountFragment.requestError(display,"PASSWORD");
                            }
                            else if(response.code() == 400){
                                accountFragment.requestError(display,"EMAIL");
                                accountFragment.requestError(display,"PASSWORD");
                            }
                            else{
                                Log.d("Mini-Projet", "Error : " + endpoint);
                                Log.d("Mini-Projet", "Error : " + response.code());
                                try {
                                    Log.d("Mini-Projet", "Error : " + response.body().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONObject json_content = jsonResponse.getJSONObject("content");
                        String access_token = json_content.optString("access_token");
                        String token_type = json_content.optString("token_type");
                        String refresh_token = json_content.optString("refresh_token");

                        SharedPreferences prefs = accountFragment.getActivity().getSharedPreferences("ca.ulaval.ima.mp", Context.MODE_PRIVATE);
                        prefs.edit().putString("ca.ulaval.ima.mp.access_token", access_token).apply();


                        accountFragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                accountFragment.updateUi();
                            }
                        });



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    static public void account_info(final InfoFragment infoFragment){
        final String endpoint = url + "account/me/";

        SharedPreferences prefs = infoFragment.getActivity().getSharedPreferences("ca.ulaval.ima.mp", Context.MODE_PRIVATE);
        String access_token = prefs.getString("ca.ulaval.ima.mp.access_token", "");

        Request request = new Request.Builder()
                .url(endpoint)
                .addHeader("Authorization", "Bearer " + access_token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {

                    infoFragment.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(response.code() == 400){
                                infoFragment.requestError();
                            }
                            else{
                                Log.d("Mini-Projet", "Error : " + endpoint);
                                Log.d("Mini-Projet", "Error : " + response.code());
                                try {
                                    Log.d("Mini-Projet", "Error : " + response.body().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });



                }
                else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONObject json_content = jsonResponse.getJSONObject("content");

                        String total_review_count = json_content.optString("total_review_count");
                        String last_name = json_content.optString("last_name");
                        String first_name = json_content.optString("first_name");
                        String email = json_content.optString("email");
                        String created = json_content.optString("created");
                        String updated = json_content.optString("updated");
                        String user = json_content.optString("user");

                        final AccountData account = new AccountData(
                                total_review_count,
                                last_name,
                                first_name,
                                email,
                                created,
                                updated,
                                user);

                        infoFragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                infoFragment.updateUi(account);
                            }
                        });



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    static public void review_create(final ReviewCreateActivity reviewCreateActivity, ReviewCreateData reviewCreate){

        final String endpoint = url + "review/";

        SharedPreferences prefs = reviewCreateActivity.getSharedPreferences("ca.ulaval.ima.mp", Context.MODE_PRIVATE);
        String access_token = prefs.getString("ca.ulaval.ima.mp.access_token", "");

        RequestBody formBody = new FormBody.Builder()
                .add("restaurant_id", reviewCreate.getRestaurantID())
                .add("stars", reviewCreate.getStars())
                .add("comment", reviewCreate.getComment())
                .build();

        Request request = new Request.Builder()
                .url(endpoint)
                .post(formBody)
                .addHeader("Authorization", "Bearer " + access_token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {

                    Log.d("Mini-Projet", "Error : " + endpoint);
                    Log.d("Mini-Projet", "Error : " + response.code());
                    try {
                        Log.d("Mini-Projet", "Error : " + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONObject json_content = jsonResponse.getJSONObject("content");
                        final String id = json_content.optString("id");


                        reviewCreateActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                reviewCreateActivity.updateUi(id);
                            }
                        });



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    static public void set_review_picture(final ReviewCreateActivity reviewCreateActivity, String review_id, File file){

        final String endpoint = url + "review/" + review_id + "/image/";

        SharedPreferences prefs = reviewCreateActivity.getSharedPreferences("ca.ulaval.ima.mp", Context.MODE_PRIVATE);
        String access_token = prefs.getString("ca.ulaval.ima.mp.access_token", "");

        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("userid", "8457851245")
                .addFormDataPart("userfile","profile.png", RequestBody.create(MEDIA_TYPE_PNG, file)).build();

        Request request = new Request.Builder()
                .url(endpoint)
                .post(req)
                .addHeader("Authorization", "Bearer " + access_token)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {

                    Log.d("Mini-Projet", "Error : " + endpoint);
                    Log.d("Mini-Projet", "Error : " + response.code());
                    try {
                        Log.d("Mini-Projet", "Error : " + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONObject json_content = jsonResponse.getJSONObject("content");

                        reviewCreateActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                reviewCreateActivity.closeUi();
                            }
                        });



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }




}

