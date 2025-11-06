# 📅 PLANO DE AÇÃO - PARKING MANAGER (até 04/12)

**Data de Criação**: 05 de Novembro de 2025
**Data de Entrega**: 04 de Dezembro de 2025
**Duração Total**: 30 dias

---

## 📊 TIMELINE RECOMENDADA

```
SEMANA 1 (05-11 Nov)      - BACKEND: Models & Repositories
SEMANA 2 (12-18 Nov)      - BACKEND: Services (lógica principal)
SEMANA 3 (19-25 Nov)      - BACKEND: Controllers (API)
SEMANA 4 (26 Nov-02 Dec)  - FRONTEND: HTML, CSS, JavaScript
SEMANA 5 (03-04 Dec)      - TESTES: Unit & Integração + Polishing
```

---

## 🎯 FASES DETALHADAS

### **FASE 1: MODELS & REPOSITORIES (5-6 dias)**

**Objetivo**: Criar a estrutura de dados e acesso a dados

```
✅ Criar Entidades Java:
  ├─ Vaga.java
  │  ├─ id: Integer (PK, Auto-increment)
  │  ├─ numero: Integer (Unique)
  │  ├─ status: Enum (LIVRE, OCUPADA)
  │  └─ createdAt: LocalDateTime
  │
  └─ Ocupacao.java
     ├─ id: Integer (PK, Auto-increment)
     ├─ vagaId: Integer (FK)
     ├─ placaCarro: String
     ├─ horaEntrada: LocalDateTime
     ├─ horaSaida: LocalDateTime (nullable)
     └─ valorPago: BigDecimal (nullable)

✅ Criar Repositories (interfaces JPA):
  ├─ VagaRepository.java
  │  ├─ Métodos: findAll(), findById(), findByStatus()
  │  └─ Queries customizadas se necessário
  │
  └─ OcupacaoRepository.java
     ├─ Métodos: findAll(), findByVagaId()
     ├─ findOcupacaoAtiva(vagaId)
     └─ Queries para filtrar histórico
```

**Arquivos a criar**:
- `backend/src/main/java/com/parking/model/Vaga.java`
- `backend/src/main/java/com/parking/model/Ocupacao.java`
- `backend/src/main/java/com/parking/repository/VagaRepository.java`
- `backend/src/main/java/com/parking/repository/OcupacaoRepository.java`

---

### **FASE 2: SERVICES (5-6 dias)**

**Objetivo**: Implementar lógica de negócio

```
✅ VagaService.java - Gerenciar vagas
  ├─ listarVagas(): List<Vaga>
  ├─ obterVaga(id): Vaga
  ├─ criarVaga(numero): Vaga
  ├─ atualizarStatusVaga(id, status): void
  └─ contarVagasLivres(): Integer

✅ OcupacaoService.java - Lógica principal
  ├─ registrarEntrada(vagaId, placaCarro): Ocupacao
  │  └─ Validações: vaga existe? está livre?
  │
  ├─ registrarSaida(ocupacaoId): Ocupacao
  │  └─ Define hora_saida e calcula valor
  │
  ├─ calcularTempoEstacionamento(entrada, saida): Long
  │  └─ Retorna tempo em minutos
  │
  ├─ calcularValor(tempoMinutos): BigDecimal
  │  └─ Lógica: R$5 por hora (ex: 1h30min = R$7,50)
  │
  ├─ listarOcupacoes(): List<Ocupacao>
  └─ listarHistorico(): List<Ocupacao>
```

**Arquivos a criar**:
- `backend/src/main/java/com/parking/service/VagaService.java`
- `backend/src/main/java/com/parking/service/OcupacaoService.java`

---

### **FASE 3: CONTROLLERS (5-6 dias)**

**Objetivo**: Expor endpoints REST da API

```
✅ VagaController.java - Endpoints de vagas
  │
  ├─ GET  /api/vagas
  │  └─ Retorna: List<VagaDTO>
  │  └─ Descrição: Listar todas as vagas com status
  │
  ├─ GET  /api/vagas/:id
  │  └─ Retorna: VagaDTO
  │  └─ Descrição: Obter detalhes de uma vaga específica
  │
  └─ POST /api/vagas
     └─ Body: { numero: Integer }
     └─ Retorna: VagaDTO
     └─ Descrição: Criar nova vaga

✅ OcupacaoController.java - Endpoints de ocupações
  │
  ├─ GET  /api/ocupacoes
  │  └─ Retorna: List<OcupacaoDTO>
  │  └─ Descrição: Listar todas as ocupações
  │
  ├─ POST /api/ocupacoes
  │  └─ Body: { vagaId: Integer, placaCarro: String }
  │  └─ Retorna: OcupacaoDTO
  │  └─ Descrição: Registrar entrada de veículo
  │
  └─ PUT  /api/ocupacoes/:id
     └─ Body: {} (vazio)
     └─ Retorna: OcupacaoDTO
     └─ Descrição: Registrar saída de veículo
```

