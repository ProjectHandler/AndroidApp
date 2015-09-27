package com.eip.projecthandler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eip.projecthandler.Adapter.CustomAdapterSubTask;
import com.eip.projecthandler.R;
import com.eip.projecthandler.models.Task;

public class TaskFragment extends Fragment {

    private Task task;
    private CustomAdapterSubTask subTaskAdapter;
    private ListView lv_mainlist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        if (task !=null) {
            TextView tv1 = (TextView) view.findViewById(R.id.tv_task_name);
            tv1.setText(task.getName());

            TextView tv2 = (TextView) view.findViewById(R.id.tv_task_description);
            tv2.setText(task.getDescription());

            ProgressBar pb_progress = (ProgressBar) view.findViewById(R.id.progressBar_task_progress);
            pb_progress.setProgress(Integer.parseInt(task.getProgress().toString()));

            Log.d("TaskFragment", "task.getMobileSubTaskDTO(): " + task.getMobileSubTaskDTO());
            lv_mainlist = (ListView) view.findViewById(R.id.listView_subTasks);
            subTaskAdapter = new CustomAdapterSubTask(getActivity(), task.getMobileSubTaskDTO());
            lv_mainlist.setAdapter(subTaskAdapter);
        }

        return view;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
