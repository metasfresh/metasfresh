package de.metas.costing;

import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.I_M_Product;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCost;
import org.compiere.model.MProduct;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import lombok.NonNull;

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

public abstract class CostingMethodHandlerTemplate implements CostingMethodHandler
{
	private static final Logger logger = LogManager.getLogger(CostingMethodHandlerTemplate.class);

	@Override
	public BigDecimal calculateSeedCosts(final CostSegment costSegment, final int orderLineId)
	{
		return null;
	}

	@Override
	public final I_M_CostDetail createCost(final CostDetailCreateRequest request)
	{
		final CostingDocumentRef documentRef = request.getDocumentRef();
		final String documentTableName = documentRef.getTableName();
		if (CostingDocumentRef.TABLE_NAME_M_MatchPO.equals(documentTableName))
		{
			return createCostForMatchPO(request);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_MatchInv.equals(documentTableName))
		{
			return createCostForMatchInvoice(request);
		}
		else if(CostingDocumentRef.TABLE_NAME_M_InOutLine.equals(documentTableName))
		{
			return createCostForMaterialShipment(request);
		}
		else if(CostingDocumentRef.TABLE_NAME_M_MovementLine.equals(documentTableName))
		{
			return createCostForMovementLine(request);
		}
		else if(CostingDocumentRef.TABLE_NAME_M_InventoryLine.equals(documentTableName))
		{
			return createCostForInventoryLine(request);
		}
		else if(CostingDocumentRef.TABLE_NAME_M_ProductionLine.equals(documentTableName))
		{
			return createCostForProductionLine(request);
		}
		else if(CostingDocumentRef.TABLE_NAME_C_ProjectIssue.equals(documentTableName))
		{
			return createCostForProjectIssue(request);
		}
		else if(CostingDocumentRef.TABLE_NAME_PP_Cost_Collector.equals(documentTableName))
		{
			return createCostForCostCollector(request);
		}
		else
		{
			throw new AdempiereException("Unknown documentRef: " + documentRef);
		}
	}

