package robott.app.com.robott;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import robott.app.com.robott.InfoPagerAdapter;
import robott.app.com.robott.R;

public class InfoShow extends FragmentActivity {


    private ViewPager viewPager;
    private InfoPagerAdapter pagerAdapter;

    private static final int NUM_PAGES = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_show);

        viewPager = (ViewPager) findViewById(R.id.infoPager);
        pagerAdapter = new InfoPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }

}