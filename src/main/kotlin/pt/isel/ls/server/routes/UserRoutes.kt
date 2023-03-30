package pt.isel.ls.server.routes

import org.http4k.core.Method
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import pt.isel.ls.server.WebApi

class UserRoutes(private val webApi: WebApi) {
    operator fun invoke(): RoutingHttpHandler {
        return routes(
            "user" bind Method.POST to webApi::createUser,
            "user" bind Method.GET to webApi::getUser
        )
    }
}