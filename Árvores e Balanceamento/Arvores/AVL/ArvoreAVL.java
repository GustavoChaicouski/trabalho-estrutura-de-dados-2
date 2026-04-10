package Arvores.AVL;

public class ArvoreAVL {
    NoAVL raiz;

    private int altura(NoAVL no) {
        if (no == null) return 0;
        return no.altura;
    }

    private int getFatorBalanceamento(NoAVL no) {
        if (no == null) return 0;
        return altura(no.esquerda) - altura(no.direita);
    }

    private NoAVL rotacaoDireita(NoAVL y) {
        NoAVL x = y.esquerda;
        NoAVL T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;

        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private NoAVL rotacaoEsquerda(NoAVL x) {
        NoAVL y = x.direita;
        NoAVL T2 = y.esquerda;

        y.esquerda = x;
        x.direita = T2;

        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }

    public void inserir(int valor) {
        raiz = inserirRecursivo(raiz, valor);
    }

    private NoAVL inserirRecursivo(NoAVL no, int valor) {
        if (no == null) return new NoAVL(valor);

        if (valor < no.valor)
            no.esquerda = inserirRecursivo(no.esquerda, valor);
        else if (valor > no.valor)
            no.direita = inserirRecursivo(no.direita, valor);
        else
            return no; // A duplicata não sobrevive

        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));

        int balanceamento = getFatorBalanceamento(no);

        // Rotação Simples à Direita (LL)
        if (balanceamento > 1 && valor < no.esquerda.valor)
            return rotacaoDireita(no);

        // Rotação Simples à Esquerda (RR)
        if (balanceamento < -1 && valor > no.direita.valor)
            return rotacaoEsquerda(no);

        // Rotação Dupla Esquerda-Direita (LR)
        if (balanceamento > 1 && valor > no.esquerda.valor) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Rotação Dupla Direita-Esquerda (RL)
        if (balanceamento < -1 && valor < no.direita.valor) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private NoAVL noMinimo(NoAVL no) {
        NoAVL atual = no;
        while (atual.esquerda != null)
            atual = atual.esquerda;
        return atual;
    }

    public void remover(int valor) {
        raiz = removerRecursivo(raiz, valor);
    }

    private NoAVL removerRecursivo(NoAVL no, int valor) {
        if (no == null) return no;

        if (valor < no.valor)
            no.esquerda = removerRecursivo(no.esquerda, valor);
        else if (valor > no.valor)
            no.direita = removerRecursivo(no.direita, valor);
        else {
            if ((no.esquerda == null) || (no.direita == null)) {
            	NoAVL temp;
            	if (no.esquerda != null)
            	    temp = no.esquerda;
            	else
            	    temp = no.direita;
                if (temp == null) {
                    temp = no;
                    no = null;
                } else {
                    no = temp; 
                }
            } else {
                NoAVL temp = noMinimo(no.direita);
                no.valor = temp.valor;
                no.direita = removerRecursivo(no.direita, temp.valor);
            }
        }

        if (no == null) return no;

        no.altura = Math.max(altura(no.esquerda), altura(no.direita)) + 1;

        int balanceamento = getFatorBalanceamento(no);

        // Rotação Simples à Direita (LL)
        if (balanceamento > 1 && getFatorBalanceamento(no.esquerda) >= 0)
            return rotacaoDireita(no);

        // Rotação Dupla Esquerda-Direita (LR)
        if (balanceamento > 1 && getFatorBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Rotação Simples à Esquerda (RR)
        if (balanceamento < -1 && getFatorBalanceamento(no.direita) <= 0)
            return rotacaoEsquerda(no);

        // Rotação Dupla Direita-Esquerda (RL)
        if (balanceamento < -1 && getFatorBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    public boolean buscar(int valor) {
        return buscarRecursivo(raiz, valor);
    }

    private boolean buscarRecursivo(NoAVL no, int valor) {
        if (no == null) return false;
        if (no.valor == valor) return true;
        if (valor < no.valor) return buscarRecursivo(no.esquerda, valor);
        return buscarRecursivo(no.direita, valor);
    }

    public int calcularAltura() {
        if (raiz == null) return -1;
        return raiz.altura - 1; 
    }
}
