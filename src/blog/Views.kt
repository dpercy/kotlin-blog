package blog

import spark.ModelAndView
import spark.Request
import spark.Response


fun index(request: Request, response: Response): ModelAndView {
    val context = hashMapOf(
        "title" to "Blog homepage",
        "username" to request.session().attribute<String>("username")
    )
    return ModelAndView(context, "index")
}


fun posts(request: Request, response: Response): ModelAndView {
    val context = hashMapOf(
        "posts" to BlogDB.posts.find().toList()
    )
    return ModelAndView(context, "posts")
}


fun newPost(request: Request, response: Response): Unit {
    BlogDB.posts.insertOne(Post(
            title=request.queryParams("title"),
            author=request.queryParams("author"),
            body=request.queryParams("body")))

    response.redirect("/posts")
}

