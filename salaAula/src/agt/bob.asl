// Agent bob in project salaAula

/* Initial beliefs and rules */


/* Initial goals */

!start.

/* Plans */

+!start : true 
    <- .print("eai galerinha").

+quemNaSala[source(Agente)] : true
    <- .print("to aqui");
        .send(Agente, tell, presente(bob)).

+!pegarChave[source(Agente)] : true
    <- .print(Agente, " pediu para eu pegar a chave do laboraorio");
        pegarChaveAmbiente.
