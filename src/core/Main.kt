import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import org.bson.Document
import spark.*
import spark.Spark.*
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
- reverse a URL

- fix template resolution??
  - avoid specifying src/templates/ before each one

 */

var mongoClient: MongoClient? = null

fun index(request: Request?, response: Response?): ModelAndView {
    val context = hashMapOf(
        "title" to "blog",
        "body" to "blah blah blah"
    )
    return ModelAndView(context, "resources/templates/index.html")
}

fun greet(request: Request?, response: Response?): ModelAndView {
    val name = request!!.params("name")
    val context = hashMapOf(
        "name" to name
    )
    return ModelAndView(context, "resources/templates/greet.html")
}

fun posts(request: Request?, response: Response?): ModelAndView {
    val postsCollection = mongoClient!!.getDatabase("test").getCollection("posts")

    val context: Map<String, Any> = hashMapOf(
            "posts" to postsCollection.find().toList()
    )
    return ModelAndView(context, "resources/templates/posts.html")
}

fun newPost(request: Request?, response: Response?): Unit {
    val postsCollection = mongoClient!!.getDatabase("test").getCollection("posts")

    val fields: Map<String, String> = hashMapOf(
        "title" to request!!.queryParams("title"),
        "author" to request!!.queryParams("author"),
        "body" to request!!.queryParams("body")
    )
    val newPost = Document(fields)
    postsCollection.insertOne(newPost)

    response!!.redirect("/posts")
}


fun main(args: Array<String>) {
    val mte = MustacheTemplateEngine()
    mongoClient = MongoClient(MongoClientURI("mongodb://localhost:27017"))

    get("/", ::index, mte)
    get("/greet/:name", ::greet, mte)
    get("/posts", ::posts, mte)
    post("/posts", ::newPost)

    enableDebugScreen()
}
