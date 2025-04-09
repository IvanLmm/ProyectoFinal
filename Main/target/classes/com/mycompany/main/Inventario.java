/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Producto> productos;
    private static final int MAX_PRODUCTOS = 100;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) throws ProductoExistenteException {
        if (productos.size() >= MAX_PRODUCTOS) {
            throw new IllegalStateException("No se pueden agregar mas productos. Limite alcanzado.");
        }
        
        if (buscarPorCodigo(producto.getCodigo()) != null) {
            throw new ProductoExistenteException("Ya existe un producto con ese codigo: " + producto.getCodigo());
        }
        
        productos.add(producto);
    }

    public Producto buscarPorCodigo(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> resultados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    public void eliminarProducto(String codigo) throws ProductoNoEncontradoException {
        Producto producto = buscarPorCodigo(codigo);
        if (producto == null) {
            throw new ProductoNoEncontradoException("Producto no encontrado: " + codigo);
        }
        productos.remove(producto);
    }

    public List<Producto> getProductos() {
        return new ArrayList<>(productos);
    }

    public List<Producto> getProductosConStockBajo() {
        List<Producto> resultados = new ArrayList<>();
        for (Producto p : productos) {
            if (p.necesitaReponer()) {
                resultados.add(p);
            }
        }
        return resultados;
    }
}