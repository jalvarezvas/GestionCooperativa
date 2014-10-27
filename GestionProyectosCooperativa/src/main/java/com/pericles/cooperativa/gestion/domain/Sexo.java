package com.pericles.cooperativa.gestion.domain;

/**
 * Enumeración de sexo
 * 
 * @author Jacin
 */
public enum Sexo {
	F("Femenino"), M("Masculino");

	private String nombreSexo; // Almacena el nombre del sexo

	// El constructor fuerza a pasar parámetros al definir un nuevo tipo
	Sexo(String nombreSexo) {
		this.nombreSexo = nombreSexo;
	}

	/**
	 * @return the nombreSexo
	 */
	public String getNombreSexo() {
		return nombreSexo;
	}
}
