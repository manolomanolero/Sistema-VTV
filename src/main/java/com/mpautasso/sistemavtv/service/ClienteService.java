package com.mpautasso.sistemavtv.service;

import com.mpautasso.sistemavtv.model.Chofer;
import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.dtos.cliente.ClienteRequest;
import com.mpautasso.sistemavtv.model.dtos.cliente.ClienteResponse;

import java.util.List;

public interface ClienteService {
    List<ClienteResponse> listarClientes();
    List<ClienteResponse> listarPropietarios();
    List<ClienteResponse> listarChoferes();
    Propietario buscarEntidadPropietarioPorDni(Long dni);
    Chofer buscarEntidadChoferPorDni(Long dni);
    ClienteResponse buscarPropietarioPorDni(Long dni);
    ClienteResponse crearCliente(ClienteRequest clienteRequest);
    ClienteResponse crearPropietarioComun(ClienteRequest clienteRequest);
    ClienteResponse crearPropietarioExento(ClienteRequest clienteRequest);
    ClienteResponse crearChofer(ClienteRequest clienteRequest);
    ClienteResponse editarPropietarioComun(ClienteRequest clienteRequest);
    ClienteResponse editarPropietarioExcento(ClienteRequest clienteRequest);
    ClienteResponse editarChofer(ClienteRequest clienteRequest);
    void eliminarPropietario(Long dni);
    void eliminarChofer(Long dni);
}
