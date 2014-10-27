package com.pericles.cooperativa.gestion.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.data.domain.Auditable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Un Contrato.
 * 
 * @author Jacin
 */
@Entity
@Table(name = "CONTRATOS")
public class Contrato implements Auditable<String, Long>, Serializable {

	private static final long serialVersionUID = -6817345553966162879L;

	@Id
	private Long id;
	@MapsId
	@OneToOne(cascade = CascadeType.PERSIST, optional = false)
	// (fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRABAJADOR", referencedColumnName = "ID")
	@NotNull
	private Trabajador trabajador;
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_CONTRATO", nullable = false)
	@NotNull
	private TipoContrato tipoContrato;
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "FECHA_INICIO", nullable = false)
	@NotNull
	private LocalDate fechaInicio;
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "FECHA_FIN")
	private LocalDate fechaFin;

	// Sistema de versionado
	@Version
	@Column(name = "VERSION")
	private int version;

	// Campos de auditoría
	@Column(name = "USUARIO_CREACION")
	protected String createdBy;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "FECHA_CREACION")
	protected DateTime createdDate;
	@Column(name = "USUARIO_ULTIMA_MODIFICACION")
	protected String lastModifiedBy;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@Column(name = "FECHA_ULTIMA_MODIFICACION")
	protected DateTime lastModifiedDate;

	/**
	 * 
	 */
	protected Contrato() {

	}

	/**
	 * @param tipoContrato
	 * @param trabajador
	 * @param fechaInicio
	 * @param fechaFin
	 */
	public Contrato(TipoContrato tipoContrato, Trabajador trabajador,
			LocalDate fechaInicio, LocalDate fechaFin) {
		super();
		this.tipoContrato = tipoContrato;
		this.trabajador = trabajador;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the trabajador
	 */
	public Trabajador getTrabajador() {
		return trabajador;
	}

	/**
	 * @param trabajador
	 *            the trabajador to set
	 */
	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	/**
	 * @return the tipoContrato
	 */
	public TipoContrato getTipoContrato() {
		return tipoContrato;
	}

	/**
	 * @param tipoContrato
	 *            the tipoContrato to set
	 */
	public void setTipoContrato(TipoContrato tipoContrato) {
		this.tipoContrato = tipoContrato;
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
	 * @return the id
	 */
	public Long getId() {
		return id;
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
	public boolean isNew() {

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
				.append("trabajador", trabajador)
				.append("tipoContrato", tipoContrato)
				.append("fechaInicio", fechaInicio)
				.append("fechaFin", fechaFin).append("version", version)
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

		return new HashCodeBuilder(17, 33).append(this.getTrabajador())
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

		if (!(obj instanceof Contrato)) {
			return false;
		}
		Contrato other = (Contrato) obj;

		// .appendSuper(super.equals(obj))
		return new EqualsBuilder().append(this.getTrabajador(),
				other.getTrabajador()).isEquals();
	}

}
