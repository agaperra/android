package com.geekbrains.android_lessons;

import android.app.Activity;

import com.geekbrains.android_lessons.fragments.MainFragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class WeekDay implements Serializable {

    private String dayOfWeek;
    private String degrees;
    private String  iconW;
    private final SimpleDateFormat dateFormat=Constants.dateFormat;
    private static final SimpleDateFormat weekDayFormat=Constants.weekDayFormat;
    private String date;
    private static final SharedPreferencesManager sPrefs= MainFragment.sPrefs;

    private static ArrayList<String> days;

    public static ArrayList<WeekDay> getDays(int value, Activity parent) {

        ArrayList<WeekDay> arrayList = new ArrayList<>();
        days = new ArrayList<>(Arrays.asList(parent.getResources().getStringArray(R.array.weekday)));

        int shift = 0;

        for (int i = 0; i < value; i++) {
            WeekDay day = new WeekDay();
            day.generateData(shift);
            arrayList.add(day);
            if (day.dayOfWeek.equals(days.get(days.size() - 1))) {
                String rawString = weekDayFormat.format(Calendar.getInstance().getTime());
                String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
                shift = -days.indexOf(currentDay);
                continue;
            }
            shift++;
        }
        return arrayList;
    }

    private void generateData(int shift) {
        Calendar now = Calendar.getInstance();
        if (shift>=0) {
            now.add(Calendar.DAY_OF_MONTH, shift);
        }
        else {
            now.add(Calendar.DAY_OF_MONTH, shift+7);
        }
        date = dateFormat.format(now.getTime());
        dayOfWeek = getDayName(shift);


    }
//
//    public  String getCityName() {
//        return sPrefs.retrieveString(Constants.tag_cityName, "Moscow");
//    }

    private String getDayName(int shift) {
        String rawString = weekDayFormat.format(Calendar.getInstance().getTime());
        String currentDay = rawString.substring(0, 1).toUpperCase() + rawString.substring(1);
        return days.get(days.indexOf(currentDay) + shift);
    }

//
//    public String getIcon(){
//        return iconW;}
//    public String getDegrees(){
//        return degrees;}
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public String  getDay(){
        return date;
    }
//
//    public void setData(String url) {
//        try {
//            final URL uri = new URL(url);
//            System.out.println(uri);
//            final Handler handler = new Handler(Looper.getMainLooper()); // Запоминаем основной поток
//            new Thread(new Runnable() {
//                @RequiresApi(api = Build.VERSION_CODES.N)
//                public void run() {
//                    HttpsURLConnection urlConnection = null;
//                    try {
//                        urlConnection = (HttpsURLConnection) uri.openConnection();
//                        urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
//                        urlConnection.setReadTimeout(10000); // установка таймаута - 10 00 миллисекунд
//                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
//                        String result = getLines(in);
//                        //System.out.println(result);
//                        // преобразование данных запроса в модель
//                        Gson gson = new Gson();
//                        final AllList list = gson.fromJson(result, AllList.class);
//                        // Возвращаемся к основному потоку
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                               displayWeather(list);
//                            }
//                        });
//                    } catch (Exception e) {
//                        //Log.e(Constants.TAG, getString(R.string.fall), e);
//                        e.printStackTrace();
//                    } finally {
//                        if (null != urlConnection) {
//                            urlConnection.disconnect();
//                        }
//                    }
//                }
//            }).start();
//        } catch (MalformedURLException e) {
//            //Log.e(Constants.TAG, getString(R.string.fall), e);
//            e.printStackTrace();
//        }
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public String getLines(BufferedReader in) {
//        return in.lines().collect(Collectors.joining("\n"));
//    }
//
//    @SuppressLint("DefaultLocale")
//    public void displayWeather(AllList list){
//        //degrees=Double.parseDouble(String.format("%.0f", list.getList()[0].getMain().getTemp()));
//        //System.out.println(degrees);
//       // list.getList()[0].getMain().setTemp(degrees);
//        degrees=String.valueOf(list.getList()[9].getMain().getTemp());
//        System.out.println(degrees);
//        String  icon = list.getList()[9].getWeather()[0].getIcon();
//        iconW = "http://openweathermap.org/img/wn/" + icon + "@4x.png";
//        DateFormat df = DateFormat.getDateTimeInstance();
//        String updatedOn = df.format(new Date(1000 * list.getList()[9].getDate()));
//        System.out.println(updatedOn);
//    }

}

