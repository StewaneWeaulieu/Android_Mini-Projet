package ca.ulaval.ima.mp.ui.account;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import ca.ulaval.ima.mp.KungryRequest;
import ca.ulaval.ima.mp.R;
import ca.ulaval.ima.mp.data.AccountCreateData;
public class SubscribeFragment extends Fragment {


    SubscribeFragment subscribeFragment = this;
    EditText account_subscribe_email_edit_text;
    EditText account_subscribe_password_edit_text;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_account_subscribe, container, false);

        View decorView = getActivity().getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();





        Button account_subscribe_subscribe_button = root.findViewById(R.id.account_subscribe_subscribe_button);
        account_subscribe_subscribe_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText account_subscribe_first_name_edit_text = root.findViewById(R.id.account_subscribe_first_name_edit_text);
                EditText account_subscribe_last_name_edit_text = root.findViewById(R.id.account_subscribe_last_name_edit_text);
                account_subscribe_email_edit_text = root.findViewById(R.id.account_subscribe_email_edit_text);
                account_subscribe_password_edit_text = root.findViewById(R.id.account_subscribe_password_edit_text);

                account_subscribe_email_edit_text.setError(null);
                account_subscribe_password_edit_text.setError(null);

                String first_name = account_subscribe_first_name_edit_text.getText().toString();
                String last_name = account_subscribe_last_name_edit_text.getText().toString();
                String email = account_subscribe_email_edit_text.getText().toString();
                String password = account_subscribe_password_edit_text.getText().toString();

                AccountCreateData account_create = new AccountCreateData(email, password, first_name,last_name);
                KungryRequest.account_create(subscribeFragment,account_create);

            }
        });


        TextView account_subscribe_kungry_member_text_view = root.findViewById(R.id.account_subscribe_kungry_member_text_view);
        TextView account_subscribe_to_subscribe_text_view = root.findViewById(R.id.account_subscribe_to_subscribe_text_view);

        View.OnClickListener accountConnectOnclicklistener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, new AccountFragment());
                transaction.commit();
            }
        };
        account_subscribe_kungry_member_text_view.setOnClickListener(accountConnectOnclicklistener);
        account_subscribe_to_subscribe_text_view.setOnClickListener(accountConnectOnclicklistener);

        return root;


    }



    public void updateUi(){

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, new InfoFragment());
        transaction.commit();

    }




    public void requestError(String error, String type){
        if(type.equals("EMAIL")){
            account_subscribe_email_edit_text.setError(error);
        }
        else {
            account_subscribe_password_edit_text.setError(error);
        }


    }

}
