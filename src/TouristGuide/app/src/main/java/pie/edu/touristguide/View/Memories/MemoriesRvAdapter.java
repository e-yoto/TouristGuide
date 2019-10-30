package pie.edu.touristguide.View.Memories;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pie.edu.touristguide.Controller.Database.Memories.MemoriesController;
import pie.edu.touristguide.Model.Memory;
import pie.edu.touristguide.R;
import pie.edu.touristguide.Util.FragmentNavigationUtil;

public class MemoriesRvAdapter extends RecyclerView.Adapter<MemoriesRvAdapter.ViewHolder>{
    private List<Memory> mMemories;
    private LayoutInflater mInflator;
    private Context context;
    private MemoriesController controller;
    private MemoriesRvAdapter adapter;


    public MemoriesRvAdapter(List<Memory> memories, Context context){
        this.mMemories = memories;
        this.context = context;
        mInflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflator.inflate(R.layout.memories_image_layout, viewGroup, false);
        controller = new MemoriesController(view.getContext());

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoriesRvAdapter.ViewHolder viewHolder, int i) {
        Memory memory = mMemories.get(i);
        viewHolder.ivMemory.setImageBitmap(memory.getByteArrayToBitmap());
    }

    @Override
    public int getItemCount() {
        return mMemories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivMemory;
        TextView etdescription;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMemory = itemView.findViewById(R.id.ivMemory);
            ivDelete = itemView.findViewById(R.id.ic_delete);
            etdescription = itemView.findViewById(R.id.tv_desc);

            itemView.setOnClickListener(this);
            ivDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            int position = this.getAdapterPosition();
            final Memory memory = mMemories.get(position);

            switch (id) {
                case R.id.ic_delete:
                    controller.deleteMemory(memory);
                    mMemories.remove(position);
                    notifyItemRemoved(position);
                    break;
            }
        }
    }
}