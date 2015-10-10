package com.bxcalculator.thecalculator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DateFormat;
import java.util.Date;


public class Calculator extends ActionBarActivity {

    TextView input, result;
    String current, operation, holder, temp = "", binNum="";
    int sum = 0, num = 0, count = 0, adCount = 0, decCount = 0, negCount = 0;
    String holder1, holder2;
    double sum2 = 0;
    private InterstitialAd interstitialAd;
    private AdRequest adRequest;
    private AdView mAdView;
    boolean isVisible = true, isPressed = true;
    public static MyDBHandler dbHandler;
    String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        input = (TextView) findViewById(R.id.input);
        result = (TextView) findViewById(R.id.result);
        dbHandler = new MyDBHandler(this, null, null, 1);


        int idList[] = {R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six, R.id.seven,
                R.id.eight, R.id.nine, R.id.zero, R.id.add, R.id.subtract, R.id.multiply, R.id.divide, R.id.equal,
                R.id.clear, R.id.negative, R.id.allclear, R.id.dot};

        mAdView = (AdView) findViewById(R.id.adView);

        // advertisement
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3040119163649827/7917453791"); //ca-app-pub-3940256099942544/1033173712
        //ca-app-pub-3940256099942544/6300978111 Banner Test

        adRequest = new AdRequest.Builder().build();
                //.addTestDevice("7172E1E8546BA99CFE184F0A09A340F8")
        mAdView.loadAd(adRequest);
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed(){
                if(isVisible == false){}
                else{
                    interstitialAd.loadAd(adRequest);
                    adCount = 0;
                }

            }
        });

        for(int id : idList){
            Button b = (Button) findViewById(id);
            b.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View view){
                    switch(view.getId()){
                        case R.id.add:
                            isPressed = true;
                            add("+");
                            break;

                        case R.id.subtract:
                            isPressed = true;
                            subtract("-");
                            break;

                        case R.id.multiply:
                            isPressed = true;
                            multiply("*");
                            break;

                        case R.id.divide:
                            isPressed = true;
                            divide("/");
                            break;

                        case R.id.equal:
                            equal();
                            adCount++;
                            if(adCount >= 10 && isVisible){
                                displayInterstitial();
                            }
                            break;

                        case R.id.dot:
                            dot();
                            break;

                        case R.id.clear:
                            clear();
                            break;

                        case R.id.allclear:
                            allClear();
                            break;

                        case R.id.one:
                            count++;
                            oneToNine("1");
                            break;

                        case R.id.two:
                            count++;
                            oneToNine("2");
                            break;

                        case R.id.three:
                            count++;
                            oneToNine("3");
                            break;

                        case R.id.four:
                            count++;
                            oneToNine("4");
                            break;

                        case R.id.five:
                            count++;
                            oneToNine("5");
                            break;

                        case R.id.six:
                            count++;
                            oneToNine("6");
                            break;

                        case R.id.seven:
                            count++;
                            oneToNine("7");
                            break;

                        case R.id.eight:
                            count++;
                            oneToNine("8");
                            break;

                        case R.id.nine:
                            count++;
                            oneToNine("9");
                            break;

                        case R.id.zero:
                            count++;
                            zero();
                            break;

                        case R.id.negative:
                            negative(sum);
                            result.setText(binNum);
                            binNum = "";
                            break;
                        default:
                    }
                }
            });
        }

    }

    public void show(View v){
        Intent i = new Intent(this, ShowSavedData.class);
        startActivity(i);
    }

    public void save(View v){
        time = DateFormat.getDateTimeInstance().format(new Date());
        Result result = new Result(holder2, holder1, time);
        dbHandler.saveResult(result);
        Toast toast = Toast.makeText(getApplicationContext(), "Data saved!", Toast.LENGTH_SHORT);
        toast.show();
    }


    public void dot(){
        if(isPressed){
            if(num == 0){
                temp = "0.";
            }
            else{
                temp = temp + ".";
            }
            holder = temp;
            input.setText(holder);
            isPressed = false;
        }
    }

    public void displayInterstitial(){
        if(interstitialAd.isLoaded()){
            interstitialAd.show();
        }
    }

    public void oneToNine(String x){
        if(num != 0 && operation != null) {
            for (int i = 0; i < (count -  temp.length()); i++) {
                temp = temp + x;
            }
            input.setText(current + " " + temp);
            num = Integer.parseInt(temp);
        }
        else if(num != 0 && operation == null) {
            for (int i = 0; i < (count - temp.length()); i++) {
                temp = temp + x;
            }
            holder = temp;
            input.setText(holder);
            sum = Integer.parseInt(temp);
        }
        else{
            num = Integer.parseInt(x);
            sum = num;
            temp = temp + x;
            holder = Integer.toString(num);
            input.setText(holder);
        }
    }

    public void zero(){
        if(num != 0 && operation != null) {
            for (int i = 0; i < (count -  temp.length()); i++) {
                temp = temp + "0";
            }
            input.setText(current + " " + temp);
            num = Integer.parseInt(temp);
        }
        else if(num != 0 && operation == null) {
            for (int i = 0; i < (count - temp.length()); i++) {
                temp = temp + "0";
            }
            holder = temp;
            input.setText(holder);
            sum = Integer.parseInt(temp);
        }
        else{
            num = 0;
            sum = num;
            temp = temp + "0";
            holder = Integer.toString(num);
            input.setText(holder);
        }
    }

    //TODO:
    public void negative(int n){
        /*if(num !=0 && operation != null){
            input.setText(current + " -");
        }
        else {
            input.setText("-");
        }*/
            //result.setText("-" + sum);
        if(n / 2 != 0) {
            negative(n /2);
        }
        binNum = binNum + Integer.toString(n%2);
    }

    /*
        TODO: Dot
     */

    /*
        TODO: Parenthesis
     */

    /*
        TODO: Further calculation
     */

    public void add(String str){
        operation = str;
        current = holder + " " + str;
        if(num != 0){
            input.setText(current);
            count = 0;
            temp = "";
        }
        else{}
    }

    public void subtract(String str){
        operation = str;
        current = holder + " " + str;
        if(num != 0){
            input.setText(current);
            count = 0;
            temp = "";
        }
        else{}
    }

    public void multiply(String str){
        operation = str;
        current = holder + " " + str;
        if(num != 0){
            input.setText(current);
            count = 0;
            temp = "";
        }
        else{}
    }

    public void divide(String str){
        operation = str;
        current = holder + " " + str;
        if(num != 0){
            input.setText(current);
            count = 0;
            temp = "";
        }
        else{}
    }

    public void equal(){

        if(operation == "+"){
            sum = sum + num;
            result.setText("" + sum);
            holder1 = Integer.toString(sum);
            holder2 = current + " " + temp;
            sum = 0; num = 0; count = 0; temp = ""; operation = null;
        }
        else if(operation == "-"){
            sum = sum - num;
            result.setText("" + sum);
            holder1 = Integer.toString(sum);
            holder2 = current + " " + temp;
            sum = 0; num = 0; count = 0; temp = ""; operation = null;
        }
        else if(operation == "*"){
            sum = sum * num;
            result.setText("" + sum);
            holder1 = Integer.toString(sum);
            holder2 = current + " " + temp;
            sum = 0; num = 0; count = 0; temp = ""; operation = null;
        }
        else if(operation == "/"){
            sum2 = (double) sum / (double) num;
            result.setText("" + sum2);
            holder1 = Double.toString(sum2);
            holder2 = current + " " + temp;
            sum = 0; num = 0; count = 0; temp = ""; operation = null;
        }
    }

    public void clear(){
        input.setText("");
        sum = 0; num = 0; count = 0; temp = "";
        operation = null;
    }

    public void allClear(){
        input.setText("");
        result.setText("");
        sum = 0; num = 0; count = 0; temp = "";
        operation = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.hide_ads:
                if(isVisible){
                    mAdView.destroy();
                    mAdView.setVisibility(View.GONE);
                    isVisible = false;
                }
                return true;
            case R.id.about_me:
                Intent i = new Intent(this, AboutPage.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}