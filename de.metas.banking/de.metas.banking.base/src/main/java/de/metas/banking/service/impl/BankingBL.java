package de.metas.banking.service.impl;

/*
 * #%L
 * de.metas.banking.base
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


import static org.compiere.model.X_C_PaySelectionLine.PAYMENTRULE_DirectDebit;
import static org.compiere.model.X_C_PaySelectionLine.PAYMENTRULE_DirectDeposit;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.banking.model.I_C_Invoice;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.POWrapper;
import org.adempiere.util.Constants;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Bank;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrg;
import org.compiere.model.MPaySelection;
import org.compiere.model.MPaySelectionCheck;
import org.compiere.model.MRecurrentPaymentHistory;
import org.compiere.model.MRecurrentPaymentLine;
import org.compiere.model.MTable;
import org.compiere.model.X_C_Invoice;
import org.compiere.model.X_C_PaySelectionCheck;
import org.compiere.process.DocAction;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.jdtaus.banking.Textschluessel;
import org.jdtaus.banking.TextschluesselVerzeichnis;
import org.jdtaus.banking.dtaus.Header;
import org.jdtaus.banking.dtaus.LogicalFile;
import org.jdtaus.banking.dtaus.LogicalFileType;
import org.jdtaus.banking.dtaus.PhysicalFile;
import org.jdtaus.banking.dtaus.PhysicalFileFactory;
import org.jdtaus.banking.dtaus.Transaction;
import org.jdtaus.core.container.ContainerFactory;

import de.metas.banking.exception.BankingException;
import de.metas.banking.model.I_C_RecurrentPayment;
import de.metas.banking.model.X_C_RecurrentPaymentLine;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.service.IBankingBL;
import de.metas.interfaces.I_C_BP_BankAccount;
import de.schaeffer.compiere.tools.DtaHelper;

public class BankingBL implements IBankingBL
{
	private static final CLogger logger = CLogger.getCLogger(BankingBL.class);

	@Override
	public Map<String, List<Transaction>> createOrders(final MPaySelection psel)
	{
		final org.compiere.model.I_C_BP_BankAccount senderAccount = psel.getC_BP_BankAccount();

		final Map<String, List<Transaction>> allTransactions = new HashMap<String, List<Transaction>>();

		final IPaySelectionDAO paySelectionBL = Services.get(IPaySelectionDAO.class);

		final String[] pRule = { X_C_PaySelectionCheck.PAYMENTRULE_DirectDeposit, X_C_PaySelectionCheck.PAYMENTRULE_DirectDebit };

		allTransactions.put(X_C_PaySelectionCheck.PAYMENTRULE_DirectDeposit, new ArrayList<Transaction>());
		allTransactions.put(X_C_PaySelectionCheck.PAYMENTRULE_DirectDebit, new ArrayList<Transaction>());

		for (final String paymentRule : pRule)
		{
			for (final MPaySelectionCheck check : paySelectionBL.retrievePaySelectionChecks(psel, paymentRule))
			{
				if (!check.isActive() || check.isProcessed())
				{
					continue;
				}

				// metas: cg : start: task 02331
				// So there is a direct deposit of - AMT and we change it to +AMT, then we also need to change
				// the jdtaus-record's type to direct debit.
				String nPaymentRule = paymentRule;
				final boolean negative = check.getPayAmt().signum() == -1;
				if (negative && X_C_PaySelectionCheck.PAYMENTRULE_DirectDeposit.equals(check.getPaymentRule()))
				{
					nPaymentRule = X_C_PaySelectionCheck.PAYMENTRULE_DirectDebit;
				}
				else if (negative && X_C_PaySelectionCheck.PAYMENTRULE_DirectDebit.equals(check.getPaymentRule()))
				{
					nPaymentRule = X_C_PaySelectionCheck.PAYMENTRULE_DirectDeposit;
				}
				// metas: cg : end: task 02331
				final Textschluessel textschluessel = checkTextSchluessel(nPaymentRule);

				final List<Transaction> transactions = allTransactions.get(nPaymentRule);
				transactions.add(mkTrx(check, senderAccount, textschluessel));
			}
		}

		return allTransactions;
	}

	private Textschluessel checkTextSchluessel(final String paymentRule)
	{
		final TextschluesselVerzeichnis textschluesselVerzeichnis =
				(TextschluesselVerzeichnis)ContainerFactory
						.getContainer()
						.getObject(TextschluesselVerzeichnis.class.getName());

		final Textschluessel textschluessel;

		if (PAYMENTRULE_DirectDebit.equals(paymentRule))
		{
			textschluessel = textschluesselVerzeichnis.getTextschluessel(5, 0, new Date());
		}
		else if (PAYMENTRULE_DirectDeposit.equals(paymentRule))
		{
			textschluessel = textschluesselVerzeichnis.getTextschluessel(51, 0, new Date());
		}
		else
		{
			// TODO -> AD_Message
			throw new BankingException("DTA-Textschluessel konnte nicht ermittelt werden");
		}
		return textschluessel;
	}

	/**
	 * Create missing due invoices for active recurrent payments.
	 */
	@Override
	public List<I_C_Invoice> createInvoicesForRecurrentPayments(final String trxName)
	{
		final List<MRecurrentPaymentLine> dueLines = MRecurrentPaymentLine.getAllDueRecurrentPaymentLines(trxName);

		final long actualTime = System.currentTimeMillis();

		final List<I_C_Invoice> result = new ArrayList<I_C_Invoice>();

		for (final MRecurrentPaymentLine line : dueLines)
		{
			Timestamp lastDate = MRecurrentPaymentLine.getLatestHistoryDateInvoicedForLine(line.get_ID(), trxName);

			if (lastDate == null)
			{
				// create the first invoice
				result.add(createInvoiceForRecurrentPaymentLine(line));

				lastDate = line.getDateFrom();
			}

			logger.fine("lastDateInvoiced: " + lastDate.toString() + " line: " + line.get_ID());

			final GregorianCalendar cal = new GregorianCalendar();
			cal.setTimeInMillis(lastDate.getTime());

			while (true)
			{
				addToCalendar(line, cal);

				final long calTime = cal.getTimeInMillis();

				if (calTime < actualTime)
				{
					result.add(createInvoiceForRecurrentPaymentLine(line));
				}
				else
				{
					break;
				}
			}
		}
		return result;
	}

	private void addToCalendar(final MRecurrentPaymentLine line, final GregorianCalendar cal)
	{
		final int frequency = line.getFrequency();

		final String frequencyType = line.getFrequencyType();

		if (X_C_RecurrentPaymentLine.FREQUENCYTYPE_Month.equals(frequencyType))
		{
			cal.add(Calendar.MONTH, frequency);
			logger.fine(frequency + " month added");
		}
		else if (X_C_RecurrentPaymentLine.FREQUENCYTYPE_Day
				.equals(frequencyType))
		{
			cal.add(Calendar.DAY_OF_MONTH, frequency);
			logger.fine(frequency + " days added");
		}
		else
		{
			throw new AdempiereException("Unimplemented frequency type: " + frequencyType);
		}
	}

	/**
	 * Create an recurrent payment invoice for the given recurrent payment line.
	 *
	 * @param line
	 * @param dateInvoiced
	 */
	public I_C_Invoice createInvoiceForRecurrentPaymentLine(final MRecurrentPaymentLine line)
	{
		final I_C_RecurrentPayment header = line.getC_RecurrentPayment();

		final MInvoice invoice = new MInvoice(line.getCtx(), 0, line.get_TrxName());
		invoice.setAD_Org_ID(line.getAD_Org_ID());

		final I_C_Invoice extInvoice = POWrapper.create(invoice, I_C_Invoice.class);
		extInvoice.setC_RecurrentPaymentLine_ID(line.get_ID());

		final Timestamp dateInvoiced = SystemTime.asTimestamp();
		invoice.setDateInvoiced(dateInvoiced);
		invoice.setDateAcct(dateInvoiced);

		invoice.setC_BPartner_ID(header.getC_BPartner_ID());
		invoice.setC_BPartner_Location_ID(header.getC_BPartner_Location_ID());

		invoice.setC_Currency_ID(line.getC_Currency_ID());
		invoice.setAD_User_ID(line.getAD_User_ID());
		invoice.setSalesRep_ID(line.getSalesRep_ID());
		invoice.setC_PaymentTerm_ID(line.getC_PaymentTerm_ID());
		invoice.setIsSOTrx(false); // always vendor invoice
		invoice.setPaymentRule(X_C_Invoice.PAYMENTRULE_DirectDeposit);

		// metas: using DocBaseType rather than sysconfig
		invoice.setC_DocTypeTarget_ID(Constants.DOCBASETYPE_AVIinvoice);
		// metas: end
		invoice.saveEx();

		final MInvoiceLine invoiceLine = new MInvoiceLine(invoice);
		invoiceLine.setC_Charge_ID(line.getC_Charge_ID());
		invoiceLine.setDescription(line.getDescription());
		invoiceLine.setQty(Env.ONE);
		invoiceLine.setPrice(line.getPayAmt());
		invoiceLine.setTax();
		invoiceLine.saveEx();

		// complete the invoice
		final boolean completed = invoice.processIt(DocAction.ACTION_Complete);
		if (completed)
		{
			invoice.saveEx();
		}
		else
		{
			throw new AdempiereException("Unable to complete invoice "
					+ invoice.getDocumentNo() + "; Logger: "
					+ MiscUtils.loggerMsgs());
		}

		// create history entry
		final MRecurrentPaymentHistory history = new MRecurrentPaymentHistory(line.getCtx(), 0, line.get_TrxName());
		history.setC_Invoice_ID(invoice.get_ID());
		history.setC_RecurrentPaymentLine_ID(line.get_ID());
		history.saveEx();

		return extInvoice;
	}

	private Transaction mkTrx(final MPaySelectionCheck check,
			final org.compiere.model.I_C_BP_BankAccount senderAccountSource,
			final Textschluessel textschluessel)
	{
		final BigDecimal payAmt = check.getPayAmt();

		final String sender = MOrg.get(check.getCtx(), check.getAD_Org_ID()).getName().toUpperCase();

		final org.compiere.model.I_C_BP_BankAccount receiverAccount = check.getC_BP_BankAccount();
		final I_C_Bank receiverBank = (I_C_Bank)MTable.get(check.getCtx(), I_C_Bank.Table_Name)
				.getPO(receiverAccount.getC_Bank_ID(), check.get_TrxName());

		final String receiver = check.getC_BPartner().getName().toUpperCase().replace('_', '-');

		final I_C_BP_BankAccount senderAccount = InterfaceWrapperHelper.create(senderAccountSource, I_C_BP_BankAccount.class);
		final String currencyIso = senderAccount.getC_Currency().getISO_Code();
		final String vz1 = check.getC_PaySelection().getName() + " - " + check.getDocumentNo();

		try
		{
			// metas: cg : start: task 02331
			// So there is a direct deposit of - AMT and we change it to +AMT, then we also need to change
			// the jdtaus-record's type to direct debit.
			BigDecimal amt = payAmt;
			if (payAmt.signum() == -1 && X_C_PaySelectionCheck.PAYMENTRULE_DirectDeposit.equals(check.getPaymentRule()))
			{
				amt = amt.negate();
			}
			// we need to handle a negative value also for DirectDebit.
			if (payAmt.signum() == -1 && X_C_PaySelectionCheck.PAYMENTRULE_DirectDebit.equals(check.getPaymentRule()))
			{
				amt = amt.negate();
			}
			// metas: cg : end: task 02331

			final Transaction transaction = DtaHelper.buildTransaction(
					textschluessel, vz1, "", currencyIso, amt, sender,
					new BigInteger(senderAccount.getAccountNo()),
					new BigInteger(senderAccount.getC_Bank().getRoutingNo()),
					receiver, new BigInteger(receiverAccount.getAccountNo()),
					new BigInteger(receiverBank.getRoutingNo()));

			check.setIsPrinted(true);
			check.saveEx();

			return transaction;
		}
		catch (final BankingException e)
		{
			e.setExecutiveAcct(senderAccount);
			e.setTargetAcct(receiverAccount);
			throw e;
		}
	}

	@Override
	public void createDtaFile(final List<Transaction> transactionList,
			final File outputFile)
	{
		final PhysicalFileFactory physicalFileFactory = (PhysicalFileFactory)ContainerFactory.getContainer().getObject(PhysicalFileFactory.class.getName());
		try
		{
			final PhysicalFile pFile = physicalFileFactory.createPhysicalFile(outputFile, PhysicalFileFactory.FORMAT_DISK);

			final List<LogicalFile> logicalFileList = new ArrayList<LogicalFile>();
			for (final Transaction transaction : transactionList)
			{
				LogicalFile logicalFile = null;

				for (final LogicalFile lFile : logicalFileList)
				{
					if (lFile.getHeader().getAccount().equals(transaction.getExecutiveAccount())
							&& lFile.getHeader().getBank().equals(transaction.getExecutiveBank())
							&& lFile.getHeader().getCustomer().toString().trim().equals(transaction.getExecutiveName().toString().trim())
							&& lFile.getHeader().getCurrency().equals(transaction.getCurrency())
							&& transaction.getType().isRemittance())
					{
						logicalFile = lFile;
						break;
					}
				}

				if (logicalFile == null)
				{
					final Header header = new Header();

					// Eigene Bankdaten eintragen
					header.setAccount(transaction.getExecutiveAccount());
					header.setBank(transaction.getExecutiveBank());
					header.setCreateDate(new java.util.Date());
					header.setCurrency(transaction.getCurrency());
					header.setCustomer(transaction.getExecutiveName());
					if (transaction.getType().isRemittance())
					{
						header.setType(LogicalFileType.GK);
					}
					else
					{
						header.setType(LogicalFileType.LK);
					}
					logicalFile = pFile.addLogicalFile(header);
					logicalFileList.add(logicalFile);
				}

				logicalFile.addTransaction(transaction);
			}
			pFile.commit();
		}
		catch (final IOException e)
		{
			// TODO -> AD_Message
			throw new BankingException("Fehler beim Export in Datei", e);
		}
	}

	@Override
	public String createBankAccountName(final org.compiere.model.I_C_BP_BankAccount bankAccount)
	{
		final StringBuilder name = new StringBuilder();
		
		if (bankAccount.getC_Bank_ID() > 0)
		{
			name.append(bankAccount.getC_Bank().getName());
		}
		else
		{
			// fallback
			name.append(bankAccount.getA_Name());
		}
		if (bankAccount.getC_Currency_ID() > 0)
		{
			if (name.length() > 0)
			{
				name.append("_");
			}
			name.append(bankAccount.getC_Currency().getISO_Code());
		}
		return name.toString();
	}
}
