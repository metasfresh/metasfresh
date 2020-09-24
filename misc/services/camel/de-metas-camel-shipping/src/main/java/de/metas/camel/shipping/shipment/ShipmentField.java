package de.metas.camel.shipping.shipment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShipmentField
{
	ORDER_NO("_bestellung_nummer"),
	SHIPMENT_SCHEDULE_ID("_bestellung_position_id"),
	PRODUCT_VALUE("_artikel_nummer"),
	DELIVERED_QTY("_artikel_menge"),
	STREET("_empfaenger_strasse"),
	HOUSE_NO("_empfaenger_hausnummer"),
	POSTAL_CODE("_empfaenger_plz"),
	CITY("_empfaenger_ort"),
	COUNTRY_CODE("_empfaenger_land"),
	DELIVERY_DATE("_aussendungen_zeitstempel"),
	LOT_NUMBER("_aussendungen_mhd_charge"),
	BEST_BEFORE_DATE("_aussendungen_mhd_ablauf_datum"),
	ARTICLE_FLAVOR("_artikel_geschmacksrichtung"),
	DOCUMENT_NO("_aussendungen_siro_warenkorb_nummer"),
	TRACKING_NUMBERS("_sendung_nummern"),
	SHIPPER_INTERNAL_NAME_SEG_1("_sendung_dienstleister"),
	SHIPPER_INTERNAL_NAME_SEG_2("_sendung_versandart"),
	PACKAGE_WEIGHT("_sendung_gewichte"),
	DELIVERED_QTY_OVERRIDE("_korrektur_artikel_menge"),

	//inventory specific:
	IS_OUT_OF_STOCK("_korrektur_wenn_out_of_stock"),
	OUT_OF_STOCK_LOCATOR("_korrektur_lagerplatz_out_of_stock")
	;

	private final String name;
}
