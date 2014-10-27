package com.pericles.cooperativa.gestion.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pericles.cooperativa.gestion.config.JpaConfiguration;

@ContextConfiguration(classes = JpaConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TareaPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;

	static Logger logger = Logger.getLogger(TareaPersistenceTests.class);

	@Test
	@Transactional
	public void testSaveTareaAndFind() throws Exception {
		long idProyecto;
		long idTarea;
		long idProyectoTarea;
		long idEntidad;
		long idUsuario;

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

		entityManager.persist(entidad);
		entityManager.flush();
		entityManager.clear();

		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		// Se crea un usuario que se añadirá al Set de usuarios para crear la
		// tarea
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
		usuario.setFechaNacimiento(new LocalDate("1970-09-14"));
		usuario.setCorreoElectronicoAsociacion("jalvarez@abierto.com");
		usuario.setTelefonoAsociacion("917778899");
		usuario.setTelefonoMovilAsociacion("666445566");
		usuario.setActivo(true);
		((Socio) usuario).setEliminado(false);

		entityManager.persist(usuario);
		entityManager.flush();
		entityManager.clear();

		idUsuario = usuario.getId();
		assertNotNull(idUsuario);
		UsuarioImpl usuarioFind = (UsuarioImpl) entityManager.find(
				UsuarioImpl.class, idUsuario);
		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);

		// Se crea un proyecto para poder establecerlo en la tarea
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 01");
		proyecto.setPresupuesto(new BigDecimal("2500.50"));
		proyecto.setDescripcion("Este es el primer proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto.setFechaFin(new LocalDate("2014-05-01"));
		proyecto.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto.getCoordinadores().add((Socio) usuarioFind);
		proyecto.setBalance("N/A");
		proyecto.setEntidadCliente(entidadFind);

		entityManager.persist(proyecto);
		entityManager.flush();
		entityManager.clear();

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);
		idUsuario = usuario.getId();
		assertNotNull(idUsuario);
		usuarioFind = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);

		// Se añade a una colección
		Set<UsuarioImpl> usuarios = new HashSet<UsuarioImpl>();
		usuarios.add(usuarioFind);

		// Qué usuarios hay en la colección
		logger.info("-------------------------------------");
		for (UsuarioImpl user : usuarios) {
			logger.info("Usuario: " + user);
		}
		logger.info("-------------------------------------");

		// Se crea la 1ª tarea (QUE SERÁ LA 2ª)
		Tarea tarea = new Tarea(proyectoFind, "2ª tarea del proyecto",
				EstadoTarea.PREVIO, new LocalDate("2014-03-01"), new LocalDate(
						"2014-04-01"), usuarios);

		proyectoFind.getTareas().add(tarea);

		entityManager.persist(proyectoFind);
		entityManager.flush();
		entityManager.clear();

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);

		// Se comprueba que se ha actualizado el proyecto con las tareas
		idTarea = proyectoFind.getTareas().iterator().next().getId();
		idProyectoTarea = proyectoFind.getTareas().iterator().next()
				.getProyecto().getId();
		assertNotNull(idTarea);
		assertNotNull(idProyectoTarea);
		assertEquals(1, proyectoFind.getTareas().size());
		assertEquals(proyectoFind, proyectoFind.getTareas().iterator().next()
				.getProyecto());

		Tarea tareaFind = (Tarea) entityManager.find(Tarea.class, idTarea);

		logger.info("Proyecto de la incidencia: " + tareaFind.getProyecto());

		logger.info("-------------------------------------");
		logger.info("Valores de la tarea:");
		logger.info("Id del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getId());
		logger.info("Id de la tarea: "
				+ proyectoFind.getTareas().iterator().next().getId());
		logger.info("Nombre del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getNombre());
		logger.info("Nombre de la otraTarea: "
				+ proyectoFind.getTareas().iterator().next().getDescripcion());
		logger.info("Estado de la tarea: "
				+ proyectoFind.getTareas().iterator().next().getEstadoTarea());
		logger.info("Fecha Inicio de la tarea: "
				+ DateTimeFormat.mediumDate().print(
						proyectoFind.getTareas().iterator().next()
								.getFechaInicio()));
		logger.info("Fecha Fin de la tarea: "
				+ DateTimeFormat.mediumDate().print(
						proyectoFind.getTareas().iterator().next()
								.getFechaFin()));
		logger.info("Usuarios: "
				+ proyectoFind.getTareas().iterator().next().getUsuarios());
		logger.info("-------------------------------------");

		// Se crea otro usuario que se añadirá al Set de usuarios para crear la
		// tarea
		UsuarioImpl otroUsuario = new Socio(); // Usuario
		otroUsuario.setIdentificacion("A1234567X");
		otroUsuario.setCorreoElectronico("aprenderjugando@hotmail.com");
		otroUsuario.setTelefono("34917411255");
		otroUsuario.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/Benito Camela, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		otroUsuario.setDomicilio(domicilio);
		otroUsuario.setNombreUsuario("Jacinto");
		otroUsuario.setApellidosUsuario("de Borbón y Borbón");
		otroUsuario.setFechaNacimiento(new LocalDate("1969-10-01"));
		otroUsuario.setCorreoElectronicoAsociacion("jalvarez2@abierto.com");
		otroUsuario.setTelefonoAsociacion("917778899");
		otroUsuario.setTelefonoMovilAsociacion("666445566");
		otroUsuario.setActivo(true);
		((Socio) otroUsuario).setEliminado(false);

		entityManager.persist(otroUsuario);
		entityManager.flush();
		entityManager.clear();

		// Se crea otro usuario que se añadirá al Set de usuarios para crear la
		// tarea
		UsuarioImpl otroUsuarioMas = new Trabajador(); // Usuario
		otroUsuarioMas.setIdentificacion("A1234567X");
		otroUsuarioMas.setCorreoElectronico("aprenderjugando@hotmail.com");
		otroUsuarioMas.setTelefono("34917411255");
		otroUsuarioMas.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/Benito Camela, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		otroUsuarioMas.setDomicilio(domicilio);
		otroUsuarioMas.setNombreUsuario("Al");
		otroUsuarioMas.setApellidosUsuario("Pachino Hay");
		otroUsuarioMas.setFechaNacimiento(new LocalDate("1969-10-01"));
		otroUsuarioMas.setCorreoElectronicoAsociacion("jalvarez2@abierto.com");
		otroUsuarioMas.setTelefonoAsociacion("917778899");
		otroUsuarioMas.setTelefonoMovilAsociacion("666445566");
		otroUsuarioMas.setActivo(true);
		((Trabajador) otroUsuarioMas).setEliminado(false);

		entityManager.persist(otroUsuarioMas);
		entityManager.flush();
		entityManager.clear();

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);
		idUsuario = usuario.getId();
		assertNotNull(idUsuario);
		usuarioFind = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);
		idUsuario = otroUsuario.getId();
		assertNotNull(idUsuario);
		UsuarioImpl usuario2Find = (UsuarioImpl) entityManager.find(
				UsuarioImpl.class, idUsuario);

		idUsuario = otroUsuarioMas.getId();
		assertNotNull(idUsuario);
		UsuarioImpl usuario3Find = (UsuarioImpl) entityManager.find(
				UsuarioImpl.class, idUsuario);

		// Se añade a una colección
		usuarios.clear();
		usuarios.add(usuarioFind);
		usuarios.add(usuario2Find);

		usuarios.add(usuario3Find);

		// Qué usuarios hay en la colección
		logger.info("-------------------------------------");
		for (UsuarioImpl user : usuarios) {
			logger.info("Usuario: " + user);
		}
		logger.info("-------------------------------------");

		// Se crea la 2º tarea (QUE SERÁ LA 1ª)
		Tarea otraTarea = new Tarea(proyectoFind, "1ª tarea del proyecto",
				EstadoTarea.PREVIO, new LocalDate("2014-03-01"), new LocalDate(
						"2014-03-15"), usuarios);

		proyectoFind.getTareas().add(otraTarea);

		entityManager.persist(proyectoFind);
		// entityManager.persist(otraTarea); // NOTA: Esto también funciona
		entityManager.flush();
		entityManager.clear();

		// Se comprueba que se puedan ver las tareas de un usuario
		idUsuario = usuario.getId();
		assertNotNull(idUsuario);
		usuarioFind = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);
		logger.info("Tareas de Usuario1" + usuarioFind.getTareas());

		idTarea = otraTarea.getId();
		assertNotNull(idTarea);
		tareaFind = (Tarea) entityManager.find(Tarea.class, idTarea);
		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);

		logger.info("Proyecto de la incidencia: " + tareaFind.getProyecto());

		logger.info("-------------------------------------");
		logger.info("Valores de la tarea:");
		logger.info("Id del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getId());
		logger.info("Id de la tarea: "
				+ proyectoFind.getTareas().iterator().next().getId());
		logger.info("Nombre del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getNombre());
		logger.info("Nombre de la otraTarea: "
				+ proyectoFind.getTareas().iterator().next().getDescripcion());
		logger.info("Estado de la tarea: "
				+ proyectoFind.getTareas().iterator().next().getEstadoTarea());
		logger.info("Fecha Inicio de la tarea: "
				+ DateTimeFormat.mediumDate().print(
						proyectoFind.getTareas().iterator().next()
								.getFechaInicio()));
		logger.info("Fecha Fin de la tarea: "
				+ DateTimeFormat.mediumDate().print(
						proyectoFind.getTareas().iterator().next()
								.getFechaFin()));
		logger.info("Usuarios: ");
		for (UsuarioImpl UsuarioEnProyecto : proyectoFind.getTareas()
				.iterator().next().getUsuarios()) {
			logger.info("Usuario: " + UsuarioEnProyecto);
		}
		logger.info("-------------------------------------");

		// Se comprueba que se hayan ordenado las tareas
		for (Tarea tareaEnProyecto : proyectoFind.getTareas()) {
			logger.info("Tarea: " + tareaEnProyecto.getDescripcion());
			for (Usuario usuarioEnTarea : tareaEnProyecto.getUsuarios()) {
				logger.info("Usuario: " + usuarioEnTarea.getApellidosUsuario());
			}
		}

	}
}
