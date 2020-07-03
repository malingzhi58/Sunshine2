package com.example.sunshine.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class AddTaskViewModel extends ViewModel {
    private LiveData<TaskEntry> task;
    public AddTaskViewModel(AppDatabase db,int taskId){
        task=db.taskDao().loadTaskById(taskId);
    }

    public LiveData<TaskEntry> getTask() {
        return task;
    }
}
