package de.metas.payment.esr.api.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.api.IESRImportBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.exception.ESRParserException;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;

/**
 * Matcher for specific case, line with code <code>005</code>
 * <ul>
 * recognize reverse booking on import due to their code which is 005 and not 002
 * <ul>
 * skip those lines and just add a error message into the line saying Reverse Booking. Please contact the admin
 * 
 * @author cg
 *
 */
class ESRReverseBookingMatcher extends AbstractESRLineMatcher
{

	/**
	 * Matches the given import line
	 */
	@Override
	public void match(final I_ESR_ImportLine importLine)
	{
		final String trxType = importLine.getESRTrxType();

		// make sure we are called with the correct type of line
		Check.assume(ESRConstants.ESRTRXTYPE_ReverseBooking.equals(trxType), "{0} does not have ERS trx type {1}", importLine, ESRConstants.ESRTRXTYPE_ReverseBooking);

		// Getting ctx with importLine's AD_Client_ID and AD_Org_ID because we want to retrieve the correct C_ReferenceNo_Doc further below
		final Properties localCtx = new Properties(InterfaceWrapperHelper.getCtx(importLine, true));

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

			// Mo73 04690: guarding against NPE (shouldn't happen, but i jsut encountered it)
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
			final String amountString = replaceNonDigitCharsWithZero(amountStringWithPosibleSpaces); // mo73_04551

			try
			{
				BigDecimal amount = new BigDecimal(amountString);
				amount = amount.divide(Env.ONEHUNDRED, 2, RoundingMode.UNNECESSARY);
				importLine.setAmount(amount.negate());
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
				if (I_C_Invoice.Table_Name.equals(tableName))
				{
					importLine.setC_ReferenceNo(esrReferenceNumberDocument.getC_ReferenceNo());

					final int invoiceID = esrReferenceNumberDocument.getRecord_ID();
					final I_C_Invoice invoice = InterfaceWrapperHelper.create(localCtx, invoiceID, I_C_Invoice.class, ITrx.TRXNAME_None);

					final String initialErrorMsg = importLine.getErrorMsg();
					boolean match = esrMatchingListener.applyESRMatchingBPartnerOfTheInvoice(invoice, importLine);
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
					else
					{
						// reset error message: we do not care in this case
						importLine.setErrorMsg(null);
						importLine.setErrorMsg(initialErrorMsg);
					}

				}
				else
				{
					logger.trace(Services.get(IMsgBL.class).getMsg(localCtx, ERR_ESR_DOES_NOT_BELONG_TO_INVOICE_2P,
							new Object[] { completeEsrReferenceNumberStr, tableName }));
				}
			}
		}

		// Set the reference number components if the reference no is not manual
		if (!importLine.isESR_IsManual_ReferenceNo())
		{
			final String initialErrorMsg = importLine.getErrorMsg();
			setValuesFromESRString(importLine);
			// reset error message: we do not care in this case
			importLine.setErrorMsg(null);
			importLine.setErrorMsg(initialErrorMsg);
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

		//
		// set payment action
		importLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Reverse_Booking);
		//
		// set error message for the user
		esrImportBL.addErrorMsgInFront(importLine, Services.get(IMsgBL.class).getMsg(localCtx, ESRConstants.ESR_Reverse_Booking));
		//
		// Update IsValid flag
		importLine.setIsValid(false);
	}

	
}
