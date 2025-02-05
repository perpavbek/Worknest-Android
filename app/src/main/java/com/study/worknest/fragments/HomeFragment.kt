package com.study.worknest.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

    private lateinit var taskList: RecyclerView
    private lateinit var calendar: RecyclerView
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var calendarAdapter: CalendarAdapter

    private var selectedDate: LocalDate? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskList = view.findViewById(R.id.taskList)
        calendar = view.findViewById(R.id.calendar)

        taskList.layoutManager = LinearLayoutManager(requireContext())
        taskList.isNestedScrollingEnabled = false
        calendar.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        selectedDate = savedInstanceState?.getString("SELECTED_DATE")?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_DATE)
        }

        TaskService.getTaskDates(requireContext()) { fetchedDates ->
            if (fetchedDates.isNullOrEmpty()) {
                showToast("You don't have tasks")
                return@getTaskDates
            }

            calendarAdapter = CalendarAdapter(fetchedDates) { date ->
                selectedDate = date
                loadTasksForDate(date)
            }
            calendar.adapter = calendarAdapter

            val dateToLoad = selectedDate ?: fetchedDates.first()
            selectedDate = dateToLoad
            loadTasksForDate(dateToLoad)
        }
    }

    private fun loadTasksForDate(date: LocalDate) {
        TaskService.getTasksByDate(requireContext(), date) { fetchedTasks ->
            if (fetchedTasks.isNullOrEmpty()) {
                showToast("No tasks for selected date")
            } else {
                taskAdapter = TaskAdapter(fetchedTasks)
                taskList.adapter = taskAdapter
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NewApi")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        selectedDate?.let {
            outState.putString("SELECTED_DATE", it.format(DateTimeFormatter.ISO_DATE))
        }
    }
}
