package de.metas.order.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadByIds;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;

import java.util.Collection;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.document.engine.DocStatus;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

public abstract class AbstractOrderDAO implements IOrderDAO
{
	@Override
	public I_C_Order getById(@NonNull final OrderId orderId)
	{
		return InterfaceWrapperHelper.load(orderId.getRepoId(), I_C_Order.class);
	}

	@Override
	public <T extends I_C_Order> T getById(
			@NonNull final OrderId orderId,
			@NonNull final Class<T> clazz)
	{
		return InterfaceWrapperHelper.load(orderId.getRepoId(), clazz);
	}

	@Override
	public I_C_OrderLine getOrderLineById(final int orderLineId)
	{
		return InterfaceWrapperHelper.load(orderLineId, I_C_OrderLine.class);
	}

	@Override
	public I_C_OrderLine getOrderLineById(@NonNull final OrderLineId orderLineId)
	{
		return getOrderLineById(orderLineId, I_C_OrderLine.class);
	}

	@Override
	public <T extends org.compiere.model.I_C_OrderLine> T getOrderLineById(@NonNull final OrderLineId orderLineId, @NonNull final Class<T> modelClass)
	{
		return InterfaceWrapperHelper.load(orderLineId.getRepoId(), modelClass);
	}

	@Override
	public Map<OrderAndLineId, I_C_OrderLine> getOrderLinesByIds(final Collection<OrderAndLineId> orderAndLineIds)
	{
		if (orderAndLineIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		return loadByIds(OrderAndLineId.getOrderLineRepoIds(orderAndLineIds), I_C_OrderLine.class)
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(orderLineRecord -> OrderAndLineId.ofRepoIds(orderLineRecord.getC_Order_ID(), orderLineRecord.getC_OrderLine_ID())));
	}

	@Override
	public List<I_C_OrderLine> retrieveOrderLines(final I_C_Order order)
	{
		return retrieveOrderLines(order, I_C_OrderLine.class);
	}

	@Override
	public <T extends org.compiere.model.I_C_OrderLine> List<T> retrieveOrderLines(
			@NonNull final I_C_Order order,
			@NonNull final Class<T> clazz)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(order.getC_Order_ID());
		if(orderId == null)
		{
			return ImmutableList.of();
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(order);
		final String trxName = InterfaceWrapperHelper.getTrxName(order);
		final List<T> orderLines = retrieveOrderLines(ctx, orderId, trxName, clazz);
		orderLines.forEach(orderLine -> orderLine.setC_Order(order));
		return orderLines;
	}

	@Override
	public List<I_C_OrderLine> retrieveOrderLines(final OrderId orderId)
	{
		return retrieveOrderLines(Env.getCtx(), orderId, ITrx.TRXNAME_ThreadInherited, I_C_OrderLine.class);
	}

	// tsa: commented out because it's not safe to cache the list of order lines and return it without even cloning.
	// @Cached(cacheName = I_C_OrderLine.Table_Name + "#via#" + I_C_OrderLine.COLUMNNAME_C_Order_ID)
	public <T extends org.compiere.model.I_C_OrderLine> List<T> retrieveOrderLines(
			@CacheCtx final Properties ctx,
			@NonNull final OrderId orderId,
			@CacheTrx final String trxName,
			@NonNull final Class<T> clazz)
	{
		final List<T> orderLines = Services.get(IQueryBL.class)
				.createQueryBuilder(org.compiere.model.I_C_OrderLine.class, ctx, trxName)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMN_C_Order_ID, orderId)
				.orderBy()
				.addColumn(org.compiere.model.I_C_OrderLine.COLUMN_Line)
				.addColumn(org.compiere.model.I_C_OrderLine.COLUMN_C_OrderLine_ID)
				.endOrderBy()
				.create()
				.list(clazz);

		return orderLines;
	}

	@Override
	public final List<Integer> retrieveAllOrderLineIds(final I_C_Order order)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(org.compiere.model.I_C_OrderLine.class, order)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMN_C_Order_ID, order.getC_Order_ID())
				.create()
				.listIds();
	}

	@Override
	public <T extends org.compiere.model.I_C_OrderLine> T retrieveOrderLine(final I_C_Order order, final int lineNo, final Class<T> clazz)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(org.compiere.model.I_C_OrderLine.class, order)
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMN_C_Order_ID, order.getC_Order_ID())
				.addEqualsFilter(org.compiere.model.I_C_OrderLine.COLUMN_Line, lineNo)
				.create()
				.firstOnly(clazz);
	}

	@Override
	public boolean hasCompletedOrders(final Properties ctx, final int bpartnerId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Order.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Order.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addInArrayOrAllFilter(I_C_Order.COLUMNNAME_DocStatus, DocStatus.Completed, DocStatus.Closed)
				.create()
				.match();
	}

	@Override
	public List<I_M_InOut> retrieveInOuts(final I_C_Order order)
	{
		return retrieveInOutsQuery(order)
				.create()
				.list(I_M_InOut.class);
	}

	@Override
	public boolean hasInOuts(final I_C_Order order)
	{
		return retrieveInOutsQuery(order)
				.create()
				.match();
	}

	private IQueryBuilder<I_M_InOut> retrieveInOutsQuery(final I_C_Order order)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_InOut.class, order)
				.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.filterByClientId()
				.addOnlyActiveRecordsFilter()
				.orderByDescending(org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID);
	}

	@Override
	public Set<UserId> retriveOrderCreatedByUserIds(@NonNull final Collection<Integer> orderIds)
	{
		if (orderIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order.class)
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, orderIds)
				.create()
				.listDistinct(I_C_Order.COLUMNNAME_CreatedBy, Integer.class)
				.stream()
				.map(UserId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public List<I_C_Order> getByIds(final Collection<OrderId> orderIds)
	{
		return getByIds(orderIds, I_C_Order.class);
	}

	@Override
	public <T extends I_C_Order> List<T> getByIds(Collection<OrderId> orderIds, Class<T> clazz)
	{
		return loadByRepoIdAwares(ImmutableSet.copyOf(orderIds), clazz);
	}

	@Override
	public Stream<OrderId> streamOrderIdsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.create()
				.listIds(OrderId::ofRepoId)
				.stream();
	}

	@Override
	public void delete(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		InterfaceWrapperHelper.delete(orderLine);
	}

	@Override
	public void save(@NonNull final org.compiere.model.I_C_Order order)
	{
		InterfaceWrapperHelper.save(order);
	}

	@Override
	public void save(@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		InterfaceWrapperHelper.save(orderLine);
	}
}
