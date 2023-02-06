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

    @GetMapping
    public ResponseEntity<List<VehiculoResponse>> listarVehiculos(){
        return ResponseEntity.ok(vehiculoService.listarVehiculos());
    }

    @GetMapping("/aptos")
    public ResponseEntity<List<VehiculoResponse>> listarVehiculosAptos(){
        return ResponseEntity.ok(vehiculoService.listarVehiculosAptos());
    }

    @GetMapping("/condicionales")
    public ResponseEntity<List<VehiculoResponse>> listarVehiculosCondicionales(){
        return ResponseEntity.ok(vehiculoService.listarVehiculosCondicionales());
    }

    @GetMapping("/rechazados")
    public ResponseEntity<List<VehiculoResponse>> listarVehiculosRechazados(){
        return ResponseEntity.ok(vehiculoService.listarVehiculosRechazados());
    }

    @GetMapping("/{dominio}")
    public ResponseEntity<VehiculoResponse> buscarVehiculo(@PathVariable String dominio){
        return ResponseEntity.ok(vehiculoService.buscarVehiculoPorDominio(dominio));
    }

    @PostMapping
    public ResponseEntity<VehiculoResponse> crearVehiculo(@RequestBody VehiculoRequest vehiculoRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.crearVehiculo(vehiculoRequest));
    }

    @PutMapping
    public ResponseEntity<VehiculoResponse> editarVehiculo(@RequestBody VehiculoRequest vehiculoRequest){
        return ResponseEntity.ok(vehiculoService.editarVehiculo(vehiculoRequest));
    }

    @DeleteMapping
    public ResponseEntity<MensajeResponse> eliminarVehiculo(@RequestParam String dominio){
        vehiculoService.eliminarVehiculo(dominio);
        return ResponseEntity.ok(new MensajeResponse("Vehiculo eliminado con exito"));
    }

}
