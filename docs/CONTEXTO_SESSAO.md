# 📌 CONTEXTO DE SESSÃO - PARKING MANAGER

**Data de Criação**: 05 de Novembro de 2025
**Última Atualização**: 05 de Novembro de 2025
**Status Geral**: Planejamento Concluído - Pronto para Implementação

---

## 📊 RESUMO EXECUTIVO

O projeto **Parking Manager** é um sistema fullstack para gerenciamento de vagas de estacionamento com:
- **Backend**: Java + Spring Boot
- **Frontend**: HTML5 + CSS3 + JavaScript
- **Banco de Dados**: MySQL

**Data de Entrega**: 04 de Dezembro de 2025 (30 dias)
**Status Atual**: ~5-10% concluído (apenas configurações básicas)

---

## 🏗️ ESTRUTURA ATUAL DO PROJETO

### ✅ JÁ IMPLEMENTADO

```
✅ Configurações Base
  ├─ pom.xml com todas as dependências (Spring Boot, JPA, MySQL, Lombok, Testes)
  ├─ ParkingManagerApplication.java com CORS configurado
  ├─ application.properties configurado
  ├─ .env.example com variáveis de ambiente
  ├─ schema.sql com tabelas (vagas e ocupacoes)
  └─ README.md documentado

✅ Estrutura de Diretórios Criada
  ├─ backend/src/main/java/com/parking/
  │  ├─ config/ (vazio)
  │  ├─ controller/ (vazio)
  │  ├─ model/ (vazio)
  │  ├─ repository/ (vazio)
  │  └─ service/ (vazio)
  ├─ frontend/ (vazio - sem arquivos)
  ├─ database/ (schema.sql criado)
  └─ docs/ (PLANO_ACAO_2025.md criado)
```

### ❌ A IMPLEMENTAR

```
❌ Backend (80% do trabalho)
  ├─ Models/Entidades (2 classes)
  ├─ Repositories (2 interfaces)
  ├─ Services (2 classes com lógica)
  ├─ Controllers (2 classes com 6 endpoints)
  └─ Testes (4 classes de teste)

❌ Frontend (20% do trabalho)
  ├─ index.html
  ├─ style.css
  └─ main.js (com AJAX)
```

---

## 📋 PLANO DE AÇÃO APROVADO

Ver arquivo completo em: `docs/PLANO_ACAO_2025.md`

### Timeline de 5 Semanas

| Semana | Datas | Fase | Objetivo |
|--------|-------|------|----------|
| 1 | 05-11 Nov | FASE 1 | Models & Repositories |
| 2 | 12-18 Nov | FASE 2 | Services (lógica) |
| 3 | 19-25 Nov | FASE 3 | Controllers (API) |
| 4 | 26 Nov-02 Dec | FASE 4 | Frontend (HTML/CSS/JS) |
| 5 | 03-04 Dec | FASE 5-7 | Testes, Integração e Finalização |

---

## 🎯 PRÓXIMAS AÇÕES (FASE 1)

### O que fazer a partir da próxima sessão:

1. **Criar Entidades Java** (Models):
   - `Vaga.java` com campos: id, numero, status (LIVRE/OCUPADA), createdAt
   - `Ocupacao.java` com campos: id, vagaId, placaCarro, horaEntrada, horaSaida, valorPago

2. **Criar Repositories** (Acesso a dados):
   - `VagaRepository.java` (interface que estende JpaRepository)
   - `OcupacaoRepository.java` (interface que estende JpaRepository)

3. **Caminhos dos arquivos**:
   ```
   backend/src/main/java/com/parking/model/Vaga.java
   backend/src/main/java/com/parking/model/Ocupacao.java
   backend/src/main/java/com/parking/repository/VagaRepository.java
   backend/src/main/java/com/parking/repository/OcupacaoRepository.java
   ```

---

## 🗄️ ESTRUTURA DO BANCO DE DADOS

### Tabelas Criadas (em schema.sql)

```sql
-- TABELA: vagas
CREATE TABLE vagas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL UNIQUE,
    status ENUM('LIVRE', 'OCUPADA') DEFAULT 'LIVRE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABELA: ocupacoes
CREATE TABLE ocupacoes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    vaga_id INT NOT NULL,
    placa_carro VARCHAR(20) NOT NULL,
    hora_entrada DATETIME NOT NULL,
    hora_saida DATETIME,
    valor_pago DECIMAL(10, 2),
    FOREIGN KEY (vaga_id) REFERENCES vagas(id)
);

-- Dados de Teste: 10 vagas inseridas
INSERT INTO vagas (numero) VALUES (1), (2), ..., (10);
```

