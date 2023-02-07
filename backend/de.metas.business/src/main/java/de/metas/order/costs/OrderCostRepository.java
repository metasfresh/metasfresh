package de.metas.order.costs;

import de.metas.order.OrderLineId;
import de.metas.order.costs.calculation_methods.CostCalculationMethod;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order_Cost;
import org.compiere.model.I_C_Order_Cost_Detail;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class OrderCostRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

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
	}

	public void deleteDetails(@NonNull final OrderCostId orderCostId)
	{
		queryDetailsByOrderCostId(orderCostId)
				.create()
				.delete();
	}

}
