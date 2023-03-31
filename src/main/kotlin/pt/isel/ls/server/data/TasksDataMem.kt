package pt.isel.ls.server.data

import pt.isel.ls.server.exceptions.TrelloException
import pt.isel.ls.server.utils.*

val users = mutableListOf<User>()
val usersBoards = mutableListOf<UserBoard>()
val boards = mutableListOf<Board>()
val lists = mutableListOf<BoardList>()
val cards = mutableListOf<Card>()

// Data Aux functions
fun getNextId(clazz: Class<*>): Int {
    return when (clazz.simpleName) {
        User::class.simpleName -> if (users.isNotEmpty()) users.last().idUser.inc() else 0
        Board::class.simpleName -> if (boards.isNotEmpty()) boards.last().idBoard.inc() else 0
        BoardList::class.simpleName -> if (lists.isNotEmpty()) lists.last().idList.inc() else 0
        Card::class.simpleName -> if (cards.isNotEmpty()) cards.last().idCard.inc() else 0
        else -> error("Unknown object type: ${clazz.toString()::class.simpleName}")
    }
}

fun initialState() { // for test purposes
    users.clear()
    usersBoards.clear()
    boards.clear()
    lists.clear()
    cards.clear()
}

fun getUser(token : String) : User {
    return users.find { it.token == token } ?: throw TrelloException.NotAuthorized()
}

fun getUser(idUser : Int) : User {
    return users.find { it.idUser == idUser } ?: throw TrelloException.NotFound("User")
}

fun checkUserInBoard(idUser: Int, idBoard: Int): UserBoard {
    return usersBoards.find { it.idUser == idUser && it.idBoard == idBoard } ?: throw TrelloException.NotFound("Board")
}

fun checkListInBoard(idList: Int, idBoard: Int) : BoardList {
    return lists.find { it.idBoard == idBoard && it.idList == idList } ?: throw TrelloException.NotFound("Board")
}