package de.metas.payment.esr.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import lombok.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MOrg;
import org.compiere.util.Env;
import org.compiere.util.Util;

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
import de.metas.logging.LogManager;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IBPBankAccountBL;
import de.metas.payment.esr.api.IESRBL;
import de.metas.payment.esr.api.IESRBPBankAccountDAO;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.document.refid.spi.impl.InvoiceReferenceNoGenerator;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.util.Check;
import de.metas.util.Services;

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

		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		final I_C_BP_BankAccount bankAccount = retrieveEsrBankAccount(invoiceRecord);

		final String invoiceReferenceNoStr = createInvoiceReferenceString(invoiceRecord, bankAccount);

		final BigDecimal openInvoiceAmount = invoiceDAO.retrieveOpenAmt(invoiceRecord);

		final String renderedCodeStr = createRenderedCodeString(invoiceReferenceNoStr, openInvoiceAmount, bankAccount);

		final I_C_Payment_Request paymentRequestRecord = newInstance(I_C_Payment_Request.class);
		paymentRequestRecord.setReference(invoiceReferenceNoStr);
		paymentRequestRecord.setFullPaymentString(renderedCodeStr.toString());
		paymentRequestRecord.setC_BP_BankAccount(bankAccount);
		paymentRequestRecord.setC_Invoice(invoiceRecord);
		paymentRequestRecord.setAmount(openInvoiceAmount);
		saveRecord(paymentRequestRecord);

		linkEsrStringsToInvoiceRecord(invoiceReferenceNoStr, renderedCodeStr, invoiceRecord);
	}

	private I_C_BP_BankAccount retrieveEsrBankAccount(@NonNull final I_C_Invoice invoiceRecord)
	{
		// get the account number for the org of the invoice
		final int orgID = invoiceRecord.getAD_Org_ID();
		final I_AD_Org org = MOrg.get(Env.getCtx(), orgID);

		final IESRBPBankAccountDAO bpBankAccountDAO = Services.get(IESRBPBankAccountDAO.class);
		final List<I_C_BP_BankAccount> bankAccounts = bpBankAccountDAO.fetchOrgEsrAccounts(org);

		Check.assume(!bankAccounts.isEmpty(), "No ESR bank account found.");
		final I_C_BP_BankAccount bankAccount = bankAccounts.get(0);
		return bankAccount;
	}

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
	private String createInvoiceReferenceString(
			@NonNull final I_C_Invoice invoiceRecord,
			final I_C_BP_BankAccount bankAccount)
	{
		final IESRImportBL esrImportBL = Services.get(IESRImportBL.class);

		final StringBuilder sb = new StringBuilder();

		final IBPBankAccountBL bpBankAccountBL = Services.get(IBPBankAccountBL.class);
		sb.append(Util.rpadZero(bpBankAccountBL.retrieveBankAccountNo(bankAccount), 7, "BankAccountNo"));

		final I_AD_Org org = invoiceRecord.getAD_Org();
		sb.append(Util.lpadZero(org.getValue(), 3, "organization"));

		final I_C_BPartner bPartner = invoiceRecord.getC_BPartner();
		final String bpartnerValue = Util.getDigits(bPartner.getValue()); // we can only use the digits
		sb.append(Util.lpadZero(bpartnerValue, 8, "business partner"));

		final String documentNo = invoiceRecord.getDocumentNo();
		sb.append(Util.lpadZero(documentNo, 8, "invoice document no"));

		final int checkDigit = esrImportBL.calculateESRCheckDigit(sb.toString());
		sb.append(checkDigit);

		return sb.toString();
	}

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
	private String createRenderedCodeString(
			@NonNull final String invoiceReferenceNoStr,
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
		amountStr = Util.lpadZero(amountStr, 10, "Open amount");

		renderedCodeStr.append(amountStr);

		final int checkDigit = Services.get(IESRImportBL.class).calculateESRCheckDigit(renderedCodeStr.toString());
		renderedCodeStr.append(checkDigit);

		renderedCodeStr.append(">");

		final IBPBankAccountBL bankAccountBL = Services.get(IBPBankAccountBL.class);

		renderedCodeStr.append(invoiceReferenceNoStr);
		renderedCodeStr.append("+ ");
		renderedCodeStr.append(bankAccountBL.retrieveESRAccountNo(bankAccount));
		renderedCodeStr.append(">");

		return renderedCodeStr.toString();
	}

	private void linkEsrStringsToInvoiceRecord(
			@NonNull final String invoiceReferenceNoStr,
			@NonNull final String renderedCodeStr,
			@NonNull final I_C_Invoice invoiceRecord)
	{
		final IReferenceNoDAO referenceNoDAO = Services.get(IReferenceNoDAO.class);

		final I_C_ReferenceNo_Type invoiceReferenceNoType = referenceNoDAO.retrieveRefNoTypeByName(Env.getCtx(), ESRConstants.DOCUMENT_REFID_ReferenceNo_Type_InvoiceReferenceNumber);
		final I_C_ReferenceNo invoiceReferenceNo = referenceNoDAO.getCreateReferenceNo(Env.getCtx(), invoiceReferenceNoType, invoiceReferenceNoStr, ITrx.TRXNAME_ThreadInherited);
		referenceNoDAO.getCreateReferenceNoDoc(invoiceReferenceNo, TableRecordReference.of(invoiceRecord));

		final I_C_ReferenceNo_Type renderedCodeReferenceNoType = referenceNoDAO.retrieveRefNoTypeByName(Env.getCtx(), ESRConstants.DOCUMENT_REFID_ReferenceNo_Type_ReferenceNumber);
		final I_C_ReferenceNo renderedCodeReferenceNo = referenceNoDAO.getCreateReferenceNo(Env.getCtx(), renderedCodeReferenceNoType, renderedCodeStr.toString(), ITrx.TRXNAME_ThreadInherited);
		referenceNoDAO.getCreateReferenceNoDoc(renderedCodeReferenceNo, TableRecordReference.of(invoiceRecord));
	}
}
