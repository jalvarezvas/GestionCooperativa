package com.pericles.cooperativa.gestion.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Auditable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Un proyecto.
 * 
 * @author Jacin
 * 
 */
@Entity
@Table(name = "PROYECTOS")
public class Proyecto implements Auditable<String, Long>, Serializable {

	private static final long serialVersionUID = -6436511722619900812L;

	private static final int MAX_LONGITUD_DESCRIPCION = 50;
	private static final String TRES_PUNTOS = "...";

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Paso 1º ¿¿¿Habría que poner updatable = false???
	@Column(name = "NOMBRE", nullable = false, length = 60, unique = true)
	@NotEmpty
	@Length(max = 60)
	private String nombre;
	@Column(name = "PRESUPUESTO", nullable = false, precision = 8, scale = 2)
	@NotNull
	@Digits(integer = 6, fraction = 2)
	private BigDecimal presupuesto;
	@Column(name = "DESCRIPCION", nullable = false, length = 255)
	@NotEmpty
	@Length(max = 255)
	private String descripcion;
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "ID_ENTIDAD_CLIENTE")
	@NotNull
	private Entidad entidadCliente;
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "FECHA_INICIO", nullable = false)
	@NotNull
	private LocalDate fechaInicio;
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "FECHA_FIN", nullable = false)
	@NotNull
	private LocalDate fechaFin;
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO_PROYECTO", nullable = false)
	@NotNull
	private EstadoProyecto estadoProyecto;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "PROYECTOS_COORDINAN_SOCIOS", joinColumns = @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_SOCIO", referencedColumnName = "ID"))
	// @OrderBy("apellidosUsuario ASC, nombreUsuario ASC")
	@NotNull
	@Size(min = 1, max = 2)
	private Set<Socio> coordinadores; // Coordina proyecto
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "PROYECTOS_COLABORAN_ENTIDADES", joinColumns = @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_ENTIDAD", referencedColumnName = "ID"))
	@OrderBy("nombreEntidad ASC")
	private Set<Entidad> entidadesColaboradoras; // Colabora en proyecto
	@Basic(fetch = FetchType.LAZY)
	@Lob
	// Vale length en Lob???
	@Column(name = "BALANCE", length = 10000)
	@Length(max = 10000)
	private String balance;

	// Paso 2º
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "PROYECTOS_PARTICIPAN_USUARIOS", joinColumns = @JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID"))
	@OrderBy("apellidosUsuario ASC, nombreUsuario ASC")
	private Set<UsuarioImpl> usuarios; // Participa en proyecto

	// Paso 3º
	@OneToMany(mappedBy = "proyecto", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, orphanRemoval = true)
	// Comprobar si es necesario CascadeType.MERGE
	@OrderBy("fechaInicio ASC, fechaFin ASC")
	private List<Tarea> tareas;

	// Paso 4º (Cuando ya esté en marcha el proyecto - Quizá en EN_EJECUCION)
	@OneToMany(mappedBy = "proyecto", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, orphanRemoval = true)
	// Comprobar si es necesario CascadeType.MERGE
	@OrderBy("fechaYHora ASC")
	private List<Incidencia> incidencias;

	// Sistema de versionado
	@Version
	@Column(name = "VERSION")
	private int version;

	// Campos de auditoría
	@Column(name = "USUARIO_CREACION")
	private String createdBy;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "FECHA_CREACION")
	private DateTime createdDate;
	@Column(name = "USUARIO_ULTIMA_MODIFICACION")
	private String lastModifiedBy;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "FECHA_ULTIMA_MODIFICACION")
	private DateTime lastModifiedDate;

	/**
	 * 
	 */
	public Proyecto() {
		entidadesColaboradoras = new HashSet<Entidad>();
		coordinadores = new HashSet<Socio>();
		usuarios = new HashSet<UsuarioImpl>();
		tareas = new ArrayList<Tarea>();
		incidencias = new ArrayList<Incidencia>();
	}

	@Transient
	public String getDescripcionCorta() {
		if (descripcion.length() <= MAX_LONGITUD_DESCRIPCION)
			return descripcion;
		StringBuffer result = new StringBuffer(MAX_LONGITUD_DESCRIPCION + 3);
		result.append(descripcion.substring(0, MAX_LONGITUD_DESCRIPCION));
		result.append(TRES_PUNTOS);

		return result.toString();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the presupuesto
	 */
	public BigDecimal getPresupuesto() {
		return presupuesto;
	}

	/**
	 * @param presupuesto
	 *            the presupuesto to set
	 */
	public void setPresupuesto(BigDecimal presupuesto) {
		this.presupuesto = presupuesto;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the entidadCliente
	 */
	public Entidad getEntidadCliente() {
		return entidadCliente;
	}

	/**
	 * @param entidadCliente
	 *            the entidadCliente to set
	 */
	public void setEntidadCliente(Entidad entidadCliente) {
		this.entidadCliente = entidadCliente;
	}

	/**
	 * @return the fechaInicio
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	// /**
	// * @return the formatted fechaInicio
	// */
	// @Transient
	// public String getFechaInicioString() {
	// return org.joda.time.format.DateTimeFormat.forPattern("dd-MM-yyyy")
	// .print(fechaInicio);
	// }

	/**
	 * @param fechaInicio
	 *            the fechaInicio to set
	 */
	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public LocalDate getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            the fechaFin to set
	 */
	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the estadoProyecto
	 */
	public EstadoProyecto getEstadoProyecto() {
		return estadoProyecto;
	}

	/**
	 * @param estadoProyecto
	 *            the estadoProyecto to set
	 */
	public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
		this.estadoProyecto = estadoProyecto;
	}

	/**
	 * @return the coordinadores
	 */
	public Set<Socio> getCoordinadores() {
		return coordinadores;
	}

	/**
	 * @param coordinadores
	 *            the coordinadores to set
	 */
	public void setCoordinadores(Set<Socio> coordinadores) {
		this.coordinadores = coordinadores;
	}

	/**
	 * @return the entidadesColaboradoras
	 */
	public Set<Entidad> getEntidadesColaboradoras() {
		return entidadesColaboradoras;
	}

	/**
	 * @param entidadesColaboradoras
	 *            the entidadesColaboradoras to set
	 */
	public void setEntidadesColaboradoras(Set<Entidad> entidadesColaboradoras) {
		this.entidadesColaboradoras = entidadesColaboradoras;
	}

	/**
	 * @return the balance
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
	 * @return the usuarios
	 */
	public Set<UsuarioImpl> getUsuarios() {
		return usuarios;
	}

	/**
	 * @param usuarios
	 *            the usuarios to set
	 */
	public void setUsuarios(Set<UsuarioImpl> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * @return the tareas
	 */
	public List<Tarea> getTareas() {
		return tareas;
	}

	/**
	 * @param tareas
	 *            the tareas to set
	 */
	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	/**
	 * @return the incidencias
	 */
	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	/**
	 * @param incidencias
	 *            the incidencias to set
	 */
	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the createdDate
	 */
	public DateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate
	 *            the lastModifiedDate to set
	 */
	public void setLastModifiedDate(DateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Transient
	public boolean isNew() { // auditoría

		if (id == null) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return new ToStringBuilder(this)
				.append("id", id)
				.append("nombre", nombre)
				.append("presupuesto", presupuesto)
				.append("descripcion", descripcion)
				.append("entidadCliente", entidadCliente)
				.append("fechaInicio", fechaInicio)
				.append("fechaFin", fechaFin)
				.append("estadoProyecto", estadoProyecto)
				.append("coordinadores", coordinadores.size())
				.append("entidadesColaboradoras", entidadesColaboradoras.size())
				.append("balance", balance).append("usuarios", usuarios.size())
				.append("tareas", tareas.size())
				.append("incidencias", incidencias.size())
				.append("version", version).append("createdBy", createdBy)
				.append("createdDate", createdDate)
				.append("lastModifiedBy", lastModifiedBy)
				.append("lastModifiedDate", lastModifiedDate).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		return new HashCodeBuilder(17, 35).append(this.getNombre())
				.toHashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (!(obj instanceof Proyecto)) {
			return false;
		}
		Proyecto other = (Proyecto) obj;

		// .appendSuper(super.equals(obj))
		return new EqualsBuilder().append(this.getNombre(), other.getNombre())
				.isEquals();
	}

}
