package com.pericles.cooperativa.gestion.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

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
import com.pericles.cooperativa.gestion.domain.Provincia;
import com.pericles.cooperativa.gestion.domain.TipoContrato;
import com.pericles.cooperativa.gestion.domain.Trabajador;

@ContextConfiguration(classes = JpaConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ContratoPersistenceTests {

	@PersistenceContext
	private EntityManager entityManager;

	static Logger logger = Logger.getLogger(ContratoPersistenceTests.class);

	@Test
	@Transactional
	public void testSaveContratoAndFind() throws Exception {

		// Para comprobar el modelo
		Metamodel mm = entityManager.getMetamodel();
		EntityType<Contrato> cont_ = mm.entity(Contrato.class);
		for (Attribute<? super Contrato, ?> attr : cont_.getAttributes()) {
			System.out.println(attr.getName() + " "
					+ attr.getJavaType().getName() + " "
					+ attr.getPersistentAttributeType());
		}

		// Para comprobar los ids
		// PersistenceUnitUtil util = entityManager.getEntityManagerFactory()
		// .getPersistenceUnitUtil();
		//
		// List<Object> result = new ArrayList<Object>();
		// for (T entity : entities) {
		// result.add(util.getIdentifier(entity));
		// }

		// Se crea un trabajador
		Trabajador trabajador1 = new Trabajador(); // Usuario
		trabajador1.setIdentificacion("B1234567Z");
		trabajador1.setCorreoElectronico("daprenderjugando@hotmail.com");
		trabajador1.setTelefono("34917411255");
		trabajador1.setTelefonoMovil("34666147799");
		// Domicilio
		Domicilio domicilio = new Domicilio("C/de la Taza, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		trabajador1.setDomicilio(domicilio);
		trabajador1.setNombreUsuario("Laborator");
		trabajador1.setApellidosUsuario("Alvarez del Vas");
		trabajador1.setFechaNacimiento(new LocalDate("1970-09-14"));
		trabajador1.setCorreoElectronicoAsociacion("djalvarez@abierto.com");
		trabajador1.setTelefonoAsociacion("918888899");
		trabajador1.setTelefonoMovilAsociacion("609445566");
		trabajador1.setActivo(true);
		trabajador1.setEliminado(false);
		trabajador1.setProfesion("Limpiabotas");
		trabajador1.setCategoriaProfesional("Jefe de proyecto");

		// Se crea un contrato a partir del trabajador (como lo hará la
		// aplicación)
		Contrato contrato1 = new Contrato();
		contrato1.setTipoContrato(TipoContrato.I);
		contrato1.setTrabajador(trabajador1);
		contrato1.setFechaInicio(new LocalDate("2014-01-01"));
		trabajador1.setContrato(contrato1);

		entityManager.persist(trabajador1);
		entityManager.flush();
		entityManager.clear();

		Trabajador trabajador1Find = (Trabajador) entityManager.find(
				Trabajador.class, trabajador1.getId());
		assertNotNull(trabajador1Find);

		// Valores del contrato
		Contrato contrato1Find = (Contrato) entityManager.find(Contrato.class,
				trabajador1Find.getContrato().getId());

		assertEquals(contrato1Find, trabajador1Find.getContrato());

		logger.info("ID del contrato: " + contrato1Find.getId());
		logger.info("ID del trabajador del contrato: "
				+ contrato1Find.getTrabajador().getId());
		logger.info("Tipo de contrato: "
				+ contrato1Find.getTipoContrato().getNombreContrato());
		logger.info("Trabajador: "
				+ contrato1Find.getTrabajador().getApellidosUsuario());
		logger.info("Fecha Inicio del contrato: "
				+ DateTimeFormat.mediumDate().print(
						contrato1Find.getFechaInicio()));
		logger.info("Contrato creado por: " + contrato1Find.getCreatedBy());
		logger.info("Contrato creado en: " + contrato1Find.getCreatedDate());
		logger.info("Contrato modificado por: "
				+ contrato1Find.getLastModifiedBy());
		logger.info("Contrato modificado en: "
				+ contrato1Find.getLastModifiedDate());

		assertNull(contrato1Find.getFechaFin());

		// Se crea otro trabajador
		Trabajador trabajador2 = new Trabajador(); // Usuario
		trabajador2.setIdentificacion("A1234567Z");
		trabajador2.setCorreoElectronico("aprenderjugando@hotmail.com");
		trabajador2.setTelefono("34917411255");
		trabajador2.setTelefonoMovil("34666147799");
		// Domicilio
		domicilio = new Domicilio("C/de la Raza, 21 3º A", "Madrid",
				Provincia.MADRID, ComunidadAutonoma.MADRID, "28022", "España");
		trabajador2.setDomicilio(domicilio);
		trabajador2.setNombreUsuario("Pepito");
		trabajador2.setApellidosUsuario("Torombolo Vasco");
		trabajador2.setFechaNacimiento(new LocalDate("1975-10-05"));
		trabajador2.setCorreoElectronicoAsociacion("jalvarez@abierto.com");
		trabajador2.setTelefonoAsociacion("917778899");
		trabajador2.setTelefonoMovilAsociacion("666445566");
		trabajador2.setActivo(true);
		trabajador2.setEliminado(false);
		trabajador2.setProfesion("Limpiabotas");
		trabajador2.setCategoriaProfesional("Jefe de proyecto");

		entityManager.persist(trabajador2);
		entityManager.flush();
		entityManager.clear();

		Trabajador trabajador2Find = (Trabajador) entityManager.find(
				Trabajador.class, trabajador2.getId());
		assertNotNull(trabajador2Find);

		// Se crea un contrato (sin intervención del trabajador -- así no lo
		// hará la aplicación)
		Contrato contrato2 = new Contrato(TipoContrato.T, trabajador2Find,
				new LocalDate("2014-01-01"), new LocalDate("2014-05-01"));

		entityManager.persist(contrato2);
		entityManager.flush();
		entityManager.clear();

		trabajador2Find = (Trabajador) entityManager.find(Trabajador.class,
				trabajador2.getId());
		assertNotNull(trabajador2Find);

		Contrato contrato2Find = (Contrato) entityManager.find(Contrato.class,
				contrato2.getId());
		assertNotNull(contrato2Find);

		trabajador2Find.setContrato(contrato2Find);

		entityManager.persist(trabajador2Find);
		entityManager.flush();
		entityManager.clear();

		trabajador2Find = (Trabajador) entityManager.find(Trabajador.class,
				trabajador2.getId());
		assertNotNull(trabajador2Find);

		// Valores del contrato
		contrato2Find = (Contrato) entityManager.find(Contrato.class,
				trabajador2Find.getContrato().getId());

		assertEquals(contrato2Find, trabajador2Find.getContrato());

		logger.info("ID del contrato: " + contrato2Find.getId());
		logger.info("ID del trabajador del contrato: "
				+ contrato2Find.getTrabajador().getId());
		logger.info("Tipo de contrato: "
				+ contrato2Find.getTipoContrato().getNombreContrato());
		logger.info("Trabajador: "
				+ contrato2Find.getTrabajador().getApellidosUsuario());
		logger.info("Fecha Inicio del contrato: "
				+ DateTimeFormat.mediumDate().print(
						contrato2Find.getFechaInicio()));
		logger.info("Fecha fin del contrato: "
				+ DateTimeFormat.mediumDate()
						.print(contrato2Find.getFechaFin()));
		logger.info("Contrato creado por: " + contrato2Find.getCreatedBy());
		logger.info("Contrato creado en: " + contrato2Find.getCreatedDate());
		logger.info("Contrato modificado por: "
				+ contrato2Find.getLastModifiedBy());
		logger.info("Contrato modificado en: "
				+ contrato2Find.getLastModifiedDate());

		assertNotNull(contrato2Find.getFechaFin());

		List<Contrato> contratos = (List<Contrato>) entityManager.createQuery(
				"select c from Contrato c", Contrato.class).getResultList();

		assertEquals(2, contratos.size());

		for (Contrato contrato : contratos) {
			logger.info("Tipo de contrato: "
					+ contrato.getTipoContrato().getNombreContrato());
			logger.info("Trabajador: "
					+ contrato.getTrabajador().getApellidosUsuario());
			logger.info("Fecha Inicio del contrato: "
					+ DateTimeFormat.mediumDate().print(
							contrato.getFechaInicio()));
			if (contrato.getFechaFin() != null) {
				logger.info("Fecha fin del contrato: "
						+ DateTimeFormat.mediumDate().print(
								contrato.getFechaFin()));
			}
			logger.info("Versión del contrato: " + contrato.getVersion());
			logger.info("Contrato creado por: " + contrato.getCreatedBy());
			logger.info("Contrato creado en: " + contrato.getCreatedDate());
			logger.info("Contrato modificado por: "
					+ contrato.getLastModifiedBy());
			logger.info("Contrato modificado en: "
					+ contrato.getLastModifiedDate());
			logger.info("----------------------");
		}

		// Se borra el trabajador para ver si se borra el contrato
		entityManager.remove(trabajador2Find);
		Contrato contratoBorrado = (Contrato) entityManager.find(
				Contrato.class, contrato2Find.getId());

		assertNull(contratoBorrado);

		contratos = (List<Contrato>) entityManager.createQuery(
				"select c from Contrato c", Contrato.class).getResultList();

		assertEquals(1, contratos.size());

		for (Contrato contrato : contratos) {
			logger.info("Tipo de contrato: "
					+ contrato.getTipoContrato().getNombreContrato());
			logger.info("Trabajador: "
					+ contrato.getTrabajador().getApellidosUsuario());
			logger.info("Fecha Inicio del contrato: "
					+ DateTimeFormat.mediumDate().print(
							contrato.getFechaInicio()));
			if (contrato.getFechaFin() != null) {
				logger.info("Fecha fin del contrato: "
						+ DateTimeFormat.mediumDate().print(
								contrato.getFechaFin()));
			}
			logger.info("Versión del contrato: " + contrato.getVersion());
			logger.info("Contrato creado por: " + contrato.getCreatedBy());
			logger.info("Contrato creado en: " + contrato.getCreatedDate());
			logger.info("Contrato modificado por: "
					+ contrato.getLastModifiedBy());
			logger.info("Contrato modificado en: "
					+ contrato.getLastModifiedDate());
			logger.info("----------------------");
		}

	}
}
