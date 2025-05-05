package de.metas.costing.impl;

import com.google.common.annotations.VisibleForTesting;
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
import de.metas.costing.methods.CostAmountType;
import de.metas.costrevaluation.CostRevaluationLineId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shippingnotification.ShippingNotificationLineId;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
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
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

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

		record.setM_CostDetail_Type(cd.getAmtType().getCode());
		record.setAmt(cd.getAmt().toBigDecimal());
		record.setC_Currency_ID(cd.getAmt().getCurrencyId().getRepoId());

		final Money sourceAmt = cd.getAmt().toSourceMoney();
		if (sourceAmt != null)
		{
			record.setSourceAmt(sourceAmt.toBigDecimal());
			record.setSource_Currency_ID(sourceAmt.getCurrencyId().getRepoId());
		}

		record.setQty(cd.getQty().toBigDecimal());
		record.setC_UOM_ID(cd.getQty().getUomId().getRepoId());

		record.setIsChangingCosts(cd.isChangingCosts());
		final CostDetailPreviousAmounts previousAmounts = cd.getPreviousAmounts();
		if (previousAmounts != null)
		{
			record.setPrev_CurrentCostPrice(previousAmounts.getCostPrice().getOwnCostPrice().toBigDecimal());
			record.setPrev_CurrentCostPriceLL(previousAmounts.getCostPrice().getComponentsCostPrice().toBigDecimal());
			record.setPrev_CurrentQty(previousAmounts.getQty().toBigDecimal());

			record.setPrev_CumulatedAmt(previousAmounts.getCumulatedAmt().toBigDecimal());
			record.setPrev_CumulatedQty(previousAmounts.getCumulatedQty().toBigDecimal());
		}

		record.setIsSOTrx(cd.isOutboundTrx());
		updateRecordFromDocumentRef(record, cd.getDocumentRef());

		record.setDescription(cd.getDescription());

		record.setDateAcct(Timestamp.from(cd.getDateAcct()));

		record.setProcessed(true); // TODO: get rid of Processed flag, or always set it!
		saveRecord(record);
		final CostDetailId id = CostDetailId.ofRepoId(record.getM_CostDetail_ID());

		return cd.withId(id);
	}

	@Override
	public CostDetail updateDateAcct(@NonNull final CostDetail costDetail, @NonNull final Instant newDateAcct)
	{
		if (Objects.equals(costDetail.getDateAcct(), newDateAcct))
		{
			return costDetail;
		}

		final CostDetailId costDetailId = Check.assumeNotNull(costDetail.getId(), "cost detail is saved: {}", costDetail);

		final I_M_CostDetail record = load(costDetailId, I_M_CostDetail.class);
		record.setDateAcct(Timestamp.from(newDateAcct));
		InterfaceWrapperHelper.save(record);

		return costDetail.withDateAcct(newDateAcct);
	}

	@VisibleForTesting
	static void updateRecordFromDocumentRef(
			@NonNull final I_M_CostDetail record,
			@NonNull final CostingDocumentRef documentRef)
	{
		final String tableName = documentRef.getTableName();
		final int recordId = documentRef.getId().getRepoId();
		if (CostingDocumentRef.TABLE_NAME_M_MatchInv.equals(tableName))
		{
			record.setM_MatchInv_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_MatchPO.equals(tableName))
		{
			record.setM_MatchPO_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_Shipping_NotificationLine.equals(tableName))
		{
			record.setM_Shipping_NotificationLine_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_InOutLine.equals(tableName))
		{
			record.setM_InOutLine_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_InventoryLine.equals(tableName))
		{
			record.setM_InventoryLine_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_MovementLine.equals(tableName))
		{
			record.setM_MovementLine_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_C_ProjectIssue.equals(tableName))
		{
			record.setC_ProjectIssue_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_PP_Cost_Collector.equals(tableName))
		{
			record.setPP_Cost_Collector_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_CostRevaluationLine.equals(tableName))
		{
			final CostRevaluationLineId costRevaluationLineId = documentRef.getId(CostRevaluationLineId.class);
			record.setM_CostRevaluation_ID(costRevaluationLineId.getCostRevaluationId().getRepoId());
			record.setM_CostRevaluationLine_ID(costRevaluationLineId.getRepoId());
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
	public Optional<CostDetail> firstOnly(@NonNull final CostDetailQuery query)
	{
		return toSqlQuery(query)
				.firstOnlyOptional()
				.map(this::toCostDetail);
	}

	private IQueryBuilder<I_M_CostDetail> toSqlQuery(@NonNull final CostDetailQuery query)
	{
		final IQueryBuilder<I_M_CostDetail> queryBuilder = queryBL.createQueryBuilder(I_M_CostDetail.class);

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
			queryBuilder.addEqualsFilter(documentRef.getCostDetailColumnName(), documentRef.getId());
			someFiltersApplied = true;

			// IsSOTrx
			if (documentRef.getOutboundTrx() != null)
			{
				queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_IsSOTrx, documentRef.getOutboundTrx());
			}
		}

		// AmtType
		final CostAmountType amtType = query.getAmtType();
		if (amtType != null)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMNNAME_M_CostDetail_Type, amtType.getCode());
		}

		// Product
		if (query.getProductId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMNNAME_M_Product_ID, query.getProductId());
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
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMNNAME_AD_Client_ID, query.getClientId());
			someFiltersApplied = true;
		}
		if (query.getOrgId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMNNAME_AD_Org_ID, query.getOrgId());
			someFiltersApplied = true;
		}

		// After M_CostDetail_ID
		if (query.getAfterCostDetailId() != null)
		{
			queryBuilder.addCompareFilter(I_M_CostDetail.COLUMN_M_CostDetail_ID, Operator.GREATER, query.getAfterCostDetailId());
			someFiltersApplied = true;
		}

		if (query.getDateAcctRage() != null)
		{
			queryBuilder.addInRange(I_M_CostDetail.COLUMNNAME_DateAcct, query.getDateAcctRage());
			someFiltersApplied = true;
		}

		// Fail if no filters were applied. Else we would fetch the whole database.
		if (!someFiltersApplied)
		{
			throw new AdempiereException("Invalid query. No filters were applied: " + query);
		}

		//
		// ORDER BYs
		if (!query.getOrderBys().isEmpty())
		{
			for (final CostDetailQuery.OrderBy orderBy : query.getOrderBys())
			{
				if (orderBy.isAscending())
				{
					queryBuilder.orderBy(orderBy.getColumnName());
				}
				else
				{
					queryBuilder.orderByDescending(orderBy.getColumnName());
				}
			}
		}

		//
		return queryBuilder;
	}

	@NonNull
	private CostDetail toCostDetail(@NonNull final I_M_CostDetail record)
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID());

		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
		final I_C_UOM productUOM = uomDAO.getById(record.getC_UOM_ID());

		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final CurrencyId sourceCurrencyId = CurrencyId.ofRepoIdOrNull(record.getSource_Currency_ID());
		final CostAmount amt = CostAmount.of(record.getAmt(), currencyId, record.getSourceAmt(), sourceCurrencyId);
		final Quantity qty = Quantitys.of(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID()));

		return CostDetail.builder()
				.id(CostDetailId.ofRepoId(record.getM_CostDetail_ID()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.acctSchemaId(acctSchemaId)
				.costElementId(CostElementId.ofRepoId(record.getM_CostElement_ID()))
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
				.amtType(CostAmountType.ofCode(record.getM_CostDetail_Type()))
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
				.dateAcct(record.getDateAcct().toInstant())
				.build();
	}

	@VisibleForTesting
	static CostingDocumentRef extractDocumentRef(final I_M_CostDetail record)
	{
		if (record.getM_MatchPO_ID() > 0)
		{
			return CostingDocumentRef.ofMatchPOId(record.getM_MatchPO_ID());
		}
		else if (record.getM_MatchInv_ID() > 0)
		{
			return CostingDocumentRef.ofMatchInvoiceId(record.getM_MatchInv_ID());
		}
		else if (record.getM_Shipping_NotificationLine_ID() > 0)
		{
			return CostingDocumentRef.ofShippingNotificationLineId(ShippingNotificationLineId.ofRepoId(record.getM_Shipping_NotificationLine_ID()));
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
		else if (record.getM_CostRevaluationLine_ID() > 0)
		{
			return CostingDocumentRef.ofCostRevaluationLineId(CostRevaluationLineId.ofRepoId(record.getM_CostRevaluation_ID(), record.getM_CostRevaluationLine_ID()));
		}
		else
		{
			throw new AdempiereException("Cannot determine " + CostingDocumentRef.class + " for " + record);
		}
	}

	@Override
	public boolean hasCostDetailsByProductId(@NonNull final ProductId productId)
	{
		return toSqlQuery(CostDetailQuery.builder().productId(productId).build())
				.anyMatch();
	}

	@Override
	public Stream<CostDetail> stream(@NonNull final CostDetailQuery query)
	{
		return toSqlQuery(query)
				.iterateAndStream()
				.map(this::toCostDetail);
	}

	@Override
	public ImmutableList<CostDetail> list(@NonNull final CostDetailQuery query)
	{
		return toSqlQuery(query)
				.stream()
				.map(this::toCostDetail)
				.collect(ImmutableList.toImmutableList());
	}

}
