package com.noubo.oldmancare.olamancaremanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ImageButton;
import android.view.View;
import android.view.ViewGroup;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import com.noubo.oldmancare.R;

import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,AddressFragment.OnFragmentInteractionListener, MineFragment.OnFragmentInteractionListener{
    private static final String TAG="MainActivity";
    private ViewPager viewPager;
    private LinearLayout mTabAddress;
    private LinearLayout mTabMine;
    private ImageButton btnAddress,btnMine;
    private PagerAdapter mPagerAdapter;
    private List<View> mViews = new ArrayList<View>();

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.d(TAG, getClass().getSimpleName());
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//隐藏标题栏
        initViews();
        initEvents();
    }

    private void initEvents() {
        mTabMine.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            /**
             * 滑动点击事件
             * @param i
             */
            @Override
            public void onPageSelected(int i) {
                int currentItem = viewPager.getCurrentItem();
                resetImg();
                switch(currentItem){
                    case 0:
                        btnAddress.setImageResource(R.drawable.faxian_press);
                        replaceFragment(new AddressFragment());
                        Log.d(TAG,"滑动地址");
                        break;
                    case 1:
                        btnMine.setImageResource(R.drawable.wo_press);
                        replaceFragment(new MineFragment());
                        Log.d(TAG,"滑动我的");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabAddress = (LinearLayout) findViewById(R.id.tab_address);
        mTabMine = (LinearLayout) findViewById(R.id.tab_mine);
        btnAddress = (ImageButton) findViewById(R.id.tab_address_img);
        btnMine = (ImageButton) findViewById(R.id.tab_mine_img);
        LayoutInflater inflater = LayoutInflater.from(this);
        View tabAddress = inflater.inflate(R.layout.fragment_address,null);
        View tabMine = inflater.inflate(R.layout.fragment_mine,null);
        mViews.add(tabAddress);
        mViews.add(tabMine);
        mPagerAdapter = new PagerAdapter(){

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);
                container.addView(view);
                Log.d(TAG,"view");
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }
        };
        viewPager.setAdapter(mPagerAdapter);
    }

    /**
     * 将所有的图片切换为暗色
     */
    private void resetImg() {
        btnMine.setImageResource(R.drawable.wo);
        btnAddress.setImageResource(R.drawable.faxian);
    }


    @Override
    public void onClick(View view) {
        resetImg();
        switch(view.getId()){
            case R.id.tab_address:
                viewPager.setCurrentItem(0);
                btnAddress.setImageResource(R.drawable.faxian_press);
                Log.d(TAG,"点击地址");
                break;
            case R.id.tab_mine:
                viewPager.setCurrentItem(1);
                btnMine.setImageResource(R.drawable.wo_press);
                Log.d(TAG,"点击我的");
                break;
            default:
                break;
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.address_layout,fragment);
        transaction.commit();
    }
}
