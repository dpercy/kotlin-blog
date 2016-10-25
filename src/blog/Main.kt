package blog

import spark.debug.DebugScreen.enableDebugScreen
import spark.template.thymeleaf.ThymeleafTemplateEngine


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

- try porting someone else's mockup to thymeleaf templates
  - see ~/Downloads/animus-blog-template
  - need to deal with static files too

- try using thymeleaf templates
   - but how do you share code between views while keeping it "natural" (previewable)?

- reverse a URL? does spark have this concept?

- fix template resolution??
  - avoid specifying src/templates/ before each one

 */




fun main(args: Array<String>) {


    mountUrls(ThymeleafTemplateEngine())
    enableDebugScreen()
}