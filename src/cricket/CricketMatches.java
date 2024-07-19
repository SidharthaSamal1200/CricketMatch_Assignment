package cricket;


import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;

public class CricketMatches {

    public static void main(String[] args) {
        String apiUrl = "https://api.cuvora.com/car/partner/cricket-data";
        String apiKey = "test-creds2320";
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("apiKey", apiKey)
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(CricketMatches::parse)
                .join();
    }

    public static String parse(String responseBody) {
        JSONArray matches = new JSONArray(responseBody);
        int highestScore = 0;
        String highestScoringTeam = "";
        int matchesWith300PlusScore = 0;

        for (int i = 0; i < matches.length(); i++) {
            JSONObject match = matches.getJSONObject(i);
            int team1Score = match.getInt("t1s");
            int team2Score = match.getInt("t2s");

            if (team1Score > highestScore) {
                highestScore = team1Score;
                highestScoringTeam = match.getString("t1");
            }
            if (team2Score > highestScore) {
                highestScore = team2Score;
                highestScoringTeam = match.getString("t2");
            }

            if ((team1Score + team2Score) > 300) {
                matchesWith300PlusScore++;
            }
        }

        System.out.println("Highest Score: " + highestScore + " and Team Name is: " + highestScoringTeam);
        System.out.println("Number Of Matches with total 300 Plus Score: " + matchesWith300PlusScore);

        return null;
    }
}



