package com.example.soyeon.login2;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.lang.NullPointerException;

public class PlantDataActivity extends AppCompatActivity {

    private Button buttondbSend;
    private EditText editPlantName;
    private EditText editDate;
    private EditText editSoilhum;

    private Socket socket;  //소켓생성
    BufferedReader in;      //서버로부터 온 데이터를 읽는다.
    PrintWriter out;        //서버에 데이터를 전송한다.
    EditText input;         //화면구성
    Button button;          //화면구성
    TextView output;        //화면구성
    String dataa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_data);
        //액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        final EditText editPlantName = (EditText) findViewById(R.id.editPlantName);
        final EditText editDate = (EditText) findViewById(R.id.editDate);
        final EditText editSoilhum = (EditText) findViewById(R.id.editSoilhum);
        buttondbSend = (Button) findViewById(R.id.buttondbSend);


        buttondbSend.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                String name = editPlantName.getText().toString();
                int date = Integer.parseInt(editDate.getText().toString());
                int hum = Integer.parseInt(editSoilhum.getText().toString());

                Data data = new Data(name,date,hum);

                Intent intent = new Intent(getApplicationContext(), PlantList.class);
                intent.putExtra("data", data);

                startActivity(intent);
            }

        });

        Thread worker = new Thread() {    //worker 를 Thread 로 생성
            public void run() { //스레드 실행구문
                try {

                    //소켓을 생성하고 입출력 스트립을 소켓에 연결한다.
                    socket = new Socket("192.168.1.220", 8838); //소켓생성
                    out = new PrintWriter(socket.getOutputStream(), true); //데이터를 전송시 stream 형태로 변환하여                                                                                                                       //전송한다.
                    in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream())); //데이터 수신시 stream을 받아들인다.

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
//소켓에서 데이터를 읽어서 화면에 표시한다.

        try {
            while (true) {
                dataa = in.readLine(); // in으로 받은 데이타를 String 형태로 읽어 data 에 저장
                output.post(new Runnable() {
                    public void run() {
                       // output.setText(dataa); //글자출력칸에 서버가 보낸 메시지를 받는다.
                    }


                });

            }
        } catch (Exception e) {
        }

        worker.start();  //onResume()에서 실행.
    }
    @Override
    protected void onStop() {  //앱 종료시
        super.onStop();
        try {
            socket.close(); //소켓을 닫는다.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
