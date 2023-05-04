package de.metas.document.sequenceno;
/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

import de.metas.common.util.time.SystemTime;
import de.metas.document.DocumentSequenceInfo;
import lombok.NonNull;
import org.compiere.util.Evaluatee;

import java.text.DecimalFormat;
import java.util.function.Supplier;

public abstract class AbstractExternalProjectRefSequenceNoProvider implements CustomSequenceNoProvider
{
	@Override
	public boolean isApplicable(@NonNull final Evaluatee context, @NonNull final DocumentSequenceInfo docSeqInfo)
	{
		return true;
	}

	@Override
	public @NonNull String provideSeqNo(
			@NonNull final Supplier<String> incrementalSeqNoSupplier,
			@NonNull final Evaluatee context,
			@NonNull final DocumentSequenceInfo documentSequenceInfo)
	{
		final String incrementalSeqNo = incrementalSeqNoSupplier.get();
		final String customPart = getCustomPart();

		final String decimalPattern = documentSequenceInfo.getDecimalPattern();
		if (decimalPattern == null)
		{
			return customPart + "-" + incrementalSeqNo;
		}

		final int incrementalSeqNoInt = Integer.parseInt(incrementalSeqNo);

		return customPart + "-" + new DecimalFormat(decimalPattern).format(incrementalSeqNoInt);
	}

	@NonNull
	protected static String getCustomPart()
	{
		return String.valueOf(SystemTime.asLocalDate().getYear())
				.substring(2);
	}
}
