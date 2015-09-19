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
           TextView tv1 = (TextView) view.findViewById(R.id.tv_project_name);
           tv1.setText(project.getName());

           TextView tv2 = (TextView) view.findViewById(R.id.tv_project_description);
           tv2.setText(project.getDescription());

           ProgressBar pb_progress = (ProgressBar) view.findViewById(R.id.progressBar_project_progress);
           pb_progress.setProgress(Integer.parseInt(project.getProgress().toString()));

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

       }

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
        fragmentTransaction.add(R.id.fragment_container, listTaskFragment);
        fragmentTransaction.commit();
    }
}
