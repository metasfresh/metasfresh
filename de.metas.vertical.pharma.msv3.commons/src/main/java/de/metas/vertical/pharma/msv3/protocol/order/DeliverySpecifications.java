package de.metas.vertical.pharma.msv3.protocol.order;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItemPartType;
import lombok.Getter;

/*
 * #%L
 * metasfresh-pharma.msv3.commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

public enum DeliverySpecifications
{
	/** see {@link StockAvailabilityResponseItemPartType#NORMAL} */
	NORMAL(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Liefervorgabe.NORMAL,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Liefervorgabe.NORMAL),
	/** see {@link StockAvailabilityResponseItemPartType#IN_PARTNER_STORAGE} */
	MAX_IN_PARTNER_STORAGE(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Liefervorgabe.MAX_VERBUND,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Liefervorgabe.MAX_VERBUND),
	/** see {@link StockAvailabilityResponseItemPartType#SUBSEQUENT_DELIVERY} */
	MAX_SUBSEQUENT_DELIVERY(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Liefervorgabe.MAX_NACHLIEFERUNG,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Liefervorgabe.MAX_NACHLIEFERUNG),
	/** see {@link StockAvailabilityResponseItemPartType#DISPO} */
	MAX_DISPO(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Liefervorgabe.MAX_DISPO,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Liefervorgabe.MAX_DISPO)
	//
	;

	@Getter
	private final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Liefervorgabe v1SoapCode;
	@Getter
	private final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Liefervorgabe v2SoapCode;

	DeliverySpecifications(
			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Liefervorgabe v1SoapCode,
			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Liefervorgabe v2SoapCode)
	{
		this.v1SoapCode = v1SoapCode;
		this.v2SoapCode = v2SoapCode;
	}

	public String value()
	{
		return v2SoapCode.value();
	}

	public static DeliverySpecifications fromV1SoapCode(final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Liefervorgabe soapCode)
	{
		final DeliverySpecifications type = typesByValue.get(soapCode.value());
		if (type == null)
		{
			throw new NoSuchElementException("No " + DeliverySpecifications.class + " found for " + soapCode);
		}
		return type;
	}

	public static DeliverySpecifications fromV2SoapCode(final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Liefervorgabe soapCode)
	{
		final DeliverySpecifications type = typesByValue.get(soapCode.value());
		if (type == null)
		{
			throw new NoSuchElementException("No " + DeliverySpecifications.class + " found for " + soapCode);
		}
		return type;
	}

	private static final ImmutableMap<String, DeliverySpecifications> typesByValue = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(DeliverySpecifications::value, Function.identity()));

}
