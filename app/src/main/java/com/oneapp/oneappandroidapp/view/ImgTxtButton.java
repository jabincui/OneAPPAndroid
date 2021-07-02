package com.oneapp.oneappandroidapp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.oneapp.oneappandroidapp.R;


/**
 * Fixed size ui component : 120dp * 120dp
 * use mSrc as image src, and mText as button text
 */
public class ImgTxtButton extends RelativeLayout {

//    private static final String TAG = ImgTxtButton.class.getSimpleName();

    /**
     * new 实例化时用
     * @param context
     */
    public ImgTxtButton(Context context) {
        this(context, null);
    }

    /**
     * xml布局文件中用
     * @param context
     * @param attrs
     */
    public ImgTxtButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public ImgTxtButton(Context context, @Nullable AttributeSet attrs, int defaultStyle) {
        this(context, attrs, defaultStyle, 0);
    }

    public ImgTxtButton(Context context, AttributeSet attrs, int defaultStyle, int defStyleRes) {
        super(context, attrs, defaultStyle, defStyleRes);
        // layout HERE
        View view = View.inflate(context, R.layout.img_txt_button, this);
        ImageView imageView = view.findViewById(R.id.img_txt_btn_image);
        TextView textView = view.findViewById(R.id.img_txt_btn_text);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImgTxtButton);
        String text = ta.getString(R.styleable.ImgTxtButton_android_text);
        Drawable src = ta.getDrawable(R.styleable.ImgTxtButton_android_src);
        if (text != null) textView.setText(text);
        if (src != null) imageView.setImageDrawable(src);
        ta.recycle();

        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) {
                    mCallback.onclick(v);
                }
            }
        });
        textView.setOnClickListener(v -> {
            if (mCallback != null) {
                mCallback.onclick(v);
            }
        });
    }


    private MCallback mCallback;
    public void setMCallback(MCallback mCallback) {
        this.mCallback = mCallback;
    }
    public interface MCallback {
        void onclick(View v);
    }
}