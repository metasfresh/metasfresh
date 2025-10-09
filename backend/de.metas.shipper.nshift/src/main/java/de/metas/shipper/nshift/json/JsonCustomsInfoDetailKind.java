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

package de.metas.shipper.nshift.json;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JsonCustomsInfoDetailKind
{
	UNKNOWN(0),
	VAT_SENDER(11),
	VAT_RECEIVER(12),
	VAT_BUYER(13),
	OTHER_COMMENTS(14),
	INVOICE_NUMBER(19),
	REASON_FOR_EXPORT(20),
	CUSTOMS_INFO_CURRENCY(21),
	DECLARATION_STATEMENT(22),
	CUSTOMS_INFO_COMMODITY_CODE(23),
	INVOICE_DATE(24),
	TERMS_OF_SHIPMENT(25),
	DISCOUNT(26),
	FREIGHT_CHARGES(27),
	INSURANCE_CHARGES(28),
	CHARGES_VALUE(29),
	CHARGES_DESC(30),
	BOOKING_NUMBER(31),
	CONTENTS(33),
	GROSS_WEIGHT(34),
	TDOC_NO(35),
	VAT_BROKER(176),
	DESCRIPTION_OF_GOODS_2(177),
	SPECIAL_INSTRUCTIONS(178),
	CUSTOMS_CREDITOR_NO(179),
	TOTAL_VALUE_2(181),
	SHIPPERS_EORI(182),
	FDA_REGISTRATION(183),
	EXPORT_LICENSE(518),
	ORIGIN_CERTIFICATE_NUMBER(519),
	CUSTOMS_BULK_ID(520),
	INVOICE_SIGNATURE(521),
	INVOICE_SIGNATURE_DATE(522);

	@JsonValue
	private final int jsonValue;
}
