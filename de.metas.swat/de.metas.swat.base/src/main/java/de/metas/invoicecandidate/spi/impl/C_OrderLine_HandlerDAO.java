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


import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.InArrayQueryFilter;
import org.adempiere.ad.dao.impl.ModelColumnNameValue;
import org.adempiere.ad.dao.impl.NotQueryFilter;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.X_C_DocType;
import org.compiere.process.DocAction;

import de.metas.adempiere.model.I_C_Order;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IC_OrderLine_HandlerDAO;

public class C_OrderLine_HandlerDAO implements IC_OrderLine_HandlerDAO
{
	private final ICompositeQueryFilter<I_C_OrderLine> additionalFilters;

	public C_OrderLine_HandlerDAO()
	{
		additionalFilters = Services.get(IQueryBL.class).createCompositeQueryFilter(I_C_OrderLine.class);
		additionalFilters.setJoinAnd();
	}

	@Override
	public IQueryBuilder<I_C_OrderLine> retrieveMissingOrderLinesQuery(final Properties ctx, final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_C_OrderLine> filters = queryBL.createCompositeQueryFilter(I_C_OrderLine.class);

		// Ordered and invoiced quantities must differ
		filters.addCompareFilter(I_C_OrderLine.COLUMNNAME_QtyInvoiced,
				CompareQueryFilter.Operator.NOT_EQUAL,
				ModelColumnNameValue.forColumnName(I_C_OrderLine.COLUMNNAME_QtyOrdered));

		//
		// Line must not already have an invoice candidate.
		{
			final IQuery<I_C_Invoice_Candidate> noICQuery = queryBL
					.createQueryBuilder(I_C_Invoice_Candidate.class, ctx, trxName)
					.create();

			final ICompositeQueryFilter<I_C_OrderLine> noICFilter = queryBL.createCompositeQueryFilter(I_C_OrderLine.class);
			noICFilter.addInSubQueryFilter(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID, I_C_Invoice_Candidate.COLUMNNAME_C_OrderLine_ID, noICQuery);

			final IQueryFilter<I_C_OrderLine> notQueryFilter = new NotQueryFilter<I_C_OrderLine>(noICFilter);
			filters.addFilter(notQueryFilter);
		}

		{
			//
			// Excluding docTypes. We ignore for Proposals, Quotations, POS-Orders (SO) or delivery RMAs (PO)
			final ICompositeQueryFilter<I_C_DocType> docTypeFilter = queryBL.createCompositeQueryFilter(I_C_DocType.class);
			docTypeFilter.setJoinOr();

			final ICompositeQueryFilter<I_C_DocType> docTypeFilterSO = queryBL.createCompositeQueryFilter(I_C_DocType.class);
			docTypeFilterSO.addEqualsFilter(I_C_DocType.COLUMNNAME_DocBaseType, X_C_DocType.DOCBASETYPE_SalesOrder);
			docTypeFilterSO.addFilter(new NotQueryFilter<I_C_DocType>(new InArrayQueryFilter<I_C_DocType>(I_C_DocType.COLUMNNAME_DocSubType, X_C_DocType.DOCSUBTYPE_Proposal,
					X_C_DocType.DOCSUBTYPE_Quotation, X_C_DocType.DOCSUBTYPE_POSOrder)));
			docTypeFilter.addFilter(docTypeFilterSO);

			final ICompositeQueryFilter<I_C_DocType> docTypeFilterPO = queryBL.createCompositeQueryFilter(I_C_DocType.class);
			docTypeFilterPO.addEqualsFilter(I_C_DocType.COLUMNNAME_DocBaseType, X_C_DocType.DOCBASETYPE_PurchaseOrder);
			docTypeFilterPO.addEqualsFilter(I_C_DocType.COLUMNNAME_DocSubType, null);
			docTypeFilter.addFilter(docTypeFilterPO);

			// Take only lines from completed orders
			final ICompositeQueryFilter<I_C_Order> orderFilter = queryBL.createCompositeQueryFilter(I_C_Order.class);
			orderFilter.addEqualsFilter(I_C_Order.COLUMNNAME_DocStatus, DocAction.ACTION_Complete);

			final IQuery<I_C_DocType> docTypeQuery = queryBL.createQueryBuilder(I_C_DocType.class, ctx, trxName)
					.filter(docTypeFilter)
					.create();

			orderFilter.addInSubQueryFilter(I_C_Order.COLUMNNAME_C_DocType_ID, I_C_DocType.COLUMNNAME_C_DocType_ID, docTypeQuery);

			final IQuery<I_C_Order> orderQuery = queryBL.createQueryBuilder(I_C_Order.class, ctx, trxName)
					.filter(orderFilter)
					.create();

			filters.addInSubQueryFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, I_C_Order.COLUMNNAME_C_Order_ID, orderQuery);
		}

		//
		// Add additional filters
		filters.addFilter(additionalFilters); // task 07242

		final IQueryBuilder<I_C_OrderLine> queryBuilder = queryBL.createQueryBuilder(I_C_OrderLine.class, ctx, trxName)
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions)
				.filter(filters)
				.filterByClientId();

		return queryBuilder;
	}

	@Override
	public void addAdditionalFilter(final IQueryFilter<I_C_OrderLine> filter)
	{
		additionalFilters.addFilter(filter);
	}

	@Override
	public IQueryFilter<I_C_OrderLine> getAdditionalFilters()
	{
		return additionalFilters.copy();
	}
}
