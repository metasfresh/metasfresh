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

package de.metas.document.sequenceno;

import de.metas.common.util.time.SystemTime;
import de.metas.document.DocumentSequenceInfo;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Evaluatee;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.Date;

public class DateSequenceProvider implements CustomSequenceNoProvider
{
	private static final Logger logger = LogManager.getLogger(DateSequenceProvider.class);

	/**
	 * Global date format (a {@link java.text.SimpleDateFormat} pattern). Use {@code yy} for the year: {@code YY} is the week-based year.
	 */
	private static final String SYSCONFIG_DATE_FORMAT = "de.metas.document.seqNo.DateSequenceProvider.dateFormat";
	/**
	 * Optional per-sequence date format: {@value #SYSCONFIG_DATE_FORMAT} + {@code ".<AD_Sequence_ID>"}; falls back to {@value #SYSCONFIG_DATE_FORMAT}.
	 * Lets one sequence (e.g. a date-only production lot {@code yy-DDD}) differ from the others that use this provider.
	 */
	private static final String SYSCONFIG_DATE_FORMAT_PER_SEQUENCE_PREFIX = SYSCONFIG_DATE_FORMAT + ".";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	/**
	 * @return {@code true} if the given {@code context} has a non-null {@code Date} value.
	 */
	@Override
	public boolean isApplicable(@NonNull final Evaluatee context, @NonNull final DocumentSequenceInfo docSeqInfo)
	{
		final Date date = getDateOrNull(context, docSeqInfo);
		final boolean result = date != null;
		logger.debug("isApplicable - Given evaluatee-context contains {}={}; -> returning {}; context={}", docSeqInfo.getDateColumn(), date, result, context);

		return result;
	}

	/**
	 * @return the given prefix + {@code context}'s {@code Date} value.
	 */
	@Override
	public String provideSequenceNo(@NonNull final Evaluatee context, @NonNull final DocumentSequenceInfo docSeqInfo, @Nullable final String autoIncrementedSeqNumber)
	{
		final Date date = getDateOrNull(context, docSeqInfo);
		Check.assumeNotNull(date, "The given context needs to have a non-empty Date value; context={}", context);

		final String dateFormatToUse = getDateFormat(docSeqInfo);
		final String result = TimeUtil.formatDate(TimeUtil.asTimestamp(date), dateFormatToUse);

		if (Check.isNotBlank(autoIncrementedSeqNumber))
		{
			final String decimalPattern = docSeqInfo.getDecimalPattern();
			final boolean stringCanBeParsedAsInt = autoIncrementedSeqNumber.matches("\\d+");
			if (!Check.isEmpty(decimalPattern) && stringCanBeParsedAsInt)
			{
				final int seqNoAsInt = Integer.parseInt(autoIncrementedSeqNumber);
				final String formattedSeqNumber = new DecimalFormat(decimalPattern).format(seqNoAsInt);
				logger.debug("provideSequenceNo - returning {};", result);
				return result + formattedSeqNumber;
			}
		}

		logger.debug("provideSequenceNo - returning {};", result);
		return result;
	}

	/**
	 * @return {@code true} if the sequence has no decimal pattern, because only then {@link #provideSequenceNo(Evaluatee, DocumentSequenceInfo, String)} ignores the incremental number.
	 */
	@Override
	public boolean isSequenceNoIndependentOfIncrement(@NonNull final DocumentSequenceInfo docSeqInfo)
	{
		return Check.isEmpty(docSeqInfo.getDecimalPattern());
	}

	private String getDateFormat(@NonNull final DocumentSequenceInfo docSeqInfo)
	{
		final String perSequenceDateFormat = docSeqInfo.getAdSequenceId() > 0
				? sysConfigBL.getValue(SYSCONFIG_DATE_FORMAT_PER_SEQUENCE_PREFIX + docSeqInfo.getAdSequenceId())
				: null;
		if (Check.isNotBlank(perSequenceDateFormat))
		{
			return perSequenceDateFormat;
		}

		final String dateFormat = sysConfigBL.getValue(SYSCONFIG_DATE_FORMAT);
		Check.assumeNotEmpty(dateFormat, "{} sysconfig has not been defined", SYSCONFIG_DATE_FORMAT);
		return dateFormat;
	}

	@Nullable
	private static Date getDateOrNull(@NonNull final Evaluatee context, final @NonNull DocumentSequenceInfo docSeqInfo)
	{
		return context.get_ValueAsDate(docSeqInfo.getDateColumn(), SystemTime.asDate());
	}

}
