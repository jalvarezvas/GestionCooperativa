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
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pericles.cooperativa.gestion.config.JpaConfiguration;
import com.pericles.cooperativa.gestion.domain.ComunidadAutonoma;
import com.pericles.cooperativa.gestion.domain.Domicilio;
import com.pericles.cooperativa.gestion.domain.Entidad;
import com.pericles.cooperativa.gestion.domain.EstadoProyecto;
import com.pericles.cooperativa.gestion.domain.EstadoTarea;
import com.pericles.cooperativa.gestion.domain.Incidencia;
import com.pericles.cooperativa.gestion.domain.Provincia;
import com.pericles.cooperativa.gestion.domain.Proyecto;
import com.pericles.cooperativa.gestion.domain.Socio;
import com.pericles.cooperativa.gestion.domain.Tarea;
import com.pericles.cooperativa.gestion.domain.UsuarioImpl;

@ContextConfiguration(classes = JpaConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ProyectoPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;

	static Logger logger = Logger.getLogger(ProyectoPersistenceTests.class);

	@Test
	@Transactional
	public void testSaveWithTareas() throws Exception {
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
		assertEquals(entidadFind, entidad);

		// Se crea un usuario (socio) que coordina y que introduce la tarea
		// (usuario)
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

		Set<Socio> coordinadores = new HashSet<Socio>();
		coordinadores.add((Socio) usuario);

		// Se crea un proyecto para poder establecerlo en la tarea
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 01");
		proyecto.setPresupuesto(new BigDecimal("250000.50"));
		proyecto.setDescripcion("Este es el primer proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto.setFechaFin(new LocalDate("2014-05-01"));
		proyecto.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto.setCoordinadores(coordinadores);
		proyecto.setBalance("N/A");
		proyecto.setEntidadCliente(entidadFind);

		entityManager.persist(proyecto);
		entityManager.flush();

		logger.info("Valores del proyecto:");
		logger.info("Nombre del proyecto: " + proyecto.getNombre());
		logger.info("Presupuesto del proyecto: " + proyecto.getPresupuesto());
		logger.info("Descripción del proyecto: " + proyecto.getDescripcion());
		logger.info("Fecha de inicio del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyecto.getFechaInicio()));
		logger.info("Fecha fin del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyecto.getFechaFin()));
		logger.info("Estado del proyecto: " + proyecto.getEstadoProyecto());
		logger.info("Coordinadores del proyecto: "
				+ proyecto.getCoordinadores().iterator().next()
						.getApellidosUsuario());
		logger.info("Balance del proyecto: " + proyecto.getBalance());
		logger.info("Entidad cliente del proyecto: "
				+ proyecto.getEntidadCliente().getNombreEntidad());
		logger.info("Proyecto creado por: " + proyecto.getCreatedBy());
		logger.info("Proyecto creado en: " + proyecto.getCreatedDate());
		logger.info("Proyecto modificado por: " + proyecto.getLastModifiedBy());
		logger.info("Proyecto modificado en: " + proyecto.getLastModifiedDate());

		// Se cambia el nombre para comprobar lastModifiedBy y lastModifiedDate
		proyecto.setNombre("Superproyecto 1");

		entityManager.persist(proyecto);
		entityManager.flush();
		entityManager.clear();

		logger.info("--------------------------------------");
		logger.info("Proyecto creado por: " + proyecto.getCreatedBy());
		logger.info("Proyecto creado en: " + proyecto.getCreatedDate());
		logger.info("Proyecto modificado por: " + proyecto.getLastModifiedBy());
		logger.info("Proyecto modificado en: " + proyecto.getLastModifiedDate());

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);

		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);
		// assertEquals(proyectoFind, proyecto);

		idUsuario = usuario.getId();
		assertNotNull(idUsuario);

		UsuarioImpl usuarioFind = (UsuarioImpl) entityManager.find(
				UsuarioImpl.class, idUsuario);
		assertEquals(usuarioFind, usuario);

		// Se añade el socio a una colección para poder crear la tarea
		Set<UsuarioImpl> usuarios = new HashSet<UsuarioImpl>();
		usuarios.add(usuarioFind);

		// Se crea la tarea
		Tarea tarea = new Tarea(proyectoFind, "1ª tarea del proyecto",
				EstadoTarea.PREVIO, new LocalDate("2014-03-01"), new LocalDate(
						"2014-04-01"), usuarios);

		proyectoFind.getTareas().add(tarea);

		logger.info("Valores de la tarea:");
		logger.info("Id del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getId());
		logger.info("Id de la tarea: "
				+ proyectoFind.getTareas().iterator().next().getId());
		logger.info("Nombre del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getNombre());
		logger.info("Nombre de la tarea: "
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

		entityManager.persist(proyectoFind);
		entityManager.flush();
		entityManager.clear();

		idProyecto = proyectoFind.getId();
		assertNotNull(idProyecto);

		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);
		assertEquals(proyectoFind, proyecto);

		// Se comprueba que se ha actualizado el proyecto con las tareas
		idTarea = proyectoFind.getTareas().iterator().next().getId();
		idProyectoTarea = proyectoFind.getTareas().iterator().next()
				.getProyecto().getId();
		assertNotNull(idTarea);
		assertNotNull(idProyectoTarea);
		assertEquals(1, proyectoFind.getTareas().size());
		assertEquals(proyectoFind, proyectoFind.getTareas().iterator().next()
				.getProyecto());

		logger.info("Valores de la tarea:");
		logger.info("Id del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getId());
		logger.info("Id de la tarea: "
				+ proyectoFind.getTareas().iterator().next().getId());
		logger.info("Nombre del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getNombre());
		logger.info("Nombre de la tarea: "
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

		// AuditReader auditReader = AuditReaderFactory.get(entityManager);
		// logger.info("El proyecto está auditado???: " +
		// auditReader.isEntityClassAudited(Proyecto.class));
		// logger.info("1ª Revisión del proyecto: " +
		// auditReader.find(Proyecto.class, idProyecto, 1));
		// logger.info("2ª Revisión del proyecto: " +
		// auditReader.find(Proyecto.class, idProyecto, 2));
		// logger.info(auditReader.getCurrentRevision(Proyecto.class, false));
		// logger.info(auditReader.getRevisions(Proyecto.class,
		// idProyecto).size());

	}

	@Test
	@Transactional
	public void testSaveAndFindWithTareas() throws Exception {
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
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		// Se crea un usuario (socio) que coordina y que introduce la tarea
		// (usuario)
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

		Set<Socio> coordinadores = new HashSet<Socio>();
		coordinadores.add((Socio) usuario);

		// Se crea un proyecto para poder establecerlo en la tarea
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 01");
		proyecto.setPresupuesto(new BigDecimal("2500.50"));
		proyecto.setDescripcion("Este es el primer proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto.setFechaFin(new LocalDate("2014-05-01"));
		proyecto.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto.setCoordinadores(coordinadores);
		proyecto.setBalance("N/A");
		proyecto.setEntidadCliente(entidadFind);

		entityManager.persist(proyecto);
		entityManager.flush();
		entityManager.clear();

		logger.info("Valores del proyecto:");
		logger.info("Nombre del proyecto: " + proyecto.getNombre());
		logger.info("Presupuesto del proyecto: " + proyecto.getPresupuesto());
		logger.info("Descripción del proyecto: " + proyecto.getDescripcion());
		logger.info("Fecha de inicio del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyecto.getFechaInicio()));
		logger.info("Fecha fin del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyecto.getFechaFin()));
		logger.info("Estado del proyecto: " + proyecto.getEstadoProyecto());
		logger.info("Coordinadores del proyecto: "
				+ proyecto.getCoordinadores().iterator().next()
						.getApellidosUsuario());
		logger.info("Balance del proyecto: " + proyecto.getBalance());
		logger.info("Entidad cliente del proyecto: "
				+ proyecto.getEntidadCliente().getNombreEntidad());

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);

		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);
		assertEquals(proyectoFind, proyecto);

		idUsuario = usuario.getId();
		assertNotNull(idUsuario);

		UsuarioImpl usuarioFind = (UsuarioImpl) entityManager.find(
				UsuarioImpl.class, idUsuario);

		// Se añade a una colección
		Set<UsuarioImpl> usuarios = new HashSet<UsuarioImpl>();
		usuarios.add(usuarioFind);

		// Se crea la tarea
		Tarea tarea = new Tarea(proyectoFind, "1ª tarea del proyecto",
				EstadoTarea.PREVIO, new LocalDate("2014-03-01"), new LocalDate(
						"2014-04-01"), usuarios);

		proyectoFind.getTareas().add(tarea);

		logger.info("Valores de la tarea:");
		logger.info("Id del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getId());
		logger.info("Id de la tarea: "
				+ proyectoFind.getTareas().iterator().next().getId());
		logger.info("Nombre del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getNombre());
		logger.info("Nombre de la tarea: "
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

		entityManager.persist(proyectoFind);
		entityManager.flush();
		entityManager.clear();

		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);

		// Se comprueba que se ha actualizado el proyecto con las tareas
		idTarea = proyectoFind.getTareas().iterator().next().getId();
		idProyectoTarea = proyectoFind.getTareas().iterator().next()
				.getProyecto().getId();
		assertNotNull(idTarea);
		assertNotNull(idProyectoTarea);

		logger.info("Valores de la tarea:");
		logger.info("Id del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getId());
		logger.info("Id de la tarea: "
				+ proyectoFind.getTareas().iterator().next().getId());
		logger.info("Nombre del proyecto: "
				+ proyectoFind.getTareas().iterator().next().getProyecto()
						.getNombre());
		logger.info("Nombre de la tarea: "
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

		Proyecto otroProyecto = (Proyecto) entityManager
				.createQuery(
						"select p from Proyecto p join p.tareas t where t.descripcion=:descripcion")
				.setParameter("descripcion", "1ª tarea del proyecto")
				.getSingleResult();
		assertEquals(1, otroProyecto.getTareas().size());
		assertEquals(otroProyecto, otroProyecto.getTareas().iterator().next()
				.getProyecto());

	}

	@Test
	@Transactional
	public void testSaveWithIncidencias() throws Exception {
		long idProyecto;
		long idIncidencia;
		long idProyectoIncidencia;
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
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		// Se crea un usuario (socio) que coordina y que introduce la incidencia
		// (usuario)
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

		Set<Socio> coordinadores = new HashSet<Socio>();
		coordinadores.add((Socio) usuario);

		// Se crea un proyecto para poder establecerlo en la entidad
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 01");
		proyecto.setPresupuesto(new BigDecimal("2500.50"));
		proyecto.setDescripcion("Este es el primer proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto.setFechaFin(new LocalDate("2014-05-01"));
		proyecto.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto.setCoordinadores(coordinadores);
		proyecto.setBalance("N/A");
		proyecto.setEntidadCliente(entidadFind);

		entityManager.persist(proyecto);
		entityManager.flush();
		entityManager.clear();

		logger.info("Valores del proyecto:");
		logger.info("Nombre del proyecto: " + proyecto.getNombre());
		logger.info("Presupuesto del proyecto: " + proyecto.getPresupuesto());
		logger.info("Descripción del proyecto: " + proyecto.getDescripcion());
		logger.info("Fecha de inicio del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyecto.getFechaInicio()));
		logger.info("Fecha fin del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyecto.getFechaFin()));
		logger.info("Estado del proyecto: " + proyecto.getEstadoProyecto());
		logger.info("Coordinadores del proyecto: "
				+ proyecto.getCoordinadores().iterator().next()
						.getApellidosUsuario());
		logger.info("Balance del proyecto: " + proyecto.getBalance());
		logger.info("Entidad cliente del proyecto: "
				+ proyecto.getEntidadCliente().getNombreEntidad());

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);

		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);
		assertEquals(proyectoFind, proyecto);

		idUsuario = usuario.getId();
		assertNotNull(idUsuario);

		UsuarioImpl usuarioFind = (UsuarioImpl) entityManager.find(
				UsuarioImpl.class, idUsuario);

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);

		// Se crea la incidencia
		Incidencia incidencia = new Incidencia(proyectoFind, usuarioFind,
				"1ª incidencia del proyecto", new LocalDateTime());

		proyectoFind.getIncidencias().add(incidencia);

		logger.info("Valores de la incidencia:");
		logger.info("Id del proyecto: "
				+ proyectoFind.getIncidencias().iterator().next().getProyecto()
						.getId());
		logger.info("Id de la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next().getId());
		logger.info("Nombre del proyecto: "
				+ proyectoFind.getIncidencias().iterator().next().getProyecto()
						.getNombre());
		logger.info("Usuario que introduce la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next().getUsuario()
						.getNombreUsuario()
				+ " "
				+ proyectoFind.getIncidencias().iterator().next().getUsuario()
						.getApellidosUsuario());
		logger.info("Descripción de la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next()
						.getDescripcion());
		logger.info("Fecha de la incidencia: "
				+ DateTimeFormat.mediumDateTime().print(
						proyectoFind.getIncidencias().iterator().next()
								.getFechaYHora()));

		entityManager.persist(proyectoFind);
		entityManager.flush();
		entityManager.clear();

		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);

		// Se comprueba que se ha actualizado el proyecto con las incidencias
		idIncidencia = proyectoFind.getIncidencias().iterator().next().getId();
		idProyectoIncidencia = proyectoFind.getIncidencias().iterator().next()
				.getProyecto().getId();
		assertNotNull(idIncidencia);
		assertNotNull(idProyectoIncidencia);

		logger.info("Valores de la incidencia:");
		logger.info("Id del proyecto: "
				+ proyectoFind.getIncidencias().iterator().next().getProyecto()
						.getId());
		logger.info("Id de la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next().getId());
		logger.info("Nombre del proyecto: "
				+ proyectoFind.getIncidencias().iterator().next().getProyecto()
						.getNombre());
		logger.info("Usuario que introduce la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next().getUsuario()
						.getNombreUsuario()
				+ " "
				+ proyectoFind.getIncidencias().iterator().next().getUsuario()
						.getApellidosUsuario());
		logger.info("Descripción de la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next()
						.getDescripcion());
		logger.info("Fecha de la incidencia: "
				+ DateTimeFormat.mediumDateTime().print(
						proyectoFind.getIncidencias().iterator().next()
								.getFechaYHora()));

		// Proyecto otroProyecto = (Proyecto) entityManager
		// .createQuery(
		// "select p from Proyecto p join p.incidencias i where i.descripcion=:descripcion")
		// .setParameter("descripcion", "1ª incidencia del proyecto")
		// .getSingleResult();
		// assertEquals(1, otroProyecto.getIncidencias().size());
		// assertEquals(otroProyecto, otroProyecto.getIncidencias().iterator()
		// .next().getProyecto());

	}

	@Test
	@Transactional
	public void testSaveAndFindWithIncidencias() throws Exception {
		long idProyecto;
		long idIncidencia;
		long idProyectoIncidencia;
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
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		// Se crea un usuario (socio) que coordina y que introduce la incidencia
		// (usuario)
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

		Set<Socio> coordinadores = new HashSet<Socio>();
		coordinadores.add((Socio) usuario);

		// Se crea un proyecto para poder establecerlo en la entidad
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 01");
		proyecto.setPresupuesto(new BigDecimal("2500.50"));
		proyecto.setDescripcion("Este es el primer proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto.setFechaFin(new LocalDate("2014-05-01"));
		proyecto.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto.setCoordinadores(coordinadores);
		proyecto.setBalance("N/A");
		proyecto.setEntidadCliente(entidadFind);

		entityManager.persist(proyecto);
		entityManager.flush();
		entityManager.clear();

		logger.info("Valores del proyecto:");
		logger.info("Nombre del proyecto: " + proyecto.getNombre());
		logger.info("Presupuesto del proyecto: " + proyecto.getPresupuesto());
		logger.info("Descripción del proyecto: " + proyecto.getDescripcion());
		logger.info("Fecha de inicio del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyecto.getFechaInicio()));
		logger.info("Fecha fin del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyecto.getFechaFin()));
		logger.info("Estado del proyecto: " + proyecto.getEstadoProyecto());
		logger.info("Coordinadores del proyecto: "
				+ proyecto.getCoordinadores().iterator().next()
						.getApellidosUsuario());
		logger.info("Balance del proyecto: " + proyecto.getBalance());
		logger.info("Entidad cliente del proyecto: "
				+ proyecto.getEntidadCliente().getNombreEntidad());

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);

		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);
		assertEquals(proyectoFind, proyecto);

		idUsuario = usuario.getId();
		assertNotNull(idUsuario);

		UsuarioImpl usuarioFind = (UsuarioImpl) entityManager.find(
				UsuarioImpl.class, idUsuario);

		// Se crea la incidencia
		Incidencia incidencia = new Incidencia(proyectoFind, usuarioFind,
				"1ª incidencia del proyecto", new LocalDateTime());

		proyectoFind.getIncidencias().add(incidencia);

		logger.info("Valores de la incidencia:");
		logger.info("Id del proyecto: "
				+ proyectoFind.getIncidencias().iterator().next().getProyecto()
						.getId());
		logger.info("Id de la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next().getId());
		logger.info("Nombre del proyecto: "
				+ proyectoFind.getIncidencias().iterator().next().getProyecto()
						.getNombre());
		logger.info("Usuario que introduce la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next().getUsuario()
						.getNombreUsuario()
				+ " "
				+ proyectoFind.getIncidencias().iterator().next().getUsuario()
						.getApellidosUsuario());
		logger.info("Descripción de la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next()
						.getDescripcion());
		logger.info("Fecha de la incidencia: "
				+ DateTimeFormat.mediumDateTime().print(
						proyectoFind.getIncidencias().iterator().next()
								.getFechaYHora()));

		entityManager.persist(proyectoFind);
		entityManager.flush();
		entityManager.clear();

		// Se comprueba que se ha actualizado el proyecto con las incidencias
		idIncidencia = proyectoFind.getIncidencias().iterator().next().getId();
		idProyectoIncidencia = proyectoFind.getIncidencias().iterator().next()
				.getProyecto().getId();
		assertNotNull(idIncidencia);
		assertNotNull(idProyectoIncidencia);

		logger.info("Valores de la incidencia:");
		logger.info("Id del proyecto: "
				+ proyectoFind.getIncidencias().iterator().next().getProyecto()
						.getId());
		logger.info("Id de la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next().getId());
		logger.info("Nombre del proyecto: "
				+ proyectoFind.getIncidencias().iterator().next().getProyecto()
						.getNombre());
		logger.info("Usuario que introduce la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next().getUsuario()
						.getNombreUsuario()
				+ " "
				+ proyectoFind.getIncidencias().iterator().next().getUsuario()
						.getApellidosUsuario());
		logger.info("Descripción de la incidencia: "
				+ proyectoFind.getIncidencias().iterator().next()
						.getDescripcion());
		logger.info("Fecha de la incidencia: "
				+ DateTimeFormat.mediumDateTime().print(
						proyectoFind.getIncidencias().iterator().next()
								.getFechaYHora()));

		Proyecto otroProyecto = (Proyecto) entityManager
				.createQuery(
						"select p from Proyecto p join p.incidencias i where i.descripcion=:descripcion")
				.setParameter("descripcion", "1ª incidencia del proyecto")
				.getSingleResult();
		assertEquals(1, otroProyecto.getIncidencias().size());
		assertEquals(otroProyecto, otroProyecto.getIncidencias().iterator()
				.next().getProyecto());

	}

	@Test
	@Transactional
	public void testSaveWithEntidadesColaboradoras() throws Exception {
		long idProyecto;
		long idEntidad;

		// Se crea una Entidad para poder establecerla en el proyecto como
		// entidad cliente y como entidad colaboradora
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

		// Se crea una Entidad para poder establecerla en el proyecto como
		// entidad colaboradora
		Entidad otraEntidad = new Entidad();
		otraEntidad.setIdentificacion("A7654321S");
		otraEntidad.setNombreEntidad("Jugando se Aprende");
		otraEntidad.setCorreoElectronico("aprenderjugando@hotmail.com");
		otraEntidad.setTelefono("34917411255");
		otraEntidad.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/Canal del Bósforo, 54 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		otraEntidad.setDomicilio(domicilio);
		otraEntidad.setNombrePersonaContacto("Marta López Rodriguez");
		otraEntidad.setCorreoElectronicoPersonaContacto("mlopez@gmail.com");
		otraEntidad.setTelefonoPersonaContacto("917411255");
		otraEntidad.setTelefonoMovilPersonaContacto("609252525");
		otraEntidad.setEliminado(false);

		entityManager.persist(otraEntidad);
		entityManager.flush();
		entityManager.clear();

		idEntidad = entidad.getId();
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		idEntidad = otraEntidad.getId();
		Entidad otraEntidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		// Se crea un usuario (socio) que coordina
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

		Set<Socio> coordinadores = new HashSet<Socio>();
		coordinadores.add((Socio) usuario);

		// Se crea un proyecto para poder establecerlo en la tarea
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 01");
		proyecto.setPresupuesto(new BigDecimal("2500.50"));
		proyecto.setDescripcion("Este es el primer proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto.setFechaFin(new LocalDate("2014-05-01"));
		proyecto.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto.setCoordinadores(coordinadores);
		proyecto.setBalance("N/A");
		proyecto.setEntidadCliente(entidadFind);
		proyecto.getEntidadesColaboradoras().add(entidadFind);
		proyecto.getEntidadesColaboradoras().add(otraEntidadFind);

		entityManager.persist(proyecto);
		entityManager.flush();
		entityManager.clear();

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);

		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);
		assertEquals(proyectoFind, proyecto);

		logger.info("Valores del proyecto:");
		logger.info("Nombre del proyecto: " + proyectoFind.getNombre());
		logger.info("Presupuesto del proyecto: "
				+ proyectoFind.getPresupuesto());
		logger.info("Descripción del proyecto: "
				+ proyectoFind.getDescripcion());
		logger.info("Fecha de inicio del proyecto: "
				+ DateTimeFormat.mediumDate().print(
						proyectoFind.getFechaInicio()));
		logger.info("Fecha fin del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyectoFind.getFechaFin()));
		logger.info("Estado del proyecto: " + proyectoFind.getEstadoProyecto());
		logger.info("Coordinadores del proyecto: "
				+ proyectoFind.getCoordinadores().iterator().next()
						.getApellidosUsuario());
		logger.info("Balance del proyecto: " + proyectoFind.getBalance());
		logger.info("Entidad cliente del proyecto: "
				+ proyectoFind.getEntidadCliente().getNombreEntidad());
		logger.info("Entidades Colaboradoras del proyecto: ");
		for (Entidad e : proyectoFind.getEntidadesColaboradoras()) {
			logger.info(e.getNombreEntidad());
		}

		assertEquals(2, proyectoFind.getEntidadesColaboradoras().size());
		assertEquals(proyectoFind, proyectoFind.getEntidadesColaboradoras()
				.iterator().next().getProyectosColaborados().iterator().next());

		// exception.expect(IllegalArgumentException.class);
	}

	@Test
	@Transactional
	public void testSaveAndFindWithEntidadesColaboradoras() throws Exception {
		long idProyecto;
		long idEntidad;

		// Se crea una Entidad para poder establecerla en el proyecto como
		// entidad cliente y como entidad colaboradora
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

		// Se crea una Entidad para poder establecerla en el proyecto como
		// entidad colaboradora
		Entidad otraEntidad = new Entidad();
		otraEntidad.setIdentificacion("A7654321S");
		otraEntidad.setNombreEntidad("Jugando se Aprende");
		otraEntidad.setCorreoElectronico("aprenderjugando@hotmail.com");
		otraEntidad.setTelefono("34917411255");
		otraEntidad.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/Canal del Bósforo, 54 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		otraEntidad.setDomicilio(domicilio);
		otraEntidad.setNombrePersonaContacto("Marta López Rodriguez");
		otraEntidad.setCorreoElectronicoPersonaContacto("mlopez@gmail.com");
		otraEntidad.setTelefonoPersonaContacto("917411255");
		otraEntidad.setTelefonoMovilPersonaContacto("609252525");
		otraEntidad.setEliminado(false);

		entityManager.persist(otraEntidad);
		entityManager.flush();
		entityManager.clear();

		idEntidad = entidad.getId();
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		idEntidad = otraEntidad.getId();
		Entidad otraEntidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		// Se crea un usuario (socio) que coordina
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

		Set<Socio> coordinadores = new HashSet<Socio>();
		coordinadores.add((Socio) usuario);

		// Se crea un proyecto para poder establecerlo en la tarea
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 01");
		proyecto.setPresupuesto(new BigDecimal("2500.50"));
		proyecto.setDescripcion("Este es el primer proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto.setFechaFin(new LocalDate("2014-05-01"));
		proyecto.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto.setCoordinadores(coordinadores);
		proyecto.setBalance("N/A");
		proyecto.setEntidadCliente(entidadFind);
		proyecto.getEntidadesColaboradoras().add(entidadFind);
		proyecto.getEntidadesColaboradoras().add(otraEntidadFind);

		entityManager.persist(proyecto);
		entityManager.flush();
		entityManager.clear();

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);

		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);
		assertEquals(proyectoFind, proyecto);

		logger.info("Valores del proyecto:");
		logger.info("Nombre del proyecto: " + proyectoFind.getNombre());
		logger.info("Presupuesto del proyecto: "
				+ proyectoFind.getPresupuesto());
		logger.info("Descripción del proyecto: "
				+ proyectoFind.getDescripcion());
		logger.info("Fecha de inicio del proyecto: "
				+ DateTimeFormat.mediumDate().print(
						proyectoFind.getFechaInicio()));
		logger.info("Fecha fin del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyectoFind.getFechaFin()));
		logger.info("Estado del proyecto: " + proyectoFind.getEstadoProyecto());
		logger.info("Coordinadores del proyecto: "
				+ proyectoFind.getCoordinadores().iterator().next()
						.getApellidosUsuario());
		logger.info("Balance del proyecto: " + proyectoFind.getBalance());
		logger.info("Entidad cliente del proyecto: "
				+ proyectoFind.getEntidadCliente().getNombreEntidad());
		logger.info("Entidades Colaboradoras del proyecto: ");
		for (Entidad e : proyectoFind.getEntidadesColaboradoras()) {
			logger.info(e.getNombreEntidad());
		}

		Proyecto otroProyecto = (Proyecto) entityManager
				.createQuery(
						"select p from Proyecto p join p.entidadesColaboradoras e where e.nombreEntidad=:nombre1 or e.nombreEntidad=:nombre2")
				.setParameter("nombre1", "Aprender Jugando")
				.setParameter("nombre2", "Jugando se Aprende")
				.getSingleResult();
		assertEquals(2, otroProyecto.getEntidadesColaboradoras().size());

		otroProyecto = (Proyecto) entityManager
				.createQuery(
						"select p from Proyecto p join p.entidadesColaboradoras e where e.nombreEntidad=:nombreEntidad")
				.setParameter("nombreEntidad", "Aprender Jugando")
				.getSingleResult();
		assertEquals(2, otroProyecto.getEntidadesColaboradoras().size());
		assertEquals(otroProyecto, otroProyecto.getEntidadesColaboradoras()
				.iterator().next().getProyectosColaborados().iterator().next());

		// exception.expect(IllegalArgumentException.class);
	}

	@Test
	@Transactional
	public void testSaveWithUsuarios() throws Exception {
		long idProyecto;
		long idEntidad;

		// Se crea una Entidad para poder establecerla en el proyecto como
		// entidad cliente y como entidad colaboradora
		// Se crea una Entidad para poder establecerla en el proyecto como
		// entidad cliente y como entidad colaboradora
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
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		// Se crea un usuario
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
		usuario.setApellidosUsuario("Camelas Atope");
		usuario.setFechaNacimiento(new LocalDate("1970-09-14"));
		usuario.setCorreoElectronicoAsociacion("jalvarez@abierto.com");
		usuario.setTelefonoAsociacion("917778899");
		usuario.setTelefonoMovilAsociacion("666445566");
		usuario.setActivo(true);
		((Socio) usuario).setEliminado(false);

		// Se crea otro usuario que se añadirá al Set de usuarios para crear la
		// tarea
		UsuarioImpl usuario2 = new Socio(); // Usuario
		usuario2.setIdentificacion("A1234567B");
		usuario2.setCorreoElectronico("baprenderjugando@hotmail.com");
		usuario2.setTelefono("34917411255");
		usuario2.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/ Cha cha chá, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		usuario2.setDomicilio(domicilio);
		usuario2.setNombreUsuario("Benito");
		usuario2.setApellidosUsuario("Alvarez del Vas");
		usuario2.setFechaNacimiento(new LocalDate("1978-01-15"));
		usuario2.setCorreoElectronicoAsociacion("jcamelas@abierto.com");
		usuario2.setTelefonoAsociacion("917778895");
		usuario2.setTelefonoMovilAsociacion("666445565");
		usuario2.setActivo(true);
		((Socio) usuario2).setEliminado(false);
		Set<Socio> coordinadores = new HashSet<Socio>();
		coordinadores.add((Socio) usuario);
		coordinadores.add((Socio) usuario2);

		// Se crea un proyecto
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 01");
		proyecto.setPresupuesto(new BigDecimal("2500.5"));
		proyecto.setDescripcion("Este es el primer proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto.setFechaFin(new LocalDate("2014-05-01"));
		proyecto.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto.setCoordinadores(coordinadores);
		proyecto.setBalance("N/A");
		proyecto.setEntidadCliente(entidadFind);
		proyecto.getEntidadesColaboradoras().add(entidadFind);
		proyecto.getUsuarios().add(usuario);
		proyecto.getUsuarios().add(usuario2);

		entityManager.persist(proyecto);
		entityManager.flush();
		// entityManager.refresh(proyecto); // Para que funcione el OrderBy
		entityManager.clear();

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);

		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);
		assertEquals(proyectoFind, proyecto);

		assertEquals(2, proyectoFind.getUsuarios().size());
		assertEquals(proyectoFind, proyectoFind.getUsuarios().iterator().next()
				.getProyectosParticipados().iterator().next());

		logger.info("Valores del proyecto:");
		logger.info("ID del proyecto: " + proyectoFind.getId());
		logger.info("Nombre del proyecto: " + proyectoFind.getNombre());
		logger.info("Presupuesto del proyecto: "
				+ proyectoFind.getPresupuesto());
		logger.info("Descripción del proyecto: "
				+ proyectoFind.getDescripcion());
		logger.info("Fecha de inicio del proyecto: "
				+ DateTimeFormat.mediumDate().print(
						proyectoFind.getFechaInicio()));
		logger.info("Fecha fin del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyectoFind.getFechaFin()));
		logger.info("Estado del proyecto: " + proyectoFind.getEstadoProyecto());
		logger.info("Coordinadores del proyecto: "
				+ proyectoFind.getCoordinadores());
		logger.info("Balance del proyecto: " + proyectoFind.getBalance());
		logger.info("Entidad cliente del proyecto: "
				+ proyectoFind.getEntidadCliente().getNombreEntidad());
		logger.info("Entidades Colaboradoras del proyecto: ");
		for (Entidad e : proyectoFind.getEntidadesColaboradoras()) {
			logger.info(e.getNombreEntidad());
		}
		logger.info("Usuarios participantes en el proyecto: ");
		for (UsuarioImpl u : proyectoFind.getUsuarios()) {
			logger.info(u.getApellidosUsuario() + ", " + u.getNombreUsuario());
		}

		// exception.expect(IllegalArgumentException.class);
	}

	@Test
	@Transactional
	public void testSaveAndFindWithUsuarios() throws Exception {
		long idProyecto;
		long idEntidad;

		// Se crea una Entidad para poder establecerla en el proyecto como
		// entidad cliente y como entidad colaboradora
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
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		// Se crea un usuario
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
		usuario.setApellidosUsuario("Camelas Atope");
		usuario.setFechaNacimiento(new LocalDate("1970-09-14"));
		usuario.setCorreoElectronicoAsociacion("jalvarez@abierto.com");
		usuario.setTelefonoAsociacion("917778899");
		usuario.setTelefonoMovilAsociacion("666445566");
		usuario.setActivo(true);
		((Socio) usuario).setEliminado(false);

		// Se crea otro usuario que se añadirá al Set de usuarios para crear la
		// tarea
		UsuarioImpl usuario2 = new Socio(); // Usuario
		usuario2.setIdentificacion("A1234567B");
		usuario2.setCorreoElectronico("baprenderjugando@hotmail.com");
		usuario2.setTelefono("34917411255");
		usuario2.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/ Cha cha chá, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		usuario2.setDomicilio(domicilio);
		usuario2.setNombreUsuario("Benito");
		usuario2.setApellidosUsuario("Alvarez del Vas");
		usuario2.setFechaNacimiento(new LocalDate("1978-01-15"));
		usuario2.setCorreoElectronicoAsociacion("jcamelas@abierto.com");
		usuario2.setTelefonoAsociacion("917778895");
		usuario2.setTelefonoMovilAsociacion("666445565");
		usuario2.setActivo(true);
		((Socio) usuario2).setEliminado(false);
		Set<Socio> coordinadores = new HashSet<Socio>();
		coordinadores.add((Socio) usuario);
		coordinadores.add((Socio) usuario2);

		// Se crea un proyecto
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 01");
		proyecto.setPresupuesto(new BigDecimal("2500.5"));
		proyecto.setDescripcion("Este es el primer proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto.setFechaFin(new LocalDate("2014-05-01"));
		proyecto.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto.setCoordinadores(coordinadores);
		proyecto.setBalance("N/A");
		proyecto.setEntidadCliente(entidadFind);
		proyecto.getEntidadesColaboradoras().add(entidadFind);
		proyecto.getUsuarios().add(usuario);
		proyecto.getUsuarios().add(usuario2);

		entityManager.persist(proyecto);
		entityManager.flush();
		entityManager.clear();

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);

		logger.info("Valores del proyecto:");
		logger.info("ID del proyecto: " + proyectoFind.getId());
		logger.info("Nombre del proyecto: " + proyectoFind.getNombre());
		logger.info("Presupuesto del proyecto: "
				+ proyectoFind.getPresupuesto());
		logger.info("Descripción del proyecto: "
				+ proyectoFind.getDescripcion());
		logger.info("Fecha de inicio del proyecto: "
				+ DateTimeFormat.mediumDate().print(
						proyectoFind.getFechaInicio()));
		logger.info("Fecha fin del proyecto: "
				+ DateTimeFormat.mediumDate().print(proyectoFind.getFechaFin()));
		logger.info("Estado del proyecto: " + proyectoFind.getEstadoProyecto());
		logger.info("Coordinadores del proyecto: "
				+ proyectoFind.getCoordinadores());
		logger.info("Balance del proyecto: " + proyectoFind.getBalance());
		logger.info("Entidad cliente del proyecto: "
				+ proyectoFind.getEntidadCliente().getNombreEntidad());
		logger.info("Entidades Colaboradoras del proyecto: ");
		for (Entidad e : proyectoFind.getEntidadesColaboradoras()) {
			logger.info(e.getNombreEntidad());
		}
		logger.info("Usuarios participantes en el proyecto: ");
		for (UsuarioImpl u : proyectoFind.getUsuarios()) {
			logger.info(u.getApellidosUsuario() + ", " + u.getNombreUsuario());
		}

		Proyecto otroProyecto = (Proyecto) entityManager
				.createQuery(
						"select p from Proyecto p join p.usuarios u where u.nombreUsuario=:nombre1 or u.nombreUsuario=:nombre2")
				.setParameter("nombre1", "Jacinto")
				.setParameter("nombre2", "Benito").getSingleResult();
		assertEquals(2, otroProyecto.getUsuarios().size());
		assertEquals(otroProyecto, otroProyecto.getUsuarios().iterator().next()
				.getProyectosParticipados().iterator().next());

		// exception.expect(IllegalArgumentException.class);
	}
}
