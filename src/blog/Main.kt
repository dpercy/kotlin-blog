package blog

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import spark.*
import spark.debug.DebugScreen.enableDebugScreen
import spark.template.mustache.MustacheTemplateEngine

/*

DONE
- get stack trace for 500
- render a template
- connect to mongodb
- render data from mongodb
- create a form
- insert data into mongodb

TODO
- create a model layer
  - http://litote.org/kmongo/
  - use Kotlin data class
  - automatically serialize to and from Json?
    - queries are still done in raw json

- add a couple more features
  - commenting
  - logging in

- try using thymeleaf templates
   - but how do you share code between views while keeping it "natural" (previewable)?

- reverse a URL? does spark have this concept?

- fix template resolution??
  - avoid specifying src/templates/ before each one

 */

var mongoClient: MongoClient? = null

fun index(request: Request, response: Response): ModelAndView {
    val context = hashMapOf(
        "title" to "blog",
        "body" to "blah blah blah"
    )
    return ModelAndView(context, "resources/templates/index.html")
}

fun greet(request: Request, response: Response): ModelAndView {
    val name = request.params("name")
    val context = hashMapOf(
        "name" to name
    )
    return ModelAndView(context, "resources/templates/greet.html")
}

fun posts(request: Request, response: Response): ModelAndView {
    val context: Map<String, Any> = hashMapOf(
        "posts" to BlogDB.posts.find().toList()
    )
    return ModelAndView(context, "resources/templates/posts.html")
}

fun newPost(request: Request, response: Response): Unit {
    BlogDB.posts.insertOne(Post(
        title=request.queryParams("title"),
        author=request.queryParams("author"),
        body=request.queryParams("body")))

    response.redirect("/posts")
}


fun main(args: Array<String>) {
    val mte = MustacheTemplateEngine()
    BlogDB.connect()

    get("/", ::index, mte)
    get("/greet/:name", ::greet, mte)
    get("/posts", ::posts, mte)
    post("/posts", ::newPost)

    enableDebugScreen()
}
