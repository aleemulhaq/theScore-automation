import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

// make api requests to theScore's api, to get team profile data, and we can verify that against the UI
// to see if UI displays data correctly
fun makeApiRequest(apiUrl: String): String {
    //    Make request to theScore's api
    val client = HttpClient.newBuilder().build();
    val request = HttpRequest.newBuilder()
        .uri(URI.create(apiUrl))
        .build()

    // response from theScore api
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    // if response not OK, return ""
    if (response.statusCode() != 200) {
        return ""
    }
    return response.body().toString()
}