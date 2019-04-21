package com.boxfox.calendar.data.repository.postgres

import io.reactivex.Completable
import io.reactivex.Single
import com.boxfox.calendar.data.model.Task
import com.boxfox.calendar.domain.TaskUsecase

class TaskRepository : TaskUsecase {
    override fun loadTasks(year: Int, month: Int): Single<List<Task>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTask(task: Task): Single<Task> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeTask(id: Int): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editTask(id: Int, task: Task): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}