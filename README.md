# Documentación del Sistema de Gestión de Precios de Inditex

Índice: 
1) Introducción
2) Objetivos de la Aplicación
3) Arquitectura General
4) Flujo de la Aplicación
5) Configuración de OpenAPI
6) Despliegue y Pruebas

# 1. Introducción
Esta documentación detalla el diseño y la arquitectura del Sistema de Gestión de Precios desarrollado como parte de la prueba técnica para Inditex. El sistema se desarrolló utilizando el marco Spring Boot y se ajusta a los patrones de diseño de microservicios.

![AppSeguros - InditexsrctestjavacominditexPriceTest java - Eclipse IDE](https://github.com/luiscalderon1994/inditext-technical/assets/143569057/138dbf43-4b27-440e-a9ba-e3e305e7d46a)



# 2. Objetivos de la Aplicación
El objetivo principal del sistema es proporcionar una API RESTful para determinar el precio aplicable de un producto en un momento determinado, en función de varios parámetros como el ID del producto, el ID de la marca y la fecha y hora.

# 3. Arquitectura General
3.1 Controlador:
El PriceController es la entrada principal de la API, y expone un endpoint HTTP GET para consultar precios.

3.2 Servicio:
El PriceService define la lógica empresarial para encontrar el precio aplicable más relevante, utilizando el repositorio para consultar datos.

3.3 Repositorio:
El PriceRepository se encarga de interactuar con la base de datos utilizando JPA y proporciona métodos para consultar precios aplicables.

3.4 Swagger 3: 
Se utiliza OpenAPI para documentar la API y proporcionar una interfaz interactiva para probarla.

![Swagger UI - Google Chrome](https://github.com/luiscalderon1994/inditext-technical/assets/143569057/15b60d4d-0dd0-4f76-8f0d-caacd1957a74)


Enlace a la api de swagger con el proyecto iniciado -> http://localhost:8080/swagger-ui/index.html#/

3.5 Circuit Breaker: 
En nuestro caso, hemos aplicado un Circuit Breaker al método findApplicablePrice, que es responsable de encontrar el precio aplicable para un producto dado en una marca y tiempo específicos. Si este método falla por cualquier motivo (por ejemplo, si la base de datos está inactiva), el Circuit Breaker se activará y llamará al método de fallback fallbackForFindApplicablePrices.
![localhost8080actuatorhealth - Google Chrome](https://github.com/luiscalderon1994/inditext-technical/assets/143569057/3908a53b-97c9-4e91-8449-365eb281166d)


Link para verificar la salud del servicio -> http://localhost:8080/actuator/health

El método de fallback está diseñado para manejar el fallo de una manera más controlada. En este caso, simplemente devolvemos null, pero podríamos diseñar estrategias más robustas como devolver un precio por defecto, registrar el error para futuras investigaciones, etc.

3.6 Base de datos H2: La tabla PRICE contiene información sobre los precios de los productos para diferentes marcas y en diferentes momentos del tiempo. Aquí está el desglose de cada columna:

1) ID: Identificador único para cada entrada de precio.
2) BRAND_ID: Identificador de la marca a la que pertenece el producto.
3) START_DATE: Fecha y hora en que comienza a aplicarse este precio.
4) END_DATE: Fecha y hora en que finaliza la aplicación de este precio.
5) PRICE_LIST: Identificador de la lista de precios a la que pertenece este precio.
6) PRODUCT_ID: Identificador del producto al que se aplica este precio.
7) PRIORITY: Un número que indica la prioridad de este precio en caso de solapamiento de fechas.
8) PRICE: El precio del producto.
9) CURR: La moneda en la que se expresa el precio (en este caso, EUR para euros).

![H2 Console - Google Chrome](https://github.com/luiscalderon1994/inditext-technical/assets/143569057/5921ca2c-bbac-4a03-a90d-e469977472a4)



   


# 4. Flujo de la Aplicación
El usuario envía una solicitud HTTP GET al endpoint /api/prices.
PriceController procesa la solicitud y llama a PriceService.
PriceService consulta PriceRepository para obtener los precios aplicables.
Se selecciona el precio con la prioridad más alta y se devuelve como respuesta.
En caso de fallo, el circuit breaker se activa y se ejecuta un método de fallback.


#5. Configuración de Swagger
El sistema utiliza OpenAPI para generar documentación en tiempo real de la API. Esta documentación está disponible en una URL específica y se puede utilizar para probar la API interactivamente.

# 6. Despliegue y Pruebas
1.1) Despliegue
La aplicación se despliega en un entorno local, lo cual es ideal para el desarrollo y las pruebas rápidas. No se requiere ninguna configuración especial para el entorno de despliegue más allá de tener instalado Java y cualquier IDE que soporte Spring Boot.

2.2) Pruebas
Para las pruebas, se utilizó JUnit 5, que es un marco de pruebas ampliamente utilizado para Java que ofrece funcionalidades para escribir pruebas simples y a la vez potentes.

![Correo Luis Calderon - Outlook](https://github.com/luiscalderon1994/inditext-technical/assets/143569057/85a4e054-d986-4c93-94c5-3a944a871acc)


3.3) Detalles de las Pruebas
Se realizaron 5 pruebas unitarias para verificar la lógica de selección de precios en diferentes escenarios:

3.3.1) Test 1: Verifica si el sistema devuelve el precio correcto cuando se realiza una petición a las 10:00 del día 14 para el producto 35455 y la marca 1 (ZARA). Se espera un precio de 24.50 EUR.

3.3.2) Test 2: Similar al Test 1, pero la petición se realiza a las 16:00 del día 14. Se espera un precio de 25.45 EUR.

3.3.3) Test 3: La petición se realiza a las 21:00 del día 14. Se espera un precio de 26.45 EUR.

3.3.4) Test 4: La petición se realiza a las 10:00 del día 15. Se espera un precio de 27.50 EUR.

3.3.5) Test 5: La petición se realiza a las 21:00 del día 16. Se espera un precio de 28.50 EUR.

Cada una de estas pruebas utiliza un método común, testCommon, que realiza las afirmaciones necesarias para asegurarse de que el precio devuelto es el correcto según las condiciones del test.

Cómo se Relaciona con el Input Dado
Las pruebas se diseñaron específicamente para abordar los casos de uso proporcionados en el input. Cada test representa un escenario específico en el que un usuario podría querer consultar el precio de un producto para una marca en un momento específico. Estas pruebas aseguran que la lógica de negocio para determinar el precio más relevante está funcionando como se espera.

Al pasar todas estas pruebas, se puede tener confianza en que la implementación es sólida y cumple con los requisitos dados.

![AppSeguros - InditexsrctestjavacominditexPriceTest java - Eclipse IDE_2](https://github.com/luiscalderon1994/inditext-technical/assets/143569057/8ff2f8a0-4316-4b18-9621-e7004a48d9d6)

