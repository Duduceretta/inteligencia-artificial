Cenario:
    turno (manha, tarde, noite)
    estacao (primavera, verao, outono e inverno)

    agentes: 
        arCondicionado (temperatura desligada, fria, normal, quente)
        iluminacao (intensidade desligada, normal, fraca, forte)
        cortina (aberta, parcial, fechada)

        alexandre ()
            crenças: 
            primavera, manha, arCondicionado(desligado), iluminacao(desligada), cortina(aberta)
            outono, manha, arCondicionado(desligado), iluminacao(desligada), cortina(aberta)
            verao, manha, arCondicionado(normal), iluminacao(fraca), cortina(fechada)

Tabela de modelagem
agente         | evento ativador (externo) |   contexto (crença)   |   plano       |   atualizacao 
arCondicionado | pessoa na sala            |possui a pessoa na base|acionar ArCond | ligado
                                           |nao atendiPessoa       |conversarPessoa| temperatura
                                                                   |acionarEquip   | atendiPessoa

arCondicionado | pessoa na sala            |possui a pessoa na base|ajustar ArCond  | ligado
                                           |atendiPessoa           |conversarPessoas| temperatura
                                                                   |ajustarTemp     | atendiPessoa

eventoAtivador : contexto
    <-
    chamarOutroPlano;
    .comandoInternoJason;
    acaoNoAmbiente; //Implementacao Java
    +crenca; //adicionar crenca
    -crenca; //remover crenca
    ?crenca. //perguntar crenca

https://jason-lang.github.io/doc/
