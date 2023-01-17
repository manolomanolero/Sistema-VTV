package com.mpautasso.sistemavtv.controller;

import com.mpautasso.sistemavtv.model.dtos.MensajeResponse;
import com.mpautasso.sistemavtv.model.dtos.propietario.PropietarioRequest;
import com.mpautasso.sistemavtv.model.dtos.propietario.PropietarioResponse;
import com.mpautasso.sistemavtv.service.PropietarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propietarios")
public class PropietarioController {
    private final PropietarioService propietarioService;

    @GetMapping
    public ResponseEntity<List<PropietarioResponse>> listarPropietarios(){
        return ResponseEntity.ok(propietarioService.listarPropietarios());
    }

    @GetMapping("/{dni}")
    public ResponseEntity<PropietarioResponse> buscarPropietario(@PathVariable Long dni){
        return ResponseEntity.ok(propietarioService.buscarPropietarioPorDni(dni));
    }

    @PostMapping("/exento")
    public ResponseEntity<PropietarioResponse> crearPropietarioExento(@RequestBody PropietarioRequest propietarioRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(propietarioService.crearPropietarioExento(propietarioRequest));
    }

    @PutMapping("/exento")
    public ResponseEntity<PropietarioResponse> editarPropietarioExento(@RequestBody PropietarioRequest propietarioRequest){
        return ResponseEntity.ok(propietarioService.editarPropietarioExcento(propietarioRequest));
    }

    @PostMapping("/comun")
    public ResponseEntity<PropietarioResponse> crearPropietarioComun(@RequestBody PropietarioRequest propietarioRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(propietarioService.crearPropietarioComun(propietarioRequest));
    }

    @PutMapping("/comun")
    public ResponseEntity<PropietarioResponse> editarPropietarioComun(@RequestBody PropietarioRequest propietarioRequest){
        return ResponseEntity.ok(propietarioService.editarPropietarioComun(propietarioRequest));
    }

    @DeleteMapping
    public ResponseEntity<MensajeResponse> eliminarPropietario(@RequestParam Long dni){
        propietarioService.eliminarPropietario(dni);
        return ResponseEntity.ok(new MensajeResponse("Propietario eliminado con exito"));
    }
}
