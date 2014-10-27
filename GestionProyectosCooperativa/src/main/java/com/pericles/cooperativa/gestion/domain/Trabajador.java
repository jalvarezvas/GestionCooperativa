package com.pericles.cooperativa.gestion.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.joda.time.DateTime;

/**
 * Un Trabajador.
 * 
 * @author Jacin
 */
@Entity
@Table(name = "TRABAJADORES")
@DiscriminatorValue("TRABAJADOR")
public class Trabajador extends UsuarioImpl {

	private static final long serialVersionUID = -7689989008529290168L;

	@Column(name = "PROFESION", length = 30)
	@Length(max = 30)
	private String profesion;
	@Column(name = "CATEGORIA_PROFESIONAL", length = 30)
	@Length(max = 30)
	private String categoriaProfesional;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "trabajador", cascade = CascadeType.PERSIST, orphanRemoval = true)
	// TODO: Si no se desea dar de alta un trabajador sin contrato
	// Añadir a @OneToOne ", optional = false"
	// Deberá ser @NotNull
	private Contrato contrato;
	// Lo controlará la aplicación
	@Column(name = "ELIMINADO", nullable = false)
	@NotNull
	private Boolean eliminado;

	// Campos de auditoría
	// @Column(name = "USUARIO_CREACION")
	// private String createdBy;
	// @DateTimeFormat(iso = ISO.DATE_TIME)
	// @Column(name = "FECHA_CREACION")
	// private DateTime createdDate;
	// @Column(name = "USUARIO_ULTIMA_MODIFICACION")
	// private String lastModifiedBy;
	// @DateTimeFormat(iso = ISO.DATE_TIME)
	// @Column(name = "FECHA_ULTIMA_MODIFICACION")
	// private DateTime lastModifiedDate;

	/**
	 * @return the profesion
	 */
	public String getProfesion() {
		return profesion;
	}

	/**
	 * @param profesion
	 *            the profesion to set
	 */
	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	/**
	 * @return the categoriaProfesional
	 */
	public String getCategoriaProfesional() {
		return categoriaProfesional;
	}

	/**
	 * @param categoriaProfesional
	 *            the categoriaProfesional to set
	 */
	public void setCategoriaProfesional(String categoriaProfesional) {
		this.categoriaProfesional = categoriaProfesional;
	}

	/**
	 * @return the contrato
	 */
	public Contrato getContrato() {
		return contrato;
	}

	/**
	 * @param contrato
	 *            the contrato to set
	 */
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	/**
	 * @return the eliminado
	 */
	public Boolean getEliminado() {
		return eliminado;
	}

	/**
	 * @param eliminado
	 *            the eliminado to set
	 */
	public void setEliminado(Boolean eliminado) {
		this.eliminado = eliminado;
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

		if (getId() == null) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pericles.cooperativa.gestion.domain.UsuarioImpl#toString()
	 */
	@Override
	public String toString() {

		return new ToStringBuilder(this).appendSuper(super.toString())
				.append("profesion", profesion)
				.append("categoriaProfesional", categoriaProfesional)
				.append("contrato", contrato).append("eliminado", eliminado)
				.append("createdBy", createdBy)
				.append("createdDate", createdDate)
				.append("lastModifiedBy", lastModifiedBy)
				.append("lastModifiedDate", lastModifiedDate).toString();
	}

}
