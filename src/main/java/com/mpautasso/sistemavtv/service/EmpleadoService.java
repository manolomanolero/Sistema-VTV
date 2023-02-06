package com.mpautasso.sistemavtv.service;

import com.mpautasso.sistemavtv.model.Empleado;
import com.mpautasso.sistemavtv.model.Inspector;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoRequest;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoResponse;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoResponse> listarInspectores();
    Empleado buscarEntidadPorDni(Long dni);
    Inspector buscarEntidadInspectorPorDni(Long dni);
    EmpleadoResponse buscarEmpleadoPorDni(Long dni);
    EmpleadoResponse crearEmpleado(EmpleadoRequest empleadoRequest);
    EmpleadoResponse editarEmpleado(EmpleadoRequest empleadoRequest);
    void eliminarEmpleado(Long dni);
}
