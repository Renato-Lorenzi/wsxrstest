## Introdução
O presente trabalho tem como objetivo realizar um experimento. A ideia é contribui com uma medição simples e prática, para uma velha discussão que é qual tecnologia utilizar, SOAP/XML ou REST/JSON. 

SOAP que originalmente foi definido como Simple Object Access protocol, é uma especificação para troca de informação estruturada na implementação de serviços na WEB. Conta com um conjunto de informações no formato XML  para suas mensagens e geralmente é utilizado em cima da camada HTTP ou SMTP para negociação e transmissão de mensagens(SOAP, 2014).

REST(Representational state transfer) é um estilo arquitetural que concebido para aplicações em rede. A principal idéia é ao invés de usar macanismos complexos para realizar interoperabilidade entre máquinas (linguagens, sistemas operacionais) utilizar apenas o protocolo HTTP, com todos seus verbos, para tal (REST, 2014).

Para realização desse trabalho foi utilizado como base as comparações realizadas por Mike Rozlog (2013), Lima (2012) e Bigolin (2012). Todos os trabalhos citados tem como objetivo realizar uma comparação entre a arquitetura REST e SOAP.

## Metodologia 
Para realização do trabalho foi utilizada a plataforma Java. Para tal, foram utilizadas as especificações JAX-RS e JAX-WS.  Ambas especificações se tratam de uma API especificada pela JCP (Java Community Process), com o objetivo de simplificar a implementação de aplicações REST (no caso do JAX-RS) e SOAP (no caso do JAX-WS).

A idéia foi criar um serviço simples que recebe uma entrada de dados, realiza a conversão desses dados para um objeto e faz persistencia do mesmo em um banco de dados postgresql (2014) usando JPA (Java Persistence API). Após isso retorna o objeto inserido no banco de dados. O principal objetivo foi deixar o serviço mais próximo de um uso real, por isso a utilização das especificações JAX-RS, JAX-WS e JPA.

O código do serviço é exatamente o mesmo tanto para o serviço REST quanto para o serviço SOAP, abaixo o código do serviço REST:

```java

    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
    public Dojo insertDojo(@FormParam("dateTime") String dateTime, @FormParam("moderator") String moderator, @FormParam("dojoLink") String dojoLink, @FormParam("constCenter") String costCenter) {
        Dojo dojo = new Dojo(dateTime, moderator, dojoLink, costCenter);
        PersistenceUtils.persist(dojo);
        return dojo;
    }


```

Código do serviço SOAP:

```java

    @WebMethod
    public Dojo insertDojo(@WebParam(name = "dateTime") String dateTime, @WebParam(name = "moderator") String moderator, @WebParam(name = "dojoLink") String dojoLink, @WebParam(name = "costCenter") String costCenter) {
        Dojo dojo = new Dojo(dateTime, moderator, dojoLink, costCenter);
        PersistenceUtils.persist(dojo);
        return dojo;
    }

```

### Obtenção de métricas
Para realizar as medições das métricas foi utilizado a ferramenta JMetter (2014) com o plugin PerfMonMetrics. Segundo Mulugeta (2014), as méticas que devem ser consideradas ao verificar a performance de um web service são:

* Latência: é a diferença de tempo entre o início da requisição e o momento em que seu retorno está acessível;
* Throughput:  (number of requests) / (total time);
* Taxa de erro: identifica a confiabilidade do serviço;

Além das métricas já citadas, foram avalidados métricas de consumo de memória e CPU durante a realização dos testes.

### Ferramantas/Bibliotecas utilizadas:
Como implementação para o JAX-RS foi utilizado a biblioteca jersey (2014). Para implementação para o JAX-WS foi utilizada a implementação de referência jaxws-rt (2014).

Para camada de persistencia foi utilizada a especificação JPA (Java Persistence API). A implementação da especificação utilizada foi a biblioteca EclipseLink (2014). A escolha por essa biblioteca foi realizada por ela ser a implementação de referência sendo assim uma versão mais leve do que outras como o Hibernate, por exemplo.

Todo o desenvolvimento foi realizado em uma máquina com sistema operacional Arch Linux. Abaixo as tabelas com as principais ferramentas/bibliotecas utilizadas utilizadas com suas respectivas versões.

