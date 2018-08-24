package de.metas.vertical.pharma.msv3.protocol.order;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.vendor.gateway.msv3.schema.v2.Substitutionsgrund;
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

public enum OrderSubstitutionReason
{
	SUCCESSOR_PRODUCT(Substitutionsgrund.NACHFOLGEPRODUKT), //
	RE_AND_PARALLEL_IMPORT(Substitutionsgrund.RE_UND_PARALLEL_IMPORT), //
	PROPOSAL(Substitutionsgrund.VORSCHLAG) //
	;

	@Getter
	private final Substitutionsgrund v2SoapCode;

	OrderSubstitutionReason(final Substitutionsgrund v2SoapCode)
	{
		this.v2SoapCode = v2SoapCode;
	}

	public String value()
	{
		return v2SoapCode.value();
	}

	public static OrderSubstitutionReason fromV2SoapCode(final Substitutionsgrund v2SoapCode)
	{
		final OrderSubstitutionReason type = v2SoapCode2type.get(v2SoapCode);
		if (type == null)
		{
			throw new NoSuchElementException("No " + OrderSubstitutionReason.class + " found for " + v2SoapCode);
		}
		return type;
	}

	private static final ImmutableMap<Substitutionsgrund, OrderSubstitutionReason> v2SoapCode2type = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(OrderSubstitutionReason::getV2SoapCode, Function.identity()));

}
