package com.marcellohaddeman.programmeren3project;

import android.os.Parcel;
import android.os.Parcelable;

public class Element implements Parcelable {
    private int objectId;
    private String identificatie;
    private String titel;
    private String geografischeLigging;
    private String kunstenaar;
    private String beschrijving;
    private String materiaal;
    private String ondergrond;
    private long plaatsingDatum;
    private String imageUrl;
    private double geoX;
    private double geoY;

    public Element(int objectId, String titel, String geografischeLigging, String kunstenaar, String beschrijving, String materiaal, String ondergrond, int plaatsingDatum, String imageUrl, double geoX, double geoY) {
        this.objectId = objectId;
        this.titel = titel;
        this.geografischeLigging = geografischeLigging;
        this.kunstenaar = kunstenaar;
        this.beschrijving = beschrijving;
        this.materiaal = materiaal;
        this.ondergrond = ondergrond;
        this.plaatsingDatum = plaatsingDatum;
        this.imageUrl = imageUrl;
        this.geoX = geoX;
        this.geoY = geoY;
    }

    public Element() {
    }

    protected Element(Parcel in) {
        objectId = in.readInt();
        identificatie = in.readString();
        titel = in.readString();
        geografischeLigging = in.readString();
        kunstenaar = in.readString();
        beschrijving = in.readString();
        materiaal = in.readString();
        ondergrond = in.readString();
        plaatsingDatum = in.readInt();
        imageUrl = in.readString();
        geoX = in.readDouble();
        geoY = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(objectId);
        dest.writeString(identificatie);
        dest.writeString(titel);
        dest.writeString(geografischeLigging);
        dest.writeString(kunstenaar);
        dest.writeString(beschrijving);
        dest.writeString(materiaal);
        dest.writeString(ondergrond);
        dest.writeLong(plaatsingDatum);
        dest.writeString(imageUrl);
        dest.writeDouble(geoX);
        dest.writeDouble(geoY);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Element> CREATOR = new Creator<Element>() {
        @Override
        public Element createFromParcel(Parcel in) {
            return new Element(in);
        }

        @Override
        public Element[] newArray(int size) {
            return new Element[size];
        }
    };

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getGeografischeLigging() {
        return geografischeLigging;
    }

    public void setGeografischeLigging(String geografischeLigging) {
        this.geografischeLigging = geografischeLigging;
    }

    public String getKunstenaar() {
        return kunstenaar;
    }

    public void setKunstenaar(String kunstenaar) {
        this.kunstenaar = kunstenaar;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getMateriaal() {
        return materiaal;
    }

    public void setMateriaal(String materiaal) {
        this.materiaal = materiaal;
    }

    public String getOndergrond() {
        return ondergrond;
    }

    public void setOndergrond(String ondergrond) {
        this.ondergrond = ondergrond;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getGeoX() {
        return geoX;
    }

    public void setGeoX(double geoX) {
        this.geoX = geoX;
    }

    public double getGeoY() {
        return geoY;
    }

    public void setGeoY(double geoY) {
        this.geoY = geoY;
    }

    public long getPlaatsingDatum() {
        return plaatsingDatum;
    }

    public void setPlaatsingDatum(long plaatsingDatum) {
        this.plaatsingDatum = plaatsingDatum;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    public String getIdentificatie() {
        return identificatie;
    }

    public void setIdentificatie(String identificatie) {
        this.identificatie = identificatie;
    }
}
