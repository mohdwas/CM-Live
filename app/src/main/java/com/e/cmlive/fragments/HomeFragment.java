package com.e.cmlive.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.e.cmlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {

    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.featured)
    TextView featured;
    @BindView(R.id.near_by)
    TextView nearBy;
    @BindView(R.id.follow)
    TextView follow;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private FeaturedFragment featuredFragment;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        init();
        addFragment();

        return root;
    }

    private void init() {
        featuredFragment = new FeaturedFragment();
        fragmentManager = getActivity().getSupportFragmentManager();
    }

    private void addFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.home_fragment_container, featuredFragment, "featured");
        fragmentTransaction.commit();
    }

    private void switchFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.home_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @OnClick({R.id.search, R.id.featured, R.id.near_by, R.id.follow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search:
                break;
            case R.id.featured:
                break;
            case R.id.near_by:
                break;
            case R.id.follow:
                break;
        }
    }
}