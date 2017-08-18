package vavien.agency.liman.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import vavien.agency.liman.R;

/**
 * Created by ${Burhan} on 19.07.2017.
 * burhantoprakman@gmail.com
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    ImageButton imb_guvenlikVideolari,imb_isKurallari,imb_cevreYonetmeligi,imb_kullaniciBilgileri,imb_iletisimBilgileri;
    Fragment newFragment = null;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootview = inflater.inflate(R.layout.layout_homefragment,container,false);
            imb_guvenlikVideolari=rootview.findViewById(R.id.guvenlikVideo);
            imb_isKurallari=rootview.findViewById(R.id.isKurallari);
            imb_cevreYonetmeligi=rootview.findViewById(R.id.cevreYonetmeligi);
            imb_kullaniciBilgileri=rootview.findViewById(R.id.kullaniciBilgileri);
            imb_iletisimBilgileri=rootview.findViewById(R.id.iletisimBilgileri);

            imb_guvenlikVideolari.setOnClickListener(this);
            imb_isKurallari.setOnClickListener(this);
            imb_cevreYonetmeligi.setOnClickListener(this);
            imb_kullaniciBilgileri.setOnClickListener(this);
            imb_iletisimBilgileri.setOnClickListener(this);

            return rootview;
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

            // write logic here b'z it is called when fragment is visible to user
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

    @Override
    public void onClick(View view) {
        switch (view.getId())

        {
            case R.id.guvenlikVideo:
                newFragment = new VideosFragment();
                break;
            case R.id.isKurallari:
                newFragment = new RulesFragment();
                break;
            case R.id.cevreYonetmeligi:
                newFragment = new CevreFragment();
                break;
            case R.id.kullaniciBilgileri:
                newFragment = new ProfileFragment();
                break;
            case R.id.iletisimBilgileri:
                newFragment = new IletisimFragment();
                break;
            default:
                break;
        }
        getFragmentManager().beginTransaction().replace(
                R.id.frameLayout, newFragment)
                .commit();

    }
}
