package com.zhangyaobin.infiniteloopviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.view.ViewPager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements ViewPager.OnPageChangeListener {
    /**
     * MyViewPager定制性较强，可以放在scrollview中，也可放在listview的头部或底部
     */
    MyViewPager mMyViewPager;
    LinearLayout mDotLayout;
    List<String> mAdImageList = new ArrayList<>();
    CountdownTimer mCountdownTimer;
    private int currentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMyViewPager = (MyViewPager) findViewById(R.id.MyViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.DotLayout);
        mCountdownTimer = new CountdownTimer(5000, 1000);
        initData();
        currentItem = mAdImageList.size() < 2 ? 1
                : mAdImageList.size() * 10;
        createDot(currentItem);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, mAdImageList, new ViewPagerAdapter.OnBannerClickListener() {
            @Override
            public void onBannerClick(String action) {
                Toast.makeText(MainActivity.this,"点击"+action,Toast.LENGTH_LONG).show();
            }
        });
        // 速度匀速，默认为加速
        LinearInterpolator interpolator = new LinearInterpolator();
        ViewPagerScroller viewPagerScroller = new ViewPagerScroller(this, interpolator);
        viewPagerScroller.initViewPagerScroll(mMyViewPager);


        mMyViewPager.setAdapter(viewPagerAdapter);

        mMyViewPager.setCurrentItem(currentItem, true);
        mMyViewPager.setOnPageChangeListener(this);

    }

    private void initData() {
        mAdImageList.add("http://img5.imgtn.bdimg.com/it/u=3425851328,2681317699&fm=206&gp=0.jpg");
        mAdImageList.add("http://pic9.nipic.com/20100904/4845745_195609329636_2.jpg");
        mAdImageList.add("http://img1.3lian.com/2015/w7/90/d/5.jpg");
        mAdImageList.add("http://pic1.nipic.com/2008-12-09/200812910493588_2.jpg");
        mAdImageList.add("http://img1.3lian.com/2015/w7/90/d/1.jpg");
        mAdImageList.add("http://pic1.nipic.com/2008-12-25/2008122510134038_2.jpg");
        mAdImageList.add("http://pic10.nipic.com/20101103/5063545_000227976000_2.jpg");
    }

    // 创建顶部广告对应的小圆点
    protected void createDot(int currentIndex) {
        mDotLayout.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 0, 5, 0);
        // Log.d("zhangyaobin", "list.size=" + list.size());
        if (mAdImageList != null && mAdImageList.size() > 1) {
            for (int i = 0; i < mAdImageList.size(); i++) {
                ImageView image = new ImageView(this);
                image.setLayoutParams(lp);
                image.setScaleType(ImageView.ScaleType.MATRIX);
                image.setImageResource(R.mipmap.banner_point_empty);
                mDotLayout.addView(image);
            }
            ((ImageView) mDotLayout.getChildAt(currentIndex % mAdImageList.size()))
                    .setImageResource(R.mipmap.banner_point_fill);
        }
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItemsIndex
     */
    private void setDotBackground(int selectItemsIndex) {
        for (int i = 0; i < mDotLayout.getChildCount(); i++) {
            if (i == selectItemsIndex) {
                ((ImageView) mDotLayout.getChildAt(i))
                        .setImageResource(R.mipmap.banner_point_fill);
            } else {
                ((ImageView) mDotLayout.getChildAt(i))
                        .setImageResource(R.mipmap.banner_point_empty);
            }
        }
    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        currentItem=i;
        setDotBackground(currentItem % mAdImageList.size());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public class CountdownTimer extends CountDownTimer {
        public CountdownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            currentItem++;
            mMyViewPager.setCurrentItem(currentItem);
            start();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }
    }

    private boolean isLooping = false;

    @Override
    protected void onResume() {
        super.onResume();
        startLoop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLoop();
    }

    private void startLoop() {
        if (mCountdownTimer != null && !isLooping) {
            isLooping = true;
            mCountdownTimer.start();
        }
    }

    private void stopLoop() {
        isLooping = false;
        mCountdownTimer.cancel();
    }



    @Override
    protected void onDestroy() {

        mCountdownTimer = null;
        super.onDestroy();
    }

//    OnBannerClickListener mOnBannerClickListener;
//
//    public interface OnBannerClickListener {
//        void onBannerClick(String action);
//    }
//
//    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
//        mOnBannerClickListener = onBannerClickListener;
//    }
}
