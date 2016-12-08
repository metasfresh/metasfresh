package de.metas.payment.esr.process;

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
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.process.DocAction;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.banking.service.IBankStatementBL;
import de.metas.document.engine.IDocActionBL;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Create {@link I_C_BankStatementLine}/{@link I_C_BankStatementLine_Ref} from {@link I_ESR_ImportLine}s.
 * 
 * @author tsa
 *
 */
public class C_BankStatementLine_CreateFrom_ESR_Import extends JavaProcess implements IProcessPrecondition
{
	// services
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient IESRImportDAO esrImportDAO = Services.get(IESRImportDAO.class);
	private final transient IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final transient IBankStatementBL bankStatementBL = Services.get(IBankStatementBL.class);

	// Parameters
	private int p_ESR_Import_ID;

	// Status
	private I_C_BankStatement _bankStatement;
	private I_ESR_Import _esrImport;
	private final Map<ArrayKey, I_C_BankStatementLine> _bankStatementLines = new HashMap<>();
	private final Map<Integer, Integer> bankStatementLineId2NextReferenceLineNo = new HashMap<>();

	// Counters
	private int count_ESR_ImportLines = 0;
	private int count_BankStatementLines = 0;
	private int count_BankStatementLine_Refs = 0;

	/**
	 * @return <code>true</code> if the given gridTab belongs to a bank statement that is drafted or in progress
	 */
	@Override
	public boolean isPreconditionApplicable(final PreconditionsContext context)
	{
		if (I_C_BankStatement.Table_Name.equals(context.getTableName()))
		{
			final I_C_BankStatement bankStatement = context.getModel(I_C_BankStatement.class);
			return docActionBL.isStatusOneOf(bankStatement,
					DocAction.STATUS_Drafted, DocAction.STATUS_InProgress);
		}
		return false;
	}

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();

			if (I_ESR_Import.COLUMNNAME_ESR_Import_ID.equals(name))
			{
				p_ESR_Import_ID = para.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		Check.errorIf(getRecord_ID() <= 0, "Process {} needs to be run with a Record_ID", this);

		//
		// Import ESR_ImportLines to bank statement line references
		createBankStatementLineRefs();

		//
		// Update Bank Statement Line's totals:
		for (final I_C_BankStatementLine bankStatementLine : getC_BankStatementLines())
		{
			bankStatementBL.recalculateStatementLineAmounts(bankStatementLine);
		}

		return "@Success@ - " + buildProgressMessage();
	}

	private String buildProgressMessage()
	{
		return "@ESR_ImportLine_ID@ #" + count_ESR_ImportLines
				+ ", @C_BankStatementLine_ID@ #" + count_BankStatementLines
				+ ", @C_BankStatementLine_Ref_ID@ #" + count_BankStatementLine_Refs;
	}

	private final I_C_BankStatement getC_BankStatement()
	{
		if (_bankStatement == null)
		{
			_bankStatement = getRecord(I_C_BankStatement.class);
		}
		return _bankStatement;
	}

	private final Collection<I_C_BankStatementLine> getC_BankStatementLines()
	{
		return _bankStatementLines.values();
	}

	private final I_ESR_Import getESR_Import()
	{
		if (_esrImport == null)
		{
			_esrImport = InterfaceWrapperHelper.create(getCtx(), p_ESR_Import_ID, I_ESR_Import.class, getTrxName());
		}
		return _esrImport;
	}

	/** Import ESR_ImportLines to bank statement line references */
	private void createBankStatementLineRefs()
	{
		final I_C_BankStatement bankStatement = getC_BankStatement();
		final I_ESR_Import esrImport = getESR_Import();

		Check.errorIf(bankStatement.getC_BP_BankAccount_ID() != esrImport.getC_BP_BankAccount_ID(),
				"C_BankStatement {} with C_BP_BankAccount_ID={} and ESR_Import {} with C_BP_BankAccount_ID={} need to have the same C_BP_BankAccount_ID",
				bankStatement, bankStatement.getC_BP_BankAccount_ID(), esrImport, esrImport.getC_BP_BankAccount_ID());

		//
		// Retrieve ESR_ImportLines, ordered by AccountingDate.
		final List<I_ESR_ImportLine> esrImportLines = Ordering.natural()
				.nullsLast()
				.onResultOf(new Function<I_ESR_ImportLine, Timestamp>()
				{

					@Override
					public Timestamp apply(I_ESR_ImportLine input)
					{
						return input.getAccountingDate();
					}

				})
				.nullsFirst()
				.sortedCopy(esrImportDAO.retrieveLines(esrImport));

		//
		// Iterate ESR_ImportLines and create bank statement line refs
		for (final I_ESR_ImportLine esrImportLine : esrImportLines)
		{
			esrImportLine.setESR_Import(esrImport); // optimization, to make sure it's not reloaded if needed

			// skip lines which are not eligible for importing
			if (!isEligible(esrImportLine))
			{
				continue;
			}

			count_ESR_ImportLines++;

			createC_BankStatementLine_Ref(esrImportLine);
		}
	}

	/** @return true if given line it's eligible for importing */
	private boolean isEligible(final I_ESR_ImportLine esrImportLine)
	{
		// Skip if already in a bank statement
		if (esrImportLine.getC_BankStatementLine_ID() > 0 || esrImportLine.getC_BankStatementLine_Ref_ID() > 0)
		{
			return false;
		}

		// Skip if no invoice
		if (esrImportLine.getC_Invoice_ID() <= 0)
		{
			return false;
		}

		// Skip if no payment
		if (esrImportLine.getC_Payment_ID() <= 0)
		{
			return false;
		}

		return true;
	}

