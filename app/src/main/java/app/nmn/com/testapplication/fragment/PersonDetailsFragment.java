package app.nmn.com.testapplication.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.microedition.khronos.opengles.GL;

import app.nmn.com.testapplication.R;
import app.nmn.com.testapplication.activity.MainActivity;
import app.nmn.com.testapplication.bean.Person;

/**
 * Created by User-PC on 1/2/2018.
 */

public class PersonDetailsFragment extends Fragment {

    private LinearLayout personDetailsFragment;
    private ImageView imageView;
    private TextView nameView, emailView,
                     genderView, ipView,
                     organizationView, positionView;
    public Person person;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        personDetailsFragment = (LinearLayout) inflater.inflate(R.layout.fragment_person_details, container, false);

        person = (Person) getArguments().getSerializable("person");

        viewsInit();

        dataInit();

        return personDetailsFragment;
    }

    private void dataInit() {

        nameView.setText(person.getFirst_name() + " " + person.getLast_name());
        emailView.setText(person.getEmail());
        genderView.setText(person.getGender());
        ipView.setText(person.getIp_address());
        organizationView.setText(person.getEmployment().getName());
        positionView.setText(person.getEmployment().getPosition());

    }

    private void viewsInit() {

        imageView = personDetailsFragment.findViewById(R.id.image_person_details_fragment);
        nameView = personDetailsFragment.findViewById(R.id.name_person_details_fragment);
        emailView = personDetailsFragment.findViewById(R.id.email_person_details_fragment);
        genderView = personDetailsFragment.findViewById(R.id.gender_person_details_fragment);
        ipView = personDetailsFragment.findViewById(R.id.ip_address_person_details_fragment);
        organizationView = personDetailsFragment.findViewById(R.id.organization_person_details_fragment);
        positionView = personDetailsFragment.findViewById(R.id.position_person_details_fragment);
        Glide.with(getActivity().getApplicationContext()).load(person.getPhoto()).into(imageView);

        MainActivity mainActivity = (MainActivity) getActivity();
        Toolbar toolbar = personDetailsFragment.findViewById(R.id.toolbar_person_details_fragment);
        mainActivity.setSupportActionBar(toolbar);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


}
