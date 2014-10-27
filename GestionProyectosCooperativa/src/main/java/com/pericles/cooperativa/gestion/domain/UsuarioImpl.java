/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * Un Usuario. (Abstracta) Será socio, empleado o colaborador
 * 
 * @author Jacin
 */
@Entity
@Table(name = "USUARIOS", indexes = { @Index(columnList = "APELLIDOS_USUARIO, NOMBRE_USUARIO", unique = false) })
public abstract class UsuarioImpl extends ContactoImpl implements Usuario {

	private static final long serialVersionUID = -1774923283981865415L;

	private static final int MAX_LONGITUD_E_MAIL = 50;
	private static final String TRES_PUNTOS = "...";

	@Column(name = "NOMBRE_USUARIO", nullable = false, length = 15)
	@NotEmpty
	@Length(max = 15)
	protected String nombreUsuario;
	@Column(name = "APELLIDOS_USUARIO", nullable = false, length = 45)
	@NotEmpty
	@Length(max = 45)
	protected String apellidosUsuario;
	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "FECHA_NACIMIENTO")
	@Past
	protected LocalDate fechaNacimiento;
	@Column(name = "SEXO")
	@Enumerated(EnumType.STRING)
	protected Sexo sexo;
	@Column(name = "E_MAIL_ASOCIACION", length = 200, unique = true)
	@Length(max = 200)
	// 320 max. (impide creación índice)
	// TODO: Revisar esto (No funciona "{invalid.email}")
	// No es una dirección de correo bien formada
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "{org.hibernate.validator.constraints.Email.message}")
	protected String correoElectronicoAsociacion;
	@Column(name = "TELEFONO_ASOCIACION", length = 15)
	@Length(max = 15)
	// NOTA: Ver @Pattern(regex=, flag=)
	protected String telefonoAsociacion;
	@Column(name = "TELEFONO_MOVIL_ASOCIACION", length = 15)
	@Length(max = 15)
	// NOTA: Ver @Pattern(regex=, flag=)
	protected String telefonoMovilAsociacion;
	@Basic(fetch = FetchType.LAZY)
	@Lob
	@Column(name = "FOTOGRAFIA")
	protected Byte[] fotografia;

	// TODO: A VER CÓMO SE HACE ESTO CON SEGURIDAD SPRING
	@Column(name = "USER_NAME", length = 15, unique = true)
	// nullable = false, length = 15, unique = true
	// @NotEmpty
	@Length(max = 15)
	protected String userName;
	@Column(name = "PASSWORD", length = 15)
	// nullable = false, length = 15
	// @NotEmpty
	@Length(max = 15)
	protected String password;

	@Column(name = "ACTIVO", nullable = false)
	@NotNull
	protected Boolean activo;
	@ManyToMany(mappedBy = "usuarios")
	@OrderBy("fechaInicio ASC, fechaFin ASC")
	protected Set<Proyecto> proyectosParticipados; // Participa en proyecto
	@ManyToMany(mappedBy = "usuarios")
	@OrderBy("fechaInicio ASC, fechaFin ASC")
	protected Set<Tarea> tareas; // Realiza tarea
	@OneToMany(mappedBy = "usuario")
	@OrderBy("fechaYHora ASC")
	protected Set<Incidencia> incidencias; // Introduce incidencias

	public UsuarioImpl() {
		proyectosParticipados = new HashSet<Proyecto>();
		tareas = new HashSet<Tarea>();
		incidencias = new HashSet<Incidencia>();
	}

	@Transient
	public String getCorreoElectronicoAsociacionCorto() {
		if (correoElectronicoAsociacion.length() <= MAX_LONGITUD_E_MAIL)
			return correoElectronicoAsociacion;
		StringBuffer result = new StringBuffer(MAX_LONGITUD_E_MAIL + 3);
		result.append(correoElectronicoAsociacion.substring(0,
				MAX_LONGITUD_E_MAIL));
		result.append(TRES_PUNTOS);

		return result.toString();
	}

	/**
	 * Recoge el nombre completo del usuario.
	 * 
	 * @return El nombre completo del usuario.
	 */
	@Transient
	public String getNombre() {
		StringBuilder nombre = new StringBuilder();

		nombre.append(nombreUsuario);
		nombre.append(" ");
		nombre.append(apellidosUsuario);

		return nombre.toString();
	}

	/**
	 * @return the nombreUsuario
	 */
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	/**
	 * @param nombreUsuario
	 *            the nombreUsuario to set
	 */
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	/**
	 * @return the apellidosUsuario
	 */
	public String getApellidosUsuario() {
		return apellidosUsuario;
	}

	/**
	 * @param apellidosUsuario
	 *            the apellidosUsuario to set
	 */
	public void setApellidosUsuario(String apellidosUsuario) {
		this.apellidosUsuario = apellidosUsuario;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento
	 *            the fechaNacimiento to set
	 */
	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * @return the sexo
	 */
	public Sexo getSexo() {
		return sexo;
	}

	/**
	 * @param sexo
	 *            the sexo to set
	 */
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the correoElectronicoAsociacion
	 */
	public String getCorreoElectronicoAsociacion() {
		return correoElectronicoAsociacion;
	}

	/**
	 * @param correoElectronicoAsociacion
	 *            the correoElectronicoAsociacion to set
	 */
	public void setCorreoElectronicoAsociacion(
			String correoElectronicoAsociacion) {
		this.correoElectronicoAsociacion = correoElectronicoAsociacion;
	}

	/**
	 * @return the telefonoAsociacion
	 */
	public String getTelefonoAsociacion() {
		return telefonoAsociacion;
	}

	/**
	 * @param telefonoAsociacion
	 *            the telefonoAsociacion to set
	 */
	public void setTelefonoAsociacion(String telefonoAsociacion) {
		this.telefonoAsociacion = telefonoAsociacion;
	}

	/**
	 * @return the telefonoMovilAsociacion
	 */
	public String getTelefonoMovilAsociacion() {
		return telefonoMovilAsociacion;
	}

	/**
	 * @param telefonoMovilAsociacion
	 *            the telefonoMovilAsociacion to set
	 */
	public void setTelefonoMovilAsociacion(String telefonoMovilAsociacion) {
		this.telefonoMovilAsociacion = telefonoMovilAsociacion;
	}

	/**
	 * @return the fotografia
	 */
	public Byte[] getFotografia() {
		return fotografia;
	}

	/**
	 * @param fotografia
	 *            the fotografia to set
	 */
	public void setFotografia(Byte[] fotografia) {
		this.fotografia = fotografia;
	}

	/**
	 * @return the user
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo
	 *            the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the proyectosParticipados
	 */
	public Set<Proyecto> getProyectosParticipados() {
		return proyectosParticipados;
	}

	/**
	 * @param proyectosParticipados
	 *            the proyectosParticipados to set
	 */
	public void setProyectosParticipados(Set<Proyecto> proyectosParticipados) {
		this.proyectosParticipados = proyectosParticipados;
	}

	/**
	 * @return the tareas
	 */
	public Set<Tarea> getTareas() {
		return tareas;
	}

	/**
	 * @param tareas
	 *            the tareas to set
	 */
	public void setTareas(Set<Tarea> tareas) {
		this.tareas = tareas;
	}

	/**
	 * @return the incidencias
	 */
	public Set<Incidencia> getIncidencias() {
		return incidencias;
	}

	/**
	 * @param incidencias
	 *            the incidencias to set
	 */
	public void setIncidencias(Set<Incidencia> incidencias) {
		this.incidencias = incidencias;
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
				.append("nombreUsuario", nombreUsuario)
				.append("apellidosUsuario", apellidosUsuario)
				.append("fechaNacimiento", fechaNacimiento)
				.append("sexo", sexo)
				.append("correoElectronicoAsociacion",
						correoElectronicoAsociacion)
				.append("telefonoMovilAsociacion", telefonoMovilAsociacion)
				.append("userName", userName).append("password", password)
				.append("activo", activo)
				.append("proyectosParticipados", proyectosParticipados.size())
				.append("tareas", tareas.size())
				.append("incidencias", incidencias.size()).toString();
	}

}
