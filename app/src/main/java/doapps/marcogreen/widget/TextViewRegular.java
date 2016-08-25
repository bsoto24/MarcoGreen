package doapps.marcogreen.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import doapps.marcogreen.util.FontUtil;

/**
 * Created by Bryam Soto on 18/08/2016.
 */
public class TextViewRegular extends TextView {

    public TextViewRegular(Context context) {
        super(context);
        init(context);
    }

    public TextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTypeface(FontUtil.getRobotoRegular(context));
    }
}
