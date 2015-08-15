package com.eip.projecthandler.fragments;

import android.accounts.NetworkErrorException;
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
import com.eip.projecthandler.Adapter.CustomAdapterProject;
import com.eip.projecthandler.R;
import com.eip.projecthandler.constants.ApiRoutes;
import com.eip.projecthandler.constants.AuthenticatorConstants;
import com.eip.projecthandler.helpers.account.AccountAuthenticator;
import com.eip.projecthandler.helpers.account.AccountHelper;
import com.eip.projecthandler.helpers.api.NetworkHelper;
import com.eip.projecthandler.listeners.ArrayNetworkListener;
import com.eip.projecthandler.models.Account;
import com.eip.projecthandler.models.Project;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ListProjectFragment extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment {

    private List<Project> listProject;
    private CustomAdapterProject projectAdapter;

    @Override
    public ListAdapter getListAdapter() {
        listProject = new ArrayList<Project>();
        projectAdapter = new CustomAdapterProject(getActivity(), listProject);
        mListView.setAdapter(projectAdapter);
        getListOfProject();
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
        return new int[]{R.color.color_primary};
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //mAdapter.notifyDataSetChanged();
                setRefreshing(false);
            }
        }, 2000);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Project project = (Project) adapterView.getItemAtPosition(position);
        ProjectFragment projectFragment = new ProjectFragment();
        projectFragment.setProject(project);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(this);
        fragmentTransaction.add(R.id.fragment_container, projectFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        return false;
    }

    private void getListOfProject() {
        try {
            String token = null;
            AccountAuthenticator aAuth = new AccountAuthenticator(getActivity());
            Account acc = AccountHelper.getAccount(getActivity());
            try {
                Bundle bundle = aAuth.getAuthToken(null, acc, AuthenticatorConstants.AUTH_TOKEN_TYPE, null);
                token = (String) bundle.get("authtoken");
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }

            NetworkHelper networkHelper = NetworkHelper.getInstance(getActivity());
            networkHelper.setAuthToken(token);
            networkHelper.arrayRequestServer(new ArrayNetworkListener() {

                @Override
                public void onCallSuccess(JSONArray result) throws JSONException {
                    //Log.d("ListProjectFragment", "requestServer success, result: " + result.toString());
                    Gson gson = new Gson();
                    listProject.clear();
                    for (int i = 0; i < result.length(); i++) {
                        Project project = gson.fromJson(result.getJSONObject(i).toString(), Project.class);
                        listProject.add(project);
                    }
                    projectAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCallError(VolleyError error) {
                    Log.d("ListProjectFragment", "requestServer Error: " + error);
                    error.printStackTrace();
                }
            }, Request.Method.GET, ApiRoutes.PROJECT_GET_BY_USER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}