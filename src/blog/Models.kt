package blog

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import core.MongoDB
import org.litote.kmongo.*

object BlogDB {
    val db = MongoDB.conn.getDatabase("blog")

    val posts: MongoCollection<Post>
        get() = db.getCollection<Post>("posts")
}

data class Post(
        val title: String,
        val author: String,
        val body: String
)

