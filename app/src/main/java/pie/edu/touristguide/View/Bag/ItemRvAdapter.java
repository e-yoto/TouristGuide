package pie.edu.touristguide.View.Bag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pie.edu.touristguide.Model.Item;
import pie.edu.touristguide.R;

/**
 * @author Emmanuel
 * RecyclerView adapter for items
 */
public class ItemRvAdapter extends RecyclerView.Adapter<ItemRvAdapter.ViewHolder> {
    private List<Item> mItems;
    private LayoutInflater mInflator;
    private Context context;

    /**
     * Pass data into constructor
     */
    public ItemRvAdapter(List<Item> items, Context context){
        this.mItems = items;
        this.context = context;
        mInflator = LayoutInflater.from(context);
    }

    /**
     * Inflate the layout from the row layout xml and returns holder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflator.inflate(R.layout.bag_row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Item item = mItems.get(i);
        viewHolder.titleTv.setText(item.getTitle());
        viewHolder.weightTv.setText(Double.toString(item.getWeight()));
        if (item.getIsLiquid() == 1)
            viewHolder.liquidTv.setText("Liquid");
    }

    /**
     * Returns total amount of items in the list
     */
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        TextView titleTv;
        TextView weightTv;
        TextView liquidTv;

        /**
         * Holds view as they scroll out of screen
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_title);
            weightTv = itemView.findViewById(R.id.tv_weight);
            liquidTv = itemView.findViewById(R.id.tv_liquid);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();
            Toast.makeText(v.getContext(), mItems.get(position).toString(), Toast.LENGTH_LONG).show();
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            int position = this.getAdapterPosition();
            menu.setHeaderTitle(mItems.get(position).getTitle());
            menu.add(0, v.getId(), position, "Edit");
            menu.add(1, v.getId(), position, "Delete");
        }


    }

}
