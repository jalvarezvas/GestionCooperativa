package com.pericles.cooperativa.gestion.domain;

import java.util.Set;

import org.joda.time.LocalDate;

/**
 * @author Jacin
 *
 */
public interface Usuario {

	public String getNombreUsuario();

	public void setNombreUsuario(String nombreUsuario);

	public String getApellidosUsuario();

	public void setApellidosUsuario(String apellidosUsuario);

	public LocalDate getFechaNacimiento();

	public void setFechaNacimiento(LocalDate fechaNacimiento);

	public Sexo getSexo();

	public void setSexo(Sexo sexo);

	public String getCorreoElectronicoAsociacion();

	public void setCorreoElectronicoAsociacion(
			String correoElectronicoAsociacion);

	public String getTelefonoAsociacion();

	public void setTelefonoAsociacion(String telefonoAsociacion);

	public String getTelefonoMovilAsociacion();

	public void setTelefonoMovilAsociacion(String telefonoMovilAsociacion);

	public Byte[] getFotografia();

	public void setFotografia(Byte[] fotografia);

	public String getUserName();

	public void setUserName(String userName);

	public String getPassword();

	public void setPassword(String password);

	public Boolean getActivo();

	public void setActivo(Boolean activo);

	public Set<Proyecto> getProyectosParticipados();

	public void setProyectosParticipados(Set<Proyecto> proyectosParticipados);

	public Set<Tarea> getTareas();

	public void setTareas(Set<Tarea> tareas);

	public Set<Incidencia> getIncidencias();

	public void setIncidencias(Set<Incidencia> incidencias);

}
