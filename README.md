# costmate

Essa aplicacao e derivada de uma planilha que controla gastos.

Primeiramente existe uma planilha que agrupa gastos mensais de acordo com o mes e ano.

Nessa planilha existem despesas que podem ter varios atributos especiais, como tipo do gasto, fixo, nao fixo, parcelado, se tem reembolso, se esta associado a um cartao de credito

No cartao de credito existem alguma informacoes com data de fechamento e vencimento

Tambem existem as entradas.

Essas informacoes sao consolidadas constantemente fornecendo uma posicao do gastos mensais.

ps.: incluir opcao para importar dados de cartao, atualmente o cartao xp exporta os dados em csv.


### atividades

[ ] possibilitar associacao de expense e income de forma posterior a criacao

[ ] criar dto para pesquisa de plan month para os dados de expense e income

[ ] criar mecanismo de copia de plan month

[ ] criar mecanismo para sumarizar valores do plan month (fazer na consulta ou consolidar via codigo?)

[x] adicionar status do pagamento no expense (pago ou nao pago) e dia vencimento

[ ] criar estrutura para planejamento mensal

    * essa estrutura deve ter tipos de gastos, se sao fixo ou nao, valor, porcentagem do limite de gasto mensal
    * esse tipo de gasto deve poder ser associado a uma expense
    * deve ter um mecanismo para gerar um consolidade verificando se os gastos mensais estao repeitando o planejamento

#### frontend

[ ] criar telas de crud


    
