package com.nordicsemi.User;

import android.app.Activity;
import android.os.Bundle;

import com.nordicsemi.nrfUARTv2.R;

public class Measure extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure);
    }
}
