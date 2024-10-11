package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorDeMoeda {
    public static void main(String[] args) {
        Scanner leitura = new Scanner(System.in);

        int opcao = 0;
        String endereco = "https://v6.exchangerate-api.com/v6/c0132588ce28a008750b4da2/latest/USD";

        while (opcao != 7) {
            System.out.println("*******************************************************");
            System.out.println("CONVERSOR DE MOEDAS");
            System.out.println("1) Dólar > Peso Argentino");
            System.out.println("2) Peso Argentino > Dólar");
            System.out.println("3) Dólar > Real Brasileiro");
            System.out.println("4) Real Brasileiro > Dólar");
            System.out.println("5) Dólar > Peso Colombiano");
            System.out.println("6) Peso Colombiano > Dólar");
            System.out.println("7) Sair");
            System.out.println("Escolha uma Opção Válida");
            System.out.println("*******************************************************");
            opcao = leitura.nextInt();

            if (opcao >= 8 || opcao <= 0) {
                System.out.println("\n\nOpção inválida, tente novamente.\n\n");
            } else if (opcao != 7) {
                System.out.println("Informe o valor que deseja converter:");
                double valor = leitura.nextDouble();


                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();

                try {
                    HttpResponse<String> response = client
                            .send(request, HttpResponse.BodyHandlers.ofString());

                    String json = response.body();


                    JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                    JsonObject rates = obj.getAsJsonObject("conversion_rates");

                    double taxa = 0;
                    String moedaOrigem = "";
                    String moedaDestino = "";

                    switch (opcao) {
                        case 1: // Dólar > Peso Argentino
                            taxa = rates.get("ARS").getAsDouble();
                            moedaOrigem = "USD";
                            moedaDestino = "ARS";
                            break;
                        case 2: // Peso Argentino > Dólar
                            taxa = 1 / rates.get("ARS").getAsDouble();
                            moedaOrigem = "ARS";
                            moedaDestino = "USD";
                            break;
                        case 3: // Dólar > Real Brasileiro
                            taxa = rates.get("BRL").getAsDouble();
                            moedaOrigem = "USD";
                            moedaDestino = "BRL";
                            break;
                        case 4: // Real Brasileiro > Dólar
                            taxa = 1 / rates.get("BRL").getAsDouble();
                            moedaOrigem = "BRL";
                            moedaDestino = "USD";
                            break;
                        case 5: // Dólar > Peso Colombiano
                            taxa = rates.get("COP").getAsDouble();
                            moedaOrigem = "USD";
                            moedaDestino = "COP";
                            break;
                        case 6: // Peso Colombiano > Dólar
                            taxa = 1 / rates.get("COP").getAsDouble();
                            moedaOrigem = "COP";
                            moedaDestino = "USD";
                            break;
                    }

                    // Calcula o valor convertido
                    double valorConvertido = valor * taxa;
                    System.out.printf("Valor convertido: %.2f %s = %.2f %s%n", valor, moedaOrigem, valorConvertido, moedaDestino);

                } catch (IOException | InterruptedException e) {
                    System.out.println("Houve Algum Erro Durante o Processo");
                }
            }
        }
        System.out.println("Programa encerrado.");
    }
}
