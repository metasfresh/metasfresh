package de.metas.material.planning.pporder.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_M_Product;
import org.compiere.model.Query;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOM;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Cost_Collector;
import org.eevolution.model.X_PP_Order_BOMLine;

import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.LiberoException;

public class PPOrderBOMDAO implements IPPOrderBOMDAO
{
	@Override
	public I_PP_Order_BOM retrieveOrderBOM(final I_PP_Order order)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_BOM.class, order)
				.filter(new EqualsQueryFilter<I_PP_Order_BOM>(I_PP_Order_BOM.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID()))
				.create()
				// .setOnlyActiveRecords(true) // we shall have only active records anyway
				.firstOnly(I_PP_Order_BOM.class);
	}

	@Override
	public List<I_PP_Order_BOMLine> retrieveOrderBOMLines(final I_PP_Order order)
	{
		return retrieveOrderBOMLines(order, I_PP_Order_BOMLine.class);
	}

	@Override
	public <T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLines(final I_PP_Order order, final Class<T> orderBOMLineClass)
	{
		Check.assumeNotNull(orderBOMLineClass, "orderBOMLineClass not null");
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<T> queryBuilder = queryBL.createQueryBuilder(orderBOMLineClass, order)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID())
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_PP_Order_BOMLine.COLUMNNAME_Line, Direction.Ascending, Nulls.Last);
		final List<T> orderBOMLines = queryBuilder
				.create()
				.list(orderBOMLineClass);

		// Optimization: set parent link
		for (final T orderBOMLine : orderBOMLines)
		{
			orderBOMLine.setPP_Order(order);
		}

		return orderBOMLines;
	}

	@Override
	public <T extends I_PP_Order_BOMLine> List<T> retrieveOrderBOMLineAlternatives(final I_PP_Order_BOMLine orderBOMLine, final Class<T> orderBOMLineClass)
	{
		Check.assumeNotNull(orderBOMLine, "orderBOMLine not null");
		Check.assumeNotNull(orderBOMLineClass, "orderBOMLineClass not null");

		//
		// Make sure we are not going to retrieve alternatives for an alternative
		if (X_PP_Order_BOMLine.COMPONENTTYPE_Variant.equals(orderBOMLine.getComponentType()))
		{
			throw new LiberoException("Cannot get alternative order BOM lines for an alternative line: " + orderBOMLine);
		}

		//
		// If BOM Line does not have VariantGroup set it means it does not have alternatives, so return empty right away
		final String variantGroup = orderBOMLine.getVariantGroup();
		if (Check.isEmpty(variantGroup, true))
		{
			return Collections.emptyList();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<T> queryBuilder = queryBL.createQueryBuilder(orderBOMLineClass, orderBOMLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, orderBOMLine.getPP_Order_ID())
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_ComponentType, X_PP_Order_BOMLine.COMPONENTTYPE_Variant)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_VariantGroup, variantGroup);

		queryBuilder.orderBy()
				.addColumn(I_PP_Order_BOMLine.COLUMNNAME_Line, Direction.Ascending, Nulls.Last);

		final List<T> orderBOMLineAlternatives = queryBuilder
				.create()
				.list(orderBOMLineClass);

		// Optimization: set parent link
		if (!orderBOMLineAlternatives.isEmpty())
		{
			final I_PP_Order ppOrder = orderBOMLine.getPP_Order();
			for (final T orderBOMLineAlt : orderBOMLineAlternatives)
			{
				orderBOMLineAlt.setPP_Order(ppOrder);
			}
		}

		return orderBOMLineAlternatives;
	}

	@Override
	public I_PP_Order_BOMLine retrieveComponentBOMLineForAlternative(final I_PP_Order_BOMLine orderBOMLineAlternative)
	{
		Check.assumeNotNull(orderBOMLineAlternative, "orderBOMLineAlternative not null");

		// Make sure we are dealing with an alternative BOM Line
		if (!X_PP_Order_BOMLine.COMPONENTTYPE_Variant.equals(orderBOMLineAlternative.getComponentType()))
		{
			throw new LiberoException("Order BOM Line is not an component alternative: "+orderBOMLineAlternative);
		}

		// If our Alternative BOM Line does not have VariantGroup => error because it's not consistent
		final String variantGroup = orderBOMLineAlternative.getVariantGroup();
		if (Check.isEmpty(variantGroup, true))
		{
			throw new LiberoException("Alternative BOM Line does not have a VariantGroup set: "+orderBOMLineAlternative);
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_PP_Order_BOMLine> queryBuilder = queryBL.createQueryBuilder(I_PP_Order_BOMLine.class, orderBOMLineAlternative)
				.addOnlyActiveRecordsFilter()
				// Same manufacturing order
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, orderBOMLineAlternative.getPP_Order_ID())
				// Exclude the alternatives/variants lines
				.addNotEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_ComponentType, X_PP_Order_BOMLine.COMPONENTTYPE_Variant)
				// For our VariantGroup
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_VariantGroup, variantGroup);

		// Get the Component BOM Line
		// NOTE: we assume there is ONLY one
		final I_PP_Order_BOMLine orderBOMLineComponent = queryBuilder.create().firstOnly(I_PP_Order_BOMLine.class);
		return orderBOMLineComponent;
	}

	@Override
	public I_PP_Order_BOMLine retrieveOrderBOMLine(final I_PP_Order ppOrder, final I_M_Product product)
	{
		Check.assumeNotNull(ppOrder, LiberoException.class, "ppOrder not null");
		Check.assumeNotNull(product, LiberoException.class, "product not null");

		return Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_BOMLine.class, ppOrder)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_PP_Order_BOMLine.class);
	}

	@Override
	public List<I_PP_Order_BOMLine> retrieveAllOrderBOMLines(final I_PP_Order_BOM orderBOM)
	{
		final IQueryBuilder<I_PP_Order_BOMLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_PP_Order_BOMLine.class, orderBOM)
				.addEqualsFilter(I_PP_Order_BOMLine.COLUMN_PP_Order_BOM_ID, orderBOM.getPP_Order_BOM_ID())
		// .addOnlyActiveRecordsFilter()
		;

		queryBuilder.orderBy()
				.addColumn(I_PP_Order_BOMLine.COLUMN_Line, Direction.Ascending, Nulls.Last);
		return queryBuilder
				.create()
				.list(I_PP_Order_BOMLine.class);
	}

	@Override
	public int retrieveNextLineNo(final I_PP_Order order)
	{
		final IQueryBuilder<I_PP_Order_BOMLine> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_Order_BOMLine.class, order);

		final ICompositeQueryFilter<I_PP_Order_BOMLine> filters = queryBuilder.getCompositeFilter();
		filters.addEqualsFilter(I_PP_Order_BOMLine.COLUMNNAME_PP_Order_ID, order.getPP_Order_ID());

		Integer maxLine = queryBuilder
				.create()
				.aggregate(I_PP_Order_BOMLine.COLUMNNAME_Line, Aggregate.MAX, Integer.class);

		if (maxLine == null)
		{
			maxLine = 0;
		}

		final int nextLine = maxLine + 10;
		return nextLine;
	}

	@Override
	public BigDecimal retrieveQtyUsageVariance(final I_PP_Order_BOMLine orderBOMLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderBOMLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderBOMLine);

		final String whereClause = I_PP_Cost_Collector.COLUMNNAME_PP_Order_BOMLine_ID + "=?"
				+ " AND " + I_PP_Cost_Collector.COLUMNNAME_PP_Order_ID + "=?"
				+ " AND " + I_PP_Cost_Collector.COLUMNNAME_DocStatus + " IN (?,?)"
				+ " AND " + I_PP_Cost_Collector.COLUMNNAME_CostCollectorType + "=?";
		BigDecimal qtyUsageVariance = new Query(ctx, I_PP_Cost_Collector.Table_Name, whereClause, trxName)
				.setParameters(new Object[] {
						orderBOMLine.getPP_Order_BOMLine_ID(),
						orderBOMLine.getPP_Order_ID(),
						X_PP_Cost_Collector.DOCSTATUS_Completed,
						X_PP_Cost_Collector.DOCSTATUS_Closed,
						X_PP_Cost_Collector.COSTCOLLECTORTYPE_UsegeVariance
				})
				.sum(I_PP_Cost_Collector.COLUMNNAME_MovementQty);
		//
		return qtyUsageVariance;
	}

}
