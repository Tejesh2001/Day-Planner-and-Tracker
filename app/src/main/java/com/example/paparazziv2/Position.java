package com.example.paparazziv2;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

// import java.util.Locale;

/**
 * This class is a wrapper class used to pass around a
 * LatLng object.
 */
public class Position implements Parcelable {
    /** The LatLng that needed to be passed around. */
    private LatLng position;
    private int clubCount = 0;
    private int eatCount = 0;
    private int festivalCount = 0;
    private int socializeCount = 0;
    private int academicCount = 0;

    public Position(double lat, double lng) {
        position = new LatLng(lat, lng);
    }

    public LatLng getPosition() {
        return position;
    }

    public Position(Parcel source) {
        String[] data = new String[2];

        source.readStringArray(data);
        position = new LatLng(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{Double.toString(position.latitude),
                Double.toString(position.longitude)});
    }

    public static final Parcelable.Creator<Position> CREATOR =
            new Parcelable.Creator<Position>() {

                @Override
                public Position createFromParcel(Parcel source) {
                    return new Position(source);  //using parcelable constructor
                }

                @Override
                public Position[] newArray(int size) {
                    return new Position[size];
                }
            };
}
