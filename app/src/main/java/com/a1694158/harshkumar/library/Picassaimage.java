package com.a1694158.harshkumar.library;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Harsh on 7/3/2017.
 */

public class Picassaimage {

    public static void getImages(Context c, String url, ImageView img)
    {
        if(url != null && url.length()>0)
        {
            Picasso.with(c).load(url).placeholder(R.drawable.loading).into(img);
        }else
        {
            Picasso.with(c).load(R.drawable.smile).into(img);
        }
    }
}
