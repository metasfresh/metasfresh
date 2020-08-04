package de.metas.payment.spi.impl;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2020 metas GmbH
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

import java.util.Properties;

import de.metas.banking.payment.IPaymentString;
import de.metas.banking.payment.IPaymentStringParserFactory;
import de.metas.banking.payment.spi.IPaymentStringParser;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Parse payment string for ESR or QR
 * 
 * PaymentType
 * 
 * first 3 chars of string
 * 
 * SPC -> QR
 * ??? -> ESR
 *
 * @author peter.wyss@kommunikativ.ch
 */
public final class ESRQRStringParser implements IPaymentStringParser
{
	public static final transient ESRQRStringParser instance = new ESRQRStringParser();

	public static final String TYPE = "ESRQRStringParser";

	private ESRQRStringParser()
	{
		super();
	}

	/**
	 * check payment type QR / ESR
	 *
	 */
	@Override
	public IPaymentString parse(final Properties ctx, final String paymentTextOriginal) throws IndexOutOfBoundsException
	{
		Check.assumeNotNull(paymentTextOriginal, "paymentText not null");

		String paymentText = paymentTextOriginal.trim(); // eliminates trailing and leading spaces

		// Checking if the prefix string has at least 3 chars
		if (paymentText.length() < 3)
		{
			throwException();
		}

		// Check PaymentString Type 
		String paymentStringType = paymentText.substring(0,3);
		
		switch(paymentStringType) {
			// QR == SPC
			case "SPC":
				return QRStringParser.instance.parse(ctx, paymentText);
			// ESR others
			default:
				return ESRCreaLogixStringParser.instance.parse(ctx, paymentText);
		} 
	}

	// Just made to avoid extra lines for throwing excecptions in the code
	private void throwException()
	{
		// mocking the old behavior; note that this exception is caught further up
		throw new IndexOutOfBoundsException();
	}
}
