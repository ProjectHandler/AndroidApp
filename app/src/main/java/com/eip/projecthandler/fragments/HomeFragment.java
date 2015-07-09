package com.eip.projecthandler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eip.projecthandler.R;

public class HomeFragment extends Fragment {

    private TextView tv_test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        /*tv_test = (TextView) view.findViewById(R.id.tv_test);
        //tv_test.setText("plop");

        try {
            URL url = new URL("http://163.5.84.233:8080/projecthandler/api/user/get/12");
            //HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //urlConnection.disconnect();

            //tv_test.setText(in.toString());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }*/


        return view;
    }

}
