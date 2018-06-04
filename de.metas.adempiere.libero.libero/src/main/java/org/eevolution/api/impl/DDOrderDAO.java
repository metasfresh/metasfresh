package org.eevolution.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_MovementLine;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPDAO;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.LiberoException;

public class DDOrderDAO implements IDDOrderDAO
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public List<I_DD_OrderLine> retrieveLines(final I_DD_Order ddOrder)
	{
		Check.assumeNotNull(ddOrder, "ddOrder not null");

		final IQueryBuilder<I_DD_OrderLine> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DD_OrderLine.class, ddOrder)
				.addEqualsFilter(I_DD_OrderLine.COLUMN_DD_Order_ID, ddOrder.getDD_Order_ID())
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_DD_OrderLine.COLUMN_Line)
				.addColumn(I_DD_OrderLine.COLUMN_DD_OrderLine_ID);

		final List<I_DD_OrderLine> ddOrderLines = queryBuilder.create()
				.list(I_DD_OrderLine.class);

		// Optimization: set DD_Order_ID link
		for (final I_DD_OrderLine ddOrderLine : ddOrderLines)
		{
			ddOrderLine.setDD_Order(ddOrder);
		}

		return ddOrderLines;
	}	// getLines

	@Override
	public IQueryFilter<I_DD_Order> getDDOrdersForTargetWarehouseQueryFilter(final int targetWarehouseId)
	{
		return new DDOrdersForTargetWarehouseQueryFilter(targetWarehouseId);
	}

	@Override
	public List<I_DD_OrderLine_Alternative> retrieveAllAlternatives(I_DD_OrderLine ddOrderLine)
	{
		Check.assumeNotNull(ddOrderLine, "ddOrderLine not null");

		final IQueryBuilder<I_DD_OrderLine_Alternative> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DD_OrderLine_Alternative.class, ddOrderLine)
				.addEqualsFilter(I_DD_OrderLine_Alternative.COLUMN_DD_OrderLine_ID, ddOrderLine.getDD_OrderLine_ID())
		// .addOnlyActiveRecordsFilter() // we are retrieving ALL
		;

		queryBuilder.orderBy()
				.addColumn(I_DD_OrderLine_Alternative.COLUMN_M_Product_ID);

		final List<I_DD_OrderLine_Alternative> ddOrderLineAlternatives = queryBuilder.create()
				.list(I_DD_OrderLine_Alternative.class);
		return ddOrderLineAlternatives;
	}

	@Override
	public <T extends I_M_MovementLine> List<T> retriveMovementLines(final I_DD_OrderLine ddOrderLine, Class<T> movementLineClass)
	{
		Check.assumeNotNull(ddOrderLine, "ddOrderLine not null");

		final IQueryBuilder<T> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(movementLineClass, ddOrderLine)
				.addEqualsFilter(I_M_MovementLine.COLUMNNAME_DD_OrderLine_ID, ddOrderLine.getDD_OrderLine_ID())
				.addEqualsFilter(I_M_MovementLine.COLUMNNAME_DD_OrderLine_Alternative_ID, null) // exclude alternatives
		;

		// Order By: just to have a predictible ordering
		queryBuilder.orderBy()
				.addColumn(I_M_MovementLine.COLUMNNAME_M_MovementLine_ID);

		return queryBuilder
				.create()
				.list(movementLineClass);
	}

	@Override
	public final IQueryBuilder<I_DD_OrderLine> retrieveForwardDDOrderLinesQuery(final I_DD_Order ddOrder)
	{
		Check.assumeNotNull(ddOrder, LiberoException.class, "ddOrder not null");
		final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

		final IQueryBuilder<I_DD_OrderLine> queryBuilder = mrpDAO
				// Retrieve MRP Supplies for DD_Order
				.retrieveQueryBuilder(ddOrder, X_PP_MRP.TYPEMRP_Supply, X_PP_MRP.ORDERTYPE_DistributionOrder)
				.createQueryBuilder()
				//
				// Collect MRP Demands which triggered DD Order supplies
				.andCollectChildren(I_PP_MRP_Alloc.COLUMN_PP_MRP_Supply_ID, I_PP_MRP_Alloc.class)
				.andCollect(I_PP_MRP_Alloc.COLUMN_PP_MRP_Demand_ID)
				.addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Demand) // just to be sure
				//
				// Collect DD_OrderLines from those MRP Demands
				.andCollect(I_PP_MRP.COLUMN_DD_OrderLine_ID);

		return queryBuilder;
	}

	@Override
	public final IQueryBuilder<I_DD_OrderLine> retrieveBackwardDDOrderLinesQuery(final I_DD_Order ddOrder)
	{
		Check.assumeNotNull(ddOrder, LiberoException.class, "ddOrder not null");
		final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

		final IQueryBuilder<I_DD_OrderLine> queryBuilder = mrpDAO
				// Retrieve MRP Demands
				.retrieveQueryBuilder(ddOrder, X_PP_MRP.TYPEMRP_Demand, X_PP_MRP.ORDERTYPE_DistributionOrder)
				.createQueryBuilder()
				//
				// Collect MRP Supplies created to balance this MRP demand
				.andCollectChildren(I_PP_MRP_Alloc.COLUMN_PP_MRP_Demand_ID, I_PP_MRP_Alloc.class)
				.andCollect(I_PP_MRP_Alloc.COLUMN_PP_MRP_Supply_ID)
				.addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Supply) // just to be sure
				//
				// Collect DD_OrderLines from those MRP Demands
				.andCollect(I_PP_MRP.COLUMN_DD_OrderLine_ID);

		return queryBuilder;
	}

	@Override
	public final IQueryBuilder<I_DD_OrderLine> retrieveBackwardDDOrderLinesQuery(final I_M_Forecast forecast)
	{
		Check.assumeNotNull(forecast, LiberoException.class, "forecast not null");
		final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

		final IQueryBuilder<I_DD_OrderLine> queryBuilder = mrpDAO
				// Retrieve MRP Demands for document
				.retrieveQueryBuilder(forecast, X_PP_MRP.TYPEMRP_Demand, X_PP_MRP.ORDERTYPE_Forecast)
				.createQueryBuilder()
				//
				// Collect MRP Supplies created to balance this MRP demand
				.andCollectChildren(I_PP_MRP_Alloc.COLUMN_PP_MRP_Demand_ID, I_PP_MRP_Alloc.class)
				.andCollect(I_PP_MRP_Alloc.COLUMN_PP_MRP_Supply_ID)
				.addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Supply) // just to be sure
				//
				// Collect DD_OrderLines from those MRP Demands
				.andCollect(I_PP_MRP.COLUMN_DD_OrderLine_ID);

		return queryBuilder;
	}

	@Override
	public IQueryBuilder<I_DD_OrderLine> retrieveForwardDDOrderLinesQuery(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, LiberoException.class, "ppOrder not null");
		final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

		//
		// Retrieve supply from this manufacturing order
		final I_PP_MRP mrpSupply = mrpDAO.retrieveQueryBuilder(ppOrder, X_PP_MRP.TYPEMRP_Supply, X_PP_MRP.ORDERTYPE_ManufacturingOrder)
				.firstOnly();

		//
		// Corner case: in some circumstances, there is no MRP supply generated (maybe the Qty was zero or it was some error while generating it)
		// => return an query builder which actually returns no result.
		if (mrpSupply == null)
		{
			final LiberoException ex = new LiberoException("No MRP supply record found for " + ppOrder);
			logger.warn(ex.getLocalizedMessage() + " [SKIPPED]", ex);
			// NOTE: we are returing a query builder which actually does nothing
			return Services.get(IQueryBL.class)
					.createQueryBuilder(I_DD_OrderLine.class, ppOrder)
					.filter(ConstantQueryFilter.<I_DD_OrderLine> of(false));
		}

		return mrpDAO.retrieveForwardMRPDemandsForSupplyQuery(mrpSupply)
				.andCollect(I_PP_MRP.COLUMN_DD_OrderLine_ID);
	}


}
