/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

/**
 * Un Socio.
 * 
 * @author Jacin
 */
@Entity
@Table(name = "SOCIOS")
@DiscriminatorValue("SOCIO")
public class Socio extends UsuarioImpl {

	private static final long serialVersionUID = -6284373407109447872L;

	// Lo controlará la aplicación
	@Column(name = "ELIMINADO", nullable = false)
	@NotNull
	private Boolean eliminado;
	@ManyToMany(mappedBy = "coordinadores", fetch = FetchType.LAZY)
	@OrderBy("fechaInicio ASC, fechaFin ASC")
	private Set<Proyecto> proyectosCoordinados; // Coordina proyecto

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

	public Socio() {
		proyectosCoordinados = new HashSet<Proyecto>();
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
	 * @return the proyectosCoordinados
	 */
	public Set<Proyecto> getProyectosCoordinados() {
		return proyectosCoordinados;
	}

	/**
	 * @param proyectosCoordinados
	 *            the proyectosCoordinados to set
	 */
	public void setProyectosCoordinados(Set<Proyecto> proyectosCoordinados) {
		this.proyectosCoordinados = proyectosCoordinados;
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
				.append("eliminado", eliminado)
				.append("proyectosCoordinados", proyectosCoordinados.size())
				.append("createdBy", createdBy)
				.append("createdDate", createdDate)
				.append("lastModifiedBy", lastModifiedBy)
				.append("lastModifiedDate", lastModifiedDate).toString();
	}

}