	private I_C_BankStatementLine getCreateBankStatementLine(final I_ESR_ImportLine esrImportLine)
	{
		final ArrayKey key = mkBankStatementLineKey(esrImportLine);
		I_C_BankStatementLine bankStatementLine = _bankStatementLines.get(key);
		if (bankStatementLine == null)
		{
			final I_ESR_Import esrImport = esrImportLine.getESR_Import();
			final Timestamp date = esrImportLine.getAccountingDate();

			final I_C_BankStatement bankStatement = getC_BankStatement();

			bankStatementLine = InterfaceWrapperHelper.newInstance(I_C_BankStatementLine.class, esrImport);
			bankStatementLine.setAD_Org_ID(esrImport.getAD_Org_ID());
			bankStatementLine.setC_BankStatement(bankStatement);
			bankStatementLine.setIsMultiplePaymentOrInvoice(true); // we have a reference line for each invoice
			bankStatementLine.setIsMultiplePayment(true); // each invoice shall have it's own payment
			bankStatementLine.setC_Currency_ID(bankStatement.getC_BP_BankAccount().getC_Currency_ID());
			bankStatementLine.setValutaDate(date);
			bankStatementLine.setDateAcct(date);
			bankStatementLine.setStatementLineDate(date);
			bankStatementLine.setReferenceNo(null); // no ReferenceNo at this level
			bankStatementLine.setC_BPartner(null); // no partner because we will have it on "line reference" level
			bankStatementLine.setStmtAmt(BigDecimal.ZERO); // will be updated at the end
			bankStatementLine.setTrxAmt(BigDecimal.ZERO); // will be updated at the end
			bankStatementLine.setChargeAmt(BigDecimal.ZERO);
			bankStatementLine.setInterestAmt(BigDecimal.ZERO);
			InterfaceWrapperHelper.save(bankStatementLine);

			_bankStatementLines.put(key, bankStatementLine);
			count_BankStatementLines++;
		}

		return bankStatementLine;
	}

	private final void createC_BankStatementLine_Ref(final I_ESR_ImportLine esrImportLine)
	{
		final I_C_Payment payment = esrImportLine.getC_Payment();

		//
		// Create the bank statement line (if not already created)
		final I_C_BankStatementLine bankStatementLine = getCreateBankStatementLine(esrImportLine);

		//
		// Create new bank statement line reference for our current pay selection line.
		final I_C_BankStatementLine_Ref bankStatementLineRef = InterfaceWrapperHelper.newInstance(I_C_BankStatementLine_Ref.class, bankStatementLine);
		bankStatementLineRef.setAD_Org_ID(bankStatementLine.getAD_Org_ID());
		bankStatementLineRef.setC_BankStatementLine(bankStatementLine);
		IBankStatementBL.DYNATTR_DisableBankStatementLineRecalculateFromReferences.setValue(bankStatementLineRef, true); // disable recalculation. we will do it at the end

		bankStatementLineRef.setC_BPartner_ID(esrImportLine.getC_BPartner_ID());
		final I_C_Invoice invoice = esrImportLine.getC_Invoice();
		bankStatementLineRef.setC_Invoice(invoice);
		bankStatementLineRef.setC_Currency_ID(invoice.getC_Currency_ID());
		bankStatementLineRef.setC_Payment(payment);

		//
		// Get pay schedule line amounts:
		final boolean isReceipt;
		if (invoiceBL.isCreditMemo(invoice))
		{
			// SOTrx=Y, but credit memo => receipt=N
			isReceipt = !invoice.isSOTrx();
		}
		else
		{
			// SOTrx=Y => receipt=Y
			isReceipt = invoice.isSOTrx();
		}

		final BigDecimal factor = isReceipt ? BigDecimal.ONE : BigDecimal.ONE.negate();
		final BigDecimal linePayAmt = payment.getPayAmt().multiply(factor);
		final BigDecimal lineDiscountAmt = payment.getDiscountAmt().multiply(factor);

		// we store the psl's discount amount, because if we create a payment from this line, then we don't want the psl's Discount to end up as a mere underpayment.
		bankStatementLineRef.setDiscountAmt(lineDiscountAmt);
		bankStatementLineRef.setTrxAmt(linePayAmt);
		bankStatementLineRef.setReferenceNo(esrImportLine.getESRReferenceNumber());

		final int nextReferenceLineNo = incrementAndGetNextReferenceLineNo(bankStatementLine);
		bankStatementLineRef.setLine(nextReferenceLineNo);
		InterfaceWrapperHelper.save(bankStatementLineRef);
		count_BankStatementLine_Refs++;

		//
		// Update pay selection line => mark it as reconciled
		esrImportLine.setC_BankStatementLine(bankStatementLine);
		esrImportLine.setC_BankStatementLine_Ref(bankStatementLineRef);
		InterfaceWrapperHelper.save(esrImportLine);
	}

	private final ArrayKey mkBankStatementLineKey(final I_ESR_ImportLine esrImportLine)
	{
		final Timestamp dateAcct = esrImportLine.getAccountingDate();
		Check.assumeNotNull(dateAcct, "dateAcct not null");

		return Util.mkKey(dateAcct.getTime());
	}

	private final int incrementAndGetNextReferenceLineNo(final I_C_BankStatementLine bankStatementLine)
	{
		final int bankStatementLineId = bankStatementLine.getC_BankStatementLine_ID();
		Integer nextReferenceLineNo = bankStatementLineId2NextReferenceLineNo.get(bankStatementLineId);
		if (nextReferenceLineNo == null)
		{
			nextReferenceLineNo = 0;
		}

		nextReferenceLineNo += 10;
		bankStatementLineId2NextReferenceLineNo.put(bankStatementLineId, nextReferenceLineNo);

		return nextReferenceLineNo;
	}

}
