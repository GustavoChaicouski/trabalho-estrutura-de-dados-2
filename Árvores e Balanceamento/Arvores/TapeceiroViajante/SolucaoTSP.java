package Arvores.TapeceiroViajante;
import java.util.ArrayList;
import java.util.List;

public class SolucaoTSP {
    List<Cidade> rota;
    double distanciaTotal;

    public SolucaoTSP(List<Cidade> rota) {
        this.rota = new ArrayList<>(rota);
        this.distanciaTotal = calcularDistanciaRota(this.rota);
    }

    private double calcularDistanciaRota(List<Cidade> r) {
        double d = 0;
        for (int i = 0; i < r.size() - 1; i++) {
            d += r.get(i).calcularDistancia(r.get(i + 1));
        }
        d += r.get(r.size() - 1).calcularDistancia(r.get(0)); // Retorno à origem
        return d;
    }
}
