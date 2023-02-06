package com.mpautasso.sistemavtv.service.implementation;

import com.mpautasso.sistemavtv.exceptions.custom.EntityAlreadyExistsException;
import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
import com.mpautasso.sistemavtv.exceptions.custom.NoSuchEntityExistsException;
import com.mpautasso.sistemavtv.mapper.VehiculoMapper;
import com.mpautasso.sistemavtv.model.Chofer;
import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.Vehiculo;
import com.mpautasso.sistemavtv.model.Version;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoRequest;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoResponse;
import com.mpautasso.sistemavtv.model.enums.EstadosInspeccion;
import com.mpautasso.sistemavtv.repository.UltimaInspeccionAutomovilRepository;
import com.mpautasso.sistemavtv.repository.VehiculoRepository;
import com.mpautasso.sistemavtv.repository.VersionRepository;
import com.mpautasso.sistemavtv.service.ClienteService;
import com.mpautasso.sistemavtv.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private final UltimaInspeccionAutomovilRepository inspeccionAutoRepo;
    private final VehiculoMapper vehiculoMapper;
    private final ClienteService clienteService;
    private final VersionRepository versionRepository;

    //Toda inspeccion anterior a esta fecha estara vencidad ya que ocurrio hace mas de 1 año
    private final Date FECHA_LIMITE_VENCIMIENTO_DE_AÑO = new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * 365);
    private final Date FECHA_LIMITE_VENCIMIENTO_DE_DIA = new Date(new Date().getTime() - 1000 * 60 * 60 * 24);

    @Override
    public List<VehiculoResponse> listarAutomoviles() {
        return vehiculoRepository.findAllAutomoviles().stream()
                .map(vehiculoMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehiculo> listarAutomovilesPorPropietario(Propietario propietario) {
        return vehiculoRepository.findAllByPropietario(propietario);
    }

    @Override
    public List<VehiculoResponse> listarAutomovilesAptos() {
        return inspeccionAutoRepo.findAllByEstadosInspeccion(EstadosInspeccion.APTO)
                .stream()
                .filter(auto ->
                        FECHA_LIMITE_VENCIMIENTO_DE_AÑO.before(auto.getFecha())
                        && auto.getVehiculo().getClass().getSimpleName().equals("Automovil"))
                .map(vehiculoMapper::inspeccionAutoToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculoResponse> listarAutomovilesCondicionales() {
        return inspeccionAutoRepo.findAllByEstadosInspeccion(EstadosInspeccion.CONDICIONAL)
                .stream()
                .filter(auto ->
                        FECHA_LIMITE_VENCIMIENTO_DE_DIA.before(auto.getFecha())
                        && auto.getVehiculo().getClass().getSimpleName().equals("Automovil"))
                .map(vehiculoMapper::inspeccionAutoToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculoResponse> listarAutomovilesRechazados() {
        return inspeccionAutoRepo.findAllByEstadosInspeccion(EstadosInspeccion.RECHAZADO)
                .stream()
                .filter(auto -> auto.getVehiculo().getClass().getSimpleName().equals("Automovil"))
                .map(vehiculoMapper::inspeccionAutoToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Vehiculo buscarEntidadPorDominio(String dominio) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByDominio(dominio);
        if (vehiculoOptional.isEmpty()) {
            throw new NoSuchEntityExistsException("No se encontro vehiculo asociado al dominio");
        }
        return vehiculoOptional.get();
    }

    @Override
    public VehiculoResponse buscarAutomovilPorDominio(String dominio) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByDominio(dominio);
        if (vehiculoOptional.isEmpty()) {
            throw new NoSuchEntityExistsException("No se encontro vehiculo asociado al dominio");
        }
        return vehiculoMapper.fromEntityToResponse(
                vehiculoOptional.get());
    }

    @Override
    public VehiculoResponse crearAutomovil(VehiculoRequest vehiculoRequest) {
        validarDatosDeVehiculo(vehiculoRequest);

        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByDominio(vehiculoRequest.getDominio());
        if (vehiculoOptional.isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un vehiculo con este dominio");
        }

        Version version = versionRepository
                .findById(vehiculoRequest.getVersionId())
                .orElseThrow(() -> {
                    throw new InvalidArgumentException("No se encontró la versión del vehiculo con tal id");
                });

        Propietario propietario = clienteService.buscarEntidadPropietarioPorDni(vehiculoRequest.getPropietarioDni());

        Chofer chofer = (vehiculoRequest.getChoferDni() == null || vehiculoRequest.getChoferDni() <= 0) ? null :
                clienteService.buscarEntidadChoferPorDni(vehiculoRequest.getChoferDni());

        return vehiculoMapper.fromEntityToResponse(
                vehiculoRepository.save(
                        vehiculoMapper.fromAutomovilRequestToEntity(vehiculoRequest, version, propietario, chofer)
                )
        );
    }

    @Override
    public VehiculoResponse editarAutomovil(VehiculoRequest vehiculoRequest) {
        validarDatosDeVehiculo(vehiculoRequest);

        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByDominio(vehiculoRequest.getDominio());
        if (vehiculoOptional.isEmpty()) {
            throw new NoSuchEntityExistsException("No se encontro el automovil a editar");
        }

        Propietario propietario = clienteService.buscarEntidadPropietarioPorDni(vehiculoRequest.getPropietarioDni());
        Version version = versionRepository
                .findById(vehiculoRequest.getVersionId())
                .orElseThrow(() -> {
                    throw new InvalidArgumentException("No se encontró la versión del vehiculo con tal id");
                });

        Vehiculo vehiculoBD = vehiculoOptional.get();
        vehiculoBD.setNumeroMotor(vehiculoRequest.getNumeroMotor());
        vehiculoBD.setNumeroChasis(vehiculoRequest.getNumeroChasis());
        vehiculoBD.setVersion(version);
        vehiculoBD.setPropietario(propietario);
        if(vehiculoRequest.getChoferDni() != null && vehiculoRequest.getChoferDni() > 0) {
            vehiculoBD.setChofer(clienteService.buscarEntidadChoferPorDni(vehiculoRequest.getChoferDni()));
        }

        return vehiculoMapper.fromEntityToResponse(
                vehiculoRepository.save(vehiculoBD)
        );
    }

    @Override
    public void eliminarVehiculo(String dominio) {
        Optional<Vehiculo> vehiculoOptional = vehiculoRepository.findByDominio(dominio);
        if (vehiculoOptional.isEmpty()) {
            throw new NoSuchEntityExistsException("No se encontro el vehiculo a eliminar");
        }
        vehiculoRepository.delete(vehiculoOptional.get());
    }

    private void validarDatosDeVehiculo(VehiculoRequest vehiculoRequest) {

        validarDominioVehiculo(vehiculoRequest.getDominio());

        if (vehiculoRequest.getVersionId() <= 0) {
            throw new InvalidArgumentException("El id de la version no puede ser menor o igual a 0");
        }
        if (vehiculoRequest.getPropietarioDni() < 1) {
            throw new InvalidArgumentException("El dni del propietario no puede ser 0 o un numero negativo");
        }
        if (vehiculoRequest.getNumeroChasis().length() != 17) {
            throw new InvalidArgumentException("El numero de chasis no es valido. Tiene que tener 17 caracteres");
        }
        if (vehiculoRequest.getNumeroMotor().isEmpty() ) {
            throw new InvalidArgumentException("El numero de motor no puede ser vacío o solo contener espacios");
        }
    }

    private void validarDominioVehiculo(String dominio) {
        if (dominio.isEmpty()) {
            throw new InvalidArgumentException("El dominio no puede ser vacío o solo contener espacios");
        } else if (dominio.length() < 6 || dominio.length() > 7) {
            throw new InvalidArgumentException("El dominio debe tener un formato valido de 6 o 7 caracteres");
        } else if (dominio.length() == 6) {
            if (!Character.isLetter(dominio.charAt(0)) || !Character.isLetter(dominio.charAt(1))
                    || !Character.isLetter(dominio.charAt(2))) {
                throw new InvalidArgumentException("Los dominios de 6 caracteres deben empezar con 3 letras");
            }
            if (!Character.isDigit(dominio.charAt(3)) || !Character.isDigit(dominio.charAt(4))
                    || !Character.isDigit(dominio.charAt(5))) {
                throw new InvalidArgumentException("Los dominios de 6 caracteres deben terminar con 3 numeros");
            }
        } else {
            if (!Character.isLetter(dominio.charAt(0)) || !Character.isLetter(dominio.charAt(1))
                    || !Character.isLetter(dominio.charAt(5)) || !Character.isLetter(dominio.charAt(6))) {
                throw new InvalidArgumentException("Los dominios de 7 caracteres deben empezar y terminar con 2 letras");
            }
            if (!Character.isDigit(dominio.charAt(2)) || !Character.isDigit(dominio.charAt(3))
                    || !Character.isDigit(dominio.charAt(4))) {
                throw new InvalidArgumentException("Los dominios de 7 caracteres deben tener 3 numeros en el medio");
            }
        }
    }
}
