# 🏦 NOVA Bank

## Projeto da disciplina de Padrões e Projetos

O **NOVA Bank** é um projeto acadêmico desenvolvido em **Java**, com o objetivo de aplicar na prática os conceitos estudados na disciplina de **Padrões e Projetos**.

O projeto simula um sistema bancário, permitindo trabalhar com operações de contas, clientes, movimentações financeiras e consulta de extrato.

A aplicação utiliza **Programação Orientada a Objetos (POO)**, **Padrões de Projeto**, **PostgreSQL** para persistência dos dados e **JDBC** para comunicação entre a aplicação Java e o banco de dados.

---

## 🎯 Objetivo do projeto

O principal objetivo do NOVA Bank é demonstrar, por meio de uma aplicação prática, a utilização dos conceitos de **Programação Orientada a Objetos** e dos **Padrões de Projeto** estudados durante a disciplina.

O projeto foi desenvolvido de forma incremental, partindo de uma aplicação bancária em Java e evoluindo para a utilização de padrões de projeto e persistência das informações em banco de dados.

---

# 🧩 Padrões de Projeto utilizados

O projeto utiliza três padrões de projeto estudados na disciplina:

- Singleton
- Factory
- Facade

---

## 1. Singleton

O padrão **Singleton** tem como objetivo controlar a criação de uma classe, permitindo que exista uma única instância compartilhada quando isso for necessário.

No NOVA Bank, o conceito de Singleton está relacionado ao gerenciamento da conexão e do acesso aos recursos utilizados pela aplicação.

A utilização desse padrão ajuda a evitar a criação desnecessária de múltiplas instâncias para o mesmo recurso.

---

## 2. Factory

O padrão **Factory** tem como objetivo centralizar a criação de objetos.

No NOVA Bank, esse padrão está representado pela classe:


ContaFactory

A `ContaFactory` auxilia na criação dos diferentes tipos de conta utilizados pelo sistema.

Dessa forma, a responsabilidade de decidir qual objeto deve ser criado fica concentrada na Factory, deixando o restante da aplicação mais organizado.

---

## 3. Facade

O padrão **Facade** fornece uma interface simplificada para acessar funcionalidades que podem envolver diferentes partes do sistema.

No NOVA Bank, esse padrão está representado pela classe:


BancoFacade

🧱 Programação Orientada a Objetos

O NOVA Bank utiliza diversos conceitos fundamentais de Programação Orientada a Objetos (POO).

Encapsulamento

O encapsulamento é utilizado para proteger os atributos das classes e controlar a forma como esses dados são acessados e modificados.

O projeto utiliza métodos como getters e setters para trabalhar com os atributos das classes.

Abstração

A abstração permite representar no sistema somente as características e comportamentos necessários para o funcionamento das operações bancárias.

Herança

A herança permite que uma classe aproveite características e comportamentos de outra classe.

No projeto, esse conceito é utilizado nas estruturas relacionadas aos diferentes tipos de pessoas e contas.

Polimorfismo

O polimorfismo permite que diferentes classes apresentem comportamentos específicos para operações semelhantes.

Interfaces

As interfaces são utilizadas para definir contratos de comportamento que podem ser implementados pelas classes do sistema.

Construtores

Os construtores são utilizados para inicializar os objetos no momento de sua criação.

🏦 Funcionalidades do sistema

O NOVA Bank possui funcionalidades relacionadas às operações bancárias, entre elas:

Cadastro de contas;
Cadastro e utilização de clientes;
Consulta de saldo;
Depósito;
Saque;
Transferência;
Consulta de extrato;
Operações bancárias;
Persistência das informações no PostgreSQL.
🗄️ Banco de dados

O NOVA Bank utiliza o PostgreSQL para realizar a persistência dos dados.

A comunicação entre a aplicação Java e o PostgreSQL é realizada utilizando JDBC.

O projeto possui o arquivo:

src/dump-sistema_bancario.sql

Esse arquivo contém a estrutura utilizada para auxiliar na configuração do banco de dados.

As informações persistentes das movimentações e do extrato são armazenadas no PostgreSQL.

💻 Tecnologias utilizadas
Java
PostgreSQL
JDBC
Git
GitHub
Visual Studio Code
DBeaver
📁 Estrutura do projeto
NovaBank/
│
├── lib/
│   └── postgresql-42.7.11.jar
│
├── src/
│   ├── App.java
│   ├── BancoFacade.java
│   ├── ClienteDAO
│   ├── Conexao.java
│   ├── Conta.java
│   ├── ContaFactory.java
│   ├── Extrato.java
│   ├── OperacoesBancarias.java
│   ├── TesteConexao.java
│   ├── pessoaFisica.java
│   ├── pessoaJuridica.java
│   ├── dump-sistema_bancario.sql
│   └── novabankuml.png
│
├── .gitignore
└── README.md
📐 Diagrama UML

O projeto possui um diagrama UML que representa a estrutura da aplicação.

O arquivo está localizado em:

src/novabankuml.png
▶️ Como executar o projeto

Para executar o NOVA Bank, é necessário possuir:

JDK instalado;
PostgreSQL instalado e configurado;
Driver JDBC do PostgreSQL;
Visual Studio Code ou outra IDE compatível com Java.

O banco de dados deve ser configurado de acordo com a estrutura utilizada pelo projeto.

O arquivo SQL está disponível em:

src/dump-sistema_bancario.sql

A conexão com o PostgreSQL deve ser configurada de acordo com o ambiente utilizado.

📚 Informações acadêmicas

Projeto: NOVA Bank
Disciplina: Padrões e Projetos
Linguagem: Java
Banco de dados: PostgreSQL
Persistência: PostgreSQL / JDBC
Controle de versão: Git / GitHub

🔗 Repositório

O projeto está disponível no GitHub:

https://github.com/mauricioe1968-lab/NovaBank

👨‍💻 Considerações finais

O NOVA Bank foi desenvolvido como projeto acadêmico da disciplina de Padrões e Projetos, com o objetivo de aplicar na prática conceitos de Programação Orientada a Objetos, Padrões de Projeto, Java e integração com PostgreSQL.

O projeto utiliza os padrões Singleton, Factory e Facade, além de conceitos como encapsulamento, abstração, herança, polimorfismo, interfaces, construtores, getters e setters.

O Git e o GitHub são utilizados para controle de versão e disponibilização do código-fonte do projeto.


