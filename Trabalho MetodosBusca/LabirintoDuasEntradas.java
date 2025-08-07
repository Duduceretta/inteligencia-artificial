import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import busca.BuscaLargura;
import busca.BuscaProfundidade;
import busca.Estado;
import busca.MostraStatusConsole;
import busca.Nodo;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

public class LabirintoDuasEntradas implements Estado {
    final char matriz[][];
    int linhaEntrada1, colunaEntrada1;
    int linhaEntrada2, colunaEntrada2;
    int linhaSaida, colunaSaida;
    final String op;
    final boolean usarEntrada1; // controlar entrada

    char [][]clonar(char origem[][]) {
        char destino[][] = new char[origem.length][origem[0].length]; 
        for (int i = 0; i < origem.length; i++) {
            for (int j = 0; j < origem[i].length; j++) {
                destino[i][j] = origem[i][j];
            }
        }
        return destino;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        LabirintoDuasEntradas outro = (LabirintoDuasEntradas) obj;
        return this.linhaEntrada1 == outro.linhaEntrada1 &&
               this.colunaEntrada1 == outro.colunaEntrada1 &&
               this.linhaEntrada2 == outro.linhaEntrada2 &&
               this.colunaEntrada2 == outro.colunaEntrada2 &&
               this.linhaSaida == outro.linhaSaida &&
               this.colunaSaida == outro.colunaSaida &&
               this.usarEntrada1 == outro.usarEntrada1 &&
               java.util.Arrays.deepEquals(this.matriz, outro.matriz);
    }

    @Override
    public int hashCode() {
        int resultado = java.util.Objects.hash(linhaEntrada1, colunaEntrada1, linhaEntrada2, colunaEntrada2, linhaSaida, colunaSaida, usarEntrada1);
        resultado = 31 * resultado + java.util.Arrays.deepHashCode(matriz);
        return resultado;
    }
    
    public LabirintoDuasEntradas(char m[][], int lE1, int cE1, int lE2, int cE2, int lS, int cS, String o, boolean usarEntrada1) {
        this.matriz = m;
        this.linhaEntrada1 = lE1;
        this.colunaEntrada1 = cE1;
        this.linhaEntrada2 = lE2;
        this.colunaEntrada2 = cE2;
        this.linhaSaida = lS;
        this.colunaSaida = cS;
        this.op = o;
        this.usarEntrada1 = usarEntrada1;
    }

    public LabirintoDuasEntradas(int dimensao, String o, int porcentagemObstaculos, boolean usarEntrada1) {
        this.matriz = new char[dimensao][dimensao];
        this.op = o;
        this.usarEntrada1 = usarEntrada1;
        
        Random gerador = new Random();
        int entrada1 = gerador.nextInt(dimensao * dimensao);
        int entrada2;
        do {
            entrada2 = gerador.nextInt(dimensao * dimensao);
        } while (entrada2 == entrada1);

        int saida;
        do {
            saida = gerador.nextInt(dimensao * dimensao);
        } while (saida == entrada1 || saida == entrada2);

        int contaPosicoes = 0;
        for (int i = 0; i < dimensao; i++) {
            for (int j = 0; j < dimensao; j++) {
                if (contaPosicoes == entrada1) {
                    this.matriz[i][j] = '1';
                    this.linhaEntrada1 = i;
                    this.colunaEntrada1 = j;
                } else if (contaPosicoes == entrada2) {
                    this.matriz[i][j] = '2';
                    this.linhaEntrada2 = i;
                    this.colunaEntrada2 = j;
                } else if (contaPosicoes == saida) {
                    this.matriz[i][j] = 'S';
                    this.linhaSaida = i;
                    this.colunaSaida = j;
                } else if (gerador.nextInt(100) < porcentagemObstaculos) {
                    this.matriz[i][j] = '@';
                } else {
                    this.matriz[i][j] = 'O';
                }
                contaPosicoes++;
            }
        }
    }
    
    @Override
    public boolean ehMeta() {
        if (usarEntrada1) {
            return this.linhaEntrada1 == this.linhaSaida && this.colunaEntrada1 == this.colunaSaida;
        } else {
            return this.linhaEntrada2 == this.linhaSaida && this.colunaEntrada2 == this.colunaSaida;
        }
    }

    @Override
    public int custo() {
        return 1;
    }

