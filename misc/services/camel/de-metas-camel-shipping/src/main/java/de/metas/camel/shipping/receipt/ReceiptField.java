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

package de.metas.camel.shipping.receipt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReceiptField
{
	EXTERNAL_ID("_wareneingang_nummer"),
	RECEIPT_SCHEDULE_ID("_anlieferung_position_id"),
	PRODUCT_SEARCH_KEY("_artikel_nummer"),
	MOVEMENT_QUANTITY("_artikel_menge"),
	MOVEMENT_DATE("_wareneingang_datum"),
	DATE_RECEIVED("_wareneingang_zeitstempel"),
	LOT_NUMBER("_wareneingang_mhd_charge"),
	EXPIRY_DATE("_wareneingang_mhd_ablauf_datum"),
	ARTICLE_FLAVOR("_artikel_geschmacksrichtung");

	private final String name;
}
