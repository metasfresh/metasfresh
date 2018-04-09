package de.metas.vertical.pharma.msv3.protocol.stockAvailability;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

/**
 * (Bedarf)
 * GH defect optimization flag indicating what need this item has in the context of the current availability request.
 * e.g:
 * direkt -> Apo definitely needs exactly this article
 * einsAusN -> Apo needs exactly one of the articles marked in this request
 * unspezifisch -> The Apo-Warenwirtschaft has no knowledge about the reason of the request.
 */
public enum RequirementType
{
	DIRECT("direkt"), STRICT_ONE_OF_SPECIFIED_IN_REQUEST("einsAusN"), NON_SPECIFIC("unspezifisch");

	@Getter
	private final String code;

	private RequirementType(final String code)
	{
		this.code = code;
	}

	public static RequirementType fromCode(@NonNull final String code)
	{
		final RequirementType type = code2type.get(code);
		if (type == null)
		{
			throw new NoSuchElementException("No " + RequirementType.class + " found for code '" + code + "'. Available types are: " + code2type.keySet());
		}
		return type;
	}

	private static final ImmutableMap<String, RequirementType> code2type = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(RequirementType::getCode, Function.identity()));
}
