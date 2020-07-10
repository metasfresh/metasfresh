package org.compiere.model;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.logging.LogManager;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
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

public class POUtils
{

	private static final Logger logger = LogManager.getLogger(POUtils.class);

	/**
	 * Removes trailing zero (after the decimal digit) if the given {@code value} is a {@link BigDecimal} with a space bigger than 15.
	 * <p>
	 * Note: does never truncate or round that number, i.e. the result will in every case be equal according to {@link BigDecimal#compareTo(BigDecimal)}.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/3914 Avoid numeric values with too many trailing zeros
	 */
	public static Object stripZerosAndLogIssueIfBigDecimalScaleTooBig(
			@Nullable final Object value,
			@NonNull PO po)
	{
		final boolean valueIsNotBigDecimal = !(value instanceof BigDecimal);
		if (valueIsNotBigDecimal)
		{
			return value; // nothing to do
		}

		final int maxAllowedScale = 15;
		final BigDecimal bdValue = (BigDecimal)value;
		if (bdValue.scale() <= maxAllowedScale)
		{
			return bdValue; // nothing to do
		}

		final BigDecimal bpWithoutTrailingZeroes = NumberUtils.stripTrailingDecimalZeros(bdValue);

		final String firstMessagePart = StringUtils.formatMessage(
				"The given value has scale={}; going to proceed with a stripped down value with scale={};",
				bdValue.scale(), bpWithoutTrailingZeroes.scale());
		final String lastMessagePart = StringUtils.formatMessage(" value={}; po={}", bdValue, po);

		final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(new AdempiereException(firstMessagePart + lastMessagePart));
		logger.warn(firstMessagePart + " created AD_Issue_ID={}" + lastMessagePart, issueId);

		return bpWithoutTrailingZeroes;
	}
}
