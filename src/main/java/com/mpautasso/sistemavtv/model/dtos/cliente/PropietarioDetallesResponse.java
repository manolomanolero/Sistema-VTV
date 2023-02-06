package com.mpautasso.sistemavtv.model.dtos.cliente;

import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoInspeccionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropietarioDetallesResponse {
    private ClienteResponse clienteResponse;
    private List<VehiculoInspeccionResponse> detallesAutomoviles;
}
