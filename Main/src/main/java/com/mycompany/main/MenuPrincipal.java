/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private Inventario inventario;
    private Scanner scanner;

    public MenuPrincipal() {
        inventario = new Inventario();
        scanner = new Scanner(System.in);
        cargarDatosEjemplo();
    }

    private void cargarDatosEjemplo() {
        try {
            inventario.agregarProducto(new Producto("001", "Martillo", "Martillo de acero", 150.50, 10, 5));
            inventario.agregarProducto(new Producto("002", "Destornillador", "Destornillador plano", 75.30, 15, 8));
            inventario.agregarProducto(new ProductoPerecedero("003", "Leche", "Leche entera 1L", 25.50, 20, 10, 
                LocalDate.now().plusDays(5)));
        } catch (ProductoExistenteException e) {
            System.out.println("Error al cargar datos de ejemplo: " + e.getMessage());
        }
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n  SISTEMA DE INVENTARIO    ");
            System.out.println("1. Agregar producto");
            System.out.println("2. Mostrar todos los productos");
            System.out.println("3. Buscar producto");
            System.out.println("4. Modificar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Ver alertas de stock");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch(opcion) {
                    case 1 -> agregarProducto();
                    case 2 -> mostrarProductos();
                    case 3 -> buscarProducto();
                    case 4 -> modificarProducto();
                    case 5 -> eliminarProducto();
                    case 6 -> verAlertasStock();
                    case 0 -> System.out.println("Saliendo del sistema...");
                    default -> System.out.println("Opcion no valida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un numero.");
                opcion = -1;
            }
        } while (opcion != 0);
        
        scanner.close();
    }

    private void agregarProducto() {
        System.out.println("\n    AGREGAR PRODUCTO    ");
        
        try {
            System.out.print("Codigo: ");
            String codigo = scanner.nextLine();
            
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine();
            
            System.out.print("Precio: ");
            double precio = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Stock: ");
            int stock = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Stock minimo: ");
            int stockMinimo = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Es producto perecedero? (s/n): ");
            String respuesta = scanner.nextLine();
            
            if (respuesta.equalsIgnoreCase("s")) {
                System.out.print("Fecha de vencimiento (AAAA-MM-DD): ");
                LocalDate fechaVencimiento = LocalDate.parse(scanner.nextLine());
                
                ProductoPerecedero producto = new ProductoPerecedero(
                    codigo, nombre, descripcion, precio, stock, stockMinimo, fechaVencimiento);
                inventario.agregarProducto(producto);
            } else {
                Producto producto = new Producto(
                    codigo, nombre, descripcion, precio, stock, stockMinimo);
                inventario.agregarProducto(producto);
            }
            
            System.out.println("Producto agregado correctamente.");
        } catch (ProductoExistenteException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un valor numerico valido.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private void mostrarProductos() {
        List<Producto> productos = inventario.getProductos();
        
        if (productos.isEmpty()) {
            System.out.println("\nNo hay productos registrados.");
            return;
        }
        
        System.out.println("\n    LISTA DE PRODUCTOS    ");
        for (Producto p : productos) {
            System.out.println(p);
            System.out.println("-----------------------------");
        }
    }

    private void buscarProducto() {
        System.out.println("\n    BUSCAR PRODUCTO    ");
        System.out.print("Ingrese codigo o nombre: ");
        String busqueda = scanner.nextLine();
        
        Producto producto = inventario.buscarPorCodigo(busqueda);
        if (producto != null) {
            System.out.println("\nProducto encontrado:");
            System.out.println(producto);
            return;
        }
        
        List<Producto> resultados = inventario.buscarPorNombre(busqueda);
        if (!resultados.isEmpty()) {
            System.out.println("\nProductos encontrados:");
            for (Producto p : resultados) {
                System.out.println(p);
                System.out.println("-----------------------------");
            }
        } else {
            System.out.println("No se encontro ningun producto.");
        }
    }

    private void modificarProducto() {
        System.out.println("\n    MODIFICAR PRODUCTO    ");
        System.out.print("Ingrese codigo del producto: ");
        String codigo = scanner.nextLine();
        
        try {
            Producto producto = inventario.buscarPorCodigo(codigo);
            if (producto == null) {
                throw new ProductoNoEncontradoException("Producto no encontrado.");
            }
            
            System.out.println("\nDatos actuales del producto:");
            System.out.println(producto);
            
            System.out.println("\nSeleccione que desea modificar:");
            System.out.println("1. Nombre");
            System.out.println("2. Descripcion");
            System.out.println("3. Precio");
            System.out.println("4. Stock");
            System.out.println("5. Stock minimo");
            if (producto instanceof ProductoPerecedero) {
                System.out.println("6. Fecha de vencimiento");
            }
            System.out.print("Opcion: ");
            
            int opcion = Integer.parseInt(scanner.nextLine());
            
            switch(opcion) {
                case 1 -> {
                    System.out.print("Nuevo nombre: ");
                    producto.setNombre(scanner.nextLine());
                }
                case 2 -> {
                    System.out.print("Nueva descripcion: ");
                    producto.setDescripcion(scanner.nextLine());
                }
                case 3 -> {
                    System.out.print("Nuevo precio: ");
                    producto.setPrecio(Double.parseDouble(scanner.nextLine()));
                }
                case 4 -> {
                    System.out.print("Nuevo stock: ");
                    producto.setStock(Integer.parseInt(scanner.nextLine()));
                }
                case 5 -> {
                    System.out.print("Nuevo stock minimo: ");
                    producto.setStockMinimo(Integer.parseInt(scanner.nextLine()));
                }
                case 6 -> {
                    if (producto instanceof ProductoPerecedero) {
                        System.out.print("Nueva fecha de vencimiento (AAAA-MM-DD): ");
                        LocalDate fecha = LocalDate.parse(scanner.nextLine());
                        ((ProductoPerecedero)producto).setFechaVencimiento(fecha);
                    } else {
                        System.out.println("Opcion no valida para este producto.");
                    }
                }
                default -> System.out.println("Opcion no valida.");
            }
            
            System.out.println("Producto modificado correctamente.");
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un valor numerico valido.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        System.out.println("\n    ELIMINAR PRODUCTO    ");
        System.out.print("Ingrese codigo del producto: ");
        String codigo = scanner.nextLine();
        
        try {
            inventario.eliminarProducto(codigo);
            System.out.println("Producto eliminado correctamente.");
        } catch (ProductoNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void verAlertasStock() {
        List<Producto> productosBajos = inventario.getProductosConStockBajo();
        
        System.out.println("\n    ALERTAS DE STOCK    ");
        if (productosBajos.isEmpty()) {
            System.out.println("No hay productos con stock bajo.");
            return;
        }
        
        for (Producto p : productosBajos) {
            System.out.println("ALERTA: " + p.getNombre() + " (Codigo: " + p.getCodigo() + ")");
            System.out.println("Stock actual: " + p.getStock() + " (Minimo requerido: " + p.getStockMinimo() + ")");
            if (p instanceof ProductoPerecedero && ((ProductoPerecedero)p).estaVencido()) {
                System.out.println("¡ADVERTENCIA: Producto vencido!");
            }
            System.out.println("-----------------------------");
        }
    }
}