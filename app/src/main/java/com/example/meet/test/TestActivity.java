package com.example.meet.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.framework.bmob.MyData;
import com.example.framework.utils.LogUtils;
import com.example.meet.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mAdd;
    private Button mDel;
    private Button mUpdate;
    private Button mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initView();
        mAdd = findViewById(R.id.add);
        mDel = findViewById(R.id.del);
        mUpdate = findViewById(R.id.update);
        mQuery = findViewById(R.id.query);
    }

    private void initView() {
        mAdd = findViewById(R.id.add);
        mAdd.setOnClickListener(this);
        mDel = findViewById(R.id.del);
        mDel.setOnClickListener(this);
        mUpdate = findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        mQuery = findViewById(R.id.query);
        mQuery.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add:
                MyData myData = new MyData();
                myData.setName("张三");
                myData.setSex(0);
                myData.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (null == e) {
                            Toast.makeText(TestActivity.this, "新增成功： "+s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.del:
                MyData myData1 = new MyData();
                myData1.setObjectId("d062680f12");
                myData1.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (null == e){
                            Toast.makeText(TestActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(TestActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.update:

                //更新Person表里面id为6b6c11c537的数据，address内容更新为“北京朝阳”
                MyData p2 = new MyData();
                p2.setName("李四");
                p2.update("8a9d74cd14", new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(TestActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TestActivity.this, "更新失败", Toast.LENGTH_SHORT).show();

                        }
                    }

                });

                break;
            case R.id.query:

//                //1 通过ID查询
                BmobQuery<MyData> bmobQuery = new BmobQuery<>();
//                bmobQuery.getObject("e3f703ee4f", new QueryListener<MyData>() {
//                    @Override
//                    public void done(MyData myData, BmobException e) {
//                        if (e == null){
//                            Toast.makeText(TestActivity.this, "姓名："+myData.getName() + " 性别："+myData.getSex(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

                //2.条件查询
//                bmobQuery.addWhereEqualTo("name","张三");
                //全部查询
                bmobQuery.findObjects(new FindListener<MyData>() {
                    @Override
                    public void done(List<MyData> list, BmobException e) {
                        if (e == null){
                            if (list != null && list.size()>0){
                                Toast.makeText(TestActivity.this, list.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
                break;
        }
    }
}