    public List<Estado> sucessores() {
        List<Estado> sucessores = new LinkedList<>();
        Set<String> visitados = new HashSet<>();

        // se a entrada atual ja estiver na saida, nao gera mais sucessores
        if ((usarEntrada1 && linhaEntrada1 == linhaSaida && colunaEntrada1 == colunaSaida) ||
            (!usarEntrada1 && linhaEntrada2 == linhaSaida && colunaEntrada2 == colunaSaida)) {
            return sucessores; // retorna lista vazia
        }

        adicionarSucessor(visitados, sucessores, paraCima());
        adicionarSucessor(visitados, sucessores, paraBaixo());
        adicionarSucessor(visitados, sucessores, paraEsquerda());
        adicionarSucessor(visitados, sucessores, paraDireita());

        return sucessores;
    }

    private void adicionarSucessor(Set<String> visitados, List<Estado> sucessores, LabirintoDuasEntradas novoEstado) {
        if (novoEstado != null && !visitados.contains(novoEstado.toString())) {
            visitados.add(novoEstado.toString());
            sucessores.add(novoEstado);
        }
    }
    
    private LabirintoDuasEntradas paraCima() {
        if (usarEntrada1) {
            if (linhaEntrada1 > 0 && matriz[linhaEntrada1 - 1][colunaEntrada1] != '@') {
                char[][] novaMatriz = clonar(matriz);
                novaMatriz[linhaEntrada1][colunaEntrada1] = 'O'; 
                novaMatriz[linhaEntrada1 - 1][colunaEntrada1] = '1';

                return new LabirintoDuasEntradas(novaMatriz, linhaEntrada1 - 1, colunaEntrada1, linhaEntrada2, colunaEntrada2, linhaSaida, colunaSaida, op, usarEntrada1);
            }
        } else {
            if (linhaEntrada2 > 0 && matriz[linhaEntrada2 - 1][colunaEntrada2] != '@') {
                char[][] novaMatriz = clonar(matriz);
                novaMatriz[linhaEntrada2][colunaEntrada2] = 'O'; 
                novaMatriz[linhaEntrada2 - 1][colunaEntrada2] = '2';

                return new LabirintoDuasEntradas(novaMatriz, linhaEntrada1, colunaEntrada1, linhaEntrada2 - 1, colunaEntrada2, linhaSaida, colunaSaida, op, usarEntrada1);
            }
        }
        return null;
    }
    
    private LabirintoDuasEntradas paraBaixo() {
        if (usarEntrada1) {
            if (linhaEntrada1 < matriz.length - 1 && matriz[linhaEntrada1 + 1][colunaEntrada1] != '@') {
                char[][] novaMatriz = clonar(matriz);
                novaMatriz[linhaEntrada1][colunaEntrada1] = 'O';
                novaMatriz[linhaEntrada1 + 1][colunaEntrada1] = '1';

                return new LabirintoDuasEntradas(novaMatriz, linhaEntrada1 + 1, colunaEntrada1, linhaEntrada2, colunaEntrada2, linhaSaida, colunaSaida, op, usarEntrada1);
            }
        } else {
            if (linhaEntrada2 < matriz.length - 1 && matriz[linhaEntrada2 + 1][colunaEntrada2] != '@') {
                char[][] novaMatriz = clonar(matriz);
                novaMatriz[linhaEntrada2][colunaEntrada2] = 'O';
                novaMatriz[linhaEntrada2 + 1][colunaEntrada2] = '2';

                return new LabirintoDuasEntradas(novaMatriz, linhaEntrada1, colunaEntrada1, linhaEntrada2 + 1, colunaEntrada2, linhaSaida, colunaSaida, op, usarEntrada1);
            }
        }
        return null;
    }

    private LabirintoDuasEntradas paraEsquerda() {
        if (usarEntrada1) {
            if (colunaEntrada1 > 0 && matriz[linhaEntrada1][colunaEntrada1 - 1] != '@') {
                char[][] novaMatriz = clonar(matriz);
                novaMatriz[linhaEntrada1][colunaEntrada1] = 'O';
                novaMatriz[linhaEntrada1][colunaEntrada1 - 1] = '1';

                return new LabirintoDuasEntradas(novaMatriz, linhaEntrada1, colunaEntrada1 - 1, linhaEntrada2, colunaEntrada2, linhaSaida, colunaSaida, op, usarEntrada1);
            }
        } else {
            if (colunaEntrada2 > 0 && matriz[linhaEntrada2][colunaEntrada2 - 1] != '@') {
                char[][] novaMatriz = clonar(matriz);
                novaMatriz[linhaEntrada2][colunaEntrada2] = 'O';
                novaMatriz[linhaEntrada2][colunaEntrada2 - 1] = '2';

                return new LabirintoDuasEntradas(novaMatriz, linhaEntrada1, colunaEntrada1, linhaEntrada2, colunaEntrada2 - 1, linhaSaida, colunaSaida, op, usarEntrada1);
            }
        }
        return null;
    }

