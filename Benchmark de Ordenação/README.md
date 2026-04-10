# Benchmark de Ordenação – Projeto III

Disciplina: Estruturas, Pesquisa e Ordenação de Dados – 2026/1
Prof. MSc. Gabriel Passos de Jesus

---

## Algoritmos Implementados

- Bubble Sort
- Heap Sort

---

## Estrutura do Repositório

```
Benchmark de Ordenação/
├── BubbleSortBenchmark.java
├── HeapSortBenchmark.java
├── Relatorio_Benchmark_Ordenacao.pdf
└── README.md
```

---

## Como Executar

### Pré-requisitos

- Java JDK 17 ou superior
- Terminal (cmd, PowerShell ou Git Bash)

### Compilar

```bash
cd "Benchmark de Ordenação"
javac BubbleSortBenchmark.java
javac HeapSortBenchmark.java
```

### Executar

```bash
java BubbleSortBenchmark
java HeapSortBenchmark
```

---

## O que os programas avaliam

- Melhor caso (array ordenado)
- Caso médio (valores aleatórios)
- Pior caso (array invertido)

---

## Configuração dos testes

- Tamanhos: 100, 500, 1000, 5000
- Execuções: 30 por cenário
- Métricas:
  - Tempo de execução
  - Comparações
  - Trocas

---

## Complexidade

| Algoritmo   | Melhor     | Médio      | Pior       |
| ----------- | ---------- | ---------- | ---------- |
| Bubble Sort | O(n)       | O(n²)      | O(n²)      |
| Heap Sort   | O(n log n) | O(n log n) | O(n log n) |

---

## Relatório

O relatório completo está disponível no arquivo:

Relatorio_Benchmark_Ordenacao.pdf

---

## Autores

- Kayky Kmita
- Jhonatan Rulian Roth
