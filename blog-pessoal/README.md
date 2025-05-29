# 📘 Blog Pessoal - Full Stack (Spring Boot + Angular)

## 💼 Projeto desenvolvido no programa **Montreal - AceleraMaker**

Este projeto é uma aplicação web full stack para gerenciamento de postagens em um blog, com foco em:
- Autenticação via JWT
- Cadastro de usuários
- Criação e listagem de temas e postagens
- Interface moderna e responsiva com Angular
- Boas práticas de segurança, arquitetura e documentação

#

## 🌐 Link do Projeto Online (RESPONSIVO)

Acesse o Blogify publicado com Netlify:

[![Blogify Online](https://img.shields.io/badge/🔗%20Ver%20Blogify%20Online-acesse%20aqui-purple?style=for-the-badge&logo=netlify)](https://blogify-blog-pessoal.netlify.app)


---

## 🚀 Tecnologias Utilizadas

### 🖥️ Back-End
- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- MySQL (ou PostgreSQL)
- Maven
- Swagger/OpenAPI

### 💻 Front-End
- Angular 17+
- Angular Material
- Bootstrap
- CSS customizado com design autoral
---

## ⚙️ Como Executar o Projeto

### 1. Clone o repositório
```bash
git clone https://github.com/yarazip/ProjetoBlogPessoal.git
cd blog-pessoal
```

2. Configure o banco de dados no arquivo application.properties
properties
```
spring.datasource.url=jdbc:mysql://localhost:3307/blog_pessoal
spring.datasource.username=root
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Execute o back-end
```
./mvnw spring-boot:run
```

4. Instale e rode o front-end Angular
```
cd frontend
npm install
ng serve
```
## 🧠 Modelagem do Domínio
### 👤 Usuario

```
id: Long

nome: String

email: String

senha: String

tipo: String
```
### 📝 Postagem
```
id: Long

titulo: String

conteudo: String

data: LocalDateTime

tema: Tema

autor: Usuario
```

### 🧩 Tema

```
id: Long

descricao: String

postagens: List<Postagem>
```

### 🔐 Segurança

```
Autenticação baseada em email + senha

Autorização por token JWT

Senhas criptografadas com BCrypt

Spring Security com filtros personalizados

Interceptors no Angular para anexar token
```

## 📑 Endpoints REST (Back-End)
### 🔐 Autenticação /api/auth

```
POST /login → Login e retorno do token JWT

POST /register → Cadastro de novo usuário
```
### 👥 Usuários /api/usuarios
```
POST / → Cadastrar usuário

PUT /{id} → Atualizar usuário

DELETE /{id} → Deletar usuário
```

### 📝 Postagens /api/postagens
```
GET / → Listar todas

GET /filtro?autor={id}&tema={id} → Filtro dinâmico

POST / → Criar nova postagem

PUT /{id} → Atualizar postagem

DELETE /{id} → Deletar postagem
```
### 🧩 Temas /api/temas
```
GET / → Listar temas

POST / → Criar tema

PUT /{id} → Atualizar tema

DELETE /{id} → Remover tema
```
### 🖼️ Interface Angular

- 🧭 Navegação com menu **dinâmico** e **responsivo**!

- 🌘 Suporte a modo **escuro/claro**

- 🧑‍💼 Área de login e cadastro

- 📝 Tela de **CRUD** de postagens e temas

- 📊 Dashboard com gráficos de postagens

- 🔐 Guardas de rota com AuthGuard

- 📦 Serviços centralizados para requisições

--- 

### 🔍 Documentação via Swagger

Acesse a documentação automática via:
```
http://localhost:8181/swagger-ui/index.html
```
---

### 🧪 Testes
- ✅ Testes manuais com **Postman**

- ✅ Boas Práticas Aplicadas

---

### 📦 DTOs para abstração de dados

- 🧹 Clean Architecture com MVC + Service Layer

- 🔐 Tokens **JWT** e segurança robusta

- 📑 Validações com **Bean** Validation

- 🌐 Separação completa entre front-end e back-end

---

## 🔐 Confidencialidade
Este projeto é exclusivamente **educacional** e destinado a fins de **aprendizado pessoal.
**

#

## 👩🏻‍💻 Autora
Yara Rosa
GitHub: https://github.com/yarazip



