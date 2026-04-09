# Benchmark de Ordenação – Projeto III

Disciplina: Estruturas, Pesquisa e Ordenação de Dados – 2026/1  
Prof. MSc. Gabriel Passos de Jesus  

---

## Algoritmos Implementados

- **Bubble Sort** (Kayky Kmita)

---

## Estrutura do Repositório

```
Benchmark de Ordenação/
├── BubbleSortBenchmark.java   # Implementação e benchmark do Bubble Sort
└── README.md
```

---

## Como Executar

### Pré-requisitos
- Java JDK 17 ou superior instalado
- Terminal (cmd, PowerShell ou Git Bash)

### Compilar

```bash
cd "Benchmark de Ordenação"
javac BubbleSortBenchmark.java
```

### Executar

```bash
java BubbleSortBenchmark
```

---

## O que o programa avalia

| Cenário | Descrição | Complexidade Teórica |
|---|---|---|
| Melhor caso | Array já ordenado crescentemente | O(n) |
| Caso médio | Array com elementos aleatórios | O(n²) |
| Pior caso | Array ordenado decrescentemente | O(n²) |

- Tamanhos testados: **n = 100, 500, 1000 e 5000**
- Repetições por experimento: **30 execuções**
- Métricas: tempo de execução (ms), comparações e trocas
- Análise estatística: média, desvio padrão, mínimo e máximo

---

## Exemplo de Saída

```
══════════════════════════════════════════════════
  TAMANHO DO ARRAY: 1000
══════════════════════════════════════════════════

┌─────────────────────────────────────────────────┐
│  MELHOR CASO  (array já ordenado) (n = 1000, 30 execuções)
├─────────────────────────────────────────────────┤
│  Tempo médio:            0,0200 ms
│  Desvio padrão:          0,0050 ms
│  Comparações (prática):                     999
│  Comparações (teoria):                      999  O(n)
│  Trocas médias:                               0
└─────────────────────────────────────────────────┘
```

---

## Relatório Técnico

O relatório completo em PDF está disponível em:  
`Relatorio_Benchmark_Ordenacao.pdf`

Contém: Introdução, Fundamentação Teórica, Análise Assintótica, Metodologia Experimental, Resultados, Discussão e Conclusão.
