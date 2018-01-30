package de.metas.costing;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Stream;

import org.compiere.model.X_M_CostElement;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;

/*
 * #%L
 * de.metas.business
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

public enum CostElementType
{
	Material(X_M_CostElement.COSTELEMENTTYPE_Material), //
	Overhead(X_M_CostElement.COSTELEMENTTYPE_Overhead), //
	BurdenMOverhead(X_M_CostElement.COSTELEMENTTYPE_BurdenMOverhead), //
	OutsideProcessing(X_M_CostElement.COSTELEMENTTYPE_OutsideProcessing), //
	Resource(X_M_CostElement.COSTELEMENTTYPE_Resource) //
	;

	// public static final int AD_REFERENCE_ID = X_M_CostElement.COSTELEMENTTYPE_AD_Reference_ID;

	@Getter
	private final String code;

	private CostElementType(final String code)
	{
		this.code = code;
	}

	public static CostElementType ofNullableCode(final String code)
	{
		if (code == null)
		{
			return null;
		}
		return ofCode(code);
	}

	public static CostElementType ofCode(final String code)
	{
		final CostElementType costingMethod = code2type.get(code);
		if (costingMethod == null)
		{
			throw new NoSuchElementException("No " + CostElementType.class + " found for code: " + code);
		}
		return costingMethod;
	}

	private static final ImmutableMap<String, CostElementType> code2type = Stream.of(values())
			.collect(ImmutableMap.toImmutableMap(CostElementType::getCode, Function.identity()));

}
