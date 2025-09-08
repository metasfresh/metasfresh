package de.metas.order.costs.inout;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.costing.CostElementId;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.costs.OrderCostDetailId;
import de.metas.order.costs.OrderCostTypeId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut_Cost;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

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
		record.setIsSOTrx(request.getSoTrx().toBoolean());
		record.setM_InOut_ID(request.getInoutAndLineId().getInOutId().getRepoId());
		record.setM_InOutLine_ID(request.getInoutAndLineId().getInOutLineId().getRepoId());

		record.setC_BPartner_ID(BPartnerId.toRepoId(request.getBpartnerId()));
		record.setC_Cost_Type_ID(request.getCostTypeId().getRepoId());
		record.setM_CostElement_ID(request.getCostElementId().getRepoId());

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
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		return InOutCost.builder()
				.id(InOutCostId.ofRepoId(record.getM_InOut_Cost_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.orderCostDetailId(OrderCostDetailId.ofRepoId(record.getC_Order_Cost_ID(), record.getC_Order_Cost_Detail_ID()))
				.orderAndLineId(OrderAndLineId.ofRepoIds(record.getC_Order_ID(), record.getC_OrderLine_ID()))
				.soTrx(SOTrx.ofBoolean(record.isSOTrx()))
				.inoutAndLineId(InOutAndLineId.ofRepoId(record.getM_InOut_ID(), record.getM_InOutLine_ID()))
				.bpartnerId(BPartnerId.ofRepoIdOrNull(record.getC_BPartner_ID()))
				.costTypeId(OrderCostTypeId.ofRepoId(record.getC_Cost_Type_ID()))
				.costElementId(CostElementId.ofRepoId(record.getM_CostElement_ID()))
				.qty(Quantitys.of(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID())))
				.costAmount(Money.of(record.getCostAmount(), currencyId))
				.costAmountInvoiced(Money.of(record.getCostAmountInvoiced(), currencyId))
				.isInvoiced(record.isInvoiced())
				.build();
	}

	private static void updateRecord(final I_M_InOut_Cost record, final InOutCost from)
	{
		record.setReversal_ID(InOutCostId.toRepoId(from.getReversalId()));
		record.setCostAmountInvoiced(from.getCostAmountInvoiced().toBigDecimal());
		record.setIsInvoiced(from.isInvoiced());
	}

	public InOutCost getById(@NonNull final InOutCostId inoutCostId)
	{
		final I_M_InOut_Cost record = InterfaceWrapperHelper.load(inoutCostId, I_M_InOut_Cost.class);
		if (record == null)
		{
			throw new AdempiereException("No InOut cost found for " + inoutCostId);
		}
		return fromRecord(record);
	}

	public ImmutableList<InOutCost> getByIds(@NonNull final Set<InOutCostId> inoutCostIds)
	{
		if (inoutCostIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_InOut_Cost.class)
				.addInArrayFilter(I_M_InOut_Cost.COLUMNNAME_M_InOut_Cost_ID, inoutCostIds)
				.orderBy(I_M_InOut_Cost.COLUMNNAME_M_InOut_Cost_ID)
				.stream()
				.map(InOutCostRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableList<InOutCost> getByInOutId(@NonNull final InOutId inoutId)
	{
		return queryBL.createQueryBuilder(I_M_InOut_Cost.class)
				.addEqualsFilter(I_M_InOut_Cost.COLUMNNAME_M_InOut_ID, inoutId)
				.orderBy(I_M_InOut_Cost.COLUMNNAME_M_InOut_Cost_ID)
				.stream()
				.map(InOutCostRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public Stream<InOutCost> stream(@NonNull final InOutCostQuery query)
	{
		return toSqlQuery(query).stream().map(InOutCostRepository::fromRecord);
	}

	private IQuery<I_M_InOut_Cost> toSqlQuery(@NonNull final InOutCostQuery query)
	{
		final IQueryBuilder<I_M_InOut_Cost> queryBuilder = queryBL.createQueryBuilder(I_M_InOut_Cost.class)
				.setLimit(query.getLimit())
				.addOnlyActiveRecordsFilter();

		if (query.getBpartnerId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_InOut_Cost.COLUMNNAME_C_BPartner_ID, query.getBpartnerId());
		}
		if (query.getSoTrx() != null)
		{
			queryBuilder.addEqualsFilter(I_M_InOut_Cost.COLUMNNAME_IsSOTrx, query.getSoTrx().toBoolean());
		}
		if (query.getOrderId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_InOut_Cost.COLUMNNAME_C_Order_ID, query.getOrderId());
		}
		if (query.getCostTypeId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_InOut_Cost.COLUMNNAME_C_Cost_Type_ID, query.getCostTypeId());
		}
		if (!query.isIncludeReversed())
		{
			queryBuilder.addIsNull(I_M_InOut_Cost.COLUMNNAME_Reversal_ID);
		}
		if (query.isOnlyWithOpenAmountToInvoice())
		{
			queryBuilder.addEqualsFilter(I_M_InOut_Cost.COLUMNNAME_IsInvoiced, false);
		}

		return queryBuilder.create();
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

	public void updateInOutCostById(final InOutCostId inoutCostId, final Consumer<InOutCost> consumer)
	{
		final I_M_InOut_Cost record = InterfaceWrapperHelper.load(inoutCostId, I_M_InOut_Cost.class);
		final InOutCost inoutCost = fromRecord(record);
		consumer.accept(inoutCost);
		updateRecord(record, inoutCost);
		InterfaceWrapperHelper.save(record);
	}
}
