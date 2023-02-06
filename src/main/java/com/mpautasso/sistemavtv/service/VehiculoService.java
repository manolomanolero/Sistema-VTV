package com.mpautasso.sistemavtv.service;

import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.Vehiculo;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoRequest;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoResponse;

import java.util.List;

public interface VehiculoService {
    List<VehiculoResponse> listarVehiculos();
    List<Vehiculo> listarVehiculos(Propietario propietario);
    List<VehiculoResponse> listarVehiculosAptos();
    List<VehiculoResponse> listarVehiculosCondicionales();
    List<VehiculoResponse> listarVehiculosRechazados();
    Vehiculo buscarEntidadPorDominio(String dominio);
    VehiculoResponse buscarVehiculoPorDominio(String dominio);
    VehiculoResponse crearVehiculo(VehiculoRequest vehiculoRequest);
    VehiculoResponse editarVehiculo(VehiculoRequest vehiculoRequest);
    void eliminarVehiculo(String dominio);

}
