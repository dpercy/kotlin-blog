import spark.Spark.get

fun main(args: Array<String>) {
    // TODO get IntelliJ to work!!
    println("Spark defaults to http://localhost:4567/") // TODO fix Spark logging
    get("/", { req, res -> "Hello!" })
}
