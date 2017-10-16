package com.example.shang.indexbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Shang on 2017/10/16.
 */
public class IndexBarView extends View {

    private String[] alpha = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
            , "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private Paint paint;
    private float cellWidth;
    private float cellHeight;

    /**
     * 回调接口
     */
    public OnUpdateLetter listener;
    public interface OnUpdateLetter{
        void onUpdate(String letter);
    }
    public void setOnUpdateLetterListener(OnUpdateLetter listener){
        this.listener = listener;
    }

    public IndexBarView(Context context) {
        this(context, null);
    }

    public IndexBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(18);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < alpha.length; i++){
            int x = (int) (cellWidth / 2.0 - paint.measureText(alpha[i]) / 2.0);
            Rect bounds = new Rect();
            paint.getTextBounds(alpha[i], 0, alpha[i].length(), bounds);
            int textHeight = bounds.height();
            int y = (int) (cellHeight / 2.0 + textHeight / 2.0 + i * cellHeight);
            canvas.drawText(alpha[i], x, y, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellWidth = getMeasuredWidth();
        cellHeight = getMeasuredHeight() / alpha.length;
    }

    int lastIndex = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = -1;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                index = (int) (event.getY() / cellHeight);
                if (index >= 0 && index < alpha.length){
                    if (index != lastIndex){
                        if (listener != null){
                            listener.onUpdate(alpha[index]);
                        }
                        lastIndex = index;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                index = (int) (event.getY() / cellHeight);
                if (index >= 0 && index < alpha.length){
                    if (index != lastIndex){
                        if (listener != null){
                            listener.onUpdate(alpha[index]);
                        }
                        lastIndex = index;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
