package com.mpautasso.sistemavtv.model.dtos.propietario;

import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilInspeccionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropietarioDetallesResponse {
    private PropietarioResponse propietarioResponse;
    private List<AutomovilInspeccionResponse> detallesAutomoviles;
}
