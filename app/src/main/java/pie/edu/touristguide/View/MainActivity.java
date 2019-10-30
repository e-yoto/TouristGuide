package pie.edu.touristguide.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import pie.edu.touristguide.R;
import pie.edu.touristguide.View.Bag.ItemHomeFragment;
import pie.edu.touristguide.View.Bag.ItemPageAdapter;
import pie.edu.touristguide.View.Contact.ContactFragment;
import pie.edu.touristguide.View.CurrencyConversion.CurrencyConversionFragment;
import pie.edu.touristguide.View.Fraud.FraudPageAdapter;
import pie.edu.touristguide.View.Fraud.FraudPreventionAddressFragment;
import pie.edu.touristguide.View.Memories.MemoriesMenu.MemoriesMenuFragment;
import pie.edu.touristguide.View.PublicHolidays.PublicHolidaysFragment;
import pie.edu.touristguide.View.Reminder.ReminderFragment;
import pie.edu.touristguide.View.Reminder.ReminderPageAdapter;
import pie.edu.touristguide.View.Translation.TranslationFragment;
import pie.edu.touristguide.View.Translation.TranslationWebViewFragment;
import pie.edu.touristguide.View.Weather.WeatherFragment;


//TODO: animation for ReminderFragment does not work
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout navDrawer;
    private NavigationView navigationView;
    private TabLayout tabLayout;
    private TabLayout itemTabLayout;
    private TabLayout fraudTabLayout;
    private ViewPager viewPager;
    private ViewPager itemViewPager;
    private ViewPager fraudViewPager;

    public static Menu optionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //View setups
        viewSetUp();
//        checkCameraPermission();


        if(savedInstanceState == null){
            tabLayout.setVisibility(View.GONE);
            itemTabLayout.setVisibility(View.GONE);
            fraudTabLayout.setVisibility(View.GONE);
            viewPager.setVisibility(View.GONE);
            itemViewPager.setVisibility(View.GONE);
            fraudViewPager.setVisibility(View.GONE);
            addToBackStack(new HomeFragment());
            navigationView.setCheckedItem(R.id.home);
        }




    }



    private void viewSetUp(){
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        navDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, navDrawer, toolbar,
                R.string.open_drawer, R.string.close_drawer);

        navDrawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        itemViewPager = (ViewPager) findViewById(R.id.pager_item);
        ItemPageAdapter myItemPagerAdapter = new ItemPageAdapter(getSupportFragmentManager());
        itemViewPager.setAdapter(myItemPagerAdapter);
        itemTabLayout = (TabLayout) findViewById(R.id.tablayout_item);
        itemTabLayout.setupWithViewPager(itemViewPager);

        viewPager = (ViewPager) findViewById(R.id.pager_reminder);
        ReminderPageAdapter myPagerAdapter = new ReminderPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_reminder);
        tabLayout.setupWithViewPager(viewPager);

        fraudViewPager = (ViewPager) findViewById(R.id.pager_fraud);
        FraudPageAdapter myFraudPageAdapter = new FraudPageAdapter(getSupportFragmentManager());
        fraudViewPager.setAdapter(myFraudPageAdapter);
        fraudTabLayout = (TabLayout) findViewById(R.id.tablayout_fraud);
        fraudTabLayout.setupWithViewPager(fraudViewPager);



        //temporary to make imageview from Memories invisible on click
        final ImageView imageView = findViewById(R.id.ivImageTaken);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.INVISIBLE);
            }
        });
    }
    /**
     * display previous Fragment on back pressed.
     */
    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count == 0){
            super.onBackPressed();
        }else {
            getSupportFragmentManager().popBackStack();
        }
    }

    /**
     * Do different operations depending on which navigation item is selected
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = new HomeFragment();
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        itemTabLayout.setVisibility(View.GONE);
        itemViewPager.setVisibility(View.GONE);
        fraudTabLayout.setVisibility(View.GONE);
        fraudViewPager.setVisibility(View.GONE);

        setMenuVisible(false);
        this.setTitle(menuItem.getTitle());
        switch (menuItem.getItemId()){
            case R.id.language_learning:
                fragment = new TranslationWebViewFragment();
                break;
            case R.id.currency_conversion:
                fragment = new CurrencyConversionFragment();
                break;
            case R.id.weather:
                fragment = new WeatherFragment();
                break;
            case R.id.conditional_reminder:
                setMenuVisible(true);
                fragment = new ReminderFragment();
                this.setTitle("Reminder");
                break;
            case R.id.contact:
                fragment = new ContactFragment();
                break;
            case R.id.fraud_checker:
                fragment = new FraudPreventionAddressFragment();
                break;
            case R.id.holidays:
                fragment = new PublicHolidaysFragment();
                break;
            case R.id.logout:
                fragment = new LoginFragment(); //Change later, this is only for testing
                break;
            case R.id.fitness:
                fragment = new StepCounterFragment();
                break;
            case R.id.photo_location:
                fragment = new MemoriesMenuFragment();
                break;
            case R.id.virtual_bag:
                fragment = new ItemHomeFragment();
                break;
        }
        addToBackStack(fragment);
        navDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openTranslations(View view) {
//        replaceFragment(new TranslationFragment());
        String ResourceIdAsString = view.getResources().getResourceName(view.getId());
        Log.v("TransMenuViewClicked", ResourceIdAsString);
        Fragment fragment = new TranslationFragment();

        Bundle args = new Bundle();
        args.putString("TRANS_VIEW_CLICKED", view.getResources().getResourceName(view.getId()));
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.fragment_holder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void addToBackStack(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.fragment_holder, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reminder_sort_menu, menu);
        optionMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private boolean visible = false;
    public void setMenuVisible(boolean visible){
        invalidateOptionsMenu();
        this.visible = visible;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_item_location).setVisible(visible);
        menu.findItem(R.id.menu_item_weather).setVisible(visible);
        menu.findItem(R.id.menu_item_time).setVisible(visible);
        return super.onPrepareOptionsMenu(menu);

    }

    public void checkCameraPermission(){
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            //do nothing if permission is granted
        }

        else{
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

}
