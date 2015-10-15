package com.eip.projecthandler.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.eip.projecthandler.Adapter.CustomAdapterTask;
import com.eip.projecthandler.R;
import com.eip.projecthandler.constants.ApiRoutes;
import com.eip.projecthandler.helpers.api.NetworkHelper;
import com.eip.projecthandler.listeners.ArrayNetworkListener;
import com.eip.projecthandler.models.Task;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListTaskFragment extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment {

    private List<Task> listTasks;
    private CustomAdapterTask taskAdapter;
    private Long projectId;
    private Boolean onlyUserTask = false;

    public static final ListTaskFragment newInstance(Long projectId, Boolean onlyUserTask) {
        ListTaskFragment f = new ListTaskFragment();
        Bundle bdl = new Bundle();
        f.setProjectId(projectId);
        f.setOnlyUserTask(onlyUserTask);
        f.setArguments(bdl);
        return f;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Boolean getOnlyUserTask() {
        return onlyUserTask;
    }

    public void setOnlyUserTask(Boolean onlyUserTask) {
        this.onlyUserTask = onlyUserTask;
    }

    @Override
    public ListAdapter getListAdapter() {
        listTasks = new ArrayList<Task>();
        getListOfTask();
        taskAdapter = new CustomAdapterTask(getActivity(), listTasks);
        mListView.setAdapter(taskAdapter);

        Log.d("ListTaskFragment", "ListTaskFragment tasks !!!! projectid:" + projectId);

        return mListView.getAdapter();
     }

    @Override
    public boolean useCustomContentView() {
        return false;
    }

    @Override
    public int getCustomContentView() {
        return 0;
    }

    @Override
    public boolean pullToRefreshEnabled() {
        return true;
    }

    @Override
    public int[] getPullToRefreshColorResources() {
        return new int[]{R.color.mdl_color_primary};
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getListOfTask();
                setRefreshing(false);
           }
       }, 2000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TaskFragment taskFragment =TaskFragment.newInstance((Task) adapterView.getItemAtPosition(position));

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(this);
        fragmentTransaction.replace(R.id.fragment_container, taskFragment);
        fragmentTransaction.addToBackStack(this.toString());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        return false;
    }

    private void getListOfTask() {
        try {
            String url;
            if (this.projectId == null) {
                url = ApiRoutes.TASK_GET_ALL_BY_USER;
            } else {
                if (this.onlyUserTask)
                    url = ApiRoutes.TASK_GET_BY_PROJECT_AND_USER(this.projectId);
                else
                    url = ApiRoutes.TASK_GET_BY_PROJECT(this.projectId);
            }
            Log.d("ListTaskFragment", "url: " + url);
            NetworkHelper networkHelper = NetworkHelper.getInstance(getActivity());
            networkHelper.retrieveToken(getActivity());
            networkHelper.arrayRequestServer(new ArrayNetworkListener() {
                @Override
                public void onCallSuccess(JSONArray result) throws JSONException {
                    Log.d("ListTaskFragment", "requestServer ok result: " + result);
                    Gson gson = new Gson();
                    listTasks.clear();
                    for (int i = 0; i < result.length(); i++) {
                        Task task = gson.fromJson(result.getJSONObject(i).toString(), Task.class);
                        listTasks.add(task);
                    }
                    taskAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCallError(VolleyError error) {
                    Log.d("ListTaskFragment", "requestServer 42 Error: " + error);
                    error.printStackTrace();
                }
            }, Request.Method.GET, url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}