package jettai.entity

data class ToDoList(
    val listName: ListName, val items: List<ToDoItem>
)

data class ToDoItem(val description: String)

enum class ToDoStatus(
    val status: String
) {
    TODO("ToDo"),
    IN_PROGRESS("In Progress"),
    DONE("Done"),
    BLOCKED("Blocked")
}

data class ListName(val name: String)

