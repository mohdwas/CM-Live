package com.e.cmlive.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.cmlive.R;
import com.e.cmlive.adapter.FollowAdapter;
import com.e.cmlive.adapter.LikesAdapter;
import com.e.cmlive.adapter.RecommendAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LikesFragment extends Fragment {

    @BindView(R.id.follow_recycler_view)
    RecyclerView followRecyclerView;
    @BindView(R.id.recommend_recycler_view)
    RecyclerView recommendRecyclerView;

    private ArrayList<Integer> imagesArray;

    public LikesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_likes, container, false);
        ButterKnife.bind(this, root);

        init();
        setupFollowRecyclerview();
        setupRecommendRecyclerview();

        return root;
    }

    private void init() {
        imagesArray = new ArrayList<>();
    }

    private void setupFollowRecyclerview() {
        followRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        followRecyclerView.setAdapter(new FollowAdapter(getActivity()));
        followRecyclerView.setNestedScrollingEnabled(false);
        followRecyclerView.setFocusable(false);
    }

    private void setupRecommendRecyclerview() {
        recommendRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recommendRecyclerView.setAdapter(new RecommendAdapter(getActivity()));
        recommendRecyclerView.setNestedScrollingEnabled(false);
        recommendRecyclerView.setFocusable(false);
    }


}