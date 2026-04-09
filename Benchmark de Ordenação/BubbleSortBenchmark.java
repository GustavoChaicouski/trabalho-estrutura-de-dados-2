import java.util.Arrays;
import java.util.Random;

/**
 * PROJETO III – Benchmark de Ordenação
 * Algoritmo: Bubble Sort
 * Avalia: Melhor Caso, Caso Médio e Pior Caso
 * Métricas: Tempo de execução, comparações, trocas e análise estatística
 */
public class BubbleSortBenchmark {

    // ========================
    // BUBBLE SORT COM CONTADORES
    // ========================

    static long comparacoes;
    static long trocas;

    /**
     * Bubble Sort otimizado (com flag de parada antecipada)
     * - Melhor caso: O(n)      → array já ordenado
     * - Caso médio:  O(n²)     → array aleatório
     * - Pior caso:   O(n²)     → array inversamente ordenado
     */
    static void bubbleSort(int[] arr) {
        int n = arr.length;
        comparacoes = 0;
        trocas = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean houveTroca = false;

            for (int j = 0; j < n - i - 1; j++) {
                comparacoes++;
                if (arr[j] > arr[j + 1]) {
                    // Troca
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    trocas++;
                    houveTroca = true;
                }
            }

            // Otimização: se não houve troca, array já está ordenado
            if (!houveTroca) break;
        }
    }

    // ========================
    // GERADORES DE ARRAYS
    // ========================

    /** Melhor caso: array já ordenado crescentemente */
    static int[] gerarMelhorCaso(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i + 1;
        return arr;
    }

    /** Pior caso: array ordenado decrescentemente */
    static int[] gerarPiorCaso(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = n - i;
        return arr;
    }

    /** Caso médio: array com elementos aleatórios */
    static int[] gerarCasoMedio(int n, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(n * 10);
        return arr;
    }

    // ========================
    // ANÁLISE ESTATÍSTICA
    // ========================

    static double media(long[] valores) {
        double soma = 0;
        for (long v : valores) soma += v;
        return soma / valores.length;
    }

    static double desvioPadrao(long[] valores, double media) {
        double soma = 0;
        for (long v : valores) soma += Math.pow(v - media, 2);
        return Math.sqrt(soma / valores.length);
    }

    static long minimo(long[] valores) {
        long min = valores[0];
        for (long v : valores) if (v < min) min = v;
        return min;
    }

    static long maximo(long[] valores) {
        long max = valores[0];
        for (long v : valores) if (v > max) max = v;
        return max;
    }

    // ========================
    // EXECUÇÃO DO BENCHMARK
    // ========================

    static void executarBenchmark(String nomeCaso, int tamanho, int repeticoes, String tipoCaso) {
        Random rand = new Random(42); // seed fixo para reprodutibilidade

        long[] tempos      = new long[repeticoes];
        long[] comparacoesArr = new long[repeticoes];
        long[] trocasArr   = new long[repeticoes];

        for (int r = 0; r < repeticoes; r++) {
            int[] arr;
            switch (tipoCaso) {
                case "melhor": arr = gerarMelhorCaso(tamanho);       break;
                case "pior":   arr = gerarPiorCaso(tamanho);         break;
                default:       arr = gerarCasoMedio(tamanho, rand);  break;
            }

            // Aquece a JVM nas primeiras iterações (warm-up)
            long inicio = System.nanoTime();
            bubbleSort(arr);
            long fim = System.nanoTime();

            tempos[r]         = fim - inicio;
            comparacoesArr[r] = comparacoes;
            trocasArr[r]      = trocas;
        }

        // Análise estatística
        double mediaTempoMs    = media(tempos) / 1_000_000.0;
        double dpTempoMs       = desvioPadrao(tempos, media(tempos)) / 1_000_000.0;
        double mediaComp       = media(comparacoesArr);
        double mediaTrocas     = media(trocasArr);

        // Complexidade teórica esperada
        long compTeoricoMelhor = tamanho - 1L;
        long compTeoricoNQ     = (long) tamanho * (tamanho - 1) / 2;

        System.out.println("┌─────────────────────────────────────────────────┐");
        System.out.printf ("│  %s (n = %d, %d execuções)%n", nomeCaso, tamanho, repeticoes);
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.printf ("│  Tempo médio:       %10.4f ms%n", mediaTempoMs);
        System.out.printf ("│  Desvio padrão:     %10.4f ms%n", dpTempoMs);
        System.out.printf ("│  Tempo mínimo:      %10.4f ms%n", minimo(tempos) / 1_000_000.0);
        System.out.printf ("│  Tempo máximo:      %10.4f ms%n", maximo(tempos) / 1_000_000.0);
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.printf ("│  Comparações (prática):  %,15.0f%n", mediaComp);

        if (tipoCaso.equals("melhor")) {
            System.out.printf("│  Comparações (teoria):   %,15d  O(n)%n", compTeoricoMelhor);
        } else {
            System.out.printf("│  Comparações (teoria):   %,15d  O(n²)%n", compTeoricoNQ);
        }

        System.out.printf ("│  Trocas médias:          %,15.0f%n", mediaTrocas);
        System.out.println("└─────────────────────────────────────────────────┘");
        System.out.println();
    }

    // ========================
    // MAIN
    // ========================

    public static void main(String[] args) {

        System.out.println("╔═════════════════════════════════════════════════╗");
        System.out.println("║     PROJETO III – Benchmark: BUBBLE SORT        ║");
        System.out.println("╚═════════════════════════════════════════════════╝");
        System.out.println();

        int[] tamanhos   = {100, 500, 1000, 5000};
        int   repeticoes = 30; // 30 execuções para análise estatística robusta

        for (int n : tamanhos) {
            System.out.println("══════════════════════════════════════════════════");
            System.out.println("  TAMANHO DO ARRAY: " + n);
            System.out.println("══════════════════════════════════════════════════");
            System.out.println();

            executarBenchmark("MELHOR CASO  (array já ordenado)",     n, repeticoes, "melhor");
            executarBenchmark("CASO MÉDIO   (array aleatório)",       n, repeticoes, "medio");
            executarBenchmark("PIOR CASO    (array inversamente ord.)",n, repeticoes, "pior");
        }

        // Resumo teórico
        System.out.println("╔═════════════════════════════════════════════════╗");
        System.out.println("║         RESUMO – COMPLEXIDADE TEÓRICA           ║");
        System.out.println("╠═════════════════════════════════════════════════╣");
        System.out.println("║  Melhor caso:  O(n)   – array já ordenado       ║");
        System.out.println("║  Caso médio:   O(n²)  – array aleatório         ║");
        System.out.println("║  Pior caso:    O(n²)  – array invertido         ║");
        System.out.println("║  Espaço:       O(1)   – in-place                ║");
        System.out.println("║  Estável:      Sim                               ║");
        System.out.println("╚═════════════════════════════════════════════════╝");
    }
}
