package com.mpautasso.sistemavtv.service;

import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.dtos.propietario.PropietarioRequest;
import com.mpautasso.sistemavtv.model.dtos.propietario.PropietarioResponse;

import java.util.List;

public interface PropietarioService {
    List<PropietarioResponse> listarPropietarios();
    Propietario buscarEntidadPorDni(Long dni);
    PropietarioResponse buscarPropietarioPorDni(Long dni);
    PropietarioResponse crearPropietarioComun(PropietarioRequest propietarioRequest);
    PropietarioResponse crearPropietarioExento(PropietarioRequest propietarioRequest);
    PropietarioResponse editarPropietarioComun(PropietarioRequest propietarioRequest);
    PropietarioResponse editarPropietarioExcento(PropietarioRequest propietarioRequest);
    void eliminarPropietario(Long dni);
}
