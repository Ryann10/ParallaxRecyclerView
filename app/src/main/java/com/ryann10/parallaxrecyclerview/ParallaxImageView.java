package com.ryann10.parallaxrecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

public class ParallaxImageView extends ImageView {
    private float start;
    private int movement;
    private float parallaxOffset;
    private int screenHeight, screenWidth;

    public ParallaxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ParallaxImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray localTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ParallaxImageView);
        this.start = localTypedArray.getFloat(1, 0.5F);
        this.movement = ((int) localTypedArray.getDimension(0, -(int) (30.0F * context.getResources().getDisplayMetrics().density)));
        localTypedArray.recycle();

        setScaleType(ImageView.ScaleType.MATRIX);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenHeight = size.y;
        screenWidth = size.x;
    }

    protected boolean setFrame(int left, int top, int right, int bottom) {
        Drawable localDrawable = getDrawable();
        if (localDrawable != null) {
            Matrix localMatrix = getImageMatrix();
            float f = (right - left) / localDrawable.getIntrinsicWidth();
            localMatrix.setTranslate(0.0F, start);

            float width = screenWidth / (float) localDrawable.getIntrinsicWidth();
            localMatrix.postScale(width, width, 0.0F, 0.0F);

            localMatrix.postScale(f, f, 0.0F, 0.0F);
            float y = movement * parallaxOffset;
            localMatrix.postTranslate(0.0F, y);
            setImageMatrix(localMatrix);
        }
        return super.setFrame(left, top, right, bottom);
    }

    public void setParallaxOffset(float top) {
        this.parallaxOffset = top / (float) screenHeight;
        setFrame(getLeft(), getTop(), getRight(), getBottom());
        invalidate();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
    }
}
