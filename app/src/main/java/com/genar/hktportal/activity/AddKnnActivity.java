package com.genar.hktportal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.genar.hktportal.R;
import com.genar.hktportal.api.KnnService;
import com.genar.hktportal.api.MakineService;
import com.genar.hktportal.helper.Utils;
import com.genar.hktportal.model.Makine;
import com.genar.hktportal.response.KnnResponse;
import com.genar.hktportal.response.MakineResponse;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;
import java.util.Date;
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

public class AddKnnActivity extends AppCompatActivity{

    @BindView(R.id.addknn_etmakine)
    EditText etMakineBarkod;
    @BindView(R.id.addknn_etoperator)
    EditText etOperatorBarkod;
    @BindView(R.id.addknn_etaciklama)
    EditText etAciklama;
    @BindView(R.id.addknn_btnoperator)
    Button btnOperator;
    @BindView(R.id.addknn_btnmakine)
    Button btnMakine;
    @BindView(R.id.addknn_btnknnkaydet)
    CircularProgressButton btnKnnKaydet;
    @BindView(R.id.addknn_spinnerbolumP)
    Spinner spBolumP;
    @BindView(R.id.addknn_spinnerbolumS)
    Spinner spBolumS;

    List<Makine> makineList = new ArrayList<>();

    ArrayList<String> makineIsimListeP = new ArrayList<>();
    ArrayList<String> makineNoListeP =new ArrayList<>();

    ArrayList<String> makineIsimListeS = new ArrayList<>();
    ArrayList<String> makineNoListeS =new ArrayList<>();


    //
    // php ye yaz
    // echo(asdasdasdas, JSON_UNESCAPED_UNICODE)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addknn);

        ButterKnife.bind(this);

        makineleriGetir();

        spBolumP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    makineIsimListeS.clear();
                    makineNoListeS.clear();
                    makineIsimListeS.add("Lütfen alt bölüm seçin.");
                    for (Makine m: makineList){
                        if(m.getUst().equals(makineNoListeP.get(position-1))){
                            makineIsimListeS.add(m.getAdi());
                            makineNoListeS.add(m.getNo());
                        }
                    }
                    setAdapterContent(spBolumS, makineIsimListeS);
                    spBolumS.setEnabled(true);
                }else{
                    spBolumS.setSelection(0);
                    spBolumS.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spBolumS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    setButtonsEnabled(true);
                }else{
                    setButtonsEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @OnClick({R.id.addknn_btnmakine, R.id.addknn_btnoperator, R.id.addknn_btnknnkaydet})
    public void onItemClick(View v) {
        switch(v.getId()){
            case R.id.addknn_btnmakine:
                    Utils.startActivityForResult(this,BarcodeActivity.class, Utils.BarkodMakineRequest);
                break;
            case R.id.addknn_btnoperator:
                    Utils.startActivityForResult(this,BarcodeActivity.class, Utils.BarkodOperatorRequest);
                break;
            case R.id.addknn_btnknnkaydet:
                    knnKaydet();
                break;
        }
    }

    private boolean checkInputs(){
        if(etOperatorBarkod.getText().toString().length() != 0 && etMakineBarkod.getText().toString().length() != 0){
            return true;
        }else{
            return false;
        }
    }

    private void knnKaydet(){
        btnKnnKaydet.startAnimation();

        Runnable r = new Runnable() {
            @Override
            public void run(){
                if(checkInputs()){
                    setButtonsEnabled(false);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://www.genar.net/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    KnnService service = retrofit.create(KnnService.class);

                    Call<KnnResponse> knnResult = service.knnResponse("knnEkle",
                            etOperatorBarkod.getText().toString(),
                            Long.toString(new Date().getTime()/1000),
                            makineNoListeS.get(spBolumS.getSelectedItemPosition()-1),
                            etMakineBarkod.getText().toString(),
//                            "gorevDeneme",
                            etAciklama.getText().toString());

                    knnResult.enqueue(new Callback<KnnResponse>() {
                        @Override
                        public void onResponse(Call<KnnResponse> call, Response<KnnResponse> response) {
                            if(response.body().getSuccess() == 1){
                                Toast.makeText(AddKnnActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                btnKnnKaydet.revertAnimation();
                                clearInputs();
                            }else{
                                Toast.makeText(AddKnnActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                btnKnnKaydet.revertAnimation();
                                setButtonsEnabled(true);
                            }

                        }

                        @Override
                        public void onFailure(Call<KnnResponse> call, Throwable t) {
                            Toast.makeText(AddKnnActivity.this, "Bir hata ile karşılaşıldı", Toast.LENGTH_SHORT).show();
                            btnKnnKaydet.revertAnimation();
                            setButtonsEnabled(true);
                        }
                    });
                }else{
                    Toast.makeText(AddKnnActivity.this, "Tüm verileri doldurmanız gerekli.", Toast.LENGTH_SHORT).show();
                    btnKnnKaydet.revertAnimation();
                }

            }
        };

        Handler h = new Handler();
        h.postDelayed(r, 750);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==Utils.BarkodMakineRequest){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data != null){
                    Barcode barcode = data.getParcelableExtra("Barcode");
                    etMakineBarkod.setText(barcode.displayValue);
                }else{
                    etMakineBarkod.setText("");
                }
            }
        }else{
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data != null){
                    Barcode barcode = data.getParcelableExtra("Barcode");
                    etOperatorBarkod.setText(barcode.displayValue);
                }else{
                    etOperatorBarkod.setText("");
                }
            }
        super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void makineleriGetir(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.genar.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MakineService service = retrofit.create(MakineService.class);

        Call<MakineResponse> makineResult = service.makineListesiGetir("makineListesi");

        makineResult.enqueue(new Callback<MakineResponse>() {
            @Override
            public void onResponse(Call<MakineResponse> call, Response<MakineResponse> response) {
                if(response.body().getSuccess() == 1){
                    makineList = response.body().getMakineler();
                    makineIsimListeP.add("Lütfen ana bölüm seçin");

                    for (Makine m: makineList){
                        if(m.getUst().equals("0")){
                            makineIsimListeP.add(m.getAdi());
                            makineNoListeP.add(m.getNo());
                        }
                    }

                    clearInputs();

                }else{
                    Toast.makeText(AddKnnActivity.this, "Makine listesi alınamadı, daha sonra tekrar deneyin.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<MakineResponse> call, Throwable t) {

            }
        });
    }

    private void setButtonsEnabled(boolean status){
        btnKnnKaydet.setEnabled(status);
        btnMakine.setEnabled(status);
        btnOperator.setEnabled(status);
        etAciklama.setEnabled(status);
    }

    private void clearInputs(){
        setAdapterContent(spBolumP, makineIsimListeP);
        spBolumS.setAdapter(null);
        spBolumS.setEnabled(false);
        etOperatorBarkod.setText("");
        etMakineBarkod.setText("");
        etAciklama.setText("");
        setButtonsEnabled(false);
    }

    private void setAdapterContent(Spinner sp, ArrayList<String> list){

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(AddKnnActivity.this, android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(spinnerAdapter);

    }
}
