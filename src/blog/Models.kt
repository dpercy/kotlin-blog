package blog

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import org.litote.kmongo.*

class BlogDB {
    companion object {
        lateinit var mongoClient: MongoClient

        fun connect() {
            mongoClient = KMongo.createClient(MongoClientURI("mongodb://localhost:27017"))
        }

        val posts: MongoCollection<Post>
        get() = mongoClient.getDatabase("test").getCollection<Post>("posts")
    }
}

data class Post(
        val title: String,
        val author: String,
        val body: String
)

