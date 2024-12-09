package de.metas.costing.impl;

<<<<<<< HEAD
=======
import com.google.common.annotations.VisibleForTesting;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
=======
import de.metas.costing.methods.CostAmountType;
import de.metas.costrevaluation.CostRevaluationLineId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

<<<<<<< HEAD
import java.util.List;
=======
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
=======
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
		record.setAmt(cd.getAmt().getValue());
		record.setC_Currency_ID(cd.getAmt().getCurrencyId().getRepoId());

=======

		record.setM_CostDetail_Type(cd.getAmtType().getCode());
		record.setAmt(cd.getAmt().toBigDecimal());
		record.setC_Currency_ID(cd.getAmt().getCurrencyId().getRepoId());

		final Money sourceAmt = cd.getAmt().toSourceMoney();
		if (sourceAmt != null)
		{
			record.setSourceAmt(sourceAmt.toBigDecimal());
			record.setSource_Currency_ID(sourceAmt.getCurrencyId().getRepoId());
		}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		record.setQty(cd.getQty().toBigDecimal());
		record.setC_UOM_ID(cd.getQty().getUomId().getRepoId());

		record.setIsChangingCosts(cd.isChangingCosts());
		final CostDetailPreviousAmounts previousAmounts = cd.getPreviousAmounts();
		if (previousAmounts != null)
		{
<<<<<<< HEAD
			record.setPrev_CurrentCostPrice(previousAmounts.getCostPrice().getOwnCostPrice().getValue());
			record.setPrev_CurrentCostPriceLL(previousAmounts.getCostPrice().getComponentsCostPrice().getValue());
			record.setPrev_CurrentQty(previousAmounts.getQty().toBigDecimal());

			record.setPrev_CumulatedAmt(previousAmounts.getCumulatedAmt().getValue());
=======
			record.setPrev_CurrentCostPrice(previousAmounts.getCostPrice().getOwnCostPrice().toBigDecimal());
			record.setPrev_CurrentCostPriceLL(previousAmounts.getCostPrice().getComponentsCostPrice().toBigDecimal());
			record.setPrev_CurrentQty(previousAmounts.getQty().toBigDecimal());

			record.setPrev_CumulatedAmt(previousAmounts.getCumulatedAmt().toBigDecimal());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			record.setPrev_CumulatedQty(previousAmounts.getCumulatedQty().toBigDecimal());
		}

		record.setIsSOTrx(cd.isOutboundTrx());
		updateRecordFromDocumentRef(record, cd.getDocumentRef());

		record.setDescription(cd.getDescription());

<<<<<<< HEAD
=======
		record.setDateAcct(Timestamp.from(cd.getDateAcct()));

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		record.setProcessed(true); // TODO: get rid of Processed flag, or always set it!
		saveRecord(record);
		final CostDetailId id = CostDetailId.ofRepoId(record.getM_CostDetail_ID());

		return cd.withId(id);
	}

<<<<<<< HEAD
	private static void updateRecordFromDocumentRef(
			final I_M_CostDetail record,
			final CostingDocumentRef documentRef)
	{
		final String tableName = documentRef.getTableName();
		final int recordId = documentRef.getRecordId();
		// final Boolean soTrx = documentRef.getOutboundTrx();
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
			// record.setIsSOTrx(soTrx);
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		else if (CostingDocumentRef.TABLE_NAME_M_InventoryLine.equals(tableName))
		{
			record.setM_InventoryLine_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_MovementLine.equals(tableName))
		{
			record.setM_MovementLine_ID(recordId);
<<<<<<< HEAD
			// record.setIsSOTrx(soTrx);
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		else if (CostingDocumentRef.TABLE_NAME_C_ProjectIssue.equals(tableName))
		{
			record.setC_ProjectIssue_ID(recordId);
		}
		else if (CostingDocumentRef.TABLE_NAME_PP_Cost_Collector.equals(tableName))
		{
			record.setPP_Cost_Collector_ID(recordId);
		}
<<<<<<< HEAD
=======
		else if (CostingDocumentRef.TABLE_NAME_M_CostRevaluationLine.equals(tableName))
		{
			final CostRevaluationLineId costRevaluationLineId = documentRef.getId(CostRevaluationLineId.class);
			record.setM_CostRevaluation_ID(costRevaluationLineId.getCostRevaluationId().getRepoId());
			record.setM_CostRevaluationLine_ID(costRevaluationLineId.getRepoId());
		}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
	public Optional<CostDetail> firstOnly(@NonNull final CostDetailQuery query)
	{
		return toSqlQuery(query)
				.firstOnlyOptional()
				.map(this::toCostDetail);
	}

	private IQueryBuilder<I_M_CostDetail> toSqlQuery(@NonNull final CostDetailQuery query)
	{
		final IQueryBuilder<I_M_CostDetail> queryBuilder = queryBL.createQueryBuilder(I_M_CostDetail.class);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
			queryBuilder.addEqualsFilter(documentRef.getCostDetailColumnName(), documentRef.getRecordId());
=======
			queryBuilder.addEqualsFilter(documentRef.getCostDetailColumnName(), documentRef.getId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			someFiltersApplied = true;

			// IsSOTrx
			if (documentRef.getOutboundTrx() != null)
			{
				queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_IsSOTrx, documentRef.getOutboundTrx());
			}
		}

<<<<<<< HEAD
		// Product
		if (query.getProductId() != null)
		{
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_M_Product_ID, query.getProductId());
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_AD_Client_ID, query.getClientId());
=======
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMNNAME_AD_Client_ID, query.getClientId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			someFiltersApplied = true;
		}
		if (query.getOrgId() != null)
		{
<<<<<<< HEAD
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMN_AD_Org_ID, query.getOrgId());
=======
			queryBuilder.addEqualsFilter(I_M_CostDetail.COLUMNNAME_AD_Org_ID, query.getOrgId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			someFiltersApplied = true;
		}

		// After M_CostDetail_ID
		if (query.getAfterCostDetailId() != null)
		{
			queryBuilder.addCompareFilter(I_M_CostDetail.COLUMN_M_CostDetail_ID, Operator.GREATER, query.getAfterCostDetailId());
			someFiltersApplied = true;
		}

