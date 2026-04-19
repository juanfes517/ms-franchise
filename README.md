# ms-franchise

Microservicio de franquicias, sucursales y productos.

## Arquitectura

El proyecto sigue una **arquitectura hexagonal (puertos y adaptadores)**: el **dominio** define casos de uso y contratos (puertos), la **capa de aplicación** orquesta flujos y expone DTOs, y la **infraestructura** implementa adaptadores de entrada (REST con Spring WebFlux) y de salida. La **persistencia está en Amazon DynamoDB**, accedida mediante el SDK de AWS y adaptadores en `infrastructure.adapter.output.dynamodb`.

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
