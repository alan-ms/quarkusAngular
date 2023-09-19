<div align="center">
  
# Quarkus - Angular

</div>

## Instalação

### Requisitos Técnicos
  - [Git](https://git-scm.com/)
  - [Docker](https://www.docker.com/get-started)
  - [Docker Compose](https://docs.docker.com/compose/install/)

### Clone do repositório
```
$ git clone https://github.com/alan-ms/quarkusAngular.git
$ cd quarkusAngular
```

### Rodando a aplicação via Docker Compose 

```
  $ docker-compose up --build
```

Aplicação vai estar disponível para uso após o Docker Compose rodar a aplicação na url: [http://localhost](http://localhost)

**Obs:** Nos testes locais ocorreu um problema de memória ram ao subir o ambiente via Docker Compose no build do back-end, então sugiro alterar em **settings -> preferences** do docker a quantidade de memória RAM disponível, passando de preferência para 8GB evitando assim qualquer problema para rodar a aplicação.

## História de Usuário

<p>
   Eu como usuário quero poder digitar código do país e obter os seus indicadores de probreza ordenados por ano.  
</p>

## Critérios de Aceitação - BDD

### Cenário 1
  - Cenário: Usuário digita o código do país
  - Dado um código de país correto obtido pelas sugestões apresentadas
  - E clica no botão de busca
  - Quando solita a busca dos indicadores ordenados por ano
  - Então mostra os indicadores por ano ordenados

### Cenário 2
  - Cenário: Usuário digita o código do país
  - Dado um código de país incorreto não disponível nas sugestões de input
  - Quando tenta fazer a busca ao clicar no botão de busca
  - Então aplicação não permite a ação bloqueando o botão

### Cenário 3
  - Cenário: Usuário digita o código do país
  - Dado um código de país correto
  - E clica no botão de busca
  - Quando solita a busca dos indicadores ordenados por ano
  - E ocorreu algum problema de conexão
  - Então mostra a mensagem de erro pedido a busca novamente do indicador
  
