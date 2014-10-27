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
public class UsuarioPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;

	static Logger logger = Logger.getLogger(UsuarioPersistenceTests.class);

	@Test
	@Transactional
	public void testSaveUsuarioAndFind() throws Exception {
		long idUsuario;

		// Se crea un usuario
		UsuarioImpl usuario = new Socio(); // Usuario
		usuario.setIdentificacion("A1234567Z");
		usuario.setCorreoElectronico("aprenderjugando@hotmail.com");
		usuario.setTelefono("34917411255");
		usuario.setTelefonoMovil("34666147799");
		// Domicilio
		Domicilio domicilio = new Domicilio("C/de la Raza, 21 3� A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "Espa�a");
		usuario.setDomicilio(domicilio);
		usuario.setNombreUsuario("Jacinto");
		usuario.setApellidosUsuario("Alvarez del Vas");
		usuario.setFechaNacimiento(new LocalDate("1970-09-14"));
		usuario.setCorreoElectronicoAsociacion("jalvarez@abierto.com");
		usuario.setTelefonoAsociacion("917778899");
		usuario.setTelefonoMovilAsociacion("666445566");
		usuario.setActivo(true);
		((Socio) usuario).setEliminado(false);

		logger.info("Valores del usuario (instancia):");
		logger.info("Identificaci�n: " + usuario.getIdentificacion());
		logger.info("Correo Electr�nico: " + usuario.getCorreoElectronico());
		logger.info("Tel�fono: " + usuario.getTelefono());
		logger.info("Tel�fono M�vil: " + usuario.getTelefonoMovil());
		logger.info("Direcci�n: " + usuario.getDomicilio().getDireccion());
		logger.info("Poblaci�n: " + usuario.getDomicilio().getPoblacion());
		logger.info("Provincia: " + usuario.getDomicilio().getProvincia());
		logger.info("Comunidad Aut�noma: "
				+ usuario.getDomicilio().getComunidadAutonoma());
		logger.info("C�digo Postal: "
				+ usuario.getDomicilio().getCodigoPostal());
		logger.info("Pa�s: " + usuario.getDomicilio().getPais());
		logger.info("Nombre: " + usuario.getNombreUsuario());
		logger.info("Apellidos: " + usuario.getApellidosUsuario());
		logger.info("Fecha Nacimiento: "
				+ DateTimeFormat.mediumDate().print(
						usuario.getFechaNacimiento()));
		logger.info("Correo Electr�nico Asociaci�n: "
				+ usuario.getCorreoElectronicoAsociacion());
		logger.info("Tel�fono Asociaci�n: " + usuario.getTelefonoAsociacion());
		logger.info("Tel�fono M�vil Asociaci�n: "
				+ usuario.getTelefonoMovilAsociacion());
		logger.info("Usuario creado por: " + usuario.getCreatedBy());
		logger.info("Usuario creado en: " + usuario.getCreatedDate());
		logger.info("Usuario modificado por: " + usuario.getLastModifiedBy());
		logger.info("Usuario modificado en: " + usuario.getLastModifiedDate());

		entityManager.persist(usuario);
		entityManager.flush();
		entityManager.clear();

		idUsuario = usuario.getId();
		assertNotNull(usuario);

		UsuarioImpl usuarioFind = (UsuarioImpl) entityManager.find(
				UsuarioImpl.class, idUsuario);
		assertEquals(usuarioFind, usuario);

		logger.info("Valores del usuario (bd):");
		logger.info("Identificaci�n: " + usuarioFind.getIdentificacion());
		logger.info("Correo Electr�nico: " + usuarioFind.getCorreoElectronico());
		logger.info("Tel�fono: " + usuarioFind.getTelefono());
		logger.info("Tel�fono M�vil: " + usuarioFind.getTelefonoMovil());
		logger.info("Direcci�n: " + usuarioFind.getDomicilio().getDireccion());
		logger.info("Poblaci�n: " + usuarioFind.getDomicilio().getPoblacion());
		logger.info("Provincia: " + usuarioFind.getDomicilio().getProvincia());
		logger.info("Comunidad Aut�noma: "
				+ usuarioFind.getDomicilio().getComunidadAutonoma());
		logger.info("C�digo Postal: "
				+ usuarioFind.getDomicilio().getCodigoPostal());
		logger.info("Pa�s: " + usuarioFind.getDomicilio().getPais());
		logger.info("Nombre: " + usuarioFind.getNombreUsuario());
		logger.info("Apellidos: " + usuarioFind.getApellidosUsuario());
		logger.info("Fecha Nacimiento: "
				+ DateTimeFormat.mediumDate().print(
						usuario.getFechaNacimiento()));
		logger.info("Correo Electr�nico Asociaci�n: "
				+ usuarioFind.getCorreoElectronicoAsociacion());
		logger.info("Tel�fono Asociaci�n: "
				+ usuarioFind.getTelefonoAsociacion());
		logger.info("Tel�fono M�vil Asociaci�n: "
				+ usuarioFind.getTelefonoMovilAsociacion());
		logger.info("Usuario creado por: " + usuario.getCreatedBy());
		logger.info("Usuario creado en: " + usuario.getCreatedDate());
		logger.info("Usuario modificado por: " + usuario.getLastModifiedBy());
		logger.info("Usuario modificado en: " + usuario.getLastModifiedDate());

		Socio socioFind = (Socio) entityManager.find(Socio.class, idUsuario);

		logger.info("Valores del socio (bd):");
		logger.info("Identificaci�n: " + usuarioFind.getIdentificacion());
		logger.info("Eliminado: " + socioFind.getEliminado());
	}

	@Test
	@Transactional
	public void testSaveUsuarioWithIncidencias() throws Exception {
		long idEntidad;
		long idUsuario;
		long idProyecto;

		// Se crea una Entidad para poder establecerla en el proyecto
		Entidad entidad = new Entidad();
		entidad.setIdentificacion("A1234567S");
		entidad.setNombreEntidad("Aprender Jugando");
		entidad.setCorreoElectronico("aprenderjugando@hotmail.com");
		entidad.setTelefono("34917411255");
		entidad.setTelefonoMovil("34666147799");
		// Domicilio
		Domicilio domicilio = new Domicilio("C/Canal del B�sforo, 54 3� A",
				"Madrid", Provincia.MADRID, ComunidadAutonoma.MADRID, "28022",
				"Espa�a");
		entidad.setDomicilio(domicilio);
		entidad.setNombrePersonaContacto("Marta L�pez Rodriguez");
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

		// Se crea un usuario (socio) que coordina y que introduce la incidencia
		// (usuario)
		UsuarioImpl usuario = new Socio(); // Usuario
		usuario.setIdentificacion("A1234567Z");
		usuario.setCorreoElectronico("aprenderjugando@hotmail.com");
		usuario.setTelefono("34917411255");
		usuario.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/de la Raza, 21 3� A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "Espa�a");
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

		// Se crea un proyecto
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

		// Se crea la incidencia
		Incidencia incidencia = new Incidencia(proyectoFind, usuarioFind,
				"1� incidencia del proyecto", new LocalDateTime());

		proyectoFind.getIncidencias().add(incidencia);

		// Se crea otra incidencia
		Incidencia incidencia2 = new Incidencia(proyectoFind, usuarioFind,
				"2� incidencia del proyecto", new LocalDateTime());

		proyectoFind.getIncidencias().add(incidencia2);

		for (Incidencia incidenciaDeUsuario : proyectoFind.getIncidencias()) {
			logger.info("Incidencia: " + incidenciaDeUsuario);
		}

		entityManager.persist(proyectoFind);
		entityManager.flush();
		entityManager.clear();

		// Se actualiza el usuario para comprobar sus incidencias
		// entityManager.refresh(usuarioFind);
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);
		assertEquals(2, proyectoFind.getIncidencias().size());
		assertEquals(proyectoFind, proyectoFind.getIncidencias().iterator()
				.next().getProyecto());

		idUsuario = usuario.getId();
		assertNotNull(idUsuario);
		usuarioFind = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);

		logger.info("-------------------------------------");
		logger.info(usuarioFind.getIncidencias().size());
		for (Incidencia incidenciaDeUsuario : usuarioFind.getIncidencias()) {
			logger.info("Incidencia: " + incidenciaDeUsuario);
		}
		logger.info("-------------------------------------");
	}

	@Test
	@Transactional
	public void testSaveUsuarioWithTareas() throws Exception {
		long idEntidad;
		long idUsuario;
		long idProyecto;

		// Se crea una Entidad para poder establecerla en el proyecto
		Entidad entidad = new Entidad();
		entidad.setIdentificacion("A1234567S");
		entidad.setNombreEntidad("Aprender Jugando");
		entidad.setCorreoElectronico("aprenderjugando@hotmail.com");
		entidad.setTelefono("34917411255");
		entidad.setTelefonoMovil("34666147799");
		// Domicilio
		Domicilio domicilio = new Domicilio("C/Canal del B�sforo, 54 3� A",
				"Madrid", Provincia.MADRID, ComunidadAutonoma.MADRID, "28022",
				"Espa�a");
		entidad.setDomicilio(domicilio);
		entidad.setNombrePersonaContacto("Marta L�pez Rodriguez");
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

		// Se crea un usuario (socio) que coordina y que introduce la tarea
		// (usuario)
		UsuarioImpl usuario = new Socio(); // Usuario
		usuario.setIdentificacion("A1234567Z");
		usuario.setCorreoElectronico("aprenderjugando@hotmail.com");
		usuario.setTelefono("34917411255");
		usuario.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/de la Raza, 21 3� A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "Espa�a");
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

		// Se crea un proyecto
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

		// Se a�ade a una colecci�n
		Set<UsuarioImpl> usuarios = new HashSet<UsuarioImpl>();
		usuarios.add(usuarioFind);

		// Se crea la 1� tarea (QUE SER� LA 2�)
		Tarea tarea = new Tarea(proyectoFind, "2� tarea del proyecto",
				EstadoTarea.PREVIO, new LocalDate("2014-03-01"), new LocalDate(
						"2014-05-01"), usuarios);

		proyectoFind.getTareas().add(tarea);

		// Se crea la 2� tarea (QUE SER� LA 1�)
		Tarea otraTarea = new Tarea(proyectoFind, "1� tarea del proyecto",
				EstadoTarea.PREVIO, new LocalDate("2014-01-01"), new LocalDate(
						"2014-04-01"), usuarios);

		proyectoFind.getTareas().add(otraTarea);

		for (Tarea tareaDeUsuario : proyectoFind.getTareas()) {
			logger.info("Tarea: " + tareaDeUsuario);
		}

		entityManager.persist(proyectoFind);
		entityManager.flush();
		entityManager.clear();

		// Se actualiza el usuario para comprobar sus tareas
		// entityManager.refresh(usuarioFind);
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);
		assertEquals(2, proyectoFind.getTareas().size());
		assertEquals(proyectoFind, proyectoFind.getTareas().iterator().next()
				.getProyecto());

		logger.info("-------------------------------------");
		for (Tarea tareaDeUsuario : proyectoFind.getTareas()) {
			logger.info("Tarea: " + tareaDeUsuario);
		}
		logger.info("-------------------------------------");

		idUsuario = usuario.getId();
		assertNotNull(idUsuario);
		usuarioFind = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);

		logger.info("-------------------------------------");
		for (Tarea tareaDeUsuario : usuarioFind.getTareas()) {
			logger.info("Tarea: " + tareaDeUsuario);
		}
		logger.info("-------------------------------------");
	}

	@Test
	@Transactional
	public void testSaveUsuarioWithProyectosParticipados() throws Exception {
		long idEntidad;
		long idUsuario;
		long idProyecto;

		// Se crea una Entidad para poder establecerla en el proyecto
		Entidad entidad = new Entidad();
		entidad.setIdentificacion("A1234567S");
		entidad.setNombreEntidad("Aprender Jugando");
		entidad.setCorreoElectronico("aprenderjugando@hotmail.com");
		entidad.setTelefono("34917411255");
		entidad.setTelefonoMovil("34666147799");
		// Domicilio
		Domicilio domicilio = new Domicilio("C/Canal del B�sforo, 54 3� A",
				"Madrid", Provincia.MADRID, ComunidadAutonoma.MADRID, "28022",
				"Espa�a");
		entidad.setDomicilio(domicilio);
		entidad.setNombrePersonaContacto("Marta L�pez Rodriguez");
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

		// Se crea un usuario (Socio) que coordina
		UsuarioImpl usuario = new Socio(); // Usuario
		usuario.setIdentificacion("A1234567Z");
		usuario.setCorreoElectronico("aprenderjugando@hotmail.com");
		usuario.setTelefono("34917411255");
		usuario.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/de la Raza, 21 3� A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "Espa�a");
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

		// Se crea un proyecto para poder establecerlo en la entidad
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 02");
		proyecto.setPresupuesto(new BigDecimal("2500.50"));
		proyecto.setDescripcion("Este es el segundo proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-03-01"));
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
		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);

		// Se crea un 2� proyecto para poder establecerlo en la entidad
		Proyecto proyecto2 = new Proyecto();
		proyecto2.setNombre("Proyecto 01");
		proyecto2.setPresupuesto(new BigDecimal("2500.50"));
		proyecto2.setDescripcion("Este es el primer proyecto.");
		proyecto2.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto2.setFechaFin(new LocalDate("2014-05-01"));
		proyecto2.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto2.getCoordinadores().add((Socio) usuarioFind);
		proyecto2.setBalance("N/A");
		proyecto2.setEntidadCliente(entidadFind);

		entityManager.persist(proyecto2);
		entityManager.flush();
		entityManager.clear();

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);
		idProyecto = proyecto2.getId();
		assertNotNull(idProyecto);
		Proyecto proyecto2Find = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);
		idUsuario = usuario.getId();
		assertNotNull(idUsuario);
		usuarioFind = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);

		// Se a�ade al 1� proyecto
		proyectoFind.getUsuarios().add(usuarioFind);

		// Se a�ade al 2� proyecto
		proyecto2Find.getUsuarios().add(usuarioFind);

		entityManager.persist(proyectoFind);
		entityManager.persist(proyecto2Find);
		entityManager.flush();
		entityManager.clear();

		// entityManager.refresh(usuario);
		idUsuario = usuario.getId();
		assertNotNull(idUsuario);
		usuarioFind = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);

		logger.info("-------------------------------------");
		for (Proyecto proyectoParticipado : usuarioFind
				.getProyectosParticipados()) {
			logger.info("Proyecto: " + proyectoParticipado);
		}
		logger.info("-------------------------------------");

		idProyecto = proyecto2.getId();
		assertNotNull(idProyecto);
		proyecto2Find = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);

		// Eliminamos un proyecto
		entityManager.remove(proyecto2Find);
		entityManager.flush();

		entityManager.refresh(usuarioFind);

		logger.info("-------------------------------------");
		for (Proyecto proyectoParticipado : usuarioFind
				.getProyectosParticipados()) {
			logger.info("Proyecto: " + proyectoParticipado);
		}
		logger.info("-------------------------------------");
	}
}
