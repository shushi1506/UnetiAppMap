package uneti.edu.vn.unetituyensinhapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Shushi on 6/10/2017.
 */

public class CardViewCustom extends LinearLayout {
    private TextView textView;
    public CardViewCustom(Context context) {
        super(context);
        initializeViews(context);
    }
    public CardViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public CardViewCustom(Context context,
                       AttributeSet attrs,
                       int defStyle) {
        super(context, attrs, defStyle);
        initializeViews(context);
    }

    /**
     * Inflates the views in the layout.
     *
     * @param context
     *           the current context for the view.
     */
    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_view_custom, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


       textView=(TextView)this.findViewById(R.id.text_v);
    }
}
