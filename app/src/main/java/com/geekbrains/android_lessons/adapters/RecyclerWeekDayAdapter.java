package com.geekbrains.android_lessons.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.android_lessons.Constants;
import com.geekbrains.android_lessons.R;
import com.geekbrains.android_lessons.SharedPreferencesManager;
import com.geekbrains.android_lessons.WeekDay;
import com.geekbrains.android_lessons.fragments.MainFragment;
import com.geekbrains.android_lessons.model.AllList;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;


public class RecyclerWeekDayAdapter extends RecyclerView.Adapter<RecyclerWeekDayAdapter.ViewHolder> {
    private final ArrayList<WeekDay> days = new ArrayList<>();
    private static final SharedPreferencesManager sPrefs= MainFragment.sPrefs;
    private int shift=0;

    @NonNull
    @Override
    public RecyclerWeekDayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weekday_item, parent, false);
        return new RecyclerWeekDayAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerWeekDayAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return days.size();
    }

    public void addItems(ArrayList<WeekDay> arrayList) {
        days.addAll(arrayList);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CardView layout;
        TextView date, weekDay,  degrees;
        ImageView weatherIcon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.ItemLayoutWeekDay);
            date = itemView.findViewById(R.id.dateTextView);
            weekDay = itemView.findViewById(R.id.weatherDayView);
            weatherIcon = itemView.findViewById(R.id.weatherIconWeekDay);
            degrees = itemView.findViewById(R.id.degreesWeekDay);
        }


        public  String getCityName() {
            return sPrefs.retrieveString(Constants.tag_cityName, "Moscow");
        }

        @SuppressLint("DefaultLocale")
        public void bind(int position) {
            WeekDay day = days.get(position);

            setData(Constants.urlWeather7Days +
                    URLEncoder.encode(getCityName()) +
                    Constants.lang+
                    Locale.forLanguageTag(Locale.getDefault().getLanguage())+
                    Constants.weatherKey);

            weekDay.setText(day.getDayOfWeek());
            date.setText(day.getDay());
        }


        public void setData(String url) {
            try {
                final URL uri = new URL(url);
                final Handler handler = new Handler(Looper.getMainLooper()); // Запоминаем основной поток
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void run() {
                        HttpsURLConnection urlConnection = null;
                        try {
                            urlConnection = (HttpsURLConnection) uri.openConnection();
                            urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                            urlConnection.setReadTimeout(10000); // установка таймаута - 10 00 миллисекунд
                            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                            String result = getLines(in);
                            //System.out.println(result);
                            // преобразование данных запроса в модель
                            Gson gson = new Gson();
                            final AllList list = gson.fromJson(result, AllList.class);
                            // Возвращаемся к основному потоку
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    displayWeather(list);
                                }
                            });
                        } catch (Exception e) {
                            //Log.e(Constants.TAG, getString(R.string.fall), e);
                            e.printStackTrace();
                        } finally {
                            if (null != urlConnection) {
                                urlConnection.disconnect();
                            }
                        }
                    }
                }).start();
            } catch (MalformedURLException e) {
                //Log.e(Constants.TAG, getString(R.string.fall), e);
                e.printStackTrace();
            }
        }


        @RequiresApi(api = Build.VERSION_CODES.N)
        public String getLines(BufferedReader in) {
            return in.lines().collect(Collectors.joining("\n"));
        }

        @SuppressLint({"DefaultLocale", "SetTextI18n"})
        public void displayWeather(AllList list){

            if(shift<=list.getList().length) {
                switch (sPrefs.retrieveInt(Constants.tag_temp, Constants.POSTFIX_KELVIN)) {
                    case 0:
                        degrees.setText(String.format("%.1f", list.getList()[shift].getMain().getTemp()) + "K\u00B0");
                        break;
                    case 1:
                        String parameter = String.valueOf(list.getList()[shift].getMain().getTemp());
                        parameter = parameter.replaceAll(",", ".");
                        double value = Double.parseDouble(parameter) - 273.15;
                        degrees.setText(String.format("%.1f", value) + "С\u00B0");
                        break;
                }
                String icon = list.getList()[shift].getWeather()[0].getIcon();
                new MainFragment.DownloadImageTask(weatherIcon).execute("http://openweathermap.org/img/wn/" + icon + "@4x.png");
                weatherIcon.setColorFilter(Color.WHITE);
//                String updatedOn = DateFormat.getDateTimeInstance().format(new Date(1000 * list.getList()[shift].getDate()));
//                System.out.println(updatedOn);
                shift += 8;
            }

        }
    }

}