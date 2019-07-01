package com.tjoeun.threadnetwork0701;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView display;
    Button btn;
    //인덱스 변수
    int value;

    //대화상자 변수
    ProgressDialog pd;
    //대화상자 출력 여부를 나타낼 변수
    boolean isQuit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //뷰 찾아오기
        display = (TextView)findViewById(R.id.display);
        btn = (Button)findViewById(R.id.btn);

        /*
        //버튼의 클릭 이벤트 처리
        btn.setOnClickListener((view)->{
            try{
                //스레드를 사용하지 않고 UI변경 코드를 만들면 모아서 한꺼번에 처리
                for(int i=0; i<100; i=i+1){
                    value = value + 1;
                    Log.e("value", value+"");
                    display.setText(Integer.toString(value));
                    Thread.sleep(100);
                }
            }catch (Exception e){}

        });
        */
        /*
        btn.setOnClickListener((view)->{
            Thread th = new Thread(){
                    public void run(){
                try {
                    //스레드를 사용하지 않고 UI변경 코드를 만들면 모아서 한꺼번에 처리
                    for (int i = 0; i < 100; i = i + 1) {
                        value = value + 1;
                        Log.e("value", value + "");
                        display.setText(Integer.toString(value));
                        Thread.sleep(100);
                    }
                 }catch(Exception e){
                }
               }
            };
        th.start();

        });
        */

        //Main Thread에게 작업을 요청하는 클래스의 객체 생성
        Handler handler = new Handler(){
            //메시지가 전송되면 호출되는 메소드
            @Override
            public void handleMessage(Message msg){
                //넘겨받은 데이터를 읽어서 정수로 변환해서 v에 저장
                int v = (Integer)msg.obj;
                display.setText(Integer.toString(v));
                if(v%100!=0 && isQuit == false){
                    pd.setProgress(v%100);
                }else{
                    pd.dismiss();
                    isQuit = true;
                }
            }
        };

        btn.setOnClickListener((view)->{
            pd = new ProgressDialog(MainActivity.this);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setTitle("업데이트 중");
            pd.setMessage("Waiting...");
            //백 버튼을 눌렀을 떄 대화상자가 닫히지 않도록 하는 설정
            pd.setCancelable(false);
            pd.show();
            isQuit = false;

                Thread th = new Thread(){
                @Override
                public void run(){
                    try{
                        for(int i=0; i<100; i=i+1){
                            value = value + 1;
                            //핸들러에게 전송할 메시지 생성
                            Message msg = new Message();
                            msg.obj = value;
                            //핸들러에게 메시지 전송
                            handler.sendMessage(msg);
                            Thread.sleep(100);
                        }
                    }catch (Exception e){}
                }
            };
            th.start();
        });


    }
}
