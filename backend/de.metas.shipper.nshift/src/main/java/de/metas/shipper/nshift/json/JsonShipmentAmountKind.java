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

/**
 * Specifies the type of amount associated with a shipment, such as insurance value or COD (Cash on Delivery) amount.
 * The values are based on the nShift API specification for ShipmentAmountKind.
 */
@RequiredArgsConstructor
@Getter
public enum JsonShipmentAmountKind
{
	/**
	 * Unknown or not specified.
	 */
	UNKNOWN(0),
	/**
	 * Price 1. The specific meaning may depend on the carrier.
	 */
	PRICE_1(1),
	/**
	 * Price 2. The specific meaning may depend on the carrier.
	 */
	PRICE_2(2),
	/**
	 * Price 3. The specific meaning may depend on the carrier.
	 */
	PRICE_3(3),
	/**
	 * The value of the goods for insurance purposes.
	 */
	INSURANCE_VALUE(4),
	/**
	 * The Cash on Delivery amount to be collected.
	 */
	COD_AMOUNT(5),
	/**
	 * A price specified by the carrier.
	 */
	CARRIER_SPECIFIED_PRICE(6),
	/**
	 * A COD amount specified by the carrier.
	 */
	CARRIER_SPECIFIED_COD_AMOUNT(7),
	/**
	 * The insurance amount on a per-package basis.
	 */
	INSURANCE_AMOUNT_PER_PACKAGE(8),
	/**
	 * A fixed price for the shipment.
	 */
	FIXED_PRICE(9),
	/**
	 * The total amount of the invoice.
	 */
	INVOICE_AMOUNT(10),
	/**
	 * The original COD amount before any adjustments.
	 */
	COD_AMOUNT_ORIGINAL(11),
	/**
	 * Insurance provided by a third party.
	 */
	THIRD_PARTY_INSURANCE(12);

	@JsonValue
	private final int jsonValue;
}