package auth

import com.mongodb.client.model.Filters.eq
import core.findOne
import spark.Request
import spark.Response
import spark.Spark.halt


fun login(request: Request, response: Response) {
    val username = request.queryParams("username")
    val password = request.queryParams("password")

    // If user doesn't exist, return a 403 to prevent username enumeration.
    val user: User = AuthDB.users.findOne(eq("username", username))
            ?: return halt(403, "Incorrect username or password")
    if (!user.checkPassword(password))
        return halt(403, "Incorrect username or password")

    // The password is correct: log the user in.
    request.session(true)
    request.session().attribute("username", username)
}

fun logout(request: Request, response: Response) {
    request.session().removeAttribute("username")
}

fun signup(request: Request, response: Response) {
    val username = request.queryParams("username")
    val password = request.queryParams("password")

    // TODO use a captcha to prevent user enumeration
    AuthDB.users.insertOne(User.fromPlaintext(username, password))

    // also add the user to the session
}