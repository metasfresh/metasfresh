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
	ORDER_NO("_bestellung_nummer"),
	SHIPMENT_SCHEDULE_ID("_bestellung_position_id"),
	PRODUCT_VALUE("_artikel_nummer"),
	DELIVERED_QTY("_artikel_menge"),
	STREET("_empfaenger_strasse"),
	HOUSE_NO("_empfaenger_hausnummer"),
	POSTAL_CODE("_empfaenger_plz"),
	CITY("_empfaenger_ort"),
	COUNTRY_CODE("_empfaenger_land"),
	DELIVERY_DATE("_kommissionierung_datum"),
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
