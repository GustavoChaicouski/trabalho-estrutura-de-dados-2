package Arvores.TapeceiroViajante;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BenchMarkTSP {
	private static final int EXECUCOES = 30;
    private static final int WARMUP = 5;

    public static void main(String[] args) {
        int[] tamanhos = {50, 100, 200};

        System.out.println("--- INICIANDO RITUAL DE BENCHMARK: TSP HEURÍSTICAS ---");

        for (int n : tamanhos) {
            System.out.println("\n---------------------------------------------------");
            System.out.println("CENÁRIO: " + n + " Cidades");
            System.out.println("---------------------------------------------------");
            
            double[] temposNN = new double[EXECUCOES - WARMUP];
            double[] distanciasNN = new double[EXECUCOES - WARMUP];
            double[] temposOpt = new double[EXECUCOES - WARMUP];
            double[] distanciasOpt = new double[EXECUCOES - WARMUP];

            for (int i = 0; i < EXECUCOES; i++) {
                List<Cidade> cidades = gerarCidades(n, i);
                
                // Medição Vizinho Mais Próximo
                long ini = System.nanoTime();
                SolucaoTSP snn = HeuristicaTSP.vizinhoMaisProximo(cidades);
                long fim = System.nanoTime();
                
                if (i >= WARMUP) {
                    temposNN[i - WARMUP] = (fim - ini) / 1_000_000.0;
                    distanciasNN[i - WARMUP] = snn.distanciaTotal;
                }

                // Medição 2-opt
                ini = System.nanoTime();
                SolucaoTSP s2opt = HeuristicaTSP.aplicarTwoOpt(snn);
                fim = System.nanoTime();

                if (i >= WARMUP) {
                    temposOpt[i - WARMUP] = (fim - ini) / 1_000_000.0;
                    distanciasOpt[i - WARMUP] = s2opt.distanciaTotal;
                }
            }

            exibirStats("Nearest Neighbor", temposNN, distanciasNN);
            exibirStats("NN + 2-opt", temposOpt, distanciasOpt);
            
            double melhoraMedia = ((calcularMedia(distanciasNN) - calcularMedia(distanciasOpt)) / calcularMedia(distanciasNN)) * 100;
            System.out.printf("Ganho médio de eficiência com 2-opt: %.2f%%\n", melhoraMedia);
        }
        
        System.out.println("\nO ciclo de cálculos se encerra aqui. O tempo é o único recurso que não se recupera.");
    }

    private static List<Cidade> gerarCidades(int n, int seed) {
        Random rand = new Random(seed);
        List<Cidade> lista = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            lista.add(new Cidade(i, rand.nextDouble() * 1000, rand.nextDouble() * 1000));
        }
        return lista;
    }

    private static void exibirStats(String label, double[] tempos, double[] distancias) {
        double mediaT = calcularMedia(tempos);
        double dpT = calcularDesvio(tempos, mediaT);
        double mediaD = calcularMedia(distancias);

        System.out.printf("[%s]\n", label);
        System.out.printf("   Tempo Médio: %.4f ms | DP: %.4f ms\n", mediaT, dpT);
        System.out.printf("   Distância Média: %.2f unidades\n", mediaD);
    }

    private static double calcularMedia(double[] dados) {
        double soma = 0;
        for (double d : dados) soma += d;
        return soma / dados.length;
    }

    private static double calcularDesvio(double[] dados, double media) {
        double var = 0;
        for (double d : dados) var += Math.pow(d - media, 2);
        return Math.sqrt(var / dados.length);
    }
}
