package blog

import spark.*

/**
 * Shim to make a request handler accept non-nullable Request and Response arguments.
 */
fun <T> sparkRequest(handler: (Request, Response) -> T): (Request?, Response?) -> T {
    return { req: Request?, resp: Response? -> handler(req!!, resp!!) }
}

fun get(path: String, handler: (Request, Response) -> ModelAndView, te: TemplateEngine) {
    return Spark.get(path, sparkRequest(handler), te)
}

fun <T> post(path: String, handler: (Request, Response) -> T) {
    return Spark.post(path, sparkRequest(handler))
}