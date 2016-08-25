package doapps.marcogreen.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import doapps.marcogreen.util.FontUtil;

/**
 * Created by Bryam Soto on 18/08/2016.
 */
public class TextViewThin extends TextView {

    public TextViewThin(Context context) {
        super(context);
        init(context);
    }

    public TextViewThin(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewThin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTypeface(FontUtil.getRobotoThin(context));
    }
}
