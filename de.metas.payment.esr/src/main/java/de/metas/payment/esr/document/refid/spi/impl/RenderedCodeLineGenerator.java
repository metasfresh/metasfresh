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


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Properties;

import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MOrg;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.document.refid.api.IReferenceNoBL;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.api.IReferenceNoGeneratorInstance;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
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
 * Creates ESR Rendered code:<br/>
 * <code>01SSSSSSSSSSC&gt;IIIIIIIIIIIIIIIIIIIIIIIIIII+ AAAAAA&gt;</code><br/>
 * where:
 * <ul>
 * <li>01 - invoice document type code (2 characters)
 * <li>SSSSSSSSSS - invoice amount, half round up, scale 0 (10 characters)
 * <li>IIIIIIIIIIIIIIIIIIIIIIIIIII - invoice reference number, see {@link InvoiceReferenceNoGenerator}
 * <li>AAAAAA - Organization's ESR account number
 * </ul>
 * 
 * @author rc
 * @task http://dewiki908/mediawiki/index.php/03720_Provide_DB-Columns_for_ESR-Note_%282012121810000046%29
 */
public class RenderedCodeLineGenerator implements IReferenceNoGenerator
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

		// Generate invoice reference number
		final IReferenceNoDAO refNoDAO = Services.get(IReferenceNoDAO.class);
		final I_C_ReferenceNo_Type refNoType = refNoDAO.retrieveRefNoTypeByName(ctx, ESRConstants.DOCUMENT_REFID_ReferenceNo_Type_InvoiceReferenceNumber);
		final IReferenceNoGeneratorInstance instance = Services.get(IReferenceNoBL.class).getReferenceNoGeneratorInstance(ctx, refNoType);
		final String invoiceReferenceNoStr = instance.generateReferenceNo(sourceModel);
		Check.assume(IReferenceNoGenerator.REFERENCENO_None != invoiceReferenceNoStr, "Instance {} shall generate a reference number for source {}", instance, sourceModel);

		//
		final StringBuilder renderedCodeLine = new StringBuilder();

		// 01 is the code for Invoice document type. For the moment it's the only document type handled by the program
		renderedCodeLine.append("01");

		// Open amount of the invoice multiplied by 100
		BigDecimal amount = Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice);
		amount = amount.multiply(Env.ONEHUNDRED).setScale(0, RoundingMode.HALF_UP);
		String amountStr = amount.toString();
		amountStr = Util.lpadZero(amountStr, 10, "Open amount");

		renderedCodeLine.append(amountStr);

		final int checkDigit = Services.get(IESRImportBL.class).calculateESRCheckDigit(renderedCodeLine.toString());
		renderedCodeLine.append(checkDigit);

		renderedCodeLine.append(">");

		// get the account number for the org of the invoice
		final int orgID = invoice.getAD_Org_ID();
		final I_AD_Org org = MOrg.get(ctx, orgID);

		final IESRBPBankAccountDAO bpBankAccountDAO = Services.get(IESRBPBankAccountDAO.class);
		final List<I_C_BP_BankAccount> bankAccounts = bpBankAccountDAO.fetchOrgEsrAccounts(org);
		
		Check.assume(!bankAccounts.isEmpty(), "No ESR bank account found.");
		final I_C_BP_BankAccount bankAccount = bankAccounts.get(0);
		final IBPBankAccountBL bankAccountBL = Services.get(IBPBankAccountBL.class);

		renderedCodeLine.append(invoiceReferenceNoStr);
		renderedCodeLine.append("+ ");
		renderedCodeLine.append(bankAccountBL.retrieveESRAccountNo(bankAccount));
		renderedCodeLine.append(">");

		// done with rendered code line
		return renderedCodeLine.toString();
	}
}
