package de.metas.invoice.matchinv.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.costing.CostElementId;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvCostPart;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.invoice.matchinv.MatchInvQuery;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.costs.OrderCostTypeId;
import de.metas.order.costs.inout.InOutCostId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_MatchInv;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class MatchInvoiceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public MatchInv getById(@NonNull final MatchInvId matchInvId)
	{
		final I_M_MatchInv record = InterfaceWrapperHelper.load(matchInvId, I_M_MatchInv.class);
		if (record == null)
		{
			throw new AdempiereException("No Match Invoice found for " + matchInvId);
		}
		return fromRecord(record);
	}

	public static MatchInv fromRecord(@NonNull final I_M_MatchInv record)
	{
		final MatchInvType type = MatchInvType.ofCode(record.getType());

		final MatchInvCostPart costPart;
		if (type.isCost())
		{
			costPart = extractMatchInvCostPart(record);
		}
		else
		{
			costPart = null;
		}

		return MatchInv.builder()
				.id(MatchInvId.ofRepoId(record.getM_MatchInv_ID()))
				.invoiceAndLineId(InvoiceAndLineId.ofRepoId(record.getC_Invoice_ID(), record.getC_InvoiceLine_ID()))
				.inoutLineId(InOutAndLineId.ofRepoId(record.getM_InOut_ID(), record.getM_InOutLine_ID()))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.soTrx(SOTrx.ofBoolean(record.isSOTrx()))
				.dateTrx(record.getDateTrx().toInstant())
				.dateAcct(record.getDateAcct().toInstant())
				.posted(record.isPosted())
				.updatedByUserId(UserId.ofRepoIdOrSystem(record.getUpdatedBy()))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.asiId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.qty(StockQtyAndUOMQtys.create(
						record.getQty(),
						ProductId.ofRepoId(record.getM_Product_ID()),
						record.getQtyInUOM(),
						UomId.ofRepoIdOrNull(record.getC_UOM_ID())))
				.type(type)
				.costPart(costPart)
				.build();
	}

	private static MatchInvCostPart extractMatchInvCostPart(final @NonNull I_M_MatchInv record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		return MatchInvCostPart.builder()
				.inoutCostId(InOutCostId.ofRepoId(record.getM_InOut_Cost_ID()))
				.costTypeId(OrderCostTypeId.ofRepoId(record.getC_Cost_Type_ID()))
				.costElementId(CostElementId.ofRepoId(record.getM_CostElement_ID()))
				.costAmountInOut(Money.of(record.getCostAmount(), currencyId))
				.costAmountInvoiced(Money.of(record.getCostAmountInvoiced(), currencyId))
				.build();
	}

	static void updateRecord(@NonNull final I_M_MatchInv record, @NonNull final MatchInvCostPart from)
	{
		record.setM_InOut_Cost_ID(from.getInoutCostId().getRepoId());
		record.setC_Cost_Type_ID(from.getCostTypeId().getRepoId());
		record.setM_CostElement_ID(from.getCostElementId().getRepoId());
		record.setC_Currency_ID(from.getCurrencyId().getRepoId());
		record.setCostAmount(from.getCostAmountInOut().toBigDecimal());
		record.setCostAmountInvoiced(from.getCostAmountInvoiced().toBigDecimal());
	}

	public Optional<MatchInv> first(@NonNull final MatchInvQuery query)
	{
		return toSqlQuery(query)
				.firstOptional()
				.map(MatchInvoiceRepository::fromRecord);
	}

	public ImmutableList<MatchInv> list(@NonNull final MatchInvQuery query)
	{
		return toSqlQuery(query)
				.stream()
				.map(MatchInvoiceRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public boolean anyMatch(@NonNull final MatchInvQuery query)
	{
		return toSqlQuery(query).anyMatch();
	}

	public ImmutableList<MatchInv> deleteAndReturn(@NonNull final MatchInvQuery query)
	{
		final ImmutableList<I_M_MatchInv> records = toSqlQuery(query).list();
		if (records.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableList<MatchInv> matchInvs = records.stream().map(MatchInvoiceRepository::fromRecord).collect(ImmutableList.toImmutableList());

		InterfaceWrapperHelper.deleteAll(records, false);

		return matchInvs;
	}

	private IQueryBuilder<I_M_MatchInv> toSqlQuery(final MatchInvQuery query)
	{
		final IQueryBuilder<I_M_MatchInv> sqlQueryBuilder = queryBL.createQueryBuilder(I_M_MatchInv.class)
				.orderBy(I_M_MatchInv.COLUMN_M_MatchInv_ID);

		if (query.isOnlyActive())
		{
			sqlQueryBuilder.addOnlyActiveRecordsFilter();
		}
		if (query.getType() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_M_MatchInv.COLUMNNAME_Type, query.getType());
		}
		if (query.getInvoiceAndLineId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID, query.getInvoiceAndLineId());
		}
		if (query.getInoutId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOut_ID, query.getInoutId());
		}
		if (query.getInoutLineId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOutLine_ID, query.getInoutLineId());
		}
		if (query.getInoutCostId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOut_Cost_ID, query.getInoutCostId());
		}
		if (query.getAsiId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_AttributeSetInstance_ID, query.getAsiId());
		}

		return sqlQueryBuilder;
	}

	public Set<MatchInvId> getIdsProcessedButNotPostedByInOutLineIds(@NonNull final Set<InOutLineId> inoutLineIds)
	{
		if (inoutLineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL
				.createQueryBuilder(I_M_MatchInv.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_MatchInv.COLUMN_M_InOutLine_ID, inoutLineIds)
				.addEqualsFilter(I_M_MatchInv.COLUMN_Processed, true)
				.addNotEqualsFilter(I_M_MatchInv.COLUMN_Posted, true)
				.create()
				.listIds(MatchInvId::ofRepoId);
	}

	public Set<MatchInvId> getIdsProcessedButNotPostedByInvoiceLineIds(@NonNull final Set<InvoiceAndLineId> invoiceAndLineIds)
	{
		if (invoiceAndLineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL
				.createQueryBuilder(I_M_MatchInv.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_M_MatchInv.COLUMN_C_InvoiceLine_ID, invoiceAndLineIds)
				.addEqualsFilter(I_M_MatchInv.COLUMN_Processed, true)
				.addNotEqualsFilter(I_M_MatchInv.COLUMN_Posted, true)
				.create()
				.listIds(MatchInvId::ofRepoId);
	}
}
