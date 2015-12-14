package Utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.example.bubblepedia.R;

/**
 * Created by Lenovo on 12/13/2015.
 */
public class WikiSearchResultTextView extends TextView {
    private boolean mToggle;

    public boolean isToggle() {
        return mToggle;
    }

    /**
    Notice that setShowText calls invalidate() and requestLayout(). These calls are crucial to ensure that the view behaves reliably.
    You have to invalidate the view after any change to its properties that might change its appearance,
    so that the system knows that it needs to be redrawn. Likewise, you need to request a new layout if a property changes that might affect the size or shape of the view. Forgetting these method calls can cause hard-to-find bugs.
     */
    public void setToggle(boolean mToggle) {
        this.mToggle = mToggle;
        invalidate();
        requestLayout();
    }

    public WikiSearchResultTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WikiSearchResultTextViewStyle, 0, 0);

        try {
            mToggle = a.getBoolean(R.styleable.WikiSearchResultTextViewStyle_toggle, false);
        } finally {
            a.recycle();
        }
    }
}
