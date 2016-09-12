import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import spark.*
import spark.Spark.*
import spark.debug.DebugScreen.enableDebugScreen
import spark.template.mustache.MustacheTemplateEngine
import org.litote.kmongo.*

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

data class Post(
    val title: String,
    val author: String,
    val body: String)

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
    val postsCollection = mongoClient!!.getDatabase("test").getCollection<Post>("posts")
    val posts: List<Post> = postsCollection.find().toList()

    val context: Map<String, Any> = hashMapOf(
        "posts" to posts
    )
    return ModelAndView(context, "resources/templates/posts.html")
}

fun newPost(request: Request?, response: Response?): Unit {
    val postsCollection = mongoClient!!.getDatabase("test").getCollection<Post>("posts")

    val newPost = Post(
        title=request!!.queryParams("title"),
        author=request!!.queryParams("author"),
        body=request!!.queryParams("body"))
    postsCollection.insertOne(newPost)

    response!!.redirect("/posts")
}


fun main(args: Array<String>) {
    val mte = MustacheTemplateEngine()
    // Note it's importants to use KMongo.createClient instead of new MongoClient here.
    // KMongo tells the driver how to convert classes to BSON and back (it passes in some "codecs").
    mongoClient = KMongo.createClient(MongoClientURI("mongodb://localhost:27017"))

    get("/", ::index, mte)
    get("/greet/:name", ::greet, mte)
    get("/posts", ::posts, mte)
    post("/posts", ::newPost)

    enableDebugScreen()
}
