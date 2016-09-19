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

fun mountUrls(te: TemplateEngine, te2: ThymeleafTemplateEngine) {
    post("/login", ::login)
    post("/logout", ::logout)
    post("/signup", ::signup)

    get("/", ::index, te2)
    get("/posts", ::posts, te)
    post("/posts", ::newPost)
}