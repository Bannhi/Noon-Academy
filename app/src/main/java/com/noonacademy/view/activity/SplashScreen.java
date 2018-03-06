package com.noonacademy.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.noonacademy.R;
import com.noonacademy.utils.Constants;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkIfUserIsLoggedIn();
    }

    private void checkIfUserIsLoggedIn(){
        Intent intent;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account==null) {
             intent = new Intent(this, LoginActivity.class);
             startActivity(intent);
             finish();
        }else{
            intent = new Intent(this, MainWorkingActivity.class);
            intent.putExtra(Constants.USER_NAME,account.getDisplayName());
            startActivity(intent);
            finish();
        }

    }


}
