package com.simongeorgeasmar.cvcreator;

import Models.EduExpInfo;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    public static final String EDU_EXP_INFO = "EDU_EXP_INFO";
    public static final String FLAG2 = "FLAG2";
    private SharedPreferences prefs2;
    private SharedPreferences.Editor editor2;
    private Gson gson = new Gson();
    private boolean flag = false;
    private Spinner spnEducation;
    private EditText edtInstitute;
    private EditText edtSubject;
    private EditText edtCompany;
    private EditText edtJobTitle;
    private EditText edtYears;
    private CheckBox chk2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setupViews();
        populateSpinner();
        setupSharedPrefs();
        checkPrefs();
    }

    private void checkPrefs() {
        flag = prefs2.getBoolean(FLAG2, false);
        if(flag){
            JsonToPojo();
            chk2.setChecked(true);
        }
    }

    private void JsonToPojo(){
      String json = prefs2.getString(EDU_EXP_INFO, "");
        json = json.substring(1, json.length()-1); //Removes brackets {}
        json = json.replaceAll("\"", ""); //Removes Quotations
        String[] splitStr = json.split(","); //Splits the string so now we have an array with [NAME]:[VALUE]
        //I've Noticed that the json code generated from the gson is ordered in alphabetic order of the names of the variables
        //so that means the array indexes will be...
        //[0]company [1]education [2]institute [3]jobTitle [4]subject [5]years

        edtCompany.setText(splitStr[0].substring(splitStr[0].indexOf(":")+1));

        //(0)HS Diploma (1)Bachelors Degree (2) Masters Degree (3)PhD
        String[] educationArray = {"HS Diploma", "Bachelors Degree", "Masters Degree", "PhD"};
        String edu = splitStr[1].substring(splitStr[1].indexOf(":")+1);
        for (int i=0; i<educationArray.length; i++){
            if(edu.equals(educationArray[i])){
                spnEducation.setSelection(i);
            }
        }

        edtInstitute.setText(splitStr[2].substring(splitStr[2].indexOf(":")+1));

        edtJobTitle.setText(splitStr[3].substring(splitStr[3].indexOf(":")+1));

        edtSubject.setText(splitStr[4].substring(splitStr[4].indexOf(":")+1));

        edtYears.setText(splitStr[5].substring(splitStr[5].indexOf(":")+1));

    }

    private void setupSharedPrefs() {
        prefs2 = PreferenceManager.getDefaultSharedPreferences(this);
        editor2 = prefs2.edit();
    }

    private void setupViews() {
        spnEducation = findViewById(R.id.spnEducation);
        edtInstitute = findViewById(R.id.edtInstitute);
        edtSubject = findViewById(R.id.edtSubject);
        edtCompany = findViewById(R.id.edtCompany);
        edtJobTitle = findViewById(R.id.edtJobTitle);
        edtYears = findViewById(R.id.edtYears);
        chk2 = findViewById(R.id.chk2);
    }

    private void populateSpinner() {
        ArrayList<String> education = new ArrayList<String>();
        education.add("HS Diploma");
        education.add("Bachelors Degree");
        education.add("Masters Degree");
        education.add("PhD");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, education);

        spnEducation.setAdapter(adapter);
    }

    public void btnDoneOnClick(View view) {
        String education = spnEducation.getSelectedItem().toString();
        String institute = edtInstitute.getText().toString();
        String subject = edtSubject.getText().toString();
        String company = edtCompany.getText().toString();
        String jobTitle = edtJobTitle.getText().toString();
        String years = edtYears.getText().toString();

        EduExpInfo data = new EduExpInfo(education, institute, subject, company, jobTitle, years);

        String dataString = gson.toJson(data);

        if(chk2.isChecked()){
            editor2.putBoolean(FLAG2, true);
            editor2.putString("EDU_EXP_INFO", dataString);
            editor2.commit();
        } else{
            editor2.putBoolean(FLAG2, false);
            //editor2.clear();
            editor2.remove(EDU_EXP_INFO);
            editor2.commit();
        }

        Toast.makeText(this, dataString, Toast.LENGTH_SHORT).show();
    }


    public void chk2OnClick(View view){
        if(chk2.isChecked()){
            Toast.makeText(this, "Your educational and professional info will be saved in the shared preferences for next time.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Your educational and professional info will be cleared from shared preferences.", Toast.LENGTH_SHORT).show();
        }
    }


}