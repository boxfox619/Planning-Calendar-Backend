package com.boxfox.calendar.data.repository.postgres

import com.boxfox.calendar.data.database.Tables.TASK
import com.boxfox.calendar.data.model.RecordNotFoundException
import io.reactivex.Completable
import io.reactivex.Single
import com.boxfox.calendar.data.model.Task
import com.boxfox.calendar.domain.TaskUsecase
import com.boxfox.calendar.service.Postgresql
import org.jooq.impl.DSL
import java.lang.NullPointerException
import java.sql.SQLException

class TaskRepository : TaskUsecase {

    override fun loadTasks(year: Int, month: Int): Single<List<Task>> = Single.create { sub ->
        try {
            val tasks = Postgresql.dsl().use { dsl ->
                dsl.selectFrom(TASK).where(DSL.year(TASK.DAY).eq(year).and(DSL.month(TASK.DAY).eq(month))).fetch()
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
                val taskRecord = dsl.insertInto(TASK).columns(TASK.NAME, TASK.DAY, TASK.HOUR)
                        .values(task.name, task.sqlDate, task.hour)
                        .returning()
                        .fetchOne()
                TaskEntityMapper.fromRecord(taskRecord)
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
                dsl.update(TASK).set(TASK.NAME, task.name).set(TASK.DAY, task.sqlDate).set(TASK.HOUR, task.hour)
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