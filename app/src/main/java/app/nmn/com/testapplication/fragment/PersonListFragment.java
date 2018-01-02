package app.nmn.com.testapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import app.nmn.com.testapplication.R;
import app.nmn.com.testapplication.activity.MainActivity;
import app.nmn.com.testapplication.adapter.PersonListAdapter;
import app.nmn.com.testapplication.bean.Person;
import app.nmn.com.testapplication.helper.RecyclerTouchListener;

/**
 * Created by User-PC on 1/2/2018.
 */

public class PersonListFragment extends Fragment {

    private LinearLayout personListFragment;
    private RecyclerView recyclerView;
    public MainActivity mainActivity;
    public List<Person> personList;
    private SearchView searchView;
    private PersonListAdapter personListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        personListFragment = (LinearLayout) inflater.inflate(R.layout.fragment_person_list, container, false);

        viewsInit();

        dataInit();

        viewsOnClick();

        return personListFragment;
    }

    private void viewsOnClick() {

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mainActivity, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                int index = personList.indexOf(personListAdapter.getPerson(position));

                mainActivity.personDetailsFragmentInit(index);
            }

            @Override
            public void onLongClick(View view, int position) {

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
                        String name = person.getFirstName() + " " + person.getLastName();

                        if(name.toLowerCase().contains(newText.toLowerCase()))
                            tempPersonList.add(person);

                    }

                    personListAdapter = new PersonListAdapter(tempPersonList, mainActivity);
                    recyclerView.setAdapter(personListAdapter);
                }else {
                    personListAdapter = new PersonListAdapter(personList, mainActivity);
                    recyclerView.setAdapter(personListAdapter);
                }

                return false;
            }
        });

    }

    private void dataInit() {

        personListAdapter = new PersonListAdapter(personList, mainActivity);
        recyclerView.setAdapter(personListAdapter);
    }

    private void viewsInit() {

        searchView = personListFragment.findViewById(R.id.search_view_person_list_fragment);
        recyclerView = personListFragment.findViewById(R.id.recycler_view_person_list_fragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}
