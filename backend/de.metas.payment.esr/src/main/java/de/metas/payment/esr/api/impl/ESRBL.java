package de.metas.payment.esr.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Properties;

import de.metas.currency.Amount;
import de.metas.invoice.InvoiceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MOrg;
import org.compiere.util.Env;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.slf4j.Logger;

import de.metas.banking.model.I_C_Payment_Request;
import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.logging.LogManager;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRBPBankAccountBL;
import de.metas.payment.esr.api.IESRBL;
import de.metas.payment.esr.api.IESRBPBankAccountDAO;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.InvoiceReferenceNo;
import de.metas.payment.esr.api.InvoiceReferenceNos;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

public class ESRBL implements IESRBL
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public boolean appliesForESRDocumentRefId(final Object sourceModel)
	{
		final String sourceTableName = InterfaceWrapperHelper.getModelTableName(sourceModel);
		Check.assume(I_C_Invoice.Table_Name.equals(sourceTableName), "Document " + sourceModel + " not supported");

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(sourceModel, I_C_Invoice.class);

		if (!invoice.isSOTrx())
		{
			logger.debug("Skip generating because invoice is purchase invoice: {}", invoice);
			return false;
		}
		if (invoice.getReversal_ID() > 0)
		{
			logger.debug("Skip generating because invoice is a reversal: {}", invoice);
			return false;
		}
		if (Services.get(IInvoiceBL.class).isCreditMemo(invoice))
		{
			logger.debug("Skip generating because invoice is a credit memo: {}", invoice);
			return false;
		}
		return true;
	}

	@Override
	public void createESRPaymentRequest(@NonNull final I_C_Invoice invoiceRecord)
	{
		// Do nothing if ESR is not enabled
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceRecord);
		if (!ESRConstants.isEnabled(ctx))
		{
			logger.debug("Skip generating because ESR not enabled");
			return;
		}

		if (!appliesForESRDocumentRefId(invoiceRecord))
		{
			logger.debug("Skip generating because source does not apply: " + invoiceRecord);
			return;
		}

		final I_C_BP_BankAccount bankAccountRecord = retrieveEsrBankAccount(invoiceRecord);

		final InvoiceReferenceNo invoiceReferenceString = InvoiceReferenceNos.createFor(invoiceRecord, bankAccountRecord);

		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
		final Amount openInvoiceAmount = invoiceDAO.retrieveOpenAmt(InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID()));

		final String renderedCodeStr = createRenderedCodeString(invoiceReferenceString, openInvoiceAmount.toBigDecimal(), bankAccountRecord);

		// create payment request with invoiceRecord's Client and Org
		final I_C_Payment_Request paymentRequestRecord = newInstance(I_C_Payment_Request.class, invoiceRecord);
		paymentRequestRecord.setReference(invoiceReferenceString.asString());
		paymentRequestRecord.setFullPaymentString(renderedCodeStr);
		paymentRequestRecord.setC_BP_BankAccount(bankAccountRecord);
		paymentRequestRecord.setC_Invoice(invoiceRecord);
		paymentRequestRecord.setAmount(openInvoiceAmount.toBigDecimal());
		saveRecord(paymentRequestRecord);

		linkEsrStringsToInvoiceRecord(invoiceReferenceString, renderedCodeStr, invoiceRecord);
	}

	private I_C_BP_BankAccount retrieveEsrBankAccount(@NonNull final I_C_Invoice invoiceRecord)
	{
		// get the account number for the org of the invoice
		final int orgID = invoiceRecord.getAD_Org_ID();
		final I_AD_Org org = MOrg.get(Env.getCtx(), orgID);

		final IESRBPBankAccountDAO esrBankAccountDAO = Services.get(IESRBPBankAccountDAO.class);
		final List<I_C_BP_BankAccount> bankAccounts = esrBankAccountDAO.fetchOrgEsrAccounts(org);

		Check.assume(!bankAccounts.isEmpty(), "No ESR bank account found.");
		return bankAccounts.get(0);
	}

	/**
	 * Creates ESR Rendered code:<br/>
	 * <code>01SSSSSSSSSSC&gt;IIIIIIIIIIIIIIIIIIIIIIIIIII+ AAAAAA&gt;</code><br/>
	 * where:
	 * <ul>
	 * <li>01 - invoice document type code (2 characters)
	 * <li>SSSSSSSSSS - invoice amount, half round up, scale 0 (10 characters)
	 * <li>IIIIIIIIIIIIIIIIIIIIIIIIIII - invoice reference number, see {@link IESRBL#createESRPaymentRequest(I_C_Invoice)}
	 * <li>AAAAAA - Organization's ESR account number
	 * </ul>
	 */
	private String createRenderedCodeString(
			@NonNull final InvoiceReferenceNo invoiceReferenceString,
			@NonNull final BigDecimal openInvoiceAmount,
			@NonNull final I_C_BP_BankAccount bankAccount)
	{
		final StringBuilder renderedCodeStr = new StringBuilder();

		// 01 is the code for Invoice document type. For the moment it's the only document type handled by the program
		renderedCodeStr.append("01");

		// Open amount of the invoice multiplied by 100
		String amountStr = openInvoiceAmount
				.multiply(Env.ONEHUNDRED)
				.setScale(0, RoundingMode.HALF_UP)
				.toString();
		amountStr = StringUtils.lpadZero(amountStr, 10, "Open amount");

		renderedCodeStr.append(amountStr);

		final int checkDigit = Services.get(IESRImportBL.class).calculateESRCheckDigit(renderedCodeStr.toString());
		renderedCodeStr.append(checkDigit);

		renderedCodeStr.append(">");

		final IESRBPBankAccountBL bankAccountBL = Services.get(IESRBPBankAccountBL.class);

		renderedCodeStr.append(invoiceReferenceString.asString());
		renderedCodeStr.append("+ ");
		renderedCodeStr.append(bankAccountBL.retrieveESRAccountNo(bankAccount));
		renderedCodeStr.append(">");

		return renderedCodeStr.toString();
	}

	private void linkEsrStringsToInvoiceRecord(
			@NonNull final InvoiceReferenceNo invoiceReferenceString,
			@NonNull final String renderedCodeStr,
			@NonNull final I_C_Invoice invoiceRecord)
	{
		final IContextAware contextAware = InterfaceWrapperHelper.getContextAware(invoiceRecord);

		final IReferenceNoDAO referenceNoDAO = Services.get(IReferenceNoDAO.class);
		final I_C_ReferenceNo_Type invoiceReferenceNoType = referenceNoDAO.retrieveRefNoTypeByName(ESRConstants.DOCUMENT_REFID_ReferenceNo_Type_InvoiceReferenceNumber);
		final I_C_ReferenceNo invoiceReferenceNo = referenceNoDAO.getCreateReferenceNo(invoiceReferenceNoType, invoiceReferenceString.asString(), contextAware);
		referenceNoDAO.getCreateReferenceNoDoc(invoiceReferenceNo, TableRecordReference.of(invoiceRecord));

		final I_C_ReferenceNo_Type renderedCodeReferenceNoType = referenceNoDAO.retrieveRefNoTypeByName(ESRConstants.DOCUMENT_REFID_ReferenceNo_Type_ReferenceNumber);
		final I_C_ReferenceNo renderedCodeReferenceNo = referenceNoDAO.getCreateReferenceNo(renderedCodeReferenceNoType, renderedCodeStr, contextAware);
		referenceNoDAO.getCreateReferenceNoDoc(renderedCodeReferenceNo, TableRecordReference.of(invoiceRecord));
	}
}
