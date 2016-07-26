package com.zhangyaobin.infiniteloopviewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zhangyaobin on 16/7/25.
 */
public class ViewPagerAdapter extends PagerAdapter {
    // int mCount = mAdImageList.size();
    List<String> mAdImageList;
    Context mContext;

    public ViewPagerAdapter(Context context, List<String> mAdImageList, OnBannerClickListener onBannerClickListener) {
        this.mContext = context;
        this.mAdImageList = mAdImageList;
        this.mOnBannerClickListener=onBannerClickListener;
        initImageView();
    }

    public void updateData(List<String> mAdImageList) {
        this.mAdImageList = mAdImageList;
    }

    @Override
    public int getCount() {
        if (mAdImageList.size() <= 1) {
            return 1;
        }
        return Integer.MAX_VALUE;
    }

    /**
     * 判断出去的view是否等于进来的view 如果为true直接复用
     */
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * 销毁预加载以外的view对象, 会把需要销毁的对象的索引位置传进来就是position
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (mAdImageList.size() == 0) {
            container
                    .removeView(mImageViews[position / 1 % 2][0]);
        } else {
            container.removeView(mImageViews[position
                    / mAdImageList.size() % 2][position
                    % mAdImageList.size()]);
        }
    }

    /**
     * 创建一个view
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView view = null;
        if (mAdImageList.size() == 0) {
            view = mImageViews[position / 1 % 2][0];
        } else {
            view = mImageViews[position / mAdImageList.size() % 2][position
                    % mAdImageList.size()];
        }
        container.addView(view, 0);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                 Toast.makeText(mContext, "点击事件", Toast.LENGTH_LONG).show();
                if (mOnBannerClickListener != null) {
//                        跳转至对应的界面

                    if (mAdImageList.size() == 0) {
                        mOnBannerClickListener.onBannerClick(""+position);
                    } else {
                        mOnBannerClickListener.onBannerClick(""+position
                                % mAdImageList.size());
                    }
                }
            }
        });
        return view;
    }

    /**
     * 装ImageView数组
     */
    protected ImageView[][] mImageViews;

    // 初始化imageview
    protected void initImageView() {
        ImageView iv;
        // imageViewList.clear();
        mImageViews = new ImageView[2][];
        if (mAdImageList != null && mAdImageList.size() > 0) {
            // 将图片装载到数组中,其中一组类似缓冲，防止图片少时出现黑色图片，即显示不出来
            mImageViews[0] = new ImageView[mAdImageList.size()];
            mImageViews[1] = new ImageView[mAdImageList.size()];
            for (int i = 0; i < mImageViews.length; i++) {
                for (int j = 0; j < mImageViews[i].length; j++) {
                    iv = new ImageView(mContext);
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Picasso.with(mContext).load(mAdImageList.get(j)).placeholder(R.mipmap.ic_launcher).into(iv);
                    mImageViews[i][j] = iv;
                }
            }
        } else {
            mImageViews[0] = new ImageView[1];
            mImageViews[1] = new ImageView[1];
            for (int i = 0; i < mImageViews.length; i++) {
                for (int j = 0; j < mImageViews[i].length; j++) {
                    iv = new ImageView(mContext);
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Picasso.with(mContext).load(R.mipmap.ic_launcher).into(iv);
                    mImageViews[i][j] = iv;
                }
            }
        }
    }

    OnBannerClickListener mOnBannerClickListener;

    public interface OnBannerClickListener {
        void onBannerClick(String action);
    }

}



