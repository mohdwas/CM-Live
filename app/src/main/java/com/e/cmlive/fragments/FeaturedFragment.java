package com.e.cmlive.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.cmlive.R;
import com.e.cmlive.activities.WatchLiveActivity;
import com.e.cmlive.adapter.FeaturedAdapter;
import com.e.cmlive.adapter.FeaturedViewPagerAdapter;
import com.e.cmlive.interfaces.FeatureListener;
import com.e.cmlive.utils.IntentUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeaturedFragment extends Fragment implements FeatureListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ArrayList<Integer> imagesArray;

    public FeaturedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_featured, container, false);
        ButterKnife.bind(this, root);

        init();
        setupViewPager();
        setupRecyclerview();

        return root;
    }

    private void init() {
        imagesArray = new ArrayList<>();

        imagesArray.add(R.drawable.bg_login_top);
        imagesArray.add(R.drawable.bg_login_top);
        imagesArray.add(R.drawable.bg_login_top);
        imagesArray.add(R.drawable.bg_login_top);
    }

    private void setupViewPager() {
        FeaturedViewPagerAdapter adapter = new FeaturedViewPagerAdapter(getActivity(), imagesArray);
        viewPager.setAdapter(adapter);
    }

    private void setupRecyclerview() {
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new FeaturedAdapter(getActivity(), this));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
    }


    @Override
    public void onFeatureSelect() {
        IntentUtils.startIntent(getActivity(), WatchLiveActivity.class);
    }
}