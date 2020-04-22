package de.metas.document.sequenceno;

import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class POReferenceAsSequenceNoProvider implements CustomSequenceNoProvider
{
	private static final Logger logger = LogManager.getLogger(POReferenceAsSequenceNoProvider.class);

	private static final String PARAM_POReference = "POReference";

	/** @return {@code true} if the given {@code context} has a non-null {@code POReference} value. */
	@Override
	public boolean isApplicable(@NonNull final Evaluatee context)
	{
		final String poReference = getPOReferenceOrNull(context);
		final boolean result = Check.isNotBlank(poReference);
		logger.debug("isApplicable - Given evaluatee-context contains {}={}; -> returning {}; context={}", PARAM_POReference, poReference, result, context);

		return result;
	}

	/** @return the given {@code context}'s {@code POReference} value. */
	@Override
	public String provideSequenceNo(@NonNull final Evaluatee context)
	{
		final String poReference = getPOReferenceOrNull(context);
		Check.assumeNotNull(poReference, "The given context needs to have a non-empty POreference value; context={}", context);

		logger.debug("provideSequenceNo - returning {};", poReference);
		return poReference;
	}

	private static String getPOReferenceOrNull(@NonNull final Evaluatee context)
	{
		String poReference = context.get_ValueAsString(PARAM_POReference);
		if (poReference == null)
		{
			return null;
		}

		poReference = poReference.trim();
		return !poReference.isEmpty() ? poReference : null;
	}

	/** @return true */
	@Override
	public boolean isUseIncrementSeqNoAsPrefix()
	{
		return true;
	}
}
