import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class Ta18_Main {
    private Connection conexion; 

    public static void main(String[] args) {
        Ta18_Main ejemplo = new Ta18_Main();
        ejemplo.openConnection();
        ejemplo.showMenu();
    }

    public void showMenu() {
    	//Variables usadas:
    	String dbName="";//Nombre de base de datos usada
    	String tableName="";//Nombre de la tabla usada
    	String entradaTeclado;//Valor usada para el scanner
    	List<String> lista = new ArrayList<>();//Lista para añadir datos
    	
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("Menú:");
            System.out.println("1. Crear Base de Datos");
            System.out.println("2. Crear Tabla");
            System.out.println("3. Añadir Parámetros");
            System.out.println("4. Mostrar Valores");
            System.out.println("5. Eliminar Valores");
            System.out.println("6. Salir");
            System.out.println("7. Ejercicio 1");
            System.out.println("8. Ejercicio 2");
            System.out.println("9. Ejercicio 3");
            System.out.println("10. Ejercicio 4");
            System.out.println("11. Ejercicio 5");
            System.out.println("12. Ejercicio 6");
            System.out.println("13. Ejercicio 7");
            System.out.println("14. Ejercicio 8");
            System.out.println("15. Ejercicio 9");
            System.out.print("Ingrese la opción deseada: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                	// Usuario crear base de datos
                    System.out.print("Ingrese el nombre de la base de datos: ");
                    dbName = scanner.nextLine();
                    createDB(dbName);
                    break;
                case 2:
                	// Usuario crear tabla en base de datos
                	System.out.print("Ingrese el nombre de la base de datos: ");
                    dbName = scanner.nextLine();
                    System.out.print("Ingrese el nombre de la tabla: ");
                    tableName = scanner.nextLine();
                    System.out.println("Ingrese los atributos de la tabla uno por uno (Ejemplo: Id Int PRIMARY KEY, Nombre nvarchar(100) o escriba 'fin' para terminar:");
                    entradaTeclado = scanner.nextLine().trim();
                    createTable(dbName, tableName, entradaTeclado);
                    break;
                case 3:
                	// Usuario ingresar datos en tabla
                	System.out.print("Ingrese el nombre de la base de datos: ");
                    dbName = scanner.nextLine();
                    System.out.print("Ingrese el nombre de la tabla: ");
                    tableName = scanner.nextLine();
                    System.out.print("Ingrese valores en formato 'id, nombre' o escriba 'fin' para terminar:");                    List<String> values = new ArrayList<>();
                    while (true) {
                    	entradaTeclado = scanner.nextLine().trim();
                        if (entradaTeclado.equalsIgnoreCase("fin")) {break;} 
                        else {values.add(entradaTeclado);}
                    }
                    if (!values.isEmpty()) {insertData(dbName, tableName, values);}
                    break;
                case 4:
                	// Usuario obtener los valores de las tablas
                	System.out.print("Ingrese el nombre de la base de datos: ");
                    dbName = scanner.nextLine();
                    System.out.print("Ingrese el nombre de la tabla: ");
                    tableName = scanner.nextLine();
                	getValues(dbName, tableName);
                    break;
                case 5:
                	// Usuario eliminar un valor en una tabla
                	System.out.print("Ingrese el nombre de la base de datos: ");
                    dbName = scanner.nextLine();
                    System.out.print("Ingrese el nombre de la tabla: ");
                    tableName = scanner.nextLine();
                	System.out.print("Ingrese la condición de eliminación (Ejemplo: id = 1): ");
                    String condicion = scanner.nextLine();
                    deleteRecord(dbName, tableName, condicion);
                    break;
                case 6:
                	// Cerrar base de datos
                    closeConnection();
                    System.out.println("Adios");
                    scanner.close();
                    return;

                case 7:
                    // Ejercicio 1 Fabricantes
                    createDB("Ejercicio1");

                    // Crear tablas
                    createTable("Ejercicio1", "FABRICANTES", "Codigo Int PRIMARY KEY, Nombre nvarchar(100)");
                    createTable("Ejercicio1", "ARTICULOS", "Codigo Int PRIMARY KEY, Nombre nvarchar(100), Precio Int, Fabricante Int, FOREIGN KEY (Fabricante) REFERENCES FABRICANTES(Codigo)");

                    // Insertar datos en FABRICANTES
                    lista.clear();
                    lista.add("1,'Fabricante1'");
                    lista.add("2,'Fabricante2'");
                    insertData("Ejercicio1", "FABRICANTES", lista);

                    // Insertar datos en ARTICULOS
                    lista.clear();
                    lista.add("1, 'Articulo1', 100, 1");
                    lista.add("2, 'Articulo2', 200, 2");
                    insertData("Ejercicio1", "ARTICULOS", lista);

                    // Realizar una eliminación de ejemplo
                    deleteRecord("Ejercicio1", "ARTICULOS", "Codigo = 1");

                    // Mostrar los valores restantes en las tablas
                    System.out.println("Valores en la tabla FABRICANTES:");
                    getValues("Ejercicio1", "FABRICANTES");

                    System.out.println("Valores en la tabla ARTICULOS:");
                    getValues("Ejercicio1", "ARTICULOS");
                    break;

                
                case 8:
                    // Ejercicio 2 Empleados
                    createDB("Ejercicio2");

                    // Crear tabla DEPARTAMENTOS
                    createTable("Ejercicio2", "DEPARTAMENTOS", "Codigo int PRIMARY KEY, Nombre nvarchar(100), Presupuesto int");

                    // Crear tabla EMPLEADOS
                    createTable("Ejercicio2", "EMPLEADOS", "DNI varchar(8) PRIMARY KEY, Nombre nvarchar(100), Apellidos nvarchar(255), Departamento int, FOREIGN KEY (Departamento) REFERENCES DEPARTAMENTOS(Codigo)");

                    // Insertar datos en DEPARTAMENTOS
                    lista.clear();
                    lista.add("1,'Contabilidad','10000'");
                    lista.add("2,'Marqueting','20000'");
                    insertData("Ejercicio2", "DEPARTAMENTOS", lista);

                    // Insertar datos en EMPLEADOS
                    lista.clear();
                    lista.add("'12345678','Juan','Lopez',1");
                    lista.add("'98765432','Maria','Perez',2");
                    insertData("Ejercicio2", "EMPLEADOS", lista);

                    // Realizar una eliminación de ejemplo
                    deleteRecord("Ejercicio2", "EMPLEADOS", "DNI = '12345678'");

                    // Mostrar los valores restantes en las tablas
                    System.out.println("Valores en la tabla EMPLEADOS:");
                    getValues("Ejercicio2", "EMPLEADOS");

                    System.out.println("Valores en la tabla DEPARTAMENTOS:");
                    getValues("Ejercicio2", "DEPARTAMENTOS");
                    break;

                case 9:
                    // Ejercicio 3 Almacenes
                    createDB("Ejercicio3");

                    // Crear tabla ALMACENES
                    createTable("Ejercicio3", "ALMACENES", "Codigo Int PRIMARY KEY, Lugar nvarchar(100), Capacidad Int");

                    // Crear tabla CAJAS
                    createTable("Ejercicio3", "CAJAS", "NumReferencia char(5) PRIMARY KEY, Contenido nvarchar(100), Valor Int, Almacen Int, FOREIGN KEY (Almacen) REFERENCES ALMACENES(Codigo)");

                    // Insertar datos en ALMACENES
                    lista.clear();
                    lista.add("1,'Almacen1',1000");
                    insertData("Ejercicio3", "ALMACENES", lista);

                    // Insertar datos en CAJAS
                    lista.clear();
                    lista.add("'C1','Juguetes',500,1");
                    lista.add("'C2','Tornillos',200,1");
                    insertData("Ejercicio3", "CAJAS", lista);

                    // Realizar una eliminación de ejemplo
                    deleteRecord("Ejercicio3", "CAJAS", "NumReferencia = 'C1'");

                    // Mostrar los valores restantes en las tablas
                    System.out.println("Valores en la tabla ALMACENES:");
                    getValues("Ejercicio3", "ALMACENES");

                    System.out.println("Valores en la tabla CAJAS:");
                    getValues("Ejercicio3", "CAJAS");
                    break;
                case 10:
                    // Ejercicio 4 Cine
                    createDB("Ejercicio4");

                    // Crear tabla PELICULAS
                    createTable("Ejercicio4", "PELICULAS", "Codigo Int PRIMARY KEY, Nombre nvarchar(100), CalificacionEdad Int");

                    // Crear tabla SALAS
                    createTable("Ejercicio4", "SALAS", "Codigo Int PRIMARY KEY, Nombre nvarchar(100), Pelicula Int, FOREIGN KEY (Pelicula) REFERENCES PELICULAS(Codigo)");

                    // Insertar datos en PELICULAS
                    lista.clear();
                    lista.add("1,'Batman',18");
                    lista.add("2,'Superman',16");
                    insertData("Ejercicio4", "PELICULAS", lista);

                    // Insertar datos en SALAS
                    lista.clear();
                    lista.add("1,'Sala1',1");
                    lista.add("2,'Sala2',1");
                    insertData("Ejercicio4", "SALAS", lista);

                    // Realizar una eliminación de ejemplo
                    deleteRecord("Ejercicio4", "SALAS", "Codigo = 1");

                    // Mostrar los valores restantes en las tablas
                    System.out.println("Valores en la tabla SALAS:");
                    getValues("Ejercicio4", "SALAS");

                    System.out.println("Valores en la tabla PELICULAS:");
                    getValues("Ejercicio4", "PELICULAS");
                    break;

                case 11:
                    // Ejercicio 5 Directores
                    createDB("Ejercicio5");

                    // Crear tabla DESPACHOS
                    createTable("Ejercicio5", "DESPACHOS", "Numero int PRIMARY KEY, Capacidad int");

                    // Crear tabla DIRECTORES
                    createTable("Ejercicio5", "DIRECTORES", "DNI varchar(8) PRIMARY KEY, NomApels nvarchar(255), DNIJefe varchar(8), Despacho int, FOREIGN KEY (Despacho) REFERENCES DESPACHOS(Numero), FOREIGN KEY (DNIJefe) REFERENCES DIRECTORES(DNI)");

                    // Insertar datos en DESPACHOS
                    lista.clear();
                    lista.add("1,10");
                    lista.add("2,15");
                    insertData("Ejercicio5", "DESPACHOS", lista);

                    // Insertar datos en DIRECTORES
                    lista.clear();
                    lista.add("'12345678','Juan Carlos',NULL,1");
                    lista.add("'98765432','Ainara Blanco','12345678',1");
                    lista.add("'87654321','Tony','12345678',2");
                    insertData("Ejercicio5", "DIRECTORES", lista);

                    // Realizar una eliminación de ejemplo
                    deleteRecord("Ejercicio5", "DIRECTORES", "DNI = '87654321'");

                    // Mostrar los valores restantes en las tablas
                    System.out.println("Valores en la tabla DIRECTORES:");
                    getValues("Ejercicio5", "DIRECTORES");

                    System.out.println("Valores en la tabla DESPACHOS:");
                    getValues("Ejercicio5", "DESPACHOS");
                    break;
                    
                case 12:
                    // Ejercicio 6 Piezas
                    createDB("Ejercicio6");

                    // Crear tabla PIEZAS
                    createTable("Ejercicio6", "PIEZAS", "Codigo int PRIMARY KEY, Nombre nvarchar(100)");

                    // Crear tabla PROVEEDORES
                    createTable("Ejercicio6", "PROVEEDORES", "Id char(4) PRIMARY KEY, Nombre nvarchar(100)");

                    // Crear tabla SUMINISTRA
                    createTable("Ejercicio6", "SUMINISTRA", "CodigoPieza int, IdProveedor char(4), Precio int, PRIMARY KEY (CodigoPieza, IdProveedor), FOREIGN KEY (CodigoPieza) REFERENCES PIEZAS(Codigo), FOREIGN KEY (IdProveedor) REFERENCES PROVEEDORES(Id)");

                    // Insertar datos en PIEZAS
                    lista.clear();
                    lista.add("1,'Tornillo'");
                    lista.add("2,'Rosca'");
                    insertData("Ejercicio6", "PIEZAS", lista);

                    // Insertar datos en PROVEEDORES
                    lista.clear();
                    lista.add("1,'IKEA'");
                    lista.add("2,'Media Markt'");
                    insertData("Ejercicio6", "PROVEEDORES", lista);

                    // Insertar datos en SUMINISTRA
                    lista.clear();
                    lista.add("1,1,10");
                    lista.add("2,2,15");
                    insertData("Ejercicio6", "SUMINISTRA", lista);

                    // Realizar una eliminación de ejemplo
                    deleteRecord("Ejercicio6", "PIEZAS", "Codigo = 1");

                    // Mostrar los valores restantes en las tablas
                    System.out.println("Valores en la tabla PIEZAS:");
                    getValues("Ejercicio6", "PIEZAS");

                    System.out.println("Valores en la tabla PROVEEDORES:");
                    getValues("Ejercicio6", "PROVEEDORES");

                    System.out.println("Valores en la tabla SUMINISTRA:");
                    getValues("Ejercicio6", "SUMINISTRA");
                    break;
                    
                case 13:
                    // Ejercicio 7 Científicos
                    createDB("Ejercicio7");

                    // Crear tabla CIENTIFICOS
                    createTable("Ejercicio7", "CIENTIFICOS", "DNI varchar(8) PRIMARY KEY, NomApels nvarchar(255)");

                    // Crear tabla PROYECTO
                    createTable("Ejercicio7", "PROYECTO", "Id char(4) PRIMARY KEY, Nombre nvarchar(255), Horas int");

                    // Crear tabla ASIGNADO_A
                    createTable("Ejercicio7", "ASIGNADO_A", "Cientifico varchar(8), Proyecto char(4), PRIMARY KEY (Cientifico, Proyecto), FOREIGN KEY (Cientifico) REFERENCES CIENTIFICOS(DNI), FOREIGN KEY (Proyecto) REFERENCES PROYECTO(Id)");

                    // Insertar datos de ejemplo en las tablas
                    lista.clear();
                    lista.add("'98765432','Juan Perez'");
                    lista.add("'23456789','Maria Juerez'");
                    insertData("Ejercicio7", "CIENTIFICOS", lista);

                    lista.clear();
                    lista.add("1,'Projecte1',100");
                    lista.add("2,'Projecte2',150");
                    insertData("Ejercicio7", "PROYECTO", lista);

                    lista.clear();
                    lista.add("'98765432',1");
                    lista.add("'23456789',2");
                    insertData("Ejercicio7", "ASIGNADO_A", lista);

                    // Realizar una eliminación de ejemplo
                    deleteRecord("Ejercicio7", "CIENTIFICOS", "DNI = '23456789'");

                    // Mostrar los valores restantes en las tablas
                    System.out.println("Valores en la tabla CIENTIFICOS:");
                    getValues("Ejercicio7", "CIENTIFICOS");

                    System.out.println("Valores en la tabla PROYECTO:");
                    getValues("Ejercicio7", "PROYECTO");

                    System.out.println("Valores en la tabla ASIGNADO_A:");
                    getValues("Ejercicio7", "ASIGNADO_A");
                    break;
                case 14:
                    // Ejercicio 8 Grandes Almacenes
                    createDB("Ejercicio8");

                    // Crear tabla PRODUCTOS
                    createTable("Ejercicio8", "PRODUCTOS", "Codigo int PRIMARY KEY, Nombre nvarchar(100), Precio int");

                    // Crear tabla CAJEROS
                    createTable("Ejercicio8", "CAJEROS", "Codigo int PRIMARY KEY, NomApels varchar(255)");

                    // Crear tabla MAQUINAS
                    createTable("Ejercicio8", "MAQUINAS", "Codigo int PRIMARY KEY, Piso int");

                    // Crear tabla VENTA
                    createTable("Ejercicio8", "VENTA", "Cajero int, Maquina int, Producto int, PRIMARY KEY (Cajero, Maquina, Producto), FOREIGN KEY (Cajero) REFERENCES CAJEROS(Codigo) ON DELETE CASCADE, FOREIGN KEY (Maquina) REFERENCES MAQUINAS(Codigo) ON DELETE CASCADE, FOREIGN KEY (Producto) REFERENCES PRODUCTOS(Codigo) ON DELETE CASCADE");

                    // Insertar datos de ejemplo en las tablas
                    lista.clear();
                    lista.add("1,'Juguete',10");
                    lista.add("2,'Libreta',5");
                    insertData("Ejercicio8", "PRODUCTOS", lista);

                    lista.clear();
                    lista.add("1,'Cajero1'");
                    lista.add("2,'Cajero2'");
                    lista.add("3,'Cajero3'");
                    insertData("Ejercicio8", "CAJEROS", lista);

                    lista.clear();
                    lista.add("1,1");
                    lista.add("2,2");
                    insertData("Ejercicio8", "MAQUINAS", lista);

                    lista.clear();
                    lista.add("1,1,1");
                    lista.add("2,2,2");
                    insertData("Ejercicio8", "VENTA", lista);

                    // Realizar una eliminación de ejemplo
                    deleteRecord("Ejercicio8", "CAJEROS", "Codigo = 3");

                    // Mostrar los valores restantes en las tablas
                    System.out.println("Valores en la tabla CAJEROS:");
                    getValues("Ejercicio8", "CAJEROS");

                    System.out.println("Valores en la tabla PRODUCTOS:");
                    getValues("Ejercicio8", "PRODUCTOS");

                    System.out.println("Valores en la tabla MAQUINAS:");
                    getValues("Ejercicio8", "MAQUINAS");

                    System.out.println("Valores en la tabla VENTA:");
                    getValues("Ejercicio8", "VENTA");
                    break;

                case 15:
                    // Ejercicio 9 Investigadores
                    createDB("Ejercicio9");

                    // Crear tabla FACULTAD
                    createTable("Ejercicio9", "FACULTAD", "Codigo int PRIMARY KEY, Nombre nvarchar(100)");

                    // Crear tabla INVESTIGADORES
                    createTable("Ejercicio9", "INVESTIGADORES", "DNI varchar(8) PRIMARY KEY, NomApels nvarchar(255), Facultad int, FOREIGN KEY (Facultad) REFERENCES FACULTAD(Codigo)");

                    // Crear tabla EQUIPOS
                    createTable("Ejercicio9", "EQUIPOS", "NumSerie char(4), Nombre nvarchar(100), Comienzo DATE NOT NULL, Fin DATE NOT NULL, Facultad int NOT NULL, PRIMARY KEY (NumSerie), FOREIGN KEY (Facultad) REFERENCES FACULTAD(Codigo)");

                    // Crear tabla RESERVA
                    createTable("Ejercicio9", "RESERVA", "DNI varchar(8), NumSerie char(4), PRIMARY KEY (DNI, NumSerie), FOREIGN KEY (DNI) REFERENCES INVESTIGADORES(DNI) ON DELETE CASCADE, FOREIGN KEY (NumSerie) REFERENCES EQUIPOS(NumSerie) ON DELETE CASCADE");

                    // Insertar datos de ejemplo en las tablas
                    lista.clear();
                    lista.add("1,'Medicina'");
                    lista.add("2,'Terminologia'");
                    insertData("Ejercicio9", "FACULTAD", lista);

                    lista.clear();
                    lista.add("'12345678','Juan Carlos',1");
                    lista.add("'98765432','Carla Velasco',2");
                    insertData("Ejercicio9", "INVESTIGADORES", lista);

                    lista.clear();
                    lista.add("1,'Equipo1','2023-10-24','2023-10-25',1");
                    lista.add("2,'Equipo2','2023-10-26','2023-10-27',2");
                    insertData("Ejercicio9", "EQUIPOS", lista);

                    lista.clear();
                    lista.add("'12345678',1");
                    lista.add("'98765432',2");
                    insertData("Ejercicio9", "RESERVA", lista);

                    // Realizar una eliminación de ejemplo
                    deleteRecord("Ejercicio9", "INVESTIGADORES", "DNI = '98765432'");

                    // Mostrar los valores restantes en las tablas
                    System.out.println("Valores en la tabla FACULTAD:");
                    getValues("Ejercicio9", "FACULTAD");

                    System.out.println("Valores en la tabla INVESTIGADORES:");
                    getValues("Ejercicio9", "INVESTIGADORES");

                    System.out.println("Valores en la tabla EQUIPOS:");
                    getValues("Ejercicio9", "EQUIPOS");

                    System.out.println("Valores en la tabla RESERVA:");
                    getValues("Ejercicio9", "RESERVA");
                    break;

               
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        }
    }
    public void openConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306?useTimezone=true&serverTimezone=UTC", "root", "password");
            System.out.println("Server Connected");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("No se ha podido conectar con la base de datos");
            System.out.println(ex);
        }
    }
    public void closeConnection() {
        try {
            if (conexion != null) {
                conexion.close();
                System.out.println("Se ha finalizado la conexión con el servidor");
            }
        }
        catch (SQLException ex) {System.out.println("Error al cerrar la conexión: " + ex.getMessage());}
    }
    public void createDB(String name) {
        try {
            Statement statement = conexion.createStatement();
            String sql = "CREATE DATABASE IF NOT EXISTS " + name;
            statement.executeUpdate(sql);
            System.out.println("Base de datos '" + name + "' creada con éxito.");
        }
        catch (SQLException ex) {System.out.println("Error al crear la base de datos: " + ex.getMessage());}
    }
    public void createTable(String db, String tableName, String columnDefinitions) {
        try {
            Statement statement = conexion.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS " + db + "." + tableName + " (" + columnDefinitions + ")";
            statement.executeUpdate(sql);
            System.out.println("Tabla '" + tableName + "' creada con éxito en la base de datos '" + db + "'.");
        } catch (SQLException ex) {
            System.out.println("Error al crear la tabla: " + ex.getMessage());
        }
    }

    public void insertData(String db, String tableName, List<String> values) {
        try {
            String sql = "INSERT INTO " + db + "." + tableName + " VALUES (" + String.join("), (", values) + ")";
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Datos insertados en la tabla '" + tableName + "' en la base de datos '" + db + "'.");
        } catch (SQLException ex) {
            System.out.println("Error al insertar datos en la tabla: " + ex.getMessage());
        }
    }
    public void getValues(String db, String table_name) {
        try {
            Statement statement = conexion.createStatement();
            String sql = "SELECT * FROM " + db + "." + table_name;
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    String value = resultSet.getString(i);
                    System.out.println(columnName + ": " + value);
                }
                System.out.println();
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener valores de la tabla: " + ex.getMessage());
        }
    }
    public void deleteRecord(String db, String tableName, String condition) {
        try {
            String sql = "DELETE FROM " + db + "." + tableName + " WHERE " + condition;
            PreparedStatement preparedStatement = conexion.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Registro eliminado de la tabla '" + tableName + "' en la base de datos '" + db + "'.");
        } catch (SQLException ex) {
            System.out.println("Error al eliminar el registro: " + ex.getMessage());
        }
    }
}