	protected I_M_CostDetail createCostForMatchPO(CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected I_M_CostDetail createCostForMatchInvoice(CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected I_M_CostDetail createCostForMaterialShipment(CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected I_M_CostDetail createCostForMovementLine(CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected I_M_CostDetail createCostForInventoryLine(CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected I_M_CostDetail createCostForProductionLine(CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected I_M_CostDetail createCostForProjectIssue(CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected I_M_CostDetail createCostForCostCollector(CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected I_M_CostDetail createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		return createCostDefaultImpl(request);
	}

	protected I_M_CostDetail createCostDefaultImpl(final CostDetailCreateRequest request)
	{
		final ICostDetailRepository costDetailsRepo = Services.get(ICostDetailRepository.class);

		final CostDetailQuery costDetailForDocumentQuery = CostDetailQuery.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.costElementId(request.getCostElementId())
				.documentRef(request.getDocumentRef())
				.build();

		// Delete all unprocessed or zero differences for given document
		costDetailsRepo.deleteUnprocessedWithNoChanges(costDetailForDocumentQuery);

		//
		// Create/Update the cost detail
		I_M_CostDetail costDetail = costDetailsRepo.getCostDetailOrNull(costDetailForDocumentQuery);
		if (costDetail == null)		// createNew
		{
			costDetail = createDraftCostDetail(request);
		}
		else
		{
			updateCostDetailFromCreateRequest(costDetail, request);
		}
		//
		costDetailsRepo.save(costDetail);

		return costDetail;
	}

	private I_M_CostDetail createDraftCostDetail(@NonNull final CostDetailCreateRequest request)
	{
		final I_M_CostDetail costDetail = InterfaceWrapperHelper.newInstance(I_M_CostDetail.class);
		costDetail.setAD_Org_ID(request.getOrgId());
		costDetail.setC_AcctSchema_ID(request.getAcctSchemaId());
		costDetail.setM_Product_ID(request.getProductId());
		costDetail.setM_AttributeSetInstance_ID(request.getAttributeSetInstanceId());

		costDetail.setM_CostElement_ID(request.getCostElementId());

		costDetail.setAmt(request.getAmt().getValue());
		costDetail.setQty(request.getQty());
		costDetail.setDescription(request.getDescription());

		final CostingDocumentRef documentRef = request.getDocumentRef();
		InterfaceWrapperHelper.setValue(costDetail, documentRef.getCostDetailColumnName(), documentRef.getRecordId());

		if (documentRef.getOutboundTrx() != null)
		{
			costDetail.setIsSOTrx(documentRef.getOutboundTrx());
		}

		return costDetail;
	}

	private void updateCostDetailFromCreateRequest(final I_M_CostDetail costDetail, final CostDetailCreateRequest request)
	{
		final BigDecimal amt = request.getAmt().getValue();
		final BigDecimal qty = request.getQty();
		costDetail.setDeltaAmt(amt.subtract(costDetail.getAmt()));
		costDetail.setDeltaQty(qty.subtract(costDetail.getQty()));
		if (isDelta(costDetail))
		{
			costDetail.setProcessed(false);
			costDetail.setAmt(amt);
			costDetail.setQty(qty);
		}
	}

	private static boolean isDelta(final I_M_CostDetail costDetail)
	{
		return !(costDetail.getDeltaAmt().signum() == 0
				&& costDetail.getDeltaQty().signum() == 0);
	}	// isDelta

	protected final CurrentCost getCurrentCost(final CostDetailCreateRequest request)
	{
		final Properties ctx = Env.getCtx();
		final I_M_Product product = MProduct.get(ctx, request.getProductId());
		final MAcctSchema as = MAcctSchema.get(ctx, request.getAcctSchemaId());
		final IProductBL productBL = Services.get(IProductBL.class);
		final CostingLevel costingLevel = productBL.getCostingLevel(product, as);
		final int costTypeId = as.getM_CostType_ID();

		final CostSegment costSegment = CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(request.getAcctSchemaId())
				.costTypeId(costTypeId)
				.productId(request.getProductId())
				.clientId(request.getClientId())
				.orgId(request.getOrgId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.build();

		final I_M_Cost costRecord = MCost.getOrCreate(costSegment, request.getCostElementId());
		return toCurrentCost(costRecord, as.getC_Currency_ID(), as.getCostingPrecision());
	}

	@Override
	public final void process(final CostDetailEvent event)
	{
		final I_M_Cost costRecord = MCost.getOrCreate(event.getCostSegment(), event.getCostElementId());
		final CurrentCost cost = toCurrentCost(costRecord, event.getCurrencyId(), event.getPrecision());

		final CostingDocumentRef documentRef = event.getDocumentRef();
		final String documentTableName = documentRef.getTableName();
		if (CostingDocumentRef.TABLE_NAME_M_MatchPO.equals(documentTableName))
		{
			processMatchPO(event, cost);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_MatchInv.equals(documentTableName))
		{
			processMatchInvoice(event, cost);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_InOutLine.equals(documentTableName))
		{
			processMaterialShipment(event, cost);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_MovementLine.equals(documentTableName))
		{
			processMovementLine(event, cost);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_InventoryLine.equals(documentTableName))
		{
			processInventoryLine(event, cost);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_ProductionLine.equals(documentTableName))
		{
			processProductionLine(event, cost);
		}
		else if (CostingDocumentRef.TABLE_NAME_C_ProjectIssue.equals(documentTableName))
		{
			processProjectIssue(event, cost);
		}
		else if (CostingDocumentRef.TABLE_NAME_PP_Cost_Collector.equals(documentTableName))
		{
			processCostCollector(event, cost);
		}
		else
		{
			processOther(event, cost);
		}

		//
		updateCostRecord(costRecord, cost);
		// costRecord.setProcessed(true); // FIXME Processed is a virtual column ?!?! wtf?!
		InterfaceWrapperHelper.save(costRecord);
	}

	protected void processMatchPO(final CostDetailEvent event, final CurrentCost cost)
	{
		// nothing on this level
	}

	protected void processMatchInvoice(final CostDetailEvent event, final CurrentCost cost)
	{
		// nothing on this level
	}

	protected void processOutboundTransactionDefaultImpl(final CostDetailEvent event, final CurrentCost cost)
	{
		// nothing on this level
	}

	protected void processMaterialShipment(final CostDetailEvent event, final CurrentCost cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processMovementLine(final CostDetailEvent event, final CurrentCost cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processInventoryLine(final CostDetailEvent event, final CurrentCost cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processProductionLine(final CostDetailEvent event, final CurrentCost cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processProjectIssue(final CostDetailEvent event, final CurrentCost cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processCostCollector(final CostDetailEvent event, final CurrentCost cost)
	{
		processOutboundTransactionDefaultImpl(event, cost);
	}

	protected void processOther(final CostDetailEvent event, final CurrentCost cost)
	{
		logger.warn("Skip event because document is not handled: {}", event);
	}

	private CurrentCost toCurrentCost(
			final I_M_Cost costRecord,
			final int currencyId, 
			final int precision)
	{
		return CurrentCost.builder()
				.id(costRecord.getM_Cost_ID())
				.currencyId(currencyId)
				.precision(precision)
				.currentCostPrice(costRecord.getCurrentCostPrice())
				.currentCostPriceLL(costRecord.getCurrentCostPriceLL())
				.currentQty(costRecord.getCurrentQty())
				.cumulatedAmt(costRecord.getCumulatedAmt())
				.cumulatedQty(costRecord.getCumulatedQty())
				.build();
	}

	private void updateCostRecord(final I_M_Cost cost, final CurrentCost from)
	{
		cost.setCurrentCostPrice(from.getCurrentCostPrice().getValue());
		cost.setCurrentCostPriceLL(from.getCurrentCostPriceLL().getValue());
		cost.setCurrentQty(from.getCurrentQty());
		cost.setCumulatedAmt(from.getCumulatedAmt().getValue());
		cost.setCumulatedQty(from.getCumulatedQty());
	}
}
