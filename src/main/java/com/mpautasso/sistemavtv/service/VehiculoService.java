package com.mpautasso.sistemavtv.service;

import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.Vehiculo;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoRequest;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoResponse;

import java.util.List;

public interface VehiculoService {
    List<VehiculoResponse> listarAutomoviles();
    List<Vehiculo> listarAutomovilesPorPropietario(Propietario propietario);
    List<VehiculoResponse> listarAutomovilesAptos();
    List<VehiculoResponse> listarAutomovilesCondicionales();
    List<VehiculoResponse> listarAutomovilesRechazados();
    Vehiculo buscarEntidadPorDominio(String dominio);
    VehiculoResponse buscarAutomovilPorDominio(String dominio);
    VehiculoResponse crearAutomovil(VehiculoRequest vehiculoRequest);
    VehiculoResponse editarAutomovil(VehiculoRequest vehiculoRequest);
    void eliminarVehiculo(String dominio);

}
