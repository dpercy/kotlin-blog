package auth

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import core.MongoDB
import org.litote.kmongo.getCollection
import java.security.MessageDigest
import java.time.Instant
import java.util.*
import com.mongodb.client.model.Filters.*;
import core.findOne
import spark.Request


object AuthDB {
    val siteWideSalt = "1F4C2B48-64F7-4B5E-8F2D-9BA86458A029"
    val db: MongoDatabase
        get() = MongoDB.conn.getDatabase("auth")

    val users: MongoCollection<User>
        get() = db.getCollection<User>("users")
}

fun hashPassword(password: String, salt: String): ByteArray {
    val saltedPassword = password + ":" + AuthDB.siteWideSalt + ":" + salt;
    // TODO internet says use bcrypt because sha256 is too easy to reverse
    val md = MessageDigest.getInstance("SHA-256")
    return md.digest(saltedPassword.toByteArray(charset("utf-8")))
}



// TODO what about indexes?
// - username has to be the primary key, especially if it's used as password salt.
data class User(val username: String,
                val hashedSaltedPassword: ByteArray) {

    companion object {
        /*
        Create a new user with the given password.
        This method handles the user-specific salting and the hashing.
         */
        fun fromPlaintext(username: String, password: String): User {
            return User(username, hashPassword(password, username))
        }
    }

    fun checkPassword(password: String): Boolean {
        //return hashedSaltedPassword == hash // apparently not for byte arrays...
        return Arrays.equals(hashedSaltedPassword, hashPassword(password, username))
    }
}
