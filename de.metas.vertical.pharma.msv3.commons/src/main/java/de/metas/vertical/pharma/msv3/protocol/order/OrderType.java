package de.metas.vertical.pharma.msv3.protocol.order;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;
import lombok.NonNull;

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

public enum OrderType
{
	NORMAL(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Auftragsart.NORMAL,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Auftragsart.NORMAL),
	//
	STACK(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Auftragsart.STAPEL,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Auftragsart.STAPEL),
	//
	SPECIAL(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Auftragsart.SONDER,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Auftragsart.SONDER),
	//
	SHIPPING(
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Auftragsart.VERSAND,
			de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Auftragsart.VERSAND)
	//
	;

	@Getter
	private final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Auftragsart v1SoapCode;
	@Getter
	private final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Auftragsart v2SoapCode;

	OrderType(
			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Auftragsart v1SoapCode,
			final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Auftragsart v2SoapCode)
	{
		this.v1SoapCode = v1SoapCode;
		this.v2SoapCode = v2SoapCode;
	}

	public String value()
	{
		return v2SoapCode.value();
	}

	public static OrderType fromV1SoapCode(@NonNull final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v1.Auftragsart soapCode)
	{
		final OrderType type = typesByValue.get(soapCode.value());
		if (type == null)
		{
			throw new NoSuchElementException("No " + OrderType.class + " found for " + soapCode);
		}
		return type;
	}

	public static OrderType fromV2SoapCode(@NonNull final de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Auftragsart soapCode)
	{
		final OrderType type = typesByValue.get(soapCode.value());
		if (type == null)
		{
			throw new NoSuchElementException("No " + OrderType.class + " found for " + soapCode);
		}
		return type;
	}

	private static final ImmutableMap<String, OrderType> typesByValue = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(OrderType::value, Function.identity()));
}
