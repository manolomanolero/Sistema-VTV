package com.mpautasso.sistemavtv.controller;

import com.mpautasso.sistemavtv.model.dtos.MensajeResponse;
import com.mpautasso.sistemavtv.model.dtos.inspector.InspectorRequest;
import com.mpautasso.sistemavtv.model.dtos.inspector.InspectorResponse;
import com.mpautasso.sistemavtv.service.InspectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inspectores")
public class InspectorController {
    private final InspectorService inspectorService;

    @GetMapping
    public ResponseEntity<List<InspectorResponse>> listarInspectores(){
        return ResponseEntity.ok(inspectorService.listarInspectores());
    }

    @GetMapping("/{dni}")
    public ResponseEntity<InspectorResponse> buscarInspector(@PathVariable Long dni){
        return ResponseEntity.ok(inspectorService.buscarInspectorPorDni(dni));
    }

    @PostMapping
    public ResponseEntity<InspectorResponse> crearInspector(@RequestBody InspectorRequest inspectorRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(inspectorService.crearInspector(inspectorRequest));
    }

    @PutMapping
    public ResponseEntity<InspectorResponse> editarInspector(@RequestBody InspectorRequest inspectorRequest){
        return ResponseEntity.ok(inspectorService.editarInspector(inspectorRequest));
    }

    @DeleteMapping
    public ResponseEntity<MensajeResponse> eliminarInspector(@RequestParam Long dni){
        inspectorService.eliminarInspector(dni);
        return ResponseEntity.ok(new MensajeResponse("Inspector eliminado con exito"));
    }

}
