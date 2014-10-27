package com.pericles.cooperativa.gestion.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import com.pericles.cooperativa.gestion.domain.Colaborador;
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
public class EntidadPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;

	static Logger logger = Logger.getLogger(EntidadPersistenceTests.class);

	@Test
	@Transactional
	public void testSaveAndFindEntidad() throws Exception {
		long idEntidad;

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

		logger.info("Valores de la entidad (instancia):");
		logger.info("Identificación: " + entidad.getIdentificacion());
		logger.info("Nombre" + entidad.getNombreEntidad());
		logger.info("Página Web: " + entidad.getPaginaWeb());
		logger.info("Correo Electrónico: " + entidad.getCorreoElectronico());
		logger.info("Teléfono: " + entidad.getTelefono());
		logger.info("Teléfono Móvil: " + entidad.getTelefonoMovil());
		logger.info("Dirección: " + entidad.getDomicilio().getDireccion());
		logger.info("Población: " + entidad.getDomicilio().getPoblacion());
		logger.info("Provincia: " + entidad.getDomicilio().getProvincia());
		logger.info("Comunidad Autónoma: "
				+ entidad.getDomicilio().getComunidadAutonoma());
		logger.info("Código Postal: "
				+ entidad.getDomicilio().getCodigoPostal());
		logger.info("País: " + entidad.getDomicilio().getPais());
		logger.info("Nombre Persona Contacto: "
				+ entidad.getNombrePersonaContacto());
		logger.info("Correo Electrónico Persona Contacto: "
				+ entidad.getNombrePersonaContacto());
		logger.info("Teléfono Persona Contacto: "
				+ entidad.getTelefonoPersonaContacto());
		logger.info("Teléfono Móvil Persona Contacto: "
				+ entidad.getTelefonoMovilPersonaContacto());
		logger.info("Entidad creada por: " + entidad.getCreatedBy());
		logger.info("Entidad creada en: " + entidad.getCreatedDate());
		logger.info("Entidad modificada por: " + entidad.getLastModifiedBy());
		logger.info("Entidad modificada en: " + entidad.getLastModifiedDate());
		logger.info("ToString-----------------------------");
		logger.info(entidad.toString());
		entityManager.persist(entidad);
		entityManager.flush();
		entityManager.clear();

		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		logger.info("Valores de la entidad (bd):");
		logger.info("Identificación: " + entidadFind.getIdentificacion());
		logger.info("Nombre" + entidadFind.getNombreEntidad());
		logger.info("Nombre" + entidadFind.getCorreoElectronico());
		logger.info("Teléfono: " + entidadFind.getTelefono());
		logger.info("Teléfono Móvil: " + entidadFind.getTelefonoMovil());
		logger.info("Dirección: " + entidadFind.getDomicilio().getDireccion());
		logger.info("Población: " + entidadFind.getDomicilio().getPoblacion());
		logger.info("Provincia: " + entidadFind.getDomicilio().getProvincia());
		logger.info("Comunidad Autónoma: "
				+ entidadFind.getDomicilio().getComunidadAutonoma());
		logger.info("Código Postal: "
				+ entidadFind.getDomicilio().getCodigoPostal());
		logger.info("País: " + entidadFind.getDomicilio().getPais());
		logger.info("Correo Electrónico Persona Contacto: "
				+ entidadFind.getNombrePersonaContacto());
		logger.info("Teléfono Persona Contacto: "
				+ entidadFind.getTelefonoPersonaContacto());
		logger.info("Teléfono Móvil Persona Contacto: "
				+ entidadFind.getTelefonoMovilPersonaContacto());
		logger.info("Entidad creada por: " + entidadFind.getCreatedBy());
		logger.info("Entidad creada en: " + entidadFind.getCreatedDate());
		logger.info("Entidad modificada por: " + entidadFind.getLastModifiedBy());
		logger.info("Entidad modificada en: " + entidadFind.getLastModifiedDate());

		// exception.expect(IllegalArgumentException.class);
	}

	@Test
	@Transactional
	public void testSaveAndFindWithProyectos() throws Exception {
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

		// Se crea un usuario (socio) que coordinará el proyecto
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

		// Se crea un proyecto para poder establecer en él la entidad cliente
		// (QUE SERÁ 2º EN LA LISTA)
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

		Proyecto otroProyecto = (Proyecto) entityManager
				.createQuery(
						"select p from Proyecto p join p.entidadCliente e where e.nombreEntidad=:nombre")
				.setParameter("nombre", "Aprender Jugando").getSingleResult();
		assertEquals(entidadFind, otroProyecto.getEntidadCliente());

		entityManager.refresh(entidadFind);
		assertNotNull(entidadFind.getProyectosContratados());

		logger.info("-------------------------------------");
		logger.info("Proyectos contratados: "
				+ entidadFind.getProyectosContratados().iterator().next()
						.getNombre());
		logger.info("-------------------------------------");

		// Se crea otro proyecto para poder establecer en él la entidad cliente
		// (QUE SERÁ 1º EN LA LISTA)
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
		entityManager.refresh(entidadFind);

		// Se comprueba que se hayan ordenado los proyectos contratados
		for (Proyecto proyectoEnEntidad : entidadFind.getProyectosContratados()) {
			logger.info("Proyecto contratado: " + proyectoEnEntidad);
		}

		// Se elimina la entidad para ver qué pasa
		if (entidadFind.getProyectosContratados().isEmpty()
				&& entidadFind.getProyectosColaborados().isEmpty()) { // Borrado
			// físico
			logger.info("Borrado físico de entidad");
			entityManager.remove(entidadFind);
			entityManager.flush();

			entidadFind = entityManager.find(Entidad.class, idEntidad);

			assertNull(entidadFind);
		} else { // Borrado lógico
			logger.info("Borrado lógico de entidad");
			entidad.setEliminado(true);
		}

		// Se borra el proyecto
		entityManager.remove(otroProyectoMas);
		entityManager.flush();
		entityManager.clear();

		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);

		// Se comprueba que se hayan ordenado los proyectos contratados
		for (Proyecto proyectoEnEntidad : entidadFind.getProyectosContratados()) {
			logger.info("Proyecto contratado: " + proyectoEnEntidad);
		}

	}

	@Test
	@Transactional
	public void testSaveAndFindWithProyectosColabora() throws Exception {
		long idEntidad;
		long idProyecto;
		long idUsuario;

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
		assertNotNull(idEntidad);
		Entidad entidadFind = (Entidad) entityManager.find(Entidad.class,
				idEntidad);

		// Se crea un colaborador de la entidad
		Colaborador colaborador = new Colaborador(); // Usuario
		colaborador.setIdentificacion("M1234567Z");
		colaborador.setCorreoElectronico("daprenderjugando@hotmail.com");
		colaborador.setTelefono("34917411255");
		colaborador.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/de la Raza, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		colaborador.setDomicilio(domicilio);
		colaborador.setNombreUsuario("Jacinto");
		colaborador.setApellidosUsuario("Alvarez del Vas");
		colaborador.setFechaNacimiento(new LocalDate("1970-09-14"));
		colaborador.setCorreoElectronicoAsociacion("djalvarez@abierto.com");
		colaborador.setTelefonoAsociacion("917778899");
		colaborador.setTelefonoMovilAsociacion("666445566");
		colaborador.setEntidad(entidadFind);
		colaborador.setActivo(true);
		colaborador.setEliminado(false);

		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);

		// Añade el colaborador a la entidad
		entidadFind.getColaboradores().add(colaborador);

		entityManager.persist(entidadFind);
		entityManager.flush();
		entityManager.clear();

		// Se crea una Entidad para poder establecerla en el proyecto como
		// entidad colaboradora
		Entidad otraEntidad = new Entidad();
		otraEntidad.setIdentificacion("A7654321S");
		otraEntidad.setNombreEntidad("Jugando se Aprende");
		otraEntidad.setCorreoElectronico("aprenderjugando@hotmail.com");
		otraEntidad.setTelefono("34917411255");
		otraEntidad.setTelefonoMovil("34666147799");
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

		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);
		idEntidad = otraEntidad.getId();
		assertNotNull(idEntidad);
		Entidad otraEntidadFind = entityManager.find(Entidad.class, idEntidad);
		idUsuario = usuario.getId();
		assertNotNull(idUsuario);
		UsuarioImpl usuarioFind = entityManager.find(UsuarioImpl.class,
				idUsuario);

		// Se crea un proyecto para poder establecerlo en la tarea
		Proyecto proyecto = new Proyecto();
		proyecto.setNombre("Proyecto 01");
		proyecto.setPresupuesto(new BigDecimal("2500.5"));
		proyecto.setDescripcion("Este es el primer proyecto.");
		proyecto.setFechaInicio(new LocalDate("2014-01-01"));
		proyecto.setFechaFin(new LocalDate("2014-05-01"));
		proyecto.setEstadoProyecto(EstadoProyecto.PENDIENTE);
		proyecto.setBalance("N/A");
		proyecto.getCoordinadores().add((Socio) usuarioFind);
		proyecto.setEntidadCliente(entidadFind);
		proyecto.getEntidadesColaboradoras().add(entidadFind);
		proyecto.getEntidadesColaboradoras().add(otraEntidadFind);

		entityManager.persist(proyecto);
		entityManager.flush();
		entityManager.clear();

		Proyecto otroProyecto = (Proyecto) entityManager
				.createQuery(
						"select p from Proyecto p join p.entidadesColaboradoras e where e.nombreEntidad=:nombre1 or e.nombreEntidad=:nombre2")
				.setParameter("nombre1", "Aprender Jugando")
				.setParameter("nombre2", "Jugando se Aprende")
				.getSingleResult();
		assertEquals(2, otroProyecto.getEntidadesColaboradoras().size());

		logger.info("Entidades Colaboradoras del proyecto: ");

		for (Entidad e : otroProyecto.getEntidadesColaboradoras()) {
			logger.info(e.getNombreEntidad());
		}

		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);
		idEntidad = otraEntidad.getId();
		assertNotNull(idEntidad);
		otraEntidadFind = entityManager.find(Entidad.class, idEntidad);
		idUsuario = usuario.getId();
		assertNotNull(idUsuario);
		usuarioFind = (UsuarioImpl) entityManager.find(UsuarioImpl.class,
				idUsuario);

		assertEquals(1, entidadFind.getProyectosColaborados().size());
		assertEquals(1, otraEntidadFind.getProyectosColaborados().size());

		logger.info("Entidad A colabora en proyecto: "
				+ entidadFind.getProyectosColaborados().iterator().next()
						.getNombre());
		logger.info("Entidad B colabora en proyecto: "
				+ otraEntidadFind.getProyectosColaborados().iterator().next()
						.getNombre());

		// Se crea una Entidad para poder establecerla en el proyecto como
		// entidad colaboradora (QUE SERÁ 1º EN LA LISTA)
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
		otroProyectoMas.getEntidadesColaboradoras().add(entidadFind);

		entityManager.persist(otroProyectoMas);
		entityManager.flush();
		entityManager.clear();

		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);

		// Se comprueba que se hayan ordenado los proyectos colaborados
		for (Proyecto proyectoEnEntidad : entidadFind.getProyectosColaborados()) {
			logger.info("Proyecto: " + proyectoEnEntidad);
		}

		// Se elimina la entidad para ver qué pasa
		if (entidadFind.getProyectosContratados().isEmpty()
				&& entidad.getProyectosColaborados().isEmpty()) { // Borrado
			// físico
			logger.info("Borrado físico de entidad");
			entityManager.remove(entidadFind);
			entityManager.flush();

			entidadFind = entityManager.find(Entidad.class, idEntidad);

			assertNull(entidadFind);
		} else { // Borrado lógico
			logger.info("Borrado lógico de entidad");
			entidadFind.setEliminado(true);
		}

		idProyecto = proyecto.getId();
		assertNotNull(idProyecto);
		Proyecto proyectoFind = (Proyecto) entityManager.find(Proyecto.class,
				idProyecto);

		// Prueba de borrado de proyecto
		entityManager.remove(proyectoFind);
		entityManager.flush();
		entityManager.clear();

		idProyecto = proyecto.getId();
		proyectoFind = entityManager.find(Proyecto.class, idProyecto);
		assertNull(proyectoFind);

		idEntidad = entidad.getId();
		assertNotNull(idEntidad);
		entidadFind = (Entidad) entityManager.find(Entidad.class, idEntidad);

		// Se comprueba que sólo se colabore en un proyecto (el otro borrado)
		for (Proyecto proyectoEnEntidad : entidadFind.getProyectosColaborados()) {
			logger.info("Proyecto: " + proyectoEnEntidad);
		}

	}
}
