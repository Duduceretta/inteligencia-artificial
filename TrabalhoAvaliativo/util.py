import random

class Util:
    numeros = '123456789'
    tamanho = len(numeros)

    @staticmethod
    def gerar_rota(n):
        rota = ''

        for i in range(n):
            rota += Util.numeros[random.randrange(Util.tamanho)]

        return rota