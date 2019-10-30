package pie.edu.touristguide.View.Memories.MemoriesMenu;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pie.edu.touristguide.Controller.Database.Memories.AlbumController;
import pie.edu.touristguide.Controller.Database.Memories.MemoriesController;
import pie.edu.touristguide.Model.Album;
import pie.edu.touristguide.Model.Item;
import pie.edu.touristguide.R;

/**
 * Fragment that displays the meny that allows the user to select an album
 */
public class MemoriesMenuFragment extends Fragment implements View.OnClickListener {
    static final int REQUEST_IMAGE_CAPTURE = 1 ;
    ImageView imageView;
    private View rootView;
    private RecyclerView recyclerView;
    private MemoMenuRvAdapter adapter;
    private Context context;
    private FloatingActionButton fab;
    private List<Album> albums;
    private AlbumController albumController;
    private String albumName;

    public MemoriesMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Inflate the View, make the action button open the camera and take a picture
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.memories_menu_layout, container, false);
        fab = rootView.findViewById(R.id.fabNewAlbum);
        imageView = (ImageView) getActivity().findViewById(R.id.ImageView);
        this.albumController = new AlbumController(getActivity().getApplicationContext());

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        albums = new ArrayList<>();
//        albums.add("USA");
//        albums.add("China");
//        albums.add("Germany");
//        albums.add("Brazil");
//        albums.add("Japan");
        albums = albumController.getAllAlbums();

        fab.setOnClickListener(this);
        view.setOnCreateContextMenuListener(this);

        //recyclerview to display the items in a list
        recyclerView = view.findViewById(R.id.rvMemoriesMenu);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new MemoMenuRvAdapter(albums, getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        context = v.getContext();

        switch (id){
            case R.id.fabNewAlbum:
                final LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.memories_add_album_layout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final EditText input = (EditText)view.findViewById(R.id.etAlbumInput);
                builder.setView(view)
                    .setTitle("Create an album")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })

                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //go to onShowListener

                        }
                    });

                final AlertDialog alert = builder.create();


                //Don't close the dialog if not all information has been checked.
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        Button b = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Don't create the album if no name is entered
                                if (input.getText().toString().matches(""))
                                    Toast.makeText(getContext(), "Please enter the album name",Toast.LENGTH_LONG).show();
                                else{
                                    albumName = input.getText().toString();
                                    Album createdAlbum = new Album(albumName);
                                    albumController.createAlbum(createdAlbum);
                                    albums.add(createdAlbum);
                                    adapter = new MemoMenuRvAdapter(albums,context);
                                    recyclerView.setAdapter(adapter);
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });

                alert.show();



                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //get position of album clicked
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final int index = item.getOrder();

        String command = item.getTitle().toString();
        Log.v("ALBUM_ITEM_CONTEXT", item.getTitle().toString());
        switch (command){
            case "Edit":
                final Album toUpdate = albums.get(index);
                final LayoutInflater inflater = LayoutInflater.from(getContext());
                View view = inflater.inflate(R.layout.memories_add_album_layout, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final EditText input = (EditText)view.findViewById(R.id.etAlbumInput);
                input.setText(toUpdate.getName());
                builder.setView(view)
                        .setTitle("Update album name")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })

                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //go to onShowListener

                            }
                        });

                final AlertDialog alert = builder.create();


                //Don't close the dialog if not all information has been checked.
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        Button b = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Don't create the album if no name is entered
                                if (input.getText().toString().matches(""))
                                    Toast.makeText(getContext(), "Please enter the album name",Toast.LENGTH_LONG).show();
                                else{
                                    albumName = input.getText().toString();
                                    Album newAlbum = new Album(albumName);
                                    albums.set(index, newAlbum);
                                    albumController.udpateAlbum(toUpdate, newAlbum);
                                    adapter = new MemoMenuRvAdapter(albums,getContext());
                                    recyclerView.setAdapter(adapter);
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                });

                alert.show();

                break;
            case "Delete":
                Album toDelete = albums.get(index);
                albums.remove(toDelete);
                albumController.deleteAlbum(toDelete);
                adapter.notifyItemRemoved(index);

                break;
        }
        return super.onContextItemSelected(item);

    }
}
