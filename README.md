# AutoBots

## Sobre o Projeto

AutoBots é um sistema desenvolvido em Java utilizando o framework Spring Boot para gerenciamento de clientes, empresas, mercadorias, serviços, vendas e usuários.

## O que foi feito nesta atividade

Nesta atividade foi realizada a “atualização de base” do sistema, ampliando suas funcionalidades para além do cadastro de clientes. Foram implementados os cadastros de empresas, usuários (com associação a empresas e definição de tipos como cliente, fornecedor ou funcionário), veículos, mercadorias, serviços, vendas e credenciais de acesso. Também foram criados os respectivos CRUDs para todas as entidades e aplicado o padrão HATEOAS, preparando o sistema para atender as necessidades mínimas de uma loja de manutenção veicular.

## Requisitos

- **Java:** 17+  
- **Maven:** 3.8.x  
- **Spring Boot:** 2.7.x  
- (As versões exatas podem ser conferidas no arquivo [`pom.xml`](automanager/pom.xml))

## Como Rodar o Projeto

1. **Clone o repositório:**
   - Baixe ou clone o repositório para sua máquina.

2. **Abra o projeto em sua IDE:**
   - Abra a pasta `automanager` em sua IDE de preferência (por exemplo, IntelliJ IDEA, Eclipse ou VS Code).

3. **Compile o projeto:**
   - Aguarde a IDE baixar as dependências e compilar o projeto automaticamente. Caso necessário, utilize a opção de "Build Project" ou "Rebuild Project" da sua IDE.

4. **Execute a aplicação:**
   - Localize a classe `AutomanagerApplication.java` no pacote `com.autobots.automanager`.
   - Clique com o botão direito sobre o arquivo e selecione "Run" ou "Executar".
   - A aplicação será iniciada e estará disponível na porta padrão (geralmente `8080`).

## ⚠️ Configuração do Banco de Dados (`application.properties`)

O arquivo [`application.properties`](automanager/src/main/resources/application.properties) define as configurações de conexão com o banco de dados MySQL.  
**Se o banco não estiver corretamente configurado, a aplicação não irá iniciar.**

> **Atenção:** O serviço do MySQL deve estar aberto e em execução antes de iniciar a aplicação.  
> Se o MySQL não estiver rodando, a aplicação não conseguirá se conectar ao banco e apresentará erro de conexão.

Exemplo de configuração:
```
spring.datasource.url=jdbc:mysql://localhost:3306/base
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

- **Banco de dados:** Certifique-se de que o banco chamado `base` existe no seu MySQL.  
  Para criar, use:
  ```sql
  CREATE DATABASE base;
  ```
- **Usuário e senha:** Ajuste `spring.datasource.username` e `spring.datasource.password` conforme seu ambiente.
- **Permissões:** O usuário configurado precisa ter permissão para criar tabelas no banco.
- **Porta:** Se seu MySQL usa outra porta, altere `localhost:3306` para a porta correta.

Se não quiser perder dados a cada reinício, troque `spring.jpa.hibernate.ddl-auto=create` por `update`.

## Como Testar se Está Funcionando

1. **Testes Automatizados**

   - Na sua IDE, localize a classe de testes (por exemplo, `AutomanagerApplicationTests.java`).
   - Clique com o botão direito sobre a classe ou sobre o diretório de testes e selecione "Run Tests" ou "Executar Testes".
   - Os resultados dos testes aparecerão na janela de execução de testes da IDE.

2. **Verificação Manual**

   - Ao iniciar a aplicação, dados de exemplo são cadastrados automaticamente pelo método `run` em [`AutomanagerApplication.java`](automanager/src/main/java/com/autobots/automanager/AutomanagerApplication.java).
   - Para verificar se a aplicação está rodando corretamente, acesse o endpoint principal de empresas:

     ```
     GET http://localhost:8080/empresas/listar
     ```

     Você pode acessar esse endpoint pelo navegador ou utilizando ferramentas como Postman ou Insomnia. Se a aplicação estiver funcionando, será retornada uma lista de empresas cadastradas (incluindo os dados de exemplo).

## Observações

- Certifique-se de que a porta 8080 está livre.
- O arquivo de configuração está em [`application.properties`](automanager/src/main/resources/application.properties).
- Para mais detalhes sobre as entidades e funcionalidades, consulte os arquivos na pasta [`src/main/java/com/autobots/automanager/`](automanager/src/main/java/com/autobots/automanager/).

## Principais Endpoints para Teste

- **Empresas**
  - Listar empresas:  
    `GET http://localhost:8080/empresas/listar`
  - Visualizar empresa por ID:  
    `GET http://localhost:8080/empresas/visualizar/{id}`

- **Usuários**
  - Listar usuários:  
    `GET http://localhost:8080/usuarios/listar`
  - Visualizar usuário por ID:  
    `GET http://localhost:8080/usuarios/visualizar/{id}`

- **Mercadorias**
  - Listar mercadorias:  
    `GET http://localhost:8080/mercadorias/listar`
  - Visualizar mercadoria por ID:  
    `GET http://localhost:8080/mercadorias/visualizar/{id}`

- **Serviços**
  - Listar serviços:  
    `GET http://localhost:8080/servicos/listar`
  - Visualizar serviço por ID:  
    `GET http://localhost:8080/servicos/visualizar/{id}`

- **Veículos**
  - Listar veículos:  
    `GET http://localhost:8080/veiculos/listar`
  - Visualizar veículo por ID:  
    `GET http://localhost:8080/veiculos/visualizar/{id}`

- **Vendas**
  - Listar vendas:  
    `GET http://localhost:8080/vendas/listar`
  - Visualizar venda por ID:  
    `GET http://localhost:8080/vendas/visualizar/{id}`

- **Credenciais**
  - Listar credenciais:  
    `GET http://localhost:8080/credenciais/listar`
  - Visualizar credencial por ID:  
    `GET http://localhost:8080/credenciais/visualizar/{id}`

---

> Você pode testar esses endpoints usando o navegador, Postman, Insomnia ou qualquer cliente HTTP.  
> Se algum endpoint retornar uma lista (mesmo vazia) ou um objeto JSON, a aplicação está rodando corretamente e acessando o banco de dados.