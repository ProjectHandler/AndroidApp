package com.eip.projecthandler.fragments;

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
import com.eip.projecthandler.helpers.api.NetworkHelper;
import com.eip.projecthandler.listeners.ArrayNetworkListener;
import com.eip.projecthandler.models.Ticket;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListTicketFragment extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment {

    private Long projectId;
    private Boolean onlyUserTicket;
    private List<Ticket> listTickets;
    private CustomAdapterTicket ticketAdapter;

    public static final ListTicketFragment newInstance(Long projectId, Boolean onlyUserTicket) {
        ListTicketFragment f = new ListTicketFragment();
        Bundle bdl = new Bundle();
        f.setProjectId(projectId);
        f.setOnlyUserTicket(onlyUserTicket);
        f.setArguments(bdl);
        return f;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Boolean getOnlyUserTicket() {
        return onlyUserTicket;
    }

    public void setOnlyUserTicket(Boolean onlyUserTicket) {
        this.onlyUserTicket = onlyUserTicket;
    }

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
        TicketFragment ticketFragment = TicketFragment.newInstance((Ticket) adapterView.getItemAtPosition(position));

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(this);
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
            String url;
            if (projectId != null && this.onlyUserTicket)
                url = ApiRoutes.TICKET_GET_BY_PROJECT_AND_USER(this.getActivity(), this.projectId);
            else if (projectId != null && !this.onlyUserTicket)
                url = ApiRoutes.TICKET_GET_BY_PROJECT(this.getActivity(), this.projectId);
            else
                url = ApiRoutes.TICKET_GET_BY_USER(this.getActivity());

            NetworkHelper networkHelper = NetworkHelper.getInstance(getActivity());
            networkHelper.retrieveToken(getActivity());
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
            }, Request.Method.GET, url, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
