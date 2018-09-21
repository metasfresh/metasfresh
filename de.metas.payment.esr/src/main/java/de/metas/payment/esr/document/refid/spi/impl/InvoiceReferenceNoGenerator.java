package de.metas.payment.esr.document.refid.spi.impl;

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


import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.document.refid.spi.IReferenceNoGenerator;
import de.metas.logging.LogManager;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IBPBankAccountBL;
import de.metas.payment.esr.api.IESRBL;
import de.metas.payment.esr.api.IESRBPBankAccountDAO;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Creates following invoice reference number: <br/>
 * <code>AAAAAA OOO BBBBBBBB NNNNNNNNN C</code><br/>
 * where:
 * <ul>
 * <li>AAAAAA - Organization's ESR account number (6 characters)
 * <li>OOO - Organization code (3 characters)
 * <li>BBBBBBBB - Business Partner's code (8 characters)
 * <li>NNNNNNNNN - Invoice Document Number (9 characters)
 * <li>C - ESR checksum digit (1 character); see {@link IESRImportBL#calculateESRCheckDigit(String)}
 * <li>NOTE: generated code is 27 characters long
 * <li>NOTE: generated reference number contains NO space; in this description spaces were added for readability
 * </ul>
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/02553:_ESR_Zahlschein_Verarbeitung_%282012030810000028%29#Suggestion_for_Invoice_reference_numbers
 */
public class InvoiceReferenceNoGenerator implements IReferenceNoGenerator
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public String generateReferenceNo(Object sourceModel)
	{
		// Do nothing if ESR is not enabled
		final Properties ctx = InterfaceWrapperHelper.getCtx(sourceModel);
		
		if (!ESRConstants.isEnabled(ctx))
		{
			logger.debug("Skip generating because ESR not enabled");
			return REFERENCENO_None;
		}
		
		if(!Services.get(IESRBL.class).appliesForESRDocumentRefId(sourceModel))
		{
			logger.debug("Skip generating because source does not apply: " + sourceModel);
			return REFERENCENO_None;
		}

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(sourceModel, I_C_Invoice.class);
				
		final StringBuilder sb = new StringBuilder();

		final I_AD_Org org = invoice.getAD_Org();

		final I_C_BPartner bPartner = invoice.getC_BPartner();

		final IESRBPBankAccountDAO bpBankAccountDAO = Services.get(IESRBPBankAccountDAO.class);
		final List<I_C_BP_BankAccount> bankAccounts = bpBankAccountDAO.fetchOrgEsrAccounts(org);
		
		Check.assume(!bankAccounts.isEmpty(), "No ESR bank account found.");
		final I_C_BP_BankAccount bankAccount = bankAccounts.get(0);

		final IBPBankAccountBL bpBankAccountBL = Services.get(IBPBankAccountBL.class);
		sb.append(Util.rpadZero(bpBankAccountBL.retrieveBankAccountNo(bankAccount), 7, "BankAccountNo"));

		sb.append(Util.lpadZero(org.getValue(), 3, "organization"));
		
		final String bpartnerValue = Util.getDigits(bPartner.getValue()); // we can only use the digits
		sb.append(Util.lpadZero(bpartnerValue, 8, "business partner"));

		final String documentNo = invoice.getDocumentNo();
		sb.append(Util.lpadZero(documentNo, 8, "invoice document no"));

		final int checkDigit = Services.get(IESRImportBL.class).calculateESRCheckDigit(sb.toString());
		sb.append(checkDigit);

		return sb.toString();
	}

}
