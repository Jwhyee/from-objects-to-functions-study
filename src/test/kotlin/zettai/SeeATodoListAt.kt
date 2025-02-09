package zettai

import org.http4k.client.JettyClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import strikt.api.expectThat

class SeeATodoListAt {
    private fun getTodoList(
        user: String,
        listName: String
    ): ToDoList {
        val client = JettyClient()
        val req = Request(Method.GET, "http://localhost:8080/todo/$user/$listName")
        val res = client(req)
        return if(res.status == Status.OK) parseResponse(res)
        else fail(res.toMessage())
    }
    private fun parseResponse(html: String): ToDoList = TODO("parse the response")
    private fun startTheApplication(
        user: String,
        listName: String,
        items: List<String>
    ) {
        val server = Zettai().asServer(Jetty(8081))
    }

    @Test
    fun `List owners can see their lists`() {
        // Given
        val user = "frank"
        val listName = "shipping"
        val foodToBuy = listOf("carrots", "apples", "milk")

        // When
        startTheApplication(user, listName, foodToBuy)
        val list = getTodoList(user, listName)

        // Then
        expectThat(list.name).isEqualTo(listName)
        expectThat(list.items).isEqualTo(foodToBuy)
    }
}