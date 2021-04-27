package com.simongeorgeasmar.cvcreator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import Models.PersonalInfo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String PERSONAL_INFO = "PERSONAL_INFO";
    public static final String FLAG = "FLAG";
    private boolean flag = false;
    private EditText edtName;
    private EditText edtEmail;
    private EditText edtPhone;
    private  RadioGroup radioGroup;
    private  RadioButton rbMale;
    private RadioButton rbFemale;
    private EditText edtAge;
    private  Spinner spnStatus;
    private EditText edtHobbies;
    private  CheckBox chk1;
    private  String str = "";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String PREFS = "PREFS";
    public static final String EDITOR = "EDITOR";
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        setupSharedPrefs();
        populateSpinner();
        checkPrefs();
    }

    private void setupViews() {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        radioGroup = findViewById(R.id.radioGroup);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        edtAge = findViewById(R.id.edtAge);
        edtHobbies = findViewById(R.id.edtHobbies);
        spnStatus = findViewById(R.id.snpStatus);
        chk1 = findViewById(R.id.chk1);
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);
        if(flag){
            JsonToPojo();
            chk1.setChecked(true);
        }
    }

    private void JsonToPojo(){
        String json = prefs.getString(PERSONAL_INFO, "");
        json = json.substring(1, json.length()-1); //Removes brackets {}
        json = json.replaceAll("\"", ""); //Removes Quotations
        String[] splitStr = json.split(","); //Splits the string so now we have an array with [NAME]:[VALUE]
        //I've Noticed that the json code generated from the gson is ordered in alphabetic order of the names of the variables
        //so that means the array indexes will be...
        //[0]age [1]email [2]gender [3]hobbies [4]name [5]phone [6]status

        edtAge.setText(splitStr[0].substring(splitStr[0].indexOf(":")+1));
        edtEmail.setText(splitStr[1].substring(splitStr[1].indexOf(":")+1));
        if(splitStr[2].substring(splitStr[2].indexOf(":")+1).equals("Male")){
            rbMale.setChecked(true);
        } else if(splitStr[2].substring(splitStr[2].indexOf(":")+1).equals("Female")){
            rbFemale.setChecked(true);
        }
        edtHobbies.setText(splitStr[3].substring(splitStr[3].indexOf(":")+1));
        edtName.setText(splitStr[4].substring(splitStr[4].indexOf(":")+1));
        edtPhone.setText(splitStr[5].substring(splitStr[5].indexOf(":")+1));

        //(0)Single (1)In a Relationship (2)Engaged (3)Married (4)Divorced (5)Widowed (6)It's Complicated
        String[] statusArray = {"Single", "In a Relationship", "Engaged", "Married", "Divorced", "Widowed", "Its Complicated"};
        String stat = splitStr[6].substring(splitStr[6].indexOf(":")+1);
        for (int i=0; i<statusArray.length; i++){
            if(stat.equals(statusArray[i])){
                spnStatus.setSelection(i);
            }
        }

    }

    private void populateSpinner() {
        ArrayList<String> status = new ArrayList<String>();
        status.add("Single");
        status.add("In a Relationship");
        status.add("Engaged");
        status.add("Married");
        status.add("Divorced");
        status.add("Widowed");
        status.add("Its Complicated");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status);

        spnStatus.setAdapter(adapter);
    }

    public void btnNextOnClick(View view) {
        String name = edtName.getText().toString();
        String email = edtEmail.getText().toString();
        String phone = edtPhone.getText().toString();
        String gender;
        if(rbMale.isChecked()){
            gender = "Male";
        } else if(rbFemale.isChecked()){
            gender = "Female";
        } else{
            gender = "";
        }
        String age = edtAge.getText().toString();
        String status = spnStatus.getSelectedItem().toString();
        String hobbies = edtHobbies.getText().toString();

        PersonalInfo personalInfo = new PersonalInfo(name, email, phone, gender, age, status, hobbies);

        String personalInfoString = gson.toJson(personalInfo);
        if(chk1.isChecked()){
            editor.putBoolean(FLAG, true);
            editor.putString("PERSONAL_INFO", personalInfoString);
            editor.commit();
        } else{
            editor.putBoolean(FLAG, false);
            //editor.clear(); //maybe remove keys
            editor.remove(PERSONAL_INFO);
            editor.commit();
        }

        Intent intent = new Intent(this, MainActivity2.class);

        startActivity(intent);

    }

    public void chk1OnClick(View view) {
        if(chk1.isChecked()){
            Toast.makeText(this, "Your personal info will be saved in the shared preferences for next time.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Your personal info will be cleared from shared preferences.", Toast.LENGTH_SHORT).show();
        }
    }
}