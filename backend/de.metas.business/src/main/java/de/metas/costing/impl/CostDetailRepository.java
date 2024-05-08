package de.metas.costing.impl;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetail;
import de.metas.costing.CostDetailId;
import de.metas.costing.CostDetailPreviousAmounts;
import de.metas.costing.CostDetailQuery;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailRepository;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_CostDetail;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
public class CostDetailRepository implements ICostDetailRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	@Override
	public CostDetail create(@NonNull final CostDetail.CostDetailBuilder costDetailBuilder)
	{
		final CostDetail cd = costDetailBuilder.build();
		Check.assumeNull(cd.getId(), "RepoId shall NOT be set for {}", cd);

		final I_M_CostDetail record = newInstance(I_M_CostDetail.class);
		Check.assumeEquals(cd.getClientId().getRepoId(), record.getAD_Client_ID(), "AD_Client_ID");
		record.setAD_Org_ID(cd.getOrgId().getRepoId());
		record.setC_AcctSchema_ID(cd.getAcctSchemaId().getRepoId());
		record.setM_CostElement_ID(cd.getCostElementId().getRepoId());
		record.setM_Product_ID(cd.getProductId().getRepoId());
		record.setM_AttributeSetInstance_ID(cd.getAttributeSetInstanceId().getRepoId());
		record.setAmt(cd.getAmt().getValue());
		record.setC_Currency_ID(cd.getAmt().getCurrencyId().getRepoId());

		record.setQty(cd.getQty().toBigDecimal());
		record.setC_UOM_ID(cd.getQty().getUomId().getRepoId());

		record.setIsChangingCosts(cd.isChangingCosts());
		final CostDetailPreviousAmounts previousAmounts = cd.getPreviousAmounts();
		if (previousAmounts != null)
		{
			record.setPrev_CurrentCostPrice(previousAmounts.getCostPrice().getOwnCostPrice().getValue());
			record.setPrev_CurrentCostPriceLL(previousAmounts.getCostPrice().getComponentsCostPrice().getValue());
			record.setPrev_CurrentQty(previousAmounts.getQty().toBigDecimal());

			record.setPrev_CumulatedAmt(previousAmounts.getCumulatedAmt().getValue());
			record.setPrev_CumulatedQty(previousAmounts.getCumulatedQty().toBigDecimal());
		}

		record.setIsSOTrx(cd.isOutboundTrx());
		updateRecordFromDocumentRef(record, cd.getDocumentRef());

		record.setDescription(cd.getDescription());

		record.setProcessed(true); // TODO: get rid of Processed flag, or always set it!
		saveRecord(record);
		final CostDetailId id = CostDetailId.ofRepoId(record.getM_CostDetail_ID());

		return cd.withId(id);
	}

	private static void updateRecordFromDocumentRef(
			final I_M_CostDetail record,
			final CostingDocumentRef documentRef)
	{
		final String tableName = documentRef.getTableName();
		final int recordId = documentRef.getRecordId();
		// final Boolean soTrx = documentRef.getOutboundTrx();
		if (CostingDocumentRef.TABLE_NAME_M_MatchInv.equals(tableName))
		{
			record.setM_MatchInv_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_MatchPO.equals(tableName))
		{
			record.setM_MatchPO_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_InOutLine.equals(tableName))
		{
			record.setM_InOutLine_ID(recordId);
			// record.setIsSOTrx(soTrx);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_InventoryLine.equals(tableName))
		{
			record.setM_InventoryLine_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_MovementLine.equals(tableName))
		{
			record.setM_MovementLine_ID(recordId);
			// record.setIsSOTrx(soTrx);
		}
		else if (CostingDocumentRef.TABLE_NAME_C_ProjectIssue.equals(tableName))
		{
			record.setC_ProjectIssue_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_PP_Cost_Collector.equals(tableName))
		{
			record.setPP_Cost_Collector_ID(recordId);
		}
		else
		{
			throw new AdempiereException("Don't know how to update " + record + " from " + documentRef);
		}
	}

	@Override
	public void delete(@NonNull final CostDetail costDetail)
	{
		final CostDetailId id = costDetail.getId();
		Check.assumeNotNull(id, "costDetail is saved: {}", costDetail);

		final I_M_CostDetail record = load(id, I_M_CostDetail.class);
		record.setProcessed(false);
		InterfaceWrapperHelper.delete(record);
	}

	@Override
	public Optional<CostDetail> getCostDetail(@NonNull final CostDetailQuery query)
	{
		final I_M_CostDetail record = createQueryBuilder(query)
				.create()
				.firstOnly(I_M_CostDetail.class);

		return record != null
				? Optional.of(toCostDetail(record))
				: Optional.empty();
	}

	private IQueryBuilder<I_M_CostDetail> createQueryBuilder(@NonNull final CostDetailQuery query)
	{
		final IQueryBuilder<I_M_CostDetail> queryBuilder = queryBL.createQueryBuilder(I_M_CostDetail.class)
				.orderBy(I_M_CostDetail.COLUMN_M_CostDetail_ID);

		boolean someFiltersApplied = false;

		// Accounting Schema
		if (query.getAcctSchemaId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_C_AcctSchema_ID, query.getAcctSchemaId());
			someFiltersApplied = true;
		}

		// Cost Element
		if (query.getCostElementId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_M_CostElement_ID, query.getCostElementId());
			someFiltersApplied = true;
		}

		// Document
		final CostingDocumentRef documentRef = query.getDocumentRef();
		if (documentRef != null)
		{
			queryBuilder.addEqualsFilter(documentRef.getCostDetailColumnName(), documentRef.getRecordId());
			someFiltersApplied = true;

			// IsSOTrx
			if (documentRef.getOutboundTrx() != null)
			{
				queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_IsSOTrx, documentRef.getOutboundTrx());
			}
		}

		// Product
		if (query.getProductId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_M_Product_ID, query.getProductId());
			someFiltersApplied = true;
		}

		// ASI
		if (query.getAttributeSetInstanceId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_M_AttributeSetInstance_ID, query.getAttributeSetInstanceId());
			someFiltersApplied = true;
		}

		// Client/Org
		if (query.getClientId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_AD_Client_ID, query.getClientId());
			someFiltersApplied = true;
		}
		if (query.getOrgId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_AD_Org_ID, query.getOrgId());
			someFiltersApplied = true;
		}

		// After M_CostDetail_ID
		if (query.getAfterCostDetailId() != null)
		{
			queryBuilder.addCompareFilter(I_M_CostDetail.COLUMN_M_CostDetail_ID, Operator.GREATER, query.getAfterCostDetailId());
			someFiltersApplied = true;
		}

		// Fail if no filters were applied. Else we would fetch the whole database.
		if (!someFiltersApplied)
		{
			throw new AdempiereException("Invalid query. No filters were applied: " + query);
		}

		//
		return queryBuilder;
	}

	private CostDetail toCostDetail(final I_M_CostDetail record)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID());

		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final I_C_UOM productUOM = uomsRepo.getById(record.getC_UOM_ID());

		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final CostAmount amt = CostAmount.of(record.getAmt(), currencyId);
		final Quantity qty = Quantity.of(record.getQty(), productUOM);

		return CostDetail.builder()
				.id(CostDetailId.ofRepoId(record.getM_CostDetail_ID()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.acctSchemaId(acctSchemaId)
				.costElementId(CostElementId.ofRepoId(record.getM_CostElement_ID()))
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.amt(amt)
				.qty(qty)
				.changingCosts(record.isChangingCosts())
				.previousAmounts(CostDetailPreviousAmounts.builder()
						.costPrice(CostPrice.builder()
								.ownCostPrice(CostAmount.of(record.getPrev_CurrentCostPrice(), currencyId))
								.componentsCostPrice(CostAmount.of(record.getPrev_CurrentCostPriceLL(), currencyId))
								.uomId(UomId.ofRepoId(productUOM.getC_UOM_ID())) // TODO: introduce M_CostDetail.Prev_CurrentQty_UOM_ID
								.build())
						.qty(Quantity.of(record.getPrev_CurrentQty(), productUOM))
						.cumulatedAmt(CostAmount.of(record.getPrev_CumulatedAmt(), currencyId))
						.cumulatedQty(Quantity.of(record.getPrev_CumulatedQty(), productUOM))
						.build())
				.documentRef(extractDocumentRef(record))
				.description(record.getDescription())
				.build();
	}

	private static CostingDocumentRef extractDocumentRef(final I_M_CostDetail record)
	{
		if (record.getM_MatchPO_ID() > 0)
		{
			return CostingDocumentRef.ofMatchPOId(record.getM_MatchPO_ID());
		}
		else if (record.getM_MatchInv_ID() > 0)
		{
			return CostingDocumentRef.ofMatchInvoiceId(record.getM_MatchInv_ID());
		}
		else if (record.getM_InOutLine_ID() > 0)
		{
			if (record.isSOTrx())
			{
				return CostingDocumentRef.ofShipmentLineId(record.getM_InOutLine_ID());
			}
			else
			{
				return CostingDocumentRef.ofReceiptLineId(record.getM_InOutLine_ID());
			}
		}
		else if (record.getM_InventoryLine_ID() > 0)
		{
			return CostingDocumentRef.ofInventoryLineId(record.getM_InventoryLine_ID());
		}
		else if (record.getM_MovementLine_ID() > 0)
		{
			if (record.isSOTrx())
			{
				return CostingDocumentRef.ofOutboundMovementLineId(record.getM_MovementLine_ID());
			}
			else
			{
				return CostingDocumentRef.ofInboundMovementLineId(record.getM_MovementLine_ID());
			}
		}
		else if (record.getC_ProjectIssue_ID() > 0)
		{
			return CostingDocumentRef.ofProjectIssueId(record.getC_ProjectIssue_ID());
		}
		else if (record.getPP_Cost_Collector_ID() > 0)
		{
			return CostingDocumentRef.ofCostCollectorId(record.getPP_Cost_Collector_ID());
		}
		else
		{
			throw new AdempiereException("Cannot determine " + CostingDocumentRef.class + " for " + record);
		}
	}

	@Override
	public List<CostDetail> getAllForDocument(@NonNull final CostingDocumentRef documentRef)
	{
		return listOrderedById(CostDetailQuery.builder()
				.documentRef(documentRef)
				.build());
	}

	@Override
	public List<CostDetail> getAllForDocumentAndAcctSchemaId(
			@NonNull final CostingDocumentRef documentRef,
			@NonNull final AcctSchemaId acctSchemaId)
	{
		return listOrderedById(CostDetailQuery.builder()
				.documentRef(documentRef)
				.acctSchemaId(acctSchemaId)
				.build());
	}

	@Override
	public boolean hasCostDetailsForProductId(@NonNull final ProductId productId)
	{
		final CostDetailQuery costDetailQuery = CostDetailQuery.builder()
				.productId(productId)
				.build();
		return createQueryBuilder(costDetailQuery)
				.create()
				.anyMatch();
	}

	@Override
	public Stream<CostDetail> streamOrderedById(@NonNull final CostDetailQuery query)
	{
		return createQueryBuilder(query)
				.clearOrderBys()
				.orderBy(I_M_CostDetail.COLUMN_M_CostDetail_ID)
				.create()
				.iterateAndStream()
				.map(this::toCostDetail);
	}

	private List<CostDetail> listOrderedById(@NonNull final CostDetailQuery query)
	{
		return createQueryBuilder(query)
				.clearOrderBys()
				.orderBy(I_M_CostDetail.COLUMN_M_CostDetail_ID)
				.create()
				.stream()
				.map(this::toCostDetail)
				.collect(ImmutableList.toImmutableList());
	}

}
