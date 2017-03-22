package de.metas.payment.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_Fact_Acct;
import org.compiere.process.DocAction;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.payment.api.IPaymentDAO;

public abstract class AbstractPaymentDAO implements IPaymentDAO
{
	@Override
	public BigDecimal getInvoiceOpenAmount(I_C_Payment payment, final boolean creditMemoAdjusted)
	{
		final I_C_Invoice invoice = payment.getC_Invoice();
		Check.assumeNotNull(invoice, "Invoice available for {}", payment);

		// NOTE: we are not using C_InvoicePaySchedule_ID. It shall be a column in C_Payment

		return Services.get(IAllocationDAO.class).retrieveOpenAmt(invoice, creditMemoAdjusted);
	}

	@Override
	public List<I_C_PaySelectionLine> getProcessedLines(final I_C_PaySelection paySelection)
	{
		Check.assumeNotNull(paySelection, "Pay selection not null");

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_PaySelectionLine.class, paySelection)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, paySelection.getC_PaySelection_ID())
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_Processed, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_C_PaySelectionLine.class);
	}

	@Override
	public List<I_C_Payment> retrievePostedWithoutFactAcct(final Properties ctx, final Timestamp startTime)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final IQueryBuilder<I_C_Payment> queryBuilder = queryBL.createQueryBuilder(I_C_Payment.class, ctx, trxName)
				.addOnlyActiveRecordsFilter();

		queryBuilder
				.addEqualsFilter(I_C_Payment.COLUMNNAME_Posted, true) // Posted
				.addEqualsFilter(I_C_Payment.COLUMNNAME_Processed, true) // Processed
				.addInArrayOrAllFilter(I_C_Payment.COLUMN_DocStatus, DocAction.STATUS_Closed, DocAction.STATUS_Completed)  // DocStatus in ('CO', 'CL')
				;

		// Only the documents created after the given start time
		if (startTime != null)
		{
			queryBuilder.addCompareFilter(I_C_Payment.COLUMNNAME_Created, Operator.GREATER_OR_EQUAL, startTime);
		}

		// Check if there are fact accounts created for each document
		final IQueryBuilder<I_Fact_Acct> subQueryBuilder = queryBL.createQueryBuilder(I_Fact_Acct.class, ctx, trxName)
				.addEqualsFilter(I_Fact_Acct.COLUMN_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_Payment.class));
		
		queryBuilder
				.addNotInSubQueryFilter(I_C_Payment.COLUMNNAME_C_Payment_ID, I_Fact_Acct.COLUMNNAME_Record_ID, subQueryBuilder.create()) // has no accounting
				;

		// Exclude the entries that don't have either PayAmt or OverUnderAmt. These entries will produce 0 in posting
		final ICompositeQueryFilter<I_C_Payment> nonZeroFilter = queryBL.createCompositeQueryFilter(I_C_Payment.class).setJoinOr()
				.addNotEqualsFilter(I_C_Payment.COLUMNNAME_PayAmt, Env.ZERO)
				.addNotEqualsFilter(I_C_Payment.COLUMNNAME_OverUnderAmt, Env.ZERO);

		queryBuilder.filter(nonZeroFilter);

		return queryBuilder
				.create()
				.list();

	}
}
