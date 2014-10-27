/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Auditable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Una incidencia
 * 
 * @author Jacin
 */
@Entity
@Table(name = "INCIDENCIAS")
public class Incidencia implements Auditable<String, Long>, Serializable {

	private static final long serialVersionUID = -2561652720999317102L;

	private static final int MAX_LONGITUD_DESCRIPCION = 100;
	private static final String TRES_PUNTOS = "...";

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "ID_PROYECTO")
	@NotNull
	private Proyecto proyecto;
	@ManyToOne(cascade = CascadeType.PERSIST, optional = false)
	@JoinColumn(name = "ID_USUARIO")
	@NotNull
	private UsuarioImpl usuario;
	@Column(name = "DESCRIPCION", nullable = false, length = 500)
	@NotEmpty
	@Length(max = 500)
	private String descripcion;
	// Se añadirá al crearla en la BD
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "FECHA_Y_HORA", nullable = false)
	private LocalDateTime fechaYHora;
	// TODO: Añadir un campo de adjunto (email, doc, foto, etc)

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
	protected Incidencia() {

	}

	/**
	 * @param proyecto
	 * @param usuario
	 * @param descripcion
	 * @param fechaYHora
	 */
	public Incidencia(Proyecto proyecto, UsuarioImpl usuario,
			String descripcion, LocalDateTime fechaYHora) {
		super();
		this.proyecto = proyecto;
		this.usuario = usuario;
		this.descripcion = descripcion;
		this.fechaYHora = fechaYHora;
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
	 * @return the usuario
	 */
	public UsuarioImpl getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(UsuarioImpl usuario) {
		this.usuario = usuario;
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
	 * @return the fechaYHora
	 */
	public LocalDateTime getFechaYHora() {
		return fechaYHora;
	}

	/**
	 * @param fechaYHora
	 *            the fechaYHora to set
	 */
	public void setFechaYHora(LocalDateTime fechaYHora) {
		this.fechaYHora = fechaYHora;
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
				.append("proyecto", proyecto.getNombre())
				.append("usuario",
						usuario.getApellidosUsuario() + " ,"
								+ usuario.getNombreUsuario())
				.append("descripcion", descripcion)
				.append("fechaYHora", fechaYHora).append("version", version)
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

		return new HashCodeBuilder(17, 39).append(this.getProyecto())
				.append(this.getDescripcion()).append(this.getFechaYHora())
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

		if (!(obj instanceof Incidencia)) {
			return false;
		}
		Incidencia other = (Incidencia) obj;

		// .appendSuper(super.equals(obj))
		return new EqualsBuilder()
				.append(this.getProyecto(), other.getProyecto())
				.append(this.getDescripcion(), other.getDescripcion())
				.append(this.getFechaYHora(), other.getFechaYHora()).isEquals();
	}

}
