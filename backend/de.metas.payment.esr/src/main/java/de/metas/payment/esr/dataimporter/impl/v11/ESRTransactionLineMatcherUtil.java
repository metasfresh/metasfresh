package de.metas.payment.esr.dataimporter.impl.v11;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.compiere.util.Env;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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
@UtilityClass
public class ESRTransactionLineMatcherUtil
{
	public static final  AdMessageKey ERR_WRONG_REGULAR_LINE_LENGTH =  AdMessageKey.of("ESR_Wrong_Regular_Line_Length");

	public static final  AdMessageKey ERR_WRONG_NUMBER_FORMAT_AMOUNT =  AdMessageKey.of("ESR_Wrong_Number_Format_Amount");

	public static final  AdMessageKey ERR_WRONG_PAYMENT_DATE =  AdMessageKey.of("ESR_Wrong_Payment_Date");

	public static final  AdMessageKey ERR_WRONG_ACCOUNT_DATE =  AdMessageKey.of("ESR_Wrong_Account_Date");


	/**
	 * 
	 * @param dateStr date string in format yyMMdd
	 * @return
	 */
	public Date extractDateFromString(@NonNull final String dateStr) throws ParseException
	{
		final DateFormat df = new SimpleDateFormat("yyMMdd");
		final Date date = df.parse(dateStr);
		return date;
	}

	public String extractEsrTrxType(@NonNull final String v11LineStr)
	{
		// first 3 digits: transaction type
		final String trxType = v11LineStr.substring(0, 3);
		return trxType;
	}

	public boolean isControlLine(@NonNull final String v11LineStr)
	{
		final String trxType = extractEsrTrxType(v11LineStr);

		return ESRConstants.ESRTRXTYPE_Payment.equals(trxType)
				|| ESRConstants.ESRTRXTYPE_Receipt.equals(trxType);
	}

	public boolean isControlLine(@NonNull final I_ESR_ImportLine importLine)
	{
		final String trxType = importLine.getESRTrxType();

		return ESRConstants.ESRTRXTYPE_Payment.equals(trxType)
				|| ESRConstants.ESRTRXTYPE_Receipt.equals(trxType);
	}

	public boolean isCorrectTransactionLineLength(@NonNull final String v11LineStr)
	{
		if (v11LineStr.length() != 100)
		{
			Loggables.addLog(Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_WRONG_REGULAR_LINE_LENGTH, new Object[] { v11LineStr.length() }));
			return false;
		}
		return true;
	}

	/**
	 * If there is a problem extracting the amount, it logs an error message to {@link Loggables#get()}.
	 * 
	 * @param esrImportLineText
	 * @return
	 */
	public BigDecimal extractAmount(@NonNull final String esrImportLineText)
	{
		final String trxType = extractEsrTrxType(esrImportLineText);

		// amount
		// note: we parse the amount before the invoice line, because when setting the invoice to this line, we already want to use it
		{
			final String amountStringWithPosibleSpaces = esrImportLineText.substring(39, 49);
			final String amountString = replaceNonDigitCharsWithZero(amountStringWithPosibleSpaces); // 04551

			try
			{
				final BigDecimal amount = new BigDecimal(amountString)
						.divide(Env.ONEHUNDRED, 2, RoundingMode.UNNECESSARY);
				if (trxType.endsWith(ESRConstants.ESRTRXTYPE_REVERSE_LAST_DIGIT))
				{
					return amount.negate();
				}
				else
				{
					Check.assume(trxType.endsWith(ESRConstants.ESRTRXTYPE_CREDIT_MEMO_LAST_DIGIT) || trxType.endsWith(ESRConstants.ESRTRXTYPE_CORRECTION_LAST_DIGIT),
							"The file contains a line with unsupported transaction type '" + trxType + "'");
					return amount;
				}
				// Important: the imported amount doesn't need to match the invoices' amounts
			}
			catch (NumberFormatException e)
			{
				Loggables.addLog(
						Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_WRONG_NUMBER_FORMAT_AMOUNT,
								new Object[]
								{ amountString }));
				return BigDecimal.ZERO;
			}
		}
	}

	// 04551
	public String replaceNonDigitCharsWithZero(String amountStringWithPosibleSpaces)
	{
		final int size = amountStringWithPosibleSpaces.length();

		StringBuilder stringWithZeros = new StringBuilder();

		for (int i = 0; i < size; i++)
		{
			final char currentChar = amountStringWithPosibleSpaces.charAt(i);

			if (!Character.isDigit(currentChar))
			{
				stringWithZeros.append('0');
			}
			else
			{
				stringWithZeros.append(currentChar);
			}
		}

		return stringWithZeros.toString();
	}

	/**
	 * Only exxtracts the string. validation does not belong here
	 * 
	 * @param esrImportLineText
	 * @return
	 */
	public String extractPostAccountNo(@NonNull final String esrImportLineText)
	{
		final String postAccountNo = esrImportLineText.substring(3, 12);
		return postAccountNo;
	}

	public String extractReferenceNumberStr(@NonNull final String esrImportLineText)
	{
		final String completeEsrReferenceNumberStr = esrImportLineText.substring(12, 39);
		return completeEsrReferenceNumberStr;
	}

	public Date extractPaymentDate(@NonNull final String esrImportLineText)
	{
		final String paymentDateStr = esrImportLineText.substring(59, 65);
		try
		{
			return ESRTransactionLineMatcherUtil.extractDateFromString(paymentDateStr);
		}
		catch (ParseException e)
		{
			Loggables.addLog(Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_WRONG_PAYMENT_DATE, new Object[]
				{ paymentDateStr }));
			return null;
		}
	}

	public Date extractAccountingDate(@NonNull final String esrImportLineText)
	{
		final String accountDateStr = esrImportLineText.substring(65, 71);
		try
		{
			return ESRTransactionLineMatcherUtil.extractDateFromString(accountDateStr);
		}
		catch (ParseException e)
		{
			Loggables.addLog(Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_WRONG_ACCOUNT_DATE, new Object[] { accountDateStr }));
			return null;
		}
	}
}
