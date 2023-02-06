package com.mpautasso.sistemavtv.mapper;

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

    public Empleado fromInspectorRequestToEntity(EmpleadoRequest empleadoRequest) {
        return modelMapper.map(empleadoRequest, Inspector.class);
    }

    public Empleado fromGerenteRequestToEntity(EmpleadoRequest empleadoRequest) {
        return modelMapper.map(empleadoRequest, Gerente.class);
    }

    public EmpleadoResponse fromEntityToResponse(Empleado empleado) {
        return new EmpleadoResponse(
                empleado.getNumeroLegajo(),
                empleado.getDni(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.cargoDelEmpleado()
        );
    }
}