    private LabirintoDuasEntradas paraDireita() {
        if (usarEntrada1) {
            if (colunaEntrada1 < matriz[0].length - 1 && matriz[linhaEntrada1][colunaEntrada1 + 1] != '@') {
                char[][] novaMatriz = clonar(matriz);
                novaMatriz[linhaEntrada1][colunaEntrada1] = 'O';
                novaMatriz[linhaEntrada1][colunaEntrada1 + 1] = '1';

                return new LabirintoDuasEntradas(novaMatriz, linhaEntrada1, colunaEntrada1 + 1, linhaEntrada2, colunaEntrada2, linhaSaida, colunaSaida, op, usarEntrada1);
            }
        } else {
            if (colunaEntrada2 < matriz[0].length - 1 && matriz[linhaEntrada2][colunaEntrada2 + 1] != '@') {
                char[][] novaMatriz = clonar(matriz);
                novaMatriz[linhaEntrada2][colunaEntrada2] = 'O';
                novaMatriz[linhaEntrada2][colunaEntrada2 + 1] = '2';

                return new LabirintoDuasEntradas(novaMatriz, linhaEntrada1, colunaEntrada1, linhaEntrada2, colunaEntrada2 + 1, linhaSaida, colunaSaida, op, usarEntrada1);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                resultado.append(this.matriz[i][j]).append("\t");
            }
            resultado.append("\n");
        }
        resultado.append("\n--- Estado Atual ---\n");
        resultado.append("Entrada 1: (").append(linhaEntrada1).append(", ").append(colunaEntrada1).append(")\n");
        resultado.append("Entrada 2: (").append(linhaEntrada2).append(", ").append(colunaEntrada2).append(")\n");
        resultado.append("Saida: (").append(linhaSaida).append(", ").append(colunaSaida).append(")\n");
        resultado.append("Usando Entrada: ").append(usarEntrada1 ? "1" : "2").append("\n");
        resultado.append("--------------------\n");

        return resultado.toString();
    }

    public static void main(String[] a) {
        LabirintoDuasEntradas estadoInicial;
        int dimensao, porcentagemObstaculos;
        try {
            dimensao = Integer.parseInt(JOptionPane.showInputDialog("Dimensao do labirinto:"));
            porcentagemObstaculos = Integer.parseInt(JOptionPane.showInputDialog("Porcentagem de obstaculos:"));
            
            // cria o estado inicial
            estadoInicial = new LabirintoDuasEntradas(dimensao, "estado inicial", porcentagemObstaculos, true);
            
            System.out.println(estadoInicial);
            
            System.out.println("Buscando solucao para Entrada 1 usando Profundidade...");
            Nodo n1 = new BuscaProfundidade(new MostraStatusConsole()).busca(estadoInicial);
            if (n1 != null) {
                System.out.println("Solucao Entrada 1:");
                System.out.println(n1.montaCaminho());
            } else {
                System.out.println("Sem solucao para Entrada 1.");
            }
            
            // cria uma copia do estado inicial
            LabirintoDuasEntradas estadoInicialCopia = new LabirintoDuasEntradas(
                estadoInicial.matriz, 
                estadoInicial.linhaEntrada1, 
                estadoInicial.colunaEntrada1, 
                estadoInicial.linhaEntrada2, 
                estadoInicial.colunaEntrada2, 
                estadoInicial.linhaSaida, 
                estadoInicial.colunaSaida, 
                "estado inicial", 
                false  // entrada 2
            );
            
            System.out.println("Buscando solucao para Entrada 2 usando Largura...");
            Nodo n2 = new BuscaLargura(new MostraStatusConsole()).busca(estadoInicialCopia);
            if (n2 != null) {
                System.out.println("Solucao Entrada 2:");
                System.out.println(n2.montaCaminho());
            } else {
                System.out.println("Sem solucao para Entrada 2.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        System.exit(0);
    }

    @Override
    public String getDescricao() {
        return "Labirinto com duas entradas e uma saida.";
    }
}
