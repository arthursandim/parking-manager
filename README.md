# Parking Manager - Gerenciamento de Vagas de Estacionamento

Projeto fullstack para gerenciamento de vagas de estacionamento com Java, JavaScript e MySQL.

## 📋 Funcionalidades

- Visualizar vagas disponíveis e ocupadas
- Registrar entrada de veículo (placa do carro)
- Registrar saída de veículo
- Calcular tempo de permanência
- Calcular valor a pagar
- Ver histórico de ocupações

## 🛠️ Tecnologias Utilizadas

- **Backend**: Java + Spring Boot
- **Frontend**: HTML5 + CSS3 + JavaScript
- **Banco de Dados**: MySQL
- **Ferramentas**: Maven, Git

## 📦 Pré-requisitos

- Java 11 ou superior
- MySQL 5.7 ou superior
- Maven 3.6 ou superior
- Node.js (opcional, apenas se usar ferramentas frontend)

## 🚀 Como Executar

### 1. Configurar Banco de Dados

```bash
# Abra o MySQL
mysql -u root -p

# Execute o script de criação
source database/schema.sql
```

### 2. Configurar Backend

```bash
# Navegue até a pasta backend
cd backend

# Configure o arquivo .env com suas credenciais do MySQL
# Copie o arquivo de exemplo:
cp .env.example .env

# Edite o .env com suas credenciais:
# DB_URL=jdbc:mysql://localhost:3306/parking_db
# DB_USER=root
# DB_PASSWORD=sua_senha

# Compile e execute
mvn clean install
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080/api`

### 3. Executar Frontend

```bash
# Abra o arquivo index.html no navegador
# Ou use um servidor local:
cd frontend
python -m http.server 3000
# ou
npx http-server -p 3000
```

Acesse: `http://localhost:3000`

## 📁 Estrutura do Projeto

```
parking-manager/
├── backend/              # Código Java/Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/parking/
│   │   │   │   ├── model/       # Entidades
│   │   │   │   ├── repository/  # Acesso a dados
│   │   │   │   ├── service/     # Lógica de negócio
│   │   │   │   └── controller/  # Endpoints
│   │   │   └── resources/       # Configurações
│   │   └── test/                # Testes unitários
│   └── pom.xml
├── frontend/             # Código HTML/CSS/JS
│   ├── index.html
│   ├── assets/
│   │   ├── css/
│   │   └── js/
│   └── pages/
├── database/             # Scripts SQL
│   └── schema.sql
└── .env.example          # Variáveis de ambiente
```

## 🧪 Testes

```bash
cd backend

# Executar todos os testes
mvn test

# Executar com cobertura
mvn test jacoco:report
```

## 📝 Endpoints da API

### Vagas
- `GET /api/vagas` - Listar todas as vagas
- `GET /api/vagas/:id` - Obter detalhes de uma vaga
- `POST /api/vagas` - Criar nova vaga

### Ocupações
- `GET /api/ocupacoes` - Listar todas as ocupações
- `POST /api/ocupacoes` - Registrar entrada de veículo
- `PUT /api/ocupacoes/:id` - Registrar saída de veículo

## 🐛 Solução de Problemas

**Erro de conexão com banco de dados:**
- Verifique se o MySQL está rodando
- Verifique as credenciais no arquivo `.env`
- Verifique se o banco `parking_db` foi criado

**Frontend não conecta à API:**
- Verifique se o backend está rodando na porta 8080
- Verifique as configurações de CORS em `config/CorsConfig.java`

## 📅 Data de Entrega

05 de dezembro de 2025

## 👨‍💻 Autor

Seu Nome

## 📄 Licença

MIT
