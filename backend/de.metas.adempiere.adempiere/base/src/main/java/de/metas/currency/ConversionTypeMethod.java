package de.metas.currency;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Getter
@AllArgsConstructor
public enum ConversionTypeMethod implements ReferenceListAwareEnum
{
	Spot("S", "Spot"),
	PeriodEnd("P", "Period End"),
	Average("A", "Average"),
	Company("C", "Company"),
	ForeignExchangeContract("F", "FEC");

	private static final ReferenceListAwareEnums.ValuesIndex<ConversionTypeMethod> index = ReferenceListAwareEnums.index(values());

	private static final ImmutableMap<String, ConversionTypeMethod> conversionTypesByName = Maps.uniqueIndex(Arrays.asList(values()), ConversionTypeMethod::getName);

	private final String code;

	private final String name;

	@NonNull
	public static ConversionTypeMethod ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@NonNull
	public static ConversionTypeMethod ofName(@NonNull final String codeOrName)
	{
		final ConversionTypeMethod conversionTypeMethod = conversionTypesByName.get(codeOrName);

		if (conversionTypeMethod == null)
		{
			throw new AdempiereException("No " + ConversionTypeMethod.class + " found for name: " + codeOrName);
		}

		return conversionTypeMethod;
	}
}
