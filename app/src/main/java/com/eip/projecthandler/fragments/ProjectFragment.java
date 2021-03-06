package com.eip.projecthandler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eip.projecthandler.R;
import com.eip.projecthandler.activities.HomeActivity;
import com.eip.projecthandler.helpers.util;
import com.eip.projecthandler.models.Project;

public class ProjectFragment extends Fragment {

    private Project project;
    private Button btn_tasks;
    private Button btn_my_tasks;
    private Button btn_tickets;
    private Button btn_my_tickets;


    public static final ProjectFragment newInstance(Project project) {
        ProjectFragment f = new ProjectFragment();
        Bundle bdl = new Bundle();
        f.setProject(project);
        f.setArguments(bdl);
        return f;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_project, container, false);

        if (project != null) {
            TextView tvProjectName = (TextView) view.findViewById(R.id.tv_project_name);
            TextView tvProjectDescription = (TextView) view.findViewById(R.id.tv_project_description);
            TextView tvDateBegin = (TextView) view.findViewById(R.id.date_begin);
            TextView tvDateEnd = (TextView) view.findViewById(R.id.date_end);
            ProgressBar pbDeadline = (ProgressBar) view.findViewById(R.id.progressBar_project_deadline);
            ProgressBar pbProgress = (ProgressBar) view.findViewById(R.id.progressBar_project_progress);

            tvProjectName.setText(project.getName());
            tvProjectDescription.setText(project.getDescription());
            tvDateBegin.setText(util.getDateString(getActivity().getApplicationContext(), project.getDateBegin()));
            tvDateEnd.setText(util.getDateString(getActivity().getApplicationContext(), project.getDateEnd()));

            pbDeadline.setProgress(project.getDateProgress());
            pbProgress.setProgress(project.getTasksProgress());

            btn_tasks = (Button) view.findViewById(R.id.tasks);
            btn_tasks.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    loadTasks(false);
                }
            });

            btn_my_tasks = (Button) view.findViewById(R.id.my_tasks);
            btn_my_tasks.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    loadTasks(true);
                }
            });

            btn_tickets = (Button) view.findViewById(R.id.tickets);
            btn_tickets.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    loadTickets(false);
                }
            });

            btn_my_tickets = (Button) view.findViewById(R.id.my_tickets);
            btn_my_tickets.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    loadTickets(true);
                }
            });

            ((HomeActivity) getActivity()).getSupportActionBar().setTitle(project.getName().toString());
        }


        return view;
    }

    private void loadTasks(Boolean onlyUserTasks) {
        ListTaskFragment listTaskFragment = ListTaskFragment.newInstance(project.getId(), onlyUserTasks);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(this);
        fragmentTransaction.replace(R.id.fragment_container, listTaskFragment);
        fragmentTransaction.addToBackStack(this.toString());
        fragmentTransaction.commit();
    }

    private void loadTickets(Boolean onlyUserTickets) {
        ListTicketFragment listTicketFragment = ListTicketFragment.newInstance(project.getId(), onlyUserTickets);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(this);
        fragmentTransaction.replace(R.id.fragment_container, listTicketFragment);
        fragmentTransaction.addToBackStack(this.toString());
        fragmentTransaction.commit();
    }
}
