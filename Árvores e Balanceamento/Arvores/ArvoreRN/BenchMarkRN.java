package Arvores.ArvoreRN;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BenchMarkRN {
	private static final int TOTAL_EXEC = 30;
    private static final int WARMUP = 5;
    private static final int VALID = TOTAL_EXEC - WARMUP;

    public static void main(String[] args) {
        int[] tamanhos = {10000, 50000, 100000};
        
        System.out.println("--- INICIANDO RITUAL EXPERIMENTAL: RED-BLACK TREE ---");
        
        System.out.println("\n[CENÁRIO A] - DADOS ALEATÓRIOS");
        for (int N : tamanhos) rodar(N, false);
        
        System.out.println("\n[CENÁRIO B] - DADOS ORDENADOS (A PROVA DE FOGO)");
        for (int N : tamanhos) rodar(N, true);
    }

    private static void rodar(int N, boolean ordenado) {
        long[] tIns = new long[VALID], tBus = new long[VALID], tRem = new long[VALID];
        int altura = 0;

        for (int i = 0; i < TOTAL_EXEC; i++) {
            ArvoreRubroNegra arn = new ArvoreRubroNegra();
            Random rand = new Random(i);
            List<Integer> dados = new ArrayList<>(N);
            for (int j = 1; j <= N; j++) dados.add(j);
            if (!ordenado) Collections.shuffle(dados, rand);

            // Inserção
            long ini = System.nanoTime();
            for (int v : dados) arn.inserir(v);
            long fim = System.nanoTime();
            if (i >= WARMUP) tIns[i - WARMUP] = fim - ini;

            // Busca (1000 ops: 50% exist / 50% void)
            List<Integer> alvos = new ArrayList<>();
            for(int j=0; j<500; j++) {
                alvos.add(dados.get(rand.nextInt(N)));
                alvos.add(N + rand.nextInt(N));
            }
            Collections.shuffle(alvos, rand);
            ini = System.nanoTime();
            for (int v : alvos) arn.buscar(v);
            fim = System.nanoTime();
            if (i >= WARMUP) tBus[i - WARMUP] = fim - ini;

            // Remoção (1000 elementos)
            List<Integer> remover = new ArrayList<>(dados.subList(0, 1000));
            Collections.shuffle(remover, rand);
            ini = System.nanoTime();
            for (int v : remover) arn.remover(v);
            fim = System.nanoTime();
            if (i >= WARMUP) tRem[i - WARMUP] = fim - ini;

            if (i == TOTAL_EXEC - 1) altura = arn.calcularAltura();
        }
        
        System.out.printf("\nN: %d | Altura: %d\n", N, altura);
        printStats("Inserção", tIns);
        printStats("Busca", tBus);
        printStats("Remoção", tRem);
    }

    private static void printStats(String op, long[] tempos) {
        double media = 0;
        for (long t : tempos) media += t;
        media /= tempos.length;
        double var = 0;
        for (long t : tempos) var += Math.pow(t - media, 2);
        double dp = Math.sqrt(var / tempos.length);
        System.out.printf("   %-10s -> Média: %.4f ms | DP: %.4f ms\n", op, media/1e6, dp/1e6);
    }
}
