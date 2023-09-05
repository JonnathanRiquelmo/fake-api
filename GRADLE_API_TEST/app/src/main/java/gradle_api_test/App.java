package gradle_api_test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonArray;
import gradle_api_test.ApiClient.MetodoHttp;

public class App {
    private static final String API_URL = "https://my-json-server.typicode.com/JonnathanRiquelmo/fake-api/alunos/";

    public JsonArray getAlunosData() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder responseBody = new StringBuilder();

            while (scanner.hasNextLine()) {
                responseBody.append(scanner.nextLine());
            }

            scanner.close();
            connection.disconnect();

            // Parse o JSON para um JsonArray
            return Json.createReader(new java.io.StringReader(responseBody.toString())).readArray();
        } else {
            throw new IOException("Erro ao obter dados da API. Código de resposta: " + responseCode);
        }
    }

    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();

        try {
            // Exemplo de solicitação GET
            String responseGet = apiClient.executarRequest(MetodoHttp.GET, "", null);
            System.out.println("Resposta GET:");
            System.out.println(responseGet);

            // Exemplo de solicitação POST (criar aluno)
            String requestBodyPost = "{\"matricula\": \"55555\", \"id\": 11, \"nome\": \"Luisa\", \"idade\": 24}";
            String responsePost = apiClient.executarRequest(MetodoHttp.POST, "", requestBodyPost);
            System.out.println("\nResposta POST:");
            System.out.println(responsePost);

            // Exemplo de solicitação PUT (atualizar aluno com ID 1)
            String requestBodyPut = "{\"matricula\": \"12345\", \"id\": 1, \"nome\": \"Jo\u00E3o\", \"idade\": 25}";
            String responsePut = apiClient.executarRequest(MetodoHttp.PUT, "1", requestBodyPut);
            System.out.println("\nResposta PUT:");
            System.out.println(responsePut);

            // Exemplo de solicitação DELETE (excluir aluno com ID 1)
            String responseDelete = apiClient.executarRequest(MetodoHttp.DELETE, "1", null);
            System.out.println("\nResposta DELETE:");
            System.out.println(responseDelete);

            // Exemplo de solicitação PATCH (atualização parcial)
            String requestBodyPatch = "{\"nome\": \"Maria Alice\"}";
            String responsePatch = apiClient.executarRequest(MetodoHttp.PATCH, "2", requestBodyPatch);
            System.out.println("\nResposta PATCH:");
            System.out.println(responsePatch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
