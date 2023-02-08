package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.calculation_methods.CostCalculationMethod;
import de.metas.order.costs.calculation_methods.CostCalculationMethodParams;
import de.metas.order.costs.calculation_methods.FixedAmountCostCalculationMethodParams;
import de.metas.order.costs.calculation_methods.PercentageCostCalculationMethodParams;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order_Cost;
import org.compiere.model.I_C_Order_Cost_Detail;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public class OrderCostRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public List<OrderCost> getByOrderLineIds(@NonNull final Set<OrderLineId> orderLineIds)
	{
		if (orderLineIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList<OrderCostId> orderCostIds = queryBL.createQueryBuilder(I_C_Order_Cost_Detail.class)
				.addInArrayFilter(I_C_Order_Cost_Detail.COLUMNNAME_C_OrderLine_ID, orderLineIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_C_Order_Cost_Detail.COLUMNNAME_C_Order_Cost_ID, OrderCostId.class);

		return getByIds(orderCostIds);
	}

	public List<OrderCost> getByIds(@NonNull final Collection<OrderCostId> orderCostIds)
	{
		if (orderCostIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<I_C_Order_Cost> headerRecords = queryBL.createQueryBuilder(I_C_Order_Cost.class)
				.addInArrayFilter(I_C_Order_Cost.COLUMNNAME_C_Order_Cost_ID, orderCostIds)
				.create()
				.list();

		final ImmutableListMultimap<OrderCostId, I_C_Order_Cost_Detail> detailRecords = queryBL.createQueryBuilder(I_C_Order_Cost_Detail.class)
				.addInArrayFilter(I_C_Order_Cost_Detail.COLUMNNAME_C_Order_Cost_ID, orderCostIds)
				.create()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						detailRecord -> OrderCostId.ofRepoId(detailRecord.getC_Order_Cost_ID()),
						detailRecord -> detailRecord));

		return headerRecords.stream()
				.map(headerRecord -> fromRecord(headerRecord, detailRecords))
				.collect(ImmutableList.toImmutableList());
	}

	private static OrderCost fromRecord(
			@NonNull final I_C_Order_Cost record,
			@NonNull final ImmutableListMultimap<OrderCostId, I_C_Order_Cost_Detail> detailRecordsMap)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		final CostCalculationMethod calculationMethod = CostCalculationMethod.ofCode(record.getCostCalculationMethod());
		final CostCalculationMethodParams calculationMethodParams = calculationMethod.map(new CostCalculationMethod.CaseMapper<CostCalculationMethodParams>()
		{
			@Override
			public CostCalculationMethodParams fixedAmount()
			{
				return FixedAmountCostCalculationMethodParams.builder()
						.fixedAmount(Money.of(record.getCostCalculation_FixedAmount(), currencyId))
						.build();
			}

			@Override
			public CostCalculationMethodParams percentageOfAmount()
			{
				return PercentageCostCalculationMethodParams.builder()
						.percentage(Percent.of(record.getCostCalculation_Percentage()))
						.build();
			}
		});

		final OrderCostId orderCostId = OrderCostId.ofRepoId(record.getC_Order_Cost_ID());

		return OrderCost.builder()
				.id(orderCostId)
				.orderId(OrderId.ofRepoId(record.getC_Order_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.costTypeId(OrderCostTypeId.ofRepoId(record.getC_Cost_Type_ID()))
				.calculationMethod(calculationMethod)
				.calculationMethodParams(calculationMethodParams)
				.distributionMethod(CostDistributionMethod.ofCode(record.getCostDistributionMethod()))
				.costAmount(Money.of(record.getCostAmount(), currencyId))
				.details(detailRecordsMap.get(orderCostId)
						.stream()
						.map(OrderCostRepository::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static OrderCostDetail fromRecord(@NonNull final I_C_Order_Cost_Detail record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return OrderCostDetail.builder()
				.id(OrderCostDetailId.ofRepoId(record.getC_Order_Cost_ID(), record.getC_Order_Cost_Detail_ID()))
				.orderLineId(OrderLineId.ofRepoId(record.getC_OrderLine_ID()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.qtyOrdered(Quantitys.create(record.getQtyOrdered(), uomId))
				.orderLineNetAmt(Money.of(record.getLineNetAmt(), currencyId))
				.costAmount(Money.of(record.getCostAmount(), currencyId))
				.qtyReceived(Quantitys.create(record.getQtyReceived(), uomId))
				.costAmountReceived(Money.of(record.getCostAmountReceived(), currencyId))
				.build();
	}

	public void saveAll(final Collection<OrderCost> orderCostsList)
	{
		// TODO optimize
		orderCostsList.forEach(this::save);
	}

	public void save(final OrderCost orderCost)
	{
		final I_C_Order_Cost orderCostRecord = orderCost.getId() != null
				? InterfaceWrapperHelper.load(orderCost.getId(), I_C_Order_Cost.class)
				: InterfaceWrapperHelper.newInstance(I_C_Order_Cost.class);
		updateRecord(orderCostRecord, orderCost);
		InterfaceWrapperHelper.save(orderCostRecord);
		final OrderCostId orderCostId = OrderCostId.ofRepoId(orderCostRecord.getC_Order_Cost_ID());
		orderCost.setId(orderCostId);

		final HashMap<OrderLineId, I_C_Order_Cost_Detail> existingDetailRecords = queryDetailsByOrderCostId(orderCost.getId())
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> OrderLineId.ofRepoId(record.getC_OrderLine_ID())));

		for (final OrderCostDetail detail : orderCost.getDetails())
		{
			final OrderLineId orderLineId = detail.getOrderLineId();
			I_C_Order_Cost_Detail detailRecord = existingDetailRecords.remove(orderLineId);
			if (detailRecord == null)
			{
				detailRecord = InterfaceWrapperHelper.newInstance(I_C_Order_Cost_Detail.class);
				detailRecord.setC_Order_Cost_ID(orderCostId.getRepoId());
			}

			detailRecord.setAD_Org_ID(orderCost.getOrgId().getRepoId());
			updateRecord(detailRecord, detail);
			InterfaceWrapperHelper.save(detailRecord);

			final OrderCostDetailId orderCostDetailId = OrderCostDetailId.ofRepoId(orderCostId, detailRecord.getC_Order_Cost_Detail_ID());
			detail.setId(orderCostDetailId);
		}

		InterfaceWrapperHelper.deleteAll(existingDetailRecords.values());
	}

	private IQueryBuilder<I_C_Order_Cost_Detail> queryDetailsByOrderCostId(final OrderCostId id)
	{
		return queryBL.createQueryBuilder(I_C_Order_Cost_Detail.class)
				.addEqualsFilter(I_C_Order_Cost_Detail.COLUMNNAME_C_Order_Cost_ID, id);
	}

	private static void updateRecord(final I_C_Order_Cost record, final OrderCost from)
	{
		record.setIsActive(true);
		record.setC_Order_ID(from.getOrderId().getRepoId());
		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setC_Cost_Type_ID(from.getCostTypeId().getRepoId());

		final CostCalculationMethod calculationMethod = from.getCalculationMethod();
		record.setCostCalculationMethod(calculationMethod.getCode());
		calculationMethod.mapOnParams(
				from.getCalculationMethodParams(),
				new CostCalculationMethod.ParamsMapper<Object>()
				{

					@Override
					public Object fixedAmount(final FixedAmountCostCalculationMethodParams params)
					{
						record.setCostCalculation_FixedAmount(params.getFixedAmount().toBigDecimal());
						return null;
					}

					@Override
					public Object percentageOfAmount(final PercentageCostCalculationMethodParams params)
					{
						record.setCostCalculation_Percentage(params.getPercentage().toBigDecimal());
						return null;
					}
				});

		record.setCostDistributionMethod(from.getDistributionMethod().getCode());
		record.setC_Currency_ID(from.getCostAmount().getCurrencyId().getRepoId());
		record.setCostAmount(from.getCostAmount().toBigDecimal());
	}

	private static void updateRecord(final I_C_Order_Cost_Detail record, final OrderCostDetail from)
	{
		record.setIsActive(true);
		record.setC_OrderLine_ID(from.getOrderLineId().getRepoId());
		record.setM_Product_ID(from.getProductId().getRepoId());
		record.setC_UOM_ID(from.getUomId().getRepoId());
		record.setQtyOrdered(from.getQtyOrdered().toBigDecimal());
		record.setC_Currency_ID(from.getCurrencyId().getRepoId());
		record.setLineNetAmt(from.getOrderLineNetAmt().toBigDecimal());
		record.setCostAmount(from.getCostAmount().toBigDecimal());
		record.setQtyReceived(from.getQtyReceived().toBigDecimal());
		record.setCostAmountReceived(from.getCostAmountReceived().toBigDecimal());
	}

	public void deleteDetails(@NonNull final OrderCostId orderCostId)
	{
		queryDetailsByOrderCostId(orderCostId)
				.create()
				.delete();
	}
}
