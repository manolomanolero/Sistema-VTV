package com.mpautasso.sistemavtv.controller;

import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
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
    public ResponseEntity<List<EmpleadoResponse>> listarEmpleados(){
        return ResponseEntity.ok(empleadoService.listarEmpleados());
    }

    @GetMapping("/inspectores")
    public ResponseEntity<List<EmpleadoResponse>> listarInspectores(){
        return ResponseEntity.ok(empleadoService.listarInspectores());
    }

    @GetMapping("/gerentes")
    public ResponseEntity<List<EmpleadoResponse>> listarGerentes(){
        return ResponseEntity.ok(empleadoService.listarGerentes());
    }

    @GetMapping("/inspectores/{dni}")
    public ResponseEntity<EmpleadoResponse> buscarInspector(@PathVariable Long dni){
        return ResponseEntity.ok(empleadoService.buscarInspectorPorDni(dni));
    }
    @GetMapping("/gerentes/{dni}")
    public ResponseEntity<EmpleadoResponse> buscarGerente(@PathVariable Long dni){
        return ResponseEntity.ok(empleadoService.buscarGerentePorDni(dni));
    }

    @PostMapping("/inspectores")
    public ResponseEntity<EmpleadoResponse> crearInspector(@RequestBody EmpleadoRequest inspectorRequest){
        validarDatosDeEmpleado(inspectorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.crearInspector(inspectorRequest));
    }

    @PostMapping("/gerentes")
    public ResponseEntity<EmpleadoResponse> crearGerente(@RequestBody EmpleadoRequest gerenteRequest){
        validarDatosDeEmpleado(gerenteRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoService.crearGerente(gerenteRequest));
    }

    @PutMapping("/inspectores")
    public ResponseEntity<EmpleadoResponse> editarInspector(@RequestBody EmpleadoRequest inspectorRequest){
        validarDatosDeEmpleado(inspectorRequest);
        return ResponseEntity.ok(empleadoService.editarInspector(inspectorRequest));
    }
    @PutMapping("/gerentes")
    public ResponseEntity<EmpleadoResponse> editarGerente(@RequestBody EmpleadoRequest gerenteRequest){
        validarDatosDeEmpleado(gerenteRequest);
        return ResponseEntity.ok(empleadoService.editarGerente(gerenteRequest));
    }

    @DeleteMapping
    public ResponseEntity<MensajeResponse> eliminarEmpleado(@RequestParam Long dni){
        empleadoService.eliminarEmpleado(dni);
        return ResponseEntity.ok(new MensajeResponse("Empleado eliminado con exito"));
    }

    private void validarDatosDeEmpleado(EmpleadoRequest inspectorRequest){
        if(inspectorRequest.getDni() < 100000 || inspectorRequest.getDni() > 1000000000){
            throw new InvalidArgumentException("El dni no puede ser menor a 100.000 o mayor a 1.000.000.000");
        }
        if(inspectorRequest.getNombre().isBlank() || inspectorRequest.getNombre().trim().length() <= 2){
            throw new InvalidArgumentException("El nombre no puede tener menos de 2 caracteres");
        }
        if(inspectorRequest.getApellido().isBlank() || inspectorRequest.getApellido().trim().length() <= 2){
            throw new InvalidArgumentException("El apellido no puede tener menos de 2 caracteres");
        }
    }
}
