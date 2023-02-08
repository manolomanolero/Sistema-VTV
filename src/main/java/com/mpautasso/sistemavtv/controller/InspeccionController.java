package com.mpautasso.sistemavtv.controller;

import com.mpautasso.sistemavtv.model.dtos.MensajeResponse;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionRequest;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.cliente.PropietarioDetallesResponse;
import com.mpautasso.sistemavtv.service.InspeccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inspecciones")
@CrossOrigin("*")
public class InspeccionController {
    private final InspeccionService inspeccionService;

    @GetMapping
    public ResponseEntity<List<InspeccionResponse>> listarInspecciones(){
        return ResponseEntity.ok(inspeccionService.listarInspecciones());
    }

    @GetMapping("/{numeroInspeccion}")
    public ResponseEntity<InspeccionResponse> buscarInspeccion(@PathVariable Long numeroInspeccion){
        return ResponseEntity.ok(inspeccionService.buscarInspeccionPorNumeroInspeccion(numeroInspeccion));
    }

    @GetMapping("/propietario/{dni}")
    public ResponseEntity<PropietarioDetallesResponse> listarInspeccionesPorAutoDeUsuario(@PathVariable Long dni){
        return ResponseEntity.ok(inspeccionService.listarInspeccionesPorVehiculoDePropietario(dni));
    }

    @PostMapping
    public ResponseEntity<InspeccionResponse> crearInspeccion(@RequestBody InspeccionRequest inspeccionRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(inspeccionService.crearInspeccion(inspeccionRequest));
    }

    @PutMapping
    public ResponseEntity<InspeccionResponse> editarInspeccion(@RequestBody InspeccionRequest inspeccionRequest){
        return ResponseEntity.ok(inspeccionService.editarInspeccion(inspeccionRequest));
    }

    @DeleteMapping
    public ResponseEntity<MensajeResponse> eliminarInspector(@RequestParam Long numeroInspeccion){
        inspeccionService.eliminarInspeccion(numeroInspeccion);
        return ResponseEntity.ok(new MensajeResponse("Inspeccion eliminada con exito"));
    }

}
