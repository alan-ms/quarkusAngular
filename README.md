<div align="center">
  # Quarkus - Angular
<div>

## Instalação
  - [Git](https://git-scm.com/)
  - [Docker](https://www.docker.com/get-started)
  - [Docker-Compose](https://docs.docker.com/compose/install/)

## Clone do repositório
```
$ git clone 
$ cd apiQuarkus
```

## Rodar rodar o ambiente 

```
  $ docker-compose up --build
```

## História de Usuário

<p>
   Eu como usuário quero poder digitar código do país e obter os seus indicadores de probreza ordenados por ano.  
</p>

## Critérios de Aceitação - Usando BDD (B)

### Cenário 1
  Cenário: Usuário digita o código do país
  Dado um código de país correto obtido pelas sugestões apresentadas
  E clica no botão de busca
  Quando solita a busca dos indicadores ordenados por ano
  Então mostra os indicadores por ano ordenados

### Cenário 2
  Cenário: Usuário digita o código do país
  Dado um código de país incorreto não disponível nas sugestões de input
  Quando tenta fazer a busca ao clicar no botão de busca
  Então aplicação não permite a ação bloqueando o botão

### Cenário 3
  Cenário: Usuário digita o código do país
  Dado um código de país correto
  E clica no botão de busca
  Quando solita a busca dos indicadores ordenados por ano
  E ocorreu algum problema de conexão
  Então mostra a mensagem de erro pedido a busca novamente do indicador
  