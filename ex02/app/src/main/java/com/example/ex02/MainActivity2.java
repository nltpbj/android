package com.example.ex02;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    ArrayList<String> array=new ArrayList<>();
    MyAdapter adapter;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.데이터생성
        array.add("강감찬");
        array.add("이순신");
        array.add("을지문덕");
        //2.어댑터생성
        adapter = new MyAdapter();
        //3. adapter->ListView
        list=findViewById(R.id.list);
        list.setAdapter(adapter);

        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name=findViewById(R.id.name);
                String strName=name.getText().toString();
                array.add(strName);
                adapter.notifyDataSetChanged();
            }
        });
    }//create

    //어댑터생성
    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return array.size();
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View layout = getLayoutInflater().inflate(
                    R.layout.item, viewGroup, false);
            TextView name = layout.findViewById(R.id.name);
            String strName = array.get(i);
            name.setText(strName);
            return layout;
        }
    }
}//Activity