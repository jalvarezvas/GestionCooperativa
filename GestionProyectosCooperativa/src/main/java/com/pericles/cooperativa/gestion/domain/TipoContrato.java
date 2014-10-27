/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

/**
 * Enumeraci�n de contratos
 * 
 * @author Jacin
 */
public enum TipoContrato {
	I("Indefinido"), T("Temporal"), F("Formaci�n"), P("Pr�cticas");

	private String nombreContrato; // Almacena el nombre del contrato

	// El constructor fuerza a pasar par�metros al definir un nuevo tipo
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
