from cromossomo import Cromossomo

estado_final = '123456789'
tamanho_populacao = int(input('Tamanho da populacao: '))
quantidade_geracoes = int(input('Numero de geracoes: '))
taxa_selecao = int(input('Taxa de selecao [25 a 35]: '))
taxa_reproducao = 100 - taxa_selecao
taxa_mutacao = int(input('Taxa de mutacao: '))

populacao = list()
nova_populacao = list()

#Gerar os estados aleatorios
Cromossomo.gerar_populacao(populacao, tamanho_populacao, estado_final)
populacao.sort(key=lambda cromossomo: cromossomo.aptidao)
Cromossomo.exibir_populacao(populacao, 0)

for i in range(1, quantidade_geracoes):
    Cromossomo.selecionar(populacao, nova_populacao, taxa_selecao)
    Cromossomo.reproduzir(populacao, nova_populacao, taxa_reproducao, estado_final)

    if i % taxa_mutacao == 0:
        Cromossomo.mutar(nova_populacao, estado_final)

    populacao.clear()
    populacao.extend(nova_populacao)
    nova_populacao.clear()
    populacao.sort(key=lambda cromossomo: cromossomo.aptidao)
    Cromossomo.exibir_populacao(populacao, i)
