package com.teamsmokeweed.qroute.bar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.teamsmokeweed.qroute.R;

/**
 * Created by jongzazaal on 26/10/2559.
 */


public class BarGenQrMap extends RelativeLayout
{
    private Button backButton, doneButton;

    public BarGenQrMap(Context context)
    {
        super(context);
        init(context);
    }

    public BarGenQrMap(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public BarGenQrMap(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(final Context context)
    {
        final View rootView = inflate(context, R.layout.bar_gen_qr, this);
        backButton = (Button) rootView.findViewById(R.id.backButton);
        doneButton = (Button) rootView.findViewById(R.id.doneButton);

        backButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                App.getBus().post(new BlackButtonClicked());
            }
        });
        doneButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                App.getBus().post(new DoneButtonClicked());
            }
        });

    }
}