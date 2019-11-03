package io.realworld.conduit.shared.infrastructure.api

import io.javalin.Javalin
import io.realworld.conduit.shared.domain.ResourceNotFoundException
import org.eclipse.jetty.http.HttpStatus

class ExceptionMapper {
    fun map(app: Javalin) {
        app.exception(Exception::class.java) { e, ctx ->
            val error = HttpStatus.Code.INTERNAL_SERVER_ERROR
            ctx.status(error.code)
            ctx.json(ApiError(
                status = error.code.toString(),
                message = error.message
            ))
        }

        app.exception(ResourceNotFoundException::class.java) { e, ctx ->
            val error = HttpStatus.Code.NOT_FOUND
            ctx.status(error.code)
            ctx.json(ApiError(
                status = error.code.toString(),
                message = error.message
            ))
        }

        app.error(404) { ctx ->
            val error = HttpStatus.Code.NOT_FOUND
            ctx.status(error.code)
            ctx.json(ApiError(
                status = error.code.toString(),
                message = error.message
            ))
        }
    }
}

private data class ApiError(
    val status: String,
    val message: String
)