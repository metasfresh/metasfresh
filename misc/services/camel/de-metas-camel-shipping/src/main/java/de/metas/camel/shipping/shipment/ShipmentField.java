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
	EXPIRY_DATE("_aussendungen_mhd_ablauf_datum"),
	ARTICLE_FLAVOR("_artikel_geschmacksrichtung"),
	DOCUMENT_NO("_aussendungen_siro_warenkorb_nummer"),
	TRACKING_NUMBERS("_sendung_nummern");

	private final String name;
}
