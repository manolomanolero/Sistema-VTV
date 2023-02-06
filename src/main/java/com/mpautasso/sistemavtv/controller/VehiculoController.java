package com.mpautasso.sistemavtv.controller;

import com.mpautasso.sistemavtv.model.dtos.MensajeResponse;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoRequest;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoResponse;
import com.mpautasso.sistemavtv.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehiculos")
@CrossOrigin("*")
public class VehiculoController {
    private final VehiculoService vehiculoService;

    @GetMapping("/automoviles")
    public ResponseEntity<List<VehiculoResponse>> listarAutomoviles(){
        return ResponseEntity.ok(vehiculoService.listarAutomoviles());
    }

    @GetMapping("/automoviles/aptos")
    public ResponseEntity<List<VehiculoResponse>> listarAutomovilesAptos(){
        return ResponseEntity.ok(vehiculoService.listarAutomovilesAptos());
    }

    @GetMapping("/automoviles/condicionales")
    public ResponseEntity<List<VehiculoResponse>> listarAutomovilesCondicionales(){
        return ResponseEntity.ok(vehiculoService.listarAutomovilesCondicionales());
    }

    @GetMapping("/automoviles/rechazados")
    public ResponseEntity<List<VehiculoResponse>> listarAutomovilesRechazados(){
        return ResponseEntity.ok(vehiculoService.listarAutomovilesRechazados());
    }

    @GetMapping("/automoviles/{dominio}")
    public ResponseEntity<VehiculoResponse> buscarVehiculo(@PathVariable String dominio){
        return ResponseEntity.ok(vehiculoService.buscarAutomovilPorDominio(dominio));
    }

    @PostMapping("/automoviles")
    public ResponseEntity<VehiculoResponse> crearAutomovil(@RequestBody VehiculoRequest vehiculoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.crearAutomovil(vehiculoRequest));
    }

    @PutMapping("/automoviles")
    public ResponseEntity<VehiculoResponse> editarAutomovil(@RequestBody VehiculoRequest vehiculoRequest){
        return ResponseEntity.ok(vehiculoService.editarAutomovil(vehiculoRequest));
    }

    @DeleteMapping
    public ResponseEntity<MensajeResponse> eliminarAutomovil(@RequestParam String dominio){
        vehiculoService.eliminarVehiculo(dominio);
        return ResponseEntity.ok(new MensajeResponse("Vehiculo eliminado con exito"));
    }

}