---

## 🔌 ENDPOINTS DA API (A IMPLEMENTAR)

```http
# VAGAS
GET    /api/vagas          → Listar todas as vagas
GET    /api/vagas/:id      → Obter vaga específica
POST   /api/vagas          → Criar nova vaga

# OCUPAÇÕES
GET    /api/ocupacoes      → Listar todas as ocupações
POST   /api/ocupacoes      → Registrar entrada de veículo
PUT    /api/ocupacoes/:id  → Registrar saída de veículo
```

---

## 📦 DEPENDÊNCIAS MAVEN

Todas as dependências necessárias já estão configuradas no `pom.xml`:

```xml
✅ spring-boot-starter-web      → Para criar API REST
✅ spring-boot-starter-data-jpa → Para JPA/Hibernate
✅ mysql-connector-java:8.0.33  → Para conectar MySQL
✅ lombok                        → Para reduzir boilerplate
✅ spring-boot-starter-test     → Para testes unitários
✅ junit-jupiter                → Para testes JUnit 5
✅ mockito-core                 → Para mocks em testes
✅ jacoco-maven-plugin          → Para cobertura de testes
```

---

## 🖥️ CONFIGURAÇÕES JÁ FEITAS

### CORS
```java
// Em: ParkingManagerApplication.java
✅ Configurado para aceitar requisições de http://localhost:3000
✅ Métodos permitidos: GET, POST, PUT, DELETE
✅ Credentials habilitados
```

### Application.properties
```properties
# Necessário configurar (já tem estrutura básica):
spring.datasource.url=jdbc:mysql://localhost:3306/parking_db
spring.datasource.username=root
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

---

## 🧪 TESTES E VALIDAÇÃO

### Como testar a API quando pronta:

**Opção 1: Postman/Insomnia**
```
POST http://localhost:8080/api/ocupacoes
Content-Type: application/json

{
  "vagaId": 1,
  "placaCarro": "ABC-1234"
}
```

**Opção 2: curl no terminal**
```bash
curl -X GET http://localhost:8080/api/vagas
```

**Opção 3: DevTools do navegador (F12)**
```javascript
fetch('http://localhost:8080/api/vagas')
  .then(r => r.json())
  .then(data => console.log(data))
```

---

## 📝 LÓGICA DE NEGÓCIO A IMPLEMENTAR

### Cálculo de Tempo Estacionado
```
tempo_minutos = (hora_saida - hora_entrada) em minutos
tempo_horas = tempo_minutos / 60
```

### Cálculo de Valor a Pagar
```
Tarifa: R$ 5,00 por hora
valor = Math.ceil(tempo_horas) * 5

Exemplos:
- 15 minutos = 1 hora arredondada = R$ 5,00
- 1 hora 30 minutos = 2 horas arredondadas = R$ 10,00
- 2 horas 15 minutos = 3 horas arredondadas = R$ 15,00
```

### Validações
```
✅ Ao registrar entrada:
  - Vaga deve existir
  - Vaga deve estar LIVRE
  - Placa do carro não pode ser vazia

✅ Ao registrar saída:
  - Ocupação deve existir
  - Ocupação deve estar ativa (hora_saida = null)
  - Atualizar status da vaga para LIVRE
