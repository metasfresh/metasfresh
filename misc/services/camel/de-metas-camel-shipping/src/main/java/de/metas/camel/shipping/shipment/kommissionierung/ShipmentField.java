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

package de.metas.camel.shipping.shipment.kommissionierung;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ShipmentField
{
	ARTICLE_NAME("_artikel_bezeichnung"),
	ARTICLE_FLAVOR("_artikel_geschmacksrichtung"),
	ARTICLE_WEIGHT("_artikel_gewicht_1_stueck"),
	ARTICLE_NOTE("_artikel_hinweistext"),
	ARTICLE_IS_STOCKED("_artikel_ist_lagerhaltig"),
	DELIVERED_QTY("_artikel_menge"),
	PRODUCT_VALUE_CURRENTLY_NOT_USED("_artikel_nummer"),
	ARTICLE_VALUE_TEMP("_artikel_nummer_gesellchen"),
	DELIVERED_NETAMT("_artikel_preis"),
	ARTICLE_PACKAGE_SIZE("_artikel_verpackungsgroesse"),
	ORDERED_QTY("_artikel_webshop_menge"),
	OPRDERED_NETAMT("_artikel_webshop_preis"),
	ORDERED_QTY_OPEN("_artikel_webshop_restmenge_noch_offen"),
	BEST_BEFORE_DATE("_aussendungen_mhd_ablauf_datum"),
	LOT_NUMBER("_aussendungen_mhd_charge"),
	DOCUMENT_NO("_aussendungen_siro_warenkorb_nummer"),
	ORDER_DATE("_bestellung_datum"),
	ORDER_ID("_bestellung_id"),
	ORDER_NO("_bestellung_nummer"),
	SHIPMENT_SCHEDULE_ID("_bestellung_position_id"),
	ORDER_LINE_COUNT("_bestellung_positionszahl"),
	ORDER_POREFERENCE("_bestellung_referenz"),
	ORDER_TIMESTAMP("_bestellung_zeitstempel"),
	ARTICLE_COUNTRY("_fuer_zoll_artikel_herkunftsland"),
	ARTICLE_CUSTOMS_NUMER("_fuer_zoll_artikel_intrastat_nummer"),
	PICKING_COUNTER("_kommissionierung_counter"),
	PICKING_DATE("_kommissionierung_datum"),
	PICKING_ID("_kommissionierung_id"),
	PICKING_SORTINGKEY("_kommissionierung_sortierer"),
	PICKING_TIMESTAMP("_kommissionierung_zeitstempel"),
	DELIVERED_QTY_OVERRIDE("_korrektur_artikel_menge"),
	DELIVERED_NETAMT_OVERRIDE("_korrektur_artikel_preis"),

	//inventory specific:,
	OUT_OF_STOCK_LOCATOR("_korrektur_lagerplatz_out_of_stock"),
	IS_OUT_OF_STOCK("_korrektur_wenn_out_of_stock"),

	PP_ORDER_ID("_stueckliste_id"),
	ARTICLE_WAS_PRODUCED("_vorkonfektioniert_ja_nein");

	private final String name;
}
