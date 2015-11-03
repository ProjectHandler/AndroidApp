package com.eip.projecthandler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.eip.projecthandler.Adapter.TicketMessageAdapter;
import com.eip.projecthandler.R;
import com.eip.projecthandler.constants.ApiRoutes;
import com.eip.projecthandler.helpers.api.NetworkHelper;
import com.eip.projecthandler.helpers.util;
import com.eip.projecthandler.listeners.ObejctNetworkListener;
import com.eip.projecthandler.models.Ticket;
import com.eip.projecthandler.models.TicketMessage;
import com.google.gson.Gson;

import org.json.JSONObject;

public class TicketFragment extends Fragment {

    private Ticket ticket;
    private TicketMessageAdapter ticketMessageAdapter;
    private ListView lv_mainlist;


    public static final TicketFragment newInstance(Ticket ticket) {
        TicketFragment f = new TicketFragment();
        Bundle bdl = new Bundle();
        f.setTicket(ticket);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        if (ticket != null) {
            TextView tv1 = (TextView) view.findViewById(R.id.tv_ticket_title);
            tv1.setText(ticket.getTitle());

            TextView tv2 = (TextView) view.findViewById(R.id.tv_ticket_text);
            tv2.setText(ticket.getText());

            Log.d("TaskFragment", "ticket.getTicketMessage(): " + ticket.getTicketMessage());
            this.lv_mainlist = (ListView) view.findViewById(R.id.listView_ticketMessage);
            this.ticketMessageAdapter = new TicketMessageAdapter(getActivity(), ticket.getTicketMessage());
            this.lv_mainlist.setAdapter(  this.ticketMessageAdapter);

            this.lv_mainlist.clearFocus();
            this.lv_mainlist.setSelection(this.lv_mainlist.getAdapter().getCount() - 1);

            ImageButton send = (ImageButton) view.findViewById(R.id.buttonSend);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   EditText msg = (EditText) view.findViewById(R.id.messageTextToSend);
                    addNewMessage(msg.getText().toString());
                    msg.setText("");
                }
            });

        }

        return view;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    private void addNewMessage(String msg) {
        TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setText(msg);
        saveTicketMessage(ticketMessage);
    }

    private void saveTicketMessage(TicketMessage ticketMessage) {
        try {
            String url = ApiRoutes.TICKET_SAVE_NEW_TICKET_MESSAGE(this.ticket.getId());
            NetworkHelper networkHelper = NetworkHelper.getInstance(getActivity().getApplicationContext());
            networkHelper.retrieveToken(getActivity().getApplicationContext());
            networkHelper.objectRequestServer(new ObejctNetworkListener() {
                @Override
                public void onCallSuccess(JSONObject result) {
                    Log.d("TicketFragment", "ticket: save success !" + result);
                    Gson gson = new Gson();
                    TicketMessage ticketMessage = gson.fromJson(result.toString(), TicketMessage.class);
                    updateListView(ticketMessage);
                }

                @Override
                public void onCallError(VolleyError error) {
                    Toast.makeText(getActivity().getApplicationContext(), R.string.save_error, Toast.LENGTH_LONG).show();
                }
            }, Request.Method.POST, url, util.objectToJsonObject(ticketMessage));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateListView(TicketMessage ticketMessage) {
        this.ticket.addTicketMessage(ticketMessage);
        this.lv_mainlist.clearFocus();
        this.lv_mainlist.setSelection( this.lv_mainlist.getAdapter().getCount() - 1);
    }
}
