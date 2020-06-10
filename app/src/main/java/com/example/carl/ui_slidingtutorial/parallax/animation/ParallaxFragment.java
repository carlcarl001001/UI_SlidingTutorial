package com.example.carl.ui_slidingtutorial.parallax.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.carl.ui_slidingtutorial.R;
import com.example.carl.ui_slidingtutorial.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.LayoutInflaterFactory;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class ParallaxFragment extends Fragment implements LayoutInflaterFactory {
    public static final String LAYOUT_ID_KEY="LAYOUT_ID_KEY";
    private CompatViewInflater mCompatViewInflater;
    private int[]mParallaxAttrs = new int[]{R.attr.translationXIn,R.attr.translationXOut,R.attr.translationYIn,R.attr.translationYOut};
    private List<View> mParallaxViews = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getArguments().getInt(LAYOUT_ID_KEY);
        inflater = inflater.cloneInContext(getActivity());
        LayoutInflaterCompat.setFactory(inflater,this);
        return inflater.inflate(layoutId,container,false);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = createView(parent,name,context,attrs);
        if (view!=null){
            analysisAttrs(view,context,attrs);
        }
        return view;
    }

    private void analysisAttrs(View view, Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs,mParallaxAttrs);
        if (array!=null&&array.getIndexCount()!=0) {
            int n = array.getIndexCount();
            ParallaxTag tag = new ParallaxTag();
            for (int i = 0; i < n; i++) {
                int attr = array.getIndex(i);
                switch (attr) {
                    case 0:
                        tag.translationXIn = array.getFloat(attr,0f);
                        break;
                    case 1:
                        tag.translationXOut = array.getFloat(attr,0f);
                        break;
                    case 2:
                        tag.translationYIn = array.getFloat(attr,0f);
                        break;
                    case 3:
                        tag.translationYOut = array.getFloat(attr,0f);
                        break;
                }
            }
            Utils.log("tag.toString:"+tag.toString());
            Utils.log("R.id.parallax_tag:"+R.id.parallax_tag);
            view.setTag(R.id.parallax_tag,tag);//看看这给的作用
            mParallaxViews.add(view);
        }
        array.recycle();
    }

    public View createView(View parent,final String name,@NonNull Context context,
                           @NonNull AttributeSet attrs){
        final boolean isPre21 = Build.VERSION.SDK_INT<21;
        if (mCompatViewInflater ==null){
            mCompatViewInflater =new CompatViewInflater();
        }
        final boolean inheritContext = isPre21&&true&&shouldInheritContext((ViewParent)parent);
        return mCompatViewInflater.createView(parent,name,context,attrs,inheritContext,isPre21,true,true);
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent ==null){
            return false;
        }
        while(true){
            if (parent==null){
                return true;
            }else if (!(parent instanceof View)|| ViewCompat.isAttachedToWindow((View)parent)){
                return false;
            }
            parent = parent.getParent();
        }

    }

    public List<View>getParallaxViews(){
        return mParallaxViews;
    }


}
