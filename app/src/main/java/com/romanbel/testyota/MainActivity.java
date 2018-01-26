package com.romanbel.testyota;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String BASE_URL = "https://api.spacexdata.com/v2/launches";
    private Spinner mSpinerYear;
    private String mYear = "2018";
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinerYear = findViewById(R.id.spinner_year);

        List<String> listYear = setdata();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listYear);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinerYear.setAdapter(adapter);
        mSpinerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mYear = adapterView.getItemAtPosition(i).toString();
                startAsyncTask();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        startAsyncTask();
    }

    private void startAsyncTask(){
        MyTask task = new MyTask();
        task.execute();
    }

    private class MyTask extends AsyncTask<Void, Void, List<Flight> >{

        @Override
        protected List<Flight>  doInBackground(Void... voids) {
            return request();
        }

        @Override
        protected void onPostExecute(List<Flight> flights) {
            super.onPostExecute(flights);
            notifyRV(flights);
            setUpRecyclerView(flights);
        }
    }

    private void setUpRecyclerView(List<Flight> flights){
        if (mRecyclerView == null){
            Log.d("LOG_TAG", "setUpRecyclerView" + flights.get(0).getRocketName());
            mRecyclerAdapter = new RecyclerAdapter(flights, this);
            mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewFragment);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setAdapter(mRecyclerAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }
    }

    private void notifyRV(List<Flight> flights) {
        if(mRecyclerAdapter != null){
            mRecyclerAdapter = new RecyclerAdapter(flights, this);
            mRecyclerView.setAdapter(mRecyclerAdapter);
        }
    }

    private List<Flight> request() {
        Uri baseUri = Uri.parse(BASE_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("launch_year", mYear);
        Request request = new Request();
        List<Flight> flights = request.fetchTranslateData(uriBuilder.toString());
        return flights;
    }

    public List<String> setdata(){
        List<String> listYear = new ArrayList<>();
        listYear.add("2018");
        listYear.add("2017");
        listYear.add("2016");
        listYear.add("2015");
        listYear.add("2014");
        listYear.add("2013");
        listYear.add("2012");
        listYear.add("2011");
        listYear.add("2010");
        listYear.add("2009");
        listYear.add("2008");
        listYear.add("2007");
        listYear.add("2006");
        return listYear;
    }
}
