/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Mensajes;

/**
 *
 * @author andre
 */
public enum TiposMensaje {
    ConectarseServidor, // Desconexion se maneja con excepciones
    Subscripciones,     // Maneja suscripciones ( booleano TRUE(Sub) ; FALSE(deSub)                      }    
    CrearObservable,    // Maneja Observables   ( booleano TRUE(Crear) ; FALSE(Destruir)                 }
    MandarDatos,        // Manda Datos          { Peticion NULL -> Obsevable Peticion != NULL -> cliente }
    NotificacionUsurio,
    TodosLosDatos;
}
