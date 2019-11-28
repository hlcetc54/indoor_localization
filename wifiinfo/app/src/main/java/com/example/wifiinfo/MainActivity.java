
package com.example.wifiinfo;
import android.os.SystemClock;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.os.SystemClock;
import android.os.SystemClock;

public class MainActivity extends AppCompatActivity  {

    WifiManager wifiMan;
    List<ScanResult> results;
    TextView result_view;

    //List<String> wifi_data;
    //wifi_data = new ArrayList<>();

    private String wifi_string = String.format("");
    private String wifi_array_InString = String.format("");
    private int PERMISSION_CODE = 1002;
    private static int file_name_counter = 0;
    private int press_num = 1;

    private int[] wifi_ID = new int[12];
    //String[] wifi_ID = new String[12];
    private String default_wifi_strength = String.format("NULL");


    //private String aaa = String.format("a\n");
    //private String bbb = String.format("b\n");

    private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //result_view.append(String.format("*** THIS IS BroadcastReceiver ***\n"));
            //Log.e("INFO", "*** THIS IS BroadcastReceiver ***\n");
            for(int i = 1; i<2; i++) {
                results = wifiMan.getScanResults();
                // Initialize the array for Wifi dataset
                //wifi_data = new ArrayList<>();

                // Clear result view
                result_view.setText("");
                String str_press_num = String.valueOf(press_num);
                str_press_num = str_press_num+String.format("\n");
                result_view.append(str_press_num);
                press_num = press_num + 1;

                int counter = 1;
                for (ScanResult result : results) {
                    String prefix = String.valueOf(counter);
                    counter = counter + 1;
                    String q = String.format("%s)SSID: %s BSSID: %s RSSI: %s \n", prefix, result.SSID, result.BSSID, result.level);
                    //Log.e("INFO", q);
                    //result_view.append(String.format("SSID: %s BSSID: %s RSSI: %s \n", result.SSID, result.BSSID, result.level));

                    does_exist(result.BSSID, result.level);

                    //wifi_data.add(q);
                    wifi_string = wifi_string + q;
                }

                for(int h=0; h<12; h++){
                    if(wifi_ID[h] == 0){
                        String woha = String.valueOf(h+1);
                        //result_view.append(String.format("%s wifi does not exist. ERROR !!! \n", woha));
                        String kkk = String.valueOf(wifi_ID[h]);
                        //result_view.append(String.format("Signal strength: %s\n\n",kkk));
                        result_view.append(String.format("%s:N\n",woha));
                    }
                    else{
                        String woha2 = String.valueOf(h+1);
                        //result_view.append(String.format("%s wifi exists\n", woha2));
                        String kkk = String.valueOf(wifi_ID[h]);
                        //result_view.append(String.format("Signal strength: %s\n\n",kkk));
                        result_view.append(String.format("%s:Y\n",woha2));
                    }
                }


                String pr = String.valueOf(i);
                wifi_string = wifi_string + String.format(" %s *******************************************\n", pr);
                //writeToFile(wifi_string);
                //wifi_data.add(String.format("***************************************************"));
                //writeToFile(wifi_data.toString());

                String datapoint = Arrays.toString(wifi_ID);//wifi_ID is an array which contains wifi signals, should be used for inference
                wifi_array_InString = wifi_array_InString + datapoint + String.format("\n");
                //result_view.append(String.format("Current Datapoint\n"));
                //result_view.append(String.format("%s\n\n",datapoint));

                writeToFile(wifi_array_InString);
                //result_view.append(String.format("*** AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ***\n"));
                //Log.e("INFO", "*** AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA ***\n");
                //SystemClock.sleep(2000);




                /*
                * SOMEWHERE HERE I HAVE TO PUT ml MODEL
                * MAKE INFERENCE
                * AFTER THAT MAKE A POINT AND DRAW A LINE
                *
                * */



            }
        }
    };

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions,
            @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        for(int k=0; k<12; k++){
            wifi_ID[k]=0;
            //wifi_ID[k] = default_wifi_strength;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // Register wifi receiver
        registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        result_view = findViewById(R.id.result_view);
        result_view.append(String.format("\n"));
        //Log.e("INFO", "****** QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ\n");

        Button wifi_scan_btn = findViewById(R.id.wifi_scan_btn);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSION_CODE);



        wifi_scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_CODE);
                    // request permission
                }else{
                    // BroadcastReceiver will get result
                    wifiMan.startScan();

                }
            }

        }
        );

        /*
        for(int i=0; i<5; i++){
            String pref = String.valueOf(i);
            result_view.append(String.format("*** %s THIS IS FOR LOOP ***\n", pref));
            Log.e("INFO", "*** THIS IS FOR LOOP ***\n");
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_CODE);
                // request permission
            }else{
                // BroadcastReceiver will get results
                wifiMan.startScan();

            }
        }
         */




        //Added later, should delete if fails
        //writeToFile(wifi_data.toString());
        //writeToFile(wifi_string);
    }

    private void writeToFile(String data) {
        //String FILENAME = "WIFI_DATA" + file_name_counter + ".txt";
        String FILENAME = "WIFI_DATA.txt";
        //file_name_counter++;
        try {
            File directoryDownload = Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS);
            File logDir = new File (directoryDownload, "WIFI_DATA"); //Creates a new folder in DOWNLOAD directory
            logDir.mkdirs();
            File file = new File(logDir, FILENAME);
            FileOutputStream out = new FileOutputStream(file);
            //FileOutputStream out = openFileOutput("myfile.txt", MODE_APPEND);

            out.write(data.getBytes()); //Write the obtained string to csv
            out.close();
        } catch (Exception e) {
            Log.e("ERROR", e.toString());
            e.printStackTrace();
        }
    }

    private void does_exist(String detected_wifi, int wifi_strength) {
        Log.e("INFO", "Starting to check whether wifi exists or not\n");
        Log.e("INFO", detected_wifi);


        if(detected_wifi.equals(String.format("94:d4:69:fa:7e:ce"))){
            wifi_ID[0] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }
        else if(detected_wifi.equals(String.format("94:d4:69:fa:7e:cd"))){
            wifi_ID[1] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }
        else if(detected_wifi.equals(String.format("94:d4:69:fa:7e:cf"))){
            wifi_ID[2] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }
        else if(detected_wifi.equals(String.format("94:d4:69:fa:7e:cc"))){
            wifi_ID[3] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }
        else if(detected_wifi.equals(String.format("94:d4:69:fa:83:4f"))){
            wifi_ID[4] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }
        else if(detected_wifi.equals(String.format("38:20:56:7e:de:8f"))){
            wifi_ID[5] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }
        else if(detected_wifi.equals(String.format("38:20:56:7e:de:8d"))){
            wifi_ID[6] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }
        else if(detected_wifi.equals(String.format("38:20:56:7e:de:8e"))){
            wifi_ID[7] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }
        else if(detected_wifi.equals(String.format("38:20:56:85:ad:2e"))){
            wifi_ID[8] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }
        else if(detected_wifi.equals(String.format("38:20:56:85:ad:2d"))){
            wifi_ID[9] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }
        else if(detected_wifi.equals(String.format("94:d4:69:fa:83:4d"))){
            wifi_ID[10] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }
        if(detected_wifi.equals(String.format("94:d4:69:fa:83:4e"))){
            wifi_ID[11] = wifi_strength;
            //wifi_ID[0] = wifi_strength;
        }

        //ART_5G-1

        /*
        if(detected_wifi.equals(String.format("14:dd:a9:d0:45:a4"))){
            wifi_ID[15] = 1;
        }
         */


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(wifiReceiver);
    }
}
