package de.metas.payment.sepa.model.validator;

/*
 * #%L
 * de.metas.payment.sepa
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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.payment.esr.api.IESRBPBankAccountBL;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.Check;
import de.metas.util.Services;

@Validator(I_SEPA_Export_Line.class)
public class SEPA_Export_Line
{
	/**
	 * If the given line's account is an ESR account, then this method sets the line's <code>OtherAccountIdentification</code> value to the ESR-account's retrieveESRAccountNo.
	 * 
	 * task 07789
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateOtherAccountIdentification(I_SEPA_Export_Line esrImport)
	{
		final I_C_BP_BankAccount bpBankAccount = InterfaceWrapperHelper.create(esrImport.getC_BP_BankAccount(), I_C_BP_BankAccount.class);
		final  String QR_IBAN = bpBankAccount.getQR_IBAN();

		if (!bpBankAccount.isEsrAccount())
		{
			return; // nothing to do if is not ESR or if is a QR account
		}

		if (QR_IBAN != null && QR_IBAN.length() > 0)
		{
			esrImport.setOtherAccountIdentification(""); // set nothing, but we need to make sure that tag is closed
			return;
		}

		final IESRBPBankAccountBL esrBankAccountBL = Services.get(IESRBPBankAccountBL.class);
		final IESRImportBL esrBL = Services.get(IESRImportBL.class);

		final String otherAccountIdentification;

		final String esrAccountNo = esrBankAccountBL.retrieveESRAccountNo(bpBankAccount);
		if (esrAccountNo.length() == 9)
		{
			// task 08341: the account should already contain a check digit, but we verify that it is actually the correct check digit.
			final int checkDigit = esrBL.calculateESRCheckDigit(esrAccountNo.substring(0, 8));

			final String checkDigitStr = Integer.toString(checkDigit);
			final String lastcharStr = esrAccountNo.substring(8);

			Check.errorUnless(checkDigitStr.equals(lastcharStr), "EsrAccountNo {} has 9 digits and should end with {}, but ends with {}; C_BP_BankAccount={}",
					esrAccountNo, checkDigitStr, lastcharStr, bpBankAccount);

			otherAccountIdentification = esrAccountNo;
		}
		else if (esrAccountNo.length() == 5)
		{
			// task 08341: idk that to do with a 5-digit  ESR-Teilnehmernummer, so i just let it be.
			// i just read about it in http://www.six-interbank-clearing.com/dam/downloads/de/standardization/iso/swiss-recommendations/implementation-guidelines-ct/standardization_isopayments_iso_20022_ch_implementation_guidelines_ct.pdf
			// but donT know anything else about it.
			otherAccountIdentification = esrAccountNo;
		}
		else
		{ // default: we prepend a check digit
			try
			{
				final int checkDigit = esrBL.calculateESRCheckDigit(esrAccountNo);
				otherAccountIdentification = esrAccountNo + checkDigit;
			}
			catch (final RuntimeException rte)
			{ // augment the exception and rethrow it
				throw AdempiereException.wrapIfNeeded(rte).appendParametersToMessage()
						.setParameter("C_BPartner_ID", bpBankAccount.getC_BPartner_ID())
						.setParameter("C_BP_BankAccount_ID",bpBankAccount.getC_BP_BankAccount_ID());
			}
		}

		esrImport.setOtherAccountIdentification(otherAccountIdentification);
	}
}