**Arquivos a criar**:
- `backend/src/main/java/com/parking/controller/VagaController.java`
- `backend/src/main/java/com/parking/controller/OcupacaoController.java`
- `backend/src/main/java/com/parking/dto/VagaDTO.java` (opcional, para melhor prática)
- `backend/src/main/java/com/parking/dto/OcupacaoDTO.java` (opcional)

---

### **FASE 4: FRONTEND (5-6 dias)**

**Objetivo**: Criar interface de usuário responsiva e funcional

```
✅ index.html - Interface principal
  ├─ Header com título "Parking Manager"
  ├─ Seção de Vagas:
  │  ├─ Tabela com colunas:
  │  │  ├─ Número da Vaga
  │  │  ├─ Status (Livre/Ocupada)
  │  │  ├─ Placa do Carro (se ocupada)
  │  │  └─ Ações (Entrada/Saída)
  │  └─ Indicador de vagas livres (ex: "5/10 livres")
  │
  ├─ Formulário de Entrada:
  │  ├─ Select: Escolher vaga
  │  ├─ Input: Placa do carro
  │  └─ Button: Registrar Entrada
  │
  ├─ Formulário de Saída:
  │  ├─ Select: Ocupação ativa
  │  ├─ Display: Tempo e Valor
  │  └─ Button: Registrar Saída
  │
  └─ Seção de Histórico:
     ├─ Tabela com histórico de ocupações
     └─ Colunas: Vaga, Placa, Entrada, Saída, Tempo, Valor

✅ style.css - Estilos responsivos
  ├─ Layout: Grid/Flexbox
  ├─ Cores:
  │  ├─ Vaga Livre: Verde (#22c55e)
  │  ├─ Vaga Ocupada: Vermelho (#ef4444)
  │  └─ Tema: Light (padrão) + Dark (opcional)
  ├─ Responsividade: Mobile, Tablet, Desktop
  ├─ Componentes: Tabelas, Formulários, Botões
  └─ Animações: Suave (transitions)

✅ main.js - Lógica e comunicação com API
  ├─ Função: carregarVagas()
  │  └─ GET /api/vagas → atualizar tabela
  │
  ├─ Função: registrarEntrada()
  │  └─ POST /api/ocupacoes → {vagaId, placaCarro}
  │
  ├─ Função: registrarSaida(ocupacaoId)
  │  └─ PUT /api/ocupacoes/:id
  │
  ├─ Função: atualizarStatusVaga(id, novoStatus)
  │  └─ Atualiza interface dinamicamente
  │
  ├─ Função: carregarHistorico()
  │  └─ GET /api/ocupacoes → mostrar histórico
  │
  └─ Handlers de eventos:
     ├─ DOMContentLoaded → carregar dados iniciais
     ├─ Click em botões → registrar entrada/saída
     └─ Refresh automático a cada 5 segundos
```

**Arquivos a criar**:
- `frontend/index.html`
- `frontend/assets/css/style.css`
- `frontend/assets/js/main.js`

---

### **FASE 5: TESTES (3-4 dias)**

**Objetivo**: Garantir qualidade e confiabilidade do código

```
✅ Testes Unitários
  ├─ VagaServiceTest.java
  │  ├─ testListarVagas()
  │  ├─ testCriarVaga()
  │  ├─ testContarVagasLivres()
  │  └─ testAtualizarStatus()
  │
  └─ OcupacaoServiceTest.java
     ├─ testRegistrarEntrada()
     ├─ testRegistrarSaida()
     ├─ testCalcularTempoEstacionamento()
     ├─ testCalcularValor()
     └─ testValidacoes()

✅ Testes de Integração
  ├─ VagaControllerTest.java
  │  ├─ testGetAllVagas()
  │  ├─ testGetVagaById()
  │  └─ testCreateVaga()
  │
  └─ OcupacaoControllerTest.java
     ├─ testPostOcupacao()
     ├─ testPutOcupacao()
     └─ testGetAllOcupacoes()

✅ Cobertura de Testes
  └─ Meta: Mínimo 80% de cobertura
```

**Arquivos a criar**:
- `backend/src/test/java/com/parking/service/VagaServiceTest.java`
- `backend/src/test/java/com/parking/service/OcupacaoServiceTest.java`
- `backend/src/test/java/com/parking/controller/VagaControllerTest.java`
- `backend/src/test/java/com/parking/controller/OcupacaoControllerTest.java`

---

### **FASE 6: INTEGRAÇÃO & CONFIGURAÇÃO (2-3 dias)**

**Objetivo**: Integrar todas as partes e validar funcionamento

```
✅ Configurações
  ├─ Editar arquivo .env com credenciais MySQL:
  │  ├─ DB_URL=jdbc:mysql://localhost:3306/parking_db
  │  ├─ DB_USER=root
  │  └─ DB_PASSWORD=sua_senha
  │
  └─ Editar application.properties:
     ├─ spring.datasource.url
     ├─ spring.datasource.username
     ├─ spring.jpa.hibernate.ddl-auto=update
     └─ spring.jpa.show-sql=false

✅ Testes de Conexão
  ├─ Verificar MySQL rodando
  ├─ Testar conexão JDBC
  └─ Confirmar tabelas criadas

✅ Testes E2E (End-to-End)
  ├─ Iniciar backend (mvn spring-boot:run)
  ├─ Iniciar frontend (http-server ou similar)
  ├─ Cenário 1: Registrar entrada
  ├─ Cenário 2: Registrar saída
  ├─ Cenário 3: Verificar cálculo de valor
  └─ Cenário 4: Visualizar histórico
```

