package safacule.com.weatherfor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private CurrentWeather mCurrentWeather;

    // Using the ButterKnife for reducing boilerplate code
    @BindView(R.id.timeLabel) TextView mTimeLabel;
    @BindView(R.id.humidityValue) TextView mHumidityValue;
    @BindView(R.id.precipValue) TextView mPrecipValue;
    @BindView(R.id.summaryLabel) TextView mSummaryLabel;
    @BindView(R.id.iconImageView) ImageView mIconImageView;
    @BindView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @BindView(R.id.parentLayout) RelativeLayout mRelativeLayout;
    @BindView(R.id.refreshImageView) ImageView mRefreshImageView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        final double latitude = 26.6642;
        final double longitude = 89.1384;

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longitude);
            }
        });
        getForecast(latitude, longitude);
//        Log.d(TAG, "Main UI code is running! ");
    }

    private void getForecast(double latitude, double longitude) {

        String apiKey = "4f9fd5b1cf034d775d5d2daf804cdf26";
        String forecastUrl = getString(R.string.forecast_Uri) + apiKey +
                "/" + latitude + "," + longitude;

        if (isNetworkAvailable()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toggleRefresh();
                }
            });

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(forecastUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError(getString(R.string.error_title3)
                            ,getString(R.string.error_message3));
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);

                        if (response.isSuccessful()) {
                            mCurrentWeather = getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserAboutError(getString(R.string.error_title1),
                                    getString(R.string.error_message1));
                        }
                    } catch (IOException|JSONException e) {
                        //Log.e(TAG, "Exception : ", e);
                    }
                }
            });

        } else {
            alertUserAboutError(getString(R.string.error_title2)
                    ,getString(R.string.error_message2));
        }
    }

    private void toggleRefresh() {
        if (mProgressBar.getVisibility() == View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        mTemperatureLabel.setText(mCurrentWeather.getmTemperature() + "");
        mSummaryLabel.setText(mCurrentWeather.getmSummary());
        mHumidityValue.setText(mCurrentWeather.getmHumidity() + "%");
        mPrecipValue.setText(mCurrentWeather.getmPrecipChance() + "%");
        mTimeLabel.setText("At "+mCurrentWeather.getFormattedTime()+" it will be");

        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), mCurrentWeather.getIconId());
        mIconImageView.setImageDrawable(drawable);

        mRelativeLayout.setBackground(ContextCompat.getDrawable(this, mCurrentWeather.getImageId()));
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        //Log.i(TAG, timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setmHumidity(currently.getDouble("humidity"));
        currentWeather.setmTime(currently.getLong("time"));
        currentWeather.setmPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setmIcon(currently.getString("icon"));
        currentWeather.setmTemperature(currently.getDouble("temperature"));
        currentWeather.setmSummary(currently.getString("summary"));
        currentWeather.setmTimeZone(timezone);

        //Log.i(TAG, currentWeather.getFormattedTime());

        return currentWeather;
    }

    private boolean isNetworkAvailable() {
        boolean isAvailable = false;
        try {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            isAvailable = networkInfo != null && networkInfo.isConnected();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return isAvailable;
    }

    private void alertUserAboutError(String title, String message) {
            Bundle bundle = new Bundle();

            bundle.putString(AlertDialogFragment.TITLE_ID, title);
            bundle.putString(AlertDialogFragment.MESSAGE_ID, message);

            AlertDialogFragment dialog = new AlertDialogFragment();
            dialog.setArguments(bundle);
            dialog.show(getFragmentManager(), "Error_dialog");
    }
}

















