|Ferramantas            | Versão  | Descrição                             |
|-----------------------|:-------:|--------------------------------------:|  
|Maven                  |3.2.3    | Gerenciador de dependências e build   |
|JMeter                 |2.11     | Testes não funcionais (obter métricas)|
|postgresql             |9.3.5    | Banco de dados utilizado nos testes   | 
|Eclipse Mars           |4.5      | IDE usada no desenvolvimento          |
|curl                   |7.8      | Usado para realizar chamadas http     |


|Bibiliotecas           | Versão  | Descrição                             |
|-----------------------|:-------:|--------------------------------------:|  
|Tomcat embeded         |7.0.34   | Lib para execução embarcada do Tomcat |
|jersey                 |2.12     | Implementação do JAX-RS               |
|jaxws-rt               |2.1.3    | Implementação de referência do JAX-WS |
|eclipselink            |2.5.0    | Implementação de referência do JPA    |


## Resultados
Para medição dos resultados foram utilizados dois planos de teste no JMeter, um para o teste com API REST e o outro para API SOAP. Os planos são identicos mudando apenas a chamada para API. Os planos simulavam 5 usuários realizando 500 requisições para o mesmo serviço o que resulta em 2500 requisições e foram executados em um notebook core 2 duo com o sistema operacional Arch Linux.

Os resultados em relação a performance são:

|        |Latência (Média)|Throughput|KB/SEC |Bytes (Média)|
|--------|:--------------:|:--------:|:-----:|------------:|  
|  SOAP  |       20       |  208     |   75  |  367        |
|  REST  |       20       |  209     |   52  |  253        |


Como pode ser visto na tabela acima o REST teve um Throughput 2.09% maior, porém por trafegar mais dados o SOAP acabou tendo uma taxa de KB/SEC maior do que o REST. A principal diferença foi relação a quantidade de bytes trafegada, que foi 45% maior no caso da API SOAP devido ao formalismo envolvido na especificação SOAP. Em relação a latência as duas APIs tiveram o mesmo resultado.

O resultado em relação a uso de CPU é apresentado no gráfico abaixo:

[gráfico]

Como pode ser visto não houve grande diferença entre o consumo de CPU do SOAP em relação, porém o que pode-se destacar é que o acabou consumindo menos.


O resultado em relação a uso de memória é apresentado no gráfico abaixo:

[gráfico]



Como pode ser visto o resultado em relação ao consumo de memória foi praticamente o mesmo. A diferença que o REST teve um pico de uso de memória um pouco maior em relação ao REST. Nos dados apresentados está sendo desconsiderado o consumo de memória real do processo e apenas está sendo apresentado a variação do inicio ao fim do teste.


## Conclusão
Conforme apresentado nos resultados o que pode-se concluir é que a diferença que mais destaca o REST é em relação ao tráfego de dados onde para realizar a mesma ação foi gasto 45% a mais de bytes com SOAP. Uma surpresa foi em relação ao uso de CPU onde SOAP teve um destaque consumindo relativamente menos CPU durante todo o processo.

Pontos fortes REST:
* Simplicidade de implementação e entendimento visto que apenas aplica os verbos HTTP;
* Redução significativa no tráfego de dados;
* Ótimo para operações stateless;

Pontos fortes SOAP:
* Processamento stateful;
* Contratos formais: Caso tenha que se manter um contrato formal entre as partes é o ideal;


O que pode se afirmar é que a escolha entre uma e outra irá depender do problema a ser resolvido visto que as duas trazem benefício e apresentam problemas. Em relação a performance e memória não foi encontrado nada que inviabiliza-se o uso de alguma delas.


## Referências
REST
http://rest.elkstein.org/

SOAP
http://en.wikipedia.org/wiki/SOAP

http://www.infoq.com/br/articles/rest-soap-when-to-use-each

Jersey
https://jersey.java.net/


Mike Rozlog
http://www.infoq.com/br/articles/rest-soap-when-to-use-each

Jean Carlos Rosário Lima
http://www.fatecsp.br/dti/tcc/tcc00056.pdf

Marcio Bigolin
http://saloon.inf.ufrgs.br/twiki-data/Disciplinas/CMP167/TF12MarcioBigolin/Textofinal.pdf

jaxws-rt
https://jax-ws.java.net/

EclipseLink
http://www.eclipse.org/eclipselink/

postgresql
http://www.postgresql.org/


Arch Linux
https://www.archlinux.org/

Mesfin Mulugeta
http://www.edugarage.com/download/attachments/25369779/DEVCON08-Web-ServiceTesting.pdf


PerfMonMetrics
http://jmeter-plugins.org/wiki/PerfMonMetrics/