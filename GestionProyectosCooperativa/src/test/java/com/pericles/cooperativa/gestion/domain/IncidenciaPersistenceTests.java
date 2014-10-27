package com.pericles.cooperativa.gestion.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
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
import com.pericles.cooperativa.gestion.domain.Incidencia;
import com.pericles.cooperativa.gestion.domain.Provincia;
import com.pericles.cooperativa.gestion.domain.Proyecto;
import com.pericles.cooperativa.gestion.domain.Socio;
import com.pericles.cooperativa.gestion.domain.UsuarioImpl;

@ContextConfiguration(classes = JpaConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class IncidenciaPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;

	static Logger logger = Logger.getLogger(IncidenciaPersistenceTests.class);

	@Test
	@Transactional
	public void testSaveIncidenciaAndFind() throws Exception {
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
		assertNotNull(idEntidad);
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
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

		// Se crea un usuario que introduce la incidencia
		UsuarioImpl usuario2 = new Socio(); // Usuario
		usuario2.setIdentificacion("B1234567Z");
		usuario2.setCorreoElectronico("daprenderjugando@hotmail.com");
		usuario2.setTelefono("34917411255");
		usuario2.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/de la Raza, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		usuario2.setDomicilio(domicilio);
		usuario2.setNombreUsuario("Carlos");
		usuario2.setApellidosUsuario("Torombolo López");
		usuario2.setFechaNacimiento(new LocalDate("1975-10-01"));
		usuario2.setCorreoElectronicoAsociacion("djalvarez@abierto.com");
		usuario2.setTelefonoAsociacion("917778899");
		usuario2.setTelefonoMovilAsociacion("666445566");
		usuario2.setActivo(true);
		((Socio) usuario2).setEliminado(false);

		entityManager.persist(usuario2);
		entityManager.flush();
		entityManager.clear();

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);
		idUsuario = usuario2.getId();
		assertNotNull(idUsuario);
		UsuarioImpl usuario2Find = (UsuarioImpl) entityManager.find(
				UsuarioImpl.class, idUsuario);

		// Se añade el usuario a los usuarios del proyecto
		proyecto.getUsuarios().add(usuario2Find);

		entityManager.persist(proyectoFind); // NOTA: Esto también funciona
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
		idUsuario = usuario2.getId();
		assertNotNull(idUsuario);
		usuario2Find = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);

		// Se crea la 1ª incidencia (QUE SERÁ LA 2ª)
		Incidencia incidencia = new Incidencia(proyectoFind, usuario2Find,
				"1ª incidencia del proyecto", new LocalDateTime("2014-04-01"));

		proyectoFind.getIncidencias().add(incidencia);

		entityManager.persist(proyectoFind);
		// entityManager.persist(incidencia); // Tb. funciona
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
		idUsuario = usuario2.getId();
		assertNotNull(idUsuario);
		usuario2Find = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);
		idIncidencia = incidencia.getId();
		assertNotNull(idIncidencia);
		Incidencia incidenciaFind = (Incidencia) entityManager.find(
				Incidencia.class, idIncidencia);

		// Se comprueba que se ha actualizado el proyecto con las incidencias
		idIncidencia = proyectoFind.getIncidencias().iterator().next().getId();
		idProyectoIncidencia = proyectoFind.getIncidencias().iterator().next()
				.getProyecto().getId();
		assertNotNull(idIncidencia);
		assertNotNull(idProyectoIncidencia);
		assertEquals(1, proyectoFind.getIncidencias().size());
		assertEquals(proyectoFind, proyectoFind.getIncidencias().iterator()
				.next().getProyecto());

		logger.info("Proyecto de la incidencia: "
				+ incidenciaFind.getProyecto());

		logger.info("-------------------------------------");
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
		logger.info("-------------------------------------");

		// Se crea un usuario que introduce la incidencia (en negocio se
		// comprobará que trabaja en el proyecto)
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

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);
		idUsuario = otroUsuario.getId();
		assertNotNull(idUsuario);
		UsuarioImpl usuario3Find = (UsuarioImpl) entityManager.find(
				UsuarioImpl.class, idUsuario);

		// Se crea la 2ª incidencia (QUE SERÁ LA 1ª)
		Incidencia otraIncidencia = new Incidencia(proyectoFind, usuario3Find,
				"2ª incidencia del proyecto", new LocalDateTime("2014-03-01"));

		proyectoFind.getIncidencias().add(otraIncidencia);

		entityManager.persist(proyectoFind);
		// entityManager.persist(otraIncidencia);
		entityManager.flush();
		// entityManager.refresh(proyectoFind);
		entityManager.clear();

		idIncidencia = otraIncidencia.getId();
		assertNotNull(idIncidencia);
		incidenciaFind = (Incidencia) entityManager.find(Incidencia.class,
				idIncidencia);
		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);

		logger.info("Proyecto de la incidencia: "
				+ incidenciaFind.getProyecto());

		logger.info("-------------------------------------");
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
		logger.info("-------------------------------------");

		// Se comprueba que se hayan ordenado las tareas
		logger.info("Incidencias ordenadas: ");
		for (Incidencia incidenciaEnProyecto : proyectoFind.getIncidencias()) {
			logger.info("Incidencia: " + incidenciaEnProyecto);
		}

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);

		// Si se elimina el proyecto la incidencia desaparece
		entityManager.remove(proyectoFind);
		entityManager.flush();
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);
		assertNull(proyectoFind);

		try {
			incidenciaFind = entityManager.find(Incidencia.class, idIncidencia);

			assertNull(incidenciaFind);
			logger.info("Incidencia es null");
		} catch (EntityNotFoundException ex) {
			logger.info("Mensaje de la excepción: " + ex.getMessage());
		}

	}

}
