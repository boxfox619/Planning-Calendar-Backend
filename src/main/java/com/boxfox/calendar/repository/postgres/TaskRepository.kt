package com.boxfox.calendar.repository.postgres

import com.boxfox.calendar.database.Tables.TASK
import com.boxfox.calendar.model.RecordNotFoundException
import io.reactivex.Completable
import io.reactivex.Single
import com.boxfox.calendar.model.Task
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.util.Postgresql
import org.jooq.impl.DSL
import java.sql.Date
import java.sql.SQLException
import java.util.*

class TaskRepository : TaskUsecase {

    private fun getCalendar(year: Int, month: Int, amount: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.add(Calendar.MONTH, amount)
        return calendar
    }

    override fun loadTasks(year: Int, month: Int): Single<List<Task>> = Single.create { sub ->
        try {
            val tasks = Postgresql.dsl().use { dsl ->
                val prevCalendar = getCalendar(year, month, -1)
                val nextCalendar = getCalendar(year, month, 1)
                dsl.selectFrom(TASK)
                        .where(DSL.year(TASK.DAY).eq(year).and(DSL.month(TASK.DAY).eq(month))
                                .or(DSL.year(TASK.DAY).eq(prevCalendar.get(Calendar.YEAR)).and(DSL.month(TASK.DAY).eq(prevCalendar.get(Calendar.MONTH))))
                                .or(DSL.year(TASK.DAY).eq(nextCalendar.get(Calendar.YEAR)).and(DSL.month(TASK.DAY).eq(nextCalendar.get(Calendar.MONTH))))
                        ).fetch()
                        .map { TaskEntityMapper.fromRecord(it) }
            }
            sub.onSuccess(tasks)
        } catch (e: SQLException) {
            sub.onError(e)
        }
    }

    override fun createTask(task: Task): Single<Task> = Single.create { sub ->
        try {
            val insertedTask = Postgresql.dsl().use { dsl ->
                dsl.insertInto(TASK).columns(TASK.NAME, TASK.DAY, TASK.STARTHOUR, TASK.ENDHOUR)
                        .values(task.name, Date.valueOf(task.date), task.startHour, task.endHour)
                        .returning()
                        .fetchOne()
                        .map { TaskEntityMapper.fromRecord(it) }
            }
            sub.onSuccess(insertedTask)
        } catch (e: SQLException) {
            sub.onError(e)
        }
    }

    override fun removeTask(id: Int) = Completable.create { sub ->
        try {
            val updated = Postgresql.dsl().use { it.deleteFrom(TASK).where(TASK.ID.eq(id)).execute() }
            when (updated) {
                0 -> sub.onError(RecordNotFoundException("task not found by id : $id"))
                else -> sub.onComplete()
            }
        } catch (e: SQLException) {
            sub.onError(e)
        }
    }

    override fun editTask(id: Int, task: Task) = Completable.create { sub ->
        try {
            val updated = Postgresql.dsl().use { dsl ->
                dsl.update(TASK).set(TASK.NAME, task.name)
                        .set(TASK.DAY, Date.valueOf(task.date))
                        .set(TASK.STARTHOUR, task.startHour)
                        .set(TASK.ENDHOUR, task.endHour)
                        .where(TASK.ID.eq(id))
                        .execute()
            }
            when (updated) {
                0 -> sub.onError(RecordNotFoundException("task not found by id : $id"))
                else -> sub.onComplete()
            }
        } catch (e: SQLException) {
            sub.onError(e)
        }
    }

}