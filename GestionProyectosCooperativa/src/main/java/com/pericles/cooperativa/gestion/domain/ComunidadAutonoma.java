/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

/**
 * Enumeraci�n de comunidades aut�nomas
 * 
 * @author Jacin
 */
public enum ComunidadAutonoma {

	ANDALUCIA("Andaluc�a"), ARAGON("Arag�n"), CANTABRIA("Cantabria"), CASTILLA_Y_LEON(
			"Castilla y Le�n"), CASTILLA_LA_MANCHA("Castilla-La Mancha"), CATALU�A(
			"Catalu�a"), CEUTA("Ciudad Aut�noma de Ceuta"), MADRID(
			"Comunidad de Madrid"), VALENCIA("Comunidad Valenciana"), EXTREMADURA(
			"Extremadura"), GALICIA("Galicia"), ISLAS_BALEARES("Islas Baleares"), ISLAS_CANARIAS(
			"Islas Canarias"), LA_RIOJA("La Rioja"), MELILLA(
			"Ciudad Aut�noma de Melilla"), NAVARRA("Comunidad Foral de Navarra"), PAIS_VASCO(
			"Pa�s Vasco"), ASTURIAS("Principado de Asturias"), MURCIA(
			"Regi�n de Murcia");

	private String nombreComunidad; // Almacena el nombre de la comunidad

	// El constructor fuerza a pasar par�metros al definir un nuevo tipo
	ComunidadAutonoma(String nombreComunidad) {
		this.nombreComunidad = nombreComunidad;
	}

	/**
	 * @return the nombreComunidad
	 */
	public String getNombreComunidad() {
		return nombreComunidad;
	}
}
