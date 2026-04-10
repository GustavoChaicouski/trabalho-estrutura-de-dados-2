package Projeto2;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] tamanhos = {10000, 100000, 1000000}; 
        int execucoes = 30;
        Random gerador = new Random();

        for (int tamanho : tamanhos) {
            System.out.println("==================================================");
            System.out.println("TESTANDO PARA " + tamanho + " ELEMENTOS");
            System.out.println("==================================================");

            int[] dados = new int[tamanho];
            ArvoreBinariaBusca bst = new ArvoreBinariaBusca();

            for (int i = 0; i < tamanho; i++) {
                int valor = gerador.nextInt(tamanho * 2);
                dados[i] = valor;
                bst.inserir(valor);
            }

            int[] dadosOrdenados = dados.clone();
            Arrays.sort(dadosOrdenados);

            long[] temposSequencial = new long[execucoes];
            long[] temposBinaria = new long[execucoes];
            long[] temposArvore = new long[execucoes];

            for (int i = 0; i < execucoes; i++) {
                int alvo = dados[gerador.nextInt(tamanho)];

                long inicioSeq = System.nanoTime();
                BuscaSequencial.buscar(dados, alvo);
                long fimSeq = System.nanoTime();
                temposSequencial[i] = (fimSeq - inicioSeq);

                long inicioBin = System.nanoTime();
                BuscaBinaria.buscar(dadosOrdenados, alvo);
                long fimBin = System.nanoTime();
                temposBinaria[i] = (fimBin - inicioBin);

                long inicioArvore = System.nanoTime();
                bst.buscar(alvo);
                long fimArvore = System.nanoTime();
                temposArvore[i] = (fimArvore - inicioArvore);
            }

            imprimirResultados("Busca Sequencial", temposSequencial);
            imprimirResultados("Busca Binária", temposBinaria);
            imprimirResultados("Busca em Árvore (BST)", temposArvore);
            System.out.println();
        }
    }

    public static void imprimirResultados(String nomeBusca, long[] tempos) {
        double media = calcularMedia(tempos);
        double desvioPadrao = calcularDesvioPadrao(tempos, media);

        System.out.printf("%-25s -> Média: %,10.2f ns | Desvio Padrão: %,10.2f ns%n", 
                          nomeBusca, media, desvioPadrao);
    }

    public static double calcularMedia(long[] tempos) {
        double soma = 0;
        for (long t : tempos) {
            soma += t;
        }
        return soma / tempos.length;
    }

    public static double calcularDesvioPadrao(long[] tempos, double media) {
        double somaDiferencasQuadrado = 0;
        for (long t : tempos) {
            somaDiferencasQuadrado += Math.pow(t - media, 2);
        }
        double variancia = somaDiferencasQuadrado / tempos.length;
        return Math.sqrt(variancia);
    }
}
