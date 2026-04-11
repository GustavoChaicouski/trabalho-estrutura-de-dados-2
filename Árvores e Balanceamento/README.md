# Projeto: Implementação e Benchmark de Árvores de Busca Balanceadas

## 1. Descrição do Projeto
Este projeto tem como objetivo a implementação manual e a análise de desempenho de três diferentes estruturas de dados do tipo árvore. A finalidade é comparar o comportamento assintótico e o tempo de execução (benchmark) dessas estruturas sob diferentes cenários de inserção, busca e remoção.

As seguintes estruturas de dados foram implementadas:
- **Árvore Binária de Busca (BST - Binary Search Tree)**
- **Árvore AVL** (Adelson-Velsky e Landis)
- **Árvore Rubro-Negra** (Red-Black Tree)

## 2. Pré-requisitos
Para compilar e executar este projeto, não é necessário o uso de bibliotecas externas ou gerenciadores de dependência (como Maven ou Gradle). O projeto utiliza apenas os recursos nativos da linguagem.

- **Java Development Kit (JDK):** Versão 17 ou superior.
- Terminal ou Prompt de Comando configurado com as variáveis de ambiente para `java` e `javac`.

Para verificar sua versão do Java, execute no terminal:
`java -version`

## 3. e 4. Instruções de Compilação e Execução
Como o projeto não utiliza ferramentas de automação de build, a compilação e a execução devem ser feitas manualmente via terminal. Abaixo, são apresentadas duas formas de execução, dependendo de como os arquivos foram organizados.

### Opção 1: Caso Simples (Todos os arquivos na mesma pasta)
Utilize esta opção se todos os arquivos `.java` (nós, árvores e benchmarks) estiverem no mesmo diretório base, sem declaração de `package`.

**Compilação:**
`javac *.java`

**Execução dos Benchmarks:**
- Para executar o benchmark da Árvore Binária de Busca:
  `java BenchmarkBST`
- Para executar o benchmark da Árvore AVL:
  `java BenchmarkAVL`
- Para executar o benchmark da Árvore Rubro-Negra:
  `java BenchmarkRubroNegra`

### Opção 2: Caso com Pacotes (Arquivos organizados em diretórios)
Utilize esta opção se o código fonte estiver organizado nas pastas `bst`, `avl` e `rubronegra`, e os arquivos possuírem a declaração de pacote correspondente no topo (ex: `package bst;`). O terminal deve estar aberto na pasta raiz do projeto (a pasta que contém os subdiretórios).

**Compilação:**
`javac bst/*.java`
`javac avl/*.java`
`javac rubronegra/*.java`

**Execução dos Benchmarks:**
- Para executar o benchmark da Árvore Binária de Busca:
  `java bst.BenchmarkBST`
- Para executar o benchmark da Árvore AVL:
  `java avl.BenchmarkAVL`
- Para executar o benchmark da Árvore Rubro-Negra:
  `java rubronegra.BenchmarkRubroNegra`

## 5. Metodologia do Benchmark
Cada classe de benchmark realiza um experimento quantitativo focado em medir o tempo gasto nas operações fundamentais (Inserção, Busca e Remoção). 

A metodologia obedece às seguintes regras:
- **Total de Execuções:** O experimento é repetido 30 vezes para cada tamanho de conjunto de dados (N). As primeiras 5 execuções de cada ciclo são ignoradas (warm-up) para evitar distorções causadas pela compilação JIT (Just-In-Time) da JVM.
- **Métricas:** Para as execuções válidas, são calculados e exibidos a **Média** (em milissegundos) e o **Desvio Padrão**, garantindo validade estatística aos resultados.
- **Cenários Analisados:**
  - **Cenário A (Dados Aleatórios):** Os elementos são inseridos e manipulados em ordem aleatória (simulando o caso médio).
  - **Cenário B (Dados Ordenados):** Os elementos são inseridos em ordem estritamente crescente (simulando o pior caso para árvores não balanceadas).

## 6. Exemplo de Saída no Terminal
Ao executar qualquer um dos benchmarks, o console exibirá um relatório semelhante ao modelo abaixo:

```text
=======================================================
  CENÁRIO A: INSERÇÃO COM DADOS ALEATÓRIOS (CASO MÉDIO)
=======================================================

-> Invocando volume de dados: 100000
   [Inserção] Média:  12.4501 ms | Desvio Padrão:   1.2034 ms
   [Busca   ] Média:   2.1030 ms | Desvio Padrão:   0.3401 ms
   [Remoção ] Média:   4.5602 ms | Desvio Padrão:   0.8900 ms
   Altura final da árvore (após extrações): 21

=======================================================
  CENÁRIO B: INSERÇÃO ORDENADA (PIOR CASO / ROTAÇÕES)  
=======================================================

-> Invocando volume de dados: 100000
   [Inserção] Média:  18.9021 ms | Desvio Padrão:   2.1004 ms
   [Busca   ] Média:   1.9500 ms | Desvio Padrão:   0.2100 ms
   [Remoção ] Média:   5.1023 ms | Desvio Padrão:   0.9012 ms
   Altura final da árvore (após extrações): 16


```
Árvore Binária de Busca (BST): No Cenário B (dados ordenados), a BST sofre degradação severa, transformando-se em uma lista encadeada (complexidade O(n) de tempo e espaço de pilha). Isso pode resultar em erros de StackOverflowError para valores altos de N devido à recursão profunda.

Árvore AVL: Garante altura rigidamente controlada (fator de balanceamento máximo de 1). Devido a isso, a Busca é extremamente rápida, porém o custo de Inserção e Remoção é levemente penalizado pelo alto número de rotações exigidas para manter o equilíbrio perfeito.

Árvore Rubro-Negra: Oferece um relaxamento no balanceamento (o caminho mais longo não é maior que o dobro do caminho mais curto). Resulta em menos rotações durante a inserção e remoção se comparada à AVL, tornando-se mais performática em cenários de gravação intensiva, mantendo a complexidade de altura garantida de O(log n).
