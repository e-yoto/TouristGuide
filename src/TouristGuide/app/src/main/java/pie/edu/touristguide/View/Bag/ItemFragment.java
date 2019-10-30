package pie.edu.touristguide.View.Bag;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pie.edu.touristguide.Controller.Database.BagController;
import pie.edu.touristguide.Model.Item;
import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.FragmentNavigationUtil;
import pie.edu.touristguide.View.HomeFragment;

/**
 * @author Emmanuel
 * Fragment for the items
 */
//public class ItemFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
public class ItemFragment extends Fragment {
    private List<Item> items;
    public ItemRvAdapter adapter;
    private int position = 0;
    private RecyclerView recyclerView;
    BagController bagController;
    Context context;
    String pageTitle;
    private View view;

    TabLayout tabLayout;
    ViewPager viewPager;

    public ItemFragment() {
        // Required empty public constructor
    }

    //THIS FRAGMENT HAS BEEN REPLACED BY ITEMHOMEFRAGMENT

}
