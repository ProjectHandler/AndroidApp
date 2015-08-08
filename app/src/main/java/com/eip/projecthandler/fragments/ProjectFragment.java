package com.eip.projecthandler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eip.projecthandler.R;
import com.eip.projecthandler.models.Project;

public class ProjectFragment extends Fragment {

    private Project project;


   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_project, container, false);

       if (project !=null) {
           TextView tv1 = (TextView) view.findViewById(R.id.tv_project_name);
           tv1.setText(project.getName());

           TextView tv2 = (TextView) view.findViewById(R.id.tv_project_description);
           tv2.setText(project.getDescription());
       }

        return view;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
