package de.metas.order.process.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import de.metas.document.engine.IDocument;
import de.metas.order.model.I_C_Order;
import de.metas.order.process.IC_Order_CreatePOFromSOsDAO;

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

public class C_Order_CreatePOFromSOsDAO implements IC_Order_CreatePOFromSOsDAO
{

	private final ICompositeQueryFilter<I_C_OrderLine> additionalFilters;

	public C_Order_CreatePOFromSOsDAO()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		additionalFilters = queryBL.createCompositeQueryFilter(I_C_OrderLine.class);
		additionalFilters.setJoinAnd();
	}

	@Override
	public Iterator<I_C_Order> createSalesOrderIterator(
			final IContextAware context,
			final boolean allowMultiplePOOrders,
			final int c_Order_ID,
			final int c_BPartner_ID,
			final int vendor_ID,
			final String poReference,
			final Timestamp datePromised_From,
			final Timestamp datePromised_To)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Order> orderQueryBuilder = queryBL.createQueryBuilder(I_C_Order.class, context)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order.COLUMNNAME_IsSOTrx, true)

				// note: no point to include closed orders, because they have no open qtys left,
				// and we will only create purchase orders for the still open qtys.
				.addEqualsFilter(I_C_Order.COLUMNNAME_DocStatus, IDocument.STATUS_Completed);

		// task 07228
		if (!allowMultiplePOOrders)
		{
			// exclude sales orders that are already linked to a purchase order
			orderQueryBuilder
					.addEqualsFilter(I_C_Order.COLUMNNAME_Link_Order_ID, null);
		}
		if (c_Order_ID > 0)
		{
			orderQueryBuilder.addEqualsFilter(I_C_Order.COLUMNNAME_C_Order_ID, c_Order_ID);
		}
		else
		{
			if (c_BPartner_ID > 0)
			{
				orderQueryBuilder.addEqualsFilter(I_C_Order.COLUMNNAME_C_BPartner_ID, c_BPartner_ID);
			}
			if (vendor_ID > 0)
			{
				final ICompositeQueryFilter<I_C_BPartner_Product> bpProductFilter = queryBL.createCompositeQueryFilter(I_C_BPartner_Product.class)
						.setJoinOr()
						.addEqualsFilter(I_C_BPartner_Product.COLUMN_C_BPartner_ID, vendor_ID)
						.addEqualsFilter(I_C_BPartner_Product.COLUMN_C_BPartner_Vendor_ID, vendor_ID);

				final IQuery<I_M_Product> productFilter = queryBL.createQueryBuilder(I_C_BPartner_Product.class, context)
						.addOnlyActiveRecordsFilter()
						.filter(bpProductFilter)
						.andCollect(I_M_Product.COLUMN_M_Product_ID, I_M_Product.class)
						.addOnlyActiveRecordsFilter()
						.create();

				final IQuery<I_C_OrderLine> bpProductOrderLineQuery = queryBL.createQueryBuilder(I_C_OrderLine.class, context)
						.addOnlyActiveRecordsFilter()
						.addInSubQueryFilter(I_C_OrderLine.COLUMNNAME_M_Product_ID, I_M_Product.COLUMNNAME_M_Product_ID, productFilter)
						.create();

				orderQueryBuilder
						.addInSubQueryFilter(I_C_Order.COLUMNNAME_C_Order_ID, I_C_OrderLine.COLUMNNAME_C_Order_ID, bpProductOrderLineQuery);
			}
			if (!Check.isEmpty(poReference, true))
			{
				orderQueryBuilder
						.addEqualsFilter(I_C_Order.COLUMNNAME_POReference, poReference);
			}

			// the time of the orders' datePromised is usually 23:59.
			// if a user set datePromised_From = nov-19th and datePromised_To = nov-19th, they expect any order with a datepromised on that date
			final DateTruncQueryFilterModifier dateTruncModifier = DateTruncQueryFilterModifier.forTruncString(TimeUtil.TRUNC_DAY);
			if (datePromised_From != null)
			{
				orderQueryBuilder
						.addCompareFilter(I_C_Order.COLUMNNAME_DatePromised, Operator.GREATER_OR_EQUAL, datePromised_From, dateTruncModifier);
			}
			if (datePromised_To != null)
			{
				orderQueryBuilder
						.addCompareFilter(I_C_Order.COLUMNNAME_DatePromised, Operator.LESS_OR_EQUAL, datePromised_To, dateTruncModifier);
			}
		}

		return orderQueryBuilder
				.orderBy().addColumn(I_C_Order.COLUMNNAME_C_Order_ID).endOrderBy()
				.create()
				.iterate(I_C_Order.class);
	}

	@Override
	public List<I_C_OrderLine> retrieveOrderLines(final I_C_Order order,
			final boolean allowMultiplePOOrders,
			final String purchaseQtySource)
	{
		Check.assumeNotNull(order, "Param order is not null");
		Check.assumeNotEmpty(purchaseQtySource, "Param purchaseQtySource is not empty");
		Check.assume(I_C_OrderLine.COLUMNNAME_QtyOrdered.equals(purchaseQtySource) || I_C_OrderLine.COLUMNNAME_QtyReserved.equals(purchaseQtySource),
				"Param purchaseQtySource={} needs to be either {} or {}",
				purchaseQtySource, I_C_OrderLine.COLUMNNAME_QtyOrdered, I_C_OrderLine.COLUMNNAME_QtyReserved
				);

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_OrderLine> orderLineQueryBuilder = queryBL.createQueryBuilder(I_C_OrderLine.class, order)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.addCompareFilter(purchaseQtySource, Operator.GREATER, BigDecimal.ZERO)
				.filter(additionalFilters);

		if (!allowMultiplePOOrders)
		{
			// exclude sales order lines that are already linked to a purchase order line
			orderLineQueryBuilder
					.addEqualsFilter(I_C_OrderLine.COLUMNNAME_Link_OrderLine_ID, null);
		}

		return orderLineQueryBuilder

				.orderBy().addColumn(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID).endOrderBy()
				.create()
				.list(I_C_OrderLine.class);
	}

	@Override
	public void addAdditionalOrderLinesFilter(final IQueryFilter<I_C_OrderLine> filter)
	{
		additionalFilters.addFilter(filter);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
