package Arvores.ArvoreRN;

public class NoRN {
	static final boolean RED = true;
    static final boolean BLACK = false;

    int valor;
    boolean cor;
    NoRN esquerda, direita, pai;

    public NoRN(int valor) {
        this.valor = valor;
        this.cor = RED;
    }

}
