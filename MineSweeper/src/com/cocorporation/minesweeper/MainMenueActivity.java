package com.cocorporation.minesweeper;


import android.support.v4.app.Fragment;

public class MainMenueActivity extends SingleFragmentActivity {

	@Override
    protected Fragment createFragment() {
        return new MainMenueFragment();
    }
}
