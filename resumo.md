Inteligencia Artificial
    - Subárea da Ciencia da Computacao: envolve matematica, linguistica, sociologia...
    - Elementos basicos da IA para construcao de sistemas com comportamento inteligente:
        - base de conhecimento - texto (Formal - xmol, json, yaml; Informal - texto puro)
        - raciocínio automatizado - dedução e indução
        - aprendizado de máquina -> reconhecer padrões -> supervisionado ou não supervisionado

    - Representação do conhecimento poara bases de conhecimento + Raciocinio automatizado por força bruta (profundidade)

    -> Linguagem Prolog -> paradigma de lógica

    %fatos ou verdades
    nasceuEm("Alexandre","Cruz Alta").
    nasceuEm("Cristina","Santa Maria").
    nasceuEm("Dante","Santa Maria").
    nasceuEm("Gabriela","Porto Alegre").
    nasceuEm("Tales","Montreal").
    nasceuEm("Jurandir","Pejuçara").
    nasceuEm("Clara","Florianopolis").
    nasceuEm("Clarissa","Cruz Alta").
    %regra recursiva de sobrecarga
    nasceuEm(Pessoa, Lugar):- 
        nasceuEm(Pessoa, I),
        estaEm(I, Lugar).

    localizadoEm("Florianopolis","SC").
    localizadoEm("Pejuçara","RS").
    localizadoEm("Cruz Alta","RS").
    localizadoEm("Santa Maria","RS").
    localizadoEm("Porto Alegre","RS").
    localizadoEm("RS","Brasil").
    localizadoEm("SC","Brasil").
    localizadoEm("Montreal","Quebec").
    localizadoEm("Quebec","Canada").

    %regra recursiva
    estaEm(Lugar, OutroLugar) :-
        localizadoEm(Lugar, OutroLugar).
    estaEm(Lugar, OutroLugar) :-
        localizadoEm(Lugar, I),
        estaEm(I, OutroLugar).

    progenitor("Jurandir","Alexandre").
    progenitor("Jurandir","Cristina").
    progenitor("Jurandir","Clarissa").
    progenitor("Clarissa","Clara").

    avo(Avo, Neta) :-
        progenitor(Avo, Pai),
        progenitor(Pai, Neta).

    irmao(I1, I2) :-
        progenitor(Pai,I1),
        progenitor(Pai,I2),
        I1 \= I2.

    tio(Tio,Sobrinho) :-
        irmao(Tio,Pai),
        progenitor(Pai,Sobrinho).


    %fatos ou verdades
    nasceuEm("Eduardo","Santa Maria").
    nasceuEm("Angela","Santa Maria").
    nasceuEm("Mateus","Polesine").
    nasceuEm("Irma","Polesine").
    nasceuEm("Vilma","Santa Maria").
    nasceuEm("Solange","Santa Maria").
    nasceuEm("Vicente","Polesine").
    nasceuEm("Silvio","Polesine").

    %regra recursiva de sobrecarga
    nasceuEm(Pessoa, Lugar):- 
        nasceuEm(Pessoa, I),
        estaEm(I, Lugar).

    localizadoEm("Polesine","RS").
    localizadoEm("Santa Maria","RS").
    localizadoEm("RS","Brasil").

    %regra recursiva
    estaEm(Lugar, OutroLugar) :-
        localizadoEm(Lugar, OutroLugar).
    estaEm(Lugar, OutroLugar) :-
        localizadoEm(Lugar, I),
        estaEm(I, OutroLugar).

    progenitor("Angela","Eduardo").
    progenitor("Mateus","Eduardo").
    progenitor("Irma","Mateus").
    progenitor("Irma","Vicente").
    progenitor("Irma","Silvio").
    progenitor("Vilma","Angela").
    progenitor("Vilma","Solange").
    progenitor("Vicente", "Vanessa")

    avo(Avo, Neta) :-
        progenitor(Avo, Pai),
        progenitor(Pai, Neta).

    irmao(I1, I2) :-
        progenitor(Pai,I1),
        progenitor(Pai,I2),
        I1 \= I2.

    tio(Tio,Sobrinho) :-
        irmao(Tio,Pai),
        progenitor(Pai,Sobrinho).

    primo(P1, P2):-
        progenitor(Pai, P1),
        progenitor(Pai, P2),
    
    
    
    

