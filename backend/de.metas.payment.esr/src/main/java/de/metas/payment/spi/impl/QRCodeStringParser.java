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

import de.metas.banking.payment.impl.PaymentString;
import de.metas.banking.payment.spi.IPaymentStringParser;

import java.util.Properties;


public class QRCodeStringParser implements IPaymentStringParser
{
	public static final String TYPE = "QRCodeStringParser";

	@Override
	public PaymentString parse(final Properties ctx, final String paymentText)
	{
		// line 01: 'SPC' || E'\n' || --QRType
		// line 02: '0200' || E'\n' || --Version
		// line 03: '1' || E'\n' || --Coding
		// line 04: COALESCE(replace(qr_iban, ' ', ''), replace(iban, ' ', ''), '') || E'\n' || -- Account
		// line 05: 'K' || E'\n' || -- CR - AdressTyp = Combined address
		// line 06: orgbp.name || E'\n' || --CR – Name
		// line 07: orgl.address1 || E'\n' || --CR –Street and building number of P.O. Box
		// line 08: coalesce(orgl.postal, '') || ' ' || coalesce(orgl.city, '') || E'\n' || -- CR Postal code and town
		// line 09: E'\n' || --Do not fill in
		// line 10: E'\n' || --Do not fill in
		// line 11: orgc.countrycode || E'\n' || -- CR Country
		// line 12: E'\n' || -- E'\n' || --UCR – AdressTyp
		// line 13: E'\n' || -- E'\n' || --UCR – Name
		// line 14: E'\n' || -- E'\n' || --UCR - Street or address line 1
		// line 15: E'\n' || -- E'\n' || --UCR – Building number or address line 2
		// line 16: E'\n' || -- E'\n' || --UCR – Postal code
		// line 17: E'\n' || -- E'\n' || --UCR – City
		// line 18: E'\n' || -- E'\n' || --UCR – Country
		// line 19: i.GrandTotal || E'\n' ||
		// line 20: cur.iso_code || E'\n' ||
		// line 21: 'K' || E'\n' || -- UD– AdressTyp = Combined address
		// line 22: substring(bpartneraddress from 1 for position(E'\n' in bpartneraddress)) || --UD – Name
		// line 23: coalesce(l.address1, '') || E'\n' || --UD –Street and building number of P.O. Box
		// line 24: coalesce(l.postal, '') || ' ' || coalesce(l.city, '') || E'\n' || -- UD Postal code and town
		// line 25: E'\n' || --Do not fill in
		// line 26: E'\n' || --Do not fill in
		// line 27: c.countrycode || E'\n' || -- UD Country
		// line 28: (case
		// 				when replace(qr_iban, ' ', '') is not null and rn.referenceNo is not null then 'QRR'
		// 				when replace(iban, ' ', '') is not null and replace(orgbpb.sepa_creditoridentifier, ' ', '') is not null then 'SCOR'
		//				else 'NON'
		//			end) || E'\n' || --ReferenceType
		// line 29: (case
		//				when replace(qr_iban, ' ', '') is not null and rn.referenceNo is not null then rn.ReferenceNo
		//				when replace(iban, ' ', '') is not null and replace(orgbpb.sepa_creditoridentifier, ' ', '') is not null then 'RF' || orgbpb.sepa_creditoridentifier
		//				else ''
		//			end) || E'\n' || --Reference
		// line 30: coalesce(i.description,'') || E'\n' ||--Unstructured message
		// line 31: 'EPD' || E'\n' || --Trailer
		//
		// line 32: '' || E'\n' --Billing information

		// TODO implement
		throw new UnsupportedOperationException();
	}
}
