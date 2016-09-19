package blog

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templateresolver.DefaultTemplateResolver
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

- try using thymeleaf templates
   - but how do you share code between views while keeping it "natural" (previewable)?

- reverse a URL? does spark have this concept?

- fix template resolution??
  - avoid specifying src/templates/ before each one

 */



class ThymeleafTemplateEngine: spark.TemplateEngine() {
    val te = TemplateEngine()

    override fun render(mav: ModelAndView): String {
//        te.setTemplateResolver(DefaultTemplateResolver())
        val context = Context()
        context.setVariables(mav.model as Map<String, Any>)

        return te.process(mav.viewName, context)
    }
}


fun main(args: Array<String>) {


    mountUrls(MustacheTemplateEngine(), ThymeleafTemplateEngine())
    enableDebugScreen()
}