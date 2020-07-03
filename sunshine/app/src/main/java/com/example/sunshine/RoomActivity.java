package com.example.sunshine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sunshine.database.AddTaskActivity2;
import com.example.sunshine.database.AppDatabase;
import com.example.sunshine.database.AppExecutors;
import com.example.sunshine.database.MainViewModel;
import com.example.sunshine.database.TaskEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RoomActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener{
    // Constant for logging
    private static final String TAG = RoomActivity.class.getSimpleName();
    // Member variables for the adapter and RecyclerView
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private AppDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        mRecyclerView = findViewById(R.id.recyclerViewTasks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Initialize the adapter and attach it to the RecyclerView
        mAdapter = new TaskAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                // Here is where you'll implement swipe to delete
                // COMPLETED (1) Get the diskIO Executor from the instance of AppExecutors and
                // call the diskIO execute method with a new Runnable and implement its run method
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        // COMPLETED (3) get the position from the viewHolder parameter
                        int position = viewHolder.getAdapterPosition();
                        List<TaskEntry> tasks = mAdapter.getTasks();
                        // COMPLETED (4) Call deleteTask in the taskDao with the task at that position
                        mDb.taskDao().deleteTask(tasks.get(position));
                        // COMPLETED (6) Call retrieveTasks method to refresh the UI
//                        retrieveTasks();
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);
        /*
         Set the Floating Action Button (FAB) to its corresponding View.
         Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Create a new intent to start an AddTaskActivity
                Intent addTaskIntent = new Intent(RoomActivity.this, AddTaskActivity2.class);
                startActivity(addTaskIntent);
            }
        });
        mDb=AppDatabase.getInstance(getApplicationContext());
        // completed: Call retrieveTasks from here and remove the onResume method for LiveData
        setupViewModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewModel();
    }

    private void setupViewModel() {
        /*
        third version: use ViewModel
         */
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTasks().observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(List<TaskEntry> taskEntries) {
                Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                mAdapter.setTasks(taskEntries);
            }
        });
        /*
        second version: use LiveData and observe
         */
        // when query data, we use LiveData instead of executor, because liveData is more effiecent handling
        // query on condition when the data has not been changed.
//        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
//        LiveData<List<TaskEntry>> tasks = mDb.taskDao().loadAllTasks();
//        tasks.observe(this, new Observer<List<TaskEntry>>() {
//            @Override
//            public void onChanged(List<TaskEntry> taskEntries) {
//                mAdapter.setTasks(taskEntries);
//            }
//        });

        /*
        first version: use executor
         */
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                 final List<TaskEntry> tasks = mDb.taskDao().loadAllTasks();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter.setTasks(tasks);
//                    }
//                });
//            }
//        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(RoomActivity.this,AddTaskActivity2.class);
        intent.putExtra(AddTaskActivity2.EXTRA_TASK_ID,itemId);
        startActivity(intent);
    }
}