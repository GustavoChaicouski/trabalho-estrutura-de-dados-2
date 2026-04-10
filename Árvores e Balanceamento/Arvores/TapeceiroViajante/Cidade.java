package Arvores.TapeceiroViajante;

public class Cidade {
    double x, y;
    int id;

    public Cidade(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public double calcularDistancia(Cidade outra) {
        double dx = this.x - outra.x;
        double dy = this.y - outra.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}