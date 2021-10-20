package org.eevolution.api.impl;

import de.metas.material.planning.pporder.LiberoException;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Forecast;
import org.eevolution.api.DDOrderLineId;
import org.eevolution.api.DDOrderQuery;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPDAO;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

public class DDOrderDAO implements IDDOrderDAO
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_DD_Order getById(final int ddOrderId)
	{
		return InterfaceWrapperHelper.load(ddOrderId, I_DD_Order.class);
	}

	@Override
	public List<I_DD_OrderLine> retrieveLines(final I_DD_Order ddOrder)
	{
		Check.assumeNotNull(ddOrder, "ddOrder not null");

		final IQueryBuilder<I_DD_OrderLine> queryBuilder = queryBL
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
	}    // getLines

	@Override
	public List<I_DD_OrderLine_Alternative> retrieveAllAlternatives(final I_DD_OrderLine ddOrderLine)
	{
		Check.assumeNotNull(ddOrderLine, "ddOrderLine not null");

		final IQueryBuilder<I_DD_OrderLine_Alternative> queryBuilder = queryBL
				.createQueryBuilder(I_DD_OrderLine_Alternative.class, ddOrderLine)
				.addEqualsFilter(I_DD_OrderLine_Alternative.COLUMN_DD_OrderLine_ID, ddOrderLine.getDD_OrderLine_ID())
				// .addOnlyActiveRecordsFilter() // we are retrieving ALL
				;

		queryBuilder.orderBy()
				.addColumn(I_DD_OrderLine_Alternative.COLUMN_M_Product_ID);

		return queryBuilder.create()
				.list(I_DD_OrderLine_Alternative.class);
	}

	@Override
	public final IQueryBuilder<I_DD_OrderLine> retrieveForwardDDOrderLinesQuery(final I_DD_Order ddOrder)
	{
		Check.assumeNotNull(ddOrder, LiberoException.class, "ddOrder not null");
		final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

		return mrpDAO
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
	}

	@Override
	public final IQueryBuilder<I_DD_OrderLine> retrieveBackwardDDOrderLinesQuery(final I_DD_Order ddOrder)
	{
		Check.assumeNotNull(ddOrder, LiberoException.class, "ddOrder not null");
		final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

		return mrpDAO
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
	}

	@Override
	public final IQueryBuilder<I_DD_OrderLine> retrieveBackwardDDOrderLinesQuery(final I_M_Forecast forecast)
	{
		Check.assumeNotNull(forecast, LiberoException.class, "forecast not null");
		final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

		return mrpDAO
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
	}

	@Override
	public void save(@NonNull final I_DD_Order ddOrder)
	{
		saveRecord(ddOrder);
	}

	@Override
	public void save(@NonNull final I_DD_OrderLine ddOrderLine)
	{
		saveRecord(ddOrderLine);
	}

	@Override
	public void save(@NonNull final I_DD_OrderLine_Alternative ddOrderLineAlternative)
	{
		saveRecord(ddOrderLineAlternative);
	}

	@Override
	public void save(@NonNull final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlternative)
	{
		saveRecord(ddOrderLineOrAlternative);
	}

	@Override
	@NonNull
	public I_DD_OrderLine getLineById(@NonNull final DDOrderLineId ddOrderLineID)
	{
		final I_DD_OrderLine record = InterfaceWrapperHelper.load(ddOrderLineID, I_DD_OrderLine.class);

		if (record == null)
		{
			throw new AdempiereException("@NotFound@")
					.appendParametersToMessage()
					.setParameter("DD_OrderLine_ID", ddOrderLineID);
		}

		return record;
	}

	@Override
	public Stream<I_DD_Order> streamDDOrders(final DDOrderQuery query)
	{
		return toSqlQuery(query)
				.create()
				.iterateAndStream();
	}

	private IQueryBuilder<I_DD_Order> toSqlQuery(final DDOrderQuery query)
	{
		final IQueryBuilder<I_DD_Order> queryBuilder = queryBL.createQueryBuilder(I_DD_Order.class);
		setOrderBys(queryBuilder, query.getOrderBys());

		//
		// DocStatus
		if (query.getDocStatus() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, query.getDocStatus());
		}

		//
		// Responsible
		query.getResponsibleId().appendFilter(queryBuilder, I_DD_Order.COLUMNNAME_AD_User_Responsible_ID);

		return queryBuilder;
	}

	private void setOrderBys(
			@NonNull IQueryBuilder<I_DD_Order> queryBuilder,
			@Nullable List<DDOrderQuery.OrderBy> orderBys)
	{
		if (orderBys != null && !orderBys.isEmpty())
		{
			orderBys.forEach(orderBy -> addOrderBy(queryBuilder, orderBy));
		}

		// just to have something predictable
		queryBuilder.orderBy(I_DD_Order.COLUMNNAME_DD_Order_ID);
	}

	private void addOrderBy(
			@NonNull final IQueryBuilder<I_DD_Order> queryBuilder,
			@NonNull final DDOrderQuery.OrderBy orderBy)
	{
		if (orderBy == DDOrderQuery.OrderBy.PriorityRule)
		{
			queryBuilder.orderBy(I_DD_Order.COLUMNNAME_PriorityRule);
		}
		else if (orderBy == DDOrderQuery.OrderBy.DatePromised)
		{
			queryBuilder.orderBy(I_DD_Order.COLUMNNAME_DatePromised);
		}
		else
		{
			throw new AdempiereException("Unknown order by: " + orderBy);
		}
	}

}
