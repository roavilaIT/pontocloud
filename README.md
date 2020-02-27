# pontocloud - o Ponto Eletronico para Cloud

API para os colaboradores baterem ponto

#### Endpoints da aplicacao

**Abaixo os endpoints da api para testar com curl:**

http://localhost:8080/pontocloud/v1/colaboradores

http://localhost:8080/pontocloud/v1/pontos/{codigodocolaborador}
 
#### Banco de dados

**Base H2** com user e password no application.properties

username=pontocloud

password=cifraR$$


#### Curls de exemplo:

#### Colaboradores

**Consultar os colaboradores:**

curl -X GET "http://localhost:8080/pontocloud/v1/colaboradores" -H "accept: application/json"


**Adicionar:**

curl -X POST "http://localhost:8080/pontocloud/v1/colaboradores" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"id\":1,\"nome\":\"roger\",\"cpf\":\"123.123.567-98\",\"email\":\"roger@itau.com.br\",\"dataCadastro\":\"20-02-2020\"}"


**Alterar:**

curl -X PUT "http://localhost:8080/pontocloud/v1/colaboradores/1" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"nome\":\"josefa\",\"cpf\":\"231.992.343-00\",\"email\":\"oba@oi.com.ac\"}"


**Consultar um colaborador especifico:**

curl -X GET "http://localhost:8080/pontocloud/v1/colaboradores/1" -H "accept: application/json"


#### Pontos

**Adicionar:**

curl -X POST "http://localhost:8080/pontocloud/v1/pontos" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"colaborador\":1}"


**Relatorio de horas:**

curl -X GET "http://localhost:8080/pontocloud/v1/pontos/1" -H "accept: application/json"
