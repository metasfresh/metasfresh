package de.metas.costing;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_CostDetail;
import org.compiere.model.MAcctSchema;
import org.compiere.util.Env;

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
	protected final ICurrentCostsRepository currentCostsRepo;
	protected final ICostDetailRepository costDetailsRepo;

	protected CostingMethodHandlerTemplate(
			@NonNull ICurrentCostsRepository currentCostsRepo,
			@NonNull final ICostDetailRepository costDetailsRepo)
	{
		this.currentCostsRepo = currentCostsRepo;
		this.costDetailsRepo = costDetailsRepo;
	}

	@Override
	public BigDecimal calculateSeedCosts(final CostSegment costSegment, final int orderLineId)
	{
		return null;
	}

	@Override
	public final CostDetailCreateResult createCost(final CostDetailCreateRequest request)
	{
		//
		// Check if existing cost detail
		final I_M_CostDetail costDetail = getExistingCostDetailOrNull(request);
		if (costDetail != null)
		{
			return createCostDetailCreateResult(costDetail, request);
		}

		//
		// Create new cost detail
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
		else if (CostingDocumentRef.TABLE_NAME_M_InOutLine.equals(documentTableName))
		{
			final Boolean outboundTrx = documentRef.getOutboundTrx();
			if (outboundTrx)
			{
				return createCostForMaterialShipment(request);
			}
			else
			{
				return createCostForMaterialReceipt(request);
			}
		}
		else if (CostingDocumentRef.TABLE_NAME_M_MovementLine.equals(documentTableName))
		{
			return createCostForMovementLine(request);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_InventoryLine.equals(documentTableName))
		{
			return createCostForInventoryLine(request);
		}
		else if (CostingDocumentRef.TABLE_NAME_M_ProductionLine.equals(documentTableName))
		{
			return createCostForProductionLine(request);
		}
		else if (CostingDocumentRef.TABLE_NAME_C_ProjectIssue.equals(documentTableName))
		{
			return createCostForProjectIssue(request);
		}
		else if (CostingDocumentRef.TABLE_NAME_PP_Cost_Collector.equals(documentTableName))
		{
			return createCostForCostCollector(request);
		}
		else
		{
			throw new AdempiereException("Unknown documentRef: " + documentRef);
		}
	}

	protected I_M_CostDetail getExistingCostDetailOrNull(final CostDetailCreateRequest request)
	{
		final CostDetailQuery costDetailQuery = extractCostDetailQuery(request);
		return costDetailsRepo.getCostDetailOrNull(costDetailQuery);
	}

	private static CostDetailQuery extractCostDetailQuery(final CostDetailCreateRequest request)
	{
		final CostElement costElement = request.getCostElement();

		return CostDetailQuery.builder()
				.acctSchemaId(request.getAcctSchemaId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.costElementId(costElement != null ? costElement.getId() : -1)
				.documentRef(request.getDocumentRef())
				.build();
	}

	protected CostDetailCreateResult createCostForMatchPO(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMatchInvoice(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMaterialReceipt(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForMaterialShipment(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForMovementLine(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForInventoryLine(final CostDetailCreateRequest request)
	{
		return createOutboundCostDefaultImpl(request);
	}

	protected CostDetailCreateResult createCostForProductionLine(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForProjectIssue(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createCostForCostCollector(final CostDetailCreateRequest request)
	{
		// nothing on this level
		return null;
	}

	protected CostDetailCreateResult createOutboundCostDefaultImpl(final CostDetailCreateRequest request)
	{
		return createCostDefaultImpl(request);
	}

	protected final CostDetailCreateResult createCostDefaultImpl(final CostDetailCreateRequest request)
	{
		final I_M_CostDetail costDetail = createDraftCostDetail(request);
		markProcessedAndSave(costDetail);
		return createCostDetailCreateResult(costDetail, request);
	}

	private I_M_CostDetail createDraftCostDetail(@NonNull final CostDetailCreateRequest request)
	{
		final I_M_CostDetail costDetail = InterfaceWrapperHelper.newInstance(I_M_CostDetail.class);
		final int costDetailClientId = costDetail.getAD_Client_ID();
		Check.assume(costDetailClientId == request.getClientId(), "same AD_Client_ID: {} vs {}", costDetailClientId, request.getClientId());
		costDetail.setAD_Org_ID(request.getOrgId());
		costDetail.setC_AcctSchema_ID(request.getAcctSchemaId());
		costDetail.setM_Product_ID(request.getProductId());
		costDetail.setM_AttributeSetInstance_ID(request.getAttributeSetInstanceId());

		final CostElement costElement = request.getCostElement();
		if (costElement != null)
		{
			costDetail.setM_CostElement_ID(costElement.getId());
		}

		costDetail.setAmt(request.getAmt().getValue());
		costDetail.setQty(request.getQty().getQty());

		costDetail.setDescription(request.getDescription());

		final CostingDocumentRef documentRef = request.getDocumentRef();
		InterfaceWrapperHelper.setValue(costDetail, documentRef.getCostDetailColumnName(), documentRef.getRecordId());

		if (documentRef.getOutboundTrx() != null)
		{
			costDetail.setIsSOTrx(documentRef.getOutboundTrx());
		}

		return costDetail;
	}

	protected final CurrentCost getCurrentCost(final CostDetailCreateRequest request)
	{
		final CostSegment costSegment = extractCostSegment(request);

		final CostElement costElement = request.getCostElement();
		final int costElementId = costElement != null ? costElement.getId() : -1;

		return currentCostsRepo.getOrCreate(costSegment, costElementId);
	}

	private static CostDetailCreateResult createCostDetailCreateResult(final I_M_CostDetail costDetail, final CostDetailCreateRequest request)
	{
		final MAcctSchema as = MAcctSchema.get(Env.getCtx(), request.getAcctSchemaId());
		return CostDetailCreateResult.builder()
				.costSegment(extractCostSegment(request))
				.costElement(request.getCostElement())
				.amt(CostAmount.of(costDetail.getAmt(), as.getC_Currency_ID()))
				.build();
	}

	private static CostSegment extractCostSegment(final CostDetailCreateRequest request)
	{
		final MAcctSchema as = MAcctSchema.get(Env.getCtx(), request.getAcctSchemaId());
		final IProductBL productBL = Services.get(IProductBL.class);
		final CostingLevel costingLevel = productBL.getCostingLevel(request.getProductId(), as);
		final int costTypeId = as.getM_CostType_ID();

		return CostSegment.builder()
				.costingLevel(costingLevel)
				.acctSchemaId(request.getAcctSchemaId())
				.costTypeId(costTypeId)
				.productId(request.getProductId())
				.clientId(request.getClientId())
				.orgId(request.getOrgId())
				.attributeSetInstanceId(request.getAttributeSetInstanceId())
				.build();
	}

	protected final void saveCurrentCosts(final CurrentCost currentCost)
	{
		currentCostsRepo.save(currentCost);
	}

	protected final void markProcessedAndSave(final I_M_CostDetail costDetail)
	{
		costDetail.setProcessed(true); // TODO: get rid of Processed flag, or always set it!
		costDetailsRepo.save(costDetail);
	}

	@Override
	public void beforeDelete(final I_M_CostDetail costDetail)
	{
		// TODO implement beforeDelete for each method...

		// if (costDetail.isProcessed())
		// {
		// costDetail.setProcessed(false);
		// costDetail.setDeltaAmt(costDetail.getAmt());
		// costDetail.setDeltaQty(costDetail.getQty());
		// process(costDetail);
		// Check.assume(costDetail.isProcessed(), "Cost detail {} shall be processed at this point", costDetail); // shall not happen
		// }
	}
}
