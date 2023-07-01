package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.ui.list.TaskViewModel
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var detailTaskViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory)[DetailTaskViewModel::class.java]

        //TODO 11 : Show detail task and implement delete action
        val id = intent.getIntExtra(TASK_ID, 0)
        detailTaskViewModel.setTaskId(id)
        detailTaskViewModel.task.observe(this) { task ->
            if (task != null) {
                val date = DateConverter.convertMillisToString(task.dueDateMillis)
                supportActionBar?.title = task.title
                findViewById<EditText>(R.id.detail_ed_title).setText(task.title)
                findViewById<EditText>(R.id.detail_ed_description).setText(task.description)
                findViewById<EditText>(R.id.detail_ed_due_date).setText(date)
            }
        }

        findViewById<Button>(R.id.btn_delete_task).setOnClickListener {
            detailTaskViewModel.deleteTask()
            Intent(this, TaskActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }
}