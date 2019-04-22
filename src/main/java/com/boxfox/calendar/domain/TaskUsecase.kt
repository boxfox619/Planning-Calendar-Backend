package com.boxfox.calendar.domain

import io.reactivex.Completable
import io.reactivex.Single
import com.boxfox.calendar.data.model.Task

interface TaskUsecase : Usecase {
    fun loadTasks(year: Int, month: Int): Single<List<Task>>
    fun createTask(task: Task): Single<Task>
    fun removeTask(id: Int): Completable
    fun editTask(id: Int, task: Task): Completable
}