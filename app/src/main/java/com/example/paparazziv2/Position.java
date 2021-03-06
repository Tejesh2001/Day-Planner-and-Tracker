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

    public Position(LatLng newPos) { position = newPos; }

    public Position(double lat, double lng) {
        position = new LatLng(lat, lng);
    }

    public LatLng getPosition() {
        return position;
    }

    public int getClubCount() {
        return clubCount;
    }

    public int getEatCount() {
        return eatCount;
    }

    public int getFestivalCount() {
        return festivalCount;
    }

    public int getSocializeCount() {
        return socializeCount;
    }

    public int getAcademicCount() {
        return academicCount;
    }

    public void incrementClub() {
        clubCount++;
    }

    public void incrementEat() {
        eatCount++;
    }

    public void incrementFestival() {
        festivalCount++;
    }

    public void incrementSocial() {
        socializeCount++;
    }

    public void incrementAcademic() {
        academicCount++;
    }

    public Position(Parcel source) {
        String[] data = new String[7];

        source.readStringArray(data);
        position = new LatLng(Double.parseDouble(data[0]), Double.parseDouble(data[1]));
        clubCount = Integer.parseInt(data[2]);
        eatCount = Integer.parseInt(data[3]);
        festivalCount = Integer.parseInt(data[4]);
        socializeCount = Integer.parseInt(data[5]);
        academicCount = Integer.parseInt(data[6]);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{Double.toString(position.latitude),
                Double.toString(position.longitude), Integer.toString(clubCount),
                Integer.toString(eatCount), Integer.toString(festivalCount),
                Integer.toString(socializeCount), Integer.toString(academicCount)});
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
