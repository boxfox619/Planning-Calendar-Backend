package com.boxfox.calendar.domain

import io.reactivex.Completable
import io.reactivex.Single
import com.boxfox.calendar.model.Task
import java.sql.Date

interface TaskUsecase : Usecase {
    fun loadTasks(year: Int, month: Int): Single<List<Task>>
    fun createTask(name: String, date: Date, hour: Short, endHour: Short): Single<List<Task>>
    fun removeTask(id: Int): Completable
    fun editTask(id: Int, task: Task): Completable
}