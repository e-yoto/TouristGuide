package pie.edu.touristguide.View.Bag;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pie.edu.touristguide.Controller.Database.BagController;
import pie.edu.touristguide.Model.Item;
import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.FragmentNavigationUtil;

/**
 * @author Emmanuel
 * Fragment for the items
 */
public class ItemHomeFragment extends Fragment implements View.OnClickListener {
    private List<Item> items;
    public ItemRvAdapter adapter;
    private int position = 0;
    private RecyclerView recyclerView;
    BagController bagController;
    String  bagType = "Carry On";
    TextView title, totalWeight;
    private View view;



    public ItemHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Create the list of items, instantiate the RecyclerView that will display the items
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.bag_home_layout, container, false);

        title = view.findViewById(R.id.tv_bag_title);
        totalWeight = view.findViewById(R.id.tv_total_weight);


        title.setText(bagType);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bagController = new BagController(getContext());
        items = bagController.getTypeItems(bagType);
        recyclerView = view.findViewById(R.id.rv_bag);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ItemRvAdapter(items, getContext());
        recyclerView.setAdapter(adapter);

        totalWeight.setText(String.valueOf(bagController.getWeight(bagType)));

        FloatingActionButton addFAB = view.findViewById(R.id.fab_add);
        addFAB.setOnClickListener(this);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.bag_options_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.option_carry_on){
            bagType = "Carry On";
            totalWeight.setText(String.valueOf(bagController.getWeight(bagType)));
        }

        else if (item.getItemId() == R.id.option_check_in){
            bagType = "Check in";
            totalWeight.setText(String.valueOf(bagController.getWeight(bagType)));
        }

        title.setText(bagType);

        updateRecyclerView();

        return true;
    }

    /**
     * When the Add button is clicked, open the Fragment that will allow the user to add an item to
     * the Bag.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        Fragment fragment;

        switch (id){
            case R.id.fab_add:
                fragment = new AddItem(adapter);
                Bundle bundle = new Bundle();
                bundle.putString("PAGE_TITLE",bagType);

                Log.v("bagType", bagType);

                fragment.setArguments(bundle);
                FragmentNavigationUtil.replaceFragment(getActivity(), fragment);
                break;
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerView();
    }

    private void updateRecyclerView(){
        items = bagController.getTypeItems(bagType);
        adapter = new ItemRvAdapter(items, getActivity());
        recyclerView.setAdapter(adapter);
        totalWeight.setText(String.valueOf(bagController.getWeight(bagType)));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public ItemRvAdapter getAdapter() {
        return adapter;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int index = item.getOrder();
        String command = item.getTitle().toString();
        Item toCRUD = items.get(index);
        switch (command){
            case "Edit":
                Bundle bundle = new Bundle();
                bundle.putParcelable("ITEM_EDIT", toCRUD);
                bundle.putString("PAGE_TITLE", bagType);
                AddItem fragment = new AddItem();
                fragment.setArguments(bundle);
                FragmentNavigationUtil.replaceFragment(getActivity(), fragment);

                break;
            case "Delete":
                items.remove(toCRUD);
                bagController.deleteItem(toCRUD);
                adapter.notifyItemRemoved(index);

                break;
        }

        return super.onContextItemSelected(item);
    }

    public void setBagType(String type){
        bagType = type;
    }
}
