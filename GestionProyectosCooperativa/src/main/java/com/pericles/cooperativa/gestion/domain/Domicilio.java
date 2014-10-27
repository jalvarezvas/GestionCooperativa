/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

/**
 * Un domicilio
 * 
 * @author Jacin
 */
@Embeddable
@Access(AccessType.FIELD)
public class Domicilio {
	@Column(name = "DIRECCION", length = 60)
	@Length(max = 60)
	private String direccion; // 60 cars.
	@Column(name = "POBLACION", length = 60)
	@Length(max = 60)
	private String poblacion; // 60 cars.
	@Enumerated(EnumType.STRING)
	@Column(name = "PROVINCIA")
	private Provincia provincia;
	@Enumerated(EnumType.STRING)
	@Column(name = "COMUNIDAD_AUTONOMA")
	private ComunidadAutonoma comunidadAutonoma;
	@Column(name = "CODIGO_POSTAL", length = 5)
	private String codigoPostal; // 5 cars.
	@Column(name = "PAIS", length = 30)
	private String pais;

	/**
	 * 
	 */
	public Domicilio() {

	}

	/**
	 * @param direccion
	 * @param poblacion
	 * @param provincia
	 * @param comunidadAutonoma
	 * @param codigoPostal
	 * @param pais
	 */
	public Domicilio(String direccion, String poblacion, Provincia provincia,
			ComunidadAutonoma comunidadAutonoma, String codigoPostal,
			String pais) {
		super();
		this.direccion = direccion;
		this.poblacion = poblacion;
		this.provincia = provincia;
		this.comunidadAutonoma = comunidadAutonoma;
		this.codigoPostal = codigoPostal;
		this.pais = pais;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion
	 *            the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the poblacion
	 */
	public String getPoblacion() {
		return poblacion;
	}

	/**
	 * @param poblacion
	 *            the poblacion to set
	 */
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	/**
	 * @return the provincia
	 */
	public Provincia getProvincia() {
		return provincia;
	}

	/**
	 * @param provincia
	 *            the provincia to set
	 */
	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	/**
	 * @return the comunidadAutonoma
	 */
	public ComunidadAutonoma getComunidadAutonoma() {
		return comunidadAutonoma;
	}

	/**
	 * @param comunidadAutonoma
	 *            the comunidadAutonoma to set
	 */
	public void setComunidadAutonoma(ComunidadAutonoma comunidadAutonoma) {
		this.comunidadAutonoma = comunidadAutonoma;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal
	 *            the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return new ToStringBuilder(this).append("direccion", direccion)
				.append("poblacion", poblacion).append("provincia", provincia)
				.append("comunidadAutonoma", comunidadAutonoma)
				.append("codigoPostal", codigoPostal).append("pais", pais)
				.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		return new HashCodeBuilder(17, 41).append(this.getDireccion())
				.append(this.getPoblacion()).append(this.getProvincia())
				.append(this.getComunidadAutonoma())
				.append(this.getCodigoPostal()).append(this.getPais())
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

		if (!(obj instanceof Domicilio)) {
			return false;
		}

		Domicilio other = (Domicilio) obj;

		// .appendSuper(super.equals(obj))
		return new EqualsBuilder()
				.append(this.getDireccion(), other.getDireccion())
				.append(this.getPoblacion(), other.getPoblacion())
				.append(this.getProvincia(), other.getProvincia())
				.append(this.getComunidadAutonoma(),
						other.getComunidadAutonoma())
				.append(this.getCodigoPostal(), other.getCodigoPostal())
				.append(this.getPais(), other.getPais()).isEquals();
	}

}
