package ru.rumedo.rumedoregapp.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import ru.rumedo.rumedoregapp.R;

public class CustomView extends View {

    private Paint paint;
    private int width;
    private int height;
    private int background;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs, 0, 0);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs, defStyleAttr, 0);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttributes(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        // Получаем списолк атрибутов контекста
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView, defStyleAttr, defStyleRes);

        setHeight(typedArray.getInteger(R.styleable.CustomView_cv_height, 100));
        setWidth(typedArray.getInteger(R.styleable.CustomView_cv_width, 100));
        setBackground(typedArray.getColor(R.styleable.CustomView_cv_background, Color.BLUE));

        typedArray.recycle();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(background);
        paint.setStyle(Paint.Style.FILL);
    }

    private void setWidth(int width) {
        this.width = width;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    private void setBackground(int background) {
        this.background = background;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, width, height, paint);
    }
}
