# Sistemas Paralelos e Distribuidos

RAFAEL FOSSA E DANIEL VALDO DALLABENETA

# APLICATIVO DE CHAT ONLINE
  O sistema que será desenvolvido terá como proposta principal disponibilizar um ambiente virtual, onde será possível enviar e receber mensagens em tempo real. 
  O objetivo é possibilitar que usuários se comuniquem entre si sem que tenham que estar próximos uns dos outros.
  
  O sistema será desenvolvido com base na linguagem de programação JAVA, através do aplicativo android studio. Serão utilizados sockets para criar canais
  de comunicação FIREBASE entre “hosts” e um server para gerenciar os canais de comunicação.


# Requisitos Funcionais
- RF001 - O sistema deve permitir Incluir Mensagens na timelapse do chat 
- RF002 - O sistema deve permitir manter histórico do chat
- RF003 - O sistema deve permitir enviar mensagem para mais de um usuário
- RF004 - O sistema deve permitir visualizar os usuários disponíveis 
- RF005 - O sistema deve permitir excluir mensagens
- RF006 - O sistema deverá permitir que o usuário bloqueie usuários específicos, de modo que não receba mensagem
- RF007 - O sistema deverá permitir efetuar login com usuário e senha

# OPERAÇÕES SUPORTADAS E CONTEÚDOS DAS MENSAGENS DO SERVIDOR 

- OBTENÇÃO DE LOGIN (VALIDAÇÃO DE USUÁRIO E SENHA)

| Operação: | "GET" |
| Campos: |Tabela, usuário, senha|
| Conteúdo: |  tabela usuário, alias do usuário, senha |
| Descrição: |  Verifica se a Senha e Login existem no cadastro de Login|
| Retorno: | Se os dados informados estiverem corretos retornar “Seja bem vindo” ,Se os dados não forem encontrados na base retorna “ Login ou Senha inválido”.|


- INSERÇÃO DE USUÁRIO

| Operação: | Tabela, nome, sobrenome, usuário, senha, e-mail, telefone |
|---        |---  |
| Conteúdo: | "INSERT" tabela usuário, Nome , Sobrenome, Alias de utilização, Senha do usuário, e-mail e telefone  |
| Descrição: | Insere o cadastro do usuário no sistema |
| Retorno: | Se inserida, retorna “Usuário cadastrado com Sucesso!!” ,Se não, retorna “Dados inconsistentes para cadastro”. Se já existe um cadastro com os dados retorna “Usuário já cadastrado.”|


- INSERÇÃO DE CONTATO

| Operação: | Código do usuário, nome, telefone, datahora, situação|
|---        |---  |
| Conteúdo: | "INSERT" Código do usuário logado, nome do contato, telefone, datahora cadastro, ativo|
| Descrição: | Insere um novo contato na Lista de contatos|
| Retorno: | Se inserido corretamente retorna “Sucesso”, Se o contato já existe na lista retorna “Contato já cadastrado”, Se não efetuar o cadastro retorna “Erro ao cadastrar contato”.|

- EXCLUSÃO DE CONTATO

| Operação: | Código do usuário, nome, telefone, datahora, situação|
|---        |---  |
| Conteúdo: | "DELETE" Código do usuário, nome do contato, telefone, ativo|
| Descrição: | Remove contato na Lista de contatos|
| Retorno: |Se removido dos contatos “Sucesso”, Caso não exclua “Contato não excluido”, Se não exisitr contato “Erro contato não existente”.|


- INSERÇÃO DE MENSAGEM NO SERVIDOR

| Operação: |Usuário, DataHora, Mensagem |
|---        |---  |
| Conteúdo: |“INSERT” código do usuário, datahora da mensagem, mensagem enviada|
| Descrição:|Inserir mensagem enviado pelo usuário|
| Retorno: |Mensagem inserida retorna "true" se não "false"|


| Operação: |Usuário, DataHora, Mensagem |
| --- | --- |
| Conteúdo: |"Delete" código do usuário, datahora da mensagem, mensagem excluída|
| Descrição: |Deleta mensagem do usuário|
| Retorno: |Mensagem excluida retorna "true" se não "false"|


- BUSCA DE MENSAGEM NO SERVIDOR

| Operação: | Usuário, DataHora, Mensagem |
| --- | --- |
| Conteúdo: |"GET" código do usuario, datahora da mensagem, última mensagem enviada/recebida|
| Descrição: |Lista as ultimas conversas e ultimas mensagens enviadas ou recebidas|
| Retorno: |Lista de usuário e prévia de conteúdo da última mensagem|


| Operação:| Usuário, DataHora, Mensagem|
| --- | --- |
| Conteúdo: |"GET" código do usuario, datahora da mensagem, todas as mensagens enviadas|
| Descrição: |Lista todas as mensagens enviadas ou recebidas|
| Retorno: |Lista de usuário e prévia de conteúdo da última mensagem|
