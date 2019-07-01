package com.tjoeun.threadnetwork0701;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class SocketActivity extends AppCompatActivity {
    EditText msg;
    Button send;
    TextView disp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);


        msg = (EditText)findViewById(R.id.msg);
        send = (Button)findViewById(R.id.send);
        disp = (TextView)findViewById(R.id.disp);


        send.setOnClickListener((view)->{
            //네트워크 작업은 스레드를 이용
            Thread th = new Thread(){
                public void run(){
                    try{

                        /*
                        //연결할 주소 생성
                        InetAddress ia  = InetAddress.getByName("192.168.0.108");
                        //소켓 만들기
                        Socket socket = new Socket(ia, 9999);
                        //입력한 문자열을 전송
                        PrintWriter pw = new PrintWriter(socket.getOutputStream());
                        pw.println(msg.getText().toString());
                        pw.flush();

                        //전송받은 문자열 읽기
                        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String content = br.readLine();
                        disp.setText(content);
                        pw.close();
                        br.close();
                        socket.close();
                        */

                        //데이터를 전송하는 데이터그램 소켓 생성
                        DatagramSocket ds = new DatagramSocket();
                        //보낼 주소 생성
                        InetAddress ia = InetAddress.getByName("192.168.0.108");
                        //보낼 데이터 생성
                        String data = msg.getText().toString();
                        // 데이터를 만들어서 전송
                        DatagramPacket dp = new DatagramPacket(data.getBytes(), data.getBytes().length, ia, 8888);
                        ds.send(dp);

                        disp.setText("메시지 전송 성공");
                        ds.close();


                    }catch(Exception e){
                        Log.e("에러", e.getMessage());
                    }
                }
            };
            th.start();
        });




    }
}
