package de.metas.currency;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

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

@AllArgsConstructor
public enum ConversionTypeMethod implements ReferenceListAwareEnum
{
	Spot("S"),
	PeriodEnd("P"),
	Average("A"),
	Company("C"),
	ForeignExchangeContract("F"),
	TaxReportingRate("T");


	private static final ReferenceListAwareEnums.ValuesIndex<ConversionTypeMethod> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	@NonNull
	public static ConversionTypeMethod ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@NonNull
	public static ConversionTypeMethod ofName(@NonNull final String name)
	{
		try
		{
			return ConversionTypeMethod.valueOf(name);
		}
		catch (final Throwable t)
		{
			throw new AdempiereException("No " + ConversionTypeMethod.class + " found for name: " + name)
					.appendParametersToMessage()
					.setParameter("AdditionalErrorMessage", t.getMessage());
		}
	}
}
