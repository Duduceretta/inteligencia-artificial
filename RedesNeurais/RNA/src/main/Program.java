/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Eduardo
 */
public class Program {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Exemplo de RNA Perceptron para classificação de equipes");
	List<Ponto> amostras = new ArrayList<>();
        List<Integer> saidas = new ArrayList<>();
        String caminhoArquivo = "amostras_saidas_problemaTimes.csv";
        
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 3) {
                    double x = Double.parseDouble(partes[0]);
                    double y = Double.parseDouble(partes[1]);
                    int saida = Integer.parseInt(partes[2]);

                    amostras.add(new Ponto(x, y));
                    saidas.add(saida);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        double taxa_aprendizado = 0.1;
        int geracoes = 1000;
        int limiar = 1;
        
        Perceptron p = new Perceptron(amostras, saidas, taxa_aprendizado, geracoes, limiar);
        p.treinar();
        String op;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("\n\nInforme valor para x (-1 a 1): ");
            double x = Double.parseDouble(scanner.nextLine());
            System.out.println("Informe valor para y (-1 a 1): ");
            double y = Double.parseDouble(scanner.nextLine()); 

            p.testar(new Ponto(x,y));
            System.out.println("1 - Sair: ");
            op = scanner.nextLine();
        } while (!"1".equals(op));  		
    }
}