<<<<<<< HEAD
=======
		if (query.getDateAcctRage() != null)
		{
			queryBuilder.addInRange(I_M_CostDetail.COLUMNNAME_DateAcct, query.getDateAcctRage());
			someFiltersApplied = true;
		}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		// Fail if no filters were applied. Else we would fetch the whole database.
		if (!someFiltersApplied)
		{
			throw new AdempiereException("Invalid query. No filters were applied: " + query);
		}

		//
<<<<<<< HEAD
		return queryBuilder;
	}

	private CostDetail toCostDetail(final I_M_CostDetail record)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID());

		final ProductId productId = ProductId.ofRepoId(record.getM_Product_ID());
<<<<<<< HEAD
		final I_C_UOM productUOM = uomsRepo.getById(record.getC_UOM_ID());

		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final CostAmount amt = CostAmount.of(record.getAmt(), currencyId);
		final Quantity qty = Quantity.of(record.getQty(), productUOM);
=======
		final I_C_UOM productUOM = uomDAO.getById(record.getC_UOM_ID());

		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final CurrencyId sourceCurrencyId = CurrencyId.ofRepoIdOrNull(record.getSource_Currency_ID());
		final CostAmount amt = CostAmount.of(record.getAmt(), currencyId, record.getSourceAmt(), sourceCurrencyId);
		final Quantity qty = Quantitys.of(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID()));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		return CostDetail.builder()
				.id(CostDetailId.ofRepoId(record.getM_CostDetail_ID()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.acctSchemaId(acctSchemaId)
				.costElementId(CostElementId.ofRepoId(record.getM_CostElement_ID()))
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(record.getM_AttributeSetInstance_ID()))
<<<<<<< HEAD
=======
				.amtType(CostAmountType.ofCode(record.getM_CostDetail_Type()))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
				.build();
	}

	private static CostingDocumentRef extractDocumentRef(final I_M_CostDetail record)
=======
				.dateAcct(record.getDateAcct().toInstant())
				.build();
	}

	@VisibleForTesting
	static CostingDocumentRef extractDocumentRef(final I_M_CostDetail record)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		else if (record.getC_ProjectIssue_ID() > 0)
		{
			return CostingDocumentRef.ofProjectIssueId(record.getC_ProjectIssue_ID());
		}
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		else if (record.getPP_Cost_Collector_ID() > 0)
		{
			return CostingDocumentRef.ofCostCollectorId(record.getPP_Cost_Collector_ID());
		}
<<<<<<< HEAD
=======
		else if (record.getM_CostRevaluationLine_ID() > 0)
		{
			return CostingDocumentRef.ofCostRevaluationLineId(CostRevaluationLineId.ofRepoId(record.getM_CostRevaluation_ID(), record.getM_CostRevaluationLine_ID()));
		}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		else
		{
			throw new AdempiereException("Cannot determine " + CostingDocumentRef.class + " for " + record);
		}
	}

	@Override
<<<<<<< HEAD
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
=======
	public boolean hasCostDetailsByProductId(@NonNull final ProductId productId)
	{
		return toSqlQuery(CostDetailQuery.builder().productId(productId).build())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.anyMatch();
	}

	@Override
<<<<<<< HEAD
	public Stream<CostDetail> streamOrderedById(@NonNull final CostDetailQuery query)
	{
		return createQueryBuilder(query)
				.clearOrderBys()
				.orderBy(I_M_CostDetail.COLUMN_M_CostDetail_ID)
				.create()
=======
	public Stream<CostDetail> stream(@NonNull final CostDetailQuery query)
	{
		return toSqlQuery(query)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.iterateAndStream()
				.map(this::toCostDetail);
	}

<<<<<<< HEAD
	private List<CostDetail> listOrderedById(@NonNull final CostDetailQuery query)
	{
		return createQueryBuilder(query)
				.clearOrderBys()
				.orderBy(I_M_CostDetail.COLUMN_M_CostDetail_ID)
				.create()
=======
	@Override
	public ImmutableList<CostDetail> list(@NonNull final CostDetailQuery query)
	{
		return toSqlQuery(query)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.stream()
				.map(this::toCostDetail)
				.collect(ImmutableList.toImmutableList());
	}

}
