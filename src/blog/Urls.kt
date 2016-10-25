package blog

import auth.login
import auth.logout
import auth.signup
import spark.Request
import spark.Response
import spark.Spark
import spark.Spark.*
import spark.template.thymeleaf.ThymeleafTemplateEngine

fun serveStaticFile(request: Request, response: Response) {
    val contextPath: String = request.contextPath()
    println("contextPath = " + contextPath)

}

fun mountUrls(te: ThymeleafTemplateEngine) {
    staticFileLocation("/static")

    post("/login", ::login)
    post("/logout", ::logout)
    post("/signup", ::signup)

    get("/", ::index, te)
    get("/posts", ::posts, te)
    post("/posts", ::newPost)
}