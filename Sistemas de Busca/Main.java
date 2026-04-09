package projeto2;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Diferentes volumes de dados conforme exigido no edital
        int[] tamanhos = {10000, 100000, 1000000}; 
        int execucoes = 30; // 30 execuções para ter uma base estatística confiável
        Random gerador = new Random();

        for (int tamanho : tamanhos) {
            System.out.println("==================================================");
            System.out.println("TESTANDO PARA " + tamanho + " ELEMENTOS");
            System.out.println("==================================================");

            // 1. Preparação dos Dados
            int[] dados = new int[tamanho];
            ArvoreBinariaBusca bst = new ArvoreBinariaBusca();

            for (int i = 0; i < tamanho; i++) {
                int valor = gerador.nextInt(tamanho * 2); // Gera números aleatórios
                dados[i] = valor;
                bst.inserir(valor); // Preenchendo a Árvore
            }

            // Cópia ordenada exclusivamente para a Busca Binária
            int[] dadosOrdenados = dados.clone();
            Arrays.sort(dadosOrdenados);

            // Arrays para armazenar os tempos de cada execução
            long[] temposSequencial = new long[execucoes];
            long[] temposBinaria = new long[execucoes];
            long[] temposArvore = new long[execucoes];

            // 2. Execução das Buscas (Motor de Testes)
            for (int i = 0; i < execucoes; i++) {
                // Sorteia um alvo que COM CERTEZA existe no array para testar o pior/médio caso
                int alvo = dados[gerador.nextInt(tamanho)];

                // --- Teste Busca Sequencial ---
                long inicioSeq = System.nanoTime();
                BuscaSequencial.buscar(dados, alvo);
                long fimSeq = System.nanoTime();
                temposSequencial[i] = (fimSeq - inicioSeq);

                // --- Teste Busca Binária ---
                long inicioBin = System.nanoTime();
                BuscaBinaria.buscar(dadosOrdenados, alvo);
                long fimBin = System.nanoTime();
                temposBinaria[i] = (fimBin - inicioBin);

                // --- Teste Busca em Árvore (BST) ---
                long inicioArvore = System.nanoTime();
                bst.buscar(alvo);
                long fimArvore = System.nanoTime();
                temposArvore[i] = (fimArvore - inicioArvore);
            }

            // 3. Cálculos Estatísticos e Impressão dos Resultados
            imprimirResultados("Busca Sequencial", temposSequencial);
            imprimirResultados("Busca Binária", temposBinaria);
            imprimirResultados("Busca em Árvore (BST)", temposArvore);
            System.out.println();
        }
    }

    // =========================================================
    // MÉTODOS AUXILIARES PARA CÁLCULO ESTATÍSTICO
    // =========================================================

    public static void imprimirResultados(String nomeBusca, long[] tempos) {
        double media = calcularMedia(tempos);
        double desvioPadrao = calcularDesvioPadrao(tempos, media);

        // Imprime os resultados formatados com 2 casas decimais
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