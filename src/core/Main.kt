import spark.Spark.get
import spark.Spark

fun main(args: Array<String>) {
    println("Spark defaults to http://localhost:4567/") // TODO fix Spark logging
    get("/", { req, res -> "Hello!" })
}
