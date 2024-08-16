# Implementação de Nós Sink e Sensor no Sinalgo

## Introdução
Este projeto tem como objetivo simular a comunicação entre uma estação base (nó Sink) e vários sensores (nós Sensor) em uma rede de sensores sem fio utilizando a ferramenta Sinalgo. O Sink é responsável por criar rotas para todos os nós da rede, enquanto os sensores enviam dados de forma distribuída para o Sink.

## Descrição do Projeto
O projeto foi desenvolvido a partir do exemplo `wsn1` no Sinalgo e envolve a criação de dois tipos principais de nós:

- **Nó Sink**:
  - **Função Principal**: Criar uma rota para todos os nós sensores na rede.
  - **Comportamento**: No tempo `t=1` da simulação, o nó Sink envia uma mensagem de broadcast para todos os nós, estabelecendo as rotas. Ele também recebe e processa mensagens de dados de todos os nós sensores.

- **Nó Sensor**:
  - **Função Principal**: Receber a mensagem de rota do nó Sink e enviar pacotes de dados para o Sink.
  - **Comportamento**: Após receber a mensagem de roteamento do Sink, o nó Sensor armazena a rota e, em cada passo de simulação, envia pacotes de dados para o nó Sink.

## Implementação
A implementação consiste em duas classes principais:

- **WsnMsg**: Classe utilizada para criar mensagens que são trocadas entre os nós da rede.
- **SimpleNode**: Classe que representa os nós da rede, configurados como nós Sink ou Sensor, definindo seu comportamento ao enviar e receber mensagens.

## Configuração da Simulação
O arquivo de configuração `wsn1_config.xml` foi ajustado para garantir que os nós criados sejam do tipo `SimpleNode`. A simulação deve ser inicializada com um nó `SimpleNode` configurado como Sink e os demais nós como sensores.

## Testes e Validação

### Teste de Roteamento
- **Objetivo**: Verificar se o nó Sink consegue estabelecer rotas com todos os sensores.
- **Procedimento**: Iniciar a simulação e observar se todos os sensores recebem a mensagem de roteamento do Sink.

### Teste de Envio de Dados
- **Objetivo**: Verificar se os sensores conseguem enviar pacotes de dados ao Sink.
- **Procedimento**: Observar no console se o Sink está recebendo os pacotes de dados de cada sensor.

### Teste de Loop
- **Objetivo**: Garantir que não há loops no encaminhamento de mensagens.
- **Procedimento**: Revisar a implementação para assegurar que o controle de loop está corretamente aplicado.

## Conclusão
O projeto foi implementado com sucesso, permitindo que o nó Sink crie rotas para todos os nós da rede e que os sensores enviem pacotes de dados ao Sink sem loops ou erros de roteamento. A simulação no Sinalgo demonstrou que as funcionalidades foram implementadas de forma eficaz.

### Aluno: Sidney Alexandre Ferracin Junior - 2348160