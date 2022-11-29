/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import java.util.Scanner;

@UtilityClass
public class CountryDisplaySequenceHelper
{
	private static final AdMessageKey MSG_AddressBuilder_WrongDisplaySequence = AdMessageKey.of("MSG_AddressBuilder_WrongDisplaySequence");

	public static void assertValidDisplaySequence(@NonNull final String displaySequence)
	{
		final boolean existsBPName = isTokenFound(displaySequence, Addressvars.BPartnerName.getName());
		final boolean existsBP = isTokenFound(displaySequence, Addressvars.BPartner.getName());
		final boolean existsBPGReeting = isTokenFound(displaySequence, Addressvars.BPartnerGreeting.getName());

		if ((existsBP && existsBPName) || (existsBP && existsBPGReeting))
		{
			throw new AdempiereException(MSG_AddressBuilder_WrongDisplaySequence)
					.appendParametersToMessage()
					.setParameter("displaySequence", displaySequence);
		}
	}


	public static boolean isTokenFound(final @NonNull String sequenceToScan, final @NonNull String token)
	{
		final Scanner scan = new Scanner(sequenceToScan);
		scan.useDelimiter("@");
		while (scan.hasNext())
		{
			if (scan.next().equals(token))
			{
				scan.close();
				return true;
			}
		}

		scan.close();
		return false;
	}
}
