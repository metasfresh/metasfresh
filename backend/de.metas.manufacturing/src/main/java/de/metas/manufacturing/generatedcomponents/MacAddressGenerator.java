/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.manufacturing.generatedcomponents;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoParts;
import de.metas.macaddress.MacAddress;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import static de.metas.macaddress.MacAddress.GROUP_DELIMITER;

/**
 * A MAC Address looks like this: 01:23:45:67:89:AB
 */
@Service
public class MacAddressGenerator implements IComponentGenerator
{
	public static final int NUMBER_OF_DIGITS = 12;

	@NonNull
	public MacAddress generateNextMacAddress(@NonNull final DocSequenceId macAddressSequenceId)
	{
		final IDocumentNoBuilder sequenceGenerator = Services.get(IDocumentNoBuilderFactory.class).forSequenceId(macAddressSequenceId)
				.setClientId(Env.getClientId())
				.setFailOnError(true);

		final DocumentNoParts documentNoParts = sequenceGenerator.buildParts();

		if (documentNoParts == null)
		{
			throw new AdempiereException("Could not retrieve next sequence number.");
		}

		return generateNextMacAddress0(documentNoParts);
	}

	public ImmutableList<MacAddress> generateNextMacAddresses(@NonNull final DocSequenceId macAddressSequenceId, final int numberOfResults)
	{
		final ImmutableList.Builder<MacAddress> result = ImmutableList.builder();

		for (int i = 0; i < numberOfResults; i++)
		{
			result.add(generateNextMacAddress(macAddressSequenceId));
		}
		return result.build();
	}

	@VisibleForTesting
	@NonNull
	MacAddress generateNextMacAddress0(@NonNull final DocumentNoParts documentNoParts)
	{
		final String prefix = StringUtils.nullToEmpty(StringUtils.replace(documentNoParts.getPrefix(), GROUP_DELIMITER, ""));
		final String sequenceNo;

		{
			final String sequenceNumberHexa;

			try
			{
				final int unsignedInt = Integer.parseUnsignedInt(documentNoParts.getSequenceNumber());
				sequenceNumberHexa = Integer.toHexString(unsignedInt);
			}
			catch (final NumberFormatException e)
			{
				throw new AdempiereException("Wrong sequence format");
			}

			final int prefixLen = prefix.length();
			final int neededDigits = NUMBER_OF_DIGITS - prefixLen;
			sequenceNo = Strings.padStart(sequenceNumberHexa, neededDigits, '0');
		}

		final StringBuilder result = new StringBuilder(prefix + sequenceNo);
		{
			final int initialStep = 2;
			int i = initialStep;
			while (i < result.length())
			{
				result.insert(i, GROUP_DELIMITER);
				i += initialStep + GROUP_DELIMITER.length();
			}
		}

		return MacAddress.of(result.toString().toUpperCase());
	}
}
