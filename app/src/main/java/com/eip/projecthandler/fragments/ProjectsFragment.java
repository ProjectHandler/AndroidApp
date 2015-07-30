package com.eip.projecthandler.fragments;

import android.support.v4.app.Fragment;

import com.eip.projecthandler.models.Project;

import java.util.List;

public class ProjectsFragment  extends Fragment {


        private List<Project> projects;

/*
        private void getProject() {
            try {
                //Gets an auth token of the specified type for a particular account, prompting the user for credentials if necessary.
                //URL url = new URL(ApiRoutes.SERVER_DEV+"/api/user/get/12?token=");
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
                //n.setAuthToken("402445ca-2650-46ee-be9d-a906dab09c63");
                n.setAuthToken(token);
                n.arrayRequestServer(new ArrayNetworkListener() {

                    @Override
                    public void onCallSuccess(JSONArray result) throws JSONException {
                        Log.d("ProjectsFragment", "requestServer success, result: " + result.toString());
                        Gson gson = new Gson();
                        projects = gson.fromJson(result.toString(), List.class);
                        projectAdapter.notifyDataSetChanged();
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
