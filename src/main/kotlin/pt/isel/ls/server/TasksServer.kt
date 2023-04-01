package pt.isel.ls.server

import org.http4k.routing.routes
import org.http4k.server.Jetty
import org.http4k.server.asServer
import pt.isel.ls.server.api.*
import pt.isel.ls.server.data.dataMem.DataMem
import pt.isel.ls.server.routes.BoardRoutes
import pt.isel.ls.server.routes.CardRoutes
import pt.isel.ls.server.routes.ListRoutes
import pt.isel.ls.server.routes.UserRoutes
import pt.isel.ls.server.services.Services
import pt.isel.ls.server.utils.logger


fun main() {
    val data = DataMem()
    // val data = DataSQL()
    val services = Services(data)
    val webAPI = WebAPI(services)

    val app = routes(
        UserRoutes(webAPI.userAPI)(),
        BoardRoutes(webAPI.boardAPI)(),
        ListRoutes(webAPI.listAPI)(),
        CardRoutes(webAPI.cardAPI)()
    )

    val jettyServer = app.asServer(Jetty(8080)).start()
    logger.info("Server started listening...")

    readln()
    jettyServer.stop()

    logger.info("Server stopped Listening.")
}
