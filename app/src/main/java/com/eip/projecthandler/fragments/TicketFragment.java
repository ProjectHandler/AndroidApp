package com.eip.projecthandler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.eip.projecthandler.Adapter.TicketMessageAdapter;
import com.eip.projecthandler.R;
import com.eip.projecthandler.models.Ticket;

public class TicketFragment  extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        if (ticket != null) {
            TextView tv1 = (TextView) view.findViewById(R.id.tv_ticket_title);
            tv1.setText(ticket.getTitle());

            TextView tv2 = (TextView) view.findViewById(R.id.tv_ticket_text);
            tv2.setText(ticket.getText());

            Log.d("TaskFragment", "ticket.getTicketMessage(): " + ticket.getTicketMessage());
            lv_mainlist = (ListView) view.findViewById(R.id.listView_ticketMessage);
            ticketMessageAdapter = new TicketMessageAdapter(getActivity(), ticket.getTicketMessage());
            lv_mainlist.setAdapter(ticketMessageAdapter);

        }

        return view;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}
