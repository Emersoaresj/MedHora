# MedHora - Sistema de Agendamento Médico

**MedHora** é um sistema desenvolvido para gerenciar agendamentos médicos, notificações e comunicação interna, com foco em oferecer uma API RESTful robusta e escalável. O projeto foi desenvolvido como parte do curso de Pós-Graduação em **Arquitetura e Desenvolvimento em JAVA** da **FIAP**.

O sistema permite a criação, atualização e consulta de agendamentos, além de gerenciar notificações associadas, tudo integrado com mensageria Kafka para comunicação assíncrona.

## Tecnologias utilizadas até o momento:

- **Spring Boot**: Framework utilizado para o desenvolvimento da API.
- **Spring Data JPA**: Para acesso simplificado ao banco de dados.
- **Kafka**: Plataforma de mensageria para comunicação entre serviços.
- **Docker**: Utilizado para containerização da aplicação.
- **Java 17+**: Linguagem principal do projeto.

## Objetivo do Projeto

Desenvolver um sistema eficiente para gerenciamento de agendamentos médicos e notificações, com integração via Kafka para comunicação assíncrona, facilitando a organização e controle das operações clínicas.

## Como Executar Localmente

### Requisitos

- **Java 17+**
- **Docker** (para rodar a aplicação e serviços relacionados)
- **Postman** (para testes da API)

### Passos para Execução

1. Clone o repositório:
   ```bash
   git clone https://github.com/Emersoaresj/MedHora.git
   cd MedHora

2. **Suba os containers Docker**:
   - O arquivo `docker-compose.yml` está configurado para rodar a aplicação e o PostgreSQL. Execute o seguinte comando:
     ```bash
     docker-compose up --build
     ```

3. A aplicação estará disponível em `http://localhost:8080` e a documentação do Swagger estará disponível em `http://localhost:8080/swagger-ui/index.html#/`.

4. O banco de dados estará disponível em `localhost:5432` com o usuário e senha configurados no `docker-compose.yml`.

5. **Testando a API com o Postman**:
   - Leia a documentação a partir do link:
[Link para a documentação da Collection do Postman](https://github.com/Emersoaresj/collections-MedHora/blob/main/README.md)
   - Você pode importar a coleção do Postman a partir do link abaixo para testar todos os endpoints da API.
[Link para a Collection do Postman](https://github.com/Emersoaresj/collections-MedHora/blob/main/MedHora.postman_collection.json)

### Endpoints Principais

## Endpoints Principais

### Agendamento
- **GET** `/agendamentos` - Listar todos os agendamentos.
- **POST** `/agendamentos` - Criar um novo agendamento.
- **PUT** `/agendamentos/{id}` - Atualizar um agendamento existente.
- **DELETE** `/agendamentos/{id}` - Remover um agendamento.

## Estrutura do Projeto

- **src/main/java/br/com/fiap/medhora**:

  - **adapter**: Camada de adaptadores e DTOs.  
    - **controller**: Controladores REST da API.  
       - `AgendamentoController.java`
    - **graphql**: Camada responsável pela consulta personalizada via GraphQL
    - **kafka**: Configurações e consumidores/produtores Kafka.
   
  - **application**:  Cadamada de Service e DTOs
     - **service**: Camada de serviços (negócio e regras da aplicação).
     - **request**: Camada de DTO da requisição
     - **response**: Camada de DTO da resposta
    - `MensagemResponse.java`

  - **infrastructure** : Camada de infra da aplicação.
     - **config**: Camada de configurações
     - **graphql**: Camada da implementação e port do serviço do graphQL
     - **mapper**: Classes responsáveis pelo mapeamento entre entidades e DTOs.  
       - `AgendamentoMapper.java`  
       - `NotificacaoMapper.java`
     - **repository**: Interfaces JPA para acesso a dados.  
       - `AgendamentoRepository.java`  
       - `NotificacaoRepository.java`

  - **model**: Entidades JPA que representam as tabelas do banco de dados.  
    - `AgendamentoEntity.java`  
    - `NotificacaoEntity.java`
  
  - **shared**: Camada de pastas compartilhadas em comum de toda a aplicação
     - **exception**: Classes para tratamento global e específico de exceções.  
       - `ErroInternoException.java`  
       -  `GlobalException.java`
     - **utils**: Classes utilitárias.  
       - `ConstantUtils.java`

- **docker-compose.yml** (se existir): Para orquestração dos containers da aplicação e dependências (ex: Kafka, banco de dados).

- **application.properties**: Configurações da aplicação.

## Contato

Se você tiver dúvidas ou sugestões, pode entrar em contato:

- **Emerson Soares** - [emersonsoares269@icloud.com](mailto:emersonsoares269@icloud.com)  
- **LinkedIn** - [Emerson Soares](https://www.linkedin.com/in/emerson-soares-9440a11b2/)
