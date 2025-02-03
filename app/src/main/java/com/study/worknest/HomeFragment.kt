package com.study.worknest

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val task = Task(null, "Name of task", 2, "Description", "In Progress", "Low", LocalDate.now(), 2, 2)
        val tasks = mutableListOf(task, task, task)

        taskList.layoutManager = LinearLayoutManager(requireContext())
        taskAdapter = TaskAdapter(tasks)
        taskList.adapter = taskAdapter

        calendar.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        calendarAdapter = CalendarAdapter(dates)
        calendar.adapter = calendarAdapter
    }
}