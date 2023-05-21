package pt.isel.ls.server.data.dataMem

import pt.isel.ls.server.data.dataInterfaces.models.ListData
import pt.isel.ls.server.exceptions.TrelloException
import pt.isel.ls.server.utils.BoardList
import java.sql.Connection

class ListDataMem : ListData {

    val lists = mutableListOf<BoardList>()

    override fun createList(idBoard: Int, name: String, con: Connection): Int {
        val newBoardList = BoardList(getNextId(), idBoard, name)
        lists.add(newBoardList)
        return newBoardList.idList
    }

    override fun getList(idList: Int, idBoard: Int, con: Connection): BoardList {
        return lists.find { it.idList == idList && it.idBoard == idBoard }
            ?: throw TrelloException.NotFound("List")
    }

    override fun getListsOfBoard(idBoard: Int, con: Connection): List<BoardList> {
        //return lists.filter { it.idBoard == idBoard }.subList(skip, skip + limit)
        TODO("Not yet implemented.")
    }

    override fun deleteList(idList: Int, idBoard: Int, con: Connection) {
        if (!lists.removeIf { it.idList == idList && it.idBoard == idBoard }) throw TrelloException.NoContent("List")
    }

    override fun getListCount(idBoard: Int, con: Connection): Int {
        return lists.count { it.idBoard == idBoard }
    }

    private fun getNextId(): Int {
        return if (lists.isEmpty()) 1 else lists.last().idList + 1
    }
}
