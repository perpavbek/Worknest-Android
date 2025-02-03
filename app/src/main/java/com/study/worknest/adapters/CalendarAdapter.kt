package com.study.worknest.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.study.worknest.R
import java.time.LocalDate

class CalendarAdapter(
    private var dates: MutableList<LocalDate>
) : RecyclerView.Adapter<CalendarAdapter.DateVewHolder>() {

    class DateVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.day)
        val weekDay: TextView = itemView.findViewById(R.id.week_day)
        val month: TextView = itemView.findViewById(R.id.month)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateVewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.date_item, parent, false)
        return DateVewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DateVewHolder, position: Int) {
        val inflater = LayoutInflater.from(holder.itemView.context)
        val date = dates[position]
        holder.day.text = date.dayOfMonth.toString()
        holder.weekDay.text = date.dayOfWeek.toString()
        holder.month.text = date.month.toString()
    }

    override fun getItemCount(): Int = dates.size
}