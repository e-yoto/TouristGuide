package pie.edu.touristguide.View.Contact;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import pie.edu.touristguide.Controller.Database.ContactController;
import pie.edu.touristguide.Model.Contact;
import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.FragmentNavigationUtil;

/**
 * @author BoTao Yu
 * Display Contacts in a recyclerview.
 */
public class ContactFragment extends Fragment implements View.OnClickListener, CRUDListener {


    private List<Contact> contacts;
    private ContactController contactController;
    private RecyclerView recyclerView;
    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        contacts = new ArrayList<>();


        recyclerView = rootView.findViewById(R.id.rv_contact);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //populate RecyclerView with the dummy data
        recyclerView.setAdapter(new ContactRvAdapter(contacts, getActivity()));

        //open Add Fragment
        FloatingActionButton addContactFab = rootView.findViewById(R.id.fab_add_contact);
        addContactFab.setOnClickListener(this);

        contactController = new ContactController(getActivity());





    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerView();
    }

    private void updateRecyclerView(){
        contacts = contactController.getAllContacts();
        ContactRvAdapter adapter = new ContactRvAdapter(contacts, getActivity());
        recyclerView.setAdapter(adapter);
    }
    /**
     * Do different operations depending on the view's ID.
     * @param view that is clicked
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_add_contact:
                FragmentNavigationUtil.replaceFragment(getActivity(), new AddFragment());
                break;
        }
    }

    public void deleteContact(int oldPhoneNumber){
        contactController.deleteContact(oldPhoneNumber);
        this.updateRecyclerView();
    }

}
