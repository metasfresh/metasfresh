/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.location;

import de.metas.i18n.AdMessageKey;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Scanner;

@EqualsAndHashCode
@Getter
public class AddressDisplaySequence
{
	public static final AddressDisplaySequence EMPTY = new AddressDisplaySequence("");
	private static final AdMessageKey MSG_AddressBuilder_WrongDisplaySequence = AdMessageKey.of("MSG_AddressBuilder_WrongDisplaySequence");

	@NonNull private final String pattern;

	private AddressDisplaySequence(@NonNull final String pattern)
	{
		this.pattern = pattern;
	}

	@NonNull
	public static AddressDisplaySequence ofNullable(@Nullable final String pattern)
	{
		return pattern != null && !Check.isBlank(pattern)
				? new AddressDisplaySequence(pattern)
				: EMPTY;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return pattern;
	}

	public void assertValid()
	{
		final boolean existsBPName = hasToken(Addressvars.BPartnerName);
		final boolean existsBP = hasToken(Addressvars.BPartner);
		final boolean existsBPGreeting = hasToken(Addressvars.BPartnerGreeting);

		if ((existsBP && existsBPName) || (existsBP && existsBPGreeting))
		{
			throw new AdempiereException(MSG_AddressBuilder_WrongDisplaySequence)
					//.appendParametersToMessage()
					.setParameter("displaySequence", this);
		}
	}

	public boolean hasToken(@NonNull final Addressvars token)
	{
		// TODO: optimize it! this is just some crap refactored code

		final Scanner scan = new Scanner(pattern);
		scan.useDelimiter("@");
		while (scan.hasNext())
		{
			if (scan.next().equals(token.getName()))
			{
				scan.close();
				return true;
			}
		}

		scan.close();
		return false;
	}

}
