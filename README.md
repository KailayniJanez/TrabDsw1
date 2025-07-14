# Trabalho 1 – Sistema de Oferta de Vagas

Repositório referente ao **Trabalho 1** da disciplina **Desenvolvimento de Software para a Web 1**, ministrada pelo Prof. Delano M. Beder na UFSCar.

## Integrantes

- **Brenda Maia**
- **Kailayni Janez**
- **Cleidson Almeida**

## Objetivo

Desenvolver um sistema web para oferta de vagas de estágios e empregos, utilizando a arquitetura **Modelo-Visão-Controlador (MVC)** e as tecnologias:

- **Backend:** Spring MVC, Spring Data JPA, Spring Security
- **Frontend:** Thymeleaf, HTML, CSS, JavaScript
- **Build:** Maven

## Funcionalidades Requeridas

### Requisitos Funcionais

- **R1:** CRUD de Profissionais (login de administrador)
- **R2:** CRUD de Empresas (login de administrador)
- **R3:** Cadastro de vagas por empresas logadas
- **R4:** Listagem pública de vagas abertas com filtro por cidade
- **R5:** Candidatura de profissionais às vagas com upload de currículo (PDF)
- **R6:** Visualização de vagas por empresa logada
- **R7:** Visualização de candidaturas por profissional logado
- **R8:** Análise de candidaturas e envio de e-mails com status
- **R9:** Suporte à internacionalização (PT + Inglês)
- **R10:** Validação e tratamento de erros

### Requisitos Técnicos

- Arquitetura MVC
- Spring Boot + Spring MVC
- Thymeleaf (template engine)
- Spring Data JPA para persistência
- Spring Security para autenticação e autorização
- Maven para compilação e execução
- Repositório Git (preferencialmente GitHub)

## Organização do Projeto

A estrutura segue o padrão de projetos Spring Boot, com pacotes separados para controller, service, repository, model e configuração de segurança.

## Internacionalização

O sistema está preparado para exibir a interface em pelo menos dois idiomas:
- Português (pt-BR)
-  Inglês (en)

## Notificações

O sistema envia e-mails automáticos aos candidatos quando seu status de candidatura é alterado pela empresa.

## Autenticação e Perfis

- **Administrador:** Gerencia cadastros de profissionais e empresas.
- **Empresa:** Gerencia vagas e avalia candidatos.
- **Profissional:** Visualiza e se candidata às vagas.

## Atribuições Individuais

> Esta seção será preenchida conforme a divisão de tarefas entre os integrantes.

- **Cleidson Almeida:**  
  Responsável pelo desenvolvimento da camada de **segurança (Spring Security)** do sistema, incluindo:
  - Autenticação e autorização para os três tipos de usuários: **Administrador**, **Empresa** e **Profissional**
  - Implementação de **persistência de senhas criptografadas** com BCrypt
  - Configuração de **páginas de erro genéricas** para tratamento global de exceções
  - **Ajustes em views** Thymeleaf relacionadas a login e navegação segura
  - Desenvolvimento de **funcionalidades no back-end** para integração final do sistema

- **Brenda Maia:**  
  Responsável pelo Suporte de internacionalização (PT + Inglês) e a Validação e tratamento de erros, incluindo:
  - Internacionalização das páginas funcionais (R9)
  - Validação dos dados presentes nos formulários (10)
  - Ajustes nos erros de mensagens e botões
  - Implementação das mensagens de idiomas (inglês e português) (R9)
  - **Ajustes:** criação da classe  Usuario e  as subclasses Empresa  e Profissional; modficação da interface  JpaRepository para a CrudRepository


- **Kailayni Janez:**  
  Responsável pelo desenvolvimento das principais funcionalidades do sistema e integração front-end/back-end, incluindo:
  - Desenvolvimento de CRUDs completos:
      - Implementaçãol de cadastro/gestão de vagas (R3)
      - Criação do sistema de candidaturas com upload de currículos (R5)
      - Desenvolvimento de painéis administrativos (R1, R2)
  - Lógica de negócio avançada:
      - Implementação do fluxo completo de candidaturas (R5-R8)
      - Validações complexas de datas e regras de negócio
      - Filtros e consultas personalizadas para listagens
  - Integração front-en/back-end:
      - Desenvolvimento de templates Thymeleaf responsivos
      - Implementação de interfaces intuitivas para todos os perfis de usuário
      - Integração de componentes Bootstrap com lógica Spring MVC
  - Sistema de notificações:
      - Configuração do serviço de e-mails para atualizações de status (R8)
      - Implementação de feedback visual para ações do usuário
  - Otimização de performance:
      - Tratamento eficiente de upload de arquivos
      - Consultas JPA otimizadas para listagens complexas

---
## Acesso ao administrador
- Email: admin@admin.com
- Senha: admin
