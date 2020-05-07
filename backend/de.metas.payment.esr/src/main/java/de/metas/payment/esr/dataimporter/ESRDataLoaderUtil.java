package de.metas.payment.esr.dataimporter;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import java.util.List;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.i18n.IMsgBL;
import de.metas.payment.esr.api.IESRBPBankAccountDAO;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.api.IESRLineHandlersService;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.I_ESR_PostFinanceUserNumber;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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
/**
 * This class contains methods useful to any ESR data loader.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class ESRDataLoaderUtil
{
	private static final String ERR_ESR_DOES_NOT_BELONG_TO_INVOICE_2P = "de.metas.payment.esr.EsrDoesNotBelongToInvoice";

	private static final String ERR_NO_ESR_NO_FOUND_IN_DB_1P = "de.metas.payment.esr.NoEsrNoFoundInDB";

	public static final String ESR_UNFIT_INVOICE_ORG = "ESR_Unfit_Invoice_Org";

	public static final String ESR_UNFIT_BPARTNER_ORG = "ESR_Unfit_BPartner_Org";

	public static final String ERR_UNFIT_BPARTNER_VALUES = "ESR_Unfit_BPartner_Values";

	public static final String ERR_UNFIT_DOCUMENT_NOS = "ESR_Unfit_DocumentNo";

	public static final String ERR_WRONG_POST_BANK_ACCOUNT = "ESR_Wrong_Post_Bank_Account";

	public I_ESR_ImportLine newLine(@NonNull final I_ESR_Import esrImport)
	{
		final I_ESR_ImportLine newLine = InterfaceWrapperHelper.newInstance(I_ESR_ImportLine.class, esrImport);
		newLine.setAD_Org_ID(esrImport.getAD_Org_ID());
		newLine.setESR_Import(esrImport);

		// all lines of one esrImport have the same C_BP_BankAccount_ID, so in future these two column can be removed from the line
		newLine.setC_BP_BankAccount(esrImport.getC_BP_BankAccount());
		if (esrImport.getC_BP_BankAccount_ID() > 0)
		{
			newLine.setAccountNo(esrImport.getC_BP_BankAccount().getAccountNo());
		}

		return newLine;
	}

	/**
	 * This method evaluates the given ESR reference number string and updates the given {@code importLine} accordingly.
	 * The ESR reference number string can come from a {@code .v11} file or a camt.54 {@code .xml} file.
	 * 
	 * @param importLine
	 * @param completeEsrReferenceNumberStr
	 */
	public void evaluateEsrReferenceNumber(@NonNull final I_ESR_ImportLine importLine)
	{
		final String completeEsrReferenceNumberStr = importLine.getESRFullReferenceNumber();
		if (Check.isEmpty(completeEsrReferenceNumberStr, true))
		{
			return; // there is nothing to do. Note that we don't log an error because if this string is empty, something already failed and was logged before.
		}

		// When matching, we will ignore the first 7 digits (the bank account no), and the last digit (check digit)
		final String esrReferenceNumberToMatch = completeEsrReferenceNumberStr.substring(7, 26);

		importLine.setESRReferenceNumber(esrReferenceNumberToMatch);

		final IESRImportDAO esrImportPA = Services.get(IESRImportDAO.class);
		final I_C_ReferenceNo_Doc esrReferenceNumberDocument = esrImportPA
				.retrieveESRInvoiceReferenceNumberDocument(Env.getCtx(), esrReferenceNumberToMatch);

		if (esrReferenceNumberDocument == null)
		{
			addMatchErrorMsg(importLine,
					Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_NO_ESR_NO_FOUND_IN_DB_1P,
							new Object[] { completeEsrReferenceNumberStr }));
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
				final I_C_Invoice invoice = InterfaceWrapperHelper.create(Env.getCtx(), invoiceID, I_C_Invoice.class, ITrx.TRXNAME_None);

				final boolean match = Services.get(IESRLineHandlersService.class)
						.applyESRMatchingBPartnerOfTheInvoice(invoice, importLine);

				// check the org: should not match with invoices from other orgs
				if (match)
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
				addMatchErrorMsg(
						importLine,
						Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_ESR_DOES_NOT_BELONG_TO_INVOICE_2P,
								new Object[] { completeEsrReferenceNumberStr, tableName }));
			}
		}

		// Set the reference number components if the reference no is not manual
		if (!importLine.isESR_IsManual_ReferenceNo())
		{
			setValuesFromESRString(importLine, completeEsrReferenceNumberStr);
		}
	}

	private void setValuesFromInvoice(final I_ESR_ImportLine importLine, final I_C_Invoice invoice)
	{
		importLine.setC_BPartner_ID(invoice.getC_BPartner_ID()); // 04582: no need to load the whole bpartner when we just need the ID
		importLine.setOrg_ID(invoice.getAD_Org_ID());

		InterfaceWrapperHelper.save(importLine);
	}

	/**
	 * This method evaluated the ESR reference string, and matches/verifies that everything is consistent with the system.<br>
	 * Any problems are logged to {@link I_ESR_ImportLine#COLUMN_MatchErrorMsg}.
	 * 
	 * @param importLine
	 * @param completeEsrReferenceNumberStr
	 */
	private void setValuesFromESRString(
			@NonNull final I_ESR_ImportLine importLine,
			@NonNull final String completeEsrReferenceNumberStr)
	{
		// Organization value
		final String orgValue = completeEsrReferenceNumberStr.substring(7, 10);
		importLine.setSektionNo(orgValue);

		// Org ID
		final I_AD_Org organization = Services.get(IOrgDAO.class).retrieveOrganizationByValue(Env.getCtx(), orgValue);
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
			bPartner = Services.get(IBPartnerDAO.class).retrieveBPartnerByValue(Env.getCtx(), formattedBPValue);
		}

		importLine.setBPartner_Value(bpValue);

		if (bPartner != null)
		{
			final boolean match = Services.get(IESRLineHandlersService.class).applyESRMatchingBPartner(bPartner, importLine);

			// check the org: should not match with invoices from other orgs
			if (match)
			{
				bPartnerId = bPartner.getC_BPartner_ID();
				importLine.setC_BPartner(bPartner);
				importLine.setESR_Document_Status(X_ESR_ImportLine.ESR_DOCUMENT_STATUS_PartiallyMatched);

			}
			else
			{
				importLine.setC_BPartner(null);
				bPartnerId = -1;
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
				addMatchErrorMsg(importLine,
						Services.get(IMsgBL.class).getMsg(Env.getCtx(), ESRDataLoaderUtil.ESR_UNFIT_INVOICE_ORG));
			}
			// check the org: should not match with invoices which have the partner from other org
			else if (Services.get(IESRLineHandlersService.class).applyESRMatchingBPartnerOfTheInvoice(invoice, importLine))
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
					// task 09861
					// Make sure the bpartners with values bigger than 1000 are correctly handled.
					// For this, check if the invoice's bp value doesn't end with the string bpvalue as it is
					else if (invoicePartner.getValue().endsWith(bpValue))
					{
						importLine.setBPartner_Value(bpValue);
						importLine.setC_BPartner(invoicePartner);
					}
					else
					{
						addMatchErrorMsg(importLine,
								Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_UNFIT_BPARTNER_VALUES, new Object[] {
										invoicePartner.getValue(),
										bpValue
								}));
					}
				}

				if (!invoiceDocumentNo.equals(documentNo))
				{
					addMatchErrorMsg(importLine,
							Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_UNFIT_DOCUMENT_NOS, new Object[] {
									invoiceDocumentNo,
									documentNo
							}));
				}
			}
			else
			{
				addMatchErrorMsg(importLine,
						Services.get(IMsgBL.class).getMsg(Env.getCtx(), ESRDataLoaderUtil.ESR_UNFIT_BPARTNER_ORG));
			}

		}
		// if (importLine.getC_Invoice() == null)
		else
		{
			// Try to get the invoice via bpartner and document no
			final I_C_Invoice invoiceFallback = Services.get(IInvoiceDAO.class).retrieveInvoiceByInvoiceNoAndBPartnerID(Env.getCtx(), documentNo, bPartnerId);
			if (invoiceFallback != null)
			{
				// check the org: should not match with invoices from other orgs
				if (invoiceFallback.getAD_Org_ID() != importLine.getAD_Org_ID())
				{
					addMatchErrorMsg(importLine,
							Services.get(IMsgBL.class).getMsg(Env.getCtx(), ESRDataLoaderUtil.ESR_UNFIT_INVOICE_ORG));
				}
				else
				{
					Services.get(IESRImportBL.class).setInvoice(importLine, invoiceFallback);
				}
			}
		}
		InterfaceWrapperHelper.save(importLine);
	}

	/**
	 * Method to remove the left zeros from a string.
	 * 
	 * @param value
	 * @return the initial String if it's made of only zeros; the string without the left zeros otherwise.
	 */
	public String removeLeftZeros(final String value)
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

	public void addMatchErrorMsg(@NonNull final I_ESR_ImportLine importLine, final String msg)
	{
		importLine.setMatchErrorMsg(addMsgToString(importLine.getMatchErrorMsg(), msg));
	}

	public void addImportErrorMsg(@NonNull final I_ESR_ImportLine importLine, final String msg)
	{
		importLine.setImportErrorMsg(addMsgToString(importLine.getImportErrorMsg(), msg));
	}

	public String addMsgToString(final String str, final String msg)
	{
		if (Check.isEmpty(msg, true))
		{
			return str;
		}

		if (Check.isEmpty(str, true))
		{
			return msg;
		}

		return str + "; " + msg;
	}

	public void evaluateESRAccountNumber(final I_ESR_Import esrImport, final I_ESR_ImportLine importLine)
	{
		final I_C_BP_BankAccount bankAcct = create(esrImport.getC_BP_BankAccount(), I_C_BP_BankAccount.class);

		final String postAcctNo = importLine.getESRPostParticipantNumber();

		final String renderedPostAccountNo = bankAcct.getESR_RenderedAccountNo();
		final String unrenderedPostAcctNo = unrenderPostAccountNo(renderedPostAccountNo);

		final boolean esrLineFitsBankAcctESRPostAcct = unrenderedPostAcctNo.equals(postAcctNo);

		final List<I_ESR_PostFinanceUserNumber> postFinanceUserNumbers = Services.get(IESRBPBankAccountDAO.class).retrieveESRPostFinanceUserNumbers(bankAcct);

		final boolean existsFittingPostFinanceUserNumber = existsPostFinanceUserNumberFitsPostAcctNo(postFinanceUserNumbers, postAcctNo);

		final boolean esrNumbersFit = esrLineFitsBankAcctESRPostAcct || existsFittingPostFinanceUserNumber;

		if (!esrNumbersFit)
		{

			ESRDataLoaderUtil.addMatchErrorMsg(importLine, Services.get(IMsgBL.class).getMsg(Env.getCtx(), ERR_WRONG_POST_BANK_ACCOUNT,
					new Object[] { postAcctNo }));
		}

	}

	private boolean existsPostFinanceUserNumberFitsPostAcctNo(final List<I_ESR_PostFinanceUserNumber> postFinanceUserNumbers, @NonNull final String postAcctNo)
	{
		if (Check.isEmpty(postFinanceUserNumbers))
		{
			return false;
		}

		for (final I_ESR_PostFinanceUserNumber postFinanceUserNumber : postFinanceUserNumbers)
		{
			// Provide support for both rendered and unrendered esr account numbers
			final String esrAcctNo = postFinanceUserNumber.getESR_RenderedAccountNo();

			if (postAcctNo.equals(esrAcctNo))
			{
				return true;
			}

			if (esrAcctNo.contains("-"))
			{
				final String unrenderedESRAcctNo = unrenderPostAccountNo(esrAcctNo);

				if (postAcctNo.equals(unrenderedESRAcctNo))
				{
					return true;
				}
			}

		}

		return false;
	}

	private String unrenderPostAccountNo(final String renderedPostAccountNo)
	{
		final String[] renderenNoComponents = renderedPostAccountNo.split("-");
		Check.assume(renderenNoComponents.length == 3, renderedPostAccountNo + " contains three '-' separated parts");

		final StringBuilder sb = new StringBuilder();
		sb.append(renderenNoComponents[0]);
		sb.append(Util.lpadZero(renderenNoComponents[1], 6, "middle section of " + renderedPostAccountNo));
		sb.append(renderenNoComponents[2]);

		return sb.toString();
	}

}