---

### **FASE 7: FINALIZAÇÃO (2 dias)**

**Objetivo**: Polish final e documentação

```
✅ Documentação
  ├─ Atualizar README.md com:
  │  ├─ Exemplos de requisições/respostas
  │  ├─ Instruções de instalação
  │  └─ Troubleshooting
  │
  └─ Adicionar comentários no código:
     ├─ JavaDoc para classes principais
     └─ Comentários em lógica complexa

✅ Code Review & Refatoração
  ├─ Verificar padrões de código
  ├─ Remover código duplicado
  ├─ Otimizar queries SQL
  └─ Melhorar tratamento de erros

✅ Verificação de Qualidade
  ├─ Executar: mvn clean test
  ├─ Verificar cobertura de testes
  ├─ Testar no navegador (Chrome, Firefox)
  └─ Verificar responsividade

✅ Git & Entrega
  ├─ Revisar commits
  ├─ Adicionar tags de versão (v1.0.0)
  ├─ Push para repositório remoto
  └─ Criar release notes
```

---

## 📋 CHECKLIST DE CONCLUSÃO

### Backend
- [ ] Models criados e com anotações JPA
- [ ] Repositories implementados
- [ ] Services com toda lógica de negócio
- [ ] Controllers com endpoints funcionando
- [ ] Testes unitários passando
- [ ] Testes de integração passando
- [ ] Application.properties configurado
- [ ] CORS configurado corretamente

### Frontend
- [ ] index.html criado com estrutura semântica
- [ ] style.css com estilos responsivos
- [ ] main.js com funções AJAX
- [ ] Tabela de vagas atualizando dinamicamente
- [ ] Formulário de entrada funcionando
- [ ] Formulário de saída funcionando
- [ ] Cálculos aparecendo corretamente
- [ ] Histórico mostrando dados

### Integração
- [ ] MySQL conectando corretamente
- [ ] Banco de dados com dados de teste
- [ ] CORS permitindo requisições do frontend
- [ ] Testes E2E passando
- [ ] Sem erros de console (JavaScript)
- [ ] Sem erros de log (Java)

### Documentação & Deploy
- [ ] README.md atualizado
- [ ] Código comentado
- [ ] Commits descritivos no Git
- [ ] Release notes criada

---

## 🛠️ RECURSOS NECESSÁRIOS

| Recurso | Versão | Status | Ação |
|---------|--------|--------|------|
| **Java** | 11+ | Verificar | `java -version` |
| **Maven** | 3.6+ | Verificar | `mvn -version` |
| **MySQL** | 5.7+ | Instalar | [Download](https://dev.mysql.com/downloads/) |
| **Git** | 2.0+ | Verificar | `git --version` |
| **Node.js** | 14+ | Opcional | Para ferramentas de build |
| **Postman/Insomnia** | Latest | Recomendado | Para testar API |
| **VS Code** | Latest | Recomendado | IDE |

---

## 📈 MÉTRICAS DE PROGRESSO

Acompanhe o progresso usando estas métricas:

```
Fase 1 (Models & Repos):    ████░░░░░░ 40% concluído
Fase 2 (Services):          ░░░░░░░░░░  0% concluído
Fase 3 (Controllers):       ░░░░░░░░░░  0% concluído
Fase 4 (Frontend):          ░░░░░░░░░░  0% concluído
Fase 5 (Testes):            ░░░░░░░░░░  0% concluído
Fase 6 (Integração):        ░░░░░░░░░░  0% concluído
Fase 7 (Finalização):       ░░░░░░░░░░  0% concluído
```

**Progresso Total**: 5-6% ✅

---

## 📞 SUPORTE E DÚVIDAS

Se encontrar problemas durante desenvolvimento:

1. **Verificar logs**:
   - Backend: Console do Maven
   - Frontend: DevTools (F12)

2. **Validações comuns**:
   - MySQL rodando? `mysql -u root -p`
   - Port 8080 livre? `netstat -an | grep 8080`
   - Port 3000 livre? `netstat -an | grep 3000`

3. **Debugging**:
   - Postman para testar API isoladamente
   - DevTools do navegador para JavaScript
   - Logs do Spring Boot para problemas de conexão

---

## 📝 NOTAS IMPORTANTES

- **Commits frequentes**: Faça commit a cada feature implementada
- **Testes antes de integrar**: Sempre rode testes localmente
- **CORS habilitado**: Backend já tem CORS configurado para localhost:3000
- **Banco de dados**: Schema SQL já foi executado, tabelas já existem
- **Pom.xml**: Todas as dependências necessárias já estão configuradas

---

**Última atualização**: 05 de Novembro de 2025
**Status**: Planejamento Concluído ✅
**Próximo Passo**: Iniciar FASE 1 - Implementar Models
