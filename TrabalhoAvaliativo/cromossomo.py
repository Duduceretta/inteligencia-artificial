import random
from util import Util


class Cromossomo:
    def __init__(self, rota, estado_final):
        self.rota = rota
        self.aptidao = self.calcular_aptidao(estado_final)


    def calcular_aptidao(self, estado_final):
        nota = 0

        #Verifica se o numero é maior que o resto (ordem crescente)
        for i in range(len(self.rota)):
            for j in range(i + 1, len(self.rota)):
                if self.rota[i] > self.rota[j]:
                    nota += 10

        #Verifica se há pares de numeros repetidos
        for i in range(1, len(self.rota) + 1):
            if str(i) in self.rota and self.rota.count(str(i)) > 1:
                pares_de_i = self.rota.count(str(i)) // 2
                nota += 20 * pares_de_i
        
        return nota

    
    def __str__(self):
        return f'{self.rota} - {self.aptidao}'


    def __eq__(self, other):
        if isinstance(other, Cromossomo):
            return self.rota == other.rota
        return False


    @staticmethod
    def gerar_populacao(populacao, tamanho_populacao, estado_final):
        for i in range(tamanho_populacao):
            rota_gerada = Util.gerar_rota(len(estado_final))
            individuo = Cromossomo(rota_gerada, estado_final)
            populacao.append(individuo)

    
    @staticmethod
    def exibir_populacao(populacao, numero_geracao):
        print(f'Geracao numero: {numero_geracao}')
        for individuo in populacao:
            print(individuo)


    @staticmethod
    def selecionar(populacao, nova_populacao, taxa_de_selecao):
        #Definir quantos serao selecionados
        #len(populacao)             - 100
        #quantidade_selecionados    - taxa_selecao
        quantidade_selecionados = int(len(populacao) * taxa_de_selecao / 100)

        torneio = list()

        #Elitismo - o mais apto SEMPRE é selecionado
        nova_populacao.append(populacao[0])

        i = 1
        while i < quantidade_selecionados:
            c1 = populacao[random.randrange(len(populacao))]

            while True:
                c2 = populacao[random.randrange(len(populacao))]
                if not c1.__eq__(c2):
                    break
            
            while True:
                c3 = populacao[random.randrange(len(populacao))]
                if not c1.__eq__(c3) and not c2.__eq__(c3):
                    break

            torneio.append(c1)
            torneio.append(c2)
            torneio.append(c3)

            torneio.sort(key=lambda cromossomo: cromossomo.aptidao)

            #Pega o mais apto dos 3 selecionados
            selecionado = torneio[0]

            #Verifica se ele nao esta na nova_populacao
            if selecionado not in nova_populacao:
                nova_populacao.append(selecionado)
                i += 1

            torneio.clear()

        print(f'Qtd selecionados: {quantidade_selecionados}')


    @staticmethod
    def reproduzir(populacao, nova_populacao, taxa_reproducao, estado_final):
        #Definir a quantidade de reproduzidos
        #len(populacao)             - 100
        #quantidade_reproduzidos    - taxa_reproducao
        quantidade_reproduzidos = int(len(populacao) * taxa_reproducao / 100)

        for i in range(int(quantidade_reproduzidos / 2) + 1):
            #Sorteia um pai entre os primeiros 20% da populacao (Mais aptos)
            cromossomo_pai = populacao[random.randrange(len(populacao))]

            while True:
                cromossomo_mae = populacao[random.randrange(len(populacao))]
                if not cromossomo_pai.__eq__(cromossomo_mae):
                    break
            
            #Pega as rotas de cada cromossomo
            pai = cromossomo_pai.rota
            mae = cromossomo_mae.rota

            primeira_metade_pai = pai[0 : int(len(pai) / 2)]
            segunda_metade_pai = pai[int(len(pai) / 2) : len(pai)]

            primeira_metade_mae = mae[0 : int(len(mae) / 2)]
            segunda_metade_mae = mae[int(len(mae) / 2) : len(mae)]

            filho1 = primeira_metade_pai + segunda_metade_mae
            filho2 = primeira_metade_mae + segunda_metade_pai

            nova_populacao.append(Cromossomo(filho1, estado_final))
            nova_populacao.append(Cromossomo(filho2, estado_final))

            #Podar os excedentes
            while len(nova_populacao) > len(populacao):
                #Remove o utlimo elemento
                nova_populacao.pop()


    @staticmethod
    def mutar(populacao, estado_final):
        #Quantidade que vai mutar
        quantidade_mutantes = random.randrange(int(len(populacao)))
        while quantidade_mutantes > 0:
            #Escolhe da populacao quem vai mutar
            posicao_mutante = random.randrange(int(len(populacao)))
            mutante = populacao[posicao_mutante]

            print(f'Vai mutar {mutante}')

            #Mutando
            palavra_mutada = mutante.rota

            #Pega um caractere da rota do mutante Ex: rota = '3642' caractere = '6'
            caractere_mutante = mutante.rota[random.randrange(len(mutante.rota))] 
            #Sorteia um novo caractere entre os numeros possiveis '123456789'
            carctere_sorteado = Util.numeros[random.randrange(Util.tamanho)]
            #Mudo na palavra o caractere mutante pelo caractere sorteado
            palavra_mutada = palavra_mutada.replace(caractere_mutante, carctere_sorteado)
            mutante = Cromossomo(palavra_mutada, estado_final)

            #Substituia a rota antiga pela nova rota mutada
            populacao[posicao_mutante] = mutante
            quantidade_mutantes -= 1

