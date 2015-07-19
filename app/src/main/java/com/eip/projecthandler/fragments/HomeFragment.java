package com.eip.projecthandler.fragments;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.eip.projecthandler.R;
import com.eip.projecthandler.constants.ApiRoutes;
import com.eip.projecthandler.constants.AuthenticatorConstants;
import com.eip.projecthandler.helpers.account.AccountAuthenticator;
import com.eip.projecthandler.helpers.account.AccountHelper;
import com.eip.projecthandler.helpers.api.NetworkHelper;
import com.eip.projecthandler.listeners.NetworkListener;
import com.eip.projecthandler.models.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class HomeFragment extends Fragment {

    private TextView tv_test;

    /*http://localhost:8080/projecthandler/api/user/get/12?token=002ee001-ef24-4cf3-ac39-4b1dc7576351
    ?token=002ee001-ef24-4cf3-ac39-4b1dc7576351*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tv_test = (TextView) view.findViewById(R.id.tv_test);

        /*tv_test = (TextView) view.findViewById(R.id.tv_test);
        //tv_test.setText("plop");*/
        //tv_test.setText(in.toString());
        try {
            //Gets an auth token of the specified type for a particular account, prompting the user for credentials if necessary.
            URL url = new URL(ApiRoutes.SERVER_DEV+"/api/user/get/12?token=");
            String token = null;

           // AccountManager accMgr = AccountManager.get(getActivity());
            //AccountHelper.getAccount(getActivity());

            AccountAuthenticator aAuth = new AccountAuthenticator(getActivity());
            Account acc = AccountHelper.getAccount(getActivity());
            try {
                Bundle b = aAuth.getAuthToken(null, acc, AuthenticatorConstants.AUTH_TOKEN_TYPE, null);
                token = (String) b.get("authtoken");
                Log.d("HomeFragment", "requestServer success, token: " + token);
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }


            NetworkHelper n = NetworkHelper.getInstance(getActivity());
            //n.setAuthToken("402445ca-2650-46ee-be9d-a906dab09c63");
            n.setAuthToken(token);
            n.requestServer(new NetworkListener() {
                @Override
                public void onCallSuccess(JSONObject result) throws JSONException {
                    Log.d("HomeFragment", "requestServer success, result: " + result.toString());
                    tv_test.setText(result.get("firstName") + " " + result.get("lastName") + " " + result.get("email"));
                    //tv_test.setText(in.toString());
                }

                @Override
                public void onCallError(VolleyError error) {
                    Log.d("HomeFragment", "requestServer Error: " + error);
                    error.printStackTrace();
                }
            }, Request.Method.GET, ApiRoutes.SERVER_DEV + "/api/user/get/51");


        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

}
