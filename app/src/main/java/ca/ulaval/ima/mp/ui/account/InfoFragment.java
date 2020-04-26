package ca.ulaval.ima.mp.ui.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ca.ulaval.ima.mp.KungryRequest;
import ca.ulaval.ima.mp.R;
import ca.ulaval.ima.mp.data.AccountData;


public class InfoFragment extends Fragment {


    InfoFragment infoFragment = this;

    TextView account_info_name_value_text_view;
    TextView account_info_email_value_text_view;
    TextView account_info_reviews_value_text_view;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        View root = inflater.inflate(R.layout.fragment_account_info, container, false);

        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();

        Button account_connexion_button = root.findViewById(R.id.account_info_logout_button);
        account_connexion_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestError();
            }
        });


        account_info_name_value_text_view  = root.findViewById(R.id.account_info_name_value_text_view);
        account_info_email_value_text_view  = root.findViewById(R.id.account_info_email_value_text_view);
        account_info_reviews_value_text_view = root.findViewById(R.id.account_info_reviews_value_text_view);


        KungryRequest.account_info(infoFragment);

        return root;
    }



    public void updateUi(AccountData account){

        account_info_name_value_text_view.setText(account.getName());
        account_info_email_value_text_view.setText(account.getEmail());
        account_info_reviews_value_text_view.setText(account.getTotal_review_count());
    }


    public void requestError(){

        SharedPreferences prefs = getActivity().getSharedPreferences("ca.ulaval.ima.mp", Context.MODE_PRIVATE);
        prefs.edit().putString("ca.ulaval.ima.mp.access_token", "").apply();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, new AccountFragment());
        transaction.commit();
    }


}