import {fetchReq} from "../auxs/utils.js";
import {createHTMLCard} from "../components/modelComponents.js";
import {createElement} from "../components/components.js";

export const cardFunc = async (card) => {

    const fetchedCard = await fetchReq(`board/${card.idBoard}/card/${card.idCard}`,"GET")

    document.querySelector("#CardTitleModal").innerText = fetchedCard.name
    document.querySelector("#CardStartDateModal").innerText = fetchedCard.startDate

    if(fetchedCard.description !== null) {
        document.querySelector("#CardDescModal").innerText = fetchedCard.description
    }
    if(fetchedCard.endDate !== null) {
        document.querySelector("#endDateTime").value = fetchedCard.endDate.replace(" ", "T")
    }

    document.querySelector("#cardSaveButton").onclick = async () => saveCard(fetchedCard)
    document.querySelector("#cardArchiveButton").onclick = async () => archiveCard(fetchedCard)
    document.querySelector("#cardDeleteButton").onclick = async () => deleteCard(fetchedCard)

    $('#cardModal').modal('show')
}

export async function createCard(listCards, list) {
    const input = createElement("input")
    listCards.appendChild(input)
    listCards.scrollTop = listCards.scrollHeight
    input.focus()
    const handleAddCard = async () => {
        if(input.value.trim() === ""){
            listCards.removeChild(input)
            return
        }
        await addCard(listCards, input, list)
    }

    input.addEventListener("focusout", handleAddCard)
    input.addEventListener("keydown", (event) => {
        if(event.key !== "Enter" || event.repeat) return
        input.removeEventListener("focusout", handleAddCard)
        handleAddCard()
    })
}

async function addCard(listCards, input, list) {
    const card = {
        idList: list.idList,
        name: input.value,
        description: null,
        endDate: null
    }
    input.remove()
    const cardId = await fetchReq(`board/${list.idBoard}/card`, "POST", card)

    card.idList = list.idList
    card.idBoard = list.idBoard
    card.idCard = cardId
    card.idx = listCards.lastChild? parseInt(listCards.lastChild.dataset.idx) + 1 : 1
    const cardElem = createHTMLCard(card)
    cardElem.addEventListener("click", () => {cardFunc(card)})
    listCards.appendChild(cardElem)
}

async function archiveCard(card) {
    const cardToMove = document.querySelector(`#Card${card.idCard}`)
    const list = document.querySelector(`#list${card.idList}`)
    const archivedContainer = document.querySelector(`#dropdownMenu-archived`)

    if(!card.archived) {
        // move to archived
        await fetchReq(`board/${card.idBoard}/card/${card.idCard}/archive`, "PUT")
        list.removeChild(cardToMove)

        const newArchived = createElement("li", null, "dropdown-item",
            `Card${card.idCard}`,
            createElement("span", "📋 " + card.name)
        )
        newArchived.addEventListener("click", async () => cardFunc(card))
        newArchived.classList.add("clickable")

        archivedContainer.appendChild(newArchived)
    } else {
        // TODO: waiting for Backend implementation
        // return to origin TODO: check if list exits if i want to unarchive
        // await fetchReq(`board/${card.idBoard}/card/${card.idCard}/dearchive`, "PUT")
        archivedContainer.removeChild(cardToMove)
        const DeArchivedCard = createHTMLCard(card, async () => cardFunc(card))
        list.appendChild(DeArchivedCard)
    }

    $('#cardModal').modal('hide')
}

async function deleteCard(card) {
    await fetchReq(`board/${card.idBoard}/card/${card.idCard}`, "DELETE")

    const cardToDelete = document.querySelector(`#Card${card.idCard}`)

    if(!card.archived) {
        const list = document.querySelector(`#list${card.idList}`)
        list.removeChild(cardToDelete)
    } else {
        const archivedContainer = document.querySelector(`#dropdownMenu-archived`)
        archivedContainer.removeChild(cardToDelete)
    }

    $('#cardModal').modal('hide')
}

async function saveCard(card) {
    const newEndDate = document.querySelector("#endDateTime").value.replace("T", " ")

    const newDescription = document.querySelector("#Description-textBox").value

    const Changes = {
        archived: card.archived,
        description: newDescription,
        endDate: newEndDate
    }

    await fetchReq(`board/${card.idBoard}/card/${card.idCard}/update`, "PUT", Changes)
    $('#cardModal').modal('hide')
}
