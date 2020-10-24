package com.e.cmlive.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.e.cmlive.R;
import com.e.cmlive.base.BaseActivity;
import com.e.cmlive.fragments.HomeFragment;
import com.e.cmlive.fragments.InboxFragment;
import com.e.cmlive.fragments.LikesFragment;
import com.e.cmlive.fragments.ProfileFragment;
import com.e.cmlive.utils.IntentUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.nav_view_home)
    BottomNavigationView navViewHome;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private HomeFragment homeFragment;
    private LikesFragment likesFragment;
    private InboxFragment inboxFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        init();
        addFragment();

    }

    private void init() {
        homeFragment = new HomeFragment();
        likesFragment = new LikesFragment();
        inboxFragment = new InboxFragment();
        profileFragment = new ProfileFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        navViewHome.setOnNavigationItemSelectedListener(this);
    }

    private void addFragment() {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, homeFragment, "home");
        fragmentTransaction.commit();
    }

    private void switchFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home: {
                switchFragment(homeFragment);
                break;
            }
            case R.id.nav_likes: {
                switchFragment(likesFragment);
                break;
            }
            case R.id.nav_live: {
                IntentUtils.startIntent(this, StartLiveActivity.class);
                break;
            }
            case R.id.nav_chats: {
                switchFragment(inboxFragment);
                break;
            }
            case R.id.nav_profile: {
                switchFragment(profileFragment);
                break;
            }
        }

        return false;
    }
}