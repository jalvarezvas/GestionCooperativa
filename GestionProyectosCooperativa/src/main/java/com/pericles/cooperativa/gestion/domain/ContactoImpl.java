/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.data.domain.Auditable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Un contacto
 * 
 * @author Jacin
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// InheritanceType.JOINED
@DiscriminatorColumn(name = "TIPO_CONTACTO")
// @FilterDef(name = "contactosActivos")
// @Filter(name = "contactosActivos", condition = "FECHA_BORRADO IS NULL")
// @SQLDelete(sql =
// "UPDATE CONTACTOS SET FECHA_BORRADO=CURRENT_DATE WHERE ID=?")
public abstract class ContactoImpl implements Contacto,
		Auditable<String, Long>, Serializable {

	private static final long serialVersionUID = 820731608576014141L;

	@Id
	@Column(name = "ID")
	// @GeneratedValue(strategy = GenerationType.TABLE)
	// .IDENTITY
	@TableGenerator(name = "Contact_Gen", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL")
	@GeneratedValue(generator = "Contact_Gen")
	protected Long id;
	@Column(name = "IDENTIFICACION", nullable = false, length = 9, unique = true)
	@NotEmpty
	@Length(min = 9, max = 9)
	// NOTA: Ver @Pattern(regex=, flag=)
	protected String identificacion; // NIF/CIF
	@Column(name = "E_MAIL", length = 320)
	@Length(max = 320)
	// TODO: Revisar esto (No funciona "{invalid.email}")
	// No es una dirección de correo bien formada
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{org.hibernate.validator.constraints.Email.message}")
	protected String correoElectronico;
	@Column(name = "TELEFONO", length = 15)
	// TODO: Revisar , message = "{javax.validation.constraints.Max.message}"
	@Length(max = 15)
	// "El número de teléfono supera los dígitos permitidos"
	// TODO: Revisar esto (No funciona
	// "{javax.validation.constraints.Digits.message}")
	@Digits(integer = 15, fraction = 0, message = "Número de teléfono incorrecto")
	protected String telefono;
	@Column(name = "TELEFONO_MOVIL", length = 15)
	@Length(max = 15)
	// NOTA: Ver @Pattern(regex=, flag=)
	protected String telefonoMovil;
	@Embedded
	@Valid
	protected Domicilio domicilio;

	// Sistema de versionado
	@Version
	@Column(name = "VERSION")
	protected int version;

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

	// @Column(name = "FECHA_BORRADO")
	// private Date fechaBorrado;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the identificacion
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/**
	 * @param identificacion
	 *            the identificacion to set
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * @return the correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	/**
	 * @param correoElectronico
	 *            the correoElectronico to set
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the telefonoMovil
	 */
	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	/**
	 * @param telefonoMovil
	 *            the telefonoMovil to set
	 */
	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	/**
	 * @return the domicilio
	 */
	public Domicilio getDomicilio() {
		return domicilio;
	}

	/**
	 * @param domicilio
	 *            the domicilio to set
	 */
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
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
				.append("identificacion", identificacion)
				.append("correoElectronico", correoElectronico)
				.append("telefono", telefono)
				.append("telefonoMovil", telefonoMovil)
				.append("domicilio", domicilio).append("telefono", telefono)
				.append("version", version).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {

		return new HashCodeBuilder(17, 31).append(this.getIdentificacion())
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

		if (!(obj instanceof ContactoImpl)) {
			return false;
		}
		ContactoImpl other = (ContactoImpl) obj;

		// .appendSuper(super.equals(obj))
		return new EqualsBuilder().append(this.getIdentificacion(),
				other.getIdentificacion()).isEquals();
	}

}
