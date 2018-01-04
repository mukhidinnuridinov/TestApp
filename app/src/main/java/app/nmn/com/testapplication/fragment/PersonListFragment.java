package app.nmn.com.testapplication.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.nmn.com.testapplication.R;
import app.nmn.com.testapplication.activity.MainActivity;
import app.nmn.com.testapplication.adapter.PersonListAdapter;
import app.nmn.com.testapplication.bean.Person;
import app.nmn.com.testapplication.helper.RecyclerTouchListener;
import app.nmn.com.testapplication.helper.VolleyMultipartRequest;
import app.nmn.com.testapplication.helper.VolleySingleton;
import app.nmn.com.testapplication.rest.API;

/**
 * Created by User-PC on 1/2/2018.
 */

public class PersonListFragment extends Fragment {

    private LinearLayout personListFragment;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private PersonListAdapter personListAdapter;
    public List<Person> personList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("FRAG", "1");

        getPersonList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        personListFragment = (LinearLayout) inflater.inflate(R.layout.fragment_person_list, container, false);

        Log.i("FRAG", "2");

        viewsInit();

        viewsOnClick();

        return personListFragment;
    }

    private void viewsOnClick() {

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                int index = personList.indexOf(personListAdapter.getPerson(position));

                personDetailsFragmentInit(index);
            }

            @Override
            public void onLongClick(View view, int position) {
                removePerson(position);
            }
        }));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText.length() > 2){
                    List<Person> tempPersonList = new ArrayList<>();

                    for (Person person : personList){
                        String name = person.getFirst_name() + " " + person.getLast_name();

                        if(name.toLowerCase().contains(newText.toLowerCase()))
                            tempPersonList.add(person);

                    }

                    personListAdapter = new PersonListAdapter(tempPersonList, getActivity().getApplicationContext());
                    recyclerView.setAdapter(personListAdapter);
                }else {
                    personListAdapter = new PersonListAdapter(personList, getActivity().getApplicationContext());
                    recyclerView.setAdapter(personListAdapter);
                }

                return false;
            }
        });

    }

    private void removePerson(final int position){

        new AlertDialog.Builder(getActivity().getApplicationContext())
                .setMessage("Remove " +
                        personListAdapter.getAllPersons().get(position).getFirst_name() + " " +
                        personListAdapter.getAllPersons().get(position).getLast_name() + "?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int index = personList.indexOf(personListAdapter.getPerson(position));
                        personList.remove(index);
                        personListAdapter.getAllPersons().remove(position);
                        personListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void dataInit() {

        personListAdapter = new PersonListAdapter(personList, getActivity().getApplicationContext());
        recyclerView.setAdapter(personListAdapter);
    }

    private void viewsInit() {

        searchView = personListFragment.findViewById(R.id.search_view_person_list_fragment);
        recyclerView = personListFragment.findViewById(R.id.recycler_view_person_list_fragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void getPersonList() {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, API.PERSONLIST, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                String resString = new String(response.data);

                Log.i("RESPONSE", resString);

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Person>>(){}.getType();
                personList = gson.fromJson(resString, listType);

                dataInit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        API.defaultPolicy(volleyMultipartRequest);

        VolleySingleton.getInstance(getActivity().getBaseContext()).addToRequestQueue(volleyMultipartRequest);
    }

    private void personDetailsFragmentInit(int position){

        Bundle bundle = new Bundle();
        bundle.putSerializable("person", personList.get(position));

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        PersonDetailsFragment personDetailsFragment = new PersonDetailsFragment();
        personDetailsFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main_activity, personDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }

}
