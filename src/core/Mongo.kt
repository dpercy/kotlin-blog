package core

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import org.bson.conversions.Bson
import org.litote.kmongo.KMongo
import org.litote.kmongo.find
import org.litote.kmongo.util.KMongoUtil

object MongoDB {
    val conn: MongoClient by lazy {
        KMongo.createClient(MongoClientURI("mongodb://localhost:27017")) }
}



/**
 * Finds the first document that match the filter in the collection.
 *
 * @param filter the query filter
 * @return the first item returned or null
 */
fun <T> MongoCollection<T>.findOne(filter: Bson): T?
        = find(filter).first()
