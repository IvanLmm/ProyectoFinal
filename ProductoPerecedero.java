/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.main;

import java.time.LocalDate;

public class ProductoPerecedero extends Producto {
    private LocalDate fechaVencimiento;

    public ProductoPerecedero(String codigo, String nombre, String descripcion, 
                             double precio, int stock, int stockMinimo, 
                             LocalDate fechaVencimiento) {
        super(codigo, nombre, descripcion, precio, stock, stockMinimo);
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean estaVencido() {
        return LocalDate.now().isAfter(fechaVencimiento);
    }

    @Override
    public String toString() {
        return super.toString() + "\n" +
               "Fecha Vencimiento: " + fechaVencimiento + 
               (estaVencido() ? " (VENCIDO)" : "");
    }
}