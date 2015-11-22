package com.eip.projecthandler.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

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

public class ListTicketFragment_v2  extends Fragment {

    private Long projectId;
    private Boolean onlyUserTicket;
    private List<Ticket> tickets;
    private CustomAdapterTicket ticketAdapter;
    private ListView lv_tickets;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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

    public static final ListTicketFragment_v2 newInstance(Long projectId, Boolean onlyUserTicket) {
        ListTicketFragment_v2 f = new ListTicketFragment_v2();
        Bundle bdl = new Bundle();
        f.setProjectId(projectId);
        f.setOnlyUserTicket(onlyUserTicket);
        f.setArguments(bdl);
        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_list_ticket, container, false);
        tickets = new ArrayList<Ticket>();

        lv_tickets = (ListView) view.findViewById(R.id.listViewTickets);
        ticketAdapter = new CustomAdapterTicket(getActivity(), tickets);
        lv_tickets.setAdapter(ticketAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_listview_refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        getListOfTicket();
                        setRefreshing(false);
                    }
                }, 2000);
            }
        });

        lv_tickets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                openExistingTicket(adapterView, position);
            }
        });

        ImageButton addTicket = (ImageButton) view.findViewById(R.id.btn_add_new_ticket);
        addTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ListTicketFragment_v2", "ListTicketFragment_v2 add ticket! ");
                NewTicketFragment newTicketFragment = NewTicketFragment.newInstance();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container, newTicketFragment);
                fragmentTransaction.addToBackStack(this.toString());
                fragmentTransaction.commit();
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(new int[]{R.color.mdl_color_primary});

        getListOfTicket();

        return view;
    }

    public void setRefreshing(boolean refreshing) {
        if (mSwipeRefreshLayout != null) mSwipeRefreshLayout.setRefreshing(refreshing);
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
                    tickets.clear();
                    for (int i = 0; i < result.length(); i++) {
                        Ticket ticket = gson.fromJson(result.getJSONObject(i).toString(), Ticket.class);
                        tickets.add(ticket);
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

    private void openExistingTicket(AdapterView<?> adapterView, int position) {
        TicketFragment ticketFragment = TicketFragment.newInstance((Ticket) adapterView.getItemAtPosition(position));

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(this);
        fragmentTransaction.replace(R.id.fragment_container, ticketFragment);
        fragmentTransaction.addToBackStack(this.toString());
        fragmentTransaction.commit();
    }

}
