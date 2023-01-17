package com.mpautasso.sistemavtv.mapper;

import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.PropietarioComun;
import com.mpautasso.sistemavtv.model.PropietarioExento;
import com.mpautasso.sistemavtv.model.dtos.propietario.PropietarioRequest;
import com.mpautasso.sistemavtv.model.dtos.propietario.PropietarioResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropietarioMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Propietario comunRequestToEntity(PropietarioRequest propietarioRequest){
        return modelMapper.map(propietarioRequest, PropietarioComun.class);
    }

    public Propietario exentoRequestToEntity(PropietarioRequest propietarioRequest){
        return modelMapper.map(propietarioRequest, PropietarioExento.class);
    }

    public PropietarioResponse entityToResponse(Propietario propietario){
        return new PropietarioResponse(
                propietario.getDni(), propietario.getNombre(), propietario.getApellido(), propietario.tipoPropietario()
        );
    }
}
