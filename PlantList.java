package com.example.soyeon.login2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

public class PlantList extends AppCompatActivity {



    private Socket socket;  //소켓생성
    BufferedReader in;      //서버로부터 온 데이터를 읽는다.
    PrintWriter out;        //서버에 데이터를 전송한다.
    EditText input;         //화면구성
    Button button;          //화면구성
    TextView output;        //화면구성
    String add;
    String data2;


    protected void onCreate(Bundle savedInstanceState) {   //앱 시작시  초기화설정

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        //액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // input = (EditText) findViewById(R.id.input); // 글자입력칸을 찾는다.
        button = (Button) findViewById(R.id.button); // 버튼을 찾는다.
        output = (TextView) findViewById(R.id.output); // 글자출력칸을 찾는다.

        Intent intent = getIntent();
        Data data3 = (Data)intent.getSerializableExtra("data");

        TextView txtName = (TextView) findViewById(R.id.txt_name);

        if(data3 != null){
            txtName.setText("식물이름 : " + data3.name  + "\n입양날짜 : " + String.valueOf(data3.date) + "\n토양습도 : " + String.valueOf(data3.hum));
        }



        button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    add = "add"; //글자입력칸에 있는 글자를 String 형태로 받아서 data에 저장
                    Log.w("NETWORK", " " + add);
                    if (add != null) { //만약 데이타가 아무것도 입력된 것이 아니라면
                        out.println(add); //data를   stream 형태로 변형하여 전송.  변환내용은 쓰레드에 담겨 있다.

                    }

                }

        });





        Thread worker = new Thread() {    //worker 를 Thread 로 생성
            public void run() { //스레드 실행구문
                try {

            //소켓을 생성하고 입출력 스트립을 소켓에 연결한다.
                    socket = new Socket("192.168.1.220", 8832); //소켓생성
                    out = new PrintWriter(socket.getOutputStream(), true); //데이터를 전송시 stream 형태로 변환하여                                                                                                                       //전송한다.
                    in = new BufferedReader(new InputStreamReader(
                            socket.getInputStream())); //데이터 수신시 stream을 받아들인다.

                } catch (IOException e) {
                    e.printStackTrace();
                }

            //소켓에서 데이터를 읽어서 화면에 표시한다.

                try {
                    while (true) {
                        data2 = in.readLine(); // in으로 받은 데이타를 String 형태로 읽어 data 에 저장
                        output.post(new Runnable() {
                            public void run() {
                                output.setText(data2); //글자출력칸에 서버가 보낸 메시지를 받는다.
                                if(data2.equals("ok")){
                                    Intent intent = new Intent(getApplicationContext(), PlantDataActivity.class);
                                    startActivity(intent);
                                }
                            }


                        });

                    }
                } catch (Exception e) {
                }


            }
        };

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



