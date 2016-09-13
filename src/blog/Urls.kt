package blog

import auth.login
import auth.logout
import auth.signup
import core.get
import core.post
import spark.Request
import spark.Response
import spark.TemplateEngine

/**
 * Created by dpercy on 9/12/16.
 */

fun mountUrls(te: TemplateEngine) {
    post("/login", ::login)
    post("/logout", ::logout)
    post("/signup", ::signup)

    get("/", ::index, te)
    get("/posts", ::posts, te)
    post("/posts", ::newPost)
}