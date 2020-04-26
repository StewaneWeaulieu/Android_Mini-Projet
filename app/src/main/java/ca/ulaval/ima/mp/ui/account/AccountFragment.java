package ca.ulaval.ima.mp.ui.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import ca.ulaval.ima.mp.KungryRequest;
import ca.ulaval.ima.mp.R;
import ca.ulaval.ima.mp.data.AccountCreateData;
import ca.ulaval.ima.mp.data.AccountLoginData;

public class AccountFragment extends Fragment {


    AccountFragment accountFragment = this;
    Button account_connect_button;

    EditText account_email_edit_text ;
    EditText account_password_edit_text;


    public void onAttach(Context context) {
        super.onAttach(context);

        SharedPreferences prefs = getActivity().getSharedPreferences("ca.ulaval.ima.mp", Context.MODE_PRIVATE);
        String access_token = prefs.getString("ca.ulaval.ima.mp.access_token", "");

        if (!access_token.equals("")){
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, new InfoFragment());
            transaction.commit();

        }

    }


    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        final View root = inflater.inflate(R.layout.fragment_account, container, false);

        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();


        account_connect_button = root.findViewById(R.id.account_connect_button);
        account_connect_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                account_email_edit_text = root.findViewById(R.id.account_email_edit_text);
                account_password_edit_text = root.findViewById(R.id.account_password_edit_text);

                account_email_edit_text.setError(null);
                account_password_edit_text.setError(null);

                String email = account_email_edit_text.getText().toString();
                String password = account_password_edit_text.getText().toString();

                AccountLoginData account_login = new AccountLoginData(email,password);


                KungryRequest.account_login(accountFragment,account_login);

            }
        });

        TextView account_kungry_new_text_view = root.findViewById(R.id.account_kungry_new_text_view);
        TextView account_subscribe_text_view = root.findViewById(R.id.account_subscribe_text_view);

        View.OnClickListener accountSubscribeOnclicklistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, new SubscribeFragment());
                transaction.commit();
            }
        };
        account_kungry_new_text_view.setOnClickListener(accountSubscribeOnclicklistener);
        account_subscribe_text_view.setOnClickListener(accountSubscribeOnclicklistener);


        return root;
    }


    public void updateUi(){

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, new InfoFragment());
        transaction.commit();

    }

    public void requestError(String error, String type){

        if(type.equals("EMAIL")){
            account_email_edit_text.setError(error);
        }
        else{
            account_password_edit_text.setError(error);
        }

    }



}