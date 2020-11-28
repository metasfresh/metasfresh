package de.metas.payment.esr.api;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;

import com.google.common.annotations.VisibleForTesting;

import de.metas.organization.IOrgDAO;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.StringUtils.TruncateAt;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class InvoiceReferenceNos
{
	/**
	 * Creates following invoice reference number: <br/>
	 * <code>AAAAAA OOO BBBBBBBB NNNNNNNNN C</code><br/>
	 * where:
	 * <ul>
	 * <li>AAAAAA - Organization's ESR account number (6 characters)
	 * <li>OOO - Organization code (3 characters)
	 * <li>BBBBBBBB - Business Partner's code (8 characters)
	 * <li>NNNNNNNN - C_Invoice_ID (8 characters)
	 * <li>C - ESR checksum digit (1 character); see {@link IESRImportBL#calculateESRCheckDigit(String)}
	 * <li>NOTE: generated code is 27 characters long
	 * <li>NOTE: generated reference number contains NO space; in this description spaces were added for readability
	 * </ul>
	 *
	 * @author tsa
	 * @task http://dewiki908/mediawiki/index.php/02553:_ESR_Zahlschein_Verarbeitung_%282012030810000028%29#Suggestion_for_Invoice_reference_numbers
	 */
	@VisibleForTesting
	public static InvoiceReferenceNo createFor(
			@NonNull final I_C_Invoice invoiceRecord,
			@NonNull final I_C_BP_BankAccount bankAccountRecord)
	{
		final IESRBPBankAccountBL esrBankAccountBL = Services.get(IESRBPBankAccountBL.class);
		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

		final String bankAccount = StringUtils.rpadZero(esrBankAccountBL.retrieveBankAccountNo(bankAccountRecord), 7, "BankAccountNo");

		final String orgValue = orgDAO.retrieveOrgValue(invoiceRecord.getAD_Org_ID());
		final String org = StringUtils.lpadZero(StringUtils.trunc(orgValue, 3, TruncateAt.STRING_START), 3, "organization");

		final String bpartnerId = Integer.toString(invoiceRecord.getC_BPartner_ID()); // we can only use the digits
		final String bPartner = StringUtils.lpadZero(StringUtils.trunc(bpartnerId, 8, TruncateAt.STRING_START), 8, "BusinessPartner");

		// note: we take the invoice-ID because the documentNo might contain non-digit characters
		final String invoicedId = Integer.toString(invoiceRecord.getC_Invoice_ID());
		final String invoice = StringUtils.lpadZero(StringUtils.trunc(invoicedId, 8, TruncateAt.STRING_START), 8, "C_Invoice_ID");

		final int checkDigit = computeCheckDigit(bankAccount, org, bPartner, invoice);

		return InvoiceReferenceNo.builder()
				.bankAccount(bankAccount)
				.org(org)
				.bPartnerHint(bPartner)
				.invoiceHint(invoice)
				.checkDigit(checkDigit)
				.build();
	}

	public static InvoiceReferenceNo parse(@NonNull final String referenceString)
	{
		final String bankAccount = referenceString.substring(0, 7);
		final String org = referenceString.substring(7, 10);
		final String bPartnerHint = removeLeftZeros(referenceString.substring(10, 18));
		final String invoiceHint = removeLeftZeros(referenceString.substring(18, 26));

		final int checkDigit = computeCheckDigit(bankAccount, org, bPartnerHint, invoiceHint);

		return InvoiceReferenceNo.builder()
				.bankAccount(bankAccount)
				.org(org)
				.bPartnerHint(bPartnerHint)
				.invoiceHint(invoiceHint)
				.checkDigit(checkDigit)
				.build();
	}

	/**
	 * Method to remove the left zeros from a string.
	 *
	 * @param value
	 * @return the initial String if it's made of only zeros; the string without the left zeros otherwise.
	 */
	private static final String removeLeftZeros(final String value)
	{
		final int size = value.length();
		int counter;
		for (counter = 0; counter < size; counter++)
		{
			if (value.charAt(counter) != '0')
			{
				break;
			}
		}
		if (counter == size)
		{
			return value;
		}
		else
		{
			return value.substring(counter, size);
		}
	}

	private static int computeCheckDigit(
			@NonNull final String bankAccount,
			@NonNull final String org,
			@NonNull final String bPartner,
			@NonNull final String invoice)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(bankAccount);
		sb.append(org);
		sb.append(bPartner);
		sb.append(invoice);

		try
		{
			final IESRImportBL esrImportBL = Services.get(IESRImportBL.class);
			final int checkDigit = esrImportBL.calculateESRCheckDigit(sb.toString());
			return checkDigit;
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("bankAccount", bankAccount)
					.setParameter("org", org)
					.setParameter("bPartner", bPartner)
					.setParameter("invoice", invoice);
		}
	}
}
