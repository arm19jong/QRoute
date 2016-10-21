package com.teamsmokeweed.qroute.bar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.teamsmokeweed.qroute.R;

/**
 * Created by jongzazaal on 21/10/2559.
 */

public class BarResultQr extends RelativeLayout
{
    private Button backButton;

    public BarResultQr(Context context)
    {
        super(context);
        init(context);
    }

    public BarResultQr(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public BarResultQr(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(final Context context)
    {
        final View rootView = inflate(context, R.layout.bar_result_read, this);
        backButton = (Button) rootView.findViewById(R.id.backButton);

        backButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                App.getBus().post(new BlackButtonClicked());
            }
        });

    }
}