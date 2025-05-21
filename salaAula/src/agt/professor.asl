laboratorioFechado.

/* Initial goals */

!start.

/* Plans */

+!start : true 
    <-  .print("bom dia turma, vou fazer a chamada");
        .broadcast(tell, quemNaSala).

+presente(Aluno) : laboratorioFechado
    <-  .print(Aluno, " pega a chave do laboratorio");
        .send(Aluno, achieve, pegarChave);
        -laboratorioFechado.