package model;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.leonardoroman.photos19.R;

import java.util.ArrayList;

/**
 * Created by leonardoroman on 12/12/17.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    private ArrayList<Bitmap> images;
    View view;
    LayoutInflater layoutInflater;

    public CustomAdapter(Context context, ArrayList<Bitmap> images) {
        this.context = context;
        this.images = images;
    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View conveView, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (conveView == null){
            view = new View(context);
            view = layoutInflater.inflate(R.layout.unicorn_view,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.unicorn_photo);
            imageView.setImageBitmap(images.get(i));
        }
        return view;
    }
}
