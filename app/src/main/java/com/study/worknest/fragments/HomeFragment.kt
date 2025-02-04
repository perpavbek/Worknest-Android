package com.study.worknest.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.worknest.API.services.TaskService
import com.study.worknest.R
import com.study.worknest.adapters.CalendarAdapter
import com.study.worknest.adapters.TaskAdapter
import java.time.LocalDate


class HomeFragment: Fragment() {
    private lateinit var taskList: RecyclerView
    private lateinit var calendar: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var calendarAdapter: CalendarAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskList = view.findViewById(R.id.taskList)
        calendar = view.findViewById(R.id.calendar)
        val dates = mutableListOf(LocalDate.now(), LocalDate.now())
        val layoutManager = object : LinearLayoutManager(requireContext()) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
        taskList.layoutManager = layoutManager
        taskList.isNestedScrollingEnabled = false

        TaskService.fetchTasks(requireContext()) { fetchedTasks ->
            if (isAdded && context != null) {
                if (!fetchedTasks.isNullOrEmpty()) {
                    taskAdapter = TaskAdapter(fetchedTasks)
                    taskList.adapter = taskAdapter
                } else {
                    Toast.makeText(context, "You don't have tasks", Toast.LENGTH_SHORT).show()
                }
            }
        }

        calendar.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        calendarAdapter = CalendarAdapter(dates){date ->
            Toast.makeText(context, date.dayOfMonth.toString(), Toast.LENGTH_SHORT).show()
        }
        calendar.adapter = calendarAdapter
    }
}