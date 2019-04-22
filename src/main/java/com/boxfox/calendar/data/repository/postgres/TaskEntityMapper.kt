package com.boxfox.calendar.data.repository.postgres

import com.boxfox.calendar.data.database.Tables.TASK
import com.boxfox.calendar.data.model.Task
import org.jooq.Record

object TaskEntityMapper {
    fun fromRecord(record: Record) = Task(record.get(TASK.ID)).apply {
        this.name = record.get(TASK.NAME)
        this.date = record.get(TASK.DAY)
        this.hour = record.get(TASK.HOUR)
    }
}