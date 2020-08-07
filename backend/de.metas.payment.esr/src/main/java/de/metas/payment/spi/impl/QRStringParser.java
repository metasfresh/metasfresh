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
import java.util.Scanner;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import de.metas.banking.payment.IPaymentString;
import de.metas.banking.payment.IPaymentStringDataProvider;
import de.metas.banking.payment.impl.PaymentString;
import de.metas.payment.api.impl.QRPaymentStringDataProvider;
import de.metas.util.Check;

/**
 *
 * @author peter.wyss@kommunikativ.ch
 */
public final class QRStringParser extends AbstractESRPaymentStringParser
{
	public static final transient QRStringParser instance = new QRStringParser();

	public static final String TYPE = "QRStringParser";

	private QRStringParser()
	{
		super();
	}

	/**
	 * Supports QR-Code.
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
		if (!paymentStringType.equals("SPC")) {
			throwException();
		}


		String QRType = null;
		String version = null;
		String codingType = null;
		String ibanAccountNo = null;

		String payeeAddressType = null;
		String payeeAddressName = null;
		String payeeAddressStreetLine1 = null;
		String payeeAddressStreetNoLine2 = null;
		String payeeAddressZIP = null;
		String payeeAddressCity = null;
		String payeeAddressCountry = null;

		String finaylPayeeAddressType = null;
		String finaylPayeeAddressName = null;
		String finaylPayeeAddressStreetLine1 = null;
		String finaylPayeeAddressStreetNoLine2 = null;
		String finaylPayeeAddressZIP = null;
		String finaylPayeeAddressCity = null;
		String finaylPayeeAddressCountry = null;
		BigDecimal amount = null;
		String currency = null;

		String payerAddressType = null;
		String payerAddressName = null;
		String payerAddressStreetLine1 = null;
		String payerAddressStreetNoLine2 = null;
		String payerAddressZIP = null;
		String payerAddressCity = null;
		String payerAddressCountry = null;

		String referenceType = null;
		String reference = null;
		String unstructuredMessage = null;
		String trailer = null;
		String invoiceInfo = null;
		String alternateMethod1 = null;
		String alternateMethod2 = null;
		
		Scanner paymentStringScanner = new Scanner(paymentText);
		int paymentStringIndex = 0;
		
		// process the line
		while (paymentStringScanner.hasNextLine()) {
		  String line = paymentStringScanner.nextLine();
		  ++paymentStringIndex;
		  switch(paymentStringIndex) {
		  	case 1:
		  		QRType = line.substring(0, 3);
		  		break;
		  	case 2:
		  		version = line.substring(0, 4);
		  		break;
		  	case 3:
		  		codingType = line.substring(0, 1);
		  		break;
		  	case 4:
		  		ibanAccountNo = line.substring(0, Math.min(line.length(),21));
		  		break;
		  	case 5:
		  		payeeAddressType = line.substring(0, Math.min(line.length(),1));
		  		break;
		  	case 6:
		  		payeeAddressName = line.substring(0, Math.min(line.length(),70));
		  		break;
		  	case 7:
		  		payeeAddressStreetLine1 = line.substring(0, Math.min(line.length(),70));
		  		break;
		  	case 8:
		  		payeeAddressStreetNoLine2 = line.substring(0, Math.min(line.length(),70));
		  		break;
		  	case 9:
		  		payeeAddressZIP = line.substring(0, Math.min(line.length(),16));
		  		break;
		  	case 10:
		  		payeeAddressCity = line.substring(0, Math.min(line.length(),35));
		  		break;
		  	case 11:
		  		payeeAddressCountry = line.substring(0, Math.min(line.length(),2));
		  		break;
		  	case 12:
		  		finaylPayeeAddressType = line.substring(0, Math.min(line.length(),1));
		  		break;
		  	case 13:
		  		finaylPayeeAddressName = line.substring(0, Math.min(line.length(),70));
		  		break;
		  	case 14:
		  		finaylPayeeAddressStreetLine1 = line.substring(0, Math.min(line.length(),70));
		  		break;
		  	case 15:
		  		finaylPayeeAddressStreetNoLine2 = line.substring(0, Math.min(line.length(),70));
		  		break;
		  	case 16:
		  		finaylPayeeAddressZIP = line.substring(0, Math.min(line.length(),16));
		  		break;
		  	case 17:
		  		finaylPayeeAddressCity = line.substring(0, Math.min(line.length(),35));
		  		break;
		  	case 18:
		  		finaylPayeeAddressCountry = line.substring(0, Math.min(line.length(),2));
		  		break;
		  	case 19:
		  		amount = new BigDecimal(line);
		  	case 20:
		  		currency = line.substring(0, Math.min(line.length(),3));
		  	case 21:
		  		payerAddressType = line.substring(0, Math.min(line.length(),1));
		  		break;
		  	case 22:
		  		payerAddressName = line.substring(0, Math.min(line.length(),70));
		  		break;
		  	case 23:
		  		payerAddressStreetLine1 = line.substring(0, Math.min(line.length(),70));
		  		break;
		  	case 24:
		  		payerAddressStreetNoLine2 = line.substring(0, Math.min(line.length(),70));
		  		break;
		  	case 25:
		  		payerAddressZIP = line.substring(0, Math.min(line.length(),16));
		  		break;
		  	case 26:
		  		payerAddressCity = line.substring(0, Math.min(line.length(),35));
		  		break;
		  	case 27:
		  		payerAddressCountry = line.substring(0, Math.min(line.length(),2));
		  		break;
		  	case 28:
		  		referenceType = line.substring(0, Math.min(line.length(),4));
		  		break;
		  	case 29:
		  		reference = line.substring(0, Math.min(line.length(),27));
		  		break;
		  	case 30:
		  		unstructuredMessage = line.substring(0, Math.min(line.length(),140));
		  		break;
		  	case 31:
		  		trailer = line.substring(0, Math.min(line.length(),3));
		  		break;
		  	case 32:
		  		invoiceInfo = line.substring(0, Math.min(line.length(),140));
		  		break;
		  	case 33:
		  		alternateMethod1 = line.substring(0, Math.min(line.length(),100));
		  		break;
		  	case 34:
		  		alternateMethod2 = line.substring(0, Math.min(line.length(),100));
		  		break;
		  }
		}
		paymentStringScanner.close();
		
		final List<String> collectedErrors = new ArrayList<>();

		final IPaymentString paymentString = new PaymentString(collectedErrors,
				paymentTextOriginal,
				true,
				amount,
				ibanAccountNo,
				referenceType == "QRR" ? true : false,
				reference,
				currency);

		final IPaymentStringDataProvider dataProvider = new QRPaymentStringDataProvider(paymentString);
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
