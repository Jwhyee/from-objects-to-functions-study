package jettai

import jettai.entity.ListName
import jettai.entity.ToDoItem
import jettai.entity.ToDoList
import jettai.entity.User
import org.http4k.server.Jetty
import org.http4k.server.asServer

fun main() {
    val user = "frank"
    val listName = "shipping"
    val items = listOf("carrots", "apples", "milk")
    val toDoList = ToDoList(
        ListName(listName), items.map(::ToDoItem)
    )
    val lists = mapOf(User(user) to listOf(toDoList))
    Zettai(lists).asServer(Jetty(8080)).start()
}