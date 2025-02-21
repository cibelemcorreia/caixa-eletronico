# Changelog

Todas as mudanças neste projeto serão documentadas aqui.

## [2.0.0] - 2025-02-21
### Mudanças Incompatíveis
- Atualização do Java de 8 para 20 (requer JDK 20 para rodar)
- Atualização do Spring Boot de 1.5.3 para 3.2.0 (mudanças estruturais na API)
- Substituição do Springfox Swagger pelo SpringDoc OpenAPI (documentação mudou para `/v3/api-docs`)
- Ajuste do `maven-compiler-plugin` para `release 20` (compilação agora requer Java 20)
- Removida classe `SwaggerConfig` e criada `OpenApiConfig` para compatibilidade com Spring Boot 3

## [1.0.0] - 2023-06-02
### Adicionado
- Primeira versão do projeto