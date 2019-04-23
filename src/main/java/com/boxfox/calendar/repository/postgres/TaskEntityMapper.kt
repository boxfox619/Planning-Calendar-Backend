package com.boxfox.calendar.repository.postgres

import com.boxfox.calendar.database.Tables.TASK
import com.boxfox.calendar.model.Task
import org.jooq.Record
import java.text.SimpleDateFormat

object TaskEntityMapper {
    private val format = SimpleDateFormat("yyyy-MM-dd")
    fun fromRecord(record: Record) = Task().apply {
        this.id = record.get(TASK.ID)
        this.name = record.get(TASK.NAME)
        this.date = format.format(record.get(TASK.DAY))
        this.startHour = record.get(TASK.STARTHOUR)
        this.endHour = record.get(TASK.ENDHOUR)
    }
}