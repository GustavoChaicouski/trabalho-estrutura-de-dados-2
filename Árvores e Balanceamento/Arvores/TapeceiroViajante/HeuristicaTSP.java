package Arvores.TapeceiroViajante;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeuristicaTSP {

    public static SolucaoTSP vizinhoMaisProximo(List<Cidade> cidades) {
        List<Cidade> naoVisitadas = new ArrayList<>(cidades);
        List<Cidade> rota = new ArrayList<>();
        
        Random rand = new Random();
        Cidade atual = naoVisitadas.remove(rand.nextInt(naoVisitadas.size()));
        rota.add(atual);

        while (!naoVisitadas.isEmpty()) {
            Cidade proxima = null;
            double minD = Double.MAX_VALUE;
            int indiceProxima = -1;

            for (int i = 0; i < naoVisitadas.size(); i++) {
                double d = atual.calcularDistancia(naoVisitadas.get(i));
                if (d < minD) {
                    minD = d;
                    proxima = naoVisitadas.get(i);
                    indiceProxima = i;
                }
            }
            atual = proxima;
            rota.add(naoVisitadas.remove(indiceProxima));
        }
        return new SolucaoTSP(rota);
    }

    public static SolucaoTSP aplicarTwoOpt(SolucaoTSP solucaoOriginal) {
        List<Cidade> rota = new ArrayList<>(solucaoOriginal.rota);
        boolean melhorou = true;
        int n = rota.size();

        while (melhorou) {
            melhorou = false;
            for (int i = 1; i < n - 1; i++) {
                for (int k = i + 1; k < n; k++) {
                    double ganho = calcularGanho(rota, i, k);
                    if (ganho < -1e-9) { // Se a mudança reduz a distância
                        inverterSubRota(rota, i, k);
                        melhorou = true;
                    }
                }
            }
        }
        return new SolucaoTSP(rota);
    }

    private static double calcularGanho(List<Cidade> rota, int i, int k) {
        int n = rota.size();
        Cidade A = rota.get(i - 1);
        Cidade B = rota.get(i);
        Cidade C = rota.get(k);
        Cidade D = (k + 1 < n) ? rota.get(k + 1) : rota.get(0);

        double dAtual = A.calcularDistancia(B) + C.calcularDistancia(D);
        double dNova = A.calcularDistancia(C) + B.calcularDistancia(D);
        
        return dNova - dAtual;
    }

    private static void inverterSubRota(List<Cidade> rota, int i, int k) {
        while (i < k) {
            Cidade temp = rota.get(i);
            rota.set(i, rota.get(k));
            rota.set(k, temp);
            i++;
            k--;
        }
    }
}
