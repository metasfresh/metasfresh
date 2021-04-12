package de.metas.payment.spi.impl;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;

import de.metas.banking.payment.spi.IPaymentStringParser;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.payment.esr.ESRConstants;
import de.metas.util.Services;

public abstract class AbstractESRPaymentStringParser implements IPaymentStringParser
{
	protected static final AdMessageKey ERR_WRONG_PAYMENT_DATE = AdMessageKey.of("ESR_Wrong_Payment_Date");
	protected static final AdMessageKey ERR_WRONG_ACCOUNT_DATE = AdMessageKey.of("ESR_Wrong_Account_Date");

	private static final AdMessageKey ERR_WRONG_NUMBER_FORMAT_AMOUNT = AdMessageKey.of("ESR_Wrong_Number_Format_Amount");

	protected final BigDecimal extractAmountFromString(final Properties ctx, final String trxType, final String amountStringWithPosibleSpaces, final List<String> collectedErrors)
	{
		final String amountString = Util.replaceNonDigitCharsWithZero(amountStringWithPosibleSpaces); // 04551

		BigDecimal amount = BigDecimal.ZERO;
		try
		{
			amount = new BigDecimal(amountString)
					.divide(Env.ONEHUNDRED, 2, RoundingMode.UNNECESSARY);
			if (trxType.endsWith(ESRConstants.ESRTRXTYPE_REVERSE_LAST_DIGIT))
			{
				amount = amount.negate();
			}
			// Important: the imported amount doesn't need to match the invoices' amounts
		}
		catch (final NumberFormatException e)
		{
			final String wrongNumberFormatAmount = Services.get(IMsgBL.class).getMsg(ctx, ERR_WRONG_NUMBER_FORMAT_AMOUNT, new Object[] { amountString });
			collectedErrors.add(wrongNumberFormatAmount);
		}
		return amount;
	}

	/**
	 *
	 * @param dateStr date string in format yyMMdd
	 * @param collectedErrors
	 * @return
	 */
	protected final Timestamp extractTimestampFromString(final Properties ctx, final String dateStr, final AdMessageKey failMessage, final List<String> collectedErrors)
	{
		final DateFormat df = new SimpleDateFormat("yyMMdd");
		final Date date;
		try
		{
			date = df.parse(dateStr);
			return TimeUtil.asTimestamp(date);
		}
		catch (final ParseException e)
		{
			final String wrongDate = Services.get(IMsgBL.class).getMsg(ctx, failMessage, new Object[] { dateStr });
			collectedErrors.add(wrongDate);
		}
		return null;
	}
}
