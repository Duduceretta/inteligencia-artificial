import random

class Util:
    numeros = '123456789'
    tamanho = len(numeros)

    @staticmethod
    def gerar_rota(n):
        """ -> Gera uma rota aleatoria com os numeros '123456789'
        de temanho 9
        Args:
            n (int): tamanho da rota
        Returns:
            String: retorna uma rota aleatoria
        """
        rota = ''

        for i in range(n):
            rota += Util.numeros[random.randrange(Util.tamanho)]

        return rota
    