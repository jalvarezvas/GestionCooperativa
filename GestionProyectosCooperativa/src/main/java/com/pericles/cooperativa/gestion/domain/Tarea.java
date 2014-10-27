/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

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
 * Una tarea.
 * 
 * @author Jacin
 * 
 */
@Entity
@Table(name = "TAREAS")
public class Tarea implements Auditable<String, Long>, Serializable {

	private static final long serialVersionUID = -8043368179569594381L;

	private static final int MAX_LONGITUD_DESCRIPCION = 50;
	private static final String TRES_PUNTOS = "...";

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "ID_PROYECTO", referencedColumnName = "ID")
	@NotNull
	private Proyecto proyecto;
	@Column(name = "DESCRIPCION", nullable = false, length = 255)
	@NotEmpty
	@Length(max = 255)
	private String descripcion;
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO_TAREA", nullable = false)
	@NotNull
	private EstadoTarea estadoTarea;
	// Debería ser >= (Por defecto) proyecto.fechaInicio
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "FECHA_INICIO", nullable = false)
	private LocalDate fechaInicio;
	// Por defecto proyecto.fechaFin
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "FECHA_FIN")
	private LocalDate fechaFin;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "USUARIOS_REALIZAN_TAREAS", joinColumns = @JoinColumn(name = "ID_TAREA", referencedColumnName = "ID"), inverseJoinColumns = { @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID") })
	@OrderBy("apellidosUsuario ASC, nombreUsuario ASC")
	@NotNull
	private Set<UsuarioImpl> usuarios; // Usuarios (del proyecto) realizan tarea

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
	protected Tarea() {
		usuarios = new HashSet<UsuarioImpl>();
	}

	/**
	 * @param proyecto
	 * @param descripcion
	 * @param estadoTarea
	 * @param fechaInicio
	 * @param fechaFin
	 * @param usuarios
	 */
	public Tarea(Proyecto proyecto, String descripcion,
			EstadoTarea estadoTarea, LocalDate fechaInicio, LocalDate fechaFin,
			Set<UsuarioImpl> usuarios) {
		this.proyecto = proyecto;
		this.descripcion = descripcion;
		this.estadoTarea = estadoTarea;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.usuarios = usuarios;
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
	 * @return the proyecto
	 */
	public Proyecto getProyecto() {
		return proyecto;
	}

	/**
	 * @param proyecto
	 *            the proyecto to set
	 */
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
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
	 * @return the estadoTarea
	 */
	public EstadoTarea getEstadoTarea() {
		return estadoTarea;
	}

	/**
	 * @param estadoTarea
	 *            the estadoTarea to set
	 */
	public void setEstadoTarea(EstadoTarea estadoTarea) {
		this.estadoTarea = estadoTarea;
	}

	/**
	 * @return the fechaInicio
	 */
	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

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

		return new ToStringBuilder(this).append("id", id)
				.append("proyecto", proyecto.getNombre())
				.append("descripcion", descripcion)
				.append("estadoTarea", estadoTarea)
				.append("fechaInicio", fechaInicio)
				.append("fechaFin", fechaFin)
				.append("usuarios", usuarios.size()).append("version", version)
				.append("createdBy", createdBy)
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

		return new HashCodeBuilder(17, 37).append(this.getProyecto())
				.append(this.getDescripcion()).append(this.getFechaInicio())
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

		if (!(obj instanceof Tarea)) {
			return false;
		}
		Tarea other = (Tarea) obj;

		// .appendSuper(super.equals(obj))
		return new EqualsBuilder()
				.append(this.getProyecto(), other.getProyecto())
				.append(this.getDescripcion(), other.getDescripcion())
				.append(this.getFechaInicio(), other.getFechaInicio())
				.isEquals();
	}

}
