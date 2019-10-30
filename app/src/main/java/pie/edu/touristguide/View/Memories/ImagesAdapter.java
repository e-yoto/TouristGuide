package pie.edu.touristguide.View.Memories;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import pie.edu.touristguide.Controller.Database.Memories.MemoriesController;
import pie.edu.touristguide.Model.Memory;
import pie.edu.touristguide.R;

/**
 * @author Emmanuel
 * Adapter for the GridView that displays the images
 */
public class ImagesAdapter extends BaseAdapter {
    private Context context;
    private MemoriesController controller;
    private int layout;
    private Integer[] images = {
            R.drawable.city1, R.drawable.city2,
            R.drawable.city1, R.drawable.city2,
            R.drawable.city1, R.drawable.city2,
            R.drawable.city1, R.drawable.city2,
            R.drawable.city1, R.drawable.city2,
            R.drawable.city1, R.drawable.city2,
            R.drawable.city1, R.drawable.city2,
            R.drawable.city1, R.drawable.city2
    };

    private List<Memory> memoryList;

    public ImagesAdapter(Context context, int layout, List<Memory> memories)
    {
        this.context = context;
        this.layout = layout;
        this.memoryList = memories;
    }

    private class ViewHolder{
        ImageView imageView;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = new ViewHolder();

//        imageView.setImageResource(images[position]);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setLayoutParams(new GridView.LayoutParams(240,240));

        if(row == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.imageView = (ImageView) row.findViewById(R.id.ivImageTaken);
            row.setTag(holder);
        }

        else
        {
            holder = (ViewHolder) row.getTag();
        }

        Memory memory = memoryList.get(position);

        byte[] image = memory.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
