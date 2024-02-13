<p align="center"><img src="https://github.com/jahirxtrap/darwincenter/blob/master/media/readme/logo.png" alt="img" style="width: 75%"></p>

## Objetivo

El SMA Darwin Center tiene el potencial de ayudar a los usuarios a mantenerse actualizados sobre las nuevas tecnologías y tendencias.

Este sistema se enfoca en recomendar artículos científicos a los usuarios con el objetivo de mantenerse actualizado en las nuevas tecnologías/tendencias, importante para la adaptabilidad y estar al tanto de la transformación digital, teniendo en cuenta el estilo de aprendizaje, inteligencias múltiples y coeficiente intelectual.

## Modelo conceptual

<p align="center"><img src="https://github.com/jahirxtrap/darwincenter/blob/master/media/readme/modelo_conceptual.png" alt="img" style="width: 60%"></p>

## Estructura

<p align="center"><img src="https://github.com/jahirxtrap/darwincenter/blob/master/media/readme/estructura_1.png" alt="img" style="width: 35%"></p>

- **scr:** Contiene el pseudocódigo inicial del algoritmo de recomendación y recursos como el ícono y logo del sistema. Además, contiene los paquetes darwincenter y modelo.
- **darwincenter:** Es el paquete principal del sistema, contiene los dos agentes utilizados (AgenteUsuario y AgenteRecomendación), además del agente principal (Main) y las clases de interfaz de usuario (MainFrame y LoginFrame).
- **modelo:** Incluye la clase encargada de crear la base de datos (Database) y los objetos necesarios para acceder a los datos en todo el sistema mediante encapsulamiento (Usuario y Doc).

<p align="center"><img src="https://github.com/jahirxtrap/darwincenter/blob/master/media/readme/estructura_2.png" alt="img" style="width: 45%"></p>

- **lib:** Contiene las librerías (JADE, SQLite JDBC y dependencias) necesarias para el funcionamiento del sistema.
- **media:** Es el repositorio local de artículos científicos.
- **database.sqlite:** Es la base de datos del sistema y tiene la siguiente estructura:

<p align="center"><img src="https://github.com/jahirxtrap/darwincenter/blob/master/media/readme/estructura_3.png" alt="img" style="width: 50%"></p>

## Ejecución

1. Ejecutar el sistema desde la clase LoginFrame.

<p align="center"><img src="https://github.com/jahirxtrap/darwincenter/blob/master/media/readme/ejecucion_1.png" alt="img" style="width: 80%"></p>

2. Iniciar sesión con las credenciales (para el administrador: admin "1234").

<p align="center"><img src="https://github.com/jahirxtrap/darwincenter/blob/master/media/readme/ejecucion_2.png" alt="img" style="width: 60%"></p>

3. El sistema analiza el perfil del usuario y ejecuta el algoritmo de recomendación para mostrar una lista de artículos.

<p align="center"><img src="https://github.com/jahirxtrap/darwincenter/blob/master/media/readme/ejecucion_3.png" alt="img" style="width: 70%"></p>

4. Se puede seleccionar un artículo, hacer doble clic, presionar enter o hacer clic en el botón 'Abrir', entonces se utilizará el lector PDF predeterminado para abrir el documento. También se puede cerrar sesión e ingresar con otro usuario.

<p align="center"><img src="https://github.com/jahirxtrap/darwincenter/blob/master/media/readme/ejecucion_4.png" alt="img" style="width: 90%"></p>

## Documentación

- [Presentación](https://docs.google.com/presentation/d/1_50OvRSj6TrAg9GjMWv46iSrrdsyic24HlbR25K0rh8/edit?usp=sharing)
- [Reporte técnico](https://drive.google.com/file/d/1AxKQFIGtyORKKMMpljLK7XUVelDLY3Kj/view?usp=sharing)
