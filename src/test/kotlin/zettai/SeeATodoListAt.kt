package zettai

import jettai.Zettai
import jettai.entity.HtmlPage
import jettai.entity.ListName
import jettai.entity.ToDoList
import jettai.entity.User
import org.eclipse.jetty.client.Response
import org.http4k.client.JettyClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Status
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class SeeATodoListAt {
    @Test
    fun `List owners can see their lists`() {
        // Given
        val user = "frank"
        val listName = "shipping"
        val foodToBuy = listOf("carrots", "apples", "milk")

        // When
        startTheApplication(user, listName, foodToBuy)
        val list = getToDoList(user, listName)

        // Then
        expectThat(list.listName.name).isEqualTo(listName)
        expectThat(list.items.map { it.description }).isEqualTo(foodToBuy)
    }

    private fun getToDoList(
        user: String,
        listName: String
    ): ToDoList {
        val client = JettyClient()

        val req = Request(Method.GET, "http://localhost:8080/todo/$user/$listName")
        val res = client(req)

        return if (res.status == Status.OK) parseResponse(res.bodyString())
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

    fun extractListData(request: Request): Pair<User, ListName> = TODO()
    fun fetchListContent(listId: Pair<User, ListName>): ToDoList = TODO()
    fun renderHtml(list: ToDoList): HtmlPage = TODO()
    fun createResponse(html: HtmlPage): Response = TODO()
}