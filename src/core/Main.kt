import spark.*
import spark.Spark.*
import spark.debug.DebugScreen.enableDebugScreen
import spark.template.mustache.MustacheTemplateEngine

/*

DONE
- get stack trace for 500
- render a template

TODO
- connect to mongodb
- render data from mongodb
- create a form
- insert data into mongodb
- reverse a URL

- fix template resolution??
  - avoid specifying src/templates/ before each one

 */


fun index(request: Request?, response: Response?): ModelAndView {
    val context = hashMapOf(
        "title" to "blog",
        "body" to "blah blah blah"
    )
    return ModelAndView(context, "resources/templates/index.html")
}


fun main(args: Array<String>) {
    val mte = MustacheTemplateEngine()

    get("/", ::index, mte)

    enableDebugScreen()
}
