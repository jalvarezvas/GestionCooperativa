package com.pericles.cooperativa.gestion.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

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
import com.pericles.cooperativa.gestion.domain.ComunidadAutonoma;
import com.pericles.cooperativa.gestion.domain.Contrato;
import com.pericles.cooperativa.gestion.domain.Domicilio;
import com.pericles.cooperativa.gestion.domain.Entidad;
import com.pericles.cooperativa.gestion.domain.EstadoProyecto;
import com.pericles.cooperativa.gestion.domain.Provincia;
import com.pericles.cooperativa.gestion.domain.Proyecto;
import com.pericles.cooperativa.gestion.domain.Socio;
import com.pericles.cooperativa.gestion.domain.TipoContrato;
import com.pericles.cooperativa.gestion.domain.Trabajador;
import com.pericles.cooperativa.gestion.domain.UsuarioImpl;

@ContextConfiguration(classes = JpaConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TrabajadorPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;

	static Logger logger = Logger.getLogger(TrabajadorPersistenceTests.class);

	@Test
	@Transactional
	public void testSaveTrabajadorAndFind() throws Exception {
		long idEntidad;
		long idProyecto;
		long idUsuario;
		long idTrabajador;
		long idContrato;

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

		// Se crea un trabajador realizará tareas
		UsuarioImpl usuarioT = new Trabajador(); // Usuario
		usuarioT.setIdentificacion("A1234567Z");
		usuarioT.setCorreoElectronico("aprenderjugando@hotmail.com");
		usuarioT.setTelefono("34917411255");
		usuarioT.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/de la Raza, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		usuarioT.setDomicilio(domicilio);
		usuarioT.setNombreUsuario("Benito");
		usuarioT.setApellidosUsuario("Camelas Mucho");
		usuarioT.setFechaNacimiento(new LocalDate("1970-09-14"));
		usuarioT.setCorreoElectronicoAsociacion("jalvarez@abierto.com");
		usuarioT.setTelefonoAsociacion("917778899");
		usuarioT.setTelefonoMovilAsociacion("666445566");
		usuarioT.setActivo(true);
		((Trabajador) usuarioT).setEliminado(false);

		// Se crea un usuario que será el coordinador y realizará tareas
		UsuarioImpl usuario = new Socio(); // Usuario
		usuario.setIdentificacion("B1234567Z");
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
		usuario.setCorreoElectronicoAsociacion("djalvarez@abierto.com");
		usuario.setTelefonoAsociacion("917778899");
		usuario.setTelefonoMovilAsociacion("666445566");
		usuario.setActivo(true);
		((Socio) usuario).setEliminado(false);

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
		proyecto.getCoordinadores().add((Socio) usuario);
		proyecto.setBalance("N/A");
		proyecto.setEntidadCliente(entidadFind);
		proyecto.getUsuarios().add(usuarioT);
		proyecto.getUsuarios().add(usuario);

		entityManager.persist(proyecto);
		entityManager.flush();
		entityManager.clear();

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);
		idUsuario = usuarioT.getId();
		assertNotNull(idUsuario);
		UsuarioImpl usuarioFind = (UsuarioImpl) entityManager.find(
				UsuarioImpl.class, idUsuario);

		// Se comprueba que se ha actualizado el proyecto con los trabajadores
		assertNotNull(proyectoFind.getUsuarios());

		logger.info("Trabajadores que participa en proyecto: ");
		for (UsuarioImpl u : proyectoFind.getUsuarios()) {
			logger.info(u.getApellidosUsuario() + ", " + u.getNombreUsuario());
		}

		logger.info("Trabajador creado por: " + usuarioFind.getCreatedBy());
		logger.info("Trabajador creado en: " + usuarioFind.getCreatedDate());
		logger.info("Trabajador modificado por: "
				+ usuarioFind.getLastModifiedBy());
		logger.info("Trabajador modificado en: "
				+ usuarioFind.getLastModifiedDate());

		logger.info("Trabajador participa en proyectos: ");
		for (Proyecto p : usuarioFind.getProyectosParticipados()) {
			logger.info(p.getNombre());
		}

		entityManager.remove(proyectoFind);
		entityManager.flush();
		entityManager.clear();

		// Se comprueba que se ha actualizado el proyecto con los trabajadores
		idUsuario = usuarioT.getId();
		assertNotNull(idUsuario);
		usuarioFind = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);

		logger.info("Trabajador participa en proyectos (NINGUNO): ");
		logger.info("Nº de proyectos: "
				+ usuarioFind.getProyectosParticipados().size());
		for (Proyecto p : usuarioFind.getProyectosParticipados()) {
			logger.info(p.getNombre());
		}

		// Esta información aparece igual que la anterior
		logger.info("Trabajador creado por: " + usuarioFind.getCreatedBy());
		logger.info("Trabajador creado en: " + usuarioFind.getCreatedDate());
		logger.info("Trabajador modificado por: "
				+ usuarioFind.getLastModifiedBy());
		logger.info("Trabajador modificado en: "
				+ usuarioFind.getLastModifiedDate());

		// Se crea un contrato
		Contrato contrato = new Contrato();
		contrato.setTipoContrato(TipoContrato.I);
		contrato.setTrabajador((Trabajador) usuarioFind);
		contrato.setFechaInicio(new LocalDate("2014-01-01"));

		// Se añade la información de contrato al trabajador (y otra)
		((Trabajador) usuarioFind).setProfesion("Limpiabotas");
		((Trabajador) usuarioFind).setCategoriaProfesional("Jefe de proyecto");
		((Trabajador) usuarioFind).setContrato(contrato);

		entityManager.persist(usuarioFind);
		entityManager.flush();
		entityManager.clear();

		idTrabajador = usuarioT.getId();
		assertNotNull(idTrabajador);
		Trabajador trabajadorFind = (Trabajador) entityManager.find(
				Trabajador.class, idTrabajador);
		assertEquals(trabajadorFind, usuarioT);

		logger.info("Valores del trabajador (bd):");
		logger.info("Identificación: " + trabajadorFind.getIdentificacion());
		logger.info("Correo Electrónico: "
				+ trabajadorFind.getCorreoElectronico());
		logger.info("Teléfono: " + trabajadorFind.getTelefono());
		logger.info("Teléfono Móvil: " + trabajadorFind.getTelefonoMovil());
		logger.info("Dirección: "
				+ trabajadorFind.getDomicilio().getDireccion());
		logger.info("Población: "
				+ trabajadorFind.getDomicilio().getPoblacion());
		logger.info("Provincia: "
				+ trabajadorFind.getDomicilio().getProvincia());
		logger.info("Comunidad Autónoma: "
				+ trabajadorFind.getDomicilio().getComunidadAutonoma());
		logger.info("Código Postal: "
				+ trabajadorFind.getDomicilio().getCodigoPostal());
		logger.info("País: " + trabajadorFind.getDomicilio().getPais());
		logger.info("Nombre: " + trabajadorFind.getNombreUsuario());
		logger.info("Apellidos: " + trabajadorFind.getApellidosUsuario());
		logger.info("Fecha Nacimiento: "
				+ DateTimeFormat.mediumDate().print(
						trabajadorFind.getFechaNacimiento()));
		logger.info("Correo Electrónico Asociación: "
				+ trabajadorFind.getCorreoElectronicoAsociacion());
		logger.info("Teléfono Asociación: "
				+ trabajadorFind.getTelefonoAsociacion());
		logger.info("Teléfono Móvil Asociación: "
				+ trabajadorFind.getTelefonoMovilAsociacion());
		logger.info("Profesión: " + trabajadorFind.getProfesion());
		logger.info("Categoría profesional: "
				+ trabajadorFind.getCategoriaProfesional());
		logger.info("Tipo de contrato: "
				+ trabajadorFind.getContrato().getTipoContrato()
						.getNombreContrato());

		logger.info("Trabajador creado por: " + trabajadorFind.getCreatedBy());
		logger.info("Trabajador creado en: "
				+ DateTimeFormat.mediumDateTime().print(
						trabajadorFind.getCreatedDate()));
		logger.info("Trabajador modificado por: "
				+ trabajadorFind.getLastModifiedBy());
		logger.info("Trabajador modificado en: "
				+ DateTimeFormat.mediumDateTime().print(
						trabajadorFind.getLastModifiedDate()));

		idContrato = contrato.getId();
		assertNotNull(idContrato);
		Contrato contratoFind = (Contrato) entityManager.find(Contrato.class,
				idContrato);

		assertEquals(contratoFind, trabajadorFind.getContrato());

		logger.info("Tipo de contrato: "
				+ contratoFind.getTipoContrato().getNombreContrato());
		logger.info("Trabajador: "
				+ contratoFind.getTrabajador().getApellidosUsuario());
		logger.info("Fecha Inicio del contrato: "
				+ DateTimeFormat.mediumDate().print(
						contratoFind.getFechaInicio()));

		assertNull(contratoFind.getFechaFin());

		trabajadorFind = (Trabajador) entityManager
				.createQuery(
						"Select t From Trabajador t join t.contrato c where c.tipoContrato=:tipoContrato")
				.setParameter("tipoContrato", TipoContrato.I).getSingleResult();
		assertEquals(TipoContrato.I, trabajadorFind.getContrato()
				.getTipoContrato());

		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);

		// TODO: Borrar lo de abajo (Sólo para demostrar que admite la misma
		// identificación en Entidad, Trabajador, Socio y Colaborador

		// Se crea trabajador
		Trabajador trabajador2 = new Trabajador(); // Usuario
		trabajador2.setIdentificacion("T1234567Z");
		trabajador2.setCorreoElectronico("taprenderjugando@hotmail.com");
		trabajador2.setTelefono("349174112551");
		trabajador2.setTelefonoMovil("346661477991");
		// Domicilio
		domicilio = new Domicilio("C/de la Raza, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		trabajador2.setDomicilio(domicilio);
		trabajador2.setNombreUsuario("Jacinto1");
		trabajador2.setApellidosUsuario("Alvarez1 del Vas");
		trabajador2.setFechaNacimiento(new LocalDate("1970-09-14"));
		trabajador2.setCorreoElectronicoAsociacion("t1djalvarez@abierto.com");
		trabajador2.setTelefonoAsociacion("9177788991");
		trabajador2.setTelefonoMovilAsociacion("6664455661");
		trabajador2.setActivo(true);
		// colaborador.setEntidad(entidadFind);
		trabajador2.setEliminado(false);

		entityManager.persist(trabajador2);
		entityManager.flush();
		entityManager.clear();

		Trabajador trabajador3 = new Trabajador(); // Usuario
		trabajador3.setIdentificacion("T1111111Z");
		trabajador3.setCorreoElectronico("taprenderjugando@hotmail.com");
		trabajador3.setTelefono("349174112551");
		trabajador3.setTelefonoMovil("346661477991");
		// Domicilio
		domicilio = new Domicilio("C/de la Raza, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		trabajador3.setDomicilio(domicilio);
		trabajador3.setNombreUsuario("Jacinto1");
		trabajador3.setApellidosUsuario("Alvarez1 del Vas");
		trabajador3.setFechaNacimiento(new LocalDate("1970-09-14"));
		trabajador3.setCorreoElectronicoAsociacion("t2djalvarez@abierto.com");
		trabajador3.setTelefonoAsociacion("9177788991");
		trabajador3.setTelefonoMovilAsociacion("6664455661");
		trabajador3.setActivo(true);
		// colaborador.setEntidad(entidadFind);
		trabajador3.setEliminado(false);

		entityManager.persist(trabajador3);
		entityManager.flush();
		entityManager.clear();

		// Se crea socio
		Socio socio = new Socio(); // Usuario
		socio.setIdentificacion("T1234567Z");
		socio.setCorreoElectronico("saprenderjugando@hotmail.com");
		socio.setTelefono("34917411255");
		socio.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/de la Raza, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		socio.setDomicilio(domicilio);
		socio.setNombreUsuario("Jacinto");
		socio.setApellidosUsuario("Alvarez del Vas");
		socio.setFechaNacimiento(new LocalDate("1970-09-14"));
		socio.setCorreoElectronicoAsociacion("sjalvarez@abierto.com");
		socio.setTelefonoAsociacion("917778899");
		socio.setTelefonoMovilAsociacion("6664455661");
		socio.setActivo(true);
		socio.setEliminado(false);

		entityManager.persist(socio);
		entityManager.flush();
		entityManager.clear();

		// Se crea Entidad
		entidad = new Entidad();
		entidad.setIdentificacion("T1234567Z");
		entidad.setNombreEntidad("papatula");
		entidad.setEliminado(false);

		entityManager.persist(entidad);
		entityManager.flush();
		entityManager.clear();

		idTrabajador = trabajador2.getId();
		assertNotNull(idTrabajador);
		trabajadorFind = (Trabajador) entityManager.find(Trabajador.class,
				idTrabajador);

		logger.info("1º trabajador: " + trabajadorFind);

		idTrabajador = trabajador3.getId();
		assertNotNull(idTrabajador);
		trabajadorFind = (Trabajador) entityManager.find(Trabajador.class,
				idTrabajador);

		logger.info("2º trabajador: " + trabajadorFind);

		idUsuario = socio.getId();
		assertNotNull(idUsuario);
		usuarioFind = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);

		logger.info("Socio: " + usuarioFind);

		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);

		logger.info("Entidad: " + entidadFind);

	}
}
