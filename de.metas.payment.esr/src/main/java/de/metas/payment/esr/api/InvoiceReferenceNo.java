package de.metas.payment.esr.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.payment.esr
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@Builder
public class InvoiceReferenceNo
{
	String bankAccount;

	String org;

	/** Last 8 digits of the C_BPArtner_ID; lpadded with 0 */
	String bPartnerHint;

	/** Last 8 digits of the C_Invoice_ID; lpadded with 0 */
	String invoiceHint;

	int checkDigit;

	private InvoiceReferenceNo(
			@NonNull final String bankAccount,
			@NonNull final String org,
			@NonNull final String bPartnerHint,
			@NonNull final String invoiceHint,
			final int checkDigit)
	{
		this.bankAccount = bankAccount;
		this.org = org;
		this.bPartnerHint = bPartnerHint;
		this.invoiceHint = invoiceHint;
		this.checkDigit = checkDigit;
	}

	public String asString()
	{
		return new StringBuilder()
				.append(bankAccount)
				.append(org)
				.append(bPartnerHint)
				.append(invoiceHint)
				.append(checkDigit)
				.toString();
	}
}
