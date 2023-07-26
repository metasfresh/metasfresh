/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.payment.spi.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Splitter;

import de.metas.banking.payment.IPaymentStringDataProvider;
import de.metas.banking.payment.PaymentString;
import de.metas.payment.api.impl.QRPaymentStringDataProvider;
import de.metas.util.Check;
import lombok.NonNull;

public final class QRCodeStringParser extends AbstractESRPaymentStringParser
{
	private static final Splitter SPLITTER = Splitter.on("\n");

	@Override
	public PaymentString parse(@NonNull final String qrCode)
	{
		final List<String> lines = SPLITTER.splitToList(qrCode);
		
		Check.assumeEquals(lines.get(0), "SPC"); // QR Type
		Check.assumeEquals(lines.get(1), "0200"); // Version
		Check.assumeEquals(lines.get(2), "1"); // Coding
		
		final String iban = lines.get(3); 
		Check.assumeNotNull(lines.get(18), "invoice total not null");
		Check.assumeNotNull(lines.get(27), "code type not null");
		Check.assumeNotNull(lines.get(28), "reference not null");
		
		final String amountString =  lines.get(18);
		final String reference = lines.get(28);
		
		final Timestamp paymentDate = null;
		final Timestamp accountDate = null;
		
		final List<String> collectedErrors = new ArrayList<>();
		
		final BigDecimal amount = extractAmountFromString(amountString, collectedErrors);

		final PaymentString paymentString = PaymentString.builder()
				.collectedErrors(collectedErrors)
				.rawPaymentString(qrCode)
				.IBAN(iban)
				.amount(amount)
				.referenceNoComplete(reference)
				.paymentDate(paymentDate)
				.accountDate(accountDate)
				.build();

		final IPaymentStringDataProvider dataProvider = new QRPaymentStringDataProvider(paymentString);
		paymentString.setDataProvider(dataProvider);
	
		return paymentString;
	}

}
