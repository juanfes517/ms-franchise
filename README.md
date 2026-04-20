# ms-franchise

Microservicio de franquicias, sucursales y productos.

## Arquitectura

El proyecto sigue una **arquitectura hexagonal (puertos y adaptadores)**: el **dominio** define casos de uso y contratos (puertos), la **capa de aplicación** orquesta flujos y expone DTOs, y la **infraestructura** implementa adaptadores de entrada (REST con Spring WebFlux) y de salida. La **persistencia está en Amazon DynamoDB**, accedida mediante el SDK de AWS y adaptadores en `infrastructure.adapter.output.dynamodb`.

### Modelo de datos en DynamoDB (una sola tabla)

Se usa **una única tabla** (`franchise-system`) para almacenar **franquicias**, **sucursales** y **productos** (patrón *single-table design*).

- **Producto:** cada producto tiene un **identificador único** en `partitionKey` y se **vincula a su sucursal** mediante el `sortKey`, que guarda el id de la sucursal.
- **Sucursal:** cada sucursal tiene un **identificador único** en `partitionKey` y se **vincula a su franquicia** mediante el `sortKey`, que guarda el id de la franquicia.
- **Franquicia:** el registro de franquicia también usa `partitionKey` y `sortKey` con el **mismo id de franquicia** en ambos atributos, de modo que convive en la misma tabla con el resto de ítems.

Los ids llevan prefijos (`FRANCHISE#`, `BRANCH#`, `PRODUCT#`) para distinguir tipos de entidad en consultas y validaciones.

## Ejecución en local

Swagger UI (misma URL en ambas opciones):

**http://localhost:8080/api/v1/swagger-ui/index.html**

---

### Opción 1: LocalStack (DynamoDB local)

1. Desde la **raíz del proyecto**, levanta los servicios:

   ```bash
   docker compose up -d --build
   ```

   Esto arranca LocalStack con DynamoDB y la aplicación (ver `docker-compose.yml`).

2. Abre en el navegador la URL de Swagger indicada arriba.

---

### Opción 2: Tabla en AWS con Terraform

1. Configura en tu equipo las **credenciales de AWS** (por ejemplo variables de entorno o perfil en `~/.aws` / `%USERPROFILE%\.aws`).

2. Ve a la carpeta de infraestructura (en este repositorio la ruta es **`IaC/terraform`**) y aplica Terraform:

   ```bash
   cd IaC/terraform
   terraform init
   terraform apply
   ```

3. Con la tabla DynamoDB ya creada en AWS, vuelve a la **raíz del proyecto** y construye la imagen:

   ```bash
   docker build -t ms-franchise:latest .
   ```

4. Ejecuta el contenedor montando tus credenciales de AWS (ejemplo para **Windows**; sustituye `<TU_USUARIO>` por tu usuario de Windows):

   ```bash
   docker run -d -p 8080:8080 -v C:\Users\<TU_USUARIO>\.aws:/root/.aws:ro -e AWS_PROFILE=default ms-franchise:latest
   ```

5. Abre la misma URL de Swagger: **http://localhost:8080/api/v1/swagger-ui/index.html**

> **Nota:** En Linux o macOS, ajusta el volumen `-v` a la ruta de tu carpeta `.aws` local.

## Endpoints de la app

Base path de la API: **`/api/v1`**

- **Health**
   - `GET /api/v1/health`
   - Recibe: sin parámetros.

- **Franchise**
   - `POST /api/v1/franchise`
   - Recibe body:
     ```json
     {
       "name": "string"
     }
     ```
   - `PATCH /api/v1/franchise`
   - Recibe body:
     ```json
     {
       "id": "string",
       "name": "string"
     }
     ```

- **Branch**
   - `POST /api/v1/branch`
   - Recibe body:
     ```json
     {
       "name": "string",
       "franchiseId": "string"
     }
     ```
   - `PATCH /api/v1/branch`
   - Recibe body:
     ```json
     {
       "id": "string",
       "name": "string",
       "franchiseId": "string"
     }
     ```
   - `GET /api/v1/branch/franchises/{franchiseId}/branches/max-stock-products`
   - Recibe path param:
      - `franchiseId` (string)

- **Product**
   - `POST /api/v1/product`
   - Recibe body:
     ```json
     {
       "name": "string",
       "stock": 0,
       "branchId": "string"
     }
     ```
   - `PATCH /api/v1/product`
   - Recibe body:
     ```json
     {
       "id": "string",
       "branchId": "string",
       "stock": 0,
       "name": "string"
     }
     ```
   - `DELETE /api/v1/product?productId={productId}&branchId={branchId}`
   - Recibe query params:
      - `productId` (string)
      - `branchId` (string)