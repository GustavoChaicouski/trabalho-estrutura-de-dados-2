import java.util.Random;

/**
 * PROJETO III – Benchmark de Ordenação
 * Algoritmo: Heap Sort
 * Avalia: Melhor Caso, Caso Médio e Pior Caso
 * Métricas: Tempo, comparações, trocas e análise estatística
 */
public class HeapSortBenchmark {

    static long comparacoes;
    static long trocas;

    // ========================
    // HEAP SORT
    // ========================

    static void heapSort(int[] arr) {
        int n = arr.length;
        comparacoes = 0;
        trocas = 0;

        // Construção do heap (max-heap)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extração dos elementos
        for (int i = n - 1; i > 0; i--) {
            trocar(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    static void heapify(int[] arr, int n, int i) {
        int maior = i;
        int esquerda = 2 * i + 1;
        int direita = 2 * i + 2;

        if (esquerda < n) {
            comparacoes++;
            if (arr[esquerda] > arr[maior]) {
                maior = esquerda;
            }
        }

        if (direita < n) {
            comparacoes++;
            if (arr[direita] > arr[maior]) {
                maior = direita;
            }
        }

        if (maior != i) {
            trocar(arr, i, maior);
            heapify(arr, n, maior);
        }
    }

    static void trocar(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        trocas++;
    }

    // ========================
    // GERADORES DE CASOS
    // ========================

    static int[] gerarMelhorCaso(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i;
        return arr;
    }

    static int[] gerarPiorCaso(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = n - i;
        return arr;
    }

    static int[] gerarCasoMedio(int n, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(n * 10);
        return arr;
    }

    // ========================
    // ESTATÍSTICA
    // ========================

    static double media(long[] v) {
        double soma = 0;
        for (long x : v) soma += x;
        return soma / v.length;
    }

    static double desvioPadrao(long[] v, double media) {
        double soma = 0;
        for (long x : v) soma += Math.pow(x - media, 2);
        return Math.sqrt(soma / v.length);
    }

    static long min(long[] v) {
        long m = v[0];
        for (long x : v) if (x < m) m = x;
        return m;
    }

    static long max(long[] v) {
        long m = v[0];
        for (long x : v) if (x > m) m = x;
        return m;
    }

    // ========================
    // BENCHMARK
    // ========================

    static void executarBenchmark(String nome, int n, int repeticoes, String tipo) {

        Random rand = new Random(42);

        long[] tempos = new long[repeticoes];
        long[] comps  = new long[repeticoes];
        long[] troc   = new long[repeticoes];

        for (int i = 0; i < repeticoes; i++) {

            int[] arr;
            switch (tipo) {
                case "melhor": arr = gerarMelhorCaso(n); break;
                case "pior":   arr = gerarPiorCaso(n); break;
                default:       arr = gerarCasoMedio(n, rand);
            }

            long inicio = System.nanoTime();
            heapSort(arr);
            long fim = System.nanoTime();

            tempos[i] = fim - inicio;
            comps[i]  = comparacoes;
            troc[i]   = trocas;
        }

        double mediaTempo = media(tempos) / 1_000_000.0;
        double dpTempo    = desvioPadrao(tempos, media(tempos)) / 1_000_000.0;

        double mediaComp  = media(comps);
        double mediaTroca = media(troc);

        double teorico = n * (Math.log(n) / Math.log(2));

        System.out.println("═══════════════════════════════════════════");
        System.out.println(nome + " (n=" + n + ")");
        System.out.println("═══════════════════════════════════════════");

        System.out.printf("Tempo médio: %.4f ms%n", mediaTempo);
        System.out.printf("Desvio padrão: %.4f ms%n", dpTempo);
        System.out.printf("Tempo min: %.4f ms%n", min(tempos) / 1_000_000.0);
        System.out.printf("Tempo max: %.4f ms%n", max(tempos) / 1_000_000.0);

        System.out.println("-----");

        System.out.printf("Comparações (prática): %.0f%n", mediaComp);
        System.out.printf("Comparações (teoria ~ n log n): %.0f%n", teorico);

        System.out.printf("Trocas médias: %.0f%n", mediaTroca);
        System.out.println();
    }

    // ========================
    // MAIN
    // ========================

    public static void main(String[] args) {

        int[] tamanhos = {100, 500, 1000, 5000};
        int repeticoes = 30;

        for (int n : tamanhos) {

            executarBenchmark("MELHOR CASO", n, repeticoes, "melhor");
            executarBenchmark("CASO MÉDIO", n, repeticoes, "medio");
            executarBenchmark("PIOR CASO", n, repeticoes, "pior");
        }

        System.out.println("===== COMPLEXIDADE TEÓRICA =====");
        System.out.println("Melhor caso: O(n log n)");
        System.out.println("Caso médio:  O(n log n)");
        System.out.println("Pior caso:   O(n log n)");
        System.out.println("Espaço: O(1)");
        System.out.println("Estável: Não");
    }
}