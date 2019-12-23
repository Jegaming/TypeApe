package com.jegaming.demo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jegaming.typeape.R;
import com.jegaming.typeape.model.TypeApeModel;
import com.jegaming.typeape.util.Utils;
import com.jegaming.typeape.view.TypeApeView;

public class MainActivity extends AppCompatActivity {

    TypeApeView mTAV;
    Button mChangeBtn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }
    
    private void initView(){
        mTAV = findViewById(R.id.typeape);
        mChangeBtn1 = findViewById(R.id.change_btn1);
        mChangeBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataA();
            }
        });
    }

    private void initData() {

        String test = "[00:00.000]{400,RAD,UL,40,#FF6347}口三口\n" +
                "[00:03.000]{400, RAN,UL,30,#FF6347}口口四口\n" +
                "[00:06.000]{400, RAN,RL,42,#FF6347}口口口五口\n" +
                "[00:08.000]{400, RAN,UL,20,#FF6347}口口六口口口\n" +
                "[00:10.000]{400, RAN,UL,40,#FF6347}口口七口口口口\n" +
                "[00:12.000]{400, RAN,RR,43,#FF6347}口口八口口口口口\n" +
                "[00:14.000]{400, RAN,UR,37,#FF6347}口口九口口口口口口\n" +
                "[00:16.000]{400, RAN,UR,30,#FF6347}口口八口口口口口\n" +
                "[00:18.000]{400, RAN,UR,44,#FF6347}口四口口\n" +
                "[00:20.000]{400, RAN,UR,55,#FF6347}口口\n" +
                "[00:22.000]{400, RAN,RL,34,#FF6347}口口八口口口口口\n" +
                "[00:24.000]{400, RAN,UL,50,#FF6347}口口\n" +
                "[00:26.000]{400, RAN,UL,40,#FF6347}口口九口口口口口口\n" +
                "[00:28.000]{400, RAN,UL,30,#FF6347}口口九口口口口口口\n" +
                "[00:30.000]{400, RAN,RR,50,#FF6347}十五\n" +
                "[00:31.000]{400, RAN,UL,50,#FF6347}十六\n" +
                "[00:32.000]{400, RAN,UL,52,#FF6347}十七十七\n" +
                "[00:33.000]{400, RAN,UL,30,#FF6347}是吧是吧\n" +
                "[00:34.000]{400, RAN,UL,30,#FF6347}十九口口口口口口\n" +
                "[00:35.000]{400, RAN,UL,35,#FF6347}二十20二十";

        TypeApeModel model = Utils.createTypeApeModel(test);
        Log.d("TYPEAPEVIEW", "initData : " + model);
        mTAV.setRecycleSize(5);
        mTAV.setBgColor(Color.parseColor("#FFEEFF"));
        //        mTAV.setBgImage(drawableToBitmap((getResources().getDrawable(R.drawable.wh3jc))));
        mTAV.setData(model);
        mTAV.setProcessListener(new TypeApeView.ProcessListener() {
            @Override
            public void onNewWord(int index) {

            }
        });
    }
    
    private void updateDataA(){
                String test = "[00:00.000]{}全民制作人们\n" +
                "[00:03.000]{}大家好\n" +
                "[00:06.000]{}我是练习时长两年半\n" +
                "[00:10.000]{}的个人练习生\n" +
                "[00:12.000]{}喜欢\n" +
                "[00:13.000]{}唱、\n" +
                "[00:14.000]{}跳、\n" +
                "[00:15.000]{}Rap、\n" +
                "[00:17.000]{}篮球\n" +
                "[00:19.000]{}Music~";

        TypeApeModel model = Utils.createTypeApeModel(test);
        mTAV.setData(model);

    }

    public static final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
