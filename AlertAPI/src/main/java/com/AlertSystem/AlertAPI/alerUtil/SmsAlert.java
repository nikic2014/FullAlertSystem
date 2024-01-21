package com.AlertSystem.AlertAPI.alerUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class SmsAlert {

    @Value("${sms-key}")
    private String sms_key;

    public String sendSms(String phoneNumber, String message) throws Exception {

        message = message.replace(" ", "+");
        message = message.replace("\n", "+");

        // Создаем объект URL
        String urlString =
                "https://sms.ru/sms/send?api_id=" + this.sms_key + "&to=" +
                        phoneNumber+"&msg=" +
                        message + "&json=1";

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Опционально - установка метода запроса (по умолчанию GET)
        connection.setRequestMethod("GET");

        // Получение ответа
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println(response.toString());
            return response.toString();
        } else {
            throw new Exception("HTTP GET request failed with response code: " + responseCode);
        }
    }
}
