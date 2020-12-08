package com.example.todotask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.todotask.adapter.ToDoAdapter;
import com.example.todotask.models.ToDoModel;
import com.example.todotask.utils.DatabaseHandler;
import com.example.todotask.DialogCloseListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private RecyclerView taskRecycleView;
    private ToDoAdapter taskAdapter;
    private DatabaseHandler db;
    private List<ToDoModel> taskList;
    private Button newTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        db = new DatabaseHandler(this);
        taskRecycleView = findViewById(R.id.taskRecyclerView);
        taskRecycleView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new ToDoAdapter(db, this);
        taskRecycleView.setAdapter(taskAdapter);

        db.openDatabase();
        taskList = db.getAllTasks();
        taskAdapter.setTasks(taskList);

        newTask = findViewById(R.id.newTask);
        newTask.setOnClickListener(v -> AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG));

    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        taskAdapter.setTasks(taskList);
        taskAdapter.notifyDataSetChanged();
    }
}