package de.metas.order.costs;

import com.google.common.collect.ImmutableSet;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order_Cost;
import org.compiere.model.I_C_Order_Cost_Detail;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Repository
public class OrderCostRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private OrderCostRepositorySession newSession()
	{
		return new OrderCostRepositorySession(queryBL);
	}

	public List<OrderCost> getByOrderId(@NonNull final OrderId orderId)
	{
		return newSession().getByOrderId(orderId);
	}

	public List<OrderCost> getByOrderIds(@NonNull final Set<OrderId> orderIds)
	{
		return newSession().getByOrderIds(orderIds);
	}

	public List<OrderCost> getByOrderLineIds(@NonNull final Set<OrderLineId> orderLineIds)
	{
		return newSession().getByOrderLineIds(orderLineIds);
	}

	public OrderCost getById(@NonNull final OrderCostId orderCostId)
	{
		return CollectionUtils.singleElement(getByIds(ImmutableSet.of(orderCostId)));
	}

	public List<OrderCost> getByIds(@NonNull final Collection<OrderCostId> orderCostIds)
	{
		return newSession().getByIds(orderCostIds);
	}

	public void saveAll(final Collection<OrderCost> orderCostsList)
	{
		newSession().saveAll(orderCostsList);
	}

	public void save(final OrderCost orderCost)
	{
		newSession().save(orderCost);
	}

	public void changeByOrderLineId(@NonNull final OrderLineId orderLineId, @NonNull final Consumer<OrderCost> consumer)
	{
		newSession().changeByOrderLineId(orderLineId, consumer);
	}

	public void deleteDetails(@NonNull final OrderCostId orderCostId)
	{
		queryBL.createQueryBuilder(I_C_Order_Cost_Detail.class)
				.addEqualsFilter(I_C_Order_Cost_Detail.COLUMNNAME_C_Order_Cost_ID, orderCostId)
				.create()
				.delete();
	}

	public boolean hasCostsByCreatedOrderLineIds(final ImmutableSet<OrderLineId> orderLineIds)
	{
		if (orderLineIds.isEmpty())
		{
			return false;
		}

		return queryBL.createQueryBuilder(I_C_Order_Cost.class)
				.addInArrayFilter(I_C_Order_Cost.COLUMNNAME_Created_OrderLine_ID, orderLineIds)
				.create()
				.anyMatch();
	}

	public void deleteByCreatedOrderLineId(@NonNull final OrderAndLineId createdOrderLineId)
	{
		final I_C_Order_Cost orderCostRecord = queryBL.createQueryBuilder(I_C_Order_Cost.class)
				.addEqualsFilter(I_C_Order_Cost.COLUMNNAME_C_Order_ID, createdOrderLineId.getOrderId())
				.addEqualsFilter(I_C_Order_Cost.COLUMNNAME_Created_OrderLine_ID, createdOrderLineId.getOrderLineId())
				.create()
				.firstOnly();
		if (orderCostRecord == null)
		{
			return;
		}

		final OrderCostId orderCostId = OrderCostId.ofRepoId(orderCostRecord.getC_Order_Cost_ID());
		deleteDetails(orderCostId);

		InterfaceWrapperHelper.delete(orderCostRecord, false);
	}
}
