package com.example.lab_4;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        // Создание таблицы и добавление записей
        dbHelper.getWritableDatabase();
        dbHelper.deleteAllData();
        dbHelper.insertData("Муталиев Даниил Аркадьевич");
        dbHelper.insertData("Муталиев Аркадий Давлетович");
        dbHelper.insertData("Муталиев Давлет Даниилович");
        dbHelper.insertData("Лазутчик Алексей Николаевич");
        dbHelper.insertData("Лазутчик Николай Аркадьевич");

        // Обработчик нажатия на первую кнопку
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = dbHelper.getAllData();
                if (cursor.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "Нет данных", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {
                    buffer.append("ID: ").append(cursor.getString(0)).append("\n");
                    buffer.append("ФИО: ").append(cursor.getString(1)).append("\n");
                    buffer.append("Время добавления: ").append(cursor.getString(2)).append("\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Одногруппники");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        // Обработчик нажатия на вторую кнопку
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.insertData("Новый студент");
                Toast.makeText(MainActivity.this, "Запись добавлена", Toast.LENGTH_SHORT).show();
            }
        });

        // Обработчик нажатия на третью кнопку
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dbHelper.updateLastRecord("Иванов Иван Иванович")) {
                    Toast.makeText(MainActivity.this, "ФИО заменено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}