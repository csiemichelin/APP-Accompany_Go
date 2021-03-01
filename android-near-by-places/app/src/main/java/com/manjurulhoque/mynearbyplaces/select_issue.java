package com.manjurulhoque.mynearbyplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.manjurulhoque.mynearbyplaces.activity.MainActivity;

public class select_issue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_issue);

        final GlobalClass globalClass = (GlobalClass) getApplicationContext();

        final int [] currentItm = {0};
        final String[] currentstr = {"test"};
        final TextView name_text = findViewById(R.id.name_text);
        final TextView phone_number_text = findViewById(R.id.phone__number);
        ImageButton sort_st = findViewById(R.id.sort_st);
        Intent intent = getIntent();

        final  String Name = globalClass.getName();
        final  String address = globalClass.getAddress();
        final  String phone_number = globalClass.getPhone_number();

        name_text.setText(Name);
        phone_number_text.setText(phone_number);
        Spinner id_spinner = (Spinner) findViewById(R.id.id_spinner);

        id_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    currentItm[0] = position;
                    currentstr[0] = "傾聽我的故事";
                    Log.d("Issue", "Issue" + currentstr[0]);
                }
                if(position==1){
                    currentItm[0] = position;
                    currentstr[0] = "扶著我散步";
                }
                if(position==2){
                    currentItm[0] = position;
                    currentstr[0] = "陪我運動";
                }
                if(position==3){
                    currentItm[0] = position;
                    currentstr[0] = "聊天";
                }
                if(position==4){
                    currentItm[0] = position;
                    currentstr[0] = "下棋";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        sort_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentItm[0]==0){
                    Intent intent = new Intent(select_issue.this, MainActivity.class);
                    intent.putExtra("Name",name_text.getText().toString().trim());
                    intent.putExtra("Issue",currentstr[0]);
                    intent.putExtra("phone_number",name_text.getText().toString().trim());
                    globalClass.setName(Name);
                    globalClass.set_phone_number(phone_number);
                    globalClass.setIssue(currentstr[0]);
                    startActivity(intent);
                }
                if (currentItm[0]==1){
                    Intent intent = new Intent(select_issue.this, MainActivity.class);
                    intent.putExtra("Name",name_text.getText().toString().trim());
                    intent.putExtra("Issue",currentstr[0]);
                    intent.putExtra("phone_number",name_text.getText().toString().trim());
                    globalClass.setName(Name);
                    globalClass.set_phone_number(phone_number);
                    globalClass.setIssue(currentstr[0]);
                    startActivity(intent);
                }
                if (currentItm[0]==2){
                    Intent intent = new Intent(select_issue.this, MainActivity.class);
                    intent.putExtra("Name",name_text.getText().toString().trim());
                    intent.putExtra("Issue",currentstr[0]);
                    intent.putExtra("phone_number",name_text.getText().toString().trim());
                    globalClass.setName(Name);
                    globalClass.set_phone_number(phone_number);
                    globalClass.setIssue(currentstr[0]);
                    startActivity(intent);
                }
                if (currentItm[0]==3){
                    Intent intent = new Intent(select_issue.this, MainActivity.class);
                    intent.putExtra("Name",name_text.getText().toString().trim());
                    intent.putExtra("Issue",currentstr[0]);
                    intent.putExtra("phone_number",name_text.getText().toString().trim());
                    startActivity(intent);
                    globalClass.setName(Name);
                    globalClass.set_phone_number(phone_number);
                    globalClass.setIssue(currentstr[0]);
                }
                if (currentItm[0]==4){
                    Intent intent = new Intent(select_issue.this, MainActivity.class);
                    intent.putExtra("Name",name_text.getText().toString().trim());
                    intent.putExtra("Issue",currentstr[0]);
                    intent.putExtra("phone_number",name_text.getText().toString().trim());
                    startActivity(intent);
                    globalClass.setName(Name);
                    globalClass.set_phone_number(phone_number);
                    globalClass.setIssue(currentstr[0]);
                }
            }
        });
    }
}
