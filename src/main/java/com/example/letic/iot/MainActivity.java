package com.example.letic.iot;


import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.util.ArrayList;

import utils.RequestParamsBuilder;

public class MainActivity extends AppCompatActivity {

    RequestQueue mQueue;
    TextView mCommand;
    TextView mResponse;
    TextView mError;

    ProgressBar mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mQueue = Volley.newRequestQueue(this);

        mCommand = (TextView) findViewById(R.id.speechText);
        mResponse = (TextView) findViewById(R.id.response);
        mError = (TextView) findViewById(R.id.error);

        mLoading = (ProgressBar) findViewById(R.id.loading);

        begin();

        Button button = (Button) findViewById(R.id.speechButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
            }
        });
    }

    private void begin(){
        mCommand.setVisibility(View.GONE);
        mResponse.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    private void loading(){
        mCommand.setVisibility(View.VISIBLE);
        mResponse.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
    }

    private void response(){
        mCommand.setVisibility(View.VISIBLE);
        mResponse.setVisibility(View.VISIBLE);
        mError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    private void error(){
        mCommand.setVisibility(View.VISIBLE);
        mResponse.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.GONE);
    }

    private void clickButton(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What should I do?");
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            mCommand.setText(matches.get(0).toString());
            loading();
            send(matches.get(0).toString());
        }else{
            mError.setText("Sorry\nAn error has happen\n\\_(\"/)_/\nPlease, try again");
            error();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void send (String voiceOrder){

        RequestParamsBuilder paramsBuilder = new RequestParamsBuilder(voiceOrder);

        if (!paramsBuilder.isNotFound()) {
            // prepare the Request
            JsonObjectRequest getRequest = new JsonObjectRequest(paramsBuilder.getMethod(), paramsBuilder.getURL(), paramsBuilder.getJson(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            showResult(response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mError.setText("Sorry\n"+error.getMessage()+"\n\\_(\"/)_/\nPlease, try again");
                            error();
                        }
                    }
            );


// add it to the RequestQueue
            mQueue.add(getRequest);
        } else {
            mError.setText("Sorry\nCommand not found\n\\_(\"/)_/\nPlease, try again");
            error();
        }
    }

    private void showResult(String response){

        GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
        Gson gson = gsonBuilder.create();

        try {
            DweetList d = gson.fromJson(response, DweetList.class);
            mResponse.setText(d.getObject() + "\n" + d.getValeur());
            response();
        }catch (JsonSyntaxException e){
            try {
                DweetSingle d = gson.fromJson(response, DweetSingle.class);
                mResponse.setText(d.getObject()+ "\n" + d.getValeur());
                response();
            }catch (Exception ex){
                mError.setText("Sorry\nObject not found\n\\_(\"/)_/\nPlease, try again");
                error();
            }

        }catch (Exception e){
            mError.setText("Sorry\n"+e.getLocalizedMessage().toString()+"\n\\_(\"/)_/\nPlease, try again");
            error();
        }
    }
}
