# Insurance API

Projeto Spring Boot para consulta de seguros.

### Arquitetura (MVC)
- Criar regras para cada seguro e salvar no banco de dados.

### Arquitetura (MVC)
```tree
├── api
│   ├── exceptionhandler
│   │   ├── ApiError.java
│   │   └── ApiExceptionHandler.java
│   ├── payload
│   │   ├── CustomerDTO.java
│   │   ├── CustomerRequest.java
│   │   ├── CustomerResponseDTO.java
│   │   ├── CustomerResponse.java
│   │   └── InsuranceDTO.java
│   └── resource
│       └── InsuranceResource.java
├── config
├── domain
│   ├── factory
│   │   └── insurance
│   │       ├── InsuranceBasicFactory.java
│   │       ├── InsuranceEnum.java
│   │       ├── InsuranceFactory.java
│   │       ├── InsurancePartialFactory.java
│   │       └── InsuranceTotalFactory.java
│   ├── model
│   │   ├── Customer.java
│   │   ├── InsuranceBasic.java
│   │   ├── Insurance.java
│   │   ├── InsurancePartial.java
│   │   └── InsuranceTotal.java
│   └── service
│       ├── impl
│       │   └── InsuranceServiceImpl.java
│       └── InsuranceService.java
├── infra
│   └── exception
│       └── BusinessException.java
├── InsurancesApiApplication.java

```
### Design Patterns
- O padrão Factory é aplicado em certas classes, notavelmente na `InsurancePartialFactory`, onde é utilizado para validar diferentes condições para criação ou não de um objeto
  
   ```java
   public class InsuranceTotalFactory implements InsuranceFactory{

    private static final Logger logger = LoggerFactory.getLogger(InsuranceTotalFactory.class);

    @Override
    public Optional<Insurance> getInsuranceByCustomer(Customer customer) {

        if(customer.getVehicleValue() > 100000){
            logger.info("c=InsurancePartialFactory, m=getInsuranceByCustomer, rule={vehicleValue >= 100000}");
            return Optional.of(new InsuranceTotal());
        }
        return Optional.empty();
    }
}

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- [Java](https://www.oracle.com/java/technologies/javase-downloads.html) (versão 17 ou superior)
- [Gradle](https://gradle.org/install/) (versão 8.5 ou superior)

## Executar Localmente usando Docker

[Docker Hub](https://hub.docker.com/r/julioosilva97/insurances-api)

  ```bash
  docker pull julioosilva97/insurances-api:latest
  docker run -p 8080:8080 julioosilva97/insurances-api:latest

  ```

## Configuração do Ambiente de Desenvolvimento

1. Clone este repositório:

    ```bash
    git clone https://github.com/julioosilva97/insurance-api-exemple
    cd insurance-api-exemple
    ```

2. Execute o aplicativo:

    ```bash
    ./gradlew bootRun
    ```

O aplicativo estará disponível em `http://localhost:8080`.

## Executar Testes

Execute os testes usando o Gradle:

  ```bash
    ./gradlew test
   ```

## Executar Relatórios do Jacoco

Execute os testes usando o Gradle:

  ```bash
    ./gradlew clean test jacocoTestReport
   ```
Abra o relatório em build/reports/jacoco/test/html/index.html no navegador.

