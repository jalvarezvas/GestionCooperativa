/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

/**
 * Enumeración de contratos
 * 
 * @author Jacin
 */
public enum TipoContrato {
	I("Indefinido"), T("Temporal"), F("Formación"), P("Prácticas");

	private String nombreContrato; // Almacena el nombre del contrato

	// El constructor fuerza a pasar parámetros al definir un nuevo tipo
	TipoContrato(String nombreContrato) {
		this.nombreContrato = nombreContrato;
	}

	/**
	 * @return the nombreContrato
	 */
	public String getNombreContrato() {
		return nombreContrato;
	}
}
