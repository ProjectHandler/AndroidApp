package com.eip.projecthandler.fragments;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.eip.projecthandler.Adapter.CustomAdapterTicket;
import com.eip.projecthandler.R;
import com.eip.projecthandler.models.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketsFragment  extends com.blunderer.materialdesignlibrary.fragments.ListViewFragment {

    private List<Ticket> tickets;
    private CustomAdapterTicket adapter;

    @Override
    public ListAdapter getListAdapter() {
        tickets = new ArrayList<Ticket>();
        adapter = new CustomAdapterTicket(getActivity(), tickets);
        mListView.setAdapter(adapter);

        //getProject();
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
        /*Project p = (Project) adapterView.getItemAtPosition(position);
        Log.d("ProjectsFragment", "click item " + p.getName());
        //open project
        ProjectFragment pf = new ProjectFragment();
        pf.setProject(p);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(this);
        fragmentTransaction.add(R.id.fragment_container, pf);
        fragmentTransaction.commit();*/
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
        return false;
    }

    /*private void getProject() {
        try {
            String token = null;
            AccountAuthenticator aAuth = new AccountAuthenticator(getActivity());
            Account acc = AccountHelper.getAccount(getActivity());
            try {
                Bundle b = aAuth.getAuthToken(null, acc, AuthenticatorConstants.AUTH_TOKEN_TYPE, null);
                token = (String) b.get("authtoken");
                Log.d("ProjectsFragment", "requestServer success, token: " + token);
            } catch (NetworkErrorException e) {
                e.printStackTrace();
            }

            NetworkHelper n = NetworkHelper.getInstance(getActivity());
            n.setAuthToken(token);
            n.arrayRequestServer(new ArrayNetworkListener() {

                @Override
                public void onCallSuccess(JSONArray result) throws JSONException {
                    Log.d("ProjectsFragment", "requestServer success, result: " + result.toString());
                    Gson gson = new Gson();
                    projects.clear();
                    for (int i = 0; i < result.length(); i++) {
                        Project p = gson.fromJson(result.getJSONObject(i).toString(), Project.class);
                        projects.add(p);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCallError(VolleyError error) {
                    Log.d("ProjectsFragment", "requestServer Error: " + error);
                    error.printStackTrace();
                }
            }, Request.Method.GET, ApiRoutes.PROJECT_GET);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
