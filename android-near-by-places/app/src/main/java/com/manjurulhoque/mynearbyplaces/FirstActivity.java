package com.manjurulhoque.mynearbyplaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.manjurulhoque.mynearbyplaces.activity.MainActivity;

public class FirstActivity extends AppCompatActivity {

    Spinner id_spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //GlobalClass globalClass = (GlobalClass) getApplicationContext();

        final int[] currentItm = {0};
        final String[] currentnum = {""};
        //設定隱藏標題
        //getSupportActionBar().hide();
        Button btn_start = findViewById(R.id.brn_start);

        final EditText et_Name = findViewById(R.id.et_Name);
        final EditText et_phone_number = findViewById(R.id.phone_number);
        id_spinner = (Spinner)findViewById(R.id.id_spinner);
        Spinner address_spinner = (Spinner) findViewById(R.id.address_spinner);

        id_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    currentItm[0] = position;
                }
                if(position==1){
                    currentItm[0] = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        address_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    currentnum[0] = "chiayi";
                }
                if(position==1){
                    currentnum[0] = "taipei";
                }
                if(position==2){
                    currentnum[0] = "new taipei";
                }
                if(position==3){
                    currentnum[0] = "taoyuan";
                }
                if(position==4){
                    currentnum[0] = "taichung";
                }
                if(position==5){
                    currentnum[0] = "tainan";
                }
                if(position==6){
                    currentnum[0] = "kaohsiung";
                }
                if(position==7){
                    currentnum[0] = "hsinchu";
                }
                if(position==8){
                    currentnum[0] = "miaoli";
                }
                if(position==9){
                    currentnum[0] = "changhua";
                }
                if(position==10){
                    currentnum[0] = "nantou";
                }
                if(position==11){
                    currentnum[0] = "yunlin";
                }
                if(position==12){
                    currentnum[0] = "pingtung";
                }
                if(position==13){
                    currentnum[0] = "yilan";
                }
                if(position==14){
                    currentnum[0] = "hualien";
                }
                if(position==15){
                    currentnum[0] = "taitung";
                }
                if(position==16){
                    currentnum[0] = "penghu";
                }
                if(position==17){
                    currentnum[0] = "kinmen";
                }
                if(position==18){
                    currentnum[0] = "lienchiang";
                }
                if(position==19){
                    currentnum[0] = "keelung";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_Name.getText().toString().length() == 0 || (et_phone_number.getText().toString().length() != 10)){
                    Toast.makeText(getApplicationContext(), "請輸入正確資訊!", Toast.LENGTH_SHORT).show();
                }
                if (currentItm[0]==0 && et_Name.getText().toString().length() != 0 &&(et_phone_number.getText().toString().length() == 10)){
                    Intent intent = new Intent(FirstActivity.this, StudentActivity.class);
                    intent.putExtra("Name",et_Name.getText().toString().trim());
                    intent.putExtra("phone_number" ,et_phone_number.getText().toString().trim());
                    intent.putExtra("address" ,currentnum[0]);

                    /*GlobalClass globalClass = (GlobalClass) getApplicationContext();
                    globalClass.setAddress(currentnum[0]);
                    globalClass.setName(et_Name.getText().toString());
                    globalClass.set_phone_number(et_phone_number.getText().toString());
                    globalClass.setIdentity(currentItm[0]);*/

                    startActivity(intent);
                    finish();
                }
                if (currentItm[0]==1 && et_Name.getText().toString().length() != 0 && (et_phone_number.getText().toString().length() == 10)){
                    Intent intent = new Intent(FirstActivity.this, OlderActivity.class);
                    intent.putExtra("Name",et_Name.getText().toString().trim());
                    intent.putExtra("phone_number" ,et_phone_number.getText().toString().trim());
                    intent.putExtra("address" ,currentnum[0]);

                    startActivity(intent);
                    finish();
                }

            }
        });
    }
}
