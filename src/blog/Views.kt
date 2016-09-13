package blog

import spark.ModelAndView
import spark.Request
import spark.Response


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

