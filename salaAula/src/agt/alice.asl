// Agent alice in project salaAula

/* Initial beliefs and rules */


/* Initial goals */
!start.

/* Plans */

+!start : true
    <- .print("cheguei em sala").
        
+quemNaSala[source(Agente)] : true
    <- .print("eu estou presente");
        .wait(3000);
        .send(Agente, tell, presente(alice)).

+!pegarChave[source(Agente)] : true
    <- .print(Agente, " pediu para eu pegar a chave do laboraorio");
        pegarChaveAmbiente.
