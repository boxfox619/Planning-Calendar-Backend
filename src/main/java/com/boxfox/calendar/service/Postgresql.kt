package com.boxfox.calendar.service

import org.jooq.impl.DSL
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object Postgresql {

    @Throws(SQLException::class)
    fun create(): Connection {
        val dbHost = System.getenv("DB_HOST")
        val dbName = System.getenv("DB_NAME")
        val dbPort = System.getenv("DB_PORT")
        val dbUser = System.getenv("DB_USER")
        val dbPassword = System.getenv("DB_PASSWORD")
        val url = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
        return DriverManager.getConnection(url, dbUser, dbPassword)
    }

    fun dsl() = DSL.using(create())
}
