package de.metas.shipper.gateway.derkurier.misc;


import org.apache.commons.lang3.StringUtils;
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;

import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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

public class ParcelNumberGenerator
{
	public static final int NO_AD_SEQUENCE_ID_FOR_TESTING = -99;

	private final IDocumentNoBuilder documentNoBuilder;

	@VisibleForTesting
	@Getter(value = AccessLevel.PACKAGE)
	private final int adSequenceId;

	/** for unit testing only */
	@VisibleForTesting
	ParcelNumberGenerator()
	{
		this(NO_AD_SEQUENCE_ID_FOR_TESTING);
	}

	public ParcelNumberGenerator(final int parcelNumberAdSequenceId)
	{
		this.adSequenceId = parcelNumberAdSequenceId;

		final DocumentSequenceInfo documentSeqInfo = Services.get(IDocumentSequenceDAO.class)
				.retriveDocumentSequenceInfo(parcelNumberAdSequenceId);

		this.documentNoBuilder = Services.get(IDocumentNoBuilderFactory.class)
				.createDocumentNoBuilder()
				.setClientId(Env.getClientId())
				.setDocumentSequenceInfo(documentSeqInfo)
				.setFailOnError(true);
	}

	public String getNextParcelNumber()
	{
		final String parcelNumberWithoutCheckDigit = documentNoBuilder.build();
		return computeAndAppendCheckDigit(parcelNumberWithoutCheckDigit);
	}

	@VisibleForTesting
	String computeAndAppendCheckDigit(@NonNull final String parcelNumberWithoutCheckDigit)
	{
		// See #3991;
		Check.assumeNotEmpty(parcelNumberWithoutCheckDigit, "Parcel Number is empty");
		Check.assume(StringUtils.isNumeric(parcelNumberWithoutCheckDigit), "Parcel Number must only contain digits but it is: " + parcelNumberWithoutCheckDigit);

		final int checkDigit = computeCheckDigit(parcelNumberWithoutCheckDigit);

		return parcelNumberWithoutCheckDigit + checkDigit;
	}

	private int computeCheckDigit(@NonNull final String parcelNumberWithoutCheckDigit)
	{
		int sumOdd = 0;
		int sumEven = 0;

		for (int i = 0; i < parcelNumberWithoutCheckDigit.length(); i++)
		{
			// odd
			if (i % 2 == 0)
			{
				sumOdd += Integer.parseInt(Character.toString(parcelNumberWithoutCheckDigit.charAt(i)));
			}

			else
			{
				sumEven += Integer.parseInt(Character.toString(parcelNumberWithoutCheckDigit.charAt(i)));
			}
		}

		int result = 3 * sumOdd + sumEven;

		result = (10 - result % 10) % 10;

		return result;
	}
}
