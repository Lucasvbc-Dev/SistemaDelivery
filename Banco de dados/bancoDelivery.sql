create database sistemaDelivery;
use sistemaDelivery;

CREATE TABLE clientes (
  idCliente INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100),
  telefone varchar(20),
  endereco varchar(200)
);

CREATE TABLE restaurante (
  idRestaurante INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100),
  tipo_cozinha varchar(50),
  telefone varchar(200)
);

CREATE TABLE tipoRestaurante(
 idTipoRestaurante int auto_increment primary key,
 nome_tipo varchar(100),
 descricao varchar(300)
 );


CREATE TABLE pedido (
  idPedido INT AUTO_INCREMENT PRIMARY KEY,
  idCliente int,
  idRestaurante int,
  data_hora datetime,
  valorTotal DECIMAL(10, 2),
  enderecoEntrega VARCHAR(255),
  status enum('EM_PREPARO', 'A_CAMINHO', 'ENTREGUE', 'CANCELADO', 'PAGO'),
  FOREIGN KEY (idCliente) REFERENCES clientes(idCliente),
  FOREIGN KEY (idRestaurante) REFERENCES restaurante(idRestaurante) 
);


CREATE TABLE itemPedido (
  idItem INT AUTO_INCREMENT PRIMARY KEY,
  idPedido INT,
  descricao varchar(150),
  quantidade int,
  preco decimal(10,2),
  FOREIGN KEY (idPedido) REFERENCES pedido(idPedido)
);

create table Pagamento(
idPagamento int auto_increment primary key,
idPedido int,
valor_total decimal(10,2),
metodo_pagamento enum('PIX', 'DINHEIRO', 'CARTAO_DE_DEBITO', 'CARTAO_DE_CREDITO'),
status_pagamento enum('PENDENTE', 'PROCESSANDO', 'CONCLUIDO', 'CANCELADO'),
data_pagamento date,
foreign key(idPedido) references pedido(idPedido)
);

CREATE TABLE entregador (
    idEntregador INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    telefone VARCHAR(20),
    tipo_veiculo VARCHAR(30),
    idRestaurante INT, 
    FOREIGN KEY (idRestaurante) REFERENCES restaurante(idRestaurante)
);


create table entrega(
idEntrega int auto_increment primary key,
idPedido INT NOT NULL,
idEntregador INT,
status_entrega enum('PENDENTE', 'PROCESSANDO', 'EM_TRANSITO', 'ENTREGUE', 'CANCELADO'),
data_saida DATETIME,
data_entrega DATETIME,
tempo_estimado varchar(10), 
observacao VARCHAR(255),
foreign key(idPedido) references pedido(idPedido),
foreign key (idEntregador) references entregador(idEntregador)
);

create table produto(
idProduto int auto_increment primary key,
nome varchar(100),
preco decimal(10,2),
descricao varchar(200),
categoria varchar(100),
idRestaurante int,
foreign key (idRestaurante) references restaurante(idRestaurante)
);





select * from clientes;
select * from entrega;
select * from entregador;
select * from itempedido;
select * from pagamento;
select * from pedido;
select * from produto;
select * from restaurante;
select * from tiporestaurante;


