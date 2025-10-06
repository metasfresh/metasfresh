/*
 * #%L
 * de.metas.shipper.gateway.nshift
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JsonCustomsArticleDetailKind
{
	UNKNOWN(0),
	ARTICLE_NO(1),
	UNIT_VALUE(2),
	TARIFF_CODE(3),
	COUNTRY_OF_ORIGIN(4),
	QUANTITY(5),
	UNIT_WEIGHT(6),
	DESCRIPTION_OF_GOODS(7),
	UNIT_OF_MEASURE(8),
	TOTAL_WEIGHT(9),
	TOTAL_VALUE(10),
	CUSTOMS_VALUE(16),
	CUSTOMS_ARTICLE_CURRENCY(17),
	CUSTOMS_ARTICLE_COMMODITY_CODE(18),
	NUMBER_OF_PIECES(32),
	NETTO_WEIGHT(36),
	NUMBER_OF_ITEMS(180),
	PRODUCT_DESCRIPTION(184),
	PRODUCT_COMPOSITION(185),
	PRODUCT_CODE(186),
	PRODUCT_SIZE(187),
	PREFERENCE(188),
	PROCEDURE_CODE(189),
	SUPPLEMENTARY_UNIT(190),
	CERTIFICATES_CODE(191),
	CERTIFICATES(192),
	GOODS_LINE_NO(193),
	UNIT_VOLUME(505),
	GENDER(506),
	CONSTRUCTION(507),
	FABRIC_MARKED(508),
	FABRIC_MUTILATED(509),
	MANUFACTURER(510),
	PRODUCTION_ADDRESS(511),
	PRODUCTION_POSTAL_CODE(512),
	PRODUCTION_CITY(513),
	PURCHASE_URL(516),
	WEIGHT_UOM(517);

	@JsonValue
	private final int jsonValue;
}