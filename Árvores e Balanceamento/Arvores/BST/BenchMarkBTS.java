package Arvores.BST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BenchMarkBTS {

	    private static final int TOTAL_EXECUCOES = 30;
	    private static final int WARMUP = 5; // Execuções descartadas para aquecimento do JIT
	    private static final int EXECUCOES_VALIDAS = TOTAL_EXECUCOES - WARMUP;

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);

	        // Cenário Aleatório: Suporta números maiores sem esgotar a pilha de recursão
	        int[] tamanhosAleatorios = {10000, 50000, 100000};
	        System.out.println("=======================================================");
	        System.out.println("  CENÁRIO A: INSERÇÃO COM DADOS ALEATÓRIOS (CASO MÉDIO) ");
	        System.out.println("=======================================================");
	        for (int N : tamanhosAleatorios) {
	            executarExperimento(N, false);
	        }

	        // Cenário Ordenado: Tamanhos reduzidos. Acima de ~8000, a recursão da BST
	        // causará StackOverflowError na maioria das JVMs padrão.
	        int[] tamanhosOrdenados = {1000, 3000, 5000};
	        System.out.println("\n=======================================================");
	        System.out.println(" CENÁRIO B: INSERÇÃO ORDENADA (O ABISMO DO PIOR CASO)  ");
	        System.out.println("=======================================================");
	        for (int N : tamanhosOrdenados) {
	            executarExperimento(N, true);
	        }

	        System.out.println("\nO experimento terminou. As sombras dos dados permanecem.");
	        System.out.println("Pressione ENTER para fechar o terminal...");
	        scanner.nextLine();
	        scanner.close();
	    }

	    private static void executarExperimento(int N, boolean ordenado) {
	        long[] temposInsercao = new long[EXECUCOES_VALIDAS];
	        long[] temposBusca = new long[EXECUCOES_VALIDAS];
	        long[] temposRemocao = new long[EXECUCOES_VALIDAS];
	        int ultimaAltura = 0;

	        System.out.println("\n-> Analisando volume de dados: " + N);

	        for (int i = 0; i < TOTAL_EXECUCOES; i++) {
	            ArvoreBi_BST bst = new ArvoreBi_BST();
	            Random random = new Random(System.nanoTime()); // Semente nova a cada iteração

	            // 1. Geração de Dados Únicos
	            List<Integer> dados = new ArrayList<>(N);
	            if (ordenado) {
	                for (int j = 1; j <= N; j++) dados.add(j);
	            } else {
	                for (int j = 1; j <= N; j++) dados.add(j);
	                Collections.shuffle(dados, random);
	            }

	            // 2. Medição da Inserção
	            long inicio = System.nanoTime();
	            for (int valor : dados) {
	                bst.inserir(valor);
	            }
	            long tempoInsercao = System.nanoTime() - inicio;

	            // 3. Preparação para Busca (Realista: 50% existentes, 50% inexistentes)
	            int numBuscas = Math.min(N, 1000); // Limita buscas para não sobrecarregar o tempo total
	            List<Integer> alvosBusca = new ArrayList<>(numBuscas);
	            for (int j = 0; j < numBuscas / 2; j++) {
	                alvosBusca.add(dados.get(random.nextInt(N))); // Existente
	                alvosBusca.add(N + 1 + random.nextInt(N));    // Inexistente (garantido pois dados vão até N)
	            }
	            Collections.shuffle(alvosBusca, random);

	            // 4. Medição da Busca
	            inicio = System.nanoTime();
	            for (int alvo : alvosBusca) {
	                bst.buscar(alvo);
	            }
	            long tempoBusca = System.nanoTime() - inicio;

	            // 5. Preparação para Remoção (Sem viés de ordem)
	            List<Integer> dadosParaRemover = new ArrayList<>(dados);
	            Collections.shuffle(dadosParaRemover, random);

	            // 6. Medição da Remoção
	            inicio = System.nanoTime();
	            for (int valor : dadosParaRemover) {
	                bst.remover(valor);
	            }
	            long tempoRemocao = System.nanoTime() - inicio;

	            // 7. Registro de Tempos (ignorando o Warm-up)
	            if (i >= WARMUP) {
	                int index = i - WARMUP;
	                temposInsercao[index] = tempoInsercao;
	                temposBusca[index] = tempoBusca;
	                temposRemocao[index] = tempoRemocao;
	            }

	            // Armazena a altura da última árvore válida antes de ser obliterada
	            if (i == TOTAL_EXECUCOES - 1) {
	                // Reconstrói rapidamente a última árvore apenas para pegar a altura final 
	                // já que a etapa de remoção a destruiu completamente.
	                for (int valor : dados) bst.inserir(valor);
	                ultimaAltura = bst.calcularAltura();
	            }
	        }

	        // Exibição dos Resultados Estatísticos
	        imprimirEstatisticas("Inserção", temposInsercao);
	        imprimirEstatisticas("Busca", temposBusca);
	        imprimirEstatisticas("Remoção", temposRemocao);
	        System.out.println("   Altura final da árvore: " + ultimaAltura);
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
	
	

