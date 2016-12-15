package de.metas.payment.esr.api.impl;

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
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.PeriodClosedException;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.acct.Doc;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.adempiere.service.IPeriodBL;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.exception.ESRParserException;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;

class ESRRegularLineMatcher extends AbstractESRLineMatcher
{

	private static final String ERR_ESR_DOES_NOT_BELONG_TO_INVOICE_2P = "de.metas.payment.esr.EsrDoesNotBelongToInvoice";

	private static final String ERR_NO_ESR_NO_FOUND_IN_DB_1P = "de.metas.payment.esr.NoEsrNoFoundInDB";

	public static final String ERR_WRONG_REGULAR_LINE_LENGTH = "ESR_Wrong_Regular_Line_Length";

	public static final String ERR_WRONG_POST_BANK_ACCOUNT = "ESR_Wrong_Post_Bank_Account";

	public static final String ERR_WRONG_NUMBER_FORMAT_AMOUNT = "ESR_Wrong_Number_Format_Amount";

	public static final String ERR_WRONG_PAYMENT_DATE = "ESR_Wrong_Payment_Date";

	public static final String ERR_WRONG_ACCOUNT_DATE = "ESR_Wrong_Account_Date";

	public static final String ERR_INVOICE_ALREADY_PAID = "ESR_Invoice_Already_Paid";

	public static final String ERR_UNFIT_BPARTNER_VALUES = "ESR_Unfit_BPartner_Values";

	public static final String ERR_UNFIT_DOCUMENT_NOS = "ESR_Unfit_DocumentNo";

	public static final String ESR_UNFIT_INVOICE_ORG = "ESR_Unfit_Invoice_Org";

	public static final String ESR_UNFIT_BPARTNER_ORG = "ESR_Unfit_BPartner_Org";

