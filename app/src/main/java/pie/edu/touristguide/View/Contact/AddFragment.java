package pie.edu.touristguide.View.Contact;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import pie.edu.touristguide.Controller.APICall.GetAdjustedTimeTask;
import pie.edu.touristguide.Controller.Database.ContactController;
import pie.edu.touristguide.Model.Contact;
import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.FragmentNavigationUtil;
import pie.edu.touristguide.Util.AddFragmentUtil;
import pie.edu.touristguide.Util.LocationUtil;

/**
 * @author Botao Yu
 * Using a Fragment, add a contact.
 */
public class AddFragment extends Fragment implements View.OnClickListener {

    private TextInputEditText etPhoneNumber;
    private TextInputEditText etName;
    private TextInputEditText etLocation;

    private TextInputLayout numberTextInputLayout;
    private TextInputLayout nameTextInputLayout;
    private TextInputLayout locationTextInputLayout;

    private long numberInEt;
    private String nameInEt;
    private String locationInEt;

    private Contact newContact;
    private long oldPhoneNumber;

    ImageView cancelIv;
    ImageView addIv;
    ImageView profileIv;

    private boolean isInputValid;

    private ContactController contactController;

    private static final int CHOOSE_PROFILE_REQUEST_CODE = 111;

    private byte[] bitMapByteArray;

    private static final String TAG = AddFragment.class.getSimpleName();
    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        viewSetup(rootView);
        contactController = new ContactController(getActivity());

