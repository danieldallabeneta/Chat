# Sistemas Paralelos e Distribuidos

RAFAEL FOSSA E DANIEL VALDO DALLABENETA

# APLICATIVO DE CHAT ONLINE
  O sistema que será desenvolvido terá como proposta principal disponibilizar um ambiente virtual, onde será possível enviar e receber mensagens em tempo real. 
  O objetivo é possibilitar que usuários se comuniquem entre si sem que tenham que estar próximos uns dos outros.
  
  O sistema será desenvolvido com base na linguagem de programação JAVA. Serão utilizados sockets para criar comunicação entre “hosts” e um server para gerenciar 
  os canais de comunicação. Banco de dados utilizando PostgreSQL.


# Requisitos Funcionais
- RF001 - Cadastar usuário
- RF002 - O sistema deve permitir efetuar login com endereço IP do servidor
- RF003 - O sistema deve permitir cadastrar usuário
- RF004 - O sistema deve permitir enviar mensagem
- RF005 - O sistema deve permitir mostrar status online/offline de cada host
- RF006 - O sistema deve permiti sair do sistema de mensagens 

- RNF001 - O sistema deve efetuar conexão com PostgreSql 
- RNF002 - O sistema deve listar ultimas 500 mensagens
- RNF003 - O sistema deve utilizar Socket


# OPERAÇÕES SUPORTADAS E CONTEÚDOS DAS MENSAGENS DO SERVIDOR 


-- INSERÇÃO DE USUÁRIO

| Operação: | Tabela, tbuser |
|---        |---  |
| Conteúdo: | "INSERT" tabela usuário,codigo, nome, senha|
| Descrição: | Insere o cadastro do usuário no banco de dados |
| Retorno: | Se inserida, retorna “Usuário cadastrado!”, Se não, retorna “Dados inconsistentes para cadastro”. Se já existe um cadastro com os dados retorna “Usuário já cadastrado.”|


-- EFETUANDO LOGIN

| Operação: | Tabela, tbuser |
|---        |---  |
| Conteúdo: | "GET" tabela usuário, nome, senha|
| Descrição: | Busca cadastro do usuário no banco de dados |
| Retorno: | Se usuário não cadastrado mensagem "Usuário não cadastrado", se cadastrado efetua login |



-- INSERÇÃO DE MENSAGENS

| Operação: | Tabela, tbmensagem |
|---        |---  |
| Conteúdo: | "INSERT" tabela mensagem, id, user|
| Descrição: | Insere a mensagem no banco de dados |
| Retorno: | Se mensagem não gravada no banco de dados "Erro ao gravar mensagem" |


-- BUSCA DE MENSAGEM

| Operação: | Tabela, tbmensage |
|---		| ---- |
| Conteúdo: | "GET" tabela mensagem, tbmensage |
| Descrição: | Lista as últimas 500 mensagens armazenadas no banco de dados |
| Retorno:  | Se não houver mensagens lisatgem será "NULL", se houver listará mensagens |




- OBTENÇÃO DE LOGIN (VALIDAÇÃO DE USUÁRIO E SENHA)

| Operação: | Tabela, usuário, senha |
|---        |---  |
| Conteúdo: | "GET" tabela usuário, alias do usuário, senha |
| Descrição: |  Verifica se a Senha e Login existem no cadastro de Login|
| Retorno: | Se os dados informados estiverem corretos retornar “Seja bem vindo” ,Se os dados não forem encontrados na base retorna “ Login ou Senha inválido”.|
