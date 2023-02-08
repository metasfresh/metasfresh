package de.metas.order.costs.inout;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.costs.OrderCostDetailId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut_Cost;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class InOutCostRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public InOutCost create(@NonNull final InOutCostCreateRequest request)
	{
		final I_M_InOut_Cost record = InterfaceWrapperHelper.newInstance(I_M_InOut_Cost.class);
		record.setReversal_ID(InOutCostId.toRepoId(request.getReversalId()));
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setC_Order_Cost_ID(request.getOrderCostDetailId().getOrderCostId().getRepoId());
		record.setC_Order_Cost_Detail_ID(request.getOrderCostDetailId().getRepoId());
		record.setC_Order_ID(request.getOrderAndLineId().getOrderRepoId());
		record.setC_OrderLine_ID(request.getOrderAndLineId().getOrderLineRepoId());
		record.setM_InOut_ID(request.getReceiptAndLineId().getInOutId().getRepoId());
		record.setM_InOutLine_ID(request.getReceiptAndLineId().getInOutLineId().getRepoId());

		record.setC_UOM_ID(request.getQty().getUomId().getRepoId());
		record.setQty(request.getQty().toBigDecimal());

		record.setC_Currency_ID(request.getCostAmount().getCurrencyId().getRepoId());
		record.setCostAmount(request.getCostAmount().toBigDecimal());

		InterfaceWrapperHelper.save(record);

		return fromRecord(record);
	}

	public void saveAll(final Collection<InOutCost> inoutCosts)
	{
		if (inoutCosts.isEmpty())
		{
			return;
		}

		final ImmutableMap<InOutCostId, InOutCost> inoutCostsById = Maps.uniqueIndex(inoutCosts, InOutCost::getId);
		final List<I_M_InOut_Cost> existingRecords = InterfaceWrapperHelper.loadByRepoIdAwares(inoutCostsById.keySet(), I_M_InOut_Cost.class);
		for (final I_M_InOut_Cost record : existingRecords)
		{
			final InOutCostId inoutCostId = InOutCostId.ofRepoId(record.getM_InOut_Cost_ID());
			final InOutCost inoutCost = inoutCostsById.get(inoutCostId);
			updateRecord(record, inoutCost);
			InterfaceWrapperHelper.save(record);
		}
	}

	private static InOutCost fromRecord(@NonNull final I_M_InOut_Cost record)
	{
		return InOutCost.builder()
				.id(InOutCostId.ofRepoId(record.getM_InOut_Cost_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.orderCostDetailId(OrderCostDetailId.ofRepoId(record.getC_Order_Cost_ID(), record.getC_Order_Cost_Detail_ID()))
				.orderAndLineId(OrderAndLineId.ofRepoIds(record.getC_Order_ID(), record.getC_OrderLine_ID()))
				.receiptAndLineId(InOutAndLineId.ofRepoId(record.getM_InOut_ID(), record.getM_InOutLine_ID()))
				.qty(Quantitys.create(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID())))
				.costAmount(Money.of(record.getCostAmount(), CurrencyId.ofRepoId(record.getC_Currency_ID())))
				.build();
	}

	private static void updateRecord(final I_M_InOut_Cost record, final InOutCost from)
	{
		record.setReversal_ID(InOutCostId.toRepoId(from.getReversalId()));
	}

	public ImmutableList<InOutCost> getByReceiptId(@NonNull final InOutId receiptId)
	{
		return queryByReceiptId(receiptId)
				.orderBy(I_M_InOut_Cost.COLUMNNAME_M_InOut_Cost_ID)
				.stream()
				.map(InOutCostRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	private IQueryBuilder<I_M_InOut_Cost> queryByReceiptId(final @NonNull InOutId receiptId)
	{
		return queryBL.createQueryBuilder(I_M_InOut_Cost.class)
				.addEqualsFilter(I_M_InOut_Cost.COLUMNNAME_M_InOut_ID, receiptId);
	}

	public void deleteAll(@NonNull final ImmutableList<InOutCost> inoutCosts)
	{
		if (inoutCosts.isEmpty())
		{
			return;
		}

		final ImmutableSet<InOutCostId> inoutCostIds = inoutCosts.stream().map(InOutCost::getId).collect(ImmutableSet.toImmutableSet());
		if (inoutCostIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_M_InOut_Cost.class)
				.addInArrayFilter(I_M_InOut_Cost.COLUMNNAME_M_InOut_Cost_ID, inoutCostIds)
				.create()
				.delete();
	}
}
