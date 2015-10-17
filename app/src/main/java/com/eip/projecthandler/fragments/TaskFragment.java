package com.eip.projecthandler.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
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
    private Button btn_tickets;
    private Button btn_my_tickets;

    public static final TaskFragment newInstance(Task task) {
        TaskFragment f = new TaskFragment();
        Bundle bdl = new Bundle();
        f.setTask(task);
        f.setArguments(bdl);
        return f;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

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

            if (task.getLevel() > 1) {
                Log.d("TaskFragment", "task.getSubTask(): " + task.getSubTask());
                lv_mainlist = (ListView) view.findViewById(R.id.listView_subTasks);
                subTaskAdapter = new CustomAdapterSubTask(getActivity(), task.getSubTask());
                lv_mainlist.setAdapter(subTaskAdapter);
            } else {
                LinearLayout entry = (LinearLayout) view.findViewById(R.id.subTaskLayout);
                ((ViewManager) entry.getParent()).removeView(entry);
            }
        }

        return view;
    }

    private void loadTickets(Boolean onlyUserTickets) {
        ListTicketFragment listTicketFragment = ListTicketFragment.newInstance(task.getId(), onlyUserTickets);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(this);
        fragmentTransaction.replace(R.id.fragment_container, listTicketFragment);
        fragmentTransaction.addToBackStack(this.toString());
        fragmentTransaction.commit();
    }
}
