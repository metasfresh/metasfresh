package de.metas.banking.payment.impl;

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


import static org.compiere.model.I_C_PaySelectionCheck.COLUMNNAME_C_PaySelection_ID;
import static org.compiere.model.I_C_PaySelectionCheck.COLUMNNAME_PaymentRule;
import static org.compiere.model.I_C_PaySelectionCheck.Table_Name;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BankStatementLine;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.MPaySelection;
import org.compiere.model.MPaySelectionCheck;
import org.compiere.model.MPaySelectionLine;
import org.compiere.model.Query;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import de.metas.banking.payment.IPaySelectionDAO;

/**
 * @author al
 */
public class PaySelectionDAO implements IPaySelectionDAO
{
	@Override
	public <T extends I_C_PaySelectionLine> List<T> retrievePaySelectionLines(
			final I_C_PaySelection paySelection,
			final Class<T> clazz)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = createQueryBuilder(paySelection);
		return queryBuilder.create()
				.list(clazz);
	}

	@Override
	public int retrievePaySelectionLinesCount(final I_C_PaySelection paySelection)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = createQueryBuilder(paySelection);
		return queryBuilder.create()
				.count();
	}

	@Override
	public int retrieveLastPaySelectionLineNo(final Properties ctx, final int paySelectionId, final String trxName)
	{
		final BigDecimal lastLineNo = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PaySelectionLine.class, ctx, trxName)
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, paySelectionId)
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_C_PaySelectionLine.COLUMNNAME_Line, IQuery.AGGREGATE_MAX, BigDecimal.class);
		if (lastLineNo == null || lastLineNo.signum() <= 0)
		{
			return 0;
		}

		return lastLineNo.intValue();
	}

	@Override
	public List<I_C_PaySelectionLine> retrievePaySelectionLinesMatchingInvoice(final I_C_PaySelection paySelection, final I_C_Invoice invoice)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = createQueryBuilder(paySelection)
				.addEqualsFilter(org.compiere.model.I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID());
		return queryBuilder
				.create()
				.list(I_C_PaySelectionLine.class);
	}

	@Override
	public List<de.metas.banking.model.I_C_PaySelectionLine> retrievePaySelectionLines(I_C_BankStatementLine bankStatementLine)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PaySelectionLine.class, bankStatementLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.banking.model.I_C_PaySelectionLine.COLUMNNAME_C_BankStatementLine_ID, bankStatementLine.getC_BankStatementLine_ID())
				.create()
				.list(de.metas.banking.model.I_C_PaySelectionLine.class);
	}

	@Override
	public de.metas.banking.model.I_C_PaySelectionLine retrievePaySelectionLine(I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_PaySelectionLine.class, bankStatementLineRef)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.banking.model.I_C_PaySelectionLine.COLUMNNAME_C_BankStatementLine_Ref_ID, bankStatementLineRef.getC_BankStatementLine_Ref_ID())
				.create()
				.firstOnly(de.metas.banking.model.I_C_PaySelectionLine.class);

	}


	@Override
	public boolean isPaySelectionLineMatchInvoice(final I_C_PaySelection paySelection, final I_C_Invoice invoice)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = createQueryBuilder(paySelection)
				.addEqualsFilter(org.compiere.model.I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID());
		return queryBuilder.create()
				.match();
	}

	private final IQueryBuilder<I_C_PaySelectionLine> createQueryBuilder(final I_C_PaySelection paySelection)
	{
		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder =
				Services.get(IQueryBL.class).createQueryBuilder(I_C_PaySelectionLine.class, paySelection);

		queryBuilder.addEqualsFilter(org.compiere.model.I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID, paySelection.getC_PaySelection_ID())
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(InterfaceWrapperHelper.getCtx(paySelection));
		return queryBuilder;
	}

	@Override
	public List<MPaySelectionCheck> retrievePaySelectionChecks(final MPaySelection ps, final String paymentRule)
	{
		final String whereClause = COLUMNNAME_C_PaySelection_ID + "=? AND " + COLUMNNAME_PaymentRule + "=?";

		final Object[] parameters = { ps.get_ID(), paymentRule };

		return retrieve(ps, whereClause, parameters);
	}

	@Override
	public List<MPaySelectionCheck> retrievePaySelectionChecks(final MPaySelection ps)
	{
		final String whereClause = COLUMNNAME_C_PaySelection_ID + "=?";

		final Object[] parameters = { ps.get_ID() };

		return retrieve(ps, whereClause, parameters);
	}

	private List<MPaySelectionCheck> retrieve(final MPaySelection ps,
			final String whereClause,
			final Object[] parameters)
	{
		final List<MPaySelectionCheck> pscs =
				new Query(ps.getCtx(), Table_Name, whereClause, ps.get_TrxName())
						.setParameters(parameters)
						.list();

		return pscs;
	}

	@Override
	public List<MPaySelectionLine> retrievePaySelectionLines(final MPaySelectionCheck psc)
	{
		final String whereClause = I_C_PaySelectionLine.COLUMNNAME_C_PaySelectionCheck_ID + "=?";

		final Object[] parameters = { psc.get_ID() };

		final List<MPaySelectionLine> paySelectionLines =
				new Query(psc.getCtx(), I_C_PaySelectionLine.Table_Name, whereClause, psc.get_TrxName())
						.setParameters(parameters)
						.setOnlyActiveRecords(true)
						.list();

		return paySelectionLines;
	}

	@Override
	public List<I_C_PaySelectionLine> retrievePaySelectionLines(final org.compiere.model.I_C_Invoice invoice)
	{
		Check.assumeNotNull(invoice, "Param 'invoice' is not null");

		final IQueryBuilder<I_C_PaySelectionLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_PaySelectionLine.class, invoice)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_PaySelectionLine.COLUMNNAME_C_Invoice_ID, invoice.getC_Invoice_ID());

		queryBuilder.orderBy()
				.addColumn(I_C_PaySelectionLine.COLUMNNAME_C_PaySelection_ID)
				.addColumn(I_C_PaySelectionLine.COLUMNNAME_Line);

		return queryBuilder
				.create()
				.list();
	}
}
