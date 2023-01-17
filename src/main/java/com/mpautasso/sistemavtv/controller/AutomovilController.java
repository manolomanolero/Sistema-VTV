package com.mpautasso.sistemavtv.controller;

import com.mpautasso.sistemavtv.model.dtos.MensajeResponse;
import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilRequest;
import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilResponse;
import com.mpautasso.sistemavtv.service.AutomovilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/automoviles")
public class AutomovilController {
    private final AutomovilService automovilService;

    @GetMapping
    public ResponseEntity<List<AutomovilResponse>> listarAutomoviles(){
        return ResponseEntity.ok(automovilService.listarAutomoviles());
    }

    @GetMapping("/aptos")
    public ResponseEntity<List<AutomovilResponse>> listarAutomovilesAptos(){
        return ResponseEntity.ok(automovilService.listarAutomovilesAptos());
    }

    @GetMapping("/condicionales")
    public ResponseEntity<List<AutomovilResponse>> listarAutomovilesCondicionales(){
        return ResponseEntity.ok(automovilService.listarAutomovilesCondicionales());
    }

    @GetMapping("/rechazados")
    public ResponseEntity<List<AutomovilResponse>> listarAutomovilesRechazados(){
        return ResponseEntity.ok(automovilService.listarAutomovilesRechazados());
    }

    @GetMapping("/{dominio}")
    public ResponseEntity<AutomovilResponse> buscarAutomovil(@PathVariable String dominio){
        return ResponseEntity.ok(automovilService.buscarAutomovilPorDominio(dominio));
    }

    @PostMapping
    public ResponseEntity<AutomovilResponse> crearAutomovil(@RequestBody AutomovilRequest automovilRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(automovilService.crearAutomovil(automovilRequest));
    }

    @PutMapping
    public ResponseEntity<AutomovilResponse> editarAutomovil(@RequestBody AutomovilRequest automovilRequest){
        return ResponseEntity.ok(automovilService.editarAutomovil(automovilRequest));
    }

    @DeleteMapping
    public ResponseEntity<MensajeResponse> eliminarAutomovil(@RequestParam String dominio){
        automovilService.eliminarAutomovil(dominio);
        return ResponseEntity.ok(new MensajeResponse("Automovil eliminado con exito"));
    }

}
