package ru.uxapps.counterlessons;

import android.os.Handler;
import android.os.Message;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class FastCountButton implements View.OnTouchListener, Handler.Callback {

    private static final int FAST_COUNT_INTERVAL_MS = 150;
    private static final int FAST_COUNT_MSG = 0;
    private final Handler mHandler = new Handler(this);

    private final View mView;

    private boolean mFastCounting;

    public FastCountButton(View view, Runnable action) {
        mView = view;
        view.setOnClickListener(v -> action.run());
        view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mView.setPressed(true);
                mHandler.sendEmptyMessageDelayed(FAST_COUNT_MSG, ViewConfiguration.getLongPressTimeout());
                break;
            case MotionEvent.ACTION_UP:
                if (!mFastCounting) mView.performClick();
                //no break
            case MotionEvent.ACTION_CANCEL:
                mHandler.removeMessages(FAST_COUNT_MSG);
                mFastCounting = false;
                mView.setPressed(false);
                mView.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == FAST_COUNT_MSG) {
            if (!mFastCounting) {
                mFastCounting = true;
                mView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                mView.getParent().requestDisallowInterceptTouchEvent(true);
            }
            mView.performClick();
            mHandler.sendEmptyMessageDelayed(FAST_COUNT_MSG, FAST_COUNT_INTERVAL_MS);
        }
        return false;
    }
}
