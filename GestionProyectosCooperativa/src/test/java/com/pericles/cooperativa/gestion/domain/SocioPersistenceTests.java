package com.pericles.cooperativa.gestion.domain;

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
import com.pericles.cooperativa.gestion.domain.ComunidadAutonoma;
import com.pericles.cooperativa.gestion.domain.Domicilio;
import com.pericles.cooperativa.gestion.domain.Entidad;
import com.pericles.cooperativa.gestion.domain.EstadoProyecto;
import com.pericles.cooperativa.gestion.domain.Provincia;
import com.pericles.cooperativa.gestion.domain.Proyecto;
import com.pericles.cooperativa.gestion.domain.Socio;
import com.pericles.cooperativa.gestion.domain.UsuarioImpl;

@ContextConfiguration(classes = JpaConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SocioPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;

	static Logger logger = Logger.getLogger(SocioPersistenceTests.class);

	@Test
	@Transactional
	public void testSaveSocioAndFind() throws Exception {
		long idCoordinador;
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
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

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

		idUsuario = usuario.getId();
		assertNotNull(idUsuario);
		usuarioFind = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);
		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);

		// Se crea otro proyecto
		Proyecto otroProyectoMas = new Proyecto();
		otroProyectoMas.setNombre("Proyecto 02");
		otroProyectoMas.setPresupuesto(new BigDecimal("2500.50"));
		otroProyectoMas.setDescripcion("Este es el segundo proyecto.");
		otroProyectoMas.setFechaInicio(new LocalDate("2014-01-01"));
		otroProyectoMas.setFechaFin(new LocalDate("2014-04-01"));
		otroProyectoMas.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		otroProyectoMas.getCoordinadores().add((Socio) usuarioFind);
		otroProyectoMas.setBalance("N/A");
		otroProyectoMas.setEntidadCliente(entidadFind);

		entityManager.persist(otroProyectoMas);
		entityManager.flush();

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);

		// Se comprueba que se ha actualizado el proyecto con los coordinadores
		idCoordinador = proyectoFind.getCoordinadores().iterator().next()
				.getId();
		assertNotNull(idCoordinador);

		Socio coordinador = (Socio) entityManager.find(Socio.class,
				idCoordinador);

		logger.info("Socio creado por: " + coordinador.getCreatedBy());
		logger.info("Socio creado en: " + coordinador.getCreatedDate());
		logger.info("Socio modificado por: " + coordinador.getLastModifiedBy());
		logger.info("Socio modificado en: " + coordinador.getLastModifiedDate());

		logger.info("Socio coordina en proyectos: ");
		for (Proyecto p : coordinador.getProyectosCoordinados()) {
			logger.info(p.getNombre());
		}

		idProyecto = otroProyectoMas.getId();
		assertNotNull(idProyecto);
		proyectoFind = (Proyecto) entityManager
				.find(Proyecto.class, idProyecto);

		// Se elimina un proyecto para comprobar en Socio
		entityManager.remove(proyectoFind);
		entityManager.flush();

		entityManager.refresh(coordinador);

		logger.info("Socio coordina en proyectos: ");
		for (Proyecto p : coordinador.getProyectosCoordinados()) {
			logger.info(p.getNombre());
		}

	}

}
