package com.genar.hktportal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.genar.hktportal.GlobalApplicaiton;
import com.genar.hktportal.R;
import com.genar.hktportal.api.HataService;
import com.genar.hktportal.api.TopCountService;
import com.genar.hktportal.helper.Utils;
import com.genar.hktportal.model.Hata;
import com.genar.hktportal.model.TopCount;
import com.genar.hktportal.response.HataResponse;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
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
    CircularProgressButton btnLogin;

    Intent intent = new Intent();

    List<Hata> hataListTop = new ArrayList<>();
    List<Hata> hataListTopOperator = new ArrayList<>();
    List<Hata> hataListTopBolum = new ArrayList<>();


    private void enableInputs(boolean stat){
        etRegistryNo.setEnabled(stat);
        etPassword.setEnabled(stat);
        btnLogin.setEnabled(stat);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.login_btngiris)
    public void loginCheck(){
        btnLogin.startAnimation();
        enableInputs(false);
        Runnable r = new Runnable() {
            @Override
            public void run(){

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.genar.net/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                LoginService service = retrofit.create(LoginService.class);

                Call<LoginResponse> loginResult = service.login("login", etRegistryNo.getText().toString(), etPassword.getText().toString());


                loginResult.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.body().getSuccess() == 1){
                            Utils.setCurrentRegistryNo( etRegistryNo.getText().toString());

                            getTopCount();
                        }else{
                            Toast.makeText(LoginActivity.this, "Giriş verileri hatalı, tekrar deneyin.", Toast.LENGTH_SHORT).show();
                            btnLogin.revertAnimation();
                            enableInputs(true);
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        btnLogin.revertAnimation();
                        enableInputs(true);
                    }
                });
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 750);
    }

    private void getTopCount(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.genar.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TopCountService service = retrofit.create(TopCountService.class);

        Call<TopCount> topCountResult = service.getTopCount("topCount");


        topCountResult.enqueue(new Callback<TopCount>() {
            @Override
            public void onResponse(Call<TopCount> call, Response<TopCount> response) {
                if(response.body().getTopCount() != null){
                    GlobalApplicaiton.TOP_LIST_COUNT = response.body().getTopCount();

                    hatalariGetir();
                }else{
                    Toast.makeText(LoginActivity.this, "TopList sayısı alınamadı", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<TopCount> call, Throwable t) {
                btnLogin.revertAnimation();
                enableInputs(true);
            }
        });
    }

    public void hatalariGetir(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.genar.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HataService service = retrofit.create(HataService.class);

        Call<HataResponse> hataResult = service.hataResponse("topXHata",GlobalApplicaiton.TOP_LIST_COUNT);

        hataResult.enqueue(new Callback<HataResponse>() {
            @Override
            public void onResponse(Call<HataResponse> call, Response<HataResponse> response) {
                if(response.body().getSuccess() == 3){
                    hataListTopOperator = response.body().getHatalarOperator();
                    hataListTopBolum = response.body().getHatalarBolum();
                    hataListTop = response.body().getHatalarMost();

                    intent = new Intent(LoginActivity.this, MainTabActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("TopOperatorList", (ArrayList) hataListTopOperator);
                    bundle.putParcelableArrayList("TopBolumList", (ArrayList) hataListTopBolum);
                    bundle.putParcelableArrayList("TopList", (ArrayList) hataListTop);
                    intent.putExtras(bundle);

                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(LoginActivity.this, "Hata listeleri alınırken hata oluştu, daha sonra tekrar deneyin.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HataResponse> call, Throwable t) {

            }
        });
    }




}
