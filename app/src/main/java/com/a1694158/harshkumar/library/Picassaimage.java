package com.a1694158.harshkumar.library;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Harsh on 7/3/2017.
 */

public class Picassaimage {


    public static void getImages(Context c, String url, ImageView img,final ProgressBar pw)
    {
        pw.setVisibility(View.VISIBLE);
        pw.getProgress();
        if(url != null && url.length()>0)
        {

            Picasso.with(c).load(url).noFade().resize(750,450).into(img, new Callback() {
                @Override
                public void onSuccess() {
                    if(pw!= null)
                    {
                        pw.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onError() {

                }
            });
        }else
        {
            Picasso.with(c).load(R.drawable.smile).into(img, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                }
            });
        }
    }
}
