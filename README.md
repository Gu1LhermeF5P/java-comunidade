#   ComUnidade - Global Solution FIAP 📱
## 👥 Integrantes

- **Nome:** Guilherme Francisco   
  **RM:** 554678 
- **Nome:** Larissa de Freitas
  **RM:** 555136
- **Nome:** João Victor Rebello de Santis  
  **RM:** 555287
  ## 📺 Link para o Vídeo de Demonstração no YouTube

[LINK_VIDEO_YOUTUBE_10_MINUTOS]

## 🎤 Link para o Vídeo Pitch no YouTube (se aplicável)

[LINK_VIDEO_PITCH_3_MINUTOS]

---

## 💡 Descrição da Solução "ComUnidade"

O "ComUnidade" é uma aplicação desenvolvida para mitigar os impactos de eventos extremos na população, facilitando a comunicação e o acesso a informações cruciais mesmo em cenários offline ou com conectividade limitada. A solução visa fortalecer a resiliência comunitária em momentos de crise.

**✨ Principais Funcionalidades:**

* **📢 Boletins de Alerta e Informação:** Permite a criação, visualização, atualização e exclusão de boletins informativos e alertas sobre a situação local (funcionalidade CRUD integrada com API backend).
* **🗣️ Canais de Comunicação:** Oferece canais de comunicação fixos (ex: "Bombeiros - Relatar Emergência" 🚒, "Defesa Civil - Alertas" 🛡️) onde os utilizadores podem enviar informações ou receber comunicados. As mensagens são registadas localmente.
* **🆘 SOS com Localização:** Uma funcionalidade de emergência que permite ao utilizador enviar um sinal de SOS, tentando obter e (simuladamente para a rede local) partilhar a sua geolocalização 📍. Inclui um botão para ligar diretamente para um número de emergência local 📞.
* **🗺️ Mapa e Recursos Offline:** Permite ao utilizador visualizar um mapa de referência da sua área (que pode ser carregado da galeria do dispositivo 🖼️) e adicionar/visualizar pontos de interesse categorizados (abrigos 🏠, água 💧, perigos  위험, etc.), guardados localmente.
* **🚀 Onboarding e Configurações:** Processo de introdução para novos utilizadores e uma tela de configurações ⚙️ para personalização básica.

**🛠️ Tecnologias Utilizadas:**

* **Frontend:** React Native (com Expo) ⚛️
* **Backend:** API RESTful desenvolvida com Java Spring Boot ☕
* **Banco de Dados:** Oracle SQL 🗃️ (conectado via Spring Data JPA)
* **Autenticação:** JWT (JSON Web Tokens) 🔑 para proteger os endpoints da API.
* **Documentação da API:** Swagger/OpenAPI 📖
* **Outras bibliotecas relevantes:** React Navigation, Axios, AsyncStorage (frontend); Spring Security, JJWT (backend).

---

## 🔗 Links dos Repositórios

* **Repositório Frontend (React Native):** [LINK_REPOSITORIO_FRONTEND_GITHUB_CLASSROOM]
* **Repositório Backend (Java Spring Boot):** [LINK_REPOSITORIO_BACKEND_GITHUB_CLASSROOM]

---

## 📋 Instruções para Acesso e Testes

### Backend (API Java Spring Boot)

1.  **Pré-requisitos:**
    * A API deve estar em execução num ambiente local ou de deploy.
    * Recomenda-se o uso do Postman ou Insomnia para testar os endpoints.
2.  **Endpoints Principais (Ex: Base URL local: `http://localhost:8080`):**
    * **Autenticação:**
        * `POST /api/auth/register` (Para criar um novo utilizador)
            * Corpo (JSON): `{ "username": "user", "email": "user@mail.com", "password": "password123", "roles": ["ROLE_USER"] }`
        * `POST /api/auth/login` (Para obter um token JWT)
            * Corpo (JSON): `{ "usernameOrEmail": "user", "password": "password123" }`
            * Resposta: JSON com `accessToken`. Copie este token.
    * **Boletins (Header `Authorization: Bearer SEU_TOKEN_JWT` necessário para POST, PUT, DELETE):**
        * `POST /api/boletins` (Criar Boletim)
            * Corpo (JSON): `{ "title": "Novo Alerta", "location": "Local X", "content": "Conteúdo do alerta...", "severity": "Alerta" }`
        * `GET /api/boletins` (Listar Boletins - com paginação e filtros)
        * `GET /api/boletins/{id}` (Buscar Boletim por ID)
        * `PUT /api/boletins/{id}` (Atualizar Boletim)
        * `DELETE /api/boletins/{id}` (Excluir Boletim)
    * **Utilizadores de Teste (sugestão):**
        * Admin: `admin` / `adminpass` (Roles: `ROLE_ADMIN`, `ROLE_USER`)
        * Comum: `user` / `userpass` (Role: `ROLE_USER`)

### Frontend (Aplicação React Native "ComUnidade")

1.  **Pré-requisitos:**
    * Node.js, npm/yarn, Expo CLI instalados.
    * Aplicação Expo Go no dispositivo físico.
    * API backend em execução e acessível (configure a `API_BASE_URL` no `src/services/bulletinService.js` do frontend).
2.  **Executando Localmente:**
    ```bash
    git clone [LINK_REPOSITORIO_FRONTEND_GITHUB_CLASSROOM]
    cd nome-da-pasta-frontend
    npm install
    npx expo start
    ```
    Digitalize o QR Code com a aplicação Expo Go.
3.  **Principais Funcionalidades a Testar (e Ícones a Observar ✨):**
    * Processo de Onboarding (se ativo).
    * Registo e Login de utilizador (se implementado no frontend).
    * Navegação pelas abas: Início (🏠), Canais (🗣️), Boletins (📢), SOS (🆘), Mapa (🗺️).
    * **Boletins:**
        * Visualizar lista (ícones de severidade/tipo de alerta).
        * Criar, ver detalhes, editar, excluir.
    * **Canais de Comunicação:**
        * Visualizar canais fixos (ícone de Bombeiros 🚒, Defesa Civil 🛡️).
        * Enviar um "relatório" para um canal.
    * **SOS:**
        * Ativar SOS (ícone de alarme 🚨).
        * Botão de ligação de emergência (ícone de telefone 📞).
    * **Mapa e Recursos:**
        * Selecionar imagem de mapa (ícone de imagem 🖼️).
        * Adicionar/visualizar pontos com ícones de categoria (🏠, 💧, 🚧).
