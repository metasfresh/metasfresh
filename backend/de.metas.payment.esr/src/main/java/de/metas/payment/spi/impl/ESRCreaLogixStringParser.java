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



import de.metas.banking.payment.IPaymentStringDataProvider;
import de.metas.banking.payment.PaymentString;
import de.metas.payment.api.impl.ESRPaymentStringDataProvider;
import de.metas.payment.esr.api.impl.ESRBPBankAccountDAO;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

/**
 * Using this name for lack of better inspiration to differentiate from the {@link ESRRegularLineParser}.<br>
 * Created for this logix scanner: {@link https://shop.crealogix.com/document-reader-scanner/clx-g130.html}
 *
 * @author al
 */
public final class ESRCreaLogixStringParser extends AbstractESRPaymentStringParser
{
	public static final transient ESRCreaLogixStringParser instance = new ESRCreaLogixStringParser();

	private ESRCreaLogixStringParser()
	{
		super();
	}

	/**
	 * Supports ESR strings of both length 55 and 44 (27 and 16 digit reference string).
	 *
	 * <ul>
	 * <li>First 3 digits: transaction type</li>
	 * <li>4-5 is doctype code</li>
	 * <li>6-13 is amount (in front of digit) - this one might be missing, shifting the numbers below</li>
	 * <li>14-15 is amount (after digit)</li>
	 * <li>16 is checksum</li>
	 * <li>18-44 is referenceno - might as well be 16 (not 27) digits long (uses for payment reference)</li>
	 * <li>44 is checksum</li>
	 * <li>47-54 is account (teilnehmernummer)</li>
	 * <li>55 is checksum</li>
	 * </ul>
	 * <p>
	 * Note to developer, should be kept in sync with {@link ESRBPBankAccountDAO#createMatchingESRAccountNumbers(java.lang.String)}
	 */
	@Override
	public PaymentString parse(@NonNull final String paymentTextOriginal) throws IndexOutOfBoundsException
	{
		String paymentText = paymentTextOriginal.replace(" ", ""); // replace all spaces with nothing

		// Validating String

		int firstGreaterSignIndex = paymentText.indexOf(">");
		int lastGreaterSignIndex = paymentText.lastIndexOf(">");

		// If there are not 2 and only 2 ">" signs, it means the ESR String is not correct
		if (firstGreaterSignIndex == -1
				|| firstGreaterSignIndex == lastGreaterSignIndex
				|| lastGreaterSignIndex != paymentText.length() - 1)
		{
			throwException();
		}

		String prefixString = paymentText.substring(0, firstGreaterSignIndex);

		//
		// Make sure that the first 3 chars for trxType are not missing
		if (prefixString.length() == 13 || prefixString.length() == 3)
		{
			prefixString = new StringBuilder("   ")
					.append(prefixString)
					.toString(); // prepend void trxType

			// also LPad the payment text
			paymentText = new StringBuilder("   ")
					.append(paymentText)
					.toString(); // prepend void trxType

			// don't forget to increase the indexes
			firstGreaterSignIndex += 3;
			lastGreaterSignIndex += 3;
		}

		final int prefixStringLentgh = prefixString.length();

		// Checking if the prefix string has one of the allowed lengths
		if (prefixStringLentgh != 16 && prefixStringLentgh != 6)
		{
			throwException();
		}

		final int plusSignIndex = paymentText.indexOf("+");

		// If there is no "+" sign, it means the ESR String is not correct
		if (plusSignIndex == -1)
		{
			throwException();
		}

		String referenceNumber = paymentText.substring(firstGreaterSignIndex + 1, plusSignIndex);

		final int referenceNumberLength = referenceNumber.length();

		final String postAccountNo = paymentText.substring(plusSignIndex + 1, lastGreaterSignIndex);

		// Checking if the suffix String has the correct length
		if (postAccountNo.length() != 9)
		{
			throwException();
		}

		final List<String> collectedErrors = new ArrayList<>();

		final String trxType = paymentText.substring(0, 3);

		final BigDecimal amount;

		if (prefixStringLentgh == 16)
		{
			final String amountString = new StringBuilder()
					.append(paymentText.substring(5, 13)) // natural amount
					.append(paymentText.substring(13, 15)) // decimal amount
					.toString();
			amount = extractAmountFromString(trxType, amountString, collectedErrors);
		}
		else
		{
			amount = null;
		}

		final String innerAccountNo = referenceNumber.substring(0, 7);
		if (referenceNumberLength != 27)
		{
			// add some leading 0, until we got length 27
			final String missingZeros = StringUtils.repeat("0", 27 - referenceNumberLength);
			referenceNumber = missingZeros + referenceNumber; //  see #4392
		}

		final Timestamp paymentDate = null;
		final Timestamp accountDate = null;

		final String orgValue = null; // esrReferenceNoComplete.substring(7, 10);

		final PaymentString paymentString = PaymentString.builder()
				.collectedErrors(collectedErrors)
				.rawPaymentString(paymentTextOriginal)
				.postAccountNo(postAccountNo)
				.innerAccountNo(innerAccountNo)
				.amount(amount)
				.referenceNoComplete(referenceNumber)
				.paymentDate(paymentDate)
				.accountDate(accountDate)
				.orgValue(orgValue)
				.build();

		final IPaymentStringDataProvider dataProvider = new ESRPaymentStringDataProvider(paymentString);
		paymentString.setDataProvider(dataProvider);

		return paymentString;
	}

	// Just made to avoid extra lines for throwing excecptions in the code
	private void throwException()
	{
		// mocking the old behavior; note that this exception is caught further up
		throw new IndexOutOfBoundsException();
	}
}
