package de.metas.invoicecandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut;
import org.compiere.process.DocAction;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_M_InOutLine;

/* package */class InOutCandidateHandlerDAO
{
	/**
	 * Get all {@link I_M_InOutLine}s which are not linked to an {@link I_C_OrderLine} and there is no invoice candidate already generated for them.
	 * 
	 * NOTE: this method will be used to identify those inout lines for which {@link InOutCandidateHandler} will generate invoice candidates.
	 * 
	 * @param ctx
	 * @param limit
	 * @param trxName
	 * @return inout lines
	 */
	public Iterator<I_M_InOutLine> retrieveAllLinesWithoutOrderLine(final Properties ctx, final int limit, final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_M_InOutLine> filters = queryBL.createCompositeQueryFilter(I_M_InOutLine.class);
		filters.addEqualsFilter(I_M_InOutLine.COLUMNNAME_C_OrderLine_ID, null);
		filters.addEqualsFilter(I_M_InOutLine.COLUMNNAME_Processed, true); // also processing e.g. closed InOuts
		filters.addEqualsFilter(I_M_InOutLine.COLUMNNAME_IsInvoiceCandidate, false); // which don't have invoice candidates already generated
		filters.addOnlyActiveRecordsFilter();

		// if the inout was reversed, and there is no IC yet, don't bother creating one
		final IQuery<I_M_InOut> inoutQuery = queryBL.createQueryBuilder(I_M_InOut.class)
				.setContext(ctx, trxName)
				.addNotEqualsFilter(I_M_InOut.COLUMNNAME_DocStatus, DocAction.STATUS_Reversed)
				.create();
		filters.addInSubQueryFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, I_M_InOut.COLUMNNAME_M_InOut_ID, inoutQuery);

		final IQueryBuilder<I_M_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class)
				.setContext(ctx, trxName)
				.filter(filters)
				.filterByClientId();
		queryBuilder.orderBy()
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);
		queryBuilder.setLimit(limit);

		return queryBuilder.create()
				.iterate(I_M_InOutLine.class);
	}
}
