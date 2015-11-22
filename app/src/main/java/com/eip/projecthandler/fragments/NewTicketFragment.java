package com.eip.projecthandler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.eip.projecthandler.Adapter.CustomAdapterData;
import com.eip.projecthandler.R;
import com.eip.projecthandler.constants.ApiRoutes;
import com.eip.projecthandler.helpers.api.NetworkHelper;
import com.eip.projecthandler.helpers.util;
import com.eip.projecthandler.listeners.ObejctNetworkListener;
import com.eip.projecthandler.models.Data;
import com.eip.projecthandler.models.Project;
import com.eip.projecthandler.models.Ticket;
import com.eip.projecthandler.models.TicketPriority;
import com.eip.projecthandler.models.TicketTracker;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewTicketFragment extends Fragment {

    private Set<Project> projects;
    private CustomAdapterData ticketTrackers;
    private CustomAdapterData ticketPriorities;
    private CustomAdapterData ticketProjects;
    private Spinner spPriority;
    private Spinner spProject;
    private Spinner spTracker;
    private EditText message;
    private EditText title;


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

        projects = new HashSet<Project>();

        message = (EditText) view.findViewById(R.id.message);
        title = (EditText) view.findViewById(R.id.tile);

        spPriority = (Spinner) view.findViewById(R.id.spinner_priority);
        ticketPriorities = new CustomAdapterData(this.getActivity().getBaseContext(), new ArrayList<Data>());
        spPriority.setAdapter(ticketPriorities);

        spProject = (Spinner) view.findViewById(R.id.spinner_project);
        ticketProjects = new CustomAdapterData(this.getActivity().getBaseContext(), new ArrayList<Data>());
        spProject.setAdapter(ticketProjects);

        spTracker = (Spinner) view.findViewById(R.id.spinner_tracker);
        ticketTrackers = new CustomAdapterData(this.getActivity().getBaseContext(), new ArrayList<Data>());
        spTracker.setAdapter(ticketTrackers);

        Button btn_create = (Button) view.findViewById(R.id.button_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateTicket()) {
                    Ticket newTicket = new Ticket();
                    TicketPriority ticketPriority = new TicketPriority();
                    ticketPriority.setId(((Data) spPriority.getSelectedItem()).getId());

                    TicketTracker ticketTracker = new TicketTracker();
                    ticketTracker.setId(((Data) spTracker.getSelectedItem()).getId());

                    newTicket.setProjectId(((Data) spProject.getSelectedItem()).getId());
                    newTicket.setTicketPriority(ticketPriority);
                    newTicket.setTicketTracker(ticketTracker);
                    newTicket.setTitle(title.getText().toString());
                    newTicket.setText(message.getText().toString());

                    saveNewTicket(newTicket);
                }
            }
        });

        loadDataForTicketCreation();

        return view;
    }

    private void loadDataForTicketCreation() {
        try {
            String url = ApiRoutes.TICKET_GET_DATA_FOR_NEW_TICKET(this.getActivity());
            NetworkHelper networkHelper = NetworkHelper.getInstance(getActivity().getApplicationContext());
            networkHelper.retrieveToken(getActivity().getApplicationContext());
            networkHelper.objectRequestServer(new ObejctNetworkListener() {
                @Override
                public void onCallSuccess(JSONObject result) throws JSONException {
                    Log.d("NewTicketFragment", "requestServer ok result: " + result.get("ticketPriorities"));

                    Gson gson = new Gson();
                    List<Data> list = new ArrayList<Data>();
                    JSONArray jsonTicketPriorities = (JSONArray) result.get("ticketPriorities");
                    for (int i = 0; i < jsonTicketPriorities.length(); i++) {
                        TicketPriority ticketPriority = gson.fromJson(jsonTicketPriorities.getJSONObject(i).toString(), TicketPriority.class);
                        list.add(new Data(ticketPriority.getId(), ticketPriority.getName()));
                    }
                    ticketPriorities.setData(list);
                    ticketPriorities.notifyDataSetChanged();

                    List<Data> listProject = new ArrayList<Data>();
                    JSONArray jsonTicketProjects = (JSONArray) result.get("projects");
                    for (int i = 0; i < jsonTicketProjects.length(); i++) {
                        Project project = gson.fromJson(jsonTicketProjects.getJSONObject(i).toString(), Project.class);
                        projects.add(project);
                        listProject.add(new Data(project.getId(), project.getName()));
                    }
                    ticketProjects.setData(listProject);
                    ticketProjects.notifyDataSetChanged();


                    List<Data> listTrackers = new ArrayList<Data>();
                    JSONArray jsonTicketTrackers = (JSONArray) result.get("ticketTrackers");
                    for (int i = 0; i < jsonTicketTrackers.length(); i++) {
                        TicketTracker tracker = gson.fromJson(jsonTicketTrackers.getJSONObject(i).toString(), TicketTracker.class);
                        listTrackers.add(new Data(tracker.getId(), tracker.getName()));
                    }
                    ticketTrackers.setData(listTrackers);
                    ticketTrackers.notifyDataSetChanged();

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

    private boolean validateTicket() {
        boolean isValid = true;

        TextView tv_title = (TextView) getView().findViewById(R.id.tv_tile);
        TextView tv_message = (TextView) getView().findViewById(R.id.tv_message);

        tv_title.setTextColor(getResources().getColor(R.color.carbon_black));
        tv_message.setTextColor(getResources().getColor(R.color.carbon_black));

        if (title.getText() == null || title.getText().toString().length() < 3) {
            isValid = false;
            tv_title.setTextColor(getResources().getColor(R.color.colorError));
        }
        if (message.getText() == null || message.getText().toString().length() < 3) {
            isValid = false;
            tv_message.setTextColor(getResources().getColor(R.color.colorError));
        }

        return isValid;
    }

    private void saveNewTicket(Ticket ticket) {
        try {
            String url = ApiRoutes.TICKET_SAVE_NEW_TICKET(this.getActivity());
            NetworkHelper networkHelper = NetworkHelper.getInstance(getActivity().getApplicationContext());
            networkHelper.retrieveToken(getActivity().getApplicationContext());
            JSONObject jsonObj = util.objectToJsonObject(ticket);

            Log.d("newTicketFragment", "jsonObj: " + jsonObj);

            networkHelper.objectRequestServer(new ObejctNetworkListener() {
                @Override
                public void onCallSuccess(JSONObject result) {
                    Log.d("newTicketFragment", "ticket: save success !" + result);

                    ListTicketFragment_v2 listTicketFragment = ListTicketFragment_v2.newInstance(null, true);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragmentTransaction.remove(newInstance());
                    fragmentTransaction.replace(R.id.fragment_container, listTicketFragment);
                    //fragmentTransaction.addToBackStack();
                    fragmentTransaction.commit();
                }

                @Override
                public void onCallError(VolleyError error) {
                    Log.d("newTicketFragment", "ticket: save error !" + error);
                    Toast.makeText(getActivity().getApplicationContext(), R.string.save_error, Toast.LENGTH_LONG).show();
                }
            }, Request.Method.POST, url, jsonObj);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
