package blog

import spark.TemplateEngine

/**
 * Created by dpercy on 9/12/16.
 */

fun mount(te: TemplateEngine) {
    get("/", ::index, te)
    get("/greet/:name", ::greet, te)
    get("/posts", ::posts, te)
    post("/posts", ::newPost)
}