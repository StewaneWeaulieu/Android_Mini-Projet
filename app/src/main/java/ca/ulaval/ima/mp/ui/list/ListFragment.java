package ca.ulaval.ima.mp.ui.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.ulaval.ima.mp.KungryLocation;
import ca.ulaval.ima.mp.KungryRequest;
import ca.ulaval.ima.mp.PaginationScrollListener;
import ca.ulaval.ima.mp.R;
import ca.ulaval.ima.mp.data.RestaurantLightData;
import ca.ulaval.ima.mp.ui.account.AccountFragment;
import ca.ulaval.ima.mp.ui.account.InfoFragment;
import ca.ulaval.ima.mp.ui.restaurant.RestaurantActivity;

public class ListFragment extends Fragment implements ListAdapter.OnItemListener {

    private RecyclerView recyclerView;

    int totalPage = 0;
    int currentPage;
    boolean isLoading;

    ListAdapter adapter;
    ListFragment listFragment = this;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        View root = inflater.inflate(R.layout.fragment_list, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

        recyclerView = root.findViewById(R.id.recycler_view_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;

                //Increment page index to load the next one
                currentPage += 1;
                Log.d("Patate", "loadMoreItems: " + currentPage);
                KungryRequest.restaurant_list(listFragment,currentPage, KungryLocation.getLocationData(),30);
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

        currentPage = 1;
        KungryRequest.restaurant_list(this,currentPage, KungryLocation.getLocationData(),30);


        return root;
    }

    public void updateUi(ArrayList<RestaurantLightData> restaurants, int currentPage, int pTotalPage){
        if(currentPage == 1){
            totalPage = pTotalPage;
            adapter = new ListAdapter(restaurants,this);
            recyclerView.setAdapter(adapter);
        }
        else{
            adapter.addAll(restaurants);
        }

        isLoading = false;
    }


    @Override
    public void onItemClick(int position) {
        RestaurantLightData restaurant = adapter.get(position);

        Intent intent = new Intent(getActivity(), RestaurantActivity.class);
        intent.putExtra("restaurantID", restaurant.getId());
        startActivityForResult(intent,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {

                String ok;
                if(data != null){
                    ok = data.getStringExtra("ok");
                }
                else{
                    ok = "";
                }

                if(ok.equals("ok")){
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, new AccountFragment());
                    transaction.commit();
                }
            }
        }
    }


}