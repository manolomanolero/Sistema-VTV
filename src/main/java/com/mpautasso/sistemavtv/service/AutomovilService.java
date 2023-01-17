package com.mpautasso.sistemavtv.service;

import com.mpautasso.sistemavtv.model.Automovil;
import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilRequest;
import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilResponse;

import java.util.List;

public interface AutomovilService {
    List<AutomovilResponse> listarAutomoviles();
    List<Automovil> listarAutomoviles(Propietario propietario);
    List<AutomovilResponse> listarAutomovilesAptos();
    List<AutomovilResponse> listarAutomovilesCondicionales();
    List<AutomovilResponse> listarAutomovilesRechazados();
    Automovil buscarEntidadPorDominio(String dominio);
    AutomovilResponse buscarAutomovilPorDominio(String dominio);
    AutomovilResponse crearAutomovil(AutomovilRequest automovilRequest);
    AutomovilResponse editarAutomovil(AutomovilRequest automovilRequest);
    void eliminarAutomovil(String dominio);

}
