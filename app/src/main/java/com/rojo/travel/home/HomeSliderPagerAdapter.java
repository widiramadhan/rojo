package com.rojo.travel.home;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.rojo.travel.R;

import java.util.List;

public class HomeSliderPagerAdapter extends PagerAdapter{

    private Context ctx;
    private LayoutInflater layoutInflater;
    private List<SliderUtil> imgUrl;
    private ImageLoader imageLoader;
    //private Integer[] images = {R.drawable.slider1,R.drawable.slider2,R.drawable.slider3,R.drawable.slider4};

    public HomeSliderPagerAdapter(List<SliderUtil> imgUrl, Context ctx) {
        this.imgUrl = imgUrl;
        this.ctx = ctx;
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imgUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.image_slider_home, null);
        SliderUtil util = imgUrl.get(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgView);

        //imageView.setImageResource(images[position]);
        try {
            imageLoader = CustomVolleyRequest.getInstance(ctx).getImageLoader();
            imageLoader.get(util.getSliderImgUrl(), ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
            /*Glide.with(ctx).load(util).asBitmap().centerCrop()
                    .placeholder(R.drawable.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);*/
        } catch (Exception ex){
            ex.printStackTrace();
        }
        container.addView(view);

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((View) object);
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
