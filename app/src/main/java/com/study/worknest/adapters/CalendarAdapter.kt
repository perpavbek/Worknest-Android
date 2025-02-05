package com.study.worknest.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.study.worknest.R
import java.time.LocalDate

class CalendarAdapter(
    private var dates: MutableList<LocalDate>,
    private val onClickDate: (LocalDate) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.DateVewHolder>() {
    private var selectedDate: Int? = null
    class DateVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layout: ConstraintLayout = itemView.findViewById(R.id.date_item)
        val day: TextView = itemView.findViewById(R.id.day)
        val weekDay: TextView = itemView.findViewById(R.id.week_day)
        val month: TextView = itemView.findViewById(R.id.month)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateVewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_item, parent, false)
        selectedDate = 0
        return DateVewHolder(view)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DateVewHolder, @SuppressLint("RecyclerView") position: Int) {
        val inflater = LayoutInflater.from(holder.itemView.context)
        val date = dates[position]
        holder.day.text = date.dayOfMonth.toString()
        holder.weekDay.text = date.dayOfWeek.toString()
        holder.month.text = date.month.toString()
        holder.layout.alpha = if (position == selectedDate) 1.0f else 0.5f

        holder.layout.setOnClickListener {
            selectedDate = position
            notifyDataSetChanged()
            onClickDate(date)
        }
    }

    override fun getItemCount(): Int = dates.size
}