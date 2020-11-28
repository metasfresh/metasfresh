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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import de.metas.banking.payment.IPaymentString;
import de.metas.banking.payment.IPaymentStringDataProvider;
import de.metas.banking.payment.impl.PaymentString;
import de.metas.payment.api.impl.ESRPaymentStringDataProvider;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.InvoiceReferenceNo;
import de.metas.payment.esr.api.InvoiceReferenceNos;
import de.metas.util.Check;

public final class ESRRegularLineParser extends AbstractESRPaymentStringParser
{
	public static final transient ESRRegularLineParser instance = new ESRRegularLineParser();

	public static final String TYPE = "ESRRegularLineParser";

	private ESRRegularLineParser()
	{
		super();
	}

	@Override
	public IPaymentString parse(final Properties ctx, final String paymentText) throws IndexOutOfBoundsException
	{
		Check.assumeNotNull(paymentText, "paymentText not null");

		final List<String> collectedErrors = new ArrayList<>();
		//
		// First 3 digits: transaction type
		final String trxType = paymentText.substring(0, 3);

		final String postAccountNo = paymentText.substring(3, 12);

		final String amountStringWithPosibleSpaces = paymentText.substring(39, 49);
		final BigDecimal amount = extractAmountFromString(ctx, trxType, amountStringWithPosibleSpaces, collectedErrors);
		if (!trxType.endsWith(ESRConstants.ESRTRXTYPE_REVERSE_LAST_DIGIT))
		{
			Check.assume(trxType.endsWith(ESRConstants.ESRTRXTYPE_CREDIT_MEMO_LAST_DIGIT) || trxType.endsWith(ESRConstants.ESRTRXTYPE_CORRECTION_LAST_DIGIT),
					"The file contains a line with unsupported transaction type '" + trxType + "'");
		}

		final String paymentDateStr = paymentText.substring(59, 65);
		final Timestamp paymentDate = extractTimestampFromString(ctx, paymentDateStr, ERR_WRONG_PAYMENT_DATE, collectedErrors);

		final String accountDateStr = paymentText.substring(65, 71);
		final Timestamp accountDate = extractTimestampFromString(ctx, accountDateStr, ERR_WRONG_ACCOUNT_DATE, collectedErrors);

		final String esrReferenceNoComplete = paymentText.substring(12, 39);
		final InvoiceReferenceNo invoiceReferenceNo = InvoiceReferenceNos.parse(esrReferenceNoComplete);

		final IPaymentString paymentString = new PaymentString(collectedErrors,
				paymentText, // FRESH-318
				postAccountNo,
				invoiceReferenceNo.getBankAccount(),
				amount,
				esrReferenceNoComplete,
				paymentDate,
				accountDate,
				invoiceReferenceNo.getOrg());

		final IPaymentStringDataProvider dataProvider = new ESRPaymentStringDataProvider(paymentString);
		paymentString.setDataProvider(dataProvider);

		return paymentString;
	}
}
