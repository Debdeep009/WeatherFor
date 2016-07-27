package safacule.com.weatherfor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Debde_000 on 24-07-2016.
 */
public class CurrentWeather {
    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;
//    private String mImage;

    public String getmTimeZone() {
        return mTimeZone;
    }

    public void setmTimeZone(String mTimeZone) {
        this.mTimeZone = mTimeZone;
    }

    private String mTimeZone;


    public String getmIcon() {
        return mIcon;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public int getIconId() {
        // clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        int iconId = R.drawable.clear_day;

        if (mIcon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (mIcon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (mIcon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (mIcon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (mIcon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (mIcon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (mIcon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (mIcon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public int getmTemperature() {
//        return (((((int) Math.round(mTemperature))-32)/9)*5) ;
            return (int) Math.round(mTemperature);
    }   //  ;p :D

    public void setmTemperature(double mTemperature) {
        this.mTemperature = mTemperature;
    }

    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getmTimeZone()));
        Date date = new Date(getmTime()*1000);
        String timeString = formatter.format(date);

        return timeString;
    }

    public int getmHumidity() {
        return (int) (mHumidity*100);
    }

    public void setmHumidity(double mHumidity) {
        this.mHumidity = mHumidity;
    }

    public int getmPrecipChance() {
        double precipPercentage = mPrecipChance*100;
        return (int) Math.round(precipPercentage);
    }

    public void setmPrecipChance(double mPrecipChance) {
        this.mPrecipChance = mPrecipChance;
    }

    public String getmSummary() {
        return mSummary;
    }

    public void setmSummary(String mSummary) {
        this.mSummary = mSummary;
    }


    public int getImageId() {

         int imageId = R.drawable.clear_day_wall;

        if (mIcon.equals("clear-day")) {
            imageId = R.drawable.clear_day_wall;
        }
        else if (mIcon.equals("clear-night")) {
            imageId = R.drawable.clear_night_wall;
        }
        else if (mIcon.equals("rain")) {
            imageId = R.drawable.rain_wall;
        }
        else if (mIcon.equals("snow")) {
            imageId = R.drawable.snow_wall;
        }
        else if (mIcon.equals("sleet")) {
            imageId = R.drawable.sleet_wall;
        }
        else if (mIcon.equals("wind")) {
            imageId = R.drawable.windy_wall;
        }
        else if (mIcon.equals("fog")) {
            imageId = R.drawable.fog_wall;
        }
        else if (mIcon.equals("cloudy")) {
            imageId = R.drawable.cloudy_wall;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            imageId = R.drawable.partly_cloudy_wall;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            imageId = R.drawable.cloudy_night_wall;
        }

        return imageId;
    }
}
