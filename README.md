# Produtos Estoque

API REST para controle de estoque de produtos, desenvolvida com Spring Boot. Permite cadastrar produtos, consultar o estoque atual e registrar vendas com baixa automática de quantidade.

## Tecnologias utilizadas

- Java 21
- Spring Boot 4
- Spring Data JPA
- Banco de dados H2 (em memória)
- Maven

## Como executar o projeto

Pré-requisitos: ter o Java 21 e o Maven instalados (ou usar o wrapper incluso no projeto).

```bash
# clonar o repositório
git clone https://github.com/Joaozin54P/Produtos_Estoque.git
cd Produtos_Estoque

# rodar a aplicação
./mvnw spring-boot:run
```

A API sobe por padrão em `http://localhost:8080`.

## Endpoints da API

### Cadastrar produto

```
POST /produtos
Content-Type: application/json

{
  "nome": "Mouse sem fio",
  "quantidade": 10
}
```

### Consultar estoque

```
GET /produtos/estoque
```

Retorna a lista de todos os produtos cadastrados com suas respectivas quantidades.

### Registrar venda

```
POST /venda/{id}
Content-Type: application/json

{
  "quantidade": 2
}
```

Dá baixa na quantidade do produto informado. Caso o estoque seja insuficiente, a API retorna erro `400 Bad Request` com a mensagem explicando o motivo.

## Estrutura do projeto

```
src/main/java/com/example/estoqueproduto
├── config          # configuração de CORS
├── controller      # endpoints REST
├── model           # entidade Produto e classes de requisição
├── repository      # interface JPA para acesso ao banco
└── service         # regras de negócio (cadastro e baixa de estoque)
```

## Autor

- João Pedro Machado
- Giovanna Aparecida