	/**
	 * Matches the given import line
	 */
	@Override
	public void match(final I_ESR_ImportLine importLine)
	{
		final String trxType = importLine.getESRTrxType();

		// make sure we are called with the correct type of line
		Check.assume(!ESRConstants.ESRTRXTYPE_Receipt.equals(trxType), "{} does not have ERS trx type {}", importLine, ESRConstants.ESRTRXTYPE_Receipt);
		Check.assume(!ESRConstants.ESRTRXTYPE_Payment.equals(trxType), "{} does not have ERS trx type {}", importLine, ESRConstants.ESRTRXTYPE_Payment);

		// Getting ctx with importLine's AD_Client_ID and AD_Org_ID because we want to retrieve the correct C_ReferenceNo_Doc further below
		final Properties localCtx = Env.deriveCtx(InterfaceWrapperHelper.getCtx(importLine, true));

		final IESRImportBL esrImportBL = Services.get(IESRImportBL.class);

		final String esrImportLineText = importLine.getESRLineText();

		if (esrImportLineText.length() != 100)
		{
			throw new ESRParserException(Services.get(IMsgBL.class).getMsg(localCtx, ERR_WRONG_REGULAR_LINE_LENGTH, new Object[] { esrImportLineText.length() }));
		}

		// next 9 digits: post participant number
		// the post account no should only contain the first 2 and the last 5 digits (the zeros at the positions 2 and 3
		// are ignored)
		{
			final String postAccountNo = esrImportLineText.substring(3, 12);
			importLine.setESRPostParticipantNumber(postAccountNo);

			// 04690: guarding against NPE (shouldn't happen, but i jsut encountered it)
			final I_C_BP_BankAccount bankAccount;
			if (importLine.getC_BP_BankAccount_ID() > 0)
			{
				bankAccount = InterfaceWrapperHelper.create(importLine.getC_BP_BankAccount(), I_C_BP_BankAccount.class);
			}
			else
			{
				bankAccount = InterfaceWrapperHelper.create(importLine.getESR_Import().getC_BP_BankAccount(), I_C_BP_BankAccount.class);
			}

			final String renderedPostAccountNo = bankAccount.getESR_RenderedAccountNo();
			String[] renderenNoComponents = renderedPostAccountNo.split("-");
			Check.assume(renderenNoComponents.length == 3, renderedPostAccountNo + " contains three '-' separated parts");

			final StringBuilder sb = new StringBuilder();
			sb.append(renderenNoComponents[0]);
			sb.append(Util.lpadZero(renderenNoComponents[1], 6, "middle section of " + renderedPostAccountNo));
			sb.append(renderenNoComponents[2]);
			final String unrenderedPostAccountNo = sb.toString();
			if (!unrenderedPostAccountNo.equals(postAccountNo))
			{
				esrImportBL.addErrorMsg(importLine, Services.get(IMsgBL.class).getMsg(localCtx, ERR_WRONG_POST_BANK_ACCOUNT,
						new Object[] { unrenderedPostAccountNo, postAccountNo }));
			}
		}

		// amount
		// note: we parse the amount before the invoice line, because when setting the invoice lie, we already want to use it
		{
			final String amountStringWithPosibleSpaces = esrImportLineText.substring(39, 49);
			final String amountString = replaceNonDigitCharsWithZero(amountStringWithPosibleSpaces); // 04551

			try
			{
				BigDecimal amount = new BigDecimal(amountString);
				amount = amount.divide(Env.ONEHUNDRED, 2, RoundingMode.UNNECESSARY);
				if (trxType.endsWith(ESRConstants.ESRTRXTYPE_REVERSE_LAST_DIGIT))
				{
					importLine.setAmount(amount.negate());
				}
				else
				{
					Check.assume(trxType.endsWith(ESRConstants.ESRTRXTYPE_CREDIT_MEMO_LAST_DIGIT) || trxType.endsWith(ESRConstants.ESRTRXTYPE_CORRECTION_LAST_DIGIT),
							"The file contains a line with unsupported transaction type '" + trxType + "'");
					importLine.setAmount(amount);
				}
				// Important: the imported amount doesn't need to match the invoices' amounts
			}
			catch (NumberFormatException e)
			{
				esrImportBL.addErrorMsg(importLine, Services.get(IMsgBL.class).getMsg(localCtx, ERR_WRONG_NUMBER_FORMAT_AMOUNT,
						new Object[] { amountString }));
			}
		}

		// The reference number of the ESR Import line
		final String completeEsrReferenceNumberStr = esrImportLineText.substring(12, 39);
		{
			// When matching, we will ignore the first 7 digits (the bank account no), and the last digit (check digit)
			final String esrReferenceNumberToMatch = completeEsrReferenceNumberStr.substring(7, 26);

			final IESRImportDAO esrImportPA = Services.get(IESRImportDAO.class);
			final I_C_ReferenceNo_Doc esrReferenceNumberDocument = esrImportPA.retrieveESRInvoiceReferenceNumberDocument(localCtx, esrReferenceNumberToMatch);

			if (esrReferenceNumberDocument == null)
			{
				esrImportBL.addErrorMsg(importLine,
						Services.get(IMsgBL.class).getMsg(localCtx, ERR_NO_ESR_NO_FOUND_IN_DB_1P, new Object[] { completeEsrReferenceNumberStr }));
			}
			else
			{
				final I_C_ReferenceNo currentReferenceNo = esrReferenceNumberDocument.getC_ReferenceNo();

				importLine.setESR_IsManual_ReferenceNo(currentReferenceNo.isManual());
				importLine.setESRReferenceNumber(esrReferenceNumberDocument.getC_ReferenceNo().getReferenceNo());

				// check if invoice
				final String tableName = Services.get(IADTableDAO.class).retrieveTableName(esrReferenceNumberDocument.getAD_Table_ID());
				if (I_C_Invoice.Table_Name.equalsIgnoreCase(tableName))
				{
					importLine.setC_ReferenceNo(esrReferenceNumberDocument.getC_ReferenceNo());

					final int invoiceID = esrReferenceNumberDocument.getRecord_ID();
					final I_C_Invoice invoice = InterfaceWrapperHelper.create(localCtx, invoiceID, I_C_Invoice.class, ITrx.TRXNAME_None);
					final I_C_BPartner invoicePartner = invoice.getC_BPartner();

					// check the org: should not match with invoices from other orgs
					if (invoice.getAD_Org_ID() != importLine.getAD_Org_ID())
					{
						Services.get(IESRImportBL.class).addErrorMsg(importLine,
								Services.get(IMsgBL.class).getMsg(localCtx, ESR_UNFIT_INVOICE_ORG));
					}
					// check the org: should not match with invoices which have the partner form other org
					else if (invoicePartner.getAD_Org_ID() > 0  // task 09852: a partner that has no org at all does not mean an inconsistency and is therefore OK
							&& invoicePartner.getAD_Org_ID() != importLine.getAD_Org_ID())
					{
						Services.get(IESRImportBL.class).addErrorMsg(importLine,
								Services.get(IMsgBL.class).getMsg(localCtx, ESR_UNFIT_BPARTNER_ORG));
					}
					else
					{
						Services.get(IESRImportBL.class).setInvoice(importLine, invoice);

						// If the retrieved I_C_ReferenceNo_Doc is manual, then don't try to parse anything from it, but take them from the invoice instead.
						if (importLine.isESR_IsManual_ReferenceNo())
						{
							setValuesFromInvoice(importLine, invoice);
						}

					}
				}
				else
				{
					esrImportBL.addErrorMsg(
							importLine,
							Services.get(IMsgBL.class).getMsg(localCtx, ERR_ESR_DOES_NOT_BELONG_TO_INVOICE_2P,
									new Object[] { completeEsrReferenceNumberStr, tableName }));
				}
			}
		}

		// Set the reference number components if the reference no is not manual
		if (!importLine.isESR_IsManual_ReferenceNo())
		{
			setValuesFromESRString(importLine);
		}

		// Payment Date
		{
			final String paymentDateStr = esrImportLineText.substring(59, 65);
			try
			{
				final Timestamp oldPaymentDate = importLine.getPaymentDate();

				// set only if not set before
				if (oldPaymentDate == null)
				{
					final Timestamp paymentDate = extractTimestampFromString(paymentDateStr);
					importLine.setPaymentDate(paymentDate);
				}

				// task 05917: check if the the payment date from the ESR file is OK for us
				try
				{
					Services.get(IPeriodBL.class).testPeriodOpen(localCtx, importLine.getPaymentDate(), Doc.DOCTYPE_APPayment, importLine.getAD_Org_ID());
				}
				catch (PeriodClosedException p)
				{
					esrImportBL.addErrorMsg(importLine, p.getLocalizedMessage());
				}

			}
			catch (ParseException e)
			{
				esrImportBL.addErrorMsg(importLine, Services.get(IMsgBL.class).getMsg(localCtx, ERR_WRONG_PAYMENT_DATE, new Object[] { paymentDateStr }));
			}
		}

		// Accounting Date
		{
			final String accountDateStr = esrImportLineText.substring(65, 71);
			try
			{
				if (importLine.getAccountingDate() == null)
				{
					final Timestamp accountingDate = extractTimestampFromString(accountDateStr);
					importLine.setAccountingDate(accountingDate);
				}
			}
			catch (ParseException e)
			{
				esrImportBL.addErrorMsg(importLine, Services.get(IMsgBL.class).getMsg(localCtx, ERR_WRONG_ACCOUNT_DATE, new Object[] { accountDateStr }));
			}
		}

		// Done with interesting data.

		//
		// Update IsValid flag
		final boolean isValid = Check.isEmpty(importLine.getErrorMsg());
		importLine.setIsValid(isValid);
		if (isValid)
		{
			importLine.setESR_Document_Status(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_TotallyMatched);
		}
	}

