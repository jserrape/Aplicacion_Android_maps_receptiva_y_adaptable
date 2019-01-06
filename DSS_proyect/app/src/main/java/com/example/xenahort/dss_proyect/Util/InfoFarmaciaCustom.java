/*
 * *
 *  * Created by Juan Carlos Serrano Pérez on 6/01/19 13:07
 *  * Any question send an email to juan.carlos.wow.95@gmail.com
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 29/12/18 15:26
 *
 */

package com.example.xenahort.dss_proyect.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.xenahort.dss_proyect.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoFarmaciaCustom  implements GoogleMap.InfoWindowAdapter{
    private final View mWindow;
    private Context mContext;

    public InfoFarmaciaCustom(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.info_farmacia, null);
    }

    private void rendowWindowText(Marker marker, View view){

        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.title);

        if(!title.equals("")){
            tvTitle.setText(title);
        }

        String snippet = marker.getSnippet();
        TextView tvSnippet = (TextView) view.findViewById(R.id.snippet);

        if(!snippet.equals("")){
            tvSnippet.setText(snippet);
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}
