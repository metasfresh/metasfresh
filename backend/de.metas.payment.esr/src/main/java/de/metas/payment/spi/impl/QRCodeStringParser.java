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

import com.google.common.base.Splitter;
import de.metas.banking.payment.impl.PaymentString;
import de.metas.banking.payment.spi.IPaymentStringParser;
import de.metas.util.Check;
import lombok.NonNull;

import java.util.List;
import java.util.Properties;

public class QRCodeStringParser implements IPaymentStringParser
{
	public static final String TYPE = "QRCodeStringParser";

	private static final Splitter SPLITTER = Splitter.on("\n");

	@Override
	public PaymentString parse(final Properties ctx_NOTUSED, @NonNull final String paymentText)
	{
		return parse(paymentText);
	}

	public PaymentString parse(@NonNull final String qrCode)
	{
		final List<String> lines = SPLITTER.splitToList(qrCode);

		Check.assumeEquals(lines.get(0), "SPC"); // QR Type
		Check.assumeEquals(lines.get(1), "0200"); // Version
		Check.assumeEquals(lines.get(2), "1"); // Coding
		final String iban = lines.get(3); // Account // line 03: COALESCE(replace(qr_iban, ' ', ''), replace(iban, ' ', ''), '') || E'\n' || -- Account
		Check.assumeEquals(lines.get(4), "K"); //  AdressTyp = Combined address
		// line 05: orgbp.name || E'\n' || --CR – Name
		// line 06: orgl.address1 || E'\n' || --CR –Street and building number of P.O. Box
		// line 07: coalesce(orgl.postal, '') || ' ' || coalesce(orgl.city, '') || E'\n' || -- CR Postal code and town
		// line 08: E'\n' || --Do not fill in
		// line 09: E'\n' || --Do not fill in
		// line 10: orgc.countrycode || E'\n' || -- CR Country
		// line 11: E'\n' || -- E'\n' || --UCR – AdressTyp
		// line 12: E'\n' || -- E'\n' || --UCR – Name
		// line 13: E'\n' || -- E'\n' || --UCR - Street or address line 1
		// line 14: E'\n' || -- E'\n' || --UCR – Building number or address line 2
		// line 15: E'\n' || -- E'\n' || --UCR – Postal code
		// line 16: E'\n' || -- E'\n' || --UCR – City
		// line 17: E'\n' || -- E'\n' || --UCR – Country
		// line 18: i.GrandTotal || E'\n' ||
		// line 19: cur.iso_code || E'\n' ||
		// line 20: 'K' || E'\n' || -- UD– AdressTyp = Combined address
		// line 21: substring(bpartneraddress from 1 for position(E'\n' in bpartneraddress)) || --UD – Name
		// line 22: coalesce(l.address1, '') || E'\n' || --UD –Street and building number of P.O. Box
		// line 23: coalesce(l.postal, '') || ' ' || coalesce(l.city, '') || E'\n' || -- UD Postal code and town
		// line 24: E'\n' || --Do not fill in
		// line 25: E'\n' || --Do not fill in
		// line 26: c.countrycode || E'\n' || -- UD Country
		// line 27: (case
		// 				when replace(qr_iban, ' ', '') is not null and rn.referenceNo is not null then 'QRR'
		// 				when replace(iban, ' ', '') is not null and replace(orgbpb.sepa_creditoridentifier, ' ', '') is not null then 'SCOR'
		//				else 'NON'
		//			end) || E'\n' || --ReferenceType
		// line 28: (case
		//				when replace(qr_iban, ' ', '') is not null and rn.referenceNo is not null then rn.ReferenceNo
		//				when replace(iban, ' ', '') is not null and replace(orgbpb.sepa_creditoridentifier, ' ', '') is not null then 'RF' || orgbpb.sepa_creditoridentifier
		//				else ''
		//			end) || E'\n' || --Reference
		// line 29: coalesce(i.description,'') || E'\n' ||--Unstructured message
		// line 30: 'EPD' || E'\n' || --Trailer
		//
		// line 31: '' || E'\n' --Billing information

		// TODO implement
		throw new UnsupportedOperationException();
	}
}
