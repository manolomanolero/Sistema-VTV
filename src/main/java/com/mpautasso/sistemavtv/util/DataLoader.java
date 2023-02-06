package com.mpautasso.sistemavtv.util;

import com.mpautasso.sistemavtv.model.*;
import com.mpautasso.sistemavtv.model.enums.EstadosInspeccion;
import com.mpautasso.sistemavtv.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private ClientesRepository clientesRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private InspeccionRepository inspeccionRepository;
    @Autowired
    private VersionRepository versionRepository;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            List<Cliente> clientes = clientesRepository.findAll();
            List<Vehiculo> vehiculos = vehiculoRepository.findAll();
            List<Empleado> inspectores = empleadoRepository.findAll();

            if (clientes.size() < 5 || vehiculos.size() < 8 || inspectores.size() < 3) {


                Propietario cliente1 = clientesRepository.save(
                        new PropietarioComun(1L, 3655100L, "Marcos", "Perez"));
                Propietario cliente2 = clientesRepository.save(
                        new PropietarioExento(2L, 2510000L, "Maria", "Perez", 2025100009L));
                Propietario cliente3 = clientesRepository.save(
                        new PropietarioComun(3L, 34436551L, "Patricio", "Martinez"));
                Propietario cliente4 = clientesRepository.save(
                        new PropietarioExento(4L, 1210000L, "Cristian", "Fernandez", 2012100008L));
                Propietario cliente5 = clientesRepository.save(
                        new PropietarioComun(5L, 5510000L, "Fran", "Ferrer"));
                Propietario cliente6 = clientesRepository.save(
                        new PropietarioExento(6L, 3622551L, "Flor", "Paz", 2036225519L));

                Chofer cliente7 = clientesRepository.save(
                        new Chofer(7L, 3444441L, "Marta", "Lopez", "AB12674AS"));
                Chofer cliente8 = clientesRepository.save(
                        new Chofer(8L, 2867641L, "Carlos", "Fernandez", "CDF21294AS"));
                Chofer cliente9 = clientesRepository.save(
                        new Chofer(9L, 4944221L, "Pipo", "Gorosito", "GG445215JH"));


                Marca marca1 = marcaRepository.save(new Marca(1L, "Peugeot"));
                Marca marca2 = marcaRepository.save(new Marca(2L, "Fiat"));
                Marca marca3 = marcaRepository.save(new Marca(3L, "Ford"));

                Modelo modelo1 = modeloRepository.save(new Modelo(1L, "201", marca1));
                Modelo modelo2 = modeloRepository.save(new Modelo(2L, "202", marca1));
                Modelo modelo3 = modeloRepository.save(new Modelo(3L, "Pulse", marca2));
                Modelo modelo4 = modeloRepository.save(new Modelo(4L, "Cronos", marca2));
                Modelo modelo5 = modeloRepository.save(new Modelo(5L, "Fiesta", marca3));


                Version version1 = versionRepository.save(new Version(1L, "C berlina", modelo1));
                Version version2 = versionRepository.save(new Version(2L, "coupé", modelo1));
                Version version3 = versionRepository.save(new Version(3L, "cabriolé", modelo1));

                Version version4 = versionRepository.save(new Version(4L, "Drive 1.3 manual", modelo3));
                Version version5 = versionRepository.save(new Version(5L, "Drive 1.3 automático", modelo3));
                Version version6 = versionRepository.save(new Version(6L, "Impetus 1.0 automático", modelo3));


                Vehiculo automovil1 = vehiculoRepository.save(
                        new Automovil(1L, "CCC-333", "ZXCS152A1", "A8SD1AS12521S", version1, cliente1)
                );
                Vehiculo automovil2 = vehiculoRepository.save(
                        new Automovil(2L, "12F-S58", "AWCS123A1", "A8SD1AS12AS3S", version4, cliente2, cliente9)
                );
                Vehiculo automovil3 = vehiculoRepository.save(
                        new Automovil(3L, "GGE-R45", "A34S123A1", "A8ZZ1AS12SD3S", version5, cliente3)
                );
                Vehiculo automovil4 = vehiculoRepository.save(
                        new Automovil(4L, "ABC-123", "AWCS589A1", "B8TLAS12AS3S", version3, cliente1, cliente8)
                );
                Vehiculo moto1 = vehiculoRepository.save(
                        new Moto(5L, "CDF-5W7", "ADDD123A1", "A8SD1AS12HH1L", version6, cliente4)
                );
                Vehiculo moto2 = vehiculoRepository.save(
                        new Moto(6L, "AAA-111", "APOI583A1", "B9SD1AS12AS3S", version6, cliente5)
                );
                Vehiculo moto3 = vehiculoRepository.save(
                        new Moto(7L, "BBB-222", "FFFS123R9", "B9SDJ2S12AS3S", version5, cliente3)
                );
                Vehiculo moto4 = vehiculoRepository.save(
                        new Moto(8L, "BAA-232", "AWCS123A1", "A8SD4HG12AS3S", version5, cliente2, cliente7)
                );


                Inspector inspector1 = empleadoRepository.save(
                        new Inspector(1L, 3655100L, "Marcos", "Perez")
                );
                Inspector inspector2 = empleadoRepository.save(
                        new Inspector(2L, 2222222L, "Carla", "Perez")
                );
                Inspector inspector3 = empleadoRepository.save(
                        new Inspector(3L, 3333333L, "Armando", "Jerez")
                );
                Gerente gerente1 = empleadoRepository.save(
                        new Gerente(4L, 3000000L, "Lucrecia", "Villa")
                );
                Gerente gerente2 = empleadoRepository.save(
                        new Gerente(5L, 3500000L, "Ezequiel", "Romero")
                );
                Gerente gerente3 = empleadoRepository.save(
                        new Gerente(6L, 3220000L, "Angel", "Messi")
                );


                Inspeccion inspeccion1 = inspeccionRepository.save(
                        new Inspeccion(1L, EstadosInspeccion.CONDICIONAL, false,
                                new Date(), inspector1, automovil1, "Fallo leve de suspension al 19%")
                );
                Inspeccion inspeccion2 = inspeccionRepository.save(
                        new Inspeccion(2L, EstadosInspeccion.APTO, false,
                                new Date(), inspector3, automovil1, "Todo correcto")
                );
                Inspeccion inspeccion3 = inspeccionRepository.save(
                        new Inspeccion(3L, EstadosInspeccion.APTO, true,
                                new Date(), inspector2, automovil2, "Todo correcto")
                );
                Inspeccion inspeccion4 = inspeccionRepository.save(
                        new Inspeccion(4L, EstadosInspeccion.RECHAZADO, false,
                                new Date(), inspector1, automovil3, "Fallo de los amortiguadores")
                );
                Inspeccion inspeccion5 = inspeccionRepository.save(
                        new Inspeccion(5L, EstadosInspeccion.CONDICIONAL, false,
                                new Date(), inspector1, automovil4, "Fallo leve de suspension al 19%")
                );
                Inspeccion inspeccion6 = inspeccionRepository.save(
                        new Inspeccion(6L, EstadosInspeccion.APTO, true,
                                new Date(), inspector2, moto1, "Todo correcto")
                );
                Inspeccion inspeccion7 = inspeccionRepository.save(
                        new Inspeccion(7L, EstadosInspeccion.RECHAZADO, false,
                                new Date(), inspector2, moto2, "Fallo grave de frenos al 19%")
                );
                Inspeccion inspeccion8 = inspeccionRepository.save(
                        new Inspeccion(8L, EstadosInspeccion.APTO, false,
                                new Date(), inspector1, moto2, "Todo correcto")
                );
                Inspeccion inspeccion9 = inspeccionRepository.save(
                        new Inspeccion(9L, EstadosInspeccion.CONDICIONAL, false,
                                new Date(), inspector3, moto3, "Fallo leve de neumatios al 15%")
                );
                Inspeccion inspeccion10 = inspeccionRepository.save(
                        new Inspeccion(10L, EstadosInspeccion.RECHAZADO, true,
                                new Date(), inspector3, moto4, "Fallo grave de contaminacion al 20%")
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
