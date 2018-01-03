package com.example.ijuin.testapplication.views.particle;

/**
 * Created by ijuin on 1/3/2018.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


public class ParticleView extends View{

    private ValueAnimator mParticleAnimator;

    public int DURATION = 4000;

    private OnAnimationListener mOnAnimationListener;

    private Paint mPaint;

    private Particle[][] mParticles;
    public void setOnAnimationListener(OnAnimationListener mOnAnimationListener) {
        this.mOnAnimationListener = mOnAnimationListener;
    }

    public ParticleView(Context context) {
        super(context);
        init();
    }


    public ParticleView(Context context,int durtion) {
        super(context);
        this.DURATION = durtion;
        init();
    }
    public ParticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        attachToActivity((Activity) getContext());
        mPaint = new Paint();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (mParticleAnimator !=null)
            drawParticle(canvas);
    }

    public void drawParticle(Canvas canvas) {
        for (Particle[] particle : mParticles) {
            for (Particle p : particle) {
                p.update((Float) mParticleAnimator.getAnimatedValue());
                mPaint.setColor(p.color);
                mPaint.setAlpha((int) (Color.alpha(p.color) * p.alpha));
                canvas.drawCircle(p.cx, p.cy, p.radius, mPaint);//
            }
        }
    }
    public void boom(final View view) {

        if(view.getVisibility()!=View.VISIBLE||view.getAlpha()==0 ||(mParticleAnimator!=null && mParticleAnimator.isRunning())){
            return;
        }
        view.post(new Runnable() {
            @Override
            public void run() {
                int[] location = new int[2];
                view.getLocationInWindow(location);
                Rect rect = new Rect(location[0],location[1],location[0]+view.getMeasuredWidth(),location[1]+view.getMeasuredHeight());

                mParticles = Particle.generateParticles(getCacheBitmapFromView(view), rect);
                mParticleAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                mParticleAnimator.setDuration(DURATION);
                mParticleAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        if (mOnAnimationListener!=null)
                            mOnAnimationListener.onAnimationStart(view,animation);
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (mOnAnimationListener!=null)
                            mOnAnimationListener.onAnimationEnd(view,animation);
                    }
                });
                mParticleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        invalidate();
                    }
                });
                mParticleAnimator.start();
            }
        });
    }

    private  Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }
    private void attachToActivity(Activity activity) {
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        rootView.addView(this, lp);
    }
    public interface OnAnimationListener{
        void onAnimationStart(View v,Animator animation);
        void onAnimationEnd(View v,Animator animation);
    }
}
