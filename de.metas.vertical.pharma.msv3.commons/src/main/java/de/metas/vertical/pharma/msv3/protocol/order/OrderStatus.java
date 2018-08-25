package de.metas.vertical.pharma.msv3.protocol.order;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

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

public enum OrderStatus
{
	UNKNOWN_ID(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Bestellstatus.KENNUNG_UNBEKANNT,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellstatus.KENNUNG_UNBEKANNT),
	//
	RESPONSE_NOT_AVAILABLE(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Bestellstatus.BESTELLANTWORT_NICHT_VERFUEGBAR,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellstatus.BESTELLANTWORT_NICHT_VERFUEGBAR),
	//
	RESPONSE_AVAILABLE(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Bestellstatus.BESTELLANTWORT_VERFUEGBAR,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellstatus.BESTELLANTWORT_VERFUEGBAR)
	//
	;

	@Getter
	private final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Bestellstatus v1SoapCode;
	@Getter
	private final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellstatus v2SoapCode;

	OrderStatus(
			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Bestellstatus v1SoapCode,
			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellstatus v2SoapCode)
	{
		this.v1SoapCode = v1SoapCode;
		this.v2SoapCode = v2SoapCode;
	}

	public String value()
	{
		return v2SoapCode.value();
	}

	public static OrderStatus fromV1SoapCode(final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Bestellstatus v1SoapCode)
	{
		final OrderStatus type = typesByValue.get(v1SoapCode.value());
		if (type == null)
		{
			throw new NoSuchElementException("No " + OrderStatus.class + " found for " + v1SoapCode);
		}
		return type;
	}

	public static OrderStatus fromV2SoapCode(final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Bestellstatus v2SoapCode)
	{
		final OrderStatus type = typesByValue.get(v2SoapCode.value());
		if (type == null)
		{
			throw new NoSuchElementException("No " + OrderStatus.class + " found for " + v2SoapCode);
		}
		return type;
	}

	private static final ImmutableMap<String, OrderStatus> typesByValue = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(OrderStatus::value, Function.identity()));

}
