package com.eip.projecthandler.fragments;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.eip.projecthandler.Adapter.CustomAdapterTicket;
import com.eip.projecthandler.R;
import com.eip.projecthandler.constants.ApiRoutes;
import com.eip.projecthandler.constants.AuthenticatorConstants;
import com.eip.projecthandler.helpers.account.AccountAuthenticator;
import com.eip.projecthandler.helpers.account.AccountHelper;
import com.eip.projecthandler.helpers.api.NetworkHelper;
import com.eip.projecthandler.listeners.ArrayNetworkListener;
import com.eip.projecthandler.models.Account;
import com.eip.projecthandler.models.Ticket;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListTicketFragment extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment {

    private List<Ticket> listTickets;
    private CustomAdapterTicket ticketAdapter;

    @Override
    public ListAdapter getListAdapter() {
        listTickets = new ArrayList<Ticket>();
        ticketAdapter = new CustomAdapterTicket(getActivity(), listTickets);
        mListView.setAdapter(ticketAdapter);

        getListOfTicket();
        return mListView.getAdapter();
    }

    @Override
    public boolean useCustomContentView() {
        return false;
    }

    @Override
    public int getCustomContentView() {
        return 0;
    }

    @Override
    public boolean pullToRefreshEnabled() {
        return true;
    }

    @Override
    public int[] getPullToRefreshColorResources() {
        return new int[]{R.color.mdl_color_primary};
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                getListOfTicket();
                setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Ticket ticket = (Ticket) adapterView.getItemAtPosition(position);
        TicketFragment ticketFragment = new TicketFragment();
        ticketFragment.setTicket(ticket);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(this);
        //fragmentTransaction.add(R.id.fragment_container, ticketFragment);
        //fragmentTransaction.commit();

        fragmentTransaction.replace(R.id.fragment_container, ticketFragment);
        fragmentTransaction.addToBackStack(this.toString());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        return false;
    }

    private void getListOfTicket() {
        try {
            String token = null;
            AccountAuthenticator aAuth = new AccountAuthenticator(getActivity());
            Account acc = AccountHelper.getAccount(getActivity());
            try {
                Bundle bundle = aAuth.getAuthToken(null, acc, AuthenticatorConstants.AUTH_TOKEN_TYPE, null);
                token = (String) bundle.get("authtoken");
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }

            String url = ApiRoutes.TICKET_GET_BY_USER;

            NetworkHelper networkHelper = NetworkHelper.getInstance(getActivity());
            networkHelper.setAuthToken(token);
            networkHelper.arrayRequestServer(new ArrayNetworkListener() {

                @Override
                public void onCallSuccess(JSONArray result) throws JSONException {
                    Log.d("ListTicketFragment", "requestServer ok result: " + result);
                    Gson gson = new Gson();
                    listTickets.clear();
                    for (int i = 0; i < result.length(); i++) {
                        Ticket ticket = gson.fromJson(result.getJSONObject(i).toString(), Ticket.class);
                        listTickets.add(ticket);
                    }
                    ticketAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCallError(VolleyError error) {
                    Log.d("ListTicketFragment", "requestServer 42 Error: " + error);
                    error.printStackTrace();
                }
            }, Request.Method.GET, url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
