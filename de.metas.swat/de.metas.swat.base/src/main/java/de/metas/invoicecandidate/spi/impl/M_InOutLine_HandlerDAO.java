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

import de.metas.interfaces.I_C_DocType;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_M_InOutLine;

/**
 * DAO to support {@link M_InOut_Handler} and {@link M_InOutLine_Handler}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */class M_InOutLine_HandlerDAO
{
	/**
	 * Get all {@link I_M_InOutLine}s which are not linked to an {@link I_C_OrderLine} and there is no invoice candidate already generated for them.
	 * 
	 * NOTE: this method will be used to identify those inout lines for which {@link M_InOutLine_Handler} will generate invoice candidates.
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

		//
		// Filter M_InOut
		{
			final IQueryBuilder<I_M_InOut> inoutQueryBuilder = queryBL.createQueryBuilder(I_M_InOut.class, ctx, trxName);
	
			// if the inout was reversed, and there is no IC yet, don't bother creating one
			inoutQueryBuilder.addNotEqualsFilter(I_M_InOut.COLUMNNAME_DocStatus, DocAction.STATUS_Reversed);
			
			// Exclude some DocTypes
			{
				final IQuery<I_C_DocType> validDocTypesQuery = queryBL.createQueryBuilder(I_C_DocType.class, ctx, trxName)
						// Don't create InvoiceCandidates for DocSubType Saldokorrektur (FRESH-454)
						.addNotEqualsFilter(I_C_DocType.COLUMNNAME_DocSubType, I_C_DocType.DOCSUBTYPE_InOutAmountCorrection)
						//
						.create();
				inoutQueryBuilder.addInSubQueryFilter(I_M_InOut.COLUMNNAME_C_DocType_ID, I_C_DocType.COLUMNNAME_C_DocType_ID, validDocTypesQuery);
			}
			
			filters.addInSubQueryFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, I_M_InOut.COLUMNNAME_M_InOut_ID, inoutQueryBuilder.create());
		}

		final IQueryBuilder<I_M_InOutLine> queryBuilder = queryBL.createQueryBuilder(I_M_InOutLine.class, ctx, trxName)
				.filter(filters)
				.filterByClientId();
		queryBuilder.orderBy()
				.addColumn(I_M_InOutLine.COLUMNNAME_M_InOutLine_ID);
		queryBuilder.setLimit(limit);

		return queryBuilder.create()
				.iterate(I_M_InOutLine.class);
	}
}
