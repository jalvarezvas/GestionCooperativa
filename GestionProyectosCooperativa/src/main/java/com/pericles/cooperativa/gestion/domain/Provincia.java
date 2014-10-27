/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

/**
 * Enumeración de provincias españolas
 * 
 * @author Jacin
 */
public enum Provincia {
	ALAVA("Álava"), ALBACETE("Albacete"), ALICANTE("Alicante"), ALMERIA(
			"Almería"), ASTURIAS("Asturias"), BADAJOZ("Badajoz"), BALEARES(
			"Baleares"), BARCELONA("Barcelona"), BURGOS("Burgos"), CACERES(
			"Cáceres"), CADIZ("Cádiz"), CANTABRIA("Cantabria"), CASTELLON(
			"Castellón"), CEUTA("Ceuta"), CIUDAD_REAL("Ciudad Real"), CORDOBA(
			"Córdoba"), CUENCA("Cuenca"), GERONA("Gerona"), GRANADA("Granada"), GUADALAJARA(
			"Guadalajara"), GUIPUZCUA("Guipuzcua"), HUELVA("Huelva"), HUESCA(
			"Huesca"), JAEN("Jaén"), LA_CORUNIA("La Coruña"), LA_RIOJA(
			"La Rioja"), LAS_PALMAS("Las Palmas"), LEON("León"), LERIDA(
			"Lérida"), LUGO("Lugo"), MADRID("Madrid"), MALAGA("Málaga"), MURCIA(
			"Murcia"), MELILLA("Melilla"), NAVARRA("Navarra"), ORENSE("Orense"), PALENCIA(
			"Palencia"), PONTEVEDRA("Pontevedra"), SALAMANCA("Salamanca"), SANTA_CRUZ_DE_TENERIFE(
			"Santa Cruz de Tenerife"), SEGOVIA("Segovia"), SEVILLA("Sevilla"), TARRAGONA(
			"Tarragona"), TERUEL("Teruel"), TOLEDO("Toledo"), VALENCIA(
			"Valencia"), VALLADOLID("Valladolid"), VIZCAYA("Vizcaya"), ZAMORA(
			"Zamora"), ZARAGOZA("Zaragoza");

	private String nombreProvincia; // Almacena el nombre de la provincia

	// El constructor fuerza a pasar parámetros al definir un nuevo tipo
	Provincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	/**
	 * @return the nombreProvincia
	 */
	public String getNombreProvincia() {
		return nombreProvincia;
	}

}
