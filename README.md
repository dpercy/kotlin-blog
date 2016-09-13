# kotlin-blog
sandbox for learning kotlin

I tried to organize things similarly to how you would in Django.

The main file is `blog/Main.kt`.  In Kotlin you can define functions at the top level; you don't need to put them in a class.  So the entry point for a Kotlin program is just a function: `fun main(args: Array<String>)`, similar to C.  Also notice that `mountUrls` is not explicitly imported anywhere.  In Kotlin, files in the same directory (same Java package) all have the same scope, similar to Go.

`mountUrls` is defined in `blog/Urls.kt`.  This file associates URLs with request handlers.  Each request handler is a function.  In Java and in Kotlin, methods/functions are in a separate namespace from variables, so to use a function as an expression you use the `::myfunction` double colon syntax.

The individual view functions are defined in `blog/Views.kt`.  Usually you'll return a ModelAndView function, which combines a template and a dictionary of template variables.  In Spark (this web framework), returning a redirect is a little different: you modify the Response rather than returning a special value. 

Queries in the Java driver are very similar to the mongo shell.  To avoid calling `getDatabase("blog").getCollection<Post>("posts")` all the time, I defined a helper called BlogDB.  Having insertOne and find() accept and return Post objects instead of a plain bson Document is really cool: it's simpler than MongoEngine but takes care of marshalling for you.

`blog/Models.kt` defines the BlogDB helper and the Post class.  The `data class Post` definition is similar to namedtuple in Python: it automatically creates a useful constructor, equals, hashCode, and toString.   But in Kotlin it has the extra advantage of letting you specify types for the fields, and letting you add mutable fields (also I'm not using this last feature).

The `auth/Models.kt` and `auth/Views.kt` files define the Users collection and the signup, login, logout views.  A couple neat things in `auth/Views.kt`
- The `a ?: b` operator is short for `a == null ? a : b`.  In Python you would use `or` for this pattern; in Kotlin it's more type safe but still concise.
- In Spark, `halt(Int, String)` throws an exception that makes the request return immediately.  However, Kotlin doesn't know that this function always throws / never returns normally.  So as a hack I've written `return halt(403, "msg")`.  It might be possible to wrap this function so Kotlin understands its control-flow behavior.  (Similar to `core/Routing.kt`, which wraps some Spark functions to tell Kotlin that they deal with non-null values.)
