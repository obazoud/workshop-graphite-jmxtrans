package gatling.cocktail
import com.excilys.ebi.gatling.core.Predef._
import com.excilys.ebi.gatling.http.Predef._
import akka.util.duration._

class RecordedSimulation extends Simulation {

  val cocktails = csv("cocktail.csv").random
  val quantities = csv("quantity.csv").random

  val baseURL = "/cocktail";

	val httpConf = {
    val baseUrl: String = "http://localhost:8080"
    httpConfig
      .baseURL(baseUrl)
      .proxy("localhost", 8080).httpsPort(8443)
      .acceptHeader("*/*")
      .acceptEncodingHeader("gzip,deflate,sdch")
      .acceptLanguageHeader("fr-FR,fr;q=0.8,en-US;q=0.6,en;q=0.4")
      .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
  }


	val headers_1 = Map(
			"Accept" -> """text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8""",
			"Cache-Control" -> """max-age=0""",
			"Proxy-Connection" -> """keep-alive"""
	)

	val headers_2 = Map(
			"Cache-Control" -> """max-age=0""",
			"If-Modified-Since" -> """Thu, 25 Jul 2013 20:17:40 GMT""",
			"If-None-Match" -> """"51f187e4-222f6"""",
			"Proxy-Connection" -> """keep-alive""",
			"X-Source-Map-Request-From" -> """inspector"""
	)

	val headers_3 = Map(
			"Accept" -> """text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8""",
			"Proxy-Connection" -> """keep-alive"""
	)

	val headers_4 = Map(
			"Cache-Control" -> """max-age=0""",
			"Proxy-Connection" -> """keep-alive""",
			"X-Source-Map-Request-From" -> """inspector"""
	)

	val headers_5 = Map(
			"Accept" -> """text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8""",
			"Cache-Control" -> """max-age=0""",
			"Content-Type" -> """application/x-www-form-urlencoded""",
			"Origin" -> """http://localhost:8080""",
			"Proxy-Connection" -> """keep-alive"""
	)

	val headers_10 = Map(
			"Accept" -> """image/webp,*/*;q=0.8""",
			"Cache-Control" -> """max-age=0""",
			"Proxy-Connection" -> """keep-alive"""
	)

	val headers_12 = Map(
			"Accept" -> """image/webp,*/*;q=0.8""",
			"Cache-Control" -> """max-age=0""",
			"If-Modified-Since" -> """Wed, 27 Nov 2013 06:17:08 GMT""",
			"Proxy-Connection" -> """keep-alive"""
	)


	val scn = scenario("cocktail")
    .feed(cocktails)
    .feed(quantities)
		.exec(http("request_1")
					.get(baseURL + "/cocktail/")
					.headers(headers_1)
			)
		.pause(1)
		.exec(http("request_3")
					.get(baseURL + "/cocktail/${cocktail}")
					.headers(headers_3)
			)
		.pause(660 milliseconds)
		.exec(http("request_5")
					.post(baseURL + "/cart/add")
					.headers(headers_5)
						.param("""cocktail""", """${cocktail}""")
						.param("""quantity""", """${quantity}""")
			)
		.pause(500 milliseconds)
		.exec(http("request_7")
					.get(baseURL + "/cart/")
					.headers(headers_3)
			)
		.pause(236 milliseconds)
		.exec(http("request_9")
					.post(baseURL + "/cart/buy")
					.headers(headers_5)
						.param("""quantity-${cocktail}""", """${quantity}""")
			)

	setUp(scn.users(1000).ramp(200).protocolConfig(httpConf))
}