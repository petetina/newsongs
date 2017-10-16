package newsongs.fr.newsongs.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import newsongs.fr.newsongs.R;
import newsongs.fr.newsongs.TransformerAdapter;

/**
 * Created by antoine on 14/10/17.
 */

public class MySlider implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private Activity ctx;
    private SliderLayout mySlider;

    public MySlider(Activity ctx){
        this.ctx = ctx;
        mySlider = (SliderLayout)ctx.findViewById(R.id.slider);
        populate();
    }

    private void populate(){
        HashMap<String,String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.drawable.background);
        file_maps.put("Big Bang Theory",R.drawable.background);
        file_maps.put("House of Cards",R.drawable.background);
        file_maps.put("Game of Thrones", R.drawable.background);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(ctx);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mySlider.addSlider(textSliderView);
        }
        mySlider.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mySlider.setCustomAnimation(new DescriptionAnimation());
        mySlider.setDuration(4000);
        mySlider.addOnPageChangeListener(this);

        ListView l = (ListView)ctx.findViewById(R.id.listMusic);
        l.setAdapter(new TransformerAdapter(ctx));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //mySlider.setPresetTransformer(((TextView) view).getText().toString());
                Toast.makeText(ctx, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(ctx,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

}
