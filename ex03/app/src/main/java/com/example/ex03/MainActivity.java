package com.example.ex03;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    DBHelper helper;
    SQLiteDatabase db;
    MyAdpter adpter;
    String sql="select _id, name, juso, phone, photo from address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("주소관리");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        helper = new DBHelper(this);
        db = helper.getReadableDatabase();

        //데이터생성
        Cursor cursor=db.rawQuery(sql, null);
        adpter = new MyAdpter(this, cursor);
        ListView list=findViewById(R.id.list);
        list.setAdapter(adpter);

        findViewById(R.id.btnInsert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
            }
        });
    }//onCreate

    class MyAdpter extends CursorAdapter{
        public MyAdpter(Context context, Cursor c) {
            super(context, c);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            View view=getLayoutInflater().inflate(R.layout.item,viewGroup,false);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            int id = cursor.getInt(0);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(MainActivity.this, UpdateActivity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            });
            CircleImageView photo=view.findViewById(R.id.photo);
            String strPhoto=cursor.getString(4);
            if(strPhoto.equals("")){
                photo.setImageResource(R.drawable.person);
            }else{
                photo.setImageBitmap(BitmapFactory.decodeFile(strPhoto));
            }
            TextView name=view.findViewById(R.id.name);
            name.setText(cursor.getString(1));
            TextView phone=view.findViewById(R.id.phone);
            phone.setText(cursor.getString(3));
            TextView juso=view.findViewById(R.id.juso);
            juso.setText(cursor.getString(2));
            view.findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder box=new AlertDialog.Builder(MainActivity.this);
                    box.setTitle("질의");
                    box.setMessage(id + "번 주소를 삭제하실래요?");
                    box.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String sql="delete from address where _id=" + id;
                            db.execSQL(sql);
                            onRestart();
                        }
                    });
                    box.setNegativeButton("아니오", null);
                    box.show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Cursor cursor = db.rawQuery(sql, null);
        adpter.changeCursor(cursor);
    }
}//Activity