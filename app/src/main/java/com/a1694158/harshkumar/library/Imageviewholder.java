package com.a1694158.harshkumar.library;

import android.view.View;
import android.widget.ImageView;

/**
 * Created by Harsh on 7/3/2017.
 */

public class Imageviewholder {
    ImageView img;

    public Imageviewholder(View imagev)
    {
        img = (ImageView) imagev.findViewById(R.id.grid_item_image);
    }
}