```

---

## 🔄 WORKFLOW DE DESENVOLVIMENTO

### 1. Antes de começar cada sessão
- Ler este arquivo (`CONTEXTO_SESSAO.md`)
- Verificar qual é a fase atual
- Revisar a fase anterior (se houver)

### 2. Durante a sessão
- Seguir as fases do `PLANO_ACAO_2025.md`
- Fazer commits frequentes no Git
- Atualizar este arquivo com progresso

### 3. Ao terminar a sessão
- Atualizar a seção "PROGRESSO ATUAL" abaixo
- Descrever o que foi feito
- Indicar qual é a próxima ação

---

## 📈 PROGRESSO ATUAL

### Status por Fase

| Fase | Descrição | Status | Completude |
|------|-----------|--------|-----------|
| 1 | Models & Repositories | ✅ Concluída | 100% |
| 2 | Services | ⏳ Pronto para começar | 0% |
| 3 | Controllers | ⏳ Aguardando Fase 2 | 0% |
| 4 | Frontend | ⏳ Aguardando Fase 3 | 0% |
| 5 | Testes | ⏳ Aguardando Fase 4 | 0% |
| 6 | Integração | ⏳ Aguardando Fase 5 | 0% |
| 7 | Finalização | ⏳ Aguardando Fase 6 | 0% |

**Progresso Total**: 20-25% ✅

---

### Arquivos Criados Nesta Sessão

```
✅ backend/src/main/java/com/parking/model/Vaga.java
✅ backend/src/main/java/com/parking/model/Ocupacao.java
✅ backend/src/main/java/com/parking/repository/VagaRepository.java
✅ backend/src/main/java/com/parking/repository/OcupacaoRepository.java
```

### Commits Realizados (FASE 1)

```
✅ a47e5ee - feat: criar entidades Vaga e Ocupacao com anotações JPA
✅ 7afab53 - feat: criar VagaRepository com queries customizadas
✅ c5bc420 - feat: criar OcupacaoRepository com queries customizadas
```

### Próxima Fase (FASE 2)

A próxima etapa é criar os **Services** com a lógica de negócio:
- `VagaService.java` - Serviço para gerenciar vagas
- `OcupacaoService.java` - Serviço para gerenciar ocupações e cálculos de tarifa

---

## 💡 DICAS E BOAS PRÁTICAS

### Git - Commits Recomendados
```bash
# Modelo de mensagem
git commit -m "feat: criar entidade Vaga com anotações JPA"
git commit -m "feat: criar entidade Ocupacao com relacionamento"
git commit -m "feat: criar VagaRepository com queries customizadas"
git commit -m "feat: criar OcupacaoRepository"

# Fazer commits após completar cada classe
```

### Código Java - Padrões a Seguir
```java
// ✅ Use Lombok para reduzir boilerplate
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVaga status;
}

// ✅ Use anotações de validação
@NotNull(message = "Número da vaga não pode ser nulo")
@Min(value = 1)
private Integer numero;

// ✅ Use enums para status
public enum StatusVaga {
    LIVRE,
    OCUPADA
}
```

### Testes - Padrão AAA
```java
// Arrange (Preparar)
Vaga vaga = new Vaga(1, StatusVaga.LIVRE);

// Act (Agir)
vagaRepository.save(vaga);

// Assert (Afirmar)
assertEquals(StatusVaga.LIVRE, vaga.getStatus());
```

---

## 🚀 COMO RETOMAR A SESSÃO

1. **Abra este arquivo** (`docs/CONTEXTO_SESSAO.md`)
2. **Verifique a seção "Progresso Atual"** para saber onde parou
3. **Leia a seção "Próximas Ações"** para ver o que fazer
4. **Consulte o arquivo de plano** (`docs/PLANO_ACAO_2025.md`) para detalhes
5. **Continue de onde parou!**

---

## 📞 INFORMAÇÕES DO PROJETO

**Repositório**: `C:\Users\Arthur\Documents\Repositórios\parking-manager`
**Linguagens**: Java, JavaScript, SQL
**Framework**: Spring Boot 3.1.5
**Database**: MySQL 5.7+
**Build Tool**: Maven 3.6+

**Documentação**:
- `README.md` - Overview do projeto
- `docs/PLANO_ACAO_2025.md` - Plano detalhado de 30 dias
- `docs/CONTEXTO_SESSAO.md` - Este arquivo (para recuperação)

---

## ✅ CHECKLIST INICIAL

Antes de começar a codificar:

- [ ] MySQL instalado e rodando
- [ ] Banco de dados `parking_db` criado
- [ ] Tabelas `vagas` e `ocupacoes` criadas (via schema.sql)
- [ ] Java 11+ instalado
- [ ] Maven 3.6+ instalado
- [ ] IDE aberta (VS Code / IntelliJ)
- [ ] Arquivo de contexto lido (este arquivo)
- [ ] Arquivo de plano lido (`PLANO_ACAO_2025.md`)

---

**Última Modificação**: 05 de Novembro de 2025 às 20:57
**Versão do Documento**: 1.0
**Status**: Pronto para retomar desenvolvimento ✅
