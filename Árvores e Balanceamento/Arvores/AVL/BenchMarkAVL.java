package Arvores.AVL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BenchMarkAVL {

    private static final int TOTAL_EXECUCOES = 30;
    private static final int WARMUP = 5;
    private static final int EXECUCOES_VALIDAS = TOTAL_EXECUCOES - WARMUP;

    public static void main(String[] args) {
        // Ao contrário da BST, a AVL não cede ao StackOverflow com dados massivos ordenados. 
        // Os tamanhos podem ser iguais para ambos os cenários e a morte da pilha não acontecerá.
        int[] tamanhos = {10000, 50000, 100000}; 

        System.out.println("=======================================================");
        System.out.println("  CENÁRIO A: INSERÇÃO COM DADOS ALEATÓRIOS (CASO MÉDIO)");
        System.out.println("=======================================================");
        for (int N : tamanhos) {
            executarExperimento(N, false);
        }

        System.out.println("\n=======================================================");
        System.out.println("  CENÁRIO B: INSERÇÃO ORDENADA (A FORÇA DAS ROTAÇÕES)  ");
        System.out.println("=======================================================");
        for (int N : tamanhos) {
            executarExperimento(N, true);
        }
    }

    private static void executarExperimento(int N, boolean ordenado) {
        long[] temposInsercao = new long[EXECUCOES_VALIDAS];
        long[] temposBusca = new long[EXECUCOES_VALIDAS];
        long[] temposRemocao = new long[EXECUCOES_VALIDAS];
        int ultimaAltura = 0;

        System.out.println("\n-> Invocando volume de dados: " + N);

        for (int i = 0; i < TOTAL_EXECUCOES; i++) {
            ArvoreAVL avl = new ArvoreAVL();
            Random random = new Random(i); // Semente ritualística forçada ao índice

            // 1. Concepção dos Dados
            List<Integer> dados = new ArrayList<>(N);
            if (ordenado) {
                for (int j = 1; j <= N; j++) dados.add(j);
            } else {
                for (int j = 1; j <= N; j++) dados.add(j);
                Collections.shuffle(dados, random);
            }

            // 2. O Peso da Inserção
            long inicio = System.nanoTime();
            for (int valor : dados) {
                avl.inserir(valor);
            }
            long tempoInsercao = System.nanoTime() - inicio;

            // 3. Preparação para a Busca 
            int numBuscas = Math.min(N, 1000);
            List<Integer> alvosBusca = new ArrayList<>(numBuscas);
            for (int j = 0; j < numBuscas / 2; j++) {
                alvosBusca.add(dados.get(random.nextInt(N))); // Fica na existência
                alvosBusca.add(N + 1 + random.nextInt(N));    // Perde-se no vazio
            }
            Collections.shuffle(alvosBusca, random);

            // 4. O Ato da Busca
            inicio = System.nanoTime();
            for (int alvo : alvosBusca) {
                avl.buscar(alvo);
            }
            long tempoBusca = System.nanoTime() - inicio;

            // 5. Preparação para a Poda (Remoção)
            int numRemocoes = Math.min(N, 1000);
            List<Integer> dadosParaRemover = new ArrayList<>(dados.subList(0, numRemocoes));
            Collections.shuffle(dadosParaRemover, random);

            // 6. A Dor da Remoção
            inicio = System.nanoTime();
            for (int valor : dadosParaRemover) {
                avl.remover(valor);
            }
            long tempoRemocao = System.nanoTime() - inicio;

            // 7. Registro no Purgatório dos Dados
            if (i >= WARMUP) {
                int index = i - WARMUP;
                temposInsercao[index] = tempoInsercao;
                temposBusca[index] = tempoBusca;
                temposRemocao[index] = tempoRemocao;
            }

            if (i == TOTAL_EXECUCOES - 1) {
                ultimaAltura = avl.calcularAltura();
            }
        }

        imprimirEstatisticas("Inserção", temposInsercao);
        imprimirEstatisticas("Busca", temposBusca);
        imprimirEstatisticas("Remoção", temposRemocao);
        System.out.println("   Altura final da árvore (após extrações): " + ultimaAltura);
    }

    private static void imprimirEstatisticas(String operacao, long[] temposNano) {
        double soma = 0;
        for (long t : temposNano) soma += t;
        double mediaNs = soma / temposNano.length;
        double mediaMs = mediaNs / 1_000_000.0;

        double variancia = 0;
        for (long t : temposNano) {
            variancia += Math.pow(t - mediaNs, 2);
        }
        variancia /= temposNano.length;
        double desvioPadraoMs = Math.sqrt(variancia) / 1_000_000.0;

        System.out.printf("   [%-8s] Média: %8.4f ms | Desvio Padrão: %8.4f ms\n", operacao, mediaMs, desvioPadraoMs);
    }
}
