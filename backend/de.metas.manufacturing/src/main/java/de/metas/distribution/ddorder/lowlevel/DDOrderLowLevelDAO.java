package de.metas.distribution.ddorder.lowlevel;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.DDOrderQuery;
import de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine_Or_Alternative;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryUpdater;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Forecast;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPDAO;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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

@Repository
public class DDOrderLowLevelDAO
{
	public static final ModelDynAttributeAccessor<I_DD_Order, MaterialDispoGroupId> ATTR_DDORDER_REQUESTED_EVENT_GROUP_ID = //
			new ModelDynAttributeAccessor<>(I_DD_Order.class.getName(), "DDOrderRequestedEvent_GroupId", MaterialDispoGroupId.class);

	public static final ModelDynAttributeAccessor<I_DD_Order, String> ATTR_DDORDER_REQUESTED_EVENT_TRACE_ID = //
			new ModelDynAttributeAccessor<>(I_DD_Order.class.getName(), "DDOrderRequestedEvent_TraceId", String.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public I_DD_Order getById(@NonNull final DDOrderId ddOrderId)
	{
		return InterfaceWrapperHelper.load(ddOrderId, I_DD_Order.class);
	}

	public List<I_DD_OrderLine> retrieveLines(@NonNull final I_DD_Order ddOrder)
	{
		final List<I_DD_OrderLine> ddOrderLines = queryBL
				.createQueryBuilder(I_DD_OrderLine.class, ddOrder)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, ddOrder.getDD_Order_ID())
				.addOnlyActiveRecordsFilter()
				.orderBy(I_DD_OrderLine.COLUMNNAME_Line)
				.orderBy(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID)
				.create()
				.list();

		// Optimization: set DD_Order_ID link
		for (final I_DD_OrderLine ddOrderLine : ddOrderLines)
		{
			ddOrderLine.setDD_Order(ddOrder);
		}

		return ddOrderLines;
	}

