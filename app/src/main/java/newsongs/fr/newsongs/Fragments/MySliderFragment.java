package newsongs.fr.newsongs.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import newsongs.fr.newsongs.API.PlaylistClient;
import newsongs.fr.newsongs.API.ServiceGenerator;
import newsongs.fr.newsongs.Models.Musique;
import newsongs.fr.newsongs.Models.Playlist;
import newsongs.fr.newsongs.PlayerInterface;
import newsongs.fr.newsongs.R;
import newsongs.fr.newsongs.Tools;
import newsongs.fr.newsongs.Adapters.TransformerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by antoine on 14/10/17.
 */

public class MySliderFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private SliderLayout mySlider;
    private ListView l;
    private PlayerInterface mListener;
    private List<Playlist> playlists;

    public MySliderFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider,container,false);

        try {
            mListener = (PlayerInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnButtonClickedListener ");
        }


        mySlider = (SliderLayout)view.findViewById(R.id.slider);
        l = (ListView)view.findViewById(R.id.listMusic);
        playlists = new ArrayList<>();

        SharedPreferences settings = getActivity().getSharedPreferences(Tools.PREFS_NAME, Context.MODE_PRIVATE); //1
        int idutilisateurdeezer = settings.getInt("idutilisateurdeezer", -2); //2

        PlaylistClient service = ServiceGenerator.createService(PlaylistClient.class);
        Call<List<Playlist>> call = service.getPlaylists(idutilisateurdeezer);
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                playlists = response.body();
                populate(playlists);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return view;
    }

    private void populate(List<Playlist> list){
        HashMap<String,String> url_maps = new HashMap<String, String>();
        for(Playlist p : list){
            url_maps.put(p.getNom(),p.getUrlimage());
        }

        int i=0;
        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getActivity());
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putInt("position",i);
            textSliderView.getBundle().putString("nom",name);

            mySlider.addSlider(textSliderView);
            i++;
        }
        mySlider.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mySlider.setCustomAnimation(new DescriptionAnimation());
        mySlider.setDuration(4000);
        mySlider.addOnPageChangeListener(this);
        l.setAdapter(new TransformerAdapter(getActivity(), new ArrayList<Musique>()));
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        final List<Musique> musicList = playlists.get(slider.getBundle().getInt("position")).getMusiques();
        l.setAdapter(new TransformerAdapter(getActivity(), musicList));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //On initialise le player
                mListener.init(musicList.get(position).getUrlpreview(),musicList.get(position).getTitre());
            }
        });
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
