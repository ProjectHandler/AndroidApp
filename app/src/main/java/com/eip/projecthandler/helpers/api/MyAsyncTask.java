package com.eip.projecthandler.helpers.api;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MyAsyncTask extends AsyncTask<Void, Void, String> {

    public AuthenticationHelper delegate = null;

    @Override
    protected String doInBackground(Void... params) {
        try {
            final String url = "http://192.168.1.8:8080/projecthandler/api/user/authenticate?email=test@test.com&password=Test1234";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            String token = restTemplate.getForObject(url, String.class);
            return token;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.processFinish(result);
    }
}
