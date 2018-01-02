package app.nmn.com.testapplication.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import app.nmn.com.testapplication.R;
import app.nmn.com.testapplication.bean.Person;
import app.nmn.com.testapplication.helper.CircleTransform;

/**
 * Created by User-PC on 1/2/2018.
 */

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.MyViewHolder> {

    private List<Person> personList;
    private Context context;

    public PersonListAdapter(List<Person> personList, Context context) {
        this.personList = personList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_person_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.nameView.setText(personList.get(position).getFirstName() + " " + personList.get(position).getLastName());
            holder.genderView.setText(personList.get(position).getGender());
            holder.emailView.setText(personList.get(position).getEmail());

            Glide.with(context).load(personList.get(position).getImage()).transform(new CircleTransform(context)).into(holder.imageView);
    }

    public Person getPerson(int position){
        return personList.get(position);
    }

    public List<Person> getAllPersons(){
        return personList;
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nameView, emailView, genderView;
        private ImageView imageView;

        public MyViewHolder(View view) {
            super(view);

            nameView = view.findViewById(R.id.name_person_list_adapter);
            emailView = view.findViewById(R.id.email_person_list_adapter);
            genderView = view.findViewById(R.id.gender_person_list_adapter);
            imageView = view.findViewById(R.id.image_person_list_adapter);
        }
    }
}
