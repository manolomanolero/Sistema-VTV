package com.mpautasso.sistemavtv.service;

import com.mpautasso.sistemavtv.model.Empleado;
import com.mpautasso.sistemavtv.model.Gerente;
import com.mpautasso.sistemavtv.model.Inspector;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoRequest;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoResponse> listarEmpleados();
    List<EmpleadoResponse> listarInspectores();
    List<EmpleadoResponse> listarGerentes();
    Empleado buscarEntidadPorDni(Long dni);
    Inspector buscarEntidadInspectorPorDni(Long dni);
    Gerente buscarEntidadGerentePorDni(Long dni);
    EmpleadoResponse buscarEmpleadoPorDni(Long dni);
    EmpleadoResponse buscarInspectorPorDni(Long dni);
    EmpleadoResponse buscarGerentePorDni(Long dni);
    EmpleadoResponse crearInspector(EmpleadoRequest empleadoRequest);
    EmpleadoResponse crearGerente(EmpleadoRequest empleadoRequest);
    EmpleadoResponse editarInspector(EmpleadoRequest empleadoRequest);
    EmpleadoResponse editarGerente(EmpleadoRequest empleadoRequest);
    void eliminarEmpleado(Long dni);
}
