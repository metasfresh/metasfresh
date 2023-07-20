package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.costing.CostElementId;
import de.metas.order.costs.calculation_methods.CostCalculationMethod;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_Cost_Type;
import org.springframework.stereotype.Repository;

@Repository
public class OrderCostTypeRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, OrderCostTypeMap> cache = CCache.<Integer, OrderCostTypeMap>builder()
			.tableName(I_C_Cost_Type.Table_Name)
			.initialCapacity(1)
			.build();

	public OrderCostType getById(@NonNull OrderCostTypeId id)
	{
		return getMap().getById(id);
	}

	private OrderCostTypeMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private OrderCostTypeMap retrieveMap()
	{
		final ImmutableList<OrderCostType> list = queryBL.createQueryBuilder(I_C_Cost_Type.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(OrderCostTypeRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new OrderCostTypeMap(list);
	}

	private static OrderCostType fromRecord(@NonNull final I_C_Cost_Type record)
	{
		return OrderCostType.builder()
				.id(OrderCostTypeId.ofRepoId(record.getC_Cost_Type_ID()))
				.code(record.getValue())
				.name(record.getName())
				.distributionMethod(CostDistributionMethod.ofCode(record.getCostDistributionMethod()))
				.calculationMethod(CostCalculationMethod.ofCode(record.getCostCalculationMethod()))
				.costElementId(CostElementId.ofRepoId(record.getM_CostElement_ID()))
				.invoiceableProductId(record.isAllowInvoicing() ? ProductId.ofRepoId(record.getM_Product_ID()) : null)
				.build();
	}

	//
	//
	//

	private static class OrderCostTypeMap
	{
		private final ImmutableMap<OrderCostTypeId, OrderCostType> byId;

		public OrderCostTypeMap(final ImmutableList<OrderCostType> list)
		{
			this.byId = Maps.uniqueIndex(list, OrderCostType::getId);
		}

		public OrderCostType getById(@NonNull final OrderCostTypeId id)
		{
			return Check.assumeNotNull(byId.get(id), "No cost type found for {}", id);
		}
	}

}
