package pt.isel.ls.server.data.transactionManager.transactions

import pt.isel.ls.server.utils.setup
import java.sql.Connection

class SQLTransaction: TransactionCtx {
    override val con: Connection = setup().connection

    override fun init() {
        con.autoCommit = false
    }

    override fun commit() {
        con.commit()
    }

    override fun rollback() {
        con.rollback()
    }
}