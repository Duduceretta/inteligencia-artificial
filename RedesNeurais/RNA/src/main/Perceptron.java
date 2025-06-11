/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Eduardo
 */
public class Perceptron {
    public List<Ponto> amostras = new ArrayList<>();
    public List<Integer> saidas = new ArrayList<>();
    public double taxa_aprendizado;
    public int geracoes;
    public int limiar;
    public int numeroAmostras;
    public int numeroAtributos;

    public double[] pesos;

    public Perceptron(List<Ponto> amostras, List<Integer> saidas, double taxa_aprendizado, int geracoes, int limiar) {
        this.amostras = amostras;
        this.saidas = saidas;
        this.taxa_aprendizado = taxa_aprendizado;
        this.geracoes = geracoes;
        this.limiar = limiar;
        this.numeroAmostras = amostras.size();
        this.pesos = new double[3]; //tamanho deste vetor peso vai ser igual a quantidade de atributos + um valor par limiar
    }

    private int funcao_ativacao_signal(double soma)
    {
        if (soma >= 0) return 1;  
        return -1;
    }
		

    public void treinar() {
        // Inserir o valor do limiar na posição limiar de cada ponto de cada amostra da lista "amostras"
        // Ex.: [[0.72, 0.82], ...] vira [[1, 0.72, 0.82], ...]
        for (int i = 0; i < this.amostras.size(); i++)
        {
            amostras.get(i).limiar = this.limiar;
        }

        // Gerar valores randômicos entre 0 e 1 (pesos) conforme o número de atributos
        // a lista de pesos tem tamanho da quantidade de atributos de uma amostra
        //pesos = [1.0,0.4,0.6], por exemplo

        Random gerador = new Random();
        pesos[0] = limiar; 
        pesos[1] = gerador.nextDouble(); //para o peso da entrada x
        pesos[2] = gerador.nextDouble(); //para o peso da entrada y

        int conta = 0;
        boolean aprendeu;
        double soma;
        int saida_gerada;
        
        while (true) {
            aprendeu = true;

            //Para cada amostra ou registro ou ponto ...
            soma = 0;
            for (int i = 0; i < amostras.size(); i++) {
                //Inicializar potencial de ativação
                soma = soma + (amostras.get(i).limiar * pesos[0]) + (amostras.get(i).x * pesos[1]) + (amostras.get(i).y * pesos[2]);


                //Obter a saída da rede considerando a função sinal
                saida_gerada = funcao_ativacao_signal(soma);

                //Verificar se a saída da rede é diferente da saída desejada
                //se sim, calibrar ou treinar ou ajustar ou fazer aprender
                if (saida_gerada != this.saidas.get(i))
                {
                    aprendeu = false;
                    double erro = this.saidas.get(i) - saida_gerada;
                    //Fazer o ajuste dos pesos para cada elemento da amostra ou SEJA UMA CALIBRAGEM DOS PESOS
                    this.pesos[0] = this.pesos[0] + this.taxa_aprendizado * erro * this.amostras.get(i).limiar;
                    this.pesos[1] = this.pesos[1] + this.taxa_aprendizado * erro * this.amostras.get(i).x;
                    this.pesos[2] = this.pesos[2] + this.taxa_aprendizado * erro * this.amostras.get(i).y;
                }
            }
            
            //Atualizar contador de gerações
            conta++;

            if (aprendeu || conta > this.geracoes)
            {
                System.out.println("Geracoes de treinamento: " + conta);
                break;
            }
        }
    }

    
    //testes para "novas" amostras
    public void testar(Ponto amostra) {
        //Inserir o valor do limiar na posição "0" para cada amostra da lista "amostras"
        amostra.limiar = this.limiar;

        //Inicializar potencial de ativação
        double soma = 0;
        //# Para cada atributo...
        soma = soma + (amostra.limiar * pesos[0]) + (amostra.x * pesos[1]) + (amostra.y * pesos[2]);

        // Obter a saída da rede considerando g a função funcao_ativacao_signal
        double saida_gerada = this.funcao_ativacao_signal(soma);

        if (saida_gerada == 1) {
            System.out.println("Classe: " + saida_gerada + " ou Time Azul");
        } else {
            System.out.println("Classe: " + saida_gerada + " ou Time Vermelho");
        }
    }
}
