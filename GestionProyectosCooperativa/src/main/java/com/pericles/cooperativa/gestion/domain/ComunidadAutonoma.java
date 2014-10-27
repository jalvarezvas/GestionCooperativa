/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

/**
 * Enumeración de comunidades autónomas
 * 
 * @author Jacin
 */
public enum ComunidadAutonoma {

	ANDALUCIA("Andalucía"), ARAGON("Aragón"), CANTABRIA("Cantabria"), CASTILLA_Y_LEON(
			"Castilla y León"), CASTILLA_LA_MANCHA("Castilla-La Mancha"), CATALUÑA(
			"Cataluña"), CEUTA("Ciudad Autónoma de Ceuta"), MADRID(
			"Comunidad de Madrid"), VALENCIA("Comunidad Valenciana"), EXTREMADURA(
			"Extremadura"), GALICIA("Galicia"), ISLAS_BALEARES("Islas Baleares"), ISLAS_CANARIAS(
			"Islas Canarias"), LA_RIOJA("La Rioja"), MELILLA(
			"Ciudad Autónoma de Melilla"), NAVARRA("Comunidad Foral de Navarra"), PAIS_VASCO(
			"País Vasco"), ASTURIAS("Principado de Asturias"), MURCIA(
			"Región de Murcia");

	private String nombreComunidad; // Almacena el nombre de la comunidad

	// El constructor fuerza a pasar parámetros al definir un nuevo tipo
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
