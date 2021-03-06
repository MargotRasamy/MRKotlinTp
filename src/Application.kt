package fr.eemi.margot


import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.html.*
import kotlinx.html.*
import kotlinx.css.*
import freemarker.cache.*
import io.ktor.freemarker.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.sessions.*
import java.net.http.HttpClient

//Fonction minimum
fun <T: Comparable<T>> minimum( myArray: Array<T>) : T {
    var minimum = myArray.first()
    var myArrayWithoutFirstElement = myArray.toList()
    for (element in  myArrayWithoutFirstElement.drop(0)) {
        minimum = if(element < minimum) element else minimum
    }
    return minimum
}

//Fonction maximum
fun <T: Comparable<T>> maximum( myArray: Array<T>) : T {
    var maximum = myArray.first()
    var myArrayWithoutFirstElement = myArray.toList()
    for (element in  myArrayWithoutFirstElement.drop(0)) {
        maximum = if(element > maximum) element else maximum
    }
    return maximum
}

//Fonction TwoSum
fun twoSum(nums: MutableList<Int>, target: Int) : Pair<Int?, Int?> {
    var dict = mutableMapOf<Int,Int>()
    // For every number n,
    for ((currentIndex, n) in nums.withIndex()) {
        // Find the complement to n that would sum up to the target.
        var complement = target - n
        // Check if the complement is in the dictionary.
        if (dict[complement]!= null){
            var complementIndex = dict[complement]
            return Pair(complementIndex, currentIndex)
        }
        // Store n and its index into the dictionary.
        dict[n] = currentIndex
    }
    //if there is no pair that makes the sum, return a pair of null
    return Pair(null,null)

}


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)



@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    install(Sessions) {
        cookie<MySession>("MY_SESSION") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    //Algorithms list
    data class algoNames(val twosum: String, val maximum: String, val minimum: String)
    val algoName = algoNames("twosum", "maximum", "minimum")

    routing {
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", mapOf("algoName" to algoName)))
        }

        get("/algo/twosum") {
            call.respond(FreeMarkerContent("twosum.ftl", mapOf("algoName" to "twosum")))
        }
        get("/algo/maximum") {
            call.respond(FreeMarkerContent("algo.ftl", mapOf("algoName" to "maximum")))
        }
        get("/algo/minimum") {
            call.respond(FreeMarkerContent("algo.ftl", mapOf("algoName" to "minimum")))
        }

//Post method for each algorithms
        post("/maximum"){
            val sendParams = call.receiveParameters()
            var myNumbersDatas = sendParams["numbersInput"] ?: ""
            var myNumbersArray = myNumbersDatas.split(",").toTypedArray()
            var answer = maximum(myNumbersArray)
            call.respond(FreeMarkerContent("answer.ftl", mapOf("answer" to answer)))
        }

        post("/minimum"){
            val sendParams = call.receiveParameters()
            var myNumbersDatas = sendParams["numbersInput"] ?: ""
            var myNumbersArray = myNumbersDatas.split(",").toTypedArray()
            var answer = minimum(myNumbersArray)
            call.respond(FreeMarkerContent("answer.ftl", mapOf("answer" to answer)))
        }

        post("/twosum"){
            val sendParams = call.receiveParameters()

            var myNumbersDatas = sendParams["numbersInput"] ?: ""
            var myNumbersArray = myNumbersDatas.split(",")
            var userNumbersList : MutableList<Int> = mutableListOf()
            for ((n, elem) in myNumbersArray.withIndex()) {
                userNumbersList.add(elem.trim().toInt())
            }
            var mySumInteger = sendParams["sumInput"] ?: ""
            var mySum = mySumInteger.trim().toInt()
            var answer = twoSum(userNumbersList, mySum)
            call.respond(FreeMarkerContent("answer.ftl", mapOf("answer" to answer)))
        }


        // Static feature. Try to access `/static/ktor_logo.svg`
        static("/static") {
            resources("static")
        }

        get("/session/increment") {
            val session = call.sessions.get<MySession>() ?: MySession()
            call.sessions.set(session.copy(count = session.count + 1))
            call.respondText("Counter is ${session.count}. Refresh to increment.")
        }
    }
}

data class IndexData(val items: List<Int>)

data class MySession(val count: Int = 0)

fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) {
    style(type = ContentType.Text.CSS.toString()) {
        +CSSBuilder().apply(builder).toString()
    }
}

fun CommonAttributeGroupFacade.style(builder: CSSBuilder.() -> Unit) {
    this.style = CSSBuilder().apply(builder).toString().trim()
}

suspend inline fun ApplicationCall.respondCss(builder: CSSBuilder.() -> Unit) {
    this.respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}
