/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.acct;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode(doNotUseGetters = true)
public final class AccountConceptualName implements Comparable<AccountConceptualName>
{
	private final String name;
	private static final ConcurrentHashMap<String, AccountConceptualName> cache = new ConcurrentHashMap<>();

	public static final AccountConceptualName P_Asset_Acct = new AccountConceptualName("P_Asset_Acct");

	static
	{
		//noinspection ArraysAsListWithZeroOrOneArgument
		Arrays.asList(P_Asset_Acct)
				.forEach(acctConceptualName -> cache.put(acctConceptualName.getAsString(), acctConceptualName));
	}

	private AccountConceptualName(@NonNull final String name) {this.name = name;}

	@Nullable
	public static AccountConceptualName ofNullableString(@Nullable final String name)
	{
		final String nameNorm = StringUtils.trimBlankToNull(name);
		return nameNorm != null ? ofString(nameNorm) : null;
	}

	public static AccountConceptualName ofString(@NonNull final String name)
	{
		final String nameNorm = StringUtils.trimBlankToNull(name);
		if (nameNorm == null)
		{
			throw new AdempiereException("empty/null account conceptual name is not allowed");
		}

		return cache.computeIfAbsent(nameNorm, AccountConceptualName::new);
	}

	@Override
	public String toString() {return name;}

	public String getAsString() {return name;}

	@Override
	public int compareTo(@NonNull final AccountConceptualName other)
	{
		return this.name.compareTo(other.name);
	}

	public static boolean equals(@Nullable final AccountConceptualName o1, @Nullable final AccountConceptualName o2) {return Objects.equals(o1, o2);}

	public boolean isAnyOf(@Nullable final AccountConceptualName... names)
	{
		if (names == null)
		{
			return false;
		}

		for (final AccountConceptualName name : names)
		{
			if (name != null && equals(this, name))
			{
				return true;
			}
		}

		return false;
	}

	public boolean isProductMandatory()
	{
		return isAnyOf(P_Asset_Acct);
	}

	public boolean isWarehouseLocatorMandatory()
	{
		return isAnyOf(P_Asset_Acct);
	}

}
