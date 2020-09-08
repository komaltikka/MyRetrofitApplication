package com.example.myretrofitapplication.activities;


import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myretrofitapplication.R;
import com.example.myretrofitapplication.constants.Constants;
import com.example.myretrofitapplication.extras.AppPreference;
import com.example.myretrofitapplication.fragment.LoginFragment;
import com.example.myretrofitapplication.fragment.ProfileFragment;
import com.example.myretrofitapplication.fragment.RegistrationFragment;
import com.example.myretrofitapplication.services.MyInterface;
import com.example.myretrofitapplication.services.RetrofitClient;
import com.example.myretrofitapplication.services.ServiceApi;

public class MainActivity extends AppCompatActivity implements MyInterface {
FrameLayout container_fragment;
public static AppPreference appPreference;
public static ServiceApi serviceApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appPreference=new AppPreference(this);
        container_fragment=findViewById(R.id.fragment_container);

       serviceApi= RetrofitClient.getApiClient( Constants.baseUrl.BASE_URL).create(ServiceApi.class);
        if(container_fragment!=null)
        {
            if(savedInstanceState!=null)
            {
                return;
            }
            if(appPreference.getLoginStatus())
            {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new ProfileFragment())
                        .commit();


            }else{
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new LoginFragment())
                        .commit();


            }
        }

    }
    @Override
    public void register() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new RegistrationFragment())
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void login(String name, String email, String created_at) {
        appPreference.setLoginStatus(true);
        appPreference.setDisplayName(name);
        appPreference.setDisplayemail(email);
        appPreference.setDisplaydate(created_at);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ProfileFragment())

                .commit();


    }

    @Override
    public void logout() {
        appPreference.setLoginStatus(false);
        appPreference.setDisplayName("Name");
        appPreference.setDisplayemail("Email");
        appPreference.setDisplaydate("Date");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())

                .commit();

    }


}
