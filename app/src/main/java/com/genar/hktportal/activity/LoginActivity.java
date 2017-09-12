package com.genar.hktportal.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.genar.hktportal.LoginApi;
import com.genar.hktportal.R;
import com.genar.hktportal.helper.Utils;
import com.genar.hktportal.model.LoginResponse;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_etsicilno)
    EditText etRegistryNo;
    @BindView(R.id.login_etsifre)
    EditText etPassword;
    @BindView(R.id.login_btngiris)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_btngiris)
    public void loginCheck(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.genar.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginApi service = retrofit.create(LoginApi.class);

        Call<LoginResponse> loginResult = service.loginResponse("login", etRegistryNo.getText().toString(), etPassword.getText().toString());

        loginResult.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.body().getSuccess() == 1){
                    Utils.setCurrentRegistryNo( etRegistryNo.getText().toString());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Giriş verileri hatalı, tekrar deneyin.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }
}
