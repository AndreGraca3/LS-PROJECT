package pt.isel.ls.server.services

import pt.isel.ls.User
import pt.isel.ls.server.data.userData.IUserData
import pt.isel.ls.server.exceptions.TrelloException
import pt.isel.ls.server.isValidString

class UserServices(private val userData: IUserData) {

    fun createUser(name: String, email: String): Pair<Int, String> {
        isValidString(name)
        isValidString(email)
        if (!Regex("@").containsMatchIn(email)) throw TrelloException.IllegalArgument(email)
        if (!userData.checkEmail(email)) throw TrelloException.AlreadyExists(email)
        return userData.createUser(name, email)
    }

    fun getUser(token: String): User {
        return userData.getUser(token) ?: throw TrelloException.NotFound("User")    //not sure
    }

    fun getUser(id: Int): User {
        return userData.getUser(id) ?: throw TrelloException.NotFound("User")
    }
}