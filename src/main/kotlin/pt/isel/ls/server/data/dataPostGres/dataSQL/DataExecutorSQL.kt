package pt.isel.ls.server.data.dataPostGres.dataSQL

import pt.isel.ls.server.data.dataInterfaces.DataExecutor
import pt.isel.ls.server.exceptions.TrelloException
import pt.isel.ls.server.exceptions.map
import pt.isel.ls.server.utils.setup
import java.sql.Connection
import java.sql.SQLException

class DataExecutorSQL<R> : DataExecutor<R> {
    override fun execute(action: (Connection) -> R): R {
        val dataSource = setup()
        try {
            return dataSource.connection.use { con ->
                con.autoCommit = false
                action(con).also { con.autoCommit = true }
            }
        } catch (e: SQLException) {
            println(e)
            val trelloException = map[e.sqlState] ?: throw TrelloException.InternalError()
            throw trelloException(e.localizedMessage)
        }
    }
}