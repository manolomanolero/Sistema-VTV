package com.mpautasso.sistemavtv.service;

import com.mpautasso.sistemavtv.model.Inspector;
import com.mpautasso.sistemavtv.model.dtos.inspector.InspectorRequest;
import com.mpautasso.sistemavtv.model.dtos.inspector.InspectorResponse;

import java.util.List;

public interface InspectorService {
    List<InspectorResponse> listarInspectores();
    Inspector buscarEntidadPorDni(Long dni);
    InspectorResponse buscarInspectorPorDni(Long dni);
    InspectorResponse crearInspector(InspectorRequest inspectorRequest);
    InspectorResponse editarInspector(InspectorRequest inspectorRequest);
    void eliminarInspector(Long dni);
}
