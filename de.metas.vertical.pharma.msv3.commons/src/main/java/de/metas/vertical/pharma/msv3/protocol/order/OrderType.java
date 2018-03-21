package de.metas.vertical.pharma.msv3.protocol.order;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.vendor.gateway.msv3.schema.Auftragsart;
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

public enum OrderType
{
	NORMAL(Auftragsart.NORMAL), //
	STACK(Auftragsart.STAPEL), //
	SPECIAL(Auftragsart.SONDER), //
	SHIPPING(Auftragsart.VERSAND) //
	;

	@Getter
	private final Auftragsart soapCode;

	OrderType(final Auftragsart soapCode)
	{
		this.soapCode = soapCode;
	}

	public static OrderType fromSoapCode(final Auftragsart soapCode)
	{
		final OrderType type = soapCode2type.get(soapCode);
		if (type == null)
		{
			throw new NoSuchElementException("No " + OrderType.class + " found for " + soapCode);
		}
		return type;

	}

	private static final ImmutableMap<Auftragsart, OrderType> soapCode2type = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(OrderType::getSoapCode, Function.identity()));
}