	public Stream<I_DD_OrderLine> streamLinesByDDOrderIds(@NonNull final Collection<DDOrderId> ddOrderIds)
	{
		if (ddOrderIds.isEmpty())
		{
			return Stream.empty();
		}

		return queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addInArrayFilter(I_DD_Order.COLUMNNAME_DD_Order_ID, ddOrderIds)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_DD_OrderLine.COLUMNNAME_DD_Order_ID)
				.orderBy(I_DD_OrderLine.COLUMNNAME_Line)
				.orderBy(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID)
				.create()
				.stream();
	}

	public List<I_DD_OrderLine_Alternative> retrieveAllAlternatives(final I_DD_OrderLine ddOrderLine)
	{
		Check.assumeNotNull(ddOrderLine, "ddOrderLine not null");

		final IQueryBuilder<I_DD_OrderLine_Alternative> queryBuilder = queryBL
				.createQueryBuilder(I_DD_OrderLine_Alternative.class, ddOrderLine)
				.addEqualsFilter(I_DD_OrderLine_Alternative.COLUMNNAME_DD_OrderLine_ID, ddOrderLine.getDD_OrderLine_ID())
				// .addOnlyActiveRecordsFilter() // we are retrieving ALL
				;

		queryBuilder.orderBy()
				.addColumn(I_DD_OrderLine_Alternative.COLUMNNAME_M_Product_ID);

		return queryBuilder.create()
				.list(I_DD_OrderLine_Alternative.class);
	}

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

	public void save(@NonNull final I_DD_Order ddOrder)
	{
		saveRecord(ddOrder);
	}

	public void save(@NonNull final I_DD_OrderLine ddOrderLine)
	{
		saveRecord(ddOrderLine);
	}

	public void save(@NonNull final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlternative)
	{
		saveRecord(ddOrderLineOrAlternative);
	}

	@NonNull
	public ImmutableList<I_DD_OrderLine> getLinesByIds(@NonNull final ImmutableSet<DDOrderLineId> ddOrderLineIds)
	{
		if (ddOrderLineIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addInArrayFilter(I_DD_OrderLine.COLUMNNAME_DD_OrderLine_ID, ddOrderLineIds)
				.create()
				.listImmutable(I_DD_OrderLine.class);
	}

	@NonNull
	public I_DD_OrderLine getLineById(@NonNull final DDOrderLineId ddOrderLineId)
	{
		final I_DD_OrderLine record = InterfaceWrapperHelper.load(ddOrderLineId, I_DD_OrderLine.class);

		if (record == null)
		{
			throw new AdempiereException("@NotFound@")
					.appendParametersToMessage()
					.setParameter("DD_OrderLine_ID", ddOrderLineId);
		}

		return record;
	}

	public Stream<I_DD_Order> streamDDOrders(final DDOrderQuery query)
	{
		return toSqlQuery(query)
				.create()
				.iterateAndStream();
	}

	public void deleteOrders(@NonNull final DeleteOrdersQuery deleteOrdersQuery)
	{
		final IQueryBuilder<I_DD_Order> deleteOrdersQueryBuilder = queryBL.createQueryBuilder(I_DD_Order.class);

		if (deleteOrdersQuery.isOnlySimulated())
		{
			deleteOrdersQueryBuilder.addEqualsFilter(I_DD_Order.COLUMNNAME_IsSimulated, deleteOrdersQuery.isOnlySimulated());
		}

		if (deleteOrdersQuery.getPpOrderBOMLineId() != null)
		{
			deleteOrdersQueryBuilder.addEqualsFilter(I_DD_Order.COLUMNNAME_Forward_PP_Order_BOMLine_ID, deleteOrdersQuery.getPpOrderBOMLineId());
		}

		if (deleteOrdersQuery.getSalesOrderLineId() != null)
		{
			final IQuery<I_DD_OrderLine> orderLineQuery = queryBL.createQueryBuilder(I_DD_OrderLine.class)
					.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_C_OrderLineSO_ID, deleteOrdersQuery.getSalesOrderLineId())
					.create();

			deleteOrdersQueryBuilder.addInSubQueryFilter(I_DD_Order.COLUMNNAME_DD_Order_ID, I_DD_OrderLine.COLUMNNAME_DD_Order_ID, orderLineQuery);
		}

		if (deleteOrdersQueryBuilder.getCompositeFilter().isEmpty())
		{
			throw new AdempiereException("Deleting all DD_Order records is not allowed!");
		}

		final boolean failIfProcessed = false;

		deleteOrdersQueryBuilder
				.create()
				.iterateAndStream()
				.peek(this::deleteLines)
				.forEach(simulatedOrder -> InterfaceWrapperHelper.delete(simulatedOrder, failIfProcessed));
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

		//
		// Warehouse From
		if (query.getWarehouseFromIds() != null && !query.getWarehouseFromIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_DD_Order.COLUMNNAME_M_Warehouse_From_ID, query.getWarehouseFromIds());
		}

		//
		// Warehouse To
		if (query.getWarehouseToIds() != null && !query.getWarehouseToIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_DD_Order.COLUMNNAME_M_Warehouse_To_ID, query.getWarehouseToIds());
		}

		//
		// Sales Order
		if (query.getSalesOrderIds() != null && !query.getSalesOrderIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_DD_Order.COLUMNNAME_C_Order_ID, query.getSalesOrderIds());
		}

		//
		// Manufacturing Order
		if (query.getManufacturingOrderIds() != null && !query.getManufacturingOrderIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_DD_Order.COLUMNNAME_Forward_PP_Order_ID, query.getManufacturingOrderIds());
		}

		//
		// Plant
		if (query.getPlantIds() != null && !query.getPlantIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_DD_Order.COLUMNNAME_PP_Plant_ID, query.getPlantIds());
		}

		//
		// Date Promised
		if (query.getDatesPromised() != null && !query.getDatesPromised().isEmpty())
		{
			final ICompositeQueryFilter<I_DD_Order> filter = queryBuilder.addCompositeQueryFilter().setJoinOr();
			for (final LocalDate datePromised : query.getDatesPromised())
			{
				filter.addEqualsFilter(I_DD_Order.COLUMNNAME_DatePromised, datePromised, DateTruncQueryFilterModifier.DAY);
			}
		}

		//
		// Line level filters
		{
			final ExtendedMemorizingSupplier<IQueryBuilder<I_DD_OrderLine>> lineQueryBuilderHolder = ExtendedMemorizingSupplier.of(() -> queryBL.createQueryBuilder(I_DD_OrderLine.class));

			//
			// Products
			if (query.getProductIds() != null && !query.getProductIds().isEmpty())
			{
				lineQueryBuilderHolder.getNotNull().addInArrayFilter(I_DD_OrderLine.COLUMNNAME_M_Product_ID, query.getProductIds());
			}

			//
			// Quantities Entered
			if (query.getQtysEntered() != null && !query.getQtysEntered().isEmpty())
			{
				final ICompositeQueryFilter<I_DD_OrderLine> qtysFilter = lineQueryBuilderHolder.getNotNull().addCompositeQueryFilter().setJoinOr();
				for (final Quantity qtyEntered : query.getQtysEntered())
				{
					qtysFilter.addCompositeQueryFilter()
							.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_QtyEntered, qtyEntered.toBigDecimal())
							.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_C_UOM_ID, qtyEntered.getUomId());
				}
			}

			//
			final IQueryBuilder<I_DD_OrderLine> lineQueryBuilder = lineQueryBuilderHolder.peek();
			if (lineQueryBuilder != null)
			{
				queryBuilder.addInSubQueryFilter(I_DD_Order.COLUMNNAME_DD_Order_ID, I_DD_OrderLine.COLUMNNAME_DD_Order_ID, lineQueryBuilder.create());
			}
		}

		//
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

	private void deleteLines(@NonNull final I_DD_Order order)
	{
		queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, order.getDD_Order_ID())
				.create()
				.deleteDirectly();
	}

	public void updateForwardPPOrderByIds(@NonNull final Set<DDOrderId> ddOrderIds, @Nullable final PPOrderId newPPOrderId)
	{
		if (ddOrderIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_DD_Order.class)
				.addInArrayFilter(I_DD_Order.COLUMNNAME_DD_Order_ID, ddOrderIds)
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_Forward_PP_Order_ID, newPPOrderId)
				.create()
				.update(ddOrder -> {
					ddOrder.setForward_PP_Order_ID(PPOrderId.toRepoId(newPPOrderId));
					return IQueryUpdater.MODEL_UPDATED;
				});
	}

	public Set<ProductId> getProductIdsByDDOrderIds(final Collection<DDOrderId> ddOrderIds)
	{
		if (ddOrderIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final List<ProductId> productIds = queryBL.createQueryBuilder(I_DD_OrderLine.class)
				.addInArrayFilter(I_DD_Order.COLUMNNAME_DD_Order_ID, ddOrderIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_DD_OrderLine.COLUMNNAME_M_Product_ID, ProductId.class);

		return ImmutableSet.copyOf(productIds);
	}

}
