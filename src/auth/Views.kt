package auth

import core.*
import org.litote.kmongo.findOne
import spark.*
import spark.Spark.*
import com.mongodb.client.model.Filters.*;
import org.bson.conversions.Bson


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
    response.redirect("/")
}

fun logout(request: Request, response: Response) {
    request.session().removeAttribute("username")
    response.redirect("/")
}

fun signup(request: Request, response: Response) {
    val username = request.queryParams("username")
    val password = request.queryParams("password")

    // TODO use a captcha to prevent user enumeration
    // TODO actually, there is a bug here because usernames don't have to be unique!
    AuthDB.users.insertOne(User.fromPlaintext(username, password))

    // also add the user to the session
    request.session().attribute("username", username)
    response.redirect("/")
}