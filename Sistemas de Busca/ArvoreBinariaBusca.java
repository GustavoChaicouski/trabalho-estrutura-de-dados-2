package projeto2;

public class ArvoreBinariaBusca {
	private No raiz;

	public ArvoreBinariaBusca() {
		raiz = null;
	}

	public void inserir(int valor) {
		raiz = inserirRecursivo(raiz, valor);
	}

	private No inserirRecursivo(No raiz, int valor) {
		if (raiz == null) {
			raiz = new No(valor);
			return raiz;
		}
		if (valor < raiz.valor) {
			raiz.esquerda = inserirRecursivo(raiz.esquerda, valor);
		} else if (valor > raiz.valor) {
			raiz.direita = inserirRecursivo(raiz.direita, valor);
		}
		return raiz;
	}

	public boolean buscar(int alvo) {
		return buscarRecursivo(raiz, alvo);
	}

	private boolean buscarRecursivo(No raiz, int alvo) {
		if (raiz == null) {
			return false;
		}
		if (raiz.valor == alvo) {
			return true;
		}
		if (alvo < raiz.valor) {
			return buscarRecursivo(raiz.esquerda, alvo);
		}
		return buscarRecursivo(raiz.direita, alvo);
	}
}