package com.example.jose.pjobs;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //Final
    private final static int GOOGLE_SINGIN = 7;

    //Google
    GoogleApiClient googleApiClient;
    SignInButton signInButtonGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initiations(Views)-----------------------------------------------------------------
        signInButtonGoogle = (SignInButton) findViewById(R.id.btn_singInGoogle);
        //-----------------------------------------------------------------------------------


        //Initiations(Google-Services)--------------------------------------------------------
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //------------------------------------------------------------------------------------


        //Onclick-----------------------------------------------------------------------------
        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i, GOOGLE_SINGIN);
            }
        });

        //------------------------------------------------------------------------------------

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case GOOGLE_SINGIN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                TestResultSingInGoogle(result);
                break;
        }
    }


    //Method that open the MainActivity-UserWithGoogle----------------------------------------------------
    private void TestResultSingInGoogle(GoogleSignInResult result) {
        if(result.isSuccess()){
            Intent i = new Intent(this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }else{
            Toast.makeText(this,"Erro en el incio de sesion con Google.", Toast.LENGTH_SHORT).show();
        }
    }
    //-----------------------------------------------------------------------------------------

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
