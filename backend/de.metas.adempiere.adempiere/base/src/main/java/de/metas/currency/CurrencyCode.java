package de.metas.currency;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
 * Three letter ISO 4217 Code of the Currency
 */
@EqualsAndHashCode
public final class CurrencyCode implements Comparable<CurrencyCode>
{
	@JsonCreator
	public static CurrencyCode ofThreeLetterCode(@NonNull final String threeLetterCode)
	{
		return interner.intern(new CurrencyCode(threeLetterCode));
	}

	private static final Interner<CurrencyCode> interner = Interners.newStrongInterner();
	public static final CurrencyCode EUR = interner.intern(new CurrencyCode("EUR"));
	public static final CurrencyCode USD = interner.intern(new CurrencyCode("USD"));
	public static final CurrencyCode CHF = interner.intern(new CurrencyCode("CHF"));

	private final String threeLetterCode;

	private CurrencyCode(@NonNull final String threeLetterCode)
	{
		if (threeLetterCode.length() != 3)
		{
			throw new AdempiereException("Invalid currency ISO 4217 code: " + threeLetterCode);
		}

		this.threeLetterCode = threeLetterCode;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return threeLetterCode;
	}

	@JsonValue
	public String toThreeLetterCode()
	{
		return threeLetterCode;
	}

	public boolean isEuro()
	{
		return this.equals(EUR);
	}

	public boolean isCHF()
	{
		return this.equals(CHF);
	}

	@Override
	public int compareTo(@NonNull final CurrencyCode other) {return this.threeLetterCode.compareTo(other.threeLetterCode);}

	public static boolean equals(@Nullable CurrencyCode cc1, @Nullable CurrencyCode cc2) {return Objects.equals(cc1, cc2);}
}
