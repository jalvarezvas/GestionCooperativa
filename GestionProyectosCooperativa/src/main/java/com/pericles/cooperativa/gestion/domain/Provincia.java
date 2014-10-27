/**
 * 
 */
package com.pericles.cooperativa.gestion.domain;

/**
 * Enumeraci�n de provincias espa�olas
 * 
 * @author Jacin
 */
public enum Provincia {
	ALAVA("�lava"), ALBACETE("Albacete"), ALICANTE("Alicante"), ALMERIA(
			"Almer�a"), ASTURIAS("Asturias"), BADAJOZ("Badajoz"), BALEARES(
			"Baleares"), BARCELONA("Barcelona"), BURGOS("Burgos"), CACERES(
			"C�ceres"), CADIZ("C�diz"), CANTABRIA("Cantabria"), CASTELLON(
			"Castell�n"), CEUTA("Ceuta"), CIUDAD_REAL("Ciudad Real"), CORDOBA(
			"C�rdoba"), CUENCA("Cuenca"), GERONA("Gerona"), GRANADA("Granada"), GUADALAJARA(
			"Guadalajara"), GUIPUZCUA("Guipuzcua"), HUELVA("Huelva"), HUESCA(
			"Huesca"), JAEN("Ja�n"), LA_CORUNIA("La Coru�a"), LA_RIOJA(
			"La Rioja"), LAS_PALMAS("Las Palmas"), LEON("Le�n"), LERIDA(
			"L�rida"), LUGO("Lugo"), MADRID("Madrid"), MALAGA("M�laga"), MURCIA(
			"Murcia"), MELILLA("Melilla"), NAVARRA("Navarra"), ORENSE("Orense"), PALENCIA(
			"Palencia"), PONTEVEDRA("Pontevedra"), SALAMANCA("Salamanca"), SANTA_CRUZ_DE_TENERIFE(
			"Santa Cruz de Tenerife"), SEGOVIA("Segovia"), SEVILLA("Sevilla"), TARRAGONA(
			"Tarragona"), TERUEL("Teruel"), TOLEDO("Toledo"), VALENCIA(
			"Valencia"), VALLADOLID("Valladolid"), VIZCAYA("Vizcaya"), ZAMORA(
			"Zamora"), ZARAGOZA("Zaragoza");

	private String nombreProvincia; // Almacena el nombre de la provincia

	// El constructor fuerza a pasar par�metros al definir un nuevo tipo
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
