package pt.isel.ls.server.exceptions

import java.lang.Exception

sealed class ServiceException(message: String) : Exception(message) {

    class BoardNotFoundException(idBoard: Int) :
        ServiceException("Board with id $idBoard not found.")

    class UserNotFoundException(idUser: Int) :
        ServiceException("User with id $idUser not found.")

    class UserCreationException(email: String) :
        ServiceException("Error creating user with email $email (already been taken).")

    class InvalidEmailException(email: String) :
        ServiceException("Error creating user with email $email (invalid email).")

    class BoardCreationException(idUser: Int) :
        ServiceException("Error creating board with id $idUser.")

    class BoardDuplicateNameException(name : String) :
        ServiceException("Error creating board with name $name.")

    class UserBoardsException(idUser: Int) :
        ServiceException("Boards not found with idUser $idUser.")

    class ListCreationOnBoardException(idBoard: Int, name: String) :
        ServiceException("Error creating a list in board with idBoard $idBoard and name $name.")

    class BoardListsException(idBoard: Int) :
        ServiceException("Lists not found in board with id $idBoard.")

    class ListNotFoundException(idList: Int) :
        ServiceException("List not found with id $idList.")

    class CardCreationDateException(endDate: String) :
        ServiceException("Error creating card in list with endDate $endDate.")

    class CardCreationException(idList: Int, name: String) :
        ServiceException("Error creating card in list with id $idList and name=$name.")

    class CardListException(idBoard: Int, idList: Int) :
        ServiceException("Cards not found in board with id $idBoard and idList=$idList.")

    class CardNotFoundException(idCard: Int) :
        ServiceException("Card not found with id $idCard.")

    class MoveCardException(idList: Int) :
        ServiceException("Error moving card in list with id $idList.")

    class UserNotFoundWithTokenException(token: String) :
        ServiceException("User not found with token=$token")

}
