package com.example.carl.ui_slidingtutorial.parallax.animation;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

import com.example.carl.ui_slidingtutorial.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ParallaxViewPager extends ViewPager {
    private List<ParallaxFragment> mFragments;
    public ParallaxViewPager(@NonNull Context context) {
        super(context);
    }

    public ParallaxViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mFragments= new ArrayList<>();
    }

    public void setLayout(FragmentManager fm,int[] layouts) {
        mFragments.clear();
        for (int layoutId:layouts){
            ParallaxFragment fragment = new ParallaxFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ParallaxFragment.LAYOUT_ID_KEY,layoutId);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }

        setAdapter(new ParallaxPagerAdapter(fm));
        //监听滑动改变位移
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //左out 右in
                ParallaxFragment outFragment= mFragments.get(position);
                List<View> parallaxViews = outFragment.getParallaxViews();
                for (View parallaxView:parallaxViews){
                    ParallaxTag tag = (ParallaxTag)parallaxView.getTag(R.id.parallax_tag);
                    parallaxView.setTranslationX((-positionOffsetPixels)*tag.translationXOut);
                    parallaxView.setTranslationY((-positionOffsetPixels)*tag.translationYOut);
                }

                try{
                    ParallaxFragment inFragment = mFragments.get(position+1);
                    parallaxViews = inFragment.getParallaxViews();
                    for (View parallaxView:parallaxViews){
                        ParallaxTag tag = (ParallaxTag)parallaxView.getTag(R.id.parallax_tag);
                        parallaxView.setTranslationX((getMeasuredWidth()-positionOffsetPixels)*tag.translationXOut);
                        parallaxView.setTranslationY((getMeasuredHeight()-positionOffsetPixels)*tag.translationYOut);
                    }
                }catch (Exception e){

                }

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class ParallaxPagerAdapter extends FragmentPagerAdapter{


        public ParallaxPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}
