package de.metas.invoice.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;

import de.metas.invoice.IMatchInvDAO;

public class MatchInvDAO implements IMatchInvDAO
{

	@Override
	public List<I_M_MatchInv> retrieveForInvoiceLine(final I_C_InvoiceLine il)
	{
		return retrieveForInvoiceLineQuery(il)
				.create()
				.list();
	}

	@Override
	public IQueryBuilder<I_M_MatchInv> retrieveForInvoiceLineQuery(final I_C_InvoiceLine il)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_MatchInv.class, il)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID, il.getC_InvoiceLine_ID())
				.addOnlyActiveRecordsFilter()
				//
				.orderBy()
				.addColumn(I_M_MatchInv.COLUMN_M_MatchInv_ID)
				.endOrderBy()
				//
				;
	}

	@Override
	public List<I_M_MatchInv> retrieveForInOutLine(final I_M_InOutLine iol)
	{
		final IQueryBuilder<I_M_MatchInv> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_MatchInv.class, iol)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOutLine_ID, iol.getM_InOutLine_ID())
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_M_MatchInv.COLUMN_M_MatchInv_ID);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public List<I_M_MatchInv> retrieveForInOut(final I_M_InOut inout)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_InOutLine.class, inout)
				.addEqualsFilter(I_M_InOutLine.COLUMN_M_InOut_ID, inout.getM_InOut_ID())
				// .addOnlyActiveRecordsFilter() // all of them because maybe we want to delete them
				//
				.andCollectChildren(I_M_MatchInv.COLUMN_M_InOutLine_ID, I_M_MatchInv.class)
				// .addOnlyActiveRecordsFilter() // all of them because maybe we want to delete them
				.orderBy()
				.addColumn(I_M_MatchInv.COLUMN_M_MatchInv_ID)
				.endOrderBy()
				//
				.create()
				.list(I_M_MatchInv.class);
	}

	@Override
	public BigDecimal retrieveQtyInvoiced(final I_M_InOutLine iol)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_MatchInv.class, iol)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOutLine_ID, iol.getM_InOutLine_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_M_MatchInv.COLUMNNAME_Qty, Aggregate.SUM, BigDecimal.class);
	}

	@Override
	public BigDecimal retrieveQtyMatched(final I_C_InvoiceLine invoiceLine)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_MatchInv.class, invoiceLine)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID, invoiceLine.getC_InvoiceLine_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_M_MatchInv.COLUMNNAME_Qty, Aggregate.SUM, BigDecimal.class);
	}

	@Override
	public boolean hasMatchInvs(final I_C_InvoiceLine invoiceLine, final I_M_InOutLine inoutLine, final String trxName)
	{
		Check.assumeNotNull(invoiceLine, "invoiceLine not null");
		final int invoiceLineId = invoiceLine.getC_InvoiceLine_ID();
		final Properties ctx = InterfaceWrapperHelper.getCtx(invoiceLine);

		Check.assumeNotNull(inoutLine, "inoutLine not null");
		final int inoutLineId = inoutLine.getM_InOutLine_ID();

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_MatchInv.class, ctx, trxName)
				.addEqualsFilter(I_M_MatchInv.COLUMN_C_InvoiceLine_ID, invoiceLineId)
				.addEqualsFilter(I_M_MatchInv.COLUMN_M_InOutLine_ID, inoutLineId)
				.addOnlyActiveRecordsFilter()
				.create()
				.match();
	}
}
