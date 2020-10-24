package com.e.cmlive.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.e.cmlive.R;
import com.e.cmlive.activities.DiamondBalanceActivity;
import com.e.cmlive.activities.SettingsActivity;
import com.e.cmlive.utils.IntentUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @OnClick({R.id.settings,R.id.transaction_history, R.id.recharge, R.id.diamonds})
    void OnItemClick(View view) {
        switch (view.getId()) {
            case R.id.settings: {
                IntentUtils.startIntent(getActivity(), SettingsActivity.class);
                break;
            }
            case R.id.transaction_history: {

                break;
            }
            case R.id.diamonds: {
                IntentUtils.startIntent(getActivity(), DiamondBalanceActivity.class);
                break;
            }
        }
    }
}