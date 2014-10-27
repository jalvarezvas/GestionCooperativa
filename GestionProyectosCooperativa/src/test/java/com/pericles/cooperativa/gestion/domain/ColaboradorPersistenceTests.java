package com.pericles.cooperativa.gestion.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pericles.cooperativa.gestion.config.JpaConfiguration;

@ContextConfiguration(classes = JpaConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ColaboradorPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;

	static Logger logger = Logger.getLogger(ColaboradorPersistenceTests.class);

	@Test
	@Transactional
	public void testSaveColaboradorAndFind() throws Exception {
		long idEntidad;
		long idColaborador;
		long idProyecto;

		// Se crea una Entidad para poder establecerla en el proyecto
		Entidad entidad = new Entidad();
		entidad.setIdentificacion("A1234567S");
		entidad.setNombreEntidad("Aprender Jugando");
		entidad.setCorreoElectronico("aprenderjugando@hotmail.com");
		entidad.setTelefono("34917411255");
		entidad.setTelefonoMovil("34666147799");
		// Domicilio
		Domicilio domicilio = new Domicilio("C/Canal del Bósforo, 54 3º A",
				"Madrid", Provincia.MADRID, ComunidadAutonoma.MADRID, "28022",
				"España");
		entidad.setDomicilio(domicilio);
		entidad.setNombrePersonaContacto("Marta López Rodriguez");
		entidad.setCorreoElectronicoPersonaContacto("mlopez@gmail.com");
		entidad.setTelefonoPersonaContacto("917411255");
		entidad.setTelefonoMovilPersonaContacto("609252525");
		entidad.setEliminado(false);

		// Se crea un colaborador que realizará tareas
		Colaborador colaborador = new Colaborador(); // Usuario
		colaborador.setIdentificacion("B1234567Z");
		colaborador.setCorreoElectronico("daprenderjugando@hotmail.com");
		colaborador.setTelefono("34917411255");
		colaborador.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/de la Taza, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		colaborador.setDomicilio(domicilio);
		colaborador.setNombreUsuario("Laborator");
		colaborador.setApellidosUsuario("Alvarez del Vas");
		colaborador.setFechaNacimiento(new LocalDate("1970-09-14"));
		colaborador.setCorreoElectronicoAsociacion("djalvarez@abierto.com");
		colaborador.setTelefonoAsociacion("918888899");
		colaborador.setTelefonoMovilAsociacion("609445566");
		colaborador.setActivo(true);
		colaborador.setEliminado(false);
		colaborador.setEntidad(entidad);

		entidad.getColaboradores().add(colaborador);

		entityManager.persist(entidad);
		entityManager.flush();
		entityManager.clear();

		logger.info("Colaborador creado por: " + colaborador.getCreatedBy());
		logger.info("Colaborador creado en: " + colaborador.getCreatedDate());
		logger.info("Colaborador modificado por: "
				+ colaborador.getLastModifiedBy());
		logger.info("Colaborador modificado en: "
				+ colaborador.getLastModifiedDate());

		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);
		assertNotNull(entidadFind);

		idColaborador = colaborador.getId();
		assertNotNull(idColaborador);
		Colaborador colaboradorFind = (Colaborador) entityManager.find(
				Colaborador.class, idColaborador);
		assertNotNull(colaboradorFind);

		logger.info("Colaborador de la entidad: "
				+ entidadFind.getNombreEntidad()
				+ ": "
				+ entidadFind.getColaboradores().iterator().next()
						.getApellidosUsuario());

		// Se crea un usuario que será el coordinador y realizará tareas
		UsuarioImpl usuario = new Socio(); // Usuario
		usuario.setIdentificacion("A1234567Z");
		usuario.setCorreoElectronico("aprenderjugando@hotmail.com");
		usuario.setTelefono("34917411255");
		usuario.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/de la Raza, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		usuario.setDomicilio(domicilio);
		usuario.setNombreUsuario("Jacinto");
		usuario.setApellidosUsuario("Alvarez del Vas");
		usuario.setFechaNacimiento(new LocalDate("1975-03-15"));
		usuario.setCorreoElectronicoAsociacion("jalvarez@abierto.com");
		usuario.setTelefonoAsociacion("917778899");
		usuario.setTelefonoMovilAsociacion("666445566");
		usuario.setActivo(true);
		((Socio) usuario).setEliminado(false);

		// Se crea un proyecto
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 01");
		proyecto.setPresupuesto(new BigDecimal("2500.50"));
		proyecto.setDescripcion("Este es el primer proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto.setFechaFin(new LocalDate("2014-01-01"));
		proyecto.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto.getCoordinadores().add((Socio) usuario);
		proyecto.setBalance("N/A");
		proyecto.setEntidadCliente(entidadFind);
		proyecto.getUsuarios().add(colaboradorFind);
		proyecto.getUsuarios().add(usuario);

		entityManager.persist(proyecto);
		entityManager.flush();
		entityManager.clear();

		// Se comprueba que se ha actualizado el proyecto con los trabajadores
		assertNotNull(proyecto.getUsuarios());

		idColaborador = colaborador.getId();
		colaboradorFind = (Colaborador) entityManager.find(Colaborador.class,
				idColaborador);
		assertNotNull(colaboradorFind);

		logger.info("Colaborador participa en proyectos: ");
		for (Proyecto p : colaboradorFind.getProyectosParticipados()) {
			logger.info(p.getNombre());
		}

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);
		assertNotNull(proyectoFind);

		assertEquals(colaboradorFind.getProyectosParticipados().iterator()
				.next(), proyectoFind);

		logger.info("Valores del colaborador (bd):");
		logger.info("Identificación: " + colaboradorFind.getIdentificacion());
		logger.info("Correo Electrónico: "
				+ colaboradorFind.getCorreoElectronico());
		logger.info("Teléfono: " + colaboradorFind.getTelefono());
		logger.info("Teléfono Móvil: " + colaboradorFind.getTelefonoMovil());
		logger.info("Dirección: "
				+ colaboradorFind.getDomicilio().getDireccion());
		logger.info("Población: "
				+ colaboradorFind.getDomicilio().getPoblacion());
		logger.info("Provincia: "
				+ colaboradorFind.getDomicilio().getProvincia());
		logger.info("Comunidad Autónoma: "
				+ colaboradorFind.getDomicilio().getComunidadAutonoma());
		logger.info("Código Postal: "
				+ colaboradorFind.getDomicilio().getCodigoPostal());
		logger.info("País: " + colaboradorFind.getDomicilio().getPais());
		logger.info("Nombre: " + colaboradorFind.getNombreUsuario());
		logger.info("Apellidos: " + colaboradorFind.getApellidosUsuario());
		logger.info("Fecha Nacimiento: " + colaboradorFind.getFechaNacimiento());
		logger.info("Correo Electrónico Asociación: "
				+ colaboradorFind.getCorreoElectronicoAsociacion());
		logger.info("Teléfono Asociación: "
				+ colaboradorFind.getTelefonoAsociacion());
		logger.info("Teléfono Móvil Asociación: "
				+ colaboradorFind.getTelefonoMovilAsociacion());
		logger.info("Entidad: "
				+ colaboradorFind.getEntidad().getNombreEntidad());

		logger.info("Colaborador creado por: " + colaboradorFind.getCreatedBy());
		logger.info("Colaborador creado en: "
				+ colaboradorFind.getCreatedDate());
		logger.info("Colaborador modificado por: "
				+ colaboradorFind.getLastModifiedBy());
		logger.info("Colaborador modificado en: "
				+ colaboradorFind.getLastModifiedDate());

		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);

		logger.info("Colaborador de la entidad "
				+ entidadFind.getNombreEntidad()
				+ ": "
				+ entidadFind.getColaboradores().iterator().next()
						.getApellidosUsuario());

		colaboradorFind = (Colaborador) entityManager
				.createQuery(
						"Select c From Colaborador c join c.entidad e where e.nombreEntidad=:nombreEntidad")
				.setParameter("nombreEntidad", "Aprender Jugando")
				.getSingleResult();
		assertEquals(colaboradorFind.getNombreUsuario(), entidadFind
				.getColaboradores().iterator().next().getNombreUsuario());

	}

}
