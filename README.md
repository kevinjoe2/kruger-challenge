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

Paso 2: Clonar el proyecto

Paso 3: Crear la base de datos Postgresql:
- Instalar en la maquina local un motor postgres
- Dentro del directorio del proyecto existe un subdirectorio script/generateDb.sql y utilizarlo para generar la base de datos.

Paso 4: Dentro de la carpeta del proyecto ejecutar los siguientes comandos:
- gradle clean build
- gradle bootRun

### En la raiz del proyecto se encuentra exportado los Endpoint en formato Postman
- `KRUGGER.postman_collection.json`

Paso 5: Por defecto se crea un usuario administrador
- Generar el token con el siguiente CURL:
  `curl --location --request POST 'http://localhost:8081/api/login' \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --header 'Cookie: JSESSIONID=5267C34455A30BD4747EBAFD983DEA88' \
  --data-urlencode 'username=kChamorro' \
  --data-urlencode 'password=123456'`
- *Nota.: El token es necesario para poder acceder a cualquier endpoint*

Paso 6: Desde postman se puede importar los siguientes CURL:

1. Crear un empleado (*UTILIZAR EL TOKEN DEL ADMINISTRADOR GENERADO EN EL PASO ANTERIOR*):
`curl --location --request POST 'http://localhost:8081/api/employees' \
   --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrQ2hhbW9ycm8iLCJyb2xlcyI6WyJST0xFX0FETUlOSVNUUkFUT1IiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL2FwaS9sb2dpbiIsImV4cCI6MTY1NTc1OTEyN30.GJyQ0HEiQUw-4-t3AsG1t5mSkjSw9JakdQBB4nQHvMw' \
   --header 'Content-Type: application/json' \
   --header 'Cookie: JSESSIONID=5267C34455A30BD4747EBAFD983DEA88' \
   --data-raw '{
   "identificationCard":"0401859376",
   "names":"Juan Pedro",
   "lastnames":"Guido Chavez",
   "email":"guido@hotmail.com"
   }'`

2. Generar token del empleado creado (*UTILIZAR EL USUARIO Y CONTRASE??A GENERADO EN EL PASO ANTERIOR*)
   `curl --location --request POST 'http://localhost:8081/api/login' \
   --header 'Content-Type: application/x-www-form-urlencoded' \
   --header 'Cookie: JSESSIONID=5267C34455A30BD4747EBAFD983DEA88' \
   --data-urlencode 'username=juanpedro.guidochavez' \
   --data-urlencode 'password=juanpedro.guidochavez'`

3. Consultar la informacion del empleado (*UTILIZAR EL TOKEN DEL EMPLEADO GENERADO EN EL PASO ANTERIOR*):
   `curl --location --request GET 'http://localhost:8081/api/employees/information' \
   --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFucGVkcm8uZ3VpZG9jaGF2ZXoiLCJyb2xlcyI6WyJST0xFX0VNUExPWUVFIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MS9hcGkvbG9naW4iLCJleHAiOjE2NTU3NTkxOTR9.FPuxj5Qt1vG2WEHD_UJKNtyvV4k4GUjJ1hm1vHaPNX8' \
   --header 'Cookie: JSESSIONID=5267C34455A30BD4747EBAFD983DEA88'`

4. Actualizar informacion del usuario rol empleado (*UTILIZAR EL TOKEN DEL EMPLEADO*)
   `curl --location --request POST 'http://localhost:8081/api/employees/saveEmployee' \
   --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFucGVkcm8uZ3VpZG9jaGF2ZXoiLCJyb2xlcyI6WyJST0xFX0VNUExPWUVFIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MS9hcGkvbG9naW4iLCJleHAiOjE2NTU3NjIxNDd9.__N5E_J9LyX8sONbQ0Zw4jpCSQoh3C8Kh0BHnO0rjHE' \
   --header 'Content-Type: application/json' \
   --header 'Cookie: JSESSIONID=5267C34455A30BD4747EBAFD983DEA88' \
   --data-raw '{
   "dateBirth": "1996-01-21",
   "homeAddress": "La gasca",
   "mobilePhone": "0996427491",
   "statusVaccine": "VACCINATED",
   "vaccines": [
   {
   "type":"Sputnik",
   "date":"2020-01-01",
   "number":"1"
   },
   {
   "type":"AstraZeneca",
   "date":"2020-07-03",
   "number":"2"
   },
   {
   "type":"Pfizer",
   "date":"2021-03-24",
   "number":"3"
   }
   ]
   }'`
5. Para buscar por los parametros (*UTILIZAR EL TOKEN DEL ADMINISTRADOR*):
   - estado de vacunaci??n
   - tipo de vacuna
   - rango de fecha de vacunaci??n
   
   `curl --location --request GET 'http://localhost:8081/api/employees/search?statusVaccine=VACCINATED&typeVaccine=Pfizer&dateFromVaccine=2020-01-01&dateToVaccine=2020-02-01' \
   --header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrQ2hhbW9ycm8iLCJyb2xlcyI6WyJST0xFX0FETUlOSVNUUkFUT1IiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL2FwaS9sb2dpbiIsImV4cCI6MTY1NTc2NTE1OH0.BNKKXD54xdRV8kkVrZaVSEVJLLnZ8PDHNKNB0tvCoVQ' \
   --header 'Cookie: JSESSIONID=5267C34455A30BD4747EBAFD983DEA88'`