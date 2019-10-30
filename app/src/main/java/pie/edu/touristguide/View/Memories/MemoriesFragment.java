package pie.edu.touristguide.View.Memories;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import pie.edu.touristguide.Controller.Database.Memories.MemoriesController;
import pie.edu.touristguide.Model.Memory;
import pie.edu.touristguide.R;
import pie.edu.touristguide.View.HomeFragment;

/**
 * @author Emmanuel
 * Fragment that will display the images
 */
public class MemoriesFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1 ;
    ImageView imageView;
    private View rootView;
    private String album;
    private List<Memory> memoryList;
    private MemoriesController controller;
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private MemoriesRvAdapter adapter;

    public MemoriesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.memories_layout, container, false);

        //Get album clicked as a string
        Bundle receive = getArguments();
        album = receive.getString("album");
        Log.v("albumClicked", album);


        controller = new MemoriesController(getContext());
        memoryList = new ArrayList<>();
        memoryList = controller.getAllMemories();
        memoryList = controller.getAlbumMemories(album);

        //set recycler view and adapter
        this.recyclerView = rootView.findViewById(R.id.rvImages);
        this.layoutManager = new LinearLayoutManager(getContext());
        this.adapter = new MemoriesRvAdapter(memoryList, getContext());
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setAdapter(adapter);

        //inflate imageview that shows image
        CardView cardView = (CardView) inflater.inflate(R.layout.memories_layout, container); //replace container with null?

        FloatingActionButton fab = rootView.findViewById(R.id.fabOpenCamera);
        imageView = (ImageView) getActivity().findViewById(R.id.ImageView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try{
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
                catch (Exception e){
                    Log.v("pictureTaken", "Picture was not taken");
                    Toast.makeText(getActivity(), "Please try again", Toast.LENGTH_SHORT);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try{
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            byte[] imageArr = getBitmapAsByteArray(bitmap);
            Memory memory = new Memory(album, imageArr, "Enter a description!");
            controller.createMemory(memory);
            memoryList.add(memory);
            adapter = new MemoriesRvAdapter(memoryList, getContext());

            //set recycler view and adapter
            this.recyclerView = rootView.findViewById(R.id.rvImages);
            this.layoutManager = new LinearLayoutManager(getContext());
            this.adapter = new MemoriesRvAdapter(memoryList, getContext());
            this.recyclerView.setLayoutManager(layoutManager);
            this.recyclerView.setAdapter(adapter);
        }

        catch (Exception e){
            Log.v("nullPicture", "Picture taking failed");
            e.printStackTrace();
        }
    }

    /**
     * Convert image bitmap to byte array to store in database.
     * https://stackoverflow.com/questions/9357668/how-to-store-image-in-sqlite-database
     * @param bitmap
     * @return
     */
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}
