package blog

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import spark.*
import spark.debug.DebugScreen.enableDebugScreen
import spark.servlet.SparkApplication
import spark.template.mustache.MustacheTemplateEngine


/*

DONE
- get stack trace for 500
- render a template
- connect to mongodb
- render data from mongodb
- create a form
- insert data into mongodb

- create a model layer
  - http://litote.org/kmongo/
  - use Kotlin data class
  - automatically serialize to and from Json?
    - queries are still done in raw json

TODO
- add a couple more features
  - commenting
  - logging in

- try using thymeleaf templates
   - but how do you share code between views while keeping it "natural" (previewable)?

- reverse a URL? does spark have this concept?

- fix template resolution??
  - avoid specifying src/templates/ before each one

 */
fun main(args: Array<String>) {
    mountUrls(MustacheTemplateEngine())
    enableDebugScreen()
}

public class JettyMain : SparkApplication {
    override fun init() {
        main(arrayOf())
    }
}