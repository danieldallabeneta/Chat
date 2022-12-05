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
| Conteúdo: | "INSERT", "USER", nome, senha, status|
| Descrição: | Insere o cadastro do usuário no banco de dados |
| Retorno: | Se inserida, retorna “INSERT;TRUE”, Se não, retorna “INSERT;FALSE”|


-- BUSCAR USUÁRIO

| Operação: | Tabela, tbuser |
|---        |---  |
| Conteúdo: | "GET", "USER", nome|
| Descrição: | Busca cadastro do usuário no banco de dados |
| Retorno: | Se usuário não cadastrado mensagem "GET;FALSE", se cadastrado "codigo,nome,senha,status" |

--FAZER LOGIN

| Operação: | Tabela, tbuser |
|---        |---  |
| Conteúdo: | "ALTER", "STATUS", codigo, status, ip, porta|
| Descrição: | Altera a situação do usuário para online e cadastra o ip e porta do host |
| Retorno: | Se alterado corretamente retorna "true". Caso não altere retorna "False" |

-- INSERÇÃO DE MENSAGENS

| Operação: | Tabela, tbmensagem |
|---        |---  |
| Conteúdo: | "INSERT","MESSAGE", user, mensagem|
| Descrição: | Insere a mensagem no banco de dados |
| Retorno: | Se mensagem gravada com sucesso retorna a lista das ultimas 500 mensagens. Se não gravada retorna "false"|


-- BUSCA DE MENSAGENS

| Operação: | Tabela, tbmessage |
|---		| ---- |
| Conteúdo: | "LIST", "MESSAGE" |
| Descrição: | Lista as últimas 500 mensagens armazenadas no banco de dados |
| Retorno:  | Se não houver mensagens o retorno será "", se houver mensagens retorna String com todas as ultimas 500 mensagens |


-BUSCA USUÁRIOS

| Operação: | Tabela, tbuser |
|---        |---  |
| Conteúdo: | "LIST", "USER" |
| Descrição: |  Retorna todos os usuários cadastrados|
| Retorno: | Se não houver usuários cadastrados o retorno será "", se houver usuários retorna String com todos os usuários cadastrados|
