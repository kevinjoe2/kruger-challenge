# Getting Started

### Reference Documentation

- Autor: Kevin Chamorro
- Fecha: 20 Junio 2022
- Asunto: Reto Kruger Backend

Paso 1: Para iniciar con la ejecucion del proyecto es necesario los siguientes requisitos:
- Spring boot 2.6.8
- Intellij Idea
- Gradle ultima version
- Postgresql ultima version

Paso 2: Conar el proyecto

Paso 3: Crear la base de datos Postgresql:
- Instalar en la maquina local un motor postgres
- Dentro del directorio del proyecto existe un subdirectorio script/generateDb.sql y utilizarlo para generar la base de datos.

Paso 4: Dentro de la carpeta del proyecto ejecutar los siguientes comandos:
- gradle clean build
- gradle bootRun

Paso 5: Por defecto se crea un usuario administrador
- Generar el token con el siguiente CURL:
  `curl --location --request POST 'http://localhost:8081/api/login' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --header 'Cookie: JSESSIONID=5267C34455A30BD4747EBAFD983DEA88' \
  --data-urlencode 'username=kChamorro' \
  --data-urlencode 'password=123456'`
- Nota.: El token es necesario para poder acceder a cualquier endpoint

Paso 6: Desde postman se puede importar los siguientes CURL:

1. Crear un empleado:
`curl --location --request POST 'http://localhost:8081/api/employees' \
--header 'Authorization: Bearer TOKEN_HERE' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=5267C34455A30BD4747EBAFD983DEA88' \
--data-raw '{
"identificationCard":"1701859300",
"names":"Juan Pedro",
"lastnames":"Guido Chavez",
"email":"guido@hotmail.com"
}'`

