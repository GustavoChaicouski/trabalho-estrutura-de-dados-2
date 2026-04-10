package Arvores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class No {
    int valor;
    No esquerda, direita;

    public No(int valor) {
        this.valor = valor;
        this.esquerda = this.direita = null;
    }
}

class ArvoreBi_BST {
    No raiz;

    public ArvoreBi_BST() {
        this.raiz = null;
    }

    public void inserir(int valor) {
        raiz = inserirRecursivo(raiz, valor);
    }

    private No inserirRecursivo(No raiz, int valor) {
        if (raiz == null) return new No(valor);
        if (valor < raiz.valor) {
            raiz.esquerda = inserirRecursivo(raiz.esquerda, valor);
        } else if (valor > raiz.valor) {
            raiz.direita = inserirRecursivo(raiz.direita, valor);
        }
        return raiz;
    }

    public boolean buscar(int valor) {
        return buscarRecursivo(raiz, valor);
    }

    private boolean buscarRecursivo(No raiz, int valor) {
        if (raiz == null) return false;
        if (raiz.valor == valor) return true;
        return valor < raiz.valor ? buscarRecursivo(raiz.esquerda, valor) : buscarRecursivo(raiz.direita, valor);
    }

    public void remover(int valor) {
        raiz = removerRecursivo(raiz, valor);
    }

    private No removerRecursivo(No raiz, int valor) {
        if (raiz == null) return null;
        if (valor < raiz.valor) {
            raiz.esquerda = removerRecursivo(raiz.esquerda, valor);
        } else if (valor > raiz.valor) {
            raiz.direita = removerRecursivo(raiz.direita, valor);
        } else {
            if (raiz.esquerda == null) return raiz.direita;
            if (raiz.direita == null) return raiz.esquerda;
            raiz.valor = encontrarMinimo(raiz.direita);
            raiz.direita = removerRecursivo(raiz.direita, raiz.valor);
        }
        return raiz;
    }

    private int encontrarMinimo(No raiz) {
        int min = raiz.valor;
        while (raiz.esquerda != null) {
            min = raiz.esquerda.valor;
            raiz = raiz.esquerda;
        }
        return min;
    }

    public int calcularAltura() {
        return calcularAlturaRecursivo(raiz);
    }

    private int calcularAlturaRecursivo(No raiz) {
        if (raiz == null) return -1;
        return Math.max(calcularAlturaRecursivo(raiz.esquerda), calcularAlturaRecursivo(raiz.direita)) + 1;
    }
}

