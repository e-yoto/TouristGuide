package pie.edu.touristguide.View.Memories.MemoriesMenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pie.edu.touristguide.Model.Album;
import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.FragmentNavigationUtil;
import pie.edu.touristguide.View.Memories.MemoriesFragment;


public class MemoMenuRvAdapter extends RecyclerView.Adapter<MemoMenuRvAdapter.ViewHolder> {
    private List<Album> mAlbums;
    private LayoutInflater mInflator;
    private Context context;

    public MemoMenuRvAdapter(List<Album> albums, Context context){
        this.mAlbums = albums;
        this.context = context;
        mInflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflator.inflate(R.layout.memories_menu_grid_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoMenuRvAdapter.ViewHolder viewHolder, int i) {
        Album album = mAlbums.get(i);
        viewHolder.tvAlbum.setText(album.getName());
    }

    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        TextView tvAlbum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAlbum = itemView.findViewById(R.id.tvAlbumName);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            context = v.getContext();
            Toast.makeText(context, mAlbums.get(position).getName(), Toast.LENGTH_LONG).show();

            Bundle args = new Bundle();
            args.putString("album", mAlbums.get(position).getName());
            Fragment fragment = new MemoriesFragment();
            fragment.setArguments(args);
            FragmentNavigationUtil.replaceFragment(((FragmentActivity)context), fragment);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            int position = this.getAdapterPosition();
            menu.setHeaderTitle(mAlbums.get(position).getName());
            menu.add(0, v.getId(), position, "Edit");
            menu.add(1, v.getId(), position, "Delete");

        }

    }
}
