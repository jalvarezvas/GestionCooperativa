/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Una Entidad.
 * 
 * @author Jacin
 */
@Entity
@Table(name = "ENTIDADES")
@DiscriminatorValue("ENTIDAD")
public class Entidad extends ContactoImpl {

	private static final long serialVersionUID = -6491923328031912510L;

	private static final int MAX_LONGITUD_PAGINA_WEB = 50;
	private static final int MAX_LONGITUD_E_MAIL = 50;
	private static final String TRES_PUNTOS = "...";

	@Column(name = "NOMBRE_ENTIDAD", nullable = false, length = 60, unique = true)
	@NotEmpty
	@Length(max = 60)
	private String nombreEntidad;
	@Column(name = "PAGINA_WEB", length = 200)
	@Length(max = 200)
	private String paginaWeb;
	@Column(name = "NOMBRE_PERSONA_CONTACTO", length = 60)
	@Length(max = 60)
	private String nombrePersonaContacto;
	@Column(name = "E_MAIL_PERSONA_CONTACTO", length = 320)
	@Length(max = 320)
	// TODO: Revisar esto (No funciona "{invalid.email}")
	// No es una dirección de correo bien formada
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{org.hibernate.validator.constraints.Email.message}")
	private String correoElectronicoPersonaContacto;
	@Column(name = "TELEFONO_PERSONA_CONTACTO", length = 15)
	@Length(max = 15)
	// NOTA: Ver @Pattern(regex=, flag=)
	private String telefonoPersonaContacto;
	@Column(name = "TELEFONO_MOVIL_PERSONA_CONTACTO", length = 15)
	@Length(max = 15)
	// NOTA: Ver @Pattern(regex=, flag=)
	private String telefonoMovilPersonaContacto;
	// Lo controlará la aplicación
	@Column(name = "ELIMINADO", nullable = false)
	@NotNull
	private Boolean eliminado;
	@OneToMany(mappedBy = "entidadCliente", fetch = FetchType.LAZY)
	@OrderBy("fechaInicio ASC, fechaFin ASC")
	private Set<Proyecto> proyectosContratados; // Contrata proyecto
	@ManyToMany(mappedBy = "entidadesColaboradoras", fetch = FetchType.LAZY)
	@OrderBy("fechaInicio ASC, fechaFin ASC")
	private Set<Proyecto> proyectosColaborados; // Colabora en proyecto

	// Paso 2º
	@OneToMany(mappedBy = "entidad", cascade = CascadeType.PERSIST)
	// @OrderBy("apellidosUsuario ASC, nombreUsuario ASC")
	// Esto no funciona cuando le entidad hereda de otra
	@OrderBy
	// Lo hará por pk (o se implementará una query con order by)
	private Set<Colaborador> colaboradores; // Colaboradores
											// proyecto

	/**
	 * 
	 */
	public Entidad() {
		proyectosContratados = new HashSet<Proyecto>();
		proyectosColaborados = new HashSet<Proyecto>();
		colaboradores = new HashSet<Colaborador>();
	}

	@Transient
	public String getPaginaWebCorta() {
		if (paginaWeb.length() <= MAX_LONGITUD_PAGINA_WEB)
			return paginaWeb;
		StringBuffer result = new StringBuffer(MAX_LONGITUD_PAGINA_WEB + 3);
		result.append(paginaWeb.substring(0, MAX_LONGITUD_PAGINA_WEB));
		result.append(TRES_PUNTOS);

		return result.toString();
	}

	@Transient
	public String getCorreoElectronicoCorto() {
		if (correoElectronico.length() <= MAX_LONGITUD_E_MAIL)
			return correoElectronico;
		StringBuffer result = new StringBuffer(MAX_LONGITUD_E_MAIL + 3);
		result.append(correoElectronico.substring(0, MAX_LONGITUD_E_MAIL));
		result.append(TRES_PUNTOS);

		return result.toString();
	}

