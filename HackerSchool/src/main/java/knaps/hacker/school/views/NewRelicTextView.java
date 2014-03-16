package knaps.hacker.school.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by lisaneigut on 15 Sep 2013.
 */
public class NewRelicTextView extends TextView {

    private int mStyleType;

    public NewRelicTextView(Context context) {
        super(context);
    }

    public NewRelicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewRelicTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        if (!isInEditMode()) {
            super.setTypeface(
                    TypefaceCache.getInstance().getTypeface(getContext(), TypefaceCache.NEW_RELIC));
        }
    }
}

