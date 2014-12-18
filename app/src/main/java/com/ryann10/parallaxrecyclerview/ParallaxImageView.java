package com.ryann10.parallaxrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Ryan on 2014-12-18.
 */
public class ParallaxImageView extends ImageView {
    private float focalPoint;
    private int movement;
    private float parallaxOffset;
    private int screenWidth, screenHeight;

    public ParallaxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray localTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ParallaxImageView);
        this.focalPoint = localTypedArray.getFloat(1, 0.5F);
        this.movement = ((int) localTypedArray.getDimension(0, -(int) (30.0F * context.getResources().getDisplayMetrics().density)));
        localTypedArray.recycle();

        init();
    }

    public ParallaxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setScaleType(ImageView.ScaleType.MATRIX);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        screenWidth = display.getWidth();  // deprecated
        screenHeight = display.getHeight();
    }

    protected boolean setFrame(int left, int top, int right, int bottom) {
        Drawable localDrawable = getDrawable();
        if (localDrawable != null) {
            Matrix localMatrix = getImageMatrix();
            float f = (right - left) / localDrawable.getIntrinsicWidth();
            //int i = (int) (-localDrawable.getIntrinsicHeight() * this.focalPoint);
            localMatrix.setTranslate(0.0F, focalPoint);

            float width = screenWidth / (float) localDrawable.getIntrinsicWidth();
            localMatrix.postScale(width, width, 0.0F, 0.0F);
            //localMatrix.setTranslate(0, 100);

            localMatrix.postScale(f, f, 0.0F, 0.0F);
            float y = movement * parallaxOffset;
            localMatrix.postTranslate(0.0F, y);//Math.min((bottom - top) / 2 + this.parallaxOffset, f * -i));
            //Log.d("Y:", y + ", parallaxOffset:" + parallaxOffset);
            setImageMatrix(localMatrix);
        }
        return super.setFrame(left, top, right, bottom);
    }

    public void setParallaxOffset(float paramDouble) {
        this.parallaxOffset = paramDouble / (float)screenHeight;
        setFrame(getLeft(), getTop(), getRight(), getBottom());
        invalidate();
    }
}
