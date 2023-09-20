package de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl;

import de.metas.document.engine.IDocument;
import de.metas.materialtracking.model.I_C_Invoice_Detail;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.IQuery;
import org.eevolution.model.I_PP_Order;

import java.util.Iterator;

/*
 * #%L
 * de.metas.materialtracking
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

public class PP_Order_MaterialTracking_HandlerDAO
{

	/* package */PP_Order_MaterialTracking_HandlerDAO()
	{
	}

	/**
	 * Gets a filter which accepts only those {@link I_PP_Order}s which are invoiceable.
	 *
	 * More precisely, manufacturing orders which:
	 * <ul>
	 * <li>reference a M_Material_Tracking and
	 * <li>are closed
	 * </ul>
	 *
	 * @return
	 */
	private IQueryFilter<I_PP_Order> getPP_OrderInvoiceableFilter(final Object contextProvider)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_PP_Order> filters = queryBL.createCompositeQueryFilter(I_PP_Order.class)
				// Only those manufacturing orders which are closed...
				.addEqualsFilter(I_PP_Order.COLUMN_DocStatus, IDocument.STATUS_Closed)
				// ...and only those which we didn't already explicitly look at
				.addEqualsFilter(de.metas.materialtracking.model.I_PP_Order.COLUMNNAME_IsInvoiceCandidate, false);

		//
		// only if they reference a M_Material_Tracking that is not yet processed
		{
			final IQuery<I_M_Material_Tracking> unprocessedMaterialTrackingsQuery = queryBL
					.createQueryBuilder(I_M_Material_Tracking.class, contextProvider)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_Material_Tracking.COLUMNNAME_Processed, false)
					.create();

			filters.addInSubQueryFilter(de.metas.materialtracking.model.I_PP_Order.COLUMNNAME_M_Material_Tracking_ID,
					I_M_Material_Tracking.COLUMNNAME_M_Material_Tracking_ID,
					unprocessedMaterialTrackingsQuery);
		}

		//
		// Only those manufacturing orders which do not already have an invoice detail
		{
			final IQuery<I_C_Invoice_Detail> invoiceDetailsWithPPOrderQuery = queryBL
					.createQueryBuilder(I_C_Invoice_Detail.class, contextProvider)
					.addOnlyActiveRecordsFilter()
					.addNotEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_C_Invoice_Candidate_ID, null)
					.create();

			filters.addNotInSubQueryFilter(I_PP_Order.COLUMNNAME_PP_Order_ID,
					I_C_Invoice_Detail.COLUMNNAME_PP_Order_ID,
					invoiceDetailsWithPPOrderQuery);
		}

		return filters;
	}

	/**
	 * Uses a <b>DB</b> query to validate if the given <code>ppOrder</code> is still invoiceable.
	 *
	 * @return true if given manufacturing order is invoiceable
	 *
	 * @see #getPP_OrderInvoiceableFilter(Object)
	 */
	/* package */boolean isInvoiceable(final I_PP_Order ppOrder)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryFilter<I_PP_Order> invoiceableFilter = getPP_OrderInvoiceableFilter(ppOrder);

		return queryBL.createQueryBuilder(I_PP_Order.class, ppOrder)
				.filter(invoiceableFilter)
				.addEqualsFilter(I_PP_Order.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.create()
				.anyMatch();

	}

	/**
	 * Retrieves PP_Orders which:
	 * <ul>
	 * <li>are closed
	 * <li>have IsInvoicecandidate='N'
	 * <li>don't have an invoice candidate linked to them
	 * <li>reference an unprocessed M_Material_tracking
	 * </ul>
	 */
	/* package */Iterator<de.metas.materialtracking.model.I_PP_Order> retrievePPOrdersWithMissingICs(@NonNull final QueryLimit limit)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_PP_Order> ppOrderQueryBuilder = queryBL.createQueryBuilder(I_PP_Order.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter();

		//
		// Only those manufacturing orders which are invoiceable
		ppOrderQueryBuilder.filter(getPP_OrderInvoiceableFilter(PlainContextAware.newWithThreadInheritedTrx()));

		//
		// Order by
		// (just to have a predictable order)
		ppOrderQueryBuilder.orderBy()
				.addColumn(I_PP_Order.COLUMN_PP_Order_ID);

		//
		// Execute query and return
		return ppOrderQueryBuilder
				.setLimit(limit)
				.create()
				.iterate(de.metas.materialtracking.model.I_PP_Order.class);
	}
}
