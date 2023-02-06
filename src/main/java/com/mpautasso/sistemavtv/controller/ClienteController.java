package com.mpautasso.sistemavtv.controller;

import com.mpautasso.sistemavtv.model.dtos.MensajeResponse;
import com.mpautasso.sistemavtv.model.dtos.cliente.ClienteRequest;
import com.mpautasso.sistemavtv.model.dtos.cliente.ClienteResponse;
import com.mpautasso.sistemavtv.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponse>> listarClientes(){
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crearCliente(@RequestBody ClienteRequest clienteRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crearCliente(clienteRequest));
    }

    @GetMapping("/choferes")
    public ResponseEntity<List<ClienteResponse>> listarChoferes(){
        return ResponseEntity.ok(clienteService.listarChoferes());
    }

    @GetMapping("/propietarios")
    public ResponseEntity<List<ClienteResponse>> listarPropietarios(){
        return ResponseEntity.ok(clienteService.listarPropietarios());
    }

    @GetMapping("/propietarios/{dni}")
    public ResponseEntity<ClienteResponse> buscarPropietario(@PathVariable Long dni){
        return ResponseEntity.ok(clienteService.buscarPropietarioPorDni(dni));
    }

    @PostMapping("/propietarios/exento")
    public ResponseEntity<ClienteResponse> crearPropietarioExento(@RequestBody ClienteRequest clienteRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crearPropietarioExento(clienteRequest));
    }

    @PutMapping("/propietarios/exento")
    public ResponseEntity<ClienteResponse> editarPropietarioExento(@RequestBody ClienteRequest clienteRequest){
        return ResponseEntity.ok(clienteService.editarPropietarioExcento(clienteRequest));
    }

    @PostMapping("/propietarios/comun")
    public ResponseEntity<ClienteResponse> crearPropietarioComun(@RequestBody ClienteRequest clienteRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crearPropietarioComun(clienteRequest));
    }

    @PutMapping("/propietarios/comun")
    public ResponseEntity<ClienteResponse> editarPropietarioComun(@RequestBody ClienteRequest clienteRequest){
        return ResponseEntity.ok(clienteService.editarPropietarioComun(clienteRequest));
    }

    @PostMapping("/choferes")
    public ResponseEntity<ClienteResponse> crearChofer(@RequestBody ClienteRequest clienteRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crearChofer(clienteRequest));
    }

    @PutMapping("/choferes")
    public ResponseEntity<ClienteResponse> editarChofer(@RequestBody ClienteRequest clienteRequest){
        return ResponseEntity.ok(clienteService.editarChofer(clienteRequest));
    }

    @DeleteMapping("/propietarios")
    public ResponseEntity<MensajeResponse> eliminarPropietario(@RequestParam Long dni){
        clienteService.eliminarPropietario(dni);
        return ResponseEntity.ok(new MensajeResponse("El propietario asociado a este dni fue eliminado con exito"));
    }
    @DeleteMapping("/choferes")
    public ResponseEntity<MensajeResponse> eliminarChoferes(@RequestParam Long dni){
        clienteService.eliminarChofer(dni);
        return ResponseEntity.ok(new MensajeResponse("El chofer asociado a este dni fue eliminado con exito"));
    }
}
