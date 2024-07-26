# Considerações Gerais

Você deverá usar este repositório como o repo principal do projeto, i.e., todos os seus commits devem estar registrados aqui, pois queremos ver como você trabalha.

A escolha de tecnologias é livre para a resolução do problema. Utilize os componentes e serviços que melhor domina, pois, a apresentação na entrega do desafio deverá ser como uma aula em que você explica em detalhes cada decisão que tomou.

Registre tudo: testes que foram executados, ideias que gostaria de implementar se tivesse tempo (explique como você as resolveria, se houvesse tempo), decisões que foram tomadas e seus porquês, arquiteturas que foram testadas e os motivos de terem sido modificadas ou abandonadas. Crie um arquivo COMMENTS.md ou HISTORY.md no repositório para registrar essas reflexões e decisões.


# O Problema

O desafio que você deve resolver é criação, implantação e monitoramento de uma API simples de Comentários (apenas backend) usando linguagem e ferramentas open source da sua preferência. 

Você precisa criar o ambiente de execução desta API com o maior número de passos automatizados possível, inclusive a esteira de deploy. 

Você deve instrumentar a aplicação/ambiente de execução e demonstrar ao menos um dashboard simples para acompanhamento da operação da aplicação. 

A aplicação será uma API REST que deverá ser disponibilizada neste repositório. Caso não tenha muito domínio de técnicas de programação, pode utilizar ferramentas que auxiliem em parte com a geração de código, mas é imprecindível demonstrar ao menos a capacidade de ajustar e personalizar o código gerado. O objetivo da API é permitir que internautas enviem comentários em texto a respeito de uma máteria e acompanhem o que outras pessoas estão falando sobre o assunto em destaque. Para isso, a API deve conter ao menos 2 rotas, uma para inserção de comentários e uma para listagem dos comentários feitos sobre o assunto. 

Os comandos de interação com a API são os seguintes:

* Criando e listando comentários por matéria

```
# matéria 1
curl -sv <host>:<port>/api/comment/new -X POST -H 'Content-Type: application/json' -d '{"email":"alice@example.com","comment":"first post!","content_id":1}'
curl -sv <host>:<port>/api/comment/new -X POST -H 'Content-Type: application/json' -d '{"email":"alice@example.com","comment":"ok, now I am gonna say something more useful","content_id":1}'
curl -sv <host>:<port>/api/comment/new -X POST -H 'Content-Type: application/json' -d '{"email":"bob@example.com","comment":"I agree","content_id":1}'

# matéria 2
curl -sv <host>:<port>/api/comment/new -X POST -H 'Content-Type: application/json' -d '{"email":"bob@example.com","comment":"I guess this is a good thing","content_id":2}'
curl -sv <host>:<port>/api/comment/new -X POST -H 'Content-Type: application/json' -d '{"email":"charlie@example.com","comment":"Indeed, dear Bob, I believe so as well","content_id":2}'
curl -sv <host>:<port>/api/comment/new -X POST -H 'Content-Type: application/json' -d '{"email":"eve@example.com","comment":"Nah, you both are wrong","content_id":2}'

# listagem matéria 1
curl -sv <host>:<port>/api/comment/list/1

# listagem matéria 2
curl -sv <host>:<port>/api/comment/list/2
```
OBS: Subistituir \<host\>:\<port\> com o respectivo endereço e porta onde a sua API for disponibilizada. 

Você é livre para implementar outras rotas, se desejar.


# O que será avaliado na sua solução?

* Capacidade de programar ou ajustar o código da API usando uma linguagem de programação

* Automação da infra, provisionamento dos hosts 

* Automação de setup e configuração dos hosts

* Pipeline de CI/CD automatizado

* Monitoração básica da aplicação 


# Dicas

Use ferramentas e bibliotecas open source, mas documente as decisões e porquês;

Automatize o máximo possível;

Em caso de dúvidas, pergunte.

# Escolhas das Ferramentas e Processos

* Utilizamos um Dockerfile para execução do build da aplicação para facilitar e reduzir a pipeline, onde a mesma pipeline pode ser utilizada em outros repositórios que sigam o padrão de conter um Dockerfile
* Foi utilizado o Github Actions pois o repositório fornecido é no Github, além das facilidades, rapidez na implementação e disponibilidade de horas de execução nos runners
* Docker hub foi usado como registry de imagem por ser uma ferramenta Open Source, no caso das imagens geradas estão publicas, mas para um ambiente controlado interno a empresa é mandatório o uso de imagens privadas para que seus sistema não fique disponivel para o mundo.
* Foi utilizado manifestos de kubernetes para facilitar a implementação da aplicação escolhida e do banco de dados Postgres que complementa o funcionamento desta aplicação
* Devido a não disponibilidade de secrets dentro do github a atualização automática dos manifestos via pipeline não foi possivel, uma vez que é necessário ter credenciais para acesso dos repo. Nesse caso, a atualização dos manifestos e de start manual e execução via shell script
* O cluster de kubernetes foi provisionado via minikube por ser open source executado localmente no meu notebook
* O uso de ingress foi impossibilitado devido a restrição no minuke ao criar rotas locais para apontamento de DNS (O ingress controller foi instalado com sucesso, adicionado ingress para aplicação porém o problema com o minkube impossibilitou acessar)
* Como medida de acesso a aplicação provisionada, foram feitos via shell script o start de portforward local onde se possibilita acessar a aplicação e o monitoramento via localhost
* Foi utilizado a ferramenta helm para implantar outras ferramentas no cluster de kubernetes, devido a agilidade de implantação é gerenciamento
* A Stack de monitoria (Prometheus e Grafana) utilizada foi levado em consideração por ser open source e de fácil instrumentação



# Melhorias Listadas

* Uso de kubernetes gerenciado dentro de um Cloud Provider (Provisionados via IAC)
* Uso do ingress para configurações de diversos paths e hosts
* Utilizar secrets para armazenar token do Github possibilitando a interação do pipeline com repositórios do Github
* Aderir o uso de env e secrets para guardar dados de credenciais, senhas e token no geral que possam ser utilizadas na pipeline
* Utilizar uma ferramenta a parte para CD, como por exemplo o Argocd que através do kustomize possibilita o uso de templates base na parte de manifestos pro kubernetes
* O uso do Argocd também possibilita trabalhar o GitOps, onde tanto aplicações quanto ferramentas podem ser implementadas no cluster de kubernetes
* Utilizar o Istio para parte de ingress e possibilitando uma melhor visibilidade dos serviços através do uso do Kiali