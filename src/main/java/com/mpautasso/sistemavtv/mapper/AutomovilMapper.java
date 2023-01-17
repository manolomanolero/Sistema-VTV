package com.mpautasso.sistemavtv.mapper;

import com.mpautasso.sistemavtv.model.Automovil;
import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.UltimaInspeccionAutomovil;
import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilRequest;
import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilResponse;
import com.mpautasso.sistemavtv.model.dtos.propietario.PropietarioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutomovilMapper {
    @Autowired
    private PropietarioMapper propietarioMapper;

    public Automovil fromRequestToEntity(AutomovilRequest automovilRequest, Propietario propietario){
        return new Automovil(null,
                automovilRequest.getMarca(),
                automovilRequest.getModelo(),
                automovilRequest.getDominio(),
                propietario);
    }

    public AutomovilResponse fromEntityToResponse(Automovil automovil){
        PropietarioResponse propietarioResponse = propietarioMapper.entityToResponse(automovil.getPropietario());
        return new AutomovilResponse(
                automovil.getMarca(),
                automovil.getModelo(),
                automovil.getDominio(),
                propietarioResponse);
    }

    public AutomovilResponse inspeccionAutoToResponse(UltimaInspeccionAutomovil inspeccionAutomovil){
        return fromEntityToResponse(inspeccionAutomovil.getAutomovil());
    }
}