        //check whether this Fragment is open because edit is clicked
        if(getArguments() != null){
            fillEditTexts();
        }



    }

    @Override
    public void onResume() {
        super.onResume();
        AddFragmentUtil.setToolBar(R.id.save_data_toolbar, getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        AddFragmentUtil.setToolBar(R.id.main_toolbar, getActivity());
    }

    private void viewSetup(View rootView){
        //intialization
        cancelIv = rootView.findViewById(R.id.iv_cancel);
        addIv = rootView.findViewById(R.id.iv_add);

        profileIv = rootView.findViewById(R.id.iv_profile_image);
        etPhoneNumber = rootView.findViewById(R.id.et_number);
        etName = rootView.findViewById(R.id.et_contact_name);
        etLocation = rootView.findViewById(R.id.et_contact_location);

        numberTextInputLayout = rootView.findViewById(R.id.text_input_layout_number);
        nameTextInputLayout = rootView.findViewById(R.id.text_input_layout_Name);
        locationTextInputLayout = rootView.findViewById(R.id.text_input_layout_location);

        //Set Listener
        cancelIv.setOnClickListener(this);
        addIv.setOnClickListener(this);
        profileIv.setOnClickListener(this);



    }
    /**
     * Do different operations depending on the view's ID.
     * @param view that is clicked
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cancel:
                //go to ContactFragment and does not do any CRUD operations
                Toast.makeText(getActivity(), "cancel", Toast.LENGTH_LONG).show();
                FragmentNavigationUtil.replaceFragment(getActivity(), new ContactFragment());
                break;
            case R.id.iv_add:
                resetTextInputLayout();
                if(getIsInputValid()){

                    //Update if AddFragment is started by edit button
                    if(getArguments() != null){
                        updateContact();
                        Toast.makeText(getActivity(), "contact updated", Toast.LENGTH_LONG).show();
                        FragmentNavigationUtil.replaceFragment(getActivity(), new ContactFragment());
                    }else{
                        //Add contact if AddFragment is started by FAB add button
                        Toast.makeText(getActivity(), "contact created", Toast.LENGTH_LONG).show();
                        getDataInEditText();
                        long rawOffSet = getRawOffSetWith(locationInEt);
                        if(rawOffSet == GetAdjustedTimeTask.COORDINATE_NOT_FOUND||
                                rawOffSet == GetAdjustedTimeTask.RAW_OFF_SET_NOT_FOUND){
                            Toast.makeText(getActivity(), "Invalid location", Toast.LENGTH_LONG).show();
                            return;
                        }
                        newContact = new Contact(nameInEt, numberInEt, locationInEt, rawOffSet, bitMapByteArray);
                        this.contactController.createContact(newContact);
                        FragmentNavigationUtil.replaceFragment(getActivity(), new ContactFragment());
                    }
                }
                else{
                    Toast.makeText(getContext(), "Please fill all fields.", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.iv_profile_image:
                //reference: https://stackoverflow.com/questions/5309190/android-pick-images-from-gallery
                Intent openGalleryIntent = new Intent();
                openGalleryIntent.setType("image/*");
                openGalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(openGalleryIntent,
                        "Choose a profile picture"),
                        CHOOSE_PROFILE_REQUEST_CODE);
                break;
        }

    }

    /**
     * perform actions on the data based on requestCode and resultCode
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //set profile picture
        if(requestCode == CHOOSE_PROFILE_REQUEST_CODE){
            try {
                Log.d(TAG, "onActivityResult works");
                InputStream inputStream = getContext().getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                //reference:https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                bitMapByteArray = stream.toByteArray();

                profileIv.setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                profileIv.invalidate();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * get time offset based on location
     * @param location to get time offset from
     * @return time offset
     */
    private long getRawOffSetWith(String location){
        long rawOffSet = 0;
        try {
            rawOffSet = new GetAdjustedTimeTask(getContext()).execute(location).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rawOffSet;
    }

    /**
     * Fill the EditTexts if AddFragment is started by edit button
     */
    private void fillEditTexts(){
        //get data from bundle
        String name = getArguments().getString(getString(R.string.contact_name_key));
        String location = getArguments().getString(getString(R.string.contact_location_key));
        String newNumberStr = getArguments().getString(getString(R.string.contact_number_key));
        byte[] bitmapBytes = getArguments().getByteArray(getString(R.string.contact_bitmap_bytes_key));
        etName.setText(name);
        etLocation.setText(location);
        etPhoneNumber.setText(newNumberStr);
        oldPhoneNumber = Long.parseLong(newNumberStr);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
        profileIv.setImageBitmap(bitmap);
    }

    /**
     * update contact depending on the old phone number of the contact.
     */
    private void updateContact(){
        getDataInEditText();
        long rawOffset = getRawOffSetWith(locationInEt);
        contactController.updateContact(new Contact(nameInEt, numberInEt, locationInEt, rawOffset, bitMapByteArray), oldPhoneNumber);
    }

    private void getDataInEditText(){
        nameInEt = etName.getText().toString();
        locationInEt = etLocation.getText().toString();
        numberInEt = Long.parseLong(etPhoneNumber.getText().toString());
    }

    /**
     * set isInputValid to false and show error message if one of the input is not valid.
     * @return isInputValid
     */
    private boolean getIsInputValid(){
        isEditTextValid(etName, nameTextInputLayout, "*Please enter a name");
        isEditTextValid(etLocation, locationTextInputLayout, "*Please enter a location");
        String location = etLocation.getText().toString();

        if(!location.isEmpty()){
            if(!LocationUtil.isLocationValid(location, getContext()))
                isEditTextValid(etLocation, locationTextInputLayout, "*" + location + " is not a valid location");
        }
        isEditTextValid(etPhoneNumber, numberTextInputLayout, "*Please enter a phone number");

        if(bitMapByteArray == null){
            profileIv.setImageTintList(ColorStateList.valueOf(Color.RED));
        }

        return isInputValid;
    }

    private void isEditTextValid(TextInputEditText editText, TextInputLayout layout, String message){
        if(editText.getText().toString().isEmpty()){
            layout.setHint("");
            layout.setError(message);
            if(isInputValid)
                isInputValid = false;
        }
    }

    private void resetTextInputLayout(){
        isInputValid = true;
        nameTextInputLayout.setErrorEnabled(false);
        numberTextInputLayout.setErrorEnabled(false);
        locationTextInputLayout.setErrorEnabled(false);
    }



}
