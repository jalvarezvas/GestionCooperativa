/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

/**
 * @author Jacin
 * 
 */
public interface Contacto {

	public String getIdentificacion();

	public void setIdentificacion(String identificacion);

	public String getCorreoElectronico();

	public void setCorreoElectronico(String correoElectronico);

	public String getTelefono();

	public void setTelefono(String telefono);

	public String getTelefonoMovil();

	public void setTelefonoMovil(String telefonoMovil);

	public Domicilio getDomicilio();

	public void setDomicilio(Domicilio domicilio);
}
