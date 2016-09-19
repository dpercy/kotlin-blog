package blog

import spark.ModelAndView
import spark.Request
import spark.Response


fun index(request: Request, response: Response): ModelAndView {
    val context = hashMapOf(
        "title" to "Blog homepage",
        "username" to (request.session().attribute<String>("username") ?: "(nobody)")
    )
    return ModelAndView(context, "resources/templates/index.html")
}


fun posts(request: Request, response: Response): ModelAndView {
    val context = hashMapOf(
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

