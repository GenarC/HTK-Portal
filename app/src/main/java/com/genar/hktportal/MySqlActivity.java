package com.genar.hktportal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.genar.hktportal.model.Person;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MySqlActivity extends AppCompatActivity {

    @BindView(R.id.sorgu_gonder)
    Button btn_gonder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sql);

        ButterKnife.bind(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }


    @OnClick(R.id.sorgu_gonder)
    public void sorgu(){

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.genar.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SorguApi service = retrofit.create(SorguApi.class);

        Call<List<Person>> repos = service.personList();

        repos.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                Person p = response.body().get(1);
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {

            }
            *//*@Override
            public void onResponse(Response<Person> response, Retrofit retrofit) {

                try {

                    cityname.setText( response.body().getName());
                    temperature.setText(response.body().getTemp());
                    pressure.setText(response.body().getPressure());
                    humidity.setText(response.body().getHumidity());
                    wind.setText(response.body().getWind());
                    coord.setText(response.body().getCoord());

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }*//*
        });




        Log.d("cem", "asdas");*/

        try {

            String urlStr="http://www.genar.net/cem";

            // call async task
            new BackgroundTask().execute(urlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class BackgroundTask extends AsyncTask<String, Void, String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {

            String url = params[0];

            String response="";
            try {

                response = WebServiceUtil.getRequest(url);

            }catch (Exception e){
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {

            try {
                // parse response to json object
                ArrayList<Person> personList = new ArrayList<>();
                JSONObject list = new JSONObject(response);
                JSONArray persons = list.getJSONArray("liste");

                for(int i=0; i < persons.length(); i++){
                    JSONObject o1 = persons.getJSONObject(i);
                    JSONObject o2 = o1.getJSONObject(""+(i+1));

                    Person p = new Person();
                    p.setAdi(o2.getString("adi"));
                    p.setEposta(o2.getString("eposta"));
                    p.setNo(o2.getString("no"));
                    p.setSifre(o2.getString("sifre"));
                    p.setSoyadi(o2.getString("soyadi"));

                    personList.add(p);
                }

                Log.d("cem", persons.toString());

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled(String result) {
            super.onCancelled(result);
        }
    }
}