	@Transient
	public String getCorreoElectronicoPersonaContactoCorto() {
		if (correoElectronicoPersonaContacto.length() <= MAX_LONGITUD_E_MAIL)
			return correoElectronicoPersonaContacto;
		StringBuffer result = new StringBuffer(MAX_LONGITUD_E_MAIL + 3);
		result.append(correoElectronicoPersonaContacto.substring(0,
				MAX_LONGITUD_E_MAIL));
		result.append(TRES_PUNTOS);

		return result.toString();
	}

	/**
	 * @return the nombre
	 */
	public String getNombreEntidad() {
		return nombreEntidad;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombreEntidad(String nombre) {
		this.nombreEntidad = nombre;
	}

	/**
	 * @return the paginaWeb
	 */
	public String getPaginaWeb() {
		return paginaWeb;
	}

	/**
	 * @param paginaWeb
	 *            the paginaWeb to set
	 */
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	/**
	 * @return the nombrePersonaContacto
	 */
	public String getNombrePersonaContacto() {
		return nombrePersonaContacto;
	}

	/**
	 * @param nombrePersonaContacto
	 *            the nombrePersonaContacto to set
	 */
	public void setNombrePersonaContacto(String nombrePersonaContacto) {
		this.nombrePersonaContacto = nombrePersonaContacto;
	}

	/**
	 * @return the correoElectronicoPersonaContacto
	 */
	public String getCorreoElectronicoPersonaContacto() {
		return correoElectronicoPersonaContacto;
	}

	/**
	 * @param correoElectronicoPersonaContacto
	 *            the correoElectronicoPersonaContacto to set
	 */
	public void setCorreoElectronicoPersonaContacto(
			String correoElectronicoPersonaContacto) {
		this.correoElectronicoPersonaContacto = correoElectronicoPersonaContacto;
	}

	/**
	 * @return the telefonoPersonaContacto
	 */
	public String getTelefonoPersonaContacto() {
		return telefonoPersonaContacto;
	}

	/**
	 * @param telefonoPersonaContacto
	 *            the telefonoPersonaContacto to set
	 */
	public void setTelefonoPersonaContacto(String telefonoPersonaContacto) {
		this.telefonoPersonaContacto = telefonoPersonaContacto;
	}

	/**
	 * @return the telefonoMovilPersonaContacto
	 */
	public String getTelefonoMovilPersonaContacto() {
		return telefonoMovilPersonaContacto;
	}

	/**
	 * @param telefonoMovilPersonaContacto
	 *            the telefonoMovilPersonaContacto to set
	 */
	public void setTelefonoMovilPersonaContacto(
			String telefonoMovilPersonaContacto) {
		this.telefonoMovilPersonaContacto = telefonoMovilPersonaContacto;
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
	 * @return the proyectosContratados
	 */
	public Set<Proyecto> getProyectosContratados() {
		return proyectosContratados;
	}

	/**
	 * @return the proyectosColaborados
	 */
	public Set<Proyecto> getProyectosColaborados() {
		return proyectosColaborados;
	}

	/**
	 * @return the colaboradores
	 */
	public Set<Colaborador> getColaboradores() {
		return colaboradores;
	}

	/**
	 * @param colaboradores
	 *            the colaboradores to set
	 */
	public void setColaboradores(Set<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pericles.cooperativa.gestion.domain.ContactoImpl#toString()
	 */
	@Override
	public String toString() {

		return new ToStringBuilder(this)
				.appendSuper(super.toString())
				.append("nombreEntidad", nombreEntidad)
				.append("paginaWeb", paginaWeb)
				.append("nombrePersonaContacto", nombrePersonaContacto)
				.append("correoElectronicoPersonaContacto",
						correoElectronicoPersonaContacto)
				.append("telefonoPersonaContacto", telefonoPersonaContacto)
				.append("telefonoMovilPersonaContacto",
						telefonoMovilPersonaContacto)
				.append("eliminado", eliminado)
				.append("proyectosContratados", proyectosContratados.size())
				.append("proyectosColaborados", proyectosColaborados.size())
				.append("colaboradores", colaboradores.size())
				.append("createdBy", createdBy)
				.append("createdDate", createdDate)
				.append("lastModifiedBy", lastModifiedBy)
				.append("lastModifiedDate", lastModifiedDate).toString();
	}

}
