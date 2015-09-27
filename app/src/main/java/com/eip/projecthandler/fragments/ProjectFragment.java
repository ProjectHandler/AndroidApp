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
    private  Button btn_tasks;
    private  Button btn_my_tasks;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_project, container, false);

       if (project !=null) {
           TextView tvProjectName           = (TextView) view.findViewById(R.id.tv_project_name);
           TextView tvProjectDescription    = (TextView) view.findViewById(R.id.tv_project_description);
           TextView tvDateBegin             = (TextView) view.findViewById(R.id.date_begin);
           TextView tvDateEnd               = (TextView) view.findViewById(R.id.date_end);
           ProgressBar pbDeadline           = (ProgressBar) view.findViewById(R.id.progressBar_project_deadline);
           ProgressBar pbProgress           = (ProgressBar) view.findViewById(R.id.progressBar_project_progress);

           tvProjectName.setText(project.getName());
           tvProjectDescription.setText(project.getDescription());
           tvDateBegin.setText(util.getDateString(getActivity().getApplicationContext(), project.getDateBegin()));
           tvDateEnd.setText(util.getDateString(getActivity().getApplicationContext(), project.getDateEnd()));

           pbDeadline.setProgress(project.getDateProgress());
           pbProgress.setProgress(project.getTasksProgress());

           btn_tasks = (Button) view.findViewById(R.id.tasks);
           btn_tasks.setOnClickListener(new View.OnClickListener() {
               public void onClick(View arg0) {
                   loadTask(false);
               }
           });

           btn_my_tasks = (Button) view.findViewById(R.id.my_tasks);
           btn_my_tasks.setOnClickListener(new View.OnClickListener() {
               public void onClick(View arg0) {
                   loadTask(true);
               }
           });

           //jour restants
           //nb tache
           //nb tickets

       }
       ((HomeActivity)getActivity()).getSupportActionBar().setTitle(project.getName().toString());

        return view;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    private void loadTask(Boolean onlyUserTask) {
        ListTaskFragment listTaskFragment = new ListTaskFragment();
        listTaskFragment.setOnlyUserTask(onlyUserTask);
        listTaskFragment.setProjectId(project.getId());


        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(this);
        //fragmentTransaction.add(R.id.fragment_container, listTaskFragment);
        //fragmentTransaction.commit();

        fragmentTransaction.replace(R.id.fragment_container, listTaskFragment);
        fragmentTransaction.addToBackStack(this.toString());
        fragmentTransaction.commit();
    }
}
