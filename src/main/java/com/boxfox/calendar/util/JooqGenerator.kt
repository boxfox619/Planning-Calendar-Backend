package com.boxfox.calendar.util

import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Target


object JooqGenerator {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val dbHost = System.getenv("DB_HOST")
        val dbName = System.getenv("DB_NAME")
        val dbPort = System.getenv("DB_PORT")
        val dbUser = System.getenv("DB_USER")
        val dbPassword = System.getenv("DB_PASSWORD")
        val url = "jdbc:postgresql://$dbHost:$dbPort/$dbName"
        val configuration = Configuration()
                .withJdbc(Jdbc()
                        .withDriver("org.postgresql.Driver")
                        .withUrl(url)
                        .withUser(dbUser)
                        .withPassword(dbPassword))
                .withGenerator(Generator()
                        .withDatabase(Database()
                                .withName("org.jooq.meta.postgres.PostgresDatabase")
                                .withIncludes(".*")
                                .withExcludes("")
                                .withInputSchema("public"))
                        .withTarget(Target().withPackageName("org.gdg.withgo.data.database")
                                .withDirectory("src/main/java")))

        GenerationTool.generate(configuration)
    }
}
