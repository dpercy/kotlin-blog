package blog

import auth.login
import auth.logout
import auth.signup
import spark.Request
import spark.Response
import spark.TemplateEngine
import spark.Spark.get
import spark.Spark.post

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