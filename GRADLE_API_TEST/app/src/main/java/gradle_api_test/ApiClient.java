package gradle_api_test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {
    private static final String BASE_URL = "https://my-json-server.typicode.com/JonnathanRiquelmo/fake-api/alunos/";

    public enum MetodoHttp {
        GET, POST, PUT, DELETE, PATCH
    }

    public String executarRequest(MetodoHttp metodo, String url, String corpo) throws IOException {
        HttpURLConnection connection = null;
        try {
            connection = criarRequest(metodo, url, corpo);
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED ||
                    responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                return response.toString();
            } else {
                throw new IOException(
                        "Falha ao executar a solicitação HTTP {" + metodo.toString().toUpperCase()
                                + "}. Código de resposta: " + responseCode);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private HttpURLConnection criarRequest(MetodoHttp metodo, String url, String corpo) throws IOException {
        URL apiUrl = new URL(BASE_URL + url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod(metodo.toString());

        if (corpo != null && !corpo.isEmpty()) {
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream outputStream = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                writer.write(corpo);
            }
        }

        return connection;
    }
}
