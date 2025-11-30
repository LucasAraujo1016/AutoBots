# AutoBots

## Sobre o Projeto

AutoBots é um sistema desenvolvido em Java utilizando o framework Spring Boot para gerenciamento de clientes, empresas, mercadorias, serviços, vendas e usuários.

## Requisitos

- **Java:** 17+  
- **Maven:** 3.8.x  
- **Spring Boot:** 2.7.x  
- (As versões exatas podem ser conferidas no arquivo [`pom.xml`](automanager/pom.xml))

## O que foi feito nesta atividade

Nesta atividade foi implementado o processo de autenticação e autorização utilizando Json Web Token (JWT), seguindo o padrão de perfis e autorizações sugerido pelos investidores. Agora, o sistema possui controle de acesso para os perfis Administrador, Gerente, Vendedor e Cliente, garantindo que cada usuário só possa acessar e manipular os recursos permitidos pelo seu perfil.

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


## Observações

- Certifique-se de que a porta 8080 está livre.
- O arquivo de configuração está em [`application.properties`](automanager/src/main/resources/application.properties).
- Para mais detalhes sobre as entidades e funcionalidades, consulte os arquivos na pasta [`src/main/java/com/autobots/automanager/`](automanager/src/main/java/com/autobots/automanager/).

---