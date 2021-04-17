# Dúvidas Bot

<h2> Bot para discord que organiza dúvidas em salas, para facilitar o trabalho em com alunos de forma virtual.</h2>

<h2> Desenvolvido em Java, utilizando Maven para gerenciar as dependências.</h2>

## To-do:
 * Há a possibilidade de adicionar estatísticas, e um painel de informações de cada aluno, suas dificuldades etc.
 * Alunos poderem ajudar outros alunos.
 * Monitores e outros alunos poderem ser convidados a sala de uma dúvida.

## Bibliotecas utilizadas:
  * JDA - [Java Discord API] (https://github.com/DV8FromTheWorld/JDA)
  * Hibernate - Para persistência de dados.
  * SQLite - Como banco de dados.


## Como funciona: 
> + Aluno digita no chat um comando: !duvida {duvida aqui}
O bot automaticamente reage a mensagem com uma mão levantada.<br>
<img src="./examplePics/1.png" width="640" height="360">

> + Para responder a dúvida, você precisa estar com cargo de monitor no servidor.
e basta clicar pra reagir a mensagem de dúvida.<br>
Caso outro usuário responda, nada acontece, pelo menos por enquanto só há essas opções.<br>
<img src="./examplePics/2.png" width="250" height="300">

> + Quando um monitor aceita a dúvida, ela é automaticamente reagida com um emoji simbolizando que já foi respondida.<br>
E é criada uma nova sala privada ao lado, só para o monitor e o aluno.<br>
<img src="./examplePics/3.png" width="640" height="360">

> + A sala é criada, já com essa mensagem que tem a dúvida do aluno para facilitar.<br>
<img src="./examplePics/4.png" width="640" height="360">

> + Você pode conversar, respondendo tudo certinho, e logo após, qualquer um dos dois, o aluno ou o monitor, podem usar o comando !finalizar, que finaliza a sala e conclui o atendimento.<br>
<img src="./examplePics/5.png" width="640" height="360">

> + E pronto, a sala é removida.<br>
<img src="./examplePics/6.png" width="640" height="360">