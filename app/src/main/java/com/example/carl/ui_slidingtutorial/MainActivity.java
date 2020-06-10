package com.example.carl.ui_slidingtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.carl.ui_slidingtutorial.parallax.animation.ParallaxViewPager;

public class MainActivity extends AppCompatActivity {
    private ParallaxViewPager mParallaxViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mParallaxViewPager=findViewById(R.id.parallax_vp);
        mParallaxViewPager.setLayout(getSupportFragmentManager(),
                new int[]{
                R.layout.fragment_page_first,
                R.layout.fragment_page_second,
                R.layout.fragment_page_third});
        //用最简单的方式让他人使用
    }
}
