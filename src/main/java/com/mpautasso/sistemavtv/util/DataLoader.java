package com.mpautasso.sistemavtv.util;

import com.mpautasso.sistemavtv.model.*;
import com.mpautasso.sistemavtv.repository.AutomovilRepository;
import com.mpautasso.sistemavtv.repository.InspeccionRepository;
import com.mpautasso.sistemavtv.repository.InspectorRepository;
import com.mpautasso.sistemavtv.repository.PropietariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private PropietariosRepository propietariosRepository;
    @Autowired
    private AutomovilRepository automovilRepository;
    @Autowired
    private InspectorRepository inspectorRepository;
    @Autowired
    private InspeccionRepository inspeccionRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            List<Propietario> propietarios = propietariosRepository.findAll();
            List<Automovil> automoviles = automovilRepository.findAll();
            List<Inspector> inspectores = inspectorRepository.findAll();

            if (propietarios.size() < 7 || automoviles.size() < 9 || inspectores.size() < 6) {
                Propietario propietario1 = propietariosRepository.save(
                        new PropietarioComun(1L, 3655100L, "Marcos", "Perez"));
                Propietario propietario2 = propietariosRepository.save(
                        new PropietarioExento(2L, 2510000L, "Maria", "Perez"));
                Propietario propietario3 = propietariosRepository.save(
                        new PropietarioComun(3L, 34436551L, "Patricio", "Martinez"));
                Propietario propietario4 = propietariosRepository.save(
                        new PropietarioExento(4L, 1210000L, "Cristian", "Fernandez"));
                Propietario propietario5 = propietariosRepository.save(
                        new PropietarioComun(5L, 5510000L, "Fran", "Ferrer"));
                Propietario propietario6 = propietariosRepository.save(
                        new PropietarioExento(6L, 3622551L, "Flor", "Paz"));
                Propietario propietario7 = propietariosRepository.save(
                        new PropietarioComun(7L, 3444441L, "Mara", "Martinez"));


                Automovil automovil1 = automovilRepository.save(
                        new Automovil(1L, "Peugeot", "c203", "ABF-S58", propietario1)
                );
                Automovil automovil2 = automovilRepository.save(
                        new Automovil(2L, "Peugeot", "c203", "12F-S58", propietario2)
                );
                Automovil automovil3 = automovilRepository.save(
                        new Automovil(3L, "Ferrari", "xzd-5", "GGE-R45", propietario3)
                );
                Automovil automovil4 = automovilRepository.save(
                        new Automovil(4L, "Citroen", "c303", "ABC-123", propietario1)
                );
                Automovil automovil5 = automovilRepository.save(
                        new Automovil(5L, "Citroen", "berlingo", "CDF-5W7", propietario4)
                );
                Automovil automovil6 = automovilRepository.save(
                        new Automovil(6L, "Peugeot", "c203", "AAA-111", propietario5)
                );
                Automovil automovil7 = automovilRepository.save(
                        new Automovil(7L, "Alfa Romeo", "gomuri", "BBB-222", propietario3)
                );
                Automovil automovil8 = automovilRepository.save(
                        new Automovil(8L, "Alfa Romeo", "zxc-2", "BAA-232", propietario7)
                );
                Automovil automovil9 = automovilRepository.save(
                        new Automovil(9L, "Alfa Romeo", "c208", "CCC-333", propietario6)
                );


                Inspector inspector1 = inspectorRepository.save(
                        new Inspector(1L, 3655100L, "Marcos", "Perez")
                );
                Inspector inspector2 = inspectorRepository.save(
                        new Inspector(2L, 2222222L, "Carla", "Perez")
                );
                Inspector inspector3 = inspectorRepository.save(
                        new Inspector(3L, 3333333L, "Armando", "Jerez")
                );
                Inspector inspector4 = inspectorRepository.save(
                        new Inspector(4L, 3000000L, "Lucrecia", "Villa")
                );
                Inspector inspector5 = inspectorRepository.save(
                        new Inspector(5L, 3500000L, "Ezequiel", "Romero")
                );
                Inspector inspector6 = inspectorRepository.save(
                        new Inspector(6L, 3220000L, "Angel", "Messi")
                );


                Inspeccion inspeccion1 = inspeccionRepository.save(
                        new Inspeccion(1L, EstadosInspeccion.CONDICIONAL, false,
                                new Date(), inspector1, automovil1)
                );
                Inspeccion inspeccion2 = inspeccionRepository.save(
                        new Inspeccion(2L, EstadosInspeccion.APTO, false,
                                new Date(), inspector3, automovil1)
                );
                Inspeccion inspeccion3 = inspeccionRepository.save(
                        new Inspeccion(3L, EstadosInspeccion.APTO, true,
                                new Date(), inspector2, automovil2)
                );
                Inspeccion inspeccion4 = inspeccionRepository.save(
                        new Inspeccion(4L, EstadosInspeccion.RECHAZADO, false,
                                new Date(), inspector5, automovil3)
                );
                Inspeccion inspeccion5 = inspeccionRepository.save(
                        new Inspeccion(5L, EstadosInspeccion.CONDICIONAL, false,
                                new Date(), inspector1, automovil4)
                );
                Inspeccion inspeccion6 = inspeccionRepository.save(
                        new Inspeccion(6L, EstadosInspeccion.APTO, true,
                                new Date(), inspector6, automovil5)
                );
                Inspeccion inspeccion7 = inspeccionRepository.save(
                        new Inspeccion(7L, EstadosInspeccion.RECHAZADO, false,
                                new Date(), inspector6, automovil7)
                );
                Inspeccion inspeccion8 = inspeccionRepository.save(
                        new Inspeccion(8L, EstadosInspeccion.APTO, false,
                                new Date(), inspector1, automovil7)
                );
                Inspeccion inspeccion9 = inspeccionRepository.save(
                        new Inspeccion(9L, EstadosInspeccion.CONDICIONAL, false,
                                new Date(), inspector4, automovil8)
                );
                Inspeccion inspeccion10 = inspeccionRepository.save(
                        new Inspeccion(10L, EstadosInspeccion.RECHAZADO, true,
                                new Date(), inspector6, automovil9)
                );

            }


        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
