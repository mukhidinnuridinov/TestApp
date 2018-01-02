package app.nmn.com.testapplication.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.nmn.com.testapplication.R;
import app.nmn.com.testapplication.bean.Employment;
import app.nmn.com.testapplication.bean.Person;
import app.nmn.com.testapplication.fragment.PersonDetailsFragment;
import app.nmn.com.testapplication.fragment.PersonListFragment;
import app.nmn.com.testapplication.helper.VolleyMultipartRequest;
import app.nmn.com.testapplication.helper.VolleySingleton;
import app.nmn.com.testapplication.rest.API;

public class MainActivity extends AppCompatActivity {

    public List<Person> personList = new ArrayList<>();
    private ProgressBar progressBar;
    private FragmentManager fragmentManager;
    private PersonListFragment personListFragment = new PersonListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewsInit();

        getPersonList();

        viewOnClick();

    }

    private void viewOnClick() {


    }

    private void getPersonList() {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.GET, API.PERSONLIST, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {

                String resString = new String(response.data);

                Log.i("RESPONSE", resString);

                try {
                    JSONArray jsonArray = new JSONArray(resString);

                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject personObject = jsonArray.getJSONObject(i);
                        Person person = new Person();

                        person.setId(personObject.getString("id"));
                        person.setFirstName(personObject.getString("first_name"));
                        person.setLastName(personObject.getString("last_name"));
                        person.setEmail(personObject.getString("email"));
                        person.setGender(personObject.getString("gender"));
                        person.setIpAddress(personObject.getString("ip_address"));
                        person.setImage(personObject.getString("photo"));

                        Employment employment = new Employment();
                        JSONObject employmentObject = personObject.getJSONObject("employment");
                        employment.setName(employmentObject.getString("name"));
                        employment.setPosition(employmentObject.getString("position"));

                        person.setEmployment(employment);
                        personList.add(person);

                    }

                    progressBar.setVisibility(View.GONE);
                    personListFragmentInit();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        API.defaultPolicy(volleyMultipartRequest);

        VolleySingleton.getInstance(this).addToRequestQueue(volleyMultipartRequest);
    }

    private void viewsInit() {

        progressBar = findViewById(R.id.progress_bar_main_activity);
        fragmentManager = getSupportFragmentManager();

    }

    private void personListFragmentInit(){

        personListFragment.mainActivity = this;
        personListFragment.personList = personList;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_main_activity, personListFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void personDetailsFragmentInit(int position){

        PersonDetailsFragment personDetailsFragment = new PersonDetailsFragment();
        personDetailsFragment.mainActivity = this;
        personDetailsFragment.person = personList.get(position);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main_activity, personDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}

