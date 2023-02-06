package com.mpautasso.sistemavtv.mapper;

import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
import com.mpautasso.sistemavtv.model.Empleado;
import com.mpautasso.sistemavtv.model.Gerente;
import com.mpautasso.sistemavtv.model.Inspector;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoRequest;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmpleadoMapper {
    private final ModelMapper modelMapper;

    public Empleado fromRequestToEntity(EmpleadoRequest empleadoRequest){
        if (empleadoRequest.getCargo().equalsIgnoreCase("gerente")){
            return modelMapper.map(empleadoRequest, Gerente.class);
        } else if (empleadoRequest.getCargo().equalsIgnoreCase("inspector")) {
            return modelMapper.map(empleadoRequest, Inspector.class);
        }
        throw new InvalidArgumentException("El cargo del empleado no fue identificado con los existentes");
    }

    public EmpleadoResponse fromEntityToResponse(Empleado empleado){
        return new EmpleadoResponse(
                empleado.getDni(),
                empleado.getNumeroLegajo(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.cargoDelEmpleado()
        );
    }
}
