package com.mpautasso.sistemavtv.controller;

import com.mpautasso.sistemavtv.model.dtos.MensajeResponse;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoRequest;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoResponse;
import com.mpautasso.sistemavtv.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/empleados")
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> listarInspectores(){
        return ResponseEntity.ok(empleadoService.listarInspectores());
    }

    @GetMapping("/{dni}")
    public ResponseEntity<EmpleadoResponse> buscarInspector(@PathVariable Long dni){
        return ResponseEntity.ok(empleadoService.buscarEmpleadoPorDni(dni));
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> crearInspector(@RequestBody EmpleadoRequest inspectorRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.crearEmpleado(inspectorRequest));
    }

    @PutMapping
    public ResponseEntity<EmpleadoResponse> editarInspector(@RequestBody EmpleadoRequest inspectorRequest){
        return ResponseEntity.ok(empleadoService.editarEmpleado(inspectorRequest));
    }

    @DeleteMapping
    public ResponseEntity<MensajeResponse> eliminarInspector(@RequestParam Long dni){
        empleadoService.eliminarEmpleado(dni);
        return ResponseEntity.ok(new MensajeResponse("Inspector eliminado con exito"));
    }

}
