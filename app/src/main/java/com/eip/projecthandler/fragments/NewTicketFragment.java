package com.eip.projecthandler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.eip.projecthandler.R;
import com.eip.projecthandler.constants.ApiRoutes;
import com.eip.projecthandler.helpers.api.NetworkHelper;
import com.eip.projecthandler.listeners.ObejctNetworkListener;
import com.eip.projecthandler.models.TicketPriority;
import com.eip.projecthandler.models.TicketTracker;
import com.eip.projecthandler.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public class NewTicketFragment extends Fragment {

    private Set<User> users;
    private Set<TicketPriority> ticketPriorities;
    private Set<TicketTracker> ticketTrackers;

    public static final NewTicketFragment newInstance() {
        NewTicketFragment f = new NewTicketFragment();
        Bundle bdl = new Bundle();
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_new_ticket, container, false);
        loadDataForTicketCreation();
        return view;
    }

    private void loadDataForTicketCreation() {
        try {
            String url = ApiRoutes.TICKET_GET_DATA_FOR_NEW_TICKET;
            NetworkHelper networkHelper = NetworkHelper.getInstance(getActivity().getApplicationContext());
            networkHelper.retrieveToken(getActivity().getApplicationContext());
            /*networkHelper.arrayRequestServer(new ArrayNetworkListener() {

                @Override
                public void onCallSuccess(JSONArray result) throws JSONException {
                    //Log.d("NewTicketFragment", "requestServer ok result: " + result);
                }

                @Override
                public void onCallError(VolleyError error) {
                    Log.d("NewTicketFragment", "requestServer 42 Error: " + error);
                    error.printStackTrace();
                }
            }, Request.Method.GET, url, null);*/

            networkHelper.objectRequestServer(new ObejctNetworkListener() {
                @Override
                public void onCallSuccess(JSONObject result) throws JSONException {
                    Log.d("NewTicketFragment", "requestServer ok result: " + result);
                }

                @Override
                public void onCallError(VolleyError error) {
                    Log.d("NewTicketFragment", "requestServer Error: " + error);
                    error.printStackTrace();
                }
            }, Request.Method.GET, url, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
