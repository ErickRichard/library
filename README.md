<div class="w3-container">

  <h1 align="left">Library Application</h1>
  Este é um projeto Spring Boot que utiliza Java 21 e PostgreSQL como banco de dados, com integração de relatórios de cobertura de código usando JacocoReport.

<h2 class="w3-text-green w3-xxlarge">1. Pré-requisitos</h2>
Antes de rodar o projeto, você precisará garantir que possui as seguintes ferramentas instaladas:
  <ul>
    <li>Java 21</li>
    <li>Maven</li>
  </ul>

<h2 class="w3-text-green w3-xxlarge">2. Instalar Dependências</h2>
Utilize o Maven para fazer o build e instalar as dependências do projeto:
  <pre style="background-color: #f3f3f3; padding: 10px; border-radius: 5px; border: 1px solid #d1d1d1; font-family: 'Courier New', Courier, monospace; white-space: pre-wrap; margin-left: 0; width: fit-content; height: auto; overflow: auto;">
      <code>mvn clean install</code>
  </pre>

<h2 class="w3-text-green w3-xxlarge">3. Banco de Dados</h2>
O projeto está configurado para utilizar PostgreSQL. Verifique as configurações de conexão abaixo:
  <ul>
    <li>URL: <code>jdbc:postgresql://localhost:5432/library</code></li>
    <li>Username: <code>postgres</code></li>
    <li>Password: <code>1234567</code></li>
  </ul>

<h2 class="w3-text-green w3-xxlarge">4. Documentação da API</h2>
A documentação completa dos endpoints está disponível através do Swagger UI. Após subir o projeto, acesse a URL abaixo:
  <p><a href="http://localhost:8080/swagger-ui/index.html">http://localhost:8080/swagger-ui/index.html</a></p>

</div>