/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.document.sequenceno;

import de.metas.common.util.time.SystemTime;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Evaluatee;

import javax.annotation.Nullable;
import java.text.DecimalFormat;

public class ExternalProjectRefSequenceNoProvider implements CustomSequenceNoProvider
{
	@Override
	public boolean isApplicable(@NonNull final Evaluatee context)
	{
		return true;
	}

	@Override
	public String provideSequenceNo(@NonNull final Evaluatee context)
	{
		return String.valueOf(SystemTime.asLocalDate().getYear()).substring(2);
	}

	/** @return true */
	@Override
	public boolean isUseIncrementSeqNoAsSuffix()
	{
		return true;
	}

	/**
	 * ExternalProjectRefSequenceNoProvider requires {@code AD_Sequence}'s decimal pattern for incrementSeqNo format.
	 */
	@Override
	@NonNull
	public String appendIncrementSeqNoAsSuffix(
			@NonNull final String customSequenceNumber,
			@NonNull final String incrementalSequenceNumber,
			@Nullable final String decimalPattern)
	{
		if (Check.isBlank(decimalPattern))
		{
			throw new AdempiereException("ExternalProjectRefSequenceNoProvider requires that DecimalPattern be defined for incrementalSequenceNumber to be properly formatted!");
		}

		final int seqNoAsInt = Integer.parseInt(incrementalSequenceNumber);

		return customSequenceNumber + "-" + new DecimalFormat(decimalPattern).format(seqNoAsInt);
	}
}
