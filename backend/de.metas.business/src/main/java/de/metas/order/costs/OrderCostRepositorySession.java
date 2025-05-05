package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.CostElementId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
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
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order_Cost;
import org.compiere.model.I_C_Order_Cost_Detail;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class OrderCostRepositorySession
{
	private final IQueryBL queryBL;

	private final HashMap<OrderCostId, I_C_Order_Cost> orderCostRecordsById = new HashMap<>();
	private final HashMap<OrderCostId, ArrayList<I_C_Order_Cost_Detail>> orderCostDetailRecordsByOrderCostId = new HashMap<>();

	OrderCostRepositorySession(
			@NonNull final IQueryBL queryBL)
	{
		this.queryBL = queryBL;
	}

	public List<OrderCost> getByOrderId(@NonNull final OrderId orderId)
	{
		return getByOrderIds(ImmutableSet.of(orderId));
	}

	public List<OrderCost> getByOrderIds(@NonNull final Set<OrderId> orderIds)
	{
		final ImmutableSet<OrderCostId> orderCostIds = retrieveOrderCostIdsByOrderIds(orderIds);
		return getByIds(orderCostIds);
	}

	private ImmutableSet<OrderCostId> retrieveOrderCostIdsByOrderIds(final @NonNull Set<OrderId> orderIds)
	{
		if (orderIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL.createQueryBuilder(I_C_Order_Cost.class)
				.addInArrayFilter(I_C_Order_Cost.COLUMNNAME_C_Order_ID, orderIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds(OrderCostId::ofRepoId);
	}

	public List<OrderCost> getByOrderLineIds(@NonNull final Set<OrderLineId> orderLineIds)
	{
		final ImmutableSet<OrderCostId> orderCostIds = retrieveOrderCostIdsByOrderLineIds(orderLineIds);
		return getByIds(orderCostIds);
	}

	private ImmutableSet<OrderCostId> retrieveOrderCostIdsByOrderLineIds(final @NonNull Set<OrderLineId> orderLineIds)
	{
		if (orderLineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		final ImmutableList<OrderCostId> orderCostIds = queryBL.createQueryBuilder(I_C_Order_Cost_Detail.class)
				.addInArrayFilter(I_C_Order_Cost_Detail.COLUMNNAME_C_OrderLine_ID, orderLineIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_C_Order_Cost_Detail.COLUMNNAME_C_Order_Cost_ID, OrderCostId.class);

		return ImmutableSet.copyOf(orderCostIds);
	}

	public List<OrderCost> getByIds(@NonNull final Collection<OrderCostId> orderCostIds)
	{
		if (orderCostIds.isEmpty())
		{
			return ImmutableList.of();
		}

		loadRecordsFromDB(orderCostIds);

		final ImmutableList.Builder<OrderCost> result = ImmutableList.builder();
		for (final OrderCostId orderCostId : orderCostIds)
		{
			final I_C_Order_Cost headerRecord = getOrderCostRecordById(orderCostId);
			final List<I_C_Order_Cost_Detail> detailRecords = getOrderCostDetailRecords(orderCostId);
			final OrderCost orderCost = fromRecord(headerRecord, detailRecords);
			result.add(orderCost);
		}

		return result.build();
	}

	private void loadRecordsFromDB(@NonNull final Collection<OrderCostId> orderCostIds)
	{
		if (orderCostIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_C_Order_Cost.class)
				.addInArrayFilter(I_C_Order_Cost.COLUMNNAME_C_Order_Cost_ID, orderCostIds)
				.create()
				.forEach(record -> orderCostRecordsById.put(OrderCostId.ofRepoId(record.getC_Order_Cost_ID()), record));

		final Map<OrderCostId, ArrayList<I_C_Order_Cost_Detail>> detailRecords = queryBL.createQueryBuilder(I_C_Order_Cost_Detail.class)
				.addInArrayFilter(I_C_Order_Cost_Detail.COLUMNNAME_C_Order_Cost_ID, orderCostIds)
				.create()
				.stream()
				.collect(Collectors.groupingBy(
						detailRecord -> OrderCostId.ofRepoId(detailRecord.getC_Order_Cost_ID()),
						GuavaCollectors.toArrayList()
				));
		this.orderCostDetailRecordsByOrderCostId.putAll(detailRecords);
	}

	private I_C_Order_Cost getOrderCostRecordById(final OrderCostId orderCostId)
	{
		return Check.assumeNotNull(orderCostRecordsById.get(orderCostId), "no record found for {}", orderCostId);
	}

	private List<I_C_Order_Cost_Detail> getOrderCostDetailRecords(final OrderCostId orderCostId)
	{
		final ArrayList<I_C_Order_Cost_Detail> detailRecords = orderCostDetailRecordsByOrderCostId.get(orderCostId);
		return detailRecords != null ? detailRecords : ImmutableList.of();
	}

	private static OrderCost fromRecord(
			@NonNull final I_C_Order_Cost record,
			@NonNull final List<I_C_Order_Cost_Detail> detailRecords)
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
				.orderId(extractOrderId(record))
				.soTrx(SOTrx.ofBoolean(record.isSOTrx()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.bpartnerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.costElementId(CostElementId.ofRepoId(record.getM_CostElement_ID()))
				.costTypeId(OrderCostTypeId.ofRepoId(record.getC_Cost_Type_ID()))
				.calculationMethod(calculationMethod)
				.calculationMethodParams(calculationMethodParams)
				.distributionMethod(CostDistributionMethod.ofCode(record.getCostDistributionMethod()))
				.costAmount(Money.of(record.getCostAmount(), currencyId))
				.details(detailRecords
						.stream()
						.map(OrderCostRepositorySession::fromRecord)
						.collect(ImmutableList.toImmutableList()))
				.createdOrderLineId(extractCreatedOrderLineId(record))
				.build();
	}

	@Nullable
	private static OrderLineId extractCreatedOrderLineId(final @NonNull I_C_Order_Cost record)
	{
		return OrderLineId.ofRepoIdOrNull(record.getCreated_OrderLine_ID());
	}

	@Nullable
	public static OrderAndLineId extractCreatedOrderAndLineId(final @NonNull I_C_Order_Cost record)
	{
		return OrderAndLineId.ofNullable(extractOrderId(record), extractCreatedOrderLineId(record));
	}

	@NonNull
	private static OrderId extractOrderId(final @NonNull I_C_Order_Cost record) {return OrderId.ofRepoId(record.getC_Order_ID());}

	private static OrderCostDetail fromRecord(@NonNull final I_C_Order_Cost_Detail record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final UomId uomId = UomId.ofRepoId(record.getC_UOM_ID());

		return OrderCostDetail.builder()
				.id(OrderCostDetailId.ofRepoId(record.getC_Order_Cost_ID(), record.getC_Order_Cost_Detail_ID()))
				.orderLineInfo(OrderCostDetailOrderLinePart.builder()
						.orderLineId(OrderLineId.ofRepoId(record.getC_OrderLine_ID()))
						.productId(ProductId.ofRepoId(record.getM_Product_ID()))
						.qtyOrdered(Quantitys.of(record.getQtyOrdered(), uomId))
						.orderLineNetAmt(Money.of(record.getLineNetAmt(), currencyId))
						.build())
				.costAmount(Money.of(record.getCostAmount(), currencyId))
				.inoutQty(Quantitys.of(record.getQtyReceived(), uomId))
				.inoutCostAmount(Money.of(record.getCostAmountReceived(), currencyId))
				.build();
	}

	public void save(final OrderCost orderCost)
	{
		saveAll(ImmutableList.of(orderCost));
	}

	public void saveAll(final Collection<OrderCost> orderCostsList)
	{
		if (orderCostsList.isEmpty())
		{
			return;
		}

		final ImmutableSet<OrderCostId> existingOrderCostIds = orderCostsList.stream().map(OrderCost::getId).filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());
		loadRecordsFromDB(existingOrderCostIds);

		orderCostsList.forEach(this::saveUsingCache);
	}

	private void saveUsingCache(final OrderCost orderCost)
	{
		final I_C_Order_Cost orderCostRecord = orderCost.getId() != null
				? getOrderCostRecordById(orderCost.getId())
				: InterfaceWrapperHelper.newInstance(I_C_Order_Cost.class);
		updateRecord(orderCostRecord, orderCost);
		InterfaceWrapperHelper.save(orderCostRecord);
		final OrderCostId orderCostId = OrderCostId.ofRepoId(orderCostRecord.getC_Order_Cost_ID());
		orderCost.setId(orderCostId);

		final HashMap<OrderLineId, I_C_Order_Cost_Detail> existingDetailRecords = getOrderCostDetailRecords(orderCost.getId())
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

	private static void updateRecord(final I_C_Order_Cost record, final OrderCost from)
	{
		record.setIsActive(true);
		record.setC_Order_ID(from.getOrderId().getRepoId());
		record.setIsSOTrx(from.getSoTrx().toBoolean());
		record.setAD_Org_ID(from.getOrgId().getRepoId());
		record.setC_BPartner_ID(BPartnerId.toRepoId(from.getBpartnerId()));
		record.setM_CostElement_ID(from.getCostElementId().getRepoId());
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
		record.setCreated_OrderLine_ID(OrderLineId.toRepoId(from.getCreatedOrderLineId()));
	}

	private static void updateRecord(final I_C_Order_Cost_Detail record, final OrderCostDetail from)
	{
		record.setIsActive(true);

		updateRecord(record, from.getOrderLineInfo());

		record.setCostAmount(from.getCostAmount().toBigDecimal());
		record.setQtyReceived(from.getInoutQty().toBigDecimal());
		record.setCostAmountReceived(from.getInoutCostAmount().toBigDecimal());
	}

	private static void updateRecord(final I_C_Order_Cost_Detail record, final OrderCostDetailOrderLinePart from)
	{
		record.setC_OrderLine_ID(from.getOrderLineId().getRepoId());
		record.setM_Product_ID(from.getProductId().getRepoId());
		record.setC_UOM_ID(from.getUomId().getRepoId());
		record.setQtyOrdered(from.getQtyOrdered().toBigDecimal());
		record.setC_Currency_ID(from.getCurrencyId().getRepoId());
		record.setLineNetAmt(from.getOrderLineNetAmt().toBigDecimal());
	}

	public void changeByOrderLineId(
			@NonNull final OrderLineId orderLineId,
			@NonNull Consumer<OrderCost> consumer)
	{
		final List<OrderCost> orderCosts = getByOrderLineIds(ImmutableSet.of(orderLineId));

		orderCosts.forEach(orderCost -> {
			consumer.accept(orderCost);
			saveUsingCache(orderCost);
		});
	}

}
