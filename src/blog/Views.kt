package blog

import spark.ModelAndView
import spark.Request
import spark.Response
import java.util.*


fun index(request: Request, response: Response): ModelAndView {
    val context = hashMapOf(
            "title" to "Blog homepage",
            "username" to request.session().attribute<String>("username"),
            "postsGrid" to BlogDB.posts.find().toList().batch(3)
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
            title = request.queryParams("title"),
            author = request.queryParams("author"),
            body = request.queryParams("body")))

    response.redirect("/posts")
}


fun <T> List<T>.batch(batchSize: Int): List<List<T>> {
    val result = ArrayList<ArrayList<T>>()
    var nLeftInRow = 0
    for (item in this) {
        if (nLeftInRow == 0) {
            result.add(ArrayList<T>())
            nLeftInRow = batchSize
        }
        result.last().add(item)
    }
    return result;
}