	// 04551
	private String replaceNonDigitCharsWithZero(String amountStringWithPosibleSpaces)
	{
		final int size = amountStringWithPosibleSpaces.length();

		StringBuilder stringWithZeros = new StringBuilder();

		for (int i = 0; i < size; i++)
		{
			final char currentChar = amountStringWithPosibleSpaces.charAt(i);

			if (!Character.isDigit(currentChar))
			{
				stringWithZeros.append('0');
			}
			else
			{
				stringWithZeros.append(currentChar);
			}
		}

		return stringWithZeros.toString();
	}

	private void setValuesFromInvoice(final I_ESR_ImportLine importLine, final I_C_Invoice invoice)
	{
		importLine.setC_BPartner_ID(invoice.getC_BPartner_ID()); // 04582: no need to load the whole bpartner when we just need the ID
		importLine.setOrg_ID(invoice.getAD_Org_ID());

		InterfaceWrapperHelper.save(importLine);
	}

	private void setValuesFromESRString(final I_ESR_ImportLine importLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(importLine);

		final String esrImportLineText = importLine.getESRLineText();
		// The reference number of the ESR Import line
		final String completeEsrReferenceNumberStr = esrImportLineText.substring(12, 39);

		// Organization value

		final String orgValue = completeEsrReferenceNumberStr.substring(7, 10);
		importLine.setSektionNo(orgValue);

		// Org ID
		final I_AD_Org organization = Services.get(IOrgDAO.class).retrieveOrganizationByValue(ctx, orgValue);
		if (organization != null)
		{
			importLine.setOrg(organization);
			importLine.setESR_Document_Status(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
		}

		// BPartner value (without initial zeros)
		final int bPartnerId;

		final String bpValue = removeLeftZeros(completeEsrReferenceNumberStr.substring(10, 18));

		// Get BPartner id
		// try to format the value
		final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);
		final String formattedBPValue = documentNoFactory.forTableName(I_C_BPartner.Table_Name, importLine.getAD_Client_ID(), importLine.getAD_Org_ID())
				.setSequenceNo(Integer.valueOf(bpValue))
				.setFailOnError(false)
				.build();

		I_C_BPartner bPartner = null;
		if (!Check.isEmpty(formattedBPValue, true))
		{
			bPartner = Services.get(IBPartnerDAO.class).retrieveBPartnerByValue(ctx, formattedBPValue);
		}

		importLine.setBPartner_Value(bpValue);

		if (bPartner != null)
		{
			// check organization
			// we should not allow matching form other org
			if (bPartner.getAD_Org_ID() > 0 // task 09852: a partner that has no org at all does not mean an inconsistency and is therefore OK
					&& bPartner.getAD_Org_ID() != importLine.getAD_Org_ID())
			{
				Services.get(IESRImportBL.class).addErrorMsg(importLine,
						Services.get(IMsgBL.class).getMsg(ctx, ESR_UNFIT_BPARTNER_ORG));

				importLine.setC_BPartner(null);
				bPartnerId = -1;
			}
			else
			{
				bPartnerId = bPartner.getC_BPartner_ID();
				importLine.setC_BPartner(bPartner);
				importLine.setESR_Document_Status(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);
			}
		}
		else
		{
			importLine.setC_BPartner(null);
			bPartnerId = -1;
		}

		// Document number (e.g. of the invoice, without initial zeros)

		final String documentNo = removeLeftZeros(completeEsrReferenceNumberStr.substring(18, 26));
		importLine.setESR_DocumentNo(documentNo);

		I_C_Invoice invoice = importLine.getC_Invoice();

		if (invoice != null)
		{
			final I_C_BPartner invoicePartner = invoice.getC_BPartner();
			final String invoiceDocumentNo = invoice.getDocumentNo();
			// final I_AD_Org invoiceOrg = InterfaceWrapperHelper.create(ctx, invoice.getAD_Org_ID(), I_AD_Org.class, trxName);

			// check the org: should not match with invoices from other orgs
			if (invoice.getAD_Org_ID() != importLine.getAD_Org_ID())
			{
				Services.get(IESRImportBL.class).addErrorMsg(importLine,
						Services.get(IMsgBL.class).getMsg(ctx, ESR_UNFIT_INVOICE_ORG));
			}
			// check the org: should not match with invoices which have the partner from other org
			else if (invoicePartner.getAD_Org_ID() > 0  // task 09852: a partner that has no org at all does not mean an inconsistency and is therefore OK
					&& invoicePartner.getAD_Org_ID() != importLine.getAD_Org_ID())
			{
				Services.get(IESRImportBL.class).addErrorMsg(importLine,
						Services.get(IMsgBL.class).getMsg(ctx, ESR_UNFIT_BPARTNER_ORG));
			}
			else
			{
				if (!invoicePartner.getValue().equals(bpValue))
				{
					// task: 05799 also try with bpValue + one '0'
					final String bpValueleftZero = '0' + bpValue;

					if (invoicePartner.getValue().endsWith(bpValueleftZero))
					{
						// if we have a match with bpValueleftZero, then we need to make sure that 'importLine' references invoice's partner
						// (the one with the leading '0')
						importLine.setBPartner_Value(bpValueleftZero);
						importLine.setC_BPartner(invoicePartner);
					}
					//task 09861
					//Make sure the bpartners with values bigger than 1000 are correctly handled.
					// For this, check if the invoice's bp value doesn't end with the string bpvalue as it is
					else if(invoicePartner.getValue().endsWith(bpValue))
					{
						importLine.setBPartner_Value(bpValue);
						importLine.setC_BPartner(invoicePartner);
					}
					else
					{
						Services.get(IESRImportBL.class).addErrorMsg(importLine,
								Services.get(IMsgBL.class).getMsg(ctx, ERR_UNFIT_BPARTNER_VALUES, new Object[] {
										invoicePartner.getValue(),
										bpValue
								}));
					}
				}

				if (!invoiceDocumentNo.equals(documentNo))
				{
					Services.get(IESRImportBL.class).addErrorMsg(importLine,
							Services.get(IMsgBL.class).getMsg(ctx, ERR_UNFIT_DOCUMENT_NOS, new Object[] {
									invoiceDocumentNo,
									documentNo
							}));
				}

			}

		}
		// if (importLine.getC_Invoice() == null)
		else
		{
			// Try to get the invoice via bpartner and document no
			final I_C_Invoice invoiceFallback = Services.get(IInvoiceDAO.class).retrieveInvoiceByInvoiceNoAndBPartnerID(ctx, documentNo, bPartnerId);
			if (invoiceFallback != null)
			{
				// check the org: should not match with invoices from other orgs
				if (invoiceFallback.getAD_Org_ID() != importLine.getAD_Org_ID())
				{
					Services.get(IESRImportBL.class).addErrorMsg(importLine,
							Services.get(IMsgBL.class).getMsg(ctx, ESR_UNFIT_INVOICE_ORG));
				}
				else
				{
					Services.get(IESRImportBL.class).setInvoice(importLine, invoiceFallback);
				}
			}
		}
		InterfaceWrapperHelper.save(importLine);
	}
}
