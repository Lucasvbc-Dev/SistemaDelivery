#  Sistema de Delivery

Este repositório contém o código-fonte e a documentação de um **Sistema de Delivery** completo, desenvolvido como um projeto de estudo e aplicação de conceitos de programação orientada a objetos, persistência de dados e arquitetura de software.

##  Visão Geral do Projeto

O sistema simula as operações de um serviço de entrega de comida, gerenciando clientes, restaurantes, entregadores, pedidos e pagamentos.

### ⚙️ Tecnologias Utilizadas

| Categoria | Tecnologia | Detalhes |
| --- | --- | --- |
| **Linguagem** | Java | Linguagem principal para o desenvolvimento da aplicação. |
| **Banco de Dados** | MySQL | Sistema de Gerenciamento de Banco de Dados Relacional (SGBDR) para persistência de dados. |
| **Acesso a Dados** | JDBC | Java Database Connectivity para conexão e manipulação do banco de dados. |
| **Arquitetura** | Camadas (DAO, Service, Controller) | Implementação de um design pattern para separação de responsabilidades e organização do código. |
| **Interface** | Java Swing (Provável) | A estrutura do pacote `View` sugere uma aplicação desktop com interface gráfica. |

### 🏗️ Arquitetura e Estrutura do Código

O projeto segue uma arquitetura em camadas bem definida, facilitando a manutenção e a escalabilidade:

| Pacote | Responsabilidade | Classes Chave |
| --- | --- | --- |
| `model` | **Modelo de Dados (Entidades)** | `Cliente`, `Restaurante`, `Pedido`, `Entrega`, `Pagamento`, etc. |
| `dao` | **Data Access Object (Persistência)** | Responsável pela comunicação direta com o banco de dados (CRUD). Ex: `ClienteDAO`, `PedidoDAO`. |
| `service` | **Regras de Negócio** | Contém a lógica de negócio e coordena as operações entre `controller` e `dao`. Ex: `PedidoService`. |
| `controller` | **Controle** | Gerencia o fluxo de dados entre a `View` e a camada `service`. |
| `View` | **Interface do Usuário** | Classes responsáveis pela interface gráfica da aplicação. Ex: `ClienteTela`, `PedidoTelaCliente`. |
| `util` | **Utilitários** | Classes de suporte, como a `ConnectionFactory` para gerenciamento de conexões com o banco. |
| `Enum` | **Constantes** | Definições de estados e métodos. Ex: `StatusPedido`, `MetodoPagamento`. |

## 🚀 Como Executar o Projeto

Siga os passos abaixo para configurar e rodar o sistema em seu ambiente local.

### 📋 Pré-requisitos

Você precisará ter instalado em sua máquina:

1. **Java Development Kit (JDK)**: Versão 8 ou superior.

1. **MySQL Server**: Versão 5.7 ou superior.

1. **IDE Java**: Como IntelliJ IDEA, Eclipse ou NetBeans.

### 💾 Configuração do Banco de Dados

1. **Crie o Banco de Dados:** Execute o script SQL fornecido para criar o banco de dados e todas as tabelas necessárias.

1. **Detalhes da Conexão:** O projeto está configurado para se conectar ao MySQL com as seguintes credenciais. **É altamente recomendável que você altere a senha ****`Lvbc2110`**** em um ambiente de produção.**

### 💻 Execução da Aplicação

1. **Importe o Projeto:** Abra sua IDE Java e importe o projeto `SistemaDelivery_Trabalho` como um projeto Java existente ou um módulo (o arquivo `.iml` está presente).

1. **Adicione a Dependência JDBC:** Certifique-se de que o driver JDBC do MySQL (Connector/J) esteja incluído nas bibliotecas do seu projeto. Você pode baixá-lo e adicioná-lo manualmente ou configurá-lo via ferramenta de build (se aplicável).

1. **Execute a Classe Principal:** A classe principal para iniciar a aplicação é:

## 📄 Documentação e Diagramas

Para uma compreensão mais aprofundada da estrutura e do design do sistema, consulte os seguintes arquivos de documentação incluídos:

| Arquivo | Descrição |
| --- | --- |
| `Delivery/Documento/Sistema de Delivery - documentaçao.pdf` | Documentação completa do projeto, incluindo requisitos, análise e design. |
| `Delivery/Diagramas/Diagrama de classe.jpg` | Representação visual das classes, seus atributos e relacionamentos. |
| `Delivery/Diagramas/diagrama- entidade-relacionamento.jpg` | Diagrama que ilustra a estrutura do banco de dados e as relações entre as tabelas. |



**

