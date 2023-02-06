package com.mpautasso.sistemavtv.service;

import com.mpautasso.sistemavtv.model.Inspeccion;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionRequest;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.cliente.PropietarioDetallesResponse;

import java.util.List;

public interface InspeccionService {
    List<InspeccionResponse> listarInspecciones();
    Inspeccion buscarEntidadPorNumeroInspeccion(Long numeroInspeccion);
    InspeccionResponse buscarInspeccionPorNumeroInspeccion(Long numeroInspeccion);
    InspeccionResponse crearInspeccion(InspeccionRequest inspeccionRequest);
    InspeccionResponse editarInspeccion(InspeccionRequest inspeccionRequest);
    void eliminarInspeccion(Long numeroInspeccion);
    PropietarioDetallesResponse listarInspeccionesPorVehiculoDePropietario(Long dni);
}
