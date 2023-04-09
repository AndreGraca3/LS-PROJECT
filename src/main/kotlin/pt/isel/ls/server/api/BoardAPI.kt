package pt.isel.ls.server.api

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.path
import pt.isel.ls.server.annotations.Auth
import pt.isel.ls.server.exceptions.TrelloException
import pt.isel.ls.server.services.BoardServices
import pt.isel.ls.server.utils.BoardIn
import pt.isel.ls.server.utils.BoardOut
import pt.isel.ls.server.utils.IDUser

class BoardAPI(private val services: BoardServices) {

    fun createBoard(request: Request): Response {
        return handleRequest(request, ::createBoardInternal)
    }

    fun getBoard(request: Request): Response {
        return handleRequest(request, ::getBoardInternal)
    }

    fun addUserToBoard(request: Request): Response {
        return handleRequest(request, ::addUserToBoardInternal)
    }

    fun getBoardsFromUser(request: Request): Response {
        return handleRequest(request, ::getBoardsFromUserInternal) // check if this return boards or idBoard's
    }

    fun getUsersFromBoard(request: Request) : Response {
        return handleRequest(request, ::getUsersFromBoardInternal)
    }

    @Auth
    private fun createBoardInternal(request: Request, token: String): Response {
        val newBoard = Json.decodeFromString<BoardIn>(request.bodyString())
        return createRsp(CREATED, BoardOut(services.createBoard(token, newBoard.name, newBoard.description)))
    }

    @Auth
    private fun getBoardInternal(request: Request, token: String): Response {
        val idBoard = getPathParam(request, "idBoard")
        return createRsp(OK, services.getBoard(token, idBoard))
    }

    @Auth
    private fun addUserToBoardInternal(request: Request, token: String): Response {
        val idBoard = getPathParam(request, "idBoard")
        val objIdUser = Json.decodeFromString<IDUser>(request.bodyString())
        return createRsp(OK, services.addUserToBoard(token, objIdUser.idUser, idBoard))
    }

    @Auth
    @Suppress("unused")
    private fun getBoardsFromUserInternal(request: Request, token: String): Response {
        return createRsp(OK, services.getBoardsFromUser(token))
    }

    @Auth
    private fun getUsersFromBoardInternal(request: Request, token: String) : Response{
        val idBoard = getPathParam(request, "idBoard")
        return createRsp(OK, services.getUsersFromBoard(token, idBoard))
    }
}
