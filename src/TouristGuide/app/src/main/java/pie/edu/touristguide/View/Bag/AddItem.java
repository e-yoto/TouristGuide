package pie.edu.touristguide.View.Bag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import pie.edu.touristguide.Controller.Database.BagController;
import pie.edu.touristguide.Model.Item;
import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.FragmentNavigationUtil;

/**
 * @author Emmanuel
 * Fragment where the user enters information on the item to add to the Bag.
 */
public class AddItem extends Fragment implements View.OnClickListener{
    private View rootView;
    private EditText itemNameET;
    private EditText weightET;
    private CheckBox isLiquidCB;
    private Item newItem;
    private BagController bagController;
    Item toEdit;
    List<Item> items;
    ItemRvAdapter adapter;

    public AddItem() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AddItem(ItemRvAdapter adapter){
        this.adapter = adapter;
    }

    /**
     * Set the listeners for the Cancel and Add buttons.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.bag_add_layout, container, false);

        ImageView cancelIv = rootView.findViewById(R.id.iv_cancel);
        ImageView addIv = rootView.findViewById(R.id.iv_add);

        itemNameET = rootView.findViewById(R.id.et_title);
        weightET = rootView.findViewById(R.id.et_weight);
        isLiquidCB = rootView.findViewById(R.id.cb_liquid);

        setData();

        cancelIv.setOnClickListener(this);
        addIv.setOnClickListener(this);

        bagController = new BagController(getContext());
        items = bagController.getAllItems();

        return rootView;
    }

    private void setData() {
        Bundle args = getArguments();

        if (args != null){
            toEdit = args.getParcelable("ITEM_EDIT");
            if (toEdit != null){
                itemNameET.setText(toEdit.getTitle());
                weightET.setText(String.valueOf(toEdit.getWeight()));
                if (toEdit.getIsLiquid() == 1){
                    isLiquidCB.setChecked(true);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolBar(R.id.save_data_toolbar);
    }

    @Override
    public void onPause() {
        super.onPause();
        setToolBar(R.id.main_toolbar);
    }


    /**
     * Set the toolbar that will appear at the top of the Activity
     */
    public void setToolBar(int toolbarId){
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        switch (toolbarId){
            case R.id.main_toolbar:
                drawerLayout.findViewById(R.id.main_toolbar).setVisibility(View.VISIBLE);
                drawerLayout.findViewById(R.id.save_data_toolbar).setVisibility(View.GONE);
                break;
            case R.id.save_data_toolbar:
                drawerLayout.findViewById(R.id.main_toolbar).setVisibility(View.GONE);
                drawerLayout.findViewById(R.id.save_data_toolbar).setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * Cancels or confirm the addition of an item.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cancel:
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_LONG).show();
                FragmentNavigationUtil.replaceFragment(getActivity(), new ItemHomeFragment());
                break;
            case R.id.iv_add:
                //Make sure that the required fields were filled
                try {

                    if (toEdit != null){
                        updateItem();
                        FragmentNavigationUtil.replaceFragment(getActivity(), new ItemHomeFragment());
                    }

                    else {
                        Bundle args = getArguments();
                        String type = args.getString("PAGE_TITLE");

                        String itemName = itemNameET.getText().toString();
                        double weight = Double.parseDouble(weightET.getText().toString());
                        int isLiquid = 0;

                        if (isLiquidCB.isChecked())
                            isLiquid = 1;

                        newItem = new Item(itemName, weight, isLiquid, type);
                        Log.v("createdItem", newItem.toString());
                        bagController.createItem(newItem);
                        Toast.makeText(getActivity(), "Bag Item added", Toast.LENGTH_LONG).show();


                        FragmentNavigationUtil.replaceFragment(getActivity(), new ItemHomeFragment());
                    }

                }

                 catch (Exception e) {
                     Toast.makeText(getContext(), "Please enter all required information", Toast.LENGTH_LONG).show();
                     return;
                 }

                break;

        }

    }

    public void updateItem(){
        Item updateItem = new Item();
        try {
            Bundle args = getArguments();

            String name = itemNameET.getText().toString();
            Double weight = Double.valueOf(weightET.getText().toString());
            int isLiquid = 0;
            if (isLiquidCB.isChecked())
                isLiquid = 1;
            String type = args.getString("PAGE_TITLE");

            updateItem.setTitle(name);
            updateItem.setWeight(weight);
            updateItem.setIsLiquid(isLiquid);
            updateItem.setType(type);
            bagController.updateItem(toEdit, updateItem);

        } catch (Exception e){
            Log.e("ERRORUPDATINGITEM", "could not update activity");
        }
        this.bagController.updateItem(toEdit, updateItem);

    }

}