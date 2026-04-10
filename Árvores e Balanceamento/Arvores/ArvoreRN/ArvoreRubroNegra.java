package Arvores.ArvoreRN;

public class ArvoreRubroNegra {
    private final NoRN T_NIL;
    private NoRN raiz;

    public ArvoreRubroNegra() {
        T_NIL = new NoRN(0);
        T_NIL.cor = NoRN.BLACK;
        T_NIL.esquerda = T_NIL.direita = T_NIL;
        raiz = T_NIL;
        T_NIL.pai = T_NIL;
    }

    private void rotacaoEsquerda(NoRN x) {
        NoRN y = x.direita;
        x.direita = y.esquerda;
        if (y.esquerda != T_NIL) y.esquerda.pai = x;
        y.pai = x.pai;
        if (x.pai == T_NIL) raiz = y;
        else if (x == x.pai.esquerda) x.pai.esquerda = y;
        else x.pai.direita = y;
        y.esquerda = x;
        x.pai = y;
    }

    private void rotacaoDireita(NoRN y) {
        NoRN x = y.esquerda;
        y.esquerda = x.direita;
        if (x.direita != T_NIL) x.direita.pai = y;
        x.pai = y.pai;
        if (y.pai == T_NIL) raiz = x;
        else if (y == y.pai.direita) y.pai.direita = x;
        else y.pai.esquerda = x;
        x.direita = y;
        y.pai = x;
    }

    public void inserir(int valor) {
        NoRN no = new NoRN(valor);
        NoRN y = T_NIL;
        NoRN x = raiz;

        while (x != T_NIL) {
            y = x;
            if (no.valor < x.valor) x = x.esquerda;
            else if (no.valor > x.valor) x = x.direita;
            else return; // Duplicatas são ignoradas
        }

        no.pai = y;
        if (y == T_NIL) raiz = no;
        else if (no.valor < y.valor) y.esquerda = no;
        else y.direita = no;

        no.esquerda = no.direita = T_NIL;
        fixupInsercao(no);
    }

    private void fixupInsercao(NoRN z) {
        while (z.pai.cor == NoRN.RED) {
            if (z.pai == z.pai.pai.esquerda) {
                NoRN y = z.pai.pai.direita;
                if (y.cor == NoRN.RED) {
                    z.pai.cor = NoRN.BLACK;
                    y.cor = NoRN.BLACK;
                    z.pai.pai.cor = NoRN.RED;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.direita) {
                        z = z.pai;
                        rotacaoEsquerda(z);
                    }
                    z.pai.cor = NoRN.BLACK;
                    z.pai.pai.cor = NoRN.RED;
                    rotacaoDireita(z.pai.pai);
                }
            } else {
                NoRN y = z.pai.pai.esquerda;
                if (y.cor == NoRN.RED) {
                    z.pai.cor = NoRN.BLACK;
                    y.cor = NoRN.BLACK;
                    z.pai.pai.cor = NoRN.RED;
                    z = z.pai.pai;
                } else {
                    if (z == z.pai.esquerda) {
                        z = z.pai;
                        rotacaoDireita(z);
                    }
                    z.pai.cor = NoRN.BLACK;
                    z.pai.pai.cor = NoRN.RED;
                    rotacaoEsquerda(z.pai.pai);
                }
            }
        }
        raiz.cor = NoRN.BLACK;
    }

    public void remover(int valor) {
        NoRN z = buscarNo(raiz, valor);
        if (z == T_NIL) return;

        NoRN x, y;
        y = z;
        boolean corOriginalY = y.cor;
        if (z.esquerda == T_NIL) {
            x = z.direita;
            transplant(z, z.direita);
        } else if (z.direita == T_NIL) {
            x = z.esquerda;
            transplant(z, z.esquerda);
        } else {
            y = minimo(z.direita);
            corOriginalY = y.cor;
            x = y.direita;
            if (y.pai == z) x.pai = y;
            else {
                transplant(y, y.direita);
                y.direita = z.direita;
                y.direita.pai = y;
            }
            transplant(z, y);
            y.esquerda = z.esquerda;
            y.esquerda.pai = y;
            y.cor = z.cor;
        }
        if (corOriginalY == NoRN.BLACK) fixupRemocao(x);
    }

    private void transplant(NoRN u, NoRN v) {
        if (u.pai == T_NIL) raiz = v;
        else if (u == u.pai.esquerda) u.pai.esquerda = v;
        else u.pai.direita = v;
        v.pai = u.pai;
    }

    private void fixupRemocao(NoRN x) {
        while (x != raiz && x.cor == NoRN.BLACK) {
            if (x == x.pai.esquerda) {
                NoRN w = x.pai.direita;
                if (w.cor == NoRN.RED) {
                    w.cor = NoRN.BLACK;
                    x.pai.cor = NoRN.RED;
                    rotacaoEsquerda(x.pai);
                    w = x.pai.direita;
                }
                if (w.esquerda.cor == NoRN.BLACK && w.direita.cor == NoRN.BLACK) {
                    w.cor = NoRN.RED;
                    x = x.pai;
                } else {
                    if (w.direita.cor == NoRN.BLACK) {
                        w.esquerda.cor = NoRN.BLACK;
                        w.cor = NoRN.RED;
                        rotacaoDireita(w);
                        w = x.pai.direita;
                    }
                    w.cor = x.pai.cor;
                    x.pai.cor = NoRN.BLACK;
                    w.direita.cor = NoRN.BLACK;
                    rotacaoEsquerda(x.pai);
                    x = raiz;
                }
            } else {
                NoRN w = x.pai.esquerda;
                if (w.cor == NoRN.RED) {
                    w.cor = NoRN.BLACK;
                    x.pai.cor = NoRN.RED;
                    rotacaoDireita(x.pai);
                    w = x.pai.esquerda;
                }
                if (w.direita.cor == NoRN.BLACK && w.esquerda.cor == NoRN.BLACK) {
                    w.cor = NoRN.RED;
                    x = x.pai;
                } else {
                    if (w.esquerda.cor == NoRN.BLACK) {
                        w.direita.cor = NoRN.BLACK;
                        w.cor = NoRN.RED;
                        rotacaoEsquerda(w);
                        w = x.pai.esquerda;
                    }
                    w.cor = x.pai.cor;
                    x.pai.cor = NoRN.BLACK;
                    w.esquerda.cor = NoRN.BLACK;
                    rotacaoDireita(x.pai);
                    x = raiz;
                }
            }
        }
        x.cor = NoRN.BLACK;
    }

    private NoRN minimo(NoRN no) {
        while (no.esquerda != T_NIL) no = no.esquerda;
        return no;
    }

    private NoRN buscarNo(NoRN no, int valor) {
        if (no == T_NIL || valor == no.valor) return no;
        if (valor < no.valor) return buscarNo(no.esquerda, valor);
        return buscarNo(no.direita, valor);
    }

    public boolean buscar(int valor) {
        return buscarNo(raiz, valor) != T_NIL;
    }

    public int calcularAltura() {
        return alturaRecursiva(raiz);
    }

    private int alturaRecursiva(NoRN no) {
        if (no == T_NIL) return -1;
        return 1 + Math.max(alturaRecursiva(no.esquerda), alturaRecursiva(no.direita));
    }
}