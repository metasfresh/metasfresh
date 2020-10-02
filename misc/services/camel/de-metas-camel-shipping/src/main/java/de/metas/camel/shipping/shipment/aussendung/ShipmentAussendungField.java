/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.camel.shipping.shipment.aussendung;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShipmentAussendungField
{
	/*NOTUSED_*/ARTICLE_NAME("_artikel_bezeichnung"),
	/*NOTUSED_*/ARTICLE_FLAVOR("_artikel_geschmacksrichtung"),
	/*NOTUSED_*/ARTICLE_WEIGHT("_artikel_gewicht_1_stueck"),
	/*NOTUSED_*/ARTICLE_NOTE("_artikel_hinweistext"),
	/*NOTUSED_*/DELIVERED_QTY("_artikel_menge"),
	/*NOTUSED_*/PRODUCT_VALUE("_artikel_nummer"),
	/*NOTUSED_*/ARTICLE_PACKAGE_SIZE("_artikel_verpackungsgroesse"),
	SHIPPING_COUNTER("_aussendung_counter"),
	SHIPPING_SORTINGKEY("_aussendung_sortierer"),
	SHIPPING_DATE("_aussendungen_datum"),
	SHIPPING_ID("_aussendungen_id"),
	/*NOTUSED_*/SHIPPING_YESNO("_aussendungen_ja"),
	/*NOTUSED_*/BEST_BEFORE_DATE("_aussendungen_mhd_ablauf_datum"),
	/*NOTUSED_*/LOT_NUMBER("_aussendungen_mhd_charge"),
	DOCUMENT_NO("_aussendungen_siro_warenkorb_nummer"),
	SHIPPING_TIMESTAMP("_aussendungen_zeitstempel"),
	ORDER_DATE("_bestellung_datum"),
	ORDER_ID("_bestellung_id"),
	ORDER_NO("_bestellung_nummer"),
	/*NOTUSED_*/SHIPMENT_SCHEDULE_ID("_bestellung_position_id"),
	ORDER_LINE_COUNT("_bestellung_positionszahl"),
	ORDER_TIMESTAMP("_bestellung_zeitstempel"),
	RECIPIENT_NAME("_empfaenger_ansprechpartner"),
	RECIPIENT_EMAIL("_empfaenger_email"),
	RECIPIENT_COMPANY("_empfaenger_firma"),
	RECIPIENT_HOUSE_NO("_empfaenger_hausnummer"),
	RECIPIENT_COUNTRY_CODE("_empfaenger_land"),
	RECIPIENT_CITY("_empfaenger_ort"),
	RECIPIENT_POSTAL_CODE("_empfaenger_plz"),
	RECIPIENT_STREET("_empfaenger_strasse"),
	RECIPIENT_PHONE("_empfaenger_telefon_muss_bei_express"),
	RECIPIENT_DELIVERYNOTE("_empfaenger_zustellinfo"),
	ARTICLE_COUNTRY("_fuer_zoll_artikel_herkunftsland"),
	ARTICLE_CUSTOMS_NUMER("_fuer_zoll_artikel_intrastat_nummer"),
	DELIVERED_QTY_OVERRIDE("_korrektur_artikel_menge"),
	DELIVERED_NETAMT_OVERRIDE("_korrektur_artikel_preis"),

	RECIPIENT_NAME_OVERRIDE("_korrektur_empfaenger_ansprechpartner"),
	RECIPIENT_EMAIL_OVERRIDE("_korrektur_empfaenger_email"),
	RECIPIENT_COMPANY_OVERRIDE("_korrektur_empfaenger_firma"),
	RECIPIENT_HOUSE_NO_OVERRIDE("_korrektur_empfaenger_hausnummer"),
	RECIPIENT_COUNTRY_CODE_OVERRIDE("_korrektur_empfaenger_land"),
	RECIPIENT_CITY_OVERRIDE("_korrektur_empfaenger_ort"),
	RECIPIENT_POSTAL_CODE_OVERRIDE("_korrektur_empfaenger_plz"),
	RECIPIENT_STREET_OVERRIDE("_korrektur_empfaenger_strasse"),
	RECIPIENT_PHONE_OVERRIDE("_korrektur_empfaenger_telefon_muss_bei_express"),
	RECIPIENT_DELIVERYNOTE_OVERRIDE("_korrektur_empfaenger_zustellinfo"),
	IS_OUT_OF_STOCK("_korrektur_wenn_out_of_stock"),

	SHIPPMENT_NAME("_sendung_bezeichnung"),
	SHIPPER_INTERNAL_NAME_SEG_1("_sendung_dienstleister"),

	SHIPPMENT_SINGLE_WEIGHT("_sendung_gewicht"),
	PACKAGE_WEIGHT("_sendung_gewichte"),
	SINGLE_TRACKING_NUMBER("_sendung_nummer"),
	TRACKING_NUMBERS("_sendung_nummern"),

	PACKAGE_COUNT("_sendung_paketanzahl"),
	SHIPPER_INTERNAL_NAME_SEG_2("_sendung_versandart"),
	SHIPPING_TPYE("_sendung_versandtyp"),
	PP_ORDER_ID("_stueckliste_id"),
	ARTICLE_WAS_PRODUCED("_vorkonfektioniert_ja_nein");

	private final String name;
}
