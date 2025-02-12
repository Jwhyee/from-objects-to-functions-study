package jettai

import jettai.entity.*
import jettai.util.andThen
import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes

class Zettai(
    val lists: Map<User, List<ToDoList>>
): HttpHandler {
    val routes = routes(
        "/todo/{user}/{list}" bind Method.GET to ::showList
    )

    override fun invoke(req: Request): Response = routes(req)

    var processFun = ::extractListData andThen
            ::fetchListContent andThen
            ::renderHtml andThen
            ::createResponse

    private fun showList(req: Request): Response = processFun(req)

    /** 요청에서 사용자 이름과 목록을 뽑아낸다. */
    fun extractListData(request: Request): Pair<User, ListName> {
        val user = request.path("user").orEmpty()
        val list = request.path("list").orEmpty()
        return Pair(User(user), ListName(list))
    }

    /** 실제 사용자 이름과 목록 이름을 키로 사용해 저장소에서 목록 데이터를 가져옴 */
    fun fetchListContent(listId: Pair<User, ListName>): ToDoList =
        lists[listId.first]?.firstOrNull { it.listName == listId.second }
            ?: error("List unknown")

    /** 목록을 모든 콘텐츠가 포함된 HTML 페이지로 변환하는 역할 */
    // language=HTML
    fun renderHtml(todoList: ToDoList): HtmlPage = HtmlPage("""
        <html>
            <body>
                <h1>Zettai</h1>
                <h2>${todoList.listName.name}</h2>
                <table>
                    <tbody>
                        ${renderItems(todoList.items)}
                    </tbody>
                </table>
            </body>
        </html>
    """.trimIndent())

    // language=HTML
    fun renderItems(items: List<ToDoItem>) = items.map {
        """
        <tr><td>${it.description}</td></tr>
        """.trimIndent()
    }.joinToString("")

    /** 생성된 HTML 페이지를 본문으로 하는 HTTP 응답 생성 */
    fun createResponse(html: HtmlPage): Response =
        Response(Status.OK).body(html.raw)
